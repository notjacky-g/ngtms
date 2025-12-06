/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.UniqueConstraint;
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
/*     */ @Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "device_name"})})
/*     */ public class DeviceGroupDeviceConfig
/*     */   implements Serializable, Comparable<DeviceGroupDeviceConfig>
/*     */ {
/*     */   private static final long serialVersionUID = 6285331684267261650L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   @Column(length = 150)
/*     */   private String id;
/*     */   @Comment("群組id")
/*     */   @Column(nullable = false, length = 50)
/*     */   private String groupId;
/*     */   @Comment("設備編號")
/*     */   @Column(nullable = false, length = 100)
/*     */   private String deviceName;
/*     */   
/*     */   public String toString() {
/*  46 */     return MoreObjects.toStringHelper(this).add("super", super.toString()).add("id", this.id).add("groupId", this.groupId).add("deviceName", this.deviceName).toString();
/*     */   }
/*     */   
/*     */   public DeviceGroupDeviceConfig() {}
/*     */   
/*     */   public DeviceGroupDeviceConfig(String groupId, String deviceName) {
/*  52 */     this.id = KeyUtils.getKey(new Object[] { groupId, deviceName });
/*  53 */     this.groupId = groupId;
/*  54 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  58 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  62 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getGroupId() {
/*  66 */     return this.groupId;
/*     */   }
/*     */   
/*     */   public void setGroupId(String groupId) {
/*  70 */     this.groupId = groupId;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  74 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  78 */     this.deviceName = deviceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(DeviceGroupDeviceConfig o) {
/*  83 */     return this.id.compareTo(o.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  88 */     int prime = 31;
/*  89 */     int result = 1;
/*  90 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/*  91 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  96 */     if (this == obj) return true; 
/*  97 */     if (obj == null) return false; 
/*  98 */     if (getClass() != obj.getClass()) return false; 
/*  99 */     DeviceGroupDeviceConfig other = (DeviceGroupDeviceConfig)obj;
/* 100 */     if (this.id == null)
/* 101 */     { if (other.id != null) return false;  }
/* 102 */     else if (!this.id.equals(other.id)) { return false; }
/* 103 */      return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceGroupDeviceConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */