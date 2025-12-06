package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

/**
 * For坪林的CCTV線上XML 外網URL欄位
 *
 * @author johnny.lin
 */
public class UrlIntraPic implements Serializable {

  private static final long serialVersionUID = 1L;

  private String linkurl;

  public String getLinkurl() {
    return linkurl;
  }

  public void setLinkurl(String linkurl) {
    this.linkurl = linkurl;
  }
}
