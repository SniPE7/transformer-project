rem deploy jar to nexus
cd /d %~dp0

call mvn install:install-file -DgeneratePom=true -DgroupId=com.tech -DartifactId=tech_util -Dversion=1.0.0.2 -Dpackaging=jar -Dfile="tech_util-1.0.0.2.jar"
call mvn install:install-file -DgeneratePom=true -DgroupId=com.tech -DartifactId=tech_standard_protocol -Dversion=1.0.0.2 -Dpackaging=jar -Durl=%NEXUS_DEPLOY_URL% -DrepositoryId=thirdparty -Dfile="tech_standard_protocol-1.0.0.2.jar"
call mvn install:install-file -DgeneratePom=true -DgroupId=com.tech -DartifactId=tech_spring_init -Dversion=1.0.0.0 -Dpackaging=jar -Durl=%NEXUS_DEPLOY_URL% -DrepositoryId=thirdparty -Dfile="tech_spring_init-1.0.0.0.jar"
call mvn install:install-file -DgeneratePom=true -DgroupId=com.tech -DartifactId=tech_queue_adapter -Dversion=1.0.0.2 -Dpackaging=jar -Durl=%NEXUS_DEPLOY_URL% -DrepositoryId=thirdparty -Dfile="tech_queue_adapter-1.0.0.2.jar"
call mvn install:install-file -DgeneratePom=true -DgroupId=com.tech -DartifactId=tech_coagent_codec -Dversion=1.0.0.1 -Dpackaging=jar -Durl=%NEXUS_DEPLOY_URL% -DrepositoryId=thirdparty -Dfile="tech_coagent_codec-1.0.0.1.jar"
call mvn install:install-file -DgeneratePom=true -DgroupId=com.tech -DartifactId=tech_coagent_client -Dversion=1.0.0.1 -Dpackaging=jar -Durl=%NEXUS_DEPLOY_URL% -DrepositoryId=thirdparty -Dfile="tech_coagent_client-1.0.0.1.jar"
 
pause  