set JAVA_HOME=D:\Tools\Java\jdk1.6.0_18

set SAML_SVR_HOME=E:\Temp\cmcc-hlj-saml-server

set JAVA_OPTS=-Dsaml.server.home=%SAML_SVR_HOME%
set CATALINA_HOME=%SAML_SVR_HOME%
%SAML_SVR_HOME%/bin/catalina %*