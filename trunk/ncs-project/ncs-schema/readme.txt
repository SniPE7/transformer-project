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

部署前的准备
================================================================================
 1. 开通分行到数据中心数据库的访问端口
 2. 获得分行和数据中心数据库的system用户口令
 3. 获得分行和数据中心WAS管理员的用户名和口令
 4. 备份分行和数据中心数据库的数据
 5. 备份分行和数据中心原有的ncsWeb.ear软件包


生产环境（DataCenter）
================================================================================
 1. 以ncs用户的身份运行如下sql, 为NCS v1.5扩展原有数据库:
		upgrade/DC/2_upgrade_ncs_new_seqs.sql
		upgrade/DC/3_upgrade_ncs_new_tables.sql
		upgrade/DC/4_upgrade_ncs_alter_tables.sql
		upgrade/DC/5_create_views.sql
		upgrade/DC/6_setup_users.sql

		sql/server_nodes.sql  各个分行节点数据

 2. 为分行连接到数据中心的DBLink, 创建连接用户, 
    以system身份运行如下sql
      upgrade/DC/create_dlink_user.sql

 3. 部署war包
 
 如果DC节点也需要使用策略生效等功能, 则需要运行如下脚本：
 1. 以ncs用户的身份, 运行如下sql：
    upgrade/Branch/upgrade_db.sql
 2. 以ncs用户的身份, 运行如下sql：
    upgrade/Branch/create_view.sql
    upgrade/upgrade_to_v155_for_icmp_threshold.sql

 3. 修改配置ncc-configuration.properties，定义本服务器的所属分行
    ncs.node.code设置为与之对应的分行标识
    上述文件路径在部署包的WEB-INF/classes目录下
 


生产环境（分行）
================================================================================
 1. 以ncs用户的身份, 运行如下sql：
    upgrade/Branch/upgrade.sql

 2. 创建指向数据中心的DBLink, 以system身份运行如下sql:
    upgrade/Branch/create_link.sql  as system user (Need to change ip address.)
    运行前请修改此文件中的ip地址等参数，确保其指向数据中心的DB
 
 3. 以ncs用户的身份, 运行如下sql：
    upgrade/Branch/create_view.sql
    upgrade/upgrade_to_v155_for_icmp_threshold.sql
    
 4. 部署war包
 5. 修改配置ncc-configuration.properties，定义本服务器的所属分行
    ncs.node.code设置为与之对应的分行标识
    上述文件路径在部署包的WEB-INF/classes目录下
    