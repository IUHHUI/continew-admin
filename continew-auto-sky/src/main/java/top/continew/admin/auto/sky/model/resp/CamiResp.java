package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;

/**
 * cami信息
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "cami信息")
public class CamiResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @Schema(description = "UUID")
    private String cami;

    /**
     * 卡密描述
     */
    @Schema(description = "卡密描述")
    private String norm;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderId;

    /**
     * 任务天数
     */
    @Schema(description = "任务天数")
    private Integer days;

    /**
     * 每日任务
     */
    @Schema(description = "每日任务")
    private String taskSpec;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private Long updateUser;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Integer state;

    /**
     * 登录信息
     */
    @Schema(description = "登录信息")
    private String loginInfo;

    /**
     * 脚本反馈
     */
    @Schema(description = "脚本反馈")
    private String scriptFeedbackInfo;

    /**
     * 用户反馈
     */
    @Schema(description = "用户反馈")
    private String userFeedbackInfo;

    /**
     * 设备ID
     */
    @Schema(description = "设备ID")
    private String deviceId;

    /**
     * 手动备注
     */
    @Schema(description = "手动备注")
    private String notes;

    /**
     * 正面评价
     */
    @Schema(description = "正面评价")
    private Boolean isPositiveReview;
}