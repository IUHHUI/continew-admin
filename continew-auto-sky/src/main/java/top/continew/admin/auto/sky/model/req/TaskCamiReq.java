package top.continew.admin.auto.sky.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改task-cami参数
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "创建或修改task-cami参数")
public class TaskCamiReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    @NotNull(message = "任务Id不能为空")
    private Integer taskId;

    /**
     * camiId
     */
    @Schema(description = "camiId")
    @NotNull(message = "camiId不能为空")
    private Integer camiId;

    /**
     * 卡密类型
     */
    @Schema(description = "卡密类型")
    @NotNull(message = "卡密类型不能为空")
    private Boolean isSelfCami;
}