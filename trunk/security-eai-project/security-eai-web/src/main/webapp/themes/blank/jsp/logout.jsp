<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.internet2.middleware.shibboleth.common.session.SessionManager"%>
<%
  List<Map<String, String>> list = (List)request.getSession().getAttribute("spLLOURL");
  SessionManager sm = (SessionManager)session.getAttribute("sessionManager");
%>
<SCRIPT LANGUAGE="JavaScript">  
    function initArr(arrUrl,arrSP){      
      <%
      for(int n = 0; n < list.size(); n++){
      %>
      arrUrl[<%=n%>] = "<%=((HashMap)list.get(n)).get("spLocalLogoutUrl")%>"; 
      arrSP[<%=n%>] = "<%=((HashMap)list.get(n)).get("spEntityID")%>"; 
      <%
      }
      %>
    }
    
    function createIframeTag(){
      var arrUrl = Array([<%=list.size()%>]);
      var arrSP = Array([<%=list.size()%>]);
      initArr(arrUrl,arrSP);
      var i=0;
      var liTag = "";
      var spUrl = "";
      for (i=0;i<arrUrl.length;i++){
        spUrl = getSpUrl(arrUrl[i]);
        liTag = "<li>";
        liTag += "<a href='"+spUrl+"'>"+arrSP[i]+"</a>";
        liTag += "<iframe src='logout_iframe.html?LLOUrl="+arrUrl[i]+"&No="+i +"' id='czj"+ i +"'";
        liTag += "frameborder='no' border='0' scrolling='no'></iframe>";
        liTag += "</li>";
        document.write(liTag);
      }
      destroyIDPsession();
    }
    
    function getSpUrl(url){
      var index = url.indexOf("SSO", 0);
      return url.substring(0, index);
    }

    function destroyIDPsession(){
      <%sm.destroySession(session.getAttribute("uid").toString());%>
    }
</SCRIPT> 
	      <div id="content-logout">
	      <div id="Noinfor">
    	      <div id="GLOAPPCount"><spring:message code="logout.view.result" />：<%=list.size()%></div>
    	      <input type="hidden" id="GLObutton" name="GLObutton" value="退出所有应用"/> 
	      </div>
    	      <div id="logoutcontent">
    	      <ul id="ultl">
   	       <SCRIPT LANGUAGE="JavaScript">  
   	        createIframeTag();
   		       </SCRIPT> 
  	        </ul>
  	        </div>
	      </div> 