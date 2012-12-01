package com.ibm.ncs.web.signon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class SignonFilter
 */
public class SignonFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SignonFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here.
		try {
			response.setLocale(new Locale("zh","CN"));
			Map<String, String> signOnFlag = null;
			signOnFlag = ((Map) ((HttpServletRequest) request).getSession(true).getAttribute("signOnFlag"));
			if(signOnFlag != null && !signOnFlag.get("username").equals("")){
				// pass the request along the filter chain
				chain.doFilter(request, response);
			}else{
				((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/login.jsp");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()+"/login.jsp");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
