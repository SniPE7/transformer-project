package com.ibm.siam.am.idp.mcc;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchCodeClient {
    
    private String url = "http://192.168.134.1:8000/BasicHttpBinding_ICodeTrans?WSDL";
    
    //忽略大小写
    private static final Pattern MATCHECODE_PATTERN = Pattern.compile("<((?i).*AskMatch2Result)>(.*)</((?i).*AskMatch2Result>)");
    
    public MatchCodeClient(String url) {
        this.url = url;
    }
    
    //必须()
    private static String getMatchCode(String name, Pattern pattern) {
        String result = "";
        
        Matcher m = pattern.matcher(name);
        while (m.find()) {
            result = m.group(2);
            break;
        }
        
        return result;
    }
    
    public String getMatchCode(String code) throws Exception {

        StringBuffer content = new StringBuffer();
        content.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        content.append("<S:Body>");
        content.append(" <AskMatch2 xmlns=\"http://Hirsch.Match.Code\" xmlns:ns2=\"http://schemas.microsoft.com/2003/10/Serialization/\">");
        content.append("    <n1>" + code + "</n1>");
        content.append("    </AskMatch2>");
        content.append(" </S:Body>");
        content.append(" </S:Envelope>");
        
        String result = HttpPostHelper.post(this.url, content.toString(), "http://Hirsch.Match.Code/ICodeTrans/AskMatch2");
        //System.out.println( result = getMatchCode(result, MATCHECODE_PATTERN) );
        
        result = getMatchCode(result, MATCHECODE_PATTERN);
        
        if(result==null || "".equals(result.trim())) {
            throw new Exception("没有找到对应的matchcode");
        }
        
        return result;
    }

    //<hir:AskMatch2Result>53168472</hir:AskMatch2Result>
    public static void main(String[] args) throws IOException {
        
        String url = "http://192.168.134.1:8000/BasicHttpBinding_ICodeTrans?WSDL";
        String code = "9E70DFCA";

        StringBuffer content = new StringBuffer();
        content.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        content.append("<S:Body>");
        content.append(" <AskMatch2 xmlns=\"http://Hirsch.Match.Code\" xmlns:ns2=\"http://schemas.microsoft.com/2003/10/Serialization/\">");
        content.append("    <n1>" + code + "</n1>");
        content.append("    </AskMatch2>");
        content.append(" </S:Body>");
        content.append(" </S:Envelope>");
        
        String result = HttpPostHelper.post(url, content.toString());
        System.out.println(result);
        System.out.println( result = getMatchCode(result, MATCHECODE_PATTERN) );
    }
}
