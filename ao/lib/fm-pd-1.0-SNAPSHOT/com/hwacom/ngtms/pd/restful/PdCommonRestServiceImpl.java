/*     */ package com.hwacom.ngtms.pd.restful;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
/*     */ import com.hwacom.ngtms.pd.fm.service.PdDataService;
/*     */ import com.hwacom.ngtms.pd.shared.dto.LocationDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdConfigDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdParametersDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdStatusDTO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/pd/common"})
/*     */ public class PdCommonRestServiceImpl
/*     */   extends BaseRestful
/*     */ {
/*  46 */   private static final Logger logger = LoggerFactory.getLogger(PdCommonRestServiceImpl.class);
/*     */   @Autowired
/*     */   PdDataService pdDataService;
/*     */   @Autowired
/*     */   ModelMapper modelMapper;
/*     */   
/*     */   @PostConstruct
/*  53 */   public void init() { setSubSystem(SubSystem.PD); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/allPdConfig"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public List<PdConfigDTO> retrievePdConfig()
/*     */   {
/*     */     try
/*     */     {
/*  64 */       logger.debug("RetrievePdConfig");
/*  65 */       return this.pdDataService.retrievePdConfig();
/*     */     } catch (Exception e) {
/*  67 */       logger.error("RetrievePdConfig failed.", e); }
/*  68 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/pdConfig/check"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT})
/*     */   public boolean checkPdConfig(@RequestBody PdParametersDTO params)
/*     */   {
/*     */     try
/*     */     {
/*  80 */       logger.debug("CheckPdConfig");
/*  81 */       String deviceName = params.getPdDeviceName();
/*  82 */       return this.pdDataService.checkPdConfig(deviceName);
/*     */     } catch (Exception e) {
/*  84 */       logger.error("CheckPdConfig failed.", e); }
/*  85 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/pdConfig/check/remove"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT})
/*     */   public boolean checkRemovedPdConfig(@RequestBody PdParametersDTO params)
/*     */   {
/*     */     try
/*     */     {
/*  97 */       logger.debug("CheckRemovedPdConfig");
/*  98 */       String deviceName = params.getPdDeviceName();
/*  99 */       return this.pdDataService.checkRemovedPdConfig(deviceName);
/*     */     } catch (Exception e) {
/* 101 */       logger.error("CheckRemovedPdConfig failed.", e); }
/* 102 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/pdConfig"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT})
/*     */   public void savePdConfig(@RequestBody PdConfigDTO dto, HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/* 114 */       logger.debug("SavePdConfig");
/* 115 */       this.pdDataService.savePdConfig(dto);
/*     */     }
/*     */     catch (Exception e) {
/* 118 */       logger.error("SavePdConfig failed.", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/pdConfig"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void updatePdConfig(@RequestBody PdConfigDTO dto, HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/* 130 */       logger.debug("UpdatePdConfig");
/* 131 */       this.pdDataService.updatePdConfig(dto);
/*     */     }
/*     */     catch (Exception e) {
/* 134 */       logger.error("UpdatePdConfig failed.", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/pdConfig"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
/*     */   public void deletePdConfig(@RequestBody PdParametersDTO params, HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/* 146 */       logger.debug("DeletePdConfig");
/* 147 */       String deviceName = params.getPdDeviceName();
/* 148 */       this.pdDataService.deletePdConfig(deviceName);
/*     */     }
/*     */     catch (Exception e) {
/* 151 */       logger.error("DeletePdConfig failed.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/roadLine"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public List<RoadLineDTO> retrieveRoadLine() {
/*     */     try {
/* 158 */       logger.debug("RetrieveRoadLine");
/* 159 */       IMap<String, RoadLine> lineMap = HzUtils.getMap(CommonFmHzMap.RoadLine);
/* 160 */       List<RoadLineDTO> lineList = new ArrayList();
/* 161 */       for (RoadLine line : lineMap.values()) {
/* 162 */         RoadLineDTO dto = (RoadLineDTO)this.modelMapper.map(line, RoadLineDTO.class);
/* 163 */         lineList.add(dto);
/*     */       }
/* 165 */       return lineList;
/*     */     } catch (Exception e) {
/* 167 */       logger.error("RetrieveRoadLine failed.", e); }
/* 168 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/location"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public List<LocationDTO> retrieveLocation()
/*     */   {
/*     */     try {
/* 175 */       logger.debug("RetrieveLocation");
/*     */       
/* 177 */       IMap<String, DeviceHostLocation> locationMap = HzUtils.getMap(CommonFmHzMap.DeviceHostLocation);
/* 178 */       List<LocationDTO> locationList = new ArrayList();
/* 179 */       for (DeviceHostLocation location : locationMap.values()) {
/* 180 */         LocationDTO dto = new LocationDTO();
/* 181 */         dto.setId(location.getId());
/* 182 */         dto.setLocName(location.getLocName());
/* 183 */         locationList.add(dto);
/*     */       }
/* 185 */       return locationList;
/*     */     } catch (Exception e) {
/* 187 */       logger.error("RetrieveLocation failed.", e); }
/* 188 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/section"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public List<RoadSectionDTO> retrieveSection()
/*     */   {
/*     */     try {
/* 195 */       logger.debug("RetrieveRoadsection");
/* 196 */       IMap<String, RoadSection> sectionMap = HzUtils.getMap(CommonFmHzMap.RoadSection);
/* 197 */       List<RoadSectionDTO> sectionList = new ArrayList();
/* 198 */       for (RoadSection section : sectionMap.values()) {
/* 199 */         RoadSectionDTO dto = (RoadSectionDTO)this.modelMapper.map(section, RoadSectionDTO.class);
/* 200 */         sectionList.add(dto);
/*     */       }
/* 202 */       return sectionList;
/*     */     } catch (Exception e) {
/* 204 */       logger.error("RetrieveRoadsection failed.", e); }
/* 205 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/loopDeviceConfig"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public List<LoopDeviceConfigDTO> retrieveLoopDeviceConfig(@RequestParam("deviceNames") List<String> deviceNames)
/*     */   {
/*     */     try
/*     */     {
/* 213 */       logger.debug("RetrieveLoopDeviceConfig.");
/* 214 */       List<LoopDeviceConfigDTO> loopDeviceConfigList = new ArrayList();
/* 215 */       loopDeviceConfigList.addAll(this.pdDataService.retrieveLoopDeviceConfig(deviceNames));
/* 216 */       return loopDeviceConfigList;
/*     */     } catch (Exception e) {
/* 218 */       logger.error("RetrieveLoopDeviceConfig failed.", e); }
/* 219 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/loopDeviceConfig/unset"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public List<LoopDeviceConfigDTO> retrieveUnsetLoopDeviceConfig()
/*     */   {
/*     */     try {
/* 226 */       logger.debug("RetrieveUnsetLoopDeviceConfig.");
/* 227 */       List<LoopDeviceConfigDTO> loopDeviceConfigList = new ArrayList();
/* 228 */       loopDeviceConfigList.addAll(this.pdDataService.retrieveUnsetLoopDeviceConfig());
/* 229 */       return loopDeviceConfigList;
/*     */     } catch (Exception e) {
/* 231 */       logger.error("RetrieveUnsetLoopDeviceConfig failed.", e); }
/* 232 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/loopDeviceConfig/update"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void updateLoopDeviceConfig(@RequestBody PdParametersDTO params, HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/* 240 */       logger.debug("UpdateLoopDeviceConfig.");
/* 241 */       String deviceName = params.getPdDeviceName();
/* 242 */       List<LoopDeviceConfigDTO> dtos = params.getLoopDeviceConfigDTOs();
/* 243 */       this.pdDataService.updateLoopDeviceConfig(deviceName, dtos);
/*     */     } catch (Exception e) {
/* 245 */       logger.error("UpdateLoopDeviceConfig failed.", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/loopDeviceConfig"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void updateLoopDeviceData(@RequestBody LoopDeviceConfigDTO dto, HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/* 258 */       logger.debug("UupdateLoopDeviceData.");
/* 259 */       this.pdDataService.updateLoopDeviceData(dto);
/*     */     } catch (Exception e) {
/* 261 */       logger.error("UupdateLoopDeviceData failed.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/pdStatus"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public List<PdStatusDTO> retrievePdStatus() {
/*     */     try {
/* 268 */       logger.debug("RetrievePdStatus.");
/* 269 */       List<PdStatusDTO> pdStatusList = new ArrayList();
/* 270 */       pdStatusList.addAll(this.pdDataService.retrievePdStatus());
/* 271 */       return pdStatusList;
/*     */     } catch (Exception e) {
/* 273 */       logger.error("RetrievePdStatus failed.", e); }
/* 274 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\restful\PdCommonRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */