<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link type="text/css" rel="stylesheet" href="themes/default/styles/smart_wizard.css">

        <div>
          <div id="wizard" class="swMain">
					<ul>
						<li>
							<a href="#step-1" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">1</span> <span class="stepDesc"> 填写手机号
								<br />
								<small></small> </span> </a>
						</li>
						<li>
							<a href="#step-2" onclick="return false" onfocus="this.blur()"> <span class="stepNumber">2</span> <span class="stepDesc"> 注册
								<br />
								<small></small> </span> </a>
						</li>
					</ul>
					<div id="step-1">

			               	<div id="name-info" style="display: block;"> 
			               		<label>姓名/Name:</label>
			               		<label id="name"><%=request.getAttribute("show_username") != null ? request.getAttribute("show_username") : ""%></label>
			               	</div>
			               	
			               	<div id="id-info" style="display: block;"> 
			               		<label>用户名/Username:</label>
			               		<label id="usernmae"><%=request.getAttribute("j_username") != null ? request.getAttribute("j_username") : ""%></label>
			               	</div>
			               	
			               	<div id="mobile-info" style="display: block;"> 
			               		<label class="float-left" style="display: block;">手机/Mobile</label>
			               		<input type="text" tabindex="1" name="j_mobile" id="j_mobile" class="textinput" style="display: block; float: left;"/>
			               	</div>
			               	
			               	<div id="checkcode" style="display: block;"> 
			               		<label class="float-left" style="display: block;"><spring:message code="logon.form.checkcode" /></label>
			               		<input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" style="display: block; float: left;"/>
			               		<img id="j_checkcodeImgCode" src="Kaptcha.jpg"
				                  style="height: 27px; width: 75px; cursor: hand; display: block; float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
				                  onclick="javascript:updateCheckCodeImg()"/>
				                  <span style="display: block; float: left; text-align:center"><font size="2px"> 点击图片更新验证码</font></span>
			               	</div>
			               	
			               	<div id="checksms" style="display: block;"> 
				               	<label class="float-left">短信验证码/SMS Check Code</label>
				                <input type="text" tabindex="4" name="j_smscode" id="j_smscode" class="textCKcode" style="display: block; float: left;"/>
				                <input type="button" tabindex="5" name="bnt_sms" id="bnt_sms" value="发送短信验证码/Send SMS Check Code" onclick="sendsms2()" style="display: block; float: left;"/>
				                <label id="lb_tipsms" class="float-left"></label>
				               	<div style="clear:both; height:0;overflow:hidden;"></div>
				            </div>

					</div>
					<div id="step-2">
			               	<div id="changemobile"> 
			               	
			               		<label>新的手机已经注册成功!</label>
			               		<label>Mobile registration succeed! </label>
			               		<label id="lb_usr_mobile" class="float-left"></label>
			               	</div>
						
					</div>

				</div><!-- End SmartWizard Content -->
        </div><!-- End of #content -->
        
        <form method="post" id="authenForm" 
                name="loginForm" 
                action="" style="display: none;">
                <input type="hidden" id="op" name="op" value="<%=request.getAttribute("op") != null ? 
                    request.getAttribute("op") : "changemobile"%>" />
				<input type="hidden" name="j_username" id="j_username" value="<%=request.getAttribute("j_username") != null ? request.getAttribute("j_username") : ""%>" />
                    
        </form>
        
        <script type="text/javascript" src="themes/default/js/jquery.smartWizard-2.0.js"></script>
        <script type="text/javascript" src="js/regmobile.js"></script>

        