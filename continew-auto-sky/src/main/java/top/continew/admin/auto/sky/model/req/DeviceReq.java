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
 * 创建或修改设备参数
 *
 * @author wjh
 * @since 2025/04/05 19:41
 */
@Data
@Schema(description = "创建或修改设备参数")
public class DeviceReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @Schema(description = "设备ID")
    @NotNull(message = "设备ID不能为空")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过 {max} 个字符")
    private String name;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    private Integer type;

    /**
     * 设备IP
     */
    @Schema(description = "设备IP")
    @NotBlank(message = "设备IP不能为空")
    @Length(max = 255, message = "设备IP长度不能超过 {max} 个字符")
    private String ip;

    /**
     * 在线情况
     */
    @Schema(description = "在线情况")
    private Integer state;
}