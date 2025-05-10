package top.continew.admin.auto.sky.model.entity;

/**
 * <pre>
 * /** @desc game login state
 *  * 1. 还没有提交过登录信息 state=1
 *  * 2.0 正在登录第一阶段,打开游戏发送认证吗或者获取二维码. state=20, 等待和游戏交互.不能再提交登录信息
 *  * 2.1 正在登录第一阶段end. state=21,能再提交登录信息
 *  * 2.2 正在登录, 等待登录结果 state=22, 等待和游戏交互.不能再提交登录信息
 *  * 2.3 登录失败, 可以再提交登录信息 state=23
 *  * 3. 登录成功的, 不能再登录 state=3
 *  *
 * export const GameLoginState ={
 * STATE_INIT:1,
 * STATE_LOGGING_1_BEGIN:20,
 * STATE_LOGGING_1_END:21,
 * STATE_LOGGING_2:22,
 * STATE_LOGIN_FAIL:23,
 * STATE_LOGIN_SUCCESS:3,
 * }
 *
 * export const GameLoginStateList =[
 * {label:'init',value:GameLoginState.STATE_INIT },
 * {label:'logging_1_begin',value:GameLoginState.STATE_LOGGING_1_BEGIN },
 * {label:'logging_1_end',value:GameLoginState.STATE_LOGGING_1_END },
 * {label:'logging_2',value:GameLoginState.STATE_LOGGING_2 },
 * {label:'login fail',value:GameLoginState.STATE_LOGIN_FAIL },
 * {label:'login success',value:GameLoginState.STATE_LOGIN_SUCCESS },
 * ].
 *
 * export const GameLoginType ={
 * PHONE_PASSWORD:1,
 * EMAIL_PASSWORD:2,
 * PHONE_SMS:3,
 * QR_CODE:4,
 * }
 *
 * export const GameTaskStateList =[
 * {label:'正常',value:1},
 * {label:'到期',value:0},
 * ].
 * </pre>
 */
public class SkyDict {
    public static final int GAME_CHANNEL_OFFICIAL = 1;
    public static final int GAME_CHANNEL_4399 = 2;
    public static final int GAME_CHANNEL_BILIBILI = 3;
    public static final int GAME_CHANNEL_HUAWEI = 4;
    public static final int GAME_CHANNEL_ALIGAME = 5;
    public static final int GAME_CHANNEL_OPPO = 6;
    public static final int GAME_CHANNEL_XIAOMI = 7;
    public static final int GAME_CHANNEL_VIVO = 8;

    public static final int GAME_LOGIN_STATE_INIT = 1;
    public static final int GAME_LOGIN_STATE_LOGGING_1_BEGIN = 20;
    public static final int GAME_LOGIN_STATE_LOGGING_1_END = 21;
    public static final int GAME_LOGIN_STATE_LOGGING_2 = 22;
    public static final int GAME_LOGIN_STATE_LOGIN_FAIL = 23;
    public static final int GAME_LOGIN_STATE_LOGIN_SUCCESS = 3;

    public static final int GAME_LOGIN_TYPE_PHONE_PASSWORD = 1;
    public static final int GAME_LOGIN_TYPE_EMAIL_PASSWORD = 2;
    public static final int GAME_LOGIN_TYPE_PHONE_SMS = 3;
    public static final int GAME_LOGIN_TYPE_QR_CODE = 4;

    /**
     * { label: '未使用', value: 1, color: 'orange' }, { label: '在使用', value: 2, color: 'yellow' }, { label: '已使用', value:
     * 3, color: 'green' },
     */
    public static final int GAME_CAMI_STATE_NOT_USED = 1;
    public static final int GAME_CAMI_STATE_USING = 2;
    public static final int GAME_CAMI_STATE_USED = 3;

    // task_state int DEFAULT 1 COMMENT '任务状态. ; 0:过期; 1:create; 2:pending; 3:running; 4:success; 5:fail';
    public static final int GAME_TASK_STATE_EXPIRED = 0;
    public static final int GAME_TASK_STATE_CREATE = 1;
    public static final int GAME_TASK_STATE_PENDING = 2;
    public static final int GAME_TASK_STATE_RUNNING = 3;
    public static final int GAME_TASK_STATE_SUCCESS = 4;
    public static final int GAME_TASK_STATE_FAIL = 5;

    // `type`      tinyint(1)   UNSIGNED NOT NULL DEFAULT 0 COMMENT '设备类型（1：登号机；0：业务机）',
    public static final int GAME_DEVICE_TYPE_LOGIN = 1;
    public static final int GAME_DEVICE_TYPE_BUSINESS = 0;
    // `state`      tinyint(1)   UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态（1：online；0：offline）',
    public static final int GAME_DEVICE_STATE_ONLINE = 1;
    public static final int GAME_DEVICE_STATE_OFFLINE = 0;

    /**
     * sky game task.
     */
    public static final int SKY_TASK_ = 1;

}
