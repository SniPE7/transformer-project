<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>




<html>
<head>
<title>IBM Tivoli NetCool信息管理系统 - 登陆界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	var djConfig =
	{
		//locale: "en-us",
		locale: "zh-cn",
		isDebug: false,
		parseOnLoad: false,
		modulePaths:
		{
			"ibm.tivoli": "/js"
		}
	};
</script>
<script type="text/javascript">
	function chkMain(){
		if (top.location.href != self.location.href) {
			top.location.href= self.location.href;
		}
	}
</script>

<link rel="stylesheet" type="text/css" href="./login.css">
</head>
<body onload="chkMain();">
<table class="main_tbl" cellpadding="0" cellspacing="5" align="center">

	<tr>
		<td align="left"></td>
		<td align="right"></td>
	</tr>
	<tr>
		<td class="dialog" colspan="2">

			<form method="POST" action="<%=request.getContextPath() %>/login.wss?mode=force">
				<table cellpadding="0" cellspacing="0">
				
							<input type="hidden" name="j_username" value="${model.username}"/>
					<tr>
						<td class="input_pad" colspan="2"><label id="info" for="info"> 当前另一个用户已使用同一用户标识登录。从下列选项中选择：</label>
							<br />
						</td>
					</tr>
					<tr>
						<td> 
                    		<input type="radio" name="ifContinue" value="force" checked="checked" id="forceradio">
                  		</td>
                  		<td>
                    		<label for="forceradio">注销另一个使用同一用户标识的用户。</label>
                  		</td>
					</tr>
					<tr>
						<td> 
                    		<input type="radio" name="ifContinue" value="continue" id="continueradio">
                  		</td>
                    	<td>
                    		<label for="continueradio">返回到登录页面并输入不同的用户标识。</label>
                  		</td>
					</tr>
					<tr align="center" > 
			            <td valign="top" colspan="2"> 
			              <input type="submit" name="submit" value="确定">
			            </td>
          			</tr>
				</table>
				</form>
	</td>
	</tr>

	<tr>
		<td colspan="2"><span id="copyright">&nbsp;</span></td>
	</tr>
</table>

</body>
</html>
