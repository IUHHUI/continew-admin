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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.continew.admin.auto.sky.mapper.CamiMapper;
import top.continew.admin.auto.sky.model.entity.CamiDO;
import top.continew.admin.auto.sky.model.query.CamiQuery;
import top.continew.admin.auto.sky.model.query.TaskCamiQuery;
import top.continew.admin.auto.sky.model.req.CamiReq;
import top.continew.admin.auto.sky.model.req.TaskCamiReq;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.CamiDetailResp;
import top.continew.admin.auto.sky.model.resp.CamiResp;
import top.continew.admin.auto.sky.service.CamiService;
import top.continew.admin.auto.sky.service.TaskCamiService;
import top.continew.admin.auto.sky.service.TaskService;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import java.util.List;

/**
 * cami业务实现
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Service
@RequiredArgsConstructor
public class CamiServiceImpl extends BaseServiceImpl<CamiMapper, CamiDO, CamiResp, CamiDetailResp, CamiQuery, CamiReq> implements CamiService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskCamiService taskCamiService;

    @Override
    protected void beforeDelete(List<Long> ids) {
        super.beforeDelete(ids);
        for (Long id : ids) {
            var taskCamiQuery = new TaskCamiQuery();
            taskCamiQuery.setCamiId(id);
            var ass = this.taskCamiService.list(taskCamiQuery, null).stream().findFirst().isPresent();
            CheckUtils.throwIf(ass, id + " 该卡密已经绑定任务，请先解除绑定");
        }
    }

    @Override
    protected void afterAdd(CamiReq req, CamiDO entity) {
        super.afterAdd(req, entity);

        if (!req.getCreateTask()) {
            return;
        }
        var taskCamiQuery = new TaskCamiQuery();
        taskCamiQuery.setCamiId(entity.getId());
        var taskCamiDO = this.taskCamiService.list(taskCamiQuery, null).stream().findFirst().orElse(null);
        if (taskCamiDO != null) {
            log.warn("卡密任务已经创建 " + taskCamiDO);
            return;
        }

        //game login success
        //create task
        TaskReq taskReq = new TaskReq();
        taskReq.setOrderId(entity.getOrderId());
        taskReq.setTaskDays(entity.getTaskSpec());
        taskReq.setNeedTime(entity.getDays());
        taskReq.setIsUrgent(entity.getIsUrgent());
        taskReq.setGameAccount("");
        taskReq.setChannel("1");

        var taskId = this.taskService.add(taskReq);

        //associate task and cami
        TaskCamiReq taskCamiReq = new TaskCamiReq();
        taskCamiReq.setTaskId(taskId);
        taskCamiReq.setCreateUser(entity.getCreateUser());
        taskCamiReq.setCamiId(entity.getId());
        taskCamiReq.setIsSelfCami(true);
        this.taskCamiService.add(taskCamiReq);
    }
}