/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.component;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.HasScrollSupport;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HasLayout;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import java.util.List;

/**
 * 提供一個會出現 scroll bar、child 的 layout 行為是 float 的 layout container。
 *
 * @author monty.pan
 */
public class ScrollFloatContainer extends Composite implements HasLayout, HasScrollSupport {
  /* 有 scroll support、最 light 的好像就是 H/VerticalLayoutContainer
   * FlowLayoutContainer 看起來沒有套 GXT Layout 機制
   * 加上考慮 TabPanel 的詭異 bug，所以暫時不考慮 FlowLayoutContainer
   */
  private VerticalLayoutContainer scrollRoot = new VerticalLayoutContainer();

  // XXX 注意：GXT 3.1.2 之後 CssFloatLayoutContainer 也有對應的 LayoutData
  private CssFloatLayoutContainer floatRoot = new CssFloatLayoutContainer();

  public ScrollFloatContainer() {
    initWidget(scrollRoot);
    scrollRoot.add(floatRoot, new VerticalLayoutData(-1, -1));
    setScrollMode(ScrollMode.AUTO);
  }

  @Override
  public void forceLayout() {
    scrollRoot.forceLayout();
  }

  @Override
  public boolean isLayoutRunning() {
    return scrollRoot.isLayoutRunning();
  }

  @Override
  public boolean isOrWasLayoutRunning() {
    return scrollRoot.isOrWasLayoutRunning();
  }

  @Override
  public ScrollSupport getScrollSupport() {
    return scrollRoot.getScrollSupport();
  }

  @Override
  public void setScrollSupport(ScrollSupport scrollSupport) {
    scrollRoot.setScrollSupport(scrollSupport);
  }

  public ScrollMode getScrollMode() {
    return getScrollSupport().getScrollMode();
  }

  public void setScrollMode(ScrollMode scrollMode) {
    getScrollSupport().setScrollMode(scrollMode);
  }

  public void add(IsWidget child) {
    add(Widget.asWidgetOrNull(child));
  }

  public void add(Widget child) {
    floatRoot.add(child);
  }

  public void insertBeforeLastOne(Widget child) {
    floatRoot.insert(child, floatRoot.getWidgetCount() - 1);
  }

  public int getWidgetCount() {
    return floatRoot.getWidgetCount();
  }

  public Widget getWidget(int index) {
    return floatRoot.getWidget(index);
  }

  public void removeWidget(List<Widget> items) {
    for (Widget w : items) {
      floatRoot.remove(w);
    }
    floatRoot.forceLayout();
  }
}
