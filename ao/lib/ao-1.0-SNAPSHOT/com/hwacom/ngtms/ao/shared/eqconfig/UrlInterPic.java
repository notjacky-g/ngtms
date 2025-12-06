package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

/**
 * Fo中區國道機房的CCTV線上XML 內網URL欄位
 *
 * @author johnny.lin
 */
public class UrlInterPic implements Serializable {

  private static final long serialVersionUID = 5377426646080893204L;

  private String linkurl;

  public String getLinkurl() {
    return linkurl;
  }

  public void setLinkurl(String linkurl) {
    this.linkurl = linkurl;
  }
}
