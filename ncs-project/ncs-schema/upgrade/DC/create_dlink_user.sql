-- Execsute as system user
CREATE USER ncslkuser IDENTIFIED BY ncslkuser
DEFAULT TABLESPACE NCS_DB
TEMPORARY TABLESPACE NCS_DB_TEMP
PROFILE DEFAULT ACCOUNT UNLOCK;

grant connect TO ncslkuser;
grant resource TO ncslkuser;
grant select any table to ncslkuser;
grant update on ncs.T_USER to ncslkuser;
grant insert, update on ncs.T_TAKE_EFFECT_HISTORY to ncslkuser;
