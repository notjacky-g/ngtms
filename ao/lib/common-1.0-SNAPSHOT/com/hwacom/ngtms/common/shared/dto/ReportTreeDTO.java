package com.hwacom.ngtms.common.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportTreeDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 3679838764147792705L;

  private String id;

  private String name;

  private String ripViewerClass;

  private Integer chartSize;

  private List<ReportTreeDTO> children = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getChartSize() {
    return chartSize;
  }

  public void setChartSize(Integer chartSize) {
    this.chartSize = chartSize;
  }

  public String getRipViewerClass() {
    return ripViewerClass;
  }

  public void setRipViewerClass(String ripViewerClass) {
    this.ripViewerClass = ripViewerClass;
  }

  public List<ReportTreeDTO> getChildren() {
    return children;
  }

  public void setChildren(List<ReportTreeDTO> children) {
    this.children = children;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ReportTreeDTO other = (ReportTreeDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
