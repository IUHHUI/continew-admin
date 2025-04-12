/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.auto.sky.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;

/**
 * 游戏任务详情信息
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "游戏任务详情信息")
public class TaskDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    @Schema(description = "版本号")
    @ExcelProperty(value = "版本号")
    private Integer version;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @ExcelProperty(value = "订单编号")
    private String orderId;

    /**
     * 渠道
     */
    @Schema(description = "渠道")
    @ExcelProperty(value = "渠道")
    private String channel;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态")
    @ExcelProperty(value = "任务状态")
    private Integer taskState;

    /**
     * 需要运行次数
     */
    @Schema(description = "需要运行次数")
    @ExcelProperty(value = "需要运行次数")
    private Integer needTime;

    /**
     * 已经运行次数
     */
    @Schema(description = "已经运行次数")
    @ExcelProperty(value = "已经运行次数")
    private Integer ranTime;

    /**
     * 任务环境名称
     */
    @Schema(description = "任务环境名称")
    @ExcelProperty(value = "任务环境名称")
    private String taskEnvName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty(value = "备注")
    private String notes;

    /**
     * 每日任务
     */
    @Schema(description = "每日任务")
    @ExcelProperty(value = "每日任务")
    private String taskDays;

    /**
     * 是否紧急
     */
    @Schema(description = "是否紧急")
    @ExcelProperty(value = "是否紧急")
    private Boolean isUrgent;

    /**
     * Game账号
     */
    @Schema(description = "Game账号")
    @ExcelProperty(value = "Game账号")
    private String gameAccount;

    /**
     * 标记删除
     */
    @Schema(description = "标记删除")
    @ExcelProperty(value = "标记删除")
    private Boolean isDel;
}