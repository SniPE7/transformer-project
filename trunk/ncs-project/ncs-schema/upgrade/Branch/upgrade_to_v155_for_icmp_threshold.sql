-- �����豸��ICMP Threshold����
create or replace view v_icmp_dev_threshold_apply as
select 
  devip as ipaddress, 
  btime, 
  etime,  
  '0'||(case when value_1_low is not null and trim(length(value_1_low))<>0 then '|'||value_1 end)||(case  when value_2_low is not null and trim(length(value_2_low))<>0 then '|'||value_2 end) as threshold, 
  'value '||comparetype||' threshold' as comparetype,  
  v1l_severity_1||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_1 end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_2 end) as severity1,
  v1l_severity_A||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_A end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_B end) as severity2,
  filter_A as filterflag1,
  filter_B as filterflag2,
  'var0'||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||value_1_low end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||value_2_low end) as varlist,
  '��Pingͨ|Ping��ͨ'||(case when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var1' then '|'||'����������|�����ʳ���ֵ' when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var2' then '|'||'��Ӧʱ��δ����ֵ|��Ӧʱ�䳬��ֵ' end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var1' then '|'||'����������|�����ʳ���ֵ' when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var2' then '|'||'��Ӧʱ��δ����ֵ|��Ӧʱ�䳬��ֵ' end) as summaryCN  
from v_icmp_dev_thresholds
;

-- ���IP��ַ�ظ�������ȡ��һ����¼
--select ipaddress, btime, etime, threshold, comparetype, severity1, severity2, filterflag1, filterflag2, varlist, summaryCN
--from (select v.*,row_number() over(partition by ipaddress order by rownum) cn from v_icmp_dev_threshold_apply v)
--where cn = 1 order by cn desc;

-- �˿ڲ��Ե�ICMP Threshold��Ч����
create or replace view v_icmp_port_threshold_apply as
select 
  ifip as ipaddress, 
  btime, 
  etime,  
  '0'||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||value_1 end)||(case  when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||value_2 end) as threshold,
  'value '||comparetype||' threshold' as comparetype,
  v1l_severity_1||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_1 end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_2 end) as severity1,  
  v1l_severity_A||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||severity_A end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||severity_B end) as severity2,  
  filter_A as filterflag1,
  filter_B as filterflag2,  
  'var0'||(case when value_1_low is not null and trim(length(value_1_low))<>0  then '|'||value_1_low end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  then '|'||value_2_low end) as varlist,  
  '��Pingͨ|Ping��ͨ'||(case when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var1' then '|'||'����������|�����ʳ���ֵ' when value_1_low is not null and trim(length(value_1_low))<>0  and value_1_low='var2' then '|'||'��Ӧʱ��δ����ֵ|��Ӧʱ�䳬��ֵ' end)||(case when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var1' then '|'||'����������|�����ʳ���ֵ' when value_2_low is not null and trim(length(value_2_low))<>0  and value_2_low='var2' then '|'||'��Ӧʱ��δ����ֵ|��Ӧʱ�䳬��ֵ' end) as summaryCN 
from v_icmp_port_thresholds 
where (ifip is not null or trim(ifip) <>'') and ifip not in ( select devip from v_icmp_dev_thresholds );

-- ���IP��ַ�ظ�������ȡ��һ����¼
--select ipaddress, btime, etime, threshold, comparetype, severity1, severity2, filterflag1, filterflag2, varlist, summaryCN
--from (select v.*,row_number() over(partition by ipaddress order by rownum) cn from v_icmp_port_threshold_apply v)
--where cn = 1
--order by cn desc;
