package com.hwacom.ngtms.room.am.view;

/**
 * Interface to represent the messages contained in resource bundle:
 * /data/workspace/NGTMS-tms-1.0/am/am-room/src/main/resources/com/hwacom/ngtms/room/am/view/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {

  /**
   * Translated "儲存".
   *
   * @return translated "儲存"
   */
  @DefaultMessage("儲存")
  @Key("button.save")
  String button_save();

  /**
   * Translated "訊息".
   *
   * @return translated "訊息"
   */
  @DefaultMessage("訊息")
  @Key("info")
  String info();

  /**
   * Translated "找不到機房地圖資料".
   *
   * @return translated "找不到機房地圖資料"
   */
  @DefaultMessage("找不到機房地圖資料")
  @Key("info.cannot.findRoomDevice")
  String info_cannot_findRoomDevice();

  /**
   * Translated "資料錯誤".
   *
   * @return translated "資料錯誤"
   */
  @DefaultMessage("資料錯誤")
  @Key("info.data.error")
  String info_data_error();

  /**
   * Translated "失敗".
   *
   * @return translated "失敗"
   */
  @DefaultMessage("失敗")
  @Key("info.fail")
  String info_fail();

  /**
   * Translated "儲存".
   *
   * @return translated "儲存"
   */
  @DefaultMessage("儲存")
  @Key("info.save")
  String info_save();

  /**
   * Translated "儲存失敗".
   *
   * @return translated "儲存失敗"
   */
  @DefaultMessage("儲存失敗")
  @Key("info.saveFail")
  String info_saveFail();

  /**
   * Translated "儲存成功".
   *
   * @return translated "儲存成功"
   */
  @DefaultMessage("儲存成功")
  @Key("info.saveSuccessFully")
  String info_saveSuccessFully();

  /**
   * Translated "成功".
   *
   * @return translated "成功"
   */
  @DefaultMessage("成功")
  @Key("info.successFully")
  String info_successFully();

  /**
   * Translated "機房門禁管制系統".
   *
   * @return translated "機房門禁管制系統"
   */
  @DefaultMessage("機房門禁管制系統")
  @Key("room")
  String room();
}
