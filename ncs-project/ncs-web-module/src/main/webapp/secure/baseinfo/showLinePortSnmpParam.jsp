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

<script language="JavaScript" type="text/javascript">

function filtery(patterns, list){
 /* backed up dropdown list */
 if (!list.bak){

   list.bak = new Array();
   for (n=0; n<list.length; n++){
     list.bak[list.bak.length] = new Array(list[n].value, list[n].text);
   }
 }
 
 patternArray = patterns.split(" ");

 match = new Array();
 nomatch = new Array();
 for (n=0;n<list.bak.length;n++){

   matchflag = 1;
   for (j=0;j<patternArray.length; j++){
     pattern = patternArray[j];
	 if(list.bak[n][1].toLowerCase().indexOf(pattern.toLowerCase())==-1){
	   matchflag = 0;
	   continue;	  
     }
   }
   if(matchflag==1){
      match[match.length] = new Array(list.bak[n][0], list.bak[n][1]);
   }else{ 
      nomatch[nomatch.length] = new Array(list.bak[n][0], list.bak[n][1]);
   }
 }

 for (n=0; n<match.length; n++){
   list[n].value = match[n][0];
   list[n].text = match[n][1];
 }
 for (n=0; n<nomatch.length; n++){
   list[n+match.length].value = nomatch[n][0];
   list[n+match.length].text = nomatch[n][1];
 }

 /* we make the 1st item selected  */
 list.selectedIndex=0;
}

</script>






<script language="javascript">
<!--
var com_ibm_isclite_currentModuleRef="";
var com_ibm_isclite_currentPageRef="";
var com_ibm_isclite_currentNodeType="";
var com_ibm_isclite_currentNodeTitle="";

function setkookie(key, value)
{
var cokie=getwholekookie();
if (cokie == null) 
 {// first time
  document.cookie = "TE3="+key+":"+value+" ; path=/";  
 } else {
  var start = cokie.indexOf(key + ":" );
  if ( start == -1 ) // not in list, so just append
    {
     document.cookie = "TE3="+cokie+" "+key+":"+value+" ; path=/";
    } else { // replace
     var end = cokie.indexOf(" ", start);
     if ( end == -1 ) end = cokie.length;
       document.cookie = "TE3="+cokie.substring(0, start-1)+" "+key+":"+value+" "+
                         cokie.substring(end+1, cokie.length)+" ; path=/";
    }
  
 }
}

function getwholekookie()
{
var len = 4;  // TE3=
var start = document.cookie.indexOf("TE3=");
if ( start == -1)
  { return null; }
var end = document.cookie.indexOf( ";", start );
if ( end == -1 ) end = document.cookie.length;
// this should return N1=E N2=C ....
return unescape( document.cookie.substring(start+len, end) );
}

function getkookie(key)
{
var cookiestr = getwholekookie();
if (cookiestr == null) return null;

var start = cookiestr.indexOf(key + ":" ); // start of the key
if ( start == -1 ) return null; // never found <key>
var value = start + key.length + 1; //begin of the value
var end = cookiestr.indexOf(" ", value); //end of the value
if ( end == -1 ) end = cookiestr.length; 
return cookiestr.substring(value,end); 
}

var dgeid=document.getElementById;
function expandCollapse(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

if (!dgeid) return;
if (document.getElementById(a).style.display=='none') {
 document.getElementById(a).style.display = 'block';
 document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_expanded.gif';
 document.getElementById(g).title = '-';
 document.getElementById(g).alt = '-';
 setkookie(a, "E");
 } else {
 document.getElementById(a).style.display = 'none';
 document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_collapsed.gif';
 document.getElementById(g).title = '+';
 document.getElementById(g).alt = '+';
 setkookie(a, "C");
 }
}

function Collapse(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

document.getElementById(a).style.display = 'none';
document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_collapsed.gif';
document.getElementById(g).title = '+';
document.getElementById(g).alt = '+';
setkookie(a, "C");
}

function Expand(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

document.getElementById(a).style.display = 'block';
document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_expanded.gif';
document.getElementById(g).title = '-';
document.getElementById(g).alt = '-';
setkookie(a, "E");
}

function initAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) 
 {

	Collapse(i);
 }
}

function CollapseAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) Collapse(i);
}

function ExpandAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) Expand(i);
}

function determineAction(e) {
    if (document.layers) {
        document.captureEvents(Event.KEYPRESS);
        document.captureEvents(Event.onClick);
    }
    
    document.onkeypress = function (evt) {
        var key = document.all ? event.keyCode : evt.which ? evt.which : evt.keyCode;
        
        if (key == 13) 
            document.navFilter.submit();
    };

    document.onclick =  function (evt) {
        document.navFilter.submit();
    };
}
window.onload =  function (){
	initAll();        
};

function addipnet(){
	ipnet.formAction.value= 'add';
	ipnet.reset();
	ipnet.gname.value="";
	ipnet.supid.value ="${model.grpnet.supid}";
	ipnet.level.value ="${model.grpnet.level}";
	for (i=0;i<ipnet.iplist.options.length; i++){
	ipnet.iplist.options.remove[i];
	}
	ipnet.action = "<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss";
	this.ipnet.submit();
}
function saveipnet(){
	ipnet.formAction.value= 'save';
	for (i=0;i<ipnet.iplist.options.length; i++){
	ipnet.iplist.options[i].selected = true;
	}
	ipnet.action = "<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss";
	this.ipnet.submit();
}
function deleteipnet(){
	ipnet.formAction.value= 'delete';
	var v = confirm('确定删除信息?');
	if( ! v ){ return false;	}
	if(v){
	ipnet.action = "<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss";
	this.ipnet.submit();
	}
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
				alert("IP already exist!");
			}
			BumpUp(fbox);
		} else {
			alert("IP/Mask is empty！")
		}
	}
	

// -->
</script>
<% int nodei=0; %>


<body onLoad="initAll();" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">线路 /端口 SNMP性能参数</TD>
           <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">线路 /端口 SNMP性能参数</TH>

  </TR>
  

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">线路 /端口 SNMP性能参数</h1>


<form action="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss" method="post" id="ipnet" name="ipnet">        
       
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
	
		<td>
    		<input type="submit" name="button.update" value="保存" class="buttons" onClick="saveipnet()"  id="functions"/>
    	</td>
    	<td>
    		<input type="submit" name="button.delete" value="删除" class="buttons" onClick="deleteipnet()"  id="functions"/>
    	</td>
        
    </tr></table>    
 
    <input type="hidden" name="gid" value="${model.gid}"/>
    <input type="hidden" name="supid" value="${model.grpnet.supid}"/>
    <input type="hidden" name="level" value="${model.grpnet.level}"/>
     <input type="hidden" name="formAction" value="save"/>  

        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row" nowrap> 
    
<form name="formAdd" method="get" action="_new.jsp" onSubmit="return confirm('确定增加记录?')">
		<input name="_name" value="snmpPIRVATE" type="hidden">
		<div class="sectionHeading" value="详细内容">SNMP性能参数</div>
		<div class="sectionContent">
		<table class="loginTable" border="0" bordercolor="#999999" cellpadding="0" cellspacing="1" width="100%">


			

				<tbody><tr class="data-row">
					<td class="tdlabel">#:</td>

					<td class="tdcontent">
						
						
						
						
						
					</td>
				</tr>

			

				<tr class="data-row">
					<td class="tdlabel">#:</td>
					<td class="tdcontent">
						
					自动计算
					  <input name="eveid" value="" id="formStyle1" type="hidden">
<input name="general" value="-1" id="formStyle1" type="hidden">
					
						
						
						
						
					</td>
				</tr>

			

				<tr class="data-row">
					<td class="tdlabel">模式类别</td>
					<td class="tdcontent">
						
					自动计算
					  <input name="modid" value="" id="formStyle1" type="hidden">
					
						
						
						
						
					</td>

				</tr>

			

				<tr class="data-row">
					<td class="tdlabel">类型</td>
					<td class="tdcontent">




									<fieldset>

									<input name="ecode" value="1"  type="radio">
									设备
								
									
								
								<input name="ecode" value="6" checked="checked" type="radio">
								线路/端口
								<input name="ecode" value="12"  type="radio">
								私有Mib Index
									</fieldset>
								
						
						
					</td>
				</tr>

			

				<tr class="data-row">
					<td class="tdlabel">名称</td>
					<td class="tdcontent">
						
						
							<input name="major" id="formStyle1" value="">
						
						
						
						
					</td>
				</tr>

			

				<tr class="data-row">
					<td class="tdlabel">描述</td>

					<td class="tdcontent">
						
						
						
						
						
							<textarea name="description" rows="5" cols="50" id="formStyle2"></textarea>
						
					</td>
				</tr>

			
			<tr>
				<td colspan="2" class="tdbutton" >
				</td>
			</tr>
		</tbody></table>

		</div>
		</form> 


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


</TD></TR>
</body>
</html>