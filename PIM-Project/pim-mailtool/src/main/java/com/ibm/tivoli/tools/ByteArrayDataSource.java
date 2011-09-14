package com.ibm.tivoli.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;

/**
 * This class implements a typed DataSource from :
 *  an InputStream
 *  a byte array
 *  a String
 *
 * @version 3.0, 01/28/99
 * @author  Zhao Donglu
 */

/***********************************************************************************************
 * @version 3.0, 01/28/99
 * @author Zhao Donglu
 ************************************************************************************************/

public class ByteArrayDataSource implements DataSource {
  private byte[] data; // data
  private String type; // content-type

  /* Create a datasource from an input stream */
  public ByteArrayDataSource(InputStream is, String type) {
    this.type = type;
    try {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      int ch;

      while ((ch = is.read()) != -1)
        // XXX : must be made more efficient by
        // doing buffered reads, rather than one byte reads
        os.write(ch);
      data = os.toByteArray();

    } catch (IOException ioex) {
    }
  }

  /* Create a datasource from a byte array */
  public ByteArrayDataSource(byte[] data, String type) {
    this.data = data;
    this.type = type;
  }

  /* Create a datasource from a String */
  public ByteArrayDataSource(String data, String type, String charset) {
    try {
      // Assumption that the string contains only ascii
      // characters ! Else just pass in a charset into this
      // constructor and use it in getBytes()
      // this.data = data.getBytes("iso-8859-1");
      this.data = data.getBytes(charset);
    } catch (UnsupportedEncodingException uex) {
    }

    this.type = type;
  }

  public InputStream getInputStream() throws IOException {
    if (data == null)
      throw new IOException("no data");
    return new ByteArrayInputStream(data);
  }

  public OutputStream getOutputStream() throws IOException {
    throw new IOException("cannot do this");
  }

  public String getContentType() {
    return type;
  }

  public String getName() {
    return "dummy";
  }
}
