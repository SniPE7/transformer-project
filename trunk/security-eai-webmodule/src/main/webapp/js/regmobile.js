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
			$('#wizard').smartWizard('showMessage', "�ύ����ʧ�ܣ�����:" +html.status+ "�����Ժ�����");
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
					$("#lb_usr_mobile").text("������ֻ���/ error mobile number: " + mobile);
				}
			} else {
				$('#wizard').smartWizard('showMessage', "�޸��ֻ���ʧ�ܣ����Ժ�����");
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "�޸��ֻ���ʧ�ܣ�����:" +html.status+ "�����Ժ�����");
		}
	});
	
	return result;
}

//����ͼ����֤��
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
		labelNext : '��һ��/Next',
		labelFinish: '���/Finsh',
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
				//$(".buttonNext").hide();
				//return false;
			}
		}
		return true;
	}

	function leavestepCallback(obj) {
		// get the current step number
		var step_num = obj.attr('rel');
		if(step_num==1) {
			if(!regmobileStepOne()){
				$('#wizard').smartWizard('showMessage', "��֤�����");
				return false;
			} else {
				$(".buttonNext").hide();
			}
		} 
		return true;
	}

});