<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
function toLogin() {
	$("#cardForm").attr("action", "login/info.do");
	$("#cardForm").submit();
}
</script>

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

	                <span style="display: block; clear: both;"><font size="2px">工卡注册成功.</font></span>
	                <span style="display: block; clear: both;"><font size="2px">Succeed to register your badge.</font></span>
<br />
	                <div class="aclogin-action">

	                  <input type="button" tabindex="7" value="转入登录页面/Go to Login Page" onclick="toLogin();" />
	                  
	                  <div class="clearfix">&nbsp;</div>
	                </div>
	              </fieldset>
	            </form>
	          </div><!-- End of #acloginpanel -->
	        </div><!-- End of #acloginpod -->
	      </div><!-- End of #content -->
	      <script>setMsg('info','<spring:message code="card.register" />');</script>
