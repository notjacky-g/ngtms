/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tem;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setTemLcsStatusReq")
/*    */ public class SetTemLcsStatusReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 38;
/*    */   public static final String cmdName = "setTemLcsStatusReq";
/*    */   @CommandParam(name="tunnel")
/*    */   public int tunnel;
/*    */   @CommandParam(name="place")
/*    */   public int place;
/*    */   @CommandParam(name="div")
/*    */   public int div;
/*    */   @CommandParam(name="status")
/*    */   public int status;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 31 */     out.writeByte(this.tunnel);
/* 32 */     out.writeByte(this.place);
/* 33 */     out.writeByte(this.div);
/* 34 */     out.writeByte(this.status);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 39 */     this.tunnel = (in.readByte() & 0xFF);
/* 40 */     this.place = (in.readByte() & 0xFF);
/* 41 */     this.div = (in.readByte() & 0xFF);
/* 42 */     this.status = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append('[');
/* 48 */     sb.append("tunnel: ").append(this.tunnel).append(", ");
/* 49 */     sb.append("place: ").append(this.place).append(", ");
/* 50 */     sb.append("div: ").append(this.div).append(", ");
/* 51 */     sb.append("status: ").append(this.status).append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tem\SetTemLcsStatusReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */