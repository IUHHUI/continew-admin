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

package top.continew.admin.config.satoken;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaSignConfig;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class SaTokenConfigurationTest {

    @Test
    void testSign() {
        // 应用 AK/SK
        String accessKey = "OWFhZDFiYmJmNzVlNDMzMjhkYzFmZj";
        String secretKey = "OGY0NjExNGU3MzMxNDljNDhlMTZmNmIwMGM1MTc2ZDg=";

        // API 地址
        String apiUrl = "http://127.0.0.1:8000/system/role";
        // 基础请求参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("description", "管理员");

        // 构建鉴权参数
        // 随机字符串
        paramMap.put("nonce", RandomUtil.randomString(32));
        // 时间戳
        paramMap.put("timestamp", System.currentTimeMillis());
        // AK
        paramMap.put("accessKey", accessKey);
        // 签名
        // 将所有请求系统参数按照自然顺序进行排序
        // 拼接 key（SK）参数
        // 将所有参数连接成一个字符串，例如：a=18b=28c=3
        String buildSignParamStr = HttpUtil.toParams(paramMap, null, false);
        buildSignParamStr = buildSignParamStr + "&key=" + secretKey;
        // 采用 MD5（32位小写）加密方式加密整个参数字符串
        String sign = SecureUtil.md5(buildSignParamStr);
        paramMap.put("sign", sign);

        // 拼接完整的请求 URL
        var ps = HttpUtil.toParams(paramMap, null, false);
        var decodeParamMap = HttpUtil.decodeParamMap(ps, null);
        var saSignConfig = new SaSignConfig();
        saSignConfig.setSecretKey(secretKey);
        SaManager.setConfig(new SaTokenConfig().setSign(saSignConfig));
        SaManager.getSaSignTemplate().checkParamMap(decodeParamMap);
    }

}