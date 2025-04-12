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

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改task-cami参数
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "创建或修改task-cami参数")
public class TaskCamiReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    @NotNull(message = "任务Id不能为空")
    private Long taskId;

    /**
     * camiId
     */
    @Schema(description = "camiId")
    @NotNull(message = "camiId不能为空")
    private Long camiId;

    /**
     * 卡密类型
     */
    @Schema(description = "卡密类型")
    @NotNull(message = "卡密类型不能为空")
    private Boolean isSelfCami;
}