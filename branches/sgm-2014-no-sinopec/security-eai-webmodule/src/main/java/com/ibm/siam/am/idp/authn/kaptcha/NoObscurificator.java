package com.ibm.siam.am.idp.authn.kaptcha;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

/**
 * 生成图片验证码
 * 
 * @author zhangxianwen
 * @since 2012-6-26 下午2:05:04
 */

public class NoObscurificator extends Configurable implements GimpyEngine {
  
  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(NoObscurificator.class);

  /** {@inheritDoc} */
  public BufferedImage getDistortedImage(BufferedImage baseImage) {
    if(log.isDebugEnabled()){
      log.debug("Get distorted image code");
    }
    return baseImage;
  }

}
