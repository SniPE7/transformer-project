-- Execute as system user

--drop PUBLIC database link nccadm;

create PUBLIC database link nccadm
　　 connect to ncslkuser identified by ncslkuser
　　 using '(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.2.100)(PORT = 1521))) (CONNECT_DATA = (SERVICE_NAME = ncc)))'
;


/*=============================================================*/
/* Alter table T_POLICY_BASE: add refer to Policy Template     */
/*=============================================================*/
alter table ncs.T_POLICY_BASE add PTVID NUMBER;

/*=============================================================*/
/* Alter table T_POLICY_BASE: 去除mpname的唯一性约束, 否则将造成DC策略无法下发到分行     */
/*=============================================================*/
alter table ncs.T_POLICY_BASE drop unique (MPNAME);
  
/*=============================================================*/
/* Create synonym to nccadm db                                 */
/*=============================================================*/
drop table ncs.t_user;
create or replace synonym ncs.T_POLICY_PUBLISH_INFO for ncs.T_POLICY_PUBLISH_INFO@nccadm;
create or replace synonym ncs.T_POLICY_TEMPLATE for ncs.T_POLICY_TEMPLATE@nccadm;
create or replace synonym ncs.T_POLICY_TEMPLATE_VER for ncs.T_POLICY_TEMPLATE_VER@nccadm;
create or replace synonym ncs.T_POLICY_TEMPLATE_SCOPE for ncs.T_POLICY_TEMPLATE_SCOPE@nccadm;
create or replace synonym ncs.T_POLICY_EVENT_RULE for ncs.T_POLICY_EVENT_RULE@nccadm;
create or replace synonym ncs.T_TAKE_EFFECT_HISTORY for ncs.T_TAKE_EFFECT_HISTORY@nccadm;
create or replace synonym ncs.T_SERVER_NODE for ncs.T_SERVER_NODE@nccadm;
create or replace synonym ncs.T_ROLE_MANAGED_NODE for ncs.T_ROLE_MANAGED_NODE@nccadm;
create or replace synonym ncs.T_ROLE for ncs.T_ROLE@nccadm;
create or replace synonym ncs.T_USER_ROLE_MAP for ncs.T_USER_ROLE_MAP@nccadm;
create or replace synonym ncs.T_USER for ncs.T_USER@nccadm;
--create or replace synonym ncs.V_POLICY_TPLT_VER_CHANGE for ncs.V_POLICY_TPLT_VER_CHANGE@nccadm;
--create or replace synonym ncs.v_apply_device_type for ncs.v_apply_device_type@nccadm;

-- Resolve ORA-22992 issue in access LOB from DBLink remote DB
create global temporary table ncs.TMP_TEH  (
   TEID                 NUMBER                          not null,
   USID                 NUMBER,
   PPIID                NUMBER,
   SERVER_ID            NUMBER,
   GENERED_TIME         TIMESTAMP,
   SRC_TYPE_FILE        CLOB,
   ICMP_XML_FILE        CLOB,
   SNMP_XML_FILE        CLOB,
   ICMP_THRESHOLD       CLOB,
   SNMP_THRESHOLD       CLOB,
   EFFECT_TIME          TIMESTAMP,
   EFFECT_STATUS        VARCHAR2(256 BYTE),
   constraint PK_TMP_TEH primary key (TEID)
) on commit delete rows;
-- insert into TMP_TEH(TEID,SNMP_XML_FILE)  select TEID,SNMP_XML_FILE from T_TAKE_EFFECT_HISTORY

exit

