/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

/** 在 RoadTreeView 中用來 DnD 的基底類別 */
public class DragItem implements Serializable, IsSerializable {
  private static final long serialVersionUID = -2175715717267824278L;

  protected String uuid;
  protected String selectedDisplayName;

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }

  public String getSelectedDisplayName() {
    return selectedDisplayName;
  }

  public void setSelectedDisplayName(String selectedDisplayName) {
    this.selectedDisplayName = selectedDisplayName;
  }
}
