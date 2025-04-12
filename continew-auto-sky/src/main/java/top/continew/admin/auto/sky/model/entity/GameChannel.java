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
public enum GameChannel {
    OFFICIAL_SERVER(1, "官服"), GAME_4399(2, "4399"), BILIBILI(3, "bilibili"), HUAWEI(4, "huawei"),
    ALI_GAME(5, "aligame"), OPPO(6, "oppo"), XIAOMI(7, "xiaomi"), VIVO(8, "vivo");

    private final int channel;
    private final String desc;

    GameChannel(int channel, String desc) {
        this.channel = channel;
        this.desc = desc;
    }

    public int getChannel() {
        return channel;
    }

    public String getDesc() {
        return desc;
    }
}
