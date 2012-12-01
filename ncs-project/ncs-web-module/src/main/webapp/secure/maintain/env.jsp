    <%--    env.jsp    检测服务器信息    --%>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <html>
    <style>
    a:hover{color:orange}
    </style>
    <title>服务器环境变量</title>
    <body>

    &nbsp;
    <table width=90% border="1" align="center" cellpadding="2" cellspacing="0" bordercolor="#99CCFF" style="border-collapse: collapse">
    <tr>
    <td width="50%" align="center" bgcolor="#99CCFF"><font color="#FFFFFF">服务器环境变量</font></td>
    <td height="16" align="center" bgcolor="#99CCFF"><font color="#FFFFFF">功能简述</font></td>
    </tr>
    <tr>
    <td width="50%"><%= request.getServerName()%>　</td>
    <td height="4">服务器的域名</td>
    </tr>
    <tr>
    <td width="50%"><%= java.net.InetAddress.getLocalHost().getHostAddress() %>　</td>
    <td height="4">服务器的IP地址</td>
    </tr>
    <tr>
    <td width="50%"><%=System.getProperty("os.name")%>　</td>
    <td height="4">服务器操作系统</td>
    </tr>
    <tr>
    <td width="50%"><%=System.getProperty("java.version")%>　</td>
    <td height="4">服务器 Java 虚拟机版本</td>
    </tr>
    <tr>
    <td width="50%"><%=application.getMajorVersion() + "." + application.getMinorVersion()%>　</td>
    <td height="4">服务器支持的 Servlet API 版本</td>
    </tr>
    <tr>
    <td width="50%"><%=JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion()%>　</td>
    <td height="4">服务器支持的 JSP API 版本</td>
    </tr>
    <tr>
    <td width="50%"><%=application.getServerInfo()%>　</td>
    <td height="4">服务器软件的名称及版本</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getServerPort()%>　</td>
    <td height="5">服务器正在运行的端口</td>
    </tr>
    <tr>
    <td width="50%"><%=System.getProperty("sun.cpu.isalist")%></td>
    <td height="5">服务器 CPU 类型</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getRealPath(request.getServletPath())%>　</td>
    <td height="4">请求的物理路径</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getPathInfo()%>　</td>
    <td height="4">在域名根目录外的到被请求页面的路径</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getMethod()%>　</td>
    <td height="4">发出request的方式</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getQueryString()%>　</td>
    <td height="4">通过使用GET方法提交的任何数据</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getServletPath()%>　</td>
    <td height="4">程序被调用的路径</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getRemoteHost()%>　</td>
    <td height="4">发出request请求的远端机器的名称</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getRemoteAddr()%>　</td>
    <td height="5">发出request请求的远端机器的IP名称</td>
    </tr>
    <tr>
    <td width="50%"><%=request.getHeader("User-Agent")%>　</td>
    <td height="4">客户请求的浏览器类型</td>
    </tr>
    </table>
    </body>
    </html>
