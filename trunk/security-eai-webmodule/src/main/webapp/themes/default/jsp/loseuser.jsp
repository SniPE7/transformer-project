<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link type="text/css" rel="stylesheet" href="themes/default/styles/smart_wizard.css">


        <div>
          <div id="wizard" class="swMain">
					<ul>
						<li>
							<a href="#step-1" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">1</span> <span class="stepDesc"> 输入验证信息
								<br />
								<small></small> </span> </a>
						</li>
						<li>
							<a href="#step-2" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">2</span> <span class="stepDesc"> 确认手机号
								<br />
								<small></small> </span> </a>
						</li>
						<li>
							<a href="#step-3" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">3</span> <span class="stepDesc"> 发送用户名
								<br />
								<small></small> </span> </a>
						</li>
					</ul>
					<div id="step-1" style="padding-top: 20px; padding-left: 20px;">

			               	<div id="idcard"> 
			               		<label for="j_idcard" class="normal-label">身份证号/ID Number</label>
			               		<input type="text" tabindex="1" name="j_idcard" id="j_idcard" class="textinput" style="display: block;"/>
			               	</div>
			               	
			               	<div id="checkcode"> 
			               		<label class="float-left normal-label" style="display: block;" ><spring:message code="logon.form.checkcode" /></label>
			               		<input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" style="display: block; float: left;"/>
			               		<img id="j_checkcodeImgCode" src="Kaptcha.jpg"
				                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
				                  onclick="javascript:updateCheckCodeImg()"/>
				                  <span style="display: block; float: left; text-align:center; line-height: 250%;" ><font size="2px"> 点击图片更新验证码</font></span>
			               	</div>

					</div>
					<div id="step-2" >
							 <div id="nomobile" style="display: none;"> 
								<label class="normal-label">你目前没有注册的手机信息，请联系人力资源部修改.</label>
			               		<label class="normal-label">Could not found your mobile number, please contact with HR. </label>
			               	</div>
			               	
			               	<div id="hadmobile" style="display: none;"> 
			               	
			               		<label class="normal-label">用户名将通过短信发送到您的手机.</label>
			               		<label class="normal-label">Your username will be sent to the registered mobile phone. </label>
			               		<label id="lb_usr_mobile" class="float-left normal-label"></label>
			               		
			               		<label class="float-left normal-label">如果手机号码不正确，请联系人力资源部修改.</label>
			               		<label class="float-left normal-label">If this mobile number is wrong, please contact with HR. </label>
			               	</div>

					</div>
					
					<div id="step-3" style="padding-top: 20px; padding-left: 20px;">
					
			               	<div id="sendusername"> 
			               	
			               		<label class="normal-label">您的用户名已经发出到如下手机：</label>
			               		<label class="normal-label">Your username has been sent to the mobile: </label>
			               		<label id="lb_usr_mobile" class="float-left normal-label"></label>
			               	</div>
						
					</div>

				</div><!-- End SmartWizard Content -->
        </div><!-- End of #content -->
        
        <script type="text/javascript" src="themes/default/js/jquery.smartWizard-2.0.js"></script>
        <script type="text/javascript" src="js/loseuser.js"></script>
        