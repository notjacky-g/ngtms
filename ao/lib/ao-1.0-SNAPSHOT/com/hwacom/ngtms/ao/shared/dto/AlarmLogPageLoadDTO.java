package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.List;

public class AlarmLogPageLoadDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 440936263483059504L;

  private List<AlarmMessageDTO> data;

  private int offset;

  private int totalPage;

  public List<AlarmMessageDTO> getData() {
    return data;
  }

  public void setData(List<AlarmMessageDTO> data) {
    this.data = data;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }
}
