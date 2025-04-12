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
 * task-cami信息
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Data
@Schema(description = "task-cami信息")
public class TaskCamiResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    private Long taskId;

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
     * camiId
     */
    @Schema(description = "camiId")
    private Long camiId;

    /**
     * 卡密类型
     */
    @Schema(description = "卡密类型")
    private Boolean isSelfCami;
}