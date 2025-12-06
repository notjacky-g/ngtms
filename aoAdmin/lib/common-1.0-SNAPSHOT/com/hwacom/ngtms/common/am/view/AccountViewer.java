/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

public class AccountViewer extends Composite {

  private static AccountViewerUiBinder uiBinder = GWT.create(AccountViewerUiBinder.class);

  interface AccountViewerUiBinder extends UiBinder<Widget, AccountViewer> {}

  @UiField BorderLayoutContainer container;

  @UiField TabPanel tabPanel;

  @UiField UserViewer userViewer;

  @UiField RoleFunctionPermissionViewer roleFunctionPermissionViewer;

  public AccountViewer() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setTitleViewer(IsWidget widget, int height) {
    container.setNorthWidget(widget, new BorderLayoutContainer.BorderLayoutData(height));
  }

  public void addTab(Widget widget, String text) {
    tabPanel.add(widget, new TabItemConfig(text));
  }

  public UserViewer getUserViewer() {
    return userViewer;
  }

  public RoleFunctionPermissionViewer getRoleFunctionPermissionViewer() {
    return roleFunctionPermissionViewer;
  }
}
