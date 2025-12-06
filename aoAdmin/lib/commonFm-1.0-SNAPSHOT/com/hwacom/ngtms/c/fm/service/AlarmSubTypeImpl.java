/*      */ package com.hwacom.ngtms.c.fm.service;
/*      */ 
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*      */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*      */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*      */ import org.springframework.context.annotation.Profile;
/*      */ import org.springframework.stereotype.Service;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Service
/*      */ @Profile({"dev"})
/*      */ public class AlarmSubTypeImpl
/*      */   implements AlarmSubType
/*      */ {
/*      */   public Integer getHcFault() {
/*   22 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   23 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(1)) == null) {
/*   24 */       return null;
/*      */     }
/*   26 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(1))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMfccFault() {
/*   31 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   32 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(2)) == null) {
/*   33 */       return null;
/*      */     }
/*   35 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(2))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMfsvFault() {
/*   40 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   41 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(4)) == null) {
/*   42 */       return null;
/*      */     }
/*   44 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(4))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFiwsFault() {
/*   49 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   50 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(5)) == null) {
/*   51 */       return null;
/*      */     }
/*   53 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(5))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVsrvFault() {
/*   58 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   59 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(6)) == null) {
/*   60 */       return null;
/*      */     }
/*   62 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(6))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMcnsFault() {
/*   67 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   68 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(7)) == null) {
/*   69 */       return null;
/*      */     }
/*   71 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(7))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVsvFault() {
/*   76 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   77 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(8)) == null) {
/*   78 */       return null;
/*      */     }
/*   80 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(8))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVdFault() {
/*   85 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   86 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(9)) == null) {
/*   87 */       return null;
/*      */     }
/*   89 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(9))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getViFault() {
/*   94 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*   95 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(10)) == null) {
/*   96 */       return null;
/*      */     }
/*   98 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(10))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRdFault() {
/*  103 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  104 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(11)) == null) {
/*  105 */       return null;
/*      */     }
/*  107 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(11))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWdFault() {
/*  112 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  113 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(12)) == null) {
/*  114 */       return null;
/*      */     }
/*  116 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(12))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidFault() {
/*  121 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  122 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(13)) == null) {
/*  123 */       return null;
/*      */     }
/*  125 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(13))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviFault() {
/*  130 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  131 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(14)) == null) {
/*  132 */       return null;
/*      */     }
/*  134 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(14))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLsFault() {
/*  139 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  140 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(15)) == null) {
/*  141 */       return null;
/*      */     }
/*  143 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(15))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBsFault() {
/*  148 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  149 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(16)) == null) {
/*  150 */       return null;
/*      */     }
/*  152 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(16))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsFault() {
/*  157 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  158 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(17)) == null) {
/*  159 */       return null;
/*      */     }
/*  161 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(17))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisFault() {
/*  166 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  167 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(18)) == null) {
/*  168 */       return null;
/*      */     }
/*  170 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(18))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsFault() {
/*  175 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  176 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(19)) == null) {
/*  177 */       return null;
/*      */     }
/*  179 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(19))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsFault() {
/*  184 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  185 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(20)) == null) {
/*  186 */       return null;
/*      */     }
/*  188 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(20))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmFault() {
/*  193 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  194 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(21)) == null) {
/*  195 */       return null;
/*      */     }
/*  197 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(21))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsFault() {
/*  202 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  203 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(22)) == null) {
/*  204 */       return null;
/*      */     }
/*  206 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(22))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsFault() {
/*  211 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  212 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(23)) == null) {
/*  213 */       return null;
/*      */     }
/*  215 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(23))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsFault() {
/*  220 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  221 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(24)) == null) {
/*  222 */       return null;
/*      */     }
/*  224 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(24))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvFault() {
/*  229 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  230 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(25)) == null) {
/*  231 */       return null;
/*      */     }
/*  233 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(25))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtFault() {
/*  238 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  239 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(26)) == null) {
/*  240 */       return null;
/*      */     }
/*  242 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(26))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsFault() {
/*  247 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  248 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(27)) == null) {
/*  249 */       return null;
/*      */     }
/*  251 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(27))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasFault() {
/*  256 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  257 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(28)) == null) {
/*  258 */       return null;
/*      */     }
/*  260 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(28))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVpFault() {
/*  265 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  266 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(29)) == null) {
/*  267 */       return null;
/*      */     }
/*  269 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(29))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAccident() {
/*  274 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  275 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(30)) == null) {
/*  276 */       return null;
/*      */     }
/*  278 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(30))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getConstruction() {
/*  283 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  284 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(31)) == null) {
/*  285 */       return null;
/*      */     }
/*  287 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(31))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDebris() {
/*  292 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  293 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(32)) == null) {
/*  294 */       return null;
/*      */     }
/*  296 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(32))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRoadDamage() {
/*  301 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  302 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(33)) == null) {
/*  303 */       return null;
/*      */     }
/*  305 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(33))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFaultCar() {
/*  310 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  311 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(34)) == null) {
/*  312 */       return null;
/*      */     }
/*  314 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(34))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFire() {
/*  319 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  320 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(35)) == null) {
/*  321 */       return null;
/*      */     }
/*  323 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(35))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSurfaceWater() {
/*  328 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  329 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(36)) == null) {
/*  330 */       return null;
/*      */     }
/*  332 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(36))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHazardousMaterial() {
/*  337 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  338 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(37)) == null) {
/*  339 */       return null;
/*      */     }
/*  341 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(37))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMilitaryControl() {
/*  346 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  347 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(38)) == null) {
/*  348 */       return null;
/*      */     }
/*  350 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(38))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSecret() {
/*  355 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  356 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(39)) == null) {
/*  357 */       return null;
/*      */     }
/*  359 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(39))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getInjuries() {
/*  364 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  365 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(40)) == null) {
/*  366 */       return null;
/*      */     }
/*  368 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(40))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCongestion() {
/*  373 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  374 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(41)) == null) {
/*  375 */       return null;
/*      */     }
/*  377 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(41))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMetroNetwork() {
/*  382 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  383 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(42)) == null) {
/*  384 */       return null;
/*      */     }
/*  386 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(42))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getExitRampCongestion() {
/*  391 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  392 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(43)) == null) {
/*  393 */       return null;
/*      */     }
/*  395 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(43))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelCongestion() {
/*  400 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  401 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(44)) == null) {
/*  402 */       return null;
/*      */     }
/*  404 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(44))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFog() {
/*  409 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  410 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(45)) == null) {
/*  411 */       return null;
/*      */     }
/*  413 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(45))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getStrongWind() {
/*  418 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  419 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(46)) == null) {
/*  420 */       return null;
/*      */     }
/*  422 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(46))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHeavyRain() {
/*  427 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  428 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(47)) == null) {
/*  429 */       return null;
/*      */     }
/*  431 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(47))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getNetworkSwitchContorl() {
/*  436 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  437 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(48)) == null) {
/*  438 */       return null;
/*      */     }
/*  440 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(48))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRampControl() {
/*  445 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  446 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(49)) == null) {
/*  447 */       return null;
/*      */     }
/*  449 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(49))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCollapse() {
/*  454 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  455 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(50)) == null) {
/*  456 */       return null;
/*      */     }
/*  458 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(50))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBridgeSettlement() {
/*  463 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  464 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(51)) == null) {
/*  465 */       return null;
/*      */     }
/*  467 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(51))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWorseningTunnelAirQuality() {
/*  472 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  473 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(52)) == null) {
/*  474 */       return null;
/*      */     }
/*  476 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(52))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelLightingFailure() {
/*  481 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  482 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(53)) == null) {
/*  483 */       return null;
/*      */     }
/*  485 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(53))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelDistribution() {
/*  490 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  491 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(54)) == null) {
/*  492 */       return null;
/*      */     }
/*  494 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(54))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVehicleStopWait() {
/*  499 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  500 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(55)) == null) {
/*  501 */       return null;
/*      */     }
/*  503 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(55))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSmoke() {
/*  508 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  509 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(56)) == null) {
/*  510 */       return null;
/*      */     }
/*  512 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(56))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayVehical() {
/*  517 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  518 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(58)) == null) {
/*  519 */       return null;
/*      */     }
/*  521 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(58))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRouteGuidanceInfo() {
/*  526 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  527 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(59)) == null) {
/*  528 */       return null;
/*      */     }
/*  530 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(59))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelFire() {
/*  535 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  536 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(60)) == null) {
/*  537 */       return null;
/*      */     }
/*  539 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(60))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAirConditioningEqu() {
/*  544 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  545 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(62)) == null) {
/*  546 */       return null;
/*      */     }
/*  548 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(62))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPowerSystem() {
/*  553 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  554 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(63)) == null) {
/*  555 */       return null;
/*      */     }
/*  557 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(63))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFireAlarmSystem() {
/*  562 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  563 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(65)) == null) {
/*  564 */       return null;
/*      */     }
/*  566 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(65))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getThermoHygroMeter() {
/*  571 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  572 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(66)) == null) {
/*  573 */       return null;
/*      */     }
/*  575 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(66))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getGenerator() {
/*  580 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  581 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(67)) == null) {
/*  582 */       return null;
/*      */     }
/*  584 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(67))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSmr() {
/*  589 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  590 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(68)) == null) {
/*  591 */       return null;
/*      */     }
/*  593 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(68))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidStopWait() {
/*  598 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  599 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(70)) == null) {
/*  600 */       return null;
/*      */     }
/*  602 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(70))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidPedestrianDetection() {
/*  607 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  608 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(71)) == null) {
/*  609 */       return null;
/*      */     }
/*  611 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(71))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidDebris() {
/*  616 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  617 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(72)) == null) {
/*  618 */       return null;
/*      */     }
/*  620 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(72))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidWrongWayVehical() {
/*  625 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  626 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(73)) == null) {
/*  627 */       return null;
/*      */     }
/*  629 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(73))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidSmoke() {
/*  634 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  635 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(74)) == null) {
/*  636 */       return null;
/*      */     }
/*  638 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(74))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidCongestion() {
/*  643 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  644 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(75)) == null) {
/*  645 */       return null;
/*      */     }
/*  647 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(75))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelAccess() {
/*  652 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  653 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(76)) == null) {
/*  654 */       return null;
/*      */     }
/*  656 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(76))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getContactTunnelAccess() {
/*  661 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  662 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(77)) == null) {
/*  663 */       return null;
/*      */     }
/*  665 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(77))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsCompareError() {
/*  670 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  671 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(78)) == null) {
/*  672 */       return null;
/*      */     }
/*  674 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(78))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsCompareError() {
/*  679 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  680 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(79)) == null) {
/*  681 */       return null;
/*      */     }
/*  683 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(79))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasCompareError() {
/*  688 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  689 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(80)) == null) {
/*  690 */       return null;
/*      */     }
/*  692 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(80))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsCompareError() {
/*  697 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  698 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(81)) == null) {
/*  699 */       return null;
/*      */     }
/*  701 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(81))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisCompareError() {
/*  706 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  707 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(82)) == null) {
/*  708 */       return null;
/*      */     }
/*  710 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(82))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsCompareError() {
/*  715 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  716 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(83)) == null) {
/*  717 */       return null;
/*      */     }
/*  719 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(83))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsCompareError() {
/*  724 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  725 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(84)) == null) {
/*  726 */       return null;
/*      */     }
/*  728 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(84))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsCompareError() {
/*  733 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  734 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(85)) == null) {
/*  735 */       return null;
/*      */     }
/*  737 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(85))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsCompareError() {
/*  742 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  743 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(86)) == null) {
/*  744 */       return null;
/*      */     }
/*  746 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(86))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmCompareError() {
/*  751 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  752 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(86)) == null) {
/*  753 */       return null;
/*      */     }
/*  755 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(263))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemDisconnect() {
/*  760 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  761 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(87)) == null) {
/*  762 */       return null;
/*      */     }
/*  764 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(87))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVdDisconnect() {
/*  769 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  770 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(88)) == null) {
/*  771 */       return null;
/*      */     }
/*  773 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(88))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getViDisconnect() {
/*  778 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  779 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(89)) == null) {
/*  780 */       return null;
/*      */     }
/*  782 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(89))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRdDisconnect() {
/*  787 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  788 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(90)) == null) {
/*  789 */       return null;
/*      */     }
/*  791 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(90))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWdDisconnect() {
/*  796 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  797 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(91)) == null) {
/*  798 */       return null;
/*      */     }
/*  800 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(91))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidDiscoonect() {
/*  805 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  806 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(92)) == null) {
/*  807 */       return null;
/*      */     }
/*  809 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(92))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviDisconnect() {
/*  814 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  815 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(93)) == null) {
/*  816 */       return null;
/*      */     }
/*  818 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(93))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLsDisconnect() {
/*  823 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  824 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(94)) == null) {
/*  825 */       return null;
/*      */     }
/*  827 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(94))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBsDisconnect() {
/*  832 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  833 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(95)) == null) {
/*  834 */       return null;
/*      */     }
/*  836 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(95))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsDisconnect() {
/*  841 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  842 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(96)) == null) {
/*  843 */       return null;
/*      */     }
/*  845 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(96))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisDisconnect() {
/*  850 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  851 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(97)) == null) {
/*  852 */       return null;
/*      */     }
/*  854 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(97))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsDisconnect() {
/*  859 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  860 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(98)) == null) {
/*  861 */       return null;
/*      */     }
/*  863 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(98))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsDisconnect() {
/*  868 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  869 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(99)) == null) {
/*  870 */       return null;
/*      */     }
/*  872 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(99))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmDisconnect() {
/*  877 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  878 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(100)) == null) {
/*  879 */       return null;
/*      */     }
/*  881 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(100))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsDisconnect() {
/*  886 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  887 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(101)) == null) {
/*  888 */       return null;
/*      */     }
/*  890 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(101))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsDisconnect() {
/*  895 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  896 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(102)) == null) {
/*  897 */       return null;
/*      */     }
/*  899 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(102))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsDisconnect() {
/*  904 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  905 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(103)) == null) {
/*  906 */       return null;
/*      */     }
/*  908 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(103))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvDisconnect() {
/*  913 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  914 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(104)) == null) {
/*  915 */       return null;
/*      */     }
/*  917 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(104))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtDisconnect() {
/*  922 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  923 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(105)) == null) {
/*  924 */       return null;
/*      */     }
/*  926 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(105))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsDisconnect() {
/*  931 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  932 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(106)) == null) {
/*  933 */       return null;
/*      */     }
/*  935 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(106))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasDisconnect() {
/*  940 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  941 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(107)) == null) {
/*  942 */       return null;
/*      */     }
/*  944 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(107))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVdConnect() {
/*  949 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  950 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(108)) == null) {
/*  951 */       return null;
/*      */     }
/*  953 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(108))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getViConnect() {
/*  958 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  959 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(109)) == null) {
/*  960 */       return null;
/*      */     }
/*  962 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(109))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRdConnect() {
/*  967 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  968 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(110)) == null) {
/*  969 */       return null;
/*      */     }
/*  971 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(110))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWdConnect() {
/*  976 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  977 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(111)) == null) {
/*  978 */       return null;
/*      */     }
/*  980 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(111))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIidConnect() {
/*  985 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  986 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(112)) == null) {
/*  987 */       return null;
/*      */     }
/*  989 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(112))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviConnect() {
/*  994 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  995 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(113)) == null) {
/*  996 */       return null;
/*      */     }
/*  998 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(113))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLsConnect() {
/* 1003 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1004 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(114)) == null) {
/* 1005 */       return null;
/*      */     }
/* 1007 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(114))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBsConnect() {
/* 1012 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1013 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(115)) == null) {
/* 1014 */       return null;
/*      */     }
/* 1016 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(115))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsConnect() {
/* 1021 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1022 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(116)) == null) {
/* 1023 */       return null;
/*      */     }
/* 1025 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(116))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisConnect() {
/* 1030 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1031 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(117)) == null) {
/* 1032 */       return null;
/*      */     }
/* 1034 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(117))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsConnect() {
/* 1039 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1040 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(118)) == null) {
/* 1041 */       return null;
/*      */     }
/* 1043 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(118))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsConnect() {
/* 1048 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1049 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(119)) == null) {
/* 1050 */       return null;
/*      */     }
/* 1052 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(119))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmConnect() {
/* 1057 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1058 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(120)) == null) {
/* 1059 */       return null;
/*      */     }
/* 1061 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(120))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsConnect() {
/* 1066 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1067 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(121)) == null) {
/* 1068 */       return null;
/*      */     }
/* 1070 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(121))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsConnect() {
/* 1075 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1076 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(122)) == null) {
/* 1077 */       return null;
/*      */     }
/* 1079 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(122))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsConnect() {
/* 1084 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1085 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(123)) == null) {
/* 1086 */       return null;
/*      */     }
/* 1088 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(123))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvConnect() {
/* 1093 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1094 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(124)) == null) {
/* 1095 */       return null;
/*      */     }
/* 1097 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(124))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtConnect() {
/* 1102 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1103 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(125)) == null) {
/* 1104 */       return null;
/*      */     }
/* 1106 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(125))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsConnect() {
/* 1111 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1112 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(126)) == null) {
/* 1113 */       return null;
/*      */     }
/* 1115 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(126))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasConnect() {
/* 1120 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1121 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(127)) == null) {
/* 1122 */       return null;
/*      */     }
/* 1124 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(127))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTemConnect() {
/* 1129 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1130 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(128)) == null) {
/* 1131 */       return null;
/*      */     }
/* 1133 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(128))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAutoIncidentDetection() {
/* 1138 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1139 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(129)) == null) {
/* 1140 */       return null;
/*      */     }
/* 1142 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(129))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getInvalidTravelTime() {
/* 1147 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1148 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(130)) == null) {
/* 1149 */       return null;
/*      */     }
/* 1151 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(130))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTravelTimeOutrange() {
/* 1156 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1157 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(131)) == null) {
/* 1158 */       return null;
/*      */     }
/* 1160 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(131))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAVIRecognitionRateBelowThreshold() {
/* 1165 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1166 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(132)) == null) {
/* 1167 */       return null;
/*      */     }
/* 1169 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(132))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEntranceRampCongestion() {
/* 1174 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1175 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(133)) == null) {
/* 1176 */       return null;
/*      */     }
/* 1178 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(133))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEarthquake() {
/* 1183 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1184 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(134)) == null) {
/* 1185 */       return null;
/*      */     }
/* 1187 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(134))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelFireInterlocking() {
/* 1192 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1193 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(135)) == null) {
/* 1194 */       return null;
/*      */     }
/* 1196 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(135))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCongestionSpeedLlimitControl() {
/* 1201 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1202 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(136)) == null) {
/* 1203 */       return null;
/*      */     }
/* 1205 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(136))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLevel2Congestion() {
/* 1210 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1211 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(137)) == null) {
/* 1212 */       return null;
/*      */     }
/* 1214 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(137))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtIncomingCall() {
/* 1219 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1220 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(138)) == null) {
/* 1221 */       return null;
/*      */     }
/* 1223 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(138))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTunnelTrafficRegulate() {
/* 1228 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1229 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(141)) == null) {
/* 1230 */       return null;
/*      */     }
/* 1232 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(141))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLocalTrafficResponse() {
/* 1237 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1238 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(142)) == null) {
/* 1239 */       return null;
/*      */     }
/* 1241 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(142))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAutoEventDectionEnd() {
/* 1246 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1247 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(143)) == null) {
/* 1248 */       return null;
/*      */     }
/* 1250 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(143))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTtsManualMessageEnd() {
/* 1255 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1256 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(144)) == null) {
/* 1257 */       return null;
/*      */     }
/* 1259 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(144))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRgsManualMessageEnd() {
/* 1264 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1265 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(145)) == null) {
/* 1266 */       return null;
/*      */     }
/* 1268 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(145))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCmsManualMessageEnd() {
/* 1273 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1274 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(146)) == null) {
/* 1275 */       return null;
/*      */     }
/* 1277 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(146))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScmManualMessageEnd() {
/* 1282 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1283 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(147)) == null) {
/* 1284 */       return null;
/*      */     }
/* 1286 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(147))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFsManualMessageEnd() {
/* 1291 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1292 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(148)) == null) {
/* 1293 */       return null;
/*      */     }
/* 1295 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(148))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRmsManualMessageEnd() {
/* 1300 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1301 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(149)) == null) {
/* 1302 */       return null;
/*      */     }
/* 1304 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(149))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWisManualMessageEnd() {
/* 1309 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1310 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(150)) == null) {
/* 1311 */       return null;
/*      */     }
/* 1313 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(150))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCslsManualMessageEnd() {
/* 1318 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1319 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(151)) == null) {
/* 1320 */       return null;
/*      */     }
/* 1322 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(151))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLcsManualMessageEnd() {
/* 1327 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1328 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(152)) == null) {
/* 1329 */       return null;
/*      */     }
/* 1331 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(152))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMasManualMessageEnd() {
/* 1336 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1337 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(153)) == null) {
/* 1338 */       return null;
/*      */     }
/* 1340 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(153))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getServiceAreaCongestion() {
/* 1345 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1346 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(154)) == null) {
/* 1347 */       return null;
/*      */     }
/* 1349 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(154))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMachineRoomAccess() {
/* 1354 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1355 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(155)) == null) {
/* 1356 */       return null;
/*      */     }
/* 1358 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(155))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidDisconnect() {
/* 1363 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1364 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(156)) == null) {
/* 1365 */       return null;
/*      */     }
/* 1367 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(156))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKqcDisconnect() {
/* 1372 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1373 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(157)) == null) {
/* 1374 */       return null;
/*      */     }
/* 1376 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(157))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPowerCordStolen() {
/* 1381 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1382 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(163)) == null) {
/* 1383 */       return null;
/*      */     }
/* 1385 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(163))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAutoImageDetection() {
/* 1390 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1391 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(165)) == null) {
/* 1392 */       return null;
/*      */     }
/* 1394 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(165))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getT74RouteGuidance() {
/* 1399 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1400 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(173)) == null) {
/* 1401 */       return null;
/*      */     }
/* 1403 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(173))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagDisconnect() {
/* 1408 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1409 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(174)) == null) {
/* 1410 */       return null;
/*      */     }
/* 1412 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(174))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagConnect() {
/* 1417 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1418 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(175)) == null) {
/* 1419 */       return null;
/*      */     }
/* 1421 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(175))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCooperationCenterEvent() {
/* 1426 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1427 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(176)) == null) {
/* 1428 */       return null;
/*      */     }
/* 1430 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(176))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKqcConnect() {
/* 1435 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1436 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(177)) == null) {
/* 1437 */       return null;
/*      */     }
/* 1439 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(177))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagRecongnitionRatebelowThreshold() {
/* 1444 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1445 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(178)) == null) {
/* 1446 */       return null;
/*      */     }
/* 1448 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(178))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOpenLaneside() {
/* 1453 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1454 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(179)) == null) {
/* 1455 */       return null;
/*      */     }
/* 1457 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(179))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayCar() {
/* 1462 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1463 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(180)) == null) {
/* 1464 */       return null;
/*      */     }
/* 1466 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(180))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayMotor() {
/* 1471 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1472 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(181)) == null) {
/* 1473 */       return null;
/*      */     }
/* 1475 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(181))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWrongWayPeople() {
/* 1480 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1481 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(182)) == null) {
/* 1482 */       return null;
/*      */     }
/* 1484 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(182))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtagSurfaceRoadCongestion() {
/* 1489 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1490 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(183)) == null) {
/* 1491 */       return null;
/*      */     }
/* 1493 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(183))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDcChargerBattery() {
/* 1498 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1499 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(185)) == null) {
/* 1500 */       return null;
/*      */     }
/* 1502 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(185))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAcupsOpStatus() {
/* 1507 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1508 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(186)) == null) {
/* 1509 */       return null;
/*      */     }
/* 1511 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(186))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVerticalDistributionCabinet() {
/* 1516 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1517 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(187)) == null) {
/* 1518 */       return null;
/*      */     }
/* 1520 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(187))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTanklevel() {
/* 1525 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1526 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(189)) == null) {
/* 1527 */       return null;
/*      */     }
/* 1529 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(189))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPaPowerAmplifier() {
/* 1534 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1535 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(190)) == null) {
/* 1536 */       return null;
/*      */     }
/* 1538 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(190))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPaVoiceAmplifier() {
/* 1543 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1544 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(191)) == null) {
/* 1545 */       return null;
/*      */     }
/* 1547 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(191))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPaRelayAmplifier() {
/* 1552 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1553 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(192)) == null) {
/* 1554 */       return null;
/*      */     }
/* 1556 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(192))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFmTransmitter() {
/* 1561 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1562 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(193)) == null) {
/* 1563 */       return null;
/*      */     }
/* 1565 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(193))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfTransmitterOutput() {
/* 1570 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1571 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(194)) == null) {
/* 1572 */       return null;
/*      */     }
/* 1574 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(194))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfTransmitterReflect() {
/* 1579 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1580 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(195)) == null) {
/* 1581 */       return null;
/*      */     }
/* 1583 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(195))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfTransmitterOutput() {
/* 1588 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1589 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(196)) == null) {
/* 1590 */       return null;
/*      */     }
/* 1592 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(196))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfTransmitterReflect() {
/* 1597 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1598 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(197)) == null) {
/* 1599 */       return null;
/*      */     }
/* 1601 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(197))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfRelayOutput1() {
/* 1606 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1607 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(198)) == null) {
/* 1608 */       return null;
/*      */     }
/* 1610 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(198))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfRelayOutput2() {
/* 1615 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1616 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(199)) == null) {
/* 1617 */       return null;
/*      */     }
/* 1619 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(199))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUhfRelayPower() {
/* 1624 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1625 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(200)) == null) {
/* 1626 */       return null;
/*      */     }
/* 1628 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(200))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfRelayOutput1() {
/* 1633 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1634 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(201)) == null) {
/* 1635 */       return null;
/*      */     }
/* 1637 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(201))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfRelayOutput2() {
/* 1642 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1643 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(202)) == null) {
/* 1644 */       return null;
/*      */     }
/* 1646 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(202))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVhfRelayPower() {
/* 1651 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1652 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(203)) == null) {
/* 1653 */       return null;
/*      */     }
/* 1655 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(203))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdStatus() {
/* 1660 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1661 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(204)) == null) {
/* 1662 */       return null;
/*      */     }
/* 1664 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(204))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidInvalidSectionTrafficData() {
/* 1669 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1670 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(205)) == null) {
/* 1671 */       return null;
/*      */     }
/* 1673 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(205))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidInvalidPDCPSectionTrafficData() {
/* 1678 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1679 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(206)) == null) {
/* 1680 */       return null;
/*      */     }
/* 1682 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(206))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getScsCompareError() {
/* 1687 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1688 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(207)) == null) {
/* 1689 */       return null;
/*      */     }
/* 1691 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(207))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidSIGFlowRate() {
/* 1696 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1697 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(208)) == null) {
/* 1698 */       return null;
/*      */     }
/* 1700 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(208))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTidSIGCongestionLevel() {
/* 1705 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1706 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(209)) == null) {
/* 1707 */       return null;
/*      */     }
/* 1709 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(209))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmsInvalidControlRoomMonitorData() {
/* 1714 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1715 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(210)) == null) {
/* 1716 */       return null;
/*      */     }
/* 1718 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(210))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmsInvalidPDSurveillanceData() {
/* 1723 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1724 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(211)) == null) {
/* 1725 */       return null;
/*      */     }
/* 1727 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(211))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEmsInvalidPDStructureData() {
/* 1732 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1733 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(212)) == null) {
/* 1734 */       return null;
/*      */     }
/* 1736 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(212))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAviationLight() {
/* 1741 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1742 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(213)) == null) {
/* 1743 */       return null;
/*      */     }
/* 1745 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(213))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHumanSensor() {
/* 1750 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1751 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(214)) == null) {
/* 1752 */       return null;
/*      */     }
/* 1754 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(214))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLighting() {
/* 1759 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1760 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(215)) == null) {
/* 1761 */       return null;
/*      */     }
/* 1763 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(215))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHostDriveMoreThan() {
/* 1768 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1769 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(216)) == null) {
/* 1770 */       return null;
/*      */     }
/* 1772 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(216))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCardFault() {
/* 1777 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1778 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(217)) == null) {
/* 1779 */       return null;
/*      */     }
/* 1781 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(217))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDgpFault() {
/* 1786 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1787 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(218)) == null) {
/* 1788 */       return null;
/*      */     }
/* 1790 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(218))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getElseFault() {
/* 1795 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1796 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(219)) == null) {
/* 1797 */       return null;
/*      */     }
/* 1799 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(219))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEtcFault() {
/* 1804 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1805 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(220)) == null) {
/* 1806 */       return null;
/*      */     }
/* 1808 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(220))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPcFault() {
/* 1813 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1814 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(221)) == null) {
/* 1815 */       return null;
/*      */     }
/* 1817 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(221))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPrinterFault() {
/* 1822 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1823 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(222)) == null) {
/* 1824 */       return null;
/*      */     }
/* 1826 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(222))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRouterFault() {
/* 1831 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1832 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(223)) == null) {
/* 1833 */       return null;
/*      */     }
/* 1835 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(223))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSvwsFault() {
/* 1840 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1841 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(224)) == null) {
/* 1842 */       return null;
/*      */     }
/* 1844 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(224))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSwitchFault() {
/* 1849 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1850 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(225)) == null) {
/* 1851 */       return null;
/*      */     }
/* 1853 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(225))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTdscFault() {
/* 1858 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1859 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(226)) == null) {
/* 1860 */       return null;
/*      */     }
/* 1862 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(226))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTisvFault() {
/* 1867 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1868 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(227)) == null) {
/* 1869 */       return null;
/*      */     }
/* 1871 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(227))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWmsFault() {
/* 1876 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1877 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(228)) == null) {
/* 1878 */       return null;
/*      */     }
/* 1880 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(228))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getEttuFault() {
/* 1885 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1886 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(229)) == null) {
/* 1887 */       return null;
/*      */     }
/* 1889 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(229))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getLocFault() {
/* 1894 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1895 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(230)) == null) {
/* 1896 */       return null;
/*      */     }
/* 1898 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(230))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRoomFault() {
/* 1903 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1904 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(231)) == null) {
/* 1905 */       return null;
/*      */     }
/* 1907 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(231))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMwsFault() {
/* 1912 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1913 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(232)) == null) {
/* 1914 */       return null;
/*      */     }
/* 1916 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(232))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getAvsFault() {
/* 1921 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1922 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(233)) == null) {
/* 1923 */       return null;
/*      */     }
/* 1925 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(233))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMsFault() {
/* 1930 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1931 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(234)) == null) {
/* 1932 */       return null;
/*      */     }
/* 1934 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(234))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKqcsFault() {
/* 1939 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1940 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(235)) == null) {
/* 1941 */       return null;
/*      */     }
/* 1943 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(235))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRssFault() {
/* 1948 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1949 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(236)) == null) {
/* 1950 */       return null;
/*      */     }
/* 1952 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(236))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCchmisFault() {
/* 1957 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1958 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(237)) == null) {
/* 1959 */       return null;
/*      */     }
/* 1961 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(237))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVfvchFault() {
/* 1966 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1967 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(238)) == null) {
/* 1968 */       return null;
/*      */     }
/* 1970 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(238))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBhfault() {
/* 1975 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1976 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(239)) == null) {
/* 1977 */       return null;
/*      */     }
/* 1979 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(239))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBhraidFault() {
/* 1984 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1985 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(240)) == null) {
/* 1986 */       return null;
/*      */     }
/* 1988 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(240))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getUpsFault() {
/* 1993 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 1994 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(241)) == null) {
/* 1995 */       return null;
/*      */     }
/* 1997 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(241))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getKvmFault() {
/* 2002 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2003 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(242)) == null) {
/* 2004 */       return null;
/*      */     }
/* 2006 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(242))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBcFault() {
/* 2011 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2012 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(243)) == null) {
/* 2013 */       return null;
/*      */     }
/* 2015 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(243))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCctvHFault() {
/* 2020 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2021 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(244)) == null) {
/* 2022 */       return null;
/*      */     }
/* 2024 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(244))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIbSFault() {
/* 2029 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2030 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(245)) == null) {
/* 2031 */       return null;
/*      */     }
/* 2033 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(245))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIIbSFault() {
/* 2038 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2039 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(246)) == null) {
/* 2040 */       return null;
/*      */     }
/* 2042 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(246))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getHmiSFault() {
/* 2047 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2048 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(247)) == null) {
/* 2049 */       return null;
/*      */     }
/* 2051 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(247))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPHmiWFault() {
/* 2056 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2057 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(248)) == null) {
/* 2058 */       return null;
/*      */     }
/* 2060 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(248))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getCfwCEFault() {
/* 2065 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2066 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(249)) == null) {
/* 2067 */       return null;
/*      */     }
/* 2069 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(249))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBhSwitchFault() {
/* 2074 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2075 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(250)) == null) {
/* 2076 */       return null;
/*      */     }
/* 2078 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(250))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getWsFault() {
/* 2083 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2084 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(251)) == null) {
/* 2085 */       return null;
/*      */     }
/* 2087 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(251))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getServerFault() {
/* 2092 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2093 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(252)) == null) {
/* 2094 */       return null;
/*      */     }
/* 2096 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(252))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getFireWallFault() {
/* 2101 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2102 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(253)) == null) {
/* 2103 */       return null;
/*      */     }
/* 2105 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(253))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRIbSFault() {
/* 2110 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2111 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(254)) == null) {
/* 2112 */       return null;
/*      */     }
/* 2114 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(254))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRaidFault() {
/* 2119 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2120 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(255)) == null) {
/* 2121 */       return null;
/*      */     }
/* 2123 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(255))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getTimeSFault() {
/* 2128 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2129 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(256)) == null) {
/* 2130 */       return null;
/*      */     }
/* 2132 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(256))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSsoSFault() {
/* 2137 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2138 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(257)) == null) {
/* 2139 */       return null;
/*      */     }
/* 2141 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(257))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getNasFault() {
/* 2146 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2147 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(258)) == null) {
/* 2148 */       return null;
/*      */     }
/* 2150 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(258))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMulticongestion() {
/* 2155 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2156 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(259)) == null) {
/* 2157 */       return null;
/*      */     }
/* 2159 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(259))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getPdRstStatus() {
/* 2164 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2165 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(260)) == null) {
/* 2166 */       return null;
/*      */     }
/* 2168 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(260))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getOdhDisconnect() {
/* 2173 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2174 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(261)) == null) {
/* 2175 */       return null;
/*      */     }
/* 2177 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(261))).getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getBycycle() {
/* 2182 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 2183 */     if (subTypeMap == null || subTypeMap.get(Integer.valueOf(262)) == null) {
/* 2184 */       return null;
/*      */     }
/* 2186 */     return ((AlarmSubTypeConfig)subTypeMap.get(Integer.valueOf(262))).getId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOldbsvFault() {
/* 2192 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getHddbsvFault() {
/* 2198 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTidFault() {
/* 2204 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getEmmFault() {
/* 2210 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getEtagFault() {
/* 2216 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPdFault() {
/* 2222 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOdhFault() {
/* 2228 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSeriousAccident() {
/* 2234 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTyphoon() {
/* 2240 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSalarmFault() {
/* 2246 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTbsFault() {
/* 2252 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPdDisconnect() {
/* 2258 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getHcDisconnect() {
/* 2264 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getMfccDisconnect() {
/* 2270 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOldbDisconnect() {
/* 2276 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getHddbDisconnect() {
/* 2282 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getFiwsDisconnect() {
/* 2288 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getImwsDisconnect() {
/* 2294 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTisvDisconnect() {
/* 2300 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getEmmsDisconnect() {
/* 2306 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getNasDisconnect() {
/* 2312 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getAvsDisconnect() {
/* 2318 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPrtDisconnect() {
/* 2324 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSalarmDisconnect() {
/* 2330 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRibsDisconnect() {
/* 2336 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getIibsDisconnect() {
/* 2342 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getEttuDisconnect() {
/* 2348 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSvwsDisconnect() {
/* 2354 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTbsDisconnect() {
/* 2360 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRwsDisconnect() {
/* 2366 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getIidsvDisconnect() {
/* 2372 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getIidwsDisconnect() {
/* 2378 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getCrsvDisconnect() {
/* 2384 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getCrwsDisconnect() {
/* 2390 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTesvDisconnect() {
/* 2396 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTewsDisconnect() {
/* 2402 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRwsFault() {
/* 2408 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getIidsvFault() {
/* 2414 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getIidwsFault() {
/* 2420 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getCrsvFault() {
/* 2426 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getCrwsFault() {
/* 2432 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTesvFault() {
/* 2438 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTewsFault() {
/* 2444 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPdConnect() {
/* 2450 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPdDoorOpen() {
/* 2456 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPrimaryRFault() {
/* 2462 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPrimarySFault() {
/* 2468 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPrimaryTFault() {
/* 2474 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSecondaryRFault() {
/* 2480 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSecondarySFault() {
/* 2486 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSecondaryTFault() {
/* 2492 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getLoop1Fault() {
/* 2498 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getLoop2Fault() {
/* 2504 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getLoop3Fault() {
/* 2510 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getLoop4Fault() {
/* 2516 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getLoop5Fault() {
/* 2522 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTemperatureUpperLimitFault() {
/* 2528 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTemperatureLowerLimitFault() {
/* 2534 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getHumidityUpperLimitFault() {
/* 2540 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getHumidityLowerLimitFault() {
/* 2546 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getVoltageUpperLimitFault() {
/* 2552 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getVoltageLowerLimitFault() {
/* 2558 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOilTankUpperLimitFault() {
/* 2564 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOilTankLowerLimitFault() {
/* 2570 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getFlashlightFault() {
/* 2576 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPowerSwitchFault() {
/* 2582 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRoomDoorFault() {
/* 2588 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getAirConditionFault() {
/* 2594 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getFireAlarmFault() {
/* 2600 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPDUFault() {
/* 2606 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getACUPSFault() {
/* 2612 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTemperatureConnect() {
/* 2618 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getHumidityConnect() {
/* 2624 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getVoltageConnect() {
/* 2630 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOilTankConnect() {
/* 2636 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getFireAlarmButton() {
/* 2642 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTemMistSystem() {
/* 2648 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getUdEvent() {
/* 2654 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getCCSConfigCompareError() {
/* 2660 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getCctvwsDisconnect() {
/* 2666 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDgpDisconnect() {
/* 2672 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getHmisDisconnect() {
/* 2678 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPjsvDisconnect() {
/* 2684 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSalmDisconnect() {
/* 2690 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTwsDisconnect() {
/* 2696 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getWmdcDisconnect() {
/* 2702 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getDisconnectAlarmSubType(String deviceType) {
/* 2707 */     if (deviceType == null) {
/* 2708 */       return null;
/*      */     }
/* 2710 */     switch (deviceType) {
/*      */       case "CARD":
/* 2712 */         return getCardFault();
/*      */       case "DBSV":
/* 2714 */         return getOldbsvFault();
/*      */       case "DGP":
/* 2716 */         return getDgpDisconnect();
/*      */       case "ELSE":
/* 2718 */         return getElseFault();
/*      */       case "ETC":
/* 2720 */         return getEtcFault();
/*      */       case "FIWS":
/* 2722 */         return getFiwsDisconnect();
/*      */       case "HC":
/* 2724 */         return getHcDisconnect();
/*      */       case "KQC":
/* 2726 */         return getKqcDisconnect();
/*      */       case "MCNS":
/* 2728 */         return getMcnsFault();
/*      */       case "MFCC":
/* 2730 */         return getMfccDisconnect();
/*      */       case "MFSV":
/* 2732 */         return getMfsvFault();
/*      */       case "PC":
/* 2734 */         return getPcFault();
/*      */       case "PRINTER":
/* 2736 */         return getPrinterFault();
/*      */       case "ROUTER":
/* 2738 */         return getRouterFault();
/*      */       case "SCM":
/* 2740 */         return getScmFault();
/*      */       case "SVWS":
/* 2742 */         return getSvwsDisconnect();
/*      */       case "SWITCH":
/* 2744 */         return getSwitchFault();
/*      */       case "TDSC":
/* 2746 */         return getTdscFault();
/*      */       case "TEM":
/* 2748 */         return getTemDisconnect();
/*      */       case "TID":
/* 2750 */         return getTidDisconnect();
/*      */       case "TISV":
/* 2752 */         return getTisvDisconnect();
/*      */       case "VP":
/* 2754 */         return getVpFault();
/*      */       case "VSRV":
/* 2756 */         return getVsrvFault();
/*      */       case "VSV":
/* 2758 */         return getVsvFault();
/*      */       case "WMS":
/* 2760 */         return getWmsFault();
/*      */       case "ETTU":
/* 2762 */         return getEttuFault();
/*      */       case "LOC":
/* 2764 */         return getLocFault();
/*      */       case "ROOM":
/* 2766 */         return getRoomFault();
/*      */       case "MWS":
/* 2768 */         return getMwsFault();
/*      */       case "AVS":
/* 2770 */         return getAvsDisconnect();
/*      */       case "MS":
/* 2772 */         return getMsFault();
/*      */       case "KQC_S":
/* 2774 */         return getKqcsFault();
/*      */       case "RSS":
/* 2776 */         return getRssFault();
/*      */       case "CC_HMI_S":
/* 2778 */         return getCchmisFault();
/*      */       case "VF_VC_H":
/* 2780 */         return getVfvchFault();
/*      */       case "BH":
/* 2782 */         return getBhfault();
/*      */       case "BH_RAID":
/* 2784 */         return getBhraidFault();
/*      */       case "UPS":
/* 2786 */         return getUpsFault();
/*      */       case "KVM":
/* 2788 */         return getKvmFault();
/*      */       case "BC":
/* 2790 */         return getBcFault();
/*      */       case "CCTV_H":
/* 2792 */         return getCctvHFault();
/*      */       case "IB_S":
/* 2794 */         return getIbSFault();
/*      */       case "I_IB_S":
/*      */       case "IIBS":
/* 2797 */         return getIibsDisconnect();
/*      */       case "HMI_S":
/*      */       case "HMIS":
/* 2800 */         return getHmisDisconnect();
/*      */       case "P_HMI_W":
/* 2802 */         return getPHmiWFault();
/*      */       case "CFW_CE":
/* 2804 */         return getCfwCEFault();
/*      */       case "BH_SWITCH":
/* 2806 */         return getBhSwitchFault();
/*      */       case "WS":
/* 2808 */         return getWsFault();
/*      */       case "SERVER":
/* 2810 */         return getServerFault();
/*      */       case "FIREWALL":
/* 2812 */         return getFireWallFault();
/*      */       case "R_IB_S":
/*      */       case "RIBS":
/* 2815 */         return getRibsDisconnect();
/*      */       case "RAID":
/* 2817 */         return getRaidFault();
/*      */       case "TIME_S":
/* 2819 */         return getTimeSFault();
/*      */       case "SSO_S":
/* 2821 */         return getSsoSFault();
/*      */       case "NAS":
/* 2823 */         return getNasDisconnect();
/*      */       case "CCTVWS":
/* 2825 */         return getCctvwsDisconnect();
/*      */       case "CRSV":
/* 2827 */         return getCrsvDisconnect();
/*      */       case "CRWS":
/* 2829 */         return getCrwsDisconnect();
/*      */       case "DB":
/* 2831 */         return getOldbDisconnect();
/*      */       case "EMMS":
/* 2833 */         return getEmmsDisconnect();
/*      */       case "IIDSV":
/* 2835 */         return getIidsvDisconnect();
/*      */       case "IIDWS":
/* 2837 */         return getIidwsDisconnect();
/*      */       case "IMWS":
/* 2839 */         return getImwsDisconnect();
/*      */       case "PJSV":
/* 2841 */         return getPjsvDisconnect();
/*      */       case "PRT":
/* 2843 */         return getPrtDisconnect();
/*      */       case "RWS":
/* 2845 */         return getRwsDisconnect();
/*      */       case "SALM":
/* 2847 */         return getSalmDisconnect();
/*      */       case "TBS":
/* 2849 */         return getTbsDisconnect();
/*      */       case "TEWS":
/* 2851 */         return getTewsDisconnect();
/*      */       case "TWS":
/* 2853 */         return getTwsDisconnect();
/*      */       case "WMDC":
/* 2855 */         return getWmdcDisconnect();
/*      */       case "AVI":
/* 2857 */         return getAviDisconnect();
/*      */       case "CCTV":
/* 2859 */         return getCctvDisconnect();
/*      */       case "CMS":
/* 2861 */         return getCmsDisconnect();
/*      */       case "CMSRST":
/* 2863 */         return getCmsDisconnect();
/*      */       case "CSLS":
/* 2865 */         return getCslsDisconnect();
/*      */       case "ET":
/* 2867 */         return getEtDisconnect();
/*      */       case "ETAG":
/* 2869 */         return getEtagDisconnect();
/*      */       case "IID":
/*      */       case "IIDTC":
/* 2872 */         return getIidDiscoonect();
/*      */       case "LCS":
/* 2874 */         return getLcsDisconnect();
/*      */       case "LS":
/* 2876 */         return getLsDisconnect();
/*      */       case "RD":
/* 2878 */         return getRdDisconnect();
/*      */       case "RGS":
/* 2880 */         return getRgsDisconnect();
/*      */       case "RMS":
/* 2882 */         return getRmsDisconnect();
/*      */       case "TTS":
/* 2884 */         return getTtsDisconnect();
/*      */       case "VD":
/* 2886 */         return getVdDisconnect();
/*      */       case "VI":
/* 2888 */         return getViDisconnect();
/*      */       case "WD":
/* 2890 */         return getWdDisconnect();
/*      */       case "WIS":
/* 2892 */         return getWisDisconnect();
/*      */     } 
/* 2894 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTEMConfigCompareError() {
/* 2901 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getVdOccupyEvent() {
/* 2907 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRegularCardOverDue() {
/* 2913 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTemporaryCardOverDue() {
/* 2919 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRoomCardOverReturnDate() {
/* 2925 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRoomillegalInvasion() {
/* 2931 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioAmplifierDisconnect() {
/* 2937 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRFLightDisconnect() {
/* 2943 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioStationDisconnect() {
/* 2949 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioPowerDown() {
/* 2955 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioPowerFault() {
/* 2961 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioPowerOverHeat() {
/* 2967 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioUpLowNoise() {
/* 2973 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getRadioDownLowNoise() {
/* 2979 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOpticalTransceiver() {
/* 2985 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDownStandingWaveRatio() {
/* 2991 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDownPowerOutputStatus() {
/* 2997 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDownStandingWave() {
/* 3003 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDownPowerOutput() {
/* 3009 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOneHalfVoltageStandingWave() {
/* 3015 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOneHalfEmissivePower() {
/* 3021 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getOneHalfTransmitterOverHeat() {
/* 3027 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getThreeFourthsVoltageStandingWave() {
/* 3033 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getThreeFourthsEmissivePower() {
/* 3039 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getThreeFourthsTransmitterOverHeat() {
/* 3045 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getAudioSmallpaDisconnect() {
/* 3051 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getAudioBgmFmDisconnect() {
/* 3057 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getFmTransmitorDisconnect() {
/* 3063 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getBdaDisconnect() {
/* 3069 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getBoschDevicePaDisconnect() {
/* 3075 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getBoschDeviceDisconnect() {
/* 3081 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getBoschDeviceSpeakerDisconnect() {
/* 3087 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDownStandingWaveStatus() {
/* 3093 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getDownPowerOutputStatusCh2() {
/* 3099 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getBroadcastDeviceXmlMismach() {
/* 3105 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getCardReaderDisconnect() {
/* 3111 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getSafetyDoorOpen() {
/* 3117 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmSubTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */