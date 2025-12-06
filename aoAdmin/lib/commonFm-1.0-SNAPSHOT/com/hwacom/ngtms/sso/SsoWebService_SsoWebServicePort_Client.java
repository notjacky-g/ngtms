/*     */ package com.hwacom.ngtms.sso;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public final class SsoWebService_SsoWebServicePort_Client
/*     */ {
/*  29 */   private static final QName SERVICE_NAME = new QName("http://sso.ngtms.hwacom.com/", "SsoWebServiceService");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  35 */     URL wsdlURL = SsoWebServiceService.WSDL_LOCATION;
/*  36 */     if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
/*  37 */       File wsdlFile = new File(args[0]);
/*     */       try {
/*  39 */         if (wsdlFile.exists()) {
/*  40 */           wsdlURL = wsdlFile.toURI().toURL();
/*     */         } else {
/*  42 */           wsdlURL = new URL(args[0]);
/*     */         } 
/*  44 */       } catch (MalformedURLException e) {
/*  45 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     SsoWebServiceService ss = new SsoWebServiceService(wsdlURL, SERVICE_NAME);
/*  50 */     SsoWebService port = ss.getSsoWebServicePort();
/*     */ 
/*     */     
/*  53 */     System.out.println("Invoking findUsersBySpCode...");
/*  54 */     String _findUsersBySpCode_spCode = "";
/*     */     try {
/*  56 */       List<SsoWsUser> _findUsersBySpCode__return = port.findUsersBySpCode(_findUsersBySpCode_spCode);
/*  57 */       System.out.println("findUsersBySpCode.result=" + _findUsersBySpCode__return);
/*     */     }
/*  59 */     catch (SsoWebServiceFault e) {
/*  60 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/*  61 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/*  65 */     System.out.println("Invoking getSsoWsStatus...");
/*     */     try {
/*  67 */       String _getSsoWsStatus__return = port.getSsoWsStatus();
/*  68 */       System.out.println("getSsoWsStatus.result=" + _getSsoWsStatus__return);
/*     */     }
/*  70 */     catch (SsoWebServiceFault e) {
/*  71 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/*  72 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/*  76 */     System.out.println("Invoking login...");
/*  77 */     String _login_spCode = "";
/*  78 */     String _login_userAccount = "";
/*  79 */     String _login_userPassword = "";
/*     */     try {
/*  81 */       SsoWsAuthentication _login__return = port.login(_login_spCode, _login_userAccount, _login_userPassword);
/*  82 */       System.out.println("login.result=" + _login__return);
/*     */     }
/*  84 */     catch (SsoWebServiceFault e) {
/*  85 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/*  86 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/*  90 */     System.out.println("Invoking findRolesBySpCode...");
/*  91 */     String _findRolesBySpCode_spCode = "";
/*     */     try {
/*  93 */       List<SsoWsRole> _findRolesBySpCode__return = port.findRolesBySpCode(_findRolesBySpCode_spCode);
/*  94 */       System.out.println("findRolesBySpCode.result=" + _findRolesBySpCode__return);
/*     */     }
/*  96 */     catch (SsoWebServiceFault e) {
/*  97 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/*  98 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 102 */     System.out.println("Invoking logout...");
/*     */     try {
/* 104 */       boolean _logout__return = port.logout();
/* 105 */       System.out.println("logout.result=" + _logout__return);
/*     */     }
/* 107 */     catch (SsoWebServiceFault e) {
/* 108 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 109 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 113 */     System.out.println("Invoking updateAuthentication...");
/* 114 */     String _updateAuthentication_clientAddr = "";
/* 115 */     String _updateAuthentication_userAccount = "";
/* 116 */     String _updateAuthentication_userPassword = "";
/* 117 */     String _updateAuthentication_sessionId = "";
/*     */     try {
/* 119 */       boolean _updateAuthentication__return = port.updateAuthentication(_updateAuthentication_clientAddr, _updateAuthentication_userAccount, _updateAuthentication_userPassword, _updateAuthentication_sessionId);
/* 120 */       System.out.println("updateAuthentication.result=" + _updateAuthentication__return);
/*     */     }
/* 122 */     catch (SsoWebServiceFault e) {
/* 123 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 124 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 128 */     System.out.println("Invoking resetPwd...");
/* 129 */     String _resetPwd_userAccount = "";
/* 130 */     String _resetPwd_userName = "";
/* 131 */     String _resetPwd_userEmail = "";
/*     */     try {
/* 133 */       SsoWsResetPwd _resetPwd__return = port.resetPwd(_resetPwd_userAccount, _resetPwd_userName, _resetPwd_userEmail);
/* 134 */       System.out.println("resetPwd.result=" + _resetPwd__return);
/*     */     }
/* 136 */     catch (SsoWebServiceFault e) {
/* 137 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 138 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 142 */     System.out.println("Invoking isCurPwdValid...");
/* 143 */     String _isCurPwdValid_userAccount = "";
/* 144 */     String _isCurPwdValid_curPwd = "";
/*     */     try {
/* 146 */       SsoWsIsCurPwdValid _isCurPwdValid__return = port.isCurPwdValid(_isCurPwdValid_userAccount, _isCurPwdValid_curPwd);
/* 147 */       System.out.println("isCurPwdValid.result=" + _isCurPwdValid__return);
/*     */     }
/* 149 */     catch (SsoWebServiceFault e) {
/* 150 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 151 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 155 */     System.out.println("Invoking modifyPwd...");
/* 156 */     String _modifyPwd_userAccount = "";
/* 157 */     String _modifyPwd_curPwd = "";
/* 158 */     String _modifyPwd_newPwd = "";
/*     */     try {
/* 160 */       SsoWsModifyPwd _modifyPwd__return = port.modifyPwd(_modifyPwd_userAccount, _modifyPwd_curPwd, _modifyPwd_newPwd);
/* 161 */       System.out.println("modifyPwd.result=" + _modifyPwd__return);
/*     */     }
/* 163 */     catch (SsoWebServiceFault e) {
/* 164 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 165 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 169 */     System.out.println("Invoking removeAuthentication...");
/* 170 */     String _removeAuthentication_clientAddr = "";
/*     */     try {
/* 172 */       boolean _removeAuthentication__return = port.removeAuthentication(_removeAuthentication_clientAddr);
/* 173 */       System.out.println("removeAuthentication.result=" + _removeAuthentication__return);
/*     */     }
/* 175 */     catch (SsoWebServiceFault e) {
/* 176 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 177 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 181 */     System.out.println("Invoking checkAuthentication...");
/*     */     try {
/* 183 */       SsoWsAuthentication _checkAuthentication__return = port.checkAuthentication();
/* 184 */       System.out.println("checkAuthentication.result=" + _checkAuthentication__return);
/*     */     }
/* 186 */     catch (SsoWebServiceFault e) {
/* 187 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 188 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 192 */     System.out.println("Invoking checkAuthenticationWithClientAddress...");
/* 193 */     String _checkAuthenticationWithClientAddress_clientAddr = "";
/*     */     try {
/* 195 */       SsoWsAuthentication _checkAuthenticationWithClientAddress__return = port.checkAuthenticationWithClientAddress(_checkAuthenticationWithClientAddress_clientAddr);
/* 196 */       System.out.println("checkAuthenticationWithClientAddress.result=" + _checkAuthenticationWithClientAddress__return);
/*     */     }
/* 198 */     catch (SsoWebServiceFault e) {
/* 199 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 200 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 204 */     System.out.println("Invoking checkAuthenticationWithIdAndPwd...");
/* 205 */     String _checkAuthenticationWithIdAndPwd_spCode = "";
/* 206 */     String _checkAuthenticationWithIdAndPwd_userAccount = "";
/* 207 */     String _checkAuthenticationWithIdAndPwd_userPassword = "";
/*     */     try {
/* 209 */       SsoWsUser _checkAuthenticationWithIdAndPwd__return = port.checkAuthenticationWithIdAndPwd(_checkAuthenticationWithIdAndPwd_spCode, _checkAuthenticationWithIdAndPwd_userAccount, _checkAuthenticationWithIdAndPwd_userPassword);
/* 210 */       System.out.println("checkAuthenticationWithIdAndPwd.result=" + _checkAuthenticationWithIdAndPwd__return);
/*     */     }
/* 212 */     catch (SsoWebServiceFault e) {
/* 213 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 214 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 218 */     System.out.println("Invoking findAllUnits...");
/*     */     try {
/* 220 */       List<SsoJobUnitDTO> _findAllUnits__return = port.findAllUnits();
/* 221 */       System.out.println("findAllUnits.result=" + _findAllUnits__return);
/*     */     }
/* 223 */     catch (SsoWebServiceFault e) {
/* 224 */       System.out.println("Expected exception: SsoWebServiceFault has occurred.");
/* 225 */       System.out.println(e.toString());
/*     */     } 
/*     */ 
/*     */     
/* 229 */     System.exit(0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\SsoWebService_SsoWebServicePort_Client.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */