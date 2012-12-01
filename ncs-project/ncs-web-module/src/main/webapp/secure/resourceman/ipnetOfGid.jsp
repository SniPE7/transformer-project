<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->
${model.refresh }
<script type="text/javascript" language="javascript">


function addipnet(){
	
	ipnet.prepareAdd.value= 'add';
	
	//ipnet.gname.value ="";

	for (i=0;i<ipnet.iplist.options.length; i++){
	ipnet.iplist.options.remove[i];
	}
	// document.getElementsByName("button_new").disabled = true;
	
	this.ipnet.submit();
	
	
}
function saveipnet(){
	ipnet.formAction.value= 'save';
	ipnet.button_update.disabled = true;
	ipnet.button_update.value += '...';
	for (i=0;i<ipnet.iplist.options.length; i++){
	ipnet.iplist.options[i].selected="true";
	}
	// document.getElementsByName("button_new").disabled = false;
	this.ipnet.submit();
	
}
function deleteipnet(){
    var v = confirm('确定删除信息?');
	if( ! v ){ return false;	}
	ipnet.formAction.value= 'delete';
	ipnet.button_delete.disabled = true;
	ipnet.button_delete.value += '...';
	for (i=0;i<ipnet.iplist.options.length; i++){
	ipnet.iplist.options[i].selected="true";
	}
	this.ipnet.submit();
}

function reloadNavi(){


window.parent.frames[2].location.reload();

}

function del(fbox) {
		for(var i=0; i<fbox.options.length; i++) {
			if((fbox.options[i].selected)&&(fbox.options[i].value!="")) {
				fbox.options[i].value = "";
				fbox.options[i].text = "";
			}
		}
		BumpUp(fbox);
	}
		
	function BumpUp(box) {
		for(var i=0; i<box.options.length; i++) {
			if(box.options[i].value == "") {
				for(var j=i; j<box.options.length-1; j++) {
					box.options[j].value = box.options[j+1].value;
					box.options[j].text = box.options[j+1].text;
				}
				var ln = i;
				break;
			}
		}
		if(ln < box.options.length) {
			box.options.length -= 1;
			BumpUp(box);
		}
	}
	function add(fbox) {

		
		var j;
		if((ipnet.ip.value!="")&&(ipnet.mask.value!="")) {
		     var ip = ipnet.ip.value;
		     var mask = ipnet.mask.value;
		     if (!(/^(\d{1,3})(\.\d{1,3}){3}$/.test(ip))){
		     alert('IP地址格式非法。');
             return false;
             }
             var reSpaceCheck = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/; 
             var validip = ip.match(reSpaceCheck);
             if (validip == null) {
             alert('请输入正确的IP地址。');
             } 
             if(!(/^(\d{1,3})(\.\d{1,3}){3}$/.test(mask))){
             alert('子网掩码格式非法。');
             return false;
             }
            var IPArray = mask.split("."); 
            var ip1 = parseInt(IPArray[0]); 
            var ip2 = parseInt(IPArray[1]); 
            var ip3 = parseInt(IPArray[2]); 
            var ip4 = parseInt(IPArray[3]); 
            if ( ip1<0 || ip1>255 /* 每个域值范围0-255 */ 
             || ip2<0 || ip2>255 
             || ip3<0 || ip3>255 
             || ip4<0 || ip4>255 ) 
           { 
              alert('请输入正确的子网掩码。');
              return false; 
           } 
     
             
             
          
			j=0;
			for(var i=0; i<fbox.options.length; i++) {
				if((fbox.options[i].value != "") 
						&& (fbox.options[i].value == 
						ipnet.ip.value+";"+ipnet.mask.value)) {
					break;
				}
				j++;
			}
			if(j==fbox.options.length) {
				var no = new Option();
				no.value = ipnet.ip.value + "||" + ipnet.mask.value;
				no.text  = ipnet.ip.value + " || " + ipnet.mask.value;
				fbox.options[fbox.options.length] = no;
				ipnet.ip.value =""; 
				ipnet.mask.value="";
			} else {
				alert("IP已存在！");
			}
			BumpUp(fbox);
		} else {
			alert("IP/Mask为空！")
		}
	}
	

// -->
</script>



<body <c:if test="${model.refresh !=null}">onLoad="reloadNavi()"</c:if> class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">资源管理 </TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">IP地址规划</TH>

  </TR>
 
  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">IP地址规划</h1>


<form action="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss" method="post" id="ipnet" name="ipnet">        
       
<!-- button section --><br>
				<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="submit" name="button_new" value="新建"  onClick="addipnet()" class="buttons" id="functions" />
    	</td>
		<td>
    		<input type="submit" name="button_update" value="保存"  onClick="saveipnet()"  class="buttons" id="functions" />
    	</td>
    	<td>
    		<input type="submit" name="button_delete" value="删除"  onClick="deleteipnet()"  class="buttons" id="functions" />
    	</td>
        
    </tr></table>    
 
   
    <input type="hidden" name="gid" value="${model.gid}"/>
    <input type="hidden" name="supid" value="${model.supid}"/>
    <input type="hidden" name="gidOriginal" value="${model.gidOriginal}"/>
    <input type="hidden" name="supidOriginal" value="${model.supidOriginal}"/>
    <input type="hidden" name="levels" value="${model.levels}"/>
     <input type="hidden" name="formAction" value="${model.formAction }"/>  
     <input type="hidden" name="prepareAdd" value="${model.prepareAdd }"/> 
      
<div class="validation-error" >${model.message }</div>
        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row" nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="3" cellpadding="5" border="0"><tr> 
		<td colspan="2">父节点网络名称：
		  <c:out value="${model.supName }"/>  <br/>
    		当前节点网络名称 
    	</td>
    	</tr><tr>
    	<td colspan="2">
 
    		&nbsp;<input type="text" name="gname" value="${model.gname}"  style="width: 245px"/>

    	</td>
    	</tr><tr>
    	<td>IP 列表</td>
    	</tr><tr>
    	<td >
  <select size="15" name="iplist" style="width: 245" multiple>
  	<c:forEach items="${model.listipbygid}" var="theIp">
  	<option value="${theIp.ip }||${theIp.mask }" >${theIp.ip }    ||    ${theIp.mask }
  	</option>
		</c:forEach>
  </select>

    	</td> 
    	<td>
    	<fieldset style="height: 120px">
    	<legend>添加 / 移除 IP + 掩码</legend>
    	<table>
    	<tr>
    	<td>IP</td>
    	
    	<td><input type="text" name="ip" value=""  /></td>
    	</tr>    	<tr>
    	<td>掩码</td>
    	
    	<td><input type="text" name="mask" value="" /></td>
    	</tr>    	
    	<tr>
    	<td colspan="2"><input type="button" name="input.addipmask" value="添加" onClick="add(iplist);"  class="buttons" id="buttonStyle1" />&nbsp;&nbsp;<input type="button" name="input.removeipmask" value="移除" onClick="del(iplist);" class="buttons" id="buttonStyle1" /></td>
    	</tr>    
    	</table>
    	</fieldset>
    	
    	</td>
    </tr>
    <tr><td colspan="2">  备注:</td></tr>
    <tr> 
		<td colspan="2">
         <textarea name="description" class="select" style="width: 245"><c:out value="${model.description }"/></textarea>
    	</td>
    	</tr>
    	
    </table>  


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

</form>
 
</TD></TR></TABLE>


</TD></TR></TABLE>

</body>
</html>