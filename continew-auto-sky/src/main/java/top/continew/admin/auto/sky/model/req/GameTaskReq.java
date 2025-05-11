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

package top.continew.admin.auto.sky.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;

/**
 * 创建或修改游戏任务参数
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Data
@Schema(description = "创建或修改游戏任务参数")
public class GameTaskReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    @Length(max = 255, message = "订单编号长度不能超过 {max} 个字符")
    private String orderId;

    /**
     * 渠道
     */
    @Schema(description = "渠道")
    @Length(max = 50, message = "渠道长度不能超过 {max} 个字符")
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
    @Length(max = 255, message = "任务环境名称长度不能超过 {max} 个字符")
    private String taskEnvName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 255, message = "备注长度不能超过 {max} 个字符")
    private String notes;

    /**
     * 每日任务
     */
    @Schema(description = "每日任务")
    @Length(max = 255, message = "每日任务长度不能超过 {max} 个字符")
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
    @Length(max = 255, message = "Game账号长度不能超过 {max} 个字符")
    private String gameAccount;
}