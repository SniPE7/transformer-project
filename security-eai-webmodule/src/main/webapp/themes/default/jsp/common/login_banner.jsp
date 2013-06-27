<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
      <div id="banner">
        <div id="logo"></div>
        <div id="slogan">
          <h1><a href="#"></a></h1>
          <h2><a href="http://siam.sinopec.com"></a></h2>
        </div>
      </div><!-- end #banner -->
      <div id="menu">
        <div id="slides">中国石化用户统一身份认证中心</div>
        <ul id="tabs">
          <c:forTokens items="${param.authenTypes}" delims="," var="authenType">
						<c:if test="${param.currentAuthen == authenType}">
							<li><a href="javascript:void(0)" class="current"><spring:message code="${authenType}"/></a></li>
						</c:if>
						<c:if test="${param.currentAuthen != authenType}">
							<li><a href="<%=request.getContextPath() %>/AuthnEngine?currentAuthentication=${authenType}&spAuthentication=${param.spAuthentication}"><spring:message code="${authenType}"/></a></li>
						</c:if>
						<c:if test="${authenType == 'urn_oasis_names_tc_SAML_2.0_ac_classes_TLSClient'}">
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
					</c:if>
				</c:forTokens>
       </ul>
     </div><!-- end of #menu -->