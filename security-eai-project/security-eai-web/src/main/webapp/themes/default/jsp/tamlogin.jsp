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
	                  <em>请输入您的账号和密码</em>
	                </legend>
	                <label for="ac_username"><spring:message code="logon.form.username" /></label>
	                <input type="text" tabindex="1" name="j_username" id="j_username" class="textinput" />
	                <label class="float-left"><spring:message code="logon.form.password" /></label>
	                <input type="password" tabindex="2" name="j_password" id="j_password" class="textinput"/>
	                <span></span>
	                <div class="aclogin-action">
	                  <div class="fl-left">
	                  
			                  <%
						    if (null!=request.getAttribute("selfRegisterUrl") && !"".equals(request.getAttribute("selfRegisterUrl"))) {
							 %>
									 <div id="forgotbox">
				                      <a href="${selfRegisterUrl}" class="forgotpass">SelfRegister</a>
				                    </div>
							<%
								}
							%>
	                    
	                  </div>
	             
						<input type="image" tabindex="3" value="登录" class="acloginbttn" src="themes/default/images/login/transparent.gif">	                   
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
/* 	        function updateCheckCodeImg() {
	          var imgCode = document.getElementById("j_checkcodeImgCode");
	          imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
	        } */
	        
	        if  (self != top){top.location  =  self.location;}
	      </script>