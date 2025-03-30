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