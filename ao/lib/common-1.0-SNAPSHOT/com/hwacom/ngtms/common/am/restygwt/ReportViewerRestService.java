package com.hwacom.ngtms.common.am.restygwt;

import com.hwacom.ngtms.common.shared.dto.ExportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.TextCallback;

@Path("/api/rpt")
public interface ReportViewerRestService extends RestService {

  @GET
  @Path("/retrieveReportTreeDTO/{module}")
  public void retrieveReportTreeDTO(
      @PathParam("module") String module, MethodCallback<List<ReportTreeDTO>> callback);

  @POST
  @Path("/previewReport")
  public void previewReport(PreviewReportDTO dto, MethodCallback<PreviewReportOutputDTO> callback);

  @POST
  @Path("/export")
  @Options(dispatcher = AuthDispatcher.class)
  public void export(ExportDTO dto, TextCallback callback);
}
