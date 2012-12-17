/*=============================================================*/
/* Alter table T_POLICY_BASE: add refer to Policy Template     */
/*=============================================================*/
alter table T_POLICY_BASE add PTVID NUMBER;

alter table NCS.T_POLICY_BASE
   add constraint FK_T_POLICY_REFERENCE_T_POLIC2 foreign key (PTVID)
      references T_POLICY_TEMPLATE_VER (PTVID);
      
/*=============================================================*/
/* Alter table T_POLICY_BASE: add refer to Policy Template     */
/*=============================================================*/
alter table T_USER add FULLNAME VARCHAR(64);
alter table T_USER add EMAIL VARCHAR(64);
