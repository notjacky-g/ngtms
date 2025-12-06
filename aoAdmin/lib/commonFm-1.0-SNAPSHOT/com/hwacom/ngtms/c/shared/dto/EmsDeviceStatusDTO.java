/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
import com.hwacom.ngtms.c.shared.OpStatusHiNibbleType;
import com.hwacom.ngtms.c.shared.OpStatusLowNibbleType;
import java.io.Serializable;
import java.util.Date;

public class EmsDeviceStatusDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 7391758150689322364L;
  /** EmsDeviceStatus的commStatus為offline */
  public static final Integer COMM_STATUS_OFFLINE = 1;
  /** EmsDeviceStatus的commStatus為online */
  public static final Integer COMM_STATUS_ONLINE = 0;
  /** hardware status bit狀態正常 */
  public static final Boolean BIT_NORMAL = false;
  /** op_mode正常 */
  public static final Integer OP_MODE_NORMAL = 0;
  /** op_status正常 */
  public static final Integer OP_STATUS_NORMAL = 0;

  /** 設備名稱 */
  private String deviceName;
  /** 記錄時間 */
  private Date timestamp;
  /** 通訊狀態 */
  private Integer commStatus;
  /** hardware status bit 0 */
  private Boolean bit0;
  /** hardware status bit 1 */
  private Boolean bit1;
  /** hardware status bit 2 */
  private Boolean bit2;
  /** hardware status bit 3 */
  private Boolean bit3;
  /** hardware status bit 4 */
  private Boolean bit4;
  /** hardware status bit 5 */
  private Boolean bit5;
  /** hardware status bit 6 */
  private Boolean bit6;
  /** hardware status bit 7 */
  private Boolean bit7;
  /** hardware status bit 8 */
  private Boolean bit8;
  /** hardware status bit 9 */
  private Boolean bit9;
  /** hardware status bit 10 */
  private Boolean bit10;
  /** hardware status bit 11 */
  private Boolean bit11;
  /** hardware status bit 12 */
  private Boolean bit12;
  /** hardware status bit 13 */
  private Boolean bit13;
  /** hardware status bit 14 */
  private Boolean bit14;
  /** hardware status bit 15 */
  private Boolean bit15;
  /** hardware status bit 16 */
  private Boolean bit16;
  /** hardware status bit 17 */
  private Boolean bit17;
  /** hardware status bit 18 */
  private Boolean bit18;
  /** hardware status bit 19 */
  private Boolean bit19;
  /** hardware status bit 20 */
  private Boolean bit20;
  /** hardware status bit 21 */
  private Boolean bit21;
  /** hardware status bit 22 */
  private Boolean bit22;
  /** hardware status bit 23 */
  private Boolean bit23;
  /** hardware status bit 24 */
  private Boolean bit24;
  /** hardware status bit 25 */
  private Boolean bit25;
  /** hardware status bit 26 */
  private Boolean bit26;
  /** hardware status bit 27 */
  private Boolean bit27;
  /** hardware status bit 28 */
  private Boolean bit28;
  /** hardware status bit 29 */
  private Boolean bit29;
  /** hardware status bit 30 */
  private Boolean bit30;
  /** hardware status bit 31 */
  private Boolean bit31;
  /** op_mode（0-255）, 預設值：0 bit 0 = 1 遠端操作 bit 1 = 1 現場操作 bit 2 = 1 現場連動(CMS、TRS) */
  private Integer opMode;

  /** opStatus bit4~bit7 */
  private OpStatusHiNibbleType opStatusHiNibble;

  /** opStatus bit0~bit3 */
  private OpStatusLowNibbleType opStatusLowNibble;

  /** 現場顯示內容 */
  private String display;

  /** 現場顯示內容比對是否符合 (True=符合, False=不符合) */
  private Boolean isDisplayContentMatch;

  /** 從{@link DeviceTcStatus}上取得的設備連線狀態,預設為false */
  private boolean alive = false;

  public EmsDeviceStatusDTO() {}

  public EmsDeviceStatusDTO(String deviceName) {
    this.deviceName = deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDeviceName() {
    return this.deviceName;
  }

  public Date getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getCommStatus() {
    return commStatus;
  }

  public void setCommStatus(Integer commStatus) {
    this.commStatus = commStatus;
  }

  public Boolean getBit(Integer bit) {
    if (bit == null) return null;
    if (bit < 0 || bit > 31) return null;
    switch (bit) {
      case 0:
        return this.getBit0();
      case 1:
        return this.getBit1();
      case 2:
        return this.getBit2();
      case 3:
        return this.getBit3();
      case 4:
        return this.getBit4();
      case 5:
        return this.getBit5();
      case 6:
        return this.getBit6();
      case 7:
        return this.getBit7();
      case 8:
        return this.getBit8();
      case 9:
        return this.getBit9();
      case 10:
        return this.getBit10();
      case 11:
        return this.getBit11();
      case 12:
        return this.getBit12();
      case 13:
        return this.getBit13();
      case 14:
        return this.getBit14();
      case 15:
        return this.getBit15();
      case 16:
        return this.getBit16();
      case 17:
        return this.getBit17();
      case 18:
        return this.getBit18();
      case 19:
        return this.getBit19();
      case 20:
        return this.getBit20();
      case 21:
        return this.getBit21();
      case 22:
        return this.getBit22();
      case 23:
        return this.getBit23();
      case 24:
        return this.getBit24();
      case 25:
        return this.getBit25();
      case 26:
        return this.getBit26();
      case 27:
        return this.getBit27();
      case 28:
        return this.getBit28();
      case 29:
        return this.getBit29();
      case 30:
        return this.getBit30();
      case 31:
        return this.getBit31();
      default:
        return null;
    }
  }

  public void setBit(Integer bit, Boolean value) {
    if (bit == null || value == null) return;
    if (bit < 0 || bit > 31) return;
    switch (bit) {
      case 0:
        this.setBit0(value);
        break;
      case 1:
        this.setBit1(value);
        break;
      case 2:
        this.setBit2(value);
        break;
      case 3:
        this.setBit3(value);
        break;
      case 4:
        this.setBit4(value);
        break;
      case 5:
        this.setBit5(value);
        break;
      case 6:
        this.setBit6(value);
        break;
      case 7:
        this.setBit7(value);
        break;
      case 8:
        this.setBit8(value);
        break;
      case 9:
        this.setBit9(value);
        break;
      case 10:
        this.setBit10(value);
        break;
      case 11:
        this.setBit11(value);
        break;
      case 12:
        this.setBit12(value);
        break;
      case 13:
        this.setBit13(value);
        break;
      case 14:
        this.setBit14(value);
        break;
      case 15:
        this.setBit15(value);
        break;
      case 16:
        this.setBit16(value);
        break;
      case 17:
        this.setBit17(value);
        break;
      case 18:
        this.setBit18(value);
        break;
      case 19:
        this.setBit19(value);
        break;
      case 20:
        this.setBit20(value);
        break;
      case 21:
        this.setBit21(value);
        break;
      case 22:
        this.setBit22(value);
        break;
      case 23:
        this.setBit23(value);
        break;
      case 24:
        this.setBit24(value);
        break;
      case 25:
        this.setBit25(value);
        break;
      case 26:
        this.setBit26(value);
        break;
      case 27:
        this.setBit27(value);
        break;
      case 28:
        this.setBit28(value);
        break;
      case 29:
        this.setBit29(value);
        break;
      case 30:
        this.setBit30(value);
        break;
      case 31:
        this.setBit31(value);
        break;
      default:
        return;
    }
  }

  public Boolean getBit0() {
    return this.bit0;
  }

  public void setBit0(Boolean bit0) {
    this.bit0 = bit0;
  }

  public Boolean getBit1() {
    return this.bit1;
  }

  public void setBit1(Boolean bit1) {
    this.bit1 = bit1;
  }

  public Boolean getBit2() {
    return this.bit2;
  }

  public void setBit2(Boolean bit2) {
    this.bit2 = bit2;
  }

  public Boolean getBit3() {
    return this.bit3;
  }

  public void setBit3(Boolean bit3) {
    this.bit3 = bit3;
  }

  public Boolean getBit4() {
    return this.bit4;
  }

  public void setBit4(Boolean bit4) {
    this.bit4 = bit4;
  }

  public Boolean getBit5() {
    return this.bit5;
  }

  public void setBit5(Boolean bit5) {
    this.bit5 = bit5;
  }

  public Boolean getBit6() {
    return this.bit6;
  }

  public void setBit6(Boolean bit6) {
    this.bit6 = bit6;
  }

  public Boolean getBit7() {
    return this.bit7;
  }

  public void setBit7(Boolean bit7) {
    this.bit7 = bit7;
  }

  public Boolean getBit8() {
    return this.bit8;
  }

  public void setBit8(Boolean bit8) {
    this.bit8 = bit8;
  }

  public Boolean getBit9() {
    return this.bit9;
  }

  public void setBit9(Boolean bit9) {
    this.bit9 = bit9;
  }

  public Boolean getBit10() {
    return this.bit10;
  }

  public void setBit10(Boolean bit10) {
    this.bit10 = bit10;
  }

  public Boolean getBit11() {
    return this.bit11;
  }

  public void setBit11(Boolean bit11) {
    this.bit11 = bit11;
  }

  public Boolean getBit12() {
    return this.bit12;
  }

  public void setBit12(Boolean bit12) {
    this.bit12 = bit12;
  }

  public Boolean getBit13() {
    return this.bit13;
  }

  public void setBit13(Boolean bit13) {
    this.bit13 = bit13;
  }

  public Boolean getBit14() {
    return this.bit14;
  }

  public void setBit14(Boolean bit14) {
    this.bit14 = bit14;
  }

  public Boolean getBit15() {
    return this.bit15;
  }

  public void setBit15(Boolean bit15) {
    this.bit15 = bit15;
  }

  public Boolean getBit16() {
    return this.bit16;
  }

  public void setBit16(Boolean bit16) {
    this.bit16 = bit16;
  }

  public Boolean getBit17() {
    return this.bit17;
  }

  public void setBit17(Boolean bit17) {
    this.bit17 = bit17;
  }

  public Boolean getBit18() {
    return this.bit18;
  }

  public void setBit18(Boolean bit18) {
    this.bit18 = bit18;
  }

  public Boolean getBit19() {
    return this.bit19;
  }

  public void setBit19(Boolean bit19) {
    this.bit19 = bit19;
  }

  public Boolean getBit20() {
    return this.bit20;
  }

  public void setBit20(Boolean bit20) {
    this.bit20 = bit20;
  }

  public Boolean getBit21() {
    return this.bit21;
  }

  public void setBit21(Boolean bit21) {
    this.bit21 = bit21;
  }

  public Boolean getBit22() {
    return this.bit22;
  }

  public void setBit22(Boolean bit22) {
    this.bit22 = bit22;
  }

  public Boolean getBit23() {
    return this.bit23;
  }

  public void setBit23(Boolean bit23) {
    this.bit23 = bit23;
  }

  public Boolean getBit24() {
    return this.bit24;
  }

  public void setBit24(Boolean bit24) {
    this.bit24 = bit24;
  }

  public Boolean getBit25() {
    return this.bit25;
  }

  public void setBit25(Boolean bit25) {
    this.bit25 = bit25;
  }

  public Boolean getBit26() {
    return this.bit26;
  }

  public void setBit26(Boolean bit26) {
    this.bit26 = bit26;
  }

  public Boolean getBit27() {
    return this.bit27;
  }

  public void setBit27(Boolean bit27) {
    this.bit27 = bit27;
  }

  public Boolean getBit28() {
    return this.bit28;
  }

  public void setBit28(Boolean bit28) {
    this.bit28 = bit28;
  }

  public Boolean getBit29() {
    return this.bit29;
  }

  public void setBit29(Boolean bit29) {
    this.bit29 = bit29;
  }

  public Boolean getBit30() {
    return this.bit30;
  }

  public void setBit30(Boolean bit30) {
    this.bit30 = bit30;
  }

  public Boolean getBit31() {
    return this.bit31;
  }

  public void setBit31(Boolean bit31) {
    this.bit31 = bit31;
  }

  public Integer getOpMode() {
    return opMode;
  }

  public void setOpMode(Integer opMode) {
    this.opMode = opMode;
  }

  public OpStatusHiNibbleType getOpStatusHiNibble() {
    return opStatusHiNibble;
  }

  public void setOpStatusHiNibble(OpStatusHiNibbleType opStatusHiNibble) {
    this.opStatusHiNibble = opStatusHiNibble;
  }

  public OpStatusLowNibbleType getOpStatusLowNibble() {
    return opStatusLowNibble;
  }

  public void setOpStatusLowNibble(OpStatusLowNibbleType opStatusLowNibble) {
    this.opStatusLowNibble = opStatusLowNibble;
  }

  public String getDisplay() {
    return this.display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public Boolean getIsDisplayContentMatch() {
    return isDisplayContentMatch;
  }

  public void setIsDisplayContentMatch(Boolean isDisplayContentMatch) {
    this.isDisplayContentMatch = isDisplayContentMatch;
  }

  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "EmsDeviceStatusDTO [deviceName="
        + deviceName
        + ", timestamp="
        + timestamp
        + ", commStatus="
        + commStatus
        + ", bit0="
        + bit0
        + ", bit1="
        + bit1
        + ", bit2="
        + bit2
        + ", bit3="
        + bit3
        + ", bit4="
        + bit4
        + ", bit5="
        + bit5
        + ", bit6="
        + bit6
        + ", bit7="
        + bit7
        + ", bit8="
        + bit8
        + ", bit9="
        + bit9
        + ", bit10="
        + bit10
        + ", bit11="
        + bit11
        + ", bit12="
        + bit12
        + ", bit13="
        + bit13
        + ", bit14="
        + bit14
        + ", bit15="
        + bit15
        + ", bit16="
        + bit16
        + ", bit17="
        + bit17
        + ", bit18="
        + bit18
        + ", bit19="
        + bit19
        + ", bit20="
        + bit20
        + ", bit21="
        + bit21
        + ", bit22="
        + bit22
        + ", bit23="
        + bit23
        + ", bit24="
        + bit24
        + ", bit25="
        + bit25
        + ", bit26="
        + bit26
        + ", bit27="
        + bit27
        + ", bit28="
        + bit28
        + ", bit29="
        + bit29
        + ", bit30="
        + bit30
        + ", bit31="
        + bit31
        + ", opMode="
        + opMode
        + ", opStatusHiNibble="
        + opStatusHiNibble
        + ", opStatusLowNibble="
        + opStatusLowNibble
        + ", display="
        + display
        + ", isDisplayContentMatch="
        + isDisplayContentMatch
        + ", alive="
        + alive
        + "]";
  }
}
