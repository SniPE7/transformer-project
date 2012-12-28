-- 创建基本角色
insert into t_role(role_id, role_name) values(0, 'admin');
insert into t_role(role_id, role_name) values(1, 'operator');

-- 将admin用户赋权限为admin角色
insert into t_user_role_map(usid, role_id) values(0, 0);
-- 将ncc用户赋权限为operator角色
insert into t_user_role_map(usid, role_id) values(1, 1);

-- 创建用户
insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('100','ahncc','ahncc','安徽分行','0');
insert into t_role(role_id, role_name) values(100, 'ahoperator');
insert into t_user_role_map(usid, role_id) values(100, 100);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(100, 100);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('101','bfncc','bfncc','北京分中心','0');
insert into t_role(role_id, role_name) values(101, 'bfoperator');
insert into t_user_role_map(usid, role_id) values(101, 101);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(101, 101);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('102','bjncc','bjncc','北京分行','0');
insert into t_role(role_id, role_name) values(102, 'bjoperator');
insert into t_user_role_map(usid, role_id) values(102, 102);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(102, 102);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('103','cqncc','cqncc','重庆分行','0');
insert into t_role(role_id, role_name) values(103, 'cqoperator');
insert into t_user_role_map(usid, role_id) values(103, 103);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(103, 103);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('104','csncc','csncc','数据中心（北京）','0');
insert into t_role(role_id, role_name) values(104, 'csoperator');
insert into t_user_role_map(usid, role_id) values(104, 104);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(104, 104);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('105','dcncc','dcncc','软件开发中心','0');
insert into t_role(role_id, role_name) values(105, 'dcoperator');
insert into t_user_role_map(usid, role_id) values(105, 105);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(105, 105);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('106','dlncc','dlncc','大连分行','0');
insert into t_role(role_id, role_name) values(106, 'dloperator');
insert into t_user_role_map(usid, role_id) values(106, 106);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(106, 106);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('107','fjncc','fjncc','福建分行','0');
insert into t_role(role_id, role_name) values(107, 'fjoperator');
insert into t_user_role_map(usid, role_id) values(107, 107);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(107, 107);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('108','gdncc','gdncc','广东分行','0');
insert into t_role(role_id, role_name) values(108, 'gdoperator');
insert into t_user_role_map(usid, role_id) values(108, 108);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(108, 108);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('109','gsncc','gsncc','甘肃分行','0');
insert into t_role(role_id, role_name) values(109, 'gsoperator');
insert into t_user_role_map(usid, role_id) values(109, 109);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(109, 109);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('110','gxncc','gxncc','广西分行','0');
insert into t_role(role_id, role_name) values(110, 'gxoperator');
insert into t_user_role_map(usid, role_id) values(110, 110);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(110, 110);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('111','gzncc','gzncc','贵州分行','0');
insert into t_role(role_id, role_name) values(111, 'gzoperator');
insert into t_user_role_map(usid, role_id) values(111, 111);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(111, 111);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('112','hbncc','hbncc','河北分行','0');
insert into t_role(role_id, role_name) values(112, 'hboperator');
insert into t_user_role_map(usid, role_id) values(112, 112);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(112, 112);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('113','hlncc','hlncc','海南分行','0');
insert into t_role(role_id, role_name) values(113, 'hloperator');
insert into t_user_role_map(usid, role_id) values(113, 113);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(113, 113);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('114','hnncc','hnncc','河南分行','0');
insert into t_role(role_id, role_name) values(114, 'hnoperator');
insert into t_user_role_map(usid, role_id) values(114, 114);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(114, 114);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('115','hpncc','hpncc','湖北分行','0');
insert into t_role(role_id, role_name) values(115, 'hpoperator');
insert into t_user_role_map(usid, role_id) values(115, 115);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(115, 115);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('116','hqncc','hqncc','总行办公','0');
insert into t_role(role_id, role_name) values(116, 'hqoperator');
insert into t_user_role_map(usid, role_id) values(116, 116);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(116, 116);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('117','hrncc','hrncc','湖南分行','0');
insert into t_role(role_id, role_name) values(117, 'hroperator');
insert into t_user_role_map(usid, role_id) values(117, 117);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(117, 117);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('118','jlncc','jlncc','吉林分行','0');
insert into t_role(role_id, role_name) values(118, 'jloperator');
insert into t_user_role_map(usid, role_id) values(118, 118);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(118, 118);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('119','jsncc','jsncc','江苏分行','0');
insert into t_role(role_id, role_name) values(119, 'jsoperator');
insert into t_user_role_map(usid, role_id) values(119, 119);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(119, 119);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('120','jxncc','jxncc','江西分行','0');
insert into t_role(role_id, role_name) values(120, 'jxoperator');
insert into t_user_role_map(usid, role_id) values(120, 120);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(120, 120);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('121','ljncc','ljncc','黑龙江分行','0');
insert into t_role(role_id, role_name) values(121, 'ljoperator');
insert into t_user_role_map(usid, role_id) values(121, 121);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(121, 121);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('122','lnncc','lnncc','辽宁分行','0');
insert into t_role(role_id, role_name) values(122, 'lnoperator');
insert into t_user_role_map(usid, role_id) values(122, 122);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(122, 122);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('123','nfncc','nfncc','数据中心（上海）','0');
insert into t_role(role_id, role_name) values(123, 'nfoperator');
insert into t_user_role_map(usid, role_id) values(123, 123);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(123, 123);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('124','nmncc','nmncc','内蒙古分行','0');
insert into t_role(role_id, role_name) values(124, 'nmoperator');
insert into t_user_role_map(usid, role_id) values(124, 124);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(124, 124);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('125','nxncc','nxncc','宁夏分行','0');
insert into t_role(role_id, role_name) values(125, 'nxoperator');
insert into t_user_role_map(usid, role_id) values(125, 125);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(125, 125);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('126','qdncc','qdncc','青岛分行','0');
insert into t_role(role_id, role_name) values(126, 'qdoperator');
insert into t_user_role_map(usid, role_id) values(126, 126);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(126, 126);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('127','qhncc','qhncc','青海分行','0');
insert into t_role(role_id, role_name) values(127, 'qhoperator');
insert into t_user_role_map(usid, role_id) values(127, 127);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(127, 127);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('128','scncc','scncc','四川分行','0');
insert into t_role(role_id, role_name) values(128, 'scoperator');
insert into t_user_role_map(usid, role_id) values(128, 128);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(128, 128);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('129','sdncc','sdncc','山东分行','0');
insert into t_role(role_id, role_name) values(129, 'sdoperator');
insert into t_user_role_map(usid, role_id) values(129, 129);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(129, 129);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('130','shncc','shncc','上海分行','0');
insert into t_role(role_id, role_name) values(130, 'shoperator');
insert into t_user_role_map(usid, role_id) values(130, 130);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(130, 130);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('131','sxncc','sxncc','山西分行','0');
insert into t_role(role_id, role_name) values(131, 'sxoperator');
insert into t_user_role_map(usid, role_id) values(131, 131);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(131, 131);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('132','szncc','szncc','深圳分行','0');
insert into t_role(role_id, role_name) values(132, 'szoperator');
insert into t_user_role_map(usid, role_id) values(132, 132);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(132, 132);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('133','tjncc','tjncc','天津分行','0');
insert into t_role(role_id, role_name) values(133, 'tjoperator');
insert into t_user_role_map(usid, role_id) values(133, 133);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(133, 133);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('134','xjncc','xjncc','新疆分行','0');
insert into t_role(role_id, role_name) values(134, 'xjoperator');
insert into t_user_role_map(usid, role_id) values(134, 134);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(134, 134);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('135','xmncc','xmncc','厦门分行','0');
insert into t_role(role_id, role_name) values(135, 'xmoperator');
insert into t_user_role_map(usid, role_id) values(135, 135);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(135, 135);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('136','xxncc','xxncc','陕西分行','0');
insert into t_role(role_id, role_name) values(136, 'xxoperator');
insert into t_user_role_map(usid, role_id) values(136, 136);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(136, 136);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('137','ynncc','ynncc','云南分行','0');
insert into t_role(role_id, role_name) values(137, 'ynoperator');
insert into t_user_role_map(usid, role_id) values(137, 137);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(137, 137);

insert into t_user(USID, UNAME, PASSWORD, FULLNAME, STATUS) values('138','zjncc','zjncc','浙江分行','0');
insert into t_role(role_id, role_name) values(138, 'zjoperator');
insert into t_user_role_map(usid, role_id) values(138, 138);
insert into T_ROLE_MANAGED_NODE(role_id, server_id) values(138, 138);


commit;
