/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.OneToMany;
/*     */ import javax.persistence.Table;
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
/*     */ @Table(uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"group_name", "group_type"})})
/*     */ public class DeviceGroup
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 845738522549780137L;
/*     */   @Id
/*     */   @Comment("群組編號")
/*     */   @Column(length=50)
/*     */   private String groupId;
/*     */   @Comment("群組名稱")
/*     */   @Column(length=20)
/*     */   private String groupName;
/*     */   @Comment("設備種類")
/*     */   @Column(length=10)
/*     */   private String groupType;
/*     */   @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER, orphanRemoval=true)
/*     */   @Comment("根據群組編號 對應到的設備名稱")
/*     */   @JoinColumn(name="group_id")
/*  44 */   private Set<DeviceGroupDeviceConfig> devices = new HashSet();
/*     */   
/*     */ 
/*     */ 
/*     */   public DeviceGroup() {}
/*     */   
/*     */ 
/*     */   public DeviceGroup(String groupId, String groupName, String groupType, Set<DeviceGroupDeviceConfig> devices)
/*     */   {
/*  53 */     this.groupId = groupId;
/*  54 */     this.groupName = groupName;
/*  55 */     this.groupType = groupType;
/*  56 */     this.devices = devices;
/*     */   }
/*     */   
/*     */   public String getGroupId() {
/*  60 */     return this.groupId;
/*     */   }
/*     */   
/*     */   public void setGroupId(String groupId) {
/*  64 */     this.groupId = groupId;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/*  68 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/*  72 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getGroupType() {
/*  76 */     return this.groupType;
/*     */   }
/*     */   
/*     */   public void setGroupType(String groupType) {
/*  80 */     this.groupType = groupType;
/*     */   }
/*     */   
/*     */   public Set<DeviceGroupDeviceConfig> getDevices() {
/*  84 */     return this.devices;
/*     */   }
/*     */   
/*     */   public void setDevices(Set<DeviceGroupDeviceConfig> devices) {
/*  88 */     this.devices.addAll(devices);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  93 */     int hash = 0;
/*  94 */     hash += (this.groupId != null ? this.groupId.hashCode() : 0);
/*  95 */     return hash;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 101 */     if (!(object instanceof DeviceGroup)) {
/* 102 */       return false;
/*     */     }
/* 104 */     DeviceGroup other = (DeviceGroup)object;
/* 105 */     if (((this.groupId == null) && (other.groupId != null)) || ((this.groupId != null) && 
/* 106 */       (!this.groupId.equals(other.groupId)))) {
/* 107 */       return false;
/*     */     }
/* 109 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 117 */     return "DeviceGroup [groupId=" + this.groupId + ", groupName=" + this.groupName + ", groupType=" + this.groupType + ", devices=" + this.devices + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */