/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2012/12/7 15:15:58                           */
/*==============================================================*/


alter table T_POLICY_BASE
   drop constraint FK_T_POLICY_REFERENCE_T_POLIC2;

alter table T_POLICY_EVENT_RULE
   drop constraint FK_T_POLICY_REFERENCE_T_POLIC6;

alter table T_POLICY_EVENT_RULE
   drop constraint FK_T_POLICY_REFERENCE_T_EVENT_;

alter table T_POLICY_TEMPLATE_SCOPE
   drop constraint FK_T_POLICY_REFERENCE_T_POLIC5;

alter table T_POLICY_TEMPLATE_SCOPE
   drop constraint FK_T_POLICY_REFERENCE_T_DEVICE;

alter table T_POLICY_TEMPLATE_SCOPE
   drop constraint FK_T_POLICY_REFERENCE_T_MANUFA;

alter table T_POLICY_TEMPLATE_VER
   drop constraint FK_T_POLICY_REFERENCE_T_POLIC4;

alter table T_POLICY_TEMPLATE_VER
   drop constraint FK_T_POLICY_REFERENCE_T_POLICY;

alter table T_ROLE_MANAGED_NODE
   drop constraint FK_T_ROLE_M_REFERENCE_T_ROLE;

alter table T_ROLE_MANAGED_NODE
   drop constraint FK_T_ROLE_M_REFERENCE_T_SERVER;

alter table T_TAKE_EFFECT_HISTORY
   drop constraint FK_T_TAKE_E_REFERENCE_T_USER;

alter table T_TAKE_EFFECT_HISTORY
   drop constraint FK_T_TAKE_E_REFERENCE_T_POLICY;

alter table T_TAKE_EFFECT_HISTORY
   drop constraint FK_T_TAKE_E_REFERENCE_T_SERVER;

alter table T_USER_ROLE_MAP
   drop constraint FK_T_USER_R_REFERENCE_T_USER;

alter table T_USER_ROLE_MAP
   drop constraint FK_T_USER_R_REFERENCE_T_ROLE;

drop table T_POLICY_EVENT_RULE cascade constraints;

drop table T_POLICY_PUBLISH_INFO cascade constraints;

drop table T_POLICY_TEMPLATE cascade constraints;

drop table T_POLICY_TEMPLATE_SCOPE cascade constraints;

drop table T_POLICY_TEMPLATE_VER cascade constraints;

drop table T_ROLE cascade constraints;

drop table T_ROLE_MANAGED_NODE cascade constraints;

drop table T_SERVER_NODE cascade constraints;

drop table T_TAKE_EFFECT_HISTORY cascade constraints;

drop table T_USER_ROLE_MAP cascade constraints;

/*==============================================================*/
/* Table: T_POLICY_EVENT_RULE                                   */
/*==============================================================*/
create table T_POLICY_EVENT_RULE  (
   EVEID                NUMBER,
   PTVID                NUMBER                          not null,
   MODID                NUMBER                          not null,
   POLL                 NUMBER                         default 60,
   VALUE_1              VARCHAR2(32)                   default 'N/A',
   VALUE_1_RULE         VARCHAR2(1024)                 default 'N/A',
   SEVERITY_1           NUMBER,
   FILTER_A             VARCHAR2(1),
   VALUE_2              VARCHAR2(32)                   default 'N/A',
   VALUE_2_RULE         VARCHAR2(1024)                 default 'N/A',
   SEVERITY_2           NUMBER,
   FILTER_B             VARCHAR2(1),
   SEVERITY_A           NUMBER,
   SEVERITY_B           NUMBER,
   VALUE_1_LOW          VARCHAR2(32)                   default '0',
   VALUE_1_LOW_RULE     VARCHAR2(1024)                 default '0',
   VALUE_2_LOW          VARCHAR2(32)                   default '0',
   VALUE_2_LOW_RULE     VARCHAR2(1024)                 default '0',
   V1L_SEVERITY_1       NUMBER,
   V1L_SEVERITY_A       NUMBER,
   V2L_SEVERITY_2       NUMBER,
   V2L_SEVERITY_B       NUMBER,
   COMPARETYPE          VARCHAR2(20)
);

comment on column T_POLICY_EVENT_RULE.MODID is
'????????????????????????eveid????????????????????????SNMP, SysLog????';

comment on column T_POLICY_EVENT_RULE.POLL is
'Poll??????????';

comment on column T_POLICY_EVENT_RULE.VALUE_1 is
'????1';

comment on column T_POLICY_EVENT_RULE.VALUE_1_RULE is
'????1';

comment on column T_POLICY_EVENT_RULE.SEVERITY_1 is
'????1????????';

comment on column T_POLICY_EVENT_RULE.VALUE_2 is
'????2';

comment on column T_POLICY_EVENT_RULE.VALUE_2_RULE is
'????2';

comment on column T_POLICY_EVENT_RULE.SEVERITY_2 is
'????2????????';

/*==============================================================*/
/* Table: T_POLICY_PUBLISH_INFO                                 */
/*==============================================================*/
create table T_POLICY_PUBLISH_INFO  (
   PPIID                NUMBER                          not null,
   VERSION              VARCHAR2(64 BYTE)               not null,
   VERSION_TAG          VARCHAR2(128 BYTE)              not null,
   DESCRIPTION          VARCHAR2(512 BYTE),
   PUBLISH_TIME         TIMESTAMP,
   CREATE_TIME         TIMESTAMP                       not null,
   UPDATE_TIME         TIMESTAMP,
   constraint PK_T_POLICY_PUBLISH_INFO primary key (PPIID)
);

/*==============================================================*/
/* Table: T_POLICY_TEMPLATE                                     */
/*==============================================================*/
create table T_POLICY_TEMPLATE  (
   PTID                 NUMBER                          not null,
   MPNAME               VARCHAR2(64)                    not null,
   STATUS               CHAR                            not null,
   CATEGORY             NUMBER                          not null,
   DESCRIPTION          VARCHAR2(400),
   constraint PK_T_POLICY_TEMPLATE primary key (PTID)
);

comment on column T_POLICY_TEMPLATE.MPNAME is
'Policy Name';

comment on column T_POLICY_TEMPLATE.STATUS is
'R - Released and used
D - Draft stage
U - Unused, history version';

comment on column T_POLICY_TEMPLATE.CATEGORY is
'1  - Device Policy
4  - Port Policy
9  - PriMIB Policy
8  - ';

/*==============================================================*/
/* Table: T_POLICY_TEMPLATE_SCOPE                               */
/*==============================================================*/
create table T_POLICY_TEMPLATE_SCOPE  (
   PTID                 NUMBER                          not null,
   PT_VERSION           VARCHAR(64)                     not null,
   DTID                 NUMBER                          not null,
   MRID                 NUMBER,
   PTVID                NUMBER,
   constraint PK_T_POLICY_TEMPLATE_SCOPE primary key (PTID, PT_VERSION, DTID)
);

/*==============================================================*/
/* Table: T_POLICY_TEMPLATE_VER                                 */
/*==============================================================*/
create table T_POLICY_TEMPLATE_VER  (
   PTVID                NUMBER                          not null,
   PT_VERSION           VARCHAR(64)                     not null,
   PTID                 NUMBER                          not null,
   PPIID                NUMBER,
   STATUS               CHAR                            not null,
   DESCRIPTION          VARCHAR2(400),
   constraint PK_T_POLICY_TEMPLATE_VER primary key (PTVID)
);

comment on column T_POLICY_TEMPLATE_VER.STATUS is
'R - Released and used
D - Draft stage
U - Unused, history version';

/*==============================================================*/
/* Table: T_ROLE                                                */
/*==============================================================*/
create table T_ROLE  (
   ROLE_ID              NUMBER                          not null,
   ROLE_NAME            VARCHAR(128)                    not null,
   constraint PK_T_ROLE primary key (ROLE_ID)
);

/*==============================================================*/
/* Table: T_ROLE_MANAGED_NODE                                   */
/*==============================================================*/
create table T_ROLE_MANAGED_NODE  (
   ROLE_ID              NUMBER                          not null,
   SERVER_ID            NUMBER                          not null,
   constraint PK_T_ROLE_MANAGED_NODE primary key (ROLE_ID, SERVER_ID)
);

/*==============================================================*/
/* Table: T_SERVER_NODE                                         */
/*==============================================================*/
create table T_SERVER_NODE  (
   SERVER_ID            NUMBER                          not null,
   SERVER_CODE          VARCHAR(64)                     not null,
   NODE_TYPE            VARCHAR(64)                     not null,
   SERVER_NAME          VARCHAR(64)                     not null,
   DESCRIPTION          VARCHAR2(512),
   SERVICE_ENDPOINT     VARCHAR2(1024),
   constraint PK_T_SERVER_NODE primary key (SERVER_ID)
);

comment on column T_SERVER_NODE.NODE_TYPE is
'M - 管理节点
O - 操作节点';

comment on column T_SERVER_NODE.SERVICE_ENDPOINT is
'Service endpoint information for access this node service interface. This can include JDBC, or WebServices access parameters.';

/*==============================================================*/
/* Table: T_TAKE_EFFECT_HISTORY                                 */
/*==============================================================*/
create table T_TAKE_EFFECT_HISTORY  (
   TEID                 NUMBER                          not null,
   USID                 NUMBER,
   PPIID                NUMBER,
   SERVER_ID            NUMBER,
   GENERED_TIME         TIMESTAMP,
   SRC_TYPE_FILE        BLOB,
   ICMP_XML_FILE        BLOB,
   SNMP_XML_FILE        BLOB,
   ICMP_THRESHOLD       BLOB,
   SNML_THRESHOLD       BLOB,
   EFFECT_TIME          TIMESTAMP,
   EFFECT_STATUS        VARCHAR2(256 BYTE),
   constraint PK_T_TAKE_EFFECT_HISTORY primary key (TEID)
);

/*==============================================================*/
/* Table: T_USER_ROLE_MAP                                       */
/*==============================================================*/
create table T_USER_ROLE_MAP  (
   USID                 NUMBER                          not null,
   ROLE_ID              NUMBER                          not null,
   constraint PK_T_USER_ROLE_MAP primary key (USID, ROLE_ID)
);

alter table T_POLICY_EVENT_RULE
   add constraint FK_T_POLICY_REFERENCE_T_POLIC6 foreign key (PTVID)
      references T_POLICY_TEMPLATE_VER (PTVID);

alter table T_POLICY_EVENT_RULE
   add constraint FK_T_POLICY_REFERENCE_T_EVENT_ foreign key (EVEID)
      references T_EVENT_TYPE_INIT (EVEID);

alter table T_POLICY_TEMPLATE_SCOPE
   add constraint FK_T_POLICY_REFERENCE_T_POLIC5 foreign key (PTVID)
      references T_POLICY_TEMPLATE_VER (PTVID);

alter table T_POLICY_TEMPLATE_SCOPE
   add constraint FK_T_POLICY_REFERENCE_T_DEVICE foreign key (DTID)
      references T_DEVICE_TYPE_INIT (DTID);

alter table T_POLICY_TEMPLATE_SCOPE
   add constraint FK_T_POLICY_REFERENCE_T_MANUFA foreign key (MRID)
      references T_MANUFACTURER_INFO_INIT (MRID);

alter table T_POLICY_TEMPLATE_VER
   add constraint FK_T_POLICY_REFERENCE_T_POLIC4 foreign key (PPIID)
      references T_POLICY_PUBLISH_INFO (PPIID);

alter table T_POLICY_TEMPLATE_VER
   add constraint FK_T_POLICY_REFERENCE_T_POLICY foreign key (PTID)
      references T_POLICY_TEMPLATE (PTID);

alter table T_ROLE_MANAGED_NODE
   add constraint FK_T_ROLE_M_REFERENCE_T_ROLE foreign key (ROLE_ID)
      references T_ROLE (ROLE_ID);

alter table T_ROLE_MANAGED_NODE
   add constraint FK_T_ROLE_M_REFERENCE_T_SERVER foreign key (SERVER_ID)
      references T_SERVER_NODE (SERVER_ID);

alter table T_TAKE_EFFECT_HISTORY
   add constraint FK_T_TAKE_E_REFERENCE_T_USER foreign key (USID)
      references T_USER (USID);

alter table T_TAKE_EFFECT_HISTORY
   add constraint FK_T_TAKE_E_REFERENCE_T_POLICY foreign key (PPIID)
      references T_POLICY_PUBLISH_INFO (PPIID);

alter table T_TAKE_EFFECT_HISTORY
   add constraint FK_T_TAKE_E_REFERENCE_T_SERVER foreign key (SERVER_ID)
      references T_SERVER_NODE (SERVER_ID);

alter table T_USER_ROLE_MAP
   add constraint FK_T_USER_R_REFERENCE_T_USER foreign key (USID)
      references T_USER (USID);

alter table T_USER_ROLE_MAP
   add constraint FK_T_USER_R_REFERENCE_T_ROLE foreign key (ROLE_ID)
      references T_ROLE (ROLE_ID);

