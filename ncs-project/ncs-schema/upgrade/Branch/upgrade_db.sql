/*=============================================================*/
/* Alter table T_POLICY_BASE: add refer to Policy Template     */
/*=============================================================*/
alter table ncs.T_POLICY_BASE add PTVID NUMBER;

/*=============================================================*/
/* Alter table T_POLICY_BASE: ȥ��mpname��Ψһ��Լ��, �������DC�����޷��·�������     */
/*=============================================================*/
alter table ncs.T_POLICY_BASE drop unique (MPNAME);
