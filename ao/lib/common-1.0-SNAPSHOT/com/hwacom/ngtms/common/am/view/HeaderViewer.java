package com.hwacom.ngtms.common.am.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface HeaderViewer extends IsWidget {

  void setHeader(String header);

  void setBackgroundImage(String url);
}
