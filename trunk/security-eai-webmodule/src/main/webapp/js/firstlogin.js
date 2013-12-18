function loginFirstStepOne() {
	var result = false;
	loading('验证中...', 1);

	$.ajax( {
		type : "post",
		url : "reg/check.do",
		dataType:"json",
		async: false,
        data:{
            "j_username":$('#j_username').val(),
            "j_checkcode":$('#j_checkcode').val(),
            "j_idcard":$('#j_idcard').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				$('#lb_username').text(msg.displayname);
				$('#lb_userid').text(msg.username);
				$('#j_useruid').val(msg.username);
				
				if(msg.mobile!=null && msg.mobile!="undefined" && msg.mobile!="") {
					$('#j_mobile').val(msg.mobile);
				}
				
				result = true;
			} else {
				//$('#wizard').smartWizard('showMessage', msg.msg);
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

function losepwdStepTwo() {
	var result = false;
	loading('请求中..', 1);

	$.ajax( {
		type : "post",
		url : "checksmscode.do",
		dataType:"json",
		async: false,
        data:{
            "j_username":$('#j_username').val(),
            "j_checkcode":$('#j_checkcode2').val(),
            "j_smscode":$('#j_smscode').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				
				result = true;
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

function changeStepSubmit() {
	var result = false;
	loading('请求中..', 1);

	$.ajax( {
		type : "post",
		url : "reg/change.do",
		dataType:"json",
		async: false,
        data:{
            "j_useruid":$('#j_useruid').val(),
            "j_checkcode":$('#j_checkcode2').val(),
            "j_npassword":$('#j_npassword').val(),
            "j_mobile":$('#j_mobile').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				result = true;
				
				$("#noauth").hide();
				$("#auth").hide();
				$("#changeok").show();
				
			} else {
				$('#wizard').smartWizard('showMessage', msg.msg);
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

function goback() {
	location.href = location.href;
	return true;
}

function fsendsms() {
	$('#j_checkcode').val($('#j_checkcode2').val());
	sendsms2();
}

//更新图形验证码
function updateCheckCodeImg(key) {
  var imgCode = document.getElementById(key);
  imgCode.src = "Kaptcha.jpg?dt=" + (new Date()).getTime();
}

$(document).ready(function() {
	// Smart Wizard
	$('#wizard').smartWizard({
		labelNext : '下一步/Next',
		labelFinish: '提交/Submit',
		onFinish : onFinishCallback,
		transitionEffect : false,
		cycleSteps : false,
		enableFinishButton : false,
		onLeaveStep : leavestepCallback,
		onShowStep: showstepCallback
	});
	
	function showstepCallback(obj) {
		var step_num = obj.attr('rel');
		
		if (step_num==1) {
			$('.buttonFinish').hide();
		} else if (step_num==2) {
						
			if(!loginFirstStepOne()){
				//$('#wizard').smartWizard('showMessage', "短信验证错误/Validation error messages");
				$("#auth").hide();
				$("#noauth").show();
				
				$(".buttonNext").hide();
				$('.buttonFinish').text("返回/Return");
				$('.buttonFinish').attr("href", "javascript:goback()");
				$('.buttonFinish').unbind("click");
				$(".buttonFinish").attr("class", "buttonFinish");

				$('.buttonFinish').show();
				
				return false;
			} else {
				updateCheckCodeImg('j_checkcodeImgCode2');
				$("#noauth").hide();
				$("#auth").show();
			}
			
		} else if(step_num==3) {
			$('.buttonNext').hide();
			$('.buttonFinish').show();			
		}
		return true;
	}

	function onFinishCallback(obj) {
		if(!validateFirstLoginSubmit(obj)) {
			return false;
		}
		
		if(!changeStepSubmit()) {
			return false;
		}
		
		//gotourl
		location.href=$("#gotourl").val();
	}  

	function leavestepCallback(obj) {
		// get the current step number
		var step_num = obj.attr('rel');
		if(step_num==1) {
			if(!validateFirstLoginOne(obj)) {
				return false;
			}
		} else if(step_num==2) {
			if(!validateFirstLoginTwo(obj)) {
				return false;
			}
			
			if(!losepwdStepTwo()) {
				$('#wizard').smartWizard('showMessage', "短信验证错误/Validation error messages");
				return false;
			}
		}

		return true;
	}
	
	$('#j_username,#j_checkcode,#j_smscode,#j_npassword,#j_npassword2,#j_idcard,#j_mobile').poshytip({
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

