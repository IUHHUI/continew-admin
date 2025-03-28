package top.continew.admin.auto.sky.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.auto.sky.model.query.TaskCamiQuery;
import top.continew.admin.auto.sky.model.req.TaskCamiReq;
import top.continew.admin.auto.sky.model.resp.TaskCamiDetailResp;
import top.continew.admin.auto.sky.model.resp.TaskCamiResp;
import top.continew.admin.auto.sky.service.TaskCamiService;

/**
 * task-cami管理 API
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Tag(name = "task-cami管理 API")
@RestController
@CrudRequestMapping(value = "/sky/taskCami", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class TaskCamiController extends BaseController<TaskCamiService, TaskCamiResp, TaskCamiDetailResp, TaskCamiQuery, TaskCamiReq> {}