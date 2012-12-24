<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<html>
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/include/table.css" media="all">

<script type="text/javascript">
function toSNMP(){
	form1.formAction.value= 'snmp';
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetails.wss?mode=SNMP&readOnly=true";
	this.form1.submit();
}
function toICMP(){
	form1.formAction.value= 'icmp';
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetails.wss?mode=ICMP&readOnly=true";
	this.form1.submit();
}

function toReturn(){
	  form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policyDefinition.wss?cate=${model.cate}&ppiid=${model.ppiid}&ptvid=${model.ptvid}&formAction=view";
	  this.form1.submit();
	}

function listSelected() {
	if (form1.listSeled.value == 1) { //selected
		document.getElementById("unselTab").style.display = 'none';
		document.getElementById("unselCountTab").style.display = 'none';
		document.getElementById("selTab").style.display = 'block';
		document.getElementById("selCountTab").style.display = 'block';
	} else if (form1.listSeled.value == 2) {//unselectd
		document.getElementById("unselTab").style.display = 'block';
		document.getElementById("unselCountTab").style.display = 'block';
		document.getElementById("selTab").style.display = 'none';
		document.getElementById("selCountTab").style.display = 'none';
	} else {//all
		document.getElementById("unselTab").style.display = 'block';
		document.getElementById("unselCountTab").style.display = 'block';
		document.getElementById("selTab").style.display = 'block';
		document.getElementById("selCountTab").style.display = 'block';
	}
}

function onLoadInit() {
	if (form1.selEveType != null)
		listEventType();
	if (form1.formAction.value != 'syslog')
		;
	listSelected();
}
</script>


