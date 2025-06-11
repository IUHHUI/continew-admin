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

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.continew.admin.auto.sky.model.entity.DeviceDO;
import top.continew.admin.auto.sky.model.entity.DeviceTaskPair;
import top.continew.admin.auto.sky.model.entity.GameLoginState;
import top.continew.admin.auto.sky.model.entity.SkyDict;
import top.continew.admin.auto.sky.model.req.GameClientLoginCallback;
import top.continew.admin.auto.sky.model.req.GameDeviceStateReq;
import top.continew.admin.auto.sky.model.resp.GameTaskResp;
import top.continew.admin.auto.sky.service.DeviceService;
import top.continew.admin.auto.sky.service.GameTaskService;
import top.continew.admin.auto.sky.service.TaskService;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.validation.CheckUtils;
import java.time.ZoneOffset;
import java.util.Collection;

@Slf4j
@Service
public class GameTaskServiceImpl implements GameTaskService {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private TaskService taskService;

    @Override
    public GameTaskResp reportAndReceiveGameTask(GameDeviceStateReq req) {
        DeviceDO deviceDO = deviceService.insertOrUpdateDevice(req);
        log.info("设备上报: {}", deviceDO);
        CheckUtils.throwIfNull(deviceDO, "设备更新失败");

        if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_WORKING) {
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

    private double getNewMaxScore() {
        return getNowScore();
    }

    private double getNowScore() {
        return System.currentTimeMillis() / 1000d;
    }

    private DeviceTaskPair getCamiDevicePairByDevice(final String device) {
        Collection<DeviceTaskPair> list = RedisUtils
            .zRangeByScore(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, 0, getNewMaxScore());
        if (list.isEmpty()) {
            return null;
        }
        return list.stream().filter(pair -> pair.device().equals(device)).findFirst().orElse(null);
    }

    /**
     * 加锁, 避免多个设备被分配了相同cami的登录任务.
     *
     * @param deviceDO deviceDO
     * @return GameTaskResp
     */
    private synchronized GameTaskResp dispatchNewGameLoginTask(DeviceDO deviceDO) {
        //dispatch new work.
        Collection<Long> taskIds = RedisUtils
            .zRangeByScore(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, 0, getNewMaxScore(), 0, 1);
        if (taskIds.isEmpty()) {
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }
        log.info("分配任务: {}", taskIds);
        //有任务
        var taskId = taskIds.iterator().next();
        var pair = new DeviceTaskPair(deviceDO.getDevice(), taskId);
        //添加到运行队列
        if (RedisUtils.zAdd(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, pair, getNowScore())) {
            RedisUtils.zRemove(SkyDict.KEY_GAME_LOGIN_STANDBY_QUEUE, taskId);
            var gameLoginDetailInfo = taskService.getGameLoginDetailInfo(taskId);
            gameLoginDetailInfo.setDevice(deviceDO.getDevice());
        }
        return buildGameLoginTaskByPair(pair);
    }

    private GameTaskResp buildGameLoginTaskByPair(DeviceTaskPair pair) {
        GameTaskResp gtr = new GameTaskResp();
        gtr.setTaskId(pair.taskId());
        gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
        var gameLoginDetailInfo = taskService.getGameLoginDetailInfo(pair.taskId());
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

    private void gameClientLoginCallback(GameDeviceStateReq req, DeviceTaskPair deviceTaskPair) {
        GameClientLoginCallback c = new GameClientLoginCallback();
        if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_WORKING) {
            if (req.getGameLoginStep() == SkyDict.GAME_LOGIN_STEP_1) {
                c.setState(GameLoginState.LOGGING_1_BEGIN.getState());
            } else {
                c.setState(GameLoginState.LOGGING_2.getState());
            }
        } else if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_FINISH) {
            if (req.getGameLoginStep() == SkyDict.GAME_LOGIN_STEP_1) {
                c.setState(GameLoginState.LOGGING_1_END.getState());
            } else {
                c.setState(GameLoginState.LOGIN_SUCCESS.getState());
            }
        } else if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_FAIL) {
            c.setState(GameLoginState.LOGIN_FAIL.getState());
        } else {
            return;
        }

        c.setDevice(deviceTaskPair.device());
        c.setTaskId(deviceTaskPair.taskId());
        c.setPhone(req.getGameLoginAccount());
        c.setChannel(req.getGameLoginChannel());
        c.setTimestamp(req.getTimestamp());
        taskService.gameClientLoginCallback(c);

        if (c.getState() == GameLoginState.LOGIN_SUCCESS.getState()) {
            //成功的任务移除.
            RedisUtils.zRemove(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, deviceTaskPair);
        }
    }

    private GameTaskResp dispatchGameLoginTask(DeviceDO deviceDO, GameDeviceStateReq req) {
        if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_IDLE) {
            return dispatchNewGameLoginTask(deviceDO);
        } else if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_WORKING) {
            //wait next report. 返回空任务信息.
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        } else if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_FAIL) {
            var camiDevicePair = getCamiDevicePairByDevice(deviceDO.getDevice());
            if (camiDevicePair == null) {
                log.warn("camiDevicePair is null. 下发新任务给设备. req {}", req);
                // dispatch new task.
                return dispatchNewGameLoginTask(deviceDO);
            }
            gameClientLoginCallback(req, camiDevicePair);
            var gameLoginDetailInfo = taskService.getGameLoginDetailInfo(camiDevicePair.taskId());
            if (gameLoginDetailInfo.getUpdateTime().toInstant(ZoneOffset.UTC).toEpochMilli() == req.getTimestamp()) {
                //login消息没有更新, 返回空任务信息.
                GameTaskResp gtr = new GameTaskResp();
                gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
                return gtr;
            }
            //登录消息更新了, 构建新的任务信息.
            return buildGameLoginTaskByPair(camiDevicePair);
        } else if (req.getState() == SkyDict.GAME_DEVICE_EXEC_STATE_FINISH) {
            var camiDevicePair = getCamiDevicePairByDevice(deviceDO.getDevice());
            if (camiDevicePair == null) {
                log.error("内部错误. camiDevicePair is null. req {}", req);
                // dispatch new task.
                return dispatchNewGameLoginTask(deviceDO);
            }

            gameClientLoginCallback(req, camiDevicePair);

            if (req.getGameLoginStep() == SkyDict.GAME_LOGIN_STEP_2) {
                //login success
                return dispatchNewGameLoginTask(deviceDO);
            } else {
                return buildGameLoginTaskByPair(camiDevicePair);
            }
        } else {
            log.error("未知状态:{}, req {}", req.getState(), req);
            //wait next report. 返回空任务信息.
            GameTaskResp gtr = new GameTaskResp();
            gtr.setType(SkyDict.GAME_DEVICE_TYPE_LOGIN);
            return gtr;
        }
    }

    private GameTaskResp dispatchGameBusinessTask(DeviceDO deviceDO, GameDeviceStateReq req) {
        //TODO
        return null;
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
