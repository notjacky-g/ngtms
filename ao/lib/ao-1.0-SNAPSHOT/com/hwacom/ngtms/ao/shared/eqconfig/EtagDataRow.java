package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

public class EtagDataRow implements Serializable {

  /** */
  private static final long serialVersionUID = -5870353354488505151L;

  private String eqId;

  /**
   * etag_id 主線型eTag的ID使用8碼。其編碼方式如下： <br>
   * 前兩碼：國道編號 02 = 國2 04 = 國4 06 = 國6 08 = 國8 10 = 國10 <br>
   * 第3碼：固定為G <br>
   * 第4~7碼：設備里程，<br>
   * 4至6碼為公里，<br>
   * 第7碼為百公尺。如：02G0123E表示為國2東向12.3公里。 <br>
   * 第8碼：方向 E = 東向 W = 西向 S = 南向 N = 北向 平面道路型eTag的ID由各區指定。
   */
  private String etagId;
  /**
   * freewayId 0：非國道。 1：國 1 2：國 2 3：國 3 4：國 4 5：國 5 6：國 6 8：國 8 10：國10 N1H：國1高架 N3K：港西聯外道 N3A：國3甲
   */
  private String freewayId;

  /**
   * 0=非快速道路 62=62號快速道路 64=64號快速道路 66=66號快速道路 68=68號快速道路 72=72號快速道路 74=74號快速道路 76=76號快速道路 78=78號快速道路
   * 82=82號快速道路 84=84號快速道路 86=86號快速道路 88=88號快速道路
   */
  private String expresswayId;

  /** 高快速公路的方向 1 = 東 2 = 西 3 = 南 4 = 北 平面道路若沒有方向，填入0 */
  private String directionId;

  /** 高快速公路的里程 如：123K+001 milepost=”123001” */
  private String milepost;

  /** 1: 主線。 2: 入口匝道。 3: 出口匝道。 5: 平面道路。 */
  private String etagProperty;

  /** 如, 新北市汐止區大同路一段costco。 */
  private String locationDescription;

  /** 雙引號內放入經度，如121.6244137。 */
  private String longitude;

  /** latitude 雙引號內放入緯度，如25.0562554。 */
  private String latitude;

  public String getEtagId() {
    return etagId;
  }

  public void setEtagId(String etagId) {
    this.etagId = etagId;
  }

  public String getFreewayId() {
    return freewayId;
  }

  public void setFreewayId(String freewayId) {
    this.freewayId = freewayId;
  }

  public String getExpresswayId() {
    return expresswayId;
  }

  public void setExpresswayId(String expresswayId) {
    this.expresswayId = expresswayId;
  }

  public String getDirectionId() {
    return directionId;
  }

  public void setDirectionId(String directionId) {
    this.directionId = directionId;
  }

  public String getMilepost() {
    return milepost;
  }

  public void setMilepost(String milepost) {
    this.milepost = milepost;
  }

  public String getEtagProperty() {
    return etagProperty;
  }

  public void setEtagProperty(String etagProperty) {
    this.etagProperty = etagProperty;
  }

  public String getLocationDescription() {
    return locationDescription;
  }

  public void setLocationDescription(String locationDescription) {
    this.locationDescription = locationDescription;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getEqId() {
    return eqId;
  }

  public void setEqId(String eqId) {
    this.eqId = eqId;
  }
}
