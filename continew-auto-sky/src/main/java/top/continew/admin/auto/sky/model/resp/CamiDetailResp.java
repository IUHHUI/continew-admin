package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;

/**
 * cami详情信息
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "cami详情信息")
public class CamiDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    @Schema(description = "版本号")
    @ExcelProperty(value = "版本号")
    private Integer version;

    /**
     * UUID
     */
    @Schema(description = "UUID")
    @ExcelProperty(value = "UUID")
    private String cami;

    /**
     * 卡密描述
     */
    @Schema(description = "卡密描述")
    @ExcelProperty(value = "卡密描述")
    private String norm;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @ExcelProperty(value = "订单编号")
    private String orderId;

    /**
     * 任务天数
     */
    @Schema(description = "任务天数")
    @ExcelProperty(value = "任务天数")
    private Integer days;

    /**
     * 每日任务
     */
    @Schema(description = "每日任务")
    @ExcelProperty(value = "每日任务")
    private String taskSpec;

    /**
     * 状态
     */
    @Schema(description = "状态")
    @ExcelProperty(value = "状态")
    private Integer state;

    /**
     * 登录信息
     */
    @Schema(description = "登录信息")
    @ExcelProperty(value = "登录信息")
    private String loginInfo;

    /**
     * 脚本反馈
     */
    @Schema(description = "脚本反馈")
    @ExcelProperty(value = "脚本反馈")
    private String scriptFeedbackInfo;

    /**
     * 用户反馈
     */
    @Schema(description = "用户反馈")
    @ExcelProperty(value = "用户反馈")
    private String userFeedbackInfo;

    /**
     * 设备ID
     */
    @Schema(description = "设备ID")
    @ExcelProperty(value = "设备ID")
    private String deviceId;

    /**
     * 手动备注
     */
    @Schema(description = "手动备注")
    @ExcelProperty(value = "手动备注")
    private String notes;

    /**
     * 正面评价
     */
    @Schema(description = "正面评价")
    @ExcelProperty(value = "正面评价")
    private Boolean isPositiveReview;

    /**
     * 标记删除
     */
    @Schema(description = "标记删除")
    @ExcelProperty(value = "标记删除")
    private Boolean isDel;
}