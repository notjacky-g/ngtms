/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
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
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryBrightControlRsp")
/*    */ public class QueryBrightControlRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 45038;
/*    */   public static final String cmdName = "queryBrightControlRsp";
/*    */   @CommandParam(name="brightMap")
/*    */   public BrightMapPm brightMapPm;
/*    */   @CommandParam(name="startHour")
/*    */   public int startHour;
/*    */   @CommandParam(name="startMin")
/*    */   public int startMin;
/*    */   @CommandParam(name="endHour")
/*    */   public int endHour;
/*    */   @CommandParam(name="endMin")
/*    */   public int endMin;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeObject(this.brightMapPm);
/* 36 */     out.writeByte(this.startHour);
/* 37 */     out.writeByte(this.startMin);
/* 38 */     out.writeByte(this.endHour);
/* 39 */     out.writeByte(this.endMin);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.brightMapPm = ((BrightMapPm)in.readObject());
/* 45 */     this.startHour = (in.readByte() & 0xFF);
/* 46 */     this.startMin = (in.readByte() & 0xFF);
/* 47 */     this.endHour = (in.readByte() & 0xFF);
/* 48 */     this.endMin = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("brightMapPm: ").append(this.brightMapPm).append(", ");
/* 55 */     sb.append("startHour: ").append(this.startHour).append(", ");
/* 56 */     sb.append("startMin: ").append(this.startMin).append(", ");
/* 57 */     sb.append("endHour: ").append(this.endHour).append(", ");
/* 58 */     sb.append("endMin: ").append(this.endMin).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryBrightControlRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */