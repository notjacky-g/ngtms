/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="bright")
/*    */ public class BrightPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="brightness65percent")
/*    */   public int brightness65percent;
/*    */   @CommandParam(name="brightness80percent")
/*    */   public int brightness80percent;
/*    */   @CommandParam(name="brightness100percent")
/*    */   public int brightness100percent;
/*    */   @CommandParam(name="reserved3")
/*    */   public int reserved3;
/*    */   @CommandParam(name="releasePreviousForceDimControl")
/*    */   public int releasePreviousForceDimControl;
/*    */   @CommandParam(name="forceDimControl")
/*    */   public int forceDimControl;
/*    */   @CommandParam(name="reserved6")
/*    */   public int reserved6;
/*    */   @CommandParam(name="reserved7")
/*    */   public int reserved7;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.brightness65percent);
/* 43 */     out.writeByte(this.brightness80percent);
/* 44 */     out.writeByte(this.brightness100percent);
/* 45 */     out.writeByte(this.reserved3);
/* 46 */     out.writeByte(this.releasePreviousForceDimControl);
/* 47 */     out.writeByte(this.forceDimControl);
/* 48 */     out.writeByte(this.reserved6);
/* 49 */     out.writeByte(this.reserved7);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.brightness65percent = (in.readByte() & 0xFFFF);
/* 55 */     this.brightness80percent = (in.readByte() & 0xFFFF);
/* 56 */     this.brightness100percent = (in.readByte() & 0xFFFF);
/* 57 */     this.reserved3 = (in.readByte() & 0xFFFF);
/* 58 */     this.releasePreviousForceDimControl = (in.readByte() & 0xFFFF);
/* 59 */     this.forceDimControl = (in.readByte() & 0xFFFF);
/* 60 */     this.reserved6 = (in.readByte() & 0xFFFF);
/* 61 */     this.reserved7 = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("brightness65percent: ").append(this.brightness65percent).append(", ");
/* 68 */     sb.append("brightness80percent: ").append(this.brightness80percent).append(", ");
/* 69 */     sb.append("brightness100percent: ").append(this.brightness100percent).append(", ");
/* 70 */     sb.append("reserved3: ").append(this.reserved3).append(", ");
/* 71 */     sb.append("releasePreviousForceDimControl: ")
/* 72 */       .append(this.releasePreviousForceDimControl)
/* 73 */       .append(", ");
/* 74 */     sb.append("forceDimControl: ").append(this.forceDimControl).append(", ");
/* 75 */     sb.append("reserved6: ").append(this.reserved6).append(", ");
/* 76 */     sb.append("reserved7: ").append(this.reserved7).append(']');
/* 77 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\BrightPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */