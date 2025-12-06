/*     */ package com.hwacom.ngtms.c.dis.fm.service;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.TransactionalMap;
/*     */ import com.hazelcast.mapreduce.aggregation.Aggregations;
/*     */ import com.hazelcast.mapreduce.aggregation.Supplier;
/*     */ import com.hazelcast.transaction.TransactionContext;
/*     */ import com.hazelcast.transaction.TransactionOptions;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.dis.fm.hz.DisHzMap;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisFullTextConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisFullTextType;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisGraphic;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisGraphicConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisMessageCompareLog;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisPhraseConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisPhraseType;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisSunshineTable;
/*     */ import com.hwacom.ngtms.c.dis.fm.repository.DisGraphicConfigRepository;
/*     */ import com.hwacom.ngtms.c.dis.fm.repository.DisGraphicRepository;
/*     */ import com.hwacom.ngtms.c.dis.fm.repository.DisMessageCompareLogRepository;
/*     */ import com.hwacom.ngtms.c.dis.shared.BrightType;
/*     */ import com.hwacom.ngtms.c.dis.shared.RGColorModel;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.invoke.SerializedLambda;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
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
/*     */ 
/*     */ 
/*     */ public abstract class DisDataProcessor<T extends DisConfig>
/*     */ {
/*  64 */   protected Logger logger = LoggerFactory.getLogger(DisDataProcessor.class);
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   protected DisGraphicConfigRepository disGraphicConfigRepository;
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   protected DisGraphicRepository disGraphicRepository;
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   protected DisMessageCompareLogRepository disMessageCompareLogRepository;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSunshineTable(List<DisSunshineTable> disSunshineConfigs) {
/*  85 */     IMap<String, DisSunshineTable> disSunshineTableMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.SunshineTable);
/*  86 */     for (DisSunshineTable disSunshineConfig : disSunshineConfigs) {
/*  87 */       disSunshineTableMap.put(disSunshineConfig.getSunDuration(), disSunshineConfig);
/*     */     }
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
/*     */   public void saveSunshineDuration(String deviceName, Date sunrise, Date sunset) {
/*     */     try {
/* 103 */       if (getDisConfig().tryLock(deviceName, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 105 */           DisConfig disConfig = (DisConfig)getDisConfig().get(deviceName);
/* 106 */           if (disConfig != null) {
/* 107 */             disConfig.setSunrise(sunrise);
/* 108 */             disConfig.setSunset(sunset);
/* 109 */             getDisConfig().put(deviceName, disConfig);
/*     */           } 
/* 111 */         } catch (Exception e) {
/* 112 */           this.logger.error("saveSunshineDuration ", e);
/*     */         } finally {
/* 114 */           getDisConfig().unlock(deviceName);
/*     */         } 
/*     */       }
/* 117 */     } catch (Exception e) {
/* 118 */       this.logger.error("saveSunshineDuration ", e);
/*     */     } 
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
/*     */   public void saveBrightType(String deviceName, BrightType brightType) {
/*     */     try {
/* 132 */       if (getDisConfig().tryLock(deviceName, 3L, TimeUnit.SECONDS)) {
/*     */         try {
/* 134 */           DisConfig disConfig = (DisConfig)getDisConfig().get(deviceName);
/*     */           
/* 136 */           if (disConfig != null) {
/* 137 */             disConfig.setBrightType(brightType);
/* 138 */             this.logger.debug("disConfig {}", disConfig);
/* 139 */             getDisConfig().put(deviceName, disConfig);
/*     */           } 
/* 141 */         } catch (Exception e) {
/* 142 */           this.logger.error("saveBrightType ", e);
/*     */         } finally {
/* 144 */           getDisConfig().unlock(deviceName);
/*     */         } 
/*     */       }
/* 147 */     } catch (Exception e) {
/* 148 */       this.logger.error("saveBrightType ", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveFlashConfig(String deviceName, RGColorModel color, Integer freq) {
/*     */     try {
/* 161 */       if (getDisConfig().tryLock(deviceName, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 163 */           DisConfig disConfig = (DisConfig)getDisConfig().get(deviceName);
/* 164 */           if (disConfig != null) {
/* 165 */             disConfig.setFlashColor(color);
/* 166 */             disConfig.setFlashFrequency(freq);
/* 167 */             getDisConfig().put(deviceName, disConfig);
/*     */           } 
/* 169 */         } catch (Exception e) {
/* 170 */           this.logger.error("saveBrightType ", e);
/*     */         } finally {
/* 172 */           getDisConfig().unlock(deviceName);
/*     */         } 
/*     */       }
/* 175 */     } catch (Exception e) {
/* 176 */       this.logger.error("saveBrightType ", e);
/*     */     } 
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
/*     */   public void saveCompareLog(String deviceName, String commandMessage, String deviceDisplay) {
/* 189 */     DisMessageCompareLog log = new DisMessageCompareLog();
/* 190 */     log.setDataTime(new Date());
/* 191 */     log.setCommandMessage(commandMessage);
/* 192 */     log.setDeviceDisplay(deviceDisplay);
/* 193 */     log.setDeviceName(deviceName);
/*     */     
/* 195 */     this.disMessageCompareLogRepository.saveAndFlush(log);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean saveDisPhraseConfig(List<DisPhraseConfig> disPhraseConfigs) {
/* 201 */     boolean saveAll = false;
/*     */ 
/*     */ 
/*     */     
/* 205 */     TransactionOptions options = (new TransactionOptions()).setTransactionType(TransactionOptions.TransactionType.TWO_PHASE);
/*     */     
/* 207 */     TransactionContext context = HzUtils.getHzInstance().newTransactionContext(options);
/*     */     
/* 209 */     context.beginTransaction();
/*     */     
/* 211 */     TransactionalMap<String, DisPhraseConfig> map = context.getMap(DisHzMap.PhraseConfig.toHzName());
/*     */     
/*     */     try {
/* 214 */       for (DisPhraseConfig data : disPhraseConfigs) {
/* 215 */         if (null == data.getOrderNo()) {
/* 216 */           IMap<String, DisPhraseConfig> disGraphicConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.PhraseConfig);
/*     */ 
/*     */           
/* 219 */           int max = ((Integer)disGraphicConfigMap.aggregate(
/* 220 */               Supplier.all(disPhraseConfig -> disPhraseConfig.getOrderNo()), 
/* 221 */               Aggregations.integerMax())).intValue();
/*     */           
/* 223 */           data.setOrderNo(Integer.valueOf(++max));
/*     */         } 
/*     */         
/* 226 */         map.put(data.getId(), data);
/*     */       } 
/* 228 */       context.commitTransaction();
/* 229 */       saveAll = true;
/*     */     }
/* 231 */     catch (Exception t) {
/* 232 */       this.logger.error("saveDisPhraseConfig", t);
/*     */       
/* 234 */       context.rollbackTransaction();
/*     */     } 
/*     */     
/* 237 */     return Boolean.valueOf(saveAll);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean saveDisPhraseType(DisPhraseType disPhraseType) {
/*     */     try {
/* 243 */       IMap<String, DisPhraseType> disPhraseTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.PhraseType);
/* 244 */       disPhraseTypeMap.put(disPhraseType.getId(), disPhraseType);
/* 245 */       return Boolean.TRUE;
/* 246 */     } catch (Exception e) {
/* 247 */       this.logger.error("saveDisPhraseType ", e);
/* 248 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean saveDisFullTextConfig(DisFullTextConfig disFullTextConfig) {
/* 259 */     IMap<String, DisFullTextConfig> disFullTextConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextConfig);
/* 260 */     IMap<String, DisFullTextType> disFullTextTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextType);
/*     */     
/* 262 */     Preconditions.checkNotNull(disFullTextConfig.getDisFullText(), "未輸入全文庫內容");
/* 263 */     Preconditions.checkNotNull(disFullTextTypeMap
/* 264 */         .get(disFullTextConfig.getDisFullText().getFullTextType()), "FullTextType不存在");
/*     */ 
/*     */     
/*     */     try {
/* 268 */       disFullTextConfigMap.put(disFullTextConfig.getId(), disFullTextConfig);
/* 269 */       return Boolean.TRUE;
/* 270 */     } catch (Exception e) {
/* 271 */       this.logger.error("saveDisFullTextConfig ", e);
/* 272 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean saveDisFullTextType(DisFullTextType disFullTextType) {
/*     */     try {
/* 279 */       IMap<String, DisFullTextType> disFullTextTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextType);
/* 280 */       disFullTextTypeMap.put(disFullTextType.getId(), disFullTextType);
/* 281 */       return Boolean.TRUE;
/* 282 */     } catch (Exception e) {
/* 283 */       this.logger.error("saveDisFullTextType ", e);
/* 284 */       return Boolean.FALSE;
/*     */     } 
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
/*     */   public String saveGraphicConfig(DisGraphicConfig disGraphicConfig, BufferedImage bufferedImage) {
/* 297 */     if (null == disGraphicConfig || null == bufferedImage) {
/* 298 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 303 */     DisGraphic newDisGraphic = new DisGraphic();
/* 304 */     String id = disGraphicConfig.getId();
/* 305 */     newDisGraphic.setDisGraphicConfigId(id);
/*     */     try {
/* 307 */       newDisGraphic.setPicture(bufferedImageToByteArray(bufferedImage, "jpg"));
/* 308 */     } catch (IOException e) {
/* 309 */       this.logger.error("bufferedImageToByteArray 轉換失敗  ", e);
/* 310 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 314 */     this.disGraphicConfigRepository.save(disGraphicConfig);
/* 315 */     this.disGraphicRepository.save(newDisGraphic);
/*     */ 
/*     */     
/* 318 */     return id;
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
/*     */   private byte[] bufferedImageToByteArray(BufferedImage bufferedImage, String formatName) throws IOException {
/* 331 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 332 */     ImageIO.write(bufferedImage, formatName, baos);
/* 333 */     baos.flush();
/* 334 */     byte[] imageInByte = baos.toByteArray();
/* 335 */     baos.close();
/*     */     
/* 337 */     return imageInByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage findImageByDisGraphicConfigId(String disGraphicConfigId) {
/* 343 */     DisGraphic disGraphic = (DisGraphic)this.disGraphicRepository.getOne(disGraphicConfigId);
/*     */     
/* 345 */     if (null != disGraphic && disGraphic.getPicture() != null) {
/*     */       try {
/* 347 */         return convertByteArray2BufferedImage(disGraphic.getPicture());
/* 348 */       } catch (IOException e) {
/* 349 */         this.logger.error(" convertByteArray2BufferedImage error : ", e.getMessage());
/* 350 */         return null;
/*     */       } 
/*     */     }
/* 353 */     this.logger.warn(" can't find DisGraphic, id: {} ", disGraphicConfigId);
/* 354 */     return null;
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
/*     */   private BufferedImage convertByteArray2BufferedImage(byte[] byteArray) throws IOException {
/* 366 */     InputStream in = new ByteArrayInputStream(byteArray);
/*     */     
/* 368 */     return ImageIO.read(in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteDisPhraseType(String disPhraseTypeId) {
/*     */     try {
/* 379 */       IMap<String, DisPhraseType> disPhraseTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.PhraseType);
/* 380 */       disPhraseTypeMap.remove(disPhraseTypeId);
/* 381 */       return Boolean.TRUE.booleanValue();
/* 382 */     } catch (Exception e) {
/* 383 */       this.logger.error("deleteDisPhraseType ", e);
/* 384 */       return Boolean.FALSE.booleanValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteDisPhraseConfig(String disPhraseConfigId) {
/* 396 */     IMap<String, DisPhraseConfig> disPhraseConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.PhraseConfig);
/*     */     
/*     */     try {
/* 399 */       disPhraseConfigMap.remove(disPhraseConfigId);
/*     */       
/* 401 */       return Boolean.TRUE.booleanValue();
/* 402 */     } catch (Exception e) {
/* 403 */       this.logger.error("saveDisFullTextConfig ", e);
/* 404 */       return Boolean.FALSE.booleanValue();
/*     */     } 
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
/*     */   public String saveGraphicConfig(DisGraphicConfig disGraphicConfig) {
/* 417 */     IMap<String, DisGraphicConfig> disGraphicConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.GraphicConfig);
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
/* 428 */     if (disGraphicConfig.getGraphicMode().intValue() != 3 && (disGraphicConfig
/* 429 */       .getTcCodeId() == null || disGraphicConfig.getTcCodeId().intValue() == 0)) {
/* 430 */       Integer max = Integer.valueOf(0);
/*     */       
/* 432 */       if (disGraphicConfig.getGraphicMode().intValue() == 2) {
/*     */         
/* 434 */         max = (Integer)disGraphicConfigMap.aggregate(
/* 435 */             Supplier.fromPredicate(t -> (((DisGraphicConfig)t.getValue()).getGraphicMode().intValue() == 2), 
/*     */               
/* 437 */               Supplier.all(d -> d.getTcCodeId())), 
/*     */ 
/*     */ 
/*     */             
/* 441 */             Aggregations.integerMax());
/*     */       } else {
/*     */         
/* 444 */         max = (Integer)disGraphicConfigMap.aggregate(
/* 445 */             Supplier.fromPredicate(t -> 
/*     */               
/* 447 */               (((DisGraphicConfig)t.getValue()).getGraphicMode().intValue() == 0 || ((DisGraphicConfig)t.getValue()).getGraphicMode().intValue() == 1), 
/* 448 */               Supplier.all(d -> d.getTcCodeId())), 
/*     */ 
/*     */ 
/*     */             
/* 452 */             Aggregations.integerMax());
/*     */       } 
/* 454 */       disGraphicConfig.setTcCodeId(max = Integer.valueOf(max.intValue() + 1));
/*     */     } 
/*     */     
/* 457 */     disGraphicConfigMap.put(disGraphicConfig.getId(), disGraphicConfig);
/*     */     
/* 459 */     return disGraphicConfig.getId();
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
/*     */   public boolean deleteGraphicConfig(String disGraphicConfigId) {
/* 472 */     IMap<String, DisGraphicConfig> disGraphicConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.GraphicConfig);
/*     */     try {
/* 474 */       disGraphicConfigMap.delete(disGraphicConfigId);
/* 475 */       return Boolean.TRUE.booleanValue();
/* 476 */     } catch (Exception e) {
/* 477 */       this.logger.error("deleteGraphicConfig ", e);
/* 478 */       return Boolean.FALSE.booleanValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteDisFullTextType(String disFullTextTypeId) {
/* 488 */     IMap<String, DisFullTextType> disFullTextTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextType);
/*     */     try {
/* 490 */       disFullTextTypeMap.delete(disFullTextTypeId);
/* 491 */       return Boolean.TRUE.booleanValue();
/* 492 */     } catch (Exception e) {
/* 493 */       this.logger.error("deleteDisFullTextType ", e);
/* 494 */       return Boolean.FALSE.booleanValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteDisFullTextConfig(String disFullTextConfigId) {
/* 505 */     IMap<String, DisFullTextConfig> disFullTextConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextConfig);
/*     */     
/*     */     try {
/* 508 */       disFullTextConfigMap.delete(disFullTextConfigId);
/* 509 */       return Boolean.TRUE.booleanValue();
/* 510 */     } catch (Exception e) {
/* 511 */       this.logger.error("deleteDisFullTextConfig ", e);
/* 512 */       return Boolean.FALSE.booleanValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract IMap<String, T> getDisConfig();
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisDataProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */