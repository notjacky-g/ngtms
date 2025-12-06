/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.TabItemConfig;

/**
 * {@link AmTabPanel} 中各 tab widget 的統一 parent class。 主要是定義 API 讓 AmTabPanel 可以自動建 overview tab 的內容。
 *
 * <p>為了簡化 ui.xml，{@link AmTab} 也包含了 {@link TabItemConfig} 的資訊：
 *
 * <ul>
 *   <li>{@link #setTabTitle(String)}
 *   <li>{@link #setClosable(boolean)}
 * </ul>
 *
 * 如果有設定這些值，透過 {@link AmTabPanel#addItem(AmTab)}（ui.xml 則是 <code>&lt;am:item&gt;</code>） 就會自動產生
 * {@link TabItemConfig}。
 *
 * @see AmTabPanel#addTab(AmTab, TabItemConfig)
 * @see AmTabPanel#addItem(AmTab)
 * @author monty.pan
 */
public class AmTab extends Composite {
  private String tabTitle = "";
  private boolean closable = true;
  private boolean defaultShow = false;
  private ImageResource buttonIcon;
  private ImageResource buttonOverIcon;

  public ImageResource getButtonIcon() {
    return buttonIcon;
  }

  public void setButtonIcon(ImageResource buttonIcon) {
    this.buttonIcon = buttonIcon;
  }

  public String getTabTitle() {
    return tabTitle;
  }

  public void setTabTitle(String tabTitle) {
    this.tabTitle = tabTitle;
  }

  public boolean isClosable() {
    return closable;
  }

  /** @param closable tab 是否能關閉，預設值為 true */
  public void setClosable(boolean closable) {
    this.closable = closable;
  }

  public boolean isDefaultShow() {
    return defaultShow;
  }

  /** @param defaultShow tab 是否預設顯示，預設值為 false */
  public void setDefaultShow(boolean defaultShow) {
    this.defaultShow = defaultShow;
  }

  /** @return the buttonOverIcon */
  public ImageResource getButtonOverIcon() {
    return buttonOverIcon;
  }

  /** @param buttonOverIcon the buttonOverIcon to set */
  public void setButtonOverIcon(ImageResource buttonOverIcon) {
    this.buttonOverIcon = buttonOverIcon;
  }
}
