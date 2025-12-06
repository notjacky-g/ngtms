/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.lcs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setTransitTimeReq")
/*    */ public class SetTransitTimeReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 32705;
/*    */   public static final String cmdName = "setTransitTimeReq";
/*    */   @CommandParam(name="transitTime")
/*    */   public byte[] transitTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 23 */     out.writeObject(this.transitTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.transitTime = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("transitTime: ")
/* 35 */       .append(BytesUtility.toHexString(this.transitTime))
/* 36 */       .append(']');
/* 37 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\lcs\SetTransitTimeReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */