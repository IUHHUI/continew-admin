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

/**
 * Game Logging info
 */
@Data
public class GameLoggingInfo {
    /**
     * 文件类型
     */
    private String cami;
    private String randNum;
    /**
     * <pre>
     * { label: 'enable login', value: 1 },
     * { label: 'submit login', value: 2 },
     * { label: 'running login', value: 3 },
     * { label: 'had logined', value: 4 },
     * </pre>
     */
    private Integer channel;
    private Integer state;
    private Integer type;
    private String phone;
    private String password;
    private String sms;
    private String qrCode;
    private String email;
    private String device;
}