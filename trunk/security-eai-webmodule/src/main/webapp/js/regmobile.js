//check smscode
function regmobileStepOne() {
	var result = false;
	
	$.ajax( {
		type : "post",
		url : "checksmscode.do",
		dataType:"json",
		async: false,
        data:{
            "j_username":$('#j_username').val(),
            "j_checkcode":$('#j_checkcode').val(),
            "j_smscode":$('#j_smscode').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				result = true;
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "提交数据失败，代码:" +html.status+ "，请稍候再试" + "/Failed to submit data, code:" + html.status + ", please try again later.");
		}
	});
	
	return result;
}

//modify mobile
function regmobileStepTwo() {
	var result = false;
	
	$.ajax( {
		type : "post",
		url : "changemobile.do",
		dataType:"json",
		async: false,
        data:{
            "j_username":$('#j_username').val(),
            "j_checkcode":$('#j_checkcode').val(),
            "j_mobile":$('#j_mobile').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				result = true;
				
				var mobile = $("#j_mobile").val();
				if(mobile.length==11) {
					$("#lb_usr_mobile").text(" " + mobile.substring(0,3) + "-XXXX-" + mobile.substring(7,11) + " ");
				} else {
					$("#lb_usr_mobile").text("错误的手机号/ Error mobile number: " + mobile);
				}
			} else {
				$('#wizard').smartWizard('showMessage', "修改手机号失败，请稍候再试" + "/Modify phone number failed, please try again later");
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "修改手机号失败，代码:" +html.status+ "，请稍候再试/Modifying phone number failed, code:" + html.status + ", please try again later.");
		}
	});
	
	return result;
}

//更新图形验证码
function updateCheckCodeImg() {
  var imgCode = document.getElementById("j_checkcodeImgCode");
  imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
}

function finshRegMobile(){
  $("#authenForm").attr("action", document.location.href);
  //$("#op").val("remindpassword");
  $("#authenForm").submit();
}

$(document).ready(function() {
	// Smart Wizard
	$('#wizard').smartWizard({
		labelNext : '下一步/Next',
		labelFinish: '完成/Finsh',
		onFinish : onFinishCallback,
		transitionEffect : false,
		cycleSteps : false,
		enableFinishButton : false,
		onLeaveStep : leavestepCallback,
		onShowStep: showstepCallback
	});

	function onFinishCallback() {
		//$('#wizard').smartWizard('showMessage', 'Finish Clicked');
		//return losepwdStepSubmit();
		finshRegMobile();
		return true;
	}  
	
	function showstepCallback(obj) {
		var step_num = obj.attr('rel');
		if(step_num==2) {
			if(!regmobileStepTwo()){
				$('.buttonNext').hide();
				$('.buttonFinish').show();
				//$(".buttonNext").hide();
				//return false;
			}
		} else if (step_num==1) {
			$('.buttonFinish').hide();
		}
		return true;
	}

	function leavestepCallback(obj) {
		// get the current step number
		var step_num = obj.attr('rel');
		if(step_num==1) {
			
			if(!validateRegMobile(obj)) {
				return false;
			}
			
			if(!regmobileStepOne()){
				$('#wizard').smartWizard('showMessage', "验证码错误/Verification code error.");
				return false;
			} else {
				$(".buttonNext").hide();
				$('.buttonFinish').show();
			}
		} 
		return true;
	}
	
	$('#j_mobile,#j_checkcode,#j_smscode').poshytip({
	    className : 'tip-yellowsimple',
	    showOn : 'none',
	    alignTo : 'target',
	    alignX : 'inner-left',
	    offsetX : 50,
	    offsetY : 5,
	    fade : false,
	    slide : true
	  });

});