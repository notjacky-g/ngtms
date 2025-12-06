/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class RampVdConfigDTO implements Serializable, IsSerializable {

  /** */
  private static final long serialVersionUID = 8568073470759979839L;

  /** 匝道編號 */
  private Integer id;

  /** 屬於哪個交流道，也就是分割點的編號 */
  private String divisionId;

  private String divisionName;

  /** 方向 */
  private String directionStr;

  /** 出入口 */
  private String rampTypeStr;

  /** 出入口敘述 */
  private String rampTypeDesc;

  /** 匝道型態(對應到RampVdType的id) */
  private Integer rampVdType;

  /** 該匝道是否屬於特例情況（0正常、1特例）如果屬於特殊狀況，該匝道計算流量的方式就必須寫HARD CODE */
  private Integer exception;

  /** 指定之VD1（預設為總匝道口） */
  private String vd1;

  /** 指定之VD2（預設為匝道口1） */
  private String vd2;

  /** 指定之VD3（預設為匝道口2) */
  private String vd3;

  /** 該匝道的壅塞程度是要拿哪支VD來算 */
  private String degreeVd1;

  /** 該匝道的壅塞程度是要拿哪支VD來算 */
  private String degreeVd2;

  /** 該匝道的壅塞程度是要拿哪支VD來算 */
  private String degreeVd3;

  /** 該匝道的壅塞程度是要拿哪支VD來算 */
  private String degreeVd4;

  /** 該匝道的壅塞程度是要拿哪支VD來算 */
  private String degreeVd5;

  /** 該匝道的壅塞程度是要拿哪支VD來算 */
  private String degreeVd6;

  /** @return the id */
  public Integer getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(Integer id) {
    this.id = id;
  }

  /** @return the divisionId */
  public String getDivisionId() {
    return divisionId;
  }

  /** @param divisionId the divisionId to set */
  public void setDivisionId(String divisionId) {
    this.divisionId = divisionId;
  }

  /** @return the divisionName */
  public String getDivisionName() {
    return divisionName;
  }

  /** @param divisionName the divisionName to set */
  public void setDivisionName(String divisionName) {
    this.divisionName = divisionName;
  }

  /** @return the directionStr */
  public String getDirectionStr() {
    return directionStr;
  }

  /** @param directionStr the directionStr to set */
  public void setDirectionStr(String directionStr) {
    this.directionStr = directionStr;
  }

  /** @return the rampTypeStr */
  public String getRampTypeStr() {
    return rampTypeStr;
  }

  /** @param rampTypeStr the rampTypeStr to set */
  public void setRampTypeStr(String rampTypeStr) {
    this.rampTypeStr = rampTypeStr;
  }

  /** @return the rampTypeDesc */
  public String getRampTypeDesc() {
    return rampTypeDesc;
  }

  /** @param rampTypeDesc the rampTypeDesc to set */
  public void setRampTypeDesc(String rampTypeDesc) {
    this.rampTypeDesc = rampTypeDesc;
  }

  /** @return the rampVdType */
  public Integer getRampVdType() {
    return rampVdType;
  }

  /** @param rampVdType the rampVdType to set */
  public void setRampVdType(Integer rampVdType) {
    this.rampVdType = rampVdType;
  }

  /** @return the exception */
  public Integer getException() {
    return exception;
  }

  /** @param exception the exception to set */
  public void setException(Integer exception) {
    this.exception = exception;
  }

  /** @return the vd1 */
  public String getVd1() {
    return vd1;
  }

  /** @param vd1 the vd1 to set */
  public void setVd1(String vd1) {
    this.vd1 = vd1;
  }

  /** @return the vd2 */
  public String getVd2() {
    return vd2;
  }

  /** @param vd2 the vd2 to set */
  public void setVd2(String vd2) {
    this.vd2 = vd2;
  }

  /** @return the vd3 */
  public String getVd3() {
    return vd3;
  }

  /** @param vd3 the vd3 to set */
  public void setVd3(String vd3) {
    this.vd3 = vd3;
  }

  /** @return the degreeVd1 */
  public String getDegreeVd1() {
    return degreeVd1;
  }

  /** @param degreeVd1 the degreeVd1 to set */
  public void setDegreeVd1(String degreeVd1) {
    this.degreeVd1 = degreeVd1;
  }

  /** @return the degreeVd2 */
  public String getDegreeVd2() {
    return degreeVd2;
  }

  /** @param degreeVd2 the degreeVd2 to set */
  public void setDegreeVd2(String degreeVd2) {
    this.degreeVd2 = degreeVd2;
  }

  /** @return the degreeVd3 */
  public String getDegreeVd3() {
    return degreeVd3;
  }

  /** @param degreeVd3 the degreeVd3 to set */
  public void setDegreeVd3(String degreeVd3) {
    this.degreeVd3 = degreeVd3;
  }

  /** @return the degreeVd4 */
  public String getDegreeVd4() {
    return degreeVd4;
  }

  /** @param degreeVd4 the degreeVd4 to set */
  public void setDegreeVd4(String degreeVd4) {
    this.degreeVd4 = degreeVd4;
  }

  /** @return the degreeVd5 */
  public String getDegreeVd5() {
    return degreeVd5;
  }

  /** @param degreeVd5 the degreeVd5 to set */
  public void setDegreeVd5(String degreeVd5) {
    this.degreeVd5 = degreeVd5;
  }

  /** @return the degreeVd6 */
  public String getDegreeVd6() {
    return degreeVd6;
  }

  /** @param degreeVd6 the degreeVd6 to set */
  public void setDegreeVd6(String degreeVd6) {
    this.degreeVd6 = degreeVd6;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "RampVdConfigDTO [id="
        + id
        + ", divisionId="
        + divisionId
        + ", divisionName="
        + divisionName
        + ", directionStr="
        + directionStr
        + ", rampTypeStr="
        + rampTypeStr
        + ", rampTypeDesc="
        + rampTypeDesc
        + ", rampVdType="
        + rampVdType
        + ", exception="
        + exception
        + ", vd1="
        + vd1
        + ", vd2="
        + vd2
        + ", vd3="
        + vd3
        + ", degreeVd1="
        + degreeVd1
        + ", degreeVd2="
        + degreeVd2
        + ", degreeVd3="
        + degreeVd3
        + ", degreeVd4="
        + degreeVd4
        + ", degreeVd5="
        + degreeVd5
        + ", degreeVd6="
        + degreeVd6
        + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RampVdConfigDTO other = (RampVdConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
