/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.RampType;
/*     */ import com.hwacom.ngtms.c.shared.TcProtocolType;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class DeviceTcConfig
/*     */   extends DeviceConfig
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Column(length = 11)
/*     */   @Comment("里程數（公尺）")
/*     */   private Integer milepost;
/*     */   @Transient
/*     */   private String mfccId;
/*     */   @Column(name = "mfcc_id", length = 20)
/*     */   @Comment("預設的MFCC軟體編號")
/*     */   private String defaultMfccId;
/*     */   @Column(length = 1)
/*     */   @Enumerated(EnumType.STRING)
/*     */   @Comment("匝道類型")
/*     */   private RampType rampType;
/*     */   @Column(length = 10)
/*     */   @Enumerated(EnumType.STRING)
/*     */   @Comment("通訊協定版本")
/*     */   private TcProtocolType protocolType;
/*     */   @Column(length = 20)
/*     */   @Comment("路線編號")
/*     */   private String lineId;
/*     */   @Column(length = 3)
/*     */   @Enumerated(EnumType.STRING)
/*     */   @Comment("方向")
/*     */   private Direction direction;
/*     */   @Column(length = 20)
/*     */   @Comment("路段編號")
/*     */   private String sectionId;
/*     */   @Comment("")
/*     */   private String location;
/*     */   @Comment("是否顯示於EMS中")
/*     */   private Boolean showInEms;
/*     */   
/*     */   public DeviceTcConfig() {}
/*     */   
/*     */   public DeviceTcConfig(String deviceName, String deviceType, String ip, Integer port, String project) {
/*  78 */     setDeviceName(deviceName);
/*  79 */     setDeviceType(deviceType);
/*  80 */     setIp(ip);
/*  81 */     setPort(port);
/*  82 */     setProject(project);
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
/*     */   public DeviceTcConfig(String deviceName, Boolean enable, String deviceType, Integer milepost, String ip, Integer port, String defaultMfccId, String project, String lineId, String sectionId, Direction direction, String location) {
/*  98 */     setDeviceName(deviceName);
/*  99 */     setEnable(enable);
/* 100 */     setDeviceType(deviceType);
/* 101 */     this.milepost = milepost;
/* 102 */     setIp(ip);
/* 103 */     setPort(port);
/* 104 */     this.defaultMfccId = defaultMfccId;
/* 105 */     setProject(project);
/* 106 */     this.lineId = lineId;
/* 107 */     this.sectionId = sectionId;
/* 108 */     this.direction = direction;
/* 109 */     this.location = location;
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
/*     */   public DeviceTcConfig(String deviceName, String tcId, Boolean enable, String deviceType, Integer milepost, String ip, Integer port, String defaultMfccId, String project, String lineId, String sectionId, Direction direction, String location, RampType rampType) {
/* 127 */     setDeviceName(deviceName);
/* 128 */     setEnable(enable);
/* 129 */     setDeviceType(deviceType);
/* 130 */     this.milepost = milepost;
/* 131 */     setIp(ip);
/* 132 */     setPort(port);
/* 133 */     this.defaultMfccId = defaultMfccId;
/* 134 */     setProject(project);
/* 135 */     this.lineId = lineId;
/* 136 */     this.sectionId = sectionId;
/* 137 */     this.direction = direction;
/* 138 */     this.location = location;
/* 139 */     this.rampType = rampType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceTcConfig(String deviceName, String deviceType, String ip, Integer port, String project, Boolean showInEms) {
/* 149 */     setDeviceName(deviceName);
/* 150 */     setDeviceType(deviceType);
/* 151 */     setIp(ip);
/* 152 */     setPort(port);
/* 153 */     setProject(project);
/* 154 */     this.showInEms = showInEms;
/*     */   }
/*     */   
/*     */   public Integer getMilepost() {
/* 158 */     return this.milepost;
/*     */   }
/*     */   
/*     */   public void setMilepost(Integer milepost) {
/* 162 */     this.milepost = milepost;
/*     */   }
/*     */   
/*     */   public String getMfccId() {
/* 166 */     return this.mfccId;
/*     */   }
/*     */   
/*     */   public void setMfccId(String mfccId) {
/* 170 */     this.mfccId = mfccId;
/*     */   }
/*     */   
/*     */   public String getDefaultMfccId() {
/* 174 */     return this.defaultMfccId;
/*     */   }
/*     */   
/*     */   public void setDefaultMfccId(String defaultMfccId) {
/* 178 */     this.defaultMfccId = defaultMfccId;
/*     */   }
/*     */   
/*     */   public RampType getRampType() {
/* 182 */     return this.rampType;
/*     */   }
/*     */   
/*     */   public void setRampType(RampType rampType) {
/* 186 */     this.rampType = rampType;
/*     */   }
/*     */   
/*     */   public TcProtocolType getProtocolType() {
/* 190 */     return this.protocolType;
/*     */   }
/*     */   
/*     */   public void setProtocolType(TcProtocolType protocolType) {
/* 194 */     this.protocolType = protocolType;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/* 198 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/* 202 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/* 206 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 210 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public String getSectionId() {
/* 214 */     return this.sectionId;
/*     */   }
/*     */   
/*     */   public void setSectionId(String sectionId) {
/* 218 */     this.sectionId = sectionId;
/*     */   }
/*     */   
/*     */   public <T extends Enum<T>> T getLocation(Class<T> t) {
/* 222 */     return Enum.valueOf(t, this.location);
/*     */   }
/*     */   
/*     */   public String getLocation() {
/* 226 */     return this.location;
/*     */   }
/*     */   
/*     */   public void setLocation(String location) {
/* 230 */     this.location = location;
/*     */   }
/*     */   
/*     */   public Boolean getShowInEms() {
/* 234 */     return this.showInEms;
/*     */   }
/*     */   
/*     */   public void setShowInEms(Boolean showInEms) {
/* 238 */     this.showInEms = showInEms;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 243 */     return "DeviceTcConfig [milepost=" + this.milepost + ", mfccId=" + this.mfccId + ", defaultMfccId=" + this.defaultMfccId + ", rampType=" + this.rampType + ", protocolType=" + this.protocolType + ", lineId=" + this.lineId + ", direction=" + this.direction + ", sectionId=" + this.sectionId + ", location=" + this.location + ", showInEms=" + this.showInEms + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceTcConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */