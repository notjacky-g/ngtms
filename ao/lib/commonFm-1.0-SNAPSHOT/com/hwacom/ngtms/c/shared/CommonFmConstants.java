package com.hwacom.ngtms.c.shared;

public class CommonFmConstants {
  // 定義 動態組態名稱
  //RptAbsoluteResourcePath: 暫存性資源(如圖檔)的路徑, 預設值: /temp
  public static final String RPT_ABSOLUTE_RESOURCE_PATH = "RptAbsoluteResourcePath";

  //RptResourceBaseUri: 暫存性資源對應的Uri根位址, 預設值: http://emm.cfreeway.nat.gov.tw/images/
  public static final String RPT_RESOURCE_BASE_URI = "RptResourceBaseUri";

  //RptServiceUsingRemotePolicy: 決定RPT Remote在收到要求時，是否移轉至獨立的service去完成, 預設值: false
  public static final String RPT_SERVICE_USING_REMOTE_POLICY = "RptServiceUsingRemotePolicy";

  //RptExportFileDirectory: 匯出檔案存放路徑, 預設值: /shared/rpt/
  public static final String RPT_EXPORT_FILE_DIRECTORY = "RptExportFileDirectory";

  //RptBulkProcessServiceUri: 調用其他Remote Service做大量圖形資料處理, 預設值: rmi://rpt.freeway.intra:5000/RptBulkProcessRemote
  public static final String RPT_BULK_PROCESS_SERVICE_URI = "RptBulkProcessServiceUri";
}
