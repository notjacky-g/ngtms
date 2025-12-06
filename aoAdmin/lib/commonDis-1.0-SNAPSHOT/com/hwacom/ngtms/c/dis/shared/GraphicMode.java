/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

public enum GraphicMode {

  /** 1：圖型模式 0：全彩圖 - 都會網狀態 */
  METRO_AREA_POLYGON,
  /** 2：全彩圖 - 改道資訊 */
  ROUTE_GUIDANCE,
  /** 全彩圖 - 2*2圖示 */
  ICON,
  /** 3：新造圖 */
  NEW_GRAPHIC_PATTERN,
  /** R22資料庫內未歸類的檔案 */
  OTHER;
}
