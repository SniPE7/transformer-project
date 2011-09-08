/**
 * 
 */
package com.ibm.tivoli.pim.task;

import java.rmi.RemoteException;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.AuthorizationException;
import com.ibm.itim.apps.provisioning.AccountMO;

/**
 * @author Administrator
 *
 */
public interface Notifier {

  void notifyActivation(AccountMO pimAccountMO, String password) throws AuthorizationException, RemoteException, ApplicationException;

  void notifyDeactivation(AccountMO pimAccountMO, String password) throws AuthorizationException, RemoteException, ApplicationException;

}
