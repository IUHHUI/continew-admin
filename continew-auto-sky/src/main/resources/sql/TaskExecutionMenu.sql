SET @parentId = 1911031924008275968;
-- 执行情况管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '执行情况管理', 1000, 2, '/sky/taskExecution', 'TaskExecution', 'sky/taskExecution/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 执行情况管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1911031924008275969, '列表', @parentId, 3, 'sky:taskExecution:list', 1, 1, 1, NOW()),
    (1911031924008275970, '详情', @parentId, 3, 'sky:taskExecution:detail', 2, 1, 1, NOW()),
    (1911031924008275971, '新增', @parentId, 3, 'sky:taskExecution:add', 3, 1, 1, NOW()),
    (1911031924008275972, '修改', @parentId, 3, 'sky:taskExecution:update', 4, 1, 1, NOW()),
    (1911031924008275973, '删除', @parentId, 3, 'sky:taskExecution:delete', 5, 1, 1, NOW()),
    (1911031924008275974, '导出', @parentId, 3, 'sky:taskExecution:export', 6, 1, 1, NOW());

