/*     */ package com.hwacom.ngtms.c.util;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceGroup;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceGroupDeviceConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadDivision;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.shared.DisplayMatch;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusHiNibble;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusHiNibbleType;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusLowNibble;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusLowNibbleType;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceGroupDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceGroupDeviceConfigDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.EmsDeviceStatusDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
/*     */ import java.util.HashSet;
/*     */ import java.util.Optional;
/*     */ import org.modelmapper.ModelMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DtoConverter
/*     */ {
/*     */   public static RoadLineDTO from(RoadLine roadLine) {
/*  39 */     if (roadLine == null) {
/*  40 */       return null;
/*     */     }
/*  42 */     RoadLineDTO roadLineBean = new RoadLineDTO();
/*  43 */     roadLineBean.setLineId(roadLine.getLineId());
/*  44 */     roadLineBean.setLineName(roadLine.getLineName());
/*  45 */     roadLineBean.setDirection(roadLine.getDirection());
/*  46 */     roadLineBean.setStartMileage(roadLine.getStartMileage());
/*  47 */     roadLineBean.setEndMileage(roadLine.getEndMileage());
/*  48 */     roadLineBean.setEnable(roadLine.isEnable());
/*  49 */     return roadLineBean;
/*     */   }
/*     */   
/*     */   public static RoadLine from(RoadLineDTO roadLineDTO) {
/*  53 */     RoadLine roadLine = new RoadLine();
/*  54 */     roadLine.setLineId(roadLineDTO.getLineId());
/*  55 */     roadLine.setLineName(roadLineDTO.getLineName());
/*  56 */     roadLine.setDirection(roadLineDTO.getDirection());
/*  57 */     roadLine.setStartMileage(roadLineDTO.getStartMileage());
/*  58 */     roadLine.setEndMileage(roadLineDTO.getEndMileage());
/*  59 */     roadLine.setEnable(roadLineDTO.isEnable());
/*  60 */     return roadLine;
/*     */   }
/*     */   
/*     */   public static RoadSectionDTO from(RoadSection roadSection) {
/*  64 */     if (roadSection == null) {
/*  65 */       return null;
/*     */     }
/*  67 */     RoadSectionDTO bean = new RoadSectionDTO();
/*  68 */     bean.setSectionId(roadSection.getSectionId());
/*  69 */     bean.setSectionName(roadSection.getSectionName());
/*  70 */     bean.setDirection(roadSection.getDirection());
/*  71 */     bean.setEndDivisionId(roadSection.getEndDivisionId());
/*  72 */     bean.setEndDivisionName("");
/*  73 */     bean.setStartDivisionId(roadSection.getStartDivisionId());
/*  74 */     bean.setStartDivisionName("");
/*  75 */     bean.setLaneCount(roadSection.getLaneCount());
/*  76 */     bean.setLineid(roadSection.getLineid());
/*  77 */     bean.setMaxSpeed(roadSection.getMaxSpeed());
/*  78 */     bean.setMinSpeed(roadSection.getMinSpeed());
/*  79 */     return bean;
/*     */   }
/*     */   
/*     */   public static DeviceConfigDTO from(DeviceTcConfig deviceConfig) {
/*  83 */     if (deviceConfig == null) {
/*  84 */       return null;
/*     */     }
/*  86 */     ModelMapper modelMapper = new ModelMapper();
/*  87 */     DeviceConfigDTO dto = (DeviceConfigDTO)modelMapper.map(deviceConfig, DeviceConfigDTO.class);
/*  88 */     dto.setLineId(deviceConfig.getLineId());
/*  89 */     Optional.<String>ofNullable(dto.getLineId())
/*  90 */       .ifPresent(lineId -> {
/*     */           IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*     */           
/*     */           Optional.<Object>ofNullable(roadLineMap.get(paramDeviceConfigDTO.getLineId())).ifPresent(());
/*     */         });
/*     */     
/*  96 */     dto.setMilepost(deviceConfig.getMilepost());
/*  97 */     dto.setDeviceType(deviceConfig.getDeviceType());
/*  98 */     dto.setPort(String.valueOf(deviceConfig.getPort()));
/*  99 */     dto.setLocation(deviceConfig.getLocation());
/* 100 */     dto.setLocationR(deviceConfig.getRampType());
/* 101 */     return dto;
/*     */   }
/*     */   
/*     */   public static DeviceTcConfig from(DeviceConfigDTO deviceConfigDTO) {
/* 105 */     ModelMapper modelMapper = new ModelMapper();
/* 106 */     DeviceTcConfig bean = (DeviceTcConfig)modelMapper.map(deviceConfigDTO, DeviceTcConfig.class);
/* 107 */     bean.setDeviceType(deviceConfigDTO.getDeviceType());
/* 108 */     bean.setPort(Integer.valueOf(Integer.parseInt(deviceConfigDTO.getPort())));
/* 109 */     bean.setLocation(deviceConfigDTO.getLocation().toString());
/* 110 */     return bean;
/*     */   }
/*     */   
/*     */   public static RoadDivisionDTO from(RoadDivision roadDivision) {
/* 114 */     if (roadDivision == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     RoadDivisionDTO result = new RoadDivisionDTO();
/* 118 */     result.setAreaType(roadDivision.getAreaType());
/* 119 */     result.setDivisionId(roadDivision.getDivisionId());
/* 120 */     result.setDivisionName(roadDivision.getDivisionName());
/* 121 */     result.setDivisionType(roadDivision.getDivisionType());
/* 122 */     result.setLineId(roadDivision.getLineId());
/* 123 */     result.setMileage(roadDivision.getMileage());
/*     */     
/* 125 */     return result;
/*     */   }
/*     */   
/*     */   public static RoadDivision from(RoadDivisionDTO roadDivisionDTO) {
/* 129 */     RoadDivision result = new RoadDivision();
/* 130 */     result.setAreaType(roadDivisionDTO.getAreaType());
/* 131 */     result.setDivisionId(roadDivisionDTO.getDivisionId());
/* 132 */     result.setDivisionName(roadDivisionDTO.getDivisionName());
/* 133 */     result.setDivisionType(roadDivisionDTO.getDivisionType());
/* 134 */     result.setLineId(roadDivisionDTO.getLineId());
/* 135 */     result.setMileage(roadDivisionDTO.getMileage());
/*     */     
/* 137 */     return result;
/*     */   }
/*     */   
/*     */   public static EmsDeviceStatusDTO from(DeviceTcStatus deviceStatus) {
/* 141 */     EmsDeviceStatusDTO result = new EmsDeviceStatusDTO(deviceStatus.getDeviceName());
/* 142 */     result.setCommStatus(deviceStatus.getCommStatus());
/* 143 */     result.setDisplay(deviceStatus.getDisplay());
/* 144 */     result.setOpMode(deviceStatus.getOpMode());
/* 145 */     result.setOpStatusHiNibble(from(deviceStatus.getOpStatusHiNibble()));
/* 146 */     result.setOpStatusLowNibble(from(deviceStatus.getOpStatusLowNibble()));
/* 147 */     result.setTimestamp(deviceStatus.getTimestamp());
/* 148 */     result.setAlive(deviceStatus.isAlive());
/* 149 */     if (DisplayMatch.MATCH == deviceStatus.getIsDisplayContentMatch()) {
/* 150 */       result.setIsDisplayContentMatch(Boolean.TRUE);
/* 151 */     } else if (DisplayMatch.NOT_MATCH == deviceStatus.getIsDisplayContentMatch()) {
/* 152 */       result.setIsDisplayContentMatch(Boolean.FALSE);
/*     */     } 
/* 154 */     for (int index = 0; index < 32; ) { result.setBit(Integer.valueOf(index), Boolean.valueOf(deviceStatus.getBit(index))); index++; }
/*     */     
/* 156 */     return result;
/*     */   }
/*     */   
/*     */   public static DeviceGroupDTO from(DeviceGroup deviceGroup) {
/* 160 */     DeviceGroupDTO result = new DeviceGroupDTO();
/* 161 */     HashSet<DeviceGroupDeviceConfigDTO> set = new HashSet<>();
/* 162 */     for (DeviceGroupDeviceConfig dgdc : deviceGroup.getDevices()) {
/* 163 */       set.add(from(dgdc));
/*     */     }
/* 165 */     result.setDevices(set);
/* 166 */     result.setGroupId(deviceGroup.getGroupId());
/* 167 */     result.setGroupName(deviceGroup.getGroupName());
/* 168 */     result.setGroupType(deviceGroup.getGroupType());
/* 169 */     return result;
/*     */   }
/*     */   
/*     */   public static DeviceGroup from(DeviceGroupDTO deviceGroup) {
/* 173 */     DeviceGroup result = new DeviceGroup();
/* 174 */     HashSet<DeviceGroupDeviceConfig> set = new HashSet<>();
/* 175 */     String groupId = KeyUtils.getKey(new Object[] { deviceGroup.getGroupType(), deviceGroup.getGroupName() });
/* 176 */     for (DeviceGroupDeviceConfigDTO dgdc : deviceGroup.getDevices()) {
/* 177 */       dgdc.setGroupId(groupId);
/* 178 */       set.add(from(dgdc));
/*     */     } 
/* 180 */     result.setDevices(set);
/* 181 */     result.setGroupId(deviceGroup.getGroupId());
/* 182 */     result.setGroupName(deviceGroup.getGroupName());
/* 183 */     result.setGroupType(deviceGroup.getGroupType().toString());
/* 184 */     return result;
/*     */   }
/*     */   
/*     */   public static DeviceGroupDeviceConfigDTO from(DeviceGroupDeviceConfig dgdc) {
/* 188 */     DeviceGroupDeviceConfigDTO result = new DeviceGroupDeviceConfigDTO();
/* 189 */     result.setDeviceName(dgdc.getDeviceName());
/* 190 */     result.setGroupId(dgdc.getGroupId());
/* 191 */     result.setId(dgdc.getId());
/* 192 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DeviceGroupDeviceConfig from(DeviceGroupDeviceConfigDTO dgdc) {
/* 197 */     DeviceGroupDeviceConfig result = new DeviceGroupDeviceConfig(dgdc.getGroupId(), dgdc.getDeviceName());
/* 198 */     return result;
/*     */   }
/*     */   
/*     */   public static OpStatusHiNibbleType from(OpStatusHiNibble nibble) {
/* 202 */     return (null == nibble) ? null : OpStatusHiNibbleType.valueOf(nibble.name());
/*     */   }
/*     */   
/*     */   public static OpStatusHiNibble from(OpStatusHiNibbleType nibble) {
/* 206 */     return (null == nibble) ? null : OpStatusHiNibble.valueOf(nibble.name());
/*     */   }
/*     */   
/*     */   public static OpStatusLowNibbleType from(OpStatusLowNibble nibble) {
/* 210 */     return (null == nibble) ? null : OpStatusLowNibbleType.valueOf(nibble.name());
/*     */   }
/*     */   
/*     */   public static OpStatusLowNibble from(OpStatusLowNibbleType nibble) {
/* 214 */     return (null == nibble) ? null : OpStatusLowNibble.valueOf(nibble.name());
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\\\util\DtoConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */