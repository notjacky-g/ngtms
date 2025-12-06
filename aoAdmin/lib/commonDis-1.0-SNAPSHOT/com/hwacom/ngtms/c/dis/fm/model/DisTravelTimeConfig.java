/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import com.hwacom.ngtms.c.dis.shared.RGColorModel;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.persistence.CascadeType;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.OneToMany;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class DisTravelTimeConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7481847933707708477L;
/*     */   private static final String DELIMITER = "-";
/*     */   @Id
/*     */   @Column(length = 41)
/*     */   @Comment("deviceName-boardId")
/*     */   private String id;
/*     */   @Column(nullable = false, length = 40)
/*     */   @Comment("設備編號")
/*     */   private String deviceName;
/*     */   @Comment("旅行時間組別編號，由1開始")
/*     */   private Integer boardId;
/*     */   @Comment("面板ICON編號 (圖型編碼，1-255，0代表不使用)")
/*  54 */   private Integer disGraphicConfigId = Integer.valueOf(0);
/*     */   
/*     */   @Column(length = 255)
/*     */   @Comment("訊息")
/*     */   private String message;
/*     */   
/*     */   @Column(length = 255)
/*     */   @Comment("前景色 rgcolor model")
/*     */   private String foregroundColor;
/*     */   
/*     */   @Column(length = 255)
/*     */   @Comment("背景色 rgcolor model")
/*     */   private String backgroundColor;
/*     */   
/*     */   @Transient
/*  69 */   private List<RGColorModel> foregroundColorModel = new ArrayList<>();
/*     */   @Transient
/*  71 */   private List<RGColorModel> backgroundColorModel = new ArrayList<>();
/*     */ 
/*     */   
/*     */   @Comment("旅行時間上限(分) -1代表的是預設值 預設值的意思是設備里程至目的地距離分別用速限上下值作計算")
/*  75 */   private Integer maxTravelTime = Integer.valueOf(-1);
/*     */ 
/*     */   
/*     */   @Comment("旅行時間下限(分) -1代表的是預設值 預設值的意思是設備里程至目的地距離分別用速限上下值作計算")
/*  79 */   private Integer minTravelTime = Integer.valueOf(-1);
/*     */ 
/*     */   
/*     */   @Comment("延遲時間(分)")
/*  83 */   private Integer delayTime = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("是否顯示旅行時間(旅行時間開關)")
/*     */   private Boolean enable;
/*     */   
/*     */   @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
/*     */   @JoinColumn(name = "parent")
/*     */   private List<DisTravelTimeDivisionConfig> disTravelTimeDivisionConfig;
/*     */   
/*     */   @Comment("超過上限值是否顯示上限值")
/*     */   private Boolean showMaxTravelTime;
/*     */   
/*     */   @Comment("額外旅行時間")
/*     */   private Integer additionalTravelTime;
/*     */ 
/*     */   
/*     */   public DisTravelTimeConfig() {}
/*     */ 
/*     */   
/*     */   public DisTravelTimeConfig(String deviceName, Integer boardId) {
/* 104 */     this.id = KeyUtils.getKey(new Object[] { deviceName, boardId });
/* 105 */     this.deviceName = deviceName;
/* 106 */     this.boardId = boardId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Objects.hashCode(new Object[] { this.id });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 116 */     if (object instanceof DisTravelTimeConfig) {
/* 117 */       if (!super.equals(object)) return false; 
/* 118 */       DisTravelTimeConfig that = (DisTravelTimeConfig)object;
/* 119 */       return Objects.equal(this.id, that.id);
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return "DisTravelTimeConfig [id=" + this.id + ", deviceName=" + this.deviceName + ", boardId=" + this.boardId + ", disGraphicConfigId=" + this.disGraphicConfigId + ", message=" + this.message + ", foregroundColor=" + this.foregroundColor + ", backgroundColor=" + this.backgroundColor + ", foregroundColorModel=" + this.foregroundColorModel + ", backgroundColorModel=" + this.backgroundColorModel + ", maxTravelTime=" + this.maxTravelTime + ", minTravelTime=" + this.minTravelTime + ", delayTime=" + this.delayTime + ", enable=" + this.enable + ", disTravelTimeDivisionConfig=" + this.disTravelTimeDivisionConfig + ", showMaxTravelTime=" + this.showMaxTravelTime + "]";
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
/*     */   public List<DisTravelTimeDivisionConfig> getDisTravelTimeDivisionConfig() {
/* 160 */     return this.disTravelTimeDivisionConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisTravelTimeDivisionConfig(List<DisTravelTimeDivisionConfig> disTravelTimeDivisionConfig) {
/* 165 */     this.disTravelTimeDivisionConfig = disTravelTimeDivisionConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 170 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 175 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDeviceName() {
/* 180 */     return this.deviceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 185 */     this.deviceName = deviceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getBoardId() {
/* 190 */     return this.boardId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBoardId(Integer boardId) {
/* 195 */     this.boardId = boardId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getDisGraphicConfigId() {
/* 200 */     return this.disGraphicConfigId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisGraphicConfigId(Integer disGraphicConfigId) {
/* 205 */     this.disGraphicConfigId = disGraphicConfigId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 210 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/* 215 */     this.message = message;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isEnable() {
/* 220 */     return this.enable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnable(Boolean enable) {
/* 225 */     this.enable = enable;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getDelaySecond() {
/* 230 */     return this.delayTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDelaySecond(Integer delaySecond) {
/* 235 */     this.delayTime = delaySecond;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getEnable() {
/* 240 */     return this.enable;
/*     */   }
/*     */   
/*     */   String getForegroundColor() {
/* 244 */     return this.foregroundColor;
/*     */   }
/*     */   
/*     */   void setForegroundColor(String foregroundColor) {
/* 248 */     this.foregroundColor = foregroundColor;
/*     */   }
/*     */   
/*     */   String getBackgroundColor() {
/* 252 */     return this.backgroundColor;
/*     */   }
/*     */   
/*     */   void setBackgroundColor(String backgroundColor) {
/* 256 */     this.backgroundColor = backgroundColor;
/*     */   }
/*     */   
/*     */   public Integer getMaxTravelTime() {
/* 260 */     return this.maxTravelTime;
/*     */   }
/*     */   
/*     */   public void setMaxTravelTime(Integer maxTravelTime) {
/* 264 */     this.maxTravelTime = maxTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getMinTravelTime() {
/* 268 */     return this.minTravelTime;
/*     */   }
/*     */   
/*     */   public void setMinTravelTime(Integer minTravelTime) {
/* 272 */     this.minTravelTime = minTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getDelayTime() {
/* 276 */     return this.delayTime;
/*     */   }
/*     */   
/*     */   public void setDelayTime(Integer delayTime) {
/* 280 */     this.delayTime = delayTime;
/*     */   }
/*     */   
/*     */   public List<RGColorModel> getForegroundColorModel() {
/* 284 */     return RGColorModel.string2RGColor(this.foregroundColor, "-");
/*     */   }
/*     */   
/*     */   public void setForegroundColorModel(List<RGColorModel> foregroundColorModel) {
/* 288 */     this.foregroundColorModel = foregroundColorModel;
/* 289 */     this.foregroundColor = RGColorModel.rgColor2String(foregroundColorModel, "-");
/*     */   }
/*     */   
/*     */   public List<RGColorModel> getBackgroundColorModel() {
/* 293 */     return RGColorModel.string2RGColor(this.backgroundColor, "-");
/*     */   }
/*     */   
/*     */   public void setBackgroundColorModel(List<RGColorModel> backgroundColorModel) {
/* 297 */     this.backgroundColorModel = backgroundColorModel;
/* 298 */     this.backgroundColor = RGColorModel.rgColor2String(backgroundColorModel, "-");
/*     */   }
/*     */   
/*     */   public Boolean getShowMaxTravelTime() {
/* 302 */     return this.showMaxTravelTime;
/*     */   }
/*     */   
/*     */   public void setShowMaxTravelTime(Boolean showMaxTravelTime) {
/* 306 */     this.showMaxTravelTime = showMaxTravelTime;
/*     */   }
/*     */   
/*     */   public Integer getAdditionalTravelTime() {
/* 310 */     return this.additionalTravelTime;
/*     */   }
/*     */   
/*     */   public void setAdditionalTravelTime(Integer additionalTravelTime) {
/* 314 */     this.additionalTravelTime = additionalTravelTime;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisTravelTimeConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */