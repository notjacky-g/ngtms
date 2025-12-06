/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class CctvConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 2854713471447169750L;
  /** Camera Id，調用時必須參考此值 CCTV 設備名稱 */
  private String cameraName;

  /** 外網連結的 URL cctv_config_data.xml中url_inter */
  private String externalUrl;

  /** cctv_config_data.xml中url_inter_pic */
  private String externaPiclUrl;

  /** cctv_config_data.xml中url_inter_pda */
  private String externalPdaUrl;

  /** cctv_config_data.xml中url_inter_pda_pic */
  private String externalPdaPicUrl;

  /** 內網連結的 URL cctv_config_data.xml中url_intra */
  private String internalUrl;

  /** cctv_config_data.xml中url_intra_pic */
  private String internalPicUrl;

  /** cctv_config_data.xml中url_intra_one */
  private String internalOneUrl;

  /** cctv_config_data.xml中url_megapixel */
  private String urlMegaPixel;

  public String getCameraName() {
    return cameraName;
  }

  public void setCameraName(String cameraName) {
    this.cameraName = cameraName;
  }

  public String getExternalUrl() {
    return externalUrl;
  }

  public void setExternalUrl(String externalUrl) {
    this.externalUrl = externalUrl;
  }

  public String getExternaPiclUrl() {
    return externaPiclUrl;
  }

  public void setExternaPiclUrl(String externaPiclUrl) {
    this.externaPiclUrl = externaPiclUrl;
  }

  public String getExternalPdaUrl() {
    return externalPdaUrl;
  }

  public void setExternalPdaUrl(String externalPdaUrl) {
    this.externalPdaUrl = externalPdaUrl;
  }

  public String getExternalPdaPicUrl() {
    return externalPdaPicUrl;
  }

  public void setExternalPdaPicUrl(String externalPdaPicUrl) {
    this.externalPdaPicUrl = externalPdaPicUrl;
  }

  public String getInternalUrl() {
    return internalUrl;
  }

  public void setInternalUrl(String internalUrl) {
    this.internalUrl = internalUrl;
  }

  public String getInternalPicUrl() {
    return internalPicUrl;
  }

  public void setInternalPicUrl(String internalPicUrl) {
    this.internalPicUrl = internalPicUrl;
  }

  public String getInternalOneUrl() {
    return internalOneUrl;
  }

  public void setInternalOneUrl(String internalOneUrl) {
    this.internalOneUrl = internalOneUrl;
  }

  public String getUrlMegaPixel() {
    return urlMegaPixel;
  }

  public void setUrlMegaPixel(String urlMegaPixel) {
    this.urlMegaPixel = urlMegaPixel;
  }

  @Override
  public String toString() {
    return "CctvConfigDTO [cameraName="
        + cameraName
        + ", externalUrl="
        + externalUrl
        + ", externaPiclUrl="
        + externaPiclUrl
        + ", externalPdaUrl="
        + externalPdaUrl
        + ", externalPdaPicUrl="
        + externalPdaPicUrl
        + ", internalUrl="
        + internalUrl
        + ", internalPicUrl="
        + internalPicUrl
        + ", internalOneUrl="
        + internalOneUrl
        + ", urlMegaPixel="
        + urlMegaPixel
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cameraName == null) ? 0 : cameraName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    CctvConfigDTO other = (CctvConfigDTO) obj;
    if (cameraName == null) {
      if (other.cameraName != null) return false;
    } else if (!cameraName.equals(other.cameraName)) return false;
    return true;
  }
}
