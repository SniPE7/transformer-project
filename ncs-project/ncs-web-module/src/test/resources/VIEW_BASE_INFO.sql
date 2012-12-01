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
'为0的是有效的设备种类';

comment on column NCC.V_MF_CATE_DEVTYPE.DEVICETYPE_MRID is
'厂商ID';

comment on column NCC.V_MF_CATE_DEVTYPE.CATEGORY is
'设备类别';

comment on column NCC.V_MF_CATE_DEVTYPE.SUBCATEGORY is
'设备子类别，例如Cisco 2600系列, 由用户输入, 非数据字典';

comment on column NCC.V_MF_CATE_DEVTYPE.MODEL is
'设备类别的厂商型号，用户输入';

comment on column NCC.V_MF_CATE_DEVTYPE.LOGO is
'预留，未使用';

comment on column NCC.V_MF_CATE_DEVTYPE.DESCRIPTION is
'描述信息';



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
'指标类别（或称为模式类别），表示当前指标的类别，如: snmp,icmp等';

comment on column V_EVENT_TYPE.GENERAL is
'-1表示SNMP Private, 0表示SNMP Public';

comment on column V_EVENT_TYPE.MAJOR is
'性能名称';


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
'1表示设备类
2表示端口类';

comment on column NCC.V_OID_GROUP.OIDVALUE is
'OID';

comment on column NCC.V_OID_GROUP.OIDNAME is
'OID名称';

comment on column NCC.V_OID_GROUP.OIDUNIT is
'数值单位';

comment on column NCC.V_OID_GROUP.OIDINDEX is
'序号';


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
'指标类别（或称为模式类别），表示当前指标的类别，如: snmp,icmp等';

comment on column V_PERFORM_PARAM.GENERAL is
'-1表示SNMP Private, 0表示SNMP Public';

comment on column V_PERFORM_PARAM.MAJOR is
'性能名称';

comment on column V_PERFORM_PARAM.OTYPE is
'1表示设备类
2表示端口类';

comment on column V_PERFORM_PARAM.EVEID is
'性能参数ID';

comment on column V_PERFORM_PARAM.MRID is
'设备类型对应的厂商，字段冗余，建议删除';

comment on column V_PERFORM_PARAM.DTID is
'设备类型ID';

comment on column V_PERFORM_PARAM.OIDGROUPNAME is
'OIDGroupName名称';

comment on column V_PERFORM_PARAM.MODID is
'性能参数的类型（SNMP）,此字段冗余，建议删除';

comment on column V_PERFORM_PARAM.OID is
'预留，未使用';

comment on column V_PERFORM_PARAM.UNIT is
'数值单位，预留，未使用';

comment on column V_PERFORM_PARAM.DESCRIPTION is
'描述';

comment on column V_PERFORM_PARAM.DEVTYPE_MRID is
'厂商ID';

comment on column V_PERFORM_PARAM.CATEGORY is
'设备类别';

comment on column V_PERFORM_PARAM.SUBCATEGORY is
'设备子类别，例如Cisco 2600系列, 由用户输入, 非数据字典';

comment on column V_PERFORM_PARAM.MODEL is
'设备类别的厂商型号，用户输入';

comment on column V_PERFORM_PARAM.LOGO is
'预留，未使用';

comment on column V_PERFORM_PARAM.DEVTYPE_DESCRIPTION is
'描述信息';
