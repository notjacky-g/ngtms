/*     */ package com.hwacom.ngtms.common.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.ILock;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
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
/*     */ import com.hwacom.ngtms.common.shared.AccessDeniedInfo;
/*     */ import com.hwacom.ngtms.common.shared.InvalidTokenException;
/*     */ import com.hwacom.ngtms.common.shared.PwdChangedTooSoonException;
/*     */ import com.hwacom.ngtms.common.shared.PwdRepeatedException;
/*     */ import com.hwacom.ngtms.common.shared.TokenExpiredException;
/*     */ import com.hwacom.ngtms.common.shared.TokenNotExpiredException;
/*     */ import com.hwacom.ngtms.common.shared.UserEmailUnmatchException;
/*     */ import com.hwacom.ngtms.common.shared.UserNotFoundException;
/*     */ import com.hwacom.ngtms.common.shared.VerificationFailedException;
/*     */ import com.hwacom.ngtms.common.shared.dto.RecoverMfaCodeDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RecoverPwdDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
/*     */ import com.hwacom.ngtms.common.util.IMapLocker;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import java.io.StringWriter;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.PostConstruct;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.context.annotation.Profile;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ @Profile({"dev"})
/*     */ public class AccountServiceImpl
/*     */   implements AccountService
/*     */ {
/*  68 */   private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
/*     */   
/*     */   @Autowired
/*     */   private UserRepository userRepository;
/*     */   
/*     */   @Autowired
/*     */   private RoleRepository roleRepository;
/*     */   
/*     */   @Autowired
/*     */   private FunctionPermissionRepository functionPermissionRepository;
/*     */   
/*     */   @Autowired
/*     */   private UnitRepository unitRepository;
/*     */   @Autowired
/*     */   private ModelMapper modelMapper;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @Autowired
/*     */   private MailService mailService;
/*     */   @Autowired
/*     */   private MfaService mfaService;
/*     */   @Value("${security.pwd.valid.min.days:1}")
/*     */   private int pwdValidMinDays;
/*     */   @Value("${security.pwd.valid.days:90}")
/*     */   private int pwdValidDays;
/*     */   @Value("${security.one.time.token.valid.minutes:60}")
/*     */   private int oneTimeTokenValidMinutes;
/*     */   @Value("${security.email.template.directory:#{null}}")
/*     */   private String templateDirectory;
/*     */   @Value("${security.account.idle.days:30}")
/*     */   private int accountIdleDays;
/*     */   @Value("${security.access.denied.notify.users:#{null}}")
/*     */   private String[] logins;
/*     */   private Configuration cfg;
/*     */   
/*     */   @PostConstruct
/*     */   public void postConstruct() {
/* 105 */     this.cfg = this.mailService.newConfiguration(Optional.ofNullable(this.templateDirectory));
/*     */   }
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
/*     */   public void init() {}
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
/*     */   private void clearAccountMapData() {
/* 133 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 134 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */     
/* 136 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/*     */     
/* 138 */     ILock iLock = HzUtils.getHzInstance().getLock("myLock");
/* 139 */     iLock.lock();
/*     */     try {
/* 141 */       userMap.clear();
/* 142 */       roleMap.clear();
/* 143 */       functionPermissionMap.clear();
/*     */     } finally {
/* 145 */       iLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadAccountMapData() {
/* 151 */     clearAccountMapData();
/* 152 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public UserBasicInfoDTO findUserBasicInfo(String login) {
/* 157 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 158 */     Optional<User> optUser = Optional.ofNullable(userMap.get(login));
/* 159 */     if (optUser.isPresent()) {
/* 160 */       return (UserBasicInfoDTO)this.modelMapper.map(optUser.get(), UserBasicInfoDTO.class);
/*     */     }
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveUserBasicInfo(UserBasicInfoDTO dto) {
/* 168 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 169 */     Optional<User> optUser = Optional.ofNullable(userMap.get(dto.getLogin()));
/* 170 */     optUser.ifPresent(user -> {
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
/* 183 */     List<User> userList = this.userRepository.findAll();
/* 184 */     logger.debug("findUserAll userList = {}", userList);
/* 185 */     return userList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createUser(User user) {
/* 190 */     user.setLocalAccount(Boolean.valueOf(true));
/* 191 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 192 */     IMapLocker.addOrUpdateMapping(userMap, user.getLogin(), user);
/* 193 */     logger.debug("createUser user = {}", user);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUser(User user) {
/* 198 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*     */     
/* 200 */     String oldPwd = Optional.<Object>ofNullable(userMap.get(user.getLogin())).map(User::getPwd1).orElse(null);
/* 201 */     if (!Objects.equals(user.getPwd1(), oldPwd)) {
/* 202 */       user.setPwdChanged(Boolean.valueOf(false));
/* 203 */       user.setLastPwdChangeTime(null);
/*     */     } 
/* 205 */     IMapLocker.addOrUpdateMapping(userMap, user.getLogin(), user);
/* 206 */     logger.debug("updateUser user = {}", user);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeUser(User user) {
/* 211 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 212 */     IMapLocker.removeMapping(userMap, user.getLogin());
/* 213 */     logger.debug("removeUser user = {}", user);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Role> findRoleAll() {
/* 218 */     List<Role> roleList = this.roleRepository.findAll();
/* 219 */     logger.debug("findRoleAll roleList = {}", roleList);
/* 220 */     return roleList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createRole(Role role) {
/* 225 */     this.roleRepository.save(role);
/*     */     
/* 227 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 228 */     roleMap.put(role.getName(), role);
/* 229 */     logger.debug("createRole role = {}", role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRole(Role role) {
/* 234 */     this.roleRepository.save(role);
/*     */     
/* 236 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 237 */     String key = role.getName();
/*     */     try {
/* 239 */       if (roleMap.tryLock(key, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 241 */           roleMap.delete(key);
/* 242 */           roleMap.put(key, role);
/*     */         } finally {
/* 244 */           roleMap.unlock(key);
/*     */         } 
/*     */       }
/* 247 */     } catch (InterruptedException e) {
/* 248 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 250 */     logger.debug("updateRole role = {}", role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeRole(Role role) {
/* 255 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 256 */     String key = role.getName();
/*     */     try {
/* 258 */       IMapLocker.removeMapping(roleMap, key);
/* 259 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 260 */       for (User user : userMap.values()) {
/* 261 */         if (user.getRoleNames().contains(role.getName())) {
/* 262 */           user.getRoleNames().remove(role.getName());
/* 263 */           updateUser(user);
/*     */         } 
/*     */       } 
/* 266 */     } catch (RuntimeException e) {
/* 267 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 269 */     logger.debug("removeRole role = {}", role);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FunctionPermission> findFunctionPermissionAll() {
/* 274 */     List<FunctionPermission> functionPermissionList = this.functionPermissionRepository.findAll();
/* 275 */     logger.debug("findFunctionPermissionAll functionPermissionList = {}", functionPermissionList);
/* 276 */     return functionPermissionList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createFunctionPermission(FunctionPermission functionPermission) {
/* 281 */     this.functionPermissionRepository.save(functionPermission);
/*     */ 
/*     */     
/* 284 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/*     */     
/* 286 */     functionPermissionMap.put(functionPermission.getName(), functionPermission);
/* 287 */     logger.debug("createFunctionPermission functionPermission = {}", functionPermission);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateFunctionPermission(FunctionPermission functionPermission) {
/* 292 */     this.functionPermissionRepository.save(functionPermission);
/*     */ 
/*     */     
/* 295 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 296 */     String key = functionPermissionMap.getName();
/*     */     try {
/* 298 */       if (functionPermissionMap.tryLock(key, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 300 */           functionPermissionMap.delete(key);
/* 301 */           functionPermissionMap.put(key, functionPermission);
/*     */         } finally {
/* 303 */           functionPermissionMap.unlock(key);
/*     */         } 
/*     */       }
/* 306 */     } catch (InterruptedException e) {
/* 307 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 309 */     logger.debug("updateFunctionPermission functionPermission = {}", functionPermission);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFunctionPermission(FunctionPermission functionPermission) {
/* 314 */     this.functionPermissionRepository.delete(functionPermission);
/*     */     
/* 316 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 317 */     String key = functionPermissionMap.getName();
/*     */     try {
/* 319 */       IMapLocker.removeMapping(functionPermissionMap, key);
/* 320 */     } catch (RuntimeException e) {
/* 321 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 323 */     logger.debug("removeFunctionPermission functionPermission = {}", functionPermission);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Unit> findUnitAll() {
/* 328 */     List<Unit> userList = this.unitRepository.findAll();
/* 329 */     logger.debug("findUnitAll unitList = {}", userList);
/* 330 */     return userList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createUnit(Unit unit) {
/* 335 */     IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 336 */     unitMap.put(unit.getName(), unit);
/* 337 */     logger.debug("createUnit unit = {}", unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUnit(Unit unit) {
/* 342 */     IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 343 */     String key = unit.getName();
/*     */     try {
/* 345 */       IMapLocker.addOrUpdateMapping(unitMap, key, unit);
/* 346 */     } catch (RuntimeException e) {
/* 347 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 349 */     logger.debug("updateUnit unit = {}", unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeUnit(Unit unit) {
/* 354 */     IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 355 */     String key = unit.getName();
/*     */     try {
/* 357 */       IMapLocker.removeMapping(unitMap, key);
/* 358 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 359 */       for (User user : userMap.values()) {
/* 360 */         if (user.getUnitNames().contains(unit.getName())) {
/* 361 */           user.getUnitNames().remove(unit.getName());
/* 362 */           updateUser(user);
/*     */         } 
/*     */       } 
/* 365 */     } catch (RuntimeException e) {
/* 366 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 368 */     logger.debug("removeUnit unit = {}", unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validatePwdExpiration(String login) {
/* 373 */     if (this.pwdValidDays <= 0) {
/* 374 */       return true;
/*     */     }
/* 376 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 377 */     Optional<User> optUser = Optional.ofNullable(userMap.get(login));
/* 378 */     User user = optUser.get();
/* 379 */     if (!user.getLocalAccount().booleanValue()) {
/* 380 */       return true;
/*     */     }
/* 382 */     if (user.getPwd2() == null || !user.getPwdChanged().booleanValue()) {
/* 383 */       return false;
/*     */     }
/* 385 */     long pwdValidMilliseconds = toMilliseconds(this.pwdValidDays);
/* 386 */     logger.debug("Password valid days: '{}', LastPasswordChangeTime: '{}', pwdValidMilliseconds: '{}'", new Object[] {
/*     */           
/* 388 */           Integer.valueOf(this.pwdValidDays), user
/* 389 */           .getLastPwdChangeTime(), 
/* 390 */           Long.valueOf(pwdValidMilliseconds) });
/* 391 */     return (user.getLastPwdChangeTime().getTime() + pwdValidMilliseconds > (new Date()).getTime());
/*     */   }
/*     */   
/*     */   private long toMilliseconds(int days) {
/* 395 */     return (days * 24 * 60 * 60) * 1000L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetPwd(String login, String pwd) throws PwdRepeatedException, PwdChangedTooSoonException {
/* 401 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 402 */     Optional<User> optUser = Optional.ofNullable(userMap.get(login));
/* 403 */     User user = optUser.get();
/* 404 */     if (!isPwdChangeable(user)) {
/* 405 */       throw new PwdChangedTooSoonException();
/*     */     }
/* 407 */     String oldOne = user.getPwd1();
/* 408 */     user.setPwd1(pwd);
/* 409 */     if (Objects.equals(user.getPwd1(), oldOne) || 
/* 410 */       Objects.equals(user.getPwd1(), user.getPwd2()) || 
/* 411 */       Objects.equals(user.getPwd1(), user.getPwd3())) {
/* 412 */       throw new PwdRepeatedException();
/*     */     }
/* 414 */     user.setPwd3(user.getPwd2());
/* 415 */     user.setPwd2(oldOne);
/* 416 */     user.setLastPwdChangeTime(new Date());
/* 417 */     user.setPwdChanged(Boolean.valueOf(true));
/* 418 */     IMapLocker.addOrUpdateMapping(userMap, login, user);
/*     */   }
/*     */   
/*     */   private boolean isPwdChangeable(User user) {
/* 422 */     if (user.getLastPwdChangeTime() == null) {
/* 423 */       return true;
/*     */     }
/* 425 */     return 
/* 426 */       ((new Date()).getTime() - user.getLastPwdChangeTime().getTime() > toMilliseconds(this.pwdValidMinDays));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendOneTimeToken(RecoverPwdDTO dto, String prefixUrl) throws UserNotFoundException, UserEmailUnmatchException, Exception {
/* 433 */     User user = verify(dto.getLogin(), dto.getEmail());
/* 434 */     UserForgotPwdToken token = generateOneTimeToken(user);
/* 435 */     sendTokenEmail(user, token, prefixUrl);
/*     */   }
/*     */ 
/*     */   
/*     */   private User verify(String login, String mail) throws UserNotFoundException, VerificationFailedException, UserEmailUnmatchException {
/* 440 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 441 */     User user = (User)userMap.get(login);
/* 442 */     if (user == null) {
/* 443 */       throw new UserNotFoundException();
/*     */     }
/* 445 */     if (!user.getLocalAccount().booleanValue()) {
/* 446 */       throw new VerificationFailedException();
/*     */     }
/* 448 */     if (!Objects.equals(user.getMail(), mail)) {
/* 449 */       throw new UserEmailUnmatchException();
/*     */     }
/* 451 */     return user;
/*     */   }
/*     */ 
/*     */   
/*     */   private UserForgotPwdToken generateOneTimeToken(User user) {
/* 456 */     IMap<String, UserForgotPwdToken> tokenMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUserForgotPwdToken);
/* 457 */     UserForgotPwdToken token = (UserForgotPwdToken)tokenMap.get(user.getLogin());
/* 458 */     if (token != null && System.currentTimeMillis() < token.getExpirationDate().getTime()) {
/* 459 */       throw new TokenNotExpiredException();
/*     */     }
/* 461 */     if (token == null) {
/* 462 */       token = new UserForgotPwdToken();
/* 463 */       token.setLogin(user.getLogin());
/* 464 */       token.setEmail(user.getMail());
/*     */     } 
/* 466 */     token.setToken(UUID.randomUUID().toString());
/* 467 */     token.setExpirationDate(computeExpirationDate());
/* 468 */     IMapLocker.addOrUpdateMapping(tokenMap, token.getLogin(), token);
/* 469 */     logger.debug("User:'{}', token: '{}'", user.getLogin(), token.getToken());
/* 470 */     return token;
/*     */   }
/*     */   
/*     */   private Date computeExpirationDate() {
/* 474 */     LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(this.oneTimeTokenValidMinutes);
/* 475 */     ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
/* 476 */     return Date.from(zonedDateTime.toInstant());
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendTokenEmail(User user, UserForgotPwdToken token, String prefixUrl) throws Exception {
/* 481 */     String subject = this.messageSourceExt.getMessage("security.pwd.recovery.subject");
/* 482 */     Template template = this.cfg.getTemplate("one-time-token.html");
/* 483 */     StringWriter stringWriter = new StringWriter();
/* 484 */     String url = prefixUrl + token.getToken();
/* 485 */     Map<String, Object> map = Collections.singletonMap("url", url);
/* 486 */     template.process(map, stringWriter);
/* 487 */     this.mailService.sendMail(user, subject, stringWriter.toString());
/* 488 */     logger.debug("Send token email to user: '{}', token: '{}', url: '{}'", new Object[] { user
/*     */           
/* 490 */           .getLogin(), token
/* 491 */           .getToken(), url });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateTokenExpiration(String token) {
/*     */     try {
/* 498 */       UserForgotPwdToken userForgotPwdToken = getToken(token);
/* 499 */       return isTokenValid(userForgotPwdToken);
/* 500 */     } catch (InvalidTokenException|TokenExpiredException e) {
/* 501 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UserForgotPwdToken getToken(String token) throws InvalidTokenException {
/* 508 */     IMap<String, UserForgotPwdToken> tokenMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUserForgotPwdToken);
/* 509 */     EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/* 510 */     PredicateBuilder predicate = entryObject.get("token").equal(token);
/* 511 */     Collection<UserForgotPwdToken> tokens = tokenMap.values((Predicate)predicate);
/* 512 */     if (tokens.size() != 1) {
/* 513 */       throw new InvalidTokenException();
/*     */     }
/* 515 */     UserForgotPwdToken userForgotPwdToken = tokens.iterator().next();
/* 516 */     return userForgotPwdToken;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTokenValid(UserForgotPwdToken userForgotPwdToken) throws TokenExpiredException {
/* 522 */     if (userForgotPwdToken.getExpirationDate().after(new Date())) {
/* 523 */       return true;
/*     */     }
/* 525 */     throw new TokenExpiredException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeToken(String login) {
/* 532 */     IMap<String, UserForgotPwdToken> tokenMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUserForgotPwdToken);
/* 533 */     IMapLocker.removeMapping(tokenMap, login);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean authenticate(String login, String pwd) throws UserNotFoundException {
/* 538 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 539 */     User user = (User)userMap.get(login);
/* 540 */     if (user == null) {
/* 541 */       throw new UserNotFoundException();
/*     */     }
/* 543 */     String originalPwd = user.getPwd1();
/* 544 */     user.setPwd1(pwd);
/* 545 */     return Objects.equals(user.getPwd1(), originalPwd);
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateAccount() {
/* 550 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 551 */     for (User user : userMap.values()) {
/*     */       try {
/* 553 */         if (Objects.equals(Boolean.TRUE, user.getLocalAccount())) {
/* 554 */           boolean enable = !isAccountExpired(user);
/* 555 */           if (enable) {
/* 556 */             enable = !isAccountIdle(user);
/*     */           }
/* 558 */           if (!Objects.equals(Boolean.valueOf(enable), user.getEnable())) {
/* 559 */             logger.info("Account enable: '{}', login: '{}'", Boolean.valueOf(enable), user.getLogin());
/* 560 */             user.setEnable(Boolean.valueOf(enable));
/* 561 */             IMapLocker.addOrUpdateMapping(userMap, user.getLogin(), user);
/*     */           } 
/*     */         } 
/* 564 */       } catch (Exception e) {
/* 565 */         logger.warn("Validate account failed! login: '{}'", user.getLogin(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isAccountExpired(User user) {
/* 571 */     boolean expired = false;
/* 572 */     if (Objects.equals(Boolean.TRUE, user.getCheckExpired())) {
/* 573 */       Date date = new Date();
/* 574 */       expired = (date.before(user.getStartTime()) || date.after(user.getEndTime()));
/*     */     } 
/* 576 */     logger.debug("Account expired: '{}', login: '{}'", Boolean.valueOf(expired), user.getLogin());
/* 577 */     return expired;
/*     */   }
/*     */   
/*     */   boolean isAccountIdle(User user) {
/* 581 */     Date date = user.getLastLoginTime();
/* 582 */     if (date == null) {
/* 583 */       date = user.getUpdateTime();
/*     */     }
/* 585 */     if (date == null && Objects.equals(Boolean.TRUE, user.getCheckExpired())) {
/* 586 */       date = user.getStartTime();
/*     */     }
/* 588 */     boolean idle = true;
/* 589 */     if (date != null) {
/*     */       
/* 591 */       LocalDateTime lastActiveTime = LocalDateTime.ofInstant((new Date(date.getTime())).toInstant(), ZoneId.systemDefault());
/* 592 */       idle = LocalDateTime.now().isAfter(lastActiveTime.plusDays(this.accountIdleDays));
/*     */     } 
/* 594 */     logger.debug("Account idle: '{}', login: '{}'", Boolean.valueOf(idle), user.getLogin());
/* 595 */     return idle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyAccessDenied(AccessDeniedInfo accessDenied) {
/* 600 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*     */     
/* 602 */     String name = Optional.<Object>ofNullable(userMap.get(accessDenied.getLogin())).map(User::getName).orElse("");
/*     */     
/* 604 */     Optional<FunctionPermission> optFunctionPermission = toFunctionPermission(accessDenied.getUri());
/* 605 */     String mailContent = toMailContent(accessDenied, name, optFunctionPermission);
/* 606 */     if (mailContent == null || !optFunctionPermission.isPresent()) {
/* 607 */       logger.debug("Ignore access denied. login: '{}', uri: '{}'", accessDenied
/*     */           
/* 609 */           .getLogin(), accessDenied
/* 610 */           .getUri());
/*     */       return;
/*     */     } 
/* 613 */     for (User user : getNotifyUsers(userMap)) {
/*     */       try {
/* 615 */         this.mailService.sendMail(user, this.messageSourceExt
/* 616 */             .getMessage("security.access.denied.email.subject"), mailContent);
/* 617 */         logger.info("Access denied. email to '{}'", user.getLogin());
/* 618 */       } catch (Exception e) {
/* 619 */         logger.warn("Access denied notification failed!, user: '{}'", user.getLogin(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String toMailContent(AccessDeniedInfo accessDenied, String name, Optional<FunctionPermission> optFunctionPermission) {
/*     */     try {
/* 629 */       StringWriter stringWriter = new StringWriter();
/* 630 */       Template template = this.cfg.getTemplate("access-denied.html");
/* 631 */       Map<String, Object> map = new HashMap<>();
/* 632 */       map.put("login", accessDenied.getLogin());
/* 633 */       map.put("name", name);
/* 634 */       map.put("functionPermissionName", optFunctionPermission
/*     */           
/* 636 */           .<String>map(FunctionPermission::getName).orElse(accessDenied.getUri()));
/* 637 */       template.process(map, stringWriter);
/* 638 */       return stringWriter.toString();
/* 639 */     } catch (Exception e) {
/* 640 */       logger.warn("Generate mail content failed!", e);
/* 641 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Optional<FunctionPermission> toFunctionPermission(String uri) {
/* 647 */     IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 648 */     Optional<FunctionPermission> result = Optional.empty();
/* 649 */     for (FunctionPermission functionPermission : functionPermissionMap.values()) {
/* 650 */       String urlMapping = functionPermission.getUrlMapping();
/* 651 */       if (urlMapping == null) {
/*     */         continue;
/*     */       }
/* 654 */       if (uri.endsWith(urlMapping)) {
/* 655 */         result = Optional.of(functionPermission);
/*     */         break;
/*     */       } 
/*     */     } 
/* 659 */     return result;
/*     */   }
/*     */   
/*     */   private Set<User> getNotifyUsers(IMap<String, User> userMap) {
/* 663 */     if (this.logins == null) {
/* 664 */       return filterByAccountFunctionPermission(userMap.values());
/*     */     }
/* 666 */     Set<User> result = new HashSet<>();
/* 667 */     for (String login : this.logins) {
/* 668 */       Optional.<Object>ofNullable(userMap.get(login)).ifPresent(u -> paramSet.add(u));
/*     */     }
/* 670 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<User> filterByAccountFunctionPermission(Collection<User> users) {
/* 675 */     Set<User> result = new HashSet<>();
/* 676 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 677 */     for (User user : users) {
/* 678 */       for (String roleName : user.getRoleNames()) {
/*     */ 
/*     */ 
/*     */         
/* 682 */         Set<String> functionPermissions = Optional.<Object>ofNullable(roleMap.get(roleName)).map(Role::getFunctionPermissions).orElse(Collections.emptySet());
/* 683 */         if (functionPermissions.contains("ACCOUNT")) {
/* 684 */           result.add(user);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 689 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMfaCode(RecoverMfaCodeDTO dto) throws UserNotFoundException, UserEmailUnmatchException, Exception {
/* 695 */     User user = verify(dto.getLogin(), dto.getEmail());
/* 696 */     this.mfaService.generateSecretIfAbsentAndSendEmail(user.getLogin());
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\AccountServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */