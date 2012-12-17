-- POLICY_TEMPLATE 版本变化关系
create or replace view V_POLICY_TPLT_VER_CHANGE as
select
  pt.ptid as ptid,
  pt.mpname as mpname,
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

--
update t_policy_base pb
  set ptvid=(select ptvc.to_ptvid from V_POLICY_TPLT_VER_CHANGE ptvc where ptvc.from_ptvid=PB.PTVID)
  where PB.PTVID > 0
;