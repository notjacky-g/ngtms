package com.hwacom.ngtms.ao.shared;

import java.io.Serializable;

public class NCUDateTime implements Serializable {

  private static final long serialVersionUID = -4164078544870338929L;

  private String date;

  private String time;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
