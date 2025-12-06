/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.shared.NCUCardData;
/*     */ import com.hwacom.ngtms.ao.shared.NCUDateTime;
/*     */ import com.hwacom.ngtms.ao.shared.NCUInfoData;
/*     */ import com.hwacom.ngtms.ao.shared.NCUReceiveRecord;
/*     */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.annotation.Profile;
/*     */ import org.springframework.http.HttpMethod;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ @Profile({"ao"})
/*     */ public class HunDureNcuServiceImpl
/*     */   implements HunDureNcuService
/*     */ {
/*     */   @Autowired
/*     */   OpLogger opLogger;
/*  48 */   private static final Logger logger = LoggerFactory.getLogger(HunDureNcuServiceImpl.class);
/*     */ 
/*     */   
/*     */   public Boolean checkNCUConnect(String deviceName) {
/*  52 */     return connectNcu(HttpMethod.GET, 
/*  53 */         getDynaConfig("NCUConnect").getValue(), deviceName);
/*     */   }
/*     */ 
/*     */   
/*     */   public NCUInfoData getNUCInfo(String deviceName) {
/*  58 */     return connectNcuInfo(HttpMethod.GET, 
/*  59 */         getDynaConfig("NCUInfo").getValue(), deviceName);
/*     */   }
/*     */ 
/*     */   
/*     */   public NCUDateTime getNCUTime(String deviceName) {
/*  64 */     return ncuDataTime(HttpMethod.GET, 
/*  65 */         getDynaConfig("NCUDataTime").getValue(), deviceName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean synchronizeNCUTime(String deviceName) {
/*  70 */     return synDataTime(HttpMethod.GET, 
/*     */         
/*  72 */         getDynaConfig("NCUSynchronizeTime").getValue(), deviceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean openDoor(String deviceName, String id, HttpServletRequest request) {
/*  78 */     return connectCardReader(HttpMethod.PUT, 
/*     */         
/*  80 */         getDynaConfig("NCUDoor").getValue(), deviceName, id, request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean readDoor(String deviceName, String id) {
/*  88 */     return connectDoorPosition(HttpMethod.GET, 
/*     */         
/*  90 */         getDynaConfig("NCUDoor").getValue(), deviceName, id, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addCard(String deviceName, NCUCardData cardData) {
/*  98 */     return controlCard(HttpMethod.POST, 
/*     */         
/* 100 */         getDynaConfig("NCUCard").getValue(), deviceName, cardData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int modifyCard(String deviceName, NCUCardData cardData) {
/* 107 */     return controlCard(HttpMethod.PUT, 
/*     */         
/* 109 */         getDynaConfig("NCUCard").getValue(), deviceName, cardData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteCard(String deviceName, NCUCardData cardData) {
/* 116 */     return controlCard(HttpMethod.DELETE, 
/*     */         
/* 118 */         getDynaConfig("NCUCard").getValue(), deviceName, cardData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NCUCardData queryCard(String deviceName, NCUCardData cardData) {
/* 125 */     return queryCard(HttpMethod.GET, 
/*     */         
/* 127 */         getDynaConfig("NCUCard").getValue(), deviceName, cardData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addAllCards(String deviceName, List<NCUCardData> cardDatas) {
/* 134 */     return addAllCard(HttpMethod.POST, 
/*     */         
/* 136 */         getDynaConfig("NCUAllCard").getValue(), deviceName, cardDatas);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean deleteAllCards(String deviceName) {
/* 143 */     return connectNcu(HttpMethod.DELETE, 
/* 144 */         getDynaConfig("NCUAllCard").getValue(), deviceName);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<NCUReceiveRecord> getNCUCardLog(String deviceName) {
/* 149 */     return getNcuCardLog(HttpMethod.GET, 
/* 150 */         getDynaConfig("NCUCardLog").getValue(), deviceName);
/*     */   }
/*     */ 
/*     */   
/*     */   private Boolean connectNcu(HttpMethod method, String api, String deviceName) {
/*     */     try {
/* 156 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 157 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 158 */       String response = "";
/* 159 */       Boolean result = Boolean.valueOf(false);
/* 160 */       HttpURLConnection connection = null;
/* 161 */       URL ur = new URL(url);
/* 162 */       connection = (HttpURLConnection)ur.openConnection();
/* 163 */       connection.setRequestMethod(method.name());
/* 164 */       connection.setRequestProperty("Content-Type", "application/string; charset=utf-8");
/* 165 */       connection.setConnectTimeout(5000);
/* 166 */       connection.setDoInput(true);
/* 167 */       connection.connect();
/* 168 */       int responseCode = connection.getResponseCode();
/* 169 */       if (responseCode == 200) {
/*     */         
/* 171 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line;
/* 172 */         while ((line = br.readLine()) != null) {
/* 173 */           response = response + line;
/*     */         }
/* 175 */         br.close();
/*     */       } 
/* 177 */       if (response.equals("true")) {
/* 178 */         result = Boolean.valueOf(true);
/*     */       }
/* 180 */       connection.disconnect();
/* 181 */       return result;
/* 182 */     } catch (Exception e) {
/* 183 */       logger.error("Connect Ncu failed.", e);
/* 184 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean connectCardReader(HttpMethod method, String api, String deviceName, String id, HttpServletRequest request) {
/*     */     try {
/* 192 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 193 */       String url = ip + "/" + api + "/" + deviceName + "/" + id;
/* 194 */       String response = "";
/* 195 */       Boolean result = Boolean.valueOf(false);
/* 196 */       HttpURLConnection connection = null;
/* 197 */       URL ur = new URL(url);
/* 198 */       connection = (HttpURLConnection)ur.openConnection();
/* 199 */       connection.setRequestMethod(method.name());
/* 200 */       connection.setRequestProperty("Content-Type", "application/string; charset=utf-8");
/* 201 */       connection.setFixedLengthStreamingMode(0);
/* 202 */       connection.setConnectTimeout(10000);
/* 203 */       connection.setDoInput(true);
/* 204 */       connection.setDoOutput(true);
/* 205 */       connection.connect();
/* 206 */       int responseCode = connection.getResponseCode();
/* 207 */       if (responseCode == 200) {
/*     */         
/* 209 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line;
/* 210 */         while ((line = br.readLine()) != null) {
/* 211 */           response = response + line;
/*     */         }
/* 213 */         br.close();
/*     */       } 
/* 215 */       if (response.equals("true")) {
/* 216 */         result = Boolean.valueOf(true);
/*     */       }
/* 218 */       connection.disconnect();
/* 219 */       if (result.booleanValue()) {
/* 220 */         addLog(request, OperationItem.SET, deviceName, OperationResult.SUCCESS, "openDoor:" + id, "遠端開門", new Object[] {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 227 */               result.booleanValue() ? "遠端操作成功" : "遠端操作失敗" });
/*     */       } else {
/* 229 */         addLog(request, OperationItem.SET, deviceName, OperationResult.FAILURE, "openDoor:" + id, "遠端開門", new Object[] {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 236 */               result.booleanValue() ? "遠端操作成功" : "遠端操作失敗" });
/*     */       } 
/* 238 */       return result;
/* 239 */     } catch (Exception e) {
/* 240 */       logger.error("Connect CardReader failed.", e);
/* 241 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean connectDoorPosition(HttpMethod method, String api, String deviceName, String id, HttpServletRequest request) {
/*     */     try {
/* 249 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 250 */       String url = ip + "/" + api + "/" + deviceName + "/" + id;
/* 251 */       String response = "";
/* 252 */       Boolean result = Boolean.valueOf(false);
/* 253 */       HttpURLConnection connection = null;
/* 254 */       URL ur = new URL(url);
/* 255 */       connection = (HttpURLConnection)ur.openConnection();
/* 256 */       connection.setRequestMethod(method.name());
/* 257 */       connection.setRequestProperty("Content-Type", "application/string; charset=utf-8");
/* 258 */       connection.setFixedLengthStreamingMode(0);
/* 259 */       connection.setConnectTimeout(5000);
/* 260 */       connection.setDoInput(true);
/* 261 */       connection.setDoOutput(true);
/* 262 */       connection.connect();
/* 263 */       int responseCode = connection.getResponseCode();
/* 264 */       if (responseCode == 200) {
/*     */         
/* 266 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line;
/* 267 */         while ((line = br.readLine()) != null) {
/* 268 */           response = response + line;
/*     */         }
/* 270 */         br.close();
/*     */       } 
/* 272 */       if (response.equals("true")) {
/* 273 */         result = Boolean.valueOf(true);
/*     */       }
/* 275 */       connection.disconnect();
/* 276 */       return result;
/* 277 */     } catch (Exception e) {
/* 278 */       logger.error("Connect CardReader failed.", e);
/* 279 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private NCUInfoData connectNcuInfo(HttpMethod method, String api, String deviceName) {
/*     */     try {
/* 285 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 286 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 287 */       NCUInfoData result = new NCUInfoData();
/* 288 */       HttpURLConnection connection = null;
/* 289 */       URL ur = new URL(url);
/* 290 */       connection = (HttpURLConnection)ur.openConnection();
/* 291 */       connection.setRequestMethod(method.name());
/* 292 */       connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 293 */       connection.setConnectTimeout(10000);
/* 294 */       connection.setDoInput(true);
/* 295 */       connection.connect();
/* 296 */       int responseCode = connection.getResponseCode();
/* 297 */       if (responseCode == 200) {
/* 298 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 299 */         StringBuilder sb = new StringBuilder();
/*     */         String line;
/* 301 */         while ((line = br.readLine()) != null) {
/* 302 */           sb.append(line + "\n");
/*     */         }
/* 304 */         br.close();
/* 305 */         JSONArray jsonarray = new JSONArray(sb.toString());
/* 306 */         for (int i = 0; i < jsonarray.length(); i++) {
/* 307 */           JSONObject obj = new JSONObject();
/* 308 */           obj = jsonarray.getJSONObject(i);
/* 309 */           result.setVerType(obj.get("verType").toString());
/* 310 */           result.setnVer(obj.get("nVer").toString());
/* 311 */           result.setsVer(obj.get("sVer").toString());
/* 312 */           result.setBate(obj.get("bate").toString());
/* 313 */           result.setYear(obj.get("year").toString());
/* 314 */           result.setMonth(obj.get("month").toString());
/* 315 */           result.setDate(obj.get("date").toString());
/* 316 */           result.setCarCount(obj.get("carCount").toString());
/* 317 */           result.setRecordCount(obj.get("recordCount").toString());
/*     */         } 
/*     */       } 
/* 320 */       return result;
/* 321 */     } catch (Exception e) {
/* 322 */       logger.error("Connect NcuInfo failed.", e);
/* 323 */       return new NCUInfoData();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int controlCard(HttpMethod method, String api, String deviceName, NCUCardData cardData) {
/*     */     try {
/* 329 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 330 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 331 */       int result = -1;
/* 332 */       String response = "";
/* 333 */       HttpURLConnection connection = null;
/* 334 */       URL ur = new URL(url);
/* 335 */       connection = (HttpURLConnection)ur.openConnection();
/* 336 */       connection.setRequestMethod(method.name());
/* 337 */       connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 338 */       connection.setConnectTimeout(10000);
/* 339 */       connection.setDoInput(true);
/* 340 */       connection.setDoOutput(true);
/* 341 */       connection.connect();
/*     */       
/* 343 */       OutputStream out = null;
/* 344 */       Gson gson = new Gson();
/* 345 */       out = new BufferedOutputStream(connection.getOutputStream());
/* 346 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
/* 347 */       writer.write(gson.toJson(cardData));
/* 348 */       writer.flush();
/* 349 */       writer.close();
/* 350 */       out.close();
/* 351 */       int responseCode = connection.getResponseCode();
/* 352 */       if (responseCode == 200) {
/*     */         
/* 354 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line;
/* 355 */         while ((line = br.readLine()) != null) {
/* 356 */           response = response + line;
/*     */         }
/* 358 */         br.close();
/* 359 */         result = Integer.valueOf(response).intValue();
/*     */       } 
/* 361 */       return result;
/* 362 */     } catch (Exception e) {
/* 363 */       logger.error("Control Card failed.", e);
/* 364 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private NCUCardData queryCard(HttpMethod method, String api, String deviceName, NCUCardData cardData) {
/*     */     try {
/* 371 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 372 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 373 */       NCUCardData result = new NCUCardData();
/* 374 */       HttpURLConnection connection = null;
/* 375 */       URL ur = new URL(url);
/* 376 */       connection = (HttpURLConnection)ur.openConnection();
/* 377 */       connection.setRequestMethod(method.name());
/* 378 */       connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 379 */       connection.setConnectTimeout(10000);
/* 380 */       connection.setDoInput(true);
/* 381 */       connection.setDoOutput(true);
/* 382 */       connection.connect();
/*     */       
/* 384 */       OutputStream out = null;
/* 385 */       Gson gson = new Gson();
/* 386 */       out = new BufferedOutputStream(connection.getOutputStream());
/* 387 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
/* 388 */       writer.write(gson.toJson(cardData));
/* 389 */       writer.flush();
/* 390 */       writer.close();
/* 391 */       out.close();
/*     */       
/* 393 */       int responseCode = connection.getResponseCode();
/* 394 */       if (responseCode == 200) {
/* 395 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 396 */         StringBuilder sb = new StringBuilder();
/*     */         String line;
/* 398 */         while ((line = br.readLine()) != null) {
/* 399 */           sb.append(line + "\n");
/*     */         }
/* 401 */         br.close();
/* 402 */         JSONArray jsonarray = new JSONArray(sb.toString());
/* 403 */         for (int i = 0; i < jsonarray.length(); i++) {
/* 404 */           JSONObject obj = new JSONObject();
/* 405 */           obj = jsonarray.getJSONObject(i);
/* 406 */           result.setcCardNo(obj.get("cCardNo").toString());
/* 407 */           result.setcStartDate(obj.get("cStartDate").toString());
/* 408 */           result.setcEndDate(obj.get("cEndDate").toString());
/*     */         } 
/*     */       } 
/* 411 */       return result;
/* 412 */     } catch (Exception e) {
/* 413 */       logger.error("Query Card failed.", e);
/* 414 */       return new NCUCardData();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int addAllCard(HttpMethod method, String api, String deviceName, List<NCUCardData> cardDatas) {
/*     */     try {
/* 421 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 422 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 423 */       int result = -1;
/* 424 */       String response = "";
/* 425 */       HttpURLConnection connection = null;
/* 426 */       URL ur = new URL(url);
/* 427 */       connection = (HttpURLConnection)ur.openConnection();
/* 428 */       connection.setRequestMethod(method.name());
/* 429 */       connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 430 */       connection.setConnectTimeout(10000);
/* 431 */       connection.setDoInput(true);
/* 432 */       connection.setDoOutput(true);
/* 433 */       connection.connect();
/*     */       
/* 435 */       OutputStream out = null;
/* 436 */       Gson gson = new Gson();
/* 437 */       out = new BufferedOutputStream(connection.getOutputStream());
/* 438 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
/* 439 */       writer.write(gson.toJson(cardDatas));
/* 440 */       writer.flush();
/* 441 */       writer.close();
/* 442 */       out.close();
/*     */       
/* 444 */       int responseCode = connection.getResponseCode();
/* 445 */       if (responseCode == 200) {
/*     */         
/* 447 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line;
/* 448 */         while ((line = br.readLine()) != null) {
/* 449 */           response = response + line;
/*     */         }
/* 451 */         br.close();
/* 452 */         result = Integer.valueOf(response).intValue();
/*     */       } 
/* 454 */       return result;
/* 455 */     } catch (Exception e) {
/* 456 */       logger.error("Add All Card failed.", e);
/* 457 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<NCUReceiveRecord> getNcuCardLog(HttpMethod method, String api, String deviceName) {
/*     */     try {
/* 463 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 464 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 465 */       List<NCUReceiveRecord> result = new ArrayList<>();
/* 466 */       HttpURLConnection connection = null;
/* 467 */       URL ur = new URL(url);
/* 468 */       connection = (HttpURLConnection)ur.openConnection();
/* 469 */       connection.setRequestMethod(method.name());
/* 470 */       connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 471 */       connection.setConnectTimeout(10000);
/* 472 */       connection.setDoInput(true);
/* 473 */       connection.connect();
/* 474 */       int responseCode = connection.getResponseCode();
/* 475 */       if (responseCode == 200) {
/* 476 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 477 */         StringBuilder sb = new StringBuilder();
/*     */         String line;
/* 479 */         while ((line = br.readLine()) != null) {
/* 480 */           sb.append(line + "\n");
/*     */         }
/* 482 */         br.close();
/* 483 */         JSONArray jsonarray = new JSONArray(sb.toString());
/* 484 */         for (int i = 0; i < jsonarray.length(); i++) {
/* 485 */           JSONObject obj = new JSONObject();
/* 486 */           obj = jsonarray.getJSONObject(i);
/* 487 */           NCUReceiveRecord record = new NCUReceiveRecord();
/* 488 */           record.setDeviceName(deviceName);
/* 489 */           record.setEventCode(obj.get("eventCode").toString());
/* 490 */           record.setDateTime(obj.get("dateTime").toString());
/* 491 */           record.setCardNumber(obj.get("cardNumber").toString());
/* 492 */           record.setDeviceID(obj.get("deviceID").toString());
/* 493 */           record.setStatusCode(obj.get("statusCode").toString());
/* 494 */           result.add(record);
/*     */         } 
/*     */       } 
/* 497 */       return result;
/* 498 */     } catch (Exception e) {
/* 499 */       logger.error("Connect NcuInfo failed.", e);
/* 500 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addLog(HttpServletRequest request, OperationItem operationItem, String deviceName, OperationResult result, String remark, String descMsgId, Object... args) {
/* 512 */     String userId = TripleDESUtils.decrypt(request.getHeader("encryptedUserLogin"));
/* 513 */     String ip = request.getRemoteHost();
/* 514 */     Date now = new Date();
/* 515 */     logger.debug("Get log time = ", now);
/* 516 */     this.opLogger.addLog(userId, ip, SubSystem.ROOM, operationItem, deviceName, now, result, remark, descMsgId, args);
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
/*     */   private Boolean synDataTime(HttpMethod method, String api, String deviceName) {
/*     */     try {
/* 531 */       String response = "";
/* 532 */       Boolean result = Boolean.valueOf(false);
/* 533 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 534 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 535 */       HttpURLConnection connection = null;
/* 536 */       URL ur = new URL(url);
/* 537 */       connection = (HttpURLConnection)ur.openConnection();
/* 538 */       connection.setRequestMethod(method.name());
/* 539 */       connection.setRequestProperty("Content-Type", "application/string; charset=utf-8");
/* 540 */       connection.setConnectTimeout(5000);
/* 541 */       connection.setDoInput(true);
/* 542 */       connection.connect();
/* 543 */       int responseCode = connection.getResponseCode();
/* 544 */       if (responseCode == 200) {
/*     */         
/* 546 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line;
/* 547 */         while ((line = br.readLine()) != null) {
/* 548 */           response = response + line;
/*     */         }
/* 550 */         br.close();
/*     */       } 
/* 552 */       if (response.equals("true")) {
/* 553 */         result = Boolean.valueOf(true);
/*     */       }
/* 555 */       connection.disconnect();
/* 556 */       return result;
/* 557 */     } catch (Exception e) {
/* 558 */       logger.error("Ncu DataTime failed.", e);
/* 559 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   private NCUDateTime ncuDataTime(HttpMethod method, String api, String deviceName) {
/*     */     try {
/* 565 */       logger.debug("Ncu DataTime Query.");
/* 566 */       String ip = getDynaConfig("NCUCardReaderApiUrl").getValue();
/* 567 */       String url = ip + "/" + api + "/" + deviceName + "/" + null;
/* 568 */       NCUDateTime result = new NCUDateTime();
/* 569 */       HttpURLConnection connection = null;
/* 570 */       URL ur = new URL(url);
/* 571 */       connection = (HttpURLConnection)ur.openConnection();
/* 572 */       connection.setRequestMethod(method.name());
/* 573 */       connection.setRequestProperty("Content-Type", "application/string; charset=utf-8");
/* 574 */       connection.setConnectTimeout(5000);
/* 575 */       connection.setDoInput(true);
/* 576 */       connection.connect();
/* 577 */       int responseCode = connection.getResponseCode();
/* 578 */       if (responseCode == 200) {
/* 579 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 580 */         StringBuilder sb = new StringBuilder();
/*     */         String line;
/* 582 */         while ((line = br.readLine()) != null) {
/* 583 */           sb.append(line + "\n");
/*     */         }
/* 585 */         br.close();
/* 586 */         logger.debug("sb = '{}'", sb.toString());
/* 587 */         JSONObject obj = new JSONObject(sb.toString());
/* 588 */         result.setDate(obj.get("date").toString());
/* 589 */         result.setTime(obj.get("time").toString());
/* 590 */         logger.debug("Ncu DataTime date = '{}' , time = '{}'", result.getDate(), result.getTime());
/*     */       } 
/* 592 */       connection.disconnect();
/* 593 */       return result;
/* 594 */     } catch (Exception e) {
/* 595 */       logger.error("Ncu DataTime failed.", e);
/* 596 */       return new NCUDateTime();
/*     */     } 
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynaConfig(String configName) {
/* 601 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 603 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk("hc_primary_group", "AoCommonFm", configName));
/*     */ 
/*     */     
/* 606 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\HunDureNcuServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */