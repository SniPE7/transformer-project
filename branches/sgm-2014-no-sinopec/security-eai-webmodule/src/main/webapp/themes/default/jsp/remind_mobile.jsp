<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
        <div id="content">
          <div id="msg" class="aui-message warning">
		        <span class="aui-icon icon-warning"></span>
		        <p>
		          <spring:message code="${authnErrorTip}" arguments="${authnArguments}"/>
		        </p>
		      </div>
          <div id="acloginpod">
            <div id="acloginpanel" class="loginpanel">
              <form class="aui cmxform" method="post" id="authenForm" 
                name="loginForm" 
                action="<%=request.getContextPath() %>/regmobile.do">
                
                <input type="hidden" id="op" name="op" value="<%=request.getAttribute("op") != null ? 
                    request.getAttribute("op") : "modifymobile"%>" />
                <input type="hidden" id="gotourl" name="gotourl" value="" />
                <fieldset>
                  <label class="float-left"><spring:message code="regmobile.info.remind.username" />：</label>
                  <div class="aclogin-action">
                  <div>
		                    <input type="hidden" name="j_username" id="j_username" class="textinput" 
		                    READONLY 
		                    value="<%=request.getAttribute("j_username") != null ? 
		                        request.getAttribute("j_username") : ""%>" />
		                    <input type="text" name="show_username" id="show_username" class="textinput" 
		                    READONLY 
		                    value="<%=request.getAttribute("show_username") != null ? 
		                        request.getAttribute("show_username") : ""%>" />
                        </div>
                        <div>
		                    <input type="submit"
		                      value="<spring:message code="regmobile.info.remind.modify" />"
		                      title="Press Alt+Shift+s to submit this form" name="commit"
		                      id="login-form-submit" class="btn btn_big btn_submit mr20" accesskey="s" />
		                    <input type="button"
		                      value="<spring:message code="regmobile.info.remind.skip" />"
		                      title="Press Alt+Shift+c to submit this form" name="skip"
		                      id="login-form-cancel" class="btn btn_big btn_submit mr20" accesskey="c"
		                      onclick="cancelBindMobile()" />
                      </div>
                    <div class="clearfix">&nbsp;</div>
                  </div>
                </fieldset>
              </form>
            </div><!-- End of #acloginpanel -->
          </div><!-- End of #acloginpod -->
        </div><!-- End of #content -->
        
        
        <script>
        function cancelBindMobile(){
          //$("#authenForm").attr("action", document.location.href);
          //$("#authenForm").attr("action", getContextPath() + "/Authn/TAMUserPassAuth");
          $("#authenForm").attr("action", "<%=request.getSession(false).getAttribute("eai-authn-url")%>");
          //$("#op").val("remindpassword");
          $("#authenForm").submit();
        }
        
        function getContextPath(){
            var contextPath = document.location.pathname;
            var index =contextPath.substr(1).indexOf("/");
            contextPath = contextPath.substr(0,index+1);
            delete index;
            return contextPath;
       }
        //alert(getContextPath() + "/Authn/TAMUserPassAuth");
        //$("#gotourl").val(location.href);
        //$("#gotourl").val(getContextPath() + "/Authn/TAMUserPassAuth");
        $("#gotourl").val("<%=request.getSession(false).getAttribute("eai-authn-url")%>");
        </script>