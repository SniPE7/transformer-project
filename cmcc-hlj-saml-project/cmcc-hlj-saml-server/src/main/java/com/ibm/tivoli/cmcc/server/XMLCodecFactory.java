package com.ibm.tivoli.cmcc.server;

import java.nio.charset.Charset;

import org.apache.mina.common.BufferDataException;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

public class XMLCodecFactory implements ProtocolCodecFactory {
  private final TextLineEncoder encoder;

  private final TextLineDecoder decoder;

  /**
   * Creates a new instance with the current default {@link Charset}.
   */
  public XMLCodecFactory() {
      this(Charset.defaultCharset());
  }

  /**
   * Creates a new instance with the specified {@link Charset}.
   */
  public XMLCodecFactory(Charset charset) {
      encoder = new TextLineEncoder(charset, LineDelimiter.UNIX);
      decoder = new TextLineDecoder(charset, new LineDelimiter(">"));
  }

  public ProtocolEncoder getEncoder() {
      return encoder;
  }

  public ProtocolDecoder getDecoder() {
      return decoder;
  }

  /**
   * Returns the allowed maximum size of the encoded line.
   * If the size of the encoded line exceeds this value, the encoder
   * will throw a {@link IllegalArgumentException}.  The default value
   * is {@link Integer#MAX_VALUE}.
   * <p>
   * This method does the same job with {@link TextLineEncoder#getMaxLineLength()}.
   */
  public int getEncoderMaxLineLength() {
      return encoder.getMaxLineLength();
  }

  /**
   * Sets the allowed maximum size of the encoded line.
   * If the size of the encoded line exceeds this value, the encoder
   * will throw a {@link IllegalArgumentException}.  The default value
   * is {@link Integer#MAX_VALUE}.
   * <p>
   * This method does the same job with {@link TextLineEncoder#setMaxLineLength(int)}.
   */
  public void setEncoderMaxLineLength(int maxLineLength) {
      encoder.setMaxLineLength(maxLineLength);
  }

  /**
   * Returns the allowed maximum size of the line to be decoded.
   * If the size of the line to be decoded exceeds this value, the
   * decoder will throw a {@link BufferDataException}.  The default
   * value is <tt>1024</tt> (1KB).
   * <p>
   * This method does the same job with {@link TextLineDecoder#getMaxLineLength()}.
   */
  public int getDecoderMaxLineLength() {
      return decoder.getMaxLineLength();
  }

  /**
   * Sets the allowed maximum size of the line to be decoded.
   * If the size of the line to be decoded exceeds this value, the
   * decoder will throw a {@link BufferDataException}.  The default
   * value is <tt>1024</tt> (1KB).
   * <p>
   * This method does the same job with {@link TextLineDecoder#setMaxLineLength(int)}.
   */
  public void setDecoderMaxLineLength(int maxLineLength) {
      decoder.setMaxLineLength(maxLineLength);
  }
}
