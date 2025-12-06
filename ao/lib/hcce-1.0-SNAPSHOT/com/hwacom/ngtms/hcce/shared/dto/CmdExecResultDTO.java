/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Arrays;

public class CmdExecResultDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -5122506289833059009L;
  private String[] stdOut;
  private String[] stdErr;
  private String exception;
  private Integer exitStatus;
  /** @return the stdOut */
  public String[] getStdOut() {
    return stdOut;
  }
  /** @param stdOut the stdOut to set */
  public void setStdOut(String[] stdOut) {
    this.stdOut = stdOut;
  }
  /** @return the stdErr */
  public String[] getStdErr() {
    return stdErr;
  }
  /** @param stdErr the stdErr to set */
  public void setStdErr(String[] stdErr) {
    this.stdErr = stdErr;
  }
  /** @return the exception */
  public String getException() {
    return exception;
  }
  /** @param exception the exception to set */
  public void setException(String exception) {
    this.exception = exception;
  }
  /** @return the exitStatus */
  public Integer getExitStatus() {
    return exitStatus;
  }
  /** @param exitStatus the exitStatus to set */
  public void setExitStatus(Integer exitStatus) {
    this.exitStatus = exitStatus;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CmdExecResultDTO [");
    if (stdOut != null) {
      builder.append("stdOut=");
      builder.append(Arrays.toString(stdOut));
      builder.append(", ");
    }
    if (stdErr != null) {
      builder.append("stdErr=");
      builder.append(Arrays.toString(stdErr));
      builder.append(", ");
    }
    if (exception != null) {
      builder.append("exception=");
      builder.append(exception);
      builder.append(", ");
    }
    if (exitStatus != null) {
      builder.append("exitStatus=");
      builder.append(exitStatus);
    }
    builder.append("]");
    return builder.toString();
  }
}
