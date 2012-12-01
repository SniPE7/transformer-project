<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>syslog</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath() %>/js/ajax.js" type="text/javascript" language="JavaScript">
</script>
<script src="/nccWeb/js/util.js"
	type="text/javascript"></script>
</head>
<script type="text/javascript">
function addsyslog(){
form1.action = "<%= request.getContextPath()%>/secure/icmp/newicmp.wss";
if(checkNull()&& isDigit()){
		this.form1.submit();
	}
}
function updatesyslog(){
form1.action = "<%= request.getContextPath()%>/secure/icmp/updateicmp.wss";
form1.submit();
}

function checkNull(){
	//alert("HERE");
   // alert(document.getElementById("subCategory").value);
	if(document.getElementById("ipaddress").value==""){
		alert("IPADDRESS不能为空!");
		return false;
	}
	if(Trim(document.getElementById("btime").value)=="")
	{	alert("BTIME不能为空!");
		return false;
	}
	if(Trim(document.getElementById("etime").value)=="")
	{	alert("ETIME不能为空!");
		return false;
	}
	
	if(Trim(document.getElementById("severity1").value)=="")
	{	alert("SEVERITY1 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("severity2").value)=="")
	{	alert("SEVERITY2 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("filterflag1").value)=="")
	{	alert("FILTERFLAG1 不能为空!");
		return false;
	}if(Trim(document.getElementById("filterflag2").value)=="")
	{	alert("FILTERFLAG2 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("threshold").value)=="")
	{	alert("THRESHOLD 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("comparetype").value)=="")
	{	alert("COMPARETYPE 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("varlist").value)=="")
	{	alert("VARLIST 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("summarycn").value)=="")
	{	alert("SUMMARYCN 不能为空!");
		return false;
	}
	
	
	return true;
}

function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }
 function isDigit(){
var filterflag1 = document.getElementById("filterflag1").value;
var filterflag2 = document.getElementById("filterflag2").value;
if(Trim(document.getElementById("filterflag1").value)!=""){
    var reg =/^[0-9]{1,20}$/;
    if(!filterflag1.match(reg)){
    	alert("请输入数字");
    	return false;
    }
    
}
if(Trim(document.getElementById("filterflag2").value)!=""){
    var reg =/^[0-9]{1,20}$/;
    if(!filterflag2.match(reg)){
    	alert("请输入数字");
    	return false;
    }
    
}
return true;
}
</script>





<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">ICMP Threshold信息
          </TD>
           <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">ICMP Threshold信息</TH>

  </TR>
   

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">添加ICMP Threshold</h1>

        <form action="" method="post" id="newdev1" name="form1">        
       
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
       	<c:choose >
					                <c:when test="${model.method == 'update'}" >					                
					                	<td>
    		<input type="button" name="button.update" value="确认" class="buttons" onClick="updatesyslog()"  />
    	</td>
					               </c:when>
					               <c:otherwise >
					               		<td>
    		<input type="button" name="button.new" value="确认" class="buttons" onClick="addsyslog()"  />
    	</td>
					               </c:otherwise>
					 </c:choose>
		
    	

        
    </tr></table>    
	 <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
	</c:if>
    
     <input type="hidden" name="formAction" value="0"/>  

        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row"  nowrap>

						<table border="1">
							<tbody>
								<tr>
								 
									<td width="30%">IPADDRESS</td>
										<c:choose >
					                <c:when test="${model.method == 'update'}" >					                
					                	<td>
    		<input type="text" name="ipaddress" size="20" value="${model.ipaddress }" readonly="true" />
    	</td>
					               </c:when>
					               <c:otherwise >
					               		<td>
    	<input type="text" name="ipaddress" size="20" value="${model.ipaddress }"/>
    	</td>
					               </c:otherwise>
					 </c:choose>
									
                                    <td>* 必填 *</td>
									
								</tr>
								
								<tr>
									<td>BTIME</td>
									<td><input type="text" id="btime" name="btime"  value="${model.btime}" size="20"></td>
									<td>* 必填 *</td>
								</tr>
								<tr>
									<td>ETIME</td>
									<td><input type="text" id="etime" name="etime" value="${model.etime }" size="20"></td>
									<td>* 必填 *</td>
								</tr>
								
								<tr>
									<td>SEVERITY1</td>
									<td><input id="severity1" type="text" name="severity1" size="20" value="${model.severity1 }" ></td>
									<td>* 必填 *</td>
								</tr>
								<tr>
									<td>SEVERITY2</td>
									<td><input  type="text" id="severity2" name="severity2" size="20" value="${model.severity2 }" ></td>
									<td>* 必填 *</td>
								</tr>
                                <tr>
									<td>FILTERFLAG1</td>
									<td><input type="text" id="filterflag1" name="filterflag1" size="20" value="${model.filterflag1 }"  onChange="isDigit()"></td>                          <td>* 必填 *</td>
								</tr>
								<tr>
									<td>FILTERFLAG2</td>
									<td><input  type="text" id="filterflag2" name="filterflag2" size="20" value="${model.filterflag2 }" onChange="isDigit()"></td>
									<td>* 必填 *</td>
								</tr>
								<tr>
									<td>THRESHOLD</td>
									<td><input id="threshold" type="text" name="threshold" size="20" value="${model.threshold}" ></td>
									<td>* 必填 *</td>
									
								</tr>
								<tr>
									<td>COMPARETYPE</td>
									<td><input id="comparetype" type="text" name="comparetype" size="20" value="${model.comparetype}" ></td>
									<td>* 必填 *</td>
								</tr>
								<tr>
									<td>VARLIST</td>
									<td><input type="text" name="varlist"  id="varlist" value="${model.varlist }" size="20" ></td>
									<td>* 必填 *</td>
								
								</tr>
								
								<tr>
									<td>SUMMARYCN</td>
									<td><input type="text" id="summarycn" name="summarycn" size="20" value="${model.summarycn}" ></td>
									<td>* 必填 *</td>
								</tr>
								
							</tbody>
						</table>



						</td>
        </tr>
        </table>

				


			





</form>

<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
           
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>



</TD></TR></TABLE>


</TD></TR></TABLE>
</body>
</html>