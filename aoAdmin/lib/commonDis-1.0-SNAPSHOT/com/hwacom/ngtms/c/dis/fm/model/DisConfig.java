/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.dis.shared.BrightType;
/*     */ import com.hwacom.ngtms.c.dis.shared.RGColorModel;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.MappedSuperclass;
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
/*     */ @MappedSuperclass
/*     */ public class DisConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7226172062805455040L;
/*     */   @Id
/*     */   @Comment("設備編號 ")
/*     */   private String deviceName;
/*     */   @Comment("終端控制器現場操作設定 0 : 允許現場操作 1 : 解除現場操作 ")
/*     */   @Column
/*     */   private Integer opMode;
/*     */   @Comment("亮度控制(1:偵測器控制,2:日照表控制,3:白天,4:黃昏,5:夜晚,6:深夜,預設為2)")
/*     */   @Column
/*     */   @Enumerated(EnumType.STRING)
/*     */   private BrightType brightType;
/*     */   @Comment("日出時段")
/*     */   @Column
/*     */   private Date sunrise;
/*     */   @Comment("日落時段")
/*     */   @Column
/*     */   private Date sunset;
/*     */   @Comment("警示燈顏色 OFF：熄滅,GREEN：綠色,RED：紅色,YELLOW：黃色")
/*     */   @Column
/*     */   @Enumerated(EnumType.STRING)
/*     */   private RGColorModel flashColor;
/*     */   @Comment("警示燈閃爍頻率 值的內容請參考通訊協定")
/*     */   @Column
/*     */   private Integer flashFrequency;
/*     */   
/*     */   public int hashCode() {
/*  65 */     return Objects.hashCode(new Object[] { this.deviceName });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  70 */     if (object instanceof DisConfig) {
/*  71 */       DisConfig that = (DisConfig)object;
/*  72 */       return Objects.equal(this.deviceName, that.deviceName);
/*     */     } 
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  78 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  82 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Integer getOpMode() {
/*  86 */     return this.opMode;
/*     */   }
/*     */   
/*     */   public void setOpMode(Integer opMode) {
/*  90 */     this.opMode = opMode;
/*     */   }
/*     */   
/*     */   public BrightType getBrightType() {
/*  94 */     return this.brightType;
/*     */   }
/*     */   
/*     */   public void setBrightType(BrightType brightType) {
/*  98 */     this.brightType = brightType;
/*     */   }
/*     */   
/*     */   public Date getSunrise() {
/* 102 */     return this.sunrise;
/*     */   }
/*     */   
/*     */   public void setSunrise(Date sunrise) {
/* 106 */     this.sunrise = sunrise;
/*     */   }
/*     */   
/*     */   public Date getSunset() {
/* 110 */     return this.sunset;
/*     */   }
/*     */   
/*     */   public void setSunset(Date sunset) {
/* 114 */     this.sunset = sunset;
/*     */   }
/*     */   
/*     */   public RGColorModel getFlashColor() {
/* 118 */     return this.flashColor;
/*     */   }
/*     */   
/*     */   public void setFlashColor(RGColorModel flashColor) {
/* 122 */     this.flashColor = flashColor;
/*     */   }
/*     */   
/*     */   public Integer getFlashFrequency() {
/* 126 */     return this.flashFrequency;
/*     */   }
/*     */   
/*     */   public void setFlashFrequency(Integer flashFrequency) {
/* 130 */     this.flashFrequency = flashFrequency;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */