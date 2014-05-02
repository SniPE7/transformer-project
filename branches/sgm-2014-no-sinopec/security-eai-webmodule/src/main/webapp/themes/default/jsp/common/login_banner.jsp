<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
      <div id="banner">
        <div id="logosgm"></div>
        <div id="slogan">
          <h1><a href="javascript:void();"></a></h1>
          <h2><a href="javascript:void();"></a></h2>
        </div>
      </div><!-- end #banner -->
      <div id="menu" style="clear:both;">
       
        <ul id="tabs">
          <%-- <c:forTokens items="${param.authenTypes}" delims="," var="authenType">
						<c:if test="${param.currentAuthen == authenType}">
							<li><a href="javascript:void(0)" class="current"><spring:message code="${authenType}"/></a></li>
						</c:if>
						<c:if test="${param.currentAuthen != authenType}">
							<li><a href="<%=request.getContextPath() %>/AuthnEngine?currentAuthentication=${authenType}&spAuthentication=${param.spAuthentication}"><spring:message code="${authenType}"/></a></li>
						</c:if>
						
						<!-- <script type="text/javascript">
							alert("authenType: ${authenType}");
						</script> -->
						
		  </c:forTokens> --%>
       </ul>
     </div>
     <div class="clear"></div>
     <!-- end of #menu -->