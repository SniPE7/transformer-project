Data Center:
1. create_user_ncs.sql as system user
2. ncs.sql as ncc user
3. upgrade/1_upgrade_ncs.sql as ncc user
4. 2_upgrade_ncs_new_seqs.sql
5. 3_upgrade_ncs_new_tables.sql
6. 4_upgrade_ncs_alter_tables.sql
7. 5_create_views.sql
8. 6_setup_users.sql
9. 9_upgrade_policies2template.sql
10. DC/create_dlink_user.sql


�������ݿ�����:
����1��4Ϊ׼����������,���Դ���һ������ǰ�Ļ������ݿ�, �����һ����������, �����������Ͳ��Ի��������С�
1. create_user_ncs.sql as system user
2. ncs.sql as ncc user
3. upgrade/1_upgrade_ncs.sql as ncc user
4. Import data data/ncsstor.sql (Optional)

���²���Ϊ�����Ͳ��Ի����������ű���
5. Branch/create_link.sql  as system user (Need to change ip address.)