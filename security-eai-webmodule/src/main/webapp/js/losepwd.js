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
			$('#wizard').smartWizard('showMessage', "�ύ����ʧ�ܣ�����:" +html.status+ "�����Ժ�����");
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
				$('#wizard').smartWizard('showMessage', "�޸�����ɹ���");
			} else {
				$('#wizard').smartWizard('showMessage', msg.msg);
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "�ύ����ʧ�ܣ�����:" +html.status+ "�����Ժ�����");
		}
	});
	
	return result;
}


//����ͼ����֤��
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
		labelNext : '��һ��/Next',
		labelFinish: '�ύ/Submit',
		onFinish : onFinishCallback,
		transitionEffect : false,
		cycleSteps : false,
		enableFinishButton : false,
		onLeaveStep : leavestepCallback
	});

	function onFinishCallback() {
		//$('#wizard').smartWizard('showMessage', 'Finish Clicked');
		return losepwdStepSubmit();
	}  

	function leavestepCallback(obj) {
		// get the current step number
		var step_num = obj.attr('rel');
		//$('#wizard').smartWizard('showMessage', step_num);
		if(step_num==1) {
			if(!losepwdStepOne()){
				$('#wizard').smartWizard('showMessage', "������֤����");
				return false;
			}
		}

		return true;
	}

});

