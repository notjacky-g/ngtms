/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @GlobalParams(paramsName="polygonPointList")
/*    */ public class PolygonPointListPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="pointList")
/*    */   public List<PointListItem> pointList;
/*    */   
/*    */   public static class PointListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="xPoint")
/*    */     public int xPoint;
/*    */     @CommandParam(name="yPoint")
/*    */     public int yPoint;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 31 */       out.writeShort(this.xPoint);
/* 32 */       out.writeShort(this.yPoint);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 37 */       this.xPoint = (in.readShort() & 0xFFFF);
/* 38 */       this.yPoint = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 42 */       StringBuilder sb = new StringBuilder();
/* 43 */       sb.append('[');
/* 44 */       sb.append("xPoint: ").append(this.xPoint).append(", ");
/* 45 */       sb.append("yPoint: ").append(this.yPoint).append(']');
/* 46 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 52 */     out.writeObject(this.pointList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 58 */     this.pointList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("pointList: ").append(this.pointList).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\PolygonPointListPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */