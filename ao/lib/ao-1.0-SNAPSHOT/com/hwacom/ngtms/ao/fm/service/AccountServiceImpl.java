/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import com.hwacom.ngtms.common.fm.model.Role;
/*    */ import com.hwacom.ngtms.common.fm.model.User;
/*    */ import com.hwacom.ngtms.common.fm.repository.RoleRepository;
/*    */ import com.hwacom.ngtms.common.fm.service.AccountAdServiceImpl;
/*    */ import com.hwacom.ngtms.common.ldap.SimpleActiveDirectory;
/*    */ import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Profile;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ @Profile({"ao"})
/*    */ public class AccountServiceImpl
/*    */   extends AccountAdServiceImpl
/*    */ {
/* 33 */   private static Logger logger = LoggerFactory.getLogger(AccountAdServiceImpl.class);
/*    */   @Autowired
/*    */   private SimpleActiveDirectory simpleActiveDirectory;
/*    */   @Autowired
/*    */   private RoleRepository roleRepository;
/*    */   
/*    */   public void saveUserBasicInfo(UserBasicInfoDTO dto) {
/* 40 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 41 */     Optional<User> optUser = Optional.ofNullable(userMap.get(dto.getLogin()));
/* 42 */     optUser.ifPresent(user -> {
/*    */           user.setName(dto.getName());
/*    */           user.setDescription(dto.getDescription());
/*    */           user.setMobile(dto.getMobile());
/*    */           user.setMail(dto.getMail());
/*    */           updateUser(user);
/*    */           if (!dto.getOriPassword().equals(dto.getPwd1())) {
/*    */             updatePassword(user.getLogin(), dto.getOriPassword(), dto.getPwd1());
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void updatePassword(String login, String oriPassword, String newPassword) {}
/*    */ 
/*    */   
/*    */   private String sha256(String s) {
/*    */     try {
/* 61 */       MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
/* 62 */       byte[] bytes = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
/* 63 */       StringBuffer result = new StringBuffer();
/* 64 */       for (byte each : bytes) {
/* 65 */         result.append(Integer.toString((each & 0xFF) + 256, 16).substring(1));
/*    */       }
/* 67 */       return result.toString();
/* 68 */     } catch (NoSuchAlgorithmException e) {
/* 69 */       return s;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateRole(Role role) {
/*    */     try {
/* 77 */       this.simpleActiveDirectory.updateGroup(role);
/* 78 */     } catch (RuntimeException e) {
/* 79 */       logger.error("save ad failed.", e);
/*    */     } 
/* 81 */     this.roleRepository.save(role);
/*    */     
/* 83 */     IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 84 */     String key = role.getName();
/*    */     try {
/* 86 */       if (roleMap.tryLock(key, 1L, TimeUnit.SECONDS)) {
/*    */         try {
/* 88 */           roleMap.delete(key);
/* 89 */           roleMap.put(key, role);
/*    */         } finally {
/* 91 */           roleMap.unlock(key);
/*    */         } 
/*    */       }
/* 94 */     } catch (InterruptedException e) {
/* 95 */       logger.error(e.getMessage(), e);
/*    */     } 
/* 97 */     logger.debug("updateRole role = {}", role);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\AccountServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */