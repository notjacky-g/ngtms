/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
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
/*    */ @CommandParams(cmdName="setRgsPolygonReq")
/*    */ public class SetRgsPolygonReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 154;
/*    */   public static final String cmdName = "setRgsPolygonReq";
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="setPolygonList")
/*    */   public List<SetPolygonListItem> setPolygonList;
/*    */   
/*    */   public static class SetPolygonListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="sectionId")
/*    */     public int sectionId;
/*    */     @CommandParam(name="polygonPointList")
/*    */     public PolygonPointListPm polygonPointListPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 36 */       out.writeByte(this.sectionId);
/* 37 */       out.writeObject(this.polygonPointListPm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 42 */       this.sectionId = (in.readByte() & 0xFF);
/* 43 */       this.polygonPointListPm = ((PolygonPointListPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("sectionId: ").append(this.sectionId).append(", ");
/* 50 */       sb.append("polygonPointListPm: ").append(this.polygonPointListPm).append(']');
/* 51 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 57 */     out.writeByte(this.gCodeId);
/* 58 */     out.writeObject(this.setPolygonList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 64 */     this.gCodeId = (in.readByte() & 0xFF);
/* 65 */     this.setPolygonList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 69 */     StringBuilder sb = new StringBuilder();
/* 70 */     sb.append('[');
/* 71 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 72 */     sb.append("setPolygonList: ").append(this.setPolygonList).append(']');
/* 73 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\SetRgsPolygonReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */