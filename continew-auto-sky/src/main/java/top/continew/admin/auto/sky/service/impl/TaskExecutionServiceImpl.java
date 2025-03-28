package top.continew.admin.auto.sky.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.auto.sky.mapper.TaskExecutionMapper;
import top.continew.admin.auto.sky.model.entity.TaskExecutionDO;
import top.continew.admin.auto.sky.model.query.TaskExecutionQuery;
import top.continew.admin.auto.sky.model.req.TaskExecutionReq;
import top.continew.admin.auto.sky.model.resp.TaskExecutionDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskExecutionResp;
import top.continew.admin.auto.sky.service.TaskExecutionService;

/**
 * 执行情况业务实现
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Service
@RequiredArgsConstructor
public class TaskExecutionServiceImpl extends BaseServiceImpl<TaskExecutionMapper, TaskExecutionDO, TaskExecutionResp, TaskExecutionDetailResp, TaskExecutionQuery, TaskExecutionReq> implements TaskExecutionService {}