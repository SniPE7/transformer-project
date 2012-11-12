Installation Guide
================================================================================
 1. Register EAI filter servlet into ITIM Self-Service
    Modify web.xml and following content before all exist filters
    Caution: 
     By default, need to modify web.xml in dir:
        $WAS_HOME/profiles/AppSrv01/config/cells/iamNode01Cell/applications/ITIM.ear/deployments/ITIM/itim_self_service.war/WEB-INF
        , not in your installedApps directory!
        
		<filter>
			<description>Play EAI responsibility for self-service. Detect login state from session, and set EAI flags.</description>
			<display-name>EAIFilter</display-name>
			<filter-name>EAIFilter</filter-name>
			<filter-class>com.ibm.lbs.EAIFilter</filter-class>
	    <init-param>
	      <description>Set EAI header name for userid exchange.</description>
	      <param-name>eai.userid.header.attr.name</param-name>
	      <param-value>am-eai-user-id</param-value>
	    </init-param>
	    <init-param>
	      <description>Set EAI header name for authen level exchange.</description>
	      <param-name>eai.authenlevel.header.attr.name</param-name>
	      <param-value>am-eai-auth-level</param-value>
	    </init-param>
	    <init-param>
	      <description>Set default authen level for the authen service</description>
	      <param-name>eai.authenlevel</param-name>
	      <param-value>1</param-value>
	    </init-param>
	    <init-param>
	      <description>Enable debug information which will output into System.out</description>
	      <param-name>debug</param-name>
	      <param-value>true</param-value>
	    </init-param>
		</filter>
		<filter-mapping>
			<filter-name>EAIFilter</filter-name>
			<url-pattern>/*</url-pattern>
		</filter-mapping>
  
  In production enviroment, set debug to false, and turn-off all debug information.
  Caution: The EAI filter must be first filter definition in web.xml.
  
  The zipped file "web.itim51.ss.sample.xml" is merged sample code with ITIM orginial web.xml.

2. Copy eai.jar into self-service web library directory.
   mkdir $WAS_HOME/profiles/AppSrv01/installedApps/iamNode01Cell/ITIM.ear/itim_self_service.war/WEB-INF/lib
   copy eai.jar into $WAS_HOME/profiles/AppSrv01/installedApps/iamNode01Cell/ITIM.ear/itim_self_service.war/WEB-INF/lib

3. Restart ITIM application from WAS console.

4. Set and enable WebSEAL EAI function.
   Tips: 
        eai trigger url:   /itim/self/*
        local-response-redirect-uri = /itim/self/Login/Logon.do
