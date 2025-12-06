/*     */ package com.hwacom.ngtms.common.restful;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.annotation.NoOperationLog;
/*     */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.FunctionPermission;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.Unit;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.fm.model.UserForgotPwdToken;
/*     */ import com.hwacom.ngtms.common.fm.service.AccountService;
/*     */ import com.hwacom.ngtms.common.shared.IncorrectPwdException;
/*     */ import com.hwacom.ngtms.common.shared.InvalidTokenException;
/*     */ import com.hwacom.ngtms.common.shared.PwdChangedTooSoonException;
/*     */ import com.hwacom.ngtms.common.shared.PwdRepeatedException;
/*     */ import com.hwacom.ngtms.common.shared.RestfulException;
/*     */ import com.hwacom.ngtms.common.shared.TokenExpiredException;
/*     */ import com.hwacom.ngtms.common.shared.TokenNotExpiredException;
/*     */ import com.hwacom.ngtms.common.shared.UserNotFoundException;
/*     */ import com.hwacom.ngtms.common.shared.dto.AccountParametersDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.ChangePwdDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RecoverMfaCodeDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RecoverPwdDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.ResetPwdDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RoleDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.SingleHintMessageDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UnitDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserDTO;
/*     */ import com.hwacom.ngtms.common.util.AuthUtils;
/*     */ import com.hwacom.ngtms.common.util.DtoConverter;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneId;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/account"})
/*     */ public class AccountRestServiceImpl
/*     */ {
/*  77 */   private static final Logger logger = LoggerFactory.getLogger(AccountRestServiceImpl.class);
/*     */   
/*     */   @Autowired
/*     */   private AccountService accountService;
/*     */   @Autowired
/*     */   protected Environment environment;
/*     */   @Autowired
/*     */   private ModelMapper modelMapper;
/*     */   @Autowired
/*     */   private BaseOpLogger opLogger;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @Value("${security.login.surveillance.account:surveillancengtms84778623}")
/*     */   private String surveillanceAccount;
/*     */   
/*     */   @RequestMapping(value = {"/getUserName"}, produces = {"text/plain;charset=UTF-8"}, method = {RequestMethod.POST})
/*     */   public String getUserName(@RequestBody String login) {
/*     */     try {
/*  95 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*  96 */       return Optional.<Object>ofNullable(userMap.get(TripleDESUtils.decrypt(login)))
/*  97 */         .map(User::getName)
/*  98 */         .orElse(login);
/*  99 */     } catch (RuntimeException ex) {
/* 100 */       logger.warn("Get user name failed!", ex);
/* 101 */       return login;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/retrieveUserBasicInfo/{login}"}, method = {RequestMethod.GET})
/*     */   public UserBasicInfoDTO retrieveUserBasicInfo(@PathVariable("login") String login) {
/*     */     try {
/* 108 */       return this.accountService.findUserBasicInfo(AuthUtils.decryptUserLogin(login));
/* 109 */     } catch (RuntimeException ex) {
/* 110 */       logger.warn("Retrieve user basic info failed!", ex);
/* 111 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/saveUserBasicInfo"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改使用者基本資料")
/*     */   public void saveUserBasicInfo(HttpServletRequest request, @RequestBody UserBasicInfoDTO dto) {
/*     */     try {
/* 119 */       String login = AuthUtils.extractUserLogin(request);
/* 120 */       if (!login.equals(dto.getLogin())) {
/* 121 */         logger.warn("Login value is modified! expect: '{}', actual: '{}'", login, dto.getLogin());
/* 122 */         dto.setLogin(login);
/*     */       } 
/* 124 */       this.accountService.saveUserBasicInfo(dto);
/* 125 */     } catch (RuntimeException ex) {
/* 126 */       logger.warn("Save user basic info failed!", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/getUser/{user:.+}"})
/*     */   public UserDTO getUser(@PathVariable("user") String user) {
/* 132 */     logger.debug("Get user. user : " + user);
/*     */     try {
/* 134 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 135 */       User userData = (User)userMap.get(user);
/* 136 */       if (userData == null) {
/* 137 */         logger.debug("can't find user : '" + user + "'");
/* 138 */         return null;
/*     */       } 
/* 140 */       return toUserDTO(userData);
/* 141 */     } catch (Exception ex) {
/* 142 */       logger.warn("get user failed. user : " + user, ex);
/* 143 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/getUsers"})
/*     */   public List<UserDTO> getUsers() {
/*     */     try {
/* 150 */       logger.debug("Get users.");
/* 151 */       List<UserDTO> list = new ArrayList<>();
/* 152 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 153 */       if (userMap != null && userMap.values() != null) {
/* 154 */         for (User user : userMap.values()) {
/* 155 */           if (Objects.equals(user.getLogin(), this.surveillanceAccount)) {
/*     */             continue;
/*     */           }
/* 158 */           list.add(toUserDTO(user));
/*     */         } 
/*     */       } else {
/* 161 */         logger.error("userList = null!");
/*     */       } 
/* 163 */       return list;
/* 164 */     } catch (Exception e) {
/* 165 */       logger.error("Get users failed.", e);
/* 166 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   private UserDTO toUserDTO(User user) {
/* 171 */     UserDTO userDTO = (UserDTO)this.modelMapper.map(user, UserDTO.class);
/* 172 */     userDTO.setRoles(getRoles(user));
/* 173 */     IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 174 */     for (String unitName : user.getUnitNames()) {
/* 175 */       Unit unit = (Unit)unitMap.get(unitName);
/* 176 */       if (unit == null)
/* 177 */         continue;  userDTO.addUnit((UnitDTO)this.modelMapper.map(unit, UnitDTO.class));
/*     */     } 
/* 179 */     IMap<String, LocalDateTime> userLockTimeMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUserLockTime);
/* 180 */     userDTO.setLockTime(
/* 181 */         Optional.<Object>ofNullable(userLockTimeMap.get(user.getLogin()))
/* 182 */         .map(localDateTime -> Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
/*     */ 
/*     */         
/* 185 */         .orElse(null));
/*     */     
/* 187 */     IMap<String, Integer> userLoginFailureTimes = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUserLoginFailureTimes);
/* 188 */     userDTO.setLoginFailureCount((Integer)userLoginFailureTimes.get(user.getLogin()));
/* 189 */     userDTO.setLastPwdChangeTime(
/* 190 */         Boolean.TRUE.equals(user.getLocalAccount()) ? user.getLastPwdChangeTime() : null);
/* 191 */     return userDTO;
/*     */   }
/*     */   
/*     */   private Set<RoleDTO> getRoles(User user) {
/* 195 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */     
/* 197 */     IMap<String, FunctionPermission> permissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 198 */     Set<RoleDTO> roleSet = new HashSet<>();
/* 199 */     for (String roleName : user.getRoleNames()) {
/* 200 */       Role role = (Role)roleMap.get(roleName);
/* 201 */       if (role != null) {
/* 202 */         RoleDTO roleDTO = (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
/* 203 */         Set<FunctionPermissionDTO> perSet = new HashSet<>();
/* 204 */         for (String perName : role.getFunctionPermissions()) {
/* 205 */           FunctionPermission per = (FunctionPermission)permissionMap.get(perName);
/* 206 */           if (per != null) {
/* 207 */             FunctionPermissionDTO perDTO = (FunctionPermissionDTO)this.modelMapper.map(per, FunctionPermissionDTO.class);
/* 208 */             perSet.add(perDTO);
/*     */           } 
/*     */         } 
/* 211 */         roleDTO.setFunctionPermissions(perSet);
/* 212 */         roleSet.add(roleDTO);
/*     */       } 
/*     */     } 
/* 215 */     return roleSet;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/getUserFunctionPermissions/{encryptedUserLogin}"})
/*     */   public Set<String> getUserFunctionPermissionIds(@PathVariable("encryptedUserLogin") String encryptedUserLogin) {
/*     */     try {
/* 222 */       String login = AuthUtils.decryptUserLogin(encryptedUserLogin);
/* 223 */       return this.accountService.getUserFunctionPermissionIds(login);
/* 224 */     } catch (Exception e) {
/* 225 */       logger.warn("Get user function permission ids failed! encryptedUserLogin: '{}'", encryptedUserLogin, e);
/*     */ 
/*     */ 
/*     */       
/* 229 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/getUserFunctionPermissions"}, method = {RequestMethod.POST})
/*     */   public Set<String> getUserFunctionPermissions(@RequestBody String encryptedUserLogin) {
/*     */     try {
/* 236 */       String login = AuthUtils.decryptUserLogin(encryptedUserLogin);
/* 237 */       return this.accountService.getUserFunctionPermissionIds(login);
/* 238 */     } catch (Exception e) {
/* 239 */       logger.warn("Get user function permission ids failed! encryptedUserLogin: '{}'", encryptedUserLogin, e);
/*     */ 
/*     */ 
/*     */       
/* 243 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/addUser"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("新增使用者")
/*     */   public UserDTO addUser(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 251 */     UserDTO dto = params.getUserDTO();
/*     */     try {
/* 253 */       logger.debug("Add user, dto='{}'", dto);
/* 254 */       this.accountService.createUser(DtoConverter.to(dto));
/* 255 */       return dto;
/* 256 */     } catch (Exception e) {
/* 257 */       logger.error("Add user failed.", e);
/* 258 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/saveUser"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改使用者資料")
/*     */   public UserDTO saveUser(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 266 */     UserDTO dto = params.getUserDTO();
/*     */     try {
/* 268 */       logger.debug("Save user, dto='{}'", dto);
/* 269 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 270 */       User user = Optional.<Object>ofNullable(userMap.get(dto.getLogin())).orElse(new User());
/* 271 */       this.accountService.updateUser(DtoConverter.merge(dto, user));
/* 272 */       return dto;
/* 273 */     } catch (Exception e) {
/* 274 */       logger.error("Save user failed.", e);
/* 275 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deleteUser"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("刪除使用者")
/*     */   public Boolean deleteUser(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 283 */     UserDTO dto = params.getUserDTO();
/*     */     try {
/* 285 */       logger.debug("Delete user, dto='{}'", dto);
/* 286 */       this.accountService.removeUser(DtoConverter.to(dto));
/* 287 */       logger.debug("deleteUser removeUser Finishlly");
/* 288 */       return Boolean.TRUE;
/* 289 */     } catch (Exception e) {
/* 290 */       logger.error("Delete user failed.", e);
/* 291 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/getRoles"})
/*     */   public List<RoleDTO> getRoles() {
/*     */     try {
/* 298 */       logger.debug("Get roles.");
/* 299 */       List<RoleDTO> list = new ArrayList<>();
/* 300 */       IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */       
/* 302 */       IMap<String, FunctionPermission> permissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 303 */       if (roleMap != null && roleMap.values() != null) {
/* 304 */         for (Role role : roleMap.values()) {
/* 305 */           RoleDTO roleDTO = (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
/* 306 */           Set<FunctionPermissionDTO> perSet = new HashSet<>();
/* 307 */           for (String perName : role.getFunctionPermissions()) {
/* 308 */             FunctionPermission per = (FunctionPermission)permissionMap.get(perName);
/* 309 */             if (per != null) {
/* 310 */               FunctionPermissionDTO perDTO = (FunctionPermissionDTO)this.modelMapper.map(per, FunctionPermissionDTO.class);
/* 311 */               perSet.add(perDTO);
/*     */             } 
/*     */           } 
/* 314 */           roleDTO.setFunctionPermissions(perSet);
/* 315 */           list.add(roleDTO);
/*     */         } 
/* 317 */         list.sort(Comparator.comparing(RoleDTO::getName));
/*     */       } else {
/* 319 */         logger.error("roleList = null!");
/*     */       } 
/* 321 */       return list;
/* 322 */     } catch (Exception e) {
/* 323 */       logger.error("Get roles failed.", e);
/* 324 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/addRole"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("新增角色")
/*     */   public RoleDTO addRole(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 332 */     RoleDTO dto = params.getRoleDTO();
/*     */     try {
/* 334 */       logger.debug("Add role, dto='{}'", dto);
/* 335 */       this.accountService.createRole(DtoConverter.to(dto));
/* 336 */       return dto;
/* 337 */     } catch (Exception e) {
/* 338 */       logger.error("Add role failed.", e);
/* 339 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/saveRole"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改角色")
/*     */   public RoleDTO saveRole(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 347 */     RoleDTO dto = params.getRoleDTO();
/*     */     try {
/* 349 */       logger.debug("Save role, dto='{}'", dto);
/* 350 */       this.accountService.updateRole(DtoConverter.to(dto));
/* 351 */       return dto;
/* 352 */     } catch (Exception e) {
/* 353 */       logger.error("Save role failed.", e);
/* 354 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deleteRole"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("刪除角色")
/*     */   public Boolean deleteRole(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 362 */     RoleDTO dto = params.getRoleDTO();
/*     */     try {
/* 364 */       logger.debug("Delete role, dto='{}'", dto);
/* 365 */       this.accountService.removeRole(DtoConverter.to(dto));
/* 366 */       return Boolean.TRUE;
/* 367 */     } catch (Exception e) {
/* 368 */       logger.error("Delete role failed.", e);
/* 369 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/getFunctionPermissions"})
/*     */   public List<FunctionPermissionDTO> getFunctionPermissions() {
/*     */     try {
/* 376 */       logger.debug("Get function permissions.");
/* 377 */       List<FunctionPermissionDTO> list = new ArrayList<>();
/* 378 */       IMap<String, FunctionPermission> map = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 379 */       if (map != null && map.values() != null) {
/* 380 */         list.addAll((Collection<? extends FunctionPermissionDTO>)map.values().stream().map(DtoConverter::from).collect(Collectors.toList()));
/*     */       } else {
/* 382 */         logger.error("functionPermissionList = null!");
/*     */       } 
/* 384 */       return list;
/* 385 */     } catch (Exception e) {
/* 386 */       logger.error("Get function permissions failed.");
/* 387 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/addFunctionPermission"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("新增權限")
/*     */   public FunctionPermissionDTO addFunctionPermission(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 396 */     FunctionPermissionDTO dto = params.getFunctionPermissionDTO();
/*     */     try {
/* 398 */       logger.debug("Add function permission, dto='{}'", dto);
/* 399 */       this.accountService.createFunctionPermission(DtoConverter.to(dto));
/* 400 */       return dto;
/* 401 */     } catch (Exception e) {
/* 402 */       logger.error("Add function permission failed.", e);
/* 403 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/saveFunctionPermission"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改權限")
/*     */   public FunctionPermissionDTO saveFunctionPermission(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 412 */     FunctionPermissionDTO dto = params.getFunctionPermissionDTO();
/*     */     try {
/* 414 */       logger.debug("Save function permission, dto='{}'", dto);
/* 415 */       this.accountService.updateFunctionPermission(DtoConverter.to(dto));
/* 416 */       return dto;
/* 417 */     } catch (Exception e) {
/* 418 */       logger.error("Save function permission failed.", e);
/* 419 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deleteFunctionPermission"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("刪除權限")
/*     */   public Boolean deleteFunctionPermission(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 428 */     FunctionPermissionDTO dto = params.getFunctionPermissionDTO();
/*     */     try {
/* 430 */       logger.debug("Delete function permission, dto='{}'", dto);
/* 431 */       this.accountService.removeFunctionPermission(DtoConverter.to(dto));
/* 432 */       return Boolean.TRUE;
/* 433 */     } catch (Exception e) {
/* 434 */       logger.error("Delete function permission failed.", e);
/* 435 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/checkAuthorization"}, method = {RequestMethod.POST})
/*     */   public Boolean checkAuthorization(@RequestBody AccountParametersDTO params) {
/* 445 */     String user = params.getUser();
/* 446 */     String password = params.getPassword();
/*     */     try {
/* 448 */       logger.debug("Check authorization, user='{}'", user);
/* 449 */       return Boolean.TRUE;
/* 450 */     } catch (Exception e) {
/* 451 */       logger.error("Check authorization failed.", e);
/* 452 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/reloadAccountMapData"})
/*     */   public Boolean reloadAccountMapData() {
/*     */     try {
/* 459 */       logger.debug("Reload account map data.");
/* 460 */       this.accountService.reloadAccountMapData();
/* 461 */       return Boolean.TRUE;
/* 462 */     } catch (Exception e) {
/* 463 */       logger.error("Reload account map data failed.", e);
/* 464 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/getUnits"})
/*     */   public List<UnitDTO> getUnits() {
/*     */     try {
/* 471 */       logger.debug("Get units.");
/* 472 */       List<UnitDTO> list = new ArrayList<>();
/* 473 */       IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/* 474 */       if (unitMap != null && unitMap.values() != null) {
/* 475 */         list.addAll((Collection<? extends UnitDTO>)unitMap
/*     */             
/* 477 */             .values()
/* 478 */             .stream()
/* 479 */             .map(unit -> (UnitDTO)this.modelMapper.map(unit, UnitDTO.class))
/*     */ 
/*     */ 
/*     */             
/* 483 */             .sorted(Comparator.comparing(UnitDTO::getName))
/* 484 */             .collect(Collectors.toList()));
/*     */       } else {
/* 486 */         logger.error("unitList = null!");
/*     */       } 
/* 488 */       return list;
/* 489 */     } catch (Exception e) {
/* 490 */       logger.error("Get units failed.", e);
/* 491 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/addUnit"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("新增單位")
/*     */   public UnitDTO addUnit(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 499 */     UnitDTO dto = params.getUnitDTO();
/*     */     try {
/* 501 */       logger.debug("Add unit, dto='{}'", dto);
/* 502 */       this.accountService.createUnit((Unit)this.modelMapper.map(dto, Unit.class));
/* 503 */       return dto;
/* 504 */     } catch (Exception e) {
/* 505 */       logger.error("Add unit failed.", e);
/* 506 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/saveUnit"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改單位")
/*     */   public UnitDTO saveUnit(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 514 */     UnitDTO dto = params.getUnitDTO();
/*     */     try {
/* 516 */       logger.debug("Save unit, dto='{}'", dto);
/* 517 */       this.accountService.updateUnit((Unit)this.modelMapper.map(dto, Unit.class));
/* 518 */       return dto;
/* 519 */     } catch (Exception e) {
/* 520 */       logger.error("Save unit failed.", e);
/* 521 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deleteUnit"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("刪除單位")
/*     */   public Boolean deleteUnit(HttpServletRequest request, @RequestBody AccountParametersDTO params) {
/* 529 */     UnitDTO dto = params.getUnitDTO();
/*     */     try {
/* 531 */       logger.debug("Delete unit, dto='{}'", dto);
/* 532 */       this.accountService.removeUnit((Unit)this.modelMapper.map(dto, Unit.class));
/* 533 */       logger.debug("deleteUnit removeUnit Finishlly");
/* 534 */       return Boolean.TRUE;
/* 535 */     } catch (Exception e) {
/* 536 */       logger.error("Delete unit failed.", e);
/* 537 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/validatePwdExpiration"})
/*     */   @NoOperationLog
/*     */   public boolean validatePwdExpiration(HttpServletRequest request) {
/*     */     try {
/* 545 */       String login = AuthUtils.extractUserLogin(request);
/* 546 */       boolean valid = this.accountService.validatePwdExpiration(login);
/* 547 */       logger.debug("Login: '{}', valid: '{}'", login, Boolean.valueOf(valid));
/* 548 */       return valid;
/* 549 */     } catch (Exception e) {
/* 550 */       logger.error("Validate pwd expiration failed.", e);
/* 551 */       return true;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/resetPwd"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改密碼(密碼過期)")
/*     */   public SingleHintMessageDTO resetPwd(HttpServletRequest request, @RequestBody String s) {
/* 558 */     String login = AuthUtils.extractUserLogin(request);
/* 559 */     String message = resetPwd(login, s).orElse(null);
/* 560 */     return new SingleHintMessageDTO(message);
/*     */   }
/*     */   
/*     */   private Optional<String> resetPwd(String login, String pwd) {
/*     */     try {
/* 565 */       this.accountService.resetPwd(login, pwd);
/* 566 */       logger.debug("Reset, login: '{}'", login);
/* 567 */       return Optional.empty();
/* 568 */     } catch (PwdRepeatedException e) {
/* 569 */       return Optional.ofNullable(this.messageSourceExt.getMessage("user.reset.pwd.repeated"));
/* 570 */     } catch (PwdChangedTooSoonException e) {
/* 571 */       return Optional.ofNullable(this.messageSourceExt.getMessage("user.reset.pwd.changed.too.soon"));
/* 572 */     } catch (Exception e) {
/* 573 */       logger.error("Reset failed.", e);
/* 574 */       return Optional.ofNullable(this.messageSourceExt
/* 575 */           .getMessage("user.reset.pwd.failure", new Object[] { e.getMessage() }));
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/recoverPwd"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("忘記密碼")
/*     */   public void recoverPwd(@RequestBody RecoverPwdDTO dto) throws Exception {
/*     */     try {
/* 583 */       logger.debug("Recover pwd. login: '{}', email: '{}', href: '{}'", new Object[] { dto
/*     */             
/* 585 */             .getLogin(), dto
/* 586 */             .getEmail(), dto
/* 587 */             .getHref() });
/* 588 */       this.accountService.sendOneTimeToken(dto, toPrefixUrl(dto.getHref()));
/* 589 */     } catch (UserNotFoundException|com.hwacom.ngtms.common.shared.UserEmailUnmatchException|com.hwacom.ngtms.common.shared.VerificationFailedException e) {
/* 590 */       logger.info("Recover pwd failed!", e);
/* 591 */       throw e;
/* 592 */     } catch (TokenNotExpiredException e) {
/* 593 */       throw e;
/* 594 */     } catch (Exception e) {
/* 595 */       logger.warn("Generate one time token failed!", e);
/* 596 */       throw new RestfulException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String toPrefixUrl(String href) {
/* 601 */     if (href.contains("?")) {
/* 602 */       href = href + "&";
/*     */     } else {
/* 604 */       if (href.endsWith("/")) {
/* 605 */         href = href.substring(0, href.length());
/*     */       }
/* 607 */       href = href + "?";
/*     */     } 
/* 609 */     String prefixUrl = href = href + "token=";
/* 610 */     logger.debug("Recover pwd prefix url: '{}'", prefixUrl);
/* 611 */     return prefixUrl;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/validateTokenExpiration/{token}"}, method = {RequestMethod.GET})
/*     */   public boolean validateTokenExpiration(@PathVariable("token") String token) {
/*     */     try {
/* 617 */       boolean result = this.accountService.validateTokenExpiration(token);
/* 618 */       logger.debug("Validate token expiration result: '{}'", Boolean.valueOf(result));
/* 619 */       return result;
/* 620 */     } catch (RuntimeException ex) {
/* 621 */       logger.warn("Validate token expiration failed!", ex);
/* 622 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/resetPwdWithToken"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改密碼(忘記密碼)")
/*     */   public SingleHintMessageDTO resetPwdWithToken(@RequestBody ResetPwdDTO dto) throws Exception {
/*     */     try {
/* 630 */       logger.debug("Reset pwd with token. token: '{}'", dto.getToken());
/* 631 */       UserForgotPwdToken userForgotPwdToken = this.accountService.getToken(dto.getToken());
/* 632 */       if (this.accountService.isTokenValid(userForgotPwdToken)) {
/* 633 */         Optional<String> optResult = resetPwd(userForgotPwdToken.getLogin(), dto.getPwd());
/* 634 */         if (optResult.isPresent()) {
/* 635 */           logger.debug("Reset pwd with token with error. token: '{}'", dto.getToken());
/* 636 */           return new SingleHintMessageDTO(optResult.get());
/*     */         } 
/* 638 */         this.accountService.removeToken(userForgotPwdToken.getLogin());
/* 639 */         logger.debug("Reset pwd with token success and token is removed. token: '{}'", dto
/* 640 */             .getToken());
/* 641 */         return new SingleHintMessageDTO(null);
/*     */       } 
/*     */       
/* 644 */       throw new TokenExpiredException();
/*     */     }
/* 646 */     catch (InvalidTokenException|TokenExpiredException e) {
/* 647 */       logger.info("Reset pwd with token failed! token!", e);
/* 648 */       throw e;
/* 649 */     } catch (Exception e) {
/* 650 */       logger.warn("Reset pwd with token failed! token: '{}'", dto.getToken(), e);
/* 651 */       throw new RestfulException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/changePwd"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("修改密碼")
/*     */   public SingleHintMessageDTO changePwd(HttpServletRequest request, @RequestBody ChangePwdDTO dto) throws Exception {
/* 659 */     String login = null;
/*     */     try {
/* 661 */       login = AuthUtils.extractUserLogin(request);
/* 662 */       boolean authenticated = this.accountService.authenticate(login, dto.getCurrentPwd());
/* 663 */       if (!authenticated) {
/* 664 */         throw new IncorrectPwdException();
/*     */       }
/* 666 */       String message = resetPwd(login, dto.getNewPwd()).orElse(null);
/* 667 */       return new SingleHintMessageDTO(message);
/* 668 */     } catch (IncorrectPwdException e) {
/* 669 */       logger.info("Change pwd failed! Incorrect pwd. login: '{}'", login);
/* 670 */       throw e;
/* 671 */     } catch (Exception e) {
/* 672 */       logger.warn("Change pwd failed! login: '{}'", login, e);
/* 673 */       throw new RestfulException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping({"/isLocalAccount"})
/*     */   @NoOperationLog
/*     */   public boolean isLocalAccount(HttpServletRequest request) {
/*     */     try {
/* 681 */       String login = AuthUtils.extractUserLogin(request);
/* 682 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*     */       
/* 684 */       boolean localAccount = ((Boolean)Optional.<Object>ofNullable(userMap.get(login)).map(User::getLocalAccount).orElse(Boolean.valueOf(false))).booleanValue();
/* 685 */       logger.debug("Login: '{}', localAccount: '{}'", login, Boolean.valueOf(localAccount));
/* 686 */       return localAccount;
/* 687 */     } catch (Exception e) {
/* 688 */       logger.error("Check local account failed.", e);
/* 689 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/recoverMfaCode"}, method = {RequestMethod.POST})
/*     */   @ApiOperation("忘記密碼")
/*     */   public void recoverMfaCode(@RequestBody RecoverMfaCodeDTO dto) throws Exception {
/*     */     try {
/* 697 */       logger.debug("Recover mfa code. login: '{}', email: '{}'", dto.getLogin(), dto.getEmail());
/* 698 */       this.accountService.sendMfaCode(dto);
/* 699 */     } catch (UserNotFoundException|com.hwacom.ngtms.common.shared.UserEmailUnmatchException|com.hwacom.ngtms.common.shared.VerificationFailedException e) {
/* 700 */       logger.info("Recover mfa code failed!", e);
/* 701 */       throw e;
/* 702 */     } catch (Exception e) {
/* 703 */       logger.warn("Recover mfa code failed!", e);
/* 704 */       throw new RestfulException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\restful\AccountRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */