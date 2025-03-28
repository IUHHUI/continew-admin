package top.continew.admin.auto.sky.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;

/**
 * cami实体
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@TableName("game_cami")
public class CamiDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * UUID
     */
    private String cami;

    /**
     * 卡密描述
     */
    private String norm;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 任务天数
     */
    private Integer days;

    /**
     * 每日任务
     */
    private String taskSpec;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 登录信息
     */
    private String loginInfo;

    /**
     * 脚本反馈
     */
    private String scriptFeedbackInfo;

    /**
     * 用户反馈
     */
    private String userFeedbackInfo;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 手动备注
     */
    private String notes;

    /**
     * 正面评价
     */
    private Boolean isPositiveReview;

    /**
     * 标记删除
     */
    private Boolean isDel;
}