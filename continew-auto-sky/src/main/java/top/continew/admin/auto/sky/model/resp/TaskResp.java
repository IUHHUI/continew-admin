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

package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;

/**
 * 游戏任务信息
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "游戏任务信息")
public class TaskResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderId;

    /**
     * 渠道
     */
    @Schema(description = "渠道")
    private String channel;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态")
    private Integer taskState;

    /**
     * 需要运行次数
     */
    @Schema(description = "需要运行次数")
    private Integer needTime;

    /**
     * 已经运行次数
     */
    @Schema(description = "已经运行次数")
    private Integer ranTime;

    /**
     * 任务环境名称
     */
    @Schema(description = "任务环境名称")
    private String taskEnvName;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private Long updateUser;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

    /**
     * 每日任务
     */
    @Schema(description = "每日任务")
    private String taskDays;

    /**
     * 是否紧急
     */
    @Schema(description = "是否紧急")
    private Boolean isUrgent;

    /**
     * Game账号
     */
    @Schema(description = "Game账号")
    private String gameAccount;
}