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

package top.continew.admin.auto.sky.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.auto.sky.model.req.GameClientLoginCallback;
import top.continew.admin.auto.sky.model.req.GameDeviceStateReq;
import top.continew.admin.auto.sky.model.req.GameLoginReq;
import top.continew.admin.auto.sky.model.resp.GameLoginResp;
import top.continew.admin.auto.sky.model.resp.GameTaskResp;
import top.continew.admin.auto.sky.service.GameTaskService;
import top.continew.starter.log.annotation.Log;

/**
 * 游戏任务执行 API
 *
 * @author wjh
 * @since 2025/05/12 20:21
 */
@Tag(name = "游戏任务执行 API")
@Log(module = "登录")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sky/worker")
public class GameTaskController {
    @Autowired
    private final GameTaskService gameTaskService;

    @Log(ignore = true)
    @Operation(summary = "上报状态", description = "客户端上报状态,领取任务.")
    @GetMapping("/state")
    public GameTaskResp reportAndReceiveGameTask(GameDeviceStateReq req) {
        return gameTaskService.reportAndReceiveGameTask(req);
    }
}