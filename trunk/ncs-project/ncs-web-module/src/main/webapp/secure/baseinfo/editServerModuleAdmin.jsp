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
<title>category test</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<% int nodei=0; %>

</head>
<script type="text/javascript">
	function New(){
		//PartnerForm.reset();
		PartnerForm.path.value="";
		PartnerForm.mid.selected="0";
		PartnerForm.desc.value="";
		PartnerForm.nmsid.value="";
		PartnerForm.modid.value="";
		//PartnerForm.action = "<%=request.getContextPath() %>/secure/baseinfo/defaultPolicyPreDefMibIndexDef.wss";
		//this.PartnerForm.submit();
	}
	
	function saveForm(){
		PartnerForm.type.value="save";
		PartnerForm.action = "<%=request.getContextPath() %>/secure/resourceman/editServerModule.wss";
		this.PartnerForm.submit();
	}
	
	function Del(){
		PartnerForm.type.value="del";
		PartnerForm.action = "<%=request.getContextPath() %>/secure/resourceman/editServerModule.wss";
		this.PartnerForm.submit();	
	}

</script>

<body onLoad="initAll();" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >
 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">网络系统规划</TD>
          <TD CLASS="pageClose">
          </TD>
      </TR>

  </TABLE>
  <TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
	<TR>
  		<TD valign="top">
        	<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">
            	<TR>
                	<TH class="wpsPortletTitle">网络系统规划</TH>
  				</TR>	
                <TBODY ID="wasUniPortlet">
    				<TR>   
  						<TD CLASS="wpsPortletArea" COLSPAN="3" >
                        	<a name="important"></a> 
                            <h1 id="title-bread-crumb">网络系统规划</h1>
                            <p ></p>
                            <table height="26" width="100%" cellspacing="0" cellpadding="0" style="background-color: #FFFFFF;">
								<tr>
                                    <td align="left" colspan="2" class="navtree"
                                        style="background-color: #FFFFFF">
                                    <!-- title of the navi welcome page 
                                    <ul class='nav-child' dir='ltr'>
                                        <li class='navigation-bullet'>网络系统规划 </li>
                                    </ul>
                                    <!--<a style='color: #000000; text-decoration: none;' 	
                                            onclick="javascript:CollapseAll();"			
                                            href="javascript:CollapseAll();"
                                            dir="ltr" title="Collapse All">
                                            <img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
                                            title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>  				
                                            Collapse All</a>-->
                                	</td>
								</tr>
                                <tr>
                                    <td>
                                        <form name="PartnerForm">
                                            <input type="hidden" name="type" value="">
                                            <input type="hidden" name="nmsid" value="${model.svrModMap.nmsid }">
                                            <input type="hidden" name="modid" value="${model.svrModMap.modid }">
											<table align="center" border="0" cellpadding="0" cellspacing="0">
                                                <tbody>
                                                    <tr height="18">
                                                        <td nowrap="nowrap">服务器名：</td>
                                    
                                                        <td>&nbsp; <input name="sevName" class="input" readonly="readonly" value="${model.servInfo.nmsname }" type="text"> <input value="${model.servInfo.nmsid }" name="servid" type="hidden"> <input name="operatetype" value="" type="hidden"></td>
                                                        <td></td>
                                                    </tr>
                                                    <tr height="18">
                                                        <td nowrap="nowrap">安装路径：</td>
                                                        <td colspan="2"><input name="path" class="input" type="text" value="${model.svrModMap.path }"><font color="#ff0000">*</font> </td>
                                    
                                                    </tr>
                                                    <tr height="18">
                                                        <td nowrap="nowrap">软件名称： <input name="oldmid" type="hidden"></td>
                                                        <td colspan="2"><select name="mid" class="input">
                                                        <c:forEach items="${model.modulelist }" var="ml">
                                                        <option value="${ml.modid}" <c:if test="${ml.modid == model.svrModMap.modid }">selected="selected"</c:if>>${ml.mname}</option>
                                                        </c:forEach>
                                                        </select><font color="#ff0000">*</font> </td>
                                                    </tr>
                                                    <tr height="18">
                                                        <td nowrap="nowrap" valign="top">备注：</td>
                                                        <td colspan="2">&nbsp; <textarea name="desc" class="select"><c:out value="${model.svrModMap.description }"/></textarea>
                                                        </td>
                                                    </tr>
                                                    <tr height="18">
                                                        <td colspan="3" valign="top">
                                                        <table align="right" border="0" cellpadding="0" cellspacing="0">
                                                            <tbody><tr>
                                                                <td valign="top"><input value="新建" class="button" onClick="New()" type="button"></td>
                                                                <td nowrap="nowrap">&nbsp; <input value="保存" class="button" onClick="saveForm()" type="button"> <input value="" name="Lev" type="hidden"></td>
                                    
                                                                <td>&nbsp; <input value="删除" class="button" onClick="Del()" type="button"></td>
                                                            </tr>
                                                        </tbody></table>
                                                        </td>
                                                    </tr>
                                                  </tbody>
												</table>
											</form>
										</td>
									</tr>
								</table>	
							</td>
                           </tr>
                          </tbody>
                         </table>
                        </td>
                       </tr>
                      </table>
                           		
    
</body>
</html>