select * from t_grp_net order by gid;

select * from t_list_ip where gid =21078;
select * from t_list_ip where gid in (176366, 176365);

select * from t_service_info;
select * from t_device_domain;

/* the sql below is copied from ncs com.eccom.ncs.dboperate.ResourceCtrl.java #173 */
/* sql =" */
select c.SRNAME,a.PHONE,a.SNMPVERSION,a.DEVID,a.ADMIN,a.RSNO,a.SYSNAME,a.SYSNAMEALIAS,a.DEVIP,
       b.NAME,b.SUBCATEGORY,b.MRNAME,b.MODEL,b.OBJECTID,
       f.NAME as domainname,f.id as domainid  
     from T_DEVICE_INFO a 
left join T_SERVICE_INFO c 
     on a.SRID=c.SRID 
left join T_DEVICE_DOMAIN f 
     on a.DOMAINID=f.id 
left join (select g.*,d.MRNAME,e.NAME 
           from T_DEVICE_TYPE g,
                T_MANUFACTURER_INFO d,
                T_CATEGORY_MAP e 
           where d.MRID=g.MRID and e.ID=g.CATEGORY) b 
      on a.DTID=b.DTID 
/*where " + wherestr;*/
;
/* revise ...*/
select 
       a.DEVID,a.SYSNAME,a.DEVIP,a.SYSNAMEALIAS,a.PHONE,a.SNMPVERSION,a.ADMIN,a.RSNO,
       b.NAME as catename,b.SUBCATEGORY,b.MRNAME,b.MODEL,b.OBJECTID
from  T_DEVICE_INFO a 
left join (select g.*,d.MRNAME,e.NAME 
           from T_DEVICE_TYPE_INIT g,
                T_MANUFACTURER_INFO_INIT d,
                T_CATEGORY_MAP_INIT e 
           where d.MRID=g.MRID and e.ID=g.CATEGORY) b 
      on a.DTID=b.DTID 
;

select LAST_NUMBER task_seq  from user_sequences where sequence_name='TASK_SEQ';
select LAST_NUMBER nm_seq from user_sequences where sequence_name='NM_SEQ';


select * from t_device_type_init;
select count(*) from t_device_type_init;
select count(*) from (select distinct objectid from t_device_type_init ) a;



select * from snmpprivate;
select * from snmppublic;
select * from T_oidgroup_info_init;
select distinct otype from t_oidgroup_info_init;

select * from t_oidgroup_info_init where opid=60680;


select * from t_event_type_init where etid=60680;


select * from t_event_type_init where etid=60680; /*nothing*/
select * from t_event_type_init where eveid=60680;/*yes*/
select * from t_event_type_init where eveid=113773;
select count(*) from t_event_type_init where general<>-1;
select count(*) from t_event_type_init where general=-1;

select * from t_module_info_init;
select * from t_event_oid_init where modid=740 and eveid=60680;


select * from T_module_info;
select * from t_event_type_init where mcode=2; /*error field*/

select distinct ecode from t_event_type_init;
select *  from t_device_type_init;
select count(*) from t_event_type_init where ecode=8;


select m.*, mcode from t_module_info m;


select * from t_oidgroup_info;
select * from t_oidgroup_info_init;
select distinct otype from t_oidgroup_info;


select * from t_policy_period;

