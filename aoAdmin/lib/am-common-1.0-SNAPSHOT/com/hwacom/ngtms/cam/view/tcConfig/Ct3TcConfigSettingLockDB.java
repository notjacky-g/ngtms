package com.hwacom.ngtms.cam.view.tcConfig;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;

public class Ct3TcConfigSettingLockDB extends TcConfigSettingItem {

  private static Ct3TcConfigSettingCommandUiBinder uiBinder =
      GWT.create(Ct3TcConfigSettingCommandUiBinder.class);

  interface Ct3TcConfigSettingCommandUiBinder extends UiBinder<Widget, Ct3TcConfigSettingLockDB> {}

  @UiField HBoxLayoutContainer hBoxLayoutContainer;

  @UiField(provided = true)
  SimpleComboBox<Integer> lockDbCB;

  public Ct3TcConfigSettingLockDB() {
    initComboBox();
    lockDbCB =
        new SimpleComboBox<Integer>(
            new LabelProvider<Integer>() {
              @Override
              public String getLabel(Integer item) {
                return CommonStringConverter.getCT3OperationMode(item);
              }
            });

    initWidget(uiBinder.createAndBindUi(this));
    for (int i = 0; i <= 2; i++) {
      lockDbCB.add(i);
    }
    lockDbCB.setValue(0);
  }

  private void initComboBox() {}

  public Integer getLockDb() {
    return lockDbCB.getCurrentValue();
  }

  @Override
  public void forceLayout() {
    hBoxLayoutContainer.forceLayout();
  }
}
