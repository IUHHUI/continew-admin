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

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.continew.admin.auto.sky.model.entity.DeviceDO;
import top.continew.admin.auto.sky.model.entity.GameLoginState;
import top.continew.admin.auto.sky.model.entity.SkyDict;
import top.continew.admin.auto.sky.model.req.GameClientLoginCallback;
import top.continew.admin.auto.sky.model.req.GameDeviceStateReq;
import top.continew.admin.auto.sky.model.resp.GameTaskResp;
import top.continew.admin.auto.sky.service.DeviceService;
import top.continew.admin.auto.sky.service.GameTaskService;
import top.continew.admin.auto.sky.service.TaskService;
import top.continew.admin.auto.sky.util.RunningTaskUtil;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.validation.CheckUtils;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Service
public class GameTaskServiceImpl implements GameTaskService {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private TaskService taskService;

    private boolean isStateWorking(final int state) {
        return state == GameLoginState.LOGGING_1_BEGIN.getState() || state == GameLoginState.LOGGING_2.getState();
    }

    private boolean validState(final int state) {
        return Arrays.stream(GameLoginState.values()).anyMatch(e -> e.getState() == state);
    }

    @Override
    public GameTaskResp reportAndReceiveGameTask(GameDeviceStateReq req) {
        DeviceDO deviceDO = deviceService.insertOrUpdateDevice(req);
        log.debug("设备: {} state {}", JSONUtil.toJsonStr(deviceDO), req);
        CheckUtils.throwIfNull(deviceDO, "设备更新失败");
        CheckUtils.throwIf(!validState(req.getState()), "上报状态错误.");

        String taskIdStr = RunningTaskUtil.getTaskIdByDevice(deviceDO.getDevice());
        if (StringUtils.isNotEmpty(taskIdStr)) {
            Long taskId = Long.parseLong(taskIdStr);
            var info = taskService.getGameLoginDetailInfo(taskId);
            if (info == null) {
                log.debug("设备: {} exec taskId {} had expire.", req.getDevice(), taskId);
                RunningTaskUtil.delete(taskIdStr);
            }
        }

        if (isStateWorking(req.getState())) {
            //waiting next report.
            GameTaskResp r = new GameTaskResp();
            r.setType(deviceDO.getType());
            return r;
        }

        if (deviceDO.getType() == SkyDict.GAME_DEVICE_TYPE_LOGIN) {
            return dispatchGameLoginTask(deviceDO, req);
        } else {
            return dispatchGameBusinessTask(deviceDO, req);
        }
    }

    /**
     * 加锁, 避免多个设备被分配了相同cami的登录任务.
     *
     * @param deviceDO deviceDO
     * @return GameTaskResp
     */
    private synchronized GameTaskResp dispatchNewGameLoginTask(DeviceDO deviceDO) {
        //dispatch new work.
        Collection<Long> taskIds = RedisUtils.zRangeByScore(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, 0, RunningTaskUtil
            .getMaxScore(), 0, 1);
        if (taskIds.isEmpty()) {
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }
        log.info("分配任务: {}", taskIds);
        //有任务
        var taskId = taskIds.iterator().next();
        var gameLoginDetailInfo = taskService.getGameLoginDetailInfo(taskId);
        if (gameLoginDetailInfo == null) {
            //expire task.
            RedisUtils.zRemove(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, taskId);
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }
        //添加到运行队列
        RunningTaskUtil.set(taskId.toString(), deviceDO.getDevice());
        log.info("GAME_LOGIN_STANDBY_QUEUE remove 任务: {}", taskId);
        RedisUtils.zRemove(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, taskId);
        gameLoginDetailInfo.setDevice(deviceDO.getDevice());
        return buildGameLoginTaskByPair(taskId);
    }

