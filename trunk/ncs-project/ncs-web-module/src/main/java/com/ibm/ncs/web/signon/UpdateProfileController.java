/**
 * 
 */
package com.ibm.ncs.web.signon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dto.TUser;
import com.ibm.ncs.model.dto.TUserPk;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.PasswordUtil;
import com.ibm.ws.webservices.engine.utils.Base64;

/**
 * @author root
 * 
 */
public class UpdateProfileController implements Controller {

	private TUserDao userDao;

	String pageView;
	String message = "";

	public TUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(TUserDao userDao) {
		this.userDao = userDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String username = ((Map<String, String>) request.getSession().getAttribute("signOnFlag")).get("username");

			List<TUser> users = this.userDao.findWhereUnameEquals(username);
			if (users != null && users.size() > 0) {
				boolean needToUpdate = false;
				TUser user = users.get(0);
				String email = request.getParameter("email");
				if (email != null && email.trim().length() > 0) {
					user.setEmail(email);
					needToUpdate = true;
				}
				String fullname = request.getParameter("fullname");
				if (fullname != null && fullname.trim().length() > 0) {
					user.setFullname(fullname);
					needToUpdate = true;
				}

				String password = request.getParameter("password");
				String newPassword1 = request.getParameter("newPassword1");
				String newPassword2 = request.getParameter("newPassword2");
				if (newPassword1 != null && newPassword1.trim().length() > 0 || newPassword2 != null && newPassword2.trim().length() > 0) {
					if (!user.getPassword().equals(new String(Base64.encode(password.getBytes())))) {
						message = "如果需要修改口令, 请正确输入您的当前口令!";
						needToUpdate = false;
					} else {
						if (newPassword1 != null && newPassword2 != null && newPassword1.equals(newPassword2)) {
							user.setPassword(new String(PasswordUtil.encode(newPassword2)));
							needToUpdate = true;
						} else {
							message = "您两次输入的口令不匹配!";
							needToUpdate = false;
						}
					}
				}
				if (needToUpdate) {
					 this.userDao.update(new TUserPk(user.getUsid()), user);
					 message = "修改成功!";
				}
			}

		} catch (Exception e) {
			message = "基础信息及口令修改失败!";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage(), e);
			e.printStackTrace();
		}
		model.put("message", message);
		return new ModelAndView(this.getPageView(), "definition", model);
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
}
