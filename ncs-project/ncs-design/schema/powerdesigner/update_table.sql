/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2012/12/7 14:24:24                           */
/*==============================================================*/


drop view NCS.V_ICMP_DEV_THRESHOLDS
/

drop view NCS.V_ICMP_PORT_THRESHOLDS
/

drop view NCS.V_MF_CATE_DEVTYPE
/

drop view NCS.V_OID_GROUP
/

drop view NCS.V_PERFORM_PARAM
/

drop view NCS.V_PORT_ICMP_POLICY_EXPORT
/

drop view NCS.V_PORT_SNMP_POLICY_EXPORT
/

drop view NCS.V_PP_DEV
/

drop view NCS.V_PP_PORT
/

drop view NCS.V_PREDEFMIB_SNMP_POLICY_EXPORT
/

drop view NCS.V_SNMP_DEV_THRESHOLDS
/

drop view NCS.V_SNMP_PDM_THRESHOLDS
/

drop view NCS.V_SNMP_PORT_THRESHOLDS
/

drop view NCS.V_SRC_TYPE_EXPORT
/

drop view NCS.V_SYSLOG_DEV_EVENTS_ATTENTION
/

drop view NCS.V_SYSLOG_PORT_EVENTS_ATTENTION
/

drop view NCS.V_SYSLOG_PORT_LINE_NOTCARE
/

drop view NCS.V_TMP
/

alter table NCS.BK_SNMP_EVENTS_PROCESS
   drop primary key cascade
/

drop table NCS."tmp_BK_SNMP_EVENTS_PROCESS" cascade constraints
/

rename BK_SNMP_EVENTS_PROCESS to "tmp_BK_SNMP_EVENTS_PROCESS"
/

alter table NCS.BK_SYSLOG_EVENTS_PROCESS
   drop primary key cascade
/

drop table NCS."tmp_BK_SYSLOG_EVENTS_PROCESS" cascade constraints
/

rename BK_SYSLOG_EVENTS_PROCESS to "tmp_BK_SYSLOG_EVENTS_PROCESS"
/

alter table NCS.BK_SYSLOG_EVENTS_PROCESS_NS
   drop primary key cascade
/

drop table NCS."tmp_BK_SYSLOG_EVENTS_PROCESS_N" cascade constraints
/

rename BK_SYSLOG_EVENTS_PROCESS_NS to "tmp_BK_SYSLOG_EVENTS_PROCESS_N"
/

alter table NCS.ICMP_THRESHOLDS
   drop primary key cascade
/

drop table NCS."tmp_ICMP_THRESHOLDS" cascade constraints
/

rename ICMP_THRESHOLDS to "tmp_ICMP_THRESHOLDS"
/

alter table NCS.LINES_EVENTS_NOTCARE
   drop primary key cascade
/

drop table NCS."tmp_LINES_EVENTS_NOTCARE" cascade constraints
/

rename LINES_EVENTS_NOTCARE to "tmp_LINES_EVENTS_NOTCARE"
/

alter table NCS.POLICY_SYSLOG
   drop primary key cascade
/

alter table NCS.POLICY_SYSLOG
   drop unique (MARK, MANUFACTURE, MPID) cascade
/

drop table NCS."tmp_POLICY_SYSLOG" cascade constraints
/

rename POLICY_SYSLOG to "tmp_POLICY_SYSLOG"
/

alter table NCS.PREDEFMIB_INFO
   drop primary key cascade
/

drop table NCS."tmp_PREDEFMIB_INFO" cascade constraints
/

rename PREDEFMIB_INFO to "tmp_PREDEFMIB_INFO"
/

alter table NCS.SNMP_EVENTS_PROCESS
   drop primary key cascade
/

drop table NCS."tmp_SNMP_EVENTS_PROCESS" cascade constraints
/

rename SNMP_EVENTS_PROCESS to "tmp_SNMP_EVENTS_PROCESS"
/

alter table NCS.SNMP_THRESHOLDS
   drop primary key cascade
/

drop table NCS."tmp_SNMP_THRESHOLDS" cascade constraints
/

rename SNMP_THRESHOLDS to "tmp_SNMP_THRESHOLDS"
/

alter table NCS.SYSLOG_EVENTS_PROCESS
   drop primary key cascade
/

alter table NCS.SYSLOG_EVENTS_PROCESS
   drop unique (EVENTS, MANUFACTURE) cascade
/

drop table NCS."tmp_SYSLOG_EVENTS_PROCESS" cascade constraints
/

rename SYSLOG_EVENTS_PROCESS to "tmp_SYSLOG_EVENTS_PROCESS"
/

alter table NCS.SYSLOG_EVENTS_PROCESS_NS
   drop primary key cascade
/

alter table NCS.SYSLOG_EVENTS_PROCESS_NS
   drop unique (EVENTS, MANUFACTURE) cascade
/

drop table NCS."tmp_SYSLOG_EVENTS_PROCESS_NS" cascade constraints
/

rename SYSLOG_EVENTS_PROCESS_NS to "tmp_SYSLOG_EVENTS_PROCESS_NS"
/

alter table NCS.T_CATEGORY_MAP_INIT
   drop unique (ID) cascade
/

drop table NCS."tmp_T_CATEGORY_MAP_INIT" cascade constraints
/

rename T_CATEGORY_MAP_INIT to "tmp_T_CATEGORY_MAP_INIT"
/

alter table NCS.T_DEVICE_INFO
   drop primary key cascade
/

alter table NCS.T_DEVICE_INFO
   drop unique (SYSNAME) cascade
/

alter table NCS.T_DEVICE_INFO
   drop unique (DEVIP) cascade
/

drop table NCS."tmp_T_DEVICE_INFO" cascade constraints
/

rename T_DEVICE_INFO to "tmp_T_DEVICE_INFO"
/

alter table NCS.T_DEVICE_TYPE_INIT
   drop primary key cascade
/

alter table NCS.T_DEVICE_TYPE_INIT
   drop unique (OBJECTID) cascade
/

drop table NCS."tmp_T_DEVICE_TYPE_INIT" cascade constraints
/

rename T_DEVICE_TYPE_INIT to "tmp_T_DEVICE_TYPE_INIT"
/

alter table NCS.T_DEVPOL_MAP
   drop primary key cascade
/

drop table NCS."tmp_T_DEVPOL_MAP" cascade constraints
/

rename T_DEVPOL_MAP to "tmp_T_DEVPOL_MAP"
/

drop table NCS."tmp_T_EVENT_OID_INIT" cascade constraints
/

rename T_EVENT_OID_INIT to "tmp_T_EVENT_OID_INIT"
/

alter table NCS.T_EVENT_TYPE_INIT
   drop primary key cascade
/

alter table NCS.T_EVENT_TYPE_INIT
   drop unique (MAJOR, MINOR, OTHER) cascade
/

drop table NCS."tmp_T_EVENT_TYPE_INIT" cascade constraints
/

rename T_EVENT_TYPE_INIT to "tmp_T_EVENT_TYPE_INIT"
/

alter table NCS.T_GRP_NET
   drop primary key cascade
/

alter table NCS.T_GRP_NET
   drop unique (GNAME) cascade
/

drop table NCS."tmp_T_GRP_NET" cascade constraints
/

rename T_GRP_NET to "tmp_T_GRP_NET"
/

alter table NCS.T_LINEPOL_MAP
   drop primary key cascade
/

drop table NCS."tmp_T_LINEPOL_MAP" cascade constraints
/

rename T_LINEPOL_MAP to "tmp_T_LINEPOL_MAP"
/

drop table NCS."tmp_T_LIST_IP" cascade constraints
/

rename T_LIST_IP to "tmp_T_LIST_IP"
/

alter table NCS.T_MANUFACTURER_INFO_INIT
   drop primary key cascade
/

alter table NCS.T_MANUFACTURER_INFO_INIT
   drop unique (OBJECTID) cascade
/

alter table NCS.T_MANUFACTURER_INFO_INIT
   drop unique (MRNAME) cascade
/

drop table NCS."tmp_T_MANUFACTURER_INFO_INIT" cascade constraints
/

rename T_MANUFACTURER_INFO_INIT to "tmp_T_MANUFACTURER_INFO_INIT"
/

alter table NCS.T_MODULE_INFO_INIT
   drop primary key cascade
/

alter table NCS.T_MODULE_INFO_INIT
   drop unique (MNAME, MCODE) cascade
/

drop table NCS."tmp_T_MODULE_INFO_INIT" cascade constraints
/

rename T_MODULE_INFO_INIT to "tmp_T_MODULE_INFO_INIT"
/

alter table NCS.T_OIDGROUP_DETAILS_INIT
   drop primary key cascade
/

drop table NCS."tmp_T_OIDGROUP_DETAILS_INIT" cascade constraints
/

rename T_OIDGROUP_DETAILS_INIT to "tmp_T_OIDGROUP_DETAILS_INIT"
/

alter table NCS.T_OIDGROUP_INFO_INIT
   drop primary key cascade
/

alter table NCS.T_OIDGROUP_INFO_INIT
   drop unique (OIDGROUPNAME) cascade
/

drop table NCS."tmp_T_OIDGROUP_INFO_INIT" cascade constraints
/

rename T_OIDGROUP_INFO_INIT to "tmp_T_OIDGROUP_INFO_INIT"
/

alter table NCS.T_POLICY_BASE
   drop primary key cascade
/

alter table NCS.T_POLICY_BASE
   drop unique (MPNAME) cascade
/

drop table NCS."tmp_T_POLICY_BASE" cascade constraints
/

rename T_POLICY_BASE to "tmp_T_POLICY_BASE"
/

alter table NCS.T_POLICY_DETAILS
   drop primary key cascade
/

drop table NCS."tmp_T_POLICY_DETAILS" cascade constraints
/

rename T_POLICY_DETAILS to "tmp_T_POLICY_DETAILS"
/

alter table NCS.T_POLICY_PERIOD
   drop primary key cascade
/

alter table NCS.T_POLICY_PERIOD
   drop unique (PPNAME) cascade
/

drop table NCS."tmp_T_POLICY_PERIOD" cascade constraints
/

rename T_POLICY_PERIOD to "tmp_T_POLICY_PERIOD"
/

alter table NCS.T_PORT_INFO
   drop primary key cascade
/

drop table NCS."tmp_T_PORT_INFO" cascade constraints
/

rename T_PORT_INFO to "tmp_T_PORT_INFO"
/

alter table NCS.T_PP_DEV
   drop primary key cascade
/

drop table NCS."tmp_T_PP_DEV" cascade constraints
/

rename T_PP_DEV to "tmp_T_PP_DEV"
/

alter table NCS.T_PP_PORT
   drop primary key cascade
/

drop table NCS."tmp_T_PP_PORT" cascade constraints
/

rename T_PP_PORT to "tmp_T_PP_PORT"
/

alter table NCS.T_SERVER_INFO
   drop primary key cascade
/

alter table NCS.T_SERVER_INFO
   drop unique (NMSIP, NMSNAME) cascade
/

drop table NCS."tmp_T_SERVER_INFO" cascade constraints
/

rename T_SERVER_INFO to "tmp_T_SERVER_INFO"
/

alter table NCS.T_SVRMOD_MAP
   drop primary key cascade
/

drop table NCS."tmp_T_SVRMOD_MAP" cascade constraints
/

rename T_SVRMOD_MAP to "tmp_T_SVRMOD_MAP"
/

alter table NCS.T_USER
   drop primary key cascade
/

alter table NCS.T_USER
   drop unique (UNAME) cascade
/

drop table NCS."tmp_T_USER" cascade constraints
/

rename T_USER to "tmp_T_USER"
/

drop sequence NCS.NM_SEQ
/

create sequence NCS.NM_SEQ
increment by 1
start with 257235008
 maxvalue 99999999999
 nominvalue
cycle
 nocache
noorder
/

create sequence POLICY_EVENT_RULE_VER_SEQ
start with 1
increment by 1
/

create sequence POLICY_TEMPLATE_VERSION_SEQ
start with 1
increment by 1
/

/*==============================================================*/
/* Table: BK_SNMP_EVENTS_PROCESS                                */
/*==============================================================*/
create table NCS.BK_SNMP_EVENTS_PROCESS  (
   BK_ID                NUMBER                          not null,
   BK_TIME              TIMESTAMP,
   MARK                 VARCHAR2(255),
   MANUFACTURE          VARCHAR2(30),
   RESULTLIST           VARCHAR2(255),
   WARNMESSAGE          VARCHAR2(255),
   SUMMARY              VARCHAR2(255),
   constraint PK_BK_SNMP_EVENTS_PROCESS primary key (BK_ID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

--WARNING: The following insert order will not restore columns: WARNMESSAGE
insert into NCS.BK_SNMP_EVENTS_PROCESS (BK_ID, MARK, MANUFACTURE, RESULTLIST, SUMMARY)
select BK_ID, MARK, MANUFACTURE, RESULTLIST, SUMMARY
from NCS."tmp_BK_SNMP_EVENTS_PROCESS"
/

/*==============================================================*/
/* Table: BK_SYSLOG_EVENTS_PROCESS                              */
/*==============================================================*/
create table NCS.BK_SYSLOG_EVENTS_PROCESS  (
   BK_ID                NUMBER                          not null,
   BK_TIME              TIMESTAMP,
   MARK                 VARCHAR2(255),
   VARLIST              VARCHAR2(255),
   BTIMELIST            VARCHAR2(62),
   ETIMELIST            VARCHAR2(62),
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER,
   SEVERITY1            NUMBER,
   SEVERITY2            NUMBER,
   PORT                 VARCHAR2(30),
   NOTCAREFLAG          NUMBER,
   TYPE                 NUMBER,
   EVENTTYPE            NUMBER,
   SUBEVENTTYPE         NUMBER,
   ALERTGROUP           VARCHAR2(100),
   ALERTKEY             VARCHAR2(100),
   SUMMARYCN            VARCHAR2(1024),
   PROCESSSUGGEST       VARCHAR2(1024),
   STATUS               VARCHAR2(100),
   ATTENTIONFLAG        NUMBER,
   EVENTS               VARCHAR2(255),
   MANUFACTURE          VARCHAR2(30),
   ORIGEVENT            VARCHAR2(1024),
   constraint PK_BK_SYSLOG_EVENTS_PROCESS primary key (BK_ID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.BK_SYSLOG_EVENTS_PROCESS (BK_ID, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT)
select BK_ID, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT
from NCS."tmp_BK_SYSLOG_EVENTS_PROCESS"
/

/*==============================================================*/
/* Table: BK_SYSLOG_EVENTS_PROCESS_NS                           */
/*==============================================================*/
create table NCS.BK_SYSLOG_EVENTS_PROCESS_NS  (
   BK_ID                NUMBER                          not null,
   BK_TIME              TIMESTAMP,
   MARK                 VARCHAR2(255),
   VARLIST              VARCHAR2(255),
   BTIMELIST            VARCHAR2(62),
   ETIMELIST            VARCHAR2(62),
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER,
   SEVERITY1            NUMBER,
   SEVERITY2            NUMBER,
   PORT                 VARCHAR2(30),
   NOTCAREFLAG          NUMBER,
   TYPE                 NUMBER,
   EVENTTYPE            NUMBER,
   SUBEVENTTYPE         NUMBER,
   ALERTGROUP           VARCHAR2(100),
   ALERTKEY             VARCHAR2(100),
   SUMMARYCN            VARCHAR2(255),
   PROCESSSUGGEST       VARCHAR2(255),
   STATUS               VARCHAR2(100),
   ATTENTIONFLAG        NUMBER,
   EVENTS               VARCHAR2(255),
   MANUFACTURE          VARCHAR2(30),
   ORIGEVENT            VARCHAR2(1024),
   constraint PK_BK_SYSLOG_EVENTS_PROCESS_NS primary key (BK_ID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.BK_SYSLOG_EVENTS_PROCESS_NS (BK_ID, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT)
select BK_ID, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT
from NCS."tmp_BK_SYSLOG_EVENTS_PROCESS_N"
/

/*==============================================================*/
/* Table: ICMP_THRESHOLDS                                       */
/*==============================================================*/
create table NCS.ICMP_THRESHOLDS  (
   IPADDRESS            VARCHAR2(20)                    not null,
   BTIME                VARCHAR2(62),
   ETIME                VARCHAR2(62),
   THRESHOLD            VARCHAR2(50),
   COMPARETYPE          VARCHAR2(50),
   SEVERITY1            VARCHAR2(20),
   SEVERITY2            VARCHAR2(20),
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER,
   VARLIST              VARCHAR2(20),
   SUMMARYCN            VARCHAR2(255),
   constraint PK_ICMP_THRESHOLDS primary key (IPADDRESS)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on table NCS.ICMP_THRESHOLDS is
'from icmp_threhold_v2 excel'
/

insert into NCS.ICMP_THRESHOLDS (IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN)
select IPADDRESS, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2, VARLIST, SUMMARYCN
from NCS."tmp_ICMP_THRESHOLDS"
/

/*==============================================================*/
/* Table: LINES_EVENTS_NOTCARE                                  */
/*==============================================================*/
create table NCS.LINES_EVENTS_NOTCARE  (
   LINESNOTCARE         VARCHAR2(255)                   not null,
   constraint PK_LINES_EVENTS_NOTCARE primary key (LINESNOTCARE)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.LINES_EVENTS_NOTCARE (LINESNOTCARE)
select LINESNOTCARE
from NCS."tmp_LINES_EVENTS_NOTCARE"
/

/*==============================================================*/
/* Table: POLICY_SYSLOG                                         */
/*==============================================================*/
create table NCS.POLICY_SYSLOG  (
   SPID                 NUMBER                          not null,
   MPID                 NUMBER,
   MARK                 VARCHAR2(255),
   MANUFACTURE          VARCHAR2(30),
   EVENTTYPE            NUMBER,
   SEVERITY1            NUMBER,
   SEVERITY2            NUMBER,
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER,
   constraint PK_POLICY_SYSLOG primary key (SPID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint AK_KEY_2_POLICY_S unique (MARK, MANUFACTURE, MPID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.POLICY_SYSLOG (SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2)
select SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2
from NCS."tmp_POLICY_SYSLOG"
/

/*==============================================================*/
/* Table: PREDEFMIB_INFO                                        */
/*==============================================================*/
create table NCS.PREDEFMIB_INFO  (
   PDMID                NUMBER                          not null,
   PTID                 NUMBER,
   DEVID                NUMBER,
   MID                  NUMBER,
   OIDINDEX             VARCHAR2(255),
   OIDNAME              VARCHAR2(255),
   constraint PK_PREDEFMIB_INFO primary key (PDMID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.PREDEFMIB_INFO (PDMID, DEVID, MID, OIDINDEX, OIDNAME)
select PDMID, DEVID, MID, OIDINDEX, OIDNAME
from NCS."tmp_PREDEFMIB_INFO"
/

/*==============================================================*/
/* Table: SNMP_EVENTS_PROCESS                                   */
/*==============================================================*/
create table NCS.SNMP_EVENTS_PROCESS  (
   MARK                 VARCHAR2(255)                   not null,
   MANUFACTURE          VARCHAR2(30),
   RESULTLIST           VARCHAR2(255),
   WARNMESSAGE          VARCHAR2(255),
   SUMMARY              VARCHAR2(255),
   constraint PK_SNMP_EVENTS_PROCESS primary key (MARK)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

--WARNING: The following insert order will not restore columns: WARNMESSAGE
insert into NCS.SNMP_EVENTS_PROCESS (MARK, MANUFACTURE, RESULTLIST, SUMMARY)
select MARK, MANUFACTURE, RESULTLIST, SUMMARY
from NCS."tmp_SNMP_EVENTS_PROCESS"
/

/*==============================================================*/
/* Table: SNMP_THRESHOLDS                                       */
/*==============================================================*/
create table NCS.SNMP_THRESHOLDS  (
   PERFORMANCE          VARCHAR2(255)                   not null,
   BTIME                VARCHAR2(62),
   ETIME                VARCHAR2(62),
   THRESHOLD            VARCHAR2(50),
   COMPARETYPE          VARCHAR2(50),
   SEVERITY1            VARCHAR2(50),
   SEVERITY2            VARCHAR2(50),
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER,
   constraint PK_SNMP_THRESHOLDS primary key (PERFORMANCE)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.SNMP_THRESHOLDS (PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2)
select PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2
from NCS."tmp_SNMP_THRESHOLDS"
/

/*==============================================================*/
/* Table: SYSLOG_EVENTS_PROCESS                                 */
/*==============================================================*/
create table NCS.SYSLOG_EVENTS_PROCESS  (
   MARK                 VARCHAR2(255)                   not null,
   VARLIST              VARCHAR2(255),
   BTIMELIST            VARCHAR2(62),
   ETIMELIST            VARCHAR2(62),
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER,
   SEVERITY1            NUMBER,
   SEVERITY2            NUMBER,
   PORT                 VARCHAR2(30),
   NOTCAREFLAG          NUMBER,
   TYPE                 NUMBER,
   EVENTTYPE            NUMBER,
   SUBEVENTTYPE         NUMBER,
   ALERTGROUP           VARCHAR2(100),
   ALERTKEY             VARCHAR2(100),
   SUMMARYCN            VARCHAR2(255),
   PROCESSSUGGEST       VARCHAR2(255),
   STATUS               VARCHAR2(100),
   ATTENTIONFLAG        NUMBER,
   EVENTS               VARCHAR2(255),
   MANUFACTURE          VARCHAR2(30)                    not null,
   ORIGEVENT            VARCHAR2(1024),
   constraint PK_SYSLOG_EVENTS_PROCESS primary key (MARK, MANUFACTURE)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint AK_KEY_2_SYSLOG_E2 unique (EVENTS, MANUFACTURE)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

--WARNING: The following insert order will not restore columns: SUMMARYCN, PROCESSSUGGEST
insert into NCS.SYSLOG_EVENTS_PROCESS (MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT)
select MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT
from NCS."tmp_SYSLOG_EVENTS_PROCESS"
/

/*==============================================================*/
/* Table: SYSLOG_EVENTS_PROCESS_NS                              */
/*==============================================================*/
create table NCS.SYSLOG_EVENTS_PROCESS_NS  (
   MARK                 VARCHAR2(255)                   not null,
   VARLIST              VARCHAR2(255),
   BTIMELIST            VARCHAR2(62),
   ETIMELIST            VARCHAR2(62),
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER,
   SEVERITY1            NUMBER,
   SEVERITY2            NUMBER,
   PORT                 VARCHAR2(30),
   NOTCAREFLAG          NUMBER,
   TYPE                 NUMBER,
   EVENTTYPE            NUMBER,
   SUBEVENTTYPE         NUMBER,
   ALERTGROUP           VARCHAR2(100),
   ALERTKEY             VARCHAR2(100),
   SUMMARYCN            VARCHAR2(255),
   PROCESSSUGGEST       VARCHAR2(255),
   STATUS               VARCHAR2(100),
   ATTENTIONFLAG        NUMBER,
   EVENTS               VARCHAR2(255),
   MANUFACTURE          VARCHAR2(30)                    not null,
   ORIGEVENT            VARCHAR2(1024),
   constraint PK_SYSLOG_EVENTS_PROCESS_NS primary key (MARK, MANUFACTURE)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint AK_KEY_2_SYSLOG_E unique (EVENTS, MANUFACTURE)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.SYSLOG_EVENTS_PROCESS_NS (MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT)
select MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT
from NCS."tmp_SYSLOG_EVENTS_PROCESS_NS"
/

/*==============================================================*/
/* Table: T_CATEGORY_MAP_INIT                                   */
/*==============================================================*/
create table NCS.T_CATEGORY_MAP_INIT  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(255)                   not null,
   FLAG                 VARCHAR2(1)                     not null,
   constraint SYS_C0011084 unique (ID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on table NCS.T_CATEGORY_MAP_INIT is
'记录设备种类, 所有flag为0的是有效的设备种类'
/

comment on column NCS.T_CATEGORY_MAP_INIT.FLAG is
'为0的是有效的设备种类'
/

insert into NCS.T_CATEGORY_MAP_INIT (ID, NAME, FLAG)
select ID, NAME, FLAG
from NCS."tmp_T_CATEGORY_MAP_INIT"
/

/*==============================================================*/
/* Table: T_DEVICE_INFO                                         */
/*==============================================================*/
create table NCS.T_DEVICE_INFO  (
   DEVID                NUMBER                          not null,
   DEVIP                VARCHAR2(15)                    not null,
   IPDECODE             NUMBER                          not null,
   SYSNAME              VARCHAR2(64),
   SYSNAMEALIAS         VARCHAR2(64),
   RSNO                 VARCHAR2(255),
   SRID                 NUMBER,
   ADMIN                VARCHAR2(64)                   default 'N/A',
   PHONE                VARCHAR2(64)                   default 'N/A',
   MRID                 NUMBER,
   DTID                 NUMBER,
   SERIALID             VARCHAR2(255),
   SWVERSION            VARCHAR2(255),
   RAMSIZE              NUMBER,
   RAMUNIT              VARCHAR2(2),
   NVRAMSIZE            NUMBER,
   NVRAMUNIT            VARCHAR2(2),
   FLASHSIZE            NUMBER,
   FLASHUNIT            VARCHAR2(2),
   FLASHFILENAME        VARCHAR2(4000),
   FLASHFILESIZE        VARCHAR2(1500),
   RCOMMUNITY           VARCHAR2(64),
   WCOMMUNITY           VARCHAR2(64),
   DESCRIPTION          VARCHAR2(400),
   DOMAINID             NUMBER,
   SNMPVERSION          VARCHAR2(10),
   constraint SYS_C0011088 primary key (DEVID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint AK_KEY_3_T_DEVICE unique (DEVIP)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011090 unique (SYSNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_DEVICE_INFO (DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION)
select DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION
from NCS."tmp_T_DEVICE_INFO"
/

/*==============================================================*/
/* Table: T_DEVICE_TYPE_INIT                                    */
/*==============================================================*/
create table NCS.T_DEVICE_TYPE_INIT  (
   MRID                 NUMBER                          not null,
   DTID                 NUMBER                          not null,
   CATEGORY             NUMBER,
   SUBCATEGORY          VARCHAR2(64),
   MODEL                VARCHAR2(64),
   OBJECTID             VARCHAR2(255),
   LOGO                 VARCHAR2(64),
   DESCRIPTION          VARCHAR2(400),
   constraint SYS_C0011093 primary key (DTID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011094 unique (OBJECTID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on column NCS.T_DEVICE_TYPE_INIT.MRID is
'????ID'
/

comment on column NCS.T_DEVICE_TYPE_INIT.SUBCATEGORY is
'????????????????Cisco 2600????, ??????????, ??????????'
/

insert into NCS.T_DEVICE_TYPE_INIT (MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION)
select MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION
from NCS."tmp_T_DEVICE_TYPE_INIT"
/

/*==============================================================*/
/* Table: T_DEVPOL_MAP                                          */
/*==============================================================*/
create table NCS.T_DEVPOL_MAP  (
   DEVID                NUMBER                          not null,
   MPID                 NUMBER,
   PPID                 NUMBER,
   constraint SYS_C0011096 primary key (DEVID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_DEVPOL_MAP (DEVID, MPID, PPID)
select DEVID, MPID, PPID
from NCS."tmp_T_DEVPOL_MAP"
/

/*==============================================================*/
/* Table: T_EVENT_OID_INIT                                      */
/*==============================================================*/
create table NCS.T_EVENT_OID_INIT  (
   EVEID                NUMBER,
   OIDGROUPNAME         VARCHAR2(255),
   MRID                 NUMBER                          not null,
   DTID                 NUMBER                          not null,
   MODID                NUMBER                          not null,
   OID                  VARCHAR2(255),
   UNIT                 VARCHAR2(32),
   DESCRIPTION          VARCHAR2(400)
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on table NCS.T_EVENT_OID_INIT is
'性能参数关联数据，为特定的设备类型指派性能指标和OIDGroup的关系，三个关键的字段为：DTID(设备类型),EVEID(性能指标),OIDGroupName(OIDGroup).
原有表中如下2个字段冗余，建议去除：
1.MRID表示设备类型对应的厂商，可以通过设备类型关联查找
2.MODID表示性能指标的类型（SNMP等），可以通过性能指标关联查找
'
/

comment on column NCS.T_EVENT_OID_INIT.EVEID is
'????????ID'
/

comment on column NCS.T_EVENT_OID_INIT.DTID is
'设备类型ID'
/

comment on column NCS.T_EVENT_OID_INIT.MODID is
'性能参数的类型（SNMP）,此字段冗余，建议删除'
/

insert into NCS.T_EVENT_OID_INIT (EVEID, OIDGROUPNAME, MRID, DTID, MODID, OID, UNIT, DESCRIPTION)
select EVEID, OIDGROUPNAME, MRID, DTID, MODID, OID, UNIT, DESCRIPTION
from NCS."tmp_T_EVENT_OID_INIT"
/

/*==============================================================*/
/* Table: T_EVENT_TYPE_INIT                                     */
/*==============================================================*/
create table NCS.T_EVENT_TYPE_INIT  (
   MODID                NUMBER                          not null,
   EVEID                NUMBER                          not null,
   ETID                 NUMBER,
   ESTID                NUMBER,
   EVEOTHERNAME         VARCHAR2(100),
   ECODE                NUMBER                          not null,
   GENERAL              NUMBER,
   MAJOR                VARCHAR2(64),
   MINOR                VARCHAR2(64),
   OTHER                VARCHAR2(64),
   DESCRIPTION          VARCHAR2(400),
   USEFLAG              VARCHAR2(1)                    default '1',
   constraint SYS_C0011103 primary key (EVEID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011104 unique (MAJOR, MINOR, OTHER)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on table NCS.T_EVENT_TYPE_INIT is
'性能指标，由MODID表示SNMP, 由general表示private或public'
/

comment on column NCS.T_EVENT_TYPE_INIT.MODID is
'指标类别（或称为模式类别），表示当前指标的类别，如: snmp,icmp等'
/

comment on column NCS.T_EVENT_TYPE_INIT.GENERAL is
'-1表示SNMP Private, 0表示SNMP Public'
/

insert into NCS.T_EVENT_TYPE_INIT (MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG)
select MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG
from NCS."tmp_T_EVENT_TYPE_INIT"
/

/*==============================================================*/
/* Table: T_GRP_NET                                             */
/*==============================================================*/
create table NCS.T_GRP_NET  (
   GID                  NUMBER                          not null,
   GNAME                VARCHAR2(255)                   not null,
   SUPID                NUMBER,
   LEVELS               NUMBER                          not null,
   DESCRIPTION          VARCHAR2(400),
   UNMALLOCEDIPSETFLAG  VARCHAR2(1)                    default '0',
   constraint SYS_C0011109 primary key (GID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011110 unique (GNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_GRP_NET (GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG)
select GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG
from NCS."tmp_T_GRP_NET"
/

/*==============================================================*/
/* Table: T_LINEPOL_MAP                                         */
/*==============================================================*/
create table NCS.T_LINEPOL_MAP  (
   PTID                 NUMBER                          not null,
   MPID                 NUMBER,
   PPID                 NUMBER,
   MCODE                NUMBER,
   FLAG                 NUMBER(1),
   constraint SYS_C0011112 primary key (PTID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_LINEPOL_MAP (PTID, MPID, PPID, MCODE, FLAG)
select PTID, MPID, PPID, MCODE, FLAG
from NCS."tmp_T_LINEPOL_MAP"
/

/*==============================================================*/
/* Table: T_LIST_IP                                             */
/*==============================================================*/
create table NCS.T_LIST_IP  (
   GID                  NUMBER                          not null,
   CATEGORY             NUMBER                          not null,
   IP                   VARCHAR2(15)                    not null,
   MASK                 VARCHAR2(15)                    not null,
   IPDECODE_MIN         NUMBER                          not null,
   IPDECODE_MAX         NUMBER                          not null,
   DESCRIPTION          VARCHAR2(400)
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_LIST_IP (GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION)
select GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION
from NCS."tmp_T_LIST_IP"
/

/*==============================================================*/
/* Table: T_MANUFACTURER_INFO_INIT                              */
/*==============================================================*/
create table NCS.T_MANUFACTURER_INFO_INIT  (
   MRID                 NUMBER                          not null,
   MRNAME               VARCHAR2(64)                    not null,
   OBJECTID             VARCHAR2(255),
   DESCRIPTION          VARCHAR2(400),
   constraint SYS_C0011121 primary key (MRID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint AK_MRNAME unique (MRNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint AK_OBJID unique (OBJECTID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_MANUFACTURER_INFO_INIT (MRID, MRNAME, OBJECTID, DESCRIPTION)
select MRID, MRNAME, OBJECTID, DESCRIPTION
from NCS."tmp_T_MANUFACTURER_INFO_INIT"
/

/*==============================================================*/
/* Table: T_MODULE_INFO_INIT                                    */
/*==============================================================*/
create table NCS.T_MODULE_INFO_INIT  (
   MODID                NUMBER                          not null,
   MNAME                VARCHAR2(64)                    not null,
   MCODE                NUMBER                          not null,
   DESCRIPTION          VARCHAR2(400),
   constraint SYS_C0011127 primary key (MODID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011128 unique (MNAME, MCODE)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on table NCS.T_MODULE_INFO_INIT is
'模式类别，如：snmp, icmp等'
/

insert into NCS.T_MODULE_INFO_INIT (MODID, MNAME, MCODE, DESCRIPTION)
select MODID, MNAME, MCODE, DESCRIPTION
from NCS."tmp_T_MODULE_INFO_INIT"
/

/*==============================================================*/
/* Table: T_OIDGROUP_DETAILS_INIT                               */
/*==============================================================*/
create table NCS.T_OIDGROUP_DETAILS_INIT  (
   OPID                 NUMBER                          not null,
   OIDVALUE             VARCHAR2(255)                   not null,
   OIDNAME              VARCHAR2(200)                   not null,
   OIDUNIT              VARCHAR2(32)                    not null,
   FLAG                 VARCHAR2(1)                    default '1' not null,
   OIDINDEX             NUMBER,
   constraint SYS_C0011134 primary key (OPID, OIDNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on column NCS.T_OIDGROUP_DETAILS_INIT.OIDVALUE is
'OID'
/

comment on column NCS.T_OIDGROUP_DETAILS_INIT.OIDNAME is
'OID????'
/

insert into NCS.T_OIDGROUP_DETAILS_INIT (OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX)
select OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX
from NCS."tmp_T_OIDGROUP_DETAILS_INIT"
/

/*==============================================================*/
/* Table: T_OIDGROUP_INFO_INIT                                  */
/*==============================================================*/
create table NCS.T_OIDGROUP_INFO_INIT  (
   OPID                 NUMBER                          not null,
   OIDGROUPNAME         VARCHAR2(255)                   not null,
   OTYPE                NUMBER                          not null,
   DESCRIPTION          VARCHAR2(64),
   constraint SYS_C0011138 primary key (OPID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011139 unique (OIDGROUPNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on table NCS.T_OIDGROUP_INFO_INIT is
'OID Group数据，由OType字段表示类别：设备、线路/端口或MID Index'
/

comment on column NCS.T_OIDGROUP_INFO_INIT.OTYPE is
'1表示设备类
2表示端口类'
/

insert into NCS.T_OIDGROUP_INFO_INIT (OPID, OIDGROUPNAME, OTYPE, DESCRIPTION)
select OPID, OIDGROUPNAME, OTYPE, DESCRIPTION
from NCS."tmp_T_OIDGROUP_INFO_INIT"
/

/*==============================================================*/
/* Table: T_POLICY_BASE                                         */
/*==============================================================*/
create table NCS.T_POLICY_BASE  (
   MPID                 NUMBER                          not null,
   PTID                 NUMBER,
   PT_VERSION           VARCHAR(64),
   MPNAME               VARCHAR2(64)                    not null,
   CATEGORY             NUMBER                          not null,
   DESCRIPTION          VARCHAR2(400),
   constraint SYS_C0011143 primary key (MPID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011144 unique (MPNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on column NCS.T_POLICY_BASE.MPNAME is
'Policy Name'
/

comment on column NCS.T_POLICY_BASE.CATEGORY is
'1  - Device Policy
4  - Port Policy
9  - PriMIB Policy
8  - '
/

insert into NCS.T_POLICY_BASE (MPID, MPNAME, CATEGORY, DESCRIPTION)
select MPID, MPNAME, CATEGORY, DESCRIPTION
from NCS."tmp_T_POLICY_BASE"
/

/*==============================================================*/
/* Table: T_POLICY_DETAILS                                      */
/*==============================================================*/
create table NCS.T_POLICY_DETAILS  (
   MPID                 NUMBER                          not null,
   MODID                NUMBER                          not null,
   EVEID                NUMBER                          not null,
   POLL                 NUMBER                         default 60,
   VALUE_1              VARCHAR2(32)                   default 'N/A',
   SEVERITY_1           NUMBER,
   FILTER_A             VARCHAR2(1),
   VALUE_2              VARCHAR2(32)                   default 'N/A',
   SEVERITY_2           NUMBER,
   FILTER_B             VARCHAR2(1),
   SEVERITY_A           NUMBER,
   SEVERITY_B           NUMBER,
   OIDGROUP             VARCHAR2(255),
   OGFLAG               VARCHAR2(1)                    default '0',
   VALUE_1_LOW          VARCHAR2(32)                   default '0',
   VALUE_2_LOW          VARCHAR2(32)                   default '0',
   V1L_SEVERITY_1       NUMBER,
   V1L_SEVERITY_A       NUMBER,
   V2L_SEVERITY_2       NUMBER,
   V2L_SEVERITY_B       NUMBER,
   COMPARETYPE          VARCHAR2(20),
   constraint SYS_C0011148 primary key (MPID, MODID, EVEID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

comment on column NCS.T_POLICY_DETAILS.MODID is
'????????????????????????eveid????????????????????????SNMP, SysLog????'
/

comment on column NCS.T_POLICY_DETAILS.POLL is
'Poll??????????'
/

comment on column NCS.T_POLICY_DETAILS.VALUE_1 is
'????1'
/

comment on column NCS.T_POLICY_DETAILS.SEVERITY_1 is
'????1????????'
/

comment on column NCS.T_POLICY_DETAILS.VALUE_2 is
'????2'
/

comment on column NCS.T_POLICY_DETAILS.SEVERITY_2 is
'????2????????'
/

insert into NCS.T_POLICY_DETAILS (MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE)
select MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE
from NCS."tmp_T_POLICY_DETAILS"
/

/*==============================================================*/
/* Table: T_POLICY_EVENT_RULE                                   */
/*==============================================================*/
create table T_POLICY_EVENT_RULE  (
   PTID                 NUMBER,
   PT_VERSION           VARCHAR(64),
   EVEID                NUMBER,
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
)
/

comment on column T_POLICY_EVENT_RULE.MODID is
'????????????????????????eveid????????????????????????SNMP, SysLog????'
/

comment on column T_POLICY_EVENT_RULE.POLL is
'Poll??????????'
/

comment on column T_POLICY_EVENT_RULE.VALUE_1 is
'????1'
/

comment on column T_POLICY_EVENT_RULE.VALUE_1_RULE is
'????1'
/

comment on column T_POLICY_EVENT_RULE.SEVERITY_1 is
'????1????????'
/

comment on column T_POLICY_EVENT_RULE.VALUE_2 is
'????2'
/

comment on column T_POLICY_EVENT_RULE.VALUE_2_RULE is
'????2'
/

comment on column T_POLICY_EVENT_RULE.SEVERITY_2 is
'????2????????'
/

/*==============================================================*/
/* Table: T_POLICY_PERIOD                                       */
/*==============================================================*/
create table NCS.T_POLICY_PERIOD  (
   PPID                 NUMBER                          not null,
   PPNAME               VARCHAR2(64)                    not null,
   START_TIME           DATE                            not null,
   END_TIME             DATE                            not null,
   DESCRIPTION          VARCHAR2(400),
   WORKDAY              VARCHAR2(7),
   ENABLED              VARCHAR2(1)                    default '1' not null,
   DEFAULTFLAG          VARCHAR2(1)                    default '0' not null,
   S1                   NUMBER                         default 0,
   S2                   NUMBER                         default 0,
   S3                   NUMBER                         default 0,
   S4                   NUMBER                         default 0,
   S5                   NUMBER                         default 0,
   S6                   NUMBER                         default 0,
   S7                   NUMBER                         default 0,
   E1                   NUMBER                         default 86399,
   E2                   NUMBER                         default 86399,
   E3                   NUMBER                         default 86399,
   E4                   NUMBER                         default 86399,
   E5                   NUMBER                         default 86399,
   E6                   NUMBER                         default 86399,
   E7                   NUMBER                         default 86399,
   BTIME                VARCHAR2(64)                   default '00:00:00|00:00:00|00:00:00|00:00:00|00:00:00|00:00:00|00:00:00',
   ETIME                VARCHAR2(64)                   default '23:59:59|23:59:59|23:59:59|23:59:59|23:59:59|23:59:59|23:59:59',
   constraint SYS_C0011155 primary key (PPID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011156 unique (PPNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_POLICY_PERIOD (PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME)
select PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME
from NCS."tmp_T_POLICY_PERIOD"
/

/*==============================================================*/
/* Table: T_POLICY_PUBLISH_INFO                                 */
/*==============================================================*/
create table T_POLICY_PUBLISH_INFO  (
   PPIID                NUMBER                          not null,
   VERSION              VARCHAR2(64 BYTE)               not null,
   VERSION_TAG          VARCHAR2(128 BYTE)              not null,
   DESCRIPTION          VARCHAR2(512 BYTE),
   PUBLISH_TIME         TIMESTAMP                       not null,
   constraint PK_T_POLICY_PUBLISH_INFO primary key (PPIID)
)
/

/*==============================================================*/
/* Table: T_POLICY_TEMPLATE                                     */
/*==============================================================*/
create table T_POLICY_TEMPLATE  (
   PTID                 NUMBER                          not null,
   PT_VERSION           VARCHAR(64)                     not null,
   PPIID                NUMBER,
   MPNAME               VARCHAR2(64)                    not null,
   STATUS               CHAR                            not null,
   CATEGORY             NUMBER                          not null,
   DESCRIPTION          VARCHAR2(400),
   constraint PK_T_POLICY_TEMPLATE primary key (PTID, PT_VERSION)
)
/

comment on column T_POLICY_TEMPLATE.MPNAME is
'Policy Name'
/

comment on column T_POLICY_TEMPLATE.STATUS is
'R - Released and used
D - Draft stage
U - Unused, history version'
/

comment on column T_POLICY_TEMPLATE.CATEGORY is
'1  - Device Policy
4  - Port Policy
9  - PriMIB Policy
8  - '
/

/*==============================================================*/
/* Table: T_POLICY_TEMPLATE_SCOPE                               */
/*==============================================================*/
create table T_POLICY_TEMPLATE_SCOPE  (
   PTID                 NUMBER                          not null,
   PT_VERSION           VARCHAR(64)                     not null,
   DTID                 NUMBER                          not null,
   MRID                 NUMBER,
   constraint PK_T_POLICY_TEMPLATE_SCOPE primary key (PTID, PT_VERSION, DTID)
)
/

/*==============================================================*/
/* Table: T_PORT_INFO                                           */
/*==============================================================*/
create table NCS.T_PORT_INFO  (
   PTID                 NUMBER                          not null,
   DEVID                NUMBER                          not null,
   IFINDEX              NUMBER,
   IFIP                 VARCHAR2(15),
   IPDECODE_IFIP        NUMBER,
   IFMAC                VARCHAR2(20),
   IFOPERSTATUS         VARCHAR2(4),
   IFDESCR              VARCHAR2(255)                   not null,
   DESCRIPTION          VARCHAR2(400),
   constraint SYS_C0011160 primary key (PTID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_PORT_INFO (PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION)
select PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION
from NCS."tmp_T_PORT_INFO"
/

/*==============================================================*/
/* Table: T_PP_DEV                                              */
/*==============================================================*/
create table NCS.T_PP_DEV  (
   DEVIP                VARCHAR2(15)                    not null,
   BTIME                VARCHAR2(64),
   ETIME                VARCHAR2(64),
   constraint PK_T_PP_DEV primary key (DEVIP)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_PP_DEV (DEVIP, BTIME, ETIME)
select DEVIP, BTIME, ETIME
from NCS."tmp_T_PP_DEV"
/

/*==============================================================*/
/* Table: T_PP_PORT                                             */
/*==============================================================*/
create table NCS.T_PP_PORT  (
   DEVIP                VARCHAR2(15)                    not null,
   IFDESCR              VARCHAR2(255)                   not null,
   BTIME                VARCHAR2(64),
   ETIME                VARCHAR2(64),
   constraint PK_T_PP_PORT primary key (DEVIP, IFDESCR)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_PP_PORT (DEVIP, IFDESCR, BTIME, ETIME)
select DEVIP, IFDESCR, BTIME, ETIME
from NCS."tmp_T_PP_PORT"
/

/*==============================================================*/
/* Table: T_ROLE                                                */
/*==============================================================*/
create table T_ROLE  (
   ROLE_ID              NUMBER                          not null,
   ROLE_NAME            VARCHAR(128)                    not null,
   constraint PK_T_ROLE primary key (ROLE_ID)
)
/

/*==============================================================*/
/* Table: T_ROLE_MANAGED_NODE                                   */
/*==============================================================*/
create table T_ROLE_MANAGED_NODE  (
   ROLE_ID              NUMBER                          not null,
   SERVER_ID            NUMBER                          not null,
   constraint PK_T_ROLE_MANAGED_NODE primary key (ROLE_ID, SERVER_ID)
)
/

/*==============================================================*/
/* Table: T_SERVER_INFO                                         */
/*==============================================================*/
create table NCS.T_SERVER_INFO  (
   NMSID                NUMBER                          not null,
   NMSIP                VARCHAR2(15)                    not null,
   NMSNAME              VARCHAR2(64)                    not null,
   USERNAME             VARCHAR2(64),
   PASSWORD             VARCHAR2(64),
   OSTYPE               VARCHAR2(64),
   DESCRIPTION          VARCHAR2(400),
   constraint SYS_C0011169 primary key (NMSID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011170 unique (NMSIP, NMSNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_SERVER_INFO (NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION)
select NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION
from NCS."tmp_T_SERVER_INFO"
/

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
)
/

comment on column T_SERVER_NODE.NODE_TYPE is
'M - 管理节点
O - 操作节点'
/

comment on column T_SERVER_NODE.SERVICE_ENDPOINT is
'Service endpoint information for access this node service interface. This can include JDBC, or WebServices access parameters.'
/

/*==============================================================*/
/* Table: T_SVRMOD_MAP                                          */
/*==============================================================*/
create table NCS.T_SVRMOD_MAP  (
   NMSID                NUMBER                          not null,
   MODID                NUMBER                          not null,
   PATH                 VARCHAR2(255),
   DESCRIPTION          VARCHAR2(400),
   constraint SYS_C0011173 primary key (NMSID, MODID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_SVRMOD_MAP (NMSID, MODID, PATH, DESCRIPTION)
select NMSID, MODID, PATH, DESCRIPTION
from NCS."tmp_T_SVRMOD_MAP"
/

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
)
/

/*==============================================================*/
/* Table: T_USER                                                */
/*==============================================================*/
create table NCS.T_USER  (
   USID                 NUMBER                          not null,
   UNAME                VARCHAR2(64)                    not null,
   PASSWORD             VARCHAR2(64),
   STATUS               VARCHAR2(2)                     not null,
   DESCRIPTION          VARCHAR2(400),
   FULLNAME             VARCHAR64,
   EMAIL                VARCHAR64,
   constraint SYS_C0011177 primary key (USID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging,
   constraint SYS_C0011178 unique (UNAME)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
       tablespace NCS_DB
        nologging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace NCS_DB
nologging
monitoring
 noparallel
/

insert into NCS.T_USER (USID, UNAME, PASSWORD, STATUS, DESCRIPTION)
select USID, UNAME, PASSWORD, STATUS, DESCRIPTION
from NCS."tmp_T_USER"
/

/*==============================================================*/
/* Table: T_USER_ROLE_MAP                                       */
/*==============================================================*/
create table T_USER_ROLE_MAP  (
   USID                 NUMBER                          not null,
   ROLE_ID              NUMBER                          not null,
   constraint PK_T_USER_ROLE_MAP primary key (USID, ROLE_ID)
)
/

/*==============================================================*/
/* View: V_ICMP_DEV_THRESHOLDS                                  */
/*==============================================================*/
create or replace view NCS.V_ICMP_DEV_THRESHOLDS as

with read only
/

/*==============================================================*/
/* View: V_ICMP_PORT_THRESHOLDS                                 */
/*==============================================================*/
create or replace view NCS.V_ICMP_PORT_THRESHOLDS as

with read only
/

/*==============================================================*/
/* View: V_MF_CATE_DEVTYPE                                      */
/*==============================================================*/
create or replace view NCS.V_MF_CATE_DEVTYPE as

with read only
/

/*==============================================================*/
/* View: V_OID_GROUP                                            */
/*==============================================================*/
create or replace view NCS.V_OID_GROUP as

with read only
/

/*==============================================================*/
/* View: V_PERFORM_PARAM                                        */
/*==============================================================*/
create or replace view NCS.V_PERFORM_PARAM as

with read only
/

/*==============================================================*/
/* View: V_PORT_ICMP_POLICY_EXPORT                              */
/*==============================================================*/
create or replace view NCS.V_PORT_ICMP_POLICY_EXPORT as

with read only
/

/*==============================================================*/
/* View: V_PORT_SNMP_POLICY_EXPORT                              */
/*==============================================================*/
create or replace view NCS.V_PORT_SNMP_POLICY_EXPORT as

with read only
/

/*==============================================================*/
/* View: V_PP_DEV                                               */
/*==============================================================*/
create or replace view NCS.V_PP_DEV as

with read only
/

/*==============================================================*/
/* View: V_PP_PORT                                              */
/*==============================================================*/
create or replace view NCS.V_PP_PORT as

with read only
/

/*==============================================================*/
/* View: V_PREDEFMIB_SNMP_POLICY_EXPORT                         */
/*==============================================================*/
create or replace view NCS.V_PREDEFMIB_SNMP_POLICY_EXPORT as

with read only
/

/*==============================================================*/
/* View: V_SNMP_DEV_THRESHOLDS                                  */
/*==============================================================*/
create or replace view NCS.V_SNMP_DEV_THRESHOLDS as

with read only
/

/*==============================================================*/
/* View: V_SNMP_PDM_THRESHOLDS                                  */
/*==============================================================*/
create or replace view NCS.V_SNMP_PDM_THRESHOLDS as

with read only
/

/*==============================================================*/
/* View: V_SNMP_PORT_THRESHOLDS                                 */
/*==============================================================*/
create or replace view NCS.V_SNMP_PORT_THRESHOLDS as

with read only
/

/*==============================================================*/
/* View: V_SRC_TYPE_EXPORT                                      */
/*==============================================================*/
create or replace view NCS.V_SRC_TYPE_EXPORT as

with read only
/

/*==============================================================*/
/* View: V_SYSLOG_DEV_EVENTS_ATTENTION                          */
/*==============================================================*/
create or replace view NCS.V_SYSLOG_DEV_EVENTS_ATTENTION as

with read only
/

/*==============================================================*/
/* View: V_SYSLOG_PORT_EVENTS_ATTENTION                         */
/*==============================================================*/
create or replace view NCS.V_SYSLOG_PORT_EVENTS_ATTENTION as

with read only
/

/*==============================================================*/
/* View: V_SYSLOG_PORT_LINE_NOTCARE                             */
/*==============================================================*/
create or replace view NCS.V_SYSLOG_PORT_LINE_NOTCARE as

with read only
/

alter table NCS.POLICY_SYSLOG
   add constraint FK_POLICY_S_REFERENCE_SYSLOG_2 foreign key (MARK, MANUFACTURE)
      references NCS.SYSLOG_EVENTS_PROCESS_NS (MARK, MANUFACTURE)
/

alter table NCS.POLICY_SYSLOG
   add constraint FK_POLICY_S_REFERENCE_SYSLOG_E foreign key (MARK, MANUFACTURE)
      references NCS.SYSLOG_EVENTS_PROCESS (MARK, MANUFACTURE)
/

alter table NCS.POLICY_SYSLOG
   add constraint FK_POLICY_S_REFERENCE_T_POLICY foreign key (MPID)
      references NCS.T_POLICY_BASE (MPID)
/

alter table NCS.PREDEFMIB_INFO
   add constraint FK_PREDEFMI_REFERENCE_T_PORT_I foreign key (PTID)
      references NCS.T_PORT_INFO (PTID)
/

alter table NCS.PREDEFMIB_INFO
   add constraint FK_PREDEFMI_REFERENCE_DEF_MIB_ foreign key (MID)
      references NCS.DEF_MIB_GRP (MID)
/

alter table NCS.PREDEFMIB_POL_MAP
   add constraint FK_PREDEFMI_REFERENCE_PREDEFMI foreign key (PDMID)
      references NCS.PREDEFMIB_INFO (PDMID)
/

alter table NCS.PREDEFMIB_POL_MAP
   add constraint FK_PREDEFMI_REFERENCE_T_POLIC2 foreign key (PPID)
      references NCS.T_POLICY_PERIOD (PPID)
/

alter table NCS.PREDEFMIB_POL_MAP
   add constraint FK_PREDEFMI_REFERENCE_T_POLICY foreign key (MPID)
      references NCS.T_POLICY_BASE (MPID)
/

alter table NCS.T_DEVICE_INFO
   add constraint FK_T_DEVICE_REFERENCE_T_DEVICE foreign key (DTID)
      references NCS.T_DEVICE_TYPE_INIT (DTID)
/

alter table NCS.T_DEVICE_TYPE_INIT
   add constraint FK_T_DEVICE_REFERENCE_T_MANUFA foreign key (MRID)
      references NCS.T_MANUFACTURER_INFO_INIT (MRID)
/

alter table NCS.T_DEVICE_TYPE_INIT
   add constraint FK_T_DEVICE_REFERENCE_T_CATEGO foreign key (CATEGORY)
      references NCS.T_CATEGORY_MAP_INIT (ID)
/

alter table NCS.T_DEVPOL_MAP
   add constraint FK_T_DEVPOL_REFERENCE_T_POLIC2 foreign key (MPID)
      references NCS.T_POLICY_BASE (MPID)
/

alter table NCS.T_DEVPOL_MAP
   add constraint FK_T_DEVPOL_REFERENCE_T_POLICY foreign key (PPID)
      references NCS.T_POLICY_PERIOD (PPID)
/

alter table NCS.T_DEVPOL_MAP
   add constraint FK_T_DEVPOL_REFERENCE_T_DEVICE foreign key (DEVID)
      references NCS.T_DEVICE_INFO (DEVID)
/

alter table NCS.T_EVENT_OID_INIT
   add constraint FK_T_EVENT__REFERENCE_T_EVENT_ foreign key (EVEID)
      references NCS.T_EVENT_TYPE_INIT (EVEID)
/

alter table NCS.T_EVENT_OID_INIT
   add constraint FK_T_EVENT__REFERENCE_T_OIDGRO foreign key (OIDGROUPNAME)
      references NCS.T_OIDGROUP_INFO_INIT (OIDGROUPNAME)
/

alter table NCS.T_EVENT_OID_INIT
   add constraint FK_T_EVENT__REFERENCE_T_DEVICE foreign key (DTID)
      references NCS.T_DEVICE_TYPE_INIT (DTID)
/

alter table NCS.T_EVENT_TYPE_INIT
   add constraint FK_T_EVENT__REFERENCE_T_MODULE foreign key (MODID)
      references NCS.T_MODULE_INFO_INIT (MODID)
/

alter table NCS.T_GRP_NET
   add constraint FK_T_GRP_NE_REFERENCE_T_GRP_NE foreign key (SUPID)
      references NCS.T_GRP_NET (GID)
/

alter table NCS.T_LINEPOL_MAP
   add constraint FK_T_LINEPO_REFERENCE_T_PORT_I foreign key (PTID)
      references NCS.T_PORT_INFO (PTID)
/

alter table NCS.T_LINEPOL_MAP
   add constraint FK_T_LINEPO_REFERENCE_T_POLIC2 foreign key (MPID)
      references NCS.T_POLICY_BASE (MPID)
/

alter table NCS.T_LINEPOL_MAP
   add constraint FK_T_LINEPO_REFERENCE_T_POLICY foreign key (PPID)
      references NCS.T_POLICY_PERIOD (PPID)
/

alter table NCS.T_LIST_IP
   add constraint FK_T_LIST_I_REFERENCE_T_GRP_NE foreign key (GID)
      references NCS.T_GRP_NET (GID)
/

alter table NCS.T_OIDGROUP_DETAILS_INIT
   add constraint FK_T_OIDGRO_REFERENCE_T_OIDGRO foreign key (OPID)
      references NCS.T_OIDGROUP_INFO_INIT (OPID)
/

alter table NCS.T_POLICY_BASE
   add constraint FK_T_POLICY_REFERENCE_T_POLIC2 foreign key (PTID, PT_VERSION)
      references T_POLICY_TEMPLATE (PTID, PT_VERSION)
/

alter table NCS.T_POLICY_DETAILS
   add constraint FK_T_POLICY_REFERENCE_T_POLIC3 foreign key (MPID)
      references NCS.T_POLICY_BASE (MPID)
/

alter table NCS.T_POLICY_DETAILS
   add constraint FK_T_POLICY_REFERENCE_T_EVENT2 foreign key (EVEID)
      references NCS.T_EVENT_TYPE_INIT (EVEID)
/

alter table T_POLICY_EVENT_RULE
   add constraint FK_T_POLICY_REFERENCE_T_POLICY foreign key (PTID, PT_VERSION)
      references T_POLICY_TEMPLATE (PTID, PT_VERSION)
/

alter table T_POLICY_EVENT_RULE
   add constraint FK_T_POLICY_REFERENCE_T_EVENT_ foreign key (EVEID)
      references NCS.T_EVENT_TYPE_INIT (EVEID)
/

alter table T_POLICY_TEMPLATE
   add constraint FK_T_POLICY_REFERENCE_T_POLIC4 foreign key (PPIID)
      references T_POLICY_PUBLISH_INFO (PPIID)
/

alter table T_POLICY_TEMPLATE_SCOPE
   add constraint FK_T_POLICY_REFERENCE_T_POLIC5 foreign key (PTID, PT_VERSION)
      references T_POLICY_TEMPLATE (PTID, PT_VERSION)
/

alter table T_POLICY_TEMPLATE_SCOPE
   add constraint FK_T_POLICY_REFERENCE_T_DEVICE foreign key (DTID)
      references NCS.T_DEVICE_TYPE_INIT (DTID)
/

alter table T_POLICY_TEMPLATE_SCOPE
   add constraint FK_T_POLICY_REFERENCE_T_MANUFA foreign key (MRID)
      references NCS.T_MANUFACTURER_INFO_INIT (MRID)
/

alter table NCS.T_PORT_INFO
   add constraint FK_T_PORT_I_REFERENCE_T_DEVICE foreign key (DEVID)
      references NCS.T_DEVICE_INFO (DEVID)
/

alter table T_ROLE_MANAGED_NODE
   add constraint FK_T_ROLE_M_REFERENCE_T_ROLE foreign key (ROLE_ID)
      references T_ROLE (ROLE_ID)
/

alter table T_ROLE_MANAGED_NODE
   add constraint FK_T_ROLE_M_REFERENCE_T_SERVER foreign key (SERVER_ID)
      references T_SERVER_NODE (SERVER_ID)
/

alter table NCS.T_SVRMOD_MAP
   add constraint FK_T_SVRMOD_REFERENCE_T_SERVER foreign key (NMSID)
      references NCS.T_SERVER_INFO (NMSID)
/

alter table NCS.T_SVRMOD_MAP
   add constraint FK_T_SVRMOD_REFERENCE_T_MODULE foreign key (MODID)
      references NCS.T_MODULE_INFO_INIT (MODID)
/

alter table T_TAKE_EFFECT_HISTORY
   add constraint FK_T_TAKE_E_REFERENCE_T_USER foreign key (USID)
      references NCS.T_USER (USID)
/

alter table T_TAKE_EFFECT_HISTORY
   add constraint FK_T_TAKE_E_REFERENCE_T_POLICY foreign key (PPIID)
      references T_POLICY_PUBLISH_INFO (PPIID)
/

alter table T_TAKE_EFFECT_HISTORY
   add constraint FK_T_TAKE_E_REFERENCE_T_SERVER foreign key (SERVER_ID)
      references T_SERVER_NODE (SERVER_ID)
/

alter table T_USER_ROLE_MAP
   add constraint FK_T_USER_R_REFERENCE_T_USER foreign key (USID)
      references NCS.T_USER (USID)
/

alter table T_USER_ROLE_MAP
   add constraint FK_T_USER_R_REFERENCE_T_ROLE foreign key (ROLE_ID)
      references T_ROLE (ROLE_ID)
/


create or replace function NCS.SETDEFAULTMANUFACTNAME(name varchar2, general number)
return varchar2 is
begin
  if general=0 then
     return 'General';
  end if;
  if name is null then
     return 'General';
  end if;
  return name;
end setDefaultManufactName;
/

