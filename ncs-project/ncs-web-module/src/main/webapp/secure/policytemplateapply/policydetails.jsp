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
<meta name="GENERATOR" content="Rational® Application Developer™ for WebSphere® Software">
<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/include/table.css" media="all">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/jquery-ui-1.9.2.custom/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" />
    <script src="<%=request.getContextPath()%>/jquery-ui-1.9.2.custom/js/jquery-1.8.3.js"></script>
    <script src="<%=request.getContextPath()%>/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js"></script>
    
    <script src="<%=request.getContextPath()%>/js/policy_rule_dialog.js"></script>

<script type="text/javascript">
function addform1(){
	form1.formAction.value= 'add';
	
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/savepolicydetails.wss";
	this.form1.submit();
}

function toSNMP(){
	form1.formAction.value= 'snmp';
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetails.wss?mode=SNMP";
	this.form1.submit();
}
function toICMP(){
	form1.formAction.value= 'icmp';
	
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetails.wss?mode=ICMP";
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
	function listEventType() {
		N = new String("syslogSection");
		eveValue = form1.selEveType.value;
		a = N.concat(eveValue);
		//alert("in listevent type js: selected event type is : " + eveValue);
		if (eveValue == 0) {
			for (i = 1; i <= 8; i++) {
				document.getElementById(N.concat(i)).style.display = 'block';
			}
		} else {
			document.getElementById(a).style.display = 'block';
			for (i = 1; i <= 8; i++) {
				if (i != eveValue)
					document.getElementById(N.concat(i)).style.display = 'none';
			}
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

								<form action="<%=request.getContextPath()%>/secure/policytemplateapply/savepolicydetails.wss" method="post" name="form1">
								  <input type="hidden" id="rule_checkbox_index" value="">
                  <input type="hidden" id="rule_checkbox_elementPrefixelementPrefix" value="">
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

												<table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0" align="center">
													<tr>
														<td><input type="button" name="button.snmp" value="SNMP" onClick="toSNMP();" class="buttons" id="functions" />&nbsp;</td>
														<td><input type="button" name="button.icmp" value="ICMP" onClick="toICMP();" class="buttons" id="functions" />&nbsp;</td>

														<td><input type="submit" name="button.new" value="保存" class="buttons" id="functions" />&nbsp;</td>

														<input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display: none" class="buttons" id="hiddenButton1290018101908118000" />
													</tr>
												</table> <br /> <br /> <c:if test="${model.message != null &&  model.message != ''}">
													<div id="errmsg">
														<fmt:message>${model.message }</fmt:message>
													</div>
												</c:if> <c:if test="${model.messageFromSave != null &&  model.messageFromSave != ''}">
													<div id="errmsg">${model.messageFromSave }</div>
												</c:if> <input type="hidden" name="formAction" value="save" /> <input type="hidden" name="ptvid" value="${model.ptvid}" /> <input type="hidden" name="cate"
												value="${model.cate}" />
											</td>
										</tr>
										<tr class="table-row">
											<td class="collection-table-text"><c:if test="${model.mode!='SYSLOG'}">
													<select name="listSeled" onChange="listSelected();" id="listSel">
														<option value="0" <c:if test="${model.displayOption==0}">selected="selected"</c:if>>All</option>
														<option value="1" <c:if test="${model.displayOption==1}">selected="selected"</c:if>>Selected</option>
														<option value="2" <c:if test="${model.displayOption==2}">selected="selected"</c:if>>Unselected</option>
													</select>&nbsp;&nbsp;
			</c:if> <c:if test="${model.mode=='SYSLOG'}">
													<select name="listeve_type" onChange="listEventType();" id="selEveType">
														<option value="0" <c:if test="${model.selectedEveType==0}">selected="selected"</c:if>>All</option>
														<c:if test="${model.cate == 4 or model.cate == 9 }">
															<option value="1" <c:if test="${model.selectedEveType== 1 }">selected="selected"</c:if>>广域网端口事件</option>
															<option value="2" <c:if test="${model.selectedEveType == 2 }">selected="selected"</c:if>>局域网端口类事件</option>
														</c:if>
														<c:if test="${model.cate ==1}">
															<option value="3" <c:if test="${model.selectedEveType == 3 }">selected="selected"</c:if>>网络设备类事件</option>
															<option value="4" <c:if test="${model.selectedEveType == 4 }">selected="selected"</c:if>>SNASW事件</option>
															<option value="5" <c:if test="${model.selectedEveType == 5 }">selected="selected"</c:if>>路由事件</option>
															<option value="6" <c:if test="${model.selectedEveType == 6 }">selected="selected"</c:if>>阀值事件</option>
															<option value="7" <c:if test="${model.selectedEveType == 7 }">selected="selected"</c:if>>安全事件</option>
															<option value="8" <c:if test="${model.selectedEveType == 8 }">selected="selected"</c:if>>其他类事件</option>
														</c:if>
													</select>&nbsp;
                </c:if></td>
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
																<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS=" framing-table" id="selTab">
																	<thead>
																		<tr>
																			<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="3%">选中</th>
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
																			<td VALIGN="middle" class="collection-table-text" align="center"><input type="checkbox" name="sel"
																				value="<%=countChecked %>|${c1.modid}|${c1.eveid}|${c1.ptvid}" checked="checked" /> <input type="hidden" name="pre" value="${c1.ptvid}|${c1.modid}|${c1.eveid}" />
																				<input type="hidden" name="modid" size="5" value="${c1.modid}" /> <input type="hidden" name="eveid" size="5" value="${c1.eveid}" /></td>
																			<td VALIGN="middle" class="collection-table-text">${c1.major}</td>
																			<td VALIGN="middle" class="collection-table-text">内<br />外
																			</td>
																			<td VALIGN="middle" class="collection-table-text"><select name="filterA" id="filter_A" class="collection-table-text">
																					<option value="1" <c:if test="${c1.filterA==1}">selected="selected"</c:if>>是</option>
																					<option value="0" <c:if test="${c1.filterA==0}">selected="selected"</c:if>>否</option>
																			</select>&nbsp;<br /> <select name="filterB" id="filter_B" class="collection-table-text">
																					<option value="1" <c:if test="${c1.filterB==1}">selected="selected"</c:if>>是</option>
																					<option value="0" <c:if test="${c1.filterB==0}">selected="selected"</c:if>>否</option>
																			</select>&nbsp;</td>
																			<td VALIGN="middle" class="collection-table-text">var0</td>
																			<td VALIGN="middle" class="collection-table-text">0</td>
																			<td VALIGN="middle" class="collection-table-text">
																			  <input type="text" name="v1lseverity1" size="3" value="${c1.v1lseverity1}" style="width: 30px;"/><br />
																			  <input type="text" name="v1lseverityA" size="3" value="${c1.v1lseverityA}"  style="width: 30px;"/></td>
																			<td VALIGN="middle" class="collection-table-text">
																			  <select name="value1low" id="value_1_low">
																					<option value="">-请选择-</option>
																					<option value="var1" <c:if test="${c1.value1low=='var1'}">selected="selected"</c:if>>Ping丢包数目</option>
																					<option value="var2" <c:if test="${c1.value1low=='var2'}">selected="selected"</c:if>>RTT时间</option>
																			  </select>
																			</td>
																			<td VALIGN="middle" class="collection-table-text">
																			  <input type="text" name="value1" id="value1_<%=countChecked %>" size="5" value="${c1.value1}"  style="width: 30px;"/> 
                                        <input type="text" name="value1Rule" id="value1_Rule_<%=countChecked %>" value="${c1.value1Rule}" style="width: 30px;"/>
                                        <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1');" class="text">定义规则</a>
																			</td>
																			<td VALIGN="middle" class="collection-table-text">
																			  <input type="text" name="severity1" size="5" value="<c:if test="${c1.severity1Null == false}" >${c1.severity1}</c:if>"  style="width: 30px;"/><br />
                                        <input type="text" name="severityA" size="5" value="<c:if test="${c1.severityANull == false}" >${c1.severityA}</c:if>"  style="width: 30px;"/>
                                      </td>
																			<td VALIGN="middle" class="collection-table-text"><select name="value2low" id="value_2_low">
																					<option value="">-请选择-</option>
																					<option value="var1" <c:if test="${c1.value2low=='var1'}">selected="selected"</c:if>>Ping丢包数目</option>
																					<option value="var2" <c:if test="${c1.value2low=='var2'}">selected="selected"</c:if>>RTT时间</option>
																			</select></td>
																			<td VALIGN="middle" class="collection-table-text">
																			  <input type="text" name="value2" id="value2_<%=countChecked %>" size="5" value="${c1.value2}"  style="width: 30px;"/>
                                        <input type="text" name="value2Rule" id="value2_Rule_<%=countChecked %>" value="${c1.value2Rule}" style="width: 30px;"/>
                                        <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2');" class="text">定义规则</a>
																			</td>
																			<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity2" size="5"
																				value="<c:if test="${c1.severity2Null == false}" >${c1.severity2}</c:if>"  style="width: 30px;"/><br /> <input type="text" name="severityB" size="5"
																				value="<c:if test="${c1.severityBNull == false}" >${c1.severityB}</c:if>"  style="width: 30px;"/></td>
																			<td VALIGN="middle" class="collection-table-text"><select name="compareType" id="compare_Type">
																					<option value="NULL" <c:if test="${c1.compareType==null}">selected="selected"</c:if>>-请选择-</option>
																					<option value="==" <c:if test="${c1.compareType=='=='}">selected="selected"</c:if>>==</option>
																					<option value="!=" <c:if test="${c1.compareType=='!='}">selected="selected"</c:if>>!=</option>
																					<option value="&lt;" <c:if test="${c1.compareType=='<'}">selected="selected"</c:if>>&lt;</option>
																					<option value="&gt" <c:if test="${c1.compareType=='>'}">selected="selected"</c:if>>&gt;</option>
																					<option value="&lt;=" <c:if test="${c1.compareType=='<='}">selected="selected"</c:if>>&lt;=</option>
																					<option value="&gt;=" <c:if test="${c1.compareType=='>='}">selected="selected"</c:if>>&gt;=</option>
																					<option value="Like" <c:if test="${c1.compareType=='Like'}">selected="selected"</c:if>>Like</option>
																					<option value="Not Like" <c:if test="${c1.compareType=='Not Like'}">selected="selected"</c:if>>Not Like</option>
																			</select></td>
																			<td VALIGN="middle" class="collection-table-text"><input type="text" name="poll" size="5" value="<c:if test="${c1.pollNull == false}" >${c1.poll}</c:if>"  style="width: 40px;"/>
																				秒</td>
																			<td VALIGN="middle" class="collection-table-text" width="3"><input type="checkbox" name="oidgroupSel" value="<%=countChecked %>"
																				<c:if test="${c1.oidgroup != null}">checked="checked"</c:if> /></td>
																			<td VALIGN="middle" class="collection-table-text"><input type="text" name="oidgroup" size="5" value="${c1.oidgroup}" /></td>
																		</tr>

																		<tr class="table-row">
																			<%
																				countChecked++;
																			%>
																	</c:forEach>
																</table>

																<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function" id="selCountTab">
																	<TR>
																		<TD CLASS="table-totals" VALIGN="baseline">Total Selected ${fn:length(model.details)} &nbsp;&nbsp;&nbsp;</TD>
																	</TR>
																</TABLE>

																<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table" id="unselTab">

																	<TBODY>
																		<TR>
																			<TD CLASS="layout-manager" id="notabs">

																				<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS=" framing-table">
																					<thead>
																						<tr>
																							<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="3%">选中</th>
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

																					<c:forEach items="${model.unselected}" var="d1">

																						<tr class="table-row">
																							<td VALIGN="middle" class="collection-table-text"><input type="checkbox" name="sel" value="<%=countChecked %>|${d1.modid}|${d1.eveid}" /> <input
																								type="hidden" name="modid" size="5" value="${d1.modid}" /> <input type="hidden" name="eveid" size="5" value="${d1.eveid}" /></td>
																							<td VALIGN="middle" class="collection-table-text">${d1.major}</td>
																							<td VALIGN="middle" class="collection-table-text">内<br />外
																							</td>
																							<td VALIGN="middle" class="collection-table-text"><select name="filterA" id="filter_A">
																									<option value="0">否</option>
																									<option value="1">是</option>
																							</select><br /> <select name="filterB" id="filter_B">
																									<option value="1">是</option>
																									<option value="0">否</option>
																							</select></td>
																							<td VALIGN="middle" class="collection-table-text">var0</td>
																							<td VALIGN="middle" class="collection-table-text">0</td>
																							<td VALIGN="middle" class="collection-table-text"><input type="text" name="v1lseverity1" size="5" value="" style="width: 30px;"/><br />
																							<input type="text" name="v1lseverityA" size="5" value="" style="width: 30px;"/></td>
																							<td VALIGN="middle" class="collection-table-text"><select name="value1low" id="value_1_low">
																									<option value="">-请选择-</option>
																									<option value="var1">Ping丢包数目</option>
																									<option value="var2">RTT时间</option>
																							</select></td>
																							<td VALIGN="middle" class="collection-table-text">
																							  <input type="text" name="value1" id="value1_<%=countChecked %>" size="5" value="" style="width: 30px;"/>
                                                <input type="text" name="value1Rule" id="value1_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
																							  <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1');" class="text">定义规则</a>
																							</td>
																							<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity1" size="5" value="" style="width: 30px;"/><br /> <input type="text" name="severityA"
																								size="5" value="" style="width: 30px;"/></td>
																							<td VALIGN="middle" class="collection-table-text"><select name="value2low" id="value_2_low">
																									<option value="">-请选择-</option>
																									<option value="var1">Ping丢包数目</option>
																									<option value="var2">RTT时间</option>
																							</select></td>
																							<td VALIGN="middle" class="collection-table-text">
																							  <input type="text" name="value2" id="value2_<%=countChecked %>" size="5" value="" style="width: 30px;"/>
                                                <input type="text" name="value2Rule" id="value2_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
                                                <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2');" class="text">定义规则</a>
																							</td>
																							<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity2" size="5" value="" style="width: 30px;"/><br />
																							<input type="text" name="severityB" size="5" value="" style="width: 30px;"/></td>
																							<td VALIGN="middle" class="collection-table-text"><select name="compareType" id="compare_Type">
																									<option value="null">-请选择-</option>
																									<option value="==">==</option>
																									<option value="!=">!=</option>
																									<option value="&lt;">&lt;</option>
																									<option value="&gt;">&gt;</option>
																									<option value="&lt;=">&lt;=</option>
																									<option value="&gt;=">&gt;=</option>
																									<option value="Like">Like</option>
																									<option value="Not Like">Not Like</option>
																							</select></td>
																							<td VALIGN="middle" class="collection-table-text"><input type="text" name="poll" size="5" value="300" style="width: 40px;"/>秒</td>
																							<td VALIGN="middle" class="collection-table-text" width="3"><input type="checkbox" name="oidgroupSel" value="<%=countChecked%>" /></td>
																							<td VALIGN="middle" class="collection-table-text"><input type="text" name="oidgroup" size="5" value="" /></td>
																						</tr>
																						<%
																							countChecked++;
																						%>
																					</c:forEach>
																				</table>

																				<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function" id="unselCountTab">

																					<TR>


																						<TD CLASS="table-totals" VALIGN="baseline">Total Unselected ${fn:length(model.unselected)} &nbsp;&nbsp;&nbsp;</TD>
																					</TR>

																				</TABLE>
																			</TD>
																		</TR>
																	</TBODY>
																</TABLE>

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
																					<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="3%">选中</th>
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
																					<td VALIGN="middle" class="collection-table-text" align="center" rowspan="2"><input type="checkbox" name="sel"
																						value="<%=countChecked %>|${c1.modid}|${c1.eveid}|${c1.ptvid}" checked="checked" /> <input type="hidden" name="pre" value="${c1.ptvid}|${c1.modid}|${c1.eveid}" />
																						<input type="hidden" name="modid" size="5" value="${c1.modid}" /> <input type="hidden" name="eveid" size="5" value="${c1.eveid}" /></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.major}</td>
																					<td VALIGN="middle" class="collection-table-text">内</td>
																					<td VALIGN="middle" class="collection-table-text"><select name="filterA" id="filter_A">
																							<option value="1" <c:if test="${c1.filterA==1}">selected="selected"</c:if>>是</option>
																							<option value="0" <c:if test="${c1.filterA==0}">selected="selected"</c:if>>否</option>
																					</select></td>

																					<td VALIGN="middle" class="collection-table-text" rowspan="2">
																					  <input type="text" name="value1" id="value1_<%=countChecked %>" size="5" value="${c1.value1}" style="width: 30px;"/>
                                            <input type="text" name="value1Rule" id="value1_Rule_<%=countChecked %>" value="${c1.value1Rule}" style="width: 30px;"/>
                                            <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1');" class="text">定义规则</a>
																					</td>
																					<td VALIGN="middle" class="collection-table-text">
																					  <input type="text" name="severity1" size="5" value="<c:if test="${c1.severity1Null == false}" >${c1.severity1}</c:if>" style="width: 30px;" style="width: 30px;"/>
																					</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">
																					  <input type="text" name="value1low" id="value1low_<%=countChecked %>" size="5" value="${c1.value1low}" style="width: 30px;"/>
                                            <input type="text" name="value1lowRule" id="value1low_Rule_<%=countChecked %>" value="${c1.value1LowRule}" style="width: 30px;"/>
                                            <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1low');" class="text">定义规则</a>
																					</td>
																					<td VALIGN="middle" class="collection-table-text">
																					  <input type="text" name="v1lseverity1" size="5"	value="<c:if test="${c1.v1lseverity1Null == false}" >${c1.v1lseverity1}</c:if>" style="width: 30px;" style="width: 30px;"/>
																					</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">
																					  <input type="text" name="value2" id="value2_<%=countChecked %>" size="5" value="${c1.value2}" style="width: 30px;"/>
                                            <input type="text" name="value2Rule" id="value2_Rule_<%=countChecked %>" value="${c1.value2Rule}" style="width: 30px;"/>
                                            <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2');" class="text">定义规则</a>
																					</td>
																					<td VALIGN="middle" class="collection-table-text">
																					  <input type="text" name="severity2" size="5"value="<c:if test="${c1.severity2Null == false}" >${c1.severity2}</c:if>" style="width: 30px;"/>
																				  </td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">
																					  <input type="text" name="value2low" id="value2low_<%=countChecked %>" size="5" value="${c1.value2low}" style="width: 30px;"/>
                                            <input type="text" name="value2lowRule" id="value2low_Rule_<%=countChecked %>" value="${c1.value2LowRule}" style="width: 30px;"/>
                                            <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2low');" class="text">定义规则</a>
																					</td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="v2lseverity2" size="5"
																						value="<c:if test="${c1.v2lseverity2Null == false}" >${c1.v2lseverity2}</c:if>" style="width: 30px;"/></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><select name="compareType" id="compare_Type">
																							<option value="NULL" <c:if test="${c1.compareType==NULL}">selected="selected"</c:if>>-请选择-</option>
																							<option value="==" <c:if test="${c1.compareType=='=='}">selected="selected"</c:if>>==</option>
																							<option value="!=" <c:if test="${c1.compareType=='!='}">selected="selected"</c:if>>!=</option>
																							<option value="&lt;" <c:if test="${c1.compareType=='<'}">selected="selected"</c:if>>&lt;</option>
																							<option value="&gt;" <c:if test="${c1.compareType=='>'}">selected="selected"</c:if>>&gt;</option>
																							<option value="&lt;=" <c:if test="${c1.compareType=='<='}">selected="selected"</c:if>>&lt;=</option>
																							<option value="&gt;=" <c:if test="${c1.compareType=='>='}">selected="selected"</c:if>>&gt;=</option>
																							<option value="Like" <c:if test="${c1.compareType=='Like'}">selected="selected"</c:if>>Like</option>
																							<option value="Not Like" <c:if test="${c1.compareType=='Not Like'}">selected="selected"</c:if>>Not Like</option>
																					</select></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="poll" size="5"
																						value="<c:if test="${c1.pollNull == false}" >${c1.poll}</c:if>" style="width: 40px;"/> 秒</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2" width="3"><input type="checkbox" name="oidgroupSel" value="<%=countChecked %>"
																						<c:if test="${c1.oidgroup != null}">checked="checked"</c:if> /></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="oidgroup" size="5" value="${c1.oidgroup}" /></td>
																				</tr>

																				<tr class="table-row">
																					<td VALIGN="middle" class="collection-table-text">外</td>
																					<td VALIGN="middle" class="collection-table-text"><select name="filterB" id="filter_B">
																							<option value="1" <c:if test="${c1.filterB==1}">selected="selected"</c:if>>是</option>
																							<option value="0" <c:if test="${c1.filterB==0}">selected="selected"</c:if>>否</option>
																					</select></td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityA" size="5"
																						value="<c:if test="${c1.severityANull == false}" >${c1.severityA}</c:if>" /></td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="v1lseverityA" size="5"
																						value="<c:if test="${c1.v1lseverityANull == false}" >${c1.v1lseverityA}</c:if>" /></td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityB" size="5"
																						value="<c:if test="${c1.severityBNull == false}" >${c1.severityB}</c:if>" /></td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="v2lseverityB" size="5"
																						value="<c:if test="${c1.v2lseverityBNull == false}" >${c1.v2lseverityB}</c:if>" /></td>
																				</tr>
																				<%
																					countChecked++;
																				%>
																			</c:forEach>
																		</table>


																		<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function" id="selCountTab">
																			<TR>
																				<TD CLASS="table-totals" VALIGN="baseline">Total Selected ${fn:length(model.details)} &nbsp;&nbsp;&nbsp;</TD>
																			</TR>
																		</TABLE>
																		<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table" id="unselTab">
																			<TBODY>
																				<TR>
																					<TD CLASS="layout-manager" id="notabs">

																						<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS=" framing-table">
																							<thead>
																								<tr>
																									<th NOWRAP VALIGN="middle" class="column-head-name" SCOPE="col" width="3%">选中</th>
																									<!--<th NOWRAP VALIGN="middle" CLASS="column-head-name" SCOPE="col" >Mode</th>-->
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
																							<c:forEach items="${model.unselected}" var="d1">

																								<tr class="table-row">
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="checkbox" name="sel" value="<%=countChecked %>|${d1.modid}|${d1.eveid}" />
																										<input type="hidden" name="modid" size="5" value="${d1.modid}" /> <input type="hidden" name="eveid" size="5" value="${d1.eveid}" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">${d1.major}</td>
																									<td VALIGN="middle" class="collection-table-text">内</td>
																									<td VALIGN="middle" class="collection-table-text"><select name="filterA" id="filter_A">
																											<option value="0">否</option>
																											<option value="1">是</option>
																									</select></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">
																									  <input type="text" name="value1" id="value1_<%=countChecked %>" size="5" value="" />
                                                    <input type="text" name="value1Rule" id="value1_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
                                                    <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1');" class="text">定义规则</a>
																									</td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity1" size="5" value="" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">
																									  <input type="text" name="value1low" id="value1low_<%=countChecked %>" size="5" value="" />
                                                    <input type="text" name="value1lowRule" id="value1low_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
                                                    <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1low');" class="text">定义规则</a>
																									</td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="v1lseverity1" size="5" value="" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">
																									  <input type="text" name="value2" id="value2_<%=countChecked %>" size="5" value="" />
                                                    <input type="text" name="value2Rule" id="value2_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
                                                    <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2');" class="text">定义规则</a>
																									</td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity2" size="5" value="" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">
																									  <input type="text" name="value2low" id="value2low_<%=countChecked %>" size="5" value="" />
                                                    <input type="text" name="value2lowRule" id="value2low_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
                                                    <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2low');" class="text">定义规则</a>
																									</td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="v2lseverity2" size="5" value="" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><select name="compareType" id="compare_Type">
																											<option value="null">-请选择-</option>
																											<option value="==">==</option>
																											<option value="!=">!=</option>
																											<option value='&lt;'>&lt;</option>
																											<option value="&gt;">&gt;</option>
																											<option value="&lt;=">&lt;=</option>
																											<option value="&gt;=">&gt;=</option>
																											<option value="Like">Like</option>
																											<option value="Not Like">Not Like</option>
																									</select></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="poll" size="5" value="300" />秒</td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2" width="3"><input type="checkbox" name="oidgroupSel" value="<%=countChecked%>" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="oidgroup" size="5" value="" /></td>
																								</tr>

																								<tr class="table-row">
																									<td VALIGN="middle" class="collection-table-text">外</td>
																									<td VALIGN="middle" class="collection-table-text"><select name="filterB" id="filter_B">
																											<option value="1">是</option>
																											<option value="0">否</option>
																									</select></td>

																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityA" size="5" value="" style="width: 30px;"/></td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="v1lseverityA" size="5" value="" style="width: 30px;"/></td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityB" size="5" value="" style="width: 30px;"/></td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="v2lseverityB" size="5" value="" style="width: 30px;"/></td>
																								</tr>

																								<%
																									countChecked++;
																								%>
																							</c:forEach>
																						</table>

																						<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function" id="unselCountTab">
																							<TR>
																								<TD CLASS="table-totals" VALIGN="baseline">Total Unselected ${fn:length(model.unselected)} &nbsp;&nbsp;&nbsp;</TD>
																							</TR>

																						</TABLE>
																					</TD>
																				</TR>
																			</TBODY>
																		</TABLE>

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
																					<td VALIGN="middle" class="collection-table-text" align="center" rowspan="2"><input type="checkbox" name="sel"
																						value="<%=countChecked %>|${c1.modid}|${c1.eveid}|${c1.ptvid}" checked="checked" /> <input type="hidden" name="pre" value="${c1.ptvid}|${c1.modid}|${c1.eveid}" />
																						<input type="hidden" name="modid" size="5" value="${c1.modid}" /> <input type="hidden" name="eveid" size="5" value="${c1.eveid}" /></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">${c1.major}</td>
																					<td VALIGN="middle" class="collection-table-text">内</td>
																					<td VALIGN="middle" class="collection-table-text"><select name="filterA" id="filter_A">
																							<option value="1" <c:if test="${c1.filterA==1}">selected="selected"</c:if>>是</option>
																							<option value="0" <c:if test="${c1.filterA==0}">selected="selected"</c:if>>否</option>
																					</select></td>

																					<td VALIGN="middle" class="collection-table-text" rowspan="2">
																					  <input type="text" name="value1" id="value1_<%=countChecked %>" size="5" value="${c1.value1}" style="width: 30px;"/>
                                            <input type="text" name="value1Rule" id="value1_Rule_<%=countChecked %>" value="${c1.value1Rule}" style="width: 30px;"/>
                                            <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1');" class="text">定义规则</a>
																					</td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity1" size="5"
																						value="<c:if test="${c1.severity1Null == false}" >${c1.severity1}</c:if>" style="width: 30px;"/>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2">
																					  <input type="text" name="value2" id="value2_<%=countChecked %>" size="5" value="${c1.value2}" style="width: 30px;"/>
                                            <input type="text" name="value2Rule" id="value2_Rule_<%=countChecked %>" value="${c1.value2Rule}" style="width: 30px;"/>
                                            <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2');" class="text">定义规则</a>
																					</td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity2" size="5"
																						value="<c:if test="${c1.severity2Null == false}" >${c1.severity2}</c:if>" style="width: 30px;"/></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><select name="compareType" id="compare_Type">
																							<option value="NULL" <c:if test="${c1.compareType==''}">selected="selected"</c:if>>-请选择-</option>
																							<option value="==" <c:if test="${c1.compareType=='=='}">selected="selected"</c:if>>==</option>
																							<option value="!=" <c:if test="${c1.compareType=='!='}">selected="selected"</c:if>>!=</option>
																							<option value="&lt;" <c:if test="${c1.compareType=='<'}">selected="selected"</c:if>>&lt;</option>
																							<option value="&gt;" <c:if test="${c1.compareType=='>'}">selected="selected"</c:if>>&gt;</option>
																							<option value="&lt;=" <c:if test="${c1.compareType=='<='}">selected="selected"</c:if>>&lt;=</option>
																							<option value="&gt;=" <c:if test="${c1.compareType=='>='}">selected="selected"</c:if>>&gt;=</option>
																							<option value="Like" <c:if test="${c1.compareType=='Like'}">selected="selected"</c:if>>Like</option>
																							<option value="Not Like" <c:if test="${c1.compareType=='Not Like'}">selected="selected"</c:if>>Not Like</option>
																					</select></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="poll" size="5"
																						value="<c:if test="${c1.pollNull == false}" >${c1.poll}</c:if>" style="width: 40px;"/> 秒</td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2" width="3"><input type="checkbox" name="oidgroupSel" value="<%=countChecked %>"
																						<c:if test="${c1.oidgroup != null}">checked="checked"</c:if> /></td>
																					<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="oidgroup" size="5" value="${c1.oidgroup}" /></td>
																				</tr>

																				<tr class="table-row">
																					<td VALIGN="middle" class="collection-table-text">外</td>
																					<td VALIGN="middle" class="collection-table-text"><select name="filterB" id="filter_B">
																							<option value="1" <c:if test="${c1.filterB==1}">selected="selected"</c:if>>是</option>
																							<option value="0" <c:if test="${c1.filterB==0}">selected="selected"</c:if>>否</option>
																					</select></td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityA" size="5"
																						value="<c:if test="${c1.severityANull == false}" >${c1.severityA}</c:if>" /></td>
																					<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityB" size="5"
																						value="<c:if test="${c1.severityBNull == false}" >${c1.severityB}</c:if>" /></td>
																				</tr>
																				<%
																					countChecked++;
																				%>
																			</c:forEach>
																		</table>
																		<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function" id="selCountTab">
																			<TR>
																				<TD CLASS="table-totals" VALIGN="baseline">Total Selected ${fn:length(model.details)} &nbsp;&nbsp;&nbsp;</TD>
																			</TR>
																		</TABLE>
																		<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table" id="unselTab">
																			<TBODY>
																				<TR>
																					<TD CLASS="layout-manager" id="notabs">
																						<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS=" framing-table">
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

																							<c:forEach items="${model.unselected}" var="d1">

																								<tr class="table-row">
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="checkbox" name="sel" value="<%=countChecked %>|${d1.modid}|${d1.eveid}" />
																										<input type="hidden" name="modid" size="5" value="${d1.modid}" /> <input type="hidden" name="eveid" size="5" value="${d1.eveid}" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">${d1.major}</td>
																									<td VALIGN="middle" class="collection-table-text">内</td>
																									<td VALIGN="middle" class="collection-table-text"><select name="filterA" id="filter_A">
																											<option value="0">否</option>
																											<option value="1">是</option>
																									</select></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">
																									  <input type="text" name="value1" id="value1_<%=countChecked %>" size="5" value="" />
                                                    <input type="text" name="value1Rule" id="value1_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
                                                    <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value1');" class="text">定义规则</a>
																									</td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity1" size="5" value="" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2">
																									  <input type="text" name="value2" id="value2_<%=countChecked %>" size="5" value="" />
                                                    <input type="text" name="value2Rule" id="value2_Rule_<%=countChecked %>" value="" style="width: 30px;"/>
                                                    <a id="define-rule" onclick="javascript: openRuleDialog('<%=countChecked %>', 'value2');" class="text">定义规则</a>
																									</td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severity2" size="5" value="" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><select name="compareType" id="compare_Type">
																											<option value="null">-请选择-</option>
																											<option value="==">==</option>
																											<option value="!=">!=</option>
																											<option value="&lt;">&lt;</option>
																											<option value="&gt;">&gt;</option>
																											<option value="&lt;=">&lt;=</option>
																											<option value="&gt;=">&gt;=</option>
																											<option value="Like">Like</option>
																											<option value="Not Like">Not Like</option>
																									</select></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="poll" size="5" value="300" />秒</td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2" width="3"><input type="checkbox" name="oidgroupSel" value="<%=countChecked%>" /></td>
																									<td VALIGN="middle" class="collection-table-text" rowspan="2"><input type="text" name="oidgroup" size="5" value="" /></td>
																								</tr>

																								<tr class="table-row">
																									<td VALIGN="middle" class="collection-table-text">外</td>
																									<td VALIGN="middle" class="collection-table-text"><select name="filterB" id="filter_B">
																											<option value="1">是</option>
																											<option value="0">否</option>
																									</select></td>

																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityA" size="5" value="" /></td>
																									<td VALIGN="middle" class="collection-table-text"><input type="text" name="severityB" size="5" value="" /></td>
																								</tr>


																								<%
																									countChecked++;
																								%>
																							</c:forEach>
																						</table>

																						<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function" id="unselCountTab">
																							<TR>
																								<TD CLASS="table-totals" VALIGN="baseline">Total Unselected ${fn:length(model.unselected)} &nbsp;&nbsp;&nbsp;</TD>
																							</TR>
																						</TABLE>
																					</TD>
																				</TR>
																			</TBODY>
																		</TABLE>

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
<%@ include file="/secure/policytemplateapply/rule_setup_dialog.jsp"%>
</body>
</html>