<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IdP Session Tool">
<TITLE>IdP Session Tool</TITLE>
</HEAD>

<BODY>
	<H1>Session Object List JSP</H1>

	This JSP will dump information about the current HTTPSession.
	<br>
	<br>

	<%@ page import="java.io.*,java.util.*,javax.servlet.*" session="false"%>
	<%!public void dumpSession(HttpServletRequest request, JspWriter out) throws IOException {
    HttpSession session = request.getSession(false);
    Object ro = null;

    out.println("Session ID from session.getID : " + session.getId() + "<br>");

    out.println("Session ID from getHeader: " + request.getHeader("Cookie") + "<br>");

    Enumeration names = session.getAttributeNames();
    if (names.hasMoreElements()) {
      int totalSize = 0;

      out.println("<h3>Session Objects:</h3>");
      out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
      out.println("<tr><td>Name</td><td>Object.toString()</td>");
      out.println("<td>Raw Bytes</td><td>Size (bytes)</td></tr>");
      while (names.hasMoreElements()) {
        String name = (String) names.nextElement();

        Object sesobj = session.getAttribute(name);

        ObjectOutputStream oos = null;
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        try {

          oos = new ObjectOutputStream(bstream);
          oos.writeObject(sesobj);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (oos != null) {
            try {
              oos.flush();
            } catch (IOException ioe) {
            }
            try {
              oos.close();
            } catch (IOException ioe) {
            }
          }
        }

        ObjectInputStream ois = null;
        ro = null;

        try {
          ois = new ObjectInputStream(new ByteArrayInputStream(bstream.toByteArray()));
          ro = ois.readObject();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (ois != null) {
            try {
              ois.close();
            } catch (IOException ioe) {
            }
          }
        }

        totalSize += bstream.size();
        out.println("<tr><td>" + name + "</td><td>" + session.getAttribute(name) + "</td><td>" + ro + "</td>");
        out.println("<td>" + bstream.size() + " bytes </td></tr>");
      }
      out.println("</table><BR>");
      out.println("Total Bytes: " + totalSize + "<br><br>");
    } else {
      out.println("No objects in session");
    }
  }%>
	<%
	  response.setHeader("Pragma", "No-cache");
	  response.setHeader("Cache-Control", "no-cache");
	  response.setDateHeader("Expires", 0);

	  HttpSession session = request.getSession(false);
	  if (session == null) {
	    out.println("No session");
	  } else {
	    dumpSession(request, out);
	  }
	%>
</BODY>