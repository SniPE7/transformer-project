#!/bin/sh
ftp -n<<!
open $1
user $2 $3 
binary
prompt off
cd $4 
put $5 
close
bye
!

