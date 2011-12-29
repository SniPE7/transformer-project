/**
 * 
 */
package com.ibm.tivoli.cmcc.web.monitor;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.session.Session;
import com.ibm.tivoli.cmcc.session.monitor.SessionMonitor;


/**
 * @author zhaodonglu
 *
 */
@Controller
@RequestMapping("/monitor/sessions")
public class SessoionController {

  /**
   * 
   */
  public SessoionController() {
    super();
  }

  @RequestMapping(value="{id}", method = RequestMethod.GET)
  public @ResponseBody Session getSessionById(@PathVariable String id) {
    SessionMonitor sessionMonitor = getSessionMonitor();
    return sessionMonitor.getSession(id);
  }
  
  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody DataTableResult<Session> getSessions(@RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength  ) {
    SessionMonitor sessionMonitor = getSessionMonitor();
    // TODO Implements paging interface
    List<Session> allSessions = sessionMonitor.getAllSessions();
    DataTableResult<Session> result = new DataTableResult<Session>(allSessions.size(), allSessions.size());
    result.setsEcho(sEcho);
    for (int i = iDisplayStart; i < allSessions.size() && i - iDisplayStart < iDisplayLength; i++) {
        result.getAaData().add(allSessions.get(i));
    }
    return result;
  }

  /**
   * @return
   */
  private SessionMonitor getSessionMonitor() {
    ServletRequestAttributes srAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = srAttr.getRequest();
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    SessionMonitor sessionMonitor = (SessionMonitor)context.getBean("sessionMonitor4WebAdmin");
    return sessionMonitor;
  }
  
}
