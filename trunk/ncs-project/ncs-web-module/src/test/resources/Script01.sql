select * from t_list_ip;
select * from t_grp_net;

select * from t_policy_base where mpname like '%syslog%';

select * from t_policy_base where lower(mpname) like  '%mem%';

select * from t_policy_base where mpid =41704;

select * from t_policy_period;

select count(*) from t_devpol_map;
select * from t_devpol_map;

select count(*) from t_linepol_map;
select * from t_linepol_map;

select count(*) from t_cfgprofile_snmp;
select * from t_cfgprofile_snmp;

select count(*) from t_cfgprofile_tmp;
select * from t_cfgprofile_tmp;

select * from t_category_map;
select * from t_device_info where devid=155072;

select * from t_devpol_map where devid=155072;
select * from t_devpol_map where mpid=55424;

select  count(*) from t_linepol_map;
select * from t_linepol_map;

select * from t_tcpportpol_map;
select * from t_port_info where devid=155072;

select * from t_portline_map ;

select * from t_service_info;

select * from t_device_info  where devid=88836 order by devid,flashfilename;
select * from t_port_info where devid=88836;

select distinct category from t_policy_base;

select distinct nmsip from t_server_info e , t_modgrp_map f, t_module_info g where e.nmsip is not null and e.nmsid =f.nmsid and f.modid=g.modid;
select * from t_server_info;
select * from t_modgrp_map;
select * from t_module_info;
select * from t_svrmod_map;

select * from t_event_type_init where eveid = 0;
select  b.MRID, 	b.DTID, 	a.MODID, 	a.EVEID, 	b.OIDGROUPNAME, 	b.OID, 	b.UNIT, 	b.DESCRIPTION, a.major from t_event_type_init a left join t_event_oid_init b  on a.eveid=b.eveid and  b.dtid=657  where a.general=-1 and a.ecode=1;
select * from t_event_oid_init where dtid=303;
select count(*) from t_event_oid_init; 
update t_event_oid_init set MRID=0, DTID=303, MODID=0, EVEID=0, OIDGROUPNAME='ASS', OID=0, UNIT='aa', DESCRIPTION='aa' where  EVEID=0 and DTID=0;


select count(*) from T_POLICY_BASE;
select * from T_POLICY_BASE where mpid=215355;


select count(*) from t_policy_details;
select * from t_policy_details where mpid=64148;
DELETE FROM T_POLICY_DETAILS WHERE MPID =91075;

select count(*) from t_event_type_init where general=-1;

select count(*) from t_device_info;
select * from t_device_info where devid=203621 or devid=203622;
select * from t_device_info where devid=203622;


select count(*)from t_user;
update t_user set password='YWRtaW4=' where usid=0;
select * from t_user;

select count(*) , bk_time from bk_system_events_process;

