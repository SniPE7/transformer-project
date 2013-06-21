<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	Enumeration   headers   =   request.getHeaderNames();   
	while   (headers.hasMoreElements())   {   
	    String   header   =   (String)headers.nextElement();   
	    String   value   =   request.getHeader(header); 
	    System.out.println("header--" + header + "  value--" + value);
	    String info = "header--" + header + "  value--" + value;
	    %>
	    <p> <%=info %></p>
	   <% }
%>

</body>
</html>