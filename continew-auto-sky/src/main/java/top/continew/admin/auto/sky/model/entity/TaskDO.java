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

package top.continew.admin.auto.sky.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;

/**
 * 游戏任务实体
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@TableName("game_task")
public class TaskDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 任务状态
     */
    private Integer taskState;

    /**
     * 需要运行次数
     */
    private Integer needTime;

    /**
     * 已经运行次数
     */
    private Integer ranTime;

    /**
     * 任务环境名称
     */
    private String taskEnvName;

    /**
     * 备注
     */
    private String notes;

    /**
     * 每日任务
     */
    private String taskDays;

    /**
     * 是否紧急
     */
    private Boolean isUrgent;

    /**
     * Game账号
     */
    private String gameAccount;

    /**
     * 标记删除
     */
    private Boolean isDel;
}