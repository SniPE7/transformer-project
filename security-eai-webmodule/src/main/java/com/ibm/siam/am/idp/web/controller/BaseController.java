package com.ibm.siam.am.idp.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.siam.am.idp.themes.ThemesUtils;

import edu.internet2.middleware.shibboleth.idp.session.ServiceInformation;
import edu.internet2.middleware.shibboleth.idp.session.Session;

/**
 * Controller基类
 * @author ZhouTengfei
 * @since 2012-11-5 下午4:04:55
 */

public class BaseController {
  
  public ModelAndView setModelAndView(ModelAndView mav, HttpServletRequest request){
    Session idpSession = (Session)request.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);
    if(null != idpSession){
      mav.addObject("PrincipalName", idpSession.getPrincipalName());
      ServiceInformation[] servicesInfo = idpSession.getServicesInformation();
      Map<String, String> smap = null;
      List<Map<String, String>> list = new ArrayList<Map<String, String>>();
      String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
      SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
      DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(TIME_PATTERN);
      long loginInstant;
      long expirationInstant;
      Date date;
      Set<String> set = new HashSet<String>();
      for(ServiceInformation si: servicesInfo){
        if(set.add(si.getEntityID())){
          smap = new HashMap<String,String>();
          smap.put("entityid", si.getEntityID());
          smap.put("method", si.getAuthenticationMethod().getAuthenticationMethod().replace(":", "_"));
          smap.put("loginInstant", si.getLoginInstant().toDateTime(DateTimeZone.getDefault()).toString(FORMATTER));
          loginInstant = si.getLoginInstant().toDateTime(ISOChronology.getInstanceUTC()).getMillis();
          expirationInstant = loginInstant + si.getAuthenticationMethod().getAuthenticationDuration();
          date = new Date(expirationInstant);
          smap.put("expirationInstant", sdf.format(date));
          list.add(smap);
        }
      }
      mav.addObject("ServiceInformation", list);
    }
  
    //设置主题样式路径
    String themeName = (String) request.getSession().getAttribute(ThemesUtils.THEME_ATTR_NAME);
    mav.addObject(ThemesUtils.THEME_PARAM_NAME, ThemesUtils.getThemeByName(themeName));
    return mav;
  }
}
