/*    */ package com.hwacom.ngtms.common.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import com.hwacom.ngtms.common.fm.model.FunctionPermission;
/*    */ import com.hwacom.ngtms.common.fm.model.Role;
/*    */ import com.hwacom.ngtms.common.fm.model.Unit;
/*    */ import com.hwacom.ngtms.common.fm.model.User;
/*    */ import com.hwacom.ngtms.common.fm.model.UserForgotPwdToken;
/*    */ import com.hwacom.ngtms.common.shared.AccessDeniedInfo;
/*    */ import com.hwacom.ngtms.common.shared.InvalidTokenException;
/*    */ import com.hwacom.ngtms.common.shared.PwdChangedTooSoonException;
/*    */ import com.hwacom.ngtms.common.shared.PwdRepeatedException;
/*    */ import com.hwacom.ngtms.common.shared.TokenExpiredException;
/*    */ import com.hwacom.ngtms.common.shared.UserEmailUnmatchException;
/*    */ import com.hwacom.ngtms.common.shared.UserNotFoundException;
/*    */ import com.hwacom.ngtms.common.shared.dto.RecoverMfaCodeDTO;
/*    */ import com.hwacom.ngtms.common.shared.dto.RecoverPwdDTO;
/*    */ import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface AccountService
/*    */ {
/*    */   void init();
/*    */   
/*    */   void reloadAccountMapData();
/*    */   
/*    */   default Set<String> getUserFunctionPermissionIds(String login) {
/* 37 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 38 */     Optional<User> optUser = Optional.ofNullable(userMap.get(login));
/* 39 */     Set<String> roleNames = optUser.<Set<String>>map(User::getRoleNames).orElse(new HashSet<>());
/* 40 */     Set<String> functionPermissionIds = new HashSet<>();
/* 41 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 42 */     for (String roleName : roleNames) {
/* 43 */       Optional<Role> optRole = Optional.ofNullable(roleMap.get(roleName));
/* 44 */       functionPermissionIds.addAll(optRole
/* 45 */           .<Collection<? extends String>>map(Role::getFunctionPermissions).orElse(new HashSet<>()));
/*    */     } 
/* 47 */     return functionPermissionIds;
/*    */   }
/*    */   
/*    */   UserBasicInfoDTO findUserBasicInfo(String paramString);
/*    */   
/*    */   void saveUserBasicInfo(UserBasicInfoDTO paramUserBasicInfoDTO);
/*    */   
/*    */   List<User> findUserAll();
/*    */   
/*    */   void createUser(User paramUser);
/*    */   
/*    */   void updateUser(User paramUser);
/*    */   
/*    */   void removeUser(User paramUser);
/*    */   
/*    */   List<Role> findRoleAll();
/*    */   
/*    */   void createRole(Role paramRole);
/*    */   
/*    */   void updateRole(Role paramRole);
/*    */   
/*    */   void removeRole(Role paramRole);
/*    */   
/*    */   List<FunctionPermission> findFunctionPermissionAll();
/*    */   
/*    */   void createFunctionPermission(FunctionPermission paramFunctionPermission);
/*    */   
/*    */   void updateFunctionPermission(FunctionPermission paramFunctionPermission);
/*    */   
/*    */   void removeFunctionPermission(FunctionPermission paramFunctionPermission);
/*    */   
/*    */   List<Unit> findUnitAll();
/*    */   
/*    */   void createUnit(Unit paramUnit);
/*    */   
/*    */   void updateUnit(Unit paramUnit);
/*    */   
/*    */   void removeUnit(Unit paramUnit);
/*    */   
/*    */   boolean validatePwdExpiration(String paramString);
/*    */   
/*    */   void resetPwd(String paramString1, String paramString2) throws PwdRepeatedException, PwdChangedTooSoonException;
/*    */   
/*    */   void sendOneTimeToken(RecoverPwdDTO paramRecoverPwdDTO, String paramString) throws UserNotFoundException, UserEmailUnmatchException, Exception;
/*    */   
/*    */   boolean validateTokenExpiration(String paramString);
/*    */   
/*    */   UserForgotPwdToken getToken(String paramString) throws InvalidTokenException;
/*    */   
/*    */   boolean isTokenValid(UserForgotPwdToken paramUserForgotPwdToken) throws TokenExpiredException;
/*    */   
/*    */   void removeToken(String paramString);
/*    */   
/*    */   boolean authenticate(String paramString1, String paramString2) throws UserNotFoundException;
/*    */   
/*    */   void validateAccount();
/*    */   
/*    */   void notifyAccessDenied(AccessDeniedInfo paramAccessDeniedInfo);
/*    */   
/*    */   void sendMfaCode(RecoverMfaCodeDTO paramRecoverMfaCodeDTO) throws UserNotFoundException, UserEmailUnmatchException, Exception;
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\AccountService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */