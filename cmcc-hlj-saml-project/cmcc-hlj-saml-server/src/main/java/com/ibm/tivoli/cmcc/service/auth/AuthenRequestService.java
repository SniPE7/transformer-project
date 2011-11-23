package com.ibm.tivoli.cmcc.service.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.request.AuthenRequest;

public interface AuthenRequestService {

  /**
   * 验证服务请求
   * @param request
   * @throws Exception
   */
  public abstract void validate(HttpServletRequest request) throws Exception;

  /**
   * 解析SAML AuthnRequest XML
   * @param request
   * @return
   * @throws IOException
   * @throws SAXException
   */
  public abstract AuthenRequest parseRequest(HttpServletRequest request) throws IOException, SAXException;

  /**
   * 提取RelayState
   * @param request
   * @return
   */
  public abstract String getRelayState(HttpServletRequest request);

  /**
   * 检查是否已经在一级或省级登录
   * @param request
   * @return
   */
  public abstract boolean isAuthenticated(HttpServletRequest request);

  /**
   * 检返回其合法的artifactID
   * @param request
   * @return
   */
  public abstract String getCurrentArtifactID(HttpServletRequest request);

  /**
   * 为登录后的用户生成ArtifactID， 并生成到Cookies
   * @param request
   * @param response
   * @param username
   * @return
   * @throws Exception
   */
  public abstract String generateAndSaveArtifactID(HttpServletRequest request, HttpServletResponse response, String username) throws Exception;

}