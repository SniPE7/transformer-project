drop view NCC.V_MF_CATE_DEVTYPE;

/*==============================================================*/
/* View: V_MF_CATE_DEVTYPE                                      */
/*==============================================================*/
create or replace view NCC.V_MF_CATE_DEVTYPE as
select
    a.ID ID,
    a.NAME NAME,
    a.FLAG FLAG,
    b.MRID MRID,
    b.MRNAME MRNAME,
    b.OBJECTID MF_OBJECTID,
    b.DESCRIPTION MF_DESCRIPTION,
    c.MRID devicetype_MRID,
    c.DTID DTID,
    c.CATEGORY CATEGORY,
    c.SUBCATEGORY SUBCATEGORY,
    c.MODEL MODEL,
    c.OBJECTID OBJECTID,
    c.LOGO LOGO,
    c.DESCRIPTION DESCRIPTION
from
    T_CATEGORY_MAP_INIT a,
    T_MANUFACTURER_INFO_INIT b,
    T_DEVICE_TYPE_INIT c
where 
   a.id=c.category and c.mrid=b.mrid
with read only;

comment on column NCC.V_MF_CATE_DEVTYPE.FLAG is
'Ϊ0������Ч���豸����';

comment on column NCC.V_MF_CATE_DEVTYPE.DEVICETYPE_MRID is
'����ID';

comment on column NCC.V_MF_CATE_DEVTYPE.CATEGORY is
'�豸���';

comment on column NCC.V_MF_CATE_DEVTYPE.SUBCATEGORY is
'�豸���������Cisco 2600ϵ��, ���û�����, �������ֵ�';

comment on column NCC.V_MF_CATE_DEVTYPE.MODEL is
'�豸���ĳ����ͺţ��û�����';

comment on column NCC.V_MF_CATE_DEVTYPE.LOGO is
'Ԥ����δʹ��';

comment on column NCC.V_MF_CATE_DEVTYPE.DESCRIPTION is
'������Ϣ';



drop view V_EVENT_TYPE;

/*==============================================================*/
/* View: V_EVENT_TYPE                                           */
/*==============================================================*/
create or replace view V_EVENT_TYPE as
select
     a.MODID MODID,
     a.EVEID EVEID,
     a.ETID ETID,
     a.ESTID ESTID,
     a.EVEOTHERNAME EVEOTHERNAME,
     a.ECODE ECODE,
     a.GENERAL GENERAL,
     a.MAJOR MAJOR,
     a.MINOR MINOR,
     a.OTHER OTHER,
     a.DESCRIPTION  DESCRIPTION,
     a.USEFLAG USEFLAG,
    b.MODID T_MODULE_INFO_INIT_MODID, /* T_MODULE_INFO_INIT_MODID */
    b.MNAME MNAME,
    b.MCODE MCODE,
    b.DESCRIPTION T_MODULE_INFO_INIT_DESCRIPTION /* T_MODULE_INFO_INIT_DESCRIPTION */
from
    T_EVENT_TYPE_INIT  a,
    T_MODULE_INFO_INIT b  
where 
    a.modid=b.modid 


with read only;

comment on column V_EVENT_TYPE.MODID is
'ָ����𣨻��Ϊģʽ��𣩣���ʾ��ǰָ��������: snmp,icmp��';

comment on column V_EVENT_TYPE.GENERAL is
'-1��ʾSNMP Private, 0��ʾSNMP Public';

comment on column V_EVENT_TYPE.MAJOR is
'��������';


drop view NCC.V_OID_GROUP;

/*==============================================================*/
/* View: V_OID_GROUP                                            */
/*==============================================================*/
create or replace view NCC.V_OID_GROUP as
select
    c.OPID T_OIDGROUP_INFO_INIT_OPID, /* T_OIDGROUP_INFO_INIT_OPID */
    c.OIDGROUPNAME OIDGROUPNAME,
    c.OTYPE OTYPE,
    c.DESCRIPTION DESCRIPTION,
    d.OPID T_OIDGROUP_DETAILS_INIT_OPID, /* T_OIDGROUP_DETAILS_INIT_OPID */
    d.OIDVALUE OIDVALUE,
    d.OIDNAME OIDNAME,
    d.OIDUNIT OIDUNIT,
    d.FLAG FLAG,
    d.OIDINDEX OIDINDEX
from
    T_OIDGROUP_INFO_INIT c,
    T_OIDGROUP_DETAILS_INIT d
where 
    c.opid=d.opid

with read only;

