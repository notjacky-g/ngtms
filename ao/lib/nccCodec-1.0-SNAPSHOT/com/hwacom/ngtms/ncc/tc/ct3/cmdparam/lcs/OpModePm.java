/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.lcs;
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
/*    */ @GlobalParams(paramsName="opMode")
/*    */ public class OpModePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="remoteOperation")
/*    */   public int remoteOperation;
/*    */   @CommandParam(name="localOperation")
/*    */   public int localOperation;
/*    */   @CommandParam(name="localReact")
/*    */   public int localReact;
/*    */   @CommandParam(name="undefine3")
/*    */   public int undefine3;
/*    */   @CommandParam(name="undefine4")
/*    */   public int undefine4;
/*    */   @CommandParam(name="undefine5")
/*    */   public int undefine5;
/*    */   @CommandParam(name="undefine6")
/*    */   public int undefine6;
/*    */   @CommandParam(name="undefine7")
/*    */   public int undefine7;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.remoteOperation);
/* 43 */     out.writeByte(this.localOperation);
/* 44 */     out.writeByte(this.localReact);
/* 45 */     out.writeByte(this.undefine3);
/* 46 */     out.writeByte(this.undefine4);
/* 47 */     out.writeByte(this.undefine5);
/* 48 */     out.writeByte(this.undefine6);
/* 49 */     out.writeByte(this.undefine7);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.remoteOperation = (in.readByte() & 0xFFFF);
/* 55 */     this.localOperation = (in.readByte() & 0xFFFF);
/* 56 */     this.localReact = (in.readByte() & 0xFFFF);
/* 57 */     this.undefine3 = (in.readByte() & 0xFFFF);
/* 58 */     this.undefine4 = (in.readByte() & 0xFFFF);
/* 59 */     this.undefine5 = (in.readByte() & 0xFFFF);
/* 60 */     this.undefine6 = (in.readByte() & 0xFFFF);
/* 61 */     this.undefine7 = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("remoteOperation: ").append(this.remoteOperation).append(", ");
/* 68 */     sb.append("localOperation: ").append(this.localOperation).append(", ");
/* 69 */     sb.append("localReact: ").append(this.localReact).append(", ");
/* 70 */     sb.append("undefine3: ").append(this.undefine3).append(", ");
/* 71 */     sb.append("undefine4: ").append(this.undefine4).append(", ");
/* 72 */     sb.append("undefine5: ").append(this.undefine5).append(", ");
/* 73 */     sb.append("undefine6: ").append(this.undefine6).append(", ");
/* 74 */     sb.append("undefine7: ").append(this.undefine7).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\lcs\OpModePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */