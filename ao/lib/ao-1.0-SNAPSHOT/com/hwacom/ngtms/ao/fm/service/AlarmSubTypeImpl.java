/*      */ package com.hwacom.ngtms.ao.fm.service;
/*      */ 
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*      */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*      */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*      */ import com.hwacom.ngtms.c.fm.service.AlarmSubType;
/*      */ import org.springframework.context.annotation.Profile;
/*      */ import org.springframework.stereotype.Service;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Service
/*      */ @Profile({"ao"})
/*      */ public class AlarmSubTypeImpl
/*      */   implements AlarmSubType
/*      */ {
/*      */   public Integer getHcFault() {
/*   23 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   24 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(37)) == null) {
/*   25 */       return null;
/*      */     }
/*   27 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(37))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMfccFault() {
/*   32 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   33 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(38)) == null) {
/*   34 */       return null;
/*      */     }
/*   36 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(38))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMfsvFault() {
/*   41 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   42 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(4)) == null) {
/*   43 */       return null;
/*      */     }
/*   45 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(4))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFiwsFault() {
/*   50 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   51 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(41)) == null) {
/*   52 */       return null;
/*      */     }
/*   54 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(41))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVsrvFault() {
/*   59 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   60 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(6)) == null) {
/*   61 */       return null;
/*      */     }
/*   63 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(6))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMcnsFault() {
/*   68 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   69 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(7)) == null) {
/*   70 */       return null;
/*      */     }
/*   72 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(7))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVsvFault() {
/*   77 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   78 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(8)) == null) {
/*   79 */       return null;
/*      */     }
/*   81 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(8))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVdFault() {
/*   86 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   87 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(24)) == null) {
/*   88 */       return null;
/*      */     }
/*   90 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(24))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getViFault() {
/*   95 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   96 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(27)) == null) {
/*   97 */       return null;
/*      */     }
/*   99 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(27))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRdFault() {
/*  104 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  105 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(11)) == null) {
/*  106 */       return null;
/*      */     }
/*  108 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(11))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWdFault() {
/*  113 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  114 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(12)) == null) {
/*  115 */       return null;
/*      */     }
/*  117 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(12))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidFault() {
/*  122 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  123 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(33)) == null) {
/*  124 */       return null;
/*      */     }
/*  126 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(33))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviFault() {
/*  131 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  132 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(26)) == null) {
/*  133 */       return null;
/*      */     }
/*  135 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(26))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLsFault() {
/*  140 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  141 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(15)) == null) {
/*  142 */       return null;
/*      */     }
/*  144 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(15))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBsFault() {
/*  149 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  150 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(16)) == null) {
/*  151 */       return null;
/*      */     }
/*  153 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(16))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsFault() {
/*  158 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  159 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(29)) == null) {
/*  160 */       return null;
/*      */     }
/*  162 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(29))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisFault() {
/*  167 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  168 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(18)) == null) {
/*  169 */       return null;
/*      */     }
/*  171 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(18))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsFault() {
/*  176 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  177 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(19)) == null) {
/*  178 */       return null;
/*      */     }
/*  180 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(19))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsFault() {
/*  185 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  186 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(20)) == null) {
/*  187 */       return null;
/*      */     }
/*  189 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(20))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmFault() {
/*  194 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  195 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(31)) == null) {
/*  196 */       return null;
/*      */     }
/*  198 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(31))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsFault() {
/*  203 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  204 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(28)) == null) {
/*  205 */       return null;
/*      */     }
/*  207 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(28))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsFault() {
/*  212 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsFault() {
/*  217 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  218 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(24)) == null) {
/*  219 */       return null;
/*      */     }
/*  221 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(24))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvFault() {
/*  226 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  227 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(32)) == null) {
/*  228 */       return null;
/*      */     }
/*  230 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(32))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtFault() {
/*  235 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  236 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(34)) == null) {
/*  237 */       return null;
/*      */     }
/*  239 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(34))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsFault() {
/*  244 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  245 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(30)) == null) {
/*  246 */       return null;
/*      */     }
/*  248 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(30))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasFault() {
/*  253 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  254 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(28)) == null) {
/*  255 */       return null;
/*      */     }
/*  257 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(28))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVpFault() {
/*  262 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  263 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(29)) == null) {
/*  264 */       return null;
/*      */     }
/*  266 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(29))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAccident() {
/*  271 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  272 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(6)) == null) {
/*  273 */       return null;
/*      */     }
/*  275 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(6))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getConstruction() {
/*  280 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  281 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(5)) == null) {
/*  282 */       return null;
/*      */     }
/*  284 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(5))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDebris() {
/*  289 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  290 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(7)) == null) {
/*  291 */       return null;
/*      */     }
/*  293 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(7))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRoadDamage() {
/*  298 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  299 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(8)) == null) {
/*  300 */       return null;
/*      */     }
/*  302 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(8))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFaultCar() {
/*  307 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  308 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(9)) == null) {
/*  309 */       return null;
/*      */     }
/*  311 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(9))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFire() {
/*  316 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  317 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(1)) == null) {
/*  318 */       return null;
/*      */     }
/*  320 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(1))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSurfaceWater() {
/*  325 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  326 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(36)) == null) {
/*  327 */       return null;
/*      */     }
/*  329 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(36))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHazardousMaterial() {
/*  334 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  335 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(2)) == null) {
/*  336 */       return null;
/*      */     }
/*  338 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(2))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMilitaryControl() {
/*  343 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  344 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(38)) == null) {
/*  345 */       return null;
/*      */     }
/*  347 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(38))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSecret() {
/*  352 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  353 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(39)) == null) {
/*  354 */       return null;
/*      */     }
/*  356 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(39))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getInjuries() {
/*  361 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  362 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(40)) == null) {
/*  363 */       return null;
/*      */     }
/*  365 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(40))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCongestion() {
/*  370 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  371 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(12)) == null) {
/*  372 */       return null;
/*      */     }
/*  374 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(12))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMetroNetwork() {
/*  379 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  380 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(42)) == null) {
/*  381 */       return null;
/*      */     }
/*  383 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(42))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getExitRampCongestion() {
/*  388 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  389 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(43)) == null) {
/*  390 */       return null;
/*      */     }
/*  392 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(43))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelCongestion() {
/*  397 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  398 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(44)) == null) {
/*  399 */       return null;
/*      */     }
/*  401 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(44))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFog() {
/*  406 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  407 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(15)) == null) {
/*  408 */       return null;
/*      */     }
/*  410 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(15))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getStrongWind() {
/*  415 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  416 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(46)) == null) {
/*  417 */       return null;
/*      */     }
/*  419 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(46))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHeavyRain() {
/*  424 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  425 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(14)) == null) {
/*  426 */       return null;
/*      */     }
/*  428 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(14))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getNetworkSwitchContorl() {
/*  433 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  434 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(48)) == null) {
/*  435 */       return null;
/*      */     }
/*  437 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(48))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRampControl() {
/*  442 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  443 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(49)) == null) {
/*  444 */       return null;
/*      */     }
/*  446 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(49))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCollapse() {
/*  451 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  452 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(17)) == null) {
/*  453 */       return null;
/*      */     }
/*  455 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(17))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBridgeSettlement() {
/*  460 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  461 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(51)) == null) {
/*  462 */       return null;
/*      */     }
/*  464 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(51))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWorseningTunnelAirQuality() {
/*  469 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  470 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(4)) == null) {
/*  471 */       return null;
/*      */     }
/*  473 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(4))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelLightingFailure() {
/*  478 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  479 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(11)) == null) {
/*  480 */       return null;
/*      */     }
/*  482 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(11))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelDistribution() {
/*  487 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  488 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(10)) == null) {
/*  489 */       return null;
/*      */     }
/*  491 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(10))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVehicleStopWait() {
/*  496 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  497 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(55)) == null) {
/*  498 */       return null;
/*      */     }
/*  500 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(55))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSmoke() {
/*  505 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  506 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(56)) == null) {
/*  507 */       return null;
/*      */     }
/*  509 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(56))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayVehical() {
/*  514 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  515 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(58)) == null) {
/*  516 */       return null;
/*      */     }
/*  518 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(58))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRouteGuidanceInfo() {
/*  523 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  524 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(59)) == null) {
/*  525 */       return null;
/*      */     }
/*  527 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(59))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelFire() {
/*  532 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  533 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(60)) == null) {
/*  534 */       return null;
/*      */     }
/*  536 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(60))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAirConditioningEqu() {
/*  541 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  542 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(62)) == null) {
/*  543 */       return null;
/*      */     }
/*  545 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(62))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPowerSystem() {
/*  550 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  551 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(63)) == null) {
/*  552 */       return null;
/*      */     }
/*  554 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(63))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFireAlarmSystem() {
/*  559 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  560 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(65)) == null) {
/*  561 */       return null;
/*      */     }
/*  563 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(65))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getThermoHygroMeter() {
/*  568 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  569 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(66)) == null) {
/*  570 */       return null;
/*      */     }
/*  572 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(66))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getGenerator() {
/*  577 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  578 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(67)) == null) {
/*  579 */       return null;
/*      */     }
/*  581 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(67))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSmr() {
/*  586 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  587 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(68)) == null) {
/*  588 */       return null;
/*      */     }
/*  590 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(68))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidStopWait() {
/*  595 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  596 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(18)) == null) {
/*  597 */       return null;
/*      */     }
/*  599 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(18))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidPedestrianDetection() {
/*  604 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  605 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(19)) == null) {
/*  606 */       return null;
/*      */     }
/*  608 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(19))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidDebris() {
/*  613 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  614 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(20)) == null) {
/*  615 */       return null;
/*      */     }
/*  617 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(20))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidWrongWayVehical() {
/*  622 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  623 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(21)) == null) {
/*  624 */       return null;
/*      */     }
/*  626 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(21))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidSmoke() {
/*  631 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  632 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(22)) == null) {
/*  633 */       return null;
/*      */     }
/*  635 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(22))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidCongestion() {
/*  640 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  641 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(75)) == null) {
/*  642 */       return null;
/*      */     }
/*  644 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(75))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelAccess() {
/*  649 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  650 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(76)) == null) {
/*  651 */       return null;
/*      */     }
/*  653 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(76))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getContactTunnelAccess() {
/*  658 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  659 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(77)) == null) {
/*  660 */       return null;
/*      */     }
/*  662 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(77))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsCompareError() {
/*  667 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  668 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(78)) == null) {
/*  669 */       return null;
/*      */     }
/*  671 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(78))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsCompareError() {
/*  676 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  677 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(61)) == null) {
/*  678 */       return null;
/*      */     }
/*  680 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(61))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasCompareError() {
/*  685 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  686 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(80)) == null) {
/*  687 */       return null;
/*      */     }
/*  689 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(80))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsCompareError() {
/*  694 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  695 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(62)) == null) {
/*  696 */       return null;
/*      */     }
/*  698 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(62))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisCompareError() {
/*  703 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  704 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(82)) == null) {
/*  705 */       return null;
/*      */     }
/*  707 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(82))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsCompareError() {
/*  712 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  713 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(83)) == null) {
/*  714 */       return null;
/*      */     }
/*  716 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(83))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsCompareError() {
/*  721 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  722 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(84)) == null) {
/*  723 */       return null;
/*      */     }
/*  725 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(84))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsCompareError() {
/*  730 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  731 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(63)) == null) {
/*  732 */       return null;
/*      */     }
/*  734 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(63))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsCompareError() {
/*  739 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  740 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(86)) == null) {
/*  741 */       return null;
/*      */     }
/*  743 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(86))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmCompareError() {
/*  748 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  749 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(64)) == null) {
/*  750 */       return null;
/*      */     }
/*  752 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(64))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemDisconnect() {
/*  757 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  758 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(100)) == null) {
/*  759 */       return null;
/*      */     }
/*  761 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(100))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVdDisconnect() {
/*  766 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  767 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(65)) == null) {
/*  768 */       return null;
/*      */     }
/*  770 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(65))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getViDisconnect() {
/*  775 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  776 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(68)) == null) {
/*  777 */       return null;
/*      */     }
/*  779 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(68))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRdDisconnect() {
/*  784 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  785 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(90)) == null) {
/*  786 */       return null;
/*      */     }
/*  788 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(90))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWdDisconnect() {
/*  793 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  794 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(91)) == null) {
/*  795 */       return null;
/*      */     }
/*  797 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(91))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidDiscoonect() {
/*  802 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  803 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(74)) == null) {
/*  804 */       return null;
/*      */     }
/*  806 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(74))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviDisconnect() {
/*  811 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  812 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(67)) == null) {
/*  813 */       return null;
/*      */     }
/*  815 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(67))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLsDisconnect() {
/*  820 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  821 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(94)) == null) {
/*  822 */       return null;
/*      */     }
/*  824 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(94))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBsDisconnect() {
/*  829 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  830 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(95)) == null) {
/*  831 */       return null;
/*      */     }
/*  833 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(95))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsDisconnect() {
/*  838 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  839 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(70)) == null) {
/*  840 */       return null;
/*      */     }
/*  842 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(70))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisDisconnect() {
/*  847 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  848 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(97)) == null) {
/*  849 */       return null;
/*      */     }
/*  851 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(97))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsDisconnect() {
/*  856 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  857 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(98)) == null) {
/*  858 */       return null;
/*      */     }
/*  860 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(98))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsDisconnect() {
/*  865 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  866 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(99)) == null) {
/*  867 */       return null;
/*      */     }
/*  869 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(99))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmDisconnect() {
/*  874 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  875 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(72)) == null) {
/*  876 */       return null;
/*      */     }
/*  878 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(72))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsDisconnect() {
/*  883 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  884 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(69)) == null) {
/*  885 */       return null;
/*      */     }
/*  887 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(69))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsDisconnect() {
/*  892 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  893 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(102)) == null) {
/*  894 */       return null;
/*      */     }
/*  896 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(102))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsDisconnect() {
/*  901 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  902 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(103)) == null) {
/*  903 */       return null;
/*      */     }
/*  905 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(103))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvDisconnect() {
/*  910 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  911 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(73)) == null) {
/*  912 */       return null;
/*      */     }
/*  914 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(73))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtDisconnect() {
/*  919 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  920 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(75)) == null) {
/*  921 */       return null;
/*      */     }
/*  923 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(75))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsDisconnect() {
/*  928 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  929 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(71)) == null) {
/*  930 */       return null;
/*      */     }
/*  932 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(71))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasDisconnect() {
/*  937 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  938 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(107)) == null) {
/*  939 */       return null;
/*      */     }
/*  941 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(107))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVdConnect() {
/*  946 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  947 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(108)) == null) {
/*  948 */       return null;
/*      */     }
/*  950 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(108))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getViConnect() {
/*  955 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  956 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(109)) == null) {
/*  957 */       return null;
/*      */     }
/*  959 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(109))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRdConnect() {
/*  964 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  965 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(110)) == null) {
/*  966 */       return null;
/*      */     }
/*  968 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(110))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWdConnect() {
/*  973 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  974 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(111)) == null) {
/*  975 */       return null;
/*      */     }
/*  977 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(111))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidConnect() {
/*  982 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  983 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(112)) == null) {
/*  984 */       return null;
/*      */     }
/*  986 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(112))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviConnect() {
/*  991 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  992 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(113)) == null) {
/*  993 */       return null;
/*      */     }
/*  995 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(113))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLsConnect() {
/* 1000 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1001 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(114)) == null) {
/* 1002 */       return null;
/*      */     }
/* 1004 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(114))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBsConnect() {
/* 1009 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1010 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(115)) == null) {
/* 1011 */       return null;
/*      */     }
/* 1013 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(115))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsConnect() {
/* 1018 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1019 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(116)) == null) {
/* 1020 */       return null;
/*      */     }
/* 1022 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(116))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisConnect() {
/* 1027 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1028 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(117)) == null) {
/* 1029 */       return null;
/*      */     }
/* 1031 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(117))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsConnect() {
/* 1036 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1037 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(118)) == null) {
/* 1038 */       return null;
/*      */     }
/* 1040 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(118))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsConnect() {
/* 1045 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1046 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(119)) == null) {
/* 1047 */       return null;
/*      */     }
/* 1049 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(119))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmConnect() {
/* 1054 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1055 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(120)) == null) {
/* 1056 */       return null;
/*      */     }
/* 1058 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(120))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsConnect() {
/* 1063 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1064 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(121)) == null) {
/* 1065 */       return null;
/*      */     }
/* 1067 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(121))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsConnect() {
/* 1072 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1073 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(122)) == null) {
/* 1074 */       return null;
/*      */     }
/* 1076 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(122))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsConnect() {
/* 1081 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1082 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(123)) == null) {
/* 1083 */       return null;
/*      */     }
/* 1085 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(123))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvConnect() {
/* 1090 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1091 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(124)) == null) {
/* 1092 */       return null;
/*      */     }
/* 1094 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(124))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtConnect() {
/* 1099 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1100 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(125)) == null) {
/* 1101 */       return null;
/*      */     }
/* 1103 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(125))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsConnect() {
/* 1108 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1109 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(126)) == null) {
/* 1110 */       return null;
/*      */     }
/* 1112 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(126))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasConnect() {
/* 1117 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1118 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(127)) == null) {
/* 1119 */       return null;
/*      */     }
/* 1121 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(127))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemConnect() {
/* 1126 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1127 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(128)) == null) {
/* 1128 */       return null;
/*      */     }
/* 1130 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(128))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAutoIncidentDetection() {
/* 1135 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1136 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(129)) == null) {
/* 1137 */       return null;
/*      */     }
/* 1139 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(129))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getInvalidTravelTime() {
/* 1144 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1145 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(130)) == null) {
/* 1146 */       return null;
/*      */     }
/* 1148 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(130))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTravelTimeOutrange() {
/* 1153 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1154 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(131)) == null) {
/* 1155 */       return null;
/*      */     }
/* 1157 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(131))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAVIRecognitionRateBelowThreshold() {
/* 1162 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1163 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(132)) == null) {
/* 1164 */       return null;
/*      */     }
/* 1166 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(132))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEntranceRampCongestion() {
/* 1171 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1172 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(133)) == null) {
/* 1173 */       return null;
/*      */     }
/* 1175 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(133))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEarthquake() {
/* 1180 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1181 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(16)) == null) {
/* 1182 */       return null;
/*      */     }
/* 1184 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(16))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelFireInterlocking() {
/* 1189 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1190 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(135)) == null) {
/* 1191 */       return null;
/*      */     }
/* 1193 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(135))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCongestionSpeedLlimitControl() {
/* 1198 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1199 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(136)) == null) {
/* 1200 */       return null;
/*      */     }
/* 1202 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(136))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLevel2Congestion() {
/* 1207 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1208 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(137)) == null) {
/* 1209 */       return null;
/*      */     }
/* 1211 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(137))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtIncomingCall() {
/* 1216 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1217 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(23)) == null) {
/* 1218 */       return null;
/*      */     }
/* 1220 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(23))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelTrafficRegulate() {
/* 1225 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1226 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(141)) == null) {
/* 1227 */       return null;
/*      */     }
/* 1229 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(141))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLocalTrafficResponse() {
/* 1234 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1235 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(142)) == null) {
/* 1236 */       return null;
/*      */     }
/* 1238 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(142))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAutoEventDectionEnd() {
/* 1243 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1244 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(143)) == null) {
/* 1245 */       return null;
/*      */     }
/* 1247 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(143))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsManualMessageEnd() {
/* 1252 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1253 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(144)) == null) {
/* 1254 */       return null;
/*      */     }
/* 1256 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(144))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsManualMessageEnd() {
/* 1261 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1262 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(145)) == null) {
/* 1263 */       return null;
/*      */     }
/* 1265 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(145))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsManualMessageEnd() {
/* 1270 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1271 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(146)) == null) {
/* 1272 */       return null;
/*      */     }
/* 1274 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(146))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmManualMessageEnd() {
/* 1279 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1280 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(147)) == null) {
/* 1281 */       return null;
/*      */     }
/* 1283 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(147))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsManualMessageEnd() {
/* 1288 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1289 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(148)) == null) {
/* 1290 */       return null;
/*      */     }
/* 1292 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(148))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsManualMessageEnd() {
/* 1297 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1298 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(149)) == null) {
/* 1299 */       return null;
/*      */     }
/* 1301 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(149))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisManualMessageEnd() {
/* 1306 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1307 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(150)) == null) {
/* 1308 */       return null;
/*      */     }
/* 1310 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(150))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsManualMessageEnd() {
/* 1315 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1316 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(151)) == null) {
/* 1317 */       return null;
/*      */     }
/* 1319 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(151))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsManualMessageEnd() {
/* 1324 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1325 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(152)) == null) {
/* 1326 */       return null;
/*      */     }
/* 1328 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(152))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasManualMessageEnd() {
/* 1333 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1334 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(153)) == null) {
/* 1335 */       return null;
/*      */     }
/* 1337 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(153))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getServiceAreaCongestion() {
/* 1342 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1343 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(154)) == null) {
/* 1344 */       return null;
/*      */     }
/* 1346 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(154))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMachineRoomAccess() {
/* 1351 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1352 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(155)) == null) {
/* 1353 */       return null;
/*      */     }
/* 1355 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(155))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidDisconnect() {
/* 1360 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1361 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(84)) == null) {
/* 1362 */       return null;
/*      */     }
/* 1364 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(84))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKqcDisconnect() {
/* 1369 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1370 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(157)) == null) {
/* 1371 */       return null;
/*      */     }
/* 1373 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(157))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPowerCordStolen() {
/* 1378 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1379 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(158)) == null) {
/* 1380 */       return null;
/*      */     }
/* 1382 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(158))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAutoImageDetection() {
/* 1387 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1388 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(165)) == null) {
/* 1389 */       return null;
/*      */     }
/* 1391 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(165))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getT74RouteGuidance() {
/* 1396 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1397 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(173)) == null) {
/* 1398 */       return null;
/*      */     }
/* 1400 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(173))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagDisconnect() {
/* 1405 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1406 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(66)) == null) {
/* 1407 */       return null;
/*      */     }
/* 1409 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(66))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagConnect() {
/* 1414 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1415 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(175)) == null) {
/* 1416 */       return null;
/*      */     }
/* 1418 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(175))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCooperationCenterEvent() {
/* 1423 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1424 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(176)) == null) {
/* 1425 */       return null;
/*      */     }
/* 1427 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(176))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKqcConnect() {
/* 1432 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1433 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(177)) == null) {
/* 1434 */       return null;
/*      */     }
/* 1436 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(177))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagRecongnitionRatebelowThreshold() {
/* 1441 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1442 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(178)) == null) {
/* 1443 */       return null;
/*      */     }
/* 1445 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(178))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOpenLaneside() {
/* 1450 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1451 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(179)) == null) {
/* 1452 */       return null;
/*      */     }
/* 1454 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(179))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayCar() {
/* 1459 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1460 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(180)) == null) {
/* 1461 */       return null;
/*      */     }
/* 1463 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(180))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayMotor() {
/* 1468 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1469 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(181)) == null) {
/* 1470 */       return null;
/*      */     }
/* 1472 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(181))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayPeople() {
/* 1477 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1478 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(182)) == null) {
/* 1479 */       return null;
/*      */     }
/* 1481 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(182))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagSurfaceRoadCongestion() {
/* 1486 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1487 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(183)) == null) {
/* 1488 */       return null;
/*      */     }
/* 1490 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(183))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDcChargerBattery() {
/* 1495 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1496 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(185)) == null) {
/* 1497 */       return null;
/*      */     }
/* 1499 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(185))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAcupsOpStatus() {
/* 1504 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1505 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(186)) == null) {
/* 1506 */       return null;
/*      */     }
/* 1508 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(186))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVerticalDistributionCabinet() {
/* 1513 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1514 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(187)) == null) {
/* 1515 */       return null;
/*      */     }
/* 1517 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(187))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTanklevel() {
/* 1522 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1523 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(189)) == null) {
/* 1524 */       return null;
/*      */     }
/* 1526 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(189))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPaPowerAmplifier() {
/* 1531 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1532 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(190)) == null) {
/* 1533 */       return null;
/*      */     }
/* 1535 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(190))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPaVoiceAmplifier() {
/* 1540 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1541 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(191)) == null) {
/* 1542 */       return null;
/*      */     }
/* 1544 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(191))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPaRelayAmplifier() {
/* 1549 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1550 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(192)) == null) {
/* 1551 */       return null;
/*      */     }
/* 1553 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(192))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFmTransmitter() {
/* 1558 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1559 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(193)) == null) {
/* 1560 */       return null;
/*      */     }
/* 1562 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(193))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfTransmitterOutput() {
/* 1567 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1568 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(194)) == null) {
/* 1569 */       return null;
/*      */     }
/* 1571 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(194))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfTransmitterReflect() {
/* 1576 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1577 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(195)) == null) {
/* 1578 */       return null;
/*      */     }
/* 1580 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(195))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfTransmitterOutput() {
/* 1585 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1586 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(196)) == null) {
/* 1587 */       return null;
/*      */     }
/* 1589 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(196))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfTransmitterReflect() {
/* 1594 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1595 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(197)) == null) {
/* 1596 */       return null;
/*      */     }
/* 1598 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(197))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfRelayOutput1() {
/* 1603 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1604 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(198)) == null) {
/* 1605 */       return null;
/*      */     }
/* 1607 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(198))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfRelayOutput2() {
/* 1612 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1613 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(199)) == null) {
/* 1614 */       return null;
/*      */     }
/* 1616 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(199))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfRelayPower() {
/* 1621 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1622 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(200)) == null) {
/* 1623 */       return null;
/*      */     }
/* 1625 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(200))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfRelayOutput1() {
/* 1630 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1631 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(201)) == null) {
/* 1632 */       return null;
/*      */     }
/* 1634 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(201))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfRelayOutput2() {
/* 1639 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1640 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(202)) == null) {
/* 1641 */       return null;
/*      */     }
/* 1643 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(202))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfRelayPower() {
/* 1648 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1649 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(203)) == null) {
/* 1650 */       return null;
/*      */     }
/* 1652 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(203))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdStatus() {
/* 1657 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1658 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(204)) == null) {
/* 1659 */       return null;
/*      */     }
/* 1661 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(204))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidInvalidSectionTrafficData() {
/* 1666 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1667 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(205)) == null) {
/* 1668 */       return null;
/*      */     }
/* 1670 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(205))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidInvalidPDCPSectionTrafficData() {
/* 1675 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1676 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(206)) == null) {
/* 1677 */       return null;
/*      */     }
/* 1679 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(206))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScsCompareError() {
/* 1684 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1685 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(207)) == null) {
/* 1686 */       return null;
/*      */     }
/* 1688 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(207))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidSIGFlowRate() {
/* 1693 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1694 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(208)) == null) {
/* 1695 */       return null;
/*      */     }
/* 1697 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(208))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidSIGCongestionLevel() {
/* 1702 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1703 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(209)) == null) {
/* 1704 */       return null;
/*      */     }
/* 1706 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(209))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmsInvalidControlRoomMonitorData() {
/* 1711 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1712 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(210)) == null) {
/* 1713 */       return null;
/*      */     }
/* 1715 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(210))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmsInvalidPDSurveillanceData() {
/* 1720 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1721 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(211)) == null) {
/* 1722 */       return null;
/*      */     }
/* 1724 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(211))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmsInvalidPDStructureData() {
/* 1729 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1730 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(212)) == null) {
/* 1731 */       return null;
/*      */     }
/* 1733 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(212))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviationLight() {
/* 1738 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1739 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(213)) == null) {
/* 1740 */       return null;
/*      */     }
/* 1742 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(213))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHumanSensor() {
/* 1747 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1748 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(214)) == null) {
/* 1749 */       return null;
/*      */     }
/* 1751 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(214))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLighting() {
/* 1756 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1757 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(215)) == null) {
/* 1758 */       return null;
/*      */     }
/* 1760 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(215))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHostDriveMoreThan() {
/* 1765 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1766 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(216)) == null) {
/* 1767 */       return null;
/*      */     }
/* 1769 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(216))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCardFault() {
/* 1774 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1775 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(217)) == null) {
/* 1776 */       return null;
/*      */     }
/* 1778 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(217))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDgpFault() {
/* 1783 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1784 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(218)) == null) {
/* 1785 */       return null;
/*      */     }
/* 1787 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(218))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getElseFault() {
/* 1792 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1793 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(219)) == null) {
/* 1794 */       return null;
/*      */     }
/* 1796 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(219))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtcFault() {
/* 1801 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1802 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(220)) == null) {
/* 1803 */       return null;
/*      */     }
/* 1805 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(220))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPcFault() {
/* 1810 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1811 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(221)) == null) {
/* 1812 */       return null;
/*      */     }
/* 1814 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(221))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPrinterFault() {
/* 1819 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1820 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(47)) == null) {
/* 1821 */       return null;
/*      */     }
/* 1823 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(47))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRouterFault() {
/* 1828 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1829 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(223)) == null) {
/* 1830 */       return null;
/*      */     }
/* 1832 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(223))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSvwsFault() {
/* 1837 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1838 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(52)) == null) {
/* 1839 */       return null;
/*      */     }
/* 1841 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(52))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSwitchFault() {
/* 1846 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1847 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(225)) == null) {
/* 1848 */       return null;
/*      */     }
/* 1850 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(225))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTdscFault() {
/* 1855 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1856 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(226)) == null) {
/* 1857 */       return null;
/*      */     }
/* 1859 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(226))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTisvFault() {
/* 1864 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1865 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(43)) == null) {
/* 1866 */       return null;
/*      */     }
/* 1868 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(43))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWmsFault() {
/* 1873 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1874 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(228)) == null) {
/* 1875 */       return null;
/*      */     }
/* 1877 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(228))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEttuFault() {
/* 1882 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1883 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(51)) == null) {
/* 1884 */       return null;
/*      */     }
/* 1886 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(51))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLocFault() {
/* 1891 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1892 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(230)) == null) {
/* 1893 */       return null;
/*      */     }
/* 1895 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(230))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRoomFault() {
/* 1900 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1901 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(231)) == null) {
/* 1902 */       return null;
/*      */     }
/* 1904 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(231))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMwsFault() {
/* 1909 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1910 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(232)) == null) {
/* 1911 */       return null;
/*      */     }
/* 1913 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(232))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAvsFault() {
/* 1918 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1919 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(46)) == null) {
/* 1920 */       return null;
/*      */     }
/* 1922 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(46))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMsFault() {
/* 1927 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1928 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(234)) == null) {
/* 1929 */       return null;
/*      */     }
/* 1931 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(234))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKqcsFault() {
/* 1936 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1937 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(235)) == null) {
/* 1938 */       return null;
/*      */     }
/* 1940 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(235))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRssFault() {
/* 1945 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1946 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(236)) == null) {
/* 1947 */       return null;
/*      */     }
/* 1949 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(236))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCchmisFault() {
/* 1954 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1955 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(237)) == null) {
/* 1956 */       return null;
/*      */     }
/* 1958 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(237))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVfvchFault() {
/* 1963 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1964 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(238)) == null) {
/* 1965 */       return null;
/*      */     }
/* 1967 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(238))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBhfault() {
/* 1972 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1973 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(239)) == null) {
/* 1974 */       return null;
/*      */     }
/* 1976 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(239))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBhraidFault() {
/* 1981 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1982 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(240)) == null) {
/* 1983 */       return null;
/*      */     }
/* 1985 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(240))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUpsFault() {
/* 1990 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1991 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(241)) == null) {
/* 1992 */       return null;
/*      */     }
/* 1994 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(241))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKvmFault() {
/* 1999 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2000 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(242)) == null) {
/* 2001 */       return null;
/*      */     }
/* 2003 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(242))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBcFault() {
/* 2008 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2009 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(243)) == null) {
/* 2010 */       return null;
/*      */     }
/* 2012 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(243))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvHFault() {
/* 2017 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2018 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(244)) == null) {
/* 2019 */       return null;
/*      */     }
/* 2021 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(244))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIbSFault() {
/* 2026 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2027 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(245)) == null) {
/* 2028 */       return null;
/*      */     }
/* 2030 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(245))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIIbSFault() {
/* 2035 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2036 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(50)) == null) {
/* 2037 */       return null;
/*      */     }
/* 2039 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(50))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHmiSFault() {
/* 2044 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2045 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(247)) == null) {
/* 2046 */       return null;
/*      */     }
/* 2048 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(247))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPHmiWFault() {
/* 2053 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2054 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(248)) == null) {
/* 2055 */       return null;
/*      */     }
/* 2057 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(248))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCfwCEFault() {
/* 2062 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2063 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(249)) == null) {
/* 2064 */       return null;
/*      */     }
/* 2066 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(249))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBhSwitchFault() {
/* 2071 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2072 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(250)) == null) {
/* 2073 */       return null;
/*      */     }
/* 2075 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(250))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWsFault() {
/* 2080 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2081 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(251)) == null) {
/* 2082 */       return null;
/*      */     }
/* 2084 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(251))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getServerFault() {
/* 2089 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2090 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(252)) == null) {
/* 2091 */       return null;
/*      */     }
/* 2093 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(252))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFireWallFault() {
/* 2098 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2099 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(253)) == null) {
/* 2100 */       return null;
/*      */     }
/* 2102 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(253))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRIbSFault() {
/* 2107 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2108 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(49)) == null) {
/* 2109 */       return null;
/*      */     }
/* 2111 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(49))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRaidFault() {
/* 2116 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2117 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(255)) == null) {
/* 2118 */       return null;
/*      */     }
/* 2120 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(255))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTimeSFault() {
/* 2125 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2126 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(256)) == null) {
/* 2127 */       return null;
/*      */     }
/* 2129 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(256))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSsoSFault() {
/* 2134 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2135 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(257)) == null) {
/* 2136 */       return null;
/*      */     }
/* 2138 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(257))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getNasFault() {
/* 2143 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2144 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(45)) == null) {
/* 2145 */       return null;
/*      */     }
/* 2147 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(45))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMulticongestion() {
/* 2152 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2153 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(259)) == null) {
/* 2154 */       return null;
/*      */     }
/* 2156 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(259))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdRstStatus() {
/* 2161 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2162 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(260)) == null) {
/* 2163 */       return null;
/*      */     }
/* 2165 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(260))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOdhDisconnect() {
/* 2170 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2171 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(77)) == null) {
/* 2172 */       return null;
/*      */     }
/* 2174 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(77))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBycycle() {
/* 2179 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2180 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(262)) == null) {
/* 2181 */       return null;
/*      */     }
/* 2183 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(262))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmmFault() {
/* 2188 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2189 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(44)) == null) {
/* 2190 */       return null;
/*      */     }
/* 2192 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(44))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagFault() {
/* 2197 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2198 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(25)) == null) {
/* 2199 */       return null;
/*      */     }
/* 2201 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(25))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHddbsvFault() {
/* 2206 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2207 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(40)) == null) {
/* 2208 */       return null;
/*      */     }
/* 2210 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(40))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOdhFault() {
/* 2215 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2216 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(36)) == null) {
/* 2217 */       return null;
/*      */     }
/* 2219 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(36))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOldbsvFault() {
/* 2224 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2225 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(39)) == null) {
/* 2226 */       return null;
/*      */     }
/* 2228 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(39))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdFault() {
/* 2233 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2234 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(35)) == null) {
/* 2235 */       return null;
/*      */     }
/* 2237 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(35))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSeriousAccident() {
/* 2242 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2243 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(3)) == null) {
/* 2244 */       return null;
/*      */     }
/* 2246 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(3))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidFault() {
/* 2251 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2252 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(43)) == null) {
/* 2253 */       return null;
/*      */     }
/* 2255 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(43))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTyphoon() {
/* 2260 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2261 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(13)) == null) {
/* 2262 */       return null;
/*      */     }
/* 2264 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(13))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAvsDisconnect() {
/* 2269 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2270 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(87)) == null) {
/* 2271 */       return null;
/*      */     }
/* 2273 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(87))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCrsvDisconnect() {
/* 2278 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2279 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(98)) == null) {
/* 2280 */       return null;
/*      */     }
/* 2282 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(98))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCrsvFault() {
/* 2287 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2288 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(57)) == null) {
/* 2289 */       return null;
/*      */     }
/* 2291 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(57))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCrwsDisconnect() {
/* 2296 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2297 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(99)) == null) {
/* 2298 */       return null;
/*      */     }
/* 2300 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(99))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCrwsFault() {
/* 2305 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2306 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(58)) == null) {
/* 2307 */       return null;
/*      */     }
/* 2309 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(58))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmmsDisconnect() {
/* 2314 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2315 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(85)) == null) {
/* 2316 */       return null;
/*      */     }
/* 2318 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(85))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEttuDisconnect() {
/* 2323 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2324 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(92)) == null) {
/* 2325 */       return null;
/*      */     }
/* 2327 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(92))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFiwsDisconnect() {
/* 2332 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2333 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(82)) == null) {
/* 2334 */       return null;
/*      */     }
/* 2336 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(82))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHcDisconnect() {
/* 2341 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2342 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(78)) == null) {
/* 2343 */       return null;
/*      */     }
/* 2345 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(78))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHddbDisconnect() {
/* 2350 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2351 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(81)) == null) {
/* 2352 */       return null;
/*      */     }
/* 2354 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(81))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIibsDisconnect() {
/* 2359 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2360 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(91)) == null) {
/* 2361 */       return null;
/*      */     }
/* 2363 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(91))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidsvDisconnect() {
/* 2368 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2369 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(96)) == null) {
/* 2370 */       return null;
/*      */     }
/* 2372 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(96))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidsvFault() {
/* 2377 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2378 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(55)) == null) {
/* 2379 */       return null;
/*      */     }
/* 2381 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(55))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidwsDisconnect() {
/* 2386 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2387 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(97)) == null) {
/* 2388 */       return null;
/*      */     }
/* 2390 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(97))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidwsFault() {
/* 2395 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2396 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(56)) == null) {
/* 2397 */       return null;
/*      */     }
/* 2399 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(56))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getImwsDisconnect() {
/* 2404 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2405 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(83)) == null) {
/* 2406 */       return null;
/*      */     }
/* 2408 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(83))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMfccDisconnect() {
/* 2413 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2414 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(79)) == null) {
/* 2415 */       return null;
/*      */     }
/* 2417 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(79))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getNasDisconnect() {
/* 2422 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2423 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(86)) == null) {
/* 2424 */       return null;
/*      */     }
/* 2426 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(86))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOldbDisconnect() {
/* 2431 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2432 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(80)) == null) {
/* 2433 */       return null;
/*      */     }
/* 2435 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(80))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdDisconnect() {
/* 2440 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2441 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(76)) == null) {
/* 2442 */       return null;
/*      */     }
/* 2444 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(76))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPrtDisconnect() {
/* 2449 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2450 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(88)) == null) {
/* 2451 */       return null;
/*      */     }
/* 2453 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(88))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRibsDisconnect() {
/* 2458 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2459 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(90)) == null) {
/* 2460 */       return null;
/*      */     }
/* 2462 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(90))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRwsDisconnect() {
/* 2467 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2468 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(95)) == null) {
/* 2469 */       return null;
/*      */     }
/* 2471 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(95))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRwsFault() {
/* 2476 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2477 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(54)) == null) {
/* 2478 */       return null;
/*      */     }
/* 2480 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(54))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSalarmDisconnect() {
/* 2485 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2486 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(89)) == null) {
/* 2487 */       return null;
/*      */     }
/* 2489 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(89))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSalarmFault() {
/* 2494 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2495 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(48)) == null) {
/* 2496 */       return null;
/*      */     }
/* 2498 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(48))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSvwsDisconnect() {
/* 2503 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2504 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(93)) == null) {
/* 2505 */       return null;
/*      */     }
/* 2507 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(93))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTbsDisconnect() {
/* 2512 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2513 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(94)) == null) {
/* 2514 */       return null;
/*      */     }
/* 2516 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(94))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTbsFault() {
/* 2521 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2522 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(53)) == null) {
/* 2523 */       return null;
/*      */     }
/* 2525 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(53))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTesvDisconnect() {
/* 2530 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2531 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(100)) == null) {
/* 2532 */       return null;
/*      */     }
/* 2534 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(100))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTesvFault() {
/* 2539 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2540 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(59)) == null) {
/* 2541 */       return null;
/*      */     }
/* 2543 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(59))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTewsDisconnect() {
/* 2548 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2549 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(101)) == null) {
/* 2550 */       return null;
/*      */     }
/* 2552 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(101))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTewsFault() {
/* 2557 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2558 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(60)) == null) {
/* 2559 */       return null;
/*      */     }
/* 2561 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(60))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTisvDisconnect() {
/* 2566 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2567 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(84)) == null) {
/* 2568 */       return null;
/*      */     }
/* 2570 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(84))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdConnect() {
/* 2575 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2576 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(102)) == null) {
/* 2577 */       return null;
/*      */     }
/* 2579 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(102))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdDoorOpen() {
/* 2584 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2585 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(103)) == null) {
/* 2586 */       return null;
/*      */     }
/* 2588 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(103))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPrimaryRFault() {
/* 2593 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2594 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(104)) == null) {
/* 2595 */       return null;
/*      */     }
/* 2597 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(104))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPrimarySFault() {
/* 2602 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2603 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(105)) == null) {
/* 2604 */       return null;
/*      */     }
/* 2606 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(105))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPrimaryTFault() {
/* 2611 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2612 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(106)) == null) {
/* 2613 */       return null;
/*      */     }
/* 2615 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(106))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSecondaryRFault() {
/* 2620 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2621 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(107)) == null) {
/* 2622 */       return null;
/*      */     }
/* 2624 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(107))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSecondarySFault() {
/* 2629 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2630 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(108)) == null) {
/* 2631 */       return null;
/*      */     }
/* 2633 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(108))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSecondaryTFault() {
/* 2638 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2639 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(109)) == null) {
/* 2640 */       return null;
/*      */     }
/* 2642 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(109))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLoop1Fault() {
/* 2647 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2648 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(110)) == null) {
/* 2649 */       return null;
/*      */     }
/* 2651 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(110))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLoop2Fault() {
/* 2656 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2657 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(111)) == null) {
/* 2658 */       return null;
/*      */     }
/* 2660 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(111))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLoop3Fault() {
/* 2665 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2666 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(112)) == null) {
/* 2667 */       return null;
/*      */     }
/* 2669 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(112))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLoop4Fault() {
/* 2674 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2675 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(113)) == null) {
/* 2676 */       return null;
/*      */     }
/* 2678 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(113))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLoop5Fault() {
/* 2683 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2684 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(114)) == null) {
/* 2685 */       return null;
/*      */     }
/* 2687 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(114))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getACUPSFault() {
/* 2692 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2693 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(133)) == null) {
/* 2694 */       return null;
/*      */     }
/* 2696 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(133))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAirConditionFault() {
/* 2701 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2702 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(130)) == null) {
/* 2703 */       return null;
/*      */     }
/* 2705 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(130))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFireAlarmFault() {
/* 2710 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2711 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(131)) == null) {
/* 2712 */       return null;
/*      */     }
/* 2714 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(131))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFlashlightFault() {
/* 2719 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2720 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(127)) == null) {
/* 2721 */       return null;
/*      */     }
/* 2723 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(127))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHumidityLowerLimitFault() {
/* 2728 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2729 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(119)) == null) {
/* 2730 */       return null;
/*      */     }
/* 2732 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(119))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHumidityUpperLimitFault() {
/* 2737 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2738 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(118)) == null) {
/* 2739 */       return null;
/*      */     }
/* 2741 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(118))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOilTankLowerLimitFault() {
/* 2746 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2747 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(125)) == null) {
/* 2748 */       return null;
/*      */     }
/* 2750 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(125))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOilTankUpperLimitFault() {
/* 2755 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2756 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(124)) == null) {
/* 2757 */       return null;
/*      */     }
/* 2759 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(124))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPDUFault() {
/* 2764 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2765 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(132)) == null) {
/* 2766 */       return null;
/*      */     }
/* 2768 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(132))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPowerSwitchFault() {
/* 2773 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2774 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(128)) == null) {
/* 2775 */       return null;
/*      */     }
/* 2777 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(128))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRoomDoorFault() {
/* 2782 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2783 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(129)) == null) {
/* 2784 */       return null;
/*      */     }
/* 2786 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(129))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemperatureLowerLimitFault() {
/* 2791 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2792 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(116)) == null) {
/* 2793 */       return null;
/*      */     }
/* 2795 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(116))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemperatureUpperLimitFault() {
/* 2800 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2801 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(115)) == null) {
/* 2802 */       return null;
/*      */     }
/* 2804 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(115))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVoltageLowerLimitFault() {
/* 2809 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2810 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(122)) == null) {
/* 2811 */       return null;
/*      */     }
/* 2813 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(122))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVoltageUpperLimitFault() {
/* 2818 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2819 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(121)) == null) {
/* 2820 */       return null;
/*      */     }
/* 2822 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(121))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHumidityConnect() {
/* 2827 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2828 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(120)) == null) {
/* 2829 */       return null;
/*      */     }
/* 2831 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(120))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOilTankConnect() {
/* 2836 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2837 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(126)) == null) {
/* 2838 */       return null;
/*      */     }
/* 2840 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(126))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemperatureConnect() {
/* 2845 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2846 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(117)) == null) {
/* 2847 */       return null;
/*      */     }
/* 2849 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(117))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVoltageConnect() {
/* 2854 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2855 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(123)) == null) {
/* 2856 */       return null;
/*      */     }
/* 2858 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(123))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFireAlarmButton() {
/* 2863 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2864 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(134)) == null) {
/* 2865 */       return null;
/*      */     }
/* 2867 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(134))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemMistSystem() {
/* 2872 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2873 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(135)) == null) {
/* 2874 */       return null;
/*      */     }
/* 2876 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(135))).getId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getUdEvent() {
/* 2882 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCCSConfigCompareError() {
/* 2887 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2888 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(160)) == null) {
/* 2889 */       return null;
/*      */     }
/* 2891 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(160))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvwsDisconnect() {
/* 2896 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2897 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(161)) == null) {
/* 2898 */       return null;
/*      */     }
/* 2900 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(161))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDgpDisconnect() {
/* 2905 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2906 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(162)) == null) {
/* 2907 */       return null;
/*      */     }
/* 2909 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(162))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHmisDisconnect() {
/* 2914 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2915 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(163)) == null) {
/* 2916 */       return null;
/*      */     }
/* 2918 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(163))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPjsvDisconnect() {
/* 2923 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2924 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(164)) == null) {
/* 2925 */       return null;
/*      */     }
/* 2927 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(164))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSalmDisconnect() {
/* 2932 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2933 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(89)) == null) {
/* 2934 */       return null;
/*      */     }
/* 2936 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(89))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTwsDisconnect() {
/* 2941 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2942 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(165)) == null) {
/* 2943 */       return null;
/*      */     }
/* 2945 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(165))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWmdcDisconnect() {
/* 2950 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2951 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(166)) == null) {
/* 2952 */       return null;
/*      */     }
/* 2954 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(166))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDisconnectAlarmSubType(String deviceType) {
/* 2959 */     if (deviceType == null) {
/* 2960 */       return null;
/*      */     }
/* 2962 */     switch (deviceType) {
/*      */       case "CARD":
/* 2964 */         return getCardFault();
/*      */       case "DBSV":
/* 2966 */         return getOldbsvFault();
/*      */       case "DGP":
/* 2968 */         return getDgpDisconnect();
/*      */       case "ELSE":
/* 2970 */         return getElseFault();
/*      */       case "ETC":
/* 2972 */         return getEtcFault();
/*      */       case "FIWS":
/* 2974 */         return getFiwsDisconnect();
/*      */       case "HC":
/* 2976 */         return getHcDisconnect();
/*      */       case "KQC":
/* 2978 */         return getKqcDisconnect();
/*      */       case "MCNS":
/* 2980 */         return getMcnsFault();
/*      */       case "MFCC":
/* 2982 */         return getMfccDisconnect();
/*      */       case "MFSV":
/* 2984 */         return getMfsvFault();
/*      */       case "PC":
/* 2986 */         return getPcFault();
/*      */       case "PRINTER":
/* 2988 */         return getPrinterFault();
/*      */       case "ROUTER":
/* 2990 */         return getRouterFault();
/*      */       case "SCM":
/* 2992 */         return getScmFault();
/*      */       case "SVWS":
/* 2994 */         return getSvwsDisconnect();
/*      */       case "SWITCH":
/* 2996 */         return getSwitchFault();
/*      */       case "TDSC":
/* 2998 */         return getTdscFault();
/*      */       case "TEM":
/* 3000 */         return getTemDisconnect();
/*      */       case "TID":
/* 3002 */         return getTidDisconnect();
/*      */       case "TISV":
/* 3004 */         return getTisvDisconnect();
/*      */       case "VP":
/* 3006 */         return getVpFault();
/*      */       case "VSRV":
/* 3008 */         return getVsrvFault();
/*      */       case "VSV":
/* 3010 */         return getVsvFault();
/*      */       case "WMS":
/* 3012 */         return getWmsFault();
/*      */       case "ETTU":
/* 3014 */         return getEttuFault();
/*      */       case "LOC":
/* 3016 */         return getLocFault();
/*      */       case "ROOM":
/* 3018 */         return getRoomFault();
/*      */       case "MWS":
/* 3020 */         return getMwsFault();
/*      */       case "AVS":
/* 3022 */         return getAvsDisconnect();
/*      */       case "MS":
/* 3024 */         return getMsFault();
/*      */       case "KQC_S":
/* 3026 */         return getKqcsFault();
/*      */       case "RSS":
/* 3028 */         return getRssFault();
/*      */       case "CC_HMI_S":
/* 3030 */         return getCchmisFault();
/*      */       case "VF_VC_H":
/* 3032 */         return getVfvchFault();
/*      */       case "BH":
/* 3034 */         return getBhfault();
/*      */       case "BH_RAID":
/* 3036 */         return getBhraidFault();
/*      */       case "UPS":
/* 3038 */         return getUpsFault();
/*      */       case "KVM":
/* 3040 */         return getKvmFault();
/*      */       case "BC":
/* 3042 */         return getBcFault();
/*      */       case "CCTV_H":
/* 3044 */         return getCctvHFault();
/*      */       case "IB_S":
/* 3046 */         return getIbSFault();
/*      */       case "I_IB_S":
/*      */       case "IIBS":
/* 3049 */         return getIibsDisconnect();
/*      */       case "HMI_S":
/*      */       case "HMIS":
/* 3052 */         return getHmisDisconnect();
/*      */       case "P_HMI_W":
/* 3054 */         return getPHmiWFault();
/*      */       case "CFW_CE":
/* 3056 */         return getCfwCEFault();
/*      */       case "BH_SWITCH":
/* 3058 */         return getBhSwitchFault();
/*      */       case "WS":
/* 3060 */         return getWsFault();
/*      */       case "SERVER":
/* 3062 */         return getServerFault();
/*      */       case "FIREWALL":
/* 3064 */         return getFireWallFault();
/*      */       case "R_IB_S":
/*      */       case "RIBS":
/* 3067 */         return getRibsDisconnect();
/*      */       case "RAID":
/* 3069 */         return getRaidFault();
/*      */       case "TIME_S":
/* 3071 */         return getTimeSFault();
/*      */       case "SSO_S":
/* 3073 */         return getSsoSFault();
/*      */       case "NAS":
/* 3075 */         return getNasDisconnect();
/*      */       case "CCTVWS":
/* 3077 */         return getCctvwsDisconnect();
/*      */       case "CRSV":
/* 3079 */         return getCrsvDisconnect();
/*      */       case "CRWS":
/* 3081 */         return getCrwsDisconnect();
/*      */       case "DB":
/* 3083 */         return getOldbDisconnect();
/*      */       case "EMMS":
/* 3085 */         return getEmmsDisconnect();
/*      */       case "IIDSV":
/* 3087 */         return getIidsvDisconnect();
/*      */       case "IIDWS":
/* 3089 */         return getIidwsDisconnect();
/*      */       case "IMWS":
/* 3091 */         return getImwsDisconnect();
/*      */       case "PJSV":
/* 3093 */         return getPjsvDisconnect();
/*      */       case "PRT":
/* 3095 */         return getPrtDisconnect();
/*      */       case "RWS":
/* 3097 */         return getRwsDisconnect();
/*      */       case "SALM":
/* 3099 */         return getSalmDisconnect();
/*      */       case "TBS":
/* 3101 */         return getTbsDisconnect();
/*      */       case "TEWS":
/* 3103 */         return getTewsDisconnect();
/*      */       case "TWS":
/* 3105 */         return getTwsDisconnect();
/*      */       case "WMDC":
/* 3107 */         return getWmdcDisconnect();
/*      */       case "AVI":
/* 3109 */         return getAviDisconnect();
/*      */       case "CCTV":
/* 3111 */         return getCctvDisconnect();
/*      */       case "CMS":
/* 3113 */         return getCmsDisconnect();
/*      */       case "CMSRST":
/* 3115 */         return getCmsDisconnect();
/*      */       case "CSLS":
/* 3117 */         return getCslsDisconnect();
/*      */       case "ET":
/* 3119 */         return getEtDisconnect();
/*      */       case "ETAG":
/* 3121 */         return getEtagDisconnect();
/*      */       case "IID":
/*      */       case "IIDTC":
/* 3124 */         return getIidDiscoonect();
/*      */       case "LCS":
/* 3126 */         return getLcsDisconnect();
/*      */       case "LS":
/* 3128 */         return getLsDisconnect();
/*      */       case "RD":
/* 3130 */         return getRdDisconnect();
/*      */       case "RGS":
/* 3132 */         return getRgsDisconnect();
/*      */       case "RMS":
/* 3134 */         return getRmsDisconnect();
/*      */       case "TTS":
/* 3136 */         return getTtsDisconnect();
/*      */       case "VD":
/* 3138 */         return getVdDisconnect();
/*      */       case "VI":
/* 3140 */         return getViDisconnect();
/*      */       case "WD":
/* 3142 */         return getWdDisconnect();
/*      */       case "WIS":
/* 3144 */         return getWisDisconnect();
/*      */     } 
/* 3146 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTEMConfigCompareError() {
/* 3152 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3153 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(167)) == null) {
/* 3154 */       return null;
/*      */     }
/* 3156 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(167))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVdOccupyEvent() {
/* 3161 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3162 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(168)) == null) {
/* 3163 */       return null;
/*      */     }
/* 3165 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(168))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRegularCardOverDue() {
/* 3170 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3171 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(169)) == null) {
/* 3172 */       return null;
/*      */     }
/* 3174 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(169))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemporaryCardOverDue() {
/* 3179 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3180 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(170)) == null) {
/* 3181 */       return null;
/*      */     }
/* 3183 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(170))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRoomCardOverReturnDate() {
/* 3188 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3189 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(171)) == null) {
/* 3190 */       return null;
/*      */     }
/* 3192 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(171))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRoomillegalInvasion() {
/* 3197 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3198 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(172)) == null) {
/* 3199 */       return null;
/*      */     }
/* 3201 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(172))).getId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioAmplifierDisconnect() {
/* 3207 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3208 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(173)) == null) {
/* 3209 */       return null;
/*      */     }
/* 3211 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(173))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRFLightDisconnect() {
/* 3216 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3217 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(174)) == null) {
/* 3218 */       return null;
/*      */     }
/* 3220 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(174))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRadioStationDisconnect() {
/* 3225 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3226 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(175)) == null) {
/* 3227 */       return null;
/*      */     }
/* 3229 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(175))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRadioPowerDown() {
/* 3234 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3235 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(176)) == null) {
/* 3236 */       return null;
/*      */     }
/* 3238 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(176))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRadioPowerFault() {
/* 3243 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3244 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(177)) == null) {
/* 3245 */       return null;
/*      */     }
/* 3247 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(177))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRadioPowerOverHeat() {
/* 3252 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3253 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(178)) == null) {
/* 3254 */       return null;
/*      */     }
/* 3256 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(178))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRadioUpLowNoise() {
/* 3261 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3262 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(179)) == null) {
/* 3263 */       return null;
/*      */     }
/* 3265 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(179))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRadioDownLowNoise() {
/* 3270 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3271 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(180)) == null) {
/* 3272 */       return null;
/*      */     }
/* 3274 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(180))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOpticalTransceiver() {
/* 3279 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3280 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(181)) == null) {
/* 3281 */       return null;
/*      */     }
/* 3283 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(181))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDownStandingWaveRatio() {
/* 3288 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3289 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(182)) == null) {
/* 3290 */       return null;
/*      */     }
/* 3292 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(182))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDownPowerOutputStatus() {
/* 3297 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3298 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(183)) == null) {
/* 3299 */       return null;
/*      */     }
/* 3301 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(183))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDownStandingWaveStatus() {
/* 3306 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3307 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(184)) == null) {
/* 3308 */       return null;
/*      */     }
/* 3310 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(184))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDownPowerOutputStatusCh2() {
/* 3315 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3316 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(185)) == null) {
/* 3317 */       return null;
/*      */     }
/* 3319 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(185))).getId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDownStandingWave() {
/* 3325 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3326 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(186)) == null) {
/* 3327 */       return null;
/*      */     }
/* 3329 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(186))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDownPowerOutput() {
/* 3334 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3335 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(187)) == null) {
/* 3336 */       return null;
/*      */     }
/* 3338 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(187))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOneHalfVoltageStandingWave() {
/* 3343 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3344 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(188)) == null) {
/* 3345 */       return null;
/*      */     }
/* 3347 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(188))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOneHalfEmissivePower() {
/* 3352 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3353 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(189)) == null) {
/* 3354 */       return null;
/*      */     }
/* 3356 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(189))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOneHalfTransmitterOverHeat() {
/* 3361 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3362 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(190)) == null) {
/* 3363 */       return null;
/*      */     }
/* 3365 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(190))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getThreeFourthsVoltageStandingWave() {
/* 3370 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3371 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(191)) == null) {
/* 3372 */       return null;
/*      */     }
/* 3374 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(191))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getThreeFourthsEmissivePower() {
/* 3379 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3380 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(192)) == null) {
/* 3381 */       return null;
/*      */     }
/* 3383 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(192))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getThreeFourthsTransmitterOverHeat() {
/* 3388 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3389 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(193)) == null) {
/* 3390 */       return null;
/*      */     }
/* 3392 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(193))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAudioSmallpaDisconnect() {
/* 3397 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3398 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(194)) == null) {
/* 3399 */       return null;
/*      */     }
/* 3401 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(194))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAudioBgmFmDisconnect() {
/* 3406 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3407 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(195)) == null) {
/* 3408 */       return null;
/*      */     }
/* 3410 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(195))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFmTransmitorDisconnect() {
/* 3415 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3416 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(196)) == null) {
/* 3417 */       return null;
/*      */     }
/* 3419 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(196))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBdaDisconnect() {
/* 3424 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3425 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(197)) == null) {
/* 3426 */       return null;
/*      */     }
/* 3428 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(197))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBoschDevicePaDisconnect() {
/* 3433 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3434 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(198)) == null) {
/* 3435 */       return null;
/*      */     }
/* 3437 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(198))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBoschDeviceDisconnect() {
/* 3442 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3443 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(199)) == null) {
/* 3444 */       return null;
/*      */     }
/* 3446 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(199))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBoschDeviceSpeakerDisconnect() {
/* 3451 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3452 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(200)) == null) {
/* 3453 */       return null;
/*      */     }
/* 3455 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(200))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBroadcastDeviceXmlMismach() {
/* 3460 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3461 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(201)) == null) {
/* 3462 */       return null;
/*      */     }
/* 3464 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(201))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCardReaderDisconnect() {
/* 3469 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3470 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(202)) == null) {
/* 3471 */       return null;
/*      */     }
/* 3473 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(202))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSafetyDoorOpen() {
/* 3478 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 3479 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(203)) == null) {
/* 3480 */       return null;
/*      */     }
/* 3482 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(203))).getId();
/*      */   }
/*      */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\AlarmSubTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */