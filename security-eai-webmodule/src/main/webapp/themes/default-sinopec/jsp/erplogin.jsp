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
              <form class="aui cmxform" method="post" id="authenForm" 
                name="loginForm" 
                action="<%=request.getAttribute("actionUrl") != null ? 
                    request.getAttribute("actionUrl") : "Authn/ClientCert"%>">
                <input type="hidden" name="op" value="login" />
                <fieldset>
                  <div class="aclogin-action">
                    <div class="fl-left">
                      <div id="forgotbox">
                        <a tabindex="5" href="https://uid.sinopec.com/siam-im-selfservice-web/view/lose/account.html"
                          class="forgotpass">忘记账号?</a>
                        <a tabindex="6"
                          href="https://uid.sinopec.com/siam-im-selfservice-web/view/reset/password.html"
                          class="forgotpass">忘记密码?</a>
                      </div>
                    </div>
                    <input type="image" tabindex="7" title="登录" alt=" " onclick="fun_logincert()"
                      src="themes/default/images/login/transparent.gif" class="acloginbttn">
                    <div class="clearfix">&nbsp;</div>
                  </div>
                </fieldset>
              </form>
            </div><!-- End of #acloginpanel -->
          </div><!-- End of #acloginpod -->
        </div><!-- End of #content -->