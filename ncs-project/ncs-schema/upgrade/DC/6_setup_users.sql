-- ����������ɫ
insert into t_role(role_id, role_name) values(0, 'admin');
insert into t_role(role_id, role_name) values(1, 'operator');

-- ��admin�û���Ȩ��Ϊadmin��ɫ
insert into t_user_role_map(usid, role_id) values(0, 0);
-- ��ncc�û���Ȩ��Ϊoperator��ɫ
insert into t_user_role_map(usid, role_id) values(1, 1);

commit;
