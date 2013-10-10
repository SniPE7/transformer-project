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
				
				if(msg.matchcode!=null && msg.matchcode!="") {
					$("#j_matchcode").val(msg.matchcode);
				}
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
            "j_checkcode":$('#j_checkcode').val(),
            "j_mobile":$('#j_mobile').val()
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


/***  工卡模块  ***/

function checkCardDevice() {
	var result = false;
	
	var badgeTool = document.getElementById("badgeTool");
	if (badgeTool == "undifined")
	{	
		//没有安装读卡器控件,默认只支持用户名
		//alert("未安装ActiveX控件。");
		return false;
	}

	try {
		var hasDevice = badgeTool.HasDevice();
		if(hasDevice) {
			result = true; 
		}
	} catch(Exception) {
		return false;
	}
	
	return result;
}

function getCardUid() {
	var result = "";
	
	var badgeTool = document.getElementById("badgeTool");
	if (badgeTool == "undifined")
	{
		//alert("未安装ActiveX控件。");
		return result;
	}

	var cardUid = "";	
	var hasDevice = badgeTool.HasDevice();
	if(hasDevice) {
		cardUid = badgeTool.GetCardUID();
		if (cardUid!=null && cardUid.length > 0) {
			result = cardUid;
		}
	}
	
	return result;
}


//工卡读取定时器
var cardTimer;

function showCardModel(){
	if(checkCardDevice()) {
		//display tag card
		$("#normal-label").hide();
		$("#badge-label").show();
		
		cardTimer = setInterval("fillCardUid();", 1000);

	} else {
		//display only username
		$("#badge-label").hide();
		$("#forgotuid_box").hide();
		$("#normal-label").show();
	}
}

function fillCardUid(){
	var cardUid = getCardUid();
	if (cardUid.length != 0) {
	//if (cardUid.length == 0) {
		//clearInterval(cardTimer);
		
		$("#j_username").val(cardUid);
		
		$("#badge-label").hide();
		$("#normal-label").hide();
		$("#j_username").hide();
		$("#badge-ok").show();
	}
}

/***  屏幕自适应模块  ***/
var screenZoom = "140%";
function setBodyZoom(zoomSize) {
	document.body.style.zoom=zoomSize;
}

function checkDesktop(){
	var result = false;
	
	if(getCookie("ispc")!=null) {
		return getCookie("ispc");
	}
	
	var badgeTool = document.getElementById("badgeTool");
	if (badgeTool == "undifined")
	{	
		//没有安装读卡器控件,默认只支持用户名
		//alert("未安装ActiveX控件。");
		return false;
	}

	try {
		var hasDevice = badgeTool.HasDevice();
		if(hasDevice) {
			result = true; 
		}
	} catch(Exception) {
		return false;
	}
	
	setCookie("ispc", result);
	
	//result = false;
	return result;
}

//always return 1, 
// except at non-default zoom levels in IE before version 8
function GetZoomFactor () {
	var factor = 1;
	if (document.body.getBoundingClientRect) {
	        // rect is only in physical pixel size in IE before version 8 
	    var rect = document.body.getBoundingClientRect ();
	    var physicalW = rect.right - rect.left;
	    var logicalW = document.body.offsetWidth;
	
	        // the zoom level is always an integer percent value
	    factor = Math.round ((physicalW / logicalW) * 100) / 100;
	}
	return factor;
}

function doBodyZoom(zoomSize) {
	if(checkDesktop()) {
		setBodyZoom(zoomSize);
		
		//var iMove = (document.body.scrollWidth-document.body.offsetWidth/GetZoomFactor())/2;
		//window.scrollTo(document.body.scrollWidth/2,document.body.scrollHeight/2);
		//window.scrollTo(iMove, 0);
		
		//document.body.style.overflow="scroll";
	    //document.all.controlscroll.value="hidden";
	}
}


function setCookie(name, value, Days) {
    var exp = new Date(); //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + encodeURIComponent(value) + ";expires=" + exp.toGMTString();
}

function getCookie(name) { //读取时，忽略键名大小写
    var reg = new RegExp(["(?:^| )", name, "=([^;]*)"].join(""), "i"),
    arr = document.cookie.match(reg);
    return arr ? decodeURIComponent(arr[1]) : null;
}
