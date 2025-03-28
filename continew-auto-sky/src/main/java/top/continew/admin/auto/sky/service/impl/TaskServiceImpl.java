package top.continew.admin.auto.sky.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.auto.sky.mapper.TaskMapper;
import top.continew.admin.auto.sky.model.entity.TaskDO;
import top.continew.admin.auto.sky.model.query.TaskQuery;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;
import top.continew.admin.auto.sky.service.TaskService;

/**
 * 游戏任务业务实现
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl extends BaseServiceImpl<TaskMapper, TaskDO, TaskResp, TaskDetailResp, TaskQuery, TaskReq> implements TaskService {}