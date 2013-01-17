-- ����Branch���õ�PPIID
-- ��������SQL��ѯfrom_ppiid
create or replace view v_branch_current_ppiid as
  select distinct ppi.ppiid as ppiid from t_policy_base pb inner join t_policy_template_ver ptv on ptv.ptvid=pb.ptvid inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
;
-- select ppiid from v_branch_current_ppiid


-- ����Ŀǰ������PPIID
-- ��������SQL��ѯto_ppiid
create or replace view v_current_released_ppiid as
  select distinct ppi.ppiid as ppiid from t_policy_publish_info ppi where status='R'
;
-- select ppiid from v_current_released_ppiid

-- ��������Ч���豸��Χ(�Ȱ�����ֱ�Ӷ�����豸��Χ, Ҳ������δ���巶Χ�Ĳ������к��ǵ������豸)
create or replace view V_MP_DEVICE_SCOPE as
-- ֱ��ָ�����豸����
(select 
  distinct mpid,devid
from
  t_policy_base pb inner join t_policy_template_scope pts on pts.ptvid=pb.ptvid
                   inner join t_device_info di on di.dtid=pts.dtid
)
union
-- �豸��Χ����ĳ����������豸�ĳ��̱�ʶֱ��ƥ��
(select 
  distinct mpid,devid
from
  t_policy_base pb inner join t_policy_template_scope pts on pts.ptvid=pb.ptvid
                   inner join t_device_info di on di.mrid=pts.mrid
)
union
-- �豸��Χ����ĳ����������豸���ͽ���ƥ��
(select 
  distinct mpid,devid
from
  t_policy_base pb inner join t_policy_template_scope pts on pts.ptvid=pb.ptvid
                   inner join t_device_type_init dt on dt.mrid=pts.mrid
                   inner join t_device_info di on di.dtid=dt.dtid
)
union
-- δ�����豸���ͷ�Χ�Ĳ���(��������豸��Ч)
(select 
  pb.mpid, di.devid
from 
  t_policy_base pb, t_device_info di
where
  pb.ptvid > 0 and
  (select count(*) from t_policy_template_scope pts where pts.ptvid=pb.ptvid) = 0
)
;