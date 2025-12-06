/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ReportViewerEvent
    extends GwtEvent<ReportViewerEvent.ReportViewerEventHandler> {

  public static final Type<ReportViewerEventHandler> TYPE =
      new Type<ReportViewerEventHandler>();

  public enum Action {
    SELECT_REPORT,
    EXPORT_CSV,
    EXPORT_PDF,
    EXPORT_XLS,
    EXPORT_PNG,
    EXPORT_JPEG
  }

  private final Action action;

  public ReportViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<ReportViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ReportViewerEventHandler handler) {
    if (action == Action.SELECT_REPORT) {
      handler.onSelectReport(this);
    } else if (action == Action.EXPORT_CSV) {
      handler.onExportCsv(this);
    } else if (action == Action.EXPORT_PDF) {
      handler.onExportPdf(this);
    } else if (action == Action.EXPORT_XLS) {
      handler.onExportXls(this);
    } else if (action == Action.EXPORT_PNG) {
      handler.onExportPng(this);
    } else if (action == Action.EXPORT_JPEG) {
      handler.onExportJpeg(this);
    }
  }

  public interface ReportViewerEventHandler extends EventHandler {

    void onSelectReport(ReportViewerEvent event);
    void onExportCsv(ReportViewerEvent event);
    void onExportPdf(ReportViewerEvent event);
    void onExportXls(ReportViewerEvent event);
    void onExportPng(ReportViewerEvent event);
    void onExportJpeg(ReportViewerEvent event);
  }
}