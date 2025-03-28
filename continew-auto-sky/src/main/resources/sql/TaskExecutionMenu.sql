SET @parentId = 1905965551529848832;
-- 执行情况管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '执行情况管理', 1000, 2, '/sky/taskExecution', 'TaskExecution', 'sky/taskExecution/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 执行情况管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1905965551529848833, '列表', @parentId, 3, 'sky:taskExecution:list', 1, 1, 1, NOW()),
    (1905965551529848834, '详情', @parentId, 3, 'sky:taskExecution:detail', 2, 1, 1, NOW()),
    (1905965551529848835, '新增', @parentId, 3, 'sky:taskExecution:add', 3, 1, 1, NOW()),
    (1905965551529848836, '修改', @parentId, 3, 'sky:taskExecution:update', 4, 1, 1, NOW()),
    (1905965551529848837, '删除', @parentId, 3, 'sky:taskExecution:delete', 5, 1, 1, NOW()),
    (1905965551529848838, '导出', @parentId, 3, 'sky:taskExecution:export', 6, 1, 1, NOW());

