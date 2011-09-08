/**
 * 
 */
package com.ibm.tivoli.pim.task;

import java.rmi.RemoteException;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.AuthorizationException;
import com.ibm.itim.apps.identity.PersonMO;
import com.ibm.itim.apps.provisioning.AccountMO;

/**
 * @author Administrator
 *
 */
public class ConsoleAndLogNotifer implements Notifier {

  /**
   * 
   */
  public ConsoleAndLogNotifer() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.task.Notifier#notifyActivation(com.ibm.itim.apps.provisioning.AccountMO, java.lang.String)
   */
  public void notifyActivation(AccountMO pimAccountMO, String password) throws AuthorizationException, RemoteException, ApplicationException {
    PersonMO ownerMO = pimAccountMO.getOwner();
    System.out.println(String.format("Requester: [%s], Target Account: [%s]", ownerMO.getData().toString(), pimAccountMO.getData().toString()));
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.task.Notifier#notifyDeactivation(com.ibm.itim.apps.provisioning.AccountMO, java.lang.String)
   */
  public void notifyDeactivation(AccountMO pimAccountMO, String password) throws AuthorizationException, RemoteException, ApplicationException {
    PersonMO ownerMO = pimAccountMO.getOwner();
    System.out.println(String.format("Requester: [%s], Target Account: [%s]", ownerMO.getData().toString(), pimAccountMO.getData().toString()));
  }
}
