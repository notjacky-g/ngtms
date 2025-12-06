/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.c.shared.DisplayMatch;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusHiNibble;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusLowNibble;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.Lob;
/*     */ import javax.persistence.Transient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class DeviceTcStatus
/*     */   extends DeviceStatus
/*     */ {
/*  21 */   public static final Integer COMM_STATUS_OFFLINE = Integer.valueOf(1);
/*     */   
/*  23 */   public static final Integer COMM_STATUS_ONLINE = Integer.valueOf(0);
/*     */   
/*  25 */   public static final Boolean BIT_NORMAL = Boolean.valueOf(false);
/*     */   
/*  27 */   public static final Integer OP_MODE_NORMAL = Integer.valueOf(0);
/*     */   
/*  29 */   public static final Integer OP_STATUS_NORMAL = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -4211408318154154910L;
/*     */ 
/*     */   
/*     */   private Boolean bit0;
/*     */ 
/*     */   
/*     */   private Boolean bit1;
/*     */ 
/*     */   
/*     */   private Boolean bit2;
/*     */ 
/*     */   
/*     */   private Boolean bit3;
/*     */ 
/*     */   
/*     */   private Boolean bit4;
/*     */ 
/*     */   
/*     */   private Boolean bit5;
/*     */   
/*     */   private Boolean bit6;
/*     */   
/*     */   private Boolean bit7;
/*     */   
/*     */   private Boolean bit8;
/*     */   
/*     */   private Boolean bit9;
/*     */   
/*     */   private Boolean bit10;
/*     */   
/*     */   private Boolean bit11;
/*     */   
/*     */   private Boolean bit12;
/*     */   
/*     */   private Boolean bit13;
/*     */   
/*     */   private Boolean bit14;
/*     */   
/*     */   private Boolean bit15;
/*     */   
/*     */   private Boolean bit16;
/*     */   
/*     */   private Boolean bit17;
/*     */   
/*     */   private Boolean bit18;
/*     */   
/*     */   private Boolean bit19;
/*     */   
/*     */   private Boolean bit20;
/*     */   
/*     */   private Boolean bit21;
/*     */   
/*     */   private Boolean bit22;
/*     */   
/*     */   private Boolean bit23;
/*     */   
/*     */   private Boolean bit24;
/*     */   
/*     */   private Boolean bit25;
/*     */   
/*     */   private Boolean bit26;
/*     */   
/*     */   private Boolean bit27;
/*     */   
/*     */   private Boolean bit28;
/*     */   
/*     */   private Boolean bit29;
/*     */   
/*     */   private Boolean bit30;
/*     */   
/*     */   private Boolean bit31;
/*     */   
/*     */   private Integer opMode;
/*     */   
/*     */   @Enumerated(EnumType.STRING)
/* 107 */   private OpStatusHiNibble opStatusHiNibble = OpStatusHiNibble.NONE;
/*     */ 
/*     */   
/*     */   @Enumerated(EnumType.STRING)
/* 111 */   private OpStatusLowNibble opStatusLowNibble = OpStatusLowNibble.NONE;
/*     */ 
/*     */   
/*     */   private String display;
/*     */ 
/*     */   
/*     */   @Enumerated(EnumType.STRING)
/*     */   private DisplayMatch isDisplayContentMatch;
/*     */ 
/*     */   
/*     */   @Transient
/*     */   private String updatedBy;
/*     */ 
/*     */   
/*     */   private String tcMessage;
/*     */ 
/*     */   
/*     */   private String tcIconDescription;
/*     */ 
/*     */   
/*     */   private String tcIconDescription2;
/*     */ 
/*     */   
/*     */   private String tcGCodeDescription;
/*     */   
/*     */   @Lob
/*     */   private byte[] contextData;
/*     */ 
/*     */   
/*     */   public DeviceTcStatus() {}
/*     */ 
/*     */   
/*     */   public DeviceTcStatus(String deviceName) {
/* 144 */     setId(deviceName);
/*     */   }
/*     */   public boolean getBit(int bitIndex) {
/*     */     Boolean bit;
/* 148 */     if (bitIndex < 0 || bitIndex > 31) return false;
/*     */     
/* 150 */     switch (bitIndex) {
/*     */       case 0:
/* 152 */         bit = getBit0();
/*     */         break;
/*     */       case 1:
/* 155 */         bit = getBit1();
/*     */         break;
/*     */       case 2:
/* 158 */         bit = getBit2();
/*     */         break;
/*     */       case 3:
/* 161 */         bit = getBit3();
/*     */         break;
/*     */       case 4:
/* 164 */         bit = getBit4();
/*     */         break;
/*     */       case 5:
/* 167 */         bit = getBit5();
/*     */         break;
/*     */       case 6:
/* 170 */         bit = getBit6();
/*     */         break;
/*     */       case 7:
/* 173 */         bit = getBit7();
/*     */         break;
/*     */       case 8:
/* 176 */         bit = getBit8();
/*     */         break;
/*     */       case 9:
/* 179 */         bit = getBit9();
/*     */         break;
/*     */       case 10:
/* 182 */         bit = getBit10();
/*     */         break;
/*     */       case 11:
/* 185 */         bit = getBit11();
/*     */         break;
/*     */       case 12:
/* 188 */         bit = getBit12();
/*     */         break;
/*     */       case 13:
/* 191 */         bit = getBit13();
/*     */         break;
/*     */       case 14:
/* 194 */         bit = getBit14();
/*     */         break;
/*     */       case 15:
/* 197 */         bit = getBit15();
/*     */         break;
/*     */       case 16:
/* 200 */         bit = getBit16();
/*     */         break;
/*     */       case 17:
/* 203 */         bit = getBit17();
/*     */         break;
/*     */       case 18:
/* 206 */         bit = getBit18();
/*     */         break;
/*     */       case 19:
/* 209 */         bit = getBit19();
/*     */         break;
/*     */       case 20:
/* 212 */         bit = getBit20();
/*     */         break;
/*     */       case 21:
/* 215 */         bit = getBit21();
/*     */         break;
/*     */       case 22:
/* 218 */         bit = getBit22();
/*     */         break;
/*     */       case 23:
/* 221 */         bit = getBit23();
/*     */         break;
/*     */       case 24:
/* 224 */         bit = getBit24();
/*     */         break;
/*     */       case 25:
/* 227 */         bit = getBit25();
/*     */         break;
/*     */       case 26:
/* 230 */         bit = getBit26();
/*     */         break;
/*     */       case 27:
/* 233 */         bit = getBit27();
/*     */         break;
/*     */       case 28:
/* 236 */         bit = getBit28();
/*     */         break;
/*     */       case 29:
/* 239 */         bit = getBit29();
/*     */         break;
/*     */       case 30:
/* 242 */         bit = getBit30();
/*     */         break;
/*     */       case 31:
/* 245 */         bit = getBit31();
/*     */         break;
/*     */       default:
/* 248 */         return false;
/*     */     } 
/* 250 */     if (bit == null) {
/* 251 */       return false;
/*     */     }
/* 253 */     return bit.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBit(int bitIndex, boolean value) {
/* 258 */     if (bitIndex < 0 || bitIndex > 31)
/* 259 */       return;  Boolean bit = Boolean.valueOf(value);
/* 260 */     switch (bitIndex) {
/*     */       case 0:
/* 262 */         setBit0(bit);
/*     */         return;
/*     */       case 1:
/* 265 */         setBit1(bit);
/*     */         return;
/*     */       case 2:
/* 268 */         setBit2(bit);
/*     */         return;
/*     */       case 3:
/* 271 */         setBit3(bit);
/*     */         return;
/*     */       case 4:
/* 274 */         setBit4(bit);
/*     */         return;
/*     */       case 5:
/* 277 */         setBit5(bit);
/*     */         return;
/*     */       case 6:
/* 280 */         setBit6(bit);
/*     */         return;
/*     */       case 7:
/* 283 */         setBit7(bit);
/*     */         return;
/*     */       case 8:
/* 286 */         setBit8(bit);
/*     */         return;
/*     */       case 9:
/* 289 */         setBit9(bit);
/*     */         return;
/*     */       case 10:
/* 292 */         setBit10(bit);
/*     */         return;
/*     */       case 11:
/* 295 */         setBit11(bit);
/*     */         return;
/*     */       case 12:
/* 298 */         setBit12(bit);
/*     */         return;
/*     */       case 13:
/* 301 */         setBit13(bit);
/*     */         return;
/*     */       case 14:
/* 304 */         setBit14(bit);
/*     */         return;
/*     */       case 15:
/* 307 */         setBit15(bit);
/*     */         return;
/*     */       case 16:
/* 310 */         setBit16(bit);
/*     */         return;
/*     */       case 17:
/* 313 */         setBit17(bit);
/*     */         return;
/*     */       case 18:
/* 316 */         setBit18(bit);
/*     */         return;
/*     */       case 19:
/* 319 */         setBit19(bit);
/*     */         return;
/*     */       case 20:
/* 322 */         setBit20(bit);
/*     */         return;
/*     */       case 21:
/* 325 */         setBit21(bit);
/*     */         return;
/*     */       case 22:
/* 328 */         setBit22(bit);
/*     */         return;
/*     */       case 23:
/* 331 */         setBit23(bit);
/*     */         return;
/*     */       case 24:
/* 334 */         setBit24(bit);
/*     */         return;
/*     */       case 25:
/* 337 */         setBit25(bit);
/*     */         return;
/*     */       case 26:
/* 340 */         setBit26(bit);
/*     */         return;
/*     */       case 27:
/* 343 */         setBit27(bit);
/*     */         return;
/*     */       case 28:
/* 346 */         setBit28(bit);
/*     */         return;
/*     */       case 29:
/* 349 */         setBit29(bit);
/*     */         return;
/*     */       case 30:
/* 352 */         setBit30(bit);
/*     */         return;
/*     */       case 31:
/* 355 */         setBit31(bit);
/*     */         return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean getBit0() {
/* 363 */     return this.bit0;
/*     */   }
/*     */   
/*     */   private void setBit0(Boolean bit0) {
/* 367 */     this.bit0 = bit0;
/*     */   }
/*     */   
/*     */   private Boolean getBit1() {
/* 371 */     return this.bit1;
/*     */   }
/*     */   
/*     */   private void setBit1(Boolean bit1) {
/* 375 */     this.bit1 = bit1;
/*     */   }
/*     */   
/*     */   private Boolean getBit2() {
/* 379 */     return this.bit2;
/*     */   }
/*     */   
/*     */   private void setBit2(Boolean bit2) {
/* 383 */     this.bit2 = bit2;
/*     */   }
/*     */   
/*     */   private Boolean getBit3() {
/* 387 */     return this.bit3;
/*     */   }
/*     */   
/*     */   private void setBit3(Boolean bit3) {
/* 391 */     this.bit3 = bit3;
/*     */   }
/*     */   
/*     */   private Boolean getBit4() {
/* 395 */     return this.bit4;
/*     */   }
/*     */   
/*     */   private void setBit4(Boolean bit4) {
/* 399 */     this.bit4 = bit4;
/*     */   }
/*     */   
/*     */   private Boolean getBit5() {
/* 403 */     return this.bit5;
/*     */   }
/*     */   
/*     */   private void setBit5(Boolean bit5) {
/* 407 */     this.bit5 = bit5;
/*     */   }
/*     */   
/*     */   private Boolean getBit6() {
/* 411 */     return this.bit6;
/*     */   }
/*     */   
/*     */   private void setBit6(Boolean bit6) {
/* 415 */     this.bit6 = bit6;
/*     */   }
/*     */   
/*     */   private Boolean getBit7() {
/* 419 */     return this.bit7;
/*     */   }
/*     */   
/*     */   private void setBit7(Boolean bit7) {
/* 423 */     this.bit7 = bit7;
/*     */   }
/*     */   
/*     */   private Boolean getBit8() {
/* 427 */     return this.bit8;
/*     */   }
/*     */   
/*     */   private void setBit8(Boolean bit8) {
/* 431 */     this.bit8 = bit8;
/*     */   }
/*     */   
/*     */   private Boolean getBit9() {
/* 435 */     return this.bit9;
/*     */   }
/*     */   
/*     */   private void setBit9(Boolean bit9) {
/* 439 */     this.bit9 = bit9;
/*     */   }
/*     */   
/*     */   private Boolean getBit10() {
/* 443 */     return this.bit10;
/*     */   }
/*     */   
/*     */   private void setBit10(Boolean bit10) {
/* 447 */     this.bit10 = bit10;
/*     */   }
/*     */   
/*     */   private Boolean getBit11() {
/* 451 */     return this.bit11;
/*     */   }
/*     */   
/*     */   private void setBit11(Boolean bit11) {
/* 455 */     this.bit11 = bit11;
/*     */   }
/*     */   
/*     */   private Boolean getBit12() {
/* 459 */     return this.bit12;
/*     */   }
/*     */   
/*     */   private void setBit12(Boolean bit12) {
/* 463 */     this.bit12 = bit12;
/*     */   }
/*     */   
/*     */   private Boolean getBit13() {
/* 467 */     return this.bit13;
/*     */   }
/*     */   
/*     */   private void setBit13(Boolean bit13) {
/* 471 */     this.bit13 = bit13;
/*     */   }
/*     */   
/*     */   private Boolean getBit14() {
/* 475 */     return this.bit14;
/*     */   }
/*     */   
/*     */   private void setBit14(Boolean bit14) {
/* 479 */     this.bit14 = bit14;
/*     */   }
/*     */   
/*     */   private Boolean getBit15() {
/* 483 */     return this.bit15;
/*     */   }
/*     */   
/*     */   private void setBit15(Boolean bit15) {
/* 487 */     this.bit15 = bit15;
/*     */   }
/*     */   
/*     */   private Boolean getBit16() {
/* 491 */     return this.bit16;
/*     */   }
/*     */   
/*     */   private void setBit16(Boolean bit16) {
/* 495 */     this.bit16 = bit16;
/*     */   }
/*     */   
/*     */   private Boolean getBit17() {
/* 499 */     return this.bit17;
/*     */   }
/*     */   
/*     */   private void setBit17(Boolean bit17) {
/* 503 */     this.bit17 = bit17;
/*     */   }
/*     */   
/*     */   private Boolean getBit18() {
/* 507 */     return this.bit18;
/*     */   }
/*     */   
/*     */   private void setBit18(Boolean bit18) {
/* 511 */     this.bit18 = bit18;
/*     */   }
/*     */   
/*     */   private Boolean getBit19() {
/* 515 */     return this.bit19;
/*     */   }
/*     */   
/*     */   private void setBit19(Boolean bit19) {
/* 519 */     this.bit19 = bit19;
/*     */   }
/*     */   
/*     */   private Boolean getBit20() {
/* 523 */     return this.bit20;
/*     */   }
/*     */   
/*     */   private void setBit20(Boolean bit20) {
/* 527 */     this.bit20 = bit20;
/*     */   }
/*     */   
/*     */   private Boolean getBit21() {
/* 531 */     return this.bit21;
/*     */   }
/*     */   
/*     */   private void setBit21(Boolean bit21) {
/* 535 */     this.bit21 = bit21;
/*     */   }
/*     */   
/*     */   private Boolean getBit22() {
/* 539 */     return this.bit22;
/*     */   }
/*     */   
/*     */   private void setBit22(Boolean bit22) {
/* 543 */     this.bit22 = bit22;
/*     */   }
/*     */   
/*     */   private Boolean getBit23() {
/* 547 */     return this.bit23;
/*     */   }
/*     */   
/*     */   private void setBit23(Boolean bit23) {
/* 551 */     this.bit23 = bit23;
/*     */   }
/*     */   
/*     */   private Boolean getBit24() {
/* 555 */     return this.bit24;
/*     */   }
/*     */   
/*     */   private void setBit24(Boolean bit24) {
/* 559 */     this.bit24 = bit24;
/*     */   }
/*     */   
/*     */   private Boolean getBit25() {
/* 563 */     return this.bit25;
/*     */   }
/*     */   
/*     */   private void setBit25(Boolean bit25) {
/* 567 */     this.bit25 = bit25;
/*     */   }
/*     */   
/*     */   private Boolean getBit26() {
/* 571 */     return this.bit26;
/*     */   }
/*     */   
/*     */   private void setBit26(Boolean bit26) {
/* 575 */     this.bit26 = bit26;
/*     */   }
/*     */   
/*     */   private Boolean getBit27() {
/* 579 */     return this.bit27;
/*     */   }
/*     */   
/*     */   private void setBit27(Boolean bit27) {
/* 583 */     this.bit27 = bit27;
/*     */   }
/*     */   
/*     */   private Boolean getBit28() {
/* 587 */     return this.bit28;
/*     */   }
/*     */   
/*     */   private void setBit28(Boolean bit28) {
/* 591 */     this.bit28 = bit28;
/*     */   }
/*     */   
/*     */   private Boolean getBit29() {
/* 595 */     return this.bit29;
/*     */   }
/*     */   
/*     */   private void setBit29(Boolean bit29) {
/* 599 */     this.bit29 = bit29;
/*     */   }
/*     */   
/*     */   private Boolean getBit30() {
/* 603 */     return this.bit30;
/*     */   }
/*     */   
/*     */   private void setBit30(Boolean bit30) {
/* 607 */     this.bit30 = bit30;
/*     */   }
/*     */   
/*     */   private Boolean getBit31() {
/* 611 */     return this.bit31;
/*     */   }
/*     */   
/*     */   private void setBit31(Boolean bit31) {
/* 615 */     this.bit31 = bit31;
/*     */   }
/*     */   
/*     */   public Integer getOpMode() {
/* 619 */     return this.opMode;
/*     */   }
/*     */   
/*     */   public void setOpMode(Integer opMode) {
/* 623 */     this.opMode = opMode;
/*     */   }
/*     */   
/*     */   public OpStatusHiNibble getOpStatusHiNibble() {
/* 627 */     return this.opStatusHiNibble;
/*     */   }
/*     */   
/*     */   public void setOpStatusHiNibble(OpStatusHiNibble opStatusHiNibble) {
/* 631 */     this.opStatusHiNibble = opStatusHiNibble;
/*     */   }
/*     */   
/*     */   public OpStatusLowNibble getOpStatusLowNibble() {
/* 635 */     return this.opStatusLowNibble;
/*     */   }
/*     */   
/*     */   public void setOpStatusLowNibble(OpStatusLowNibble opStatusLowNibble) {
/* 639 */     this.opStatusLowNibble = opStatusLowNibble;
/*     */   }
/*     */   
/*     */   public String getDisplay() {
/* 643 */     return this.display;
/*     */   }
/*     */   
/*     */   public void setDisplay(String display) {
/* 647 */     this.display = display;
/*     */   }
/*     */   
/*     */   public DisplayMatch getIsDisplayContentMatch() {
/* 651 */     return this.isDisplayContentMatch;
/*     */   }
/*     */   
/*     */   public void setIsDisplayContentMatch(DisplayMatch isDisplayContentMatch) {
/* 655 */     this.isDisplayContentMatch = isDisplayContentMatch;
/*     */   }
/*     */   
/*     */   public String getUpdatedBy() {
/* 659 */     return this.updatedBy;
/*     */   }
/*     */   
/*     */   public void setUpdatedBy(String updatedBy) {
/* 663 */     this.updatedBy = updatedBy;
/*     */   }
/*     */   
/*     */   public void setTcMessage(String tcMessage) {
/* 667 */     this.tcMessage = tcMessage;
/*     */   }
/*     */   
/*     */   public String getTcMessage() {
/* 671 */     return this.tcMessage;
/*     */   }
/*     */   
/*     */   public String getTcIconDescription() {
/* 675 */     return this.tcIconDescription;
/*     */   }
/*     */   
/*     */   public void setTcIconDescription(String tcIconDescription) {
/* 679 */     this.tcIconDescription = tcIconDescription;
/*     */   }
/*     */   
/*     */   public String getTcGCodeDescription() {
/* 683 */     return this.tcGCodeDescription;
/*     */   }
/*     */   
/*     */   public void setTcGCodeDescription(String tcGCodeDescription) {
/* 687 */     this.tcGCodeDescription = tcGCodeDescription;
/*     */   }
/*     */   
/*     */   public String getTcIconDescription2() {
/* 691 */     return this.tcIconDescription2;
/*     */   }
/*     */   
/*     */   public void setTcIconDescription2(String tcIconDescription2) {
/* 695 */     this.tcIconDescription2 = tcIconDescription2;
/*     */   }
/*     */   
/*     */   public byte[] getContextData() {
/* 699 */     return this.contextData;
/*     */   }
/*     */   
/*     */   public void setContextData(byte[] contextData) {
/* 703 */     this.contextData = contextData;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 708 */     return "DeviceTcStatus [getId()=" + getId() + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceTcStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */