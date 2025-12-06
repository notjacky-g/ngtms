package com.hwacom.ngtms.ao.shared.dto;

import com.hwacom.ngtms.alarm.shared.AlarmState;
import java.io.Serializable;
import java.util.Date;

public class AlarmMessageDTO implements Serializable {

  private static final long serialVersionUID = 42998690854361579L;

  private String id;

  private Date timestamp;

  private String alarmType;

  private String deviceName;

  private String displayName;

  private String message;

  private int alarmLevel;

  private double value;

  private AlarmState state;

  private String note;

  private String operator;

  private String priority;

  private Boolean flashEnabled;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getAlarmType() {
    return alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getAlarmLevel() {
    return alarmLevel;
  }

  public void setAlarmLevel(int alarmLevel) {
    this.alarmLevel = alarmLevel;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public AlarmState getState() {
    return state;
  }

  public void setState(AlarmState state) {
    this.state = state;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public Boolean getFlashEnabled() {
    return flashEnabled;
  }

  public void setFlashEnabled(Boolean flashEnabled) {
    this.flashEnabled = flashEnabled;
  }

  @Override
  public String toString() {
    return "AlarmMessageDTO [id="
        + id
        + ", timestamp="
        + timestamp
        + ", alarmType="
        + alarmType
        + ", deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", message="
        + message
        + ", alarmLevel="
        + alarmLevel
        + ", value="
        + value
        + ", state="
        + state
        + ", note="
        + note
        + ", operator="
        + operator
        + ", priority="
        + priority
        + ", flashEnabled="
        + flashEnabled
        + "]";
  }
}
