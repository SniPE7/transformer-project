#!/usr/bin/sh

SERVER=$1
USER=$2
PASS=$3
PATH=$4
FILE=$5
SRCF=$6

#must have trust host at remote machine, copy key-gen
# restart the three services

#ssh ncoadmin@NCBF3 '/opt/TIVOLI/ITM/li6263/is/bin/ism_startup.sh stop nco_m_snmp'
#ssh ncoadmin@NCBF3 '/opt/TIVOLI/ITM/li6263/is/bin/ism_startup.sh stop nco_m_icmp'

echo "/usr/bin/ssh $USER@$SERVER /opt/TIVOLI/ITM/li6263/is/bin/ism_startup.sh stop nco_m_icmp"
echo "/usr/bin/ssh $USER@$SERVER /opt/TIVOLI/ITM/li6263/is/bin/ism_startup.sh stop nco_m_snmp"
/usr/bin/ssh $USER@$SERVER  '/opt/TIVOLI/ITM/li6263/is/bin/ism_startup.sh stop nco_m_snmp' 2>&1
/usr/bin/ssh $USER@$SERVER  '/opt/TIVOLI/ITM/li6263/is/bin/ism_startup.sh stop nco_m_icmp' 2>&1
echo "sleep 30"
/bin/sleep 30
/usr/bin/ssh $USER@$SERVER  '/opt/TIVOLI/ITM/li6263/is/bin/ism_startup.sh stop nco_m_snmp' 2>&1
echo "exit 0"
exit 0
