/**
 * ��ʾ��ʾ��Ϣ��
 * 
 * @param type
 * @param txt
 */
function setMsg(type, txt) {
  var div = document.getElementById("infoDivMsg");
  if(type == "error"){
    div.className = "aui-message info invisible";
  }else{
    div.className = "aui-message error invisible";
  }
  var divMsg = document.getElementById(type +"DivMsg");
  divMsg.className = "aui-message " + type;
  var span = document.createElement("span");
  span.className = "aui-icon icon-" + type;
  divMsg.appendChild(span);
  var p = document.createElement("p");
  p.appendChild(document.createTextNode(txt));
  divMsg.appendChild(p);
}

function sendsms() {
	$.ajax( {
		type : "post",
		url : "getsmscode.do",
		dataType:"json",
        data:{
            "j_username":$('#j_username').val(),
            "j_checkcode":$('#j_checkcode').val()
        },
		success : function(msg) {
			if(msg.status=='fail') {
				setMsg('error', msg.msg);
			} else if(msg.status=='success') {
				updateTimeLabel(60);
				
				var mobile =  msg.mobile;
				if(mobile.length==11) {
					$("#lb_usr_mobile").text(" " + mobile.substring(0,3) + "-XXXX-" + mobile.substring(7,11) + " ");
				} else {
					$("#lb_usr_mobile").text("������ֻ���/ error mobile number");
				}
				//setMsg('info', msg.code);
				$("#j_smscode").val(msg.code);
			}
		},
		error:function(html){
			setMsg('info', "�ύ����ʧ�ܣ�����:" +html.status+ "�����Ժ�����");
		}
	});
}

function sendsms2() {
	$.ajax( {
		type : "post",
		url : "getsmscode.do",
		dataType:"json",
        data:{
            "j_username":$('#j_username').val(),
            "j_checkcode":$('#j_checkcode').val()
        },
		success : function(msg) {
			if(msg.status=='fail') {
				$('#wizard').smartWizard('showMessage', msg.msg);
			} else if(msg.status=='success') {
				updateTimeLabel(60);
				
				var mobile =  msg.mobile;
				if(mobile.length==11) {
					$("#lb_usr_mobile").text(" " + mobile.substring(0,3) + "-XXXX-" + mobile.substring(7,11) + " ");
				} else {
					$("#lb_usr_mobile").text("������ֻ���/ error mobile number");
				}
				//setMsg('info', msg.code);
				$("#j_smscode").val(msg.code);
				$("#j_useruid").val(msg.username);
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "�ύ����ʧ�ܣ�����:" +html.status+ "�����Ժ�����");
			//alert("�ύ����ʧ�ܣ�����:" +html.status+ "�����Ժ�����");
		}
	});
}

function updateTimeLabel(time) {
    var btn = $("#bnt_sms");
    
    btn.attr("disabled", true);
    btn.val("" + (time--) + "��");
    
    var a_sendcode = jQuery("#a_sendcode");
    btn.val(time <= 0 ? "����/Send": ("" + (time) + "��"));
    var hander = setInterval(function() {
        if (time <= 0) {
        	btn.attr("disabled", false);
        	
            clearInterval(hander);
            hander = null;
            btn.val("����/Send");
            //a_sendcode.attr("disabled", false);
            jQuery("#lb_tipsms").text("");
        } else {
            btn.attr("disabled", true);
            //a_sendcode.attr("disabled", true);
            btn.val("" + (time--) + "��");
            jQuery("#lb_tipsms").text("60�������ٴ��ط�/Your can re-send until 10");
        }
    },
    1000);
}
