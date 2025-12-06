/*     */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.List;
/*     */ 
/*     */ @CommandParams(cmdName="setPhaseReq")
/*     */ public class SetPhaseReqPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 24339;
/*     */   public static final String cmdName = "setPhaseReq";
/*     */   @CommandParam(name="phaseOrder")
/*     */   public int phaseOrder;
/*     */   @CommandParam(name="signalMap")
/*     */   public SignalMapPm signalMapPm;
/*     */   @CommandParam(name="signalCount")
/*     */   public int signalCount;
/*     */   @CommandParam(name="subPhaseList")
/*     */   public List<SubPhaseListItem> subPhaseList;
/*     */   
/*     */   public static class SubPhaseListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="signalStatusList")
/*     */     public List<SignalStatusListItem> signalStatusList;
/*     */     
/*     */     public static class SignalStatusListItem
/*     */       implements Externalizable
/*     */     {
/*     */       private static final long serialVersionUID = 1L;
/*     */       @CommandParam(name="signalStatus")
/*     */       public SignalStatusPm signalStatusPm;
/*     */       
/*     */       public void writeExternal(ObjectOutput out)
/*     */         throws IOException
/*     */       {
/*  44 */         out.writeObject(this.signalStatusPm);
/*     */       }
/*     */       
/*     */       public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */       {
/*  49 */         this.signalStatusPm = ((SignalStatusPm)in.readObject());
/*     */       }
/*     */       
/*     */       public String toString() {
/*  53 */         StringBuilder sb = new StringBuilder();
/*  54 */         sb.append('[');
/*  55 */         sb.append("signalStatusPm: ").append(this.signalStatusPm).append(']');
/*  56 */         return sb.toString();
/*     */       }
/*     */     }
/*     */     
/*     */     public void writeExternal(ObjectOutput out) throws IOException
/*     */     {
/*  62 */       out.writeObject(this.signalStatusList);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in)
/*     */       throws IOException, ClassNotFoundException
/*     */     {
/*  68 */       this.signalStatusList = ((List)in.readObject());
/*     */     }
/*     */     
/*     */     public String toString() {
/*  72 */       StringBuilder sb = new StringBuilder();
/*  73 */       sb.append('[');
/*  74 */       sb.append("signalStatusList: ").append(this.signalStatusList).append(']');
/*  75 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/*  81 */     out.writeByte(this.phaseOrder);
/*  82 */     out.writeObject(this.signalMapPm);
/*  83 */     out.writeByte(this.signalCount);
/*  84 */     out.writeObject(this.subPhaseList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  90 */     this.phaseOrder = (in.readByte() & 0xFF);
/*  91 */     this.signalMapPm = ((SignalMapPm)in.readObject());
/*  92 */     this.signalCount = (in.readByte() & 0xFF);
/*  93 */     this.subPhaseList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/*  97 */     StringBuilder sb = new StringBuilder();
/*  98 */     sb.append('[');
/*  99 */     sb.append("phaseOrder: ").append(this.phaseOrder).append(", ");
/* 100 */     sb.append("signalMapPm: ").append(this.signalMapPm).append(", ");
/* 101 */     sb.append("signalCount: ").append(this.signalCount).append(", ");
/* 102 */     sb.append("subPhaseList: ").append(this.subPhaseList).append(']');
/* 103 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SetPhaseReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */