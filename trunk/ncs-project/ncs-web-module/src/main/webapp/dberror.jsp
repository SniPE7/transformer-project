<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<html>
<head>

<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<div style="direction:ltr">
<link href='<%=request.getContextPath() %>/include/Styles.css' rel="styleSheet" type="text/css">

<title>NetCool信息管理系统欢迎界面</title>

</head>
<body style="direction:ltr">
<div style="direction:ltr">
<TABLE WIDTH="100%">
	

</TABLE>


<table width="98%" cellpadding=0 cellspacing=0 border=0>

    <tr>
      <td valign="top">

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>

  <td valign="top">

<table  border="0"  width="100%"  height="100%"   cellpadding="0" cellspacing="8">
    <tr>
        <td  height="100%"   >
   	         
            <table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="wasPortlet">
                <tr >
                    <td colspan="3" class="wpsPortletTitle">

                    </td>
                </tr>
                       
              
                
                <tr>
                    <td width="1" height="1"><img alt="" title="" border="0" width="1" height="1" src='images/dot.gif'></td>
                    <td height="1"><img alt="" title="" border="0" width="1" height="1" src='images/dot.gif'></td>
                    <td width="1" height="1"><img alt="" title="" border="0" width="1" height="1" src='images/dot.gif'></td>
                </tr>

            </table>
        </td>
    </tr>
</table>

  </td>

  <td valign="top">
    
   
<table  border="0"  width="100%"  height="100%"   cellpadding="0" cellspacing="8">
    <tr>

        <td  height="100%"   >
   	         
            <table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="wasPortlet" >
                <tr >
                    <td colspan="3" class="wpsPortletTitle">
                        <table border="0" width="100%" cellpadding="0" cellspacing="0" >
                            <tr>

                        
                                
                                
									<td width="100%" nowrap valign="middle" class="wpsPortletTitleText">
									 	关于NetCool信息管理系统
									 </td>

                                <td >
                                    -</td>
                                
                                <td  >
                                    []              </td>

                                <td width="1" ><img alt="" title="" border="0" width="1" height="1" src='<%=request.getContextPath()%>/images/dot.gif'></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                       
                <tr>
                    <td width="100%" height="100%" colspan="3" class="wpsPortletBorder" valign="top">
                        <table width="100%" border="0"  cellpadding="5" height="100%" cellspacing="0">
                            <tr>

                                <td valign="top">
                                    <!-- call content render here -->
                                    









<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<META HTTP-EQUIV="Expires" CONTENT="0">
</head>
<body>



<STYLE>
TEXTAREA { width:95%; border-style: none; scrollbar-face-color:#CCCCCC; scrollbar-shadow-color:#FFFFFF; 
scrollbar-highlight-color:#FFFFFF; scrollbar-3dlight-color:#6B7A92; scrollbar-darkshadow-color:#6B7A92; 
scrollbar-track-color:#E2E2E2; scrollbar-arrow-color:#6B7A92  }
</STYLE>
<form>

<div align="center">
  <textarea dir="ltr" name="abouttext" rows='8' cols='40' class="desctext" style="color:red" readonly>

&nbsp;	<c:if test="${model.message != null &&  model.message != ''}">
									<fmt:message>${model.message }</fmt:message>
								</c:if>
  </textarea>
</div>
</form>
</body>
</html>

                                    <!-- deal with dynamic title here -->
                                    
                                </td>
                            </tr>
                        </table>
                    </td>

                </tr>
                
                <tr>
                    <td width="1" height="1"><img alt="" title="" border="0" width="1" height="1" src='<%=request.getContextPath()%>/images/dot.gif'></td>
                    <td height="1"><img alt="" title="" border="0" width="1" height="1" src='<%=request.getContextPath()%>/images/dot.gif'></td>
                    <td width="1" height="1"><img alt="" title="" border="0" width="1" height="1" src='<%=request.getContextPath()%>/images/dot.gif'></td>
                </tr>
            </table>
        </td>
    </tr>

</table>

  </td>

</tr>
</table>


      </td>
    </tr>

</table>

</div>
</body>
</html>


