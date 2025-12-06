/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class NodeDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -2545732446012945193L;
  private String key;
  private String name;
  /** @return the key */
  public String getKey() {
    return key;
  }
  /** @param key the key to set */
  public void setKey(String key) {
    this.key = key;
  }
  /** @return the name */
  public String getName() {
    return name;
  }
  /** @param name the name to set */
  public void setName(String name) {
    this.name = name;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("NodeDTO [key=");
    builder.append(key);
    builder.append(", name=");
    builder.append(name);
    builder.append("]");
    return builder.toString();
  }
}
