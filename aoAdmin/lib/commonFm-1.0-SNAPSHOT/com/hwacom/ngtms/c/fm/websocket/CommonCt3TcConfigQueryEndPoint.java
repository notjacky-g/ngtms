/*     */ package com.hwacom.ngtms.c.fm.websocket;
/*     */ 
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcHardwareStatus;
/*     */ import com.hwacom.ngtms.c.fm.service.CommonCt3TcService;
/*     */ import com.hwacom.ngtms.c.fm.service.DeviceStatusService;
/*     */ import com.hwacom.ngtms.c.shared.CommonCt3QueryMessage;
/*     */ import com.hwacom.ngtms.c.shared.DataMessage;
/*     */ import com.hwacom.ngtms.c.shared.HardwareTransmissionPeriod;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.c.shared.TcProtocolType;
/*     */ import com.hwacom.ngtms.c.shared.WebSocketCloseReason;
/*     */ import com.hwacom.ngtms.c.util.Fw2Utils;
/*     */ import com.hwacom.ngtms.common.websocket.SpringConfigurator;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.CommRestartAndTestRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetEquipmentNoRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetReportHwStatusCycleRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetTimeRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetVerRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.QueryLockDbRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.TimePm;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import javax.json.Json;
/*     */ import javax.json.JsonArray;
/*     */ import javax.json.JsonException;
/*     */ import javax.json.JsonObject;
/*     */ import javax.json.JsonString;
/*     */ import javax.json.JsonWriter;
/*     */ import javax.websocket.CloseReason;
/*     */ import javax.websocket.DecodeException;
/*     */ import javax.websocket.Decoder;
/*     */ import javax.websocket.EncodeException;
/*     */ import javax.websocket.Encoder;
/*     */ import javax.websocket.EndpointConfig;
/*     */ import javax.websocket.OnClose;
/*     */ import javax.websocket.OnError;
/*     */ import javax.websocket.OnMessage;
/*     */ import javax.websocket.OnOpen;
/*     */ import javax.websocket.Session;
/*     */ import javax.websocket.server.ServerEndpoint;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ServerEndpoint(value = "/websocket/ct3/common/tcConfigQuery", configurator = SpringConfigurator.class, decoders = {CommonCt3TcConfigQueryEndPoint.MessageDecoder.class}, encoders = {CommonCt3TcConfigQueryEndPoint.MessageEncoder.class})
/*     */ @Service
/*     */ public class CommonCt3TcConfigQueryEndPoint
/*     */   extends AbstractServerEndpoint
/*     */ {
/*     */   private int commandCount;
/*     */   @Autowired
/*     */   private CommonCt3TcService commonTcService;
/*     */   @Autowired
/*     */   private DeviceStatusService deviceStatusService;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*  84 */   private static final Logger logger = LoggerFactory.getLogger(CommonCt3TcConfigQueryEndPoint.class);
/*     */   
/*     */   @OnOpen
/*     */   public void start(Session session, EndpointConfig config) {
/*  88 */     init(session, config, OperationItem.GET, (SubSystem)null);
/*  89 */     logger.info("Common ct3 tc config query Websocket Session:'{}' Started...", session.getId());
/*     */   }
/*     */   
/*     */   @OnMessage
/*     */   public void query(CommonCt3QueryMessage message, Session session) {
/*  94 */     logger.debug("Common ct3 tc config query message");
/*  95 */     String deviceType = message.getDeviceType();
/*  96 */     setSubSystem(SubSystem.valueOf(deviceType));
/*  97 */     List<String> deviceNames = message.getDeviceNames();
/*  98 */     this.commandCount = message.getMappings().size();
/*     */     
/* 100 */     if (message.getMappings().contains(Mapping.DEVICE_NUMBER.toString())) {
/*     */       try {
/* 102 */         this.commonTcService.queryCt3EquipmentNumber(deviceNames, message
/*     */             
/* 104 */             .getEquipmentNo().intValue(), new Callback(deviceNames, "common.ct3.tc.configQuery.deviceNumber", tcResponse -> {
/*     */                 Map<String, String> map = new HashMap<>();
/*     */                 
/*     */                 GetEquipmentNoRspPm pm = (GetEquipmentNoRspPm)tcResponse.getCmdBindingObj();
/*     */                 
/*     */                 Integer eqIdNunber = Integer.valueOf(0);
/*     */                 
/*     */                 if (pm.equipmentDataList != null && pm.equipmentDataList.size() > 0) {
/*     */                   GetEquipmentNoRspPm.EquipmentDataListItem item = pm.equipmentDataList.get(0);
/*     */                   
/*     */                   eqIdNunber = Integer.valueOf(item.equipmentId);
/*     */                 } 
/*     */                 map.put("deviceNumber", Fw2Utils.byte2VersionNoString(eqIdNunber.intValue()));
/*     */                 return map;
/*     */               }));
/* 119 */       } catch (RuntimeException e) {
/* 120 */         logger.warn("Query deviceNumber failed! deviceNames:{}", deviceNames, e);
/* 121 */         offerExceptionLog(deviceNames, e, this.messageSourceExt
/* 122 */             .getMessage("common.ct3.tc.configQuery.deviceNumber"));
/* 123 */         countDown();
/*     */       } 
/*     */     }
/*     */     
/* 127 */     if (message.getMappings().contains(Mapping.HARDWARE.toString())) {
/*     */       try {
/* 129 */         this.commonTcService.queryCt3CommRestartAndTest(deviceNames, new Callback(deviceNames, "common.ct3.tc.configQuery.hardware", tcResponse -> {
/*     */                 CommRestartAndTestRspPm pm = (CommRestartAndTestRspPm)tcResponse.getCmdBindingObj();
/*     */ 
/*     */ 
/*     */                 
/*     */                 List<DeviceTcHardwareStatus> abnormalList = this.deviceStatusService.getCt3AbnormalHwByGetHwStatusRspPm(pm, paramCommonCt3QueryMessage.getDeviceType());
/*     */ 
/*     */ 
/*     */                 
/*     */                 List<DeviceTcHardwareStatus> hardwareStatusList = this.deviceStatusService.getHardwareStatusList(paramCommonCt3QueryMessage.getDeviceType(), TcProtocolType.CT3);
/*     */ 
/*     */                 
/*     */                 Map<String, String> map = new HashMap<>();
/*     */ 
/*     */                 
/*     */                 for (DeviceTcHardwareStatus config : hardwareStatusList) {
/*     */                   map.put(config.getDescription(), Boolean.toString(abnormalList.contains(config)));
/*     */                 }
/*     */ 
/*     */                 
/*     */                 return map;
/*     */               }));
/* 151 */       } catch (RuntimeException e) {
/* 152 */         logger.warn("Query hardware info failed! deviceNames:{}", deviceNames, e);
/* 153 */         offerExceptionLog(deviceNames, e, this.messageSourceExt
/* 154 */             .getMessage("common.ct3.tc.configQuery.hardware"));
/* 155 */         countDown();
/*     */       } 
/*     */     }
/*     */     
/* 159 */     if (message.getMappings().contains(Mapping.DEVICE_TIME.toString())) {
/*     */       try {
/* 161 */         this.commonTcService.queryCt3Time(deviceNames, new Callback(deviceNames, "common.ct3.tc.configQuery.deviceTime", tcResponse -> {
/*     */                 GetTimeRspPm pm = (GetTimeRspPm)tcResponse.getCmdBindingObj();
/*     */                 
/*     */                 TimePm timePm = pm.timePm;
/*     */                 
/*     */                 Calendar calendar = Calendar.getInstance();
/*     */                 
/*     */                 calendar.set(1, timePm.year + 1911);
/*     */                 
/*     */                 calendar.set(2, timePm.month - 1);
/*     */                 
/*     */                 calendar.set(5, timePm.day);
/*     */                 
/*     */                 calendar.set(11, timePm.hour);
/*     */                 
/*     */                 calendar.set(12, timePm.min);
/*     */                 calendar.set(13, timePm.sec);
/*     */                 return Collections.singletonMap("deviceTime", Long.toString(calendar.getTime().getTime()));
/*     */               }));
/* 180 */       } catch (RuntimeException e) {
/* 181 */         logger.warn("Query deviceTime info failed! deviceNames:{}", deviceNames, e);
/* 182 */         offerExceptionLog(deviceNames, e, "common.ct3.tc.configQuery.deviceTime");
/* 183 */         countDown();
/*     */       } 
/*     */     }
/*     */     
/* 187 */     if (message.getMappings().contains(Mapping.FIRMWARE.toString())) {
/*     */       try {
/* 189 */         this.commonTcService.queryCt3Ver(deviceNames, new Callback(deviceNames, "common.ct3.tc.configQuery.firmware", tcResponse -> {
/*     */                 Map<String, String> map = new HashMap<>();
/*     */                 
/*     */                 GetVerRspPm pm = (GetVerRspPm)tcResponse.getCmdBindingObj();
/*     */                 
/*     */                 map.put("version", Fw2Utils.byte2VersionNoString(pm.version));
/*     */                 
/*     */                 map.put("grade", Integer.toString(pm.commandSet));
/*     */                 
/*     */                 Calendar calendar = Calendar.getInstance();
/*     */                 
/*     */                 calendar.set(1, pm.year + 1911);
/*     */                 calendar.set(2, pm.month - 1);
/*     */                 calendar.set(5, pm.day);
/*     */                 map.put("date", Long.toString(calendar.getTime().getTime()));
/*     */                 return map;
/*     */               }));
/* 206 */       } catch (RuntimeException e) {
/* 207 */         logger.warn("Retrieve firmware info failed! dleviceNames:{}", deviceNames, e);
/* 208 */         offerExceptionLog(deviceNames, e, "common.ct3.tc.configQuery.firmware");
/* 209 */         countDown();
/*     */       } 
/*     */     }
/*     */     
/* 213 */     if (message.getMappings().contains(Mapping.TRANSMISSION_PERIOD.toString())) {
/*     */       try {
/* 215 */         this.commonTcService.queryCt3ReportHwStatusCycle(deviceNames, new Callback(deviceNames, "common.ct3.tc.configQuery.transmissionPeriod", tcResponse -> {
/*     */                 GetReportHwStatusCycleRspPm pm = (GetReportHwStatusCycleRspPm)tcResponse.getCmdBindingObj();
/*     */ 
/*     */ 
/*     */                 
/*     */                 Map<String, String> map = new HashMap<>();
/*     */ 
/*     */ 
/*     */                 
/*     */                 if (pm != null) {
/*     */                   map.put("hardwareTransmissionPeriod", transferIntToCt3HardwareTransmissionPeriod(pm.hardwareCycle).toString());
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 return map;
/*     */               }));
/* 232 */       } catch (RuntimeException e) {
/* 233 */         logger.warn("Retrieve transmisssion period failed! dleviceNames:{}", deviceNames, e);
/* 234 */         offerExceptionLog(deviceNames, e, this.messageSourceExt
/* 235 */             .getMessage("common.ct3.tc.transmit.period"));
/* 236 */         countDown();
/*     */       } 
/*     */     }
/*     */     
/* 240 */     if (message.getMappings().contains(Mapping.OPERATION_MODE.toString())) {
/*     */       try {
/* 242 */         this.commonTcService.queryCt3LockDb(deviceNames, new Callback(deviceNames, "common.ct3.tc.configQuery.lockDb", tcResponse -> {
/*     */                 QueryLockDbRspPm pm = (QueryLockDbRspPm)tcResponse.getCmdBindingObj();
/*     */ 
/*     */                 
/*     */                 Map<String, String> map = new HashMap<>();
/*     */ 
/*     */                 
/*     */                 if (pm != null) {
/*     */                   map.put("operationMode", String.valueOf(pm.lockDb));
/*     */                 }
/*     */ 
/*     */                 
/*     */                 return map;
/*     */               }));
/* 256 */       } catch (RuntimeException e) {
/* 257 */         logger.warn("Retrieve transmisssion period failed! dleviceNames:{}", deviceNames, e);
/* 258 */         offerExceptionLog(deviceNames, e, this.messageSourceExt
/* 259 */             .getMessage("common.ct3.tc.transmit.period"));
/* 260 */         countDown();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static HardwareTransmissionPeriod transferIntToCt3HardwareTransmissionPeriod(int period) {
/* 266 */     switch (period) {
/*     */       case 0:
/* 268 */         return HardwareTransmissionPeriod.STOP;
/*     */       case 1:
/* 270 */         return HardwareTransmissionPeriod.ONE_SECOND;
/*     */       case 2:
/* 272 */         return HardwareTransmissionPeriod.TWO_SECONDS;
/*     */       case 3:
/* 274 */         return HardwareTransmissionPeriod.FIVE_SECONDS;
/*     */       case 4:
/* 276 */         return HardwareTransmissionPeriod.ONE_MINUTE;
/*     */       case 5:
/* 278 */         return HardwareTransmissionPeriod.FIVE_MINUTE;
/*     */     } 
/* 280 */     throw new RuntimeException("Unknow transmissionPeriod:" + period);
/*     */   }
/*     */ 
/*     */   
/*     */   private void close(int closeCode) {
/*     */     try {
/* 286 */       this.session.close(new CloseReason(() -> paramInt, ""));
/* 287 */     } catch (IOException e) {
/* 288 */       logger.warn("Close websocket Session:'{}' failed!", this.session.getId(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @OnClose
/*     */   public void end(Session session, CloseReason closeReason) {
/* 294 */     logger.info("Websocket Session:'{}' Ended..., reason:'{}'", session.getId(), closeReason);
/*     */   }
/*     */   
/*     */   @OnError
/*     */   public void error(Session session, Throwable t) {
/* 299 */     logger.warn("Websocket Session:'{}' Error!", session.getId(), t);
/*     */   }
/*     */ 
/*     */   
/*     */   private class Callback
/*     */     implements BiConsumer<String, TcResponse>
/*     */   {
/*     */     private int count;
/*     */     
/*     */     private String messageId;
/*     */     
/*     */     private Function<TcResponse, Map<String, String>> responseExtractor;
/*     */ 
/*     */     
/*     */     public Callback(List<String> deviceNames, String messageId, Function<TcResponse, Map<String, String>> responseExtractor) {
/* 314 */       this.count = deviceNames.size();
/* 315 */       this.messageId = messageId;
/* 316 */       this.responseExtractor = responseExtractor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(String deviceName, TcResponse response) {
/* 325 */       if (response.getResult() == TcResponse.Result.SUCCESS) {
/* 326 */         Map<String, String> map = this.responseExtractor.apply(response);
/* 327 */         for (Map.Entry<String, String> entry : map.entrySet()) {
/* 328 */           CommonCt3TcConfigQueryEndPoint.this.send(new CommonCt3TcConfigQueryEndPoint.DataMessageImpl(deviceName, entry.getKey(), entry.getValue()));
/*     */         }
/*     */       } 
/* 331 */       this.count--;
/* 332 */       if (this.count == 0) {
/* 333 */         CommonCt3TcConfigQueryEndPoint.this.countDown();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void countDown() {
/* 339 */     this.commandCount--;
/* 340 */     if (this.commandCount == 0)
/* 341 */       close(WebSocketCloseReason.FINISHED.getCode()); 
/*     */   }
/*     */   
/*     */   private enum Mapping
/*     */   {
/* 346 */     DEVICE_NUMBER,
/* 347 */     HARDWARE,
/* 348 */     DEVICE_TIME,
/* 349 */     FIRMWARE,
/* 350 */     TRANSMISSION_PERIOD,
/* 351 */     OPERATION_MODE;
/*     */   }
/*     */   
/*     */   public static class QueryMessageImpl
/*     */     implements CommonCt3QueryMessage
/*     */   {
/*     */     private String deviceType;
/* 358 */     private List<String> deviceNames = new ArrayList<>();
/*     */     
/* 360 */     private Set<String> mappings = new HashSet<>();
/*     */     
/*     */     private Integer equipmentNo;
/*     */     
/*     */     public List<String> getDeviceNames() {
/* 365 */       return this.deviceNames;
/*     */     }
/*     */     
/*     */     public String getDeviceType() {
/* 369 */       return this.deviceType;
/*     */     }
/*     */     
/*     */     public void setDeviceType(String deviceType) {
/* 373 */       this.deviceType = deviceType;
/*     */     }
/*     */     
/*     */     public void setDeviceNames(List<String> deviceNames) {
/* 377 */       this.deviceNames = deviceNames;
/*     */     }
/*     */     
/*     */     public Set<String> getMappings() {
/* 381 */       return this.mappings;
/*     */     }
/*     */     
/*     */     public void setMappings(Set<String> mappings) {
/* 385 */       this.mappings = mappings;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getEquipmentNo() {
/* 390 */       return this.equipmentNo;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setEquipmentNo(Integer equipment) {
/* 395 */       this.equipmentNo = equipment;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DataMessageImpl
/*     */     implements DataMessage
/*     */   {
/*     */     private String deviceName;
/*     */     private String name;
/*     */     private String value;
/*     */     
/*     */     public DataMessageImpl() {}
/*     */     
/*     */     public DataMessageImpl(String deviceName, String name, String value) {
/* 410 */       this.deviceName = deviceName;
/* 411 */       this.name = name;
/* 412 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 417 */       return "com.hwacom.ngtms.cms.am.CmsCt3TcConfigQueryEndpoint.DataMessage[deviceName=" + this.deviceName + ", name=" + this.name + ", value" + this.value + "]";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDeviceName() {
/* 427 */       return this.deviceName;
/*     */     }
/*     */     
/*     */     public void setDeviceName(String deviceName) {
/* 431 */       this.deviceName = deviceName;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 435 */       return this.name;
/*     */     }
/*     */     
/*     */     public void setName(String name) {
/* 439 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 443 */       return this.value;
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 447 */       this.value = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MessageEncoder
/*     */     implements Encoder.Text<DataMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public String encode(DataMessage object) throws EncodeException {
/* 466 */       JsonObject model = Json.createObjectBuilder().add("deviceName", object.getDeviceName()).add("name", object.getName()).add("value", object.getValue()).build();
/* 467 */       StringWriter stringWriter = new StringWriter();
/* 468 */       JsonWriter jsonWriter = Json.createWriter(stringWriter);
/* 469 */       jsonWriter.writeObject(model);
/*     */       
/* 471 */       return stringWriter.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MessageDecoder
/*     */     implements Decoder.Text<CommonCt3QueryMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */     
/*     */     public CommonCt3QueryMessage decode(String s) throws DecodeException {
/* 485 */       CommonCt3TcConfigQueryEndPoint.logger.debug("in MessageDecoder");
/* 486 */       CommonCt3TcConfigQueryEndPoint.QueryMessageImpl msg = new CommonCt3TcConfigQueryEndPoint.QueryMessageImpl();
/* 487 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 488 */       JsonArray deviceNameArray = jsonObject.getJsonArray("deviceNames");
/* 489 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 490 */       msg.getDeviceNames().addAll(jsonArrayToCollection(deviceNameArray));
/* 491 */       JsonArray mappingArray = jsonObject.getJsonArray("mappings");
/* 492 */       for (String each : jsonArrayToCollection(mappingArray)) {
/* 493 */         msg.getMappings().add(each);
/*     */       }
/* 495 */       Object o = jsonObject.get("equipmentNo");
/* 496 */       CommonCt3TcConfigQueryEndPoint.logger.debug("jsonObject.get(\"equipmentNo\"):'{}'", o);
/* 497 */       if (o != null) {
/* 498 */         msg.setEquipmentNo(Integer.valueOf(jsonObject.getInt("equipmentNo")));
/*     */       }
/*     */       
/* 501 */       return msg;
/*     */     }
/*     */     
/*     */     private Collection<String> jsonArrayToCollection(JsonArray jsonArray) {
/* 505 */       if (jsonArray == null) {
/* 506 */         return Collections.emptySet();
/*     */       }
/*     */       
/* 509 */       Set<String> result = new HashSet<>();
/* 510 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 511 */         JsonString jsonString = jsonArray.getJsonString(i);
/* 512 */         result.add(jsonString.getString());
/*     */       } 
/*     */       
/* 515 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 521 */         Json.createReader(new StringReader(s)).readObject();
/* 522 */         return true;
/* 523 */       } catch (JsonException ex) {
/* 524 */         CommonCt3TcConfigQueryEndPoint.logger.warn("QueryMessage format Error, will not be decoded! '{}'", s);
/* 525 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\websocket\CommonCt3TcConfigQueryEndPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */