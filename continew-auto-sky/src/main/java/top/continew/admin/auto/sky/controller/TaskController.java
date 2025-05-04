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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.auto.sky.model.query.TaskQuery;
import top.continew.admin.auto.sky.model.req.GameClientLoginCallback;
import top.continew.admin.auto.sky.model.req.GameLoginReq;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.GameLoginResp;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;
import top.continew.admin.auto.sky.service.TaskService;
import top.continew.admin.common.controller.BaseController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;
import top.continew.starter.log.annotation.Log;

/**
 * 游戏任务管理 API
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Tag(name = "游戏任务管理 API")
@RestController
@CrudRequestMapping(value = "/sky/task", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class TaskController extends BaseController<TaskService, TaskResp, TaskDetailResp, TaskQuery, TaskReq> {
    @Log(ignore = true)
    @Operation(summary = "查询上号状态", description = "查询上号状态")
    @GetMapping("/login/state")
    public GameLoginResp gameLoginState(GameLoginReq req) {
        return baseService.gameLoginState(req);
    }

    @Log(ignore = true)
    @Operation(summary = "user上号1", description = "获取手机验证码/刷新二维码")
    @PostMapping("/login/submit1")
    public GameLoginResp gameLoginSubmit1(GameLoginReq req) {
        baseService.gameLoginSubmit1(req);
        return baseService.gameLoginState(req);
    }

    @Log(ignore = true)
    @Operation(summary = "user上号2", description = "user上号")
    @PostMapping("/login/submit2")
    public GameLoginResp gameLoginSubmit2(GameLoginReq req) {
        baseService.gameLoginSubmit2(req);
        return baseService.gameLoginState(req);
    }

    @Log(ignore = true)
    @Operation(summary = "手机更新上号状态", description = "手机更新上号状态")
    @PostMapping("/client/login/callback")
    public void clientLoginCallback(GameClientLoginCallback data) {
        baseService.gameClientLoginCallback(data);
    }
}