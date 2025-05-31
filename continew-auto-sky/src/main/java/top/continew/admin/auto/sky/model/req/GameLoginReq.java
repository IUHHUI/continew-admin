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

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * Game Login Req.
 * {
 * "cami": "8456ab00-184b-11f0-82d9-6e6b89292b82",
 * "randNum": "1744537307000",
 * "channel": "1",
 * "type": 1,
 * "phone": "1231231",
 * "password": "111",
 * "subAccountPrefix": "aaa",
 * "sms": ""
 * }
 */
@Data
@Schema(description = "游戏登录参数")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameLoginReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件类型
     */
    @Schema(description = "卡密")
    @NotNull(message = "卡密不能为空")
    private String cami;
    @Schema(description = "时间戳")
    @NotNull(message = "时间戳不能为空")
    private String randNum;
    /**
     * <pre>
     * { label: 'enable login', value: 1 },
     * { label: 'submit login', value: 2 },
     * { label: 'running login', value: 3 },
     * { label: 'had logined', value: 4 },
     * </pre>
     */
    @Schema(description = "游戏渠道")
    private Integer channel;
    @Schema(description = "登录类型:1手机密码,2邮箱密码,3手机验证码,4二维码")
    private Integer type;
    @Schema(description = "手机号")
    @NotNull(message = "手机号不能为空")
    private String phone;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "验证码")
    private String sms;
    @Schema(description = "二维码地址")
    private String qrCode;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "子账号前缀")
    private String subAccountPrefix;
    @Schema(description = "预约时间")
    private Long appointmentTime;
}