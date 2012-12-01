#!/bin/sh

SERVER=$1
USER=$2
PASS=$3
Targetdir=$4
FILE=$5
SRCF=$6


# copy to target
# scp $SRCF $USER/$PASS@$SERVER:/$PATH/$FILE 
echo "FTP to Target ..."
cd /opt/netcool/etc/jobmgr 2>&1
ftp -n<<!
open $SERVER
user $USER $PASS 
binary
prompt off
cd $Targetdir
put $FILE 
close
bye
!

#cd /tmp
#./ftp_test.sh NCBF3 ncoadmin ncoadmin /opt/TIVOLI/ITM/li6263/is/profiles/active icmp.xml

echo "exit 0"
exit 0
