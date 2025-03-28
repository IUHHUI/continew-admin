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
 * @since 2025/03/29 20:49
 */
@Tag(name = "cami管理 API")
@RestController
@CrudRequestMapping(value = "/sky/cami", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class CamiController extends BaseController<CamiService, CamiResp, CamiDetailResp, CamiQuery, CamiReq> {}