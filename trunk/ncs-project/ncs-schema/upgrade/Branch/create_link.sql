drop PUBLIC database link nccadm;

create PUBLIC database link nccadm
　　 connect to ncs identified by ncs
　　 using '(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.2.100)(PORT = 1521))) (CONNECT_DATA = (SERVICE_NAME = ncc)))'
;


/*=============================================================*/
/* Alter table T_POLICY_BASE: add refer to Policy Template     */
/*=============================================================*/
alter table T_POLICY_BASE add PTVID NUMBER;

/*=============================================================*/
/* Create synonym to nccadm db                                 */
/*=============================================================*/
drop table t_user;
create or replace synonym T_POLICY_PUBLISH_INFO for T_POLICY_PUBLISH_INFO@nccadm;
create or replace synonym T_POLICY_TEMPLATE for T_POLICY_TEMPLATE@nccadm;
create or replace synonym T_POLICY_TEMPLATE_VER for T_POLICY_TEMPLATE_VER@nccadm;
create or replace synonym T_POLICY_TEMPLATE_SCOPE for T_POLICY_TEMPLATE_SCOPE@nccadm;
create or replace synonym T_POLICY_EVENT_RULE for T_POLICY_EVENT_RULE@nccadm;
create or replace synonym T_TAKE_EFFECT_HISTORY for T_TAKE_EFFECT_HISTORY@nccadm;
create or replace synonym T_SERVER_NODE for T_SERVER_NODE@nccadm;
create or replace synonym T_ROLE_MANAGED_NODE for T_ROLE_MANAGED_NODE@nccadm;
create or replace synonym T_ROLE for T_ROLE@nccadm;
create or replace synonym T_USER_ROLE_MAP for T_USER_ROLE_MAP@nccadm;
create or replace synonym T_USER for T_USER@nccadm;
create or replace synonym V_POLICY_TPLT_VER_CHANGE for V_POLICY_TPLT_VER_CHANGE@nccadm;
create or replace synonym v_apply_device_type for v_apply_device_type@nccadm;


