/*     */ package com.hwacom.ngtms.node.performance.fm.model;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
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
/*     */ @Table(indexes={@javax.persistence.Index(columnList="group_name"), @javax.persistence.Index(columnList="node_name"), @javax.persistence.Index(columnList="record_time")})
/*     */ public class NodePerformanceLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Column(nullable=false, name="group_name")
/*     */   @Comment("Cluster 群組名稱")
/*     */   private String groupName;
/*     */   @Column(nullable=false, name="node_name")
/*     */   @Comment("節點名稱")
/*     */   private String nodeName;
/*     */   @Column(nullable=false, name="record_time")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("記錄時間")
/*     */   private Date recordTime;
/*     */   @Comment("CPU每分鐘平均負載")
/*     */   private Double cpuLoadAverage;
/*     */   @Comment("MEM使用百分比")
/*     */   private Double memUsedPercent;
/*     */   @Column(length=1000)
/*     */   @Comment("硬碟使用率百分比的 map，key 為 diskName，value 為硬碟使用率，型態為 double")
/*     */   private String diskUsageJson;
/*     */   @Comment("CPU 使用率百分比")
/*     */   private double cpuUsage;
/*     */   @Comment("所有網路介面接收流量的總和")
/*     */   private long sumOfRxSpeedPerSec;
/*     */   @Comment("所有網路介面傳輸流量的總和")
/*     */   private long sumOfTxSpeedPerSec;
/*     */   
/*     */   public String getGroupName()
/*     */   {
/*  77 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/*  81 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/*  85 */     return this.nodeName;
/*     */   }
/*     */   
/*     */   public void setNodeName(String nodeName) {
/*  89 */     this.nodeName = nodeName;
/*     */   }
/*     */   
/*     */   public Double getCpuLoadAverage() {
/*  93 */     return this.cpuLoadAverage;
/*     */   }
/*     */   
/*     */   public void setCpuLoadAverage(Double cpuLoadAverage) {
/*  97 */     this.cpuLoadAverage = cpuLoadAverage;
/*     */   }
/*     */   
/*     */   public Double getMemUsedPercent() {
/* 101 */     return this.memUsedPercent;
/*     */   }
/*     */   
/*     */   public void setMemUsedPercent(Double memUsedPercent) {
/* 105 */     this.memUsedPercent = memUsedPercent;
/*     */   }
/*     */   
/*     */   public String getDiskUsageJson() {
/* 109 */     return this.diskUsageJson;
/*     */   }
/*     */   
/*     */   public void setDiskUsageJson(String diskUsageJson) {
/* 113 */     this.diskUsageJson = diskUsageJson;
/*     */   }
/*     */   
/*     */   public Map<String, Double> getDiskUsage() {
/* 117 */     Type type = new TypeToken() {}.getType();
/* 118 */     return (Map)new Gson().fromJson(this.diskUsageJson, type);
/*     */   }
/*     */   
/*     */   public void setDiskUsage(Map<String, Double> map) {
/* 122 */     this.diskUsageJson = new Gson().toJson(map);
/*     */   }
/*     */   
/*     */   public Date getRecordTime() {
/* 126 */     return this.recordTime;
/*     */   }
/*     */   
/*     */   public void setRecordTime(Date recordTime) {
/* 130 */     this.recordTime = recordTime;
/*     */   }
/*     */   
/*     */   public Long getId() {
/* 134 */     return this.id;
/*     */   }
/*     */   
/*     */   public double getCpuUsage() {
/* 138 */     return this.cpuUsage;
/*     */   }
/*     */   
/*     */   public void setCpuUsage(double cpuUsage) {
/* 142 */     this.cpuUsage = cpuUsage;
/*     */   }
/*     */   
/*     */   public long getSumOfRxSpeedPerSec() {
/* 146 */     return this.sumOfRxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public void setSumOfRxSpeedPerSec(long sumOfRxSpeedPerSec) {
/* 150 */     this.sumOfRxSpeedPerSec = sumOfRxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public long getSumOfTxSpeedPerSec() {
/* 154 */     return this.sumOfTxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public void setSumOfTxSpeedPerSec(long sumOfTxSpeedPerSec) {
/* 158 */     this.sumOfTxSpeedPerSec = sumOfTxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 163 */     int prime = 31;
/* 164 */     int result = 1;
/* 165 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 166 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 171 */     if (this == obj) return true;
/* 172 */     if (obj == null) return false;
/* 173 */     if (getClass() != obj.getClass()) return false;
/* 174 */     NodePerformanceLog other = (NodePerformanceLog)obj;
/* 175 */     if (this.id == null) {
/* 176 */       if (other.id != null) return false;
/* 177 */     } else if (!this.id.equals(other.id)) return false;
/* 178 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 183 */     return "NodePerformanceLog [id=" + this.id + ", groupName=" + this.groupName + ", nodeName=" + this.nodeName + ", cpuloadAverage=" + this.cpuLoadAverage + ", memUsedPercent=" + this.memUsedPercent + ", diskUsageJson=" + this.diskUsageJson + ", recordTime=" + this.recordTime + ", cpuUsage=" + this.cpuUsage + ", sumOfRxSpeedPerSec=" + this.sumOfRxSpeedPerSec + ", sumOfTxSpeedPerSec=" + this.sumOfTxSpeedPerSec + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\fm\model\NodePerformanceLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */