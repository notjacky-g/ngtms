/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.List;
/*     */ 
/*     */ @GlobalParams(paramsName="genericMessage")
/*     */ public class GenericMessagePm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @CommandParam(name="mode")
/*     */   public int mode;
/*     */   @CommandParam(name="gCodeId")
/*     */   public int gCodeId;
/*     */   @CommandParam(name="iconCodeList")
/*     */   public List<IconCodeListItem> iconCodeList;
/*     */   @CommandParam(name="msgList")
/*     */   public List<MsgListItem> msgList;
/*     */   @CommandParam(name="sectionStatusList")
/*     */   public List<SectionStatusListItem> sectionStatusList;
/*     */   
/*     */   public static class IconCodeListItem implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="iconCodeId")
/*     */     public int iconCodeId;
/*     */     @CommandParam(name="xPointOfIcon")
/*     */     public int xPointOfIcon;
/*     */     @CommandParam(name="yPointOfIcon")
/*     */     public int yPointOfIcon;
/*     */     
/*     */     public void writeExternal(ObjectOutput out) throws IOException
/*     */     {
/*  40 */       out.writeByte(this.iconCodeId);
/*  41 */       out.writeShort(this.xPointOfIcon);
/*  42 */       out.writeShort(this.yPointOfIcon);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  47 */       this.iconCodeId = (in.readByte() & 0xFF);
/*  48 */       this.xPointOfIcon = (in.readShort() & 0xFFFF);
/*  49 */       this.yPointOfIcon = (in.readShort() & 0xFFFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  53 */       StringBuilder sb = new StringBuilder();
/*  54 */       sb.append('[');
/*  55 */       sb.append("iconCodeId: ").append(this.iconCodeId).append(", ");
/*  56 */       sb.append("xPointOfIcon: ").append(this.xPointOfIcon).append(", ");
/*  57 */       sb.append("yPointOfIcon: ").append(this.yPointOfIcon).append(']');
/*  58 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class MsgListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     @CommandParam(name="message")
/*     */     public byte[] message;
/*     */     
/*     */     @CommandParam(name="color")
/*     */     public byte[] color;
/*     */     
/*     */     @CommandParam(name="xPointOfMsg")
/*     */     public int xPointOfMsg;
/*     */     @CommandParam(name="yPointOfMsg")
/*     */     public int yPointOfMsg;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  82 */       out.writeObject(this.message);
/*  83 */       out.writeObject(this.color);
/*  84 */       out.writeShort(this.xPointOfMsg);
/*  85 */       out.writeShort(this.yPointOfMsg);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  90 */       this.message = ((byte[])in.readObject());
/*  91 */       this.color = ((byte[])in.readObject());
/*  92 */       this.xPointOfMsg = (in.readShort() & 0xFFFF);
/*  93 */       this.yPointOfMsg = (in.readShort() & 0xFFFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  97 */       StringBuilder sb = new StringBuilder();
/*  98 */       sb.append('[');
/*  99 */       sb.append("message: ")
/* 100 */         .append(BytesUtility.toHexString(this.message))
/* 101 */         .append(", ");
/* 102 */       sb.append("color: ")
/* 103 */         .append(BytesUtility.toHexString(this.color))
/* 104 */         .append(", ");
/* 105 */       sb.append("xPointOfMsg: ").append(this.xPointOfMsg).append(", ");
/* 106 */       sb.append("yPointOfMsg: ").append(this.yPointOfMsg).append(']');
/* 107 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class SectionStatusListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     @CommandParam(name="sectionNo")
/*     */     public int sectionNo;
/*     */     @CommandParam(name="trafficStatus")
/*     */     public int trafficStatus;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/* 125 */       out.writeByte(this.sectionNo);
/* 126 */       out.writeByte(this.trafficStatus);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/* 131 */       this.sectionNo = (in.readByte() & 0xFF);
/* 132 */       this.trafficStatus = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 136 */       StringBuilder sb = new StringBuilder();
/* 137 */       sb.append('[');
/* 138 */       sb.append("sectionNo: ").append(this.sectionNo).append(", ");
/* 139 */       sb.append("trafficStatus: ").append(this.trafficStatus).append(']');
/* 140 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/* 146 */     out.writeByte(this.mode);
/* 147 */     out.writeByte(this.gCodeId);
/* 148 */     out.writeObject(this.iconCodeList);
/* 149 */     out.writeObject(this.msgList);
/* 150 */     out.writeObject(this.sectionStatusList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 156 */     this.mode = (in.readByte() & 0xFF);
/* 157 */     this.gCodeId = (in.readByte() & 0xFF);
/* 158 */     this.iconCodeList = ((List)in.readObject());
/* 159 */     this.msgList = ((List)in.readObject());
/* 160 */     this.sectionStatusList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 164 */     StringBuilder sb = new StringBuilder();
/* 165 */     sb.append('[');
/* 166 */     sb.append("mode: ").append(this.mode).append(", ");
/* 167 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 168 */     sb.append("iconCodeList: ").append(this.iconCodeList).append(", ");
/* 169 */     sb.append("msgList: ").append(this.msgList).append(", ");
/* 170 */     sb.append("sectionStatusList: ").append(this.sectionStatusList).append(']');
/* 171 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\GenericMessagePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */