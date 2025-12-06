/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.event.shared.Event;
import com.hwacom.ngtms.cam.client.event.TitleViewMaskEvent.TitleViewMaskEventHandler;
import com.hwacom.ngtms.cam.client.event.TitleViewSystemEnvEvent.TitleViewSystemEnvEventHandler;

@Deprecated
public class AmEventCenter {
  private static final SimpleEventBus eventBus = new SimpleEventBus();

  public static void fireEvent(Event<? extends EventHandler> event) {
    eventBus.fireEvent(event);
  }

  public static void fireEventFromSource(Event<? extends EventHandler> event, Object source) {
    eventBus.fireEventFromSource(event, source);
  }

  public static HandlerRegistration addRoadTreeSelectHandler(RoadTreeSelectHandler h) {
    return eventBus.addHandler(RoadTreeSelectEvent.TYPE, h);
  }

  public static HandlerRegistration addDeviceConfigSelectHandler(DeviceConfigSelectHandler h) {
    return eventBus.addHandler(DeviceConfigSelectEvent.TYPE, h);
  }

  public static HandlerRegistration addDeviceConfigDeselectHandler(DeviceConfigDeselectHandler h) {
    return eventBus.addHandler(DeviceConfigDeselectEvent.TYPE, h);
  }

  public static HandlerRegistration addDataModifyHandler(DataModifyHandler h) {
    return eventBus.addHandler(DataModifyEvent.TYPE, h);
  }

  public static HandlerRegistration addDataFetchHandler(DataFetchHandler h) {
    return eventBus.addHandler(DataFetchEvent.TYPE, h);
  }

  public static HandlerRegistration addRoadTreeViewerEventHandler(RoadTreeViewerEventHandler h) {
    return eventBus.addHandler(RoadTreeViewerEvent.TYPE, h);
  }

  public static HandlerRegistration addChartItemSelectionEventHandler(ChartItemSelectionHandler h) {
    return eventBus.addHandler(ChartItemSelectionEvent.TYPE, h);
  }

  public static HandlerRegistration addGroupOperationHandler(GroupOperationHandler h) {
    return eventBus.addHandler(GroupOperationEvent.TYPE, h);
  }

  public static HandlerRegistration addRoadTreeViewerMaskEventHandler(
      RoadTreeViewerMaskEventHandler h) {
    return eventBus.addHandler(RoadTreeViewerMaskEvent.TYPE, h);
  }

  public static HandlerRegistration addDragItemSelectHandler(DragItemSelectHandler h) {
    return eventBus.addHandler(DragItemSelectEvent.TYPE, h);
  }

  public static HandlerRegistration addDragItemDeselectHandler(DragItemDeselectHandler h) {
    return eventBus.addHandler(DragItemDeselectEvent.TYPE, h);
  }

  public static HandlerRegistration addTitleViewMaskEventHandler(TitleViewMaskEventHandler h) {
    return eventBus.addHandler(TitleViewMaskEvent.TYPE, h);
  }

  public static HandlerRegistration addTitleViewSystemEnvEventHandler(
      TitleViewSystemEnvEventHandler h) {
    return eventBus.addHandler(TitleViewSystemEnvEvent.TYPE, h);
  }
}
