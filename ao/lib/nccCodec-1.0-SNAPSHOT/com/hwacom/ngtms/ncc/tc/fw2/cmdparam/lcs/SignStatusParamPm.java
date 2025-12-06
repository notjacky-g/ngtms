/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lcs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @GlobalParams(paramsName="signStatusParam")
/*    */ public class SignStatusParamPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="signStatusList")
/*    */   public List<SignStatusListItem> signStatusList;
/*    */   
/*    */   public static class SignStatusListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="signNo")
/*    */     public int signNo;
/*    */     @CommandParam(name="signStatus")
/*    */     public int signStatus;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 30 */       out.writeByte(this.signNo);
/* 31 */       out.writeByte(this.signStatus);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 36 */       this.signNo = (in.readByte() & 0xFF);
/* 37 */       this.signStatus = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 41 */       StringBuilder sb = new StringBuilder();
/* 42 */       sb.append('[');
/* 43 */       sb.append("signNo: ").append(this.signNo).append(", ");
/* 44 */       sb.append("signStatus: ").append(this.signStatus).append(']');
/* 45 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 51 */     out.writeObject(this.signStatusList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 57 */     this.signStatusList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     StringBuilder sb = new StringBuilder();
/* 62 */     sb.append('[');
/* 63 */     sb.append("signStatusList: ").append(this.signStatusList).append(']');
/* 64 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lcs\SignStatusParamPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */