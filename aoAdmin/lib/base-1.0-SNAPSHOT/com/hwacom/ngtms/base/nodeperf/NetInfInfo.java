/*     */ package com.hwacom.ngtms.base.nodeperf;
/*     */ 
/*     */ import com.hazelcast.nio.serialization.PortableReader;
/*     */ import com.hazelcast.nio.serialization.PortableWriter;
/*     */ import com.hwacom.ngtms.base.hazelcast.serializer.HzPortable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetInfInfo
/*     */   extends HzPortable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String ifName;
/*     */   private long txBytes;
/*     */   private long RxBytes;
/*     */   private long lastTxBytes;
/*     */   private long lastRxBytes;
/*     */   private long txDiffBytes;
/*     */   private long rxDiffBytes;
/*     */   private long txSpeedPerSec;
/*     */   private long rxSpeedPerSec;
/*     */   
/*     */   public void readPortable(PortableReader reader) throws IOException {
/*  29 */     this.ifName = reader.readUTF("ifName");
/*  30 */     this.txBytes = reader.readLong("txBytes");
/*  31 */     this.RxBytes = reader.readLong("RxBytes");
/*  32 */     this.lastTxBytes = reader.readLong("lastTxBytes");
/*  33 */     this.lastRxBytes = reader.readLong("lastRxBytes");
/*  34 */     this.txDiffBytes = reader.readLong("txDiffBytes");
/*  35 */     this.rxDiffBytes = reader.readLong("rxDiffBytes");
/*  36 */     this.txSpeedPerSec = reader.readLong("txSpeedPerSec");
/*  37 */     this.rxSpeedPerSec = reader.readLong("rxSpeedPerSec");
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePortable(PortableWriter writer) throws IOException {
/*  42 */     writer.writeUTF("ifName", this.ifName);
/*  43 */     writer.writeLong("txBytes", this.txBytes);
/*  44 */     writer.writeLong("RxBytes", this.RxBytes);
/*  45 */     writer.writeLong("lastTxBytes", this.lastTxBytes);
/*  46 */     writer.writeLong("lastRxBytes", this.lastRxBytes);
/*  47 */     writer.writeLong("txDiffBytes", this.txDiffBytes);
/*  48 */     writer.writeLong("rxDiffBytes", this.rxDiffBytes);
/*  49 */     writer.writeLong("txSpeedPerSec", this.txSpeedPerSec);
/*  50 */     writer.writeLong("rxSpeedPerSec", this.rxSpeedPerSec);
/*     */   }
/*     */   
/*     */   public long getTxBytes() {
/*  54 */     return this.txBytes;
/*     */   }
/*     */   
/*     */   public void setTxBytes(long txBytes) {
/*  58 */     this.txBytes = txBytes;
/*     */   }
/*     */   
/*     */   public long getRxBytes() {
/*  62 */     return this.RxBytes;
/*     */   }
/*     */   
/*     */   public void setRxBytes(long rxBytes) {
/*  66 */     this.RxBytes = rxBytes;
/*     */   }
/*     */   
/*     */   public String getIfName() {
/*  70 */     return this.ifName;
/*     */   }
/*     */   
/*     */   public void setIfName(String ifName) {
/*  74 */     this.ifName = ifName;
/*     */   }
/*     */   
/*     */   public long getLastTxBytes() {
/*  78 */     return this.lastTxBytes;
/*     */   }
/*     */   
/*     */   public void setLastTxBytes(long lastTxBytes) {
/*  82 */     this.lastTxBytes = lastTxBytes;
/*  83 */     this.txDiffBytes = this.txBytes - lastTxBytes;
/*     */   }
/*     */   
/*     */   public long getLastRxBytes() {
/*  87 */     return this.lastRxBytes;
/*     */   }
/*     */   
/*     */   public void setLastRxBytes(long lastRxBytes) {
/*  91 */     this.lastRxBytes = lastRxBytes;
/*  92 */     this.rxDiffBytes = this.RxBytes - lastRxBytes;
/*     */   }
/*     */   
/*     */   public long getTxDiffBytes() {
/*  96 */     return this.txDiffBytes;
/*     */   }
/*     */   
/*     */   public long getRxDiffBytes() {
/* 100 */     return this.rxDiffBytes;
/*     */   }
/*     */   
/*     */   public long getTxSpeedPerSec() {
/* 104 */     return this.txSpeedPerSec;
/*     */   }
/*     */   
/*     */   public void setTxSpeedPerSec(long txSpeedPerSec) {
/* 108 */     this.txSpeedPerSec = txSpeedPerSec;
/*     */   }
/*     */   
/*     */   public long getRxSpeedPerSec() {
/* 112 */     return this.rxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public void setRxSpeedPerSec(long rxSpeedPerSec) {
/* 116 */     this.rxSpeedPerSec = rxSpeedPerSec;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 121 */     return "NetInfInfo [ifName=" + this.ifName + ", txBytes=" + this.txBytes + ", RxBytes=" + this.RxBytes + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\nodeperf\NetInfInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */