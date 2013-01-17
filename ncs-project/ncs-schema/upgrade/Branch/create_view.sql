-- 查找Branch在用的PPIID
-- 方便升级SQL查询from_ppiid
create or replace view v_branch_current_ppiid as
  select distinct ppi.ppiid as ppiid from t_policy_base pb inner join t_policy_template_ver ptv on ptv.ptvid=pb.ptvid inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
;
-- select ppiid from v_branch_current_ppiid


-- 查找目前发布的PPIID
-- 方便升级SQL查询to_ppiid
create or replace view v_current_released_ppiid as
  select distinct ppi.ppiid as ppiid from t_policy_publish_info ppi where status='R'
;
-- select ppiid from v_current_released_ppiid

-- 策略中有效的设备范围(既包含了直接定义的设备范围, 也包含了未定义范围的策略所有涵盖的所有设备)
create or replace view V_MP_DEVICE_SCOPE as
-- 直接指定的设备类型
(select 
  distinct mpid,devid
from
  t_policy_base pb inner join t_policy_template_scope pts on pts.ptvid=pb.ptvid
                   inner join t_device_info di on di.dtid=pts.dtid
)
union
-- 设备范围定义的厂商类型与设备的厂商标识直接匹配
(select 
  distinct mpid,devid
from
  t_policy_base pb inner join t_policy_template_scope pts on pts.ptvid=pb.ptvid
                   inner join t_device_info di on di.mrid=pts.mrid
)
union
-- 设备范围定义的厂商类型与设备类型进行匹配
(select 
  distinct mpid,devid
from
  t_policy_base pb inner join t_policy_template_scope pts on pts.ptvid=pb.ptvid
                   inner join t_device_type_init dt on dt.mrid=pts.mrid
                   inner join t_device_info di on di.dtid=dt.dtid
)
union
-- 未设置设备类型范围的策略(针对所有设备有效)
(select 
  pb.mpid, di.devid
from 
  t_policy_base pb, t_device_info di
where
  pb.ptvid > 0 and
  (select count(*) from t_policy_template_scope pts where pts.ptvid=pb.ptvid) = 0
)
;