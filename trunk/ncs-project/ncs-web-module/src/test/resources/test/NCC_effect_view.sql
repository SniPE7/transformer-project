drop view NCC.V_SYSLOG_DEV_EVENTS_ATTENTION;

/*==============================================================*/
/* View: V_SYSLOG_DEV_EVENTS_ATTENTION                          */
/*==============================================================*/
create or replace view NCC.V_SYSLOG_DEV_EVENTS_ATTENTION as
select
   b.DEVIP,c.MARK ,
   c.SEVERITY1 ,
   d.PROCESSSUGGEST
from   
   NCC.T_DEVPOL_MAP a,
   NCC.T_DEVICE_INFO b,
   NCC.POLICY_SYSLOG c,
   NCC.SYSLOG_EVENTS_PROCESS d 
where 
   a.devid=b.devid and a.mpid=c.mpid and c.mark=d.mark and c.manufacture=d.manufacture
   and c.severity1<=7
union 
select
   b.DEVIP,c.MARK ,
   c.SEVERITY1 ,
   e.PROCESSSUGGEST 
from   
   NCC.T_DEVPOL_MAP a,
   NCC.T_DEVICE_INFO b,
   NCC.POLICY_SYSLOG c,
   NCC.SYSLOG_EVENTS_PROCESS_NS e   
where 
   a.devid=b.devid and a.mpid=c.mpid and c.mark=e.mark and c.manufacture=e.manufacture
   and c.severity1 <=7
with read only;

drop view NCC.V_SYSLOG_PORT_EVENTS_ATTENTION;

/*==============================================================*/
/* View: V_SYSLOG_PORT_EVENTS_ATTENTION                         */
/*==============================================================*/
create or replace view NCC.V_SYSLOG_PORT_EVENTS_ATTENTION as
select
   c.DEVIP devip,
   b.ifdescr ifdescr,
   d.MARK mark,
   d.SEVERITY1 severity1,
   e.PROCESSSUGGEST processsuggest
from   
   NCC.T_LINEPOL_MAP a,
   NCC.T_PORT_INFO b,
   NCC.T_DEVICE_INFO c,
   NCC.POLICY_SYSLOG d,
   NCC.SYSLOG_EVENTS_PROCESS e 
where 
   a.ptid=b.ptid and b.devid=c.devid and a.mpid=d.mpid and d.mark=e.mark and d.manufacture=e.manufacture 
   and d.severity1<=7
union 
select
   c.DEVIP devip,
   b.ifdescr ifdescr,
   d.MARK mark,
   d.SEVERITY1 severity1,
   f.PROCESSSUGGEST processsuggest
from   
   NCC.T_LINEPOL_MAP a,
   NCC.T_PORT_INFO b,
   NCC.T_DEVICE_INFO c,
   NCC.POLICY_SYSLOG d,
   NCC.SYSLOG_EVENTS_PROCESS_NS f   
where 
   a.ptid=b.ptid and b.devid=c.devid and a.mpid=d.mpid and d.mark=f.mark and d.manufacture=f.manufacture
   and d.severity1<=7
   
   
with read only;

drop view NCC.V_SYSLOG_PDM_EVENTS_ATTENTION;

/*==============================================================*/
/* View: V_SYSLOG_PDM_EVENTS_ATTENTION                          */
/*==============================================================*/
create or replace view NCC.V_SYSLOG_PDM_EVENTS_ATTENTION as
select
   c.DEVIP devip,
   b.oidname oidname,
   d.MARK mark,
   d.SEVERITY1 severity1,
   e.PROCESSSUGGEST processsuggest
from   
   NCC.PREDEFMIB_POL_MAP a,
   NCC.PREDEFMIB_INFO b,
   NCC.T_DEVICE_INFO c,
   NCC.POLICY_SYSLOG d,
   NCC.SYSLOG_EVENTS_PROCESS e 
where 
   a.pdmid=b.pdmid and b.devid=c.devid and a.mpid=d.mpid and d.mark=e.mark and d.manufacture=e.manufacture
   and d.severity1<=7
union 
select
   c.DEVIP devip,
   b.oidname oidname,
   d.MARK mark,
   d.SEVERITY1 severity1,
   f.PROCESSSUGGEST processsuggest
from   
   NCC.PREDEFMIB_POL_MAP a,
   NCC.PREDEFMIB_INFO b,
   NCC.T_DEVICE_INFO c,
   NCC.POLICY_SYSLOG d,
   NCC.SYSLOG_EVENTS_PROCESS_NS f   
where 
   a.pdmid=b.pdmid and b.devid=c.devid and a.mpid=d.mpid and d.mark=f.mark and d.manufacture=f.manufacture
   and d.severity1<=7
with read only;

drop view NCC.V_SYSLOG_DEV_LINE_NOTCARE;

/*==============================================================*/
/* View: V_SYSLOG_DEV_LINE_NOTCARE                              */
/*==============================================================*/
create or replace view NCC.V_SYSLOG_DEV_LINE_NOTCARE as
select
   b.DEVIP devip
from   
   NCC.T_DEVPOL_MAP a,
   NCC.T_DEVICE_INFO b,
   NCC.POLICY_SYSLOG d
where 
   a.devid=b.devid and a.mpid=d.mpid and d.severity1>7

with read only;

drop view NCC.V_SYSLOG_PORT_LINE_NOTCARE;

/*==============================================================*/
/* View: V_SYSLOG_PORT_LINE_NOTCARE                             */
/*==============================================================*/
create or replace view NCC.V_SYSLOG_PORT_LINE_NOTCARE as
select
   b.DEVIP devip,
   c.ifip ifip,
   c.ifdescr ifdescr
from   
   NCC.T_LINEPOL_MAP a,
   NCC.T_DEVICE_INFO b,
   NCC.T_PORT_INFO c,
   NCC.POLICY_SYSLOG d
where 
   a.ptid=c.ptid and b.devid=c.devid and a.mpid=d.mpid and d.severity1 > 7

with read only;


drop view NCC.V_SYSLOG_PDM_LINE_NOTCARE;

/*==============================================================*/
/* View: V_SYSLOG_PDM_LINE_NOTCARE                              */
/*==============================================================*/
create or replace view NCC.V_SYSLOG_PDM_LINE_NOTCARE as
select
   b.DEVIP devip,
   c.oidname oidname
from   
   NCC.PREDEFMIB_POL_MAP a,
   NCC.T_DEVICE_INFO b,
   NCC.PREDEFMIB_INFO c,
   NCC.POLICY_SYSLOG d
where 
   a.pdmid=c.pdmid and b.devid=c.devid and a.mpid=d.mpid and d.severity1>7

with read only;

drop view NCC.V_SNMP_DEV_THRESHOLDS;

/*==============================================================*/
/* View: V_SNMP_DEV_THRESHOLDS                                  */
/*==============================================================*/
create or replace view NCC.V_SNMP_DEV_THRESHOLDS as
select 
    b.devip devip,
    c.major major,
d.btime btime,
d.etime etime,
    e.value_1 value_1,
    e.value_2 value_2,
    e.comparetype comparetype,
    e.severity_1 severity_1,
    e.severity_2 severity_2,
    e.filter_A filter_A,
    e.filter_B filter_B
from
    T_DEVPOL_MAP a,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_PERIOD d, 
    T_POLICY_DETAILS e,
    T_MODULE_INFO_INIT f
where 
    a.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid 
 and (  d.defaultflag=1 or a.ppid=d.ppid ) 
    and e.modid=c.modid and c.modid=f.modid and lower(f.mname)='snmp'
with read only;

comment on column NCC.V_SNMP_DEV_THRESHOLDS.MAJOR is
'性能名称';

comment on column NCC.V_SNMP_DEV_THRESHOLDS.VALUE_1 is
'阀值1';

comment on column NCC.V_SNMP_DEV_THRESHOLDS.VALUE_2 is
'阀值2';

comment on column NCC.V_SNMP_DEV_THRESHOLDS.SEVERITY_1 is
'阀值1告警级别';

comment on column NCC.V_SNMP_DEV_THRESHOLDS.SEVERITY_2 is
'阀值2告警级别';

drop view NCC.V_SNMP_PORT_THRESHOLDS;

/*==============================================================*/
/* View: V_SNMP_PORT_THRESHOLDS                                 */
/*==============================================================*/
create or replace view NCC.V_SNMP_PORT_THRESHOLDS as
select 
    b.devip devip,
    f.ifdescr ifdescr,
    c.major major,
d.btime btime,
d.etime etime,
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
    T_LINEPOL_MAP a,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_PERIOD d, 
    T_POLICY_DETAILS e,
    T_PORT_INFO f,
    T_module_info_init g
where 
    a.ptid=f.ptid and f.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid and e.modid=c.modid
 and ( d.defaultflag=1 or a.ppid=d.ppid ) 
    and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='snmp'
    
with read only;

comment on column NCC.V_SNMP_PORT_THRESHOLDS.MAJOR is
'性能名称';

comment on column NCC.V_SNMP_PORT_THRESHOLDS.VALUE_1 is
'阀值1';

comment on column NCC.V_SNMP_PORT_THRESHOLDS.VALUE_2 is
'阀值2';

comment on column NCC.V_SNMP_PORT_THRESHOLDS.SEVERITY_1 is
'阀值1告警级别';

comment on column NCC.V_SNMP_PORT_THRESHOLDS.SEVERITY_2 is
'阀值2告警级别';


drop view NCC.V_SNMP_PDM_THRESHOLDS;

/*==============================================================*/
/* View: V_SNMP_PDM_THRESHOLDS                                  */
/*==============================================================*/
create or replace view NCC.V_SNMP_PDM_THRESHOLDS as
select 
    b.devip devip,
    f.oidname oidname,
    c.major major,
d.btime btime,
d.etime etime,
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
    PREDEFMIB_POL_MAP a,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
   T_POLICY_PERIOD d,
    T_POLICY_DETAILS e,
    PREDEFMIB_INFO f,
    T_MODULE_INFO_INIT g
where 
    a.pdmid=f.pdmid and f.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid 
and ( d.defaultflag=1 or a.ppid=d.ppid )
    and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='snmp'
    
with read only;

comment on column NCC.V_SNMP_PDM_THRESHOLDS.MAJOR is
'性能名称';

comment on column NCC.V_SNMP_PDM_THRESHOLDS.VALUE_1 is
'阀值1';

comment on column NCC.V_SNMP_PDM_THRESHOLDS.VALUE_2 is
'阀值2';

comment on column NCC.V_SNMP_PDM_THRESHOLDS.SEVERITY_1 is
'阀值1告警级别';

comment on column NCC.V_SNMP_PDM_THRESHOLDS.SEVERITY_2 is
'阀值2告警级别';


drop view NCC.V_ICMP_DEV_THRESHOLDS;

/*==============================================================*/
/* View: V_ICMP_DEV_THRESHOLDS                                  */
/*==============================================================*/
create or replace view NCC.V_ICMP_DEV_THRESHOLDS as
select 
    b.devip devip,    
btime btime,
etime etime,
    '0'value0,    /*  default=0 阀值0 THRESHOLDS */  
    e.value_1 value_1, /* 阀值1 */
    e.value_2 value_2, /* 阀值2 */
    'var0'var0,        /* 阀值0内容 VARLIST */
    e.value_1_low, /* 阀值1内容 */
    e.value_2_low, /* 阀值2内容 */
    e.comparetype comparetype,
    e.v1l_severity_1 v1l_severity_1,        /* 阀值0告警级别 内 */
    e.severity_1 severity_1, /* 阀值1告警级别 内 */
    e.severity_2 severity_2, /* 阀值2告警级别 内 */
    e.v1l_severity_A v1l_severity_A, /* 阀值0告警级别 外 */
    e.severity_A severity_A,        /* 阀值0告警级别 外 */
    e.severity_B severity_B,         /* 阀值0告警级别 外 */
    e.filter_A filter_A,
    e.filter_B filter_B
from
    T_DEVPOL_MAP a,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_PERIOD d,
    T_POLICY_DETAILS e,
    T_MODULE_INFO_INIT g
where 
    a.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid 
       and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='icmp'
and ( a.ppid=d.ppid or d.defaultflag=1 and rownum<=1)
with read only;

comment on column NCC.V_ICMP_DEV_THRESHOLDS.VALUE_1 is
'阀值1';

comment on column NCC.V_ICMP_DEV_THRESHOLDS.VALUE_2 is
'阀值2';

comment on column NCC.V_ICMP_DEV_THRESHOLDS.SEVERITY_1 is
'阀值1告警级别';

comment on column NCC.V_ICMP_DEV_THRESHOLDS.SEVERITY_2 is
'阀值2告警级别';

drop view NCC.V_ICMP_PORT_THRESHOLDS;

/*==============================================================*/
/* View: V_ICMP_PORT_THRESHOLDS                                 */
/*==============================================================*/
create or replace view NCC.V_ICMP_PORT_THRESHOLDS as
select 
    b.devip devip, /* still need this one? device ip */
    f.ifip ifip,   /* port ip */
    (case when f.ifip is not null or trim(f.ifip)='' then f.ifip else b.devip end) expip,
btime btime,
etime etime,
    '0'value0,    /*  default=0 阀值0 THRESHOLDS */  
    e.value_1 value_1, /* 阀值1 */
    e.value_2 value_2, /* 阀值2 */
    'var0'var0,        /* 阀值0内容 VARLIST */
    e.value_1_low, /* 阀值1内容 */
    e.value_2_low, /* 阀值2内容 */
    e.comparetype comparetype,
    e.v1l_severity_1 v1l_severity_1,        /* 阀值0告警级别 内 */
    e.severity_1 severity_1, /* 阀值1告警级别 内 */
    e.severity_2 severity_2, /* 阀值2告警级别 内 */
    e.v1l_severity_A v1l_severity_A, /* 阀值0告警级别 外 */
    e.severity_A severity_A,        /* 阀值0告警级别 外 */
    e.severity_B severity_B,         /* 阀值0告警级别 外 */
    e.filter_A filter_A,
    e.filter_B filter_B
from
    T_LINEPOL_MAP a,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    T_POLICY_PERIOD d,
    T_POLICY_DETAILS e,
    T_PORT_INFO f,
    T_MODULE_INFO_INIT g
where 
    a.ptid=f.ptid and f.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid 
       and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='icmp'
 and ( a.ppid=d.ppid or d.defaultflag=1  and rownum<=1 ) 
    
with read only;

comment on column NCC.V_ICMP_PORT_THRESHOLDS.VALUE_1 is
'阀值1';

comment on column NCC.V_ICMP_PORT_THRESHOLDS.VALUE_2 is
'阀值2';

comment on column NCC.V_ICMP_PORT_THRESHOLDS.SEVERITY_1 is
'阀值1告警级别';

comment on column NCC.V_ICMP_PORT_THRESHOLDS.SEVERITY_2 is
'阀值2告警级别';

drop view NCC.V_ICMP_PDM_THRESHOLDS;

/*==============================================================*/
/* View: V_ICMP_PDM_THRESHOLDS                                  */
/*==============================================================*/
create or replace view NCC.V_ICMP_PDM_THRESHOLDS as
select 
    b.devip devip, /* still need this one? device ip */
    f.oidname,   /* port ip */
    (case when oidname is not null then oidname else b.devip end) expip,
btime btime,
etime etime,
    '0'value0,    /*  default=0 阀值0 THRESHOLDS */  
    e.value_1 value_1, /* 阀值1 */
    e.value_2 value_2, /* 阀值2 */
    'var0' var0,        /* 阀值0内容 VARLIST */
    e.value_1_low , /* 阀值1内容 */
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
    PREDEFMIB_POL_MAP a,
    T_DEVICE_INFO b,
    T_EVENT_TYPE_INIT c,
    /* T_POLICY_PERIOD d,*/
    T_POLICY_DETAILS e,
    PREDEFMIB_INFO f,
    T_MODULE_INFO_INIT g
where 
    a.pdmid=f.pdmid and f.devid=b.devid and a.mpid=e.mpid and e.eveid=c.eveid 
/* and ( d.defaultflag=1 or a.ppid=d.ppid )*/
           and e.modid=c.modid and c.modid=g.modid and lower(g.mname)='icmp'
with read only;

comment on column NCC.V_ICMP_PDM_THRESHOLDS.VALUE_1 is
'阀值1';

comment on column NCC.V_ICMP_PDM_THRESHOLDS.VALUE_2 is
'阀值2';

comment on column NCC.V_ICMP_PDM_THRESHOLDS.SEVERITY_1 is
'阀值1告警级别';

comment on column NCC.V_ICMP_PDM_THRESHOLDS.SEVERITY_2 is
'阀值2告警级别';
