<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
				<h3 class="active">认证方式</h3>
				<div class="desc idTabs">
				<ol>
				<c:if test="${param.spAuthentication == 'urn_oasis_names_tc_SAML_2.0_ac_classes_TLSClient'}">
				</c:if>
				<c:forTokens items="${param.authenTypes}" delims="," var="authenType">
				<c:if test="${param.currentAuthen == authenType}">
					<li class="selected">
				</c:if>
				<c:if test="${param.currentAuthen != authenType}">
					<li>
				</c:if>
					<a href="<%=request.getContextPath() %>/AuthnEngine?currentAuthentication=${authenType}&spAuthentication=${param.spAuthentication}"><spring:message code="${authenType}"/></a></li>
				<%-- <c:if test="${authenType == 'urn_oasis_names_tc_SAML_2.0_ac_classes_TLSClient'}">
				<script type="text/javascript">
					//使用证书产生用户身份验证数据---begin
					function fun_logincert() {
						var ob;
						var random;
						var err = 0;
						try{
							ob = new ActiveXObject("TassAuthClientX.kernal");
							random = "sRandom";
							if(random != ""){
								result = "";
								err = ob.IC_Sign("Tass Unite std RSA CSP Provider",random,32);
							}
						} catch(objError) {
							result= "";
							err="8";
						}
						if(err == 0) {
							window.location.href = "<%=request.getContextPath() %>/AuthnEngine?currentAuthentication=${authenType}";
						}
					}
					//使用证书产生用户身份验证数据---end
					<%
				    if (!"true".equals(request.getAttribute("loginFailed"))) {
					 %>
							fun_logincert();
					<%
						}
					%>
					//alert("ok");
				</script>
				</c:if> --%>
				</c:forTokens>
				</ol>
				</div>