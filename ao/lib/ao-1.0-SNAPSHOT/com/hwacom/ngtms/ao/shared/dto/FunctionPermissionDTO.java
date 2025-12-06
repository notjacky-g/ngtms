/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class FunctionPermissionDTO
    implements Serializable, Comparable<FunctionPermissionDTO>, IsSerializable {

  private static final long serialVersionUID = 1882934179062975300L;

  private String id;

  private String name;

  private String description;

  private String parentId;

  /** @return the id */
  public String getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(String id) {
    this.id = id;
  }

  /** @return the name */
  public String getName() {
    return name;
  }

  /** @param name the name to set */
  public void setName(String name) {
    this.name = name;
  }

  /** @return the description */
  public String getDescription() {
    return description;
  }

  /** @param description the description to set */
  public void setDescription(String description) {
    this.description = description;
  }

  /** @return the parentId */
  public String getParentId() {
    return parentId;
  }

  @Override
  public int compareTo(FunctionPermissionDTO o) {
    // TODO Auto-generated method stub
    return 0;
  }
}
