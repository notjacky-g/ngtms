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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="brightMap")
/*    */ public class BrightMapPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="sixtyFive")
/*    */   public int sixtyFive;
/*    */   @CommandParam(name="eighty")
/*    */   public int eighty;
/*    */   @CommandParam(name="hundred")
/*    */   public int hundred;
/*    */   @CommandParam(name="noneLight")
/*    */   public int noneLight;
/*    */   @CommandParam(name="releaseForce")
/*    */   public int releaseForce;
/*    */   @CommandParam(name="force")
/*    */   public int force;
/*    */   @CommandParam(name="noneControl")
/*    */   public int noneControl;
/*    */   @CommandParam(name="local")
/*    */   public int local;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.sixtyFive);
/* 43 */     out.writeByte(this.eighty);
/* 44 */     out.writeByte(this.hundred);
/* 45 */     out.writeByte(this.noneLight);
/* 46 */     out.writeByte(this.releaseForce);
/* 47 */     out.writeByte(this.force);
/* 48 */     out.writeByte(this.noneControl);
/* 49 */     out.writeByte(this.local);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.sixtyFive = (in.readByte() & 0xFFFF);
/* 55 */     this.eighty = (in.readByte() & 0xFFFF);
/* 56 */     this.hundred = (in.readByte() & 0xFFFF);
/* 57 */     this.noneLight = (in.readByte() & 0xFFFF);
/* 58 */     this.releaseForce = (in.readByte() & 0xFFFF);
/* 59 */     this.force = (in.readByte() & 0xFFFF);
/* 60 */     this.noneControl = (in.readByte() & 0xFFFF);
/* 61 */     this.local = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("sixtyFive: ").append(this.sixtyFive).append(", ");
/* 68 */     sb.append("eighty: ").append(this.eighty).append(", ");
/* 69 */     sb.append("hundred: ").append(this.hundred).append(", ");
/* 70 */     sb.append("noneLight: ").append(this.noneLight).append(", ");
/* 71 */     sb.append("releaseForce: ").append(this.releaseForce).append(", ");
/* 72 */     sb.append("force: ").append(this.force).append(", ");
/* 73 */     sb.append("noneControl: ").append(this.noneControl).append(", ");
/* 74 */     sb.append("local: ").append(this.local).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\BrightMapPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */