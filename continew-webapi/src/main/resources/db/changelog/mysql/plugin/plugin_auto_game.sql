-- liquibase formatted sql

-- changeset wjh:1
-- comment 初始化代码生成插件
-- 初始化表结构
CREATE TABLE IF NOT EXISTS `game_cami` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT 'ID',
	version INT DEFAULT 1 COMMENT '版本号',
    cami VARCHAR(63) DEFAULT (uuid()) COMMENT 'UUID字符串',
    norm VARCHAR(255) DEFAULT '' COMMENT '卡密信息描述',
    order_id VARCHAR(255) NOT NULL COMMENT '网点订单编号',
    days INT DEFAULT 1 COMMENT '任务天数',
    task_spec VARCHAR(255) DEFAULT '{}' COMMENT '每日任务（JSON字符串）',
    login_uri VARCHAR(255) NOT NULL COMMENT '',
    `create_user` bigint(20)   NOT NULL                    COMMENT '创建人',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_user` bigint(20)   DEFAULT NULL                COMMENT '修改人',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    `state` INT DEFAULT 1 COMMENT '状态, 1: 未使用; 2: 在使用; 3: 已使用',
    login_info VARCHAR(255) DEFAULT '' COMMENT '登录信息',
    script_feedback_info VARCHAR(255) DEFAULT '' COMMENT '脚本反馈信息',
    user_feedback_info VARCHAR(255) DEFAULT '' COMMENT '用户反馈信息',
    device_id VARCHAR(255) DEFAULT '' COMMENT '设备ID',
    notes VARCHAR(255) DEFAULT '' COMMENT '备注',
    is_positive_review BOOLEAN DEFAULT FALSE COMMENT '是否为正面评价',
    is_del Boolean DEFAULT FALSE COMMENT '标记为删除',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `game_cami`(`cami`),
    UNIQUE INDEX `game_order`(`order_id`),
    UNIQUE INDEX `game_create_user`(`create_user`),
    UNIQUE INDEX `game_update_user`(`update_user`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT =  'cami';

CREATE TABLE IF NOT EXISTS `game_task` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT 'ID',
	version INT DEFAULT 1 COMMENT '版本号',
    `order_id` VARCHAR(255) NOT NULL COMMENT '网点订单编号',
    channel VARCHAR(50) DEFAULT '' COMMENT '渠道',
    task_state int DEFAULT 1 COMMENT '任务状态. 1:create; 2:pending; 3:running; 4:success; 5:fail;',
    `need_time` INT DEFAULT 0 COMMENT '需要运行次数',
    ran_time INT DEFAULT 0 COMMENT '已经运行次数',
    task_env_name VARCHAR(255) DEFAULT '' COMMENT '任务环境名称',
    `create_user` bigint(20)   NOT NULL                    COMMENT '创建人',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_user` bigint(20)   DEFAULT NULL                COMMENT '修改人',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    notes VARCHAR(255) DEFAULT '' COMMENT '备注',
    task_days VARCHAR(255) DEFAULT '{}' COMMENT '每日任务（JSON字符串）',
    is_urgent BOOLEAN DEFAULT FALSE COMMENT '是否紧急',
    game_account VARCHAR(255) DEFAULT '' COMMENT 'game账号',
    is_del Boolean DEFAULT FALSE COMMENT '标记为删除',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `game_order`(`order_id`),
    UNIQUE INDEX `game_create_user`(`create_user`),
    UNIQUE INDEX `game_update_user`(`update_user`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT =  '任务';

CREATE TABLE IF NOT EXISTS `game_task_cami` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT 'ID',
    version INT DEFAULT 1 COMMENT '版本号',
    task_id INT NOT NULL COMMENT '任务Id',
    `create_user` bigint(20)   NOT NULL                    COMMENT '创建人',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_user` bigint(20)   DEFAULT NULL                COMMENT '修改人',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    cami_id INT NOT NULL COMMENT 'camiId',
    is_self_cami Boolean DEFAULT FALSE COMMENT 'true:自定义卡密, false:附加卡密',
    is_del BOOLEAN DEFAULT FALSE COMMENT '标记为删除',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `game_task_id`(`task_id`),
    UNIQUE INDEX `game_cami_id`(`cami_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT =  'task-cami';

CREATE TABLE IF NOT EXISTS `game_task_execution` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT 'ID',
    version INT DEFAULT 1 COMMENT '版本号',
    task_id INT NOT NULL COMMENT '任务Id',
    begin_time DATETIME NOT NULL COMMENT 'begin执行时间',
    end_time DATETIME DEFAULT NULL COMMENT 'end执行时间',
    msg VARCHAR(512) DEFAULT '{}' COMMENT '任务消息记录(json)',
    is_del BOOLEAN DEFAULT FALSE COMMENT '标记为删除',
    PRIMARY KEY (id),
    UNIQUE INDEX game_task_exec_begin (begin_time)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='任务执行情况';

-- ----------------------------
-- Table structure for game_device
-- ----------------------------
CREATE TABLE IF NOT EXISTS `game_device` (
    `id` BIGINT(20) NOT NULL COMMENT '设备ID',
    name VARCHAR(255) NOT NULL COMMENT '在线情况',
    type BOOLEAN DEFAULT FALSE COMMENT '设备类型,true:登号机, false:业务机.',
    ip VARCHAR(255) NOT NULL COMMENT '设备IP',
    state BOOLEAN DEFAULT FALSE COMMENT '在线情况',
    connect_time DATETIME DEFAULT NULL COMMENT '通信时间',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='设备';