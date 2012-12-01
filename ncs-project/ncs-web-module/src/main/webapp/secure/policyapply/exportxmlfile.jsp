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


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表</title>
<link type="text/css" href="../css/default.css" rel="StyleSheet">
<link type="text/css" href="../basic/default.css" rel="StyleSheet">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript" language="JavaScript">

function applyPolicy(){
   apply.disabled=true;
   if(window.confirm('确定进行配置生效吗？')){

   info.innerHTML='正在进行生效，请稍等．．．．．．';
   Dframe.location.href="<%=request.getContextPath()%>/secure/policyapply/exportxmlfile.wss";
   parent.policylist.location.href="effectinfo.jsp?OffSet=apply";

   }else{
   		apply.disabled=false;
   }

}

function downloadAppPolicylylog()
{
        Dframe.location.href="../doservlet/downeffectlog"
}
function Reload(){
	 apply.disabled=false;
     downapplypolicylog.disabled=false;
     info.innerHTML='';
	 parent.policylist.location.href="../maintain/effectinfo.jsp?OffSet=1";
}

</script>
</head>
<body>

</body>
</html>
