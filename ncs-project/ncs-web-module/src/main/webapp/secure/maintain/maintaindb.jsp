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
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>数据库备份与恢复</title>
<script src="<%=request.getContextPath() %>/js/ajax.js" type="text/javascript" language="JavaScript">
</script>
<script type="text/javascript" language="JavaScript">

function GetXmlHttpObject(){
	var xhreq=null;
	try{
		// Firefox, Opera 8.0+, Safari
		xhreq=new XMLHttpRequest();
	}
	catch (e){
		//Internet Explorer
		try{
			xhreq=new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e){
			xhreq=new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xhreq;
}

function stateChanged(){ 
	if (xhreq.readyState==4 && xhreq.status==200){ 
		document.getElementById("info").innerHTML=xhreq.responseText;
	}
}
function callback(){ 
		xhreq=GetXmlHttpObject();
		if (xhreq==null){			
			return;
		}
		var url ="<%=request.getContextPath()%>/secure/maintain/statedbrestore.wss";
		xhreq.open("GET",url,true);
		xhreq.onreadystatechange=stateChanged ;
		xhreq.send(null);	
		
}



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
                       setInterval("callback()", "3000");
                       document.DatabaseForm.submit();
               //        RenewMessage();
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
<body class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"><iframe src="" name="Dframe" width="0" height="0"></iframe>

<input type="hidden" name="formAction" value="" >
<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">数据库维护
          </TD>
         <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
  <TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

 <TR>
      <TH class="wpsPortletTitle" width="100%">数据库维护</TH>

      
  </TR>
  
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        <h1 id="title-bread-crumb">数据库备份与恢复</h1>
        <br>

        <table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" class="button-section">
          
          <tr valign="top">
            <td class="table-button-section"  nowrap>
            <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
              <tr>
                <td>       &nbsp;        </td>
            
              </tr>
            </table>
              </td>
          </tr>
        </table>

<form name="DatabaseForm" ENCTYPE="multipart/form-data">
<TABLE border=0  cellpadding="0" cellspacing="5" align=center width="100%">
<TR >
	<TD nowrap class="table-row">数据库备份：</TD>
	<TD><input type=button name="backupdatabase" value="备份" onclick="databaseback('1')" class="buttons" id="buttonStyle1"></TD>
</TR>

<TR >
	<TD class="table-row">数据库恢复：</TD>
	<TD><input type="file" name="databasename"  size="15" maxlength="15"  value="" class="buttons" id="buttonStyle1"> <input type="button" class="buttons" value="恢复" onClick="databaseback('2')" name="restoredatabase" id="buttonStyle1"></TD>
</TR>
</TABLE>
</form>
 
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
  
  <tr valign="top">
        <td class="table-row" nowrap> 
    
<table  align=center><tr><td><font color=0000ff>
<div id="info" class="table-row">
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
</TD></TR></TABLE>







</body>
</html>
