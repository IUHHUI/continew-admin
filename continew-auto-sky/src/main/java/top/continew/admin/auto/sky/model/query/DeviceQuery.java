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
 * 设备查询条件
 *
 * @author wjh
 * @since 2025/04/05 19:41
 */
@Data
@Schema(description = "设备查询条件")
public class DeviceQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @Schema(description = "设备ID")
    @Query(type = QueryType.EQ)
    private Long id;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    @Query(type = QueryType.EQ)
    private Integer type;

    /**
     * 在线情况
     */
    @Schema(description = "在线情况")
    @Query(type = QueryType.EQ)
    private Integer state;
}