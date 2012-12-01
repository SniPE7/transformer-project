<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<title>IP DECODE</title>
<script src="<%=request.getContextPath() %>/js/ajax.js" type="text/javascript" language="JavaScript">
</script>
<script type="text/javascript" language="JavaScript">

function databaseback(name){
	 if(name=='1'){
		if (window.confirm("确实要备份数据库吗？")){
                    Dframe.location.href="<%=request.getContextPath()%>/secure/maintain/dbmaintain.wss?formAction=backup";
		 }
		}
   else if(name=='2'){
         if(DatabaseForm.databasename.value=="")
			 { 
				alert('数据库文件不能为空！');
				return false;}
		else{
			if (window.confirm("确实要恢复数据库吗？")){
                       document.DatabaseForm.action="<%=request.getContextPath()%>/secure/maintain/dbmaintain.wss?formAction=restore";
                       document.DatabaseForm.method="post";
                       document.DatabaseForm.target="Dframe";
                       document.DatabaseForm.restoredatabase.disabled=true;
                       info.innerHTML='正在进行数据库恢复，请稍等．．．．．．';
                       document.DatabaseForm.submit();
                      }
		}
   }
}
function Reload(){
    DatabaseForm.backupdatabase.disabled=false;
    DatabaseForm.restoredatabase.disabled=false;
    info.innerHTML='';
}
</script>

</head>
<body>

<br/>
<iframe src="" name="Dframe" width="0" height="0"></iframe>

<input type="hidden" name="formAction" value="" >
<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">
 <TR>
      <TH class="wpsPortletTitle" width="100%">IP DECODE</TH>

      
  </TR>
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        

        <table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" class="button-section">
          
          <tr valign="top">
            <td class="table-button-section"  nowrap>
            <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
              <tr>
                <td>               </td>
            
              </tr>
            </table>
              </td>
          </tr>
        </table>
<%
String strtmp="0",decode2ip="";
long dispip2long =-1l;
long min=-1l;
long max=-1l;
String ipparam ="";
String maskparam ="";
try{
	dispip2long = com.ibm.ncs.util.IPAddr.ip2Long(request.getParameter("ip"));
	
	strtmp = request.getParameter("long");
	if (strtmp!= null){
	long tmp = Long.parseLong(strtmp);
	decode2ip = com.ibm.ncs.util.IPAddr.decode(tmp);
	ipparam = request.getParameter("ip");
	maskparam = request.getParameter("mask");
	min = com.ibm.ncs.util.IPAddr.getMinIp(ipparam, maskparam);
	max = com.ibm.ncs.util.IPAddr.getMaxIp(ipparam,maskparam);
}
}catch (Exception e){ e.printStackTrace();}
%>


<form id="form1"  method="post" action="#">
<TABLE border=0  cellpadding="0" cellspacing="5" align=center width="100%">
<TR>
	<TD nowrap>IP Address</TD>
	<TD><input type=submit name="backupdatabase" value="IP > long" ></TD>
</TR>
<tr><td></td><td><%=dispip2long %></td></tr>
<TR>
	<TD>Long </TD>
	<TD> <input type="submit" class="button" value="decode > IP"  ></TD>
</TR>
<tr><td><input type="text" name="long" value="0" ></td><td><%=decode2ip %></td></tr>
<tr><td></td><td></td></tr>
<tr><td>ip<input type="text" name="ip" value="<%=ipparam %>" ></td><td>mask<input type="text" name="mask" value="<%=maskparam %>" ></td><td></td></tr>
<tr><td>min=<%=min %></td><td>max=<%=max %></td><td></td></tr>
</TABLE>
</form>
 
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
  
  <tr valign="top">
        <td class="table-row" nowrap> 
    
<table  align=center><tr><td><font color=0000ff>
<div id=info>
${model.message }
</div></font>
</td></tr></table>


        </td>
        </tr>
        </table>        
        
<!-- Data Table -->


<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			
            <TD CLASS="table-totals" VALIGN="baseline">               
           
             &nbsp;&nbsp;&nbsp;               
        
		</TD>
	</TR>

</TABLE>


 
</TD></TR></TABLE>








</body>
</html>