</head>
<body>


	<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
		<TR>
			<TD CLASS="pageTitle">策略管理</TD>
			<TD CLASS="pageClose"><a href="<%=request.getContextPath() %>/secure/policytemplateapply/policyDefinition.wss?cate=${model.cate }&ptvid=${model.ptvid }" target="detail"
				dir="ltr"> <img border="0" src="../../images/back.gif" width="16" height="16"></a></TD>
		</TR>

	</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
		<TR>
			<TD VALIGN="middle">
				<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">
					<TR>
						<TH class="wpsPortletTitle" width="100%">监控策略定义</TH>
					</TR>
					<TBODY ID="wasUniPortlet">
						<TR>
							<TD CLASS="wpsPortletArea" COLSPAN="3">

								<form action="" method="post" name="form1">
								  <input type="hidden" id="readOnly" value="true">
                  <input type="hidden" id="formAction" value="">
								  <input type="hidden" name="ppiiid" value="${model.ppiiid}" />
                  <input type="hidden" name="ptvid" value="${model.ptvid}" />
								  <input type="hidden" name="cate"  value="${model.cate}" />
									<a name="important"></a>
									<h1 id="title-bread-crumb">
										策略定制--策略名称<input type="hidden" name="mpname" value="${model.mpname}" /> ${model.mpname}
									</h1>

									<a name="main"></a>
									<%
										int countChecked = 0;
									%>
									<!-- button section -->
									<table border="0" cellpadding="3" cellspacing="0" VALIGN="middle" width="100%" summary="Framing Table" CLASS="button-section">

										<tr align="left">
											<td align="left"></td>

										</tr>
										<tr VALIGN="middle">

											<td class="table-button-section" nowrap>

												<table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0" align="left">
													<tr>
														<td><input type="button" name="button.snmp" value="SNMP" onClick="toSNMP();" class="buttons" id="functions" />&nbsp;</td>
														<td><input type="button" name="button.icmp" value="ICMP" onClick="toICMP();" class="buttons" id="functions" />&nbsp;</td>

														<td><input type="button" name="button.new" value="返回" onClick="toReturn();" class="buttons" id="functions" />&nbsp;</td>

														<input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display: none" class="buttons" id="hiddenButton1290018101908118000" />
													</tr>
												</table> <br /> <br /> <c:if test="${model.message != null &&  model.message != ''}">
													<div id="errmsg">
														<fmt:message>${model.message }</fmt:message>
													</div>
												</c:if> <c:if test="${model.messageFromSave != null &&  model.messageFromSave != ''}">
													<div id="errmsg">${model.messageFromSave }</div>
												</c:if>
											</td>
										</tr>
										<tr>
											<td>${model.mode}<input type="hidden" name="mode" value="${model.mode}" /></td>
										</tr>
									</table>



									<!-- Data Table -->

									<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

										<TBODY>
											<TR>
												<TD CLASS="layout-manager" id="notabs"><c:choose>
														<c:when test="${model.mode == 'ICMP'}">
															<div>
																<!-- start of if mode = ICMP -->
																<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="framing-table" id="selTab">
																	<thead>
																		<tr>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="3%">#</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col">事件名称</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">监控时段</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="6%">是否过滤</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值1内容</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值1</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">Ping不通级别</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值2内容</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值2</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值2告警级别</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值3内容</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值3</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值3告警级别</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">阈值比较方式</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">POLL间隔</th>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%" colspan="2">OID Group</th>
																		</tr>
																	</thead>
																	<%
																		countChecked = 0;
																	%>
																	<c:forEach items="${model.details}" var="c1">
																		<tr class="table-row">
																			<td VALIGN="middle" class="collection-table-text" align="center"><%=countChecked + 1%></td>
																			<td VALIGN="middle" class="collection-table-text">${c1.major}</td>
																			<td VALIGN="middle" class="collection-table-text">内<br />外</td>
																			<td VALIGN="middle" class="collection-table-text">
																			  <c:if test="${c1.filterA==1}">是</c:if>
																			  <c:if test="${c1.filterA==0}">否</c:if><br />
																				<c:if test="${c1.filterB==1}">是</c:if>
																				<c:if test="${c1.filterB==0}">否</c:if>
																			</td>
																			<td VALIGN="middle" class="collection-table-text">var0</td>
																			<td VALIGN="middle" class="collection-table-text">0</td>
																			<td VALIGN="middle" class="collection-table-text">
																			  ${c1.v1lseverity1}<br />
																			  ${c1.v1lseverityA}</td>
																			<td VALIGN="middle" class="collection-table-text">
																					<c:if test="${c1.value1low=='var1'}">Ping丢包数目</c:if>
																					<c:if test="${c1.value1low=='var2'}">RTT时间</c:if>
																			</td>
																			<td VALIGN="middle" class="collection-table-text">${c1.value1RuleDisplayInfo}</td>
																			<td VALIGN="middle" class="collection-table-text">
																			  <c:if test="${c1.severity1Null == false}" >${c1.severity1}</c:if><br />
                                        <c:if test="${c1.severityANull == false}" >${c1.severityA}</c:if>
                                      </td>
																			<td VALIGN="middle" class="collection-table-text">
																					<c:if test="${c1.value2low=='var1'}">Ping丢包数目</c:if>
																					<c:if test="${c1.value2low=='var2'}">RTT时间</c:if>
																			</td>
																			<td VALIGN="middle" class="collection-table-text">${c1.value2RuleDisplayInfo}</td>
																			<td VALIGN="middle" class="collection-table-text">
																			<c:if test="${c1.severity2Null == false}" >${c1.severity2}</c:if><br />
                                      <c:if test="${c1.severityBNull == false}" >${c1.severityB}</c:if>
                                      </td>
																			<td VALIGN="middle" class="collection-table-text">${c1.compareType=='=='}</td>
																			<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.pollNull == false}" >${c1.poll}</c:if>秒</td>
																			<td VALIGN="middle" class="collection-table-text" width="3">
																				<c:if test="${c1.oidgroup != null}">是</c:if></td>
																			<td VALIGN="middle" class="collection-table-text">${c1.oidgroup}</td>
																		</tr>

																		<tr class="table-row">
																			<%
																				countChecked++;
																			%>
																	</c:forEach>
																</table>
															</div>
															<!-- end of if mode = ICMP -->
															
														</c:when>
														<c:when test="${model.mode == 'SNMP'}">
															<c:choose>
																<c:when test="${model.cate == 4}">
																	<div>
																		<!-- start of if mode = snmp port policy -->
																		<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS=" framing-table" id="selTab">
																			<thead>
																				<tr>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="3%">#</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col">事件名称</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">监控时段</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="6%">是否过滤</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值In1</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值In1告警级别</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值In2</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值In2告警级别</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值Out1</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值Out1告警级别</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值Out2</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值Out2告警级别</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">阈值比较方式</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">POLL间隔</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%" colspan="2">OID Group</th>
																				</tr>
																			</thead>
																			<%
																				countChecked = 0;
																			%>
																			<c:forEach items="${model.details}" var="c1">
																				<tr class="table-row">
																					<td VALIGN="middle" class="collection-table-text" align="center" rowspan="2"><%=countChecked + 1%></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.major}</td>
																					<td VALIGN="middle" class="collection-table-text">内</td>
																					<td VALIGN="middle" class="collection-table-text">
																					<c:if test="${c1.filterA==1}">是</c:if>
																					<c:if test="${c1.filterA==0}">否</c:if>
																					</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.value1RuleDisplayInfo}</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severity1Null == false}" >${c1.severity1}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.value1LowRuleDisplayInfo}</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.v1lseverity1Null == false}" >${c1.v1lseverity1}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.value2RuleDisplayInfo}</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severity2Null == false}" >${c1.severity2}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.value2LowRuleDisplayInfo}</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.v2lseverity2Null == false}" >${c1.v2lseverity2}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.compareType=='=='}</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><c:if test="${c1.pollNull == false}" >${c1.poll}</c:if> 秒</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2" width="3"><c:if test="${c1.oidgroup != null}">是</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.oidgroup}</td>
																				</tr>

																				<tr class="table-row">
																					<td VALIGN="middle" class="collection-table-text">外</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.filterB==1}">是</c:if><c:if test="${c1.filterB==0}">否</c:if></td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severityANull == false}" >${c1.severityA}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.v1lseverityANull == false}" >${c1.v1lseverityA}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severityBNull == false}" >${c1.severityB}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.v2lseverityBNull == false}" >${c1.v2lseverityB}</c:if></td>
																				</tr>
																				<%
																					countChecked++;
																				%>
																			</c:forEach>
																		</table>
																	</div>
																	<!-- end of if mode = SNMP -->
																</c:when>
																<c:otherwise>
																	<div>
																		<!-- start of if mode = snmp port policy -->

																		<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS=" framing-table" id="selTab">
																			<thead>
																				<tr>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="3%">选中</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col">事件名称</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">监控时段</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="6%">是否过滤</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值1</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值1告警级别</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="2%">阈值2</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="9%">阈值2告警级别</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">阈值比较方式</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%">POLL间隔</th>
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="5%" colspan="2">OID Group</th>
																				</tr>
																			</thead>
																			<%
																				countChecked = 0;
																			%>
																			<c:forEach items="${model.details}" var="c1">
																				<tr class="table-row">
																					<td VALIGN="middle" class="collection-table-text" align="center" rowspan="2"><%=countChecked + 1 %></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2" nowrap="nowrap">${c1.major}</td>
																					<td VALIGN="middle" class="collection-table-text">内</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.filterA==1}">是</c:if><c:if test="${c1.filterA==0}">否</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.value1RuleDisplayInfo}</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severity1Null == false}" >${c1.severity1}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.value2RuleDisplayInfo}</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severity2Null == false}" >${c1.severity2}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.compareType==''}</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><c:if test="${c1.pollNull == false}" >${c1.poll}</c:if> 秒</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2" width="3"><c:if test="${c1.oidgroup != null}">是</c:if></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.oidgroup}</td>
																				</tr>

																				<tr class="table-row">
																					<td VALIGN="middle" class="collection-table-text">外</td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.filterB==1}">是</c:if><c:if test="${c1.filterB==0}">否</c:if></td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severityANull == false}" >${c1.severityA}</c:if></td>
																					<td VALIGN="middle" class="collection-table-text"><c:if test="${c1.severityBNull == false}" >${c1.severityB}</c:if></td>
																				</tr>
																				<%
																					countChecked++;
																				%>
																			</c:forEach>
																		</table>
																	</div>
																	<!-- end of if mode = snmp other policy -->
																</c:otherwise>
															</c:choose>
														</c:when>
													</c:choose></TD>
											</TR>
										</TBODY>
									</TABLE>
								</form>
							</TD>
						</TR>
				</TABLE>
</body>
</html>