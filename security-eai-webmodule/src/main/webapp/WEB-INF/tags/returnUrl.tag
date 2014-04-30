<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="requestAttrName" required="false" rtexprvalue="true" description="name of text element"%>
<%@ attribute name="inputName" required="false" rtexprvalue="true" description="name of text element"%>
<c:if test="${requestAttrName == null || requestAttrName == ''}">
    <c:set var="requestAttrName" value="eai-redir-url-header" />
</c:if>
<c:if test="${inputName == null || inputName == ''}">
    <c:set var="inputName" value="eaiReturnUrlInPage" />
</c:if>
<%
String retUrl = (String)request.getAttribute("eai-redir-url-header");
if (retUrl == null && session != null) {
  retUrl = (String)session.getAttribute("eai-redir-url-header");
}
retUrl = (retUrl != null)?retUrl:"";
%>
<input type="hidden" name="${inputName}" value='<%=retUrl%>'/>