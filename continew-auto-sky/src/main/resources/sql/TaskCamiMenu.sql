SET @parentId = 1905965551445962752;
-- task-cami管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, 'task-cami管理', 1000, 2, '/sky/taskCami', 'TaskCami', 'sky/taskCami/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- task-cami管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1905965551445962753, '列表', @parentId, 3, 'sky:taskCami:list', 1, 1, 1, NOW()),
    (1905965551445962754, '详情', @parentId, 3, 'sky:taskCami:detail', 2, 1, 1, NOW()),
    (1905965551445962755, '新增', @parentId, 3, 'sky:taskCami:add', 3, 1, 1, NOW()),
    (1905965551445962756, '修改', @parentId, 3, 'sky:taskCami:update', 4, 1, 1, NOW()),
    (1905965551445962757, '删除', @parentId, 3, 'sky:taskCami:delete', 5, 1, 1, NOW()),
    (1905965551445962758, '导出', @parentId, 3, 'sky:taskCami:export', 6, 1, 1, NOW());

