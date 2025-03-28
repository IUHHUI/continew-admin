package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;

/**
 * 执行情况信息
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "执行情况信息")
public class TaskExecutionResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    private Integer taskId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    /**
     * 任务记录
     */
    @Schema(description = "任务记录")
    private String msg;
}