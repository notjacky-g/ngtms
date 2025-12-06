/*    */ package com.hwacom.ngtms.base.nodeperf;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.PortableReader;
/*    */ import com.hazelcast.nio.serialization.PortableWriter;
/*    */ import com.hwacom.ngtms.base.hazelcast.serializer.HzPortable;
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiskInfo
/*    */   extends HzPortable
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String devName;
/*    */   private String dirName;
/*    */   private long used;
/*    */   private long avail;
/*    */   private long total;
/*    */   private double usePercent;
/*    */   
/*    */   public void readPortable(PortableReader reader) throws IOException {
/* 26 */     this.devName = reader.readUTF("devName");
/* 27 */     this.dirName = reader.readUTF("dirName");
/* 28 */     this.used = reader.readLong("used");
/* 29 */     this.avail = reader.readLong("avail");
/* 30 */     this.total = reader.readLong("total");
/* 31 */     this.usePercent = reader.readDouble("usePercent");
/*    */   }
/*    */ 
/*    */   
/*    */   public void writePortable(PortableWriter writer) throws IOException {
/* 36 */     writer.writeUTF("devName", this.devName);
/* 37 */     writer.writeUTF("dirName", this.dirName);
/* 38 */     writer.writeLong("used", this.used);
/* 39 */     writer.writeLong("avail", this.avail);
/* 40 */     writer.writeLong("total", this.total);
/* 41 */     writer.writeDouble("usePercent", this.usePercent);
/*    */   }
/*    */   
/*    */   public String getDevName() {
/* 45 */     return this.devName;
/*    */   }
/*    */   
/*    */   public void setDevName(String devName) {
/* 49 */     this.devName = devName;
/*    */   }
/*    */   
/*    */   public String getDirName() {
/* 53 */     return this.dirName;
/*    */   }
/*    */   
/*    */   public void setDirName(String dirName) {
/* 57 */     this.dirName = dirName;
/*    */   }
/*    */   
/*    */   public long getUsed() {
/* 61 */     return this.used;
/*    */   }
/*    */   
/*    */   public void setUsed(long used) {
/* 65 */     this.used = used;
/*    */   }
/*    */   
/*    */   public long getAvail() {
/* 69 */     return this.avail;
/*    */   }
/*    */   
/*    */   public void setAvail(long avail) {
/* 73 */     this.avail = avail;
/*    */   }
/*    */   
/*    */   public long getTotal() {
/* 77 */     return this.total;
/*    */   }
/*    */   
/*    */   public void setTotal(long total) {
/* 81 */     this.total = total;
/*    */   }
/*    */   
/*    */   public double getUsePercent() {
/* 85 */     return this.usePercent;
/*    */   }
/*    */   
/*    */   public void setUsePercent(double usePercent) {
/* 89 */     this.usePercent = usePercent;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 94 */     return "DiskInfo [devName=" + this.devName + ", dirName=" + this.dirName + ", used=" + this.used + ", avail=" + this.avail + ", total=" + this.total + ", usePercent=" + this.usePercent + "]";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\nodeperf\DiskInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */