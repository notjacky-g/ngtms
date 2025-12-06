/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="setStreetParamReq")
/*    */ public class SetStreetParamReqPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44825;
/*    */   public static final String cmdName = "setStreetParamReq";
/*    */   @CommandParam(name="streenStatusList")
/*    */   public List<StreenStatusListItem> streenStatusList;
/*    */   @CommandParam(name="disCycle")
/*    */   public int disCycle;
/*    */   
/*    */   public static class StreenStatusListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="streenStatus")
/*    */     public int streenStatus;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 29 */       out.writeByte(this.streenStatus);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 34 */       this.streenStatus = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 38 */       StringBuilder sb = new StringBuilder();
/* 39 */       sb.append('[');
/* 40 */       sb.append("streenStatus: ").append(this.streenStatus).append(']');
/* 41 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 50 */     out.writeObject(this.streenStatusList);
/* 51 */     out.writeByte(this.disCycle);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 57 */     this.streenStatusList = ((List)in.readObject());
/* 58 */     this.disCycle = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("streenStatusList: ").append(this.streenStatusList).append(", ");
/* 65 */     sb.append("disCycle: ").append(this.disCycle).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\SetStreetParamReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */