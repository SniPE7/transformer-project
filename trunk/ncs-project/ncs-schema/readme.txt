��������
================================================================================
 ׼����������,���Դ���һ������ǰ�Ļ������ݿ�, �����һ����������, ������
 �����Ͳ��Ի��������к�ִ�б����ֵĲ�����

 * ׼��2���������Data Center��Branch��
 * �����Ͼ���װOracle 10g��WebSphere App Server 7
 * �������������ر�˵��������Ҫ�����������ִ��

 * ��ʼ�����ݿ�
  	1. Create ncs user
  	   Execute "create_user_ncs.sql" as system user
		2. Create ncs tables
		   Execute "ncs.sql" as ncs user
		3. Upgrate tables:
		   Execute "upgrade/1_upgrade_ncs.sql" as ncs user
		4. Import init and sample data
		   Execute "data/ncsstor.sql" as ncs user

����ǰ��׼��
================================================================================
 1. ��ͨ���е������������ݿ�ķ��ʶ˿�
 2. ��÷��к������������ݿ��system�û�����
 3. ��÷��к���������WAS����Ա���û����Ϳ���
 4. ���ݷ��к������������ݿ������
 5. ���ݷ��к���������ԭ�е�ncsWeb.ear�����


����������DataCenter��
================================================================================
 1. ��ncs�û��������������sql, ΪNCS v1.5��չԭ�����ݿ�:
		upgrade/DC/2_upgrade_ncs_new_seqs.sql
		upgrade/DC/3_upgrade_ncs_new_tables.sql
		upgrade/DC/4_upgrade_ncs_alter_tables.sql
		upgrade/DC/5_create_views.sql
		upgrade/DC/6_setup_users.sql

		sql/server_nodes.sql  �������нڵ�����

 2. Ϊ�������ӵ��������ĵ�DBLink, ���������û�, 
    ��system�����������sql
      upgrade/DC/create_dlink_user.sql

 3. ����war��
 
 ���DC�ڵ�Ҳ��Ҫʹ�ò�����Ч�ȹ���, ����Ҫ�������½ű���
 1. ��ncs�û������, ��������sql��
    upgrade/Branch/upgrade_db.sql
 2. ��ncs�û������, ��������sql��
    upgrade/Branch/create_view.sql
 3. �޸�����ncc-configuration.properties�����屾����������������
    ncs.node.code����Ϊ��֮��Ӧ�ķ��б�ʶ
    �����ļ�·���ڲ������WEB-INF/classesĿ¼��
 


�������������У�
================================================================================
 1. ��ncs�û������, ��������sql��
    upgrade/Branch/upgrade.sql

 2. ����ָ���������ĵ�DBLink, ��system�����������sql:
    upgrade/Branch/create_link.sql  as system user (Need to change ip address.)
    ����ǰ���޸Ĵ��ļ��е�ip��ַ�Ȳ�����ȷ����ָ���������ĵ�DB
 
 3. ��ncs�û������, ��������sql��
    upgrade/Branch/create_view.sql
    
 4. ����war��
 5. �޸�����ncc-configuration.properties�����屾����������������
    ncs.node.code����Ϊ��֮��Ӧ�ķ��б�ʶ
    �����ļ�·���ڲ������WEB-INF/classesĿ¼��
    