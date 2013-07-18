<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
function prevstep() {
	$("#cardForm").attr("action", "card/selectop.do");
	$("#cardForm").submit();
}

function nextstep() {
	$("#cardForm").attr("action", "card/chgreg_execute.do");
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
	            <form class="aui cmxform" method="post" id="cardForm" name="cardForm"
	              action="${actionUrl}" onSubmit="return validate(this);">
	              <input type="hidden" name="op" value="login" />
	              <fieldset>

	                <label for="ac_username">请填写您已经注册的手机/Please input your registered mobile phone:</label>
	                <input type="text" tabindex="1" name="mobile" id="mobile" class="textinput" value="3333" />
	                <label class="float-left">密码/Password</label>
	                <input type="password" tabindex="2" name="j_password" id="j_password" class="textinput" value="Pass1234" />

	                <label class="float-left"><spring:message code="logon.form.checkcode" /></label>
	                <input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" style="display: block; float: left;"/>
	                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
	                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
	                  onclick="javascript:updateCheckCodeImg()"/>
	                <span style="display: block; clear: both;"><font size="2px">请输入图片中的字符，区分大小写</font></span>

	                <div class="aclogin-action">
	                  <input type="button" tabindex="7" value="上一步" onclick="prevstep();" />
	                  <input type="button" tabindex="7" value="下一步" onclick="nextstep();" />

	                  <div class="clearfix">&nbsp;</div>
	                </div>
	              </fieldset>
	            </form>
	          </div><!-- End of #acloginpanel -->
	        </div><!-- End of #acloginpod -->
	      </div><!-- End of #content -->
	      <script>setMsg('info','<spring:message code="card.register" />');</script>
	      <script type="text/javascript">
	        //更新图形验证码
	        function updateCheckCodeImg() {
	          var imgCode = document.getElementById("j_checkcodeImgCode");
	          imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
	        }
	      </script>