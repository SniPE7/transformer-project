<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

function saveform1(){
	  if(document.getElementsByName("ppiid").item(0).value == ""){
	    alert("版本不能为空！");
	    return false;
	  }
	  form1.action = "<%=request.getContextPath() %>/secure/policytemplateapply/releasePolicyPublishInfoController.wss";
	  this.form1.submit();
	}

function reloadNavi(){
	window.parent.frames[2].location.reload();
}
</script>
<body <c:if test="${definition.refresh eq 'true'}">onLoad="reloadNavi()"</c:if> class="navtree" style="background-color: #FFFFFF;" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
	<form action="" method="post" id="form1" name="form1">
	  <input type="hidden" name="ppiid" value="${definition.policyPublishInfo.ppiid}">
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
							<TH class="wpsPortletTitle" width="100%">发布下发策略模板集</TH>
						</TR>
						<TR>
							<TD CLASS="wpsPortletArea" COLSPAN="3"><a name="important"></a>

								<h1 id="title-bread-crumb">策略模板集</h1>
								<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" class="button-section">

									<tr valign="top">
										<td class="table-button-section" nowrap>
											<table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
												<tr>
													<td><input type="button" name="button_update" value="发布" class="buttons" onClick="saveform1()" id="functions" /></td>
												</tr>
											</table> 
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
                        <tr>
                          <td>版本<font color="red">*</font></td>
                          <td>&nbsp;${definition.policyPublishInfo.version}
                          </td>
                        </tr>
                        <tr>
                          <td>版本标记<font color="red">*</font></td>
                          <td>&nbsp;${definition.policyPublishInfo.versionTag}
                          </td>
                        </tr>
												<tr>
													<td>备注</td>
													<td><textarea rows="4" cols="20" name="description" style="width: 445px">${definition.policyPublishInfo.description}</textarea></td>
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
</script>
</body>
</html>