<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.ibm.siam.am.idp.authn.entity.CardRegisterEntity" %>
<%
	String errorMessage = (String)session.getAttribute("CardErrorMessage");
%>

<div id="content" class="conten-login">
    <div id="acloginpod">
      <div id="acloginpanel" class="loginpanel">
        <form class="aui cmxform" method="post" id="cardForm" name="cardForm" >
        <input type="hidden" name="op" value="card" />
        <fieldset>
          <span style="display: block; clear: both;"><font size="2px">错误信息/Error Message : <%=(errorMessage==null)?"":errorMessage %></font></span>
          
          <span style="display: block; clear: both;"></span>
	      <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

          <div class="clearfix">&nbsp;</div>
          
        </fieldset>
      </form>
    </div><!-- End of #acloginpanel -->
  </div><!-- End of #acloginpod -->
</div><!-- End of #content -->
