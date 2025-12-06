/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.user.client.ui.IsWidget;
import java.util.Map;

public interface RIPViewer extends IsWidget {

  /**
   * Get report query conditions according to different Widget.
   *
   * @return report query conditions
   */
  Map<String, Object> getInputParameter();

  /**
   * Used when UI is transfer to this report UI and needs to keep report query conditions from other
   * module.
   *
   * @param inputParameter
   */
  void setInputParameter(Map<String, Object> inputParameter);

  /**
   * Check if all report query conditions is valid.
   *
   * @return true or false
   */
  boolean isValid();

  /** Clear all report query conditions. */
  void clearInputParameter();

  void setHeading(String heading);
}
