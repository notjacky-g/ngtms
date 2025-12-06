package com.hwacom.ngtms.cam.view.tcConfig;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;

public class Ct3TcConfigSettingCommand extends TcConfigSettingItem {

  private static Ct3TcConfigSettingCommandUiBinder uiBinder =
      GWT.create(Ct3TcConfigSettingCommandUiBinder.class);

  interface Ct3TcConfigSettingCommandUiBinder extends UiBinder<Widget, Ct3TcConfigSettingCommand> {}

  @UiField HBoxLayoutContainer hBoxLayoutContainer;

  @UiField(provided = true)
  SimpleComboBox<Integer> commandSetCombo;

  String commandSet0 = "訊息等級B，包含基本訊息，但使用無線環境";
  String commandSet1 = "訊息等級B，包含基本訊息";
  String commandSet2 = "訊息等級B及A，包含基本訊息及進階訊息";
  String commandSet3 = "訊息等級B、A及O，包含基本訊息、進階訊息及選擇訊息";

  public Ct3TcConfigSettingCommand() {
    initComboBox();
    commandSetCombo =
        new SimpleComboBox<Integer>(
            new LabelProvider<Integer>() {
              @Override
              public String getLabel(Integer item) {
                switch (item) {
                  case 0:
                    return "訊息等級B，包含基本訊息，但使用無線環境";
                  case 1:
                    return "訊息等級B，包含基本訊息";
                  case 2:
                    return "訊息等級B及A，包含基本訊息及進階訊息";
                  case 3:
                    return "訊息等級B、A及O，包含基本訊息、進階訊息及選擇訊息";
                  default:
                    return "";
                }
              }
            });

    initWidget(uiBinder.createAndBindUi(this));
    for (int i = 0; i <= 3; i++) {
      commandSetCombo.add(i);
    }
    commandSetCombo.setValue(0);
  }

  private void initComboBox() {}

  public Integer getCommandSet() {
    return commandSetCombo.getCurrentValue();
  }

  @Override
  public void forceLayout() {
    hBoxLayoutContainer.forceLayout();
  }
}
