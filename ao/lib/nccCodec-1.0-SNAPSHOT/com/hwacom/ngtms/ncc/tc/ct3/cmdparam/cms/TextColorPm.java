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
/*    */ 
/*    */ @GlobalParams(paramsName="textColor")
/*    */ public class TextColorPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="fontColor")
/*    */   public int fontColor;
/*    */   @CommandParam(name="backgroundColor")
/*    */   public int backgroundColor;
/*    */   @CommandParam(name="flashSpeed")
/*    */   public int flashSpeed;
/*    */   @CommandParam(name="NA")
/*    */   public int NA;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 30 */     out.writeByte(this.fontColor);
/* 31 */     out.writeByte(this.backgroundColor);
/* 32 */     out.writeByte(this.flashSpeed);
/* 33 */     out.writeByte(this.NA);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 38 */     this.fontColor = (in.readByte() & 0xFFFF);
/* 39 */     this.backgroundColor = (in.readByte() & 0xFFFF);
/* 40 */     this.flashSpeed = (in.readByte() & 0xFFFF);
/* 41 */     this.NA = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     StringBuilder sb = new StringBuilder();
/* 46 */     sb.append('[');
/* 47 */     sb.append("fontColor: ").append(this.fontColor).append(", ");
/* 48 */     sb.append("backgroundColor: ").append(this.backgroundColor).append(", ");
/* 49 */     sb.append("flashSpeed: ").append(this.flashSpeed).append(", ");
/* 50 */     sb.append("NA: ").append(this.NA).append(']');
/* 51 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\TextColorPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */