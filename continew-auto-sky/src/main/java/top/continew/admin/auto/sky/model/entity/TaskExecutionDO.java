package top.continew.admin.auto.sky.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;
import java.time.*;

/**
 * 执行情况实体
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@TableName("game_task_execution")
public class TaskExecutionDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 任务Id
     */
    private Integer taskId;

    /**
     * 开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 任务记录
     */
    private String msg;

    /**
     * 标记删除
     */
    private Boolean isDel;
}