--
update t_policy_base pb
  set ptvid=(select ptvc.to_ptvid from V_POLICY_TPLT_VER_CHANGE ptvc where ptvc.from_ptvid=PB.PTVID)
  where PB.PTVID > 0
;