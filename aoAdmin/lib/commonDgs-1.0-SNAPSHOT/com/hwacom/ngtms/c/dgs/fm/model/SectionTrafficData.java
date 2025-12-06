/*     */ package com.hwacom.ngtms.c.dgs.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import com.hwacom.ngtms.c.dgs.shared.WeightType;
/*     */ import com.hwacom.ngtms.c.shared.DateClassType;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ import javax.persistence.Transient;
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
/*     */ public class SectionTrafficData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9042710913683027146L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   protected String id;
/*     */   @Comment("資料時間")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date dataTime;
/*     */   @Comment("車流量")
/*  41 */   private Integer carVolume = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("車速")
/*  45 */   private Integer carSpeed = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("平均佔有率")
/*  49 */   private Integer averageOccupancy = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("壅塞壅塞程度（壅塞程度表的LEVEL，預設0）")
/*  53 */   private Integer level = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("VD旅行時間")
/*  57 */   private Integer vdTravelTime = Integer.valueOf(0);
/*     */   
/*     */   @Comment("VD車速")
/*  60 */   private Integer vdCarSpeed = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("旅行時間上限（預設0）")
/*  64 */   private Integer upperLimitTime = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("旅行時間下限（預設0）")
/*  68 */   private Integer lowerLimitTime = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("路段編號")
/*     */   @Column(nullable = false)
/*     */   private String sectionId;
/*     */ 
/*     */   
/*     */   @Comment("旅行時間（秒）（預設0）")
/*  77 */   private Integer travelTime = Integer.valueOf(0);
/*     */   @Comment("權重編號(1~16)")
/*     */   @Enumerated(EnumType.STRING)
/*  80 */   private WeightType weightType = WeightType.Type0000;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Comment("VD 旅行時間權重")
/*  86 */   private Integer vdPercentage = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("ETAG 旅行時間權重 ")
/*  90 */   private Integer etagPercentage = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("ETC 旅行時間權重")
/*  94 */   private Integer etcPercentage = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("HTD 旅行時間權重")
/*  98 */   private Integer histPercentage = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("etag旅行時間")
/* 102 */   private Integer etagTravelTime = Integer.valueOf(0);
/*     */   
/*     */   @Comment("etag車速")
/* 105 */   private Integer etagCarSpeed = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("ETC旅行時間 ")
/* 109 */   private Integer etcTravelTime = Integer.valueOf(0);
/*     */   
/*     */   @Comment("ETC車速")
/* 112 */   private Integer etcCarSpeed = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("HTD時間旅行 ")
/* 116 */   private Integer histTravelTime = Integer.valueOf(0);
/*     */   
/*     */   @Comment("HTD車速")
/* 119 */   private Integer histCarSpeed = Integer.valueOf(0);
/*     */   
/*     */   @Comment("這時間是屬於哪個禮拜的星期幾")
/*     */   private DateClassType dataType;
/*     */   
/*     */   @Comment("進匝道總流量")
/*     */   @Transient
/* 126 */   private Integer rampInTotalVolume = Integer.valueOf(-1);
/*     */   
/*     */   @Comment("出匝道總流量")
/*     */   @Transient
/* 130 */   private Integer rampOutTotalVolume = Integer.valueOf(-1);
/*     */   
/*     */   @Comment("起始里程")
/*     */   @Transient
/*     */   private int startMileage;
/*     */   
/*     */   @Comment("結束里程")
/*     */   @Transient
/*     */   private int endMileage;
/*     */   
/*     */   public SectionTrafficData() {}
/*     */   
/*     */   public SectionTrafficData(String sectionId, Date dataTime) {
/* 143 */     this.sectionId = sectionId;
/* 144 */     this.dataTime = dataTime;
/* 145 */     this.id = KeyUtils.getKey(sectionId, dataTime);
/*     */   }
/*     */   
/*     */   public String getSectionId() {
/* 149 */     return this.sectionId;
/*     */   }
/*     */   
/*     */   public void setSectionId(String sectionId) {
/* 153 */     this.sectionId = sectionId;
/*     */   }
/*     */   
/*     */   public int getStartMileage() {
/* 157 */     return this.startMileage;
/*     */   }
/*     */   
/*     */   public void setStartMileage(int startMileage) {
/* 161 */     this.startMileage = startMileage;
/*     */   }
/*     */   
/*     */   public int getEndMileage() {
/* 165 */     return this.endMileage;
/*     */   }
/*     */   
/*     */   public void setEndMileage(int endMileage) {
/* 169 */     this.endMileage = endMileage;
/*     */   }
/*     */   
/*     */   public Integer getTravelTime() {
/* 173 */     return this.travelTime;
/*     */   }
/*     */   
/*     */   public void setTravelTime(Integer travelTime) {
/* 177 */     this.travelTime = travelTime;
/*     */   }
/*     */   
/*     */   public Integer getEtagTravelTime() {
/* 181 */     return this.etagTravelTime;
/*     */   }
/*     */   
/*     */   public void setEtagTravelTime(Integer etagTravelTime) {
/* 185 */     this.etagTravelTime = etagTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getEtcTravelTime() {
/* 189 */     return this.etcTravelTime;
/*     */   }
/*     */   
/*     */   public void setEtcTravelTime(Integer etcTravelTime) {
/* 193 */     this.etcTravelTime = etcTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getHistTravelTime() {
/* 197 */     return this.histTravelTime;
/*     */   }
/*     */   
/*     */   public void setHistTravelTime(Integer histTravelTime) {
/* 201 */     this.histTravelTime = histTravelTime;
/*     */   }
/*     */   
/*     */   public WeightType getWeightType() {
/* 205 */     return this.weightType;
/*     */   }
/*     */   
/*     */   public void setWeightType(WeightType weightType) {
/* 209 */     this.weightType = weightType;
/*     */   }
/*     */   
/*     */   public Integer getVdPercentage() {
/* 213 */     return this.vdPercentage;
/*     */   }
/*     */   
/*     */   public void setVdPercentage(Integer vdPercentage) {
/* 217 */     this.vdPercentage = vdPercentage;
/*     */   }
/*     */   
/*     */   public Integer getEtagPercentage() {
/* 221 */     return this.etagPercentage;
/*     */   }
/*     */   
/*     */   public void setEtagPercentage(Integer etagPercentage) {
/* 225 */     this.etagPercentage = etagPercentage;
/*     */   }
/*     */   
/*     */   public Integer getEtcPercentage() {
/* 229 */     return this.etcPercentage;
/*     */   }
/*     */   
/*     */   public void setEtcPercentage(Integer etcPercentage) {
/* 233 */     this.etcPercentage = etcPercentage;
/*     */   }
/*     */   
/*     */   public Integer getHistPercentage() {
/* 237 */     return this.histPercentage;
/*     */   }
/*     */   
/*     */   public void setHistPercentage(Integer histPercentage) {
/* 241 */     this.histPercentage = histPercentage;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/* 245 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/* 249 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public Integer getCarVolume() {
/* 253 */     return this.carVolume;
/*     */   }
/*     */   
/*     */   public void setCarVolume(Integer carVolume) {
/* 257 */     this.carVolume = carVolume;
/*     */   }
/*     */   
/*     */   public Integer getCarSpeed() {
/* 261 */     return this.carSpeed;
/*     */   }
/*     */   
/*     */   public void setCarSpeed(Integer carSpeed) {
/* 265 */     this.carSpeed = carSpeed;
/*     */   }
/*     */   
/*     */   public Integer getAverageOccupancy() {
/* 269 */     return this.averageOccupancy;
/*     */   }
/*     */   
/*     */   public void setAverageOccupancy(Integer averageOccupancy) {
/* 273 */     this.averageOccupancy = averageOccupancy;
/*     */   }
/*     */   
/*     */   public Integer getLevel() {
/* 277 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(Integer level) {
/* 281 */     this.level = level;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 285 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 289 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Integer getVdTravelTime() {
/* 293 */     return this.vdTravelTime;
/*     */   }
/*     */   
/*     */   public void setVdTravelTime(Integer vdTravelTime) {
/* 297 */     this.vdTravelTime = vdTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getUpperLimitTime() {
/* 301 */     return this.upperLimitTime;
/*     */   }
/*     */   
/*     */   public void setUpperLimitTime(Integer upperLimitTime) {
/* 305 */     this.upperLimitTime = upperLimitTime;
/*     */   }
/*     */   
/*     */   public Integer getLowerLimitTime() {
/* 309 */     return this.lowerLimitTime;
/*     */   }
/*     */   
/*     */   public void setLowerLimitTime(Integer lowerLimitTime) {
/* 313 */     this.lowerLimitTime = lowerLimitTime;
/*     */   }
/*     */   
/*     */   public DateClassType getDataType() {
/* 317 */     return this.dataType;
/*     */   }
/*     */   
/*     */   public void setDataType(DateClassType dataType) {
/* 321 */     this.dataType = dataType;
/*     */   }
/*     */   
/*     */   public Integer getVdCarSpeed() {
/* 325 */     return this.vdCarSpeed;
/*     */   }
/*     */   
/*     */   public void setVdCarSpeed(Integer vdCarSpeed) {
/* 329 */     this.vdCarSpeed = vdCarSpeed;
/*     */   }
/*     */   
/*     */   public Integer getEtagCarSpeed() {
/* 333 */     return this.etagCarSpeed;
/*     */   }
/*     */   
/*     */   public void setEtagCarSpeed(Integer etagCarSpeed) {
/* 337 */     this.etagCarSpeed = etagCarSpeed;
/*     */   }
/*     */   
/*     */   public Integer getEtcCarSpeed() {
/* 341 */     return this.etcCarSpeed;
/*     */   }
/*     */   
/*     */   public void setEtcCarSpeed(Integer etcCarSpeed) {
/* 345 */     this.etcCarSpeed = etcCarSpeed;
/*     */   }
/*     */   
/*     */   public Integer getHistCarSpeed() {
/* 349 */     return this.histCarSpeed;
/*     */   }
/*     */   
/*     */   public void setHistCarSpeed(Integer histCarSpeed) {
/* 353 */     this.histCarSpeed = histCarSpeed;
/*     */   }
/*     */   
/*     */   public Integer getRampInTotalVolume() {
/* 357 */     return this.rampInTotalVolume;
/*     */   }
/*     */   
/*     */   public void setRampInTotalVolume(Integer rampInTotalVolume) {
/* 361 */     this.rampInTotalVolume = rampInTotalVolume;
/*     */   }
/*     */   
/*     */   public Integer getRampOutTotalVolume() {
/* 365 */     return this.rampOutTotalVolume;
/*     */   }
/*     */   
/*     */   public void setRampOutTotalVolume(Integer rampOutTotalVolume) {
/* 369 */     this.rampOutTotalVolume = rampOutTotalVolume;
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
/*     */   public void setAllTravelTime(int vdTravelTime, int etagTravelTime, int etcTravelTime, int histTravelTime) {
/* 382 */     setVdTravelTime(Integer.valueOf(vdTravelTime));
/* 383 */     this.etagTravelTime = Integer.valueOf(etagTravelTime);
/* 384 */     this.etcTravelTime = Integer.valueOf(etcTravelTime);
/* 385 */     this.histTravelTime = Integer.valueOf(histTravelTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 390 */     int prime = 31;
/* 391 */     int result = 1;
/* 392 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 393 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 398 */     if (this == obj) return true; 
/* 399 */     if (obj == null) return false; 
/* 400 */     if (getClass() != obj.getClass()) return false; 
/* 401 */     SectionTrafficData other = (SectionTrafficData)obj;
/* 402 */     if (this.id == null)
/* 403 */     { if (other.id != null) return false;  }
/* 404 */     else if (!this.id.equals(other.id)) { return false; }
/* 405 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 410 */     return "SectionTrafficData [id=" + this.id + ", dataTime=" + this.dataTime + ", carVolume=" + this.carVolume + ", carSpeed=" + this.carSpeed + ", averageOccupancy=" + this.averageOccupancy + ", level=" + this.level + ", vdTravelTime=" + this.vdTravelTime + ", upperLimitTime=" + this.upperLimitTime + ", lowerLimitTime=" + this.lowerLimitTime + ", sectionId=" + this.sectionId + ", travelTime=" + this.travelTime + ", weightType=" + this.weightType + ", vdPercentage=" + this.vdPercentage + ", etagPercentage=" + this.etagPercentage + ", etcPercentage=" + this.etcPercentage + ", histPercentage=" + this.histPercentage + ", etagTravelTime=" + this.etagTravelTime + ", etcTravelTime=" + this.etcTravelTime + ", histTravelTime=" + this.histTravelTime + ", dataType=" + this.dataType + ", startMileage=" + this.startMileage + ", endMileage=" + this.endMileage + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\model\SectionTrafficData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */