<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link type="text/css" rel="stylesheet" href="themes/default/styles/smart_wizard.css">

<div>
	<div class="swMain" id="wizard">

		<ul class="anchor">
			<li><a onfocus="return false" onclick="return false" href="#step-1" class="done" isdone="1" rel="1"> <span class="stepNumber"></span> <span class="stepDesc">选择找回方式<br>
						<small>Choose Type</small>
				</span>
			</a></li>
			
		</ul>

		<input type="hidden" value="" name="gotourl" id="gotourl">
		<div class="stepContainer" style="height: 327px;">

			<div style="padding-top: 20px; padding-left: 20px; display: block;" id="step-1" class="content">
				<br>
				<br> 
	      <!--      <div id="forgotsms_box" style="display: block;" ><a tabindex="5" href="losepwd.do" class="forgotpass">短信找回/by sms</a></div>-->
	            <div id="forgotmail_box" style="display: block;" ><a tabindex="5" href="resetpwdbymail.do" class="forgotpass">邮件找回/by mail</a></div>
			</div>
		</div>
		<!--<div class="actionBar">
			<div class="loader">Loading</div>
			<a href="javascript:void(0)" class="buttonFinish" onclick="return true;">下一步/Next</a>
		</div> -->
	</div>	
	<!-- End SmartWizard Content -->
</div>
<!-- End of #content -->
<!-- <object id="badgeTool" style="display:none" classid="clsid:395E6CF3-3084-487D-9606-EDAA8B2C4E3C"></object> -->
			<script type="text/javascript">
		       
		        function nextStepSubmit() {
		        	
		        }
	      </script>


