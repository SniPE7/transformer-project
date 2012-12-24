-- conn system/passw0rd@ncc;
-- Create Tablespace
DROP TABLESPACE NCS_DB INCLUDING CONTENTS AND DATAFILES;
CREATE TABLESPACE NCS_DB
DATAFILE '/oracledata/NETCOOL/NCS_DB' SIZE 512M
DEFAULT STORAGE (
                INITIAL 5M
                NEXT   10M
                MINEXTENTS 1
                MAXEXTENTS 9999
                PCTINCREASE 0)
                NOLOGGING
                ONLINE;
COMMIT;

-- Temporary tablespace.
DROP TABLESPACE NCS_DB_TEMP INCLUDING CONTENTS AND DATAFILES;
CREATE TEMPORARY TABLESPACE NCS_DB_TEMP
TEMPFILE '/oracledata/NETCOOL/NCS_DB_TEMP' SIZE 128M ;
COMMIT;

-- Create the role
DROP role NCS_DW; 
create role NCS_DW;
grant connect to NCS_DW;
grant alter session to NCS_DW;
grant create any index to NCS_DW;
grant create any procedure to NCS_DW;
grant create session to NCS_DW;
grant create table to NCS_DW;
grant create view to NCS_DW;

-- Create user
DROP USER ncs CASCADE;
CREATE USER ncs IDENTIFIED BY ncs 
DEFAULT TABLESPACE NCS_DB
TEMPORARY TABLESPACE NCS_DB_TEMP
PROFILE DEFAULT ACCOUNT UNLOCK;
GRANT CONNECT TO ncs;
GRANT RESOURCE TO ncs;
GRANT SELECT_CATALOG_ROLE TO ncs;
GRANT SELECT ANY TABLE TO ncs;
GRANT UNLIMITED TABLESPACE TO ncs;
GRANT CREATE ANY VIEW TO ncs;
GRANT CREATE PUBLIC DATABASE LINK TO ncs;
GRANT CREATE  DATABASE LINK TO ncs;
grant create synonym to ncs;
GRANT NCS_DW TO ncs;


COMMIT;
exit