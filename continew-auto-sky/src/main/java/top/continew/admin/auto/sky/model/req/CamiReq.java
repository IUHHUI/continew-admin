package top.continew.admin.auto.sky.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改cami参数
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "创建或修改cami参数")
public class CamiReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 卡密描述
     */
    @Schema(description = "卡密描述")
    @Length(max = 255, message = "卡密描述长度不能超过 {max} 个字符")
    private String norm;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    @Length(max = 255, message = "订单编号长度不能超过 {max} 个字符")
    private String orderId;

    /**
     * 任务天数
     */
    @Schema(description = "任务天数")
    @NotNull(message = "任务天数不能为空")
    private Integer days;

    /**
     * 每日任务
     */
    @Schema(description = "每日任务")
    @NotBlank(message = "每日任务不能为空")
    @Length(max = 255, message = "每日任务长度不能超过 {max} 个字符")
    private String taskSpec;

    /**
     * 手动备注
     */
    @Schema(description = "手动备注")
    @Length(max = 255, message = "手动备注长度不能超过 {max} 个字符")
    private String notes;
}