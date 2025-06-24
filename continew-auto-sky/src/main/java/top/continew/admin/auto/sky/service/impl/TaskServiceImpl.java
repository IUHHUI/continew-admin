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
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.GameLoginResp;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;
import top.continew.admin.auto.sky.service.TaskService;
import top.continew.admin.system.service.DictItemService;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Objects;
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

    @Autowired
    private DictItemService dictItemService;

    // 缓存游戏登录信息
    private final Cache<Long, GameLoginDetailInfo> gameLoginDetailInfoCache = CacheBuilder.newBuilder()
        .maximumSize(5000L)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build();

    @Override
    public GameLoginDetailInfo getGameLoginDetailInfo(Long taskId) {
        if (log.isDebugEnabled()) {
            log.debug("all is :{}", gameLoginDetailInfoCache.asMap().entrySet());
        }
        return gameLoginDetailInfoCache.getIfPresent(taskId);
    }

    @Override
    public GameChannelDO getGameChannelByVal(Integer channelVal) {
        var channels = dictItemService.listByDictCode(SkyDict.GAME_CHANNEL_DICT_CODE);
        var channel = channels.stream()
            .filter(item -> Objects.equals(item.getValue(), channelVal))
            .findFirst()
            .orElseThrow();
        return new GameChannelDO(channel.getLabel(), (Integer)channel.getValue());
    }

    @Override
    public GameChannelDO getGameChannelByName(String channelName) {
        var channels = dictItemService.listByDictCode(SkyDict.GAME_CHANNEL_DICT_CODE);
        var c = channels.stream()
            .filter(item -> Objects.equals(item.getLabel(), channelName))
            .findFirst()
            .orElseThrow();
        return new GameChannelDO(c.getLabel(), (Integer)c.getValue());
    }

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

        var taskCamiDO = taskCamiMapper.lambdaQuery()
            .select(TaskCamiDO::getId, TaskCamiDO::getTaskId, TaskCamiDO::getCamiId)
            .eq(TaskCamiDO::getCamiId, cami.getId())
            .one();
        CheckUtils.throwIfNull(taskCamiDO, "续单卡密不能登录");

        //1. 卡密还没有被使用.
        //2. 卡密已使用
        //3. 卡密在使用
        if (cami.getState() == CamiState.INIT.getState()) {
            return buildLoginRespOfCamiInit(taskCamiDO.getTaskId());
        } else if (cami.getState() == CamiState.USED.getState()) {
            return buildLoginRespOfCamiUsed(cami, taskCamiDO.getTaskId());
        } else {
            return buildLoginRespOfCamiLogging(taskCamiDO.getTaskId());
        }
    }

    private GameLoginResp buildLoginRespOfCamiInit(Long taskId) {
        GameLoginResp resp = new GameLoginResp();
        var gameLogging = gameLoginDetailInfoCache.getIfPresent(taskId);
        resp.setTaskId(taskId);
        if (null == gameLogging) {
            resp.setState(GameLoginState.INIT.getState());
            resp.setType(GameLoginType.PHONE_PASSWORD.getType());
        } else {
            BeanUtil.copyProperties(gameLogging, resp);
        }
        return resp;
    }

    private GameLoginResp buildLoginRespOfCamiUsed(CamiDO cami, Long taskId) {
        var taskDO = get(taskId);
        CheckUtils.throwIfNull(taskDO, cami + " 的任务不存在, taskId " + taskId);

        GameLoginResp resp = new GameLoginResp();
        resp.setTaskId(taskId);
        //已经登录成功了, 让前端感觉当前登录情况,
        if (StringUtils.isNotBlank(cami.getLoginInfo())) {
            var d = JSONUtil.toBean(cami.getLoginInfo(), GameLoginData.class);
            BeanUtil.copyProperties(d, resp);
        }

        resp.setRemainingDays(taskDO.getNeedTime() - taskDO.getRanTime());
        resp.setEndTime(resp.getAppointmentDateTime().plusDays(taskDO.getNeedTime() - taskDO.getRanTime()));
        return resp;
    }

    private GameLoginResp buildLoginRespOfCamiLogging(Long taskId) {
        //有登录信息但是还没有登录成功
        GameLoginResp resp = new GameLoginResp();
        resp.setTaskId(taskId);
        var gameLogging = gameLoginDetailInfoCache.getIfPresent(taskId);
        CheckUtils.throwIfNull(gameLogging, "卡密状态不对");
        BeanUtil.copyProperties(gameLogging, resp);
        return resp;
    }

    @Override
    public void gameLoginSubmit1(GameLoginReq req) {
        checkLoginReq(req);
        boolean validType = Objects.equals(req.getType(), GameLoginType.QR_CODE.getType()) || Objects.equals(req
            .getType(), GameLoginType.PHONE_SMS.getType());
        CheckUtils.throwIf(!validType, "登录类型错误");

        var camiDO = camiMapper.lambdaQuery()
            .select(CamiDO::getId, CamiDO::getCami, CamiDO::getState, CamiDO::getIsUrgent, CamiDO::getCreateTime, CamiDO::getCreateUser)
            .eq(CamiDO::getCami, req.getCami())
            .one();
        CheckUtils.throwIf(camiDO.getState().equals(CamiState.USED.getState()), "卡密已使用");

        this.checkGameLoginSubmit(req, camiDO);

        this.gameLoginSubmit1(req, camiDO);
    }

    @Override
    public void updateCamiState(String cami, long updateUser, int state, GameLoginData gameLoginData) {
        CamiDO camiDO = new CamiDO();
        camiDO.setUpdateTime(LocalDateTime.now());
        camiDO.setUpdateUser(updateUser);
        camiDO.setState(state);
        camiDO.setLoginInfo(JSONUtil.toJsonStr(gameLoginData));
        camiMapper.updateById(camiDO);
    }

    /**
     * 对应短信认证码和二维码登录.
     *
     * @param req 登录参数
     */
    private void gameLoginSubmit1(GameLoginReq req, CamiDO camiDO) {
        var newGameLogging = new GameLoginDetailInfo();
        BeanUtil.copyProperties(req, newGameLogging);
        newGameLogging.setState(GameLoginState.LOGGING_1_BEGIN.getState());
        newGameLogging.setUpdateTime(LocalDateTime.now());

        var taskCamiDO = taskCamiMapper.lambdaQuery()
            .select(TaskCamiDO::getId, TaskCamiDO::getTaskId, TaskCamiDO::getCamiId, TaskCamiDO::getIsSelfCami)
            .eq(TaskCamiDO::getCamiId, camiDO.getId())
            .one();
        gameLoginDetailInfoCache.put(taskCamiDO.getTaskId(), newGameLogging);
        pushGameLoginTaskToStandby(taskCamiDO.getTaskId(), camiDO.getIsUrgent());
    }

    @Override
    public void gameLoginSubmit2(GameLoginReq req) {
        checkLoginReq(req);

        var camiDO = camiMapper.lambdaQuery()
            .select(CamiDO::getId, CamiDO::getCami, CamiDO::getState, CamiDO::getIsUrgent, CamiDO::getCreateTime, CamiDO::getCreateUser)
            .eq(CamiDO::getCami, req.getCami())
            .one();
        this.checkGameLoginSubmit(req, camiDO);

        var newGameLogging = new GameLoginDetailInfo();
        BeanUtil.copyProperties(req, newGameLogging);
        newGameLogging.setState(GameLoginState.LOGGING_2.getState());
        newGameLogging.setUpdateTime(LocalDateTime.now());

        var taskCamiDO = taskCamiMapper.lambdaQuery()
            .select(TaskCamiDO::getId, TaskCamiDO::getTaskId, TaskCamiDO::getCamiId, TaskCamiDO::getIsSelfCami)
            .eq(TaskCamiDO::getCamiId, camiDO.getId())
            .one();
        gameLoginDetailInfoCache.put(taskCamiDO.getTaskId(), newGameLogging);
        pushGameLoginTaskToStandby(taskCamiDO.getTaskId(), camiDO.getIsUrgent());
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

        var taskCamiDO = taskCamiMapper.lambdaQuery()
            .select(TaskCamiDO::getId, TaskCamiDO::getTaskId, TaskCamiDO::getCamiId, TaskCamiDO::getIsSelfCami)
            .eq(TaskCamiDO::getCamiId, camiDO.getId())
            .one();
        CheckUtils.throwIfNull(taskCamiDO, "续单卡密不能登录");
        CheckUtils.throwIf(!taskCamiDO.getIsSelfCami(), "续单卡密不能登录");

        var gameLogging = gameLoginDetailInfoCache.getIfPresent(taskCamiDO.getTaskId());
        log.debug("游戏登录task: {}", gameLogging);
        if (gameLogging != null) {
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGIN_SUCCESS
                .getState()), "已经登录成功");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_1_BEGIN
                .getState()), "正在登录");
            CheckUtils.throwIf(Objects.equals(gameLogging.getState(), GameLoginState.LOGGING_2.getState()), "正在登录");
        }
    }

    @Override
    public void gameClientLoginCallback(GameClientLoginCallback info) {
        log.info("更新游戏登录状态:{}", info);

        //verify device.
        var device = deviceMapper.lambdaQuery().select().eq(DeviceDO::getDevice, info.getDevice()).one();
        CheckUtils.throwIfNull(device, "设备不存在");

        var gameLogging = gameLoginDetailInfoCache.getIfPresent(info.getTaskId());
        if (gameLogging == null) {
            log.error("游戏登录状态不存在. taskId: {}", info.getTaskId());
            return;
        }
        if (info.getChangeTimestamp() != gameLogging.getUpdateTime().toInstant(ZoneOffset.UTC).toEpochMilli()) {
            // gameLogging expire, 然后用户重新提交信息.
            // 时间对不上是可能的, 这时候,应该让云手机重置状态.
            log.error("登录信息timestamp 不相等. callback {}, gameLoginInfo {}", info, gameLogging);
            return;
        }
        var camiDO = camiMapper.lambdaQuery()
            .select(CamiDO::getId, CamiDO::getCami, CamiDO::getState, CamiDO::getIsUrgent, CamiDO::getCreateTime, CamiDO::getCreateUser)
            .eq(CamiDO::getCami, gameLogging.getCami())
            .one();
        if (camiDO == null) {
            log.error("卡密不存在. callback {}", info);
            return;
        }
        //GameLoginState.LOGGING_1_END, GameLoginState.LOGIN_FAIL, GameLoginState.LOGIN_SUCCESS
        if (Objects.equals(info.getState(), GameLoginState.LOGGING_1_END.getState())) {
            if (StringUtils.isNotBlank(info.getQrCode())) {
                gameLogging.setType(GameLoginType.QR_CODE.getType());
                gameLogging.setQrCode(info.getQrCode());
            } else {
                gameLogging.setQrCode("");
            }
            gameLogging.setSms("");
            gameLogging.setPassword("");
            gameLogging.setState(GameLoginState.LOGGING_1_END.getState());
            //update
            gameLoginDetailInfoCache.put(info.getTaskId(), gameLogging);
            return;
        } else if (Objects.equals(info.getState(), GameLoginState.LOGIN_FAIL.getState())) {
            GameLoginData gameLoginData = new GameLoginData();
            BeanUtil.copyProperties(gameLogging, gameLoginData);
            //update cami state
            this.updateCamiState(gameLogging.getCami(), device
                .getCreateUser(), SkyDict.GAME_CAMI_STATE_FAIL, gameLoginData);
            gameLogging.setState(GameLoginState.LOGIN_FAIL.getState());
            gameLoginDetailInfoCache.put(info.getTaskId(), gameLogging);
            return;
        } else if (!Objects.equals(info.getState(), GameLoginState.LOGIN_SUCCESS.getState())) {
            log.error("invalid callback:{}", info);
            return;
        }

        // GameLoginState.LOGIN_SUCCESS
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

    private boolean camiOnGameLoginTask(Long taskId) {
        if (RedisUtils.zScore(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, taskId) == null) {
            return false;
        }

        Collection<DeviceTaskPair> list = RedisUtils
            .zRangeByScore(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, 0, Long.MAX_VALUE);
        if (list.isEmpty()) {
            return false;
        }
        return list.stream().anyMatch(pair -> pair.taskId().equals(taskId));
    }

    private void pushGameLoginTaskToStandby(Long taskId, boolean isUrgent) {
        if (this.camiOnGameLoginTask(taskId)) {
            log.warn("task {} is running on login queue.", taskId);
        }
        if (isUrgent) {
            RedisUtils.zAdd(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, taskId, SkyDict.SCORE_URGENT);
        } else {
            RedisUtils.zAdd(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, taskId, SkyDict.SCORE_NORMAL);
        }
    }

}