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

<%-- I18N Formatting with EL --%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>

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
		<div class="text-description">To self register for Tivoli Identity Manager, please complete
								the following information and click Submit.</td>
		</div>
		<c:if test="${!empty missingValue}" >
			<br/>
			<table align="center" border="0" cellpadding="0" cellspacing="1" class="table-border-message" dir="LTR">
	        	<tr align="left" valign="TOP">
	            	<td align="center" class="message-background" valign="top" width="50">
	              	 	<img src="images/message_error.gif">
		            </td>	
		            <td align="left" valign="middle" width="*">
	    	           <p> 
	        	          <span class="text-normal"><c:out value="${missingValue}"/></span> &nbsp;
	            	   </p>
	            	</td>	
	         </tr>
	      </table>
		</c:if>
		<form accept-charset="UTF-8" method="post" action="ValidateData">
			<input name="submitted" type="hidden" value="true">
			<div class="section">
				<div class="subheading-text">Personal Information</div>
				<div class="field">			
					<label class="text-normal"><img src="images/status_required.gif">Last Name</label>
					<input class="entry-field-required" align="left" id="sn" name="sn" size="50" type="text" value="<c:out value="${param.sn}" />">
				</div>
				<div class="field">
					<label class="text-normal"><img src="images/status_required.gif">Full Name</label>
					<input class="entry-field-required" name="cn" size="50" type="text" value="<c:out value="${param.cn}" />">
				</div>
				<div class="field">
					<label class="text-normal">First Name</label>
					<input class="entry-field" name="givenname" size="50" type="text" value="<c:out value="${param.givenname}" />">
				</div>
				<div class="field">
					<label class="text-normal">Initials</label>
					<input class="entry-field" name="initials" size="50" type="text" value="<c:out value="${param.initials}" />">
				</div>
				<div class="field">
					<label class="text-normal">Home Address</label>
					<input class="entry-field" name="homepostaladdress" size="50" type="text" value="<c:out value="${param.homepostaladdress}" />">
				</div>
				<div class="field">
					<label class="text-normal">Shared Secret</label>
					<input class="entry-field" name="ersharedsecret" size="50" type="text" value="<c:out value="${param.ersharedsecret}" />">
				</div>
			</div>	
			<div class="section">
				<div class="subheading-text">Corporate Information</div>
				<div class="field">
					<label class="text-normal"><img src="images/status_required.gif">Location</label>
					<input class="entry-field-required" name="l" size="50" type="text" value="<c:out value="${param.l}" />">
				</div>
				<div class="field">			
					<label class="text-normal">Room Number</label>				
					<input class="entry-field" name="roomnumber" size="50" type="text" value="<c:out value="${param.roomnumber}" />">
				</div>
				<div class="field">
					<label class="text-normal">Employee Number</label>
					<input class="entry-field" name="employeenumber" size="50" type="text" value="<c:out value="${param.employeenumber}" />">
				</div>
				<div class="field">
					<label class="text-normal">Title</label>
					<input class="entry-field" name="title" size="50" type="text" value="<c:out value="${param.title}" />">
				</div>
				<div class="field">
					<label class="text-normal">Postal Address</label>
					<input class="entry-field" name="postaladdress" size="50" type="text" value="<c:out value="${param.postaladdress}" />">
				</div>
			</div>
			<div class="section">
				<div class="subheading-text">Communication Information</div>
				<div class="field">
						<label class="text-normal">Email Address</label>				
						<input class="entry-field" name="mail" size="50" type="text" value="<c:out value="${param.mail}" />">
				</div>
				<div class="field">
					<label class="text-normal">Telephone Number</label>			
					<input class="entry-field" name="telephonenumber" size="50" type="text" value="<c:out value="${param.telephonenumber}" />">
				</div>
				<div class="field">
					<label class="text-normal">Mobile Phone Number</label>
					<input class="entry-field" name="mobile" size="50" type="text" value="<c:out value="${param.mobile}" />">
				</div>
				<div class="field">
					<label class="text-normal">Pager</label>				
					<input class="entry-field" name="pager" size="50" type="text" value="<c:out value="${param.pager}" />">
				</div>
				<div class="field">
					<label class="text-normal">Home Phone</label>				
					<input class="entry-field" name="homephone" size="50" type="text" value="<c:out value="${param.homephone}" />">
				</div>
			</div>
			<br/>
			<input class="button" type="submit" value="Submit" onMouseOver="this.className = 'buttonover';" onMouseOut="this.className = 'button'"/>
		</form>
	</div>
</body>
</html>