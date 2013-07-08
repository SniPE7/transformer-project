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
	                <div id="normal-label">
	                	<label for="ac_username"><spring:message code="logon.form.username" /></label>
	                </div>
	               	<div id="badge-label"> <label for="ac_username">请将工卡插入读卡器 或 输入用户名</label>
		               	<label for="ac_username" style="display: block; float: left;">Please place your badge over reader, Or enter your username</label> 
		               	<div id="forgotbox" style="display: block; float: left;text-align:center"><a tabindex="9" href="https://uid.sinopec.com/siam-im-selfservice-web/view/reset/password.html" class="forgotpass">注册工卡/Register My Badge?</a></div>
	               	</div>
					
	               	<div style="clear:both; height:0;overflow:hidden;"></div>
	                <input type="text" tabindex="1" name="j_username" id="j_username" class="textinput" style="display: block; float: left;"/>
	                <div id="forgotbox" style="display: block; float: left;text-align:center"><a tabindex="9" href="https://uid.sinopec.com/siam-im-selfservice-web/view/lose/account.html" class="forgotpass">忘记用户名/Forget Username?</a></div>
	                <div style="clear:both; height:0;overflow:hidden;"></div>
	                
	                <label class="float-left"><spring:message code="logon.form.password" /></label>
	                <input type="password" tabindex="2" name="j_password" id="j_password" class="textinput" style="display: block; float: left;"/>
	                <div id="forgotbox" style="display: block; float: left;text-align:center"><a tabindex="9" href="https://uid.sinopec.com/siam-im-selfservice-web/view/reset/password.html" class="forgotpass">忘记密码/Forget Password?</a></div>
	                <div style="clear:both; height:0;overflow:hidden;"></div>
	               
	                <label class="float-left"><spring:message code="logon.form.checkcode" /></label>
	                <input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" style="display: block; float: left;"/>
	                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
	                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
	                  onclick="javascript:updateCheckCodeImg()"/>
	                <span style="display: block; float: left; text-align:center"><font size="2px"> 点击图片更新验证码</font></span>
	                 <div style="clear:both; height:0;overflow:hidden;"></div>
	                 
	                <label class="float-left">短信一次性密码/SMS One Time Password</label>
	                <input type="text" tabindex="4" name="j_smscode" id="j_smscode" class="textCKcode" style="display: block; float: left;"/>
	                <input type="button" tabindex="5" name="bnt_sms" id="bnt_sms" value="发送/Send" onclick="sendsms()" style="display: block; float: left;"/>
	                <label id="lb_tipsms" class="float-left"></label>
	               	<div style="clear:both; height:0;overflow:hidden;"></div>
	               
	                <label class="float-left">密码将通过短信发送到您的手机.</label>
	                <label class="float-left">SMS password will be sent to the registered mobile phone.</label>
	                <label id="lb_usr_mobile" class="float-left"></label>
	                
	                <div class="aclogin-action" style="display: block; clear: both;">
	                  <div class="fl-left">
	                   <!--  <div id="forgotbox">
	                      <a tabindex="5" href="https://uid.sinopec.com/siam-im-selfservice-web/view/lose/account.html" class="forgotpass">忘记用户名/Forget Username?</a>
	                      <a tabindex="6" href="https://uid.sinopec.com/siam-im-selfservice-web/view/reset/password.html" class="forgotpass">忘记密码/Forget Password?</a>
	                    </div> -->
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