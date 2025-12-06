package com.hwacom.ngtms.ao.am.restygwt;

import com.hwacom.ngtms.ao.shared.dto.LifeFaceLockCardDTO;
import com.hwacom.ngtms.ao.shared.dto.NcuConfigDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomCardReaderLogQueryParamDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardLogDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomNCUMessageDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomNCUStatusDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
import com.hwacom.ngtms.ao.shared.dto.RoomPermissionDTO;
import com.hwacom.ngtms.common.am.restygwt.AuthDispatcher;
import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

@Path("/api/ao/ncu")
public interface AoNcuViewerRestService extends RestService {
  @GET
  @Path("/cardReaderStatus")
  void getCardReaderStatus(MethodCallback<List<RoomNCUStatusDTO>> paramMethodCallback);
  
  @GET
  @Path("/readDoor/{deviceName:.+}")
  void getDoorStatus(@PathParam("deviceName") String paramString, MethodCallback<Boolean> paramMethodCallback);
  
  @PUT
  @Path("/openDoor/{deviceName:.+}")
  @Options(dispatcher = AuthDispatcher.class)
  void openDoor(@PathParam("deviceName") @Attribute("id") RoomNCUStatusDTO paramRoomNCUStatusDTO, MethodCallback<Boolean> paramMethodCallback);
  
  @GET
  @Path("/configData")
  void getNcuConfig(MethodCallback<List<NcuConfigDTO>> paramMethodCallback);
  
  @POST
  @Path("/configData")
  @Options(dispatcher = AuthDispatcher.class)
  void addNcuConfig(NcuConfigDTO paramNcuConfigDTO, MethodCallback<Boolean> paramMethodCallback);
  
  @PUT
  @Path("/configData")
  @Options(dispatcher = AuthDispatcher.class)
  void updateNcuConfig(NcuConfigDTO paramNcuConfigDTO, MethodCallback<Boolean> paramMethodCallback);
  
  @DELETE
  @Path("/configData/{deviceName:.+}")
  @Options(dispatcher = AuthDispatcher.class)
  void deleteNcuConfig(@PathParam("deviceName") String paramString, MethodCallback<Boolean> paramMethodCallback);
  
  @GET
  @Path("/ncuTreeData")
  void getNcuTreeData(MethodCallback<List<NcuConfigDTO>> paramMethodCallback);
  
  @POST
  @Path("/roomCardConfig")
  void createRoomCardConfig(RoomCardConfigDTO paramRoomCardConfigDTO, MethodCallback<Boolean> paramMethodCallback);
  
  @POST
  @Path("/cardConfig")
  @Options(dispatcher = AuthDispatcher.class)
  void saveCardConfig(RoomCardIssueParam paramRoomCardIssueParam, MethodCallback<Boolean> paramMethodCallback);
  
  @POST
  @Path("/cardPermission")
  @Options(dispatcher = AuthDispatcher.class)
  void addCardPermission(RoomNCUCardDTO paramRoomNCUCardDTO, MethodCallback<RoomNCUMessageDTO> paramMethodCallback);
  
  @GET
  @Path("/ncu")
  void getNcu(MethodCallback<List<RoomNcuDeviceNameDTO>> paramMethodCallback);
  
  @GET
  @Path("/nculocation/{deviceName:.+}")
  void getlocationByNcu(@PathParam("deviceName") String paramString, MethodCallback<List<RoomNcuDeviceNameDTO>> paramMethodCallback);
  
  @POST
  @Path("/ncuCardReaderLog")
  @Options(dispatcher = AuthDispatcher.class)
  void getNcuCardReaderLog(RoomCardReaderLogQueryParamDTO paramRoomCardReaderLogQueryParamDTO, MethodCallback<List<RoomNCUCardLogDTO>> paramMethodCallback);
  
  @GET
  @Path("/lockCard")
  void getAllLockCards(MethodCallback<List<LifeFaceLockCardDTO>> paramMethodCallback);
  
  @POST
  @Path("/lockCard")
  @Options(dispatcher = AuthDispatcher.class)
  void getLockCards(RoomCardReaderLogQueryParamDTO paramRoomCardReaderLogQueryParamDTO, MethodCallback<List<LifeFaceLockCardDTO>> paramMethodCallback);
  
  @PUT
  @Path("/lockCard")
  @Options(dispatcher = AuthDispatcher.class)
  void setLockCard(LifeFaceLockCardDTO paramLifeFaceLockCardDTO, MethodCallback<Boolean> paramMethodCallback);
  
  @DELETE
  @Path("/lockCard")
  @Options(dispatcher = AuthDispatcher.class)
  void setUnLockCard(LifeFaceLockCardDTO paramLifeFaceLockCardDTO, MethodCallback<Boolean> paramMethodCallback);
  
  @GET
  @Path("/permission/{deviceName:.+}")
  void getOneRoomPermissin(@PathParam("deviceName") String paramString, MethodCallback<List<RoomPermissionDTO>> paramMethodCallback);
  
  @GET
  @Path("/permissions")
  void getAllRoomPermissin(MethodCallback<List<RoomPermissionDTO>> paramMethodCallback);
  
  @GET
  @Path("/dataTimeNow/{deviceName:.+}")
  void getNcuHostDataTimeNow(@PathParam("deviceName") String paramString, MethodCallback<NcuConfigDTO> paramMethodCallback);
  
  @PUT
  @Path("/synchronizeTime/{deviceName:.+}")
  @Options(dispatcher = AuthDispatcher.class)
  void synchronizeNcuHostTime(@PathParam("deviceName") String paramString, MethodCallback<Boolean> paramMethodCallback);
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\restygwt\AoNcuViewerRestService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */