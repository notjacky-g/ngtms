package com.hwacom.ngtms.cam.view.device;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.LocationTypeEnum;
import com.hwacom.ngtms.c.shared.RampType;
import com.hwacom.ngtms.c.shared.dto.DeviceTcConfigDTO;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.util.Arrays;

public class DeviceTcConfigForm extends Composite {

  private static DeviceTcConfigFormUiBinder uiBinder = GWT.create(DeviceTcConfigFormUiBinder.class);

  interface DeviceTcConfigFormUiBinder extends UiBinder<Widget, DeviceTcConfigForm> {}

  private LocationTypeEnum locationTypeEnum = GWT.create(LocationTypeEnum.class);

  @UiField IntegerField milepost;

  @UiField TextField mfccId;

  @UiField TextField defaultMfccId;

  @UiField TextField lineId;

  @UiField TextField sectionId;

  @UiField SimpleComboBox<Enum<?>> locationType;

  @UiField(provided = true)
  LabelProvider<Enum<?>> locationTypeLabelProvider;

  @UiField SimpleComboBox<RampType> rampType;

  @UiField(provided = true)
  LabelProvider<RampType> rampTypeLabelProvider;

  @UiConstructor
  public DeviceTcConfigForm() {
    initComboBoxLabelProvider();
    initWidget(uiBinder.createAndBindUi(this));
    initComboBox();
  }

  private void initComboBoxLabelProvider() {
    locationTypeLabelProvider =
        new LabelProvider<Enum<?>>() {
          @Override
          public String getLabel(Enum<?> item) {
            return locationTypeEnum.toLabel(item);
          }
        };

    rampTypeLabelProvider =
        new LabelProvider<RampType>() {
          @Override
          public String getLabel(RampType item) {
            return CommonStringConverter.getRampTypeName(item);
          }
        };
  }

  private void initComboBox() {
    locationType.add(locationTypeEnum.values());

    rampType.add(Arrays.asList(RampType.values()));
  }

  public DeviceTcConfigDTO getDto() {
    DeviceTcConfigDTO dto = new DeviceTcConfigDTO();
    dto.setMilepost(milepost.getValue());
    dto.setMfccId(mfccId.getValue());
    dto.setDefaultMfccId(defaultMfccId.getValue());
    dto.setLineId(lineId.getValue());
    dto.setSectionId(sectionId.getValue());
    dto.setLocation(locationType.getValue() != null ? locationType.getValue().toString() : null);
    dto.setRampType(rampType.getValue());
    return dto;
  }

  public void setDto(DeviceTcConfigDTO dto) {
    milepost.setValue(dto.getMilepost());
    mfccId.setValue(dto.getMfccId());
    defaultMfccId.setValue(dto.getDefaultMfccId());
    lineId.setValue(dto.getLineId());
    sectionId.setValue(dto.getSectionId());
    locationType.setValue(locationTypeEnum.valueOf(dto.getLocation()));
    rampType.setValue(dto.getRampType());
  }
}
