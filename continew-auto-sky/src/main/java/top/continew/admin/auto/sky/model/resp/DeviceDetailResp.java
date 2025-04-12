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

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;

/**
 * 设备详情信息
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "设备详情信息")
public class DeviceDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备device
     */
    @Schema(description = "设备device")
    @ExcelProperty(value = "设备device")
    private String device;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @ExcelProperty(value = "名称")
    private String name;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    @ExcelProperty(value = "设备类型")
    private Integer type;

    /**
     * 设备IP
     */
    @Schema(description = "设备IP")
    @ExcelProperty(value = "设备IP")
    private String ip;

    /**
     * 在线情况
     */
    @Schema(description = "在线情况")
    @ExcelProperty(value = "在线情况")
    private Integer state;
}