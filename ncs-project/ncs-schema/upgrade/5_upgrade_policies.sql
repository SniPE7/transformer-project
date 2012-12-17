--insert into t_policy_publish_info(ppiid, version, version_tag, description, status, create_time)
--values('10001', (select max(to_number(version)) + 1 from t_policy_publish_info), '�����ϰ汾Ǩ��', '�����ϰ汾Ǩ�Ƶ����в�����Ϣ', 'D', sysdate);

insert into t_policy_publish_info(ppiid, version, version_tag, description, status, create_time)
values('10001', '1', '�����ϰ汾Ǩ��', '�����ϰ汾Ǩ�Ƶ����в�����Ϣ', 'D', sysdate);

insert into t_policy_template(PTID, MPNAME, STATUS, CATEGORY, DESCRIPTION)
 select mpid, mpname, 'D', category, description from t_policy_base;

insert into t_policy_template_ver(ptvid, pt_version, ptid, ppiid, status,description)
 select mpid, '1', mpid, '10001', 'D', description from t_policy_base;
 
insert into T_POLICY_EVENT_RULE(
  PTVID,
  MODID,
  EVEID,
  POLL,
  VALUE_1,
  VALUE_1_RULE,
  SEVERITY_1,
  FILTER_A,
  VALUE_2,
  VALUE_2_RULE,
  SEVERITY_2,
  FILTER_B,
  SEVERITY_A,
  SEVERITY_B,
  OIDGROUP,
  OGFLAG,
  VALUE_1_LOW,
  VALUE_1_LOW_RULE,
  VALUE_2_LOW,
  VALUE_2_LOW_RULE,
  V1L_SEVERITY_1,
  V1L_SEVERITY_A,
  V2L_SEVERITY_2,
  V2L_SEVERITY_B,
  COMPARETYPE)
 select 
  MPID,
  MODID,
  EVEID,
  POLL,
  VALUE_1,
  '',
  SEVERITY_1,
  FILTER_A,
  VALUE_2,
  '',
  SEVERITY_2,
  FILTER_B,
  SEVERITY_A,
  SEVERITY_B,
  OIDGROUP,
  OGFLAG,
  VALUE_1_LOW,
  '',
  VALUE_2_LOW,
  '',
  V1L_SEVERITY_1,
  V1L_SEVERITY_A,
  V2L_SEVERITY_2,
  V2L_SEVERITY_B,
  COMPARETYPE
 from
  T_POLICY_DETAILS
 where
  MPID in (select ptvid from t_policy_template_ver)
;

insert into t_policy_template_scope(ptvid, dtid)
 select ptv.ptvid, dt.dtid from T_POLICY_TEMPLATE_VER ptv, t_device_type_init dt where ptv.ptvid is not null and dt.dtid is not null
;

-- ����Ϊ����״̬
update t_policy_publish_info set status='R', publish_time=sysdate where ppiid='10001';

-- �޸�ԭ�в���, ���õ�������
update t_policy_base set ptvid=mpid;

commit;
