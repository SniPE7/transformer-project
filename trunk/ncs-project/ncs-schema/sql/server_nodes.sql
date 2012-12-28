alter table T_TAKE_EFFECT_HISTORY
  drop constraint FK_T_TAKE_E_REFERENCE_T_SERVER;
  
alter table T_TAKE_EFFECT_HISTORY
   add constraint FK_T_TAKE_E_REFERENCE_T_SERVER foreign key (SERVER_ID)
      references T_SERVER_NODE (SERVER_ID)
      on delete cascade;

alter table T_ROLE_MANAGED_NODE
   drop constraint FK_T_ROLE_M_REFERENCE_T_SERVER;
alter table T_ROLE_MANAGED_NODE
   add constraint FK_T_ROLE_M_REFERENCE_T_SERVER foreign key (SERVER_ID)
      references T_SERVER_NODE (SERVER_ID)
      on delete cascade;
delete from t_server_node;

insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('100','AH','operation','安徽分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('101','BF','operation','北京分中心',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('102','BJ','operation','北京分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('103','CQ','operation','重庆分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('104','CS','operation','数据中心（北京）',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('105','DC','operation','软件开发中心',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('106','DL','operation','大连分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('107','FJ','operation','福建分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('108','GD','operation','广东分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('109','GS','operation','甘肃分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('110','GX','operation','广西分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('111','GZ','operation','贵州分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('112','HB','operation','河北分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('113','HL','operation','海南分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('114','HN','operation','河南分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('115','HP','operation','湖北分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('116','HQ','operation','总行办公',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('117','HR','operation','湖南分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('118','JL','operation','吉林分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('119','JS','operation','江苏分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('120','JX','operation','江西分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('121','LJ','operation','黑龙江分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('122','LN','operation','辽宁分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('123','NF','operation','数据中心（上海）',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('124','NM','operation','内蒙古分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('125','NX','operation','宁夏分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('126','QD','operation','青岛分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('127','QH','operation','青海分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('128','SC','operation','四川分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('129','SD','operation','山东分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('130','SH','operation','上海分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('131','SX','operation','山西分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('132','SZ','operation','深圳分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('133','TJ','operation','天津分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('134','XJ','operation','新疆分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('135','XM','operation','厦门分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('136','XX','operation','陕西分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('137','YN','operation','云南分行',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('138','ZJ','operation','浙江分行',NULL,NULL);

commit;