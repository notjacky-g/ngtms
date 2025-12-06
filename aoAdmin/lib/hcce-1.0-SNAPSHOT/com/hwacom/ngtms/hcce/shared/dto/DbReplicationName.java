/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/** 這是指資料庫裏使用 showhealth 取資料的資料庫名稱 現在有三種 OLDB,BKDB */
public enum DbReplicationName implements IsSerializable {
  OLDB,
  HDDB,
  BKDB;
}
