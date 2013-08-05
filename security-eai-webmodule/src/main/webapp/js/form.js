/**
 * 显示提示信息。
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
					$("#lb_usr_mobile").text("错误的手机号/ error mobile number");
				}
				//setMsg('info', msg.code);
				$("#j_smscode").val(msg.code);
			}
		},
		error:function(html){
			setMsg('info', "提交数据失败，代码:" +html.status+ "，请稍候再试" + "/Failed to submit data, code:" + html.status + ", please try again later.");
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
					$("#lb_usr_mobile").text("错误的手机号/ Error Mobile Number");
				}
				//setMsg('info', msg.code);
				$("#j_smscode").val(msg.code);
				$("#j_useruid").val(msg.username);
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "提交数据失败，代码:" +html.status+ "，请稍候再试" + "/Failed to submit data, code:" + html.status + ", please try again later.");
			//alert("提交数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
}

function sendsms3() {
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
					$("#lb_usr_mobile").text("错误的手机号/ Error Mobile Mumber");
				}
				
				$("#lb_username").text(msg.displayname);
				$("#lb_userid").text(msg.username);
				
				//setMsg('info', msg.code);
				$("#j_smscode").val(msg.code);
				$("#j_useruid").val(msg.username);
			}
		},
		error:function(html){
			$('#wizard').smartWizard('showMessage', "提交数据失败，代码:" +html.status+ "，请稍候再试" + "/Failed to submit data, code:" + html.status + ", please try again later.");
			//alert("提交数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
}

function updateTimeLabel(time) {
    var btn = $("#bnt_sms");
    
    btn.attr("disabled", true);
    btn.val("" + (time--) + "秒");
    
    var a_sendcode = jQuery("#a_sendcode");
    btn.val(time <= 0 ? "发送/Send": ("" + (time) + "秒Second"));
    var hander = setInterval(function() {
        if (time <= 0) {
        	btn.attr("disabled", false);
        	
            clearInterval(hander);
            hander = null;
            btn.val("发送/Send");
            //a_sendcode.attr("disabled", false);
            jQuery("#lb_tipsms").text("");
        } else {
            btn.attr("disabled", true);
            //a_sendcode.attr("disabled", true);
            btn.val("" + (time--) + "秒Second");
            jQuery("#lb_tipsms").text("60秒后可以再次重发/Your can re-send until 60");
        }
    },
    1000);
}


//加载信息
function loading(name, overlay) {
	$('body').append('<div id="loading"><div id="overlay"></div><div id="preloader">' + name + '...</div></div>');
    /*if (overlay == 1) {
        $('#overlay').css('opacity', 0.1).fadeIn(function() {
            $('#preloader').fadeIn();
        });
        return false;
    }
    $('#preloader').fadeIn();*/
   
    $('#overlay').css('opacity', 0.1).show();
    $('#preloader').show();
}

function dounload() {
	$("#loading").remove();
}

function unloading() {
	
	
	setTimeout(dounload, 500);
   /* $('#preloader').fadeOut('fast', function() {
        $('#overlay').fadeOut();
    });*/
}
