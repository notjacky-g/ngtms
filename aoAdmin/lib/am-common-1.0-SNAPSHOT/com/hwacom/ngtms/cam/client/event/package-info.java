@GwtEvents({
  @GwtEvent(
    name = "RareWordViewerEvent",
    actions = { "DOWNLOAD"}),
  @GwtEvent(
    name = "RareWordQueryViewerEvent",
    actions = {
       "QUERY_LIST",
       "QUERY"
    }
  ),
  @GwtEvent(
    name = "BackgroundGraphicEditViewerEvent",
    actions = {
       "DEVICE_CHANGED",
       "SELECT_GRAPHIC",
       "DOWNLOAD_ALL",
       "DOWNLOAD",
       "CREATE_GRAPHIC",
       "MODIFY_GRAPHIC",
       "REMOVE_GRAPHIC" }),
  @GwtEvent(
    name = "BackgroundGraphicQueryViewerEvent",
    actions = {
       "QUERY_LIST",
       "QUERY_GRAPHIC" }),
  @GwtEvent(
		    name = "TitleViewEvent",
		    actions = {
		       "REFRESH_ROLE" })
})
package com.hwacom.ngtms.cam.client.event;

import com.hwacom.ngtms.toolbox.gwt.annotation.GwtEvent;
import com.hwacom.ngtms.toolbox.gwt.annotation.GwtEvents;
