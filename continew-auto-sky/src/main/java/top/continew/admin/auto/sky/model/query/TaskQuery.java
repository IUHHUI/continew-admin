package top.continew.admin.auto.sky.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 游戏任务查询条件
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "游戏任务查询条件")
public class TaskQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @Query(type = QueryType.LIKE)
    private String orderId;

    /**
     * 渠道
     */
    @Schema(description = "渠道")
    @Query(type = QueryType.EQ)
    private String channel;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态")
    @Query(type = QueryType.EQ)
    private Integer taskState;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @Query(type = QueryType.EQ)
    private Long createUser;
}