<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
function check() {

}

var init = 0;

function checkCardStatus() {
	var badgeTool = document.getElementById("badgeTool");
	if (badgeTool == "undifined")
	{
		alert("未安装ActiveX控件。");
		return;
	}

	var cardUid = "";

	try {
		var hasDevice = badgeTool.HasDevice();
		if (hasDevice == "0") {
			return;
		}

		cardUid = badgeTool.GetCardUID();

		if (cardUid.length > 0) {
			clearInterval(cardTimer);

			// alert("Card UID=" + cardUid);
			$("#carduid").attr("value", cardUid);
			
			$("#cardForm").attr("action", "card/selectop.do");
			$("#cardForm").submit();
		}
	} catch (Exception) {
		clearInterval(cardTimer);
		return;
	}

}

cardTimer = setInterval("checkCardStatus();", 1000);

</script>

<!-- <object id="badgeTool" style="display:none" classid="clsid:395E6CF3-3084-487D-9606-EDAA8B2C4E3C" codebase='/eaiweb/control/BadgeTool.cab#version=1,0,0,0'></object>  -->
<!-- <object id="badgeTool" style="display:none" classid="clsid:395E6CF3-3084-487D-9606-EDAA8B2C4E3C"></object> -->

<div id="content" class="conten-login">
			<div class="aui-message error invisible" id="errorDivMsg">
			  <!-- shown with class="aui-message error" -->
			</div>
			<div class="aui-message info invisible" id="infoDivMsg">
			  <!-- shown with class="aui-message info" -->
			</div>

    <div id="acloginpod">
      <div id="acloginpanel" class="loginpanel">
        <form class="aui cmxform" method="post" id="cardForm" name="cardForm" action="card/insert2.do" >
        <input type="hidden" name="op" value="card" />
        <fieldset>
        
          <span style="display: block; clear: both;"><font size="2px">请将工卡插入读卡器.</font></span>
          <span style="display: block; clear: both;"><font size="2px">Please place your badge over reader.</font></span>
          <br/>
	      <input type="text" tabindex="1" name="carduid" id="carduid" value="" class="textinput" />

          <div class="clearfix">&nbsp;</div>
          
        </fieldset>
      </form>
    </div><!-- End of #acloginpanel -->
  </div><!-- End of #acloginpod -->
</div><!-- End of #content -->

<script>setMsg('info','<spring:message code="card.register" />');</script>
