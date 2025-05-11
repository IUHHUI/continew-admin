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
    private int type;

    @Schema(description = "卡密任务id")
    private long taskId;

    /**
     * for game task.
     */
    @Schema(description = "游戏任务id")
    private int gameTaskId;
    @Schema(description = "游戏任务name")
    private String gameTaskName;
    @Schema(description = "游戏任务步骤")
    private int gameTaskStep;
    @Schema(description = "游戏任务步骤状态")
    private int gameTaskStepState;

    @Schema(description = "游戏任务JsUrl")
    private String gameTaskJsUrl;
    @Schema(description = "游戏任务JsMd5")
    private String gameTaskJsMd5;

    @Schema(description = "登录类型")
    private int gameLoginType;
    @Schema(description = "登录channel")
    private int gameLoginChannel;
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
    private int gameLoginStep;
    //用于判断登录信息是否修改.
    @Schema(description = "登录信息修改时间")
    private long timestamp;
}
