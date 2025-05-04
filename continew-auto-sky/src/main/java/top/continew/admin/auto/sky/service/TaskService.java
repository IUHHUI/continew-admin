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

package top.continew.admin.auto.sky.service;

import top.continew.admin.auto.sky.model.req.GameClientLoginCallback;
import top.continew.admin.auto.sky.model.req.GameLoginReq;
import top.continew.admin.auto.sky.model.resp.GameLoginResp;
import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.auto.sky.model.query.TaskQuery;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;

/**
 * 游戏任务业务接口
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
public interface TaskService extends BaseService<TaskResp, TaskDetailResp, TaskQuery, TaskReq> {

    /**
     * 游戏登录状态
     *
     * @param req 登录参数
     * @return 登录状态
     */
    GameLoginResp gameLoginState(GameLoginReq req);

    /**
     * 打开游戏, 选择二维码登录或者输入手机号
     *
     * @param req 登录参数
     */
    void gameLoginSubmit1(GameLoginReq req);

    /**
     * 游戏登录
     *
     * @param req 登录参数
     */
    void gameLoginSubmit2(GameLoginReq req);

    void gameClientLoginCallback(GameClientLoginCallback info);
}