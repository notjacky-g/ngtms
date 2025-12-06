/*     */ package com.hwacom.ngtms.c.dgs.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class RoadTunnelDetail
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Comment("子隧道 ID")
/*     */   private String id;
/*     */   @Comment("子隧道名稱")
/*     */   private String tunnelName;
/*     */   @Comment("路段編號 ")
/*     */   private String sectionId;
/*     */   @Comment("開始里程 ")
/*     */   private Integer startMileage;
/*     */   @Comment("結束里程")
/*     */   private Integer endMileage;
/*     */   @Comment("母隧道 ID")
/*     */   private String parentTunnelId;
/*     */   @Comment("RSP的LCS上游要觸動為改道訊息的數目 ")
/*     */   private Integer rspLcsRange;
/*     */   @Comment("RSP的LCS的事故符號是否閃爍(0否1是)")
/*  54 */   private Boolean rspLcsIfflashAccident = Boolean.FALSE;
/*     */ 
/*     */   
/*     */   @Comment("RSP的LCS的改道符號是否閃爍(0否1是)")
/*  58 */   private Boolean rspLcsIfflashChange = Boolean.FALSE;
/*     */ 
/*     */   
/*     */   @Comment("是否連動")
/*  62 */   private Boolean gearing = Boolean.FALSE;
/*     */ 
/*     */   
/*     */   @Comment("連動的 CMS 設備名稱 ")
/*     */   private String cmsDeviceName;
/*     */ 
/*     */   
/*     */   @Comment("CMS 顯示內容")
/*     */   private String cmsMessage;
/*     */ 
/*     */   
/*     */   @Comment("連動的 LCS 設備名稱 ")
/*     */   private String lcsDeviceName;
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  79 */     int hash = 0;
/*  80 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/*     */     
/*  82 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  88 */     if (!(object instanceof RoadTunnelDetail)) {
/*  89 */       return false;
/*     */     }
/*  91 */     RoadTunnelDetail other = (RoadTunnelDetail)object;
/*  92 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/*  93 */       return false;
/*     */     }
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "com.hwacom.ngtms.common.fm.model.RoadTunnelDetail[ id = " + this.id + " ]";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 105 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 110 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTunnelName() {
/* 115 */     return this.tunnelName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTunnelName(String tunnelName) {
/* 120 */     this.tunnelName = tunnelName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSectionId() {
/* 125 */     return this.sectionId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSectionId(String sectionId) {
/* 130 */     this.sectionId = sectionId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getStartMileage() {
/* 135 */     return this.startMileage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStartMileage(Integer startMileage) {
/* 140 */     this.startMileage = startMileage;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getEndMileage() {
/* 145 */     return this.endMileage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndMileage(Integer endMileage) {
/* 150 */     this.endMileage = endMileage;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParentTunnelId() {
/* 155 */     return this.parentTunnelId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParentTunnelId(String parentTunnelId) {
/* 160 */     this.parentTunnelId = parentTunnelId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getRspLcsRange() {
/* 165 */     return this.rspLcsRange;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRspLcsRange(Integer rspLcsRange) {
/* 170 */     this.rspLcsRange = rspLcsRange;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getRspLcsIfflashAccident() {
/* 175 */     return this.rspLcsIfflashAccident;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRspLcsIfflashAccident(Boolean rspLcsIfflashAccident) {
/* 180 */     this.rspLcsIfflashAccident = rspLcsIfflashAccident;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getRspLcsIfflashChange() {
/* 185 */     return this.rspLcsIfflashChange;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRspLcsIfflashChange(Boolean rspLcsIfflashChange) {
/* 190 */     this.rspLcsIfflashChange = rspLcsIfflashChange;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCmsDeviceName() {
/* 195 */     return this.cmsDeviceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCmsDeviceName(String cmsDeviceName) {
/* 200 */     this.cmsDeviceName = cmsDeviceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCmsMessage() {
/* 205 */     return this.cmsMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCmsMessage(String cmsMessage) {
/* 210 */     this.cmsMessage = cmsMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLcsDeviceName() {
/* 215 */     return this.lcsDeviceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLcsDeviceName(String lcsDeviceName) {
/* 220 */     this.lcsDeviceName = lcsDeviceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getGearing() {
/* 225 */     return this.gearing;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGearing(Boolean gearing) {
/* 230 */     this.gearing = gearing;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\model\RoadTunnelDetail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */