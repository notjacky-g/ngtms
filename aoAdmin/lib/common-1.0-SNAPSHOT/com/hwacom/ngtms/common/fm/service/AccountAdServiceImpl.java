/*     */ package com.hwacom.ngtms.common.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.ILock;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.FunctionPermission;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.Unit;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.fm.model.UserForgotPwdToken;
/*     */ import com.hwacom.ngtms.common.fm.repository.FunctionPermissionRepository;
/*     */ import com.hwacom.ngtms.common.fm.repository.RoleRepository;
/*     */ import com.hwacom.ngtms.common.fm.repository.UnitRepository;
/*     */ import com.hwacom.ngtms.common.fm.repository.UserRepository;
/*     */ import com.hwacom.ngtms.common.ldap.SimpleActiveDirectory;
/*     */ import com.hwacom.ngtms.common.shared.AccessDeniedInfo;
/*     */ import com.hwacom.ngtms.common.shared.InvalidTokenException;
/*     */ import com.hwacom.ngtms.common.shared.PwdChangedTooSoonException;
/*     */ import com.hwacom.ngtms.common.shared.PwdRepeatedException;
/*     */ import com.hwacom.ngtms.common.shared.TokenExpiredException;
/*     */ import com.hwacom.ngtms.common.shared.UserEmailUnmatchException;
/*     */ import com.hwacom.ngtms.common.shared.UserNotFoundException;
/*     */ import com.hwacom.ngtms.common.shared.dto.RecoverMfaCodeDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RecoverPwdDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
/*     */ import com.hwacom.ngtms.common.util.IMapLocker;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccountAdServiceImpl
/*     */   implements AccountService
/*     */ {
/*  52 */   private static Logger logger = LoggerFactory.getLogger(AccountAdServiceImpl.class);
/*     */   
/*     */   @Autowired
/*     */   private SimpleActiveDirectory simpleActiveDirectory;
/*     */   
/*     */   @Autowired
/*     */   private UserRepository userRepository;
/*     */   @Autowired
/*     */   private RoleRepository roleRepository;
/*     */   
/*     */   public void init() {
/*  63 */     logger.debug("AccountService sync start...");
/*  64 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*     */     
/*  66 */     List<Role> adRoles = this.simpleActiveDirectory.getAllGroups();
/*  67 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*  68 */     logger.debug("Role size: '{}'", Integer.valueOf(adRoles.size()));
/*  69 */     for (Role adRole : adRoles) {
/*  70 */       if (roleMap.get(adRole.getName()) == null) {
/*  71 */         adRole.setUpdateTime(new Date());
/*  72 */         adRole.setEnable(Boolean.valueOf(true));
/*  73 */         IMapLocker.addOrUpdateMapping(roleMap, adRole.getName(), adRole);
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     List<User> adUserList = this.simpleActiveDirectory.getAllUsers();
/*  78 */     logger.debug("User size: '{}'", Integer.valueOf(adUserList.size()));
/*  79 */     for (User adUser : adUserList) {
/*  80 */       adUser.setEnable(Boolean.valueOf(true));
/*  81 */       adUser.setCheckExpired(Boolean.valueOf(false));
/*  82 */       String login = adUser.getLogin();
/*  83 */       User user = getUserIgnoreLoginCase(userMap, login);
/*  84 */       if (user != null) {
/*  85 */         login = user.getLogin();
/*  86 */         adUser.setUnitNames(user.getUnitNames());
/*  87 */         adUser.setUpdateTime(user.getUpdateTime());
/*     */       } 
/*  89 */       if (adUser.getPwd1() == null) {
/*  90 */         adUser.setPwd1("67727a41b5b1d4dfca981e4045b1bb2f1e7fef0e3e8825c028949d186cad4c00");
/*     */       }
/*     */       
/*  93 */       if (!EqualsBuilder.reflectionEquals(adUser, user)) {
/*  94 */         adUser.setUpdateTime(new Date());
/*  95 */         adUser.setPwd2(adUser.getPwd1());
/*  96 */         adUser.setPwdChanged(Boolean.valueOf(true));
/*     */         
/*  98 */         Date lastPwdChangeTime = Date.from(
/*  99 */             LocalDateTime.now()
/* 100 */             .plus(10L, ChronoUnit.YEARS)
/* 101 */             .atZone(ZoneId.systemDefault())
/* 102 */             .toInstant());
/* 103 */         adUser.setLastPwdChangeTime(lastPwdChangeTime);
/* 104 */         adUser.setLocalAccount(Boolean.valueOf(false));
/* 105 */         IMapLocker.addOrUpdateMapping(userMap, login, adUser);
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     List<User> removeUsers = new ArrayList<>();
/* 110 */     for (User mapUser : userMap.values()) {
/* 111 */       if (!adUserList.contains(mapUser)) {
/* 112 */         removeUsers.add(mapUser); continue;
/*     */       } 
/* 114 */       User adUser = adUserList.get(adUserList.indexOf(mapUser));
/* 115 */       adUser.setUnitNames(mapUser.getUnitNames());
/*     */       
/* 117 */       adUser.setPwd1("1b4f0e9851971998e732078544c96b36c3d01cedf7caa332359d6f1d83567014");
/* 118 */       adUser.setDescription(adUser.getName());
/* 119 */       userMap.set(adUser.getLogin(), adUser);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     List<Role> roleList = findRoleAll();
/* 129 */     for (Role role : roleList)
/*     */     {
/* 131 */       roleMap.set(role.getName(), role);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     logger.debug("AccountService sync done..."); } @Autowired
/*     */   private FunctionPermissionRepository functionPermissionRepository; @Autowired
/*     */   private UnitRepository unitRepository; @Autowired
/*     */   private ModelMapper modelMapper; private User getUserIgnoreLoginCase(IMap<String, User> userMap, String login) {
/* 143 */     String lowerCaseLogin = login.toLowerCase();
/* 144 */     for (User user : userMap.values()) {
/* 145 */       if (Objects.equals(lowerCaseLogin, user.getLogin().toLowerCase())) {
/* 146 */         return user;
/*     */       }
/*     */     } 
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadAccountMapData() {
/* 154 */     clearAccountMapData();
/* 155 */     init();
/*     */   }
/*     */   
/*     */   private void clearAccountMapData() {
/* 159 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 160 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */     
/* 162 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/*     */     
/* 164 */     ILock iLock = HzUtils.getHzInstance().getLock("myLock");
/* 165 */     iLock.lock();
/*     */     try {
/* 167 */       userMap.clear();
/* 168 */       roleMap.clear();
/* 169 */       functionPermissionMap.clear();
/*     */     } finally {
/* 171 */       iLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public UserBasicInfoDTO findUserBasicInfo(String login) {
/* 177 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 178 */     Optional<User> optUser = Optional.ofNullable(userMap.get(login));
/* 179 */     if (optUser.isPresent()) {
/* 180 */       return (UserBasicInfoDTO)this.modelMapper.map(optUser.get(), UserBasicInfoDTO.class);
/*     */     }
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveUserBasicInfo(UserBasicInfoDTO dto) {
/* 188 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 189 */     Optional<User> optUser = Optional.ofNullable(userMap.get(dto.getLogin()));
/* 190 */     optUser.ifPresent(user -> {
/*     */           user.setPwd1(paramUserBasicInfoDTO.getPwd1());
/*     */           user.setName(paramUserBasicInfoDTO.getName());
/*     */           user.setDescription(paramUserBasicInfoDTO.getDescription());
/*     */           user.setMobile(paramUserBasicInfoDTO.getMobile());
/*     */           user.setMail(paramUserBasicInfoDTO.getMail());
/*     */           updateUser(user);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<User> findUserAll() {
/* 203 */     List<User> userList = this.simpleActiveDirectory.getAllUsers();
/* 204 */     logger.debug("findUserAll userList = {}", userList);
/* 205 */     return userList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createUser(User user) {
/* 210 */     this.simpleActiveDirectory.createUser(user);
/* 211 */     for (String roleName : user.getRoleNames()) {
/*     */       try {
/* 213 */         this.simpleActiveDirectory.addUserToGroup(user.getLogin(), roleName);
/* 214 */       } catch (NamingException e) {
/* 215 */         logger.error("Add user to group failed, user='{}', group='{}'", user.getLogin(), roleName);
/*     */       } 
/*     */     } 
/* 218 */     user.setLocalAccount(Boolean.valueOf(false));
/* 219 */     this.userRepository.save(user);
/*     */     
/* 221 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 222 */     userMap.put(user.getLogin(), user);
/* 223 */     logger.debug("createUser user = {}", user);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUser(User user) {
/*     */     try {
/* 230 */       this.simpleActiveDirectory.updateUser(user);
/* 231 */     } catch (Exception e1) {
/* 232 */       logger.warn(e1.getMessage(), e1);
/*     */     } 
/* 234 */     this.userRepository.save(user);
/*     */     
/* 236 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 237 */     String key = user.getLogin();
/*     */     try {
/* 239 */       if (userMap.tryLock(key, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 241 */           userMap.delete(key);
/* 242 */           userMap.put(key, user);
/*     */         } finally {
/* 244 */           userMap.unlock(key);
/*     */         } 
/*     */       }
/* 247 */     } catch (InterruptedException e) {
/* 248 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 250 */     logger.debug("updateUser user = {}", user);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeUser(User user) {
/* 255 */     this.simpleActiveDirectory.removeUser(user.getLogin());
/* 256 */     this.userRepository.delete(user);
/*     */     
/* 258 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 259 */     String key = user.getLogin();
/*     */     try {
/* 261 */       IMapLocker.removeMapping(userMap, key);
/* 262 */     } catch (RuntimeException e) {
/* 263 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 265 */     logger.debug("removeUser user = {}", user);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Role> findRoleAll() {
/* 270 */     List<Role> roleList = this.simpleActiveDirectory.getAllGroups();
/* 271 */     List<Role> roleListDb = this.roleRepository.findAll();
/* 272 */     for (Role roleDb : roleListDb) {
/* 273 */       for (Role roleLdap : roleList) {
/* 274 */         if (roleLdap.getName().equals(roleDb.getName())) {
/* 275 */           roleLdap.setFunctionPermissions(roleDb.getFunctionPermissions());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     logger.debug("findRoleAll roleList = {}", roleList);
/* 281 */     return roleList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createRole(Role role) {
/* 286 */     this.simpleActiveDirectory.createGroup(role);
/* 287 */     this.roleRepository.save(role);
/*     */     
/* 289 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 290 */     roleMap.put(role.getName(), role);
/* 291 */     logger.debug("createRole role = {}", role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRole(Role role) {
/*     */     try {
/* 297 */       this.simpleActiveDirectory.updateGroup(role);
/* 298 */     } catch (Exception e1) {
/* 299 */       logger.warn(e1.getMessage(), e1);
/*     */     } 
/* 301 */     this.roleRepository.save(role);
/*     */     
/* 303 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 304 */     String key = role.getName();
/*     */     try {
/* 306 */       if (roleMap.tryLock(key, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 308 */           roleMap.delete(key);
/* 309 */           roleMap.put(key, role);
/*     */         } finally {
/* 311 */           roleMap.unlock(key);
/*     */         } 
/*     */       }
/* 314 */     } catch (InterruptedException e) {
/* 315 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 317 */     logger.debug("updateRole role = {}", role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeRole(Role role) {
/* 322 */     this.simpleActiveDirectory.removeGroup(role.getName());
/* 323 */     this.roleRepository.delete(role);
/*     */     
/* 325 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 326 */     String key = role.getName();
/*     */     try {
/* 328 */       IMapLocker.removeMapping(roleMap, key);
/* 329 */     } catch (RuntimeException e) {
/* 330 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 332 */     logger.debug("removeRole role = {}", role);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FunctionPermission> findFunctionPermissionAll() {
/* 337 */     List<FunctionPermission> functionPermissionList = this.functionPermissionRepository.findAll();
/* 338 */     logger.debug("findFunctionPermissionAll functionPermissionList = {}", functionPermissionList);
/* 339 */     return functionPermissionList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createFunctionPermission(FunctionPermission functionPermission) {
/* 344 */     this.functionPermissionRepository.save(functionPermission);
/*     */ 
/*     */     
/* 347 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/*     */     
/* 349 */     functionPermissionMap.put(functionPermission.getName(), functionPermission);
/* 350 */     logger.debug("createFunctionPermission functionPermission = {}", functionPermission);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateFunctionPermission(FunctionPermission functionPermission) {
/* 355 */     this.functionPermissionRepository.save(functionPermission);
/*     */ 
/*     */     
/* 358 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 359 */     String key = functionPermissionMap.getName();
/*     */     try {
/* 361 */       if (functionPermissionMap.tryLock(key, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 363 */           functionPermissionMap.delete(key);
/* 364 */           functionPermissionMap.put(key, functionPermission);
/*     */         } finally {
/* 366 */           functionPermissionMap.unlock(key);
/*     */         } 
/*     */       }
/* 369 */     } catch (InterruptedException e) {
/* 370 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 372 */     logger.debug("updateFunctionPermission functionPermission = {}", functionPermission);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFunctionPermission(FunctionPermission functionPermission) {
/* 377 */     this.functionPermissionRepository.delete(functionPermission);
/*     */     
/* 379 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 380 */     String key = functionPermission.getName();
/*     */     try {
/* 382 */       IMapLocker.removeMapping(functionPermissionMap, key);
/* 383 */     } catch (RuntimeException e) {
/* 384 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 386 */     logger.debug("removeFunctionPermission functionPermission = {}", functionPermission);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Unit> findUnitAll() {
/* 391 */     List<Unit> userList = this.unitRepository.findAll();
/* 392 */     logger.debug("findUnitAll unitList = {}", userList);
/* 393 */     return userList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createUnit(Unit unit) {
/* 398 */     IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 399 */     unitMap.put(unit.getName(), unit);
/* 400 */     logger.debug("createUnit unit = {}", unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUnit(Unit unit) {
/* 405 */     IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 406 */     String key = unit.getName();
/*     */     try {
/* 408 */       IMapLocker.addOrUpdateMapping(unitMap, key, unit);
/* 409 */     } catch (RuntimeException e) {
/* 410 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 412 */     logger.debug("updateUnit unit = {}", unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeUnit(Unit unit) {
/* 417 */     IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 418 */     String key = unit.getName();
/*     */     try {
/* 420 */       IMapLocker.removeMapping(unitMap, key);
/* 421 */     } catch (RuntimeException e) {
/* 422 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 424 */     logger.debug("removeUnit unit = {}", unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validatePwdExpiration(String login) {
/* 429 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetPwd(String login, String pwd) throws PwdRepeatedException, PwdChangedTooSoonException {}
/*     */ 
/*     */   
/*     */   public void removeToken(String login) {}
/*     */ 
/*     */   
/*     */   public boolean authenticate(String login, String pwd) throws UserNotFoundException {
/* 441 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validateTokenExpiration(String token) {
/* 446 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserForgotPwdToken getToken(String token) throws InvalidTokenException {
/* 451 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTokenValid(UserForgotPwdToken userForgotPwdToken) throws TokenExpiredException {
/* 456 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendOneTimeToken(RecoverPwdDTO dto, String prefixUrl) throws UserNotFoundException, UserEmailUnmatchException {}
/*     */ 
/*     */   
/*     */   public void validateAccount() {
/* 465 */     logger.info("Ignore AD.");
/*     */   }
/*     */   
/*     */   public void notifyAccessDenied(AccessDeniedInfo accessDenied) {}
/*     */   
/*     */   public void sendMfaCode(RecoverMfaCodeDTO dto) throws UserNotFoundException, UserEmailUnmatchException, Exception {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\AccountAdServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */