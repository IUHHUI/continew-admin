package top.continew.admin.auto.sky.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.auto.sky.model.query.CamiQuery;
import top.continew.admin.auto.sky.model.req.CamiReq;
import top.continew.admin.auto.sky.model.resp.CamiDetailResp;
import top.continew.admin.auto.sky.model.resp.CamiResp;

/**
 * cami业务接口
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
public interface CamiService extends BaseService<CamiResp, CamiDetailResp, CamiQuery, CamiReq> {}