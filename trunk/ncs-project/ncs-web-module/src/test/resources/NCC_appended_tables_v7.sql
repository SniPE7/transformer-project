
/*==============================================================*/
/* Table: T_POLICY_DETAILS 			*/
/*==============================================================*/
ALTER TABLE T_POLICY_DETAILS ADD COMPARETYPE VARCHAR2(20);


/*==============================================================*/
/* Table: T_POLICY_PERIOD 			*/
/*==============================================================*/
ALTER TABLE T_POLICY_PERIOD ADD BTIME VARCHAR2(62) DEFAULT '00:00|00:00|00:00|00:00|00:00|00:00|00:00';
ALTER TABLE T_POLICY_PERIOD ADD ETIME VARCHAR2(62) DEFAULT '23:59|23:59|23:59|23:59|23:59|23:59|23:59';







/*==============================================================*/
alter table NCC.PREDEFMIB_INFO
   drop constraint FK_PREDEFMI_REFERENCE_DEF_MIB_;

alter table NCC.PREDEFMIB_POL_MAP
   drop constraint FK_PREDEFMI_REFERENCE_PREDEFMI;

alter table NCC.DEF_MIB_GRP
   drop primary key cascade;

drop table NCC.DEF_MIB_GRP cascade constraints;

drop table NCC.EVENTS_ATTENTION cascade constraints;

alter table NCC.ICMP_THRESHOLDS
   drop primary key cascade;

drop table NCC.ICMP_THRESHOLDS cascade constraints;

drop table NCC.LINES_EVENTS_NOTCARE cascade constraints;

alter table NCC.POLICY_SYSLOG
   drop primary key cascade;

drop table NCC.POLICY_SYSLOG cascade constraints;

alter table NCC.PREDEFMIB_INFO
   drop primary key cascade;

drop table NCC.PREDEFMIB_INFO cascade constraints;

alter table NCC.PREDEFMIB_POL_MAP
   drop primary key cascade;

drop table NCC.PREDEFMIB_POL_MAP cascade constraints;

alter table NCC.SNMP_THRESHOLDS
   drop primary key cascade;

drop table NCC.SNMP_THRESHOLDS cascade constraints;

alter table NCC.SNMP_THRESHOLDS
   drop primary key cascade;

drop table NCC.SNMP_THRESHOLDS cascade constraints;

alter table NCC.POLICY_SYSLOG
   drop constraint FK_POLICY_S_REFERENCE_SYSLOG_E;

alter table NCC.SYSLOG_EVENTS_PROCESS
   drop primary key cascade;

drop table NCC.SYSLOG_EVENTS_PROCESS cascade constraints;

alter table NCC.POLICY_SYSLOG
   drop constraint FK_POLICY_S_REFERENCE_SYSLOG_E;

alter table NCC.SYSLOG_EVENTS_PROCESS_NS
   drop primary key cascade;

drop table NCC.SYSLOG_EVENTS_PROCESS_NS cascade constraints;

/*==============================================================*/
/* Table: DEF_MIB_GRP                                           */
/*==============================================================*/
create table NCC.DEF_MIB_GRP  (
   MID                  INT                             not null,
   NAME                 VARCHAR2(256),
   INDEXOID             VARCHAR2(256),
   INDEXVAR             VARCHAR2(256),
   DESCROID             VARCHAR2(256),
   DESCRVAR             VARCHAR2(256)
);

alter table NCC.DEF_MIB_GRP
   add constraint PK_DEF_MIB_GRP primary key (MID);

alter table NCC.EVENTS_ATTENTION
   drop primary key cascade;

drop table NCC.EVENTS_ATTENTION cascade constraints;

/*==============================================================*/
/* Table: EVENTS_ATTENTION                                      */
/*==============================================================*/
create table NCC.EVENTS_ATTENTION  (
   EVENTSATTENTION      VARCHAR2(255)                   not null,
   SEVERITY             NUMBER,
   PROCESSSUGGEST       VARCHAR2(255)
);

alter table NCC.EVENTS_ATTENTION
   add constraint PK_EVENTS_ATTENTION primary key (EVENTSATTENTION);



/*==============================================================*/
/* Table: ICMP_THRESHOLDS                                       */
/*==============================================================*/
create table NCC.ICMP_THRESHOLDS  (
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
   SUMMARYCN            VARCHAR2(255)
);

comment on table NCC.ICMP_THRESHOLDS is
'from icmp_threhold_v2 
excel';

alter table NCC.ICMP_THRESHOLDS
   add constraint PK_ICMP_THRESHOLDS primary key (IPADDRESS);


alter table NCC.LINES_EVENTS_NOTCARE
   drop primary key cascade;

drop table NCC.LINES_EVENTS_NOTCARE cascade constraints;

/*==============================================================*/
/* Table: LINES_EVENTS_NOTCARE                                  */
/*==============================================================*/
create table NCC.LINES_EVENTS_NOTCARE  (
   LINESNOTCARE         VARCHAR2(255)                   not null
);

alter table NCC.LINES_EVENTS_NOTCARE
   add constraint PK_LINES_EVENTS_NOTCARE primary key (LINESNOTCARE);

/*==============================================================*/
/* Table: POLICY_SYSLOG                                         */
/*==============================================================*/
create table NCC.POLICY_SYSLOG  (
   SPID                 NUMBER                          not null,
   MPID                 NUMBER,
   MARK                 VARCHAR2(255),
   MANUFACTURE          VARCHAR2(30),
   EVENTTYPE            NUMBER,
   SEVERITY1            NUMBER,
   SEVERITY2            NUMBER,
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER
);

alter table NCC.POLICY_SYSLOG
   add constraint PK_POLICY_SYSLOG primary key (SPID);

/*==============================================================*/
/* Table: PREDEFMIB_INFO                                        */
/*==============================================================*/
create table NCC.PREDEFMIB_INFO  (
   PDMID                NUMBER                          not null,
   MID                  INT,
   DEVID                NUMBER,
   OIDINDEX             VARCHAR2(255),
   OIDNAME              VARCHAR2(255)
);

alter table NCC.PREDEFMIB_INFO
   add constraint PK_PREDEFMIB_INFO primary key (PDMID);

/*==============================================================*/
/* Table: PREDEFMIB_POL_MAP                                     */
/*==============================================================*/
create table NCC.PREDEFMIB_POL_MAP  (
   PDMID                NUMBER                          not null,
   MPID                 NUMBER,
   PPID                 NUMBER,
   MCODE                NUMBER,
   FLAG                 NUMBER(1)
);

alter table NCC.PREDEFMIB_POL_MAP
   add constraint PK_PREDEFMIB_POL_MAP primary key (PDMID);

/*==============================================================*/
/* Table: SNMP_EVENTS_PROCESS                                   */
/*==============================================================*/
create table NCC.SNMP_EVENTS_PROCESS  (
   MARK                 varchar2(255)                   not null,
   MANUFACTURE          VARCHAR2(30),
   RESULTLIST           VARCHAR2(255),
   WARNMESSAGE          VARCHAR2(255),
   SUMMARY              VARCHAR2(255)
);

alter table NCC.SNMP_EVENTS_PROCESS
   add constraint PK_SNMP_EVENTS_PROCESS primary key (MARK);

/*==============================================================*/
/* Table: SNMP_THRESHOLDS                                       */
/*==============================================================*/
create table NCC.SNMP_THRESHOLDS  (
   PERFORMANCE          VARCHAR2(255)                   not null,
   BTIME                VARCHAR2(62),
   ETIME                VARCHAR2(62),
   THRESHOLD            VARCHAR2(50),
   COMPARETYPE          VARCHAR2(50),
   SEVERITY1            VARCHAR2(50),
   SEVERITY2            VARCHAR2(50),
   FILTERFLAG1          NUMBER,
   FILTERFLAG2          NUMBER
);

alter table NCC.SNMP_THRESHOLDS
   add constraint PK_SNMP_THRESHOLDS primary key (PERFORMANCE);


/*==============================================================*/
/* Table: SYSLOG_EVENTS_PROCESS                                 */
/*==============================================================*/
create table NCC.SYSLOG_EVENTS_PROCESS  (
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
   ORIGEVENT            VARCHAR2(1024)
);

alter table NCC.SYSLOG_EVENTS_PROCESS
   add constraint PK_SYSLOG_EVENTS_PROCESS primary key (MARK, MANUFACTURE);




/*==============================================================*/
/* Table: SYSLOG_EVENTS_PROCESS_NS                              */
/*==============================================================*/
create table NCC.SYSLOG_EVENTS_PROCESS_NS  (
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
   ORIGEVENT            VARCHAR2(1024)
);

alter table NCC.SYSLOG_EVENTS_PROCESS_NS
   add constraint PK_SYSLOG_EVENTS_PROCESS_NS primary key (MARK, MANUFACTURE);


alter table NCC.PREDEFMIB_INFO
   add constraint FK_PREDEFMI_REFERENCE_DEF_MIB_ foreign key (MID)
      references NCC.DEF_MIB_GRP (MID);

alter table NCC.PREDEFMIB_POL_MAP
   add constraint FK_PREDEFMI_REFERENCE_PREDEFMI foreign key (PDMID)
      references NCC.PREDEFMIB_INFO (PDMID);
