package com.ibm.ncs.web.signon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TRoleAndServerNodeDao;
import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dto.TRoleAndServerNode;
import com.ibm.ncs.model.dto.TUser;
import com.ibm.ncs.model.dto.TUserPk;
import com.ibm.ncs.util.Base64Encode;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.web.HttpSessionList;

public class TUsersController implements Controller {

	private TUserDao TUserDao;
	private TRoleAndServerNodeDao roleAndServerNodeDao;
	
	String pageView;
	String action;
	public String message = "";

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		message = "";

		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> signOnFlag = new HashMap<String, String>();
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		String mode = request.getParameter("mode");
		String ifContinue = request.getParameter("ifContinue");

		if (action != null && action.equalsIgnoreCase("login")) {
			try {
				if (mode != null && mode.equalsIgnoreCase("force")) {
					if (ifContinue != null && ifContinue.equalsIgnoreCase("continue")) {
						return new ModelAndView("/login.jsp", "model", model);
					}
				}

				if ((!(mode != null && mode.equals("force"))) && (username == null || password == null || username.equals("") || password.equals(""))) {
					message = "login.missing";
					model.put("message", message);
					return new ModelAndView("/login.jsp", "model", model);
				}

				Base64Encode bencode = new Base64Encode();
				model.put("username", username);
				model.put("password", password);

				signOnFlag.put("username", username);

				if (password != null)
					password = bencode.encode(password.getBytes());

				List<TUser> dto = TUserDao.findWhereUnameEquals(username);
				if (dto == null || dto.size() < 1) {
					message = "login.user";
					model.put("message", message);
					return new ModelAndView("/login.jsp", "model", model);
				} else if (dto.size() > 1) {
					message = "login.multiRecord";
					model.put("message", message);
					return new ModelAndView("/login.jsp", "model", model);
				} else {
					if (mode != null && mode.equalsIgnoreCase("force")) {
						if (ifContinue != null && ifContinue.equalsIgnoreCase("force")) {
							// To do: expire all other sessions
							// System.out.println("---code mark000---");
							Map sessions = HttpSessionList.getSessions();
							try {
								synchronized (sessions) {
									String sessionID = request.getSession(true).getId();
									System.out.println("sync ...session(true).getid...sessionID=" + sessionID + "; sessionMap=\n" + sessions);
									Iterator sessionIterator = sessions.keySet().iterator();
									// System.out.println("---code mark---111");
									while (sessionIterator.hasNext()) {
										System.out.println("---code mark---222");
										try {
											HttpSession theSession = (HttpSession) sessions.get(sessionIterator.next());
											if (!theSession.getId().equals(sessionID)) {
												theSession.invalidate();
												System.out.println("---code mark---333...theSession.invalidate()");
											}
										} catch (Exception e) {
											System.out.println("---code mark---444");
											e.printStackTrace();
										}
									}
									System.out.println("sync...sessionMap=" + sessions);
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								synchronized (this) {
									dto.get(0).setStatus("1");
									TUserPk pk = dto.get(0).createPk();
									TUserDao.update(pk, dto.get(0));
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						if (!password.equals(dto.get(0).getPassword())) {
							message = "login.password";
							model.put("message", message);
							return new ModelAndView("/login.jsp", "model", model);
						}

						// check status
						/*
						if (dto.get(0).getStatus().equals("1")) {
							return new ModelAndView("/forceLogin.jsp", "model", model);
						} else {
							synchronized (this) {
								dto.get(0).setStatus("1");
								TUserPk pk = dto.get(0).createPk();
								TUserDao.update(pk, dto.get(0));
							}
						}
						*/
						// Set roles into Session
						List<TRoleAndServerNode> roles = this.roleAndServerNodeDao.findByUsername(username);
						request.getSession(true).setAttribute("roles", roles);
						//signOnFlag.put("role", "all");// not used
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "login.login_btn";
				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " User Login Failed!\n Error Message:\n" + e);
				return new ModelAndView("/login.jsp", "model", model);
			}

			model.put("message", message);
			request.getSession(true).setAttribute("signOnFlag", signOnFlag);
			Log4jInit.ncsLog.info(this.getClass().getName() + "  User: " + username + " logged in to the system.");
			return new ModelAndView(pageView, "model", model);

		} else if (action != null && action.equalsIgnoreCase("logout")) {
			try {
				model.put("message", message);
				Map<String, String> signOutFlag = (Map<String, String>) request.getSession(true).getAttribute("signOnFlag");
				if (signOutFlag != null) {
					// reset status to 0, need testing
					username = signOutFlag.get("username");
					List<TUser> dto = TUserDao.findWhereUnameEquals(username);
					if (dto != null && dto.size() == 1) {
						synchronized (this) {
							TUser user = dto.get(0);
							user.setStatus("0");
							TUserDao.update(user.createPk(), user);
						}
					}
					Log4jInit.ncsLog.info(this.getClass().getName() + " User: " + signOutFlag.get("username") + " logged out from the system.");
					request.getSession().removeAttribute("signOnFlag");
					return new ModelAndView("/login.jsp", "model", model);
				}
			} catch (Exception e) {
				model.put("message", message);
				Log4jInit.ncsLog.info(this.getClass().getName() + " User logged out from the system failed!\nError Message:\n" + e.getMessage());

				e.printStackTrace();
			}
		}

		model.put("message", message);
		return new ModelAndView("/login.jsp", "model", model);
	}

	public TUserDao getTUserDao() {
		return TUserDao;
	}

	public void setTUserDao(TUserDao userDao) {
		TUserDao = userDao;
	}

	public TRoleAndServerNodeDao getRoleAndServerNodeDao() {
		return roleAndServerNodeDao;
	}

	public void setRoleAndServerNodeDao(TRoleAndServerNodeDao roleAndServerNodeDao) {
		this.roleAndServerNodeDao = roleAndServerNodeDao;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public static void display(TUser dto) {
		StringBuffer buf = new StringBuffer();
		buf.append(dto.getUname());
		buf.append(", ");
		buf.append(dto.getPassword());
		buf.append(", ");
		buf.append(dto.getStatus());
		buf.append(", ");
		buf.append(dto.getDescription());
		buf.append(", ");
		buf.append(dto.getUsid());
		System.out.println(buf.toString());
	}
}
