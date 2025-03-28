package top.continew.admin.auto.sky.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;

/**
 * task-cami实体
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@TableName("game_task_cami")
public class TaskCamiDO extends BaseDO {

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
     * camiId
     */
    private Integer camiId;

    /**
     * 卡密类型
     */
    private Boolean isSelfCami;

    /**
     * 标记为删除
     */
    private Boolean isDel;
}