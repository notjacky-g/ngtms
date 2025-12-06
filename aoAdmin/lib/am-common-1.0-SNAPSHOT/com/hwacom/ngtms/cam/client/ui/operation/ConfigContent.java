/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.operation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.view.Messages;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldSet;

/**
 * 提供「現場組態設定」的「設定區」要顯示的內容。 由於此區內容的 layout 與按鈕行為固定，因此抽出 abstract class， 由各 child class 實做差異的部份。
 *
 * @author monty.pan
 * @param <H> 不同 config content 可掛載的 submit handler
 */
public abstract class ConfigContent<H extends OpSubmitHandler> implements IsWidget {
  private final Messages messages = GWT.create(Messages.class);
  private FramedPanel root = new FramedPanel();
  private FieldSet context = new FieldSet();
  private HandlerManager eventBus = new HandlerManager(null);
  private final String name;

  /** 按下「設定」按鈕會觸發的程序。基本上是製造一個對應的 {@link OpSubmitEvent}，然後 {@link #fireEvent(OpSubmitEvent)}。 */
  protected abstract void submit();

  /**
   * 給 caller 掛載對應 handler 的介面。
   *
   * @param h
   * @return
   */
  public abstract HandlerRegistration addSubmitHandler(H h);

  /** @return 不同 config content 的 UI 內容 */
  protected abstract Widget getContent();

  //XXX 目前找不到不用 swing-way 的方法組畫面（因為 context 內容是 child class 提供）
  /** @param name 「設定」區與「功能選項」區會顯示的名稱 */
  protected ConfigContent(String name) {
    this.name = name;
    context.setHeading(name);

    root.setHeaderVisible(false);
    root.setResize(true);
    root.add(context);
    TextButton submitBtn =
        new TextButton(
            messages.button_set(),
            new SelectHandler() { //I18N
              @Override
              public void onSelect(SelectEvent event) {
                submit();
              }
            });
    root.addButton(submitBtn);
  }

  public String getName() {
    return name;
  }

  protected final HandlerRegistration addHandler(Type<H> type, H handler) {
    return eventBus.addHandler(type, handler);
  }

  protected void fireEvent(OpSubmitEvent<? extends OpSubmitHandler> event) {
    eventBus.fireEvent(event);
  }

  @Override
  public final Widget asWidget() {
    context.add(getContent());
    context.setWidth(1);
    /* XXX 非常奇怪的 workaround
     * 如果 context 有設定寬度，則整個 ContentBase 的大小就會 follow GXT 的 size 機制，
     * 但是為什麼看上去會撐滿 DeviceConfigView 的 setupPanel 就又是另一個謎。
     * （設定最底下的 root 還不行，可能是因為 DeviceConfigView 的 setupPanel.setResize()？）
     */
    return root;
  }
}
