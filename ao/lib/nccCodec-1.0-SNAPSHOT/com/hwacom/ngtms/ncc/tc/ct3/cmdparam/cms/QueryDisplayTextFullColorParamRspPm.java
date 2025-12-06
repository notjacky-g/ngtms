/*     */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*     */ 
/*     */ import java.io.ObjectOutput;
/*     */ 
/*     */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="queryDisplayTextFullColorParamRsp")
/*     */ public class QueryDisplayTextFullColorParamRspPm implements java.io.Externalizable {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 45012;
/*     */   public static final String cmdName = "queryDisplayTextFullColorParamRsp";
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="textId")
/*     */   public int textId;
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="fontColorList")
/*     */   public java.util.List<FontColorListItem> fontColorList;
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="bgColorList")
/*     */   public java.util.List<BgColorListItem> bgColorList;
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="glitterSpeedList")
/*     */   public java.util.List<GlitterSpeedListItem> glitterSpeedList;
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="vBound")
/*     */   public int vBound;
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="hBound")
/*     */   public int hBound;
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="vSpace")
/*     */   public int vSpace;
/*     */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="hSpace")
/*     */   public int hSpace;
/*     */   
/*     */   public static class FontColorListItem implements java.io.Externalizable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="fullColor")
/*     */     public FullColorPm fullColorPm;
/*     */     
/*  32 */     public void writeExternal(ObjectOutput out) throws java.io.IOException { out.writeObject(this.fullColorPm); }
/*     */     
/*     */     public void readExternal(java.io.ObjectInput in)
/*     */       throws java.io.IOException, ClassNotFoundException
/*     */     {
/*  37 */       this.fullColorPm = ((FullColorPm)in.readObject());
/*     */     }
/*     */     
/*     */     public String toString() {
/*  41 */       StringBuilder sb = new StringBuilder();
/*  42 */       sb.append('[');
/*  43 */       sb.append("fullColorPm: ").append(this.fullColorPm).append(']');
/*  44 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class BgColorListItem
/*     */     implements java.io.Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="fullColor")
/*     */     public FullColorPm fullColorPm;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws java.io.IOException
/*     */     {
/*  59 */       out.writeObject(this.fullColorPm);
/*     */     }
/*     */     
/*     */     public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException
/*     */     {
/*  64 */       this.fullColorPm = ((FullColorPm)in.readObject());
/*     */     }
/*     */     
/*     */     public String toString() {
/*  68 */       StringBuilder sb = new StringBuilder();
/*  69 */       sb.append('[');
/*  70 */       sb.append("fullColorPm: ").append(this.fullColorPm).append(']');
/*  71 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class GlitterSpeedListItem
/*     */     implements java.io.Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="glitterSpeed")
/*     */     public int glitterSpeed;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws java.io.IOException
/*     */     {
/*  86 */       out.writeByte(this.glitterSpeed);
/*     */     }
/*     */     
/*     */     public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException
/*     */     {
/*  91 */       this.glitterSpeed = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  95 */       StringBuilder sb = new StringBuilder();
/*  96 */       sb.append('[');
/*  97 */       sb.append("glitterSpeed: ").append(this.glitterSpeed).append(']');
/*  98 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws java.io.IOException
/*     */   {
/* 116 */     out.writeByte(this.textId);
/* 117 */     out.writeObject(this.fontColorList);
/* 118 */     out.writeObject(this.bgColorList);
/* 119 */     out.writeObject(this.glitterSpeedList);
/* 120 */     out.writeByte(this.vBound);
/* 121 */     out.writeByte(this.hBound);
/* 122 */     out.writeShort(this.vSpace);
/* 123 */     out.writeShort(this.hSpace);
/*     */   }
/*     */   
/*     */   public void readExternal(java.io.ObjectInput in)
/*     */     throws java.io.IOException, ClassNotFoundException
/*     */   {
/* 129 */     this.textId = (in.readByte() & 0xFF);
/* 130 */     this.fontColorList = ((java.util.List)in.readObject());
/* 131 */     this.bgColorList = ((java.util.List)in.readObject());
/* 132 */     this.glitterSpeedList = ((java.util.List)in.readObject());
/* 133 */     this.vBound = (in.readByte() & 0xFF);
/* 134 */     this.hBound = (in.readByte() & 0xFF);
/* 135 */     this.vSpace = (in.readShort() & 0xFFFF);
/* 136 */     this.hSpace = (in.readShort() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 140 */     StringBuilder sb = new StringBuilder();
/* 141 */     sb.append('[');
/* 142 */     sb.append("textId: ").append(this.textId).append(", ");
/* 143 */     sb.append("fontColorList: ").append(this.fontColorList).append(", ");
/* 144 */     sb.append("bgColorList: ").append(this.bgColorList).append(", ");
/* 145 */     sb.append("glitterSpeedList: ").append(this.glitterSpeedList).append(", ");
/* 146 */     sb.append("vBound: ").append(this.vBound).append(", ");
/* 147 */     sb.append("hBound: ").append(this.hBound).append(", ");
/* 148 */     sb.append("vSpace: ").append(this.vSpace).append(", ");
/* 149 */     sb.append("hSpace: ").append(this.hSpace).append(']');
/* 150 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryDisplayTextFullColorParamRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */