package com.hwacom.ngtms.common.am;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.restygwt.ReportViewerRestService;
import com.hwacom.ngtms.common.am.view.Messages;
import com.hwacom.ngtms.common.am.view.ReportViewer;

public class ReportEP extends AmEntryPoint {

  public static final Messages messages = GWT.create(Messages.class);

  public static ReportViewerRestService service = GWT.create(ReportViewerRestService.class);

  public ReportEP() {
    super(messages.rpt(), service);
  }

  @Override
  protected void allServicesReady() {
    init(new ReportViewer(null));
  }
}
