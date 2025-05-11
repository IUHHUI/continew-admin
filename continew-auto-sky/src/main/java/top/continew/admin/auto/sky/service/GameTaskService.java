package top.continew.admin.auto.sky.service;

import top.continew.admin.auto.sky.model.req.GameDeviceStateReq;
import top.continew.admin.auto.sky.model.resp.GameTaskResp;

/**
 * @author ephui
 */
public interface GameTaskService {

    GameTaskResp reportAndReceiveGameTask(GameDeviceStateReq req);

    void pushGameWorkTask(long taskId, long deviceId, boolean isUrgent);

    void scheduleGameTaskEveryDay();

}
