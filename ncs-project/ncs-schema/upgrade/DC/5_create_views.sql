-- POLICY_TEMPLATE 版本变化关系
create or replace view V_POLICY_TPLT_VER_CHANGE as
select distinct
  pt.ptid as ptid,
  pt.mpname as mpname,
  pt.category as category,
  pt.description as description,
  from_ppi.ppiid as from_ppiid,
  from_ptv.ptvid as from_ptvid,
  from_ppi.version as from_version,
  from_ppi.version_tag as from_version_tag,
  to_ptv.ptvid as to_ptvid,
  to_ppi.ppiid as to_ppiid,
  to_ppi.version as to_version,
  to_ppi.version_tag as to_version_tag
from 
  t_policy_publish_info from_ppi inner join t_policy_template_ver from_ptv on from_ppi.ppiid=from_ptv.ppiid 
                                 inner join t_policy_template_ver to_ptv on from_ptv.ptid=to_ptv.ptid
                                 inner join t_policy_publish_info to_ppi on to_ptv.ppiid=to_ppi.ppiid
                                 inner join t_policy_template pt on pt.ptid=from_ptv.ptid 
where
  from_ptv.ptvid<>to_ptv.ptvid
  and to_number(from_ppi.version) < to_number(to_ppi.version)
;

--策略应用的设备类型
create or replace view v_apply_device_type as
 select
  pb.mpid,pb.ptvid,di.dtid,mi.mrid
 from
  t_policy_base pb inner join t_devpol_map dpm on pb.mpid=dpm.mpid
                  inner join t_device_info di on di.devid=dpm.devid
                  inner join t_device_type_init dt on dt.dtid=di.dtid
                  inner join t_manufacturer_info_init mi on dt.mrid=mi.mrid
 where
  pb.ptvid > 0
union
 select
  pb.mpid,pb.ptvid,di.dtid,mi.mrid
 from
  t_policy_base pb inner join t_linepol_map lpm on pb.mpid=lpm.mpid
                  inner join t_port_info pi on pi.ptid=lpm.ptid
                  inner join t_device_info di on di.devid=pi.devid
                  inner join t_device_type_init dt on dt.dtid=di.dtid
                  inner join t_manufacturer_info_init mi on dt.mrid=mi.mrid
 where
  pb.ptvid > 0
union
 select
   pb.mpid,pb.ptvid,di.dtid,mi.mrid
 from
   t_policy_base pb inner join predefmib_pol_map ppm on pb.mpid=ppm.mpid
                  inner join predefmib_info pi on pi.pdmid=ppm.pdmid
                  inner join t_device_info di on di.devid=pi.devid
                  inner join t_device_type_init dt on dt.dtid=di.dtid
                  inner join t_manufacturer_info_init mi on dt.mrid=mi.mrid
 where
  pb.ptvid > 0
;
