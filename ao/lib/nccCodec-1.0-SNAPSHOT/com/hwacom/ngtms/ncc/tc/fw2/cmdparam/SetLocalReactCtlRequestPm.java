/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setLocalReactCtlRequest")
/*    */ public class SetLocalReactCtlRequestPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 3841;
/*    */   public static final String cmdName = "setLocalReactCtlRequest";
/*    */   @CommandParam(name="event")
/*    */   public int event;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 22 */     out.writeByte(this.event);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 27 */     this.event = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 31 */     StringBuilder sb = new StringBuilder();
/* 32 */     sb.append('[');
/* 33 */     sb.append("event: ").append(this.event).append(']');
/* 34 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\SetLocalReactCtlRequestPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */