/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import java.io.Serializable;
import java.util.Date;

public class BrowserAlarm implements Serializable {

  private static final long serialVersionUID = 3501479412380047324L;
  private Date publishDate;
  private String content;

  public Date getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(Date publishDate) {
    this.publishDate = publishDate;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "BrowserAlarm [publishDate=" + publishDate + ", content=" + content + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((publishDate == null) ? 0 : publishDate.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    BrowserAlarm other = (BrowserAlarm) obj;
    if (publishDate == null) {
      if (other.publishDate != null) return false;
    } else if (!publishDate.equals(other.publishDate)) return false;
    return true;
  }
}
