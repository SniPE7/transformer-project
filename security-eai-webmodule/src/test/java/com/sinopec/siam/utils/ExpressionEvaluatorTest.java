package com.sinopec.siam.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import com.ibm.util.ExpressionEvaluator;
import com.ibm.util.VariableResolver;

public class ExpressionEvaluatorTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testEvaluate() throws Exception {
    MockHttpServletRequest context = new MockHttpServletRequest();
    context.setParameter("cn", "abcdef");

    ExpressionEvaluator<HttpServletRequest> evaluator = new ExpressionEvaluator<HttpServletRequest>(new VariableResolver<HttpServletRequest>() {

      public String resolve(HttpServletRequest context, String name) {
        return context.getParameter(name);
      }
    });
    
    String result = evaluator.evaluate("(&(sprolelist={cn})(objectclass=inetorgperson))", context);
    assertEquals("(&(sprolelist=abcdef)(objectclass=inetorgperson))", result);
  }
  
  public void testTimeFormat() throws Exception {
    String browserTZ = "+0900";
    String inputDate = "2012-01-01";
    TimeZone current = TimeZone.getTimeZone(browserTZ);

    DateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd");
    Date d = dfInput.parse(inputDate);
    Calendar c = Calendar.getInstance(current);
    c.setTime(d);
    
    DateFormat dfOutput = new SimpleDateFormat("yyyyMMddHHmmss.ZZZ");
    dfOutput.setTimeZone(TimeZone.getTimeZone("GMT"));
    // 20120101000000Z
    Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    c2.setTimeInMillis(c.getTimeInMillis());
    String output = dfOutput.format(c2.getTime());
    assertEquals("", output);
  }

}
