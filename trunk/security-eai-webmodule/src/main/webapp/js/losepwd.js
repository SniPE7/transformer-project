function losepwdStepOne() {
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


function losepwdStepSubmit() {
	var result = false;
	
	$.ajax( {
		type : "post",
		url : "changelosepwd.do",
		dataType:"json",
		async: false,
        data:{
            "j_useruid":$('#j_useruid').val(),
            "j_checkcode":$('#j_checkcode').val(),
            "j_npassword":$('#j_npassword').val()
        },
		success : function(msg) {
			if(msg.status=='success') {
				result = true;
				//$("#j_useruid").val(msg.usrename);
				$('#wizard').smartWizard('showMessage', "修改密码成功！/Change password success!");
				
			} else {
				$('#wizard').smartWizard('showMessage', msg.msg);
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "提交数据失败，代码:" +html.status+ "，请稍候再试" + "/Failed to submit data, code:" + html.status + ", please try again later.");
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
		} else if(step_num==2) {
			$('.buttonNext').hide();
			$('.buttonFinish').show();
			
			//$('.buttonFinish').attr("onclick", "return checkPwd(this);");
		}
		return true;
	}
	
	function checkPwd(){
		if(!validateLosePwdTwo()) {
			return false;
		}
	}

	function onFinishCallback() {
		//alert("start...");
		if(!validateLosePwdTwo()) {
			//alert("error");
			return false;
		}
		
		//$('#wizard').smartWizard('showMessage', 'Finish Clicked');
		if(!losepwdStepSubmit()) {
			return false;
		}
		
		//gotourl
		location.href=$("#gotourl").val();
	}  

	function leavestepCallback(obj) {
		// get the current step number
		var step_num = obj.attr('rel');
		//$('#wizard').smartWizard('showMessage', step_num);
		if(step_num==1) {
			
			if(!validateLosePwdOne(obj)) {
				return false;
			}
			
			if(!losepwdStepOne()){
				$('#wizard').smartWizard('showMessage', "短信验证错误/Validation error messages");
				return false;
			} 
		}

		return true;
	}
	
	$('#j_username,#j_checkcode,#j_smscode,#j_npassword,#j_npassword2').poshytip({
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

