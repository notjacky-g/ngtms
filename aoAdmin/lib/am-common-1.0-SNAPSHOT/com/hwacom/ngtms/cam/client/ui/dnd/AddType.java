/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.dnd;

import com.hwacom.ngtms.cam.client.ui.AmTabPanel;
import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;

/**
 * {@link RoadTreeViewer} DnD 的資料型態，以區別該 fire 哪種 event。
 *
 * @see AmTabPanel#setDndType(AddType)
 * @author monty.pan
 */
// XXX 如果子系統都實做完也沒有用到其他 AddType，就讓他消失吧...
public enum AddType {
  DeviceConfig,
  Other
}
