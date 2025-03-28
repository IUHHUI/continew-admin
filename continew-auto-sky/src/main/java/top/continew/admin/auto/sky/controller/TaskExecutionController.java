package top.continew.admin.auto.sky.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.auto.sky.model.query.TaskExecutionQuery;
import top.continew.admin.auto.sky.model.req.TaskExecutionReq;
import top.continew.admin.auto.sky.model.resp.TaskExecutionDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskExecutionResp;
import top.continew.admin.auto.sky.service.TaskExecutionService;

/**
 * 执行情况管理 API
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Tag(name = "执行情况管理 API")
@RestController
@CrudRequestMapping(value = "/sky/taskExecution", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class TaskExecutionController extends BaseController<TaskExecutionService, TaskExecutionResp, TaskExecutionDetailResp, TaskExecutionQuery, TaskExecutionReq> {}