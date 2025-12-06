/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.fm.service.AccountService;
/*     */ import com.hwacom.ngtms.sso.SsoWebService;
/*     */ import com.hwacom.ngtms.sso.SsoWebServiceFault;
/*     */ import com.hwacom.ngtms.sso.SsoWebServiceService;
/*     */ import com.hwacom.ngtms.sso.SsoWsRole;
/*     */ import com.hwacom.ngtms.sso.SsoWsUser;
/*     */ import com.hwacom.ngtms.sso.SsoWsUserProperty;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SsoWebServiceSynchronizer
/*     */ {
/*  39 */   private Logger logger = LoggerFactory.getLogger(SsoWebServiceSynchronizer.class);
/*  40 */   private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*     */   
/*     */   @Value("${sso.webservice.endpoint:}")
/*     */   private String ssoEndPoint;
/*     */   
/*     */   private SsoWebService ssoWebService;
/*     */   
/*     */   @Autowired
/*     */   private AccountService accountService;
/*     */   
/*     */   @Autowired
/*     */   MessageSourceExt messageSourceExt;
/*     */   
/*     */   public void synchronizeData(String spCode) {
/*  54 */     this.logger.debug("synchronizeData start...");
/*     */     try {
/*  56 */       if (getSsoWebService() == null) {
/*  57 */         this.logger.error("getSsoWebService() is null! Can not do synchronizeSsoData!");
/*     */         return;
/*     */       } 
/*  60 */       List<SsoWsRole> ssoRoles = getSsoWebService().findRolesBySpCode(spCode);
/*  61 */       this.logger.debug("ssoRoles => {}", ssoRoles);
/*  62 */       modifyLocalRole(ssoRoles);
/*     */       
/*  64 */       List<SsoWsUser> ssoUsers = getSsoWebService().findUsersBySpCode(spCode);
/*  65 */       this.logger.debug("ssoUsers.size => {}", (ssoUsers != null) ? Integer.valueOf(ssoUsers.size()) : "ssoUsers is null");
/*  66 */       printSsoUsers(ssoUsers);
/*  67 */       modifyLocalUsers(ssoUsers, ssoRoles);
/*  68 */       disableNotExistedUsers(ssoUsers);
/*  69 */       disableNotExistedRoles(ssoRoles);
/*  70 */     } catch (SsoWebServiceFault e) {
/*  71 */       this.logger.error(e.getMessage(), (Throwable)e);
/*  72 */     } catch (Exception e) {
/*  73 */       this.logger.error(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void disableNotExistedUsers(List<SsoWsUser> ssoUsers) {
/*     */     try {
/*  79 */       IMap<String, User> userIMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*     */       
/*  81 */       label17: for (User user : userIMap.values()) {
/*  82 */         for (SsoWsUser ssoWsUser : ssoUsers) {
/*  83 */           if (ssoWsUser.getLogin().equals(user.getLogin())) {
/*     */             continue label17;
/*     */           }
/*     */         } 
/*  87 */         this.logger.debug("Disable User : {}", user);
/*  88 */         user.setEnable(Boolean.FALSE);
/*  89 */         this.accountService.updateUser(user);
/*     */       } 
/*  91 */     } catch (Exception e) {
/*  92 */       this.logger.error("disableNotExistedUsers Error : ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void disableNotExistedRoles(List<SsoWsRole> ssoRoles) {
/*     */     try {
/*  98 */       IMap<String, Role> roleIMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */       
/* 100 */       label17: for (Role role : roleIMap.values()) {
/* 101 */         for (SsoWsRole ssoWsRole : ssoRoles) {
/* 102 */           if (ssoWsRole.getName().equals(role.getName())) {
/*     */             continue label17;
/*     */           }
/*     */         } 
/* 106 */         this.logger.debug("Disable Role : {}", role);
/* 107 */         role.setEnable(Boolean.FALSE);
/* 108 */         this.accountService.updateRole(role);
/*     */       } 
/* 110 */     } catch (Exception e) {
/* 111 */       this.logger.error("disableNotExistedUsers Error : ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void modifyLocalUsers(List<SsoWsUser> ssoUsers, List<SsoWsRole> ssoRoles) {
/* 116 */     this.logger.debug("modifyLocalUsers start...");
/* 117 */     if (ssoUsers != null) {
/* 118 */       for (SsoWsUser ssoUser : ssoUsers) {
/*     */         try {
/* 120 */           modifyLocalUser(ssoUser, ssoRoles);
/* 121 */         } catch (Exception e) {
/* 122 */           this.logger.error(e.getMessage(), e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void printSsoUsers(List<SsoWsUser> ssoUsers) {
/* 129 */     this.logger.debug("printSsoUsers start...");
/* 130 */     if (ssoUsers != null) {
/* 131 */       this.logger.debug("ssoUsers size : {}", Integer.valueOf(ssoUsers.size()));
/* 132 */       for (SsoWsUser ssoUser : ssoUsers) {
/* 133 */         this.logger.debug("ssoUser : login : {} ; roles : {} ; description : {}, password : {}", new Object[] { ssoUser
/*     */               
/* 135 */               .getLogin(), ssoUser
/* 136 */               .getRoles(), ssoUser
/* 137 */               .getPassword(), ssoUser
/* 138 */               .getDescription() });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void modifyLocalRole(List<SsoWsRole> ssoRoles) throws Exception {
/* 144 */     this.logger.debug("modifyLocalRole start...");
/*     */     try {
/* 146 */       IMap<String, Role> roleIMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 147 */       if (ssoRoles != null) {
/* 148 */         ssoRoles
/* 149 */           .stream()
/* 150 */           .forEach(ssoRole -> {
/*     */               Role localRole = (Role)paramIMap.get(ssoRole.getName());
/*     */               
/*     */               if (localRole == null) {
/*     */                 Role newLocalRole = new Role();
/*     */                 
/*     */                 newLocalRole.setName(ssoRole.getName());
/*     */                 
/*     */                 newLocalRole.setDescription(ssoRole.getDescription());
/*     */                 newLocalRole.setUpdateTime(new Date());
/*     */                 newLocalRole.setEnable(Boolean.TRUE);
/*     */                 this.accountService.createRole(newLocalRole);
/*     */                 this.logger.info("Create new Local Role : {}", newLocalRole);
/*     */               } else if (!localRole.getDescription().equals(ssoRole.getDescription())) {
/*     */                 localRole.setDescription(ssoRole.getDescription());
/*     */                 localRole.setUpdateTime(new Date());
/*     */                 localRole.setEnable(Boolean.TRUE);
/*     */                 this.accountService.updateRole(localRole);
/*     */                 this.logger.info("Update Local Role : {}", localRole);
/*     */               } 
/*     */             });
/*     */       } else {
/* 172 */         this.logger.info("ssoRoles is null,So can not modify Local Roles!");
/*     */       } 
/* 174 */     } catch (Exception e) {
/* 175 */       this.logger.error("modifyLocalRole Error : ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private SsoWsUser findSsoWsUser(List<SsoWsUser> ssoUsers, String userId) {
/* 180 */     this.logger.debug("findSsoWsUser start...");
/* 181 */     if (ssoUsers == null || userId == null) return null; 
/* 182 */     for (SsoWsUser ssoWsUser : ssoUsers) {
/* 183 */       if (userId.equals(ssoWsUser.getLogin())) {
/* 184 */         return ssoWsUser;
/*     */       }
/*     */     } 
/* 187 */     return null;
/*     */   }
/*     */   
/*     */   private Set<String> generateRole(List<String> roles, List<SsoWsRole> ssoRoles) {
/* 191 */     this.logger.debug("generateRole start...");
/* 192 */     if (roles == null) return null;
/*     */     
/* 194 */     IMap<String, Role> roleIMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 195 */     Set<String> returnRoles = new HashSet<>();
/* 196 */     for (String roleStr : roles) {
/* 197 */       String oldRole = ((Role)roleIMap.get(roleStr)).getName();
/* 198 */       if (oldRole != null) {
/* 199 */         returnRoles.add(oldRole); continue;
/*     */       } 
/* 201 */       SsoWsRole nowSsoRole = null;
/* 202 */       for (SsoWsRole ssoRole : ssoRoles) {
/* 203 */         if (roleStr.equals(ssoRole.getName())) {
/* 204 */           nowSsoRole = ssoRole;
/*     */           break;
/*     */         } 
/*     */       } 
/* 208 */       if (nowSsoRole != null) {
/* 209 */         Role role = new Role();
/* 210 */         role.setName(roleStr);
/* 211 */         role.setDescription((nowSsoRole != null) ? nowSsoRole.getDescription() : "");
/* 212 */         role.setUpdateTime(new Date());
/*     */         try {
/* 214 */           this.accountService.createRole(role);
/* 215 */         } catch (Exception e) {
/* 216 */           this.logger.error(e.getMessage(), e);
/*     */         } 
/* 218 */         returnRoles.add(role.getName());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 224 */     return returnRoles;
/*     */   }
/*     */   
/*     */   public void synchronizeDataBySelf(String userId, String spCode) {
/* 228 */     this.logger.debug("synchronizeDataBySelf start...");
/* 229 */     this.logger.info("userName : {} want to do synchronized data!", userId);
/* 230 */     if (userId == null) {
/* 231 */       this.logger.warn("userId is null! Do nothing!");
/*     */       return;
/*     */     } 
/*     */     try {
/* 235 */       List<SsoWsUser> ssoUsers = getSsoWebService().findUsersBySpCode(spCode);
/* 236 */       SsoWsUser ssoWsUser = findSsoWsUser(ssoUsers, userId);
/* 237 */       List<SsoWsRole> ssoRoles = getSsoWebService().findRolesBySpCode(spCode);
/* 238 */       this.logger.debug("ssoRoles => {}", ssoRoles);
/* 239 */       modifyLocalUser(ssoWsUser, ssoRoles);
/* 240 */     } catch (SsoWebServiceFault e) {
/* 241 */       this.logger.error(e.getMessage(), (Throwable)e);
/* 242 */     } catch (Exception e) {
/* 243 */       this.logger.error(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getSsoName(SsoWsUser ssoWsUser) {
/* 248 */     List<SsoWsUserProperty> properties = ssoWsUser.getProperies();
/* 249 */     for (SsoWsUserProperty property : properties) {
/* 250 */       if ("name".equals(property.getName())) {
/* 251 */         this.logger.debug("sso name : {} ", property.getValue());
/* 252 */         return property.getValue();
/*     */       } 
/*     */     } 
/* 255 */     return null;
/*     */   }
/*     */   
/*     */   private void setSsoOtherAttributes(SsoWsUser ssoWsUser, User localUser) {
/* 259 */     List<SsoWsUserProperty> properties = ssoWsUser.getProperies();
/* 260 */     for (SsoWsUserProperty property : properties) {
/* 261 */       this.logger.debug("property name : {} ; value : {}", property.getName(), property.getValue());
/* 262 */       if ("initial_date".equals(property.getName())) {
/* 263 */         Date startTime = null;
/*     */         try {
/* 265 */           startTime = this.dateFormat.parse(property.getValue());
/* 266 */           if (startTime != null) localUser.setStartTime(startTime); 
/* 267 */         } catch (ParseException e) {
/* 268 */           this.logger.error(e.getMessage(), e);
/*     */         } 
/*     */       } 
/* 271 */       if ("expire_date".equals(property.getName())) {
/* 272 */         Date endTime = null;
/*     */         try {
/* 274 */           endTime = this.dateFormat.parse(property.getValue());
/* 275 */           if (endTime != null) localUser.setEndTime(endTime); 
/* 276 */         } catch (ParseException e) {
/* 277 */           this.logger.error(e.getMessage(), e);
/*     */         } 
/*     */       } 
/* 280 */       if ("invalid".equals(property.getName())) {
/* 281 */         String invalid = property.getValue();
/* 282 */         boolean disable = Boolean.valueOf((invalid != null) ? invalid : "false").booleanValue();
/* 283 */         localUser.setEnable(Boolean.valueOf(!disable));
/*     */       } 
/* 285 */       if ("mobiles".equals(property.getName())) {
/* 286 */         String mobile = property.getValue();
/* 287 */         localUser.setMobile(mobile);
/*     */       } 
/* 289 */       if ("email".equals(property.getName())) {
/* 290 */         String eMail = property.getValue();
/* 291 */         localUser.setMail(eMail);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void modifyLocalUser(SsoWsUser ssoWsUser, List<SsoWsRole> ssoRoles) throws Exception {
/* 297 */     this.logger.debug("modifyLocalUser start...");
/* 298 */     this.logger.debug("ssoWsUser : login : {} ; password : {} ; description : {}", new Object[] { ssoWsUser
/*     */           
/* 300 */           .getLogin(), ssoWsUser
/* 301 */           .getPassword(), ssoWsUser
/* 302 */           .getDescription() });
/* 303 */     IMap<String, User> userIMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 304 */     User localUser = (User)userIMap.get(ssoWsUser.getLogin());
/* 305 */     this.logger.debug("modifyLocalUser localUser : {}", localUser);
/* 306 */     if (localUser == null) {
/* 307 */       User newLocalUser = new User();
/* 308 */       newLocalUser.setLogin(ssoWsUser.getLogin());
/* 309 */       if (ssoWsUser != null) {
/* 310 */         newLocalUser.setPwd1(ssoWsUser.getPassword());
/* 311 */         newLocalUser.setDescription(ssoWsUser.getDescription());
/* 312 */         newLocalUser.setEnable(Boolean.TRUE);
/* 313 */         String name = getSsoName(ssoWsUser);
/* 314 */         newLocalUser.setName((name != null) ? name : ssoWsUser.getLogin());
/* 315 */         newLocalUser.setLogin(ssoWsUser.getLogin());
/* 316 */         newLocalUser.setCheckExpired(Boolean.valueOf(true));
/* 317 */         newLocalUser.setUpdateTime(new Date());
/* 318 */         setSsoOtherAttributes(ssoWsUser, newLocalUser);
/* 319 */         Set<String> newRoles = generateRole(ssoWsUser.getRoles(), ssoRoles);
/* 320 */         newLocalUser.setRoleNames(newRoles);
/* 321 */         this.logger.info("SSO new User ,So create New User : user => {}", newLocalUser);
/* 322 */         this.accountService.createUser(newLocalUser);
/*     */       } 
/*     */     } else {
/* 325 */       this.logger.debug("localUser => {},{},{}", new Object[] { localUser
/* 326 */             .getLogin(), localUser.getPwd1(), localUser.getName() });
/* 327 */       if (ssoWsUser != null) {
/* 328 */         if (!localUser.getPwd1().equals(ssoWsUser.getPassword()) || 
/* 329 */           !checkTheSameOfSsoRoleAndLocalRole(ssoWsUser.getRoles(), new ArrayList<>())) {
/* 330 */           localUser.setPwd1(ssoWsUser.getPassword());
/* 331 */           localUser.setDescription(ssoWsUser.getDescription());
/* 332 */           localUser.setEnable(Boolean.TRUE);
/* 333 */           String name = getSsoName(ssoWsUser);
/* 334 */           localUser.setName((name != null) ? name : ssoWsUser.getLogin());
/* 335 */           localUser.setCheckExpired(Boolean.valueOf(true));
/* 336 */           localUser.setUpdateTime(new Date());
/* 337 */           setSsoOtherAttributes(ssoWsUser, localUser);
/* 338 */           Set<String> newRoles = generateRole(ssoWsUser.getRoles(), ssoRoles);
/* 339 */           localUser.setRoleNames(newRoles);
/* 340 */           this.logger.info("SSO User had modify,So to update User : user => {}", localUser);
/* 341 */           this.accountService.updateUser(localUser);
/*     */         } else {
/* 343 */           this.logger.info("ssoWsUser is the same local User, So can not to modify local User!");
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkTheSameOfSsoRoleAndLocalRole(List<String> ssoRoles, List<Role> localRoles) {
/* 350 */     boolean result = true;
/* 351 */     if (ssoRoles == null && localRoles == null) {
/* 352 */       return true;
/*     */     }
/* 354 */     if (ssoRoles != null && localRoles == null) {
/* 355 */       return false;
/*     */     }
/* 357 */     if (ssoRoles == null && localRoles != null) {
/* 358 */       return false;
/*     */     }
/* 360 */     if (ssoRoles.size() != localRoles.size()) {
/* 361 */       return false;
/*     */     }
/*     */     
/* 364 */     label26: for (String ssoRole : ssoRoles) {
/* 365 */       for (Role role : localRoles) {
/* 366 */         if (ssoRole.equals(role.getName())) {
/*     */           continue label26;
/*     */         }
/*     */       } 
/* 370 */       result = false;
/*     */     } 
/* 372 */     return result;
/*     */   }
/*     */   
/*     */   private SsoWebService getSsoWebService() throws Exception {
/* 376 */     if (this.ssoWebService != null) return this.ssoWebService; 
/*     */     try {
/* 378 */       SsoWebServiceService ssoWsService = new SsoWebServiceService(new URL(this.ssoEndPoint));
/* 379 */       this.ssoWebService = ssoWsService.getSsoWebServicePort();
/* 380 */     } catch (MalformedURLException e) {
/* 381 */       throw e;
/*     */     } 
/* 383 */     return this.ssoWebService;
/*     */   }
/*     */   
/*     */   public String getSsoEndPoint() {
/* 387 */     return this.ssoEndPoint;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\SsoWebServiceSynchronizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */