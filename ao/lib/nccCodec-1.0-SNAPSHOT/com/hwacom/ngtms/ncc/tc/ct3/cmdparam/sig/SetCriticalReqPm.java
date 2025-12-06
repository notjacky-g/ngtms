/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setCriticalReq")
/*    */ public class SetCriticalReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24349;
/*    */   public static final String cmdName = "setCriticalReq";
/*    */   @CommandParam(name="subPhaseCount")
/*    */   public int subPhaseCount;
/*    */   @CommandParam(name="cycleTime")
/*    */   public int cycleTime;
/*    */   @CommandParam(name="offset")
/*    */   public int offset;
/*    */   @CommandParam(name="criticalList")
/*    */   public List<CriticalListItem> criticalList;
/*    */   
/*    */   public static class CriticalListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="green")
/*    */     public int green;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 38 */       out.writeShort(this.green);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 43 */       this.green = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("green: ").append(this.green).append(']');
/* 50 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 56 */     out.writeByte(this.subPhaseCount);
/* 57 */     out.writeShort(this.cycleTime);
/* 58 */     out.writeShort(this.offset);
/* 59 */     out.writeObject(this.criticalList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 65 */     this.subPhaseCount = (in.readByte() & 0xFF);
/* 66 */     this.cycleTime = (in.readShort() & 0xFFFF);
/* 67 */     this.offset = (in.readShort() & 0xFFFF);
/* 68 */     this.criticalList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     sb.append('[');
/* 74 */     sb.append("subPhaseCount: ").append(this.subPhaseCount).append(", ");
/* 75 */     sb.append("cycleTime: ").append(this.cycleTime).append(", ");
/* 76 */     sb.append("offset: ").append(this.offset).append(", ");
/* 77 */     sb.append("criticalList: ").append(this.criticalList).append(']');
/* 78 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SetCriticalReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */