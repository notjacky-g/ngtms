/*     */ package com.hwacom.ngtms.common.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Basic;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Inheritance;
/*     */ import javax.persistence.InheritanceType;
/*     */ import javax.persistence.Lob;
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
/*     */ @Inheritance(strategy=InheritanceType.JOINED)
/*     */ public class DeviceConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7484610547573013938L;
/*     */   @Id
/*     */   @Column(length=40)
/*     */   @Comment("設備名稱")
/*     */   private String deviceName;
/*     */   @Column(length=100)
/*     */   @Comment("UI 顯示可修改的deviceName")
/*     */   private String displayName;
/*     */   @Comment("0=Y 1=N")
/*     */   private Boolean enable;
/*     */   @Column(nullable=false)
/*     */   @Comment("設備種類")
/*     */   private String deviceType;
/*     */   @Column(length=20)
/*     */   @Comment("網路ＩＰ")
/*     */   private String ip;
/*     */   @Column(length=10)
/*     */   @Comment("網路通訊埠")
/*     */   private Integer port;
/*     */   @Column(nullable=false, length=50)
/*     */   @Comment("工程標別")
/*     */   private String project;
/*     */   @Comment("緯度")
/*     */   private Double latitude;
/*     */   @Comment("經度")
/*     */   private Double longitude;
/*     */   @Comment("備註")
/*     */   private String memo;
/*     */   @Lob
/*     */   @Basic(fetch=FetchType.LAZY)
/*     */   @Comment("各類型設備自定義欄位值")
/*     */   private String extend;
/*     */   
/*     */   public String toString()
/*     */   {
/*  77 */     return "DeviceConfig [deviceName=" + this.deviceName + ", displayName=" + this.displayName + ", enable=" + this.enable + ", deviceType=" + this.deviceType + ", ip=" + this.ip + ", port=" + this.port + ", project=" + this.project + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", memo=" + this.memo + "]";
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
/*     */   public DeviceConfig() {}
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
/*     */   public DeviceConfig(String deviceName, String deviceType, String ip, Integer port, String project)
/*     */   {
/* 104 */     this.deviceName = deviceName;
/* 105 */     this.deviceType = deviceType;
/* 106 */     this.ip = ip;
/* 107 */     this.port = port;
/* 108 */     this.project = project;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeviceConfig(String deviceName, Boolean enable, String deviceType, String ip, Integer port, String project)
/*     */   {
/* 118 */     this.deviceName = deviceName;
/* 119 */     this.enable = enable;
/* 120 */     this.deviceType = deviceType;
/* 121 */     this.ip = ip;
/* 122 */     this.port = port;
/* 123 */     this.project = project;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeviceConfig(String deviceName, String tcId, Boolean enable, String deviceType, String ip, Integer port, String project)
/*     */   {
/* 134 */     this.deviceName = deviceName;
/* 135 */     this.enable = enable;
/* 136 */     this.deviceType = deviceType;
/* 137 */     this.ip = ip;
/* 138 */     this.port = port;
/* 139 */     this.project = project;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/* 143 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 147 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Boolean isEnable() {
/* 151 */     if (this.enable != null) return this.enable;
/* 152 */     return Boolean.FALSE;
/*     */   }
/*     */   
/*     */   public void setEnable(Boolean enable) {
/* 156 */     this.enable = enable;
/*     */   }
/*     */   
/*     */   public <T extends Enum<T>> T getDeviceType(Class<T> t) {
/* 160 */     return Enum.valueOf(t, this.deviceType);
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 164 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/* 168 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public String getIp() {
/* 172 */     return this.ip;
/*     */   }
/*     */   
/*     */   public void setIp(String ip) {
/* 176 */     this.ip = ip;
/*     */   }
/*     */   
/*     */   public Integer getPort() {
/* 180 */     return this.port;
/*     */   }
/*     */   
/*     */   public void setPort(Integer port) {
/* 184 */     this.port = port;
/*     */   }
/*     */   
/*     */   public String getProject() {
/* 188 */     return this.project;
/*     */   }
/*     */   
/*     */   public void setProject(String project) {
/* 192 */     this.project = project;
/*     */   }
/*     */   
/*     */   public Double getLongitude() {
/* 196 */     return this.longitude;
/*     */   }
/*     */   
/*     */   public void setLongitude(Double longitude) {
/* 200 */     this.longitude = longitude;
/*     */   }
/*     */   
/*     */   public Double getLatitude() {
/* 204 */     return this.latitude;
/*     */   }
/*     */   
/*     */   public void setLatitude(Double latitude) {
/* 208 */     this.latitude = latitude;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 212 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) {
/* 216 */     this.displayName = displayName;
/*     */   }
/*     */   
/*     */   public Boolean getEnable() {
/* 220 */     return this.enable;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 225 */     return Objects.hashCode(new Object[] { this.deviceName });
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/* 230 */     if ((object instanceof DeviceConfig)) {
/* 231 */       DeviceConfig that = (DeviceConfig)object;
/* 232 */       return Objects.equal(this.deviceName, that.deviceName);
/*     */     }
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   public String getMemo() {
/* 238 */     return this.memo;
/*     */   }
/*     */   
/*     */   public void setMemo(String memo) {
/* 242 */     this.memo = memo;
/*     */   }
/*     */   
/*     */   public String getExtend() {
/* 246 */     return this.extend;
/*     */   }
/*     */   
/*     */   public void setExtend(String extend) {
/* 250 */     this.extend = extend;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\DeviceConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */