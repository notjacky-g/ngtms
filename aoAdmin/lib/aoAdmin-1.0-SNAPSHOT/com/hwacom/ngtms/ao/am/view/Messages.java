package com.hwacom.ngtms.ao.am.view;

import com.google.gwt.i18n.client.LocalizableResource.Key;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.Messages.DefaultMessage;

public interface Messages extends Messages {
  @DefaultMessage("警報訊息相關資料")
  @Key("alarmLog.alarmContextData")
  String alarmLog_alarmContextData();
  
  @DefaultMessage("詳細資料table key")
  @Key("alarmLog.alarmContextKey")
  String alarmLog_alarmContextKey();
  
  @DefaultMessage("詳細資料所在table")
  @Key("alarmLog.alarmContextTable")
  String alarmLog_alarmContextTable();
  
  @DefaultMessage("警報會話(Session)編號")
  @Key("alarmLog.alarmSessionId")
  String alarmLog_alarmSessionId();
  
  @DefaultMessage("警報來源")
  @Key("alarmLog.alarmSource")
  String alarmLog_alarmSource();
  
  @DefaultMessage("警報次類別")
  @Key("alarmLog.alarmSubType")
  String alarmLog_alarmSubType();
  
  @DefaultMessage("警報次類別編號")
  @Key("alarmLog.alarmSubTypeId")
  String alarmLog_alarmSubTypeId();
  
  @DefaultMessage("警報類別")
  @Key("alarmLog.alarmType")
  String alarmLog_alarmType();
  
  @DefaultMessage("是誤報")
  @Key("alarmLog.button.isFalseAlarm")
  String alarmLog_button_isFalseAlarm();
  
  @DefaultMessage("已處理")
  @Key("alarmLog.button.processed")
  String alarmLog_button_processed();
  
  @DefaultMessage("是否已結束")
  @Key("alarmLog.closed")
  String alarmLog_closed();
  
  @DefaultMessage("是否要設定為已處理?")
  @Key("alarmLog.confirmSetAlarmProcessed")
  String alarmLog_confirmSetAlarmProcessed();
  
  @DefaultMessage("是否要設定為是誤報?")
  @Key("alarmLog.confirmSetFalseAlarm")
  String alarmLog_confirmSetFalseAlarm();
  
  @DefaultMessage("等級")
  @Key("alarmLog.degree")
  String alarmLog_degree();
  
  @DefaultMessage("設備編號")
  @Key("alarmLog.deviceName")
  String alarmLog_deviceName();
  
  @DefaultMessage("設備種類")
  @Key("alarmLog.deviceType")
  String alarmLog_deviceType();
  
  @DefaultMessage("方向")
  @Key("alarmLog.direction")
  String alarmLog_direction();
  
  @DefaultMessage("設備名稱")
  @Key("alarmLog.displayName")
  String alarmLog_displayName();
  
  @DefaultMessage("終點里程數(m)")
  @Key("alarmLog.endMileage")
  String alarmLog_endMileage();
  
  @DefaultMessage("結束時間")
  @Key("alarmLog.endTime")
  String alarmLog_endTime();
  
  @DefaultMessage("事件位置")
  @Key("alarmLog.eventLocation")
  String alarmLog_eventLocation();
  
  @DefaultMessage("警報編號")
  @Key("alarmLog.id")
  String alarmLog_id();
  
  @DefaultMessage("警報資訊")
  @Key("alarmLog.info")
  String alarmLog_info();
  
  @DefaultMessage("即時資料")
  @Key("alarmLog.instantData")
  String alarmLog_instantData();
  
  @DefaultMessage("是否誤報")
  @Key("alarmLog.isFalseAlarm")
  String alarmLog_isFalseAlarm();
  
  @DefaultMessage("路線")
  @Key("alarmLog.line")
  String alarmLog_line();
  
  @DefaultMessage("路線編號")
  @Key("alarmLog.lineId")
  String alarmLog_lineId();
  
  @DefaultMessage("其他相關資料")
  @Key("alarmLog.otherInfo")
  String alarmLog_otherInfo();
  
  @DefaultMessage("是否已處理")
  @Key("alarmLog.processed")
  String alarmLog_processed();
  
  @DefaultMessage("查詢")
  @Key("alarmLog.queryButton")
  String alarmLog_queryButton();
  
  @DefaultMessage("查詢資料")
  @Key("alarmLog.queryData")
  String alarmLog_queryData();
  
  @DefaultMessage("警報查詢")
  @Key("alarmLog.queryTitle")
  String alarmLog_queryTitle();
  
  @DefaultMessage("是否為反應計畫事件")
  @Key("alarmLog.rpsEvent")
  String alarmLog_rpsEvent();
  
  @DefaultMessage("路段")
  @Key("alarmLog.section")
  String alarmLog_section();
  
  @DefaultMessage("路段編號")
  @Key("alarmLog.sectionId")
  String alarmLog_sectionId();
  
  @DefaultMessage("警報是已處理設定失敗")
  @Key("alarmLog.setAlarmProcessed.fail")
  String alarmLog_setAlarmProcessed_fail();
  
  @DefaultMessage("警報是已處理設定成功")
  @Key("alarmLog.setAlarmProcessed.success")
  String alarmLog_setAlarmProcessed_success();
  
  @DefaultMessage("警報是誤報設定失敗")
  @Key("alarmLog.setFalseAlarm.fail")
  String alarmLog_setFalseAlarm_fail();
  
  @DefaultMessage("警報是誤報設定成功")
  @Key("alarmLog.setFalseAlarm.success")
  String alarmLog_setFalseAlarm_success();
  
  @DefaultMessage("顯示即時資料")
  @Key("alarmLog.showInstantData")
  String alarmLog_showInstantData();
  
  @DefaultMessage("起點里程數(m)")
  @Key("alarmLog.startMileage")
  String alarmLog_startMileage();
  
  @DefaultMessage("開始時間")
  @Key("alarmLog.startTime")
  String alarmLog_startTime();
  
  @DefaultMessage("切換為即時資料顯示")
  @Key("alarmLog.switchToInstantData")
  String alarmLog_switchToInstantData();
  
  @DefaultMessage("切換為查詢資料顯示")
  @Key("alarmLog.switchToQueryData")
  String alarmLog_switchToQueryData();
  
  @DefaultMessage("警報時間")
  @Key("alarmLog.timestamp")
  String alarmLog_timestamp();
  
  @DefaultMessage("取消")
  @Key("button.text.cancel")
  String button_text_cancel();
  
  @DefaultMessage("取消")
  @Key("button.text.cancel.tooltip")
  String button_text_cancel_tooltip();
  
  @DefaultMessage("清除全部設備")
  @Key("button.text.clearSelectedDevice")
  String button_text_clearSelectedDevice();
  
  @DefaultMessage("匯出檔案")
  @Key("button.text.exportToFile")
  String button_text_exportToFile();
  
  @DefaultMessage("列印")
  @Key("button.text.print")
  String button_text_print();
  
  @DefaultMessage("查詢")
  @Key("button.text.query")
  String button_text_query();
  
  @DefaultMessage("確定")
  @Key("button.text.save")
  String button_text_save();
  
  @DefaultMessage("確定")
  @Key("button.text.save.tooltip")
  String button_text_save_tooltip();
  
  @DefaultMessage("所屬區處")
  @Key("column.pdConfig.area")
  String column_pdConfig_area();
  
  @DefaultMessage("設備編號")
  @Key("column.pdConfig.deviceName")
  String column_pdConfig_deviceName();
  
  @DefaultMessage("方向")
  @Key("column.pdConfig.direction")
  String column_pdConfig_direction();
  
  @DefaultMessage("設備名稱")
  @Key("column.pdConfig.displayName")
  String column_pdConfig_displayName();
  
  @DefaultMessage("是否啟用")
  @Key("column.pdConfig.enable")
  String column_pdConfig_enable();
  
  @DefaultMessage("網路IP")
  @Key("column.pdConfig.ip")
  String column_pdConfig_ip();
  
  @DefaultMessage("緯度")
  @Key("column.pdConfig.latitude")
  String column_pdConfig_latitude();
  
  @DefaultMessage("道路名稱")
  @Key("column.pdConfig.lineId")
  String column_pdConfig_lineId();
  
  @DefaultMessage("機房名稱")
  @Key("column.pdConfig.locationNo")
  String column_pdConfig_locationNo();
  
  @DefaultMessage("經度")
  @Key("column.pdConfig.longitude")
  String column_pdConfig_longitude();
  
  @DefaultMessage("總迴路數")
  @Key("column.pdConfig.loopNo")
  String column_pdConfig_loopNo();
  
  @DefaultMessage("備註")
  @Key("column.pdConfig.memo")
  String column_pdConfig_memo();
  
  @DefaultMessage("電錶號")
  @Key("column.pdConfig.meterNo")
  String column_pdConfig_meterNo();
  
  @DefaultMessage("里程")
  @Key("column.pdConfig.milepost")
  String column_pdConfig_milepost();
  
  @DefaultMessage("聯絡電話")
  @Key("column.pdConfig.phone")
  String column_pdConfig_phone();
  
  @DefaultMessage("網路通訊埠")
  @Key("column.pdConfig.port")
  String column_pdConfig_port();
  
  @DefaultMessage("路段名稱")
  @Key("column.pdConfig.section")
  String column_pdConfig_section();
  
  @DefaultMessage("連線狀態")
  @Key("column.pdStatus.connectivity")
  String column_pdStatus_connectivity();
  
  @DefaultMessage("設備名稱")
  @Key("column.pdStatus.displayName")
  String column_pdStatus_displayName();
  
  @DefaultMessage("箱門開啟")
  @Key("column.pdStatus.doorOpen")
  String column_pdStatus_doorOpen();
  
  @DefaultMessage("全部")
  @Key("comboBox.all")
  String comboBox_all();
  
  @DefaultMessage("選擇匯出檔案格式")
  @Key("combobox.staticText.exportSelection")
  String combobox_staticText_exportSelection();
  
  @DefaultMessage("Excel")
  @Key("combobox.staticText.option.excel")
  String combobox_staticText_option_excel();
  
  @DefaultMessage("Excel(公式)")
  @Key("combobox.staticText.option.excelPoi")
  String combobox_staticText_option_excelPoi();
  
  @DefaultMessage("PDF")
  @Key("combobox.staticText.option.pdf")
  String combobox_staticText_option_pdf();
  
  @DefaultMessage("選擇印表機")
  @Key("combobox.staticText.printerSelection")
  String combobox_staticText_printerSelection();
  
  @DefaultMessage("年")
  @Key("combobox.staticText.year")
  String combobox_staticText_year();
  
  @DefaultMessage("處理中...")
  @Key("container.mask.loading")
  String container_mask_loading();
  
  @DefaultMessage("起訖時間:")
  @Key("dateRangePicker")
  String dateRangePicker();
  
  @DefaultMessage("設備種類")
  @Key("deviceConfig.deviceType")
  String deviceConfig_deviceType();
  
  @DefaultMessage("南向")
  @Key("direciton.S")
  String direciton_S();
  
  @DefaultMessage("北向")
  @Key("direction.N")
  String direction_N();
  
  @DefaultMessage("雙向")
  @Key("direction.NS")
  String direction_NS();
  
  @DefaultMessage("東")
  @Key("direction.east")
  String direction_east();
  
  @DefaultMessage("北")
  @Key("direction.north")
  String direction_north();
  
  @DefaultMessage("南")
  @Key("direction.south")
  String direction_south();
  
  @DefaultMessage("西")
  @Key("direction.west")
  String direction_west();
  
  @DefaultMessage("匯出")
  @Key("export")
  String export();
  
  @DefaultMessage("訊息")
  @Key("info")
  String info();
  
  @DefaultMessage("新增失敗")
  @Key("info.addFail")
  String info_addFail();
  
  @DefaultMessage("新增成功")
  @Key("info.addSuccessfully")
  String info_addSuccessfully();
  
  @DefaultMessage("找不到對應的圖表")
  @Key("info.err.downloadChart.message")
  String info_err_downloadChart_message();
  
  @DefaultMessage("產生圖表")
  @Key("info.err.downloadChart.title")
  String info_err_downloadChart_title();
  
  @DefaultMessage("輸入錯誤")
  @Key("info.err.inputFail")
  String info_err_inputFail();
  
  @DefaultMessage("未選擇任何路段!")
  @Key("info.err.sectionListNull")
  String info_err_sectionListNull();
  
  @DefaultMessage("喔喔")
  @Key("info.err.title")
  String info_err_title();
  
  @DefaultMessage("還有查詢條件未填喔！")
  @Key("info.invalidInput.text")
  String info_invalidInput_text();
  
  @DefaultMessage("尚未選擇任何設備")
  @Key("info.noSelectedAnyDevice")
  String info_noSelectedAnyDevice();
  
  @DefaultMessage("請選擇一筆交流道")
  @Key("info.noSelectedOneInterchange")
  String info_noSelectedOneInterchange();
  
  @DefaultMessage("請選擇資料")
  @Key("info.noSelectedOneItem")
  String info_noSelectedOneItem();
  
  @DefaultMessage("請選擇一筆交流道的匝道")
  @Key("info.noSelectedOneRamp")
  String info_noSelectedOneRamp();
  
  @DefaultMessage("請選擇一筆交流道的環道")
  @Key("info.noSelectedOneRing")
  String info_noSelectedOneRing();
  
  @DefaultMessage("請選擇一筆系統交流道")
  @Key("info.noSelectedOneSystemInterchange")
  String info_noSelectedOneSystemInterchange();
  
  @DefaultMessage("不可是空白")
  @Key("info.notEmpty")
  String info_notEmpty();
  
  @DefaultMessage("請按新增按鈕來新增新資料!")
  @Key("info.pleaseClickAddButton")
  String info_pleaseClickAddButton();
  
  @DefaultMessage("刪除失敗")
  @Key("info.removeFail")
  String info_removeFail();
  
  @DefaultMessage("刪除成功")
  @Key("info.removeSuccessfully")
  String info_removeSuccessfully();
  
  @DefaultMessage("儲存失敗")
  @Key("info.saveFail")
  String info_saveFail();
  
  @DefaultMessage("儲存成功")
  @Key("info.saveSuccessfully")
  String info_saveSuccessfully();
  
  @DefaultMessage("設備選擇")
  @Key("label.text.chooseDevice")
  String label_text_chooseDevice();
  
  @DefaultMessage("方向")
  @Key("label.text.direction")
  String label_text_direction();
  
  @DefaultMessage("路線名稱")
  @Key("label.text.roadLine")
  String label_text_roadLine();
  
  @DefaultMessage("刪除")
  @Key("menuItem.text.delete")
  String menuItem_text_delete();
  
  @DefaultMessage("訊息")
  @Key("message")
  String message();
  
  @DefaultMessage("新增失敗")
  @Key("message.addFail")
  String message_addFail();
  
  @DefaultMessage("新增成功")
  @Key("message.addSuccessfully")
  String message_addSuccessfully();
  
  @DefaultMessage("密碼不相同，請重新輸入")
  @Key("message.confirmPasswordError")
  String message_confirmPasswordError();
  
  @DefaultMessage("協調者不能執行停止操作")
  @Key("message.coordinatorCanNotStop")
  String message_coordinatorCanNotStop();
  
  @DefaultMessage("資料擷取失敗")
  @Key("message.dataFetchError")
  String message_dataFetchError();
  
  @DefaultMessage("刪除失敗")
  @Key("message.deleteFail")
  String message_deleteFail();
  
  @DefaultMessage("刪除成功")
  @Key("message.deleteSuccessfully")
  String message_deleteSuccessfully();
  
  @DefaultMessage("確定要做 SSO 同步嗎?")
  @Key("message.doSSO")
  String message_doSSO();
  
  @DefaultMessage("此筆資料確定要新增嗎?")
  @Key("message.itemAddConfirm")
  String message_itemAddConfirm();
  
  @DefaultMessage("此筆資料確定要刪除嗎?")
  @Key("message.itemDeleteConfirm")
  String message_itemDeleteConfirm();
  
  @DefaultMessage("此筆資料已存在!若要修改請按儲存鈕!")
  @Key("message.itemExisted")
  String message_itemExisted();
  
  @DefaultMessage("此筆資料不存在!可按新增鈕新增此筆資料!")
  @Key("message.itemNoExisted")
  String message_itemNoExisted();
  
  @DefaultMessage("此筆資料確定要儲存嗎?")
  @Key("message.itemSaveConfirm")
  String message_itemSaveConfirm();
  
  @DefaultMessage("否")
  @Key("message.no")
  String message_no();
  
  @DefaultMessage("尚未選擇項目")
  @Key("message.notSelectedItem")
  String message_notSelectedItem();
  
  @DefaultMessage("確定要儲存此筆嗎?")
  @Key("message.saveConfirm")
  String message_saveConfirm();
  
  @DefaultMessage("儲存失敗")
  @Key("message.saveFail")
  String message_saveFail();
  
  @DefaultMessage("儲存成功")
  @Key("message.saveSuccessfully")
  String message_saveSuccessfully();
  
  @DefaultMessage("啟動中，請稍候...")
  @Key("message.startHcNode")
  String message_startHcNode();
  
  @DefaultMessage("開始時間必須大於結束時間")
  @Key("message.startTimeGreaterThanEndTimeError")
  String message_startTimeGreaterThanEndTimeError();
  
  @DefaultMessage("停止中，請稍候...")
  @Key("message.stopHcNode")
  String message_stopHcNode();
  
  @DefaultMessage("系統啟動中")
  @Key("message.systemActive")
  String message_systemActive();
  
  @DefaultMessage("系統已斷線")
  @Key("message.systemDisconnected")
  String message_systemDisconnected();
  
  @DefaultMessage("系統不是啟動中無法操作")
  @Key("message.systemNotOperation")
  String message_systemNotOperation();
  
  @DefaultMessage("系統待命中")
  @Key("message.systemStandby")
  String message_systemStandby();
  
  @DefaultMessage("系統不是啟動中無法取得資料")
  @Key("message.systemStandbyNotGetData")
  String message_systemStandbyNotGetData();
  
  @DefaultMessage("確定要重載嗎?")
  @Key("message.toReload")
  String message_toReload();
  
  @DefaultMessage("處理中，請稍候...")
  @Key("message.waiting")
  String message_waiting();
  
  @DefaultMessage("是")
  @Key("message.yes")
  String message_yes();
  
  @DefaultMessage("取消")
  @Key("pdConfigSettingViewer.cancel")
  String pdConfigSettingViewer_cancel();
  
  @DefaultMessage("確認")
  @Key("pdConfigSettingViewer.confirm")
  String pdConfigSettingViewer_confirm();
  
  @DefaultMessage("是否刪除迴路底下存在設備的Pd設備")
  @Key("pdConfigSettingViewer.confirmDeleteDevice")
  String pdConfigSettingViewer_confirmDeleteDevice();
  
  @DefaultMessage("設備設定")
  @Key("pdConfigSettingViewer.deviceSetting")
  String pdConfigSettingViewer_deviceSetting();
  
  @DefaultMessage("否")
  @Key("pdConfigSettingViewer.enable.false")
  String pdConfigSettingViewer_enable_false();
  
  @DefaultMessage("是")
  @Key("pdConfigSettingViewer.enable.true")
  String pdConfigSettingViewer_enable_true();
  
  @DefaultMessage("資料調整中")
  @Key("pdConfigSettingViewer.info.adjusting")
  String pdConfigSettingViewer_info_adjusting();
  
  @DefaultMessage("已有此PD點資料")
  @Key("pdConfigSettingViewer.info.deviceExist")
  String pdConfigSettingViewer_info_deviceExist();
  
  @DefaultMessage("PD設備名稱為空")
  @Key("pdConfigSettingViewer.info.deviceNameEmpty")
  String pdConfigSettingViewer_info_deviceNameEmpty();
  
  @DefaultMessage("有必填欄位為空")
  @Key("pdConfigSettingViewer.info.fieldEmpty")
  String pdConfigSettingViewer_info_fieldEmpty();
  
  @DefaultMessage("請重新輸入")
  @Key("pdConfigSettingViewer.info.inputAgain")
  String pdConfigSettingViewer_info_inputAgain();
  
  @DefaultMessage("請重新選取設備")
  @Key("pdConfigSettingViewer.info.selectDeviceAgain")
  String pdConfigSettingViewer_info_selectDeviceAgain();
  
  @DefaultMessage("修改")
  @Key("pdConfigSettingViewer.update")
  String pdConfigSettingViewer_update();
  
  @DefaultMessage("更新設備狀態")
  @Key("pdStatus.button.update")
  String pdStatus_button_update();
  
  @DefaultMessage("全部")
  @Key("pdStatus.comboBox.all")
  String pdStatus_comboBox_all();
  
  @DefaultMessage("分迴路")
  @Key("pdStatus.headerGroup.loop")
  String pdStatus_headerGroup_loop();
  
  @DefaultMessage("一次側")
  @Key("pdStatus.headerGroup.primary")
  String pdStatus_headerGroup_primary();
  
  @DefaultMessage("二次側")
  @Key("pdStatus.headerGroup.secondary")
  String pdStatus_headerGroup_secondary();
  
  @DefaultMessage("PD點設備狀態")
  @Key("pdStatus.heading.pdstatus")
  String pdStatus_heading_pdstatus();
  
  @DefaultMessage("異常狀態")
  @Key("pdStatus.label.abnormalStatus")
  String pdStatus_label_abnormalStatus();
  
  @DefaultMessage("路線")
  @Key("pdStatus.label.roadLine")
  String pdStatus_label_roadLine();
  
  @DefaultMessage("異常")
  @Key("pdStatus.status.abnormal")
  String pdStatus_status_abnormal();
  
  @DefaultMessage("關閉")
  @Key("pdStatus.status.close")
  String pdStatus_status_close();
  
  @DefaultMessage("正常")
  @Key("pdStatus.status.normal")
  String pdStatus_status_normal();
  
  @DefaultMessage("斷線")
  @Key("pdStatus.status.offline")
  String pdStatus_status_offline();
  
  @DefaultMessage("連線")
  @Key("pdStatus.status.online")
  String pdStatus_status_online();
  
  @DefaultMessage("開啟")
  @Key("pdStatus.status.open")
  String pdStatus_status_open();
  
  @DefaultMessage("報表產生軟體")
  @Key("rpt.title")
  String rpt_title();
  
  @DefaultMessage("警報列表")
  @Key("system.alarm")
  String system_alarm();
  
  @DefaultMessage("確認警報")
  @Key("system.alarm.ack")
  String system_alarm_ack();
  
  @DefaultMessage("確認")
  @Key("system.alarm.ack.button")
  String system_alarm_ack_button();
  
  @DefaultMessage("非常危急")
  @Key("system.alarm.confirm.priority.critical")
  String system_alarm_confirm_priority_critical();
  
  @DefaultMessage("優先")
  @Key("system.alarm.confirm.priority.high")
  String system_alarm_confirm_priority_high();
  
  @DefaultMessage("一般")
  @Key("system.alarm.confirm.priority.medium")
  String system_alarm_confirm_priority_medium();
  
  @DefaultMessage("設備名稱")
  @Key("system.alarm.deviceName")
  String system_alarm_deviceName();
  
  @DefaultMessage("警報等級")
  @Key("system.alarm.level")
  String system_alarm_level();
  
  @DefaultMessage("警報訊息")
  @Key("system.alarm.message")
  String system_alarm_message();
  
  @DefaultMessage("警報狀態")
  @Key("system.alarm.state")
  String system_alarm_state();
  
  @DefaultMessage("未確認")
  @Key("system.alarm.state.unack.alm")
  String system_alarm_state_unack_alm();
  
  @DefaultMessage("已自動排除")
  @Key("system.alarm.state.unack.rtn")
  String system_alarm_state_unack_rtn();
  
  @DefaultMessage("時間")
  @Key("system.alarm.timestamp")
  String system_alarm_timestamp();
  
  @DefaultMessage("報表批次列印")
  @Key("tab.reportBatchPrint")
  String tab_reportBatchPrint();
  
  @DefaultMessage("報表產生")
  @Key("tab.reportGeneration")
  String tab_reportGeneration();
  
  @DefaultMessage("請稍後...")
  @Key("waiting")
  String waiting();
  
  @DefaultMessage("機房門禁管制系統")
  @Key("windowTitle")
  String windowTitle();
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */