/**
 * 执行验证。
 */
var usernameErrormsg = '账号不合法，请重新输入';
var passwordErrormsg = '账号密码不合法，请重新输入';
var checkcodeErrormsg = '验证码不合法，请重新输入';
var newpasswordErrormsg = '新密码不合法，请重新输入';
var confirmpasswordErrormsg = '确认新密码不合法，请重新输入';
var equalpasswordErrormsg = '确认新密码与新密码不相同，请重新输入';

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
  
  $('#j_username,#j_password,#j_checkcode,#j_new_password,#j_confirm_password').poshytip({
    className : 'tip-yellowsimple',
    showOn : 'none',
    alignTo : 'target',
    alignX : 'inner-left',
    offsetX : 50,
    offsetY : 5,
    fade : false,
    slide : true
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
}

// validate login fields
function validate(form) {
  var flag = validateField("#j_username", /.{1,100}$/, usernameErrormsg, true);
  flag = flag && validateField("#j_password", /.{1,20}$/, passwordErrormsg, false);
  flag = flag && validateField("#j_checkcode", /^[A-Za-z0-9]{5}$/, checkcodeErrormsg, false);
  flag = flag && validateField("#j_new_password", /.{1,20}$/, newpasswordErrormsg, false);
  flag = flag && validateField("#j_confirm_password", new RegExp("^" + $.trim($("#j_new_password").val()) + "$"), equalpasswordErrormsg, false);

  return flag;
}

