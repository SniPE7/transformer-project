keytool -genkey -dname "CN=ZDL,OU=CDL,O=IBM,C=CN" -alias my -keyalg RSA -keysize 512 -keystore mystore -storetype jks -keypass passw0rd -storepass passw0rd -validity 4000
keytool -selfcert -alias my -keystore mystore -keyalg RSA -storetype jks -storepass passw0rd
keytool -export -alias my -keystore mystore.jks -storetype jks -file exported-der.crt