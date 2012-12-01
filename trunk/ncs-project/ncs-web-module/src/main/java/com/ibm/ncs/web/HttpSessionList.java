package com.ibm.ncs.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dao.spring.TUserDaoImpl;
import com.ibm.ncs.model.dto.TUser;

/**
 * Application Lifecycle Listener implementation class HttpSessionList
 *
 */
public class HttpSessionList implements HttpSessionListener {

	private static final Map<String, HttpSession> sessions //= new HashMap<String, HttpSession>();
		//= Collections.synchronizedMap(new HashMap<String, HttpSession>() );
		= new ConcurrentHashMap<String, HttpSession>();
    /**
     * Default constructor. 
     */
    public HttpSessionList() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        try {
        	synchronized(this){
			sessions.remove(event.getSession().getId());
		//	sessions.clear();
			event.getSession().invalidate();
			System.out.println("sessionDistroyed...event.session.id="+event.getSession().getId());

			clearUserStatus(event);
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event) {
    	try {
    		synchronized (this){
			HttpSession aSession = event.getSession();
			sessions.put(aSession.getId(), aSession);
System.out.println("sessionCreated...event.session.id="+event.getSession().getId());
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static HttpSession searchSession(String sessionID){
    	return sessions.get(sessionID);
    }
    
    public static int count(){
    	return sessions.size();
    }

	public static Map<String, HttpSession> getSessions() {
		return sessions;
	}
	
	private void clearUserStatus(HttpSessionEvent event){
		try{
			Map<String, String> signOutFlag = (Map<String, String>) event.getSession().getAttribute("signOnFlag");				
			//reset status to 0, need testing
			String username = signOutFlag.get("username");
			TUserDao TUserDao = new TUserDaoImpl();
			List<TUser> dto = TUserDao.findWhereUnameEquals(username);
			if(dto!=null && dto.size() == 1){
				synchronized (this){
				TUser user = dto.get(0);
				user.setStatus("0");
				TUserDao.update(user.createPk(), user);
				}
			}
		}catch (Exception e){
			
		}
	}
	
}
