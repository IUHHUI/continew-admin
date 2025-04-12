SET @parentId = 1911031923664343040;
-- cami管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, 'cami管理', 1000, 2, '/sky/cami', 'Cami', 'sky/cami/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- cami管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1911031923664343041, '列表', @parentId, 3, 'sky:cami:list', 1, 1, 1, NOW()),
    (1911031923664343042, '详情', @parentId, 3, 'sky:cami:detail', 2, 1, 1, NOW()),
    (1911031923664343043, '新增', @parentId, 3, 'sky:cami:add', 3, 1, 1, NOW()),
    (1911031923664343044, '修改', @parentId, 3, 'sky:cami:update', 4, 1, 1, NOW()),
    (1911031923664343045, '删除', @parentId, 3, 'sky:cami:delete', 5, 1, 1, NOW()),
    (1911031923664343046, '导出', @parentId, 3, 'sky:cami:export', 6, 1, 1, NOW());

