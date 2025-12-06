/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.hwacom.ngtms.c.shared.dto.AmParametersDTO;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.TextCallback;

public class ExportExcelFile {
  private static final String SERVER_DOWNLOAD_URL =
      GWT.getHostPageBaseURL() + "DownloadFileServlet";
  private static final String ACTION_DOWNLOAD_EXCEL = "downloadExcel";

  public static <M> List<List<String>> transferAsList(Grid<M> grid, boolean visibleOnly) {
    if (grid.getStore().size() == 0) {
      return new ArrayList<>();
    }

    List<List<String>> result = new ArrayList<>();
    // generate header
    List<String> header = new ArrayList<>();
    List<Integer> columnIndices = new ArrayList<>();
    ColumnModel<M> columnModel = grid.getColumnModel();
    for (ColumnConfig<M, ?> config : columnModel.getColumns()) {
      if (visibleOnly && config.isHidden()) {
        continue;
      }
      header.add(config.getHeader().asString());
      columnIndices.add(columnModel.indexOf(config));
    }
    result.add(header);

    // generate data
    GridView<M> gridView = grid.getView();
    int listStoreSize = grid.getStore().getAll().size();
    for (int row = 0; row < listStoreSize; row++) {
      List<String> dataRows = new ArrayList<>();
      for (int col : columnIndices) {
        Element e = gridView.getCell(row, col);
        dataRows.add(e.getInnerText());
      }
      result.add(dataRows);
    }
    return result;
  }

  /**
   * 匯出Excel檔，檔案格式為xlsx
   *
   * @param grid
   * @param fileName 不用加上附檔名
   * @param columnWidths 選擇性，若給null，將不會指定欄寬；<br>
   *     若有設定，也不需要每個欄位都給定欄寬，故若將List中的某個item設為null，就不會設定該欄欄寬
   */
  public static <M> void export(Grid<M> grid, String fileName, List<Integer> columnWidths) {
    export(grid, fileName, columnWidths, true);
  }

  /**
   * 匯出Excel檔，檔案格式為xlsx
   *
   * @param grid
   * @param fileName 不用加上附檔名
   * @param columnWidths 選擇性，若給null，將不會指定欄寬；<br>
   *     若有設定，也不需要每個欄位都給定欄寬，故若將List中的某個item設為null，就不會設定該欄欄寬
   * @param visibleOnly 是否列印隱藏的 Column, true 不印, false 印
   */
  public static <M> void export(
      Grid<M> grid, String fileName, List<Integer> columnWidths, boolean visibleOnly) {
    if (grid.getStore().size() == 0) {
      return;
    }
    export(transferAsList(grid, visibleOnly), fileName, columnWidths);
  }

  /**
   * 匯出Excel檔，檔案格式為xlsx
   *
   * @param data
   * @param fileName 不用加上附檔名
   * @param columnWidths 選擇性，若給null，將不會指定欄寬；<br>
   *     若有設定，也不需要每個欄位都給定欄寬，故若將List中的某個item設為null，就不會設定該欄欄寬
   */
  public static <M> void export(
      List<List<String>> data, final String fileName, List<Integer> columnWidths) {
    AmParametersDTO params = new AmParametersDTO();
    params.setCsvData(data);
    params.setCsvHeaderWidths(columnWidths);
    HomeEP.camService.exportOrderedListDataFromRpt(
        params,
        new TextCallback() {
          @Override
          public void onSuccess(Method method, String result) {
            if (result != null && !result.isEmpty()) {
              String encodedFileName = URL.encodeQueryString(fileName + ".xlsx");
              String encodedUrl = URL.encode(result);
              String downloadUrl =
                  SERVER_DOWNLOAD_URL
                      + "?action="
                      + ACTION_DOWNLOAD_EXCEL
                      + "&fileName="
                      + encodedFileName
                      + "&url="
                      + encodedUrl;
              GWT.log("downloadUrl url: " + downloadUrl);
              Window.open(downloadUrl, null, null);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("ExportExcelFile.export failed.", caught);
          }
        });
  }

  private static native void jsniDownload(String url, String fileName)
  /*-{
      var link = document.createElement("a");
      if (link.download !== undefined) // feature detection
      {
          // Browsers that support HTML5 download attribute
          link.setAttribute("href", [url]);
          link.setAttribute("download", [fileName]);
          link.setAttribute("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
          link.style.visibility = 'hidden';
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
      }
  }-*/ ;
}
