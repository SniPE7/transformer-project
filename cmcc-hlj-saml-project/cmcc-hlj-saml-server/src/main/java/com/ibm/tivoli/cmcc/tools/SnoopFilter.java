package com.ibm.tivoli.cmcc.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SnoopFilter implements Filter {
  private static Log log = LogFactory.getLog(MonitorFilter.class);

  public SnoopFilter() {
    super();
  }

  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    chain.doFilter(request, response);
    if (log.isDebugEnabled()) {
      HttpServletRequest req = (HttpServletRequest) request;
      StringWriter result = genOutput(req);
      log.debug(result.toString());
    }
  }

  /**
   * @param req
   * @return
   */
  private StringWriter genOutput(HttpServletRequest req) {
    StringWriter result = new StringWriter();
    PrintWriter out = new PrintWriter(result);

    out.println("--------------- Snoop Http Servlet ---------------------------------");
    out.println(String.format("Request URL: %s", HttpUtils.getRequestURL(req).toString()));
    out.println("[Request Info]");
    print(out, "Request method", req.getMethod());
    print(out, "Request URI", req.getRequestURI());
    print(out, "Request protocol", req.getProtocol());
    print(out, "Servlet path", req.getServletPath());
    print(out, "Path info", req.getPathInfo());
    print(out, "Path translated", req.getPathTranslated());
    print(out, "Query string", req.getQueryString());
    print(out, "Content length", req.getContentLength());
    print(out, "Content type", req.getContentType());
    print(out, "Server name", req.getServerName());
    print(out, "Server port", req.getServerPort());
    print(out, "Remote user", req.getRemoteUser());
    print(out, "Remote address", req.getRemoteAddr());
    print(out, "Remote host", req.getRemoteHost());
    print(out, "Authorization scheme", req.getAuthType());

    out.println("");

    Enumeration e = req.getHeaderNames();
    if (e.hasMoreElements()) {
      out.println("[Request Header Info]");
      while (e.hasMoreElements()) {
        String name = (String) e.nextElement();
        out.println(" " + name + ": " + req.getHeader(name));
      }
      out.println("");
    }

    e = req.getParameterNames();
    if (e.hasMoreElements()) {
      out.println("[Servlet parameters (Single Value style):]");
      while (e.hasMoreElements()) {
        String name = (String) e.nextElement();
        out.println(" " + name + " = " + req.getParameter(name));
      }
      out.println("");
    }

    e = req.getParameterNames();
    if (e.hasMoreElements()) {
      out.println("[ Servlet parameters (Multiple Value style): ]");
      while (e.hasMoreElements()) {
        String name = (String) e.nextElement();
        String vals[] = (String[]) req.getParameterValues(name);
        if (vals != null) {
          out.print(name);
          out.println(vals[0]);
          for (int i = 1; i < vals.length; i++)
            out.println("           " + vals[i]);
        }
      }
      out.println("");
    }

    String cipherSuite = (String) req.getAttribute("javax.net.ssl.cipher_suite");

    if (cipherSuite != null) {
      X509Certificate certChain[] = (X509Certificate[]) req.getAttribute("javax.net.ssl.peer_certificates");

      out.println("[HTTPS Information:]");
      out.println("Cipher Suite:  " + cipherSuite);

      if (certChain != null) {
        for (int i = 0; i < certChain.length; i++) {
          out.println("client cert chain [" + i + "] = " + certChain[i].toString());
        }
      }
      out.println("");
      out.println("");
    }
    return result;
  }

  private void print(PrintWriter out, String name, String value) {
    out.print(" " + name + ": ");
    out.println(value == null ? "&lt;none&gt;" : value);
  }

  private void print(PrintWriter out, String name, int value) {
    out.print(" " + name + ": ");
    if (value == -1) {
      out.println("&lt;none&gt;");
    } else {
      out.println(value);
    }
  }

  public void destroy() {
    // TODO Auto-generated method stub

  }

}
