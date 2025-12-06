/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.hwacom.ngtms.c.ncc.client.NccClient;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.CommRestartAndTestReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.CommRestartReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetEquipmentNoReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetReportHwStatusCycleReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetTimeReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.GetVerReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.QueryDbPasswordReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.QueryLockDbReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.ResetDeviceReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.SetCommandSetReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.SetDbPasswordReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.SetLockDbReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.SetReportHwStatusCycleReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.SetTimeReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.TimePm;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
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
/*     */ @Service
/*     */ public class CommonCt3TcServiceImpl
/*     */   implements CommonCt3TcService
/*     */ {
/*  37 */   private static final Logger logger = LoggerFactory.getLogger(CommonCt3TcServiceImpl.class);
/*     */   
/*     */   @Autowired
/*     */   private NccClient nccClient;
/*     */   
/*     */   public void setCt3ResetDevice(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/*  43 */     logger.debug("Set ct3 reset device, deviceNames='{}'", deviceNames);
/*  44 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/*  45 */         .<String>toArray(new String[0]), new ResetDeviceReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryCt3EquipmentNumber(List<String> deviceNames, int equipmentNumber, BiConsumer<String, TcResponse> callback) {
/*  53 */     logger.debug("Query ct3 equipment number, deviceNames='{}', eqNo='{}'", deviceNames, 
/*  54 */         Integer.valueOf(equipmentNumber));
/*  55 */     GetEquipmentNoReqPm pm = new GetEquipmentNoReqPm();
/*  56 */     pm.equipmentNo = equipmentNumber;
/*  57 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/*  58 */         .<String>toArray(new String[0]), pm, (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCt3CommRestart(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/*  65 */     logger.debug("Set ct3 communication restart, deviceNames='{}'", deviceNames);
/*  66 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/*  67 */         .<String>toArray(new String[0]), new CommRestartReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryCt3CommRestartAndTest(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/*  75 */     logger.debug("Query ct3 communication restart and test, deviceNames='{}'", deviceNames);
/*  76 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/*  77 */         .<String>toArray(new String[0]), new CommRestartAndTestReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCt3Time(List<String> deviceNames, Calendar time, BiConsumer<String, TcResponse> callback) {
/*  85 */     logger.debug("Set ct3 time, deviceNames='{}', time='{}'", deviceNames, time);
/*  86 */     if (time == null) {
/*  87 */       logger.error("Time is null.");
/*     */       return;
/*     */     } 
/*  90 */     TimePm timePm = new TimePm();
/*  91 */     timePm.year = time.get(1) - 1911;
/*  92 */     timePm.month = time.get(2);
/*  93 */     timePm.day = time.get(5);
/*     */     
/*  95 */     int week = time.get(7);
/*  96 */     if (week == 7) {
/*  97 */       week = 0;
/*     */     }
/*  99 */     timePm.week = week;
/* 100 */     timePm.hour = time.get(11);
/* 101 */     timePm.min = time.get(12);
/* 102 */     timePm.sec = time.get(13);
/* 103 */     SetTimeReqPm pm = new SetTimeReqPm();
/* 104 */     pm.timePm = timePm;
/* 105 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 106 */         .<String>toArray(new String[0]), pm, (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryCt3Time(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/* 113 */     logger.debug("Query ct3 time, deviceNames='{}'", deviceNames);
/* 114 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 115 */         .<String>toArray(new String[0]), new GetTimeReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCt3CommandSet(List<String> deviceNames, int commandSet, BiConsumer<String, TcResponse> callback) {
/* 123 */     logger.debug("Set ct3 command set, deviceNames='{}', commandSet='{}'", deviceNames, Integer.valueOf(commandSet));
/* 124 */     SetCommandSetReqPm pm = new SetCommandSetReqPm();
/* 125 */     pm.commandSet = commandSet;
/* 126 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 127 */         .<String>toArray(new String[0]), pm, (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryCt3Ver(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/* 134 */     logger.debug("Query ct3 version, deviceNames='{}'", deviceNames);
/* 135 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 136 */         .<String>toArray(new String[0]), new GetVerReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCt3ReportHwStatusCycle(List<String> deviceNames, int cycle, BiConsumer<String, TcResponse> callback) {
/* 144 */     logger.debug("Set ct3 report hwStatus cycle, deviceNames='{}', cycle='{}'", deviceNames, Integer.valueOf(cycle));
/* 145 */     SetReportHwStatusCycleReqPm pm = new SetReportHwStatusCycleReqPm();
/* 146 */     pm.hardwareCycle = cycle;
/* 147 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 148 */         .<String>toArray(new String[0]), pm, (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryCt3ReportHwStatusCycle(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/* 156 */     logger.debug("Query ct3 report hwStatus cycle, deviceNames='{}'", deviceNames);
/* 157 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 158 */         .<String>toArray(new String[0]), new GetReportHwStatusCycleReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCt3DbPassword(List<String> deviceNames, byte[] password, BiConsumer<String, TcResponse> callback) {
/* 166 */     logger.debug("Set ct3 db password, deviceNames='{}', password='{}'", deviceNames, password);
/* 167 */     SetDbPasswordReqPm pm = new SetDbPasswordReqPm();
/* 168 */     pm.password = password;
/* 169 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 170 */         .<String>toArray(new String[0]), pm, (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryCt3DbPassword(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/* 178 */     logger.debug("Query ct3 db password, deviceNames='{}', password='{}'", deviceNames);
/* 179 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 180 */         .<String>toArray(new String[0]), new QueryDbPasswordReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCt3LockDb(List<String> deviceNames, int lockDb, BiConsumer<String, TcResponse> callback) {
/* 188 */     logger.debug("Set ct3 lock db, deviceNames='{}', lockDb='{}'", Integer.valueOf(lockDb));
/* 189 */     SetLockDbReqPm pm = new SetLockDbReqPm();
/* 190 */     pm.lockDb = lockDb;
/* 191 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 192 */         .<String>toArray(new String[0]), pm, (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryCt3LockDb(List<String> deviceNames, BiConsumer<String, TcResponse> callback) {
/* 199 */     logger.debug("Query ct3 lock db, deviceNames='{}'", deviceNames);
/* 200 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 201 */         .<String>toArray(new String[0]), new QueryLockDbReqPm(), (reqSessionId, deviceName, tcResponse) -> paramBiConsumer.accept(deviceName, tcResponse));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\CommonCt3TcServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */