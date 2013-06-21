package group.tivoli.security.eai.web.themes;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 主题皮肤池类
 * @author ZhouTengfei
 * @since 2012-11-3 下午3:31:31
 */

public class ThemesPool {
  private Map<String, String> themesMap = new HashMap<String, String>();
  private final static String DEFAULTTHEMES = "default";
  private final static String basePath = "/themes/";
  private final static String baseName = "/index.jsp";
  private static String realPath;
  private boolean initialized = false;
  
  private String themes;
  
  /**
   * @return the themes
   */
  public String getThemes() {
    return themes;
  }

  /**
   * @param themes the themes to set
   */
  public void setThemes(String themes) {
    this.themes = themes;
  }

  /**
   * 初始化。
   */
  public void initialize() {
    if (initialized) {
      return;
    }
    realPath = getWebPath();
    if(StringUtils.isNotEmpty(themes)){
      for(String themesName : themes.split(",")){
        if(StringUtils.isNotEmpty(themesName)){
          StringBuffer themesPath = new StringBuffer(basePath).append(themesName).append(baseName);
          File themesFile = new File(realPath + themesPath);
          if(themesFile.exists()){
            themesMap.put(themesName, themesPath.toString());
          }
        }
      }
    }
    initialized = true;
  }

  /**
   * @return the themesMap
   */
  public Map<String, String> getThemesMap() {
    return themesMap;
  }

  public String getThemesByName(String themesName){
    if(!themesMap.isEmpty() && themesMap.containsKey(themesName)){
//      return themesMap.get(themesName);
      return themesName;
    }else{
      return DEFAULTTHEMES;
    }
  }
  
  private String getWebPath(){
    URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
    String path = url.toString();
    int index = path.indexOf("WEB-INF");
    
    path = path.substring(0, index);
    if(path.startsWith("file")){//当class文件在classes目录中时，此时返回file:/D:/...这样的路径
      path = path.substring(6);
    }
    try {
      path =  URLDecoder.decode(path, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return path;
  }
}

