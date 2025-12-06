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
/*     */ public enum CommonFmHzQueue
/*     */   implements HzDistObjEnum
/*     */ {
/*  14 */   AlarmMessage("用於發送警報訊息"), 
/*     */   
/*     */ 
/*  17 */   EventMessage("用於發送反應計畫事件訊息"), 
/*     */   
/*     */ 
/*  20 */   MAQ_EventMessage("預備要顯示的事件訊息"), 
/*     */   
/*     */ 
/*  23 */   ETAG_Data1MinReport("用於存放ncc所收集到的1分鐘ETAG資料_"), 
/*     */   
/*     */ 
/*  26 */   NDS_DispathTask("用於存放分派計算路段"), 
/*     */   
/*     */ 
/*  29 */   LSS_Data1HrReport("存放NCC收集的LSS1小時資料_30H"), 
/*  30 */   LSS_EventReport("存放NCC收集的LSS事件資料_32H"), 
/*     */   
/*     */ 
/*  33 */   IID_StateLogReport("存放NCC收集的IID即時影像事件偵測資料"), 
/*     */   
/*     */ 
/*  36 */   TTS_DataReport("存放NCC收集的TTS主動回報資料_5FH1AH"), 
/*     */   
/*     */ 
/*  39 */   CSLS_DisplayChange("NCC收集的CSLS狀態改變回報資料_B5H"), 
/*     */   
/*     */ 
/*  42 */   CMS_DisplayChange("收集主動查詢CMS設備與改變後主動回報實際顯示之內容"), 
/*     */   
/*     */ 
/*  45 */   LCS_SingleLaneReport("NCC收集的LCS+SCS狀態改變回報資料(單車道)_CFHCAH"), 
/*  46 */   LCS_Report("NCC收集的LCS狀態改變回報資料(雙車道)_C3H"), 
/*     */   
/*     */ 
/*  49 */   SCM_SingleStatusReport("NCC收集的SCM燈態狀態改變回報資料_5F0H"), 
/*     */   
/*     */ 
/*  52 */   SCM_WorkStatusReport("NCC收集的SCM現場操作回報資料_5F08"), 
/*     */   
/*     */ 
/*  55 */   SCM_TcDataChangeReport("NCC收集的SCM現場設備資料更動回報_5F0A"), 
/*     */   
/*     */ 
/*  58 */   RMS_Event("RMS接收反應計畫通知的訊息佇列"), 
/*  59 */   RMS_Report("NCC收集的RMS主動改變回報資料"), 
/*     */   
/*     */ 
/*     */ 
/*  63 */   TEM_IncidentQueue("TEM傳送事件給反應計畫的訊息佇列"), 
/*  64 */   TEM_Report("NCC收集的TES主動回報資料"), 
/*     */   
/*     */ 
/*     */ 
/*  68 */   WIS_DisplayChange("收集主動查詢WIS設備與改變後主動回報實際顯示之內容"), 
/*     */   
/*     */ 
/*  71 */   EMS_HardwareStatusReport("硬體故障主動回報 command id=0AH(0BH, 01H 一併處理) ,name=hwStatusReport"), 
/*     */   
/*  73 */   EMS_LocalReactCtlReqReport("主動回報要求/結束現場連動 command id=0F00H ,name=localReactCtlReqReport"), 
/*     */   
/*  75 */   WDS_PeriodWdDataReport("回報 WD 週期性資料 command id=28 name=periodWdDataReport"), 
/*  76 */   WDS_WindEventReport("回報強風事件 command id=2A name=windEventReport"), 
/*     */   
/*  78 */   RDS_PeriodRdDataReport("回報 RD 週期性資料 command id=48 name=periodRdDataReport"), 
/*  79 */   RDS_RainEventReport("回報豪雨事件 command id=4A name=rainEventReport"), 
/*     */   
/*  81 */   VIS_PeriodViDataReport("回報 VI 週期性資料 command id=20 name=periodViDataReport"), 
/*  82 */   VIS_FogEventReport("回報濃霧事件 command id=22 name=fogEventReport"), 
/*     */   
/*  84 */   VDS_PeriodVdDataReport("回報 VD 週期性資料 command id=10H ,name=periodVdDataReport"), 
/*  85 */   VDS_EventVdDataReport("回報 VD 事件偵測資料 command id=17H ,name=eventVdDataReport"), 
/*  86 */   VDS_SpotSpeedDataReport("回報 VD 現點速率調查資料 command id=18H ,name=spotSpeedDataReport"), 
/*  87 */   VDS_OccupyReport("回報 VD 觸動事件 command id=1AH ,name=occupyReport"), 
/*     */   
/*  89 */   ETAG_EtagEventReport("即時回報eTag資料 command id=3F04 name=eTagIdEvent"), 
/*  90 */   ETAG_PeriodEtagDataReport("回報eTag週期性資料 command id=3F12 name=eTagDataReport"), 
/*     */   
/*  92 */   LSS_PeriodLsdDataReport("回報 LSD 週期性資料 command id=30 name=periodLsdDataReport"), 
/*  93 */   LSS_LsdEventReport("回報 LSD 事件 command id=32 name=lsdEventReport"), 
/*     */   
/*  95 */   IID_EventReport("回報IID偵測事件 command id=16 name=iidEventReport"), 
/*     */   
/*  97 */   CMS_DisplayChangeReport("主動回報 CMS 顯示訊息改變 command id=5A name=cmsDisplayMessageChangeReport"), 
/*  98 */   CMS_ExtDisplayChangeReport("擴充主動回報 CMS 顯示訊息改變 command id=5B name=extCmsDisplayMessageChangeReport"), 
/*     */   
/*     */ 
/* 101 */   RGS_DisplayChangeReport("主動回報 RGS 顯示訊息改變 command id=90 name=rgsDisplayMessageChangeReport"), 
/*     */   
/* 103 */   WIS_DisplayChangeReport("主動回報 WIS 顯示訊息改變 command id=DFDA name=wisDisplayMessageChangeReport"), 
/* 104 */   WIS_LocalReactCtlReqReport("專供WIS主動回報要求/結束現場連動 command id=0F00H ,name=localReactCtlReqReport"), 
/*     */   
/* 106 */   TTS_DisplayTravelTimeChangeReport("主動回報TTS 顯示訊息改變 command id=5F1A name=ttsDisplayTravelTimeChangeReport"), 
/*     */   
/*     */ 
/* 109 */   CSLS_SpeedChangeReport("主動回報 SLS 顯示狀態 command id=B5 name=cslsSpeedChangeReport"), 
/*     */   
/* 111 */   LCS_SignStatusReport("主動回報 LCS LED 顯示狀態 command id=C3 name=lcsSignStatusReport"), 
/* 112 */   LCS_SignConflictReport("主動回報 LCS LED 正反顯示關係 command id=C6 name=lcsSignConflictReport"), 
/*     */   
/* 114 */   SCS_ControlModeAndPlanChangeReport("主動回報 SCS 顯示狀態改變 command id=CFE2 name=scsControlModeAndPlanChangeReport"), 
/*     */   
/*     */ 
/* 117 */   RMS_ControlModeAndPlanChangeReport("主動回報儀控模式及時制編號 command id=82 name=rmsControlModeAndPlanChangeReport"), 
/*     */   
/* 119 */   RMS_SignalChangeReport("主動回報 RMS 號誌燈泡顯示狀態 command id=86 name=rmsSignalChangeReport"), 
/* 120 */   RMS_SignalOpChangeReport("主動回報匝道管制現場操作之起訖 command id=A6 name=rmsSignalOpChangeReport"), 
/* 121 */   RMS_BosStatusChangeReport("主動回報匝道管制號誌顯示狀態 command id=A7 name=rmsBosStatusChangeReport"), 
/* 122 */   RMS_ExtBosStatusChangeReport("顯示訊息改變主動回報 command id=AE name=rmsExtBosStatusChangeReport"), 
/* 123 */   RMS_ActionResultReport("主動回報匝道儀控啟動或結束之執行結果 command id=AA name=rmsActionResultReport"), 
/* 124 */   RMS_PeriodVdDataReport("在ITR mode下，每20秒主動回報資料 command id=AF01 name=rmsPeriodVdDataReport"), 
/* 125 */   RMS_PlanChangeReport("回報上一時制的車流量 command id=AF02 name=rmsPlanChangeReport"), 
/* 126 */   RMS_CongestionStatusReport("雍塞狀態回報 command id=AF03 name=congestionStatusReport"), 
/*     */   
/* 128 */   TEM_FireAlarmReport("火警事件 command id=10 name=temFireAlarmReport"), 
/* 129 */   TEM_AirQualityReport("空氣品質 command id=11 name=temAirQualityReport"), 
/* 130 */   TEM_LightDamageReport("照明事件 command id=12 name=temLightDamageReport"), 
/* 131 */   TEM_PowerStatusReport("配電事件 command id=13 name=temPowerStatusReport"), 
/* 132 */   TEM_RoomAccessReport("機房門禁事件 command id=14 name=temRoomAccessReport"), 
/* 133 */   TEM_TunnelAccessReport("聯絡隧道門禁事件 command id=15 name=temTunnelAccessReport"), 
/*     */   
/* 135 */   AVI_PeriodAviDataReport_CT3("回報Avi週期性資料 command id=E402 name=periodAviDataReportCt3"), 
/* 136 */   AVI_PeriodAviCountReport_CT3("回報Avi週期車輛數 command id=E403 name=periodAviCountReportCt3"), 
/* 137 */   ETAG_PeriodEtagDataReport_CT3("回報eTag週期性資料 command id=E402 name=periodEtagDataReportCt3"), 
/* 138 */   ETAG_PeriodEtagCountReport_CT3("回報eTag週期性資料 command id=E403 name=periodAviCountReportCt3"), 
/* 139 */   VDS_PeriodVdDataReport_CT3("CT3 回報 VD 週期性資料 command id=6F0FH ,name=periodVdDataReportCt3"), 
/* 140 */   VDS_EventVdDataReport_CT3("CT3 回報VD 週期性資料 command id=6F01H, name=eventVdDataReportCt3"), 
/* 141 */   VDS_OccupyReport_CT3("回報 VD 壓佔事件 command id=6F03H ,name=occupyReportCt3"), 
/* 142 */   VDS_OccupyEndReport_CT3("回報 VD 結束壓佔事件 command id=6F04H ,name=longOccupyReportCt3"), 
/* 143 */   VDS_SpotSpeedDataReport_CT3("回報 VD 現點速率調查資料 command id=6F02H ,name=spotSpeedReportCt3"), 
/*     */   
/* 145 */   LCS_ManualControl_KPT("LCS 手自動控制盤狀態 command id=7F40 name=lcsManualControlReport"), 
/* 146 */   OH_EventReport_KPT("回報超高事件 command id=6F51 name=ohEventReport"), 
/* 147 */   MF_StatusReport_KPT("回報柵欄機運作狀態 command id=6F53 name=mfStatusReport"), 
/*     */   
/* 149 */   ETS_EventReport("回報ET偵測事件 command id=4201 name=etEventReport");
/*     */   
/*     */   private String description;
/*     */   
/*     */   private CommonFmHzQueue(String description)
/*     */   {
/* 155 */     this.description = description;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toHzName()
/*     */   {
/* 161 */     return toString();
/*     */   }
/*     */   
/*     */   public String getDescription()
/*     */   {
/* 166 */     return this.description;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\hz\CommonFmHzQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */