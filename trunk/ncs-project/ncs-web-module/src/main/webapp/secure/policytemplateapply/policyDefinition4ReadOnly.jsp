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
	  document.getElementsByName("formAction").item(0).value= "bind";
	  form1.formAction.value = "bind";
	  form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetailsPDM.wss";
	  this.form1.submit();
	}
function policydetails(){
	  document.getElementsByName("formAction").item(0).value= "bind";
	  form1.formAction.value = "bind";
	  form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetails.wss?mode=ICMP";
	  this.form1.submit();
	}

</script>
<body class="navtree" style="background-color: #FFFFFF;" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
	<form action="" method="post" id="form1" name="form1">
    <input type="hidden" name="readOnly" value="true" /> 
    <input type="hidden" name="ptvid" value="${definition.ptvid}"/>
    <input type="hidden" name="formAction" value="test" />
		<input type="hidden" name="cate" value="${definition.cate}" />
		<input type="hidden" name="insertOrUpdate" value="false" /> 
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
                            <td><input type="submit" name="button_bind" value="查看策略定义" class="buttons" onClick="policydetails9()" id="functions" /></td>
                          </c:if>
                          <c:if test="${definition.cate !=9}">
                            <td><input type="submit" name="button_bind" value="查看策略定义" class="buttons" onClick="policydetails()" id="functions" /></td>
                          </c:if>
                        </tr>
                      </table> 
										</td>
									</tr>
								</table> <!-- button section -->
								<table border="1" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
									<tr valign="top">
										<td class="table-row" nowrap>
											<table style="display: inline; font-size: 95%;" cellspacing="3" cellpadding="5" border="0">
                        <tr>
                          <td>当前策略模板集</td>
                          <td>&nbsp;
                          <c:if test="${definition.currentPolicyPublishInfo != null}">
                            ${definition.currentPolicyPublishInfo.versionTag} -- [版本: ${definition.currentPolicyPublishInfo.version}]
                            <input type="hidden" name="ppiid" value="${definition.currentPolicyPublishInfo.ppiid}" />
                          </c:if> 
                          </td>
                        </tr>
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
												<td>&nbsp; ${definition.mpname}
												</td>
												</tr>
                        <tr>
                          <td>适用的设备类型</td>
                          <td>&nbsp;
                            <table>
                              <tbody>
                                <tr>
                                  <td><select multiple="multiple" size="12" name="selected_device_type_list" style="width: 240px; font-size: x-small;">
                                      <c:forEach var="devType" items="${definition.selectedDeviceTypes}">
                                      <option value="${devType.dtid}">${devType.mrName} - ${devType.model}</option>
                                      </c:forEach>
                                    </select>
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </td>
                        </tr>
												<tr>
													<td>备注</td>
													<td>${definition.policyTemplateVer.description}</td>
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
	<script type="text/javascript">
	if (document.getElementsByName("ptvid").item(0).value == "") {
		document.getElementsByName("button_new").item(0).disabled = true;
		document.getElementsByName("button_bind").item(0).disabled = true;
	}
	</script>
</body>
</html>