<%@ include file="/include/include.jsp" %>
<%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
${model.message}&nbsp;${model.stat}
<div id="errmsg"><pre> ${model.errMsg} </pre></div>
