package com.sinopec.siam.am.idp.authn.module;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注销接口 先清理本地session 然后转向webseal注销url Servlet implementation class
 * SSOLogoutServlet
 */
public class SSOLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(SSOLogoutServlet.class);

	/** SSO logout path */
	private String sloPath;

	/** {@inheritDoc} */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		if (sloPath == null || sloPath.length() == 0) {
			response.sendRedirect(request.getContextPath());
		} else {
			response.sendRedirect(sloPath);
		}

	}

	/** {@inheritDoc} */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		if (getInitParameter("sloPath") != null && !"".equals(getInitParameter("sloPath").trim())) {
			sloPath = getInitParameter("sloPath");
		}
	}
}
