<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/include/include.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<style type="text/css">
@import "../../dojo/dijit/themes/tundra/tundra.css";
</style>
<script type="text/javascript" src="../dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>
<script type="text/javascript">
dojo.require("dojo.parser");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojox.layout.ContentPane");
</script>

<script type="text/javascript">

function loadBasicInfo(){
	window.open("<%=request.getContextPath() %>/secure/baseinfo/naviBaseInfo.jsp", "navigator");
	window.open("<%=request.getContextPath() %>/secure/baseinfo/manufecturers.wss", "detail");	
	window.close();
}
function loadMaintainDb(){
	window.open("<%=request.getContextPath() %>/secure/maintain/naviDbMaintain.jsp", "navigator");
	window.open("<%=request.getContextPath() %>/secure/maintain/dbmaintain.wss", "detail");	
	window.close();
}
function loadIPAddr(){
	window.open("<%=request.getContextPath() %>/secure/resourceman/naviipnet.wss", "navigator");
	window.open("<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?prepareAdd=add&gid=0", "detail");
	window.close();
}

function loadNetworkDevice(){
	window.open("<%=request.getContextPath() %>/secure/resourceman/naviDevice.wss", "navigator");
	window.open("<%=request.getContextPath() %>/secure/resourceman/deviceWel.jsp", "detail");
	window.close();
}

function loadNetworkMgrmt(){
	window.open("<%=request.getContextPath() %>/secure/resourceman/naviNetworkAdmin.wss", "navigator");
	window.open("<%=request.getContextPath() %>/secure/resourceman/serverManagement.wss", "detail");
	window.close();
}

function loadPollicyTemplateDef(){
    window.open("<%=request.getContextPath() %>/secure/policytemplateapply/naviPolicyDefinition.wss", "navigator");
    window.open("<%=request.getContextPath() %>/secure/policytemplateapply/policyDefinition.wss?cate=1", "detail");
    window.close();
}

function loadPollicyDef(){
	window.open("<%=request.getContextPath() %>/secure/policyapply/naviPolicyDefinition.wss", "navigator");
	window.open("<%=request.getContextPath() %>/secure/policyapply/policyDefinition.wss?cate=1", "detail");
	window.close();
}

function loadPollicyTemplateDef(){
	  window.open("<%=request.getContextPath() %>/secure/policytemplateapply/naviPolicyDefinition.wss", "navigator");
	  window.open("<%=request.getContextPath() %>/secure/policytemaplteapply/policyDefinition.wss?cate=1", "detail");
	  window.close();
	}

function loadPolicyApply(){
	window.open("<%=request.getContextPath() %>/secure/policyapply/naviPolicyApply.wss", "navigator");
	window.open("<%=request.getContextPath() %>/secure/policyapply/policyApplyWel.jsp", "detail");
	window.close();
}

function loadGenerateConfig(){
	window.open("<%=request.getContextPath() %>/secure/output/navigenpolicyfile.jsp", "navigator");
	window.open("<%=request.getContextPath() %>/secure/output/genpolicyfile.jsp", "detail");
	window.close();
}

function onTabChange(){
	var container = dijit.byId("nmsmenu");
	dojo.connect(container,"selectChild",function(child){
		console.log("select c hild",child);
		if(child.id == 'baseinfomenu'){
			loadBasicInfo();
		}
		if(child.id == 'resmenu'){
		   loadIPAddr();
		}
		if(child.id == 'policyapplymenu'){
		   //loadPollicyDef();
		   loadPollicyTemplateDef();
		}
		if(child.id == 'maintainmenu'){
		   loadMaintainDb();
		}
	});
}

</script>


<style type="text/css">
@import "../dojo/dojo/resources/dojo.css";

@import "../dojo/dijit/themes/tundra/tundra.css";

@import "../dojo/dijit/themes/dijit.css";
</style>
<title>banner</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR"
	content="Rational® Application Developer™ for WebSphere® Software">
</head>
<body class="tundra">
<div dojotype="dijit.layout.TabContainer" id="nmsmenu"
	style="height: 100%; background-color: transparent; width: 100%">
<div dojotype="dijit.layout.ContentPane" id="baseinfomenu" title="基础信息" style='background-color: transparent;' >
                <div id="baseinfotab">
                <span class="wpsToolbarBannerBackground"  onClick="loadBasicInfo();">
                	<a  href="#" >基础信息维护</a>
                    <!--<a href="" target="navigator">基础信息维护</a>-->
                </span>
                
                &nbsp;|&nbsp;

                </div>
</div>
<div dojotype="dijit.layout.ContentPane" id="resmenu"  title="资源管理" style='background-color: transparent;'>
                <div id="resourcetab">
                <span class="wpsToolbarBannerBackground" onClick="loadIPAddr();">
                	<a  href="#" > IP地址规划</a>
                    <!--<a href="<%=request.getContextPath() %>/secure/resourceman/naviipnet.wss" target="navigator">IP地址规划</a>-->
                </span>
                &nbsp;|&nbsp;
                <span class="wpsToolbarBannerBackground" onClick="loadNetworkDevice();">
                	<a  href="#">网络设备 </a>
                    <!--<a href="" target="navigator">网络设备</a>-->
                </span>
                &nbsp;|&nbsp;
               
                <span class="wpsToolbarBannerBackground" onClick="loadNetworkMgrmt();">
                	<a  href="#">网管系统规划</a>
                    <!--<a href="" target="navigator">网络系统规划</a>-->
                 </span>
                &nbsp;|&nbsp;
                </div>
          
</div>
<div dojotype="dijit.layout.ContentPane" id="policyapplymenu" title="策略管理" style='background-color: transparent;'>
                <div id="policyapptab">
                
                <span class="wpsToolbarBannerBackground" onClick="loadPollicyTemplateDef();">
                  <a  href="#">策略模板定义</a>
                    <!--<a href="" target="navigator">监控策略定义</a>-->
                 </span>
                &nbsp;|&nbsp;
                <span class="wpsToolbarBannerBackground" onClick="loadPollicyDef();">
                	<a  href="#">监控策略定义</a>
                    <!--<a href="" target="navigator">监控策略定义</a>-->
                 </span>
                &nbsp;|&nbsp;
                <span class="wpsToolbarBannerBackground" onClick="loadPolicyApply();">
                	<a  href="#">策略应用</a>
                    <!--<a href="" target="navigator">策略应用</a>-->
                </span>
                &nbsp;|&nbsp;
                <span class="wpsToolbarBannerBackground" onClick="loadGenerateConfig();">
                	<a  href="#"> 生成监控配置 </a>
                    <!--<a href="" target="navigator">生成监控配置</a>-->
                </span>
                </div>
</div>
<div dojotype="dijit.layout.ContentPane" id="maintainmenu" title="数据库维护" style='background-color: transparent;'>
                <div id="maintaintab">
              
                <span class="wpsToolbarBannerBackground" onclick="loadMaintainDb();">
               <a  href="#" > 数据库维护</a>
<!--                <a href="<%=request.getContextPath() %>/secure/maintain/naviDbMaintain.jsp" target="navigator">数据库维护</a>-->
                </span>
                </div>
</div>
</div>
<script>
	dojo.addOnLoad(function(){
		onTabChange();
	});
</script>
</body>
</html>