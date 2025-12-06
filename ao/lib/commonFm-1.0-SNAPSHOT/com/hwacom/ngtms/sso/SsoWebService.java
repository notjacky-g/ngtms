package com.hwacom.ngtms.sso;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace="http://sso.ngtms.hwacom.com/", name="SsoWebService")
@XmlSeeAlso({ObjectFactory.class})
public abstract interface SsoWebService
{
  @WebMethod
  @RequestWrapper(localName="findUsersBySpCode", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.FindUsersBySpCode")
  @ResponseWrapper(localName="findUsersBySpCodeResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.FindUsersBySpCodeResponse")
  @WebResult(name="userValue", targetNamespace="")
  public abstract List<SsoWsUser> findUsersBySpCode(@WebParam(name="spCode", targetNamespace="") String paramString)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="getSsoWsStatus", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.GetSsoWsStatus")
  @ResponseWrapper(localName="getSsoWsStatusResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.GetSsoWsStatusResponse")
  @WebResult(name="wsStatus", targetNamespace="")
  public abstract String getSsoWsStatus()
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="login", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.Login")
  @ResponseWrapper(localName="loginResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.LoginResponse")
  @WebResult(name="authenticationValue", targetNamespace="")
  public abstract SsoWsAuthentication login(@WebParam(name="spCode", targetNamespace="") String paramString1, @WebParam(name="userAccount", targetNamespace="") String paramString2, @WebParam(name="userPassword", targetNamespace="") String paramString3)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="findRolesBySpCode", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.FindRolesBySpCode")
  @ResponseWrapper(localName="findRolesBySpCodeResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.FindRolesBySpCodeResponse")
  @WebResult(name="roleValue", targetNamespace="")
  public abstract List<SsoWsRole> findRolesBySpCode(@WebParam(name="spCode", targetNamespace="") String paramString)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="logout", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.Logout")
  @ResponseWrapper(localName="logoutResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.LogoutResponse")
  @WebResult(name="logoutValue", targetNamespace="")
  public abstract boolean logout()
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="updateAuthentication", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.UpdateAuthentication")
  @ResponseWrapper(localName="updateAuthenticationResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.UpdateAuthenticationResponse")
  @WebResult(name="updateValue", targetNamespace="")
  public abstract boolean updateAuthentication(@WebParam(name="clientAddr", targetNamespace="") String paramString1, @WebParam(name="userAccount", targetNamespace="") String paramString2, @WebParam(name="userPassword", targetNamespace="") String paramString3, @WebParam(name="sessionId", targetNamespace="") String paramString4)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="resetPwd", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.ResetPwd")
  @ResponseWrapper(localName="resetPwdResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.ResetPwdResponse")
  @WebResult(name="resetResult", targetNamespace="")
  public abstract SsoWsResetPwd resetPwd(@WebParam(name="userAccount", targetNamespace="") String paramString1, @WebParam(name="userName", targetNamespace="") String paramString2, @WebParam(name="userEmail", targetNamespace="") String paramString3)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="isCurPwdValid", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.IsCurPwdValid")
  @ResponseWrapper(localName="isCurPwdValidResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.IsCurPwdValidResponse")
  @WebResult(name="validResult", targetNamespace="")
  public abstract SsoWsIsCurPwdValid isCurPwdValid(@WebParam(name="userAccount", targetNamespace="") String paramString1, @WebParam(name="curPwd", targetNamespace="") String paramString2)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="modifyPwd", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.ModifyPwd")
  @ResponseWrapper(localName="modifyPwdResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.ModifyPwdResponse")
  @WebResult(name="modifyResult", targetNamespace="")
  public abstract SsoWsModifyPwd modifyPwd(@WebParam(name="userAccount", targetNamespace="") String paramString1, @WebParam(name="curPwd", targetNamespace="") String paramString2, @WebParam(name="newPwd", targetNamespace="") String paramString3)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="removeAuthentication", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.RemoveAuthentication")
  @ResponseWrapper(localName="removeAuthenticationResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.RemoveAuthenticationResponse")
  @WebResult(name="removeValue", targetNamespace="")
  public abstract boolean removeAuthentication(@WebParam(name="clientAddr", targetNamespace="") String paramString)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="checkAuthentication", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.CheckAuthentication")
  @ResponseWrapper(localName="checkAuthenticationResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.CheckAuthenticationResponse")
  @WebResult(name="authenticationValue", targetNamespace="")
  public abstract SsoWsAuthentication checkAuthentication()
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="checkAuthenticationWithClientAddress", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.CheckAuthenticationWithClientAddress")
  @ResponseWrapper(localName="checkAuthenticationWithClientAddressResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.CheckAuthenticationWithClientAddressResponse")
  @WebResult(name="authenticationValue", targetNamespace="")
  public abstract SsoWsAuthentication checkAuthenticationWithClientAddress(@WebParam(name="clientAddr", targetNamespace="") String paramString)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="checkAuthenticationWithIdAndPwd", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.CheckAuthenticationWithIdAndPwd")
  @ResponseWrapper(localName="checkAuthenticationWithIdAndPwdResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.CheckAuthenticationWithIdAndPwdResponse")
  @WebResult(name="userValue", targetNamespace="")
  public abstract SsoWsUser checkAuthenticationWithIdAndPwd(@WebParam(name="spCode", targetNamespace="") String paramString1, @WebParam(name="userAccount", targetNamespace="") String paramString2, @WebParam(name="userPassword", targetNamespace="") String paramString3)
    throws SsoWebServiceFault;
  
  @WebMethod
  @RequestWrapper(localName="findAllUnits", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.FindAllUnits")
  @ResponseWrapper(localName="findAllUnitsResponse", targetNamespace="http://sso.ngtms.hwacom.com/", className="com.hwacom.ngtms.sso.FindAllUnitsResponse")
  @WebResult(name="unitValue", targetNamespace="")
  public abstract List<SsoJobUnitDTO> findAllUnits()
    throws SsoWebServiceFault;
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\SsoWebService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */