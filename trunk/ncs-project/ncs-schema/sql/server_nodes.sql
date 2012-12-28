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

insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('100','AH','operation','���շ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('101','BF','operation','����������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('102','BJ','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('103','CQ','operation','�������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('104','CS','operation','�������ģ�������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('105','DC','operation','�����������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('106','DL','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('107','FJ','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('108','GD','operation','�㶫����',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('109','GS','operation','�������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('110','GX','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('111','GZ','operation','���ݷ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('112','HB','operation','�ӱ�����',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('113','HL','operation','���Ϸ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('114','HN','operation','���Ϸ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('115','HP','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('116','HQ','operation','���а칫',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('117','HR','operation','���Ϸ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('118','JL','operation','���ַ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('119','JS','operation','���շ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('120','JX','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('121','LJ','operation','����������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('122','LN','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('123','NF','operation','�������ģ��Ϻ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('124','NM','operation','���ɹŷ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('125','NX','operation','���ķ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('126','QD','operation','�ൺ����',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('127','QH','operation','�ຣ����',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('128','SC','operation','�Ĵ�����',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('129','SD','operation','ɽ������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('130','SH','operation','�Ϻ�����',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('131','SX','operation','ɽ������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('132','SZ','operation','���ڷ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('133','TJ','operation','������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('134','XJ','operation','�½�����',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('135','XM','operation','���ŷ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('136','XX','operation','��������',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('137','YN','operation','���Ϸ���',NULL,NULL);
insert into t_server_node(server_id, server_code, node_type, server_name, description, service_endpoint) values('138','ZJ','operation','�㽭����',NULL,NULL);

commit;