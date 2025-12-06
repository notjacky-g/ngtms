/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.client.ui.component.ScrollFloatContainer;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import java.util.List;

public class GroupMenuItem extends Composite {

  private static GroupItemUiBinder uiBinder = GWT.create(GroupItemUiBinder.class);

  interface GroupItemUiBinder extends UiBinder<Widget, GroupMenuItem> {}

  private String name;

  @UiField ScrollFloatContainer groupItemPanel;

  public GroupMenuItem(String name) {
    this.name = name;
    this.initWidget(uiBinder.createAndBindUi(this));
    groupItemPanel.setScrollMode(ScrollMode.AUTOY);
  }

  public String getName() {
    return name;
  }

  public void add(List<ImageItem> itemList) {
    for (ImageItem item : itemList) {
      SimpleContainer con = new SimpleContainer();
      con.add(item, new MarginData(5, 0, 0, 5));
      groupItemPanel.add(con);
    }
  }
}
