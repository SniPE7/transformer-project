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

exit

