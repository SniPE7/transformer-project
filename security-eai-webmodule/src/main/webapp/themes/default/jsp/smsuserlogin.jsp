<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	      <div id="content" class="conten-login">
	        
         
	        <div id="acloginpod">
	          <div id="acloginpanel" class="loginpanel">
	            <form class="aui cmxform" method="post" id="authenForm" name="loginForm"
	              action="${actionUrl}" onSubmit="return validate(this);">
	              <input type="hidden" name="op" value="login" />
	              <input type="hidden" name="j_matchcode" id="j_matchcode" value="" />
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
	               
	                <label class="float-left"><spring:message code="logon.form.checkcode" /></label>
	                <div style="display: block; float: left;"><input type="text" tabindex="2" name="j_checkcode" id="j_checkcode" class="textCKcode"/></div>
	                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
	                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="点击更新/Click to refresh code" alt="点击更新/Click to refresh code"
	                  onclick="javascript:updateCheckCodeImg()"/>
	                 <div style="clear:both; height:0;overflow:hidden;"></div>
	                 
	                <label class="float-left">短信一次性密码/SMS One Time Password</label>
	                <div style="display: block; float: left;"><input type="text" tabindex="3" name="j_smscode" id="j_smscode" class="textCKcode"/></div>
	                <input type="button" tabindex="4" name="bnt_sms" id="bnt_sms" value="发送/Send" onclick="sendsms()" class="btn btn_big" style="display: block; float: left;"/>
	                <label id="lb_tipsms" class="float-left"  style="width: 160px;"></label>
	               	<div style="clear:both; height:0;overflow:hidden;"></div>
	               
	                <label class="float-left">密码将通过短信发送到您的手机.</label>
	                <label class="float-left">SMS password will be sent to the registered mobile phone.</label>
	                <label id="lb_usr_mobile" class="float-left" style="color:red;"></label>
	                
	                <div class="aclogin-action" style="display: block; clear: both;">
	                  <div class="fl-left">
	                  </div>
	                  <div style="margin-top: 15px;">
	                    <button type="submit" id="J_sidebar_login" class="btn btn_big btn_submit mr20" style="float: left; margin-left: 0px; ">登录/Login</button> 
	                  </div>
                    <div style="clear:both; height:0;overflow:hidden;"></div>
                    <div id="forgotuid_box" style="display: none; float: left;text-align:center;margin-top: 15px;" ><a tabindex="5" href="loseuser.do" class="forgotpass">忘记用户名/Forget Username?</a></div>
                    <div id="firstlogin_box" style="display: block; float: left;text-align:center; vertical-align: bottom; margin-left: 0px;margin-top: 15px;"><a tabindex="6" href="reg/firstlogin.do" class="forgotpass">首次登陆?/First Login?</a></div>
	                  <div class="clearfix">&nbsp;</div>
	                </div>
	              </fieldset>
	            </form>
	            
	            <div id="sgm-auth-types">
	            	<span style="display: block; float: left; line-height:250%;font-size: 12px;text-align:center">选择认证类型/Login Type:      </span>
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
            
	      <!-- <object id="badgeTool" style="display:none" classid="clsid:395E6CF3-3084-487D-9606-EDAA8B2C4E3C"></object> -->
	      
	      
	      <!-- <script>setMsg('info','<spring:message code="login.form.error.title.tam" />');</script> -->
	      <script type="text/javascript">
	        //更新图形验证码
	        function updateCheckCodeImg() {
	          var imgCode = document.getElementById("j_checkcodeImgCode");
	          imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
	        }
	        
	        showCardModel();
	      </script>