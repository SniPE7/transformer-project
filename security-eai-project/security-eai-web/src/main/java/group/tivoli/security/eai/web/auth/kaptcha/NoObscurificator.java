package group.tivoli.security.eai.web.auth.kaptcha;

import java.awt.image.BufferedImage;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

/**
 * 生成图片验证码
 * 
 * @author zhangxianwen
 * @since 2012-6-26 下午2:05:04
 */

public class NoObscurificator extends Configurable implements GimpyEngine {

  /** {@inheritDoc} */
  public BufferedImage getDistortedImage(BufferedImage baseImage) {
    return baseImage;
  }

}