comment on column NCC.V_OID_GROUP.OTYPE is
'1��ʾ�豸��
2��ʾ�˿���';

comment on column NCC.V_OID_GROUP.OIDVALUE is
'OID';

comment on column NCC.V_OID_GROUP.OIDNAME is
'OID����';

comment on column NCC.V_OID_GROUP.OIDUNIT is
'��ֵ��λ';

comment on column NCC.V_OID_GROUP.OIDINDEX is
'���';


drop view V_PERFORM_PARAM;

/*==============================================================*/
/* View: V_PERFORM_PARAM                                        */
/*==============================================================*/
create or replace view V_PERFORM_PARAM as
select
     a.MODID eventtype_MODID,
     a.EVEID eventtype_EVEID,
     a.ETID ETID,
     a.ESTID ESTID,
     a.EVEOTHERNAME EVEOTHERNAME,
     a.ECODE ECODE,
     a.GENERAL GENERAL,
     a.MAJOR MAJOR,
     a.MINOR MINOR,
     a.OTHER OTHER,
     a.DESCRIPTION  eventtype_DESCRIPTION,
     a.USEFLAG USEFLAG,
 
    c.OPID oidgroup_OPID, /* T_OIDGROUP_INFO_INIT_OPID */
    c.OIDGROUPNAME oidgroup_OIDGROUPNAME,
    c.OTYPE OTYPE,
    c.DESCRIPTION oidgroup_DESCRIPTION,

    e.EVEID EVEID,
    e.MRID MRID,
    e.DTID DTID,
    e.OIDGROUPNAME OIDGROUPNAME,
    e.MODID MODID, /* T_EVENT_OID_INIT_MODID */
    e.OID OID,
    e.UNIT UNIT,
    e.DESCRIPTION DESCRIPTION,

    p.MRID devtype_MRID,
    p.DTID devtype_DTID,
    p.CATEGORY CATEGORY,
    p.SUBCATEGORY SUBCATEGORY,
    p.MODEL MODEL,
    p.OBJECTID OBJECTID,
    p.LOGO LOGO,
    p.DESCRIPTION devtype_DESCRIPTION
from
    T_EVENT_TYPE_INIT  a,

    T_OIDGROUP_INFO_INIT c,

    T_EVENT_OID_INIT e,

    T_DEVICE_TYPE_INIT p    
where 
    e.eveid=a.eveid  and e.dtid=p.dtid 
    and e.oidgroupname=c.oidgroupname


with read only;

comment on column V_PERFORM_PARAM.EVENTTYPE_MODID is
'ָ����𣨻��Ϊģʽ��𣩣���ʾ��ǰָ��������: snmp,icmp��';

comment on column V_PERFORM_PARAM.GENERAL is
'-1��ʾSNMP Private, 0��ʾSNMP Public';

comment on column V_PERFORM_PARAM.MAJOR is
'��������';

comment on column V_PERFORM_PARAM.OTYPE is
'1��ʾ�豸��
2��ʾ�˿���';

comment on column V_PERFORM_PARAM.EVEID is
'���ܲ���ID';

comment on column V_PERFORM_PARAM.MRID is
'�豸���Ͷ�Ӧ�ĳ��̣��ֶ����࣬����ɾ��';

comment on column V_PERFORM_PARAM.DTID is
'�豸����ID';

comment on column V_PERFORM_PARAM.OIDGROUPNAME is
'OIDGroupName����';

comment on column V_PERFORM_PARAM.MODID is
'���ܲ��������ͣ�SNMP��,���ֶ����࣬����ɾ��';

comment on column V_PERFORM_PARAM.OID is
'Ԥ����δʹ��';

comment on column V_PERFORM_PARAM.UNIT is
'��ֵ��λ��Ԥ����δʹ��';

comment on column V_PERFORM_PARAM.DESCRIPTION is
'����';

comment on column V_PERFORM_PARAM.DEVTYPE_MRID is
'����ID';

comment on column V_PERFORM_PARAM.CATEGORY is
'�豸���';

comment on column V_PERFORM_PARAM.SUBCATEGORY is
'�豸���������Cisco 2600ϵ��, ���û�����, �������ֵ�';

comment on column V_PERFORM_PARAM.MODEL is
'�豸���ĳ����ͺţ��û�����';

comment on column V_PERFORM_PARAM.LOGO is
'Ԥ����δʹ��';

comment on column V_PERFORM_PARAM.DEVTYPE_DESCRIPTION is
'������Ϣ';
