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

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Game Login Resp.
 */
@Data
@Schema(description = "游戏登录情况信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameLoginResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务id")
    private Long taskId;

    /**
     * {@link top.continew.admin.auto.sky.model.entity.GameLoginState}
     */
    @Schema(description = "登录状态")
    private Integer state;
    @Schema(description = "登录类型:1手机密码,2邮箱密码,3手机验证码,4二维码")
    private Integer type;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "二维码地址")
    private String qrCode;
    @Schema(description = "subAccount")
    private String subAccount;
    @Schema(description = "预约时间")
    private LocalDateTime appointmentDateTime;
    @Schema(description = "剩余天数")
    private Integer remainingDays;
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
    @Schema(description = "是否紧急")
    private Boolean isUrgent;
}