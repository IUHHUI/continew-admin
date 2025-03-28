package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;

/**
 * task-cami详情信息
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "task-cami详情信息")
public class TaskCamiDetailResp extends BaseDetailResp {

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
     * camiId
     */
    @Schema(description = "camiId")
    @ExcelProperty(value = "camiId")
    private Integer camiId;

    /**
     * 卡密类型
     */
    @Schema(description = "卡密类型")
    @ExcelProperty(value = "卡密类型")
    private Boolean isSelfCami;

    /**
     * 标记为删除
     */
    @Schema(description = "标记为删除")
    @ExcelProperty(value = "标记为删除")
    private Boolean isDel;
}