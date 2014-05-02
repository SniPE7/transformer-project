package com.ibm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ļ�ϵͳ���໷����ȡ�ļ���������
 * 
 * <p>�̳���java.io.InputStream��
 * 
 * @author zhaodonglu
 * 
 * @see java.io.InputStream
 */
public class FileOrClasspathInputStream extends InputStream {

  private static Log log = LogFactory.getLog(FileOrClasspathInputStream.class);

  /** �ļ���������װʵ�� */
  private InputStream wrapperIn = null;

  /**
   * 
   * ���췽����
   * 
   * <p>ʵ�����ļ��������
   * 
   * @param path
   *          Like "classpath:/config/sp.p12", "/confg/sp.p12" or
   *          "file:/confg/sp.p12"
   * @throws IOException
   */
  public FileOrClasspathInputStream(String path) throws IOException {
    if (path == null) {
      throw new IOException("Path is null");
    }
    if (path.toLowerCase().startsWith("classpath:")) {
      String classpath = path.substring("classpath:".length());
      log.debug(String.format("reading from classpath: [%s], orginal path: [%s]", classpath, path));
      this.wrapperIn = this.getClass().getResourceAsStream(classpath);
    } else {
      String filePath = path;
      if (path.toLowerCase().startsWith("file:")) {
        filePath = path.substring("file:".length());
      }
      File file = new File(filePath);
      log.debug(String.format("reading from file system, file path: [%s], orginal path: [%s]", file.getCanonicalPath(),
          path));
      this.wrapperIn = new FileInputStream(file);
    }
    if (this.wrapperIn == null) {
      throw new IOException(String.format("failure to read data from path: [%s]", path));
    }
  }

  /**
   * ���������ж�ȡ���ݵ���һ���ֽڡ����� 0 �� 255 ��Χ�ڵ� int �ֽ�ֵ��
   * <p>�����Ϊ�Ѿ�������ĩβ��û�п��õ��ֽڣ��򷵻�ֵ-1��
   * <p>���������ݿ��á���⵽��ĩβ�����׳��쳣ǰ���˷���һֱ������
   * 
   * @return ��һ�������ֽڣ������������ĩβ���򷵻� -1��
   * @exception IOException
   * 
   * @see java.io.InputStream#read()
   */
  @Override
  public int read() throws IOException {
    return wrapperIn.read();
  }

  /**
   * �رմ����������ͷ����������������ϵͳ��Դ��
   * 
   * @exception IOException
   * 
   * @see java.io.InputStream#close()
   */
  public void close() throws IOException {
    this.wrapperIn.close();
  }

}
