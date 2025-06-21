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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "游戏登录参数")
public class GameClientLoginCallback implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件类型
     */
    @Schema(description = "卡密")
    @NotNull(message = "卡密不能为空")
    private Long taskId;

    @Schema(description = "设备device id")
    @NotNull(message = "设备device id不能为空")
    private String device;

    @Schema(description = "游戏渠道")
    @NotNull(message = "游戏渠道不能为空")
    private Integer channel;

    @Schema(description = "登录类型:1手机密码,2邮箱密码,3手机验证码,4二维码")
    @NotNull(message = "登录类型不能为空")
    private Integer type;

    @Schema(description = "手机号")
    @NotNull(message = "手机号不能为空")
    private String phone;

    /**
     * {@link top.continew.admin.auto.sky.model.entity.GameLoginState}
     */
    @Schema(description = "登录状态")
    @NotNull(message = "登录状态不能为空")
    private int state;

    @Schema(description = "二维码地址")
    private String qrCode;

    @Schema(description = "当前任务信息timestamp")
    private long timestamp;
}