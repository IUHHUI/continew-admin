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

package top.continew.admin.auto.sky.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 执行情况查询条件
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "执行情况查询条件")
public class TaskExecutionQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Query(type = QueryType.EQ)
    private Integer id;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    @Query(type = QueryType.EQ)
    private Integer taskId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @Query(type = QueryType.GE)
    private LocalDateTime beginTime;
}