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
/*    */ @GlobalParams(paramsName="controlStrategy")
/*    */ public class ControlStrategyPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="pretimedControl")
/*    */   public int pretimedControl;
/*    */   @CommandParam(name="dynamicControl")
/*    */   public int dynamicControl;
/*    */   @CommandParam(name="siteControl")
/*    */   public int siteControl;
/*    */   @CommandParam(name="centerControl")
/*    */   public int centerControl;
/*    */   @CommandParam(name="phaseControl")
/*    */   public int phaseControl;
/*    */   @CommandParam(name="realTimeControl")
/*    */   public int realTimeControl;
/*    */   @CommandParam(name="actuatedControl")
/*    */   public int actuatedControl;
/*    */   @CommandParam(name="vipControl")
/*    */   public int vipControl;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.pretimedControl);
/* 43 */     out.writeByte(this.dynamicControl);
/* 44 */     out.writeByte(this.siteControl);
/* 45 */     out.writeByte(this.centerControl);
/* 46 */     out.writeByte(this.phaseControl);
/* 47 */     out.writeByte(this.realTimeControl);
/* 48 */     out.writeByte(this.actuatedControl);
/* 49 */     out.writeByte(this.vipControl);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.pretimedControl = (in.readByte() & 0xFFFF);
/* 55 */     this.dynamicControl = (in.readByte() & 0xFFFF);
/* 56 */     this.siteControl = (in.readByte() & 0xFFFF);
/* 57 */     this.centerControl = (in.readByte() & 0xFFFF);
/* 58 */     this.phaseControl = (in.readByte() & 0xFFFF);
/* 59 */     this.realTimeControl = (in.readByte() & 0xFFFF);
/* 60 */     this.actuatedControl = (in.readByte() & 0xFFFF);
/* 61 */     this.vipControl = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("pretimedControl: ").append(this.pretimedControl).append(", ");
/* 68 */     sb.append("dynamicControl: ").append(this.dynamicControl).append(", ");
/* 69 */     sb.append("siteControl: ").append(this.siteControl).append(", ");
/* 70 */     sb.append("centerControl: ").append(this.centerControl).append(", ");
/* 71 */     sb.append("phaseControl: ").append(this.phaseControl).append(", ");
/* 72 */     sb.append("realTimeControl: ").append(this.realTimeControl).append(", ");
/* 73 */     sb.append("actuatedControl: ").append(this.actuatedControl).append(", ");
/* 74 */     sb.append("vipControl: ").append(this.vipControl).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\ControlStrategyPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */