-- ICMP
update T_POLICY_EVENT_RULE 
set 
  value_1='33', value_1_rule='rule:{expression:threshold>=13 '||'&'||'&'||' threshold <=83, display:threshold>=13 '||'&'||'&'||' threshold <=83}',
  value_2='44', value_2_rule='rule:{expression:threshold==44, display:阀值固定为44}',
  value_1_low='var1', value_1_low_rule='rule:{expression:threshold>=17 '||'&'||'&'||' threshold <=87, display:threshold>=17 '||'&'||'&'||' threshold <=87}',
  value_2_low='var2', value_2_low_rule='rule:{expression:threshold==88, display:阀值固定为88}',
  severity_1=1, severity_2=2, severity_a=3, severity_b=4, v1l_severity_1=5, v1l_severity_a=5, v2l_severity_2=6, V2L_SEVERITY_B=8
where modid='741'
;

-- ICMP
update T_POLICY_EVENT_RULE 
set 
  value_1='33', value_1_rule='rule:{expression:threshold>=13 '||'&'||'&'||' threshold <=83, display:threshold>=13 '||'&'||'&'||' threshold <=83}',
  value_2='44', value_2_rule='rule:{expression:threshold==44, display:阀值固定为44}',
  value_1_low='77', value_1_low_rule='rule:{expression:threshold>=17 '||'&'||'&'||' threshold <=87, display:threshold>=17 '||'&'||'&'||' threshold <=87}',
  value_2_low='88', value_2_low_rule='rule:{expression:threshold==88, display:阀值固定为88}',
  severity_1=1, severity_2=2, severity_a=3, severity_b=4, v1l_severity_1=5, v1l_severity_a=6, v2l_severity_2=7, V2L_SEVERITY_B=8
where modid='740'
;