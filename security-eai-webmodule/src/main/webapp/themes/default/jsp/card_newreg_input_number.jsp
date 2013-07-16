<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
function prevstep() {
	$("#cardForm").attr("action", "card/selectop.do");
	$("#cardForm").submit();
}

function nextstep() {
	$("#cardForm").attr("action", "card/check_login_status.do");
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

	                <span style="display: block; clear: both;"><font size="2px">工卡读入成功.</font></span>
	                <span style="display: block; clear: both;"><font size="2px">Succeed to read your badge.</font></span>
<br />

	                <label for="ac_username">身份证号/ID Number</label>
	                <input type="text" tabindex="1" name="id_number" id="id_number" class="textinput" />
	                <label class="float-left">员工号/Employee Number</label>
	                <input type="text" tabindex="2" name="employee_number" id="employee_number" class="textinput"/>

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
	      <script>setMsg('info','<spring:message code="login.form.error.title.tam" />');</script>
	      <script type="text/javascript">
	        //更新图形验证码
	        function updateCheckCodeImg() {
	          var imgCode = document.getElementById("j_checkcodeImgCode");
	          imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
	        }
	      </script>