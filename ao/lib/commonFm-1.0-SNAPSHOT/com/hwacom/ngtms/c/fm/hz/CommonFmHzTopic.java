/*     */ package com.hwacom.ngtms.c.fm.hz;
/*     */ 
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum CommonFmHzTopic
/*     */   implements HzDistObjEnum
/*     */ {
/*  15 */   AlarmOfBrowser("用於主動向前端回報告警事件"), 
/*     */   
/*     */ 
/*  18 */   VDS_DataSpotSpeed("用於主動回報現點速率時使用"), 
/*     */   
/*     */ 
/*  21 */   RDS_Event("發佈RDS事件資料"), 
/*  22 */   VIS_Event("發佈VIS事件資料"), 
/*  23 */   WDS_Event("發佈WDS事件資料"), 
/*     */   
/*     */ 
/*  26 */   LSS_Event("發佈LSS事件資料"), 
/*     */   
/*     */ 
/*  29 */   IID_EventTopic("發佈IID事件確認資料給反應計畫"), 
/*     */   
/*     */ 
/*  32 */   ET_Event("發佈ETTU的ET來話事件資料"), 
/*     */   
/*     */ 
/*  35 */   FGS_BitMap("發佈FGS的BitMap"), 
/*     */   
/*     */ 
/*  38 */   CSLS_BitMap("發佈CSLS的BitMap"), 
/*     */   
/*     */ 
/*  41 */   LCS_SignResponse("發佈LCS TC查詢結果"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  48 */   CMS_ProtocolResponse("用於rmi呼叫TC[通訊協定]的回報"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  54 */   CMS_BitMap("發佈CMS的BitMap查詢結果"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  60 */   CMS_LedStatus("發佈CMS的LedStatus查詢結果"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  65 */   CMS_CycleDisplay("發佈CMS循環顯示訊息查詢結果"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  71 */   CMS_DisFullText("發佈CMS設備目前實際顯示之內容指令查詢結果"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */   CMS_PrestoredMessage("發佈CMS設備目前預存訊息"), 
/*     */   
/*     */ 
/*  80 */   RMS_ProtocolResponse("用於rmi呼叫TC[通訊協定]的回報"), 
/*  81 */   RMS_BitMapResponse("用於查詢警告標誌 bit map 的回報"), 
/*     */   
/*  83 */   RMS_OnlineEvent("設備斷線後重新連線時的事件通知"), 
/*  84 */   RMS_SignalInfo("即時燈號狀態"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  91 */   WIS_CommandResult("用於Wis rmi呼叫TC[通訊協定]的回報"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */   WIS_LedStatus("發佈WIS的LedStatus查詢結果"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */   WIS_DisFullText("發佈WIS設備目前實際顯示之內容指令查詢結果");
/*     */   
/*     */   private String description;
/*     */   
/*     */   private CommonFmHzTopic(String description)
/*     */   {
/* 109 */     this.description = description;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toHzName()
/*     */   {
/* 115 */     return toString();
/*     */   }
/*     */   
/*     */   public String getDescription()
/*     */   {
/* 120 */     return this.description;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\hz\CommonFmHzTopic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */