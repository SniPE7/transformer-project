-- ����������ɫ
insert into t_role(role_id, role_name) values(0, 'admin');
insert into t_role(role_id, role_name) values(1, 'operator');

-- ��admin�û���Ȩ��Ϊadmin��ɫ
insert into t_user_role_map(usid, role_id) values(0, 0);
-- ��ncc�û���Ȩ��Ϊoperator��ɫ
insert into t_user_role_map(usid, role_id) values(1, 1);

-- �����û�
--insert into t_user(USID, UNAME, PASSWORD) values(100, 'ahncc', 'ahncc');
-- ��ncc�û���Ȩ��Ϊoperator��ɫ
--insert into t_role(role_id, role_name) values(100, 'ahoperator');
--insert into t_user_role_map(usid, role_id) values(100, 100);
--insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(100, 100);

commit;
