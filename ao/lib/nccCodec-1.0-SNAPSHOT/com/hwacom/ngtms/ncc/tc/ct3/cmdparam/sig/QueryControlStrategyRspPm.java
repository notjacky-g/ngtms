/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryControlStrategyRsp")
/*    */ public class QueryControlStrategyRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24512;
/*    */   public static final String cmdName = "queryControlStrategyRsp";
/*    */   @CommandParam(name="controlStrategy")
/*    */   public ControlStrategyPm controlStrategyPm;
/*    */   @CommandParam(name="effectTime")
/*    */   public int effectTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.controlStrategyPm);
/* 27 */     out.writeByte(this.effectTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.controlStrategyPm = ((ControlStrategyPm)in.readObject());
/* 33 */     this.effectTime = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("controlStrategyPm: ").append(this.controlStrategyPm).append(", ");
/* 40 */     sb.append("effectTime: ").append(this.effectTime).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\QueryControlStrategyRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */