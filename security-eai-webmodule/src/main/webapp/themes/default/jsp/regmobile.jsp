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
					<div id="step-1" style="padding-top: 20px; padding-left: 20px;">

			               	<div id="name-info" style="display: block;"> 
			               		<div><label class="normal-label" style="line-height: 20px;">姓名/Name:</label></div>
			               		<div><label class="normal-label" id="name" style="line-height: 20px; margin-bottom: 2px; margin-left: 20px; font-weight:normal;"> <%=request.getAttribute("j_username") != null ? request.getAttribute("j_username") : ""%></label></div>
			               	</div>
			               	<div style="clear:both; height:0;overflow:hidden;"></div>
			               	
			               	<div id="id-info" style="display: block;"> 
			               		<div><label class="normal-label" style="line-height: 20px;">用户名/Username:</label></div>
			               		<div><label id="usernmae" class="normal-label" style="line-height: 20px; margin-bottom: 10px; margin-left: 20px; font-weight:normal;"> <%=request.getAttribute("show_username") != null ? request.getAttribute("show_username") : ""%></label></div>
			               	</div>
			               	<div style="clear:both; height:0;overflow:hidden;"></div>
			               	
			               	<div id="mobile-info" style="display: block;"> 
			               		<label class="float-left normal-label" style="display: block;">手机/Mobile</label>
			               		<input type="text" tabindex="1" name="j_mobile" id="j_mobile" class="textinput"/>
			               	</div>
			               	<div style="clear:both; height:0;overflow:hidden;"></div>
			               	
			               	<div id="checkcode" style="display: block;"> 
			               		<div><label class="float-left normal-label" style="display: block;"><spring:message code="logon.form.checkcode" /></label></div>
			               		<div>
			               				<input type="text" tabindex="3" name="j_checkcode" id="j_checkcode" class="textCKcode" style="display: block;float: left;"/>
				               			<img id="j_checkcodeImgCode" src="Kaptcha.jpg"
					                 	 style="height: 27px; width: 75px; cursor: hand; display: block;float: left; margin-left: 10px; margin-top: 3px;" title="看不清，请点击"
					               	   onclick="javascript:updateCheckCodeImg()"/>
					               	   <span style="display: block; float: left; text-align:center;line-height: 250%;"><font size="2px">点击更新/Click to refresh code</font></span>
				                </div>
			               	</div>
			               	<div style="clear:both; height:0;overflow:hidden;"></div>
			               	
			               	<div id="checksms" style="display: block;"> 
				               <div>	<label class="float-left normal-label">短信验证码/SMS Check Code</label>
					                <input type="text" tabindex="4" name="j_smscode" id="j_smscode" class="textCKcode" style="display: block; float: left;"/>
					                <input type="button" tabindex="5" name="bnt_sms" id="bnt_sms" class="btn btn_big" value="发送/Send" onclick="sendsms2()" style="display: block; float: left;"/>
				                </div>
				                <label id="lb_tipsms" class="float-left normal-label" style="line-height:250%;"></label>
				            </div>
							<div style="clear:both; height:0;overflow:hidden;"></div>

					</div>
					<div id="step-2" style="padding-top: 20px; padding-left: 20px;">
			               	<div id="changemobile"> 
			               	
			               		<label class="normal-label">新的手机已经注册成功!</label>
			               		<label class="normal-label">Mobile registration succeed! </label>
			               		<label id="lb_usr_mobile" class="float-left normal-label" style="color:red;"></label>
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

        