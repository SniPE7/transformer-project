dojo.provide("ibm_soap.widget.SoapService");

dojo.require("ibm_soap.rpc.SoapService");
dojo.require("ibm_soap.util.WsdlParser");
dojo.require("ibm_soap.widget.RpcService");

dojo.declare("ibm_soap.widget.SoapService",ibm_soap.widget.RpcService,
{
	//	summary: A widget for 'SoapService' service
	//	description: This widget represents 'SoapService'.
	
	_createService: function() {
		//summary: Creates an instance of ibm_soap.rpc.SoapService
		// description: Creates an instance of ibm_soap.rpc.SoapService
		var url = this.url;
		if(dojo.isString(url)){
			var extension = url.substring(url.lastIndexOf(".")+1);
			
			// If the url is a .wsdl file, instantiate the WsdlParser to parse
			// the wsdl and instantiate the service using the returned smd object
			if(extension == "wsdl"){
					var parser=new ibm_soap.util.WsdlParser();
	 				parser.parse(url);
	 				return new ibm_soap.rpc.SoapService(parser.smdObj); // SoapService
			}
		}
		var soapService = new ibm_soap.rpc.SoapService(this.url);
		return soapService; //SoapService
	}
});
