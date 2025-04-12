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

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.auto.sky.model.query.CamiQuery;
import top.continew.admin.auto.sky.model.req.CamiReq;
import top.continew.admin.auto.sky.model.resp.CamiDetailResp;
import top.continew.admin.auto.sky.model.resp.CamiResp;
import top.continew.admin.auto.sky.service.CamiService;

/**
 * cami管理 API
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Tag(name = "cami管理 API")
@RestController
@CrudRequestMapping(value = "/sky/cami", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class CamiController extends BaseController<CamiService, CamiResp, CamiDetailResp, CamiQuery, CamiReq> {}