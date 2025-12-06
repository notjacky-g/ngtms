package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.widget.core.client.ModalPanel.ModalPanelAppearance;

public class ModalPanelBlackAppearance implements ModalPanelAppearance {
  public interface ModalPanelResources extends ClientBundle {
    @Source("ModalPanel.gss")
    ModalPanelStyle css();
  }

  public interface ModalPanelStyle extends CssResource {
    String panel();
  }

  private final ModalPanelResources resources;
  private final ModalPanelStyle style;

  public ModalPanelBlackAppearance() {
    this(GWT.<ModalPanelResources>create(ModalPanelResources.class));
  }

  public ModalPanelBlackAppearance(ModalPanelResources resources) {
    this.resources = resources;
    this.style = this.resources.css();
    this.style.ensureInjected();
  }

  @Override
  public void render(SafeHtmlBuilder sb) {
    sb.appendHtmlConstant("<div class='" + style.panel() + "'></div>");
  }
}
