-------------------------------------------------------
-- Export file for user NCS                          --
-- Created by dccb-zhanghw01 on 2012/11/29, 11:12:54 --
-------------------------------------------------------

spool ncs.log

prompt
prompt Creating table BK_SNMP_EVENTS_PROCESS
prompt =====================================
prompt
create table BK_SNMP_EVENTS_PROCESS
(
  BK_ID       NUMBER not null,
  BK_TIME     TIMESTAMP(6),
  MARK        VARCHAR2(255),
  MANUFACTURE VARCHAR2(30),
  RESULTLIST  VARCHAR2(255),
  WARNMESSAGE VARCHAR2(255),
  SUMMARY     VARCHAR2(255)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table BK_SNMP_EVENTS_PROCESS
  add constraint PK_BK_SNMP_EVENTS_PROCESS primary key (BK_ID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BK_SYSLOG_EVENTS_PROCESS
prompt =======================================
prompt
create table BK_SYSLOG_EVENTS_PROCESS
(
  BK_ID          NUMBER not null,
  BK_TIME        TIMESTAMP(6),
  MARK           VARCHAR2(255),
  VARLIST        VARCHAR2(255),
  BTIMELIST      VARCHAR2(62),
  ETIMELIST      VARCHAR2(62),
  FILTERFLAG1    NUMBER,
  FILTERFLAG2    NUMBER,
  SEVERITY1      NUMBER,
  SEVERITY2      NUMBER,
  PORT           VARCHAR2(30),
  NOTCAREFLAG    NUMBER,
  TYPE           NUMBER,
  EVENTTYPE      NUMBER,
  SUBEVENTTYPE   NUMBER,
  ALERTGROUP     VARCHAR2(100),
  ALERTKEY       VARCHAR2(100),
  SUMMARYCN      VARCHAR2(255),
  PROCESSSUGGEST VARCHAR2(255),
  STATUS         VARCHAR2(100),
  ATTENTIONFLAG  NUMBER,
  EVENTS         VARCHAR2(255),
  MANUFACTURE    VARCHAR2(30),
  ORIGEVENT      VARCHAR2(1024)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table BK_SYSLOG_EVENTS_PROCESS
  add constraint PK_BK_SYSLOG_EVENTS_PROCESS primary key (BK_ID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BK_SYSLOG_EVENTS_PROCESS_NS
prompt ==========================================
prompt
create table BK_SYSLOG_EVENTS_PROCESS_NS
(
  BK_ID          NUMBER not null,
  BK_TIME        TIMESTAMP(6),
  MARK           VARCHAR2(255),
  VARLIST        VARCHAR2(255),
  BTIMELIST      VARCHAR2(62),
  ETIMELIST      VARCHAR2(62),
  FILTERFLAG1    NUMBER,
  FILTERFLAG2    NUMBER,
  SEVERITY1      NUMBER,
  SEVERITY2      NUMBER,
  PORT           VARCHAR2(30),
  NOTCAREFLAG    NUMBER,
  TYPE           NUMBER,
  EVENTTYPE      NUMBER,
  SUBEVENTTYPE   NUMBER,
  ALERTGROUP     VARCHAR2(100),
  ALERTKEY       VARCHAR2(100),
  SUMMARYCN      VARCHAR2(255),
  PROCESSSUGGEST VARCHAR2(255),
  STATUS         VARCHAR2(100),
  ATTENTIONFLAG  NUMBER,
  EVENTS         VARCHAR2(255),
  MANUFACTURE    VARCHAR2(30),
  ORIGEVENT      VARCHAR2(1024)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table BK_SYSLOG_EVENTS_PROCESS_NS
  add constraint PK_BK_SYSLOG_EVENTS_PROCESS_NS primary key (BK_ID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table DEF_MIB_GRP
prompt ==========================
prompt
create table DEF_MIB_GRP
(
  MID      INTEGER not null,
  NAME     VARCHAR2(256),
  INDEXOID VARCHAR2(256),
  INDEXVAR VARCHAR2(256),
  DESCROID VARCHAR2(256),
  DESCRVAR VARCHAR2(256)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table DEF_MIB_GRP
  add constraint PK_DEF_MIB_GRP primary key (MID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table DEF_MIB_GRP
  add constraint AK_KEY_2_DEF_MIB_ unique (NAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table EVENTS_ATTENTION
prompt ===============================
prompt
create table EVENTS_ATTENTION
(
  EVENTSATTENTION VARCHAR2(255) not null,
  SEVERITY        NUMBER,
  PROCESSSUGGEST  VARCHAR2(255)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table EVENTS_ATTENTION
  add constraint PK_EVENTS_ATTENTION primary key (EVENTSATTENTION)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ICMP_THRESHOLDS
prompt ==============================
prompt
create table ICMP_THRESHOLDS
(
  IPADDRESS   VARCHAR2(20) not null,
  BTIME       VARCHAR2(62),
  ETIME       VARCHAR2(62),
  THRESHOLD   VARCHAR2(50),
  COMPARETYPE VARCHAR2(50),
  SEVERITY1   VARCHAR2(20),
  SEVERITY2   VARCHAR2(20),
  FILTERFLAG1 NUMBER,
  FILTERFLAG2 NUMBER,
  VARLIST     VARCHAR2(20),
  SUMMARYCN   VARCHAR2(255)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table ICMP_THRESHOLDS
  is 'from icmp_threhold_v2 excel';
alter table ICMP_THRESHOLDS
  add constraint PK_ICMP_THRESHOLDS primary key (IPADDRESS)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table LINES_EVENTS_NOTCARE
prompt ===================================
prompt
create table LINES_EVENTS_NOTCARE
(
  LINESNOTCARE VARCHAR2(255) not null
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table LINES_EVENTS_NOTCARE
  add constraint PK_LINES_EVENTS_NOTCARE primary key (LINESNOTCARE)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table POLICY_SYSLOG
prompt ============================
prompt
create table POLICY_SYSLOG
(
  SPID        NUMBER not null,
  MPID        NUMBER,
  MARK        VARCHAR2(255),
  MANUFACTURE VARCHAR2(30),
  EVENTTYPE   NUMBER,
  SEVERITY1   NUMBER,
  SEVERITY2   NUMBER,
  FILTERFLAG1 NUMBER,
  FILTERFLAG2 NUMBER
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICY_SYSLOG
  add constraint PK_POLICY_SYSLOG primary key (SPID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICY_SYSLOG
  add constraint AK_KEY_2_POLICY_S unique (MARK, MANUFACTURE, MPID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table PREDEFMIB_INFO
prompt =============================
prompt
create table PREDEFMIB_INFO
(
  PDMID    NUMBER not null,
  DEVID    NUMBER,
  MID      INTEGER,
  OIDINDEX VARCHAR2(255),
  OIDNAME  VARCHAR2(255)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table PREDEFMIB_INFO
  add constraint PK_PREDEFMIB_INFO primary key (PDMID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table PREDEFMIB_POL_MAP
prompt ================================
prompt
create table PREDEFMIB_POL_MAP
(
  PDMID NUMBER not null,
  MPID  NUMBER,
  PPID  NUMBER,
  MCODE NUMBER,
  FLAG  NUMBER(1)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table PREDEFMIB_POL_MAP
  add constraint PK_PREDEFMIB_POL_MAP primary key (PDMID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SNMP_EVENTS_PROCESS
prompt ==================================
prompt
create table SNMP_EVENTS_PROCESS
(
  MARK        VARCHAR2(255) not null,
  MANUFACTURE VARCHAR2(30),
  RESULTLIST  VARCHAR2(255),
  WARNMESSAGE VARCHAR2(255),
  SUMMARY     VARCHAR2(255)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SNMP_EVENTS_PROCESS
  add constraint PK_SNMP_EVENTS_PROCESS primary key (MARK)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SNMP_THRESHOLDS
prompt ==============================
prompt
create table SNMP_THRESHOLDS
(
  PERFORMANCE VARCHAR2(255) not null,
  BTIME       VARCHAR2(62),
  ETIME       VARCHAR2(62),
  THRESHOLD   VARCHAR2(50),
  COMPARETYPE VARCHAR2(50),
  SEVERITY1   VARCHAR2(50),
  SEVERITY2   VARCHAR2(50),
  FILTERFLAG1 NUMBER,
  FILTERFLAG2 NUMBER
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SNMP_THRESHOLDS
  add constraint PK_SNMP_THRESHOLDS primary key (PERFORMANCE)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYSLOG_EVENTS_PROCESS
prompt ====================================
prompt
create table SYSLOG_EVENTS_PROCESS
(
  MARK           VARCHAR2(255) not null,
  VARLIST        VARCHAR2(255),
  BTIMELIST      VARCHAR2(62),
  ETIMELIST      VARCHAR2(62),
  FILTERFLAG1    NUMBER,
  FILTERFLAG2    NUMBER,
  SEVERITY1      NUMBER,
  SEVERITY2      NUMBER,
  PORT           VARCHAR2(30),
  NOTCAREFLAG    NUMBER,
  TYPE           NUMBER,
  EVENTTYPE      NUMBER,
  SUBEVENTTYPE   NUMBER,
  ALERTGROUP     VARCHAR2(100),
  ALERTKEY       VARCHAR2(100),
  SUMMARYCN      VARCHAR2(255),
  PROCESSSUGGEST VARCHAR2(255),
  STATUS         VARCHAR2(100),
  ATTENTIONFLAG  NUMBER,
  EVENTS         VARCHAR2(255),
  MANUFACTURE    VARCHAR2(30) not null,
  ORIGEVENT      VARCHAR2(1024)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYSLOG_EVENTS_PROCESS
  add constraint PK_SYSLOG_EVENTS_PROCESS primary key (MARK, MANUFACTURE)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYSLOG_EVENTS_PROCESS
  add constraint AK_KEY_2_SYSLOG_E2 unique (EVENTS, MANUFACTURE)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYSLOG_EVENTS_PROCESS_NS
prompt =======================================
prompt
create table SYSLOG_EVENTS_PROCESS_NS
(
  MARK           VARCHAR2(255) not null,
  VARLIST        VARCHAR2(255),
  BTIMELIST      VARCHAR2(62),
  ETIMELIST      VARCHAR2(62),
  FILTERFLAG1    NUMBER,
  FILTERFLAG2    NUMBER,
  SEVERITY1      NUMBER,
  SEVERITY2      NUMBER,
  PORT           VARCHAR2(30),
  NOTCAREFLAG    NUMBER,
  TYPE           NUMBER,
  EVENTTYPE      NUMBER,
  SUBEVENTTYPE   NUMBER,
  ALERTGROUP     VARCHAR2(100),
  ALERTKEY       VARCHAR2(100),
  SUMMARYCN      VARCHAR2(255),
  PROCESSSUGGEST VARCHAR2(255),
  STATUS         VARCHAR2(100),
  ATTENTIONFLAG  NUMBER,
  EVENTS         VARCHAR2(255),
  MANUFACTURE    VARCHAR2(30) not null,
  ORIGEVENT      VARCHAR2(1024)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYSLOG_EVENTS_PROCESS_NS
  add constraint PK_SYSLOG_EVENTS_PROCESS_NS primary key (MARK, MANUFACTURE)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYSLOG_EVENTS_PROCESS_NS
  add constraint AK_KEY_2_SYSLOG_E unique (EVENTS, MANUFACTURE)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_CATEGORY_MAP_INIT
prompt ==================================
prompt
create table T_CATEGORY_MAP_INIT
(
  ID   NUMBER not null,
  NAME VARCHAR2(255) not null,
  FLAG VARCHAR2(1) not null
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_CATEGORY_MAP_INIT
  is '记录设备种类, 所有flag为0的是有效的设备种类';
comment on column T_CATEGORY_MAP_INIT.FLAG
  is '为0的是有效的设备种类';
alter table T_CATEGORY_MAP_INIT
  add unique (ID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_DEVICE_INFO
prompt ============================
prompt
create table T_DEVICE_INFO
(
  DEVID         NUMBER not null,
  DEVIP         VARCHAR2(15) not null,
  IPDECODE      NUMBER not null,
  SYSNAME       VARCHAR2(64),
  SYSNAMEALIAS  VARCHAR2(64),
  RSNO          VARCHAR2(255),
  SRID          NUMBER,
  ADMIN         VARCHAR2(64) default 'N/A',
  PHONE         VARCHAR2(64) default 'N/A',
  MRID          NUMBER,
  DTID          NUMBER,
  SERIALID      VARCHAR2(255),
  SWVERSION     VARCHAR2(255),
  RAMSIZE       NUMBER,
  RAMUNIT       VARCHAR2(2),
  NVRAMSIZE     NUMBER,
  NVRAMUNIT     VARCHAR2(2),
  FLASHSIZE     NUMBER,
  FLASHUNIT     VARCHAR2(2),
  FLASHFILENAME VARCHAR2(4000),
  FLASHFILESIZE VARCHAR2(1500),
  RCOMMUNITY    VARCHAR2(64),
  WCOMMUNITY    VARCHAR2(64),
  DESCRIPTION   VARCHAR2(400),
  DOMAINID      NUMBER,
  SNMPVERSION   VARCHAR2(10)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_DEVICE_INFO.SYSNAME
  is '设备名称';
alter table T_DEVICE_INFO
  add primary key (DEVID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_DEVICE_INFO
  add constraint AK_KEY_3_T_DEVICE unique (DEVIP)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_DEVICE_INFO
  add unique (SYSNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_DEVICE_TYPE_INIT
prompt =================================
prompt
create table T_DEVICE_TYPE_INIT
(
  MRID        NUMBER not null,
  DTID        NUMBER not null,
  CATEGORY    NUMBER,
  SUBCATEGORY VARCHAR2(64),
  MODEL       VARCHAR2(64),
  OBJECTID    VARCHAR2(255),
  LOGO        VARCHAR2(64),
  DESCRIPTION VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_DEVICE_TYPE_INIT
  is '设备类型';
comment on column T_DEVICE_TYPE_INIT.MRID
  is '厂商ID';
comment on column T_DEVICE_TYPE_INIT.CATEGORY
  is '设备类别';
comment on column T_DEVICE_TYPE_INIT.SUBCATEGORY
  is '设备子类别，例如Cisco 2600系列, 由用户输入, 非数据字典';
comment on column T_DEVICE_TYPE_INIT.MODEL
  is '设备类别的厂商型号，用户输入';
comment on column T_DEVICE_TYPE_INIT.LOGO
  is '预留，未使用';
comment on column T_DEVICE_TYPE_INIT.DESCRIPTION
  is '描述信息';
alter table T_DEVICE_TYPE_INIT
  add primary key (DTID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_DEVICE_TYPE_INIT
  add unique (OBJECTID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_DEVPOL_MAP
prompt ===========================
prompt
create table T_DEVPOL_MAP
(
  DEVID NUMBER not null,
  MPID  NUMBER,
  PPID  NUMBER
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_DEVPOL_MAP
  add primary key (DEVID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_EVENT_OID_INIT
prompt ===============================
prompt
create table T_EVENT_OID_INIT
(
  EVEID        NUMBER,
  MRID         NUMBER not null,
  DTID         NUMBER not null,
  OIDGROUPNAME VARCHAR2(255),
  MODID        NUMBER not null,
  OID          VARCHAR2(255),
  UNIT         VARCHAR2(32),
  DESCRIPTION  VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_EVENT_OID_INIT
  is '性能参数关联数据，为特定的设备类型指派性能指标和OIDGroup的关系，三个关键的字段为：DTID(设备类型),EVEID(性能指标),OIDGroupName(OIDGroup).
原有表中如下2个字段冗余，建议去除：
1.MRID表示设备类型对应的厂商，可以通过设备类型关联查找
2.MODID表示性能指标的类型（SNMP等），可以通过性能指标关联查找
';
comment on column T_EVENT_OID_INIT.EVEID
  is '性能参数ID';
comment on column T_EVENT_OID_INIT.MRID
  is '设备类型对应的厂商，字段冗余，建议删除';
comment on column T_EVENT_OID_INIT.DTID
  is '设备类型ID';
comment on column T_EVENT_OID_INIT.OIDGROUPNAME
  is 'OIDGroupName名称';
comment on column T_EVENT_OID_INIT.MODID
  is '性能参数的类型（SNMP）,此字段冗余，建议删除';
comment on column T_EVENT_OID_INIT.OID
  is '预留，未使用';
comment on column T_EVENT_OID_INIT.UNIT
  is '数值单位，预留，未使用';
comment on column T_EVENT_OID_INIT.DESCRIPTION
  is '描述';

prompt
prompt Creating table T_EVENT_TYPE_INIT
prompt ================================
prompt
create table T_EVENT_TYPE_INIT
(
  MODID        NUMBER not null,
  EVEID        NUMBER not null,
  ETID         NUMBER,
  ESTID        NUMBER,
  EVEOTHERNAME VARCHAR2(100),
  ECODE        NUMBER not null,
  GENERAL      NUMBER,
  MAJOR        VARCHAR2(64),
  MINOR        VARCHAR2(64),
  OTHER        VARCHAR2(64),
  DESCRIPTION  VARCHAR2(400),
  USEFLAG      VARCHAR2(1) default '1'
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_EVENT_TYPE_INIT
  is '性能指标，由MODID表示SNMP, 由general表示private或public';
comment on column T_EVENT_TYPE_INIT.MODID
  is '指标类别（或称为模式类别），表示当前指标的类别，如: snmp,icmp等';
comment on column T_EVENT_TYPE_INIT.GENERAL
  is '-1表示SNMP Private, 0表示SNMP Public';
comment on column T_EVENT_TYPE_INIT.MAJOR
  is '性能名称';
alter table T_EVENT_TYPE_INIT
  add primary key (EVEID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_EVENT_TYPE_INIT
  add unique (MAJOR, MINOR, OTHER)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_GRP_NET
prompt ========================
prompt
create table T_GRP_NET
(
  GID                 NUMBER not null,
  GNAME               VARCHAR2(255) not null,
  SUPID               NUMBER not null,
  LEVELS              NUMBER not null,
  DESCRIPTION         VARCHAR2(400),
  UNMALLOCEDIPSETFLAG VARCHAR2(1) default '0'
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_GRP_NET
  is '网络架构';
alter table T_GRP_NET
  add primary key (GID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_GRP_NET
  add unique (GNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_LINEPOL_MAP
prompt ============================
prompt
create table T_LINEPOL_MAP
(
  PTID  NUMBER not null,
  MPID  NUMBER,
  PPID  NUMBER,
  MCODE NUMBER,
  FLAG  NUMBER(1)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_LINEPOL_MAP
  add primary key (PTID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_LIST_IP
prompt ========================
prompt
create table T_LIST_IP
(
  GID          NUMBER not null,
  CATEGORY     NUMBER not null,
  IP           VARCHAR2(15) not null,
  MASK         VARCHAR2(15) not null,
  IPDECODE_MIN NUMBER not null,
  IPDECODE_MAX NUMBER not null,
  DESCRIPTION  VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_MANUFACTURER_INFO_INIT
prompt =======================================
prompt
create table T_MANUFACTURER_INFO_INIT
(
  MRID        NUMBER not null,
  MRNAME      VARCHAR2(64) not null,
  OBJECTID    VARCHAR2(255),
  DESCRIPTION VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_MANUFACTURER_INFO_INIT
  is '厂商数据，所有设备的厂商信息';
alter table T_MANUFACTURER_INFO_INIT
  add primary key (MRID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_MANUFACTURER_INFO_INIT
  add constraint AK_MRNAME unique (MRNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_MANUFACTURER_INFO_INIT
  add constraint AK_OBJID unique (OBJECTID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_MODULE_INFO_INIT
prompt =================================
prompt
create table T_MODULE_INFO_INIT
(
  MODID       NUMBER not null,
  MNAME       VARCHAR2(64) not null,
  MCODE       NUMBER not null,
  DESCRIPTION VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_MODULE_INFO_INIT
  is '模式类别，如：snmp, icmp等';
alter table T_MODULE_INFO_INIT
  add primary key (MODID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_MODULE_INFO_INIT
  add unique (MNAME, MCODE)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_OIDGROUP_DETAILS_INIT
prompt ======================================
prompt
create table T_OIDGROUP_DETAILS_INIT
(
  OPID     NUMBER not null,
  OIDVALUE VARCHAR2(255) not null,
  OIDNAME  VARCHAR2(200) not null,
  OIDUNIT  VARCHAR2(32) not null,
  FLAG     VARCHAR2(1) default '1' not null,
  OIDINDEX NUMBER
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_OIDGROUP_DETAILS_INIT.OIDVALUE
  is 'OID';
comment on column T_OIDGROUP_DETAILS_INIT.OIDNAME
  is 'OID名称';
comment on column T_OIDGROUP_DETAILS_INIT.OIDUNIT
  is '数值单位';
comment on column T_OIDGROUP_DETAILS_INIT.OIDINDEX
  is '序号';
alter table T_OIDGROUP_DETAILS_INIT
  add primary key (OPID, OIDNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_OIDGROUP_INFO_INIT
prompt ===================================
prompt
create table T_OIDGROUP_INFO_INIT
(
  OPID         NUMBER not null,
  OIDGROUPNAME VARCHAR2(255) not null,
  OTYPE        NUMBER not null,
  DESCRIPTION  VARCHAR2(64)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_OIDGROUP_INFO_INIT
  is 'OID Group数据，由OType字段表示类别：设备、线路/端口或MID Index';
comment on column T_OIDGROUP_INFO_INIT.OTYPE
  is '1表示设备类
2表示端口类';
alter table T_OIDGROUP_INFO_INIT
  add primary key (OPID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_OIDGROUP_INFO_INIT
  add unique (OIDGROUPNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_POLICY_BASE
prompt ============================
prompt
create table T_POLICY_BASE
(
  MPID        NUMBER not null,
  MPNAME      VARCHAR2(64) not null,
  CATEGORY    NUMBER not null,
  DESCRIPTION VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_POLICY_BASE
  is '策略定义';
comment on column T_POLICY_BASE.MPNAME
  is '策略名称';
comment on column T_POLICY_BASE.CATEGORY
  is '1表示设备策略
4表示端口策略';
comment on column T_POLICY_BASE.DESCRIPTION
  is '策略描述';
alter table T_POLICY_BASE
  add primary key (MPID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_POLICY_BASE
  add unique (MPNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_POLICY_DETAILS
prompt ===============================
prompt
create table T_POLICY_DETAILS
(
  MPID           NUMBER not null,
  MODID          NUMBER not null,
  EVEID          NUMBER not null,
  POLL           NUMBER default 60,
  VALUE_1        VARCHAR2(32) default 'N/A',
  SEVERITY_1     NUMBER,
  FILTER_A       VARCHAR2(1),
  VALUE_2        VARCHAR2(32) default 'N/A',
  SEVERITY_2     NUMBER,
  FILTER_B       VARCHAR2(1),
  SEVERITY_A     NUMBER,
  SEVERITY_B     NUMBER,
  OIDGROUP       VARCHAR2(255),
  OGFLAG         VARCHAR2(1) default '0',
  VALUE_1_LOW    VARCHAR2(32) default '0',
  VALUE_2_LOW    VARCHAR2(32) default '0',
  V1L_SEVERITY_1 NUMBER,
  V1L_SEVERITY_A NUMBER,
  V2L_SEVERITY_2 NUMBER,
  V2L_SEVERITY_B NUMBER,
  COMPARETYPE    VARCHAR2(20)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_POLICY_DETAILS
  is '策略详细信息';
comment on column T_POLICY_DETAILS.MODID
  is '此字段冗余，删除，仅需要eveid即可获得对应的事件类别（SNMP, SysLog等）';
comment on column T_POLICY_DETAILS.POLL
  is 'Poll间隔（秒）';
comment on column T_POLICY_DETAILS.VALUE_1
  is '阀值1';
comment on column T_POLICY_DETAILS.SEVERITY_1
  is '阀值1告警级别';
comment on column T_POLICY_DETAILS.VALUE_2
  is '阀值2';
comment on column T_POLICY_DETAILS.SEVERITY_2
  is '阀值2告警级别';
alter table T_POLICY_DETAILS
  add primary key (MPID, MODID, EVEID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_POLICY_PERIOD
prompt ==============================
prompt
create table T_POLICY_PERIOD
(
  PPID        NUMBER not null,
  PPNAME      VARCHAR2(64) not null,
  START_TIME  DATE not null,
  END_TIME    DATE not null,
  DESCRIPTION VARCHAR2(400),
  WORKDAY     VARCHAR2(7),
  ENABLED     VARCHAR2(1) default '1' not null,
  DEFAULTFLAG VARCHAR2(1) default '0' not null,
  S1          NUMBER default 0,
  S2          NUMBER default 0,
  S3          NUMBER default 0,
  S4          NUMBER default 0,
  S5          NUMBER default 0,
  S6          NUMBER default 0,
  S7          NUMBER default 0,
  E1          NUMBER default 86399,
  E2          NUMBER default 86399,
  E3          NUMBER default 86399,
  E4          NUMBER default 86399,
  E5          NUMBER default 86399,
  E6          NUMBER default 86399,
  E7          NUMBER default 86399,
  BTIME       VARCHAR2(64) default '00:00:00|00:00:00|00:00:00|00:00:00|00:00:00|00:00:00|00:00:00',
  ETIME       VARCHAR2(64) default '23:59:59|23:59:59|23:59:59|23:59:59|23:59:59|23:59:59|23:59:59'
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_POLICY_PERIOD
  is '监控时段策略';
comment on column T_POLICY_PERIOD.PPNAME
  is '时段名称';
alter table T_POLICY_PERIOD
  add primary key (PPID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_POLICY_PERIOD
  add unique (PPNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_PORT_INFO
prompt ==========================
prompt
create table T_PORT_INFO
(
  PTID          NUMBER not null,
  DEVID         NUMBER not null,
  IFINDEX       NUMBER,
  IFIP          VARCHAR2(15),
  IPDECODE_IFIP NUMBER,
  IFMAC         VARCHAR2(20),
  IFOPERSTATUS  VARCHAR2(4),
  IFDESCR       VARCHAR2(255) not null,
  DESCRIPTION   VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_PORT_INFO
  add primary key (PTID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_PP_DEV
prompt =======================
prompt
create table T_PP_DEV
(
  DEVIP VARCHAR2(15) not null,
  BTIME VARCHAR2(64),
  ETIME VARCHAR2(64)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_PP_DEV
  add constraint PK_T_PP_DEV primary key (DEVIP)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_PP_PORT
prompt ========================
prompt
create table T_PP_PORT
(
  DEVIP   VARCHAR2(15) not null,
  IFDESCR VARCHAR2(255) not null,
  BTIME   VARCHAR2(64),
  ETIME   VARCHAR2(64)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_PP_PORT
  add constraint PK_T_PP_PORT primary key (DEVIP, IFDESCR)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_SERVER_INFO
prompt ============================
prompt
create table T_SERVER_INFO
(
  NMSID       NUMBER not null,
  NMSIP       VARCHAR2(15) not null,
  NMSNAME     VARCHAR2(64) not null,
  USERNAME    VARCHAR2(64),
  PASSWORD    VARCHAR2(64),
  OSTYPE      VARCHAR2(64),
  DESCRIPTION VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table T_SERVER_INFO
  is '网络系统规划';
alter table T_SERVER_INFO
  add primary key (NMSID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_SERVER_INFO
  add unique (NMSIP, NMSNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_SVRMOD_MAP
prompt ===========================
prompt
create table T_SVRMOD_MAP
(
  NMSID       NUMBER not null,
  MODID       NUMBER not null,
  PATH        VARCHAR2(255),
  DESCRIPTION VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_SVRMOD_MAP
  add primary key (NMSID, MODID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table T_USER
prompt =====================
prompt
create table T_USER
(
  USID        NUMBER not null,
  UNAME       VARCHAR2(64) not null,
  PASSWORD    VARCHAR2(64),
  STATUS      VARCHAR2(2) not null,
  DESCRIPTION VARCHAR2(400)
)
tablespace NCS_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_USER
  add primary key (USID)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_USER
  add unique (UNAME)
  using index 
  tablespace NCS_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence NM_SEQ
prompt ========================
prompt
create sequence NM_SEQ
minvalue 1
maxvalue 99999999999
start with 257235008
increment by 1
nocache
cycle;

prompt
prompt Creating sequence TASK_SEQ
prompt ==========================
prompt
create sequence TASK_SEQ
minvalue 1
maxvalue 999999999
start with 87064
increment by 1
nocache
cycle;

prompt
prompt Creating view V_DEVICE_ICMP_POLICY_EXPORT
prompt =========================================
prompt
create or replace view v_device_icmp_policy_export as
select
  d.devid devid,
  d.devip devip,
  d.ipdecode ipdecode,
  d.sysname sysname, /* Device Name*/
  d.mrid mrid, /*Manufactureror ID*/
  '' mrname, /*Manufactureror Name*/
  d.dtid dtid,
  d.rcommunity rcommunity,
  d.snmpversion snmpversion,
  pd.mpid mpid,
  pd.eveid eveid,
  pd.poll poll, /*Polling time*/
  et.major eventTypeMajor, /*Event Type Name*/
  mi.mname moduleName
from
  t_device_info d,
  t_devpol_map,
  t_policy_details pd,
  t_module_info_init mi,
  t_event_type_init et
where
 t_devpol_map.devid=d.devid
 and t_devpol_map.mpid=pd.mpid
 and et.eveid=pd.eveid
 and mi.modid=pd.modid
 and mi.mname='icmp'
/

prompt
prompt Creating view V_DEVICE_SNMP_POLICY_EXPORT
prompt =========================================
prompt
create or replace view v_device_snmp_policy_export as
select
  d.devid devid,
  d.devip devip,
  d.ipdecode ipdecode,
  d.sysname sysname, /* Device Name*/
  d.mrid mrid, /*Manufactureror ID*/
  m.mrname mrname, /*Manufactureror Name*/
  d.dtid dtid,
  d.rcommunity rcommunity,
  d.snmpversion snmpversion,
  m.objectid mobjectid,
  pd.mpid mpid,
  pd.eveid eveid,
  pd.poll poll, /*Polling time*/
  et.major eventTypeMajor, /*Event Type Name*/
  ogi.opid opid, /*OID Group ID*/
  ogi.oidgroupname oidGroupName,
  mi.mname moduleName
from
  t_device_info d,
  t_manufacturer_info_init m,
  t_devpol_map,
  t_policy_details pd,
  t_module_info_init mi,
  t_event_type_init et,
  t_event_oid_init eo,
  t_oidgroup_info_init ogi
where
 t_devpol_map.devid=d.devid
 and d.mrid=m.mrid
 and t_devpol_map.mpid=pd.mpid
 and et.eveid=pd.eveid
 and eo.eveid=pd.eveid
 and mi.modid=pd.modid
 and eo.dtid=d.dtid
 and ogi.oidgroupname=eo.oidgroupname
/

prompt
prompt Creating view V_EVENT_TYPE
prompt ==========================
prompt
create or replace view v_event_type as
select
     a.MODID MODID,
     a.EVEID EVEID,
     a.ETID ETID,
     a.ESTID ESTID,
     a.EVEOTHERNAME EVEOTHERNAME,
     a.ECODE ECODE,
     a.GENERAL GENERAL,
     a.MAJOR MAJOR,
     a.MINOR MINOR,
     a.OTHER OTHER,
     a.DESCRIPTION  DESCRIPTION,
     a.USEFLAG USEFLAG,
    b.MODID T_MODULE_INFO_INIT_MODID, /* T_MODULE_INFO_INIT_MODID */
    b.MNAME MNAME,
    b.MCODE MCODE,
    b.DESCRIPTION T_MODULE_INFO_INIT_DESCRIPTION /* T_MODULE_INFO_INIT_DESCRIPTION */
from
    T_EVENT_TYPE_INIT  a,
    T_MODULE_INFO_INIT b
where
    a.modid=b.modid
with read only
/

prompt
prompt Creating view V_ICMP_DEV_THRESHOLDS
prompt ===================================
prompt
create or replace view v_icmp_dev_thresholds as
select
    b.devip devip,
(case when d.btime is not null then d.btime else z.btime end) btime,
(case when d.etime is not null then d.etime else z.etime end) etime,
    '0' value0,    /*  default=0 阀值0 THRESHOLDS */
    e.value_1 value_1, /* 阀值1 */
    e.value_2 value_2, /* 阀值2 */
    'var0' var0,        /* 阀值0内容 VARLIST */
    e.value_1_low, /* 阀值1内容 */
    e.value_2_low, /* 阀值2内容 */
    e.comparetype comparetype,
    e.v1l_severity_1 v1l_severity_1,        /* 阀值0告警级别 内 */
    e.severity_1 severity_1, /* 阀值1告警级别 内 */
    e.severity_2 severity_2, /* 阀值2告警级别 内 */
    e.v1l_severity_A v1l_severity_A, /* 阀值0告警级别 外 */
    e.severity_A severity_A,        /* 阀值1告警级别 外 */
    e.severity_B severity_B,         /* 阀值2告警级别 外 */
    e.filter_A filter_A,
    e.filter_B filter_B
from
    T_DEVPOL_MAP a left join T_POLICY_PERIOD d on a.ppid=d.ppid,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_DETAILS e,
    T_MODULE_INFO_INIT g,
     (select btime,etime from t_policy_period y where y.defaultflag=1) z
where
    a.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid
       and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='icmp'
with read only
/

prompt
prompt Creating view V_ICMP_PORT_THRESHOLDS
prompt ====================================
prompt
create or replace view v_icmp_port_thresholds as
select
    f.ifip ifip,   /* port ip */
(case when d.btime is not null then d.btime else z.btime end) btime,
(case when d.etime is not null then d.etime else z.etime end) etime,
    '0'value0,    /*  default=0 阀值0 THRESHOLDS */
    e.value_1 value_1, /* 阀值1 */
    e.value_2 value_2, /* 阀值2 */
    'var0' var0,        /* 阀值0内容 VARLIST */
    e.value_1_low, /* 阀值1内容 */
    e.value_2_low, /* 阀值2内容 */
    e.comparetype comparetype,
    e.v1l_severity_1 v1l_severity_1,        /* 阀值0告警级别 内 */
    e.severity_1 severity_1, /* 阀值1告警级别 内 */
    e.severity_2 severity_2, /* 阀值2告警级别 内 */
    e.v1l_severity_A v1l_severity_A, /* 阀值0告警级别 外 */
    e.severity_A severity_A,        /* 阀值1告警级别 外 */
    e.severity_B severity_B,         /* 阀值2告警级别 外 */
    e.filter_A filter_A,
    e.filter_B filter_B
from
    T_LINEPOL_MAP a left join T_POLICY_PERIOD d on a.ppid=d.ppid,
    T_EVENT_TYPE_INIT c,
    T_POLICY_DETAILS e,
    T_PORT_INFO f,
    T_MODULE_INFO_INIT g,
    (select btime,etime from t_policy_period y where y.defaultflag=1) z
where
    a.ptid=f.ptid  and a.mpid=e.mpid and e.eveid=c.eveid
    and f.ifip is not null  and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='icmp'
with read only
/

prompt
prompt Creating view V_MF_CATE_DEVTYPE
prompt ===============================
prompt
create or replace view v_mf_cate_devtype as
select
    a.ID ID,
    a.NAME NAME,
    a.FLAG FLAG,
    b.MRID MRID,
    b.MRNAME MRNAME,
    b.OBJECTID MF_OBJECTID,
    b.DESCRIPTION MF_DESCRIPTION,
    c.MRID devicetype_MRID,
    c.DTID DTID,
    c.CATEGORY CATEGORY,
    c.SUBCATEGORY SUBCATEGORY,
    c.MODEL MODEL,
    c.OBJECTID OBJECTID,
    c.LOGO LOGO,
    c.DESCRIPTION DESCRIPTION
from
    T_CATEGORY_MAP_INIT a,
    T_MANUFACTURER_INFO_INIT b,
    T_DEVICE_TYPE_INIT c
where
   a.id=c.category and c.mrid=b.mrid
with read only
/

prompt
prompt Creating view V_OID_GROUP
prompt =========================
prompt
create or replace view v_oid_group as
select
    c.OPID T_OIDGROUP_INFO_INIT_OPID, /* T_OIDGROUP_INFO_INIT_OPID */
    c.OIDGROUPNAME OIDGROUPNAME,
    c.OTYPE OTYPE,
    c.DESCRIPTION DESCRIPTION,
    d.OPID T_OIDGROUP_DETAILS_INIT_OPID, /* T_OIDGROUP_DETAILS_INIT_OPID */
    d.OIDVALUE OIDVALUE,
    d.OIDNAME OIDNAME,
    d.OIDUNIT OIDUNIT,
    d.FLAG FLAG,
    d.OIDINDEX OIDINDEX
from
    T_OIDGROUP_INFO_INIT c,
    T_OIDGROUP_DETAILS_INIT d
where
    c.opid=d.opid
with read only
/

prompt
prompt Creating view V_PERFORM_PARAM
prompt =============================
prompt
create or replace view v_perform_param as
select
     a.MODID eventtype_MODID,
     a.EVEID eventtype_EVEID,
     a.ETID ETID,
     a.ESTID ESTID,
     a.EVEOTHERNAME EVEOTHERNAME,
     a.ECODE ECODE,
     a.GENERAL GENERAL,
     a.MAJOR MAJOR,
     a.MINOR MINOR,
     a.OTHER OTHER,
     a.DESCRIPTION  eventtype_DESCRIPTION,
     a.USEFLAG USEFLAG,
     c.OPID oidgroup_OPID, /* T_OIDGROUP_INFO_INIT_OPID */
    c.OIDGROUPNAME oidgroup_OIDGROUPNAME,
    c.OTYPE OTYPE,
    c.DESCRIPTION oidgroup_DESCRIPTION,
    e.EVEID EVEID,
    e.MRID MRID,
    e.DTID DTID,
    e.OIDGROUPNAME OIDGROUPNAME,
    e.MODID MODID, /* T_EVENT_OID_INIT_MODID */
    e.OID OID,
    e.UNIT UNIT,
    e.DESCRIPTION DESCRIPTION,
    p.MRID devtype_MRID,
    p.DTID devtype_DTID,
    p.CATEGORY CATEGORY,
    p.SUBCATEGORY SUBCATEGORY,
    p.MODEL MODEL,
    p.OBJECTID OBJECTID,
    p.LOGO LOGO,
    p.DESCRIPTION devtype_DESCRIPTION
from
    T_EVENT_TYPE_INIT  a,
    T_OIDGROUP_INFO_INIT c,
    T_EVENT_OID_INIT e,
    T_DEVICE_TYPE_INIT p
where
    e.eveid=a.eveid  and e.dtid=p.dtid
    and e.oidgroupname=c.oidgroupname
with read only
/

prompt
prompt Creating function SETDEFAULTMANUFACTNAME
prompt ========================================
prompt
create or replace function setDefaultManufactName(name varchar2, general number) return varchar2 is
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

prompt
prompt Creating view V_PORT_ICMP_POLICY_EXPORT
prompt =======================================
prompt
create or replace view v_port_icmp_policy_export as
select
  p.ptid pdid,
  p.ifindex ifindex,
  p.ifip ifip,
  p.ifdescr ifdescr,
  d.devid devid,
  d.devip devip,
  d.ipdecode ipdecode,
  d.sysname sysname, /* Device Name*/
  d.mrid mrid, /*Manufactureror ID*/
  setDefaultManufactName(m.mrname, 1) mrname, /*Manufactureror Name*/
  d.dtid dtid,
  d.rcommunity rcommunity,
  d.snmpversion snmpversion,
  m.objectid mobjectid,
  pd.mpid mpid,
  pd.eveid eveid,
  pd.poll poll, /*Polling time*/
  et.major eventTypeMajor, /*Event Type Name*/
  mi.mname moduleName
from
  t_port_info p,
  t_linepol_map,
  t_device_info d left join t_manufacturer_info_init m on d.mrid=m.mrid,
  t_policy_details pd,
  t_module_info_init mi,
  t_event_type_init et
where
 p.ptid=t_linepol_map.ptid
 and p.devid=d.devid
 and d.mrid=d.mrid
 and t_linepol_map.mpid=pd.mpid
 and mi.modid=pd.modid
 and et.eveid=pd.eveid
 and mi.mname='icmp'
/

prompt
prompt Creating view V_PORT_SNMP_POLICY_EXPORT
prompt =======================================
prompt
create or replace view v_port_snmp_policy_export as
select
  p.ptid pdid,
  p.ifindex ifindex,
  p.ifip ifip,
  p.ifdescr ifdescr,
  d.devid devid,
  d.devip devip,
  d.ipdecode ipdecode,
  d.sysname sysname, /* Device Name*/
  d.mrid mrid, /*Manufactureror ID*/
  setDefaultManufactName(m.mrname, et.general) mrname, /*Manufactureror Name*/
  d.dtid dtid,
  d.rcommunity rcommunity,
  d.snmpversion snmpversion,
  m.objectid mobjectid,
  pd.mpid mpid,
  pd.eveid eveid,
  pd.poll poll, /*Polling time*/
  et.major eventTypeMajor, /*Event Type Name*/
  ogi.opid opid, /*OID Group ID*/
  ogi.oidgroupname oidGroupName,
  mi.mname moduleName
from
  t_port_info p,
  t_linepol_map,
  t_device_info d left join t_manufacturer_info_init m on d.mrid=m.mrid,
  t_policy_details pd,
  t_module_info_init mi,
  t_event_type_init et,
  t_oidgroup_info_init ogi
where
 p.ptid=t_linepol_map.ptid
 and p.devid=d.devid
 and t_linepol_map.mpid=pd.mpid
 and mi.modid=pd.modid
 and et.eveid=pd.eveid
 and ogi.oidgroupname like concat(concat(concat(et.major, '_'), setDefaultManufactName(m.mrname, et.general)), '%')
/

prompt
prompt Creating view V_PP_DEV
prompt ======================
prompt
create or replace view v_pp_dev as
select
    b.devip devip,
    (case when d.btime is not null then d.btime else z.btime end) btime,
    (case when d.etime is not null then d.etime else z.etime end) etime
from
    T_DEVPOL_MAP a left join T_POLICY_PERIOD d on a.ppid=d.ppid,
    T_DEVICE_INFO b,
    (select btime,etime from t_policy_period y where y.defaultflag=1) z
where
    a.devid=b.devid
with read only
/

prompt
prompt Creating view V_PP_PORT
prompt =======================
prompt
create or replace view v_pp_port as
select
    b.devip devip,
    f.ifdescr ifdescr,
(case when d.btime is not null then d.btime else z.btime end) btime,
(case when d.etime is not null then d.etime else z.etime end) etime
 from
    T_LINEPOL_MAP a left join T_POLICY_PERIOD d on a.ppid=d.ppid,
    T_DEVICE_INFO b,
    T_PORT_INFO f,
    (select btime,etime from t_policy_period y where y.defaultflag=1) z
where
    a.ptid=f.ptid and f.devid=b.devid
with read only
/

prompt
prompt Creating view V_PREDEFMIB_SNMP_POLICY_EXPORT
prompt ============================================
prompt
create or replace view v_predefmib_snmp_policy_export as
select
  p.pdmid pdmid,
  p.mid mid,
  p.oidindex oidindex,
  p.oidname oidname,
  d.devid devid,
  d.devip devip,
  d.ipdecode ipdecode,
  d.sysname sysname, /* Device Name*/
  d.mrid mrid, /*Manufactureror ID*/
  setDefaultManufactName(m.mrname, et.general) mrname, /*Manufactureror Name*/
  d.dtid dtid,
  d.rcommunity rcommunity,
  d.snmpversion snmpversion,
  m.objectid mobjectid,
  pd.mpid mpid,
  pd.eveid eveid,
  pd.poll poll, /*Polling time*/
  et.major eventTypeMajor, /*Event Type Name*/
  ogi.opid opid, /*OID Group ID*/
  ogi.oidgroupname oidGroupName,
  mi.mname moduleName
from
  predefmib_info p,
  predefmib_pol_map,
  t_device_info d left join t_manufacturer_info_init m on d.mrid=m.mrid,
  t_policy_details pd,
  t_module_info_init mi,
  t_event_type_init et,
  t_oidgroup_info_init ogi
where
 p.pdmid=predefmib_pol_map.pdmid
 and p.devid=d.devid
 and predefmib_pol_map.mpid=pd.mpid
 and mi.modid=pd.modid
 and et.eveid=pd.eveid
 and ogi.oidgroupname like concat(concat(concat(et.major, '_'), setDefaultManufactName(m.mrname, et.general)), '%')
/

prompt
prompt Creating view V_SNMP_DEV_THRESHOLDS
prompt ===================================
prompt
create or replace view v_snmp_dev_thresholds as
select
    b.devip devip,
    c.major major,
(case when d.btime is not null then d.btime else z.btime end) btime,
(case when d.etime is not null then d.etime else z.etime end) etime,
    e.value_1 value_1,
    e.value_2 value_2,
    e.comparetype comparetype,
    e.severity_1 severity_1,
    e.severity_2 severity_2,
    e.severity_A severity_A,
    e.severity_B severity_B,
    e.filter_A filter_A,
    e.filter_B filter_B
from
    T_DEVPOL_MAP a left join T_POLICY_PERIOD d on a.ppid=d.ppid,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_DETAILS e,
    T_MODULE_INFO_INIT f,
    (select btime,etime from t_policy_period y where y.defaultflag=1) z
where
    a.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid
    and e.modid=c.modid and c.modid=f.modid and lower(f.mname)='snmp'
with read only
/

prompt
prompt Creating view V_SNMP_PDM_THRESHOLDS
prompt ===================================
prompt
create or replace view v_snmp_pdm_thresholds as
select
    b.devip devip,
    f.oidname oidname,
    c.major major,
(case when d.btime is not null then d.btime else z.btime end) btime,
(case when d.etime is not null then d.etime else z.etime end) etime,
    e.value_1 value_1,            /* in1 */
 /*   e.value_1_low value_1_low, */   /* in2 */
    e.value_2 value_2,            /* out1 */
 /*   e.value_2_low value_2_low,   */ /* out2 */
    e.comparetype comparetype,
    e.severity_1 severity_1,      /* in1 告警 内 */
    e.severity_A severity_A,      /* in1 告警 外 */
 /*   e.v1l_severity_1 v1l_severity_1,  */ /* in2 内 */
 /*   e.v1l_severity_A v1l_severity_A,  */ /* in2 外 */
    e.severity_2 severity_2,      /* out1 告警 内 */
    e.severity_B severity_B,      /* out1 告警 外 */
 /*   e.v2l_severity_2 v2l_severity_2,  */ /* out2 内 */
 /*   e.v2l_severity_B v2l_severity_B,  */ /* out2 外 */
    e.filter_A filter_A,
    e.filter_B filter_B
from
    PREDEFMIB_POL_MAP a left join T_POLICY_PERIOD d on a.ppid=d.ppid,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_DETAILS e,
    PREDEFMIB_INFO f,
    T_MODULE_INFO_INIT g,
    (select btime,etime from t_policy_period y where y.defaultflag=1) z
where
    a.pdmid=f.pdmid and f.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid
    and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='snmp'
with read only
/

prompt
prompt Creating view V_SNMP_PORT_THRESHOLDS
prompt ====================================
prompt
create or replace view v_snmp_port_thresholds as
select
    b.devip devip,
    f.ifdescr ifdescr,
    c.major major,
(case when d.btime is not null then d.btime else z.btime end) btime,
(case when d.etime is not null then d.etime else z.etime end) etime,
    e.value_1 value_1,            /* in1 */
    e.value_1_low value_1_low,    /* in2 */
    e.value_2 value_2,            /* out1 */
    e.value_2_low value_2_low,    /* out2 */
    e.comparetype comparetype,
    e.severity_1 severity_1,      /* in1 告警 内 */
    e.severity_A severity_A,      /* in1 告警 外 */
    e.v1l_severity_1 v1l_severity_1,   /* in2 内 */
    e.v1l_severity_A v1l_severity_A,   /* in2 外 */
    e.severity_2 severity_2,      /* out1 告警 内 */
    e.severity_B severity_B,      /* out1 告警 外 */
    e.v2l_severity_2 v2l_severity_2,   /* out2 内 */
    e.v2l_severity_B v2l_severity_B,   /* out2 外 */
    e.filter_A filter_A,
    e.filter_B filter_B
from
    T_LINEPOL_MAP a left join T_POLICY_PERIOD d on a.ppid=d.ppid,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_DETAILS e,
    T_PORT_INFO f,
    T_module_info_init g,
        (select btime,etime from t_policy_period y where y.defaultflag=1) z
where
    a.ptid=f.ptid and f.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid and e.modid=c.modid
    and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='snmp'
with read only
/

prompt
prompt Creating view V_SRC_TYPE_EXPORT
prompt ===============================
prompt
create or replace view v_src_type_export as
select
  d.devid devid,
  d.devip devip,
  d.ipdecode ipdecode,
  d.sysname sysname, /* Device Name*/
  d.mrid mrid, /*Manufactureror ID*/
  m.mrname mrname /*Manufactureror Name*/
from
  t_device_info d inner join t_manufacturer_info_init m on d.mrid=m.mrid
order by d.devip
/

prompt
prompt Creating view V_SYSLOG_DEV_EVENTS_ATTENTION
prompt ===========================================
prompt
create or replace view v_syslog_dev_events_attention as
select
   b.DEVIP,c.MARK ,d.events events,
   c.SEVERITY1 ,
   d.PROCESSSUGGEST
from
   T_DEVPOL_MAP a,
   T_DEVICE_INFO b,
   POLICY_SYSLOG c,
   SYSLOG_EVENTS_PROCESS d
where
   a.devid=b.devid and a.mpid=c.mpid and c.mark=d.mark and c.manufacture=d.manufacture
union
select
   b.DEVIP,c.MARK , e.events events,
   c.SEVERITY1 ,
   e.PROCESSSUGGEST
from
   T_DEVPOL_MAP a,
   T_DEVICE_INFO b,
   POLICY_SYSLOG c,
   SYSLOG_EVENTS_PROCESS_NS e
where
   a.devid=b.devid and a.mpid=c.mpid and c.mark=e.mark and c.manufacture=e.manufacture
with read only
/

prompt
prompt Creating view V_SYSLOG_PORT_EVENTS_ATTENTION
prompt ============================================
prompt
create or replace view v_syslog_port_events_attention as
select
   c.DEVIP devip,
   b.ifdescr ifdescr,
   d.MARK mark,
   e.events events,
   d.SEVERITY1 severity1,
   e.PROCESSSUGGEST processsuggest
from
   T_LINEPOL_MAP a,
   T_PORT_INFO b,
   T_DEVICE_INFO c,
   POLICY_SYSLOG d,
   SYSLOG_EVENTS_PROCESS e
where
   a.ptid=b.ptid and b.devid=c.devid and a.mpid=d.mpid and d.mark=e.mark and d.manufacture=e.manufacture
   and d.severity1<=7
union
select
   c.DEVIP devip,
   b.ifdescr ifdescr,
   d.MARK mark,
   f.events events,
   d.SEVERITY1 severity1,
   f.PROCESSSUGGEST processsuggest
from
   T_LINEPOL_MAP a,
   T_PORT_INFO b,
   T_DEVICE_INFO c,
   POLICY_SYSLOG d,
   SYSLOG_EVENTS_PROCESS_NS f
where
   a.ptid=b.ptid and b.devid=c.devid and a.mpid=d.mpid and d.mark=f.mark and d.manufacture=f.manufacture
   and d.severity1<=7
with read only
/

prompt
prompt Creating view V_SYSLOG_PORT_LINE_NOTCARE
prompt ========================================
prompt
create or replace view v_syslog_port_line_notcare as
select
   distinct b.DEVIP devip,
   c.ifip ifip,
   c.ifdescr ifdescr
from
   T_LINEPOL_MAP a,
   T_DEVICE_INFO b,
   T_PORT_INFO c,
   POLICY_SYSLOG d
where
   a.ptid=c.ptid and b.devid=c.devid and a.mpid=d.mpid and (d.severity1 is null or d.severity1 > 7)
with read only
/

prompt
prompt Creating view V_TMP
prompt ===================
prompt
create or replace view v_tmp as
select dst.PPID as oldid, src.PPID as newid from T_POLICY_PERIOD src inner join T_POLICY_PERIOD dst on dst.PPNAME=src.PPNAME where src.defaultflag='1'
/


spool off
