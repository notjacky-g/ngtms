/*     */ package com.hwacom.ngtms.ao.fm;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.fm.task.AutoConfirmRtnAlarmTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.CardReaderLogDataTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.CardReaderSynchronizeTimeTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.CheckCloseVoiceAlarmTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.CheckNotProccessTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.EqConfigDataTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.PdConfigDataXmlTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.PdOperationDataXmlTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.PdStatusTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomCardReaderStatusTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomDeviceAlarmTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomDeviceBMSTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomDeviceSMRTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomDeviceStatusTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomDeviceWaterPowerTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomEnvironmentalControlAlarmTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomInTimeAlarmTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomPDAlarmTask;
/*     */ import com.hwacom.ngtms.ao.fm.task.RoomRtuStatusTask;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusDeviceConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusPinMapping;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusReadConfig;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AoCommonFm
/*     */   extends FmeMainBase
/*     */ {
/*  48 */   private static Logger logger = LoggerFactory.getLogger(AoCommonFm.class);
/*     */   
/*  50 */   IMap<String, ModbusDeviceConfig> cfgMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusDeviceConfig);
/*  51 */   IMap<Long, ModbusPinMapping> pMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/*  52 */   IMap<Long, ModbusReadConfig> rMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusReadConfig);
/*     */   
/*     */   static {
/*  55 */     setDynamicConfigDeclares(new DynamicConfigDeclare[] { new DynamicConfigDeclare("eqConfigDataTaskCron", "0 5 * * * ?"), new DynamicConfigDeclare("roomCardReaderStatusTaskCron", "15 * * * * ?"), new DynamicConfigDeclare("roomCardReaderLogTaskCron", "*/3 * * * * ?"), new DynamicConfigDeclare("roomDeviceStatusTaskCron", "15 * * * * ?"), new DynamicConfigDeclare("rtuDeviceStatusTaskCron", "10 * * * * ?"), new DynamicConfigDeclare("pdStatusTaskCron", "5 * * * * ?"), new DynamicConfigDeclare("roomInTimeAlarmTaskCron", "*/30 * * * * ?"), new DynamicConfigDeclare("roomDeviceAlarmTaskCron", "10 * * * * ?"), new DynamicConfigDeclare("roomPDAlarmTaskCron", "* */5 * * * ?"), new DynamicConfigDeclare("roomECAlarmTaskCron", "* */5 * * * ?"), new DynamicConfigDeclare("autoConfirmRtnAlarmTaskCron", "50 * * * * ?"), new DynamicConfigDeclare("roomSMRBMSTaskCron", "1 * * * * ?"), new DynamicConfigDeclare("roomWaterPowerTaskCron", "0 0 * * * ?"), new DynamicConfigDeclare("pdOperationDataXmlTaskCron", "10 * * * * ?"), new DynamicConfigDeclare("pdConfigDataXmlTaskCron", "10 0 0 * * ?"), new DynamicConfigDeclare("checkNotProccessTaskCron", "10 0 0 * * ?"), new DynamicConfigDeclare("checkCloseVoiceAlarmTask", "5 * * * * ?"), new DynamicConfigDeclare("cardReaderSynchronizeTimeTaskCron", "0 0 2 * * ?"), new DynamicConfigDeclare("illegalInvasionCron", "0 * * * * ?"), new DynamicConfigDeclare("eqConfigDataUrl", "http://10.105.6.75/xml/cloud_30/30_1day_eq_config_data.xml"), new DynamicConfigDeclare("NCUCardReaderApiUrl", "http://127.0.0.1:12345"), new DynamicConfigDeclare("NCUConnect", "api/NCUConnet"), new DynamicConfigDeclare("NCUInfo", "api/NCUInfo"), new DynamicConfigDeclare("NCUDoor", "api/NCUDoor"), new DynamicConfigDeclare("NCUCard", "api/NCUCard"), new DynamicConfigDeclare("NCUAllCard", "api/NCUAllCard"), new DynamicConfigDeclare("NCUCardLog", "api/NCUCardLog"), new DynamicConfigDeclare("LifeFaceApiUrl", "10.122.41.152:6075"), new DynamicConfigDeclare("LifeFaceUserLogin", "user/web/login"), new DynamicConfigDeclare("LifeFaceFailedMessage", "liveface/default-nonrecognized?sessionId=") });
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AoCommonFm(String name, String description) {
/* 100 */     super(name, description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws FmException {
/* 110 */     logger.debug("Ao commonFm init.");
/*     */   }
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
/*     */ 
/*     */   
/*     */   protected void runFm() {
/*     */     try {
/* 128 */       scheduleReschedulableJob("eqConfigDataTaskCron", EqConfigDataTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 132 */           getDynaConfig("eqConfigDataTaskCron").getValue());
/*     */ 
/*     */       
/* 135 */       scheduleReschedulableJob("roomCardReaderStatusTaskCron", RoomCardReaderStatusTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 139 */           getDynaConfig("roomCardReaderStatusTaskCron").getValue());
/*     */ 
/*     */       
/* 142 */       scheduleReschedulableJob("rtuDeviceStatusTaskCron", RoomRtuStatusTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 146 */           getDynaConfig("rtuDeviceStatusTaskCron").getValue());
/*     */ 
/*     */       
/* 149 */       scheduleReschedulableJob("roomDeviceStatusTaskCron", RoomDeviceStatusTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 153 */           getDynaConfig("roomDeviceStatusTaskCron").getValue());
/*     */ 
/*     */       
/* 156 */       scheduleReschedulableJob("pdStatusTaskCron", PdStatusTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 160 */           getDynaConfig("pdStatusTaskCron").getValue());
/*     */ 
/*     */       
/* 163 */       scheduleReschedulableJob("cardReaderSynchronizeTimeTaskCron", CardReaderSynchronizeTimeTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 167 */           getDynaConfig("cardReaderSynchronizeTimeTaskCron").getValue());
/*     */ 
/*     */       
/* 170 */       scheduleReschedulableJob("roomCardReaderLogTaskCron", CardReaderLogDataTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 174 */           getDynaConfig("roomCardReaderLogTaskCron").getValue());
/*     */ 
/*     */       
/* 177 */       scheduleReschedulableJob("autoConfirmRtnAlarmTaskCron", AutoConfirmRtnAlarmTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 181 */           getDynaConfig("autoConfirmRtnAlarmTaskCron").getValue());
/*     */ 
/*     */       
/* 184 */       scheduleReschedulableJob("roomPDAlarmTaskCron", RoomPDAlarmTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 188 */           getDynaConfig("roomPDAlarmTaskCron").getValue());
/*     */ 
/*     */       
/* 191 */       scheduleReschedulableJob("roomECAlarmTaskCron", RoomEnvironmentalControlAlarmTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 195 */           getDynaConfig("roomECAlarmTaskCron").getValue());
/*     */ 
/*     */       
/* 198 */       scheduleReschedulableJob("roomDeviceAlarmTaskCron", RoomDeviceAlarmTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 202 */           getDynaConfig("roomDeviceAlarmTaskCron").getValue());
/*     */ 
/*     */       
/* 205 */       scheduleReschedulableJob("roomInTimeAlarmTaskCron", RoomInTimeAlarmTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 209 */           getDynaConfig("roomInTimeAlarmTaskCron").getValue());
/*     */ 
/*     */       
/* 212 */       scheduleReschedulableJob("roomSMRBMSTaskCron", RoomDeviceBMSTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 216 */           getDynaConfig("roomSMRBMSTaskCron").getValue());
/*     */ 
/*     */       
/* 219 */       scheduleReschedulableJob("roomSMRBMSTaskCron", RoomDeviceSMRTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 223 */           getDynaConfig("roomSMRBMSTaskCron").getValue());
/*     */ 
/*     */       
/* 226 */       scheduleReschedulableJob("roomWaterPowerTaskCron", RoomDeviceWaterPowerTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 230 */           getDynaConfig("roomWaterPowerTaskCron").getValue());
/*     */ 
/*     */       
/* 233 */       scheduleReschedulableJob("pdOperationDataXmlTaskCron", PdOperationDataXmlTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 237 */           getDynaConfig("pdOperationDataXmlTaskCron").getValue());
/*     */ 
/*     */       
/* 240 */       scheduleReschedulableJob("pdConfigDataXmlTaskCron", PdConfigDataXmlTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 244 */           getDynaConfig("pdConfigDataXmlTaskCron").getValue());
/*     */ 
/*     */       
/* 247 */       scheduleReschedulableJob("checkNotProccessTaskCron", CheckNotProccessTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 251 */           getDynaConfig("checkNotProccessTaskCron").getValue());
/*     */ 
/*     */       
/* 254 */       scheduleReschedulableJob("checkCloseVoiceAlarmTask", CheckCloseVoiceAlarmTask.class, null, 
/*     */ 
/*     */ 
/*     */           
/* 258 */           getDynaConfig("checkCloseVoiceAlarmTask").getValue());
/*     */       
/* 260 */       while (checkFmKeepRunning()) {
/*     */         try {
/* 262 */           TimeUnit.SECONDS.sleep(60L);
/* 263 */         } catch (InterruptedException ie) {}
/*     */       }
/*     */     
/*     */     }
/* 267 */     catch (Exception e) {
/* 268 */       logger.error("AoCommonError", e);
/*     */     } finally {
/* 270 */       logger.info("{} stopped", getFmeName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTesting() throws FmException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAllowConcurrentExecution() {
/* 289 */     return Boolean.FALSE.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDynaConfigRemoved(String name, DynamicConfig dynamicConfig) {
/* 294 */     logger.info("Dynamic Config removed, name:'{}'", name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDynaConfigUpdated(String name, DynamicConfig dynamicConfig) {
/* 299 */     logger.info("Dynamic Config update, name:'{}'", name);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\AoCommonFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */