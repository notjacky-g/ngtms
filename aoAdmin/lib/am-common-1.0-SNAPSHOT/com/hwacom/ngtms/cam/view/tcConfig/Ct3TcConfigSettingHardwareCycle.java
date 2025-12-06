package com.hwacom.ngtms.cam.view.tcConfig;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.Radio;

public class Ct3TcConfigSettingHardwareCycle extends TcConfigSettingItem {

  private static Ct3TcConfigSettingHardwareCycleUiBinder uiBinder =
      GWT.create(Ct3TcConfigSettingHardwareCycleUiBinder.class);

  interface Ct3TcConfigSettingHardwareCycleUiBinder
      extends UiBinder<Widget, Ct3TcConfigSettingHardwareCycle> {}

  @UiField VerticalLayoutContainer verticalLayoutContainer;

  ToggleGroup hardwareToggleGroup;

  @UiField Radio termination;

  @UiField Radio oneSecond;

  @UiField Radio twoSeconds;

  @UiField Radio fiveSeconds;

  @UiField Radio oneMinute;

  @UiField Radio fiveMinutes;

  public Ct3TcConfigSettingHardwareCycle() {
    initWidget(uiBinder.createAndBindUi(this));
    hardwareToggleGroup = new ToggleGroup();
    hardwareToggleGroup.add(termination);
    hardwareToggleGroup.add(oneSecond);
    hardwareToggleGroup.add(twoSeconds);
    hardwareToggleGroup.add(fiveSeconds);
    hardwareToggleGroup.add(oneMinute);
    hardwareToggleGroup.setValue(fiveMinutes);
  }

  public Integer getHardwareTransmissionPeriod() {
    Radio radio = (Radio) hardwareToggleGroup.getValue();
    if (termination.getBoxLabel().equals(radio.getBoxLabel())) {
      return 0;
    } else if (oneSecond.getBoxLabel().equals(radio.getBoxLabel())) {
      return 1;
    } else if (twoSeconds.getBoxLabel().equals(radio.getBoxLabel())) {
      return 2;
    } else if (fiveSeconds.getBoxLabel().equals(radio.getBoxLabel())) {
      return 3;
    } else if (oneMinute.getBoxLabel().equals(radio.getBoxLabel())) {
      return 4;
    } else if (fiveMinutes.getBoxLabel().equals(radio.getBoxLabel())) {
      return 5;
    } else {
      throw new IllegalStateException(
          "can't identified HardwareTransmissionPeriod:" + radio.getBoxLabel());
    }
  }

  @Override
  public void forceLayout() {
    verticalLayoutContainer.forceLayout();
  }
}
