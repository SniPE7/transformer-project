/*
*  this function get resouce name and account. and return a table for client.
*
*/


function getserveraccount(xmlurl){
	var htmlData="";
	$.ajax({
		url: xmlurl,
		type: 'GET',
		dataType: 'xml',//这里可以不写，但千万别写text或者html!!!
		timeout: 1000,
		async: false,
		error: function(xml){	alert('Error loading XML document'+xml);},
		success: function(xml){
			htmlData = '<table width="100%" border="0" cellpadding="1" cellspacing="1" id="serverpim">';
			htmlData+= '<tr bgcolor="#0099CC">';
			htmlData+= '<td width="30%">Account Infomation</td>';
			htmlData+= '<td width="16%">Start TIME</td>';
			htmlData+= '<td width="21%">End TIME</td>';
			htmlData+= '<td width="33%">Action Or Status</td>';
			htmlData+= '</tr>';
										
			$(xml).find("resource").each(function(){
				var serverlist = $(this);
				var serveraccout = serverlist.children("AccountID").text();
				var requestor = serverlist.children("Requestor").text();
				var approver = serverlist.children("Approver").text();
				var status = serverlist.children("status").text();

												
				htmlData+= '<tr bgcolor="#BB99CC">';
				htmlData+= '<td>'+serverlist.children("AccountID").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("StartTime").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("EndTime").text()+'</td>';
				if(status.toLowerCase()!="finish"){
					htmlData+= '<td>'+requestor+' Has Occupted.</td>';
					}else{
						htmlData+= '<td><button name="'+serveraccout+'" id="add">Add</button></td>';
				}
				htmlData+= '</tr>';
			});
			htmlData+= '</talbe>';

		}
	});
	return htmlData;
}
	
/*         Get HISTORY   */

function gethistory(xmlurl){
	var htmlData="";
	$.ajax({
		url:xmlurl,
		type: 'GET',
		dataType: 'xml',//这里可以不写，但千万别写text或者html!!!
		timeout: 1000,
		async: false,
		error: function(xml){	alert('Error loading XML document'+xml);},
		success: function(xml){
			htmlData = '<table width="100%" border="0" cellpadding="1" cellspacing="1" id="historyrecs">';
			htmlData+= '<tr bgcolor="#0099CC" class="taskheader itemnumber">';
			htmlData+= '<td width="8%" align="center" valign="middle">Proccess ID</td>';
			htmlData+= '<td width="13%" align="center" valign="middle">Account Infomation</td>';
			htmlData+= '<td width="12%" align="center" valign="middle">Start TIME</td>';
			htmlData+= '<td width="12%" align="center" valign="middle">End TIME</td>';
			htmlData+= '<td width="29%" align="center" valign="middle">Reason</td>';
			htmlData+= '<td width="6%" align="center" valign="middle">Requestor</td>';
			htmlData+= '<td width="6%" align="center" valign="middle">Approver</td>';
			htmlData+= '<td width="7%" align="center" valign="middle">Status</td>';
			htmlData+= '<td width="9%" align="center" valign="middle">Acction</td>';
			htmlData+= '</tr>';
			
			$(xml).find("serverlist").each(function(i){
				var serverlist = $(this);
				
				htmlData+= '<tr bgcolor="#EE99CC">';
				htmlData+= '<td>'+serverlist.children("ProccessID").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("AccountID").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("StartTime").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("EndTime").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("Reason").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("Requestor").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("Approver").text()+'</td>';
				htmlData+= '<td>'+serverlist.children("Acction").text()+'</td>';
				htmlData+= '<td>Acction</td>';
				htmlData+= '</tr>';
			});
			htmlData+= '</talbe>';
		}
	});
	return htmlData;
}