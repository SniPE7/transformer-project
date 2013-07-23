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
							<a href="#step-2" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">2</span> <span class="stepDesc"> 修改口令
								<br />
								<small></small> </span> </a>
						</li>
					</ul>
					<div id="step-1">
							<div id="normal-label">
		                		<label for="ac_username"><spring:message code="logon.form.username" /></label>
			                </div>
			               	<div id="badge-label"> <label for="ac_username">请将工卡插入读卡器 或 输入用户名</label>
				               	<label for="ac_username" style="display: block; float: left;">Please place your badge over reader, Or enter your username</label> 
			               	</div>
							
			               	<div style="clear:both; height:0;overflow:hidden;"></div>
			                <input type="text" tabindex="1" name="j_username" id="j_username" class="textinput" style="display: block; float: left;"/>
			                <input type="hidden" tabindex="100" name="j_useruid" id="j_useruid"/>
			                <div id="forgotbox" style="display: block; float: left;text-align:center"><a tabindex="9" href="loseuser.do" class="forgotpass">忘记用户名/Forget Username?</a></div>
			                <div style="clear:both; height:0;overflow:hidden;"></div>
			                
			               
			                <label class="float-left"><spring:message code="logon.form.checkcode" /></label>
			                <input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" style="display: block; float: left;"/>
			                <img id="j_checkcodeImgCode" src="Kaptcha.jpg"
			                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
			                  onclick="javascript:updateCheckCodeImg()"/>
			                <span style="display: block; float: left; text-align:center"><font size="2px"> 点击图片更新验证码</font></span>
			                 <div style="clear:both; height:0;overflow:hidden;"></div>
			                 
			                <label class="float-left">短信验证码/SMS Check Code</label>
			                <input type="text" tabindex="4" name="j_smscode" id="j_smscode" class="textCKcode" style="display: block; float: left;"/>
			                <input type="button" tabindex="5" name="bnt_sms" id="bnt_sms" value="发送/Send" onclick="sendsms2()" style="display: block; float: left;"/>
			                <label id="lb_tipsms" class="float-left"></label>
			               	<div style="clear:both; height:0;overflow:hidden;"></div>
			               
			                <label class="float-left">短信验证码将通过短信发送到您的手机.</label>
			                <label class="float-left">SMS check code will be sent to the registered mobile phone.</label>
			                <label id="lb_usr_mobile" class="float-left"></label>

					</div>
					<div id="step-2">
						 <label >姓名/Name:</label>
			             <label id="lb_username" >张三</label>
			             
			             <label >用户名/Username:</label>
			             <label id="lb_userid" >User001</label>
			             
			             
			             <label for="ac_username">新口令/New Password</label>
			             <input type="password" tabindex="7" name="j_npassword" id="j_npassword" class="textinput" style="display: block; float: left;"/>
			             
			             
			             <label for="ac_username">新口令/New Password</label>
			             <input type="password" tabindex="8" name="j_npassword2" id="j_npassword2" class="textinput" style="display: block; float: left;"/>

					</div>

				</div><!-- End SmartWizard Content -->
        </div><!-- End of #content -->
        
         <script type="text/javascript" src="themes/default/js/jquery.smartWizard-2.0.js"></script>
        <script type="text/javascript" src="js/losepwd.js"></script>
       