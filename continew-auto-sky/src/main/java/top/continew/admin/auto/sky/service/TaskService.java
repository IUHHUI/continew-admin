package top.continew.admin.auto.sky.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.auto.sky.model.query.TaskQuery;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;

/**
 * 游戏任务业务接口
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
public interface TaskService extends BaseService<TaskResp, TaskDetailResp, TaskQuery, TaskReq> {}