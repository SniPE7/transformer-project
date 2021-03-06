<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.internet2.middleware.shibboleth.idp.authn.LoginHandler" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="eai"%>
        <div id="content">
          <div class="aui-message error invisible" id="errorDivMsg">
            <!-- shown with class="aui-message error" -->
          </div>
          <div class="aui-message info invisible" id="infoDivMsg">
            <!-- shown with class="aui-message info" -->
          </div>
<%
          String infoTag = request.getAttribute(LoginHandler.AUTHENTICATION_INFO_KEY) != null ?  request.getAttribute(LoginHandler.AUTHENTICATION_INFO_KEY).toString() : "";
%>
          <div id="acloginpod" <%=("user.modify.password.success".equals(infoTag))?"style='display:none;'":"" %>>
            <div id="acloginpanel" class="loginpanel">
              <form class="aui cmxform" method="post" id="authenForm" 
                name="loginForm" onSubmit="return validate(this);"
                action="<%=request.getContextPath() %>/ModifyPassword">
                <input type="hidden" name="op" value="<%=request.getAttribute("op") != null ? 
                    request.getAttribute("op") : "resetpassword"%>" /><eai:returnUrl/>
                <fieldset>
                  <label class="float-left"><spring:message code="modifyPass.form.username" /></label>
                  <input type="hidden" name="j_username" id="j_username" class="textinput" 
                    READONLY 
                    value="<%=request.getAttribute("j_username") != null ? 
                        request.getAttribute("j_username") : ""%>" />
                  <input type="text" name="show_username" id="show_username" class="textinput" 
                    READONLY 
                    value="<%=request.getAttribute("show_username") != null ? 
                        request.getAttribute("show_username") : ""%>" />
                  <label class="float-left"><spring:message code="modifyPass.form.password.ori" /></label>
                  <input type="password" name="j_password" id="j_password" class="textinput" value="" />
                  
                  <label class="float-left"><spring:message code="modifyPass.form.password.new" /></label>
                  <input type="password" name="j_new_password" id="j_new_password" class="textinput" value="" />
                 
                 <label class="float-left"><spring:message code="modifyPass.form.password.confirm" /></label>
                 <input type="password" name="j_confirm_password" id="j_confirm_password" class="textinput" value="" />
                 
                  <div class="aclogin-action">
                    <input type="submit"
                      value="<spring:message code="modifyPass.form.modify" />"
                      title="Press Alt+Shift+s to submit this form" name="login"
                      id="login-form-submit" class="btn btn_big btn_submit mr20" accesskey="s" />
                    <div class="clearfix">&nbsp;</div>
                  </div>
                </fieldset>
              </form>
            </div><!-- End of #acloginpanel -->
          </div><!-- End of #acloginpod -->
        </div><!-- End of #content -->
        <% 
        	if(!"".equals(infoTag)) {
        		%>
        		<script>setMsg('info', '<spring:message code="<%=infoTag%>"  />');</script>
        		<%
        	}
        %>
        <% if (!"user.modify.password.success".equals(infoTag)) { %>
        <script>setMsg('info', '<spring:message code="modifyPass.info.userpass.policy" />');</script>
        <% } %>