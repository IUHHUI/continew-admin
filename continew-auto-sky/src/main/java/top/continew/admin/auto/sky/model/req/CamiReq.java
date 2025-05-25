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

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改cami参数
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Data
@Schema(description = "创建或修改cami参数")
public class CamiReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 卡密描述
     */
    @Schema(description = "卡密描述")
    @Length(max = 255, message = "卡密描述长度不能超过 {max} 个字符")
    private String norm;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    @Length(max = 255, message = "订单编号长度不能超过 {max} 个字符")
    private String orderId;

    /**
     * 任务天数
     */
    @Schema(description = "任务天数")
    @NotNull(message = "任务天数不能为空")
    private Integer days;

    /**
     * 每日任务
     */
    @Schema(description = "每日任务")
    @NotBlank(message = "每日任务不能为空")
    @Length(max = 255, message = "每日任务长度不能超过 {max} 个字符")
    private String taskSpec;

    /**
     * 手动备注
     */
    @Schema(description = "手动备注")
    @Length(max = 255, message = "手动备注长度不能超过 {max} 个字符")
    private String notes;

    @Schema(description = "创建任务")
    private Boolean createTask;
}