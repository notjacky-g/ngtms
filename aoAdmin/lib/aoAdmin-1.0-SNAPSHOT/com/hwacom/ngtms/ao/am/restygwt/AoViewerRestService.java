package com.hwacom.ngtms.ao.am.restygwt;

import com.hwacom.ngtms.ao.shared.dto.PowerWaterStatusDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordQueryParamDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomAnalogTreeDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomDeviceParamDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomGroupDeviceStatusDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomInfoDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomNCUStatusDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomSetAndUnSetConfigDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomSvgGroupConifgDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
import com.hwacom.ngtms.common.am.restygwt.AuthDispatcher;
import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDoDTO;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

@Path("/api/ao")
public interface AoViewerRestService extends RestService {
  @GET
  @Path("/replayUrl")
  void getReplayUrl(MethodCallback<Map<String, String>> paramMethodCallback);
  
  @GET
  @Path("/roomAnalogTree")
  void getRoomAnalogTree(MethodCallback<List<RoomAnalogTreeDTO>> paramMethodCallback);
  
  @POST
  @Path("/roomAnalogRecord")
  void getRoomAnalogRecord(RoomAnalogRecordQueryParamDTO paramRoomAnalogRecordQueryParamDTO, MethodCallback<List<RoomAnalogRecordDTO>> paramMethodCallback);
  
  @GET
  @Path("/roomInfo")
  void getRoomInfo(MethodCallback<List<RoomInfoDTO>> paramMethodCallback);
  
  @GET
  @Path("/roomStatus")
  void getRoomStatus(MethodCallback<List<RoomInfoDTO>> paramMethodCallback);
  
  @GET
  @Path("/safeStatus")
  void getSafeStatus(MethodCallback<List<RoomInfoDTO>> paramMethodCallback);
  
  @GET
  @Path("/roomLine")
  void getRoomLineData(MethodCallback<List<String>> paramMethodCallback);
  
  @GET
  @Path("/roomGroupDeviceData")
  void getRoomGroupDeviceData(MethodCallback<RoomSetAndUnSetConfigDTO> paramMethodCallback);
  
  @POST
  @Path("/roomGroupDeviceData")
  @Options(dispatcher = AuthDispatcher.class)
  void saveRoomGroupDeviceData(List<RoomSvgGroupConifgDTO> paramList, MethodCallback<Boolean> paramMethodCallback);
  
  @GET
  @Path("/roomDevicePositionConfig/{svgName}")
  void queryDevicePositionConfig(@PathParam("svgName") String paramString, MethodCallback<List<DeviceSvgPositionConfigDTO>> paramMethodCallback);
  
  @POST
  @Path("/roomDeviceStatusConfig")
  void refreshRoomDeviceStatus(RoomDeviceParamDTO paramRoomDeviceParamDTO, MethodCallback<List<RoomDeviceStatusDTO>> paramMethodCallback);
  
  @GET
  @Path("/roomGoupDeviceStatus/{svgName}/{groupName:.+}")
  void getRoomGroupDeviceStatus(@PathParam("svgName") String paramString1, @PathParam("groupName") String paramString2, MethodCallback<List<RoomGroupDeviceStatusDTO>> paramMethodCallback);
  
  @POST
  @Path("/roomDevicePositionConfig")
  @Options(dispatcher = AuthDispatcher.class)
  void saveRoomDevicePositionConfigs(List<DeviceSvgPositionConfigDTO> paramList, MethodCallback<Boolean> paramMethodCallback);
  
  @GET
  @Path("/roomCctv/{backgroundId}")
  void getRoomCctvVideo(@PathParam("backgroundId") String paramString, MethodCallback<List<RoomCctvUrlDTO>> paramMethodCallback);
  
  @GET
  @Path("/roomSafeInfo/{backgroundId}")
  void getRoomSafeInfo(@PathParam("backgroundId") String paramString, MethodCallback<RoomNCUStatusDTO> paramMethodCallback);
  
  @PUT
  @Path("/roomDoOutput")
  @Options(dispatcher = AuthDispatcher.class)
  void updateRoomDoOutput(RoomDoDTO paramRoomDoDTO, MethodCallback<Boolean> paramMethodCallback);
  
  @GET
  @Path("/powerWater")
  void getPowerWaterStatusList(MethodCallback<PowerWaterStatusDTO> paramMethodCallback);
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\restygwt\AoViewerRestService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */