package group.tivoli.security.eai.loginmodule.tam.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.itim.ws.exceptions.WSInvalidLoginException;
import com.ibm.itim.ws.exceptions.WSLoginServiceException;
import com.ibm.itim.ws.model.WSSession;
import com.ibm.itim.ws.services.WSItimService;
import com.ibm.itim.ws.services.facade.ITIMWebServiceFactory;
import com.sinopec.siam.am.idp.authn.service.TimLdapUserPassServiceImpl;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

/**
 * 使用用户自身账号密码调用 TIM webservice 修改密码接口 
 * @author xuhong
 * @since 2012-12-12 下午12:36:01
 */

public class TimLdapSelfUserPassServiceImpl extends TimLdapUserPassServiceImpl {
  private final Logger log = LoggerFactory.getLogger(TimLdapSelfUserPassServiceImpl.class);

  private TimUserPasswordService timUserPasswordService;
  
  public TimUserPasswordService getTimUserPasswordService() {
    return timUserPasswordService;
  }

  public void setTimUserPasswordService(TimUserPasswordService timUserPasswordService) {
    this.timUserPasswordService = timUserPasswordService;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws ServiceException
   * @throws MalformedURLException
   * @throws RemoteException
   * @throws WSLoginServiceException
   * @throws WSInvalidLoginException
   * @throws javax.xml.rpc.ServiceException 
   */
  public void updatePassword(String username, String oldPassword, String newPassword) throws MalformedURLException,
  WSInvalidLoginException, WSLoginServiceException, RemoteException, ServiceException {
    if(log.isDebugEnabled()){
      log.debug("Update User Pass by username [{}]", username);
    }
    List<LdapUserEntity> result = getUserByUsername(username);
    if (result.size() == 0) {
      log.error("Username not exists, username [{}]", username);
      throw new ServiceException(String.format("Username not exists, username[%s]", username));
    } else if (result.size() > 1) {
      log.error("Find more than one user, username [{}]", username);
      throw new ServiceException(String.format("Find more than one user by username, username[%s]", username));
    }
    LdapUserEntity userEntity = result.get(0);
    
    //before call the webservice api, check the user's systemuser erchangepswdrequired, true, set to false. 
    //otherwise , call webservice error
    if(timUserPasswordService.getSystemUserPwdRequired(username)) {
      timUserPasswordService.closeSystemUserPwdRequired(username);
    }
    
    // 修改TIM口令（API）
    ITIMWebServiceFactory webServiceFactory = new ITIMWebServiceFactory(getTimSoapEndpoint());
    WSItimService wsItimService = webServiceFactory.getWSItimService();
    Calendar scheduledTime = Calendar.getInstance();
    scheduledTime.setTime(new Date());
    WSSession wsSession = wsItimService.login(username, oldPassword);

    wsItimService.synchPasswords(wsSession, userEntity.getValueAsString(getUserOwnerLdapAttribute()), newPassword,
        scheduledTime, isNotifyByMail());

  }
}
