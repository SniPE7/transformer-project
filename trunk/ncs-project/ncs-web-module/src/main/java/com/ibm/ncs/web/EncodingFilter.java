

package com.ibm.ncs.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.*;

public class EncodingFilter
    implements Filter
{

    public EncodingFilter()
    {
        encoding = null;
    }

    public void destroy()
    {
        encoding = null;
        config = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws ServletException, IOException
    {
        if(encoding != null){
            req.setCharacterEncoding(encoding);
        	resp.setLocale(new Locale("zh","CN"));
        	chain.doFilter(req, resp);
	    }else{
	        req.setCharacterEncoding("UTF-8");
	        resp.setLocale(new Locale("zh","CN"));
	        chain.doFilter(req, resp);
	    }
    }

    public void init(FilterConfig config)
        throws ServletException
    {
        this.config = config;
        encoding = config.getServletContext().getInitParameter("encoding");
    }

    protected String encoding;
    protected FilterConfig config;
}
