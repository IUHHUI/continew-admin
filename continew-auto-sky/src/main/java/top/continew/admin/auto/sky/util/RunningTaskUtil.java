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

package top.continew.admin.auto.sky.util;

import org.apache.commons.lang.StringUtils;
import top.continew.admin.auto.sky.model.entity.SkyDict;
import top.continew.starter.cache.redisson.util.RedisUtils;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collection;

/**
 * running task 10分钟有效. redis: taskId -> device.
 */
public class RunningTaskUtil {
    private RunningTaskUtil() {
    }

    public static double getNowScore() {
        return System.currentTimeMillis() / 1000d;
    }

    public static double getMaxScore() {
        return getNowScore() + 1;
    }

    public static synchronized void delete(String taskId) {
        RedisUtils.delete(taskId);
        RedisUtils.zRemove(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, taskId);
    }

    /**
     * 清理错误的任务.
     *
     * @param device device.
     */
    public static synchronized void deleteByDevice(String device) {
        Collection<String> list = RedisUtils.zRangeByScore(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, 0, getMaxScore());
        if (list.isEmpty()) {
            return;
        }

        for (String taskId : list) {
            String device1 = RedisUtils.get(taskId);
            if (StringUtils.isNotEmpty(device1)) {
                if (device1.equals(device)) {
                    RedisUtils.delete(taskId);
                }
            }
        }
    }

    public static synchronized void set(String taskId, String device) {
        RedisUtils.set(taskId, device, Duration.ofMinutes(10));
        RedisUtils.zAdd(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, taskId, getNowScore());
    }

    @Nullable
    public static synchronized String getDeviceByTaskId(String taskId) {
        String device = RedisUtils.get(taskId);
        if (StringUtils.isEmpty(device)) {
            //no task running, clean
            RedisUtils.zRemove(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, taskId);
        }
        return device;
    }

    @Nullable
    public static synchronized String getTaskIdByDevice(String device) {
        Collection<String> list = RedisUtils.zRangeByScore(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, 0, getMaxScore());
        if (list.isEmpty()) {
            return null;
        }

        for (String taskId : list) {
            String device1 = RedisUtils.get(taskId);
            if (StringUtils.isEmpty(device1)) {
                //expire running task
                RedisUtils.zRemove(SkyDict.KEY_GAME_LOGIN_RUNNING_QUEUE, taskId);
            } else {
                if (device1.equals(device)) {
                    return taskId;
                }
            }
        }
        return null;
    }
}
