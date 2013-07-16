<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
function check() {

}

var init = 0;

function checkCardStatus() {
//alert("1");
	$("#cardForm").attr("action", "card/selectop.do");
	$("#cardForm").submit();
}

cardTimer = setInterval("checkCardStatus();", 1000);
</script>

<div id="content" class="conten-login">
    <div id="acloginpod">
      <div id="acloginpanel" class="loginpanel">
        <form class="aui cmxform" method="post" id="cardForm" name="cardForm" action="card/insert2.do" >
        <input type="hidden" name="op" value="card" />
        <fieldset>
        
          <span style="display: block; clear: both;"><font size="2px">请将工卡插入读卡器.</font></span>
          <span style="display: block; clear: both;"><font size="2px">Please place your badge over reader.</font></span>
          
	      <input type="text" tabindex="1" name="carduid" id="carduid" value="1234" class="textinput" />

          <div class="clearfix">&nbsp;</div>
          
        </fieldset>
      </form>
    </div><!-- End of #acloginpanel -->
  </div><!-- End of #acloginpod -->
</div><!-- End of #content -->

<script>setMsg('info','<spring:message code="login.form.error.title.tam" />');</script>
