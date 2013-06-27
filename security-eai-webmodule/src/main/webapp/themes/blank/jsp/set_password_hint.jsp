<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
        <div id="content">
          <div class="aui-message error invisible" id="errorDivMsg">
            <!-- shown with class="aui-message error" -->
          </div>
          <div class="aui-message info invisible" id="infoDivMsg">
            <!-- shown with class="aui-message info" -->
          </div>
          <div id="acloginpod">
            <div id="acloginpanel" class="loginpanel">
              <form class="aui cmxform" method="post" id="passHintForm" 
              name="passHintForm" action="<%=request.getAttribute("actionUrl")%>">
                <input type="hidden" name="op" value="<%=request.getAttribute("op")%>" />
                <fieldset>
                  <label class="float-left"><spring:message code="passhint.form.username" /></label>
                  <input type="text" name="j_username" id="j_username" class="text medium-field" 
                  READONLY 
                  value="<%=request.getAttribute("j_username") != null ? 
                      request.getAttribute("j_username") : ""%>" />
                  <label class="float-left"><spring:message code="passhint.form.question" /></label>
                    <select name="hintQuestion">
                  <%
                    String[] hints = (String[]) request.getAttribute("hintQuestionCandidate");
                    if (hints != null) {
                      for (int i = 0; i < hints.length; i++) {
                  %>
                  <option value="<%=hints[i]%>"><%=hints[i]%></option>
                  <%
                    }
                    }
                  %>
                    </select>
                  <label class="float-left"><spring:message code="passhint.form.answer" /></label>
                  <input type="text" name="hintAnswer" id="hintAnswer" class="text medium-field" />
                 
                  <div class="aclogin-action">
                    <input type="submit"
                      value="<spring:message code="passhint.form.submit" />"
                      title="Press Alt+Shift+s to submit this form" name="setHint"
                      id="login-form-submit" class="button" accesskey="s" />
                    <div class="clearfix">&nbsp;</div>
                  </div>
                </fieldset>
              </form>
            </div><!-- End of #acloginpanel -->
          </div><!-- End of #acloginpod -->
        </div><!-- End of #content -->