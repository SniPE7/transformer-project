
				var xmlHttp = false;
				var dofunc;
                try {
                     xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                   } catch (e) {
                 try {
                     xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                      } catch (e2) {
                          xmlHttp = false;
                                    }
                       }
                if (!xmlHttp && typeof XMLHttpRequest != 'undefined') xmlHttp = new XMLHttpRequest();
                
               
                
                function callServer(url,dofunction){
                  
                   dofunc=dofunction;
                   xmlHttp.open("GET", url, true);
                   xmlHttp.onreadystatechange = updatePage;
                   xmlHttp.send(null);
                                       }
                                       
             function updatePage(){
                   if (xmlHttp.readyState==4){
                     var response = xmlHttp.responseText;
                     var tt=trim(response);
                     eval(dofunc+"('"+tt+"')");
                      }
              }
              function  trim(str)
{
    return str.replace(/(^\s*)|(\s*$)/g, "");  
}
              