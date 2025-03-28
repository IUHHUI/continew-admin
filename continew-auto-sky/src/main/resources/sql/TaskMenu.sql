SET @parentId = 1905965551349493760;
-- 游戏任务管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '游戏任务管理', 1000, 2, '/sky/task', 'Task', 'sky/task/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 游戏任务管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1905965551349493761, '列表', @parentId, 3, 'sky:task:list', 1, 1, 1, NOW()),
    (1905965551349493762, '详情', @parentId, 3, 'sky:task:detail', 2, 1, 1, NOW()),
    (1905965551349493763, '新增', @parentId, 3, 'sky:task:add', 3, 1, 1, NOW()),
    (1905965551349493764, '修改', @parentId, 3, 'sky:task:update', 4, 1, 1, NOW()),
    (1905965551349493765, '删除', @parentId, 3, 'sky:task:delete', 5, 1, 1, NOW()),
    (1905965551349493766, '导出', @parentId, 3, 'sky:task:export', 6, 1, 1, NOW());

