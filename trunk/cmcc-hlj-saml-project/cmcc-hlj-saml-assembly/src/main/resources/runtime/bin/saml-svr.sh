#!/bin/sh
#
# Please set JAVA_HOME and SAML_SVR_HOME
#
export JAVA_HOME=/opt/IBM/WebSphere/AppServer/java
export SAML_SVR_HOME=/home/saml/cmcc-hlj-saml-server

#
# Caution: DO NOT MODIFY THE FOLLOWING LINES!
#
export JAVA_OPTS="-Dsaml.server.home=$SAML_SVR_HOME -Djava.awt.headless=true"
export CATALINA_HOME=$SAML_SVR_HOME
$SAML_SVR_HOME/bin/catalina.sh $*