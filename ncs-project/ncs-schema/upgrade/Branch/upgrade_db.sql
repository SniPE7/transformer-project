/*=============================================================*/
/* Alter table T_POLICY_BASE: add refer to Policy Template     */
/*=============================================================*/
alter table ncs.T_POLICY_BASE add PTVID NUMBER;

/*=============================================================*/
/* Alter table T_POLICY_BASE: 去除mpname的唯一性约束, 否则将造成DC策略无法下发到分行     */
/*=============================================================*/
alter table ncs.T_POLICY_BASE drop unique (MPNAME);
