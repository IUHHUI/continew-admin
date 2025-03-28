package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;

/**
 * 执行情况详情信息
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "执行情况详情信息")
public class TaskExecutionDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    @Schema(description = "版本号")
    @ExcelProperty(value = "版本号")
    private Integer version;

    /**
     * 任务Id
     */
    @Schema(description = "任务Id")
    @ExcelProperty(value = "任务Id")
    private Integer taskId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @ExcelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @ExcelProperty(value = "结束时间")
    private LocalDateTime endTime;

    /**
     * 任务记录
     */
    @Schema(description = "任务记录")
    @ExcelProperty(value = "任务记录")
    private String msg;

    /**
     * 标记删除
     */
    @Schema(description = "标记删除")
    @ExcelProperty(value = "标记删除")
    private Boolean isDel;
}