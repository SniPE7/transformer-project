
<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<html>
<head>
<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/login.css' rel="styleSheet" type="text/css">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->

<script language="JavaScript" type="text/javascript">

function policydetails9(){
	
	///alert("in policyDefinition details**form.action" + form1.action);
	document.getElementsByName("formAction").item(0).value= "bind";
	form1.formAction.value = "bind";
	//alert("1: form1.bind_btn.type: " + form1.bind_btn.type);
	//alert("in policyDefinition.jsp. testing param mode=icmp & mpname=" + form1.mpname.value);
	form1.action = "<%=request.getContextPath()%>/secure/policyapply/policydetailsPDM.wss";
	this.form1.submit();
}
function policydetails(){
	
	///alert("in policyDefinition details**form.action" + form1.action);
	document.getElementsByName("formAction").item(0).value= "bind";
	form1.formAction.value = "bind";
	//alert("1: form1.bind_btn.type: " + form1.bind_btn.type);
	//alert("in policyDefinition.jsp. testing param mode=icmp & mpname=" + form1.mpname.value);
	form1.action = "<%=request.getContextPath()%>/secure/policyapply/policydetails.wss?mode=ICMP";
	this.form1.submit();
}
function addform1(){	

//alert("in policyDefinition addform**form.action" + form1.action);
	document.getElementsByName("mpname").item(0).value = "";
	document.getElementsByName("mpid").item(0).value = "";
	document.getElementsByName("description").item(0).value = "";
	//disable "New" button
	document.getElementsByName("button_new").item(0).disabled = true;	
	document.getElementsByName("button_bind").item(0).disabled = true;
}
function saveform1(){
		
//alert("in policyDefinition save **form.action" + form1.action);
	if(document.getElementsByName("button_new").item(0).disabled == true){
		document.getElementsByName("formAction").item(0).value= "add";
		form1.formAction.value = "add";
	}else{
		form1.formAction.value = "save";
		document.getElementsByName("formAction").item(0).value= "save";
	}
	
	if(document.getElementsByName("mpname").item(0).value == ""){
		alert("策略名称不能为空！");
		if(form1.formAction.value == "add"){
			document.getElementsByName("mpname").item(0).value = "";
			document.getElementsByName("mpid").item(0).value = "";
			document.getElementsByName("description").item(0).value = "";
			//disable "New" button
			document.getElementsByName("button_new").item(0).disabled = true;	
			document.getElementsByName("button_bind").item(0).disabled = true;
		}
		return false;
	}
	document.getElementsByName("insertOrUpdate").item(0).value="true";
	form1.action = "<%=request.getContextPath()%>/secure/policyapply/policyDefinition.wss";
	this.form1.submit();
	document.getElementsByName("button_new").item(0).disabled = false;
}

function deleteform1(){
	
	if(document.getElementsByName("mpname").item(0).value == "" || document.getElementsByName("mpid").item(0).value == 0){
		alert("没有可以删除的数据！");
		
		document.getElementsByName("button_new").item(0).disabled = true;	
		document.getElementsByName("button_bind").item(0).disabled = true;
		return false;
	}
	
	var vv=confirm("确认删除该条记录?");
   	if(vv){
   		document.getElementsByName("formAction").item(0).value= "delete";
		form1.formAction.value = "delete";
		form1.action = "<%=request.getContextPath()%>/secure/policyapply/policyDefinition.wss";
		this.form1.submit();		
   }
		document.getElementsByName("button_new").item(0).disabled = true;	
		document.getElementsByName("button_bind").item(0).disabled = true;
}
function reloadNavi(){

	
	window.parent.frames[2].location.reload();
	//alert("in policyDefinition **form.action" + form1.action);
	
}


</script>

<body <c:if test="${definition.refresh eq 'true'}">onLoad="reloadNavi()"</c:if> class="navtree" style="background-color: #FFFFFF;" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
	<form action="" method="post" id="form1" name="form1">

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
							<TH class="wpsPortletTitle" width="100%">监控策略定义</TH>
						</TR>
						<TR>
							<TD CLASS="wpsPortletArea" COLSPAN="3"><a name="important"></a>
								<h1 id="title-bread-crumb">策略</h1>
								<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" class="button-section">
									<tr valign="top">
										<td class="table-button-section" nowrap>
											<table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
												<tr>
													<c:if test="${definition.cate ==9}">
														<td><input type="submit" name="button_bind" value="策略定制" class="buttons" onClick="policydetails9()" id="functions" /></td>
													</c:if>
													<c:if test="${definition.cate !=9}">
														<td><input type="submit" name="button_bind" value="策略定制" class="buttons" onClick="policydetails()" id="functions" /></td>
													</c:if>
													<td><input type="button" name="button_update" value="保存" class="buttons" onClick="saveform1()" id="functions" /></td>
													<td>
                        <c:if test="${definition.policybase.polictTemplateVer == null}">
													  <input type="submit" name="button_delete" value="删除" class="buttons" onClick="deleteform1()" id="functions" />
												</c:if>
													</td>
												</tr>
											</table> <input type="hidden" name="formAction" value="test" />
										</td>
									</tr>
								</table> <!-- button section -->
								<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
									<tr valign="top">
										<td class="table-row" nowrap>
											<table style="display: inline; font-size: 95%;" cellspacing="3" cellpadding="5" border="0">
												<tr>
												</tr>
												<tr>
												<tr>
													<c:if test="${definition.message != null &&  definition.message != ''}">
														<div id="errmsg">
															<fmt:message>${definition.message }</fmt:message>
														</div>
													</c:if>
												</tr>
												<tr></tr>
                        <c:if test="${definition.policybase.polictTemplateVer != null}">
                        <tr>
                          <td>当前策略模板集</td>
                          <td>&nbsp;
                            [${definition.policybase.polictTemplateVer.policyPublishInfo.versionTag}] V[${definition.policybase.polictTemplateVer.policyPublishInfo.version}]
                          </td>
                        </tr>
                        </c:if> 
                        <tr>
                          <td>策略类型</td>
                          <td>&nbsp; 
                          <c:if test="${definition.cate ==9}">私有MIB策略</c:if>
                          <c:if test="${definition.cate ==4}">端口策略</c:if>
                          <c:if test="${definition.cate ==1}">设备策略</c:if>
                          </td>
                        </tr>
                        <tr>
												<td>策略名称<font color="red">*</font></td>
												<td>&nbsp; 
                        <c:if test="${definition.policybase.polictTemplateVer == null}">
												  <input type="text" name="mpname" value="${definition.policybase.mpname}" style="width: 245px" />
												</c:if>
                        <c:if test="${definition.policybase.polictTemplateVer != null}">
                          <input type="hidden" name="mpname" value="${definition.policybase.mpname}" style="width: 245px" />
                          ${definition.policybase.mpname}
                        </c:if>
												  <input type="hidden" name="cate" value="${definition.cate}" />
												  <input type="hidden" name="mpid" value="${definition.mpid}" />
                          <input type="hidden" name="delete1" value="false" />
                          <input type="hidden" name="insertOrUpdate" value="false" />
												</td>
												</tr>
												<tr>
													<td>备注</td>
													<td><textarea rows="4" cols="20" name="description" style="width: 245px">${definition.policybase.description}</textarea></td>
												</tr>
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
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
	<script language="JavaScript" type="text/javascript">
	if(document.getElementsByName("mpid").item(0).value == ""){
		document.getElementsByName("button_new").item(0).disabled = true;	
		document.getElementsByName("button_bind").item(0).disabled = true;
	}
</script>
</body>
</html>