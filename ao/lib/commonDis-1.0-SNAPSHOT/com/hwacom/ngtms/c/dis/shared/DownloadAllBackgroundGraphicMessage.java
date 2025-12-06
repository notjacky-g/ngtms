/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import com.hwacom.ngtms.c.shared.DeviceListMessage;

public interface DownloadAllBackgroundGraphicMessage extends DeviceListMessage {
  String TYPE = DownloadAllBackgroundGraphicMessage.class.getName();
}
