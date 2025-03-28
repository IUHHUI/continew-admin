package top.continew.admin.auto.sky.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 执行情况查询条件
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "执行情况查询条件")
public class TaskExecutionQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Query(type = QueryType.EQ)
    private Integer id;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    @Query(type = QueryType.EQ)
    private Integer taskId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @Query(type = QueryType.GE)
    private LocalDateTime beginTime;
}