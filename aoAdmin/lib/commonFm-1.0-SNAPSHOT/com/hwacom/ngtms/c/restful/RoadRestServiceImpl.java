/*     */ package com.hwacom.ngtms.c.restful;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadDivision;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.c.shared.AreaType;
/*     */ import com.hwacom.ngtms.c.shared.DivisionType;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadParametersDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.BeanUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/road"})
/*     */ public class RoadRestServiceImpl
/*     */   extends BaseRestful
/*     */ {
/*  42 */   private static final Logger logger = LoggerFactory.getLogger(RoadRestServiceImpl.class);
/*     */   @Autowired
/*     */   private OpLogger opLogger;
/*     */   
/*     */   @RequestMapping(value = {"/roadLine"}, method = {RequestMethod.GET})
/*     */   public List<RoadLineDTO> getRoadLines() {
/*     */     try {
/*  49 */       logger.debug("Get road lines.");
/*  50 */       List<RoadLineDTO> list = new ArrayList<>();
/*  51 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*  52 */       if (roadLineMap != null) {
/*  53 */         for (RoadLine bean : roadLineMap.values()) {
/*  54 */           RoadLineDTO dto = new RoadLineDTO();
/*  55 */           BeanUtils.copyProperties(bean, dto, new String[] { "roadType" });
/*  56 */           dto.setGgCodeId(bean.getgCodeId());
/*  57 */           dto.setDirection(bean.getDirection());
/*  58 */           dto.setEnable(bean.isEnable());
/*  59 */           dto.setDirection(bean.getDirection());
/*  60 */           list.add(dto);
/*     */         } 
/*     */       } else {
/*  63 */         logger.error("roadLineMap is null.");
/*     */       } 
/*  65 */       return list;
/*  66 */     } catch (Exception e) {
/*  67 */       logger.error("Get road lines failed.", e);
/*  68 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/roadLine"}, method = {RequestMethod.POST})
/*     */   public Boolean addRoadLine(@RequestBody RoadParametersDTO params) {
/*  75 */     RoadLineDTO dto = params.getRoadLineDTO();
/*     */     try {
/*  77 */       logger.debug("Add road line, dto='{}'", dto);
/*  78 */       Boolean result = Boolean.TRUE;
/*  79 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*  80 */       if (roadLineMap != null) {
/*  81 */         RoadLine bean = (RoadLine)roadLineMap.get(dto.getLineId());
/*  82 */         if (bean == null) {
/*  83 */           bean = new RoadLine();
/*  84 */           BeanUtils.copyProperties(dto, bean, new String[] { "roadType" });
/*  85 */           bean.setgCodeId(dto.getGgCodeId());
/*  86 */           bean.setDirection(dto.getDirection());
/*  87 */           bean.setDirection(dto.getDirection());
/*  88 */           roadLineMap.put(bean.getLineId(), bean);
/*  89 */           logS("oplog.addRoadLine", dto.toString());
/*     */         } else {
/*  91 */           logger.error(dto.getLineId() + " key had existed! can not add it!");
/*  92 */           logF("oplog.addRoadLine.fail", dto.toString());
/*  93 */           return Boolean.FALSE;
/*     */         } 
/*     */       } else {
/*  96 */         logger.error("roadLineMap is null.");
/*  97 */         logF("oplog.addRoadLine.fail", dto.toString());
/*  98 */         return Boolean.FALSE;
/*     */       } 
/* 100 */       return result;
/* 101 */     } catch (Exception e) {
/* 102 */       logger.error("Add road line failed.", e);
/* 103 */       logF("oplog.addRoadLine.fail", dto.toString());
/* 104 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/roadLine/{lineId}"}, method = {RequestMethod.DELETE})
/*     */   public Boolean removeRoadLine(@PathVariable("lineId") String lineId) {
/*     */     try {
/* 112 */       logger.debug("Remove road line, key='{}'", lineId);
/* 113 */       Boolean result = Boolean.TRUE;
/* 114 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 115 */       if (roadLineMap != null) {
/* 116 */         RoadLine bean = (RoadLine)roadLineMap.get(lineId);
/* 117 */         if (bean != null) {
/* 118 */           roadLineMap.remove(lineId);
/* 119 */           logS("oplog.deleteRoadLine", lineId);
/*     */         } else {
/* 121 */           logger.error("key :" + lineId + " can not find the RoadLine Value!");
/* 122 */           logF("oplog.deleteRoadLine.fail", lineId);
/* 123 */           return Boolean.FALSE;
/*     */         } 
/*     */       } else {
/* 126 */         logger.error("roadLineMap is null.");
/* 127 */         logF("oplog.deleteRoadLine.fail", lineId);
/* 128 */         return Boolean.FALSE;
/*     */       } 
/* 130 */       return result;
/* 131 */     } catch (Exception e) {
/* 132 */       logger.error("Remove road line failed.", e);
/* 133 */       logF("oplog.deleteRoadLine.fail", lineId);
/* 134 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/roadLine"}, method = {RequestMethod.PUT})
/*     */   public Boolean saveRoadLine(@RequestBody RoadParametersDTO params) {
/* 141 */     RoadLineDTO dto = params.getRoadLineDTO();
/*     */     try {
/* 143 */       logger.debug("Save road line, dto='{}'", dto);
/* 144 */       Boolean result = Boolean.TRUE;
/* 145 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 146 */       if (roadLineMap != null) {
/* 147 */         RoadLine bean = (RoadLine)roadLineMap.get(dto.getLineId());
/* 148 */         if (bean != null) {
/* 149 */           BeanUtils.copyProperties(dto, bean, new String[] { "roadType" });
/* 150 */           bean.setgCodeId(dto.getGgCodeId());
/* 151 */           bean.setDirection(dto.getDirection());
/* 152 */           roadLineMap.put(bean.getLineId(), bean);
/* 153 */           logS("oplog.saveRoadLine", dto.getLineId());
/*     */         } else {
/* 155 */           logger.error("key :" + dto.getLineId() + " can not find the RoadLine Value!");
/* 156 */           logF("oplog.saveRoadLine.fail", dto.getLineId());
/* 157 */           return Boolean.FALSE;
/*     */         } 
/*     */       } else {
/* 160 */         logger.error("roadLineMap is null.");
/* 161 */         logF("oplog.saveRoadLine.fail", dto.getLineId());
/* 162 */         return Boolean.FALSE;
/*     */       } 
/* 164 */       return result;
/* 165 */     } catch (Exception e) {
/* 166 */       logger.error("Save road line failed.", e);
/* 167 */       logF("oplog.saveRoadLine.fail", dto.getLineId());
/* 168 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/roadDivision"}, method = {RequestMethod.GET})
/*     */   public List<RoadDivisionDTO> getRoadDivisions() {
/*     */     try {
/* 175 */       logger.debug("Get road divisions.");
/* 176 */       List<RoadDivisionDTO> list = new ArrayList<>();
/* 177 */       IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 178 */       if (roadDivisionMap != null) {
/* 179 */         IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 180 */         roadDivisionMap
/* 181 */           .values()
/* 182 */           .stream()
/* 183 */           .forEach(bean -> {
/*     */               RoadDivisionDTO dto = new RoadDivisionDTO();
/*     */               
/*     */               BeanUtils.copyProperties(bean, dto);
/*     */               dto.setAreaType(bean.getAreaType());
/*     */               dto.setDivisionType(bean.getDivisionType());
/*     */               if (paramIMap != null) {
/*     */                 for (RoadLine roadLine : paramIMap.values()) {
/*     */                   if (dto.getLineId().equals(roadLine.getLineId())) {
/*     */                     dto.setLineName(roadLine.getLineName());
/*     */                     break;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */               paramList.add(dto);
/*     */             });
/*     */       } else {
/* 200 */         logger.error("roadDivisionMap is null.");
/*     */       } 
/* 202 */       return list;
/* 203 */     } catch (Exception e) {
/* 204 */       logger.error("Get road divisions failed.", e);
/* 205 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/RoadDivision/{divisionType}"}, method = {RequestMethod.GET})
/*     */   public List<RoadDivisionDTO> getRoadDivisionsByDivisionType(@PathVariable("divisionType") DivisionType type) {
/*     */     try {
/* 214 */       logger.debug("Get road divisions by division type, type='{}'", type);
/* 215 */       List<RoadDivisionDTO> list = new ArrayList<>();
/* 216 */       IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 217 */       if (roadDivisionMap != null) {
/* 218 */         roadDivisionMap
/* 219 */           .values()
/* 220 */           .stream()
/* 221 */           .filter(bean -> (bean.getAreaType() == AreaType.C && bean.getDivisionType() == paramDivisionType))
/* 222 */           .forEach(bean -> {
/*     */               RoadDivisionDTO dto = new RoadDivisionDTO();
/*     */               
/*     */               BeanUtils.copyProperties(bean, dto);
/*     */             });
/*     */       } else {
/* 228 */         logger.error("roadDivisionMap is null!");
/*     */       } 
/* 230 */       return list;
/* 231 */     } catch (Exception e) {
/* 232 */       logger.error("Get road divisions by division type failed.", e);
/* 233 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/roadDivision"}, method = {RequestMethod.PUT})
/*     */   public Boolean saveRoadDivision(@RequestBody RoadParametersDTO params) {
/* 241 */     RoadDivisionDTO dto = params.getRoadDivisionDTO();
/*     */     try {
/* 243 */       logger.debug("Save road division, dto='{}'", dto);
/* 244 */       Boolean result = Boolean.TRUE;
/* 245 */       IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 246 */       if (roadDivisionMap != null) {
/* 247 */         RoadDivision bean = (RoadDivision)roadDivisionMap.get(dto.getDivisionId());
/* 248 */         if (bean != null) {
/* 249 */           BeanUtils.copyProperties(dto, bean);
/* 250 */           roadDivisionMap.put(bean.getDivisionId(), bean);
/* 251 */           logS("oplog.saveRoadDivision", dto.toString());
/*     */         } else {
/* 253 */           logger.error("key :" + dto.getDivisionId() + " can not find the RoadDivision Value!");
/* 254 */           logF("oplog.saveRoadDivision.fail", dto.toString());
/* 255 */           return Boolean.FALSE;
/*     */         } 
/*     */       } else {
/* 258 */         logger.error("roadDivisionMap is null!");
/* 259 */         logF("oplog.saveRoadDivision.fail", dto.toString());
/* 260 */         return Boolean.FALSE;
/*     */       } 
/* 262 */       return result;
/* 263 */     } catch (Exception e) {
/* 264 */       logger.error("Save road division failed.", e);
/* 265 */       logF("oplog.saveRoadDivision.fail", dto.toString());
/* 266 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/roadDivision"}, method = {RequestMethod.POST})
/*     */   public Boolean addRoadDivision(@RequestBody RoadParametersDTO params) {
/* 274 */     RoadDivisionDTO dto = params.getRoadDivisionDTO();
/*     */     try {
/* 276 */       logger.debug("Add road division, dto='{}'", dto);
/* 277 */       Boolean result = Boolean.TRUE;
/* 278 */       IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 279 */       if (roadDivisionMap != null) {
/* 280 */         RoadDivision bean = (RoadDivision)roadDivisionMap.get(dto.getDivisionId());
/* 281 */         if (bean == null) {
/* 282 */           bean = new RoadDivision();
/* 283 */           BeanUtils.copyProperties(dto, bean);
/* 284 */           roadDivisionMap.put(bean.getDivisionId(), bean);
/* 285 */           logS("oplog.addRoadDivision", dto.toString());
/*     */         } else {
/* 287 */           logger.error(dto.getLineId() + " key had existed! can not add it!");
/* 288 */           logF("oplog.addRoadDivision.fail", dto.toString());
/* 289 */           return Boolean.FALSE;
/*     */         } 
/*     */       } else {
/* 292 */         logger.error("RoadDivision is null!");
/* 293 */         logF("oplog.addRoadDivision.fail", dto.toString());
/* 294 */         return Boolean.FALSE;
/*     */       } 
/* 296 */       return result;
/* 297 */     } catch (Exception e) {
/* 298 */       logger.error(e.getMessage(), e);
/* 299 */       logF("oplog.addRoadDivision.fail", dto.toString());
/* 300 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/roadDivision/{divisionId:.+}"}, method = {RequestMethod.DELETE})
/*     */   public Boolean removeRoadDivision(@PathVariable("divisionId") String divisionId) {
/*     */     try {
/* 308 */       logger.debug("Remove road division, key='{}'", divisionId);
/* 309 */       Boolean result = Boolean.TRUE;
/* 310 */       IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 311 */       if (roadDivisionMap != null) {
/* 312 */         RoadDivision bean = (RoadDivision)roadDivisionMap.get(divisionId);
/* 313 */         if (bean != null) {
/* 314 */           roadDivisionMap.remove(divisionId);
/* 315 */           logS("oplog.deleteRoadDivision", divisionId);
/*     */         } else {
/* 317 */           logger.error("key :" + divisionId + " can not find the RoadDivision Value! So can not delete it!");
/*     */           
/* 319 */           logF("oplog.addRoadDivision.fail", divisionId);
/* 320 */           return Boolean.FALSE;
/*     */         } 
/*     */       } else {
/* 323 */         logger.error("roadDivisionMap is null!");
/* 324 */         logF("oplog.addRoadDivision.fail", divisionId);
/* 325 */         return Boolean.FALSE;
/*     */       } 
/* 327 */       return result;
/* 328 */     } catch (Exception e) {
/* 329 */       logger.error("Remove road division failed.", e);
/* 330 */       logF("oplog.addRoadDivision.fail", divisionId);
/* 331 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/roadSection"}, method = {RequestMethod.GET})
/*     */   public List<RoadSectionDTO> getRoadSections() {
/*     */     try {
/* 338 */       logger.debug("Get road sections.");
/* 339 */       List<RoadSectionDTO> list = new ArrayList<>();
/* 340 */       IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/* 341 */       if (roadSectionMap != null) {
/* 342 */         roadSectionMap
/* 343 */           .values()
/* 344 */           .stream()
/* 345 */           .forEach(bean -> {
/*     */               RoadSectionDTO dto = new RoadSectionDTO();
/*     */               
/*     */               BeanUtils.copyProperties(bean, dto);
/*     */               paramList.add(dto);
/*     */             });
/*     */       } else {
/* 352 */         logger.error("getRoadSections is null!");
/*     */       } 
/* 354 */       return list;
/* 355 */     } catch (Exception e) {
/* 356 */       logger.error("Get road sections failed.", e);
/* 357 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void logS(String messageId, String mesasge) {
/* 362 */     this.opLogger.addLogF(SubSystem.HCCE
/* 363 */         .getDefaultUserId(), 
/* 364 */         HzUtils.getLocalMemberIpAddress(), SubSystem.SMG, null, messageId, new Object[] { mesasge });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logF(String messageId, String mesasge) {
/* 372 */     this.opLogger.addLogF(SubSystem.HCCE
/* 373 */         .getDefaultUserId(), 
/* 374 */         HzUtils.getLocalMemberIpAddress(), SubSystem.SMG, null, messageId, new Object[] { mesasge });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\restful\RoadRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */