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
