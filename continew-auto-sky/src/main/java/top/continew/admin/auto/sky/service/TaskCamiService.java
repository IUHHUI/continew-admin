package top.continew.admin.auto.sky.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.auto.sky.model.query.TaskCamiQuery;
import top.continew.admin.auto.sky.model.req.TaskCamiReq;
import top.continew.admin.auto.sky.model.resp.TaskCamiDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskCamiResp;

/**
 * task-cami业务接口
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
public interface TaskCamiService extends BaseService<TaskCamiResp, TaskCamiDetailResp, TaskCamiQuery, TaskCamiReq> {}