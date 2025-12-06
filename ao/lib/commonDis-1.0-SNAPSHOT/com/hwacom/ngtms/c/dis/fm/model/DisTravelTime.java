/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
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
/*     */ public class DisTravelTime
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9070811299356021202L;
/*     */   @Comment("deviceName+'-'+boardId")
/*     */   private String id;
/*     */   @Comment("設備名稱")
/*     */   private String deviceName;
/*     */   @Comment("面板ID")
/*     */   private Integer boardId;
/*     */   @Comment("旅行時間產生時間")
/*     */   private Date dataTime;
/*     */   @Comment("系統計算旅行時間(不含延遲時間) ")
/*  41 */   private Integer travelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("根據上下限值，系統校正過計算旅行時間(不含延遲時間) ")
/*  45 */   private Integer adjustTravelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment(" 實際下載旅行時間(包含延遲時間)")
/*  49 */   private Integer tcDisplayTravelTime = Integer.valueOf(0);
/*     */   @Comment("超過上下限")
/*  51 */   private boolean outOfBounds = false;
/*     */   
/*     */ 
/*     */ 
/*     */   @Comment(" vd旅行時間")
/*  56 */   private Integer vdTravelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment(" AVI旅行時間")
/*  60 */   private Integer aviTravelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("ETC旅行時間 ")
/*  64 */   private Integer etcTravelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("HTD歷史旅行時間")
/*  68 */   private Integer hisTravelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("分割點名稱(目的地)")
/*     */   private String divisionName;
/*     */   
/*     */ 
/*     */   @Comment("旅行時間上限(單位：分)")
/*  76 */   private Integer maxTravelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("旅行時間下限(單位：分)")
/*  80 */   private Integer minTravelTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("延遲時間(單位：分) offerset r22")
/*  84 */   private Integer delayTime = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  90 */     return Objects.hashCode(new Object[] { Integer.valueOf(super.hashCode()), this.id });
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/*  95 */     if ((object instanceof DisTravelTime)) {
/*  96 */       if (!super.equals(object)) return false;
/*  97 */       DisTravelTime that = (DisTravelTime)object;
/*  98 */       return Objects.equal(this.id, that.id);
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public String getDivisionName() {
/* 104 */     return this.divisionName;
/*     */   }
/*     */   
/*     */   public void setDivisionName(String divisionName) {
/* 108 */     this.divisionName = divisionName;
/*     */   }
/*     */   
/*     */   public Integer getMaxTravelTime() {
/* 112 */     return this.maxTravelTime;
/*     */   }
/*     */   
/*     */   public void setMaxTravelTime(Integer maxTravelTime) {
/* 116 */     this.maxTravelTime = maxTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getMinTravelTime() {
/* 120 */     return this.minTravelTime;
/*     */   }
/*     */   
/*     */   public void setMinTravelTime(Integer minTravelTime) {
/* 124 */     this.minTravelTime = minTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getDelayTime() {
/* 128 */     return this.delayTime;
/*     */   }
/*     */   
/*     */   public void setDelayTime(Integer delayTime) {
/* 132 */     this.delayTime = delayTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getId()
/*     */   {
/* 141 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(String id)
/*     */   {
/* 150 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName()
/*     */   {
/* 155 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName)
/*     */   {
/* 160 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Integer getBoardId()
/*     */   {
/* 165 */     return this.boardId;
/*     */   }
/*     */   
/*     */   public void setBoardId(Integer boardId)
/*     */   {
/* 170 */     this.boardId = boardId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date getDataTime()
/*     */   {
/* 179 */     return (Date)this.dataTime.clone();
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime)
/*     */   {
/* 184 */     this.dataTime = ((Date)dataTime.clone());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Integer getTravelTime()
/*     */   {
/* 193 */     return this.travelTime;
/*     */   }
/*     */   
/*     */   public void setTravelTime(Integer travelTime)
/*     */   {
/* 198 */     this.travelTime = travelTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Integer getTcDisplayTravelTime()
/*     */   {
/* 207 */     return this.tcDisplayTravelTime;
/*     */   }
/*     */   
/*     */   public void setTcDisplayTravelTime(Integer tcDisplayTravelTime)
/*     */   {
/* 212 */     this.tcDisplayTravelTime = tcDisplayTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getVdTravelTime()
/*     */   {
/* 217 */     return this.vdTravelTime;
/*     */   }
/*     */   
/*     */   public void setVdTravelTime(Integer vdTravelTime)
/*     */   {
/* 222 */     this.vdTravelTime = vdTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getAviTravelTime()
/*     */   {
/* 227 */     return this.aviTravelTime;
/*     */   }
/*     */   
/*     */   public void setAviTravelTime(Integer aviTravelTime)
/*     */   {
/* 232 */     this.aviTravelTime = aviTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getEtcTravelTime()
/*     */   {
/* 237 */     return this.etcTravelTime;
/*     */   }
/*     */   
/*     */   public void setEtcTravelTime(Integer etcTravelTime)
/*     */   {
/* 242 */     this.etcTravelTime = etcTravelTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Integer getHisTravelTime()
/*     */   {
/* 251 */     return this.hisTravelTime;
/*     */   }
/*     */   
/*     */   public void setHisTravelTime(Integer hisTravelTime)
/*     */   {
/* 256 */     this.hisTravelTime = hisTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getAdjustTravelTime() {
/* 260 */     return this.adjustTravelTime;
/*     */   }
/*     */   
/*     */   public void setAdjustTravelTime(Integer adjustTravelTime) {
/* 264 */     this.adjustTravelTime = adjustTravelTime;
/*     */   }
/*     */   
/*     */   public boolean isOutOfBounds() {
/* 268 */     return this.outOfBounds;
/*     */   }
/*     */   
/*     */   public void setOutOfBounds(boolean outOfBounds) {
/* 272 */     this.outOfBounds = outOfBounds;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private String id;
/*     */     private String deviceName;
/*     */     private Integer boardId;
/*     */     private Date dataTime;
/*     */     private Integer travelTime;
/*     */     private Integer tcDisplayTravelTime;
/*     */     private Integer vdTravelTime;
/*     */     private Integer aviTravelTime;
/*     */     private Integer etcTravelTime;
/*     */     private Integer hisTravelTime;
/*     */     private String divisionName;
/*     */     private Integer maxTravelTime;
/*     */     private Integer minTravelTime;
/*     */     private Integer delayTime;
/*     */     private Integer adjustTravelTime;
/* 291 */     private boolean outOfBounds = false;
/*     */     
/*     */     public Builder(String deviceName, Integer boardId)
/*     */     {
/* 295 */       this.id = KeyUtils.getKey(new Object[] { deviceName, boardId });
/* 296 */       this.deviceName = deviceName;
/* 297 */       this.boardId = boardId;
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
/*     */     public Builder(String deviceName, Integer boardId, Date dataTime, Integer travelTime, Integer tcDisplayTravelTime, Integer maxTravelTime, Integer minTravelTime, Integer delayTime)
/*     */     {
/* 310 */       this.id = KeyUtils.getKey(new Object[] { deviceName, boardId });
/* 311 */       this.deviceName = deviceName;
/* 312 */       this.boardId = boardId;
/* 313 */       this.dataTime = dataTime;
/* 314 */       this.travelTime = travelTime;
/* 315 */       this.tcDisplayTravelTime = tcDisplayTravelTime;
/* 316 */       this.maxTravelTime = maxTravelTime;
/* 317 */       this.minTravelTime = minTravelTime;
/* 318 */       this.delayTime = delayTime;
/*     */     }
/*     */     
/*     */     public Builder id(String id) {
/* 322 */       this.id = id;
/* 323 */       return this;
/*     */     }
/*     */     
/*     */     public Builder deviceName(String deviceName) {
/* 327 */       this.deviceName = deviceName;
/* 328 */       return this;
/*     */     }
/*     */     
/*     */     public Builder boardId(Integer boardId) {
/* 332 */       this.boardId = boardId;
/* 333 */       return this;
/*     */     }
/*     */     
/*     */     public Builder dataTime(Date dataTime) {
/* 337 */       this.dataTime = dataTime;
/* 338 */       return this;
/*     */     }
/*     */     
/*     */     public Builder travelTime(Integer travelTime) {
/* 342 */       this.travelTime = travelTime;
/* 343 */       return this;
/*     */     }
/*     */     
/*     */     public Builder tcDisplayTravelTime(Integer tcDisplayTravelTime) {
/* 347 */       this.tcDisplayTravelTime = tcDisplayTravelTime;
/* 348 */       return this;
/*     */     }
/*     */     
/*     */     public Builder vdTravelTime(Integer vdTravelTime) {
/* 352 */       this.vdTravelTime = vdTravelTime;
/* 353 */       return this;
/*     */     }
/*     */     
/*     */     public Builder aviTravelTime(Integer aviTravelTime) {
/* 357 */       this.aviTravelTime = aviTravelTime;
/* 358 */       return this;
/*     */     }
/*     */     
/*     */     public Builder etcTravelTime(Integer etcTravelTime) {
/* 362 */       this.etcTravelTime = etcTravelTime;
/* 363 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hisTravelTime(Integer hisTravelTime) {
/* 367 */       this.hisTravelTime = hisTravelTime;
/* 368 */       return this;
/*     */     }
/*     */     
/*     */     public Builder divisionName(String divisionName) {
/* 372 */       this.divisionName = divisionName;
/* 373 */       return this;
/*     */     }
/*     */     
/*     */     public Builder maxTravelTime(Integer maxTravelTime) {
/* 377 */       this.maxTravelTime = maxTravelTime;
/* 378 */       return this;
/*     */     }
/*     */     
/*     */     public Builder minTravelTime(Integer minTravelTime) {
/* 382 */       this.minTravelTime = minTravelTime;
/* 383 */       return this;
/*     */     }
/*     */     
/*     */     public Builder delayTime(Integer delayTime) {
/* 387 */       this.delayTime = delayTime;
/* 388 */       return this;
/*     */     }
/*     */     
/*     */     public Builder adjustTravelTime(Integer adjustTravelTime) {
/* 392 */       this.adjustTravelTime = adjustTravelTime;
/* 393 */       return this;
/*     */     }
/*     */     
/*     */     public Builder outOfBounds(boolean outOfBounds) {
/* 397 */       this.outOfBounds = outOfBounds;
/* 398 */       return this;
/*     */     }
/*     */     
/*     */     public DisTravelTime build() {
/* 402 */       DisTravelTime disTravelTime = new DisTravelTime(null);
/* 403 */       disTravelTime.id = this.id;
/* 404 */       disTravelTime.deviceName = this.deviceName;
/* 405 */       disTravelTime.boardId = this.boardId;
/* 406 */       disTravelTime.dataTime = this.dataTime;
/* 407 */       disTravelTime.travelTime = this.travelTime;
/* 408 */       disTravelTime.tcDisplayTravelTime = this.tcDisplayTravelTime;
/* 409 */       disTravelTime.vdTravelTime = this.vdTravelTime;
/* 410 */       disTravelTime.aviTravelTime = this.aviTravelTime;
/* 411 */       disTravelTime.etcTravelTime = this.etcTravelTime;
/* 412 */       disTravelTime.hisTravelTime = this.hisTravelTime;
/* 413 */       disTravelTime.divisionName = this.divisionName;
/* 414 */       disTravelTime.maxTravelTime = this.maxTravelTime;
/* 415 */       disTravelTime.minTravelTime = this.minTravelTime;
/* 416 */       disTravelTime.delayTime = this.delayTime;
/* 417 */       disTravelTime.adjustTravelTime = this.adjustTravelTime;
/* 418 */       disTravelTime.outOfBounds = this.outOfBounds;
/*     */       
/* 420 */       return disTravelTime;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisTravelTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */