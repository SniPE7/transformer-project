/**
 * 
 */
package com.ibm.ncs.util.test;

/**
 * @author dlee
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;


import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

//import com.ibm.job4web.delegate.QoutDelegator;
//import com.ibm.job4web.delegate.TinputDelegator;
//import com.ibm.job4web.jms.InputReceiverThread;
//import com.ibm.job4web.util.JClientconstant;
//import com.ibm.job4web.util.ServiceLocatorException;
//import com.ibm.job4web.util.Stopwatch;

public class JobHandler
{ 
	private static final Logger LOGGER = Logger.getLogger(JobHandler.class);
	private static final Logger LOGGER2 = Logger.getLogger("errorLog");
//	private static FileHandler filehler ,errorhandler;
	private String jobmgrId = "";
	
	private boolean stopoutput = false;
	private boolean stopinput  = false;
	//private boolean stoperror  = false;
	
	Process process; 
	Thread in; 
	Thread out; 
	Thread err;
	Thread msgin;
	
	Writer theOutput;
	String stdOutText = "jobhandler stdout...";

	public JobHandler(final Process process, final String jobmgrId)
	{ 
//		logfiles();
		
		this.jobmgrId = jobmgrId;
		this.process = process; 
		final InputStream stdOut 		= process.getInputStream(); 
		 
		final byte[] buffer = new byte[1024]; 
		out = new Thread() 
		{ 
			
			//String line; 
			int lineNumber=0; 
			public void run() 
			{ 
				StringBuffer tmp = new StringBuffer();
				try { 					
					
					 int countException =0;
					while(!stopoutput)// (true)
					{ 
						int count = stdOut.read(buffer); 
//						if (count<=0){stopoutput=true;stopinput=true;continue;}
//						if (count<=0){
//							try {
//								Thread.currentThread().sleep(1000);
//							} catch (InterruptedException e) {								
//								stopoutput=true;
//								stopinput=true;
//								continue;
//							}
//						}
//						System.out.println("stdout stream read buffer count = "+count); //test only
						
						String text;
						try {
							text = new String(buffer, 0, count); //(buffer, 0, count-1);
					//		System.out.println(text);
						} catch (Exception e) {
							countException++;
							if (countException>=3) {
								stopoutput = true;
								stopinput = true;
								text="$$MSGEXIT$$";//JClientconstant.MSG_EXIT;//"End of jobhandler...stdout";
							}
							else {sleep(1000);
								text=".";//"jobhandler...stdout =-1, countEx="+countException; // test only
							}
							//e.printStackTrace();
						}
//						System.out.println(lineNumber+":"+text); //test only
//						lineNumber++; 
//						if(theOutput==null){
//							PrintWriter myoutput = new PrintWriter(theOutput);
//							myoutput.print(text);
//						}
						
						LOGGER.info(text);
						tmp.append(text);
//						int exitValue = process.waitFor();
//						System.out.println("process.waitFor() ...exit Value is : " +exitValue);
//						System.out.println("process.exitValue() ...exit Value is : " +process.exitValue());
						stdOutText = tmp.toString();
//						System.out.println("Jobhandler...tmp.tostring()="+tmp.toString());
//						System.out.println("Jobhandler...stdOutText="+stdOutText);
						if((text.contains("$$MSGEXIT$$"))){stopoutput=true;stopinput=true;}
					}

				} 
				catch (Exception e)
				{ 
					LOGGER2.error("Thread stdOut got Exception:"+e.getMessage());
//					e.printStackTrace();
					stopoutput = true;
					LOGGER.info("Exiting output thread");
				} 
				finally
				{			
					try {
						stdOut.close();
					} catch (IOException e) {
					}
				}

			} 
		}; 
		final InputStream stdErr 		= process.getErrorStream(); 
		//final BufferedReader r=new BufferedReader(new InputStreamReader(inputStream)); 
		final byte[] buffer2 = new byte[1024]; 
		err = new Thread() 
		{ 
			//String line; 
			int lineNumber=0; 
			public void run() 
			{ 
				StringBuffer tmp2 = new StringBuffer();
				try { 

					while (!stopoutput) // (true)
					{ 
						int count = stdErr.read(buffer2); 
						
//						if (count<=0) {stopoutput=true;stopinput=true;continue;};
//						if (count<=0){
//							try {
//								Thread.currentThread().sleep(1000);
//							} catch (InterruptedException e) {								
//								stopoutput=true;
//								stopinput=true;
//								break;
//								//continue;
//							}
//						}
//						System.out.println("error stream read buffer count = "+count);
						String text;
						try {
							text = new String(buffer2, 0, count-1);
						} catch (Exception e) {
//							stopoutput=true;
//							stopinput=true;
							text=".";//"jobhandler...2& ";
							sleep(1000); 
						}
						LOGGER.info(text);
						tmp2.append(text);
						stdOutText += tmp2.toString();
						System.out.println("err.text="+text);
					} 
//					System.out.println("++__++ ending the error stream!!  [stopoutput="+stopoutput+"] [stopinput="+stopinput+"]");
					
//					int exitValue = process.waitFor();
//					System.out.println("error thread process.waitFor() ...exit Value is : " +exitValue);
//					System.out.println("error thread process.exitValue() ...exit Value is : " +process.exitValue());
				} 
				catch (Exception e)
				{ 
					LOGGER2.error("Thread stdErr got Exception:"+e.getMessage());
//					e.printStackTrace();
					stopoutput = true;
					LOGGER.error("Exiting 2& thread");
				} 
				finally
				{				
					try {
						stdErr.close();
					} catch (IOException e) {
					}
				}
			} 
		}; 
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		final OutputStream stdIn = process.getOutputStream(); 
		in = new Thread() 
		{ 
//			//String line; 
			public void run() 
			{ 
				try { 
					while (!stopinput)//(true)
					{ 
						String input = reader.readLine();//+"\n";
						if (null==input||input.equals("")||input.equalsIgnoreCase("null")){
							input +=".";
							//continue;
						}
						input += "\n";
						stdIn.write(	input.getBytes()); 
						stdIn.flush(); 
						LOGGER.info(input);
						
							try {
								Thread.currentThread().sleep(1000);
							} catch (InterruptedException e) {								
								stopoutput=true;
								stopinput=true;
								//continue;
								break;
							}
						
					} 
					//reader.close();
					LOGGER.info("++__++ ending the stdin stream!! loop ended!!");
				} 
				catch (Exception e) 
				{ 
					LOGGER.info("Thread stdIn get :"+e.getMessage());
					//e.printStackTrace();
					stopinput = true;
					LOGGER.info("Exiting input thread");
				}finally{
					try {
						reader.close();
					} catch (IOException e) {
					}
				}
			} 
		}; 
		//a method accept remote input from a queue directly, another in thread.
		//final OutputStream stdIn2 = process.getOutputStream(); 
//		receiverThread = new InputReceiverThread();
//		receiverThread.setStdin2(stdIn);
//		receiverThread.setJobmgrId(jobmgrId);
//		if(stopinput==true){receiverThread.setStop(true);}
//		class MsgIn extends Thread implements MessageListener
//		//msgin = new MsgIn()
//		{ 
//			//String line; 
//			public void run() 
//			{ 
////			   	Context ctx = null;				
////				ConnectionFactory cf = null;				
////				Destination dest = null;				
////				Connection connection = null;				
////				Session session = null;
////				MessageConsumer destReceiver = null;
////				Stopwatch stopwatch = new Stopwatch();
////				TinputDelegator tid = new TinputDelegator();
//				try { 					
////					 ctx = new InitialContext();					
////					 cf = (ConnectionFactory) ctx.lookup("jms/JMS_CF1");					
////					 dest = (Destination) ctx.lookup("jms/STDINT");	//("jms/STDINQ");					
////					 connection = cf.createConnection("wasadmin","wasadmin");
////					 session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
////					 destReceiver = session.createConsumer(dest);
//					 //tid.setMessageListener(this);
////					 destReceiver.getMessageSelector();
//					 //tid.start();
//					while (!stopinput)//(true)
//					{ 
////						try {
////							LOGGER.fine("Msgin new TinputDelegator (tid)/ rebuild elapsed time= "+stopwatch.elapsedTime());stopwatch.restart();
////							tid.init();
////							tid.setMessageListener(this);
////							tid.start();
////							LOGGER.fine("Msgin tid start listener elapsed time= "+stopwatch.elapsedTime());stopwatch.restart();
////						} catch (Exception e1) {//add looger wanrining
////							//LOGGER.warning("Thread stdin2 get exception: "+e1.getMessage());
////						}
//						try {
//							Thread.currentThread().sleep(60000);//(900000);
//							//stop = isStop();
//						} catch (InterruptedException  e) {				
//							stopinput = true;
//							continue;
//						}				
////						try {
////							tid.closeAll();
////							LOGGER.fine("Msgin (sleep) +(tid close listener) elapsed time= "+stopwatch.elapsedTime());stopwatch.restart();
////						} catch (Exception e) {
////						}
//					} 	
//					LOGGER.info("++__++ ending the inputReceiver !!");
//				} 
//				catch (Exception e)
//				{ 
//					LOGGER2.warning("Thread message stdIn2 got Exception:"+e.getMessage());
//					
//				} 
//				finally
//				{		
////					try {
////						tid.closeAll();
////					} catch (Exception e) {
////					}
////					try {
////						destReceiver.close();
////						session.close();
////						connection.close();
////					} catch (JMSException e) {
//////						e.printStackTrace();
////					}					
//				}
//			} 
//			
//		    public void onMessage(Message message) {
//
//		        try {
//		            String msgText = ((TextMessage) message).getText()+"\n";
//		            String id = message.getStringProperty(JClientconstant.JOB_MGR_ID);
////		            System.out.println("msg property id="+id);
////		            System.out.println("jobmgrid="+jobmgrId);
////		            System.out.println("input receiver thread listener... onMessage.."+msgText);
////		            System.out.println("message.getJMSExpiration()="+message.getJMSExpiration());
//		            
//		            if (id.equals(jobmgrId)) {
////			            System.out.println("input receiver thread listener... onMessage.."+msgText);
////			            System.out.println("jobmgrId=msgproprety id"+id);
//						stdIn.write(msgText.getBytes());
//						stdIn.flush();
//						LOGGER.fine(msgText);
//					}
//		            else if(id.equals(JClientconstant.WAS_B2B_STARTED)){
//		            	//response for the application b2b web console deployed
////		            	byte[] response = JClientconstant.JOB_MGR_ALIVE.getBytes();
////		            	stdOut.read(response);
//		            	LOGGER.fine(JClientconstant.JOB_MGR_ALIVE);
//		            	System.out.println("..."+JClientconstant.JOB_MGR_ALIVE);
//		            	try {
//							QoutDelegator qd = new QoutDelegator();
//							qd.init();
//							qd.sendingMsg(jobmgrId, JClientconstant.JOB_MGR_ALIVE);
//							qd.closeAll();
//						} catch (Exception e) {
//							LOGGER2.warning("Msgin received from b2b app notifcation, but failed to sendout msg queue: "+ e.getMessage());
//						}
//		            }
//
//		        } catch (JMSException e) {
//		            e.printStackTrace();
//		            stopinput = true;
//		        } catch (IOException e) {
//					e.printStackTrace();
//				}
//		    }
//		}; 
//		msgin = new MsgIn();

		
		
		//a method that accept string from stdout thread, and then sending it to queue.
//		sender = new Thread() 
//		{ 
//			public void run() 
//			{ 
//				try { 
//					while (true)
//					{ 
//						
//						
//					} 
//				} 
//				catch (Exception e)
//				{ 
//					LOGGER2.warning("Thread receiver got Exception:"+e.getMessage());
//					e.printStackTrace();
//				} 
//			} 
//		};
	
		
	} 

	public void startIn()
	{ 
		in.start(); 
	} 

	public void startOut()
	{ 
		out.start(); 
	} 

	public void interruptIn()
	{ 
		in.interrupt(); 
	} 

	public void interruptOut() 
	{ 
		out.interrupt(); 
	} 
	
	public void startErr(){
		err.start();
	}
	
	public void interruptErr(){
		err.interrupt();
	}
		
	public void startIn2(){
		//receiverThread.start();
	//	msgin.start();
	}
	
	public void interruptIn2(){
		//receiverThread.interrupt();
	//	msgin.interrupt();
	}
		
	public boolean isStopoutput() {
		return stopoutput;
	}

	public String getJobmgrId() {
		return jobmgrId;
	}

	public void setJobmgrId(String jobmgrId) {
		this.jobmgrId = jobmgrId;
	}

	public Writer getTheOutput() {
		return theOutput;
	}

	public void setTheOutput(Writer theOutput) {
		this.theOutput = theOutput;
	}

	public String getStdOutText() {
		return stdOutText;
	}

	public void setStdOutText(String stdOutText) {
		this.stdOutText = stdOutText;
	}

//	public void logfiles(){
//		try {
//			filehler = new FileHandler("logs/exe-shell.log", true);//("../log/jobhandler.log", true);	
//			filehler.setFormatter(new SimpleFormatter());
//			LOGGER.setLevel(Level.FINEST);
//			LOGGER.addHandler(filehler);
//
//			errorhandler = new FileHandler("logs/errorhandler.log", true);
//			errorhandler.setFormatter(new SimpleFormatter());
//			LOGGER2.setLevel(Level.FINEST);
//			LOGGER2.addHandler(errorhandler);
//			
//		} catch (IOException e1) {
//			
//		}
//	}
//	public void logclose(){
//		filehler.close();
//		errorhandler.close();
//	}
}

