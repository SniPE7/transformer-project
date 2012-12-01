<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>

<style type="text/css">


a.current{
background: #FF6600;
}
</style>
<link href='<%=request.getContextPath() %>/include/Styles.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->






<script type="text/javascript" language="javascript">
<!--
var com_ibm_isclite_currentModuleRef="";
var com_ibm_isclite_currentPageRef="";
var com_ibm_isclite_currentNodeType="";
var com_ibm_isclite_currentNodeTitle="";

function setkookie(key, value)
{
var cokie=getwholekookie();
if (cokie == null) 
 {// first time
  document.cookie = "TE3="+key+":"+value+" ; path=/";  
 } else {
  var start = cokie.indexOf(key + ":" );
  if ( start == -1 ) // not in list, so just append
    {
     document.cookie = "TE3="+cokie+" "+key+":"+value+" ; path=/";
    } else { // replace
     var end = cokie.indexOf(" ", start);
     if ( end == -1 ) end = cokie.length;
       document.cookie = "TE3="+cokie.substring(0, start-1)+" "+key+":"+value+" "+
                         cokie.substring(end+1, cokie.length)+" ; path=/";
    }
  
 }
}

function getwholekookie()
{
var len = 4;  // TE3=
var start = document.cookie.indexOf("TE3=");
if ( start == -1)
  { return null; }
var end = document.cookie.indexOf( ";", start );
if ( end == -1 ) end = document.cookie.length;
// this should return N1=E N2=C ....
return unescape( document.cookie.substring(start+len, end) );
}

function getkookie(key)
{
var cookiestr = getwholekookie();
if (cookiestr == null) return null;

var start = cookiestr.indexOf(key + ":" ); // start of the key
if ( start == -1 ) return null; // never found <key>
var value = start + key.length + 1; //begin of the value
var end = cookiestr.indexOf(" ", value); //end of the value
if ( end == -1 ) end = cookiestr.length; 
return cookiestr.substring(value,end); 
}

var dgeid=document.getElementById;
function expandCollapse(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

if (!dgeid) return;
if (document.getElementById(a).style.display=='none') {
 document.getElementById(a).style.display = 'block';
 document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_expanded.gif';
 document.getElementById(g).title = '-';
 document.getElementById(g).alt = '-';
 setkookie(a, "E");
 } else {
 document.getElementById(a).style.display = 'none';
 document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_collapsed.gif';
 document.getElementById(g).title = '+';
 document.getElementById(g).alt = '+';
 setkookie(a, "C");
 }
}

function Collapse(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

document.getElementById(a).style.display = 'none';
document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_collapsed.gif';
document.getElementById(g).title = '+';
document.getElementById(g).alt = '+';
setkookie(a, "C");
}

function Expand(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

document.getElementById(a).style.display = 'block';
document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_expanded.gif';
document.getElementById(g).title = '-';
document.getElementById(g).alt = '-';
setkookie(a, "E");
}

function initAll()
{
base=new String("N");
//var txt="";
for (i=1; i<100; i++) 
 {
	try{
	Collapse(i);
	}
	catch(err){
	//txt+=i+" , ";
	}

 }
 for (i=100; i<1000; i++) 
 {
	try{
	Collapse(i);
	}
	catch(err){
	//txt+=i+" , ";
	}

 }
 for (i=10000; i<11000; i++) 
 {
	try{
	Collapse(i);
	}
	catch(err){
	//txt+=i+" , ";
	}

 }
 //alert ("txt:"+txt);
}

function CollapseAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) Collapse(i);
}

function ExpandAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) Expand(i);
}

function determineAction(e) {
    if (document.layers) {
        document.captureEvents(Event.KEYPRESS);
        document.captureEvents(Event.onClick);
    }
    
    document.onkeypress = function (evt) {
        var key = document.all ? event.keyCode : evt.which ? evt.which : evt.keyCode;
        
        if (key == 13) 
            document.navFilter.submit();
    };

    document.onclick =  function (evt) {
        document.navFilter.submit();
    };
}
window.onload =  function (){
	initAll();        
};

// -->
</script>
<% int nodei=0; %>


<body onLoad="initAll();" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

<div class="navBody" style="background-color: #FFFFFF;">
<table height="26" width="100%" cellspacing="0" cellpadding="0"
	style="background-color: #FFFFFF;">

	<tr>
		<td align="left" colspan="2" class="navtree"
			style="background-color: #FFFFFF">
		<!-- title of the navi welcome page -->
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>基础信息</li>
		</ul>
		<!--<a style='color: #000000; text-decoration: none;' 	
				onclick="javascript:CollapseAll();"			
				href="javascript:CollapseAll();"
				dir="ltr" title="Collapse All">
				<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
				title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>  				
				Collapse All</a>-->


<!--		 manufacturer type info -->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
				onClick="javascript:expandCollapse('<%=nodei %>');" 
				title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'> 
			<a style='color: #000000; text-decoration: none;' 				
				href="<%=request.getContextPath() %>/secure/baseinfo/manufecturers.wss"
				onClick="javascript:expandCollapse('<%=nodei %>');"  
				target="detail" dir="ltr" title="厂商"> 				
				厂商</a></div>
		<div class='nav-child-container' style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>

		<c:import url="/secure/baseinfo/naviManufacture.wss" />  
		</div>

<!--		 category type info -->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="设备类型">
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
				
				title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'> 
							
				设备类型</a></div>
		<div class='nav-child-container' style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'><a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/deviceCategory.wss"
				target="detail" dir="ltr" title="Device types">设备类型</a></li>
		</ul>
		
		</div>
				
<!--	SNMP benchmark	-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="性能指标"> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			性能指标</a></div>

		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/ICMPParam.wss"
				target="detail" dir="ltr"
				title="Device type">
				ICMP性能指标</a></li>
		</ul>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/deviceSnmpParam.wss"
				target="detail" dir="ltr"
				title="Device">
				设备</a></li>
		</ul>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/linePortSnmpParam.wss"
				target="detail" dir="ltr"
				title="line / port">
				线路 / 端口</a></li>
		</ul>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/preDefinedMibIndexSnmpParam.wss"
				target="detail" dir="ltr"
				title="Predefined MIB index">
				私有MIB Index</a></li>
		</ul>		
		</div>
				

<!--	Predefined MIB group	-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="私有MIB group"> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			私有MIB group</a></div>

		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/defaultPolicyPreDefMibIndexDef.wss"
				target="detail" dir="ltr"
				title="Predefined MIB index">
				私有MIB index</a></li>
		</ul>		
		</div>
	
		
<!--	OID groups	-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="OID GROUPs"> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			OID GROUPs</a></div>

		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/deviceoid.wss"
				target="detail" dir="ltr"
				title="Device type">
				设备类型</a></li>
		</ul>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/linePortOid.wss"
				target="detail" dir="ltr"
				title="line / port">
				线路 / 端口</a></li>
		</ul>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/preDefinedMibIndexOid.wss"
				target="detail" dir="ltr"
				title="Predefined MIB index">
				私有MIB Index</a></li>
		</ul>		
		</div>
		
<!--		function specifications info-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
		<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="性能参数关联">
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
				 
				title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
				性能参数关联</a></div>
		<div class='nav-child-container' style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>

		<c:import url="/secure/baseinfo/naviSpecification.wss" />  
		</div>

<% nodei++ ;%>

        
<!--		SNMP Management-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="SNMP events"> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			SNMP管理</a></div>

		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/excel/upload.wss?mode=SNMP"
				target="detail" dir="ltr"
				title="SNMP events type">
		    SNMP性能管理</a></li>
		</ul>
		</div>


<!--		syslog event type-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>

			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="Syslog events"> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			Syslog事件</a></div>
            <div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/excel/uploadsyslog.jsp"
				target="detail" dir="ltr"
				title="Syslog events type">
				Syslog事件管理</a></li>
		</ul>
		</div>
<%--
		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/syslogEventType.jsp"
				target="detail" dir="ltr"
				title="Syslog events type">
				Syslog event type</a></li>
		</ul>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/syslogEventSubType.jsp"
				target="detail" dir="ltr"
				title="Syslog events sub type">
				Syslog event sub type</a></li>
		</ul>
		</div>
--%>
		

<!--	Time frame default policy	-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="默认时段策略"> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			默认时段策略</a></div>

		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
						<li class='navigation-bullet'>				
			<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/policyapply/defaultPolicyPeriodDef.wss"
				target="detail" dir="ltr"
				title="Time Frame Default Ploicy">
			
			默认时段策略</a></li>
		</ul>
		</div>

<!--	Import export base info	-->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title="Import / Export"> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			导入 / 导出</a></div>
		
		 <div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
<%--		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/policyapply/importPolicy.jsp"
				target="detail" dir="ltr"
				title="Policy Import">
		    策略导入</a></li>
		</ul>
		
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/policyapply/exportPolicy.wss?formAction=list"
				target="detail" dir="ltr"
				title="Policy Export">
		    策略导出</a></li>
		</ul>
--%>        
        <ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/importBaseInfo.jsp"
				target="detail" dir="ltr"
				title="BaseInfo Import">
		    基础信息导入</a></li>
		</ul>
		
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/exportBaseInfo.jsp"
				target="detail" dir="ltr"
				title="BaseInfo Export">
		    基础信息导出</a></li>
		</ul>
		</div>
<%--
		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/securey/baseinfo/impExpBaseInfo.jsp"
				target="detail" dir="ltr"
				title="Import / Export">
				Device type</a></li>
		</ul>
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/updateBaseInfo.jsp"
				target="detail" dir="ltr"
				title="SNMP Event Process Management">
				SNMP Event Process Management</a></li>
		</ul>	
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/baseinfo/updateBaseInfo.jsp"
				target="detail" dir="ltr"
				title="Update Base Info">
				Update Base Info</a></li>
		</ul>		
		</div>
--%>

&nbsp;
</td></tr></table></div>
	
</body>
</html>