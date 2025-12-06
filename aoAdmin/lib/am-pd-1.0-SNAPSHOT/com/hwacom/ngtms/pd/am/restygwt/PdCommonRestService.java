package com.hwacom.ngtms.pd.am.restygwt;

import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.common.am.restygwt.AuthDispatcher;
import com.hwacom.ngtms.pd.shared.dto.LocationDTO;
import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
import com.hwacom.ngtms.pd.shared.dto.PdConfigDTO;
import com.hwacom.ngtms.pd.shared.dto.PdParametersDTO;
import com.hwacom.ngtms.pd.shared.dto.PdStatusDTO;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

@Path("/api/pd/common")
public interface PdCommonRestService extends RestService {

  @GET
  @Path("/allPdConfig")
  public void retrievePdConfig(MethodCallback<List<PdConfigDTO>> callback);

  /**
   * 檢查是否已有 PD 設備資訊
   *
   * @param deviceName
   * @param callback
   */
  @PUT
  @Path("/pdConfig/check")
  public void checkPdConfig(PdParametersDTO dto, MethodCallback<Boolean> callback);

  /**
   * 檢查欲刪除的 PD 設備迴路底下是否有設備
   *
   * @param deviceName
   * @param callback
   */
  @PUT
  @Path("/pdConfig/check/remove")
  public void checkRemovedPdConfig(PdParametersDTO dto, MethodCallback<Boolean> callback);

  /**
   * 新增 PD 設備資訊
   *
   * @param pdConfigDTO
   */
  @PUT
  @Path("/pdConfig")
  @Options(dispatcher = AuthDispatcher.class)
  public void savePdConfig(PdConfigDTO dto, MethodCallback<Void> callback);

  /**
   * 修改 PD 設備資訊
   *
   * @param pdConfigDTO
   */
  @POST
  @Path("/pdConfig")
  @Options(dispatcher = AuthDispatcher.class)
  public void updatePdConfig(PdConfigDTO dto, MethodCallback<Void> callback);

  /**
   * 刪除 PD 設備資訊
   *
   * @param pdConfigDTO
   */
  @DELETE
  @Path("/pdConfig")
  @Options(dispatcher = AuthDispatcher.class)
  public void deletePdConfig(PdParametersDTO dto, MethodCallback<Void> callback);

  @GET
  @Path("/roadLine")
  public void retrieveRoadLine(MethodCallback<List<RoadLineDTO>> callback);

  @GET
  @Path("/location")
  public void retrieveLocation(MethodCallback<List<LocationDTO>> callback);

  @GET
  @Path("/section")
  public void retrieveSection(MethodCallback<List<RoadSectionDTO>> callback);

  /**
   * 取得所選設備的 底下迴路設備組態
   *
   * @param deviceNames
   */
  @GET
  @Path("/loopDeviceConfig")
  public void retrieveLoopDeviceConfig(
      @QueryParam("deviceNames") List<String> deviceNames,
      MethodCallback<List<LoopDeviceConfigDTO>> callback);

  /** 取得尚未設定的 底下迴路設備組態 */
  @GET
  @Path("/loopDeviceConfig/unset")
  public void retrieveUnsetLoopDeviceConfig(MethodCallback<List<LoopDeviceConfigDTO>> callback);

  @POST
  @Path("/loopDeviceConfig/update")
  @Options(dispatcher = AuthDispatcher.class)
  public void updateLoopDeviceConfig(PdParametersDTO params, MethodCallback<Void> callback);

  /**
   * 修改 PD底下迴路設備資訊
   *
   * @param LoopDeviceConfigDTO
   */
  @POST
  @Path("/loopDeviceConfig")
  @Options(dispatcher = AuthDispatcher.class)
  public void updateLoopDeviceData(LoopDeviceConfigDTO dto, MethodCallback<Void> callback);

  /** 取得所有 PD點狀態 */
  @GET
  @Path("/pdStatus")
  public void retrievePdStatus(MethodCallback<List<PdStatusDTO>> callback);
}
