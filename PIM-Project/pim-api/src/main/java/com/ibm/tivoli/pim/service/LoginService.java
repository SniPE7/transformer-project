/**
 * 
 */
package com.ibm.tivoli.pim.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.ibm.tivoli.pim.entity.LoginResponse;

/**
 * @author Administrator
 *
 */
@WebService(targetNamespace="http://login.service.pim.tivoli.ibm.com/")
@Path(value = "/loginservice")  
public interface LoginService {
  @WebMethod(operationName = "login")
  @WebResult(name="loginResponse")
  @GET
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  @Path(value = "/login/{username}/result")  
  public abstract LoginResponse login(@WebParam(name = "username") @PathParam("username")String username, 
                                      @WebParam(name = "password") @QueryParam("password")String password);

  @WebMethod(operationName = "check")
  @WebResult(name="loginResponse")
  @GET  
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  @Path(value = "/status")  
  public abstract LoginResponse getCurrentUser();
  
  @WebMethod(operationName = "logout")
  @WebResult(name="logoutResponse")
  @GET  
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  @Path(value = "/logout")  
  public abstract Response logout();
}
