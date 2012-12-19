<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/login.css' rel="styleSheet" type="text/css">
<title>列表</title>
<script src="<%=request.getContextPath()%>/js/ajax.js" type="text/javascript" language="JavaScript">
</script>
<script type="text/javascript" language="JavaScript">

function GetXmlHttpObject(){
	var xhreq=null;
	try{
		// Firefox, Opera 8.0+, Safari
		xhreq=new XMLHttpRequest();
	}
	catch (e){
		//Internet Explorer
		try{
			xhreq=new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e){
			xhreq=new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xhreq;
}

function stateChanged1(){ 
	if (xhreq.readyState==4 && xhreq.status==200){ 
		document.getElementById("info").innerHTML=xhreq.responseText;
	}
}
function callback1(){ 
		xhreq=GetXmlHttpObject();
		if (xhreq==null){			
			return;
		}
		var url ="<%=request.getContextPath()%>/secure/output/stateprocess.wss";
		xhreq.open("GET",url,true);
		xhreq.onreadystatechange=stateChanged1 ;
		xhreq.send(null);	
		
}
function stateChanged2(){ 
	if (xhreq2.readyState==4 && xhreq2.status==200){ 
		document.getElementById("info2").innerHTML=xhreq2.responseText;
	}
}
function callback2(){ 
		xhreq2=GetXmlHttpObject();
		if (xhreq2==null){			
			return;
		}
		var url ="<%=request.getContextPath()%>/secure/output/stateexeshell.wss";
		xhreq2.open("GET",url,true);
		xhreq2.onreadystatechange=stateChanged2 ;
		xhreq2.send(null);	
		
}
function applyPolicy(){
 if (window.confirm('确定生成监控配置吗？')) {
   document.getElementById("apply").disabled=true;
   document.getElementById("info").innerHTML="<img border='0' src='../../images/icon_progress.gif' width='16' height='16' > 正在进行生成监控配置，请稍等．．．．．．";  
   Dframe.location.href="<%=request.getContextPath()%>/secure/policyapply/exportxmlfile.wss?ppiid=<c:out value='${definition.policyPublishInfo.ppiid}'/>";
   RenewMessage();
 } else {
   	//	apply.disabled=false;
 }

}

function exeShell(){
  if (window.confirm('确定进行配置生效吗？')){
     document.getElementById("exeshell").disabled=true;
     document.getElementById("info2").innerHTML="<img border='0' src='../../images/icon_progress.gif' width='16' height='16' >正在进行生效，请稍等．．．．．．";
     Dframe.location.href="<%=request.getContextPath()%>/secure/policyapply/exeshell.wss?ppiid=<c:out value='${definition.policyPublishInfo.ppiid}'/>";
     RenewExeShell();
   } else {
   	//	apply.disabled=false;
   }
}

function downloadAppPolicylylog()
{
        Dframe.location.href="<%=request.getContextPath()%>/doservlet/downeffectlog"
}
function Reload(){
    
	 document.getElementById("apply").disabled=false;
   downapplypolicylog.disabled=false;
   document.getElementById("info").innerHTML='';
	 parent.policylist.location.href="<%=request.getContextPath()%>/maintain/effectinfo.jsp?OffSet=1";
}

function RenewMessage(){

	var url ="<%=request.getContextPath()%>/secure/output/stateprocess.wss";
	
	callback1();
	
	setTimeout("RenewMessage()","3000");
}

function RenewExeShell(){

	var url ="<%=request.getContextPath()%>/secure/output/stateexeshell.wss";
	
	callback2();
	
	setTimeout("RenewExeShell()","3000");
}
function updatedo(response){
	document.getElementById("info").innerHTML=response;
}

function updatedo2(response){
	document.getElementById("info2").innerHTML=response;
}
</script>
</head>
<body>
	<iframe src="" name="Dframe" width="0" height="0"></iframe>
	<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
		<TR>
			<TD CLASS="pageTitle">策略管理</TD>
			<TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
		</TR>
	</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
		<TR>
			<TD valign="top">
				<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">
					<TR>
						<TH class="wpsPortletTitle" width="100%">生成监控配置</TH>
					</TR>
					<TBODY ID="wasUniPortlet">
						<TR>
							<TD CLASS="wpsPortletArea" COLSPAN="3"><a name="important"></a>
								<h1 id="title-bread-crumb">生成监控配置</h1>
								<p></p>
								<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" class="button-section">

									<tr valign="top">
										<td class="table-button-section" nowrap>
											<table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
												<tr>
													<td>
													<input type="button" value="生成监控配置" onclick="javascript:applyPolicy()" name="apply" id="apply" class="buttons">
													<input type="button" value="生效" 	onclick="javascript:exeShell()" name="exeshell" id="exeshell" class="buttons">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table> <!-- button section -->
								<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
									<tr valign="top">
										<td class="table-row" nowrap>
											<table align=left>
												<tr>
													<td>
									          <c:if test="${definition.policyPublishInfo != null}"> 
									          当前策略集: [${definition.policyPublishInfo.versionTag}] V[${definition.policyPublishInfo.version}]
									          </c:if>
													</td>
												</tr>
											</table>
											<table  align=center>
											  <tr><td><font color=0000ff><div id=info class="table-row"></div><div id=info2 class="table-row"></div></font></td></tr>
											</table>										
										</td>
									</tr>
								</table> <!-- Data Table -->

								<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">
									<TR>
										<TD CLASS="table-totals" VALIGN="baseline">&nbsp;&nbsp;&nbsp;</TD>
									</TR>
								</TABLE></TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TABLE>
</body>
</html>
