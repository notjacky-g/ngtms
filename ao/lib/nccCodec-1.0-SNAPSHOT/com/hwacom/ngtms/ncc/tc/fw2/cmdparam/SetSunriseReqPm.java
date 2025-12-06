/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
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
/*    */ @CommandParams(cmdName="setSunriseReq")
/*    */ public class SetSunriseReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 9;
/*    */   public static final String cmdName = "setSunriseReq";
/*    */   @CommandParam(name="sunriseHour")
/*    */   public int sunriseHour;
/*    */   @CommandParam(name="sunriseMinute")
/*    */   public int sunriseMinute;
/*    */   @CommandParam(name="sunsetHour")
/*    */   public int sunsetHour;
/*    */   @CommandParam(name="sunsetMinute")
/*    */   public int sunsetMinute;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 31 */     out.writeByte(this.sunriseHour);
/* 32 */     out.writeByte(this.sunriseMinute);
/* 33 */     out.writeByte(this.sunsetHour);
/* 34 */     out.writeByte(this.sunsetMinute);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 39 */     this.sunriseHour = (in.readByte() & 0xFF);
/* 40 */     this.sunriseMinute = (in.readByte() & 0xFF);
/* 41 */     this.sunsetHour = (in.readByte() & 0xFF);
/* 42 */     this.sunsetMinute = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append('[');
/* 48 */     sb.append("sunriseHour: ").append(this.sunriseHour).append(", ");
/* 49 */     sb.append("sunriseMinute: ").append(this.sunriseMinute).append(", ");
/* 50 */     sb.append("sunsetHour: ").append(this.sunsetHour).append(", ");
/* 51 */     sb.append("sunsetMinute: ").append(this.sunsetMinute).append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\SetSunriseReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */