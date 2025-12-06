package com.hwacom.ngtms.room.am.restygwt;

import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.common.am.restygwt.AuthDispatcher;
import com.hwacom.ngtms.room.shared.dto.RoomBackgroundSvgConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
import com.hwacom.ngtms.room.shared.dto.RoomCardPermissionTreeDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardReaderLogDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardReaderMappingConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceLocationConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDoDTO;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

@Path("/api/room/common")
public interface RoomCommonRestService extends RestService {

  /** 取得定期卡群組 */
  @GET
  @Path("/roomCardGroupConfig")
  public void getRoomCardGroupConfig(MethodCallback<List<RoomCardGroupConfigDTO>> callbcak);

  /** 新增定期卡群組 */
  @POST
  @Path("/roomCardGroupConfig")
  public void createRoomCardGroupConfig(
      RoomCardGroupConfigDTO dto, MethodCallback<Boolean> callback);

  /** 修改定期卡群組 */
  @PUT
  @Path("/roomCardGroupConfig")
  public void updateRoomCardGroupConfig(
      RoomCardGroupConfigDTO dto, MethodCallback<Boolean> callback);

  /** 刪除定期卡群組 */
  @DELETE
  @Path("/roomCardGroupConfig/{id}")
  public void deleteRoomCardGroupConfig(@PathParam("id") Long id, MethodCallback<Boolean> callback);

  /** 取得 svg 底圖資訊 */
  @GET
  @Path("/roomBackgroundSvgConfig")
  public void getBackgroundSvgConfig(MethodCallback<List<RoomBackgroundSvgConfigDTO>> callback);

  /**
   * 取得 機房設備位置設定
   *
   * @param svgName 機房底圖名稱
   */
  @GET
  @Path("/roomDevicePositionConfig/{svgName}")
  public void queryDevicePositionConfig(
      @PathParam("svgName") String svgName,
      MethodCallback<List<DeviceSvgPositionConfigDTO>> callback);

  /**
   * 儲存 svg 圖設備位置
   *
   * @param list 設備位置列表
   */
  @POST
  @Path("/roomDevicePositionConfig")
  @Options(dispatcher = AuthDispatcher.class)
  public void saveRoomDevicePositionConfigs(
      List<DeviceSvgPositionConfigDTO> position, MethodCallback<Boolean> callback);

  /** 取得 機房資訊 */
  @GET
  @Path("/roomDeviceLocationConfig")
  public void getRoomDeviceLocationConfigData(
      MethodCallback<List<RoomDeviceLocationConfigDTO>> callback);

  /**
   * 從機房位置找出對應的底圖
   *
   * @param hostId DeviceHostLocation的Id
   * @param subLocationName RoomDeviceLocationConfig的位置
   */
  @GET
  @Path("/roomDeviceLocationConfig/{hostId}/{subLocationName}")
  public void getRoomLocationMapConfig(
      @PathParam("hostId") Integer hostId,
      @PathParam("subLocationName") String subLocationName,
      MethodCallback<RoomBackgroundSvgConfigDTO> callback);

  /**
   * 更新設備狀態
   *
   * @param List<String> deviceNameList
   */
  @GET
  @Path("/roomDeviceStatusConfig/{deviceNameList}")
  public void refreshRoomDeviceStatus(
      @QueryParam("deviceNameList") List<String> deviceNameList,
      MethodCallback<List<RoomDeviceStatusDTO>> callback);

  /** 取得 機房閉路電視Url */
  @GET
  @Path("/roomCctvUrl")
  public void getRoomCctvUrl(MethodCallback<String> callback);

  /** 取得卡片資料 */
  @GET
  @Path("/roomCardConfig")
  public void getRoomCardConfig(MethodCallback<List<RoomCardConfigDTO>> callback);

  /** 新增卡片(未發卡) */
  @POST
  @Path("/roomCardConfig")
  public void createRoomCardConfig(RoomCardConfigDTO dto, MethodCallback<Boolean> callback);

  /** 修改卡片資料 (傳至卡機) */
  @PUT
  @Path("/roomCardConfig")
  @Options(dispatcher = AuthDispatcher.class)
  public void updateRoomCardConfig(RoomCardIssueParam param, MethodCallback<String> callback);

  /** 卡片發卡(只存資料庫) */
  @POST
  @Path("/issueRoomCard")
  @Options(dispatcher = AuthDispatcher.class)
  public void issueRoomCard(RoomCardIssueParam param, MethodCallback<Boolean> callback);

  @POST
  @Path("/issueRoomCardImmediately")
  @Options(dispatcher = AuthDispatcher.class)
  public void issueRoomCardImmediately(RoomCardIssueParam param, MethodCallback<String> callback);

  /** 取得 機房 各卡機位置資訊 */
  @GET
  @Path("/cardReaderLocation")
  public void getCardReaderLocation(MethodCallback<List<RoomCardPermissionTreeDTO>> callback);

  /** 取得該卡片所有註冊的卡機 */
  @GET
  @Path("/roomCardReaderMappingConfig/{aba}")
  public void getRoomCardReaderMappingConfig(
      @PathParam("aba") String aba, MethodCallback<List<RoomCardReaderMappingConfigDTO>> callback);

  /** 取得 機房資訊 */
  @GET
  @Path("/roomDeviceSubLocationConfig")
  public void getRoomDeviceSubLocationConfigData(
      MethodCallback<List<RoomDeviceSubLocationConfigDTO>> callback);

  /**
   * 取得機房內卡機開/關門狀態
   *
   * @param locationName
   * @param subLocationName RoomDeviceLocationConfig的位置
   */
  @GET
  @Path("/roomCardReaderLog/{locationName}/{subLocationName}")
  public void getCardReaderData(
      @PathParam("locationName") String locName,
      @PathParam("subLocationName") String subLocationName,
      MethodCallback<List<RoomCardReaderLogDTO>> callback);

  /** 指令卡機開門 */
  @POST
  @Path("/doorStatus/{deviceName}")
  public void changeDoorOpen(
      @PathParam("deviceName") String deviceName, MethodCallback<Boolean> callback);

  @GET
  @Path("/roomDeviceConfig/{locationName}/{subLocationName}")
  public void getRoomDeviceConfig(
      @PathParam("locationName") String locName,
      @PathParam("subLocationName") String subLocationName,
      MethodCallback<List<RoomDeviceConfigDTO>> callback);

  /** 取得機房內設備(除卡機) */
  @GET
  @Path("/roomDeviceType")
  public void getRoomDeviceType(MethodCallback<List<DeviceTypeDTO>> callback);

  /**
   * 取得機房內卡機開/關門狀態
   *
   * @param dto (roomDeviceConfigDTO)
   */
  @POST
  @Path("/roomDeviceConfig/update")
  @Options(dispatcher = AuthDispatcher.class)
  public void updateRoomDeviceConfig(RoomDeviceConfigDTO dto, MethodCallback<Boolean> callback);

  @GET
  @Path("/roomDeviceSubLocationConfig/query/{deviceName}")
  public void querySelectedRoomSubLocation(
      @PathParam("deviceName") String deviceName, MethodCallback<List<String>> callback);

  @GET
  @Path("/cctvUrl/{locationName}/{subLocation}")
  public void getRoomCctvUrls(
      @PathParam("locationName") String locationName,
      @PathParam("subLocation") String subLocation,
      MethodCallback<List<RoomCctvUrlDTO>> callback);

  /**
   * DO 點位控制
   *
   * @param dto
   */
  @PUT
  @Path("/doDevice")
  @Options(dispatcher = AuthDispatcher.class)
  public void updateDoDevice(RoomDoDTO dto, MethodCallback<Boolean> callback);
}
