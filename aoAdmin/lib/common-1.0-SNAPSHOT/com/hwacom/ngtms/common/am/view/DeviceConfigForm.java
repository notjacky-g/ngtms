package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.shared.dto.DeviceConfigDTO;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;

public class DeviceConfigForm extends Composite {

  private static DeviceConfigFormUiBinder uiBinder = GWT.create(DeviceConfigFormUiBinder.class);

  interface DeviceConfigFormUiBinder extends UiBinder<Widget, DeviceConfigForm> {}

  private String deviceType;

  private ToggleGroup toggle;

  @UiField VerticalLayoutContainer verticalLayoutContainer;

  @UiField TextField deviceName;

  @UiField TextField displayName;

  @UiField TextField ip;

  @UiField IntegerField port;

  @UiField TextField project;

  @UiField FieldLabel longitudeContainer;

  @UiField DoubleField longitude;

  @UiField FieldLabel latitudeContainer;

  @UiField DoubleField latitude;

  @UiField TextField memo;

  @UiField Radio deviceConfigEnable;

  @UiField Radio deviceConfigDisable;

  @UiConstructor
  public DeviceConfigForm(String deviceType) {
    this.deviceType = deviceType;
    initWidget(uiBinder.createAndBindUi(this));
    toggle = new ToggleGroup();
    toggle.add(deviceConfigEnable);
    toggle.add(deviceConfigDisable);
    toggle.setValue(deviceConfigEnable);
  }

  public void removeCoordinateFields() {
    longitudeContainer.removeFromParent();
    latitudeContainer.removeFromParent();
  }

  public void setScrollMode(ScrollMode scrollMode) {
    verticalLayoutContainer.setScrollMode(scrollMode);
  }

  public DeviceConfigDTO getDto() {
    if (!validate()) {
      return null;
    }
    DeviceConfigDTO dto = new DeviceConfigDTO();
    dto.setDeviceName(deviceName.getValue());
    dto.setDisplayName(displayName.getValue());
    Radio selected = (Radio) toggle.getValue();
    dto.setEnable(deviceConfigEnable.getBoxLabel().equals(selected.getBoxLabel()));
    dto.setDeviceType(deviceType);
    dto.setIp(ip.getValue());
    if (port.getValue() != null) {
      dto.setPort(Integer.toString(port.getValue()));
    }
    dto.setProject(project.getValue());
    dto.setLatitude(latitude.getValue());
    dto.setLongitude(longitude.getValue());
    dto.setMemo(memo.getValue());
    return dto;
  }

  public void setDto(DeviceConfigDTO dto) {
    deviceName.setValue(dto.getDeviceName());
    deviceName.setEnabled(dto.getDeviceName() == null);
    displayName.setValue(dto.getDisplayName());
    if (dto.isEnable() != null) {
      toggle.setValue(dto.isEnable() ? deviceConfigEnable : deviceConfigDisable);
    }
    ip.setValue(dto.getIp());
    if (dto.getPort() != null) {
      port.setValue(Integer.parseInt(dto.getPort()));
    }
    project.setValue(dto.getProject());
    latitude.setValue(dto.getLatitude());
    longitude.setValue(dto.getLongitude());
    memo.setValue(dto.getMemo());
    verticalLayoutContainer.forceLayout();
  }

  private boolean validate() {
    boolean valid = true;
    if (!deviceName.isValid()) {
      valid = false;
    }
    if (!displayName.isValid()) {
      valid = false;
    }
    return valid;
  }
}
