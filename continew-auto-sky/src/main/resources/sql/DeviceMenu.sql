SET @parentId = 1908485253507731456;
-- 设备管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '设备管理', 1000, 2, '/sky/device', 'Device', 'sky/device/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 设备管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1908485253507731457, '列表', @parentId, 3, 'sky:device:list', 1, 1, 1, NOW()),
    (1908485253507731458, '详情', @parentId, 3, 'sky:device:detail', 2, 1, 1, NOW()),
    (1908485253507731459, '新增', @parentId, 3, 'sky:device:add', 3, 1, 1, NOW()),
    (1908485253507731460, '修改', @parentId, 3, 'sky:device:update', 4, 1, 1, NOW()),
    (1908485253507731461, '删除', @parentId, 3, 'sky:device:delete', 5, 1, 1, NOW()),
    (1908485253507731462, '导出', @parentId, 3, 'sky:device:export', 6, 1, 1, NOW());

