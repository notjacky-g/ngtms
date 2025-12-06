/*     */ package com.hwacom.ngtms.sso;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  27 */   private static final QName _CheckAuthentication_QNAME = new QName("http://sso.ngtms.hwacom.com/", "checkAuthentication");
/*  28 */   private static final QName _CheckAuthenticationResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "checkAuthenticationResponse");
/*  29 */   private static final QName _CheckAuthenticationWithClientAddress_QNAME = new QName("http://sso.ngtms.hwacom.com/", "checkAuthenticationWithClientAddress");
/*  30 */   private static final QName _CheckAuthenticationWithClientAddressResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "checkAuthenticationWithClientAddressResponse");
/*  31 */   private static final QName _CheckAuthenticationWithIdAndPwd_QNAME = new QName("http://sso.ngtms.hwacom.com/", "checkAuthenticationWithIdAndPwd");
/*  32 */   private static final QName _CheckAuthenticationWithIdAndPwdResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "checkAuthenticationWithIdAndPwdResponse");
/*  33 */   private static final QName _FindAllUnits_QNAME = new QName("http://sso.ngtms.hwacom.com/", "findAllUnits");
/*  34 */   private static final QName _FindAllUnitsResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "findAllUnitsResponse");
/*  35 */   private static final QName _FindRolesBySpCode_QNAME = new QName("http://sso.ngtms.hwacom.com/", "findRolesBySpCode");
/*  36 */   private static final QName _FindRolesBySpCodeResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "findRolesBySpCodeResponse");
/*  37 */   private static final QName _FindUsersBySpCode_QNAME = new QName("http://sso.ngtms.hwacom.com/", "findUsersBySpCode");
/*  38 */   private static final QName _FindUsersBySpCodeResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "findUsersBySpCodeResponse");
/*  39 */   private static final QName _GetSsoWsStatus_QNAME = new QName("http://sso.ngtms.hwacom.com/", "getSsoWsStatus");
/*  40 */   private static final QName _GetSsoWsStatusResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "getSsoWsStatusResponse");
/*  41 */   private static final QName _IsCurPwdValid_QNAME = new QName("http://sso.ngtms.hwacom.com/", "isCurPwdValid");
/*  42 */   private static final QName _IsCurPwdValidResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "isCurPwdValidResponse");
/*  43 */   private static final QName _Login_QNAME = new QName("http://sso.ngtms.hwacom.com/", "login");
/*  44 */   private static final QName _LoginResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "loginResponse");
/*  45 */   private static final QName _Logout_QNAME = new QName("http://sso.ngtms.hwacom.com/", "logout");
/*  46 */   private static final QName _LogoutResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "logoutResponse");
/*  47 */   private static final QName _ModifyPwd_QNAME = new QName("http://sso.ngtms.hwacom.com/", "modifyPwd");
/*  48 */   private static final QName _ModifyPwdResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "modifyPwdResponse");
/*  49 */   private static final QName _RemoveAuthentication_QNAME = new QName("http://sso.ngtms.hwacom.com/", "removeAuthentication");
/*  50 */   private static final QName _RemoveAuthenticationResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "removeAuthenticationResponse");
/*  51 */   private static final QName _ResetPwd_QNAME = new QName("http://sso.ngtms.hwacom.com/", "resetPwd");
/*  52 */   private static final QName _ResetPwdResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "resetPwdResponse");
/*  53 */   private static final QName _UpdateAuthentication_QNAME = new QName("http://sso.ngtms.hwacom.com/", "updateAuthentication");
/*  54 */   private static final QName _UpdateAuthenticationResponse_QNAME = new QName("http://sso.ngtms.hwacom.com/", "updateAuthenticationResponse");
/*  55 */   private static final QName _SsoWebServiceFault_QNAME = new QName("http://sso.ngtms.hwacom.com/", "SsoWebServiceFault");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CheckAuthentication createCheckAuthentication()
/*     */   {
/*  69 */     return new CheckAuthentication();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CheckAuthenticationResponse createCheckAuthenticationResponse()
/*     */   {
/*  77 */     return new CheckAuthenticationResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CheckAuthenticationWithClientAddress createCheckAuthenticationWithClientAddress()
/*     */   {
/*  85 */     return new CheckAuthenticationWithClientAddress();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CheckAuthenticationWithClientAddressResponse createCheckAuthenticationWithClientAddressResponse()
/*     */   {
/*  93 */     return new CheckAuthenticationWithClientAddressResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CheckAuthenticationWithIdAndPwd createCheckAuthenticationWithIdAndPwd()
/*     */   {
/* 101 */     return new CheckAuthenticationWithIdAndPwd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CheckAuthenticationWithIdAndPwdResponse createCheckAuthenticationWithIdAndPwdResponse()
/*     */   {
/* 109 */     return new CheckAuthenticationWithIdAndPwdResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FindAllUnits createFindAllUnits()
/*     */   {
/* 117 */     return new FindAllUnits();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FindAllUnitsResponse createFindAllUnitsResponse()
/*     */   {
/* 125 */     return new FindAllUnitsResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FindRolesBySpCode createFindRolesBySpCode()
/*     */   {
/* 133 */     return new FindRolesBySpCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FindRolesBySpCodeResponse createFindRolesBySpCodeResponse()
/*     */   {
/* 141 */     return new FindRolesBySpCodeResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FindUsersBySpCode createFindUsersBySpCode()
/*     */   {
/* 149 */     return new FindUsersBySpCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FindUsersBySpCodeResponse createFindUsersBySpCodeResponse()
/*     */   {
/* 157 */     return new FindUsersBySpCodeResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public GetSsoWsStatus createGetSsoWsStatus()
/*     */   {
/* 165 */     return new GetSsoWsStatus();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public GetSsoWsStatusResponse createGetSsoWsStatusResponse()
/*     */   {
/* 173 */     return new GetSsoWsStatusResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IsCurPwdValid createIsCurPwdValid()
/*     */   {
/* 181 */     return new IsCurPwdValid();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IsCurPwdValidResponse createIsCurPwdValidResponse()
/*     */   {
/* 189 */     return new IsCurPwdValidResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Login createLogin()
/*     */   {
/* 197 */     return new Login();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoginResponse createLoginResponse()
/*     */   {
/* 205 */     return new LoginResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Logout createLogout()
/*     */   {
/* 213 */     return new Logout();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LogoutResponse createLogoutResponse()
/*     */   {
/* 221 */     return new LogoutResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ModifyPwd createModifyPwd()
/*     */   {
/* 229 */     return new ModifyPwd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ModifyPwdResponse createModifyPwdResponse()
/*     */   {
/* 237 */     return new ModifyPwdResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public RemoveAuthentication createRemoveAuthentication()
/*     */   {
/* 245 */     return new RemoveAuthentication();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public RemoveAuthenticationResponse createRemoveAuthenticationResponse()
/*     */   {
/* 253 */     return new RemoveAuthenticationResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ResetPwd createResetPwd()
/*     */   {
/* 261 */     return new ResetPwd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ResetPwdResponse createResetPwdResponse()
/*     */   {
/* 269 */     return new ResetPwdResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public UpdateAuthentication createUpdateAuthentication()
/*     */   {
/* 277 */     return new UpdateAuthentication();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public UpdateAuthenticationResponse createUpdateAuthenticationResponse()
/*     */   {
/* 285 */     return new UpdateAuthenticationResponse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoWsUser createSsoWsUser()
/*     */   {
/* 293 */     return new SsoWsUser();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoWsUserProperty createSsoWsUserProperty()
/*     */   {
/* 301 */     return new SsoWsUserProperty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoWsAuthentication createSsoWsAuthentication()
/*     */   {
/* 309 */     return new SsoWsAuthentication();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoWsRole createSsoWsRole()
/*     */   {
/* 317 */     return new SsoWsRole();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoWsResetPwd createSsoWsResetPwd()
/*     */   {
/* 325 */     return new SsoWsResetPwd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoWsIsCurPwdValid createSsoWsIsCurPwdValid()
/*     */   {
/* 333 */     return new SsoWsIsCurPwdValid();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoWsModifyPwd createSsoWsModifyPwd()
/*     */   {
/* 341 */     return new SsoWsModifyPwd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SsoJobUnitDTO createSsoJobUnitDTO()
/*     */   {
/* 349 */     return new SsoJobUnitDTO();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="checkAuthentication")
/*     */   public JAXBElement<CheckAuthentication> createCheckAuthentication(CheckAuthentication value)
/*     */   {
/* 358 */     return new JAXBElement(_CheckAuthentication_QNAME, CheckAuthentication.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="checkAuthenticationResponse")
/*     */   public JAXBElement<CheckAuthenticationResponse> createCheckAuthenticationResponse(CheckAuthenticationResponse value)
/*     */   {
/* 367 */     return new JAXBElement(_CheckAuthenticationResponse_QNAME, CheckAuthenticationResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="checkAuthenticationWithClientAddress")
/*     */   public JAXBElement<CheckAuthenticationWithClientAddress> createCheckAuthenticationWithClientAddress(CheckAuthenticationWithClientAddress value)
/*     */   {
/* 376 */     return new JAXBElement(_CheckAuthenticationWithClientAddress_QNAME, CheckAuthenticationWithClientAddress.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="checkAuthenticationWithClientAddressResponse")
/*     */   public JAXBElement<CheckAuthenticationWithClientAddressResponse> createCheckAuthenticationWithClientAddressResponse(CheckAuthenticationWithClientAddressResponse value)
/*     */   {
/* 385 */     return new JAXBElement(_CheckAuthenticationWithClientAddressResponse_QNAME, CheckAuthenticationWithClientAddressResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="checkAuthenticationWithIdAndPwd")
/*     */   public JAXBElement<CheckAuthenticationWithIdAndPwd> createCheckAuthenticationWithIdAndPwd(CheckAuthenticationWithIdAndPwd value)
/*     */   {
/* 394 */     return new JAXBElement(_CheckAuthenticationWithIdAndPwd_QNAME, CheckAuthenticationWithIdAndPwd.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="checkAuthenticationWithIdAndPwdResponse")
/*     */   public JAXBElement<CheckAuthenticationWithIdAndPwdResponse> createCheckAuthenticationWithIdAndPwdResponse(CheckAuthenticationWithIdAndPwdResponse value)
/*     */   {
/* 403 */     return new JAXBElement(_CheckAuthenticationWithIdAndPwdResponse_QNAME, CheckAuthenticationWithIdAndPwdResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="findAllUnits")
/*     */   public JAXBElement<FindAllUnits> createFindAllUnits(FindAllUnits value)
/*     */   {
/* 412 */     return new JAXBElement(_FindAllUnits_QNAME, FindAllUnits.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="findAllUnitsResponse")
/*     */   public JAXBElement<FindAllUnitsResponse> createFindAllUnitsResponse(FindAllUnitsResponse value)
/*     */   {
/* 421 */     return new JAXBElement(_FindAllUnitsResponse_QNAME, FindAllUnitsResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="findRolesBySpCode")
/*     */   public JAXBElement<FindRolesBySpCode> createFindRolesBySpCode(FindRolesBySpCode value)
/*     */   {
/* 430 */     return new JAXBElement(_FindRolesBySpCode_QNAME, FindRolesBySpCode.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="findRolesBySpCodeResponse")
/*     */   public JAXBElement<FindRolesBySpCodeResponse> createFindRolesBySpCodeResponse(FindRolesBySpCodeResponse value)
/*     */   {
/* 439 */     return new JAXBElement(_FindRolesBySpCodeResponse_QNAME, FindRolesBySpCodeResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="findUsersBySpCode")
/*     */   public JAXBElement<FindUsersBySpCode> createFindUsersBySpCode(FindUsersBySpCode value)
/*     */   {
/* 448 */     return new JAXBElement(_FindUsersBySpCode_QNAME, FindUsersBySpCode.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="findUsersBySpCodeResponse")
/*     */   public JAXBElement<FindUsersBySpCodeResponse> createFindUsersBySpCodeResponse(FindUsersBySpCodeResponse value)
/*     */   {
/* 457 */     return new JAXBElement(_FindUsersBySpCodeResponse_QNAME, FindUsersBySpCodeResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="getSsoWsStatus")
/*     */   public JAXBElement<GetSsoWsStatus> createGetSsoWsStatus(GetSsoWsStatus value)
/*     */   {
/* 466 */     return new JAXBElement(_GetSsoWsStatus_QNAME, GetSsoWsStatus.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="getSsoWsStatusResponse")
/*     */   public JAXBElement<GetSsoWsStatusResponse> createGetSsoWsStatusResponse(GetSsoWsStatusResponse value)
/*     */   {
/* 475 */     return new JAXBElement(_GetSsoWsStatusResponse_QNAME, GetSsoWsStatusResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="isCurPwdValid")
/*     */   public JAXBElement<IsCurPwdValid> createIsCurPwdValid(IsCurPwdValid value)
/*     */   {
/* 484 */     return new JAXBElement(_IsCurPwdValid_QNAME, IsCurPwdValid.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="isCurPwdValidResponse")
/*     */   public JAXBElement<IsCurPwdValidResponse> createIsCurPwdValidResponse(IsCurPwdValidResponse value)
/*     */   {
/* 493 */     return new JAXBElement(_IsCurPwdValidResponse_QNAME, IsCurPwdValidResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="login")
/*     */   public JAXBElement<Login> createLogin(Login value)
/*     */   {
/* 502 */     return new JAXBElement(_Login_QNAME, Login.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="loginResponse")
/*     */   public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value)
/*     */   {
/* 511 */     return new JAXBElement(_LoginResponse_QNAME, LoginResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="logout")
/*     */   public JAXBElement<Logout> createLogout(Logout value)
/*     */   {
/* 520 */     return new JAXBElement(_Logout_QNAME, Logout.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="logoutResponse")
/*     */   public JAXBElement<LogoutResponse> createLogoutResponse(LogoutResponse value)
/*     */   {
/* 529 */     return new JAXBElement(_LogoutResponse_QNAME, LogoutResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="modifyPwd")
/*     */   public JAXBElement<ModifyPwd> createModifyPwd(ModifyPwd value)
/*     */   {
/* 538 */     return new JAXBElement(_ModifyPwd_QNAME, ModifyPwd.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="modifyPwdResponse")
/*     */   public JAXBElement<ModifyPwdResponse> createModifyPwdResponse(ModifyPwdResponse value)
/*     */   {
/* 547 */     return new JAXBElement(_ModifyPwdResponse_QNAME, ModifyPwdResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="removeAuthentication")
/*     */   public JAXBElement<RemoveAuthentication> createRemoveAuthentication(RemoveAuthentication value)
/*     */   {
/* 556 */     return new JAXBElement(_RemoveAuthentication_QNAME, RemoveAuthentication.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="removeAuthenticationResponse")
/*     */   public JAXBElement<RemoveAuthenticationResponse> createRemoveAuthenticationResponse(RemoveAuthenticationResponse value)
/*     */   {
/* 565 */     return new JAXBElement(_RemoveAuthenticationResponse_QNAME, RemoveAuthenticationResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="resetPwd")
/*     */   public JAXBElement<ResetPwd> createResetPwd(ResetPwd value)
/*     */   {
/* 574 */     return new JAXBElement(_ResetPwd_QNAME, ResetPwd.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="resetPwdResponse")
/*     */   public JAXBElement<ResetPwdResponse> createResetPwdResponse(ResetPwdResponse value)
/*     */   {
/* 583 */     return new JAXBElement(_ResetPwdResponse_QNAME, ResetPwdResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="updateAuthentication")
/*     */   public JAXBElement<UpdateAuthentication> createUpdateAuthentication(UpdateAuthentication value)
/*     */   {
/* 592 */     return new JAXBElement(_UpdateAuthentication_QNAME, UpdateAuthentication.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="updateAuthenticationResponse")
/*     */   public JAXBElement<UpdateAuthenticationResponse> createUpdateAuthenticationResponse(UpdateAuthenticationResponse value)
/*     */   {
/* 601 */     return new JAXBElement(_UpdateAuthenticationResponse_QNAME, UpdateAuthenticationResponse.class, null, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @XmlElementDecl(namespace="http://sso.ngtms.hwacom.com/", name="SsoWebServiceFault")
/*     */   public JAXBElement<String> createSsoWebServiceFault(String value)
/*     */   {
/* 610 */     return new JAXBElement(_SsoWebServiceFault_QNAME, String.class, null, value);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\ObjectFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */