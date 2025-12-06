/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import org.hibernate.annotations.GenericGenerator;
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
/*     */ 
/*     */ @Entity
/*     */ public class DeviceSvgPositionConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8605744145245965495L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @GeneratedValue(generator="system-uuid")
/*     */   @GenericGenerator(name="system-uuid", strategy="uuid2")
/*     */   @Column(length=40)
/*     */   private String id;
/*     */   @Comment("設備編號")
/*     */   @Column(nullable=false)
/*     */   private String deviceName;
/*     */   @Comment("底圖名稱")
/*     */   @Column(nullable=false)
/*     */   private String svgName;
/*     */   @Comment("設備位於圖片上的 x 座標 ")
/*     */   private Float positionX;
/*     */   @Comment("設備位於圖片上的 y 座標")
/*     */   private Float positionY;
/*     */   @Comment("設備的行車方向")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction trafficDirection;
/*     */   @Comment("設備群組名稱")
/*     */   private String deviceGroupName;
/*     */   
/*     */   public String getId()
/*     */   {
/*  64 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  68 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  72 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  76 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getSvgName() {
/*  80 */     return this.svgName;
/*     */   }
/*     */   
/*     */   public void setSvgName(String svgName) {
/*  84 */     this.svgName = svgName;
/*     */   }
/*     */   
/*     */   public Float getPositionX() {
/*  88 */     return this.positionX;
/*     */   }
/*     */   
/*     */   public void setPositionX(Float positionX) {
/*  92 */     this.positionX = positionX;
/*     */   }
/*     */   
/*     */   public Float getPositionY() {
/*  96 */     return this.positionY;
/*     */   }
/*     */   
/*     */   public void setPositionY(Float positionY) {
/* 100 */     this.positionY = positionY;
/*     */   }
/*     */   
/*     */   public Direction getTrafficDirection() {
/* 104 */     return this.trafficDirection;
/*     */   }
/*     */   
/*     */   public void setTrafficDirection(Direction trafficDirection) {
/* 108 */     this.trafficDirection = trafficDirection;
/*     */   }
/*     */   
/*     */   public String getDeviceGroupName() {
/* 112 */     return this.deviceGroupName;
/*     */   }
/*     */   
/*     */   public void setDeviceGroupName(String deviceGroupName) {
/* 116 */     this.deviceGroupName = deviceGroupName;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 121 */     int prime = 31;
/* 122 */     int result = 1;
/* 123 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 124 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 129 */     if (this == obj) return true;
/* 130 */     if (obj == null) return false;
/* 131 */     if (getClass() != obj.getClass()) return false;
/* 132 */     DeviceSvgPositionConfig other = (DeviceSvgPositionConfig)obj;
/* 133 */     if (this.id == null) {
/* 134 */       if (other.id != null) return false;
/* 135 */     } else if (!this.id.equals(other.id)) return false;
/* 136 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceSvgPositionConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */