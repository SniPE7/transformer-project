<!--
/********************************************************************
*  Licensed Materials - Property of IBM
*  
*  (c) Copyright IBM Corp.  2007, 2009 All Rights Reserved
*  
*  US Government Users Restricted Rights - Use, duplication or
*  disclosure restricted by GSA ADP Schedule Contract with
*  IBM Corp.
********************************************************************/
-->
<html>
<head>
	<%-- Core with EL --%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
	<title>Self Registration</title>
	<link href="css/imperative.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#EFEFEF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="document.getElementById('sn').focus()">
	<table width="100%" height="60" border="0" cellpadding="0" cellspacing="0">
	  <tr bgcolor="#000000"> 
	    <td width="57"><img src="images/img_clear.gif" width="20" height="20"></td>
	    <td width="57"><img src="images/logo_tivoli.gif" width="47" height="25" alt="Tivoli"></td>
	    <td width="100%" valign="middle" nowrap class="text-productname">&nbsp;&nbsp;Tivoli Identity Products Integration Sample </td>
	    <td width="103"><img src="images/mosaic_banner.jpg" width="186" height="56"></td>
	    <td width="103"><img src="images/logo_ibm.gif" width="96" height="56"></td>
	  </tr>
	</table>
	<table cellspacing="1" cellpadding="0" border="0" width="100%" class="taskbar">
		<tbody>
			<tr nowrap=""> 
    			<td nowrap="" height="26" width="95%"/>
		  	</tr>
		</tbody>
	</table>
	<div class="section">
	<div class="heading-text">Self Registration</div>
	<div class="heading-line"><img src="images/img_clear.gif" width="1" height="2"/></div>
	<div class="section">
		<div class="text-description"><b>Request status:</b> <c:out value="${statusValue}"/></div>
	</div>
</body>
</html>