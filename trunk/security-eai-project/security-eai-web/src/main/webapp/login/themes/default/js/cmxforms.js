/**
 * 执行验证。
 */
var flag = true;//是否可提交
var usernameErrormsg = 'The account is not valid, please enter again';
var passwordErrormsg = 'The password is not valid, please enter again';
var checkcodeErrormsg = 'Verification code is not valid, please enter again';
var newpasswordErrormsg = 'The new password is not valid, please enter again';
var confirmpasswordErrormsg = 'Confirm new password is not valid, please enter again';
var equalpasswordErrormsg = 'Confirm the new password and the new password is not the same, please enter again';

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
  
  if($("#j_username").val() != undefined){
    $("#j_username").blur(function(){
      if (!validateField("#j_username", /.{1,100}$/, usernameErrormsg, true)){
        flag = false;
        $(this).focus();
      }else{
        flag = true;
        $(this).poshytip('hide');
      }
    });    
  }
  
  if($("#j_password").val() != undefined){
    $("#j_password").blur(function(){
      if (!validateField("#j_password", /.{1,20}$/, passwordErrormsg, false)){
        flag = false;
      }else{
        flag = true;
        $(this).poshytip('hide');
      }
    });    
  }
  
  if($("#j_checkcode").val() != undefined){
    $("#j_checkcode").blur(function(){
      if (!validateField("#j_checkcode", /^[A-Za-z0-9]{4}$/, checkcodeErrormsg, false)){
        flag = false;
      }else{
        flag = true;
        $(this).poshytip('hide');
      }
    });    
  }
  
  if($("#j_new_password").val() != undefined){
    $("#j_new_password").blur(function(){
      if($(this).val() == ""){
        showMsg("#j_new_password", "The new password can't for empty");
        flag = false;
      }else{
        flag = true;
        $(this).poshytip('hide');
      }
    });    
  }
  
  if($("#j_confirm_password").val() != undefined){
    $("#j_confirm_password").blur(function(){
      if($(this).val() == ""){
        showMsg("#j_confirm_password", "Confirm password can't for empty");
        flag = false;
      }else{
        if($(this).val() != $("#j_new_password").val()){
          showMsg("#j_confirm_password", "Two input password does not match");
          flag = false;
        }else{
          flag = true;
          $(this).poshytip('hide');
        }
      }
    });
  }
  
  $("form[id='authenForm'] input").keypress(function(e) {
    if (e.which == 13) {// 判断所按是否回车键
      var inputs = $("form[id='authenForm']").find("input"); // 获取表单中的所有输入框
      var idx = inputs.index(this); // 获取当前焦点输入框所处的位置
      
      if ((idx == inputs.length - 2) ||(idx == inputs.length - 1)) {// 判断是否是最后一个输入框
        //if (confirm("The last input box has input, whether to submit")) // 用户确认
        if(flag)
          $("form[id='authenForm']").submit(); // 提交表单
      } else {
        inputs[idx + 1].focus(); // 设置焦点
        inputs[idx + 1].select(); // 选中文字
      }
      return false;// 取消默认的提交行为
    }
  });
  
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
  //$(id).focus();
}

// validate login fields
function validate(form) {
  return flag;
}