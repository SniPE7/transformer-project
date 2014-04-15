<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link type="text/css" rel="stylesheet" href="themes/default/styles/smart_wizard.css">


        <div>
          <div id="wizard" class="swMain">
          			
						<ul>
							<li>
								<a href="#step-1" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">1</span> <span class="stepDesc"> 输入找回账号
									<br />
									<small>Enter user information</small> </span> </a>
							</li>
							<li>
								<a href="#step-2" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">2</span> <span class="stepDesc"> 安全邮件验证
									<br />
									<small>Secure E-mail validation</small> </span> </a>
							</li>
							<li>
								<a href="#step-3" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">3</span> <span class="stepDesc"> 设置新口令
									<br />
									<small>Change password</small> </span> </a>
							</li>
						</ul>
					<div id="step-1" style="padding-top: 20px; padding-left: 20px;">
							<div id="normal-label">
		                		<label for="ac_username" class="normal-label"><spring:message code="logon.form.username" /></label>
			                </div>
			               	<div id="badge-label" style="display: none"> <label for="ac_username" class="normal-label">请将工卡插入读卡器 或 输入用户名</label>
				               	<label for="ac_username" style="display: block; float: left;" class="normal-label">Please place your badge over reader, Or enter your username</label> 
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
			               	<input type="hidden" tabindex="100" name="j_useruid" id="j_useruid"/>
			                <input type="text" tabindex="1" name="j_username" id="j_username" class="textinput" style="display: block; float: left;"/>			                
			                <div style="clear:both; height:0;overflow:hidden;"></div>
					              
	                <label class="float-left"><spring:message code="logon.form.checkcode" /></label>
	                <div style="display: block; float: left;"><input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" /></div>
<!--	                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
	                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="点击更新/Click to refresh code" alt="点击更新/Click to refresh"
	                  onclick="javascript:updateCheckCodeImg()"/>
-->	                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
	                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" onclick="javascript:updateCheckCodeImg()"/>点击更新/Click to refresh
					<div style="clear:both; height:0;overflow:hidden;"></div>
					</div>
					<div id="step-2" style="padding-top: 20px; padding-left: 20px;">
						<label id="j_msg" class="float-left normal-label" style="color:red;"></label>
						<div id="nomail" style="display: none;"> 
							<label class="normal-label">您的安全邮箱地址没有设置,请联系SGM IT.</label>
		               		<label class="normal-label">Your safe email address is not set, please contact with HR. </label>
		               	<!--	<label class="normal-label"><a href="resetpwdbymail.do">重新找回密码/To reclaim the password</a> </label>-->
		               	</div>
		               	
		               	<div id="novalidate" style="display: none;"> 
							<label class="normal-label">您的找回密码链接已经失效！</label>
		               		<label class="normal-label">You retrieve password link have failed! </label>
		               		<label class="normal-label"><a href="resetpwdbymail.do">重新找回密码/To reclaim the password</a> </label>
		               	</div>
		               	
		               	<div id="mail" style="display: none;"> 
		               		<label id="lb-mail" class="float-left normal-label" style="color:red;"></label>
		               		<label class="normal-label">密码找回邮件已发到上述邮箱，请您注意接收邮件！</label>
		               		<label class="normal-label">Password reset email has been sent to above your mailbox, please check your email! </label>
		               		
		            	</div>

					</div>
					<div id="step-3" style="padding-top: 20px; padding-left: 20px;">
						
			             <br/><br/>
			             <label for="ac_username" class="normal-label float-left">新密码/New Password</label>
			             <input type="password" tabindex="7" name="j_npassword" id="j_npassword" class="textinput" style="display: block; "/>
			             
			             
			             <label for="ac_username" class="normal-label float-left">确认新密码/Affirm New Password</label>
			             <input type="password" tabindex="8" name="j_npassword2" id="j_npassword2" class="textinput" style="display: block; "/>

					</div>
					<input type="hidden" id="gotourl" name="gotourl" value="<%=request.getAttribute("gotoUrl") != null ? request.getAttribute("gotoUrl") : "#"%>" />
				</div><!-- End SmartWizard Content -->
        </div><!-- End of #content -->
        	      <!-- <object id="badgeTool" style="display:none" classid="clsid:395E6CF3-3084-487D-9606-EDAA8B2C4E3C"></object> -->
        
         <script type="text/javascript" src="themes/default/js/jquery.smartWizard-2.0.js"></script>
        <script type="text/javascript" src="js/resetpwdbymail.js"></script>
        <script type="text/javascript">
		        showCardModel();
		</script>
		<script type="text/javascript">
	      	//you can change the screenZoom String to xxx%
			//doBodyZoom(screenZoom);
	       
	        //更新图形验证码
	        function updateCheckCodeImg() {
	          var imgCode = document.getElementById("j_checkcodeImgCode");
	          imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
	        }
	      </script>
       