/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RoadTreeViewerEventHandler extends EventHandler {

  /**
   * fire if action equals {@code Action.SELECT_DEVICE}
   *
   * @param event
   * @see RoadTreeViewSelectionEvent.Action
   */
  void onSelectSelectedDevice(RoadTreeViewerEvent event);

  void onRemoveAllDevice(RoadTreeViewerEvent event);

  void onReady(RoadTreeViewerEvent event);
}
