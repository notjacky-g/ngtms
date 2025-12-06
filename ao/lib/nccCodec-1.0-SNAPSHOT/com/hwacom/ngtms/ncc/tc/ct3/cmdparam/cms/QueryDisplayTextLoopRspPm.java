/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryDisplayTextLoopRsp")
/*    */ public class QueryDisplayTextLoopRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44997;
/*    */   public static final String cmdName = "queryDisplayTextLoopRsp";
/*    */   @CommandParam(name="textId")
/*    */   public int textId;
/*    */   @CommandParam(name="startX")
/*    */   public int startX;
/*    */   @CommandParam(name="startY")
/*    */   public int startY;
/*    */   @CommandParam(name="windowWide")
/*    */   public int windowWide;
/*    */   @CommandParam(name="windowHigh")
/*    */   public int windowHigh;
/*    */   @CommandParam(name="stepX")
/*    */   public int stepX;
/*    */   @CommandParam(name="stepY")
/*    */   public int stepY;
/*    */   @CommandParam(name="speed")
/*    */   public int speed;
/*    */   @CommandParam(name="loops")
/*    */   public int loops;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 47 */     out.writeByte(this.textId);
/* 48 */     out.writeByte(this.startX);
/* 49 */     out.writeByte(this.startY);
/* 50 */     out.writeByte(this.windowWide);
/* 51 */     out.writeByte(this.windowHigh);
/* 52 */     out.writeByte(this.stepX);
/* 53 */     out.writeByte(this.stepY);
/* 54 */     out.writeByte(this.speed);
/* 55 */     out.writeByte(this.loops);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 60 */     this.textId = (in.readByte() & 0xFF);
/* 61 */     this.startX = (in.readByte() & 0xFF);
/* 62 */     this.startY = (in.readByte() & 0xFF);
/* 63 */     this.windowWide = (in.readByte() & 0xFF);
/* 64 */     this.windowHigh = (in.readByte() & 0xFF);
/* 65 */     this.stepX = (in.readByte() & 0xFF);
/* 66 */     this.stepY = (in.readByte() & 0xFF);
/* 67 */     this.speed = (in.readByte() & 0xFF);
/* 68 */     this.loops = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     sb.append('[');
/* 74 */     sb.append("textId: ").append(this.textId).append(", ");
/* 75 */     sb.append("startX: ").append(this.startX).append(", ");
/* 76 */     sb.append("startY: ").append(this.startY).append(", ");
/* 77 */     sb.append("windowWide: ").append(this.windowWide).append(", ");
/* 78 */     sb.append("windowHigh: ").append(this.windowHigh).append(", ");
/* 79 */     sb.append("stepX: ").append(this.stepX).append(", ");
/* 80 */     sb.append("stepY: ").append(this.stepY).append(", ");
/* 81 */     sb.append("speed: ").append(this.speed).append(", ");
/* 82 */     sb.append("loops: ").append(this.loops).append(']');
/* 83 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryDisplayTextLoopRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */