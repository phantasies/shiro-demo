
INSERT INTO `sys_user` VALUES (1, 'admin', 'AE449AD18CD86408', 'ROLE_ADMIN', '2018-12-25 15:10:51');
INSERT INTO `sys_user` VALUES (3, 'user001', '657FE7D30BA54A21', 'ROLE_USER_LOW', '2018-12-25 15:10:51');
INSERT INTO `sys_user` VALUES (4, 'user002', '69E86B0DB21FF40E', 'ROLE_USER_HIGH', '2018-12-25 15:10:51');

INSERT INTO `sys_user_role` VALUES (1, 'ROLE_ADMIN', 'function1_menu', NULL);
INSERT INTO `sys_user_role` VALUES (2, 'ROLE_ADMIN', 'function1_query', NULL);
INSERT INTO `sys_user_role` VALUES (3, 'ROLE_ADMIN', 'function1_edit', NULL);
INSERT INTO `sys_user_role` VALUES (4, 'ROLE_USER_LOW', 'function1_menu', NULL);
INSERT INTO `sys_user_role` VALUES (5, 'ROLE_USER_LOW', 'function1_query', NULL);

INSERT INTO `sys_permission` VALUES (11, 'function1_menu', 'xxx菜单', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (12, 'function1_query', 'xxx查询', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (13, 'function1_edit', 'xxx编辑', NULL, NULL, NULL);
