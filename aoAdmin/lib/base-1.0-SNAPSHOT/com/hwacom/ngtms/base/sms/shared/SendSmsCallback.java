/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.base.sms.shared;

import java.util.List;
import java.util.Map;

/** @author yhleu */
public interface SendSmsCallback {
  public void onSendResponse(Map<String, List<SmsSendResponse>> resultMap);
}
