/*     */ package com.hwacom.ngtms.hcce.fme.manager;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.hwacom.ngtms.hcce.core.exception.InvalidFmeDefinitionException;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinitionPk;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.ProhibitNode;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.repository.FmeDefinitionRepository;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.repository.ProhibitNodeRepository;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeDefTable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.TreeSet;
/*     */ import javax.persistence.EntityManager;
/*     */ import javax.persistence.PersistenceContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class FmeDataServiceImpl
/*     */   implements FmeDataService
/*     */ {
/*  36 */   private static Logger logger = LoggerFactory.getLogger(FmeDataServiceImpl.class);
/*     */   
/*  38 */   public static final Long _DEFINITION_TABLE_INIT_VERSION = Long.valueOf(-9999L);
/*     */   
/*     */   @Autowired
/*     */   private FmeDefinitionRepository fmeDefinitionRepository;
/*     */   @Autowired
/*     */   private ProhibitNodeRepository prohibitNodeRepository;
/*     */   @PersistenceContext(unitName = "NGTMS_OLDB")
/*     */   private EntityManager entityManager;
/*     */   
/*     */   public void verifyFmeDefinitions(List<FmeDefinition> fmeDefinitions) throws InvalidFmeDefinitionException {
/*  48 */     TreeSet<FmeDefinitionPk> chkIds = new TreeSet<>();
/*  49 */     HashMap<String, Integer> classCountingMap = new HashMap<>();
/*  50 */     for (FmeDefinition fmeDefinition : fmeDefinitions) {
/*  51 */       if (StringUtils.isBlank(fmeDefinition.getGroupName()))
/*  52 */         throw new InvalidFmeDefinitionException("groupName field is null, " + fmeDefinition); 
/*  53 */       if (StringUtils.isBlank(fmeDefinition.getFmeName()))
/*  54 */         throw new InvalidFmeDefinitionException("fmeName field is null, " + fmeDefinition); 
/*  55 */       if (StringUtils.isBlank(fmeDefinition.getClassName())) {
/*  56 */         throw new InvalidFmeDefinitionException("className field is blank, " + fmeDefinition);
/*     */       }
/*  58 */       Integer count = classCountingMap.get(fmeDefinition.getClassName());
/*  59 */       if (count == null) {
/*  60 */         count = Integer.valueOf(0);
/*     */       }
/*  62 */       Integer integer1 = count, integer2 = count = Integer.valueOf(count.intValue() + 1);
/*  63 */       classCountingMap.put(fmeDefinition.getClassName(), count);
/*  64 */       FmeDefinitionPk fmeDefinitionPk = fmeDefinition.getPk();
/*  65 */       if (chkIds.contains(fmeDefinitionPk)) {
/*  66 */         throw new InvalidFmeDefinitionException("Duplicated fme name, " + fmeDefinition);
/*     */       }
/*  68 */       chkIds.add(fmeDefinitionPk);
/*     */     } 
/*     */     
/*  71 */     for (String className : classCountingMap.keySet()) {
/*     */       try {
/*  73 */         Constructor<?> c = Class.forName(className).getConstructor(new Class[] { String.class, String.class });
/*  74 */         Object fme = c.newInstance(new Object[] { className, null });
/*  75 */         if (fme instanceof FmeMainBase) {
/*  76 */           FmeMainBase mainBase = (FmeMainBase)fme;
/*  77 */           if (!mainBase.isAllowConcurrentExecution() && ((Integer)classCountingMap.get(className)).intValue() > 1) {
/*  78 */             throw new InvalidFmeDefinitionException(className + " appeared several times in the FME defintion table, but " + className + " does not support concurrent execution");
/*     */           }
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*  85 */         throw new InvalidFmeDefinitionException(className + " must be a subclass of " + FmeMainBase.class
/*  86 */             .getSimpleName());
/*     */       }
/*  88 */       catch (NoSuchMethodException|SecurityException|ClassNotFoundException|InstantiationException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  95 */         throw new InvalidFmeDefinitionException("Failed to run reflective operation when checking " + className, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional("oldbTransactionManager")
/*     */   public void updateFmeDefList(String groupName, List<FmeDefinition> fmeDefinitions) {
/* 104 */     TreeSet<String> validFmeNames = new TreeSet<>();
/* 105 */     TreeSet<Long> validPnIds = new TreeSet<>();
/*     */     
/* 107 */     for (FmeDefinition fmeDefinition : fmeDefinitions) {
/* 108 */       fmeDefinition.setGroupName(groupName);
/* 109 */       this.fmeDefinitionRepository.save(fmeDefinition);
/* 110 */       validFmeNames.add(fmeDefinition.getFmeName());
/*     */       
/* 112 */       if (fmeDefinition.getProhibitNodes() == null) {
/*     */         continue;
/*     */       }
/* 115 */       for (ProhibitNode pn : fmeDefinition.getProhibitNodes()) {
/* 116 */         pn.setGroupName(fmeDefinition.getGroupName());
/* 117 */         pn.setFmeName(fmeDefinition.getFmeName());
/* 118 */         pn = (ProhibitNode)this.prohibitNodeRepository.save(pn);
/* 119 */         validPnIds.add(pn.getId());
/*     */       } 
/*     */     } 
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
/* 133 */     if (validPnIds.size() == 0) {
/* 134 */       this.prohibitNodeRepository.deleteByGroupName(groupName);
/*     */     } else {
/* 136 */       this.prohibitNodeRepository.deleteByIdNotInGroup(groupName, validPnIds);
/*     */     } 
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
/*     */     try {
/* 150 */       if (validFmeNames.size() == 0) {
/* 151 */         logger.debug("Delete fmeDefinition by groupName. groupName: {}", groupName);
/* 152 */         this.fmeDefinitionRepository.deleteByGroupName(groupName);
/*     */       } else {
/* 154 */         logger.debug("Delete fmeDefinition by groupName and validFmeNames. groupName: {}, validFmeNames: {}", groupName, validFmeNames);
/*     */ 
/*     */ 
/*     */         
/* 158 */         this.fmeDefinitionRepository.deleteByNameNotInGroup(groupName, validFmeNames);
/*     */       } 
/* 160 */     } catch (Exception e) {
/* 161 */       logger.error("Delete fme definition failed", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FmeDefTable loadFmeDefTable(String groupName) {
/* 167 */     FmeDefTable fmeDefTable = new FmeDefTable();
/* 168 */     Iterable<ProhibitNode> prohibitNodes = null;
/* 169 */     List<FmeDefinition> fmeDefinitions = this.fmeDefinitionRepository.findByGroupName(groupName);
/* 170 */     for (FmeDefinition fmeDefinition : fmeDefinitions) {
/*     */       
/* 172 */       prohibitNodes = this.prohibitNodeRepository.findByGroupNameAndFmeName(fmeDefinition
/* 173 */           .getGroupName(), fmeDefinition.getFmeName());
/* 174 */       fmeDefinition.setProhibitNodes(Lists.newArrayList(prohibitNodes));
/* 175 */       logger.debug("Load FmeDefition: {}" + fmeDefinition);
/*     */     } 
/* 177 */     verifyFmeDefinitionsOnLoading(fmeDefinitions);
/* 178 */     fmeDefTable.setDefinitions(fmeDefinitions);
/* 179 */     fmeDefTable.setIssueVersion(_DEFINITION_TABLE_INIT_VERSION);
/*     */     
/* 181 */     return fmeDefTable;
/*     */   }
/*     */   
/*     */   private void verifyFmeDefinitionsOnLoading(List<FmeDefinition> fmeDefinitions) {
/* 185 */     HashMap<String, Integer> classCountingMap = new HashMap<>();
/* 186 */     List<FmeDefinition> badFmeDefinitions = new ArrayList<>();
/* 187 */     for (FmeDefinition fmeDefinition : fmeDefinitions) {
/* 188 */       Integer count = classCountingMap.get(fmeDefinition.getClassName());
/* 189 */       if (count == null) {
/* 190 */         count = Integer.valueOf(0);
/*     */       }
/* 192 */       Integer integer1 = count, integer2 = count = Integer.valueOf(count.intValue() + 1);
/* 193 */       classCountingMap.put(fmeDefinition.getClassName(), count);
/*     */     } 
/*     */     
/* 196 */     for (String className : classCountingMap.keySet()) {
/*     */       try {
/* 198 */         Constructor<?> c = Class.forName(className).getConstructor(new Class[] { String.class, String.class });
/* 199 */         Object fme = c.newInstance(new Object[] { className, null });
/* 200 */         if (fme instanceof FmeMainBase) {
/* 201 */           FmeMainBase mainBase = (FmeMainBase)fme;
/* 202 */           if (!mainBase.isAllowConcurrentExecution() && ((Integer)classCountingMap.get(className)).intValue() > 1) {
/* 203 */             logger.warn("{} appeared several times in the FME defintion table, but it does not support concurrent execution", className);
/*     */ 
/*     */             
/* 206 */             for (FmeDefinition fmeDefinition : fmeDefinitions) {
/* 207 */               if (fmeDefinition.getClassName().equals(className) && 
/* 208 */                 !badFmeDefinitions.contains(fmeDefinition)) {
/* 209 */                 badFmeDefinitions.add(fmeDefinition);
/* 210 */                 logger.warn("The FME Definition: {} was discared", fmeDefinition);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           
/* 216 */           logger.warn("{} must be a subclass of {}", className, FmeMainBase.class.getSimpleName());
/* 217 */           for (FmeDefinition fmeDefinition : fmeDefinitions) {
/* 218 */             if (fmeDefinition.getClassName().equals(className) && 
/* 219 */               !badFmeDefinitions.contains(fmeDefinition)) {
/* 220 */               badFmeDefinitions.add(fmeDefinition);
/* 221 */               logger.warn("The FME Definition: {} was discared", fmeDefinition);
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/* 226 */       } catch (NoSuchMethodException|SecurityException|ClassNotFoundException|InstantiationException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 233 */         logger.warn("Failed to run reflective operations when checking {}, error message: {}", className, e
/*     */ 
/*     */             
/* 236 */             .toString());
/* 237 */         for (FmeDefinition fmeDefinition : fmeDefinitions) {
/* 238 */           if (fmeDefinition.getClassName().equals(className) && 
/* 239 */             !badFmeDefinitions.contains(fmeDefinition)) {
/* 240 */             badFmeDefinitions.add(fmeDefinition);
/* 241 */             logger.warn("The FME Definition: {} was discared", fmeDefinition);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 246 */       fmeDefinitions.removeAll(badFmeDefinitions);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\FmeDataServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */