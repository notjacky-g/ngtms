/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="getScsLedTestRsp")
/*    */ public class GetScsLedTestRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 86;
/*    */   public static final String cmdName = "getScsLedTestRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="badLedModuleList")
/*    */   public List<BadLedModuleListItem> badLedModuleList;
/*    */   
/*    */   public static class BadLedModuleListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="xPositionOfmodule")
/*    */     public int xPositionOfmodule;
/*    */     @CommandParam(name="yPositionOfmodule")
/*    */     public int yPositionOfmodule;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 35 */       out.writeByte(this.xPositionOfmodule);
/* 36 */       out.writeByte(this.yPositionOfmodule);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 41 */       this.xPositionOfmodule = (in.readByte() & 0xFF);
/* 42 */       this.yPositionOfmodule = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 46 */       StringBuilder sb = new StringBuilder();
/* 47 */       sb.append('[');
/* 48 */       sb.append("xPositionOfmodule: ").append(this.xPositionOfmodule).append(", ");
/* 49 */       sb.append("yPositionOfmodule: ").append(this.yPositionOfmodule).append(']');
/* 50 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 56 */     out.writeObject(this.hwStatusPm);
/* 57 */     out.writeObject(this.badLedModuleList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 63 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 64 */     this.badLedModuleList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 68 */     StringBuilder sb = new StringBuilder();
/* 69 */     sb.append('[');
/* 70 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 71 */     sb.append("badLedModuleList: ").append(this.badLedModuleList).append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\GetScsLedTestRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */