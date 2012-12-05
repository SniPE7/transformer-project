alter table BK_SYSLOG_EVENTS_PROCESS modify (PROCESSSUGGEST varchar2(1024), SUMMARYCN  varchar2(1024));
alter table SYSLOG_EVENTS_PROCESS modify (PROCESSSUGGEST varchar2(1024), SUMMARYCN  varchar2(1024));
alter table BK_SNMP_EVENTS_PROCESS modify (WARNMESSAGE varchar2(1024));
alter table SNMP_EVENTS_PROCESS modify (WARNMESSAGE varchar2(1024));
