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
									<small>Enter user information</small> </span> </a>
							</li>
							<li>
								<a href="#step-2" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">2</span> <span class="stepDesc"> 修改手机号
									<br />
									<small>Change phone number</small> </span> </a>
							</li>
							<li>
								<a href="#step-3" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">3</span> <span class="stepDesc"> 修改口令
									<br />
									<small>Change password</small> </span> </a>
							</li>
						</ul>
					<div id="step-1" style="padding-top: 20px; padding-left: 20px;">
							<div id="normal-label" >
		                		<label for="ac_username" class="normal-label"><spring:message code="logon.form.username" /></label>
			                </div>
			               	<div id="badge-label" style="display:none;"> 
					            <div id="card-tag" style="float:left;">
				               		<img src="themes/default/images/nocard.png" width="64" height="64" alt="读卡状态">
				               	</div>
			               		<div id="card-info" style="float:left;margin-top:10px;">
				               		<label for="ac_username">请将工卡插入读卡器</label>
					               	<label for="ac_username" style="display: block; float: left;">Please place your badge over reader</label> 
					            </div>
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
			                <input type="hidden" tabindex="1" name="j_username" id="j_username" class="textinput" style="display: block; float: left;"/>			                
			                <div style="clear:both; height:0;overflow:hidden;"></div>
			                
			                <div id="idcard"> 
			               		<label for="j_idcard" class="normal-label">身份证号/ID Number</label>
			               		<input type="text" tabindex="1" name="j_idcard" id="j_idcard" class="textinput" style="display: block;"/>
			               	</div>
			               
			                <label class="float-left normal-label"><spring:message code="logon.form.checkcode" /></label>
			                <input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" style="display: block; float: left;"/>
			                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
			                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
			                  onclick="javascript:updateCheckCodeImg('j_checkcodeImgCode')"/>
			                <span style="display: block; float: left; text-align:center; line-height: 250%;"><font size="2px">点击更新/Click to refresh code</font></span>
			                 <div style="clear:both; height:0;overflow:hidden;"></div>

					</div>
					<div id="step-2" style="padding-top: 20px; padding-left: 20px;">
							<div id="noauth" style="display: none;">
								<label class="normal-label">您填写的信息未通过验证或非首次登录注册，请联系人力资源部确认您的信息.</label>
			               		<label class="normal-label">Fill in your information is not verified or not the first login, please contact the HR to confirm your information. </label>
			               	</div>
			               	
			               	<div id="auth" style="display: none;">
			               		 <label class="normal-label float-left">姓名/Name:</label>
					             <label id="lb_username" class="normal-label"></label>
					             
					             <label class="normal-label float-left">用户名/Username:</label>
					             <label id="lb_userid" class="normal-label"></label>
					             
								<div id="mobile-info" style="display: block;"> 
				               		<label class="float-left normal-label" style="display: block;">手机/Mobile</label>
				               		<input type="text" tabindex="1" name="j_mobile" id="j_mobile" class="textinput"/>
				               	</div>
				               	<div style="clear:both; height:0;overflow:hidden;"></div>
				               	
				               	<div id="checkcode" style="display: block;"> 
				               		<div><label class="float-left normal-label" style="display: block;"><spring:message code="logon.form.checkcode" /></label></div>
				               		<div>
				               				<input type="text" tabindex="3" name="j_checkcode2" id="j_checkcode2" class="textCKcode" style="display: block;float: left;"/>
					               			<img id="j_checkcodeImgCode2" src="Kaptcha.jpg"
						                 	 style="height: 27px; width: 75px; cursor: hand; display: block;float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
						               	   onclick="javascript:updateCheckCodeImg('j_checkcodeImgCode2')"/>
						               	   <span style="display: block; float: left; text-align:center;line-height: 250%;"><font size="2px">点击更新/Click to refresh code</font></span>
					                </div>
				               	</div>
				               	<div style="clear:both; height:0;overflow:hidden;"></div>
				               	
				               	<div id="checksms" style="display: block;"> 
					               <div>	<label class="float-left normal-label">短信验证码/SMS Check Code</label>
						                <input type="text" tabindex="4" name="j_smscode" id="j_smscode" class="textCKcode" style="display: block; float: left;"/>
						                <input type="button" tabindex="5" name="bnt_sms" id="bnt_sms" class="btn btn_big" value="发送/Send" onclick="fsendsms()" style="display: block; float: left;"/>
					                </div>
					                <label id="lb_tipsms" class="float-left normal-label" style="line-height:250%;  width: 320px;"></label>
					            </div>
								<div style="clear:both; height:0;overflow:hidden;"></div>
							</div>
							
							<div id="changeok" style="display: none;">
								<label class="normal-label">您的手机和密码已经更改，请重新登录.</label>
			               		<label class="normal-label">Your phone and the password has been changed, please log in again. </label>
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

        
        <script type="text/javascript" src="themes/default/js/jquery.smartWizard-2.0.js"></script>
        <script type="text/javascript" src="js/firstlogin.js"></script>
        <script type="text/javascript">
		        showCardModel();
	      </script>
       