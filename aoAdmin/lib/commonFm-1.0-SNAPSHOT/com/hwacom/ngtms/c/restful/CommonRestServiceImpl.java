/*     */ package com.hwacom.ngtms.c.restful;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceGroup;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceGroupDeviceConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import com.hwacom.ngtms.c.fm.model.MfccConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadDivision;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.shared.dto.AmParametersDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceGroupDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.EmsDeviceStatusDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.MfccConfigDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
/*     */ import com.hwacom.ngtms.c.util.DtoConverter;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.FunctionPermission;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RoleDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserDTO;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.CellStyle;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/cam"})
/*     */ public class CommonRestServiceImpl
/*     */ {
/*  88 */   private static final Logger logger = LoggerFactory.getLogger(CommonRestServiceImpl.class);
/*     */   @Resource
/*     */   private Environment environment;
/*     */   @Autowired
/*     */   private ModelMapper modelMapper;
/*     */   
/*     */   @RequestMapping(value = {"/userDTO"}, method = {RequestMethod.GET})
/*     */   public UserDTO getUserDTO(HttpServletRequest req) {
/*  96 */     UserDTO user = null;
/*     */     try {
/*  98 */       logger.debug("Get user dto.");
/*  99 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/* 100 */       if (userId == null) {
/* 101 */         logger.error("Can not find userId from HttpServletRequest.");
/* 102 */         return null;
/*     */       } 
/* 104 */       logger.debug("Get current user, user='{}'", userId);
/*     */       
/* 106 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 107 */       User localUser = (User)userMap.get(userId);
/* 108 */       if (localUser == null) {
/* 109 */         logger.error("Can not find user by userId='{}'", userId);
/* 110 */         return null;
/*     */       } 
/* 112 */       user = (UserDTO)this.modelMapper.map(localUser, UserDTO.class);
/* 113 */       IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */       
/* 115 */       IMap<String, FunctionPermission> permissionMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 116 */       Set<RoleDTO> roleSet = new HashSet<>();
/* 117 */       for (String roleName : localUser.getRoleNames()) {
/* 118 */         Role role = (Role)roleMap.get(roleName);
/* 119 */         if (role != null) {
/* 120 */           RoleDTO roleDTO = (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
/* 121 */           Set<FunctionPermissionDTO> perSet = new HashSet<>();
/* 122 */           for (String perName : role.getFunctionPermissions()) {
/* 123 */             FunctionPermission per = (FunctionPermission)permissionMap.get(perName);
/* 124 */             if (per != null) {
/* 125 */               FunctionPermissionDTO perDTO = (FunctionPermissionDTO)this.modelMapper.map(per, FunctionPermissionDTO.class);
/* 126 */               perSet.add(perDTO);
/*     */             } 
/*     */           } 
/* 129 */           roleDTO.setFunctionPermissions(perSet);
/* 130 */           roleSet.add(roleDTO);
/*     */         } 
/*     */       } 
/* 133 */       user.setRoles(roleSet);
/* 134 */       logger.debug("UserDTO : {} ", user);
/* 135 */     } catch (Exception e) {
/* 136 */       logger.error("Get UserDTO failed.", e);
/*     */     } 
/* 138 */     return user;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/roadLineDTO"}, method = {RequestMethod.GET})
/*     */   public List<RoadLineDTO> getRoadLineDTO() {
/*     */     try {
/* 144 */       logger.debug("Get road line dto.");
/* 145 */       IMap<String, RoadLine> lineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 146 */       return (List<RoadLineDTO>)lineMap.values().stream().map(DtoConverter::from).collect(Collectors.toList());
/* 147 */     } catch (RuntimeException e) {
/* 148 */       logger.error("Get road line dto failed.", e);
/* 149 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/roadSectionDTO"}, method = {RequestMethod.GET})
/*     */   public List<RoadSectionDTO> getRoadSectionDTO() {
/*     */     try {
/* 156 */       logger.debug("Get road section dto.");
/* 157 */       IMap<String, RoadSection> sectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/* 158 */       return (List<RoadSectionDTO>)sectionMap.values().stream().map(DtoConverter::from).collect(Collectors.toList());
/* 159 */     } catch (RuntimeException e) {
/* 160 */       logger.error("Get road section dto failed.", e);
/* 161 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/roadDivisionList"}, method = {RequestMethod.GET})
/*     */   public List<RoadDivisionDTO> getRoadDivisionList() {
/*     */     try {
/* 168 */       logger.debug("Get road division dto.");
/* 169 */       IMap<String, RoadDivision> divisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 170 */       return (List<RoadDivisionDTO>)divisionMap.values().stream().map(DtoConverter::from).collect(Collectors.toList());
/* 171 */     } catch (RuntimeException e) {
/* 172 */       logger.error("Get road division dto failed.", e);
/* 173 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/mfccConfigDTO"}, method = {RequestMethod.GET})
/*     */   public List<MfccConfigDTO> getMfccConfigDTO() {
/*     */     try {
/* 180 */       logger.debug("Get mfcc config dto.");
/* 181 */       IMap<String, MfccConfig> mfccMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.MfccConfig);
/* 182 */       return (List<MfccConfigDTO>)mfccMap
/* 183 */         .values()
/* 184 */         .stream()
/* 185 */         .map(config -> toMfccConfigDTO(config))
/* 186 */         .collect(Collectors.toList());
/* 187 */     } catch (RuntimeException e) {
/* 188 */       logger.error("Get mfcc config dto failed.", e);
/* 189 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   private MfccConfigDTO toMfccConfigDTO(MfccConfig config) {
/* 194 */     MfccConfigDTO dto = new MfccConfigDTO();
/*     */     try {
/* 196 */       BeanUtils.copyProperties(dto, config);
/* 197 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 198 */       logger.error("failed to transfer to MfccConfigDto", e);
/*     */     } 
/* 200 */     return dto;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceConfigDTO/{deviceType}"}, method = {RequestMethod.GET})
/*     */   public List<DeviceConfigDTO> getDeviceConfigDTO(@PathVariable("deviceType") String deviceType) {
/*     */     try {
/* 207 */       logger.debug("Get device config dto, deviceType='{}'", deviceType);
/*     */       
/* 209 */       PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("deviceType").equal(deviceType);
/* 210 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 211 */       return (List<DeviceConfigDTO>)deviceMap
/* 212 */         .values((Predicate)predicate)
/* 213 */         .stream()
/* 214 */         .map(DtoConverter::from)
/* 215 */         .collect(Collectors.toList());
/* 216 */     } catch (RuntimeException e) {
/* 217 */       logger.error("Get device config dto failed.", e);
/* 218 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceConfig"}, method = {RequestMethod.GET})
/*     */   public List<DeviceConfigDTO> getDeviceConfig(@RequestParam("deviceType") List<String> deviceTypeList) {
/*     */     try {
/* 227 */       logger.debug("Get device config, types='{}'", deviceTypeList);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("deviceType").in(deviceTypeList.<Comparable>toArray((Comparable[])new String[deviceTypeList.size()]));
/* 233 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 234 */       return (List<DeviceConfigDTO>)deviceMap
/* 235 */         .values((Predicate)predicate)
/* 236 */         .stream()
/* 237 */         .map(DtoConverter::from)
/* 238 */         .collect(Collectors.toList());
/* 239 */     } catch (RuntimeException e) {
/* 240 */       logger.error("Get device config failed.", e);
/* 241 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceStatus/deviceTypeList"}, method = {RequestMethod.GET})
/*     */   public List<EmsDeviceStatusDTO> getDeviceStatus(@RequestParam("deviceTypeList") List<String> deviceTypeList) {
/*     */     try {
/* 250 */       logger.debug("Get device status, types='{}'", deviceTypeList);
/* 251 */       List<String> candidateList = new ArrayList<>();
/* 252 */       for (DeviceConfigDTO config : getDeviceConfig(deviceTypeList)) {
/* 253 */         candidateList.add(config.getDeviceName());
/*     */       }
/* 255 */       return getDeviceStatusList(candidateList);
/* 256 */     } catch (RuntimeException e) {
/* 257 */       logger.error("Get device status failed.", e);
/* 258 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceStatusList/deviceNameList"}, method = {RequestMethod.GET})
/*     */   public List<EmsDeviceStatusDTO> getDeviceStatusList(@RequestParam("deviceNameList") List<String> deviceNameList) {
/*     */     try {
/* 267 */       logger.debug("Get device status list, deviceNames='{}'", deviceNameList);
/* 268 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("deviceName").in(deviceNameList.<Comparable>toArray((Comparable[])new String[deviceNameList.size()]));
/* 274 */       return (List<EmsDeviceStatusDTO>)statusMap
/* 275 */         .values((Predicate)predicate)
/* 276 */         .stream()
/* 277 */         .map(DtoConverter::from)
/* 278 */         .collect(Collectors.toList());
/* 279 */     } catch (RuntimeException e) {
/* 280 */       logger.error("Get device status list failed.", e);
/* 281 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceGroup/deviceTypeList"}, method = {RequestMethod.GET})
/*     */   public List<DeviceGroupDTO> getDeviceGroup(@RequestParam("deviceTypeList") List<String> deviceTypeList) {
/*     */     try {
/* 290 */       logger.debug("Get device group, types='{}'", deviceTypeList);
/* 291 */       IMap<String, DeviceGroup> groupMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceGroup);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("groupType").in(deviceTypeList.<Comparable>toArray((Comparable[])new String[deviceTypeList.size()]));
/* 297 */       return (List<DeviceGroupDTO>)groupMap
/* 298 */         .values((Predicate)predicate)
/* 299 */         .stream()
/* 300 */         .map(DtoConverter::from)
/* 301 */         .collect(Collectors.toList());
/* 302 */     } catch (RuntimeException e) {
/* 303 */       logger.error("Get device group failed.", e);
/* 304 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceGroup/{groupId}"}, method = {RequestMethod.DELETE})
/*     */   public Boolean removeDeviceGroup(@PathVariable("groupId") String groupId) {
/*     */     try {
/* 312 */       logger.debug("Remove device group, groupId='{}'", groupId);
/* 313 */       IMap<String, DeviceGroup> deviceGroupMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceGroup);
/* 314 */       return Boolean.valueOf((deviceGroupMap.remove(groupId) != null));
/* 315 */     } catch (RuntimeException e) {
/* 316 */       logger.error("Remove device group failed.", e);
/* 317 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceGroup"}, method = {RequestMethod.POST})
/*     */   public Boolean createDeviceGroup(@RequestBody AmParametersDTO params) {
/* 324 */     DeviceGroupDTO deviceGroupDTO = params.getDeviceGroupDTO();
/*     */     try {
/* 326 */       logger.debug("Create device group, group='{}'", deviceGroupDTO);
/* 327 */       Boolean result = Boolean.TRUE;
/* 328 */       DeviceGroup deviceGroup = DtoConverter.from(deviceGroupDTO);
/* 329 */       String groupId = checkAndGetGroupId(deviceGroup);
/* 330 */       refreshGroupId(deviceGroup, groupId);
/*     */       
/* 332 */       IMap<String, DeviceGroup> deviceGroupMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceGroup);
/* 333 */       if (deviceGroupMap.containsKey(groupId)) {
/* 334 */         return Boolean.FALSE;
/*     */       }
/* 336 */       deviceGroupMap.set(groupId, deviceGroup);
/* 337 */       return result;
/* 338 */     } catch (RuntimeException e) {
/* 339 */       logger.error("Create device group failed.", e);
/* 340 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String checkAndGetGroupId(DeviceGroup deviceGroup) {
/* 345 */     Preconditions.checkArgument((deviceGroup.getGroupType() != null), "Group type cannot be null");
/* 346 */     Preconditions.checkArgument(!StringUtils.isBlank(deviceGroup.getGroupName()), "Group name cannot be blank");
/* 347 */     return KeyUtils.getKey(new Object[] { deviceGroup.getGroupType(), deviceGroup.getGroupName() });
/*     */   }
/*     */   
/*     */   private void refreshGroupId(DeviceGroup deviceGroup, String groupId) {
/* 351 */     deviceGroup.setGroupId(groupId);
/* 352 */     for (DeviceGroupDeviceConfig config : deviceGroup.getDevices()) {
/* 353 */       config.setGroupId(groupId);
/* 354 */       config.setId(KeyUtils.getKey(new Object[] { groupId, config.getDeviceName() }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deviceGroup"}, method = {RequestMethod.PUT})
/*     */   public Boolean updateDeviceGroup(@RequestBody AmParametersDTO params) {
/* 361 */     DeviceGroupDTO deviceGroupDTO = params.getDeviceGroupDTO();
/*     */     try {
/* 363 */       logger.debug("Update device group, group='{}'", deviceGroupDTO);
/* 364 */       DeviceGroup dg = DtoConverter.from(deviceGroupDTO);
/* 365 */       String groupId = checkAndGetGroupId(dg);
/* 366 */       refreshGroupId(dg, groupId);
/* 367 */       IMap<String, DeviceGroup> deviceGroupMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceGroup);
/* 368 */       if (!deviceGroupMap.containsKey(groupId)) {
/* 369 */         return Boolean.FALSE;
/*     */       }
/* 371 */       deviceGroupMap.set(groupId, dg);
/* 372 */       return Boolean.TRUE;
/* 373 */     } catch (RuntimeException e) {
/* 374 */       logger.error("Update device group failed.", e);
/* 375 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/exportOrderedListDataFromRpt"}, method = {RequestMethod.POST})
/*     */   public String exportOrderedListDataFromRpt(@RequestBody AmParametersDTO params) {
/* 385 */     List<List<String>> data = params.getCsvData();
/* 386 */     List<Integer> headerWidths = params.getCsvHeaderWidths();
/*     */     try {
/* 388 */       logger.debug("Export ordered list data from rpt.");
/* 389 */       return exportExcelFromOrderedList(data, headerWidths);
/* 390 */     } catch (RuntimeException e) {
/* 391 */       logger.warn("Export ordered list data from rpt failed.", e);
/* 392 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String exportExcelFromOrderedList(List<List<String>> data, List<Integer> columnWidths) {
/* 398 */     String fileName = UUID.randomUUID().toString() + ".xlsx";
/*     */ 
/*     */     
/* 401 */     File file = new File(getRptDynaConfig("RptAbsoluteResourcePath").getValue(), fileName);
/* 402 */     Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
/* 403 */     try(XSSFWorkbook null = new XSSFWorkbook(); 
/* 404 */         FileOutputStream fos = new FileOutputStream(file)) {
/* 405 */       Sheet sheet = xSSFWorkbook.createSheet("Sheet1");
/*     */       
/* 407 */       if (columnWidths != null) {
/* 408 */         int column = 0;
/* 409 */         for (Integer width : columnWidths) {
/* 410 */           if (width != null && width.intValue() >= 0) {
/* 411 */             sheet.setColumnWidth(column, width.intValue() * 256);
/*     */           }
/* 413 */           column++;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 418 */       CellStyle verticalCenter = xSSFWorkbook.createCellStyle();
/* 419 */       verticalCenter.setVerticalAlignment((short)1);
/* 420 */       CellStyle wrapAndVerticalCenter = xSSFWorkbook.createCellStyle();
/* 421 */       wrapAndVerticalCenter.setWrapText(true);
/* 422 */       wrapAndVerticalCenter.setVerticalAlignment((short)1);
/* 423 */       int rowNum = 0;
/* 424 */       for (List<String> rowData : data) {
/* 425 */         Row row = sheet.createRow(rowNum++);
/* 426 */         int colNum = 0;
/* 427 */         for (String value : rowData) {
/* 428 */           Cell cell = row.createCell(colNum++);
/* 429 */           if (value == null) {
/* 430 */             cell.setCellType(1);
/* 431 */             cell.setCellValue("");
/* 432 */             cell.setCellStyle(verticalCenter);
/*     */             
/*     */             continue;
/*     */           } 
/* 436 */           if (pattern.matcher(value).matches()) {
/* 437 */             if (isOverIntegerRange(value)) {
/* 438 */               cell.setCellType(1);
/* 439 */               cell.setCellValue(value);
/* 440 */               cell.setCellStyle(verticalCenter); continue;
/*     */             } 
/* 442 */             cell.setCellType(0);
/* 443 */             cell.setCellValue((new Double(value)).doubleValue());
/* 444 */             cell.setCellStyle(verticalCenter);
/*     */             continue;
/*     */           } 
/* 447 */           cell.setCellType(1);
/* 448 */           cell.setCellValue(value);
/* 449 */           if (value.contains(System.lineSeparator())) {
/* 450 */             cell.setCellStyle(wrapAndVerticalCenter); continue;
/*     */           } 
/* 452 */           cell.setCellStyle(verticalCenter);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 458 */       xSSFWorkbook.write(fos);
/* 459 */       return getRptDynaConfig("RptResourceBaseUri").getValue() + fileName;
/* 460 */     } catch (IOException e) {
/* 461 */       logger.error("Export Excel From Ordered List fail, message:'{}'", e);
/* 462 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DynamicConfig getRptDynaConfig(String configName) {
/* 467 */     IMap<String, DynamicConfig> configMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */ 
/*     */ 
/*     */     
/* 471 */     DynamicConfig dynamicConfig = (DynamicConfig)configMap.get(new DynamicConfigPk((String)this.environment
/*     */           
/* 473 */           .getProperty("hz.group.name", String.class), "RptFm", configName));
/* 474 */     if (dynamicConfig == null) {
/* 475 */       dynamicConfig = (DynamicConfig)configMap.get(configName);
/*     */     }
/* 477 */     return dynamicConfig;
/*     */   }
/*     */   
/*     */   private boolean isOverIntegerRange(String value) {
/* 481 */     String numStr = value.startsWith("-") ? value.substring(1) : value;
/*     */     
/* 483 */     return (numStr.split("\\.")[0].length() > 10);
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/deviceTypeList/{tcTypeOnly}"}, method = {RequestMethod.GET})
/*     */   public List<DeviceTypeDTO> getDeviceTypeList(@PathVariable("tcTypeOnly") Boolean tcTypeOnly) {
/* 488 */     List<DeviceTypeDTO> list = new ArrayList<>();
/*     */     try {
/* 490 */       logger.debug("Get Device Type List.");
/* 491 */       IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 492 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 493 */       map.values()
/* 494 */         .forEach(deviceType -> {
/*     */             if (paramBoolean.booleanValue()) {
/*     */               PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("deviceType").equal(deviceType.getId());
/*     */ 
/*     */               
/*     */               for (DeviceTcConfig config : paramIMap.values((Predicate)predicate)) {
/*     */                 if (config.getProtocolType() != null) {
/*     */                   DeviceTypeDTO deviceTypeDTO = (DeviceTypeDTO)this.modelMapper.map(deviceType, DeviceTypeDTO.class);
/*     */                   
/*     */                   paramList.add(deviceTypeDTO);
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } else {
/*     */               DeviceTypeDTO deviceTypeDTO = (DeviceTypeDTO)this.modelMapper.map(deviceType, DeviceTypeDTO.class);
/*     */               
/*     */               paramList.add(deviceTypeDTO);
/*     */             } 
/*     */           });
/*     */       
/* 515 */       list.sort(
/* 516 */           Comparator.comparing(DeviceTypeDTO::getCategory).thenComparing(DeviceTypeDTO::getId));
/* 517 */       return list;
/* 518 */     } catch (Exception e) {
/* 519 */       logger.error("Get Device Type List failed.", e);
/* 520 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\restful\CommonRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */