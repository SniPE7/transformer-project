<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	      <div id="content" class="conten-login">
	      <div class="aui-message error invisible" id="errorDivMsg">
            <!-- shown with class="aui-message error" -->
          </div>
          <div class="aui-message info invisible" id="infoDivMsg">
            <!-- shown with class="aui-message info" -->
          </div>
	        <div id="acloginpod">
	          <div id="acloginpanel" class="loginpanel">
	            <form class="aui cmxform" method="post" id="authenForm" name="loginForm"
	              action="${actionUrl}" onSubmit="return validate(this);">
	              <input type="hidden" name="op" value="login" />
	              <fieldset>
	                <legend>
	                  <em>请输入您的统一账号和密码</em>
	                </legend>
	                <label for="ac_username"><spring:message code="logon.form.username" /></label>
	                <input type="text" tabindex="1" name="j_username" id="j_username" class="textinput" />
	                <label class="float-left"><spring:message code="logon.form.password" /></label>
	                <input type="password" tabindex="2" name="j_password" id="j_password" class="textinput"/>
	                <label class="float-left"><spring:message code="logon.form.checkcode" /></label>
	                <input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" />
	                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
	                  style="height: 25px; width: 75px; cursor: hand" title="看不清，请点击"
	                  onclick="javascript:updateCheckCodeImg()"/></br>
	                <span><font size="2px">请输入图片中的字符，区分大小写</font></span>
	                <div class="aclogin-action">
	                  <div class="fl-left">
	                    <label accesskey="r" for="login-form-remember-me">
	                    <input type="checkbox" tabindex="4" value="true" name="os_cookie"
	                      id="login-form-remember-me" class="checkbox" />
	                    <spring:message code="logon.form.rememberme" /></label>
	                    <div id="forgotbox">
	                      <a tabindex="5" href="help/getlostpassword.mt" class="forgotpass">忘记账号?</a>
	                      <a tabindex="6" href="help/getlostpassword.mt" class="forgotpass">忘记密码?</a>
	                    </div>
	                  </div>
	                  <input type="image" tabindex="7" value="登录" class="acloginbttn" 
	                   src="themes/default/images/login/transparent.gif"/>
	                  <div class="clearfix">&nbsp;</div>
	                </div>
	              </fieldset>
	            </form>
	          </div><!-- End of #acloginpanel -->
	        </div><!-- End of #acloginpod -->
	      </div><!-- End of #content -->
	      <script>setMsg('info','<spring:message code="login.form.error.title.tam" />');</script>
	      <script type="text/javascript">
	        //更新图形验证码
	        function updateCheckCodeImg() {
	          var imgCode = document.getElementById("j_checkcodeImgCode");
	          imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
	        }
	      </script>