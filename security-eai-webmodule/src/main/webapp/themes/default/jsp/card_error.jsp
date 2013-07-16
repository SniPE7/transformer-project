<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.ibm.siam.am.idp.authn.entity.CardRegisterEntity" %>
<%
	String errorMessage = (String)session.getAttribute("CardErrorMessage");
%>
<script type="text/javascript">
function prevstep() {
	$("#cardForm").attr("action", "card/newreg_input_number.do");
	$("#cardForm").submit();
}

function nextstep() {
	$("#cardForm").attr("action", "card/newreg_execute.do");
	$("#cardForm").submit();
}
</script>

	      <div id="content" class="conten-login">
	        <div id="acloginpod">
	          <div id="acloginpanel" class="loginpanel">
	            <form class="aui cmxform" method="post" id="cardForm" name="cardForm"
	              action="${actionUrl}" onSubmit="return validate(this);">
	              <input type="hidden" name="op" value="login" />
	              <fieldset>

	                <span style="display: block; clear: both;"><font size="2px">错误信息/Error Message : <%=errorMessage %></font></span>
<br />

	                <div class="aclogin-action">

	                  <div class="clearfix">&nbsp;</div>
	                </div>
	              </fieldset>
	            </form>
	          </div><!-- End of #acloginpanel -->
	        </div><!-- End of #acloginpod -->
	      </div><!-- End of #content -->
	      <script>setMsg('info','<spring:message code="login.form.error.title.tam" />');</script>
