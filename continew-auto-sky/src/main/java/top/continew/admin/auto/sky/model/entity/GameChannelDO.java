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
 * <pre>
 * { label: '官服', value: 1 },
 * { label: '4399', value: 2 },
 * { label: 'bilibili', value: 3 },
 * { label: 'huawei', value: 4 },
 * { label: 'aligame', value: 5 },
 * { label: 'oppo', value: 6 },
 * { label: 'xiaomi', value: 7 },
 * { label: 'vivo', value: 8 },
 * </pre>
 */
@Data
public class GameChannelDO {
    private String name;
    private Integer value;

    public GameChannelDO(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}