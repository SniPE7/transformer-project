package com.ibm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 从文件系统或类环境读取文件输入流。
 * 
 * <p>继承自java.io.InputStream。
 * 
 * @author zhaodonglu
 * 
 * @see java.io.InputStream
 */
public class FileOrClasspathInputStream extends InputStream {

  private static Log log = LogFactory.getLog(FileOrClasspathInputStream.class);

  /** 文件输入流封装实例 */
  private InputStream wrapperIn = null;

  /**
   * 
   * 构造方法。
   * 
   * <p>实例化文件输入对象。
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
   * 从输入流中读取数据的下一个字节。返回 0 到 255 范围内的 int 字节值。
   * <p>如果因为已经到达流末尾而没有可用的字节，则返回值-1。
   * <p>在输入数据可用、检测到流末尾或者抛出异常前，此方法一直阻塞。
   * 
   * @return 下一个数据字节；如果到达流的末尾，则返回 -1。
   * @exception IOException
   * 
   * @see java.io.InputStream#read()
   */
  @Override
  public int read() throws IOException {
    return wrapperIn.read();
  }

  /**
   * 关闭此输入流并释放与该流关联的所有系统资源。
   * 
   * @exception IOException
   * 
   * @see java.io.InputStream#close()
   */
  public void close() throws IOException {
    this.wrapperIn.close();
  }

}
