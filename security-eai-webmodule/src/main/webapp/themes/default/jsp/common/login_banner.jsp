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
        <div id="slides">上海通用汽车统一身份认证中心</div>
        <ul id="tabs">
          <c:forTokens items="${param.authenTypes}" delims="," var="authenType">
						<c:if test="${param.currentAuthen == authenType}">
							<li><a href="javascript:void(0)" class="current"><spring:message code="${authenType}"/></a></li>
						</c:if>
						<c:if test="${param.currentAuthen != authenType}">
							<li><a href="<%=request.getContextPath() %>/AuthnEngine?currentAuthentication=${authenType}&spAuthentication=${param.spAuthentication}"><spring:message code="${authenType}"/></a></li>
						</c:if>
						
						<!-- <script type="text/javascript">
							alert("authenType: ${authenType}");
						</script> -->
						
							<script type="text/javascript">
								//是否连接读卡器
								var hasReader = false;
							
								//检测是否连接读卡器---begin
								function checkReader() {
									return false;
									
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
									
									hasReader = true;
									
									return hasReader;
								}
								//检测是否连接读卡器---end
								
								checkReader();
								
								//alert("ok");
						</script>
				</c:forTokens>
       </ul>
     </div><!-- end of #menu -->