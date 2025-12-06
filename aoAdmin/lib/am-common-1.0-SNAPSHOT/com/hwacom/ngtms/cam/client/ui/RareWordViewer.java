/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.hwacom.ngtms.c.dis.shared.RareWordMessage;
import com.hwacom.ngtms.cam.client.event.RareWordViewerEvent;
import com.hwacom.ngtms.cam.client.event.RareWordViewerEvent.Action;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.data.shared.writer.JsonWriter;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import java.util.ArrayList;
import java.util.List;

public class RareWordViewer extends Composite {

  private static RareWordViewerUiBinder uiBinder = GWT.create(RareWordViewerUiBinder.class);

  interface RareWordViewerUiBinder extends UiBinder<Widget, RareWordViewer> {}

  private static Messages messages = GWT.create(Messages.class);

  private List<String> deviceNames = new ArrayList<String>();

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private final CmsRareWordMessageAutoBeanFactory factory =
      GWT.create(CmsRareWordMessageAutoBeanFactory.class);

  private boolean active;

  private boolean downloading;

  @UiField SimpleContainer container;

  public RareWordViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    mask(messages.hint_selectDevice());
  }

  private native void registerDownloadFunction(Element object) /*-{
        var self = this;
        object.onDownload = $entry(function(code, word32, word48, word64) {
            self.@com.hwacom.ngtms.cam.client.ui.RareWordViewer::download(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(code, word32, word48, word64);
        });
    }-*/;

  private void download(
      final String code, final String word32, final String word48, final String word64) {
    RareWordMessage message = factory.rareWordMessage().as();
    message.setType(RareWordMessage.TYPE);
    message.setDeviceNames(deviceNames);
    message.setCode(code);
    message.setWord32(word32);
    message.setWord48(word48);
    message.setWord64(word64);
    JsonWriter<RareWordMessage> jsonWriter = new JsonWriter<>(factory, RareWordMessage.class);
    String json = jsonWriter.write(message);
    clientFactory.getEventBus().fireEventFromSource(new RareWordViewerEvent(Action.DOWNLOAD), json);
  }

  @Override
  public void unmask() {
    // 為了讓 ActiveX 的元件正常顯示，在 unmask 時需重新設定
    attachActiveXObject();
    super.unmask();
  }

  private void attachActiveXObject() {
    HTML html =
        new HTML(
            SafeHtmlUtils.fromTrustedString(
                "<object"
                    + " id=\"newFont\""
                    + " name=\"newFont\""
                    + " classid=\"clsid:E101D0F5-21B6-4D4E-A6D3-29DD38687616\""
                    + " codebase=\"ActiveX/NewFontActiveX.cab#1,0,6,0\" />"));
    container.setWidget(html);
    NodeList<Element> nodeList = html.getElement().getElementsByTagName("object");
    registerDownloadFunction(nodeList.getItem(0));
  }

  public void addDevices(List<String> deviceNames) {
    if (active && this.deviceNames.isEmpty()) {
      unmask();
    }
    for (String deviceName : deviceNames) {
      if (!this.deviceNames.contains(deviceName)) {
        this.deviceNames.add(deviceName);
      }
    }
  }

  public void removeDevices(List<String> deviceNames) {
    this.deviceNames.removeAll(deviceNames);
    if (this.deviceNames.isEmpty()) {
      mask(messages.hint_selectDevice());
    }
  }

  public void setActive(boolean active) {
    this.active = active;
    if (active && !deviceNames.isEmpty() && !downloading) {
      unmask();
    }
  }

  public void downloading() {
    mask(messages.inProgress());
    downloading = true;
  }

  public void downloadFinished() {
    if (active) {
      unmask();
    }
    downloading = false;
  }

  interface CmsRareWordMessageAutoBeanFactory extends AutoBeanFactory {
    AutoBean<RareWordMessage> rareWordMessage();
  }
}
