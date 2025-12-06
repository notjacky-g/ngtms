/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tts;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpModePm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="ttsDisplayTravelTimeChangeReport")
/*    */ public class TtsDisplayTravelTimeChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24346;
/*    */   public static final String cmdName = "ttsDisplayTravelTimeChangeReport";
/*    */   @CommandParam(name="displayPosition")
/*    */   public int displayPosition;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="commState")
/*    */   public int commState;
/*    */   @CommandParam(name="opStatus")
/*    */   public OpStatusPm opStatusPm;
/*    */   @CommandParam(name="opMode")
/*    */   public OpModePm opModePm;
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
/* 44 */     out.writeByte(this.displayPosition);
/* 45 */     out.writeObject(this.hwStatusPm);
/* 46 */     out.writeByte(this.commState);
/* 47 */     out.writeObject(this.opStatusPm);
/* 48 */     out.writeObject(this.opModePm);
/* 49 */     out.writeObject(this.travelTime);
/* 50 */     out.writeByte(this.backgroundColor);
/* 51 */     out.writeByte(this.foregroundColor);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 56 */     this.displayPosition = (in.readByte() & 0xFF);
/* 57 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 58 */     this.commState = (in.readByte() & 0xFF);
/* 59 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 60 */     this.opModePm = ((OpModePm)in.readObject());
/* 61 */     this.travelTime = ((byte[])in.readObject());
/* 62 */     this.backgroundColor = (in.readByte() & 0xFFFF);
/* 63 */     this.foregroundColor = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("displayPosition: ").append(this.displayPosition).append(", ");
/* 70 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 71 */     sb.append("commState: ").append(this.commState).append(", ");
/* 72 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 73 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 74 */     sb.append("travelTime: ")
/* 75 */       .append(BytesUtility.toHexString(this.travelTime))
/* 76 */       .append(", ");
/* 77 */     sb.append("backgroundColor: ").append(this.backgroundColor).append(", ");
/* 78 */     sb.append("foregroundColor: ").append(this.foregroundColor).append(']');
/* 79 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tts\TtsDisplayTravelTimeChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */