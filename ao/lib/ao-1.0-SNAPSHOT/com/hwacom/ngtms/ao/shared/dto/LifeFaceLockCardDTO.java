/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.List;

public class LifeFaceLockCardDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -8432541518053735141L;
  /** id(時間+主機 ) */
  private String id;
  /** 辨識時間 */
  private String lifeFaceTime;
  /** 辨識地點 */
  private String lifeFaceLocation;
  /** 顯示鎖卡人員 */
  private List<String> lockPeople;
  /** 卡片資料 */
  private List<String> lockCards;
  /** 辨識結果 */
  private boolean isFaceMatch;
  /** 是否鎖卡 */
  private boolean isLockCard;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLifeFaceTime() {
    return lifeFaceTime;
  }

  public void setLifeFaceTime(String lifeFaceTime) {
    this.lifeFaceTime = lifeFaceTime;
  }

  public String getLifeFaceLocation() {
    return lifeFaceLocation;
  }

  public void setLifeFaceLocation(String lifeFaceLocation) {
    this.lifeFaceLocation = lifeFaceLocation;
  }

  public List<String> getLockPeople() {
    return lockPeople;
  }

  public void setLockPeople(List<String> lockPeople) {
    this.lockPeople = lockPeople;
  }

  public List<String> getLockCards() {
    return lockCards;
  }

  public void setLockCards(List<String> lockCards) {
    this.lockCards = lockCards;
  }

  public boolean isFaceMatch() {
    return isFaceMatch;
  }

  public void setFaceMatch(boolean isFaceMatch) {
    this.isFaceMatch = isFaceMatch;
  }

  public boolean isLockCard() {
    return isLockCard;
  }

  public void setLockCard(boolean isLockCard) {
    this.isLockCard = isLockCard;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    LifeFaceLockCardDTO other = (LifeFaceLockCardDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
