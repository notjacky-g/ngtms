/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmTypeConfig;
/*     */ import com.hwacom.ngtms.c.fm.repository.AlarmSubTypeConfigRepository;
/*     */ import com.hwacom.ngtms.c.fm.repository.AlarmTypeConfigRepository;
/*     */ import com.hwacom.ngtms.c.shared.AlarmLogEventContext;
/*     */ import com.hwacom.ngtms.c.shared.dto.AlarmSubTypeConfigDTO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.persistence.EntityNotFoundException;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.data.domain.Sort;
/*     */ import org.springframework.data.domain.Sort.Direction;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AlarmDataService
/*     */ {
/*  37 */   private static Logger logger = LoggerFactory.getLogger(AlarmDataService.class);
/*     */   
/*     */   @Autowired
/*     */   private ModelMapper modelMapper;
/*     */   @Autowired
/*     */   private AlarmTypeConfigRepository alarmTypeConfigRepository;
/*     */   
/*     */   public void createAlarmTypeConfig(AlarmTypeConfig alarmTypeConfig)
/*     */   {
/*  46 */     IMap<String, AlarmTypeConfig> alarmTypeConfigMap = HzUtils.getMap(CommonFmHzMap.AlarmTypeConfig);
/*  47 */     alarmTypeConfigMap.put(alarmTypeConfig.getAlarmType(), alarmTypeConfig);
/*     */   }
/*     */   
/*     */   public void updateAlarmTypeConfig(AlarmTypeConfig alarmTypeConfig)
/*     */   {
/*  52 */     IMap<String, AlarmTypeConfig> alarmTypeConfigMap = HzUtils.getMap(CommonFmHzMap.AlarmTypeConfig);
/*  53 */     alarmTypeConfigMap.put(alarmTypeConfig.getAlarmType(), alarmTypeConfig);
/*     */   }
/*     */   
/*     */   public void removeAlarmTypeConfig(String alarmType)
/*     */   {
/*  58 */     IMap<String, AlarmTypeConfig> alarmTypeConfigMap = HzUtils.getMap(CommonFmHzMap.AlarmTypeConfig);
/*  59 */     alarmTypeConfigMap.delete(alarmType);
/*     */   }
/*     */   
/*     */ 
/*     */   @Transactional("oldbTransactionManager")
/*     */   public AlarmSubTypeConfigDTO createAlarmSubTypeConfig(AlarmSubTypeConfigDTO alarmSubTypeConfigDto)
/*     */   {
/*  66 */     AlarmSubTypeConfig alarmSubTypeConfig = (AlarmSubTypeConfig)this.modelMapper.map(alarmSubTypeConfigDto, AlarmSubTypeConfig.class);
/*  67 */     IMap<String, AlarmTypeConfig> typeMap = HzUtils.getMap(CommonFmHzMap.AlarmTypeConfig);
/*  68 */     logger.debug("typeMap.size(): " + typeMap.size());
/*  69 */     AlarmTypeConfig alarmTypeConfig = (AlarmTypeConfig)typeMap.get(alarmSubTypeConfig.getAlarmType());
/*  70 */     if (alarmTypeConfig != null) {
/*  71 */       alarmSubTypeConfig.setId(Integer.valueOf(this.alarmSubTypeConfigRepository.getMaxId() + 1));
/*  72 */       alarmSubTypeConfig.setAlarmType(alarmTypeConfig.getAlarmType());
/*     */       
/*  74 */       IMap<Integer, AlarmSubTypeConfig> alarmSubTypeConfigMap = HzUtils.getMap(CommonFmHzMap.AlarmSubTypeConfig);
/*  75 */       alarmSubTypeConfigMap.put(alarmSubTypeConfig.getId(), alarmSubTypeConfig);
/*  76 */       alarmSubTypeConfigDto.setId(alarmSubTypeConfig.getId());
/*  77 */       return alarmSubTypeConfigDto;
/*     */     }
/*     */     
/*  80 */     throw new EntityNotFoundException("The alarmTypeConfig with key " + alarmSubTypeConfig.getAlarmType() + " was not found");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Transactional("oldbTransactionManager")
/*     */   public AlarmSubTypeConfigDTO updateAlarmSubTypeConfig(AlarmSubTypeConfigDTO alarmSubTypeConfigDto)
/*     */   {
/*  88 */     AlarmSubTypeConfig alarmSubTypeConfig = (AlarmSubTypeConfig)this.modelMapper.map(alarmSubTypeConfigDto, AlarmSubTypeConfig.class);
/*     */     
/*  90 */     IMap<Integer, AlarmSubTypeConfig> alarmSubTypeConfigMap = HzUtils.getMap(CommonFmHzMap.AlarmSubTypeConfig);
/*  91 */     if (alarmSubTypeConfigMap.get(alarmSubTypeConfig.getId()) != null) {
/*  92 */       alarmSubTypeConfigMap.put(alarmSubTypeConfig.getId(), alarmSubTypeConfig);
/*  93 */       return alarmSubTypeConfigDto;
/*     */     }
/*     */     
/*     */ 
/*  97 */     throw new EntityNotFoundException("The alarmSubTypeConfig with key " + alarmSubTypeConfig.getAlarmType() + " was not found");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeAlarmSubTypeConfig(Integer id)
/*     */   {
/* 104 */     IMap<Integer, AlarmSubTypeConfig> alarmSubTypeConfigMap = HzUtils.getMap(CommonFmHzMap.AlarmSubTypeConfig);
/* 105 */     alarmSubTypeConfigMap.delete(id);
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
/*     */   public List<AlarmLog> findAlarmLogs(List<String> deviceNames, Integer alarmSubTypeId, Date startTime, Date endTime)
/*     */   {
/* 118 */     List<AlarmLog> result = new ArrayList();
/* 119 */     List<AlarmLog> alarmLogList = new ArrayList();
/* 120 */     if (alarmSubTypeId == null)
/*     */     {
/* 122 */       alarmLogList = this.alarmLogRepository.findByTimestampBetween(startTime, endTime, new Sort(Sort.Direction.ASC, new String[] { "id" }));
/*     */     }
/*     */     else {
/* 125 */       List<String> alarmSubTypeList = new ArrayList();
/* 126 */       alarmSubTypeList.add(String.valueOf(alarmSubTypeId));
/*     */       
/* 128 */       alarmLogList = this.alarmLogRepository.findByTimestampBetweenAndAlarmSubTypeIds(startTime, endTime, alarmSubTypeList, new Sort(Sort.Direction.ASC, new String[] { "id" }));
/*     */     }
/*     */     Gson gson;
/* 131 */     if ((deviceNames != null) && (!deviceNames.isEmpty())) {
/* 132 */       gson = new Gson();
/* 133 */       for (AlarmLog log : alarmLogList) {
/* 134 */         if (log.getContextData() != null)
/*     */         {
/* 136 */           AlarmLogEventContext contextData = (AlarmLogEventContext)gson.fromJson(log.getContextData(), AlarmLogEventContext.class);
/* 137 */           if ((contextData != null) && (deviceNames.contains(contextData.getDeviceName()))) {
/* 138 */             result.add(log);
/*     */           }
/*     */         }
/*     */       }
/*     */     } else {
/* 143 */       result = alarmLogList;
/*     */     }
/* 145 */     return result;
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
/*     */   @Autowired
/*     */   private AlarmSubTypeConfigRepository alarmSubTypeConfigRepository;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Autowired
/*     */   private AlarmLogRepository alarmLogRepository;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<AlarmLog> findByTimestampBetween(Date startTime, Date endTime)
/*     */   {
/* 176 */     return this.alarmLogRepository.findByTimestampBetween(startTime, endTime, new Sort(Sort.Direction.ASC, new String[] { "id" }));
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */