/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.base.i18n.shared.MessageType;

public enum SubSystem implements MessageType, IsSerializable {
  AVI("#AVI"), //自動車牌辨識分析軟體

  BeaCon("#BeaCon"), //藍芽推播
  BSS("#BSS"), //橋樑沉陷資料收集軟體

  CCS("#CCS"), //閉路電視監視軟體
  CMS("#CMS"), //資訊可變標誌顯示軟體
  CSLS("#CSLS"), //速限可變標誌軟體

  DDS("#DDS"), //交通動態畫面顯示軟體
  DDSS("#DDSS"), //救災管理資訊決策支援軟體

  EMS("#EMS"), //設備監視軟體
  EMM("#EMM"), //設備監視維護軟體
  ETS("#ETS"), //緊急電話軟體
  ETAG("#ETAG"), //ETAG電子標籤分析軟體分析軟體

  FGS("#FGS"), //霧慢行標誌軟體

  HCCE("#HCCE"), //中央電腦叢集系統

  IID("#IID"), //影像事件偵測軟體
  IIP("#IIP"), //事件輸入軟體
  IDS("#IDS"), //事件調派

  KQS("#KQS"), //路況查詢軟體

  LCS("#LCS"), //車道管制號誌軟體
  LSS("#LSS"), //坍方資料收集軟體

  MAS("#MAS"), //多功能警示標誌軟體
  MCNS("#MCNS"), //移動性施工監控及通報軟體

  NCC("#NCC"), //網路通訊控制軟體

  PD("#PD"), //電力監控軟體
  PDCP("#PDCP"), //探測車資料收集與提供軟體
  PEX("#PEX"), //反應計畫執行軟體
  PTS("#PTS"), //多媒體簡報說明軟體

  QLDS("#QLDS"), //壅塞回堵偵測收集軟體

  RDS("#RDS"), //雨量資料收集軟體
  RGS("#RGS"), //路徑導引標誌顯示軟體
  RLC("#RLC"), //調撥車道控制管理軟體
  RMS("#RMS"), //匝道儀控軟體
  ROOM("#ROOM"), //機房門禁管制系統
  RPT("#RPT"), //報表產生軟體
  RSP("#RSP"), //反應計畫產生軟體

  SCH("#SCH"), //操作排程軟體
  SCM("#SCM"), //號誌控制管理軟體
  SCS("#SCS"), //路肩控制三面轉版系統
  SMG("#SMG"), //系統管理軟體
  SMS("#SMS"), //簡訊發送系統

  TDS("#TDS"), //路況播映軟體
  TEM("#TEM"), //隧道機電事件監視軟體
  TID("#TID"), //線上交通資訊庫軟體
  TTS("#TTS"), //旅行時間標誌顯示軟體

  UD("#UD"), //超高偵測器軟體
  VDS("#VDS"), //交通資料收集軟體
  VIS("#VIS"), //濃霧資料收集軟體

  WDS("#WDS"), //風力資料收集軟體
  WIS("#WIS"), //天候資訊可變標誌軟體
  WMS("#WMS"), //投影顯示軟體
  ;

  private String defaultUserId;

  SubSystem(String defaultUserId) {
    this.defaultUserId = defaultUserId;
  }

  public String getDefaultUserId() {
    return defaultUserId;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "base.SubSystem";
  }
}
