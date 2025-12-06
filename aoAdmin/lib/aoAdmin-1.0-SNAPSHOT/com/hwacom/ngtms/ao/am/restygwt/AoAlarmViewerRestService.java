package com.hwacom.ngtms.ao.am.restygwt;

import com.hwacom.ngtms.ao.shared.dto.AlarmLogPageLoadDTO;
import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
import com.hwacom.ngtms.ao.shared.dto.AlarmRecordQueryParamDTO;
import com.hwacom.ngtms.ao.shared.dto.AlarmSendMessageDTO;
import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
import com.hwacom.ngtms.common.am.restygwt.AuthDispatcher;
import com.hwacom.ngtms.common.shared.dto.RoleDTO;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

@Path("/api/smg/ao/alarm")
public interface AoAlarmViewerRestService extends RestService {
  @GET
  @Path("/user")
  void getUsers(MethodCallback<List<UserDTO>> paramMethodCallback);
  
  @GET
  @Path("/role")
  void getRoles(MethodCallback<List<RoleDTO>> paramMethodCallback);
  
  @GET
  @Path("/allTypes")
  void getAllAlarmType(MethodCallback<List<AlarmTypeDTO>> paramMethodCallback);
  
  @GET
  @Path("/allNonRtn")
  void getAllNonRtnAlarms(MethodCallback<List<AlarmMessageDTO>> paramMethodCallback);
  
  @POST
  @Path("/confirm")
  @Options(dispatcher = AuthDispatcher.class)
  void confirmAlarm(AlarmMessageDTO paramAlarmMessageDTO, MethodCallback<String> paramMethodCallback);
  
  @POST
  @Path("/alarmLogPageLoad")
  void getAlarmLogWithParamPageLoad(AlarmRecordQueryParamDTO paramAlarmRecordQueryParamDTO, MethodCallback<AlarmLogPageLoadDTO> paramMethodCallback);
  
  @POST
  @Path("/alarmLog/param")
  void getAlarmLogWithParam(AlarmRecordQueryParamDTO paramAlarmRecordQueryParamDTO, MethodCallback<List<AlarmMessageDTO>> paramMethodCallback);
  
  @POST
  @Path("/alarmSendMessage")
  void alarmSendMessage(AlarmSendMessageDTO paramAlarmSendMessageDTO, MethodCallback<Void> paramMethodCallback);
  
  @GET
  @Path("/alarmSendVoice")
  void getPlayAlarmMp3(MethodCallback<Void> paramMethodCallback);
  
  @GET
  @Path("/alarmStopVoice")
  void getStopAlarmMp3(MethodCallback<Void> paramMethodCallback);
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\restygwt\AoAlarmViewerRestService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */