/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.node.performance.am.view;

import com.hwacom.ngtms.hcce.shared.dto.NodeDTO;
import com.hwacom.ngtms.node.performance.am.presenter.HistoryDataPresenter;
import java.util.Date;
import java.util.List;

public interface HistoryDataViewer {

  void setPresenter(HistoryDataPresenter presenter);

  Date getStartDate();

  Date getEndDate();

  String getGroupName();

  String getNodeName();

  void updateNodeNames(List<NodeDTO> result);
}
