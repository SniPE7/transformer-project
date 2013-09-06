<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	      <div id="content" class="conten-login">
	       
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
	               	<div id="badge-label" style="display:none;"> 
			            <div id="card-tag" style="float:left;">
		               		<img src="themes/default/images/nocard.png" width="64" height="64" alt="读卡状态">
		               	</div>
	               		<div id="card-info" style="float:left;margin-top:10px;">
		               		<label for="ac_username">请将工卡插入读卡器 或 输入用户名</label>
			               	<label for="ac_username" style="display: block; float: left;">Please place your badge over reader, Or enter your username</label> 
			            </div>
		               	<!-- <div id="forgotbox" style="display: block; float: left;text-align:center"><a tabindex="6" href="./card/insert.do" class="forgotpass">注册工卡/Register My Badge?</a></div> -->
	               	</div>
	               	<div id="badge-ok" style="display:none;"> 
			            <div id="card-tag" style="float:left;">
		               		<img src="themes/default/images/card.png" width="64" height="64" alt="读卡成功">
		               	</div>
	               		<div id="card-info" style="float:left;margin-top:10px;">
		               		<label for="ac_username">读卡成功！</label>
			               	<label for="ac_username" style="display: block; float: left;">Successful reader！ </label> 
			               	<label for="ac_username"></label>
			            </div>
	               	</div>
					
	               	<div style="clear:both; height:0;overflow:hidden;"></div>
	                <div style="display: block; float: left;"><input type="text" tabindex="1" name="j_username" id="j_username" class="textinput" value="<%=request.getAttribute("j_username") != null ? request.getAttribute("j_username") : ""%>"/></div>
	                <div style="clear:both; height:0;overflow:hidden;"></div>
	                <label class="float-left"><spring:message code="logon.form.password" /></label>
	                <div style="display: block; float: left;"><input type="password" tabindex="2" name="j_password" id="j_password" class="textinput" /></div>
	                <div style="clear:both; height:0;overflow:hidden;"></div>
	                
	                <label class="float-left"><spring:message code="logon.form.checkcode" /></label>
	                <div style="display: block; float: left;"><input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" /></div>
	                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
	                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="点击更新/Click to refresh code" alt="点击更新/Click to refresh code"
	                  onclick="javascript:updateCheckCodeImg()"/>
	                <div style="clear:both; height:0;overflow:hidden;"></div>
	                <div class="aclogin-action">
		                  <div style="margin-top: 15px;float: left;">
		                     <button type="submit" id="J_sidebar_login" class="btn btn_big btn_submit mr20">登录/Login</button>
		                  </div>
		                  <div style="clear:both; height:0;overflow:hidden;"></div>
	                    <div id="forgotpwd_box" style="display: block; float: left;text-align:center; vertical-align: bottom; margin-left: 0px;margin-top: 15px;"><a tabindex="6" href="losepwd.do" class="forgotpass">忘记密码/Forget Password?</a></div>
	                    <div id="forgotuid_box" style="display: block; float: left;text-align:center;margin-top: 15px;" ><a tabindex="5" href="loseuser.do" class="forgotpass">忘记用户名/Forget Username?</a></div>
	                </div>
	              </fieldset>
	            </form>
	            
			      <div id="sgm-auth-types">
	            	<span style="display: block; float: left; line-height:250%;font-size: 12px;text-align:center">选择认证类型/Login Type:</span>
		            <c:forTokens items="${param.authenTypes}" delims="," var="authenType">
							<c:if test="${param.currentAuthen != authenType}">
								<a href="<%=request.getContextPath() %>/AuthnEngine?currentAuthentication=${authenType}&spAuthentication=${param.spAuthentication}"><spring:message code="${authenType}"/></a>
							</c:if>
			  		</c:forTokens> 
			      </div>
	          </div><!-- End of #acloginpanel -->
	        </div><!-- End of #acloginpod -->
	      </div><!-- End of #content -->
	      

	      <div class="aui-message error invisible" id="errorDivMsg">
            <!-- shown with class="aui-message error" -->
          </div>
          <div class="aui-message info invisible" id="infoDivMsg">
            <!-- shown with class="aui-message info" -->
          </div>
	      
	      
	      <!-- <script>setMsg('info','<spring:message code="login.form.error.title.tam" />');</script> -->
	      
	      
	      <script type="text/javascript">
	      	//you can change the screenZoom String to xxx%
	      	//doBodyZoom(screenZoom);
	       
	        //更新图形验证码
	        function updateCheckCodeImg() {
	          var imgCode = document.getElementById("j_checkcodeImgCode");
	          imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
	        }
	        
	        showCardModel();

	      </script>