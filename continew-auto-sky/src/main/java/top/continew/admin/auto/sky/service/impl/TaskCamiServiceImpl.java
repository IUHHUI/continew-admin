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
 * @since 2025/03/29 20:49
 */
@Service
@RequiredArgsConstructor
public class TaskCamiServiceImpl extends BaseServiceImpl<TaskCamiMapper, TaskCamiDO, TaskCamiResp, TaskCamiDetailResp, TaskCamiQuery, TaskCamiReq> implements TaskCamiService {}