function loseuserStepOne() {
	var result = false;
	
	$.ajax( {
		type : "post",
		url : "checkcode.do",
		dataType:"json",
		async: false,
        data:{
            "j_checkcode":$('#j_checkcode').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				result = true;
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "提交数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
	
	return result;
}

function loseuserStepTwo(issend) {
	var result = false;
	
	$.ajax( {
		type : "post",
		url : "checkidcard.do",
		dataType:"json",
		async: false,
        data:{
            "j_idcard":$('#j_idcard').val(),
            "j_checkcode":$('#j_checkcode').val(),
            "issend":issend
        },
		success : function(msg) {
			if(msg.status=='success') {
				result = true;
				
				var mobile =  msg.mobile;
				if(mobile.length==11) {
					$("#lb_usr_mobile").text(" " + mobile.substring(0,3) + "-XXXX-" + mobile.substring(7,11) + " ");
				} else {
					$("#lb_usr_mobile").text("错误的手机号/ error mobile number: " + mobile);
				}
			} else {
				//$('#wizard').smartWizard('showMessage', "提交数据失败，请稍候再试");
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "提交数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
	
	return result;
}


//更新图形验证码
function updateCheckCodeImg() {
  var imgCode = document.getElementById("j_checkcodeImgCode");
  imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
}

function cancelLosePassword(){
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
		return true;
	}  
	
	function showstepCallback(obj) {
		var step_num = obj.attr('rel');
		if(step_num==2) {
			if(!loseuserStepTwo("false")){
				//$('#wizard').smartWizard('showMessage', "验证码错误");
				$("#nomobile").show();
				$("#hadmobile").hide();
				$(".buttonNext").attr("disabled",false);

				return true;
			} else {
				$("#nomobile").hide();
				$("#hadmobile").show();
			}
		}
		return true;
	}

	function leavestepCallback(obj) {
		// get the current step number
		var step_num = obj.attr('rel');
		if(step_num==1) {
			if(!loseuserStepOne()){
				$('#wizard').smartWizard('showMessage', "验证码错误");
				return false;
			} else {
				$(".buttonNext").text("发送用户名/Send");
			}
		} else if(step_num==2) {
			if(!loseuserStepTwo("true")){
				$('#wizard').smartWizard('showMessage', "短信发送失败");
				return false;
			} else {
				$(".buttonNext").hide();
			}
		}

		return true;
	}

});
