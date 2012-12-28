-- 创建基本角色
insert into t_role(role_id, role_name) values(0, 'admin');
insert into t_role(role_id, role_name) values(1, 'operator');

-- 将admin用户赋权限为admin角色
insert into t_user_role_map(usid, role_id) values(0, 0);
-- 将ncc用户赋权限为operator角色
insert into t_user_role_map(usid, role_id) values(1, 1);

-- 创建用户
--insert into t_user(USID, UNAME, PASSWORD) values(100, 'ahncc', 'ahncc');
-- 将ncc用户赋权限为operator角色
--insert into t_role(role_id, role_name) values(100, 'ahoperator');
--insert into t_user_role_map(usid, role_id) values(100, 100);
--insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(100, 100);

commit;
