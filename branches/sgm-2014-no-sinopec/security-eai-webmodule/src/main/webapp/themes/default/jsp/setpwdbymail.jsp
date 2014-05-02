<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link type="text/css" rel="stylesheet" href="themes/default/styles/smart_wizard.css">

<div>
	<div class="swMain" id="wizard">

		<ul class="anchor">
			<li><a onfocus="this.blur()" onclick="return false"
				href="#step-1" class="done" isdone="1" rel="1"> <span
					class="stepNumber">1</span> <span class="stepDesc"> 输入找回账号 <br>
						<small>Enter user information</small>
				</span>
			</a></li>
			<li><a onfocus="this.blur()" onclick="return false"
				href="#step-2" class="done" isdone="1" rel="2"> <span
					class="stepNumber">2</span> <span class="stepDesc"> 安全邮件验证 <br>
						<small>Secure E-mail validation</small>
				</span>
			</a></li>
			<li><a onfocus="this.blur()" onclick="return false"
				href="#step-3" class="selected" isdone="1" rel="3"> <span
					class="stepNumber">3</span> <span class="stepDesc"> 设置新口令 <br>
						<small>Change password</small>
				</span>
			</a></li>
		</ul>

		<input type="hidden" value="" name="gotourl" id="gotourl">
		<div class="stepContainer" style="height: 327px;">
			<div style="padding-top: 20px; padding-left: 20px; display: none;" id="step-1" class="content">
				
			</div>
			<div style="padding-top: 20px; padding-left: 20px; display: none;" id="step-2" class="content">

			</div>
			<div style="padding-top: 20px; padding-left: 20px; display: block;"
				id="step-3" class="content">
				<br>
				<br> 
				
				<%
					boolean isAvail = (Boolean)request.getAttribute("isavail");
					if(!isAvail) {
				%>
					<div style="display: block;" id="novalidate">
						<label class="normal-label">您的找回密码链接已经失效！</label> <label
							class="normal-label">You retrieve password link have failed! </label> <label class="normal-label"><a
							href="resetpwdbymail.do">重新找回密码/To reclaim the password</a> </label>
					</div>
				<%
					} else {
				%>
					<div style="display: block;" id="div-changepwd">
						<label class="normal-label float-left" for="ac_username">新密码/New
							Password</label> <input type="password" style="display: block;"
							class="textinput" id="j_npassword" name="j_npassword" tabindex="7">
		
		
						<label class="normal-label float-left" for="ac_username">确认新密码/Affirm
							New Password</label> <input type="password" style="display: block;"
							class="textinput" id="j_npassword2" name="j_npassword2"
							tabindex="8">
					</div>
					<label id="lb-errormsg" class="float-left normal-label" style="color:red;"></label>
					
					<div id="div-chgpwd-success" style="display: none;"> 
		               		<label class="normal-label">修改密码成功！</label>
		               		<label class="normal-label">change password successful! </label>
		            </div>
				<%
					}
				%>
			</div>
		</div>
		<div class="actionBar">
			<div class="loader">Loading</div>
			<a href="javascript:void(0)" class="buttonFinish" onclick="return resetPwdStepSubmit();">提交/Submit</a>
		</div>
	</div>
	
	<% if(!isAvail) {%>
		<script type="text/javascript">
			$('.buttonFinish').hide();
		</script>
	<%} %>
	<!-- End SmartWizard Content -->
</div>
<!-- End of #content -->
<!-- <object id="badgeTool" style="display:none" classid="clsid:395E6CF3-3084-487D-9606-EDAA8B2C4E3C"></object> -->
			<script type="text/javascript">
		        $(document).ready(function() {
		        	$('#j_npassword,#j_npassword2').poshytip({
		        	    className : 'tip-yellowsimple',
		        	    showOn : 'none',
		        	    alignTo : 'target',
		        	    alignX : 'center',
		        	    offsetX : 50,
		        	    offsetY : 5,
		        	    fade : false,
		        	    slide : true,
		        	    timeOnScreen : 2000
		        	  });
		
		        });
		        
		        function validatePwd() {
		        	var flag = validateField("#j_npassword2", new RegExp("^" + $.trim($("#j_npassword").val()) + "$"), equalpasswordErrormsg, false);
		        	return flag;
		        }
		        
		        function resetPwdStepSubmit() {
		        	$('[id=lb-errormsg]').text("");
		        	
		        	if(!validatePwd()) {
		        		return false;
		        	}
		        	
		        	var result = false;
		        	loading('请求中..', 1);

		        	$.ajax( {
		        		type : "post",
		        		url : "resetpwd/changepwd.do",
		        		dataType:"json",
		        		async: false,
		                data:{
		                    "j_npassword":$('#j_npassword').val()
		                },
		        		success : function(msg) {
		        			if(msg.status=='success') {
		        				//alert(msg);
		        				//$('[id=lb-mail]').text(msg.mail);
		        				$('#div-changepwd').hide();
		        				$('.buttonFinish').hide();
		        				
		        				$('#div-chgpwd-success').show();
		        				result = true;
		        			} else {
		        				//$('#wizard').smartWizard('showMessage', "修改密码错误/change password error messages [" + msg.msg + "]");
		        				$('[id=lb-errormsg]').text(msg.msg);
		        				//alert(msg.msg);
		        			}
		        			unloading();
		        		},
		        		error:function(html){
		        			unloading();
		        			$('#wizard').smartWizard('showMessage', "提交数据失败，代码:" +html.status+ "，请稍候再试" + "/Failed to submit data, code:" + html.status + ", please try again later.");
		        		}
		        	});
		        	
		        	return result;
		        }
	      </script>


