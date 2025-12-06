/*     */ package com.hwacom.ngtms.common.util;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.FunctionPermission;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.Unit;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RoleDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UnitDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserDTO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Optional;
/*     */ import org.modelmapper.ModelMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DtoConverter
/*     */ {
/*     */   public static <T> ArrayList<T> convert(Collection<T> list) {
/*  31 */     ArrayList<T> result = new ArrayList<>();
/*  32 */     for (T t : list) {
/*  33 */       result.add(t);
/*     */     }
/*  35 */     return result;
/*     */   }
/*     */   
/*     */   public static UserDTO from(User bean) {
/*  39 */     UserDTO dto = new UserDTO();
/*  40 */     dto.setCheckExpired(bean.getCheckExpired());
/*  41 */     dto.setDescription(bean.getDescription());
/*  42 */     dto.setEnable(bean.getEnable());
/*  43 */     dto.setEndTime(bean.getEndTime());
/*  44 */     dto.setLogin(bean.getLogin());
/*  45 */     dto.setName(bean.getName());
/*  46 */     dto.setPwd1(bean.getPwd1());
/*  47 */     dto.setStartTime(bean.getStartTime());
/*  48 */     dto.setUpdateTime(bean.getUpdateTime());
/*  49 */     dto.setMail(bean.getMail());
/*  50 */     dto.setMobile(bean.getMobile());
/*  51 */     if (bean.getRoleNames() != null) {
/*  52 */       IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*  53 */       for (String roleName : bean.getRoleNames()) {
/*  54 */         Optional.<Object>ofNullable(roleMap.get(roleName))
/*  55 */           .ifPresent(role -> paramUserDTO.addRole(from(role)));
/*     */       }
/*     */     } 
/*  58 */     if (bean.getUnitNames() != null) {
/*  59 */       ModelMapper mapper = new ModelMapper();
/*  60 */       IMap<String, Unit> unitMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUnit);
/*  61 */       for (String unitName : bean.getUnitNames()) {
/*  62 */         Optional.<Object>ofNullable(unitMap.get(unitName))
/*  63 */           .ifPresent(unit -> paramUserDTO.addUnit((UnitDTO)paramModelMapper.map(paramString, UnitDTO.class)));
/*     */       }
/*     */     } 
/*  66 */     return dto;
/*     */   }
/*     */   
/*     */   public static User to(UserDTO dto) {
/*  70 */     return merge(dto, new User());
/*     */   }
/*     */   
/*     */   public static User merge(UserDTO dto, User user) {
/*  74 */     user.setCheckExpired(dto.getCheckExpired());
/*  75 */     user.setDescription(dto.getDescription());
/*  76 */     user.setEnable(dto.getEnable());
/*  77 */     user.setEndTime(dto.getEndTime());
/*  78 */     user.setLogin(dto.getLogin());
/*  79 */     user.setName(dto.getName());
/*  80 */     user.setPwd1(dto.getPwd1());
/*  81 */     user.setStartTime(dto.getStartTime());
/*  82 */     user.setUpdateTime(dto.getUpdateTime());
/*  83 */     user.setMail(dto.getMail());
/*  84 */     user.setMobile(dto.getMobile());
/*  85 */     user.setRoleNames(new HashSet());
/*  86 */     if (dto.getRoles() != null) {
/*  87 */       for (RoleDTO role : dto.getRoles()) {
/*  88 */         user.addRole(to(role));
/*     */       }
/*     */     }
/*  91 */     user.setUnitNames(new HashSet());
/*  92 */     if (dto.getUnits() != null) {
/*  93 */       ModelMapper mapper = new ModelMapper();
/*  94 */       for (UnitDTO unit : dto.getUnits()) {
/*  95 */         user.addUnit((Unit)mapper.map(unit, Unit.class));
/*     */       }
/*     */     } 
/*  98 */     return user;
/*     */   }
/*     */   
/*     */   public static RoleDTO from(Role bean) {
/* 102 */     RoleDTO dto = new RoleDTO();
/* 103 */     dto.setDescription(bean.getDescription());
/* 104 */     dto.setName(bean.getName());
/* 105 */     dto.setUpdateTime(bean.getUpdateTime());
/* 106 */     if (bean.getFunctionPermissions() != null) {
/*     */       
/* 108 */       IMap<String, FunctionPermission> functionPermissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 109 */       for (String id : bean.getFunctionPermissions()) {
/* 110 */         Optional.<Object>ofNullable(functionPermissionMap.get(id))
/* 111 */           .ifPresent(functionPermission -> paramRoleDTO.addFunctionPermission(from(functionPermission)));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 116 */     dto.setEnable(bean.getEnable());
/* 117 */     return dto;
/*     */   }
/*     */   
/*     */   public static Role to(RoleDTO dto) {
/* 121 */     Role bean = new Role();
/* 122 */     bean.setDescription(dto.getDescription());
/* 123 */     bean.setName(dto.getName());
/* 124 */     bean.setUpdateTime(dto.getUpdateTime());
/* 125 */     if (dto.getFunctionPermissions() != null) {
/* 126 */       for (FunctionPermissionDTO functionPermission : dto.getFunctionPermissions()) {
/* 127 */         bean.addFunctionPermission(to(functionPermission));
/*     */       }
/*     */     }
/* 130 */     bean.setEnable(dto.getEnable());
/* 131 */     return bean;
/*     */   }
/*     */   
/*     */   public static FunctionPermissionDTO from(FunctionPermission bean) {
/* 135 */     FunctionPermissionDTO dto = new FunctionPermissionDTO();
/* 136 */     dto.setId(bean.getId());
/* 137 */     dto.setDescription(bean.getDescription());
/* 138 */     dto.setName(bean.getName());
/* 139 */     dto.setUpdateTime(bean.getUpdateTime());
/* 140 */     dto.setLevel(bean.getLevel());
/* 141 */     dto.setParentId(bean.getParentId());
/* 142 */     dto.setUrlMapping(bean.getUrlMapping());
/* 143 */     dto.setSequence(bean.getSequence());
/* 144 */     return dto;
/*     */   }
/*     */   
/*     */   public static FunctionPermission to(FunctionPermissionDTO dto) {
/* 148 */     FunctionPermission bean = new FunctionPermission();
/* 149 */     bean.setId(dto.getId());
/* 150 */     bean.setDescription(dto.getDescription());
/* 151 */     bean.setName(dto.getName());
/* 152 */     bean.setUpdateTime(dto.getUpdateTime());
/* 153 */     bean.setLevel(dto.getLevel());
/* 154 */     bean.setParentId(dto.getParentId());
/* 155 */     bean.setUrlMapping(dto.getUrlMapping());
/* 156 */     bean.setSequence(dto.getSequence());
/* 157 */     return bean;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\commo\\util\DtoConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */