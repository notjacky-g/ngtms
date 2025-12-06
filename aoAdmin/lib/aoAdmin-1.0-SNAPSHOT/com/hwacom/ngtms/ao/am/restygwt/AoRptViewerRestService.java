package com.hwacom.ngtms.ao.am.restygwt;

import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
import com.hwacom.ngtms.ao.shared.dto.FieldDTO;
import com.hwacom.ngtms.ao.shared.dto.OperatorDTO;
import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
import com.hwacom.ngtms.ao.shared.dto.ReportConfigBaseDTO;
import com.hwacom.ngtms.ao.shared.dto.ReportPreviewInfoDTO;
import com.hwacom.ngtms.ao.shared.dto.RptParametersDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewDataDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewGridDataDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewHeaderDTO;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("/api/rpt/ao")
public interface AoRptViewerRestService extends RestService {
  @GET
  @Path("/abnormalStatus/{deviceType}")
  void fetchHardwareStatuesByDeviceType(@PathParam("deviceType") String paramString, MethodCallback<List<FieldDTO>> paramMethodCallback);
  
  @GET
  @Path("/roadLine")
  void findPaths(MethodCallback<List<RoadLineDTO>> paramMethodCallback);
  
  @GET
  @Path("/location")
  void retrieveLocation(MethodCallback<List<PdLocationDTO>> paramMethodCallback);
  
  @GET
  @Path("/allTypes")
  void getAllAlarmType(MethodCallback<List<AlarmTypeDTO>> paramMethodCallback);
  
  @GET
  @Path("/deviceType/{category}")
  void fetchDeviceTypeByCategory(@PathParam("category") String paramString, MethodCallback<List<DeviceTypeDTO>> paramMethodCallback);
  
  @POST
  @Path("/fetchHardwareStatuesByDeviceType")
  void fetchHardwareStatuesByDeviceType(RptParametersDTO paramRptParametersDTO, MethodCallback<List<FieldDTO>> paramMethodCallback);
  
  @GET
  @Path("/fetchSysOperators")
  void fetchSysOperators(MethodCallback<List<OperatorDTO>> paramMethodCallback);
  
  @GET
  @Path("/retrievePrinterList")
  void retrievePrinterList(MethodCallback<List<String>> paramMethodCallback);
  
  @POST
  @Path("/fetchQueryData")
  void fetchQueryData(RptParametersDTO paramRptParametersDTO, MethodCallback<ReportPreviewInfoDTO> paramMethodCallback);
  
  @GET
  @Path("/fetchReportConfigData")
  void fetchReportConfigData(MethodCallback<List<ReportConfigBaseDTO>> paramMethodCallback);
  
  @POST
  @Path("/downloadChart")
  void downloadChart(RptParametersDTO paramRptParametersDTO, MethodCallback<String> paramMethodCallback);
  
  @POST
  @Path("/downloadReport")
  void downloadReport(RptParametersDTO paramRptParametersDTO, MethodCallback<String> paramMethodCallback);
  
  @POST
  @Path("/printReport")
  void printReport(RptParametersDTO paramRptParametersDTO, MethodCallback<Void> paramMethodCallback);
  
  @POST
  @Path("/fetchPreviewHeader")
  void fetchPreviewHeader(RptParametersDTO paramRptParametersDTO, MethodCallback<Map<String, PreviewHeaderDTO>> paramMethodCallback);
  
  @POST
  @Path("/query")
  void query(RptParametersDTO paramRptParametersDTO, MethodCallback<List<PreviewDataDTO>> paramMethodCallback);
  
  @POST
  @Path("/queryWithHeader")
  void queryWithHeader(RptParametersDTO paramRptParametersDTO, MethodCallback<PreviewGridDataDTO> paramMethodCallback);
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\restygwt\AoRptViewerRestService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */