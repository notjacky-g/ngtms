/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.etag;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="eTagDataReport")
/*    */ public class ETagDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 16146;
/*    */   public static final String cmdName = "eTagDataReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="list")
/*    */   public List<ListItem> list;
/*    */   
/*    */   public static class ListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneId")
/*    */     public int laneId;
/*    */     @CommandParam(name="carType")
/*    */     public int carType;
/*    */     @CommandParam(name="second")
/*    */     public int second;
/*    */     @CommandParam(name="tagId")
/*    */     public byte[] tagId;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 44 */       out.writeByte(this.laneId);
/* 45 */       out.writeByte(this.carType);
/* 46 */       out.writeByte(this.second);
/* 47 */       out.writeObject(this.tagId);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 52 */       this.laneId = (in.readByte() & 0xFF);
/* 53 */       this.carType = (in.readByte() & 0xFF);
/* 54 */       this.second = (in.readByte() & 0xFF);
/* 55 */       this.tagId = ((byte[])in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 59 */       StringBuilder sb = new StringBuilder();
/* 60 */       sb.append('[');
/* 61 */       sb.append("laneId: ").append(this.laneId).append(", ");
/* 62 */       sb.append("carType: ").append(this.carType).append(", ");
/* 63 */       sb.append("second: ").append(this.second).append(", ");
/* 64 */       sb.append("tagId: ")
/* 65 */         .append(BytesUtility.toHexString(this.tagId))
/* 66 */         .append(']');
/* 67 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 73 */     out.writeObject(this.hwStatusPm);
/* 74 */     out.writeObject(this.dhmPm);
/* 75 */     out.writeObject(this.list);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 81 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 82 */     this.dhmPm = ((DhmPm)in.readObject());
/* 83 */     this.list = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     StringBuilder sb = new StringBuilder();
/* 88 */     sb.append('[');
/* 89 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 90 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 91 */     sb.append("list: ").append(this.list).append(']');
/* 92 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\etag\ETagDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */