/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusPinMapping;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class RoomDeviceBMSServcie
/*     */ {
/*  30 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceBMSServcie.class); @Autowired
/*     */   OpLogger opLogger;
/*     */   public void process() {
/*  33 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  34 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*  35 */     IMap<Long, ModbusPinMapping> pingMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/*     */     
/*  37 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  38 */     PredicateBuilder pb = eo.get("deviceType").equal("BMS").and((Predicate)eo.get("enable").equal(Boolean.valueOf(true)));
/*  39 */     byte[] data = null;
/*  40 */     for (DeviceTcConfig tcConfig : deviceConfigMap.values((Predicate)pb)) {
/*  41 */       String deviceName = tcConfig.getDeviceName();
/*     */       
/*     */       try {
/*  44 */         URL url = new URL("http://" + tcConfig.getIp() + ":" + tcConfig.getPort() + "/stringindex?0_0");
/*  45 */         HttpURLConnection con = (HttpURLConnection)url.openConnection();
/*  46 */         con.setConnectTimeout(5000);
/*     */         
/*  48 */         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
/*     */         
/*  50 */         String urlString = ""; String inputLine;
/*  51 */         while ((inputLine = in.readLine()) != null) {
/*  52 */           urlString = urlString + inputLine;
/*     */         }
/*  54 */         in.close();
/*  55 */         int registerLength = 100;
/*  56 */         if (tcConfig.getDeviceName().equals("AG-RTU-5")) {
/*  57 */           registerLength = 50;
/*     */         }
/*  59 */         data = new byte[registerLength * 2];
/*     */         
/*  61 */         Pattern pattern1 = Pattern.compile("String Voltage :\\s*(?<v>-*[0-9]+.[0-9]+)\\s*V", 2);
/*     */         
/*  63 */         Matcher matcher1 = pattern1.matcher(urlString);
/*  64 */         boolean matchFound = matcher1.find();
/*  65 */         if (matchFound) {
/*  66 */           double volt = Double.valueOf(matcher1.group(1)).doubleValue();
/*  67 */           data[0] = (byte)(int)(volt * 100.0D / 256.0D);
/*  68 */           data[1] = (byte)(int)(volt * 100.0D % 256.0D);
/*     */         } 
/*     */         
/*  71 */         Pattern pattern2 = Pattern.compile("String Current :\\s*(?<v>-*[0-9]+.[0-9]+)\\s*A", 2);
/*     */         
/*  73 */         Matcher matcher2 = pattern2.matcher(urlString);
/*  74 */         boolean matchFound2 = matcher2.find();
/*  75 */         if (matchFound2) {
/*  76 */           double a = Double.valueOf(matcher2.group(1)).doubleValue();
/*  77 */           data[2] = (byte)(int)(a * 100.0D / 256.0D);
/*  78 */           data[3] = (byte)(int)(a * 100.0D % 256.0D);
/*     */         } 
/*     */         
/*  81 */         Pattern pattern3 = Pattern.compile(">(?<v>[0-9]+.[0-9]+)\\s*V|>(?<temp>[0-9]+.[0-9]+)&deg;C", 2);
/*     */ 
/*     */         
/*  84 */         Matcher matcher3 = pattern3.matcher(urlString);
/*  85 */         int count = 0;
/*  86 */         byte[] temp = new byte[2];
/*  87 */         while (matcher3.find()) {
/*  88 */           String value = null;
/*  89 */           String matcherString = matcher3.group();
/*  90 */           if (matcherString.substring(1, 2).trim().equals("0")) {
/*  91 */             value = matcherString.substring(1, 2).trim();
/*     */           } else {
/*  93 */             value = matcherString.substring(1, 5).trim();
/*     */           } 
/*  95 */           double val = Double.valueOf(value).doubleValue();
/*  96 */           temp[0] = (byte)(int)Math.round(val * 100.0D / 256.0D);
/*  97 */           temp[1] = (byte)(int)(val * 100.0D % 256.0D);
/*  98 */           System.arraycopy(temp, 0, data, 4 + count * 2, 2);
/*  99 */           count++;
/*     */         } 
/* 101 */         con.disconnect();
/* 102 */         if (registerLength > 74) {
/* 103 */           URL homeUrl = new URL("http://" + tcConfig.getIp() + ":" + tcConfig.getPort());
/* 104 */           HttpURLConnection conHome = (HttpURLConnection)homeUrl.openConnection();
/*     */           
/* 106 */           BufferedReader inHome = new BufferedReader(new InputStreamReader(conHome.getInputStream(), "UTF-8"));
/*     */           
/* 108 */           String homeUrlString = ""; String inputHomeLine;
/* 109 */           while ((inputHomeLine = inHome.readLine()) != null) {
/* 110 */             homeUrlString = homeUrlString + inputHomeLine;
/*     */           }
/* 112 */           inHome.close();
/*     */           
/* 114 */           Pattern pattern4 = Pattern.compile("Battery\\[(?<v>[0-9]+)\\] out of average voltage<", 2);
/*     */           
/* 116 */           Matcher matcher4 = pattern4.matcher(homeUrlString);
/* 117 */           List<String> outVoltageItems = new ArrayList<>();
/* 118 */           while (matcher4.find()) {
/* 119 */             outVoltageItems.add(matcher4.group());
/*     */           }
/* 121 */           for (int i = 0; i < 24; i++) {
/* 122 */             if (outVoltageItems != null && outVoltageItems
/* 123 */               .size() > 0 && outVoltageItems
/* 124 */               .get(i) != null) {
/* 125 */               temp[0] = 1;
/* 126 */               temp[1] = 0;
/*     */             } else {
/* 128 */               temp[0] = 0;
/* 129 */               temp[1] = 0;
/*     */             } 
/* 131 */             System.arraycopy(temp, 0, data, 100 + i * 2, temp.length);
/*     */           } 
/* 133 */           conHome.disconnect();
/*     */         } 
/* 135 */         Thread.sleep(100L);
/* 136 */       } catch (Exception e) {
/* 137 */         logger.error("Get BMS Value failed , deviceName = '{}'", deviceName, e);
/*     */       } 
/*     */       
/* 140 */       EntryObject aso = (new PredicateBuilder()).getEntryObject();
/* 141 */       PredicateBuilder pbas = aso.get("deviceName").equal(deviceName);
/* 142 */       for (ModbusPinMapping pingMapValue : pingMap.values((Predicate)pbas)) {
/*     */         try {
/* 144 */           int address = pingMapValue.getAddress().intValue() - 40001;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 153 */           int reslutValue = data[address * 2] * 256 + data[address * 2 + 1];
/* 154 */           dataMap.put(pingMapValue.getKeyName(), Integer.valueOf(Math.abs(reslutValue)));
/* 155 */         } catch (Exception e) {
/* 156 */           logger.error("Plug BMS Value Into dataMap failed , deviceName = '{}'", deviceName);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomDeviceBMSServcie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */