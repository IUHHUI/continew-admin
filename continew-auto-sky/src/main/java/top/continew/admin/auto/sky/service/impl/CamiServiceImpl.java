package top.continew.admin.auto.sky.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.auto.sky.mapper.CamiMapper;
import top.continew.admin.auto.sky.model.entity.CamiDO;
import top.continew.admin.auto.sky.model.query.CamiQuery;
import top.continew.admin.auto.sky.model.req.CamiReq;
import top.continew.admin.auto.sky.model.resp.CamiDetailResp;
import top.continew.admin.auto.sky.model.resp.CamiResp;
import top.continew.admin.auto.sky.service.CamiService;

/**
 * cami业务实现
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Service
@RequiredArgsConstructor
public class CamiServiceImpl extends BaseServiceImpl<CamiMapper, CamiDO, CamiResp, CamiDetailResp, CamiQuery, CamiReq> implements CamiService {}