    private GameTaskResp buildGameLoginTaskByPair(long taskId) {
        GameTaskResp gtr = new GameTaskResp();
        gtr.setTaskId(taskId);
        gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
        var gameLoginDetailInfo = taskService.getGameLoginDetailInfo(taskId);
        gtr.setGameLoginAccount(gameLoginDetailInfo.getPhone());
        gtr.setGameLoginPassword(gameLoginDetailInfo.getPassword());
        gtr.setGameLoginEmail(gameLoginDetailInfo.getEmail());
        gtr.setGameLoginSms(gameLoginDetailInfo.getSms());
        gtr.setGameLoginSubAccount(gameLoginDetailInfo.getSubAccount());

        gtr.setGameLoginChannel(gameLoginDetailInfo.getChannel());

        gtr.setGameLoginType(gameLoginDetailInfo.getType());
        gtr.setTimestamp(gameLoginDetailInfo.getUpdateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
        if (gameLoginDetailInfo.getType() == SkyDict.GAME_LOGIN_TYPE_PHONE_PASSWORD || gameLoginDetailInfo
            .getType() == SkyDict.GAME_LOGIN_TYPE_EMAIL_PASSWORD) {
            //密码登录, 直接第二步骤.
            gtr.setGameLoginStep(SkyDict.GAME_LOGIN_STEP_2);
        } else {
            if (gameLoginDetailInfo.getType() == SkyDict.GAME_LOGIN_TYPE_PHONE_SMS) {
                if (gameLoginDetailInfo.getState() == GameLoginState.LOGGING_1_END.getState() || gameLoginDetailInfo
                    .getState() == GameLoginState.LOGGING_2.getState()) {
                    gtr.setGameLoginStep(SkyDict.GAME_LOGIN_STEP_2);
                } else {
                    gtr.setGameLoginStep(SkyDict.GAME_LOGIN_STEP_1);
                }
            } else {
                gtr.setGameLoginStep(SkyDict.GAME_LOGIN_STEP_1);
            }
        }

        return gtr;
    }

    private void gameClientLoginCallback(GameDeviceStateReq req, long taskId, String device) {
        GameClientLoginCallback c = new GameClientLoginCallback();
        c.setTaskId(taskId);
        c.setDevice(device);
        c.setChannel(req.getGameLoginChannel());
        c.setType(req.getGameLoginType());
        c.setPhone(req.getGameLoginAccount());
        c.setState(req.getState());
        c.setQrCode(req.getGameLoginQrcode());
        c.setTimestamp(req.getTimestamp());
        taskService.gameClientLoginCallback(c);

        if (c.getState() == GameLoginState.LOGIN_SUCCESS.getState()) {
            //成功的任务移除.
            RunningTaskUtil.delete(String.valueOf(taskId));
        }
    }

    private GameTaskResp dispatchGameLoginTask(DeviceDO deviceDO, GameDeviceStateReq req) {
        if (req.getState() == GameLoginState.INIT.getState()) {
            String taskIdStr = RunningTaskUtil.getTaskIdByDevice(deviceDO.getDevice());
            if (StringUtils.isEmpty(taskIdStr)) {
                return dispatchNewGameLoginTask(deviceDO);
            } else {
                //存在任务on running queue.
                return buildGameLoginTaskByPair(Long.parseLong(taskIdStr));
            }
        }

        if (req.getTaskId() == null || req.getTaskId() == 0) {
            log.error("taskId is null or 0. req {}", req);
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }

        if (isStateWorking(req.getState())) {
            //wait next report. 返回空任务信息.
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }

        String runningDevice = RunningTaskUtil.getDeviceByTaskId(String.valueOf(req.getTaskId()));
        if (StringUtils.isEmpty(runningDevice)) {
            log.warn("task is not running. 下发新任务给设备. req {}", req);
            // dispatch new task.
            return dispatchNewGameLoginTask(deviceDO);
        }

        if (!runningDevice.equals(req.getDevice())) {
            log.debug("登录任务: {} running on {},  不是当前设备{}", req.getTaskId(), runningDevice, req.getDevice());
            //清理内存中当前设备的任务信息.
            RunningTaskUtil.deleteByDevice(req.getDevice());
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }

        if (req.getState() == GameLoginState.LOGIN_FAIL.getState()) {
            //可能由于网络多次上报.
            gameClientLoginCallback(req, req.getTaskId(), req.getDevice());
            var gameLoginDetailInfo = taskService.getGameLoginDetailInfo(req.getTaskId());
            if (gameLoginDetailInfo.getUpdateTime().toInstant(ZoneOffset.UTC).toEpochMilli() == req.getTimestamp()) {
                //login消息没有更新, 返回空任务信息.
                GameTaskResp gtr = new GameTaskResp();
                gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
                return gtr;
            }
            //登录消息更新了, 构建新的任务信息.
            return buildGameLoginTaskByPair(req.getTaskId());
        } else if (req.getState() == GameLoginState.LOGGING_1_END.getState()) {
            gameClientLoginCallback(req, req.getTaskId(), req.getDevice());
            return buildGameLoginTaskByPair(req.getTaskId());
        } else if (req.getState() == GameLoginState.LOGIN_SUCCESS.getState()) {
            gameClientLoginCallback(req, req.getTaskId(), req.getDevice());
            return dispatchNewGameLoginTask(deviceDO);
        } else {
            log.error("未知状态:{}, req {}", req.getState(), req);
            //wait next report. 返回空任务信息.
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }
    }

    private GameTaskResp dispatchGameBusinessTask(DeviceDO deviceDO, GameDeviceStateReq req) {
        //TODO 临时返回空任务.
        GameTaskResp r = new GameTaskResp();
        r.setType(deviceDO.getType());
        return r;
    }

    @Override
    public void pushGameWorkTask(long taskId, long deviceId, boolean isUrgent) {
        //TODO
    }

    @Override
    public void scheduleGameTaskEveryDay() {
        //TODO
    }
}
