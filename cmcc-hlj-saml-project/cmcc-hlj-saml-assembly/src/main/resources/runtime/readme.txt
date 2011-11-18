CMCC SAML Server Readme File

Created date: 2010-07-10
Created by: Zhao DongLu

================================================================================================================
* 服务器安装、启动及停止
  1. 下载服务器安装包
     可以从Hudson构建服务器下载，下载URL为:
     http://192.168.245.129:9090/job/CMCC-SAML-SERVER/ws/cmcc-hlj-saml-project/cmcc-hlj-saml-assembly/target/cmcc-saml-server-1.0.0-SNAPSHOT-bin.zip
     其中IP地址为虚拟机的地址,请按实际地址修改
     
  2. 在目标安装路径解压缩安装包, 解压缩后将在目标安装路径下产生子目录cmcc-hlj-saml-server, 
     此子目录即为$SAML_SVR_HOME
     
  3. 安装JDK 1.5或更高的版本, JDK的目标安装目录即为$JAVA_HOME目录
  
  4. 修改服务器的启动和停止脚本 $SAML_SVR_HOME/bin/saml-svr.sh, 设置JAVA_HOME和SAML_SVR_HOME环境变量, 例如:
     export JAVA_HOME=/home/saml/jre
     export SAML_SVR_HOME=/home/saml/cmcc-hlj-saml-server
     
  5. 确保TCP端口8080和18080未被占用
  
  6. 启动服务器，执行如下命令之一:
     前台启动模式: ./saml-svr.sh run
     后台启动模式: ./saml-svr.sh start
     注：此命令在$SAML_SVR_HOME/bin目录下
     
  7. 通过日志文件或屏幕输出（仅限于前台启动模式）查看服务器启动状态
     启动日志文件 $SAML_SVR_HOME/logs/catalina.out 中找到如下两行内容, 表示服务器启动正常:
     2010-07-10 15:53:53,816 751 INFO  [SAMLSocketServer.java:89] : CMCC SAML server started, listening on 8080
     INFO: Starting Coyote HTTP/1.1 on http-18080
     如果启动异常, 也通过上述文件查看
     
  8. 服务启动后, 提供两个访问端口:
     TCP/8080     SAML Socket Server
     HTTP/18080   Web SAML Client Test Tool (可以通过http://hostname:18080访问)
     
  9. 服务终止可以使用如下命令:
     ./saml-svr.sh stop


* 服务器日志文件
  1. 日志文件统一存放在$SAML_SVR_HOME/logs目录下 包括两个日志文件:
     1). catalina.out
         缺省设置下,此文件包括服务器启动、停止和所有的业务日志
     2). sampl_server.log
         缺省设置下,此文件包括服务器所有业务日志.
  2. 可以通过修改日志配置文件调整日志规则, 配置文件的路径如下:
     $SAML_SVR_HOME/webapps/samlsvc/WEB-INF/classes/log4j.xml
     
     
* 服务器配置文件
  1. 服务器配置文件为 $SAML_SVR_HOME/conf/saml-server-config.properties
  2. 其中重点需要调整的参数如下:
     ldap.server.*           这一组参数用于配置需要连接的目录服务器
     message.saml.issuer     SAML Issuer URL, 用于填充服务器的SAML响应报文的相关项
     cmd.global.logout       服务器接收到全局注销时需要执行的命令
     remote.saml.server.*    用于Web SAML Client Test Tool连接的目标SAML服务器, 通常填写总部的SAML服务器参数
     
=================================================================================================================
      