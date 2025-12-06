/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import com.google.common.base.Objects;
import com.hwacom.ngtms.c.dis.util.TransferHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

/**
 * FullText說明：全文庫表 一次只下一張2X2的全彩圖
 *
 * @author Jeff.Ku
 */
@Embeddable
public class DisFullText implements Serializable {

  private static final long serialVersionUID = 400271756898138024L;

  private static final String DELIMITER = "-";

  /** FullTextType 分類(0=警告 1=控制 2=宣導 etc …) TODO 沒看到設定 */
  @Column(length = 70)
  private String fullTextType;

  /** Category 種類（EX. 2x8a、2x8c、5x2a） */
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private PanelCategory category = PanelCategory.NONE;

  /**
   * 全彩圖形碼(1-255，) CMS,WIS 0代表不使用<br>
   * RGS 因為通訊協定要帶座標的關係，不使用請帶NULL不然 NCC會轉換成 iconCodeId 0,iconCodeX 0, iconCodeY 0，
   */
  private Integer iconCodeId = 0;

  /** FullColor2_X 全彩圖型位置ｘ軸 */
  @Column(name = "icon_code_x")
  private Integer iconCodeX;

  /** FullColor2_Y 全彩圖型位置ｙ軸 */
  @Column(name = "icon_code_y")
  private Integer iconCodeY;

  /** 底圖型碼(1-32，0代表不使用) */
  private Integer gCodeId = 0;

  /** 底圖位置ｘ軸 */
  @Column(name = "g_code_x")
  private Integer gCodeX = 0;

  /** 底圖位置ｙ軸 */
  @Column(name = "g_code_y")
  private Integer gCodeY = 0;

  /** Text 文字內容（如：天） */
  @Column(length = 150)
  private String message;

  /** 字前景顏色 EX: YELLOW-RED-GREEN */
  @Column(length = 500)
  private String foregroundColor;

  /** 字背景顏色 EX: YELLOW-RED-GREEN */
  @Column(length = 500)
  private String backgroundColor;

  /** FirstWordX *首字位置ｘ軸 */
  @Column(name = "first_word_x")
  private Integer firstWordX;

  /** FirstWordY *首字位置ｙ軸 */
  @Column(name = "first_word_y")
  private Integer firstWordY;

  /** 垂直字距 */
  private Integer textSpace = 0;

  /** 水平字距 */
  private Integer horSpace = 1;

  /** Text 閃爍速度 EX:1-0.5-0-2, 0代表STOP */
  private String flashSpeed;

  /** 用來將DB存放的foregroundColor拆解成字串List供AM使用,EX:[RGColorModel.GREEN, RGColorModel.RED] */
  @Transient private List<RGColorModel> foregroundColorModel = new ArrayList<>();

  /** 用來將DB存放的backgroundColor拆解成字串List供AM使用,EX:[RGColorModel.GREEN, RGColorModel.YELLOW] */
  @Transient private List<RGColorModel> backgroundColorModel = new ArrayList<>();

  /** 用來將DB存放的foregroundColorCodes拆解成字串List供AM使用, EX:[FF0000, FF0000, FF0000, FF0000] */
  @Transient private List<String> foregroundRGBList = new ArrayList<>();

  /** 用來將DB存放的backgroundColorCodes拆解成字串List供AM使用 , EX:[FF0000, FF0000, FF0000, FF0000] */
  @Transient private List<String> backgroundRGBList = new ArrayList<>();

  /** 用來將DB存放的flashSpeed拆解成字串List供AM使用,EX:[0.5, 1, 2] */
  @Transient private List<Float> flashSpeedList = new ArrayList<>();

  /**
   * 全彩設備文字顏色用3byte表示,無法使用RGColorModel<br>
   * 字前景顏色（如：FFFFFF） 前兩個為紅色色階，中間兩個為綠色色階，最後兩個為藍色色階
   */
  @Column(length = 500)
  private String foregroundColorCodes;

  /**
   * 全彩設備文字顏色用3byte表示,無法使用RGColorModel<br>
   * 字背景顏色（如：000000） 前兩個為紅色色階，中間兩個為綠色色階，最後兩個為藍色色階
   */
  @Column(length = 500)
  private String backgroundColorCodes;

  /** r22 display_part 旅行時間組別編號，由一開始 */
  private Integer boardId = 1;

  @PostLoad
  private void string2RGColor() {
    // 將資料庫儲存的RGColorModel ( ex:YELLOW-RED-GREEN) 轉回成 List<RGColorModel>
    this.foregroundColorModel = RGColorModel.string2RGColor(foregroundColor, DELIMITER);
    this.backgroundColorModel = RGColorModel.string2RGColor(backgroundColor, DELIMITER);
    //將資料庫儲存的flashSpeed ( ex:1-0.5-2) 轉成 List<Float>
    this.flashSpeedList = TransferHelper.string2FloatList(flashSpeed, DELIMITER);
  }

  @PrePersist
  private void rgColor2String() {
    // 將List<RGColorModel> 轉成資料庫儲存的格式
    this.foregroundColor = RGColorModel.rgColor2String(foregroundColorModel, DELIMITER);
    this.backgroundColor = RGColorModel.rgColor2String(backgroundColorModel, DELIMITER);
    //將List<Float> 轉成資料庫儲存的格式
    this.foregroundColor = TransferHelper.floatList2String(flashSpeedList, DELIMITER);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(fullTextType, category, iconCodeId, gCodeId, message);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof DisFullText) {
      DisFullText that = (DisFullText) object;
      return Objects.equal(this.fullTextType, that.fullTextType)
          && Objects.equal(this.category, that.category)
          && Objects.equal(this.iconCodeId, that.iconCodeId)
          && Objects.equal(this.gCodeId, that.gCodeId)
          && Objects.equal(this.message, that.message);
    }
    return false;
  }

  public String getFullTextType() {
    return fullTextType;
  }

  public void setFullTextType(String fullTextType) {
    this.fullTextType = fullTextType;
  }

  public PanelCategory getCategory() {
    return category;
  }

  public void setCategory(PanelCategory category) {
    this.category = category;
  }

  public Integer getIconCodeId() {
    return iconCodeId;
  }

  public void setIconCodeId(Integer iconCodeId) {
    this.iconCodeId = iconCodeId;
  }

  public Integer getIconCodeX() {
    return iconCodeX;
  }

  public void setIconCodeX(Integer iconCodeX) {
    this.iconCodeX = iconCodeX;
  }

  public Integer getIconCodeY() {
    return iconCodeY;
  }

  public void setIconCodeY(Integer iconCodeY) {
    this.iconCodeY = iconCodeY;
  }

  public Integer getgCodeId() {
    return gCodeId;
  }

  public void setgCodeId(Integer gCodeId) {
    this.gCodeId = gCodeId;
  }

  public Integer getgCodeX() {
    return gCodeX;
  }

  public void setgCodeX(Integer gCodeX) {
    this.gCodeX = gCodeX;
  }

  public Integer getgCodeY() {
    return gCodeY;
  }

  public void setgCodeY(Integer gCodeY) {
    this.gCodeY = gCodeY;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getForegroundColor() {
    return foregroundColor;
  }

  public void setForegroundColor(String foregroundColor) {
    this.foregroundColor = foregroundColor;
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public Integer getTextSpace() {
    return textSpace;
  }

  public void setTextSpace(Integer textSpace) {
    this.textSpace = textSpace;
  }

  public List<RGColorModel> getForegroundColorModel() {
    return RGColorModel.string2RGColor(foregroundColor, DELIMITER);
  }

  public void setForegroundColorModel(List<RGColorModel> foregroundColorModel) {
    this.foregroundColorModel = foregroundColorModel;
    this.foregroundColor = RGColorModel.rgColor2String(foregroundColorModel, DELIMITER);
  }

  public List<RGColorModel> getBackgroundColorModel() {
    return RGColorModel.string2RGColor(backgroundColor, DELIMITER);
  }

  public void setBackgroundColorModel(List<RGColorModel> backgroundColorModel) {
    this.backgroundColorModel = backgroundColorModel;
    this.backgroundColor = RGColorModel.rgColor2String(backgroundColorModel, DELIMITER);
  }

  public List<Float> getFlashSpeedList() {
    return TransferHelper.string2FloatList(flashSpeed, DELIMITER);
  }

  public void setFlashSpeedList(List<Float> flashSpeedList) {
    this.flashSpeedList = flashSpeedList;
    this.flashSpeed = TransferHelper.floatList2String(flashSpeedList, DELIMITER);
  }

  public Integer getFirstWordX() {
    return firstWordX;
  }

  public void setFirstWordX(Integer firstWordX) {
    this.firstWordX = firstWordX;
  }

  public Integer getFirstWordY() {
    return firstWordY;
  }

  public void setFirstWordY(Integer firstWordY) {
    this.firstWordY = firstWordY;
  }

  public String getForegroundColorCodes() {
    return foregroundColorCodes;
  }

  public void setForegroundColorCodes(String foregroundColorCodes) {
    this.foregroundColorCodes = foregroundColorCodes;
  }

  public String getBackgroundColorCodes() {
    return backgroundColorCodes;
  }

  public void setBackgroundColorCodes(String backgroundColorCodes) {
    this.backgroundColorCodes = backgroundColorCodes;
  }

  public Integer getHorSpace() {
    return horSpace;
  }

  public void setHorSpace(Integer horSpace) {
    this.horSpace = horSpace;
  }

  public List<String> getForegroundColorList() {
    List<String> foregroundColorList = new ArrayList<>();
    if (foregroundColorCodes != null && !foregroundColorCodes.isEmpty()) {
      char[] rgs = foregroundColorCodes.toCharArray();
      //6char為一個色碼 ex：FFFF00
      for (int i = 0; i <= (foregroundColorCodes.length() - 6); i = i + 6) {
        foregroundColorList.add(String.copyValueOf(rgs, i, 6));
      }
    }
    return foregroundColorList;
  }

  public void setForegroundRGBList(List<String> foregroundRGBList) {
    this.foregroundRGBList = foregroundRGBList;
    StringBuffer sb = new StringBuffer();
    for (String rgb : foregroundRGBList) {
      sb.append(rgb);
    }
    this.foregroundColorCodes = sb.toString();
  }

  public List<String> getBackgroundRGBList() {
    List<String> backgroundColorList = new ArrayList<>();
    if (backgroundColorCodes != null && !backgroundColorCodes.isEmpty()) {
      char[] rgs = backgroundColorCodes.toCharArray();
      //6char為一個色碼 ex：FFFF00
      for (int i = 0; i <= (backgroundColorCodes.length() - 6); i = i + 6) {
        backgroundColorList.add(String.copyValueOf(rgs, i, 6));
      }
    }
    return backgroundColorList;
  }

  public void setBackgroundColorList(List<String> backgroundRGBList) {
    this.backgroundRGBList = backgroundRGBList;
    StringBuffer sb = new StringBuffer();
    for (String rgb : backgroundRGBList) {
      sb.append(rgb);
    }
    //compile hcAdmin  [ERROR]: The method join(String, List<String>) is undefined for the type String
    //String.join("", backgroundRGBList)
    this.backgroundColorCodes = sb.toString();
  }

  public Integer getBoardId() {
    return boardId;
  }

  public void setBoardId(Integer boardId) {
    this.boardId = boardId;
  }

  public static class Builder {
    private String fullTextType;
    private PanelCategory category = PanelCategory.NONE;
    private Integer iconCodeId = 0;
    private Integer iconCodeX;
    private Integer iconCodeY;
    private Integer gCodeId = 0;
    private Integer gCodeX;
    private Integer gCodeY;
    private String message;
    private String foregroundColor;
    private String backgroundColor;
    private Integer firstWordX;
    private Integer firstWordY;
    private Integer textSpace = 0;
    private List<RGColorModel> foregroundColorModel = new ArrayList<>();
    private List<RGColorModel> backgroundColorModel = new ArrayList<>();

    public Builder fullTextType(String fullTextType) {
      this.fullTextType = fullTextType;
      return this;
    }

    public Builder category(PanelCategory category) {
      this.category = category;
      return this;
    }

    public Builder iconCodeId(Integer iconCodeId) {
      this.iconCodeId = iconCodeId;
      return this;
    }

    public Builder iconCodeX(Integer iconCodeX) {
      this.iconCodeX = iconCodeX;
      return this;
    }

    public Builder iconCodeY(Integer iconCodeY) {
      this.iconCodeY = iconCodeY;
      return this;
    }

    public Builder gCodeId(Integer gCodeId) {
      this.gCodeId = gCodeId;
      return this;
    }

    public Builder gCodeX(Integer gCodeX) {
      this.gCodeX = gCodeX;
      return this;
    }

    public Builder gCodeY(Integer gCodeY) {
      this.gCodeY = gCodeY;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder foregroundColor(String foregroundColor) {
      this.foregroundColor = foregroundColor;
      return this;
    }

    public Builder backgroundColor(String backgroundColor) {
      this.backgroundColor = backgroundColor;
      return this;
    }

    public Builder firstWordX(Integer firstWordX) {
      this.firstWordX = firstWordX;
      return this;
    }

    public Builder firstWordY(Integer firstWordY) {
      this.firstWordY = firstWordY;
      return this;
    }

    public Builder textSpace(Integer textSpace) {
      this.textSpace = textSpace;
      return this;
    }

    public Builder foregroundColorModel(List<RGColorModel> foregroundColorModel) {
      this.foregroundColorModel = foregroundColorModel;
      return this;
    }

    public Builder backgroundColorModel(List<RGColorModel> backgroundColorModel) {
      this.backgroundColorModel = backgroundColorModel;
      return this;
    }

    public DisFullText build() {
      DisFullText disFullText = new DisFullText();
      disFullText.fullTextType = fullTextType;
      disFullText.category = category;
      disFullText.iconCodeId = iconCodeId;
      disFullText.iconCodeX = iconCodeX;
      disFullText.iconCodeY = iconCodeY;
      disFullText.gCodeId = gCodeId;
      disFullText.gCodeX = gCodeX;
      disFullText.gCodeY = gCodeY;
      disFullText.message = message;
      disFullText.foregroundColor = foregroundColor;
      disFullText.backgroundColor = backgroundColor;
      disFullText.firstWordX = firstWordX;
      disFullText.firstWordY = firstWordY;
      disFullText.textSpace = textSpace;
      disFullText.foregroundColorModel = foregroundColorModel;
      disFullText.backgroundColorModel = backgroundColorModel;
      return disFullText;
    }
  }
}
