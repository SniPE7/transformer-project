/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author zhaodonglu
 * 
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /*
   * (non-Javadoc)
   * 
   * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
   */
  @Override
  public void serialize(Date date, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
    String formattedDate = dateFormat.format(date);
    gen.writeString(formattedDate);
  }

}
