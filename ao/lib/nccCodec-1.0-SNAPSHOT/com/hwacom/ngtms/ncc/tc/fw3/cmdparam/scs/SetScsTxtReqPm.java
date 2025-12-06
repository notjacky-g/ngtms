/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setScsTxtReq")
/*    */ public class SetScsTxtReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 53227;
/*    */   public static final String cmdName = "setScsTxtReq";
/*    */   @CommandParam(name="txtLevel")
/*    */   public int txtLevel;
/*    */   @CommandParam(name="txtId")
/*    */   public int txtId;
/*    */   @CommandParam(name="txtColorR")
/*    */   public int txtColorR;
/*    */   @CommandParam(name="txtColorG")
/*    */   public int txtColorG;
/*    */   @CommandParam(name="txtColorB")
/*    */   public int txtColorB;
/*    */   @CommandParam(name="lineDataList")
/*    */   public List<LineDataListItem> lineDataList;
/*    */   
/*    */   public static class LineDataListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="lineData")
/*    */     public byte[] lineData;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 44 */       out.writeObject(this.lineData);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 49 */       this.lineData = ((byte[])in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 53 */       StringBuilder sb = new StringBuilder();
/* 54 */       sb.append('[');
/* 55 */       sb.append("lineData: ")
/* 56 */         .append(BytesUtility.toHexString(this.lineData))
/* 57 */         .append(']');
/* 58 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 64 */     out.writeByte(this.txtLevel);
/* 65 */     out.writeByte(this.txtId);
/* 66 */     out.writeByte(this.txtColorR);
/* 67 */     out.writeByte(this.txtColorG);
/* 68 */     out.writeByte(this.txtColorB);
/* 69 */     out.writeObject(this.lineDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 75 */     this.txtLevel = (in.readByte() & 0xFF);
/* 76 */     this.txtId = (in.readByte() & 0xFF);
/* 77 */     this.txtColorR = (in.readByte() & 0xFF);
/* 78 */     this.txtColorG = (in.readByte() & 0xFF);
/* 79 */     this.txtColorB = (in.readByte() & 0xFF);
/* 80 */     this.lineDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 84 */     StringBuilder sb = new StringBuilder();
/* 85 */     sb.append('[');
/* 86 */     sb.append("txtLevel: ").append(this.txtLevel).append(", ");
/* 87 */     sb.append("txtId: ").append(this.txtId).append(", ");
/* 88 */     sb.append("txtColorR: ").append(this.txtColorR).append(", ");
/* 89 */     sb.append("txtColorG: ").append(this.txtColorG).append(", ");
/* 90 */     sb.append("txtColorB: ").append(this.txtColorB).append(", ");
/* 91 */     sb.append("lineDataList: ").append(this.lineDataList).append(']');
/* 92 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\SetScsTxtReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */