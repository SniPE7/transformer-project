开发环境
================================================================================
 准备开发环境,可以创建一个升级前的基础数据库, 帮助搭建一个开发环境, 无需在
 生产和测试环境中运行和执行本部分的操作。

 * 准备2个虚拟机（Data Center和Branch）
 * 在其上均安装Oracle 10g和WebSphere App Server 7
 * 后续步骤如无特别说明，均需要在两个虚机上执行

 * 初始化数据库
  	1. Create ncs user
  	   Execute "create_user_ncs.sql" as system user
		2. Create ncs tables
		   Execute "ncs.sql" as ncs user
		3. Upgrate tables:
		   Execute "upgrade/1_upgrade_ncs.sql" as ncs user
		4. Import init and sample data
		   Execute "data/ncsstor.sql" as ncs user
		   
生产环境（DataCenter）
================================================================================
 1. 以ncs用户的身份运行如下sql, 创建NCS V1.5扩展原有数据库:
		upgrade/DC/2_upgrade_ncs_new_seqs.sql
		upgrade/DC/3_upgrade_ncs_new_tables.sql
		upgrade/DC/4_upgrade_ncs_alter_tables.sql
		upgrade/DC/5_create_views.sql
		upgrade/DC/6_setup_users.sql
		upgrade/DC/6_setup_users.sql

		sql/server_nodes.sql  各个分行节点数据

 2. 为分行连接到数据中心的DBLink, 创建连接用户, 
    以system身份运行如下sql
      upgrade/DC/create_dlink_user.sql

 3. 部署war包
 4. 修改applicationContext.xml


生产环境（分行）
================================================================================
 1. 创建指向数据中心的DBLink, 以system身份运行如下sql:
    upgrade/Branch/create_link.sql  as system user (Need to change ip address.)
    运行前请修改此文件中的ip地址等参数，确保其指向数据中心的DB
 
 2. 以ncs用户的身份, 运行如下sql：
    upgrade/Branch/create_view.sql
    
 3. 部署war包
 4. 修改applicationContext.xml
 5. 修改配置，定义本服务器的所属分行   