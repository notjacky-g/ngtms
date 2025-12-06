/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.base.sms.shared;

import java.util.List;

/** @author yhleu */
public interface QuerySmsCallback {
  public void onQueryResponse(List<SmsQueryResponse> smsQueryResponseList);
}
