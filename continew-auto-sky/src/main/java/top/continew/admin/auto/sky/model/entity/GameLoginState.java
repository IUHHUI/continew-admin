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
 * 1. 还没有提交过登录信息 state=1
 * 2.0 正在登录第一阶段,打开游戏发送认证吗或者获取二维码. state=20, 等待和游戏交互.不能再提交登录信息
 * 2.1 正在登录第一阶段end. state=21,能再提交登录信息
 * 2.2 正在登录, 等待登录结果 state=22, 等待和游戏交互.不能再提交登录信息
 * 2.3 登录失败, 可以再提交登录信息 state=23
 * 3. 登录成功的, 不能再登录 state=3
 * </pre>
 */
public enum GameLoginState {
    INIT(1, "还没有提交过登录信息"), LOGGING_1_BEGIN(20, "正在登录第一阶段,打开游戏发送认证吗或者获取二维码."), LOGGING_1_END(21, "正在登录第一阶段END."),
    LOGGING_2(22, "正在登录, 等待登录结果"), LOGIN_FAIL(23, "登录失败, 可以再提交登录信息"), LOGIN_SUCCESS(3, "登录成功的, 不能再登录");

    private final int state;
    private final String desc;

    GameLoginState(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
