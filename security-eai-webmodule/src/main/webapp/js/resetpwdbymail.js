function validateLosePwdOne(form) {
  var flag = validateField("#j_username", /.{1,100}$/, usernameErrormsg, true);

  return flag;
}

function resetPwdStepOne() {
	var result = false;
	loading('请求中..', 1);

	$.ajax( {
		type : "post",
		url : "resetpwd/sendmail.do",
		dataType:"json",
		async: false,
        data:{
            "j_username":$('#j_username').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				//alert(msg.mail);
				$('[id=lb-mail]').text(msg.mail);
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
		$('.buttonFinish').hide();

		if (step_num==2) {
			$('.buttonNext').hide();
			
			if(!resetPwdStepOne()) {
				$("#mail").hide();
				$("#nomail").show();
			} else {
				$("#nomail").hide();
				$("#mail").show();
			}
		}
		
		return true;
	}

	function onFinishCallback() {
		
		//gotourl
		location.href=$("#gotourl").val();
	}  

	function leavestepCallback(obj) {
		var step_num = obj.attr('rel');
		$('.buttonFinish').hide();
		
		if(step_num==1) {
			
			if(!validateLosePwdOne(obj)) {
				return false;
			}
		}

		return true;
	}
	
	$('#j_username').poshytip({
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




