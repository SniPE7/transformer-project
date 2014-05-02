package com.ibm.siam.am.idp.mcc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpPostHelper {
    
    private static int ConnectTimeOut = 2000;//ms
    private static int ReadTimeOut = 2000;//ms

    public static String post(String wsdlUrl, String content) throws IOException {
        return post(wsdlUrl, content, null);
    }
    
    public static String post(String wsdlUrl, String content, String soapActionName) throws IOException {
        StringBuffer result = new StringBuffer();
        
        OutputStreamWriter out = null;
        InputStream responseStream = null;
        BufferedReader responseReader = null;
        
        BufferedWriter buffWriter = null;
        
        try {
            URL url = new URL(wsdlUrl);
    
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/xml");
            
            connection.setConnectTimeout(ConnectTimeOut);
            connection.setReadTimeout(ReadTimeOut);
            
            if(soapActionName!=null && !"".equals(soapActionName.trim())) {
                connection.setRequestProperty("SOAPAction", soapActionName);
            }
            
            //connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
            //connection.setRequestProperty("Host", " w3.ospf.cn.ibm.com:10000");
            //connection.setRequestProperty("Content-Length", "242");
    
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.write(content);
            out.flush();
            
            closeStream(out);
    
            // response
            responseStream = connection.getInputStream();
            responseReader = new BufferedReader(new InputStreamReader(responseStream));
            
            String inputLine = "";
            
            while ((inputLine = responseReader.readLine()) != null) {
                //System.out.println(inputLine);
                result.append(inputLine);
            }
            
            /*
             * while ((sCurrentLine = l_reader.readLine()) != null) { sTotalString += sCurrentLine + "\r\n"; //parseXML }
             */
    
            /*buffWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:/result.xml")));// 将结果存放的位置
            while ((inputLine = responseReader.readLine()) != null) {
                //System.out.println(inputLine);
                
                buffWriter.write(inputLine);
                buffWriter.newLine();
            }*/
            
        } finally {
            closeStream(out);
            closeStream(responseStream);
            closeStream(responseReader);
            closeStream(buffWriter);
        }

        return result.toString();
    }
    
    public static void closeStream(Closeable obj) {
        if(obj!=null) {
            try {
                obj.close();
            } catch (IOException e) {
            }
            obj = null;
        }
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
        
        String result = post(url, content.toString());
        System.out.println(result);
      //  System.out.println( result = getMatchCode(result, MATCHECODE_PATTERN) );
    }
}
