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
/*     */ public class NetStat
/*     */   extends HzPortable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int tcpInboundTotal;
/*     */   private int tcpOutboundTotal;
/*     */   private int allInboundTotal;
/*     */   private int allOutboundTotal;
/*     */   private int tcpEstablished;
/*     */   private int tcpSynSent;
/*     */   private int tcpSynRecv;
/*     */   private int tcpFinWait1;
/*     */   private int tcpFinWait2;
/*     */   private int tcpTimeWait;
/*     */   private int tcpClose;
/*     */   private int tcpCloseWait;
/*     */   private int tcpLastAck;
/*     */   private int tcpListen;
/*     */   private int tcpClosing;
/*     */   private int tcpIdle;
/*     */   private int tcpBound;
/*     */   
/*     */   public void readPortable(PortableReader reader)
/*     */     throws IOException
/*     */   {
/*  37 */     this.tcpInboundTotal = reader.readInt("tcpInboundTotal");
/*  38 */     this.tcpOutboundTotal = reader.readInt("tcpOutboundTotal");
/*  39 */     this.allInboundTotal = reader.readInt("allInboundTotal");
/*  40 */     this.allOutboundTotal = reader.readInt("allOutboundTotal");
/*  41 */     this.tcpEstablished = reader.readInt("tcpEstablished");
/*  42 */     this.tcpSynSent = reader.readInt("tcpSynSent");
/*  43 */     this.tcpSynRecv = reader.readInt("tcpSynRecv");
/*  44 */     this.tcpFinWait1 = reader.readInt("tcpFinWait1");
/*  45 */     this.tcpFinWait2 = reader.readInt("tcpFinWait2");
/*  46 */     this.tcpTimeWait = reader.readInt("tcpTimeWait");
/*  47 */     this.tcpClose = reader.readInt("tcpClose");
/*  48 */     this.tcpCloseWait = reader.readInt("tcpCloseWait");
/*  49 */     this.tcpLastAck = reader.readInt("tcpLastAck");
/*  50 */     this.tcpListen = reader.readInt("tcpListen");
/*  51 */     this.tcpClosing = reader.readInt("tcpClosing");
/*  52 */     this.tcpIdle = reader.readInt("tcpIdle");
/*  53 */     this.tcpBound = reader.readInt("tcpBound");
/*     */   }
/*     */   
/*     */   public void writePortable(PortableWriter writer) throws IOException
/*     */   {
/*  58 */     writer.writeInt("tcpInboundTotal", this.tcpInboundTotal);
/*  59 */     writer.writeInt("tcpOutboundTotal", this.tcpOutboundTotal);
/*  60 */     writer.writeInt("allInboundTotal", this.allInboundTotal);
/*  61 */     writer.writeInt("allOutboundTotal", this.allOutboundTotal);
/*  62 */     writer.writeInt("tcpEstablished", this.tcpEstablished);
/*  63 */     writer.writeInt("tcpSynSent", this.tcpSynSent);
/*  64 */     writer.writeInt("tcpSynRecv", this.tcpSynRecv);
/*  65 */     writer.writeInt("tcpFinWait1", this.tcpFinWait1);
/*  66 */     writer.writeInt("tcpFinWait2", this.tcpFinWait2);
/*  67 */     writer.writeInt("tcpTimeWait", this.tcpTimeWait);
/*  68 */     writer.writeInt("tcpClose", this.tcpClose);
/*  69 */     writer.writeInt("tcpCloseWait", this.tcpCloseWait);
/*  70 */     writer.writeInt("tcpLastAck", this.tcpLastAck);
/*  71 */     writer.writeInt("tcpListen", this.tcpListen);
/*  72 */     writer.writeInt("tcpClosing", this.tcpClosing);
/*  73 */     writer.writeInt("tcpIdle", this.tcpIdle);
/*  74 */     writer.writeInt("tcpBound", this.tcpBound);
/*     */   }
/*     */   
/*     */   public int getTcpInboundTotal() {
/*  78 */     return this.tcpInboundTotal;
/*     */   }
/*     */   
/*     */   public void setTcpInboundTotal(int tcpInboundTotal) {
/*  82 */     this.tcpInboundTotal = tcpInboundTotal;
/*     */   }
/*     */   
/*     */   public int getTcpOutboundTotal() {
/*  86 */     return this.tcpOutboundTotal;
/*     */   }
/*     */   
/*     */   public void setTcpOutboundTotal(int tcpOutboundTotal) {
/*  90 */     this.tcpOutboundTotal = tcpOutboundTotal;
/*     */   }
/*     */   
/*     */   public int getAllInboundTotal() {
/*  94 */     return this.allInboundTotal;
/*     */   }
/*     */   
/*     */   public void setAllInboundTotal(int allInboundTotal) {
/*  98 */     this.allInboundTotal = allInboundTotal;
/*     */   }
/*     */   
/*     */   public int getAllOutboundTotal() {
/* 102 */     return this.allOutboundTotal;
/*     */   }
/*     */   
/*     */   public void setAllOutboundTotal(int allOutboundTotal) {
/* 106 */     this.allOutboundTotal = allOutboundTotal;
/*     */   }
/*     */   
/*     */   public int getTcpEstablished() {
/* 110 */     return this.tcpEstablished;
/*     */   }
/*     */   
/*     */   public void setTcpEstablished(int tcpEstablished) {
/* 114 */     this.tcpEstablished = tcpEstablished;
/*     */   }
/*     */   
/*     */   public int getTcpSynSent() {
/* 118 */     return this.tcpSynSent;
/*     */   }
/*     */   
/*     */   public void setTcpSynSent(int tcpSynSent) {
/* 122 */     this.tcpSynSent = tcpSynSent;
/*     */   }
/*     */   
/*     */   public int getTcpSynRecv() {
/* 126 */     return this.tcpSynRecv;
/*     */   }
/*     */   
/*     */   public void setTcpSynRecv(int tcpSynRecv) {
/* 130 */     this.tcpSynRecv = tcpSynRecv;
/*     */   }
/*     */   
/*     */   public int getTcpFinWait1() {
/* 134 */     return this.tcpFinWait1;
/*     */   }
/*     */   
/*     */   public void setTcpFinWait1(int tcpFinWait1) {
/* 138 */     this.tcpFinWait1 = tcpFinWait1;
/*     */   }
/*     */   
/*     */   public int getTcpFinWait2() {
/* 142 */     return this.tcpFinWait2;
/*     */   }
/*     */   
/*     */   public void setTcpFinWait2(int tcpFinWait2) {
/* 146 */     this.tcpFinWait2 = tcpFinWait2;
/*     */   }
/*     */   
/*     */   public int getTcpTimeWait() {
/* 150 */     return this.tcpTimeWait;
/*     */   }
/*     */   
/*     */   public void setTcpTimeWait(int tcpTimeWait) {
/* 154 */     this.tcpTimeWait = tcpTimeWait;
/*     */   }
/*     */   
/*     */   public int getTcpClose() {
/* 158 */     return this.tcpClose;
/*     */   }
/*     */   
/*     */   public void setTcpClose(int tcpClose) {
/* 162 */     this.tcpClose = tcpClose;
/*     */   }
/*     */   
/*     */   public int getTcpCloseWait() {
/* 166 */     return this.tcpCloseWait;
/*     */   }
/*     */   
/*     */   public void setTcpCloseWait(int tcpCloseWait) {
/* 170 */     this.tcpCloseWait = tcpCloseWait;
/*     */   }
/*     */   
/*     */   public int getTcpLastAck() {
/* 174 */     return this.tcpLastAck;
/*     */   }
/*     */   
/*     */   public void setTcpLastAck(int tcpLastAck) {
/* 178 */     this.tcpLastAck = tcpLastAck;
/*     */   }
/*     */   
/*     */   public int getTcpListen() {
/* 182 */     return this.tcpListen;
/*     */   }
/*     */   
/*     */   public void setTcpListen(int tcpListen) {
/* 186 */     this.tcpListen = tcpListen;
/*     */   }
/*     */   
/*     */   public int getTcpClosing() {
/* 190 */     return this.tcpClosing;
/*     */   }
/*     */   
/*     */   public void setTcpClosing(int tcpClosing) {
/* 194 */     this.tcpClosing = tcpClosing;
/*     */   }
/*     */   
/*     */   public int getTcpIdle() {
/* 198 */     return this.tcpIdle;
/*     */   }
/*     */   
/*     */   public void setTcpIdle(int tcpIdle) {
/* 202 */     this.tcpIdle = tcpIdle;
/*     */   }
/*     */   
/*     */   public int getTcpBound() {
/* 206 */     return this.tcpBound;
/*     */   }
/*     */   
/*     */   public void setTcpBound(int tcpBound) {
/* 210 */     this.tcpBound = tcpBound;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 215 */     return "NetStat [tcpInboundTotal=" + this.tcpInboundTotal + ", tcpOutboundTotal=" + this.tcpOutboundTotal + ", allInboundTotal=" + this.allInboundTotal + ", allOutboundTotal=" + this.allOutboundTotal + ", tcpEstablished=" + this.tcpEstablished + ", tcpSynSent=" + this.tcpSynSent + ", tcpSynRecv=" + this.tcpSynRecv + ", tcpFinWait1=" + this.tcpFinWait1 + ", tcpFinWait2=" + this.tcpFinWait2 + ", tcpTimeWait=" + this.tcpTimeWait + ", tcpClose=" + this.tcpClose + ", tcpCloseWait=" + this.tcpCloseWait + ", tcpLastAck=" + this.tcpLastAck + ", tcpListen=" + this.tcpListen + ", tcpClosing=" + this.tcpClosing + ", tcpIdle=" + this.tcpIdle + ", tcpBound=" + this.tcpBound + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\nodeperf\NetStat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */