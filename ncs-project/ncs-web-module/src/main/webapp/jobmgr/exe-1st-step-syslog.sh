#!/bin/sh

SERVER=$1
USER=$2
PASS=$3
Targetdir=$4
#FILE=$5
SRCF=$5

echo "copy SrcType to /opt/netcool/omnibus/lookuptables ..."
#/bin/cp /tmp/SrcType /opt/netcool/omnibus/lookuptables
/bin/cp $SRCF $Targetdir 2>&1

echo "restart sevice ..."
#/opt/netcool/omnibus/bin/nco_pa_stop -server NCXX1_PA -password 1 -process SYSLOG 
echo "/opt/netcool/omnibus/bin/nco_pa_stop -server ${SERVER}_PA -password 1 -process SYSLOG"
/opt/netcool/omnibus/bin/nco_pa_stop -server ${SERVER}_PA -password 1 -process SYSLOG 2>&1| tee tmp.txt
cat tmp.txt

/bin/sleep 1
#/opt/netcool/omnibus/bin/nco_pa_status -server NCXX1_PA -password 1
echo "/opt/netcool/omnibus/bin/nco_pa_status -server ${SERVER}_PA -password 1"
/opt/netcool/omnibus/bin/nco_pa_status -server ${SERVER}_PA -password 1 2>&1|tee tmp.txt
cat tmp.txt

#/opt/netcool/omnibus/bin/nco_pa_start -server NCXX1_PA -password 1 -process SYSLOG
echo "/opt/netcool/omnibus/bin/nco_pa_start -server ${SERVER}_PA -password 1 -process SYSLOG"
/opt/netcool/omnibus/bin/nco_pa_start -server ${SERVER}_PA -password 1 -process SYSLOG 2>&1| tee tmp.txt
cat tmp.txt

#/opt/netcool/omnibus/bin/nco_pa_status -server NCXX1_PA -password 1
echo "/opt/netcool/omnibus/bin/nco_pa_status -server ${SERVER}_PA -password 1 "
/opt/netcool/omnibus/bin/nco_pa_status -server ${SERVER}_PA -password 1  2>&1|tee tmp.txt
cat tmp.txt


echo "exit 0"


exit 0
