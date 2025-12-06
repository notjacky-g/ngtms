/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReportRepositoryTreeNode implements Serializable {

  private static final long serialVersionUID = 8397938055462360850L;

  /**
   * The depth of the tree.
   *
   * @author jack
   */
  public enum Depth {
    /** 根節點, 只會有一個根節點 */
    ROOT,
    /** 根節點下的第一層節點 */
    FIRST,
    /** 根節點下的第二層節點 */
    SECOND,
    /** 葉節點, 實際的報表或輸出檔案的節點 */
    LEAF;
  }

  /**
   * The type of the tree node.
   *
   * @author jack
   */
  public enum Type {
    REPORT,
    EXPORT_FILE
  }

  private String id;

  private String name;

  private Boolean roadMap;

  private Depth depth;

  private Type type;

  private String ripViewerClass;

  private Map<String, String> charts = new HashMap<>();

  /** available if Type is EXPORT_FILE */
  private ReportFile exportFileInfo;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ReportRepositoryTreeNode)) {
      return false;
    }
    ReportRepositoryTreeNode other = (ReportRepositoryTreeNode) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }

    return true;
  }

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

  /** @return <code>null</code>, if the Depth is ROOT */
  public Depth getDepth() {
    return depth;
  }

  public void setDepth(Depth depth) {
    this.depth = depth;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getRipViewerClass() {
    return ripViewerClass;
  }

  public void setRipViewerClass(String ripViewerClass) {
    this.ripViewerClass = ripViewerClass;
  }

  public ReportFile getExportFileInfo() {
    return exportFileInfo;
  }

  public void setExportFileInfo(ReportFile exportFileInfo) {
    this.exportFileInfo = exportFileInfo;
  }

  /** @return the charts */
  public Map<String, String> getCharts() {
    return charts;
  }

  /** @param charts the charts to set */
  public void setCharts(Map<String, String> charts) {
    this.charts = charts;
  }

  /** @return the roadMap */
  public Boolean getRoadMap() {
    return roadMap;
  }

  /** @param roadMap the roadMap to set */
  public void setRoadMap(Boolean roadMap) {
    this.roadMap = roadMap;
  }
}
