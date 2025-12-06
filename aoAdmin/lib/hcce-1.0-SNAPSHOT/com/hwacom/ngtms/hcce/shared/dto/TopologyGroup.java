/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum TopologyGroup implements IsSerializable {
  HC_PRIMARY_GROUP("hc_primary_group"),
  HC_BACKUP_GROUP("hc_backup_group"),
  HC_CLIENT_GROUP("hc_client_group");

  private final String name;

  private TopologyGroup(String s) {
    name = s;
  }

  public boolean equalsName(String otherName) {
    return (otherName == null) ? false : name.equals(otherName);
  }

  public String toString() {
    return name;
  }
}
