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
import java.io.Serializable;

/**
 * Game Login Resp.
 */
@Data
@Schema(description = "游戏登录情况信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameTaskResp implements Serializable {
    /**
     * 0 login, 1 game task;
     */
    @Schema(description = "要执行的游戏任务类型")
    private Integer type;

    // invalid taskId : 0, null;
    @Schema(description = "卡密任务id")
    private Long taskId;

    /**
     * for game task.
     */
    @Schema(description = "游戏任务id")
    private Integer gameTaskId;
    @Schema(description = "游戏任务name")
    private String gameTaskName;
    @Schema(description = "游戏任务步骤")
    private Integer gameTaskStep;
    @Schema(description = "游戏任务步骤状态")
    private Integer gameTaskStepState;

    @Schema(description = "游戏任务JsUrl")
    private String gameTaskJsUrl;
    @Schema(description = "游戏任务JsMd5")
    private String gameTaskJsMd5;

    @Schema(description = "登录类型")
    private Integer gameLoginType;
    @Schema(description = "登录channel")
    private Integer gameLoginChannel;
    @Schema(description = "登录账号")
    private String gameLoginAccount;
    @Schema(description = "登录邮箱")
    private String gameLoginEmail;
    @Schema(description = "登录密码")
    private String gameLoginPassword;
    @Schema(description = "登录子账号")
    private String gameLoginSubAccount;
    @Schema(description = "登录验证码")
    private String gameLoginSms;
    @Schema(description = "登录阶段")
    private Integer gameLoginStep;
    //用于判断登录信息是否修改.
    @Schema(description = "登录信息修改时间")
    private Long timestamp;
}
