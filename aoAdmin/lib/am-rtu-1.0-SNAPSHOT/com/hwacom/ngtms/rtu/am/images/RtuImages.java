package com.hwacom.ngtms.rtu.am.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface RtuImages extends ClientBundle {

  public RtuImages INSTANCE = GWT.create(RtuImages.class);

  ImageResource bannerImage();

  ImageResource deviceStatus();

  ImageResource deviceStatusOver();

  ImageResource tcConfigQuery();

  ImageResource tcConfigQueryOver();

  ImageResource tcConfigSetting();

  ImageResource tcConfigSettingOver();

  ImageResource identification();

  ImageResource identificationOver();

  ImageResource travelTimeDataFilter();

  ImageResource travelTimeDataFilterOver();

  ImageResource vehicleFilter();

  ImageResource vehicleFilterOver();

  ImageResource stationaryVehicle();

  ImageResource stationaryVehicleOver();

  ImageResource section();

  ImageResource sectionOver();

  ImageResource deviceInfo();

  ImageResource overDeviceInfo();
}
