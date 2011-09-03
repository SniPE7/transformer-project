

PIM Web Part Comments.
By Guo Xiang Yong

1. 2 Pages
   A: Common User Page FR(Client IE and Server functions)
      a. View History
	  b. Select server and private accout(root,oracle,tivoli etc.)
	  c. Submit the request batch for many resource.
	  
	  1). History can send a xml format £¨PIM/history?top=10 )
		<?xml version="1.0" encoding="UTF-8"?>
		<serverlists>
			<serverlist>
			<ProccessID>201101081234567</ProccessID>
			<AccountID>AIX_HE_192.168.9.120:oracle</AccountID>
			<StartTime>20110202 15:00:00</StartTime>
			<EndTime>20110203 06:30:00</EndTime>
			<Reason>Oracle Admin Monthly Maintance</Reason>
			<Requestor>test1</Requestor>
			<Approver>admin1</Approver>
			<Status>Finish</Status>
			<Acction></Acction>
			</serverlist>
		</serverlists>
		
	  2). Server and account list can send out a xml file, inclucde the resource's status. £¨PIM/resource?servername=AIX_HE_192.168.9.120*&start="20110202 15:00:00 GMT"&end="20110202 15:00:00 GMT" )
	  
		<?xml version="1.0" encoding="UTF-8"?>
		<resources>
			<resource>
				<AccountID>AIX_HE_192.168.9.120:oracle</AccountID>
				<StartTime>20110202 15:00:00</StartTime>
				<EndTime>20110203 06:30:00</EndTime>
				<Requestor></Requestor>
				<Approver></Approver>
				<Status>Finish</Status>
				<Acction></Acction>
			</resource>
		</resources>


	  3). POST (PIM/requestaccount)
	     resourcelist(List)
		 
	B: Approver's Page FR(Client IE and Server functions)
		a. View Approval's History
