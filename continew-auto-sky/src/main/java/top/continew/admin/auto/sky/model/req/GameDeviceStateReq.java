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
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "上报设备状态")
public class GameDeviceStateReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @Schema(description = "设备编号")
    @NotBlank(message = "编号不能为空")
    @Length(max = 255, message = "编号长度不能超过 {max} 个字符")
    private String device;

    @Schema(description = "设备状态")
    private int state;

    private int type;

    @Schema(description = "设备当前任务")
    private long taskId;
    @Schema(description = "设备当前game业务任务Id")
    private int gameTaskId;
    @Schema(description = "设备当前game业务任务name")
    private String gameTaskName;
    @Schema(description = "设备当前game业务任务步骤")
    private int gameTaskStep;
    @Schema(description = "设备当前game业务任务state")
    private int gameTaskStepState;

    @Schema(description = "设备当前业务任务步骤")
    private int taskStep;

    @Schema(description = "设备当前业务任务信息")
    private String taskInfo;

    //pw, sms, qrcode.
    @Schema(description = "设备当前登录type.")
    private int gameLoginType;
    @Schema(description = "设备当前登录任务账号信息")
    private String gameLoginAccount;
    @Schema(description = "设备当前登录任务步骤")
    private int gameLoginStep;
    @Schema(description = "设备当前登录二维码")
    private String gameLoginQrcode;

    @Schema(description = "设备当前登录渠道")
    private int gameLoginChannel;

    @Schema(description = "设备当前任务信息timestamp")
    private long timestamp;
}