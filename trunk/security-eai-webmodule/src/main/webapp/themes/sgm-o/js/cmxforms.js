/**
 * 执行验证。
 */
var usernameErrormsg = '账号不合法，请重新输入/account is invalid, please re-enter';
var passwordErrormsg = '账号密码不合法，请重新输入/password is invalid, please re-enter';
var checkcodeErrormsg = '验证码不合法，请重新输入/verification code is invalid, please re-enter';
var smscodeErrormsg = '短信码不合法，请重新输入/sms code is invalid, please re-enter';
var newpasswordErrormsg = '新密码不合法，请重新输入/password is invalid, please re-enter';
var confirmpasswordErrormsg = '确认新密码不合法，请重新输入/password is invalid, please re-enter';
var equalpasswordErrormsg = '确认新密码与新密码不相同，请重新输入/the two password is not the same, please re-enter';

var idcardErrormsg = '身份证输入不有误，请重新输入/ idcard is invalid, please re-enter';
var mobileErrormsg = '手机号输入不有误，请重新输入/ mobile is invalid, please re-enter';

$().ready(function() {
  $("#sidebar").collapse({
    show : function() {
      this.animate({
        opacity : 'toggle',
        height : 'toggle'
      }, 200);
    },
    hide : function() {
      this.animate({
        opacity : 'toggle',
        height : 'toggle'
      }, 200);
    }
  });
  
  $('#j_username,#j_password,#j_checkcode,#j_smscode,#j_new_password,#j_confirm_password').poshytip({
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
  whoami();
});

// get login account
function whoami() {
  var username = $.cookie('suid');
  if (username) {
    $('#j_username').val(username);
    $('#remember_me').attr('checked', 'checked');
  }
}


// validate field
function validateField(id, regex, errormsg, trim) {
  var value = trim ? $.trim($(id).val()) : $(id).val();
  if(undefined == value){
    return true;
  }
  
  if (!regex.test(value)) {
    showMsg(id, errormsg);
    return false;
  }
  $(id).poshytip('hide');
  return true;
}

//显示提示信息
function showMsg(id, errormsg){
  $(id).poshytip('update', errormsg);
  $(id).poshytip('show');
  //$(id).poshytip('hide');
  //$(id).poshytip('hideDelayed', 2000);
}

// validate login fields
function validate(form) {
  var flag = validateField("#j_username", /.{1,100}$/, usernameErrormsg, true);
  flag = flag && validateField("#j_password", /.{1,20}$/, passwordErrormsg, false);
  flag = flag && validateField("#j_checkcode", /^[A-Za-z0-9]{5}$/, checkcodeErrormsg, false);
  flag = flag && validateField("#j_smscode", /^[0-9]{6}$/, smscodeErrormsg, false);
  flag = flag && validateField("#j_new_password", /.{1,20}$/, newpasswordErrormsg, false);
  flag = flag && validateField("#j_confirm_password", new RegExp("^" + $.trim($("#j_new_password").val()) + "$"), equalpasswordErrormsg, false);

  return flag;
}


//validate loseuser fields
function validateLoseUser(form) {
  var flag = validateField("#j_idcard", /^[A-Za-z0-9]{18}$/, idcardErrormsg, true);
  flag = flag && validateField("#j_checkcode", /^[A-Za-z0-9]{5}$/, checkcodeErrormsg, false);

  return flag;
}


//validate losepwd stepone fields
function validateLosePwdOne(form) {
  var flag = validateField("#j_username", /.{1,100}$/, usernameErrormsg, true);
  flag = flag && validateField("#j_checkcode", /^[A-Za-z0-9]{5}$/, checkcodeErrormsg, false);
  flag = flag && validateField("#j_smscode", /^[0-9]{6}$/, smscodeErrormsg, false);

  return flag;
}

//validate losepwd steptwo fields
function validateLosePwdTwo() {
  var flag = validateField("#j_npassword2", new RegExp("^" + $.trim($("#j_npassword").val()) + "$"), equalpasswordErrormsg, false);
  return flag;
}


//validate regmobile fields
function validateRegMobile(form) {
  var flag = validateField("#j_mobile",  /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/, mobileErrormsg, true);
  flag = flag && validateField("#j_checkcode", /^[A-Za-z0-9]{5}$/, checkcodeErrormsg, false);
  flag = flag && validateField("#j_smscode", /^[0-9]{6}$/, smscodeErrormsg, false);

  return flag;
}
