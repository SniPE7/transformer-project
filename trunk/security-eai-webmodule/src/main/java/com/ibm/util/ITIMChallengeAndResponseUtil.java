package com.ibm.util;

/**
 * Description:
 * @author hu
 * @since 2012-9-27 上午5:20:54
 */

public class ITIMChallengeAndResponseUtil {
  
  public static final ChallengeAndResposneUtil challengeAndResposneUtil;
  static{
    challengeAndResposneUtil = new ChallengeAndResposneUtil();
  }
  /***
   * 生成找回密码问题和答案
   * @param question 密码找回问题
   * @param answer 密码找回答案
   * @return xml格式的字符串
   */
  public static String generateChallengeAndResponse(String question, String answer){
    if(question == null || (question.length() == 0) 
        || answer == null || (answer.length() == 0)){
      return null;
    }
    String response = new String(challengeAndResposneUtil.hashForUpdate(answer));
    StringBuffer challengeAndResponse = new StringBuffer();
    challengeAndResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    challengeAndResponse.append("<ChallengeResponseSet>");
    challengeAndResponse.append("<ChallengeResponse Challenge=\"");
    challengeAndResponse.append(question);
    challengeAndResponse.append("\" Response=\"");
    challengeAndResponse.append(response);
    challengeAndResponse.append("\"/>");
    challengeAndResponse.append("</ChallengeResponseSet>");
    return challengeAndResponse.toString();
  }
}
