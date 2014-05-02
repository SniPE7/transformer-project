package com.sinopec.siam.am.idp.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.sinopec.siam.am.idp.authn.service.MultiplePersonFoundException;
import com.sinopec.siam.am.idp.authn.service.PersonNotFoundException;
import com.sinopec.siam.am.idp.authn.service.PersonService;
import com.sinopec.siam.am.idp.authn.service.PersonServiceException;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * 忘记密码 Controller。
 * 
 * @author Booker
 */
@Controller
public class LosePwdController extends BaseController {

    private final Logger log        = LoggerFactory.getLogger(LosePwdController.class);

    @Autowired
    @Qualifier("personService")
    PersonService userPassService;

    @Value("#{beanProperties['eai.loginmodule.isinternet']}")
    private boolean isInternet = false;

    /**
     * 返回页面。
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/losepwd.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

        /*if(isInternet) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/resetpwdbymail.do");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
            } catch (IOException e) {
            }
        }*/

        HttpSession session = request.getSession(false);
        if (session != null) {
            LoginContextEntry entry = (LoginContextEntry) session.getAttribute(HttpServletHelper.LOGIN_CTX_KEY_NAME);
            if (entry != null) {
                String realUrl = "";
                Object redirUrl = session.getAttribute("eai-redir-url-header");
                if (redirUrl != null) {
                    realUrl = redirUrl.toString();
                } else {
                    realUrl = request.getContextPath() + entry.getLoginContext().getAccessEnforcerURL();
                }

                request.setAttribute("gotoUrl", realUrl);
            }
        }

        ModelAndView mav = new ModelAndView("/lose/password");
        super.setModelAndView(mav, request);
        return mav;
    }

    /**
     * 用户忘记密码后修改密码接口
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/changelosepwd.do", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody
    Map<String, String> changeLosePassword(HttpServletRequest request, HttpServletResponse response) {

        String msg = "";
        String status = "success";

        String userid = request.getParameter("j_useruid");
        String checkcode = request.getParameter("j_checkcode");
        String newPassword = request.getParameter("j_npassword");

        // check kaptcha code
        HttpSession session = request.getSession(false);
        if (null == session || null == checkcode
            || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
            // return false
            Map<String, String> smsInfo = new HashMap<String, String>();
            smsInfo.put("msg", "超时,验证码错误/timeout and verification code error");
            smsInfo.put("status", "fail");

            return smsInfo;
        }

        // change password
        // 修改TIM口令（API）
        try {
            userPassService.updatePassword(userid, newPassword);

            // 清理验证码
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);

        } catch (PersonNotFoundException e) {
            msg = String.format("PersonNotFoundException Exception. username:%s", userid);
            log.error(String.format(msg, userid), e);

            status = "fail";
        } catch (MultiplePersonFoundException e) {
            msg = String.format("MultiplePersonFoundException Exception. username:%s", userid);
            log.error(String.format(msg, userid), e);

            status = "fail";
        } catch (PersonServiceException e) {
            msg = String.format("PersonServiceException Exception. username:%s", userid);
            log.error(String.format(msg, userid), e);

            status = "fail";
        }

        Map<String, String> smsInfo = new HashMap<String, String>();
        smsInfo.put("msg", msg);
        smsInfo.put("status", status);

        return smsInfo;

    }

}
