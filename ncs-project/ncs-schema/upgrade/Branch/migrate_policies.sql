-- ���²���
update t_policy_base pb set ptvid=(select ptvid from t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=ptv.ppiid where ptv.ppiid=(select max(ppiid) from T_POLICY_PUBLISH_INFO where status='R') and pb.mpname=pt.mpname and pb.category=pt.category) where ptvid is null;
-- select ptvid from t_policy_base where ptvid>0;

-- ɾ��������¼�
delete from t_policy_details pd where (mpid,modid,eveid) not in (select (select distinct mpid from t_policy_base where ptvid=per.ptvid), modid,eveid from t_policy_event_rule per where ptvid in (select ptvid from t_policy_template_ver where ppiid=(select max(ppiid) from T_POLICY_PUBLISH_INFO where status='R')))
and mpid in (select mpid from t_policy_base where ptvid>0);

-- ���������ӵ��¼�
insert into t_policy_details
               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE)
select distinct pb.mpid, 
MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE
from
  t_policy_event_rule per inner join t_policy_base pb on pb.ptvid = per.ptvid
where
  (modid, eveid) not in (select modid, eveid from t_policy_details where mpid in (select mpid from t_policy_base where ptvid>0));

-- �޸�ԭ�е��¼�
update
  t_policy_details pd
  set 
    POLL=(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		VALUE_1=(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		SEVERITY_1=(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		FILTER_A=(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		VALUE_2=(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		SEVERITY_2=(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		FILTER_B=(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		SEVERITY_A=(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		SEVERITY_B=(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		OIDGROUP=(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		OGFLAG=(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		VALUE_1_LOW=(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		VALUE_2_LOW=(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		V1L_SEVERITY_1=(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		V1L_SEVERITY_A=(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		V2L_SEVERITY_2=(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		V2L_SEVERITY_B=(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid),
		COMPARETYPE=(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
  where 
  (modid, eveid) in (select modid, eveid from t_policy_details where mpid in (select mpid from t_policy_base where ptvid>0))
  and (
    POLL<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or VALUE_1<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or SEVERITY_1<>(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or FILTER_A<>(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or VALUE_2<>(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or SEVERITY_2<>(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or FILTER_B<>(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or SEVERITY_A<>(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or SEVERITY_B<>(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or OIDGROUP<>(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or OGFLAG<>(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or VALUE_1_LOW<>(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or VALUE_2_LOW<>(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or V1L_SEVERITY_1<>(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or V1L_SEVERITY_A<>(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or V2L_SEVERITY_2<>(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or V2L_SEVERITY_B<>(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		or COMPARETYPE<>(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
		)
;

-- ���
--  ��Ӳ�����ϸ��Ϣ
insert into t_policy_details
               ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE)
select distinct PTVID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE
from
  t_policy_event_rule
where
  ptvid in ( select ptv.ptvid from t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid	where ptv.ppiid=257235100 and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0) )
;

--  ��Ӳ��Զ���
insert into t_policy_base(mpid, ptvid, mpname, category, description)
	select
	  distinct ptv.ptvid, ptv.ptvid, pt.mpname, pt.category, pt.description
	from
	 t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
	                           inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
	where ptv.ppiid=257235100 and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
;
-- select ptvid from t_policy_base where ptvid>0;

-- ɾ�������
delete from t_devpol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=257235100));
delete from t_linepol_map where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=257235100));
delete from predefmib_pol_map  where mpid in (select mpid from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=257235100));
delete from t_policy_base where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=257235100);






-- ��detail�в�ѯ�¼����ݱ仯���¼�
select pb.*, pd.* from t_policy_details pd inner join t_policy_base pb on pb.mpid=pd.mpid
  where 
  (modid, eveid) in (select modid, eveid from t_policy_details d where d.mpid in (select d.mpid from t_policy_base where ptvid>0))
  and pd.mpid in (select d.mpid from t_policy_base d where d.mpid in (select d.mpid from t_policy_base where ptvid>0))
  and (
    POLL<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_1<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_1<>(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or FILTER_A<>(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_2<>(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_2<>(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or FILTER_B<>(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_A<>(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_B<>(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or OIDGROUP<>(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or OGFLAG<>(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_1_LOW<>(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_2_LOW<>(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V1L_SEVERITY_1<>(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V1L_SEVERITY_A<>(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V2L_SEVERITY_2<>(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V2L_SEVERITY_B<>(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or COMPARETYPE<>(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        )
;

-- ��event_rule�в�ѯ�¼����ݱ仯���¼�
select * from t_policy_event_rule where (ptvid, modid, eveid) in
select pb.ptvid, pd.modid, pd.eveid from t_policy_details pd inner join t_policy_base pb on pb.mpid=pd.mpid
  where 
  (modid, eveid) in (select modid, eveid from t_policy_details d where d.mpid in (select d.mpid from t_policy_base where ptvid>0))
  and pd.mpid in (select d.mpid from t_policy_base d where d.mpid in (select d.mpid from t_policy_base where ptvid>0))
  and (
    POLL<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_1<>(select distinct POLL from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_1<>(select distinct SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or FILTER_A<>(select distinct FILTER_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_2<>(select distinct VALUE_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_2<>(select distinct SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or FILTER_B<>(select distinct FILTER_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_A<>(select distinct SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or SEVERITY_B<>(select distinct SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or OIDGROUP<>(select distinct OIDGROUP from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or OGFLAG<>(select distinct OGFLAG from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_1_LOW<>(select distinct VALUE_1_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or VALUE_2_LOW<>(select distinct VALUE_2_LOW from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V1L_SEVERITY_1<>(select distinct V1L_SEVERITY_1 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V1L_SEVERITY_A<>(select distinct V1L_SEVERITY_A from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V2L_SEVERITY_2<>(select distinct V2L_SEVERITY_2 from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or V2L_SEVERITY_B<>(select distinct V2L_SEVERITY_B from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        or COMPARETYPE<>(select distinct COMPARETYPE from t_policy_event_rule per inner join t_policy_base pb on pb.ptvid=per.ptvid where pb.mpid=pd.mpid and per.modid=pd.modid and per.eveid=pd.eveid)
        )
;