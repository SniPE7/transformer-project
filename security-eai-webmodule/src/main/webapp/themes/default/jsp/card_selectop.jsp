<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript">
function newreg() {
	$("#cardForm").attr("action", "card/newreg_input_number.do");
	$("#cardForm").submit();
}

function chgreg() {
	$("#cardForm").attr("action", "card/chgreg_verify_user.do");
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
	            <form class="aui cmxform" method="post" id="cardForm" name="cardForm" action="">
	              <input type="hidden" name="op" value="login" />
	              <fieldset>
	                <legend>
	                  <em>请输入您的统一账号和密码</em>
	                </legend>

	                <span style="display: block; clear: both;"><font size="2px">工卡读入成功.</font></span>
	                <span style="display: block; clear: both;"><font size="2px">Succeed to read your badge.</font></span>
<br />
	                <div class="aclogin-action">

	                  <input type="button" tabindex="7" value="第一次注册工卡" onclick="newreg();"/><br /><br />
	                  <input type="button" tabindex="7" value="工卡已更换，注册工卡" onclick="chgreg();">
	                  
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