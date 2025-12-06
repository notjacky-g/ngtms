package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class RoomAnalogRecordDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -8081465046376185114L;

  private String id;

  private Double point1Value = Double.NaN;

  private AnalogyDataDTO point1Data;

  private Double point2Value = Double.NaN;

  private AnalogyDataDTO point2Data;

  private Double point3Value = Double.NaN;

  private AnalogyDataDTO point3Data;

  private Double point4Value = Double.NaN;

  private AnalogyDataDTO point4Data;

  private Double point5Value = Double.NaN;

  private AnalogyDataDTO point5Data;

  private Double point6Value = Double.NaN;

  private AnalogyDataDTO point6Data;

  private Double point7Value = Double.NaN;

  private AnalogyDataDTO point7Data;

  private Double point8Value = Double.NaN;

  private AnalogyDataDTO point8Data;

  private Double point9Value = Double.NaN;

  private AnalogyDataDTO point9Data;

  private Double point10Value = Double.NaN;

  private AnalogyDataDTO point10Data;

  private Date dataTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getPoint1Value() {
    return point1Value;
  }

  public void setPoint1Value(Double point1Value) {
    this.point1Value = point1Value;
  }

  public AnalogyDataDTO getPoint1Data() {
    return point1Data;
  }

  public void setPoint1Data(AnalogyDataDTO point1Data) {
    this.point1Data = point1Data;
  }

  public Double getPoint2Value() {
    return point2Value;
  }

  public void setPoint2Value(Double point2Value) {
    this.point2Value = point2Value;
  }

  public AnalogyDataDTO getPoint2Data() {
    return point2Data;
  }

  public void setPoint2Data(AnalogyDataDTO point2Data) {
    this.point2Data = point2Data;
  }

  public Double getPoint3Value() {
    return point3Value;
  }

  public void setPoint3Value(Double point3Value) {
    this.point3Value = point3Value;
  }

  public AnalogyDataDTO getPoint3Data() {
    return point3Data;
  }

  public void setPoint3Data(AnalogyDataDTO point3Data) {
    this.point3Data = point3Data;
  }

  public Double getPoint4Value() {
    return point4Value;
  }

  public void setPoint4Value(Double point4Value) {
    this.point4Value = point4Value;
  }

  public AnalogyDataDTO getPoint4Data() {
    return point4Data;
  }

  public void setPoint4Data(AnalogyDataDTO point4Data) {
    this.point4Data = point4Data;
  }

  public Double getPoint5Value() {
    return point5Value;
  }

  public void setPoint5Value(Double point5Value) {
    this.point5Value = point5Value;
  }

  public AnalogyDataDTO getPoint5Data() {
    return point5Data;
  }

  public void setPoint5Data(AnalogyDataDTO point5Data) {
    this.point5Data = point5Data;
  }

  public Double getPoint6Value() {
    return point6Value;
  }

  public void setPoint6Value(Double point6Value) {
    this.point6Value = point6Value;
  }

  public AnalogyDataDTO getPoint6Data() {
    return point6Data;
  }

  public void setPoint6Data(AnalogyDataDTO point6Data) {
    this.point6Data = point6Data;
  }

  public Double getPoint7Value() {
    return point7Value;
  }

  public void setPoint7Value(Double point7Value) {
    this.point7Value = point7Value;
  }

  public AnalogyDataDTO getPoint7Data() {
    return point7Data;
  }

  public void setPoint7Data(AnalogyDataDTO point7Data) {
    this.point7Data = point7Data;
  }

  public Double getPoint8Value() {
    return point8Value;
  }

  public void setPoint8Value(Double point8Value) {
    this.point8Value = point8Value;
  }

  public AnalogyDataDTO getPoint8Data() {
    return point8Data;
  }

  public void setPoint8Data(AnalogyDataDTO point8Data) {
    this.point8Data = point8Data;
  }

  public Double getPoint9Value() {
    return point9Value;
  }

  public void setPoint9Value(Double point9Value) {
    this.point9Value = point9Value;
  }

  public AnalogyDataDTO getPoint9Data() {
    return point9Data;
  }

  public void setPoint9Data(AnalogyDataDTO point9Data) {
    this.point9Data = point9Data;
  }

  public Double getPoint10Value() {
    return point10Value;
  }

  public void setPoint10Value(Double point10Value) {
    this.point10Value = point10Value;
  }

  public AnalogyDataDTO getPoint10Data() {
    return point10Data;
  }

  public void setPoint10Data(AnalogyDataDTO point10Data) {
    this.point10Data = point10Data;
  }

  public Date getDataTime() {
    return dataTime;
  }

  public void setDataTime(Date dataTime) {
    this.dataTime = dataTime;
  }
}
