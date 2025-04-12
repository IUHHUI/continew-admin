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

package top.continew.admin.auto.sky.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.auto.sky.mapper.TaskCamiMapper;
import top.continew.admin.auto.sky.model.entity.TaskCamiDO;
import top.continew.admin.auto.sky.model.query.TaskCamiQuery;
import top.continew.admin.auto.sky.model.req.TaskCamiReq;
import top.continew.admin.auto.sky.model.resp.TaskCamiDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskCamiResp;
import top.continew.admin.auto.sky.service.TaskCamiService;

/**
 * task-cami业务实现
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Service
@RequiredArgsConstructor
public class TaskCamiServiceImpl extends BaseServiceImpl<TaskCamiMapper, TaskCamiDO, TaskCamiResp, TaskCamiDetailResp, TaskCamiQuery, TaskCamiReq> implements TaskCamiService {}