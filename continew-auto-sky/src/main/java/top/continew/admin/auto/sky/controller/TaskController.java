package top.continew.admin.auto.sky.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.auto.sky.model.query.TaskQuery;
import top.continew.admin.auto.sky.model.req.TaskReq;
import top.continew.admin.auto.sky.model.resp.TaskDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskResp;
import top.continew.admin.auto.sky.service.TaskService;

/**
 * 游戏任务管理 API
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Tag(name = "游戏任务管理 API")
@RestController
@CrudRequestMapping(value = "/sky/task", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class TaskController extends BaseController<TaskService, TaskResp, TaskDetailResp, TaskQuery, TaskReq> {}