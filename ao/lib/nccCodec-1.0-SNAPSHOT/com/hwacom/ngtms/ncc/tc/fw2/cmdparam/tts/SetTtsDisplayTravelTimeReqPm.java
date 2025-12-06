/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tts;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setTtsDisplayTravelTimeReq")
/*    */ public class SetTtsDisplayTravelTimeReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24343;
/*    */   public static final String cmdName = "setTtsDisplayTravelTimeReq";
/*    */   @CommandParam(name="displayPosition")
/*    */   public int displayPosition;
/*    */   @CommandParam(name="travelTime")
/*    */   public byte[] travelTime;
/*    */   @CommandParam(name="backgroundColor")
/*    */   public int backgroundColor;
/*    */   @CommandParam(name="foregroundColor")
/*    */   public int foregroundColor;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeByte(this.displayPosition);
/* 33 */     out.writeObject(this.travelTime);
/* 34 */     out.writeByte(this.backgroundColor);
/* 35 */     out.writeByte(this.foregroundColor);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.displayPosition = (in.readByte() & 0xFF);
/* 41 */     this.travelTime = ((byte[])in.readObject());
/* 42 */     this.backgroundColor = (in.readByte() & 0xFFFF);
/* 43 */     this.foregroundColor = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("displayPosition: ").append(this.displayPosition).append(", ");
/* 50 */     sb.append("travelTime: ")
/* 51 */       .append(BytesUtility.toHexString(this.travelTime))
/* 52 */       .append(", ");
/* 53 */     sb.append("backgroundColor: ").append(this.backgroundColor).append(", ");
/* 54 */     sb.append("foregroundColor: ").append(this.foregroundColor).append(']');
/* 55 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tts\SetTtsDisplayTravelTimeReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */