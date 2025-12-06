/*     */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
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
/*     */ 
/*     */ @CommandParams(cmdName="signalStepReport")
/*     */ public class SignalStepReportPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 24335;
/*     */   public static final String cmdName = "signalStepReport";
/*     */   @CommandParam(name="signalMap")
/*     */   public SignalMapPm signalMapPm;
/*     */   @CommandParam(name="greenNorth")
/*     */   public int greenNorth;
/*     */   @CommandParam(name="greenEastNorth")
/*     */   public int greenEastNorth;
/*     */   @CommandParam(name="greenEast")
/*     */   public int greenEast;
/*     */   @CommandParam(name="greenEastSouth")
/*     */   public int greenEastSouth;
/*     */   @CommandParam(name="greenSouth")
/*     */   public int greenSouth;
/*     */   @CommandParam(name="greenWestSouth")
/*     */   public int greenWestSouth;
/*     */   @CommandParam(name="greenWest")
/*     */   public int greenWest;
/*     */   @CommandParam(name="greenWestNorth")
/*     */   public int greenWestNorth;
/*     */   @CommandParam(name="yellowNorth")
/*     */   public int yellowNorth;
/*     */   @CommandParam(name="yellowEastNorth")
/*     */   public int yellowEastNorth;
/*     */   @CommandParam(name="yellowEast")
/*     */   public int yellowEast;
/*     */   @CommandParam(name="yellowEastSouth")
/*     */   public int yellowEastSouth;
/*     */   @CommandParam(name="yellowSouth")
/*     */   public int yellowSouth;
/*     */   @CommandParam(name="yellowWestSouth")
/*     */   public int yellowWestSouth;
/*     */   @CommandParam(name="yellowWest")
/*     */   public int yellowWest;
/*     */   @CommandParam(name="yellowWestNorth")
/*     */   public int yellowWestNorth;
/*     */   @CommandParam(name="redNorth")
/*     */   public int redNorth;
/*     */   @CommandParam(name="redEastNorth")
/*     */   public int redEastNorth;
/*     */   @CommandParam(name="redEast")
/*     */   public int redEast;
/*     */   @CommandParam(name="redEastSouth")
/*     */   public int redEastSouth;
/*     */   @CommandParam(name="redSouth")
/*     */   public int redSouth;
/*     */   @CommandParam(name="redWestSouth")
/*     */   public int redWestSouth;
/*     */   @CommandParam(name="redWest")
/*     */   public int redWest;
/*     */   @CommandParam(name="redWestNorth")
/*     */   public int redWestNorth;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  95 */     out.writeObject(this.signalMapPm);
/*  96 */     out.writeByte(this.greenNorth);
/*  97 */     out.writeByte(this.greenEastNorth);
/*  98 */     out.writeByte(this.greenEast);
/*  99 */     out.writeByte(this.greenEastSouth);
/* 100 */     out.writeByte(this.greenSouth);
/* 101 */     out.writeByte(this.greenWestSouth);
/* 102 */     out.writeByte(this.greenWest);
/* 103 */     out.writeByte(this.greenWestNorth);
/* 104 */     out.writeByte(this.yellowNorth);
/* 105 */     out.writeByte(this.yellowEastNorth);
/* 106 */     out.writeByte(this.yellowEast);
/* 107 */     out.writeByte(this.yellowEastSouth);
/* 108 */     out.writeByte(this.yellowSouth);
/* 109 */     out.writeByte(this.yellowWestSouth);
/* 110 */     out.writeByte(this.yellowWest);
/* 111 */     out.writeByte(this.yellowWestNorth);
/* 112 */     out.writeByte(this.redNorth);
/* 113 */     out.writeByte(this.redEastNorth);
/* 114 */     out.writeByte(this.redEast);
/* 115 */     out.writeByte(this.redEastSouth);
/* 116 */     out.writeByte(this.redSouth);
/* 117 */     out.writeByte(this.redWestSouth);
/* 118 */     out.writeByte(this.redWest);
/* 119 */     out.writeByte(this.redWestNorth);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/* 124 */     this.signalMapPm = ((SignalMapPm)in.readObject());
/* 125 */     this.greenNorth = (in.readByte() & 0xFFFF);
/* 126 */     this.greenEastNorth = (in.readByte() & 0xFFFF);
/* 127 */     this.greenEast = (in.readByte() & 0xFFFF);
/* 128 */     this.greenEastSouth = (in.readByte() & 0xFFFF);
/* 129 */     this.greenSouth = (in.readByte() & 0xFFFF);
/* 130 */     this.greenWestSouth = (in.readByte() & 0xFFFF);
/* 131 */     this.greenWest = (in.readByte() & 0xFFFF);
/* 132 */     this.greenWestNorth = (in.readByte() & 0xFFFF);
/* 133 */     this.yellowNorth = (in.readByte() & 0xFFFF);
/* 134 */     this.yellowEastNorth = (in.readByte() & 0xFFFF);
/* 135 */     this.yellowEast = (in.readByte() & 0xFFFF);
/* 136 */     this.yellowEastSouth = (in.readByte() & 0xFFFF);
/* 137 */     this.yellowSouth = (in.readByte() & 0xFFFF);
/* 138 */     this.yellowWestSouth = (in.readByte() & 0xFFFF);
/* 139 */     this.yellowWest = (in.readByte() & 0xFFFF);
/* 140 */     this.yellowWestNorth = (in.readByte() & 0xFFFF);
/* 141 */     this.redNorth = (in.readByte() & 0xFFFF);
/* 142 */     this.redEastNorth = (in.readByte() & 0xFFFF);
/* 143 */     this.redEast = (in.readByte() & 0xFFFF);
/* 144 */     this.redEastSouth = (in.readByte() & 0xFFFF);
/* 145 */     this.redSouth = (in.readByte() & 0xFFFF);
/* 146 */     this.redWestSouth = (in.readByte() & 0xFFFF);
/* 147 */     this.redWest = (in.readByte() & 0xFFFF);
/* 148 */     this.redWestNorth = (in.readByte() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 152 */     StringBuilder sb = new StringBuilder();
/* 153 */     sb.append('[');
/* 154 */     sb.append("signalMapPm: ").append(this.signalMapPm).append(", ");
/* 155 */     sb.append("greenNorth: ").append(this.greenNorth).append(", ");
/* 156 */     sb.append("greenEastNorth: ").append(this.greenEastNorth).append(", ");
/* 157 */     sb.append("greenEast: ").append(this.greenEast).append(", ");
/* 158 */     sb.append("greenEastSouth: ").append(this.greenEastSouth).append(", ");
/* 159 */     sb.append("greenSouth: ").append(this.greenSouth).append(", ");
/* 160 */     sb.append("greenWestSouth: ").append(this.greenWestSouth).append(", ");
/* 161 */     sb.append("greenWest: ").append(this.greenWest).append(", ");
/* 162 */     sb.append("greenWestNorth: ").append(this.greenWestNorth).append(", ");
/* 163 */     sb.append("yellowNorth: ").append(this.yellowNorth).append(", ");
/* 164 */     sb.append("yellowEastNorth: ").append(this.yellowEastNorth).append(", ");
/* 165 */     sb.append("yellowEast: ").append(this.yellowEast).append(", ");
/* 166 */     sb.append("yellowEastSouth: ").append(this.yellowEastSouth).append(", ");
/* 167 */     sb.append("yellowSouth: ").append(this.yellowSouth).append(", ");
/* 168 */     sb.append("yellowWestSouth: ").append(this.yellowWestSouth).append(", ");
/* 169 */     sb.append("yellowWest: ").append(this.yellowWest).append(", ");
/* 170 */     sb.append("yellowWestNorth: ").append(this.yellowWestNorth).append(", ");
/* 171 */     sb.append("redNorth: ").append(this.redNorth).append(", ");
/* 172 */     sb.append("redEastNorth: ").append(this.redEastNorth).append(", ");
/* 173 */     sb.append("redEast: ").append(this.redEast).append(", ");
/* 174 */     sb.append("redEastSouth: ").append(this.redEastSouth).append(", ");
/* 175 */     sb.append("redSouth: ").append(this.redSouth).append(", ");
/* 176 */     sb.append("redWestSouth: ").append(this.redWestSouth).append(", ");
/* 177 */     sb.append("redWest: ").append(this.redWest).append(", ");
/* 178 */     sb.append("redWestNorth: ").append(this.redWestNorth).append(']');
/* 179 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SignalStepReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */