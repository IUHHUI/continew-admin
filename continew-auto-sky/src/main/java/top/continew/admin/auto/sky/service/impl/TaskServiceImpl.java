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
import cn.hutool.json.JSONUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.continew.admin.auto.sky.mapper.CamiMapper;
import top.continew.admin.auto.sky.mapper.DeviceMapper;
import top.continew.admin.auto.sky.mapper.TaskCamiMapper;
import top.continew.admin.auto.sky.mapper.TaskMapper;
import top.continew.admin.auto.sky.model.entity.*;
import top.continew.admin.auto.sky.model.query.TaskQuery;
import top.continew.admin.auto.sky.model.req.GameClientLoginCallback;
import top.continew.admin.auto.sky.model.req.GameLoginReq;
import top.continew.admin.auto.sky.model.req.TaskCamiReq;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.GameLoginResp;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;
import top.continew.admin.auto.sky.service.TaskService;
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
    @Autowired
    private CamiMapper camiMapper;
    @Autowired
    private TaskCamiMapper taskCamiMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    // 缓存游戏登录信息
    private final Cache<String, GameLoggingInfo> gameLoggingCache = CacheBuilder.newBuilder()
        .maximumSize(5000L)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build();

    @Override
    public GameLoginResp gameLoginState(GameLoginReq req) {
        var cami = camiMapper.lambdaQuery()
            .select(CamiDO::getId, CamiDO::getCami, CamiDO::getState, CamiDO::getIsUrgent, CamiDO::getLoginInfo, CamiDO::getCreateTime, CamiDO::getCreateUser)
            .eq(CamiDO::getCami, req.getCami())
            .one();
        CheckUtils.throwIfNull(cami, "卡密不存在");

        CheckUtils.throwIfNotEqual(Long.parseLong(req.getRandNum()), cami.getCreateTime()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(), "卡密不存在");

        CheckUtils.throwIf(cami.getState() == CamiState.USED.getState(), "卡密已使用");
        //1. 卡密还没有被使用.
        //2. 卡密已使用
        //3. 卡密在使用
        if (cami.getState() == CamiState.INIT.getState()) {
            return buildLoginRespOfCamiInit(cami);
        } else if (cami.getState() == CamiState.USED.getState()) {
            return buildLoginRespOfCamiUsed(cami);
        } else {
            return buildLoginRespOfCamiLogging(cami);
        }
    }

    private GameLoginResp buildLoginRespOfCamiInit(CamiDO cami) {
        GameLoginResp resp = new GameLoginResp();
        var gameLogging = gameLoggingCache.getIfPresent(cami.getCami());
        if (null == gameLogging) {
            resp.setState(GameLoginState.INIT.getState());
            resp.setType(GameLoginType.PHONE_PASSWORD.getType());
        } else {
            BeanUtil.copyProperties(gameLogging, resp);
        }
        return resp;
    }

    private GameLoginResp buildLoginRespOfCamiUsed(CamiDO cami) {
        GameLoginResp resp = new GameLoginResp();
        //已经登录成功了, 让前端感觉当前登录情况,
        if (StringUtils.isNotBlank(cami.getLoginInfo())) {
            var d = JSONUtil.toBean(cami.getLoginInfo(), GameLoginData.class);
            BeanUtil.copyProperties(d, resp);
        }

        var taskCamiDO = taskCamiMapper.lambdaQuery()
            .select(TaskCamiDO::getId, TaskCamiDO::getTaskId, TaskCamiDO::getCamiId)
            .eq(TaskCamiDO::getCamiId, cami.getId())
            .one();
        CheckUtils.throwIfNull(taskCamiDO, "任务卡密关系不存在");

        var taskDO = get(taskCamiDO.getTaskId());
        CheckUtils.throwIfNull(taskDO, "任务不存在");

        //TODO now
        resp.setAppointmentTime(LocalDateTime.now());
        resp.setRemainingDays(taskDO.getNeedTime() - taskDO.getRanTime());
        resp.setEndTime(LocalDateTime.now().plusDays(taskDO.getNeedTime() - taskDO.getRanTime()));
        return resp;
    }

    private GameLoginResp buildLoginRespOfCamiLogging(CamiDO cami) {
        //有登录信息但是还没有登录成功
        GameLoginResp resp = new GameLoginResp();
        var gameLogging = gameLoggingCache.getIfPresent(cami.getCami());
        CheckUtils.throwIfNull(gameLogging, "卡密状态不对");
        BeanUtil.copyProperties(gameLogging, resp);
        return resp;
    }

    @Override
    public void gameLoginSubmit1(GameLoginReq req) {
        checkLoginReq(req);
        CheckUtils.throwIf(req.getType() == CamiState.USED.getState(), "卡密已使用");
        boolean wrongType = !Objects.equals(req.getType(), GameLoginType.QR_CODE.getType()) && !Objects.equals(req.getType(), GameLoginType.PHONE_SMS.getType());
        CheckUtils.throwIf(wrongType, "登录类型错误");

        var camiDO = camiMapper.lambdaQuery()
            .select(CamiDO::getId, CamiDO::getCami, CamiDO::getState, CamiDO::getIsUrgent, CamiDO::getCreateTime, CamiDO::getCreateUser)
            .eq(CamiDO::getCami, req.getCami())
            .one();

        this.checkGameLoginSubmit(req, camiDO);

        this.gameLoginSubmit1(req, camiDO);
    }

    private void updateCamiState(String cami, long updateUser, int state, GameLoginData data) {
        CamiDO camiDO = new CamiDO();
        camiDO.setUpdateTime(LocalDateTime.now());
        camiDO.setUpdateUser(updateUser);
        camiDO.setState(state);
        camiDO.setLoginInfo(JSONUtil.toJsonStr(data));
        camiMapper.updateById(camiDO);
    }

    /**
     * 对应短信认证码和二维码登录.
     *
     * @param req 登录参数
     */
    private void gameLoginSubmit1(GameLoginReq req, CamiDO camiDO) {
        var newGameLogging = new GameLoggingInfo();
        BeanUtil.copyProperties(req, newGameLogging);
        //获取上号设备
        var device = getDeviceForLogin();
        CheckUtils.throwIfNull(device, "没有上号设备");
        newGameLogging.setState(GameLoginState.LOGGING_1_BEGIN.getState());
        newGameLogging.setDevice(device.getDevice());
        gameLoggingCache.put(camiDO.getCami(), newGameLogging);
    }

    @Override
    public void gameLoginSubmit2(GameLoginReq req) {
        checkLoginReq(req);
        CheckUtils.throwIf(req.getType() == CamiState.USED.getState(), "卡密已使用");

        var camiDO = camiMapper.lambdaQuery()
            .select(CamiDO::getId, CamiDO::getCami, CamiDO::getState, CamiDO::getIsUrgent, CamiDO::getCreateTime, CamiDO::getCreateUser)
            .eq(CamiDO::getCami, req.getCami())
            .one();

        this.checkGameLoginSubmit(req, camiDO);

        var newGameLogging = new GameLoggingInfo();
        BeanUtil.copyProperties(req, newGameLogging);
        //获取上号设备
        var device = getDeviceForLogin();
        CheckUtils.throwIfNull(device, "没有上号设备");
        newGameLogging.setState(GameLoginState.LOGGING_2.getState());
        newGameLogging.setDevice(device.getDevice());
        gameLoggingCache.put(camiDO.getCami(), newGameLogging);
    }

    /**
     * 游戏登录状态.
     *
     * @param req 登录参数
     */
    private void checkGameLoginSubmit(GameLoginReq req, CamiDO camiDO) {
        CheckUtils.throwIfNull(req.getChannel(), "渠道不能为空");
        CheckUtils.throwIfNull(camiDO, "卡密不存在");
        CheckUtils.throwIfNotEqual(Long.parseLong(req.getRandNum()), camiDO.getCreateTime()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(), "卡密不存在");
        CheckUtils.throwIf(camiDO.getState() == CamiState.USED.getState(), "卡密已使用");

        var gameLogging = gameLoggingCache.getIfPresent(camiDO.getCami());
        if (gameLogging != null) {
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGIN_SUCCESS.getState()), "已经登录成功");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_1_BEGIN.getState()), "正在登录");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_2.getState()), "正在登录");
        }
    }

    @Override
    public void gameClientLoginCallback(GameClientLoginCallback info) {
        log.info("更新游戏登录状态:{}", info);

        //verify device.
        var device = deviceMapper.lambdaQuery()
            .select(DeviceDO::getId, DeviceDO::getDevice, DeviceDO::getState)
            .eq(DeviceDO::getDevice, info.getDevice())
            .one();
        CheckUtils.throwIfNull(device, "设备不存在");

        var gameLogging = gameLoggingCache.getIfPresent(info.getCami());
        CheckUtils.throwIfNull(gameLogging, "游戏登录状态不存在");
        CheckUtils.throwIf(!Objects.equals(gameLogging.getDevice(), info.getDevice()), "设备不匹配");
        var camiDO = camiMapper.lambdaQuery()
            .select(CamiDO::getId, CamiDO::getCami, CamiDO::getState, CamiDO::getIsUrgent, CamiDO::getCreateTime, CamiDO::getCreateUser)
            .eq(CamiDO::getCami, info.getCami())
            .one();
        CheckUtils.throwIfNull(camiDO, "卡密不存在");

        if (!(Objects.equals(info.getDevice(), gameLogging.getDevice()) && Objects.equals(info.getPhone(), gameLogging.getPhone()) && Objects.equals(info.getChannel(), gameLogging.getChannel()) && Objects.equals(info.getType(), gameLogging.getType()))) {
            CheckUtils.throwIf(true, "登录参数不匹配");
        }

        //GameLoginState.LOGGING_1_END, GameLoginState.LOGIN_FAIL, GameLoginState.LOGIN_SUCCESS
        if (Objects.equals(info.getState(), GameLoginState.LOGGING_1_END.getState())) {
            if (StringUtils.isNotBlank(info.getQrCode())) {
                gameLogging.setType(GameLoginType.QR_CODE.getType());
                gameLogging.setQrCode(info.getQrCode());
            } else {
                gameLogging.setType(info.getType());
                gameLogging.setQrCode("");
            }
            gameLogging.setSms("");
            gameLogging.setPassword("");
            gameLogging.setState(GameLoginState.LOGGING_1_END.getState());
            //update
            gameLoggingCache.put(camiDO.getCami(), gameLogging);
            return;
        } else if (Objects.equals(info.getState(), GameLoginState.LOGIN_FAIL.getState())) {
            gameLogging.setState(GameLoginState.LOGIN_FAIL.getState());
            gameLoggingCache.put(camiDO.getCami(), gameLogging);
            return;
        } else if (!Objects.equals(info.getState(), GameLoginState.LOGIN_SUCCESS.getState())) {
            log.error("invalid callback:{}", info);
            return;
        }

        //game login success
        //create task
        TaskReq taskReq = new TaskReq();
        taskReq.setOrderId(camiDO.getOrderId());
        taskReq.setTaskDays(camiDO.getTaskSpec());
        taskReq.setNeedTime(camiDO.getDays());
        taskReq.setIsUrgent(camiDO.getIsUrgent());
        taskReq.setGameAccount(gameLogging.getPhone());
        taskReq.setChannel(gameLogging.getChannel().toString());
        var taskId = this.add(taskReq);

        //associate task and cami
        TaskCamiReq taskCamiReq = new TaskCamiReq();
        taskCamiReq.setTaskId(taskId);
        taskCamiReq.setCamiId(camiDO.getId());
        var taskCamiDO = new TaskCamiDO();
        taskCamiDO.setIsSelfCami(true);
        taskCamiDO.setTaskId(taskId);
        taskCamiDO.setCamiId(camiDO.getId());
        taskCamiDO.setCreateUser(camiDO.getCreateUser());
        taskCamiDO.setCreateTime(LocalDateTime.now());
        taskCamiMapper.insert(taskCamiDO);

        camiDO.setState(CamiState.USED.getState());
        camiDO.setUpdateUser(camiDO.getCreateUser());
        camiDO.setUpdateTime(LocalDateTime.now());
        GameLoginData loginData = new GameLoginData();
        BeanUtil.copyProperties(gameLogging, loginData);
        camiDO.setLoginInfo(JSONUtil.toJsonStr(loginData));
        camiMapper.updateById(camiDO);
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
            .select(DeviceDO::getId, DeviceDO::getDevice)
            .eq(DeviceDO::getState, 1)
            .eq(DeviceDO::getType, 1)
            .list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }
}