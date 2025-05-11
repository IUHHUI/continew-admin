/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.auto.sky.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.continew.admin.auto.sky.mapper.DeviceMapper;
import top.continew.admin.auto.sky.model.entity.DeviceDO;
import top.continew.admin.auto.sky.model.entity.SkyDict;
import top.continew.admin.auto.sky.model.query.DeviceQuery;
import top.continew.admin.auto.sky.model.req.DeviceReq;
import top.continew.admin.auto.sky.model.req.GameDeviceStateReq;
import top.continew.admin.auto.sky.model.resp.DeviceDetailResp;
import top.continew.admin.auto.sky.model.resp.DeviceResp;
import top.continew.admin.auto.sky.service.DeviceService;
import top.continew.admin.system.service.UserService;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import java.time.LocalDateTime;

/**
 * 设备业务实现
 *
 * @author wjh
 * @since 2025/04/12 20:21
 */
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl extends BaseServiceImpl<DeviceMapper, DeviceDO, DeviceResp, DeviceDetailResp, DeviceQuery, DeviceReq> implements DeviceService {
    @Autowired
    private UserService userService;

    private long getAdminId() {
        //TODO
        return this.userService.getByUsername("admin").getId();
    }

    @Override
    public DeviceDO insertOrUpdateDevice(GameDeviceStateReq req) {
        DeviceDO deviceDO = baseMapper.lambdaQuery().select().eq(DeviceDO::getDevice, req.getDevice()).one();
        if (null == deviceDO) {
            deviceDO = new DeviceDO();
            deviceDO.setDevice(req.getDevice());
            var now = LocalDateTime.now();
            deviceDO.setCreateUser(getAdminId());
            deviceDO.setCreateTime(now);
            deviceDO.setState(SkyDict.GAME_DEVICE_STATE_ONLINE);
        } else {
            deviceDO.setUpdateTime(LocalDateTime.now());
            deviceDO.setUpdateUser(deviceDO.getCreateUser());
        }
        baseMapper.insertOrUpdate(deviceDO);
        return deviceDO;
    }
}