/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.auto.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.admin.auto.sky.mapper.CamiMapper;
import top.continew.admin.auto.sky.mapper.DeviceMapper;
import top.continew.admin.auto.sky.mapper.TaskCamiMapper;
import top.continew.admin.auto.sky.mapper.TaskMapper;
import top.continew.admin.auto.sky.model.entity.*;
import top.continew.admin.auto.sky.model.query.TaskQuery;
import top.continew.admin.auto.sky.model.req.GameClientLoginReq;
import top.continew.admin.auto.sky.model.req.GameLoginReq;
import top.continew.admin.auto.sky.model.req.TaskCamiReq;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.GameLoginResp;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;
import top.continew.admin.auto.sky.service.TaskService;
import top.continew.admin.common.context.UserContextHolder;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 游戏任务业务实现
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl extends BaseServiceImpl<TaskMapper, TaskDO, TaskResp, TaskDetailResp, TaskQuery, TaskReq> implements TaskService {
    private CamiMapper camiMapper;
    private TaskCamiMapper taskCamiMapper;
    private DeviceMapper deviceMapper;

    // 缓存游戏登录信息
    private final Cache<String, GameLoggingInfo> gameLoggingCache = CacheBuilder.newBuilder()
        .maximumSize(5000L)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build();

    @Override
    public GameLoginResp gameLoginState(GameLoginReq req) {
        var cami = camiMapper.lambdaQuery()
            .select(CamiDO::getCami, CamiDO::getCreateTime, CamiDO::getLoginInfo)
            .eq(CamiDO::getCami, req.getCamiUuid())
            .one();
        CheckUtils.throwIfNull(cami, "卡密不存在");
        CheckUtils.throwIf(Long.parseLong(req.getRandNum()) == cami.getCreateTime()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(), "卡密不存在");
        CheckUtils.throwIf(cami.getState() == 1, "卡密已使用");
        //卡密还没有被使用.
        var gameLogging = gameLoggingCache.getIfPresent(cami.getCami());
        GameLoginResp resp = new GameLoginResp();
        if (null == gameLogging) {
            resp.setState(GameLoginState.INIT.getState());
            resp.setType(GameLoginType.PHONE_PASSWORD.getType());
        } else {
            //让前端感觉当前登录情况, 展示页面. 正在:执行上号中请等待, 已经登录成功了.
            BeanUtil.copyProperties(gameLogging, resp);
        }
        return resp;
    }

    /**
     * 对应短信认证码和二维码登录.
     *
     * @param req 登录参数
     */
    @Override
    public void gameLogin1(GameLoginReq req) {
        checkLoginReq(req);
        var cami = camiMapper.lambdaQuery()
            .select(CamiDO::getCami, CamiDO::getCreateTime, CamiDO::getLoginInfo, CamiDO::getOrderId)
            .eq(CamiDO::getCami, req.getCamiUuid())
            .one();
        CheckUtils.throwIfNull(req.getChannel(), "渠道不能为空");
        CheckUtils.throwIfNull(cami, "卡密不存在");
        CheckUtils.throwIf(cami.getState() != 1, "卡密已使用");
        CheckUtils.throwIf(Long.parseLong(req.getRandNum()) == cami.getCreateTime()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(), "卡密不存在");

        var gameLogging = gameLoggingCache.getIfPresent(cami.getCami());
        if (gameLogging != null) {
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGIN_SUCCESS
                .getState()), "已经登录成功");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_1_BEGIN
                .getState()), "正在登录");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_2.getState()), "正在登录");
        }
        var newGameLogging = new GameLoggingInfo();
        BeanUtil.copyProperties(req, newGameLogging);
        //获取上号设备
        var device = getDeviceForLogin();
        CheckUtils.throwIfNull(device, "没有上号设备");
        newGameLogging.setState(GameLoginState.LOGGING_1_BEGIN.getState());
        newGameLogging.setDevice(device.getDevice());
        gameLoggingCache.put(cami.getCami(), newGameLogging);
    }

    @Override
    public void gameLogin2(GameLoginReq req) {
        checkLoginReq(req);
        var cami = camiMapper.lambdaQuery()
            .select(CamiDO::getCami, CamiDO::getCreateTime, CamiDO::getLoginInfo, CamiDO::getOrderId)
            .eq(CamiDO::getCami, req.getCamiUuid())
            .one();
        CheckUtils.throwIfNull(cami, "卡密不存在");
        CheckUtils.throwIf(Long.parseLong(req.getRandNum()) == cami.getCreateTime()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(), "卡密不存在");
        CheckUtils.throwIf(cami.getState() == 3, "卡密已使用");

        var gameLogging = gameLoggingCache.getIfPresent(cami.getCami());
        if (gameLogging != null) {
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGIN_SUCCESS
                .getState()), "已经登录成功");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_1_BEGIN
                .getState()), "正在登录");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_2.getState()), "正在登录");
        }
        var newGameLogging = new GameLoggingInfo();
        BeanUtil.copyProperties(req, newGameLogging);
        //获取上号设备
        var device = getDeviceForLogin();
        CheckUtils.throwIfNull(device, "没有上号设备");
        newGameLogging.setState(GameLoginState.LOGGING_2.getState());
        newGameLogging.setDevice(device.getDevice());
        gameLoggingCache.put(cami.getCami(), newGameLogging);
    }

    @Override
    public void updateGameLoginState(GameClientLoginReq req) {
        log.info("更新游戏登录状态:{}", req);

        var gameLogging = gameLoggingCache.getIfPresent(req.getCamiUuid());
        CheckUtils.throwIfNull(gameLogging, "游戏登录状态不存在");
        CheckUtils.throwIf(!Objects.equals(gameLogging.getDevice(), req.getDevice()), "设备不匹配");
        var camiDO = camiMapper.lambdaQuery()
            .select(CamiDO::getCami, CamiDO::getCreateTime, CamiDO::getLoginInfo, CamiDO::getOrderId)
            .eq(CamiDO::getCami, req.getCamiUuid())
            .one();
        CheckUtils.throwIfNull(camiDO, "卡密不存在");

        //create task
        TaskReq taskReq = new TaskReq();
        taskReq.setOrderId(camiDO.getOrderId());
        taskReq.setTaskDays(camiDO.getTaskSpec());
        taskReq.setNeedTime(camiDO.getDays());
        taskReq.setIsUrgent(camiDO.getIsUrgent());
        taskReq.setGameAccount(gameLogging.getPhone());
        taskReq.setChannel(gameLogging.getChannel().toString());
        var taskId = this.add(taskReq);

        TaskCamiReq taskCamiReq = new TaskCamiReq();
        taskCamiReq.setTaskId(taskId);
        taskCamiReq.setCamiId(camiDO.getId());
        var taskCamiDO = new TaskCamiDO();
        taskCamiDO.setIsSelfCami(true);
        taskCamiDO.setTaskId(taskId);
        taskCamiDO.setCamiId(camiDO.getId());
        taskCamiDO.setCreateUser(UserContextHolder.getUserId());
        taskCamiDO.setCreateTime(LocalDateTime.now());
        taskCamiMapper.insert(taskCamiDO);
    }

    private void checkLoginReq(GameLoginReq req) {
        // "登录类型:1手机密码,2邮箱密码,3手机验证码,4二维码"
        CheckUtils.throwIfNull(req.getPhone(), "手机号不能为空");
        if (req.getType() == GameLoginType.PHONE_PASSWORD.getType()) {
            CheckUtils.throwIfNull(req.getPassword(), "密码不能为空");
        } else if (req.getType() == GameLoginType.EMAIL_PASSWORD.getType()) {
            CheckUtils.throwIfNull(req.getEmail(), "邮箱不能为空");
        }
    }

    private DeviceDO getDeviceForLogin() {
        //TODO
        var list = deviceMapper.lambdaQuery()
            .select(DeviceDO::getId)
            .eq(DeviceDO::getState, 1)
            .eq(DeviceDO::getType, 1)
            .list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }
}