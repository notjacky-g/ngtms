/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared;

import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
import java.io.Serializable;
import java.util.List;

public class FmeDefTable implements Serializable {

  private static final long serialVersionUID = -8449136229080265509L;

  private List<FmeDefinition> definitions;
  private Long issueVersion;

  public List<FmeDefinition> getDefinitions() {
    return definitions;
  }

  public void setDefinitions(List<FmeDefinition> definitions) {
    this.definitions = definitions;
  }

  public Long getIssueVersion() {
    return issueVersion;
  }

  public void setIssueVersion(Long issueVersion) {
    this.issueVersion = issueVersion;
  }

  @Override
  public String toString() {
    return "FmeDefTable [issueVersion=" + issueVersion + ", definitions=" + definitions + "]";
  }
}
