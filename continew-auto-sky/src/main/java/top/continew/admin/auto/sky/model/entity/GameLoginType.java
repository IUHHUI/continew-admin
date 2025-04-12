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
 * 登录类型:1手机密码,2邮箱密码,3手机验证码,4二维码.
 */
public enum GameLoginType {
    PHONE_PASSWORD(1, "手机密码"), EMAIL_PASSWORD(2, "邮箱密码"), PHONE_SMS(3, "手机验证码"), QR_CODE(4, "二维码");

    private final int type;
    private final String desc;

    GameLoginType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
