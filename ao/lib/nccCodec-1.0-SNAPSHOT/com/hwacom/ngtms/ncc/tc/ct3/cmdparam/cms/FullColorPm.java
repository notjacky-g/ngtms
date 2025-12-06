/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="fullColor")
/*    */ public class FullColorPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="red")
/*    */   public int red;
/*    */   @CommandParam(name="green")
/*    */   public int green;
/*    */   @CommandParam(name="blue")
/*    */   public int blue;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 27 */     out.writeByte(this.red);
/* 28 */     out.writeByte(this.green);
/* 29 */     out.writeByte(this.blue);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 34 */     this.red = (in.readByte() & 0xFF);
/* 35 */     this.green = (in.readByte() & 0xFF);
/* 36 */     this.blue = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     StringBuilder sb = new StringBuilder();
/* 41 */     sb.append('[');
/* 42 */     sb.append("red: ").append(this.red).append(", ");
/* 43 */     sb.append("green: ").append(this.green).append(", ");
/* 44 */     sb.append("blue: ").append(this.blue).append(']');
/* 45 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\FullColorPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */