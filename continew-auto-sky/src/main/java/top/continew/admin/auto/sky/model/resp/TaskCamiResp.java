package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;

/**
 * task-cami信息
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "task-cami信息")
public class TaskCamiResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    private Integer taskId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime updateTime;

    /**
     * camiId
     */
    @Schema(description = "camiId")
    private Integer camiId;

    /**
     * 卡密类型
     */
    @Schema(description = "卡密类型")
    private Boolean isSelfCami;
}