/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.dom.client.Element;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import java.util.ArrayList;
import java.util.List;

public class ExportCsvFile {
  private static final String CRLF = "\r\n";

  public static void exportAsCsv(String filename, List<List<String>> data) {
    StringBuilder sb = new StringBuilder();
    sb.append('\uFEFF');
    for (List<String> row : data) {
      for (int i = 0; i < row.size(); i++) {
        if (i > 0) sb.append(",");
        if (row.get(i) == null) {
          sb.append("");
        } else {
          if (isNumeric(row.get(i))) {
            sb.append("=\"" + row.get(i) + "\"");
          } else {
            sb.append('"' + row.get(i) + '"');
          }
        }
      }
      sb.append(CRLF);
    }

    generateCsv(filename, sb.toString());
  }

  private static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?"); //match a number with optional '-' and decimal.
  }

  private static native void generateCsv(String filename, String text)
  /*-{
      var blob = new Blob([text], { type: 'text/csv;charset=utf-8;' });

      if (navigator.msSaveBlob) // IE 10+
      {
          navigator.msSaveBlob(blob, filename);
      }
      else
      {
          var link = document.createElement("a");
          if (link.download !== undefined) // feature detection
          {
              // Browsers that support HTML5 download attribute
              var url = URL.createObjectURL(blob);
              link.setAttribute("href", url);
              link.setAttribute("download", filename);
              link.style.visibility = 'hidden';
              document.body.appendChild(link);
              link.click();
              document.body.removeChild(link);
          }
      }
  }-*/ ;

  public static <M> void export(Grid<M> grid, String fileName) {
    export(grid, fileName, true);
  }

  public static <M> void export(Grid<M> grid, String fileName, List<List<String>> preMessageList) {
    export(grid, fileName, true, preMessageList);
  }

  /**
   * @param grid
   * @param fileName
   * @param visibleOnly 是否列印隱藏的 Column, true 不印, false 印
   * @param <M>
   */
  public static <M> void export(Grid<M> grid, String fileName, boolean visibleOnly) {
    if (grid.getStore().size() == 0) {
      return;
    }

    List<List<String>> csv = new ArrayList<>();
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
    csv.add(header);

    // generate data
    GridView<M> gridView = grid.getView();
    int listStoreSize = grid.getStore().getAll().size();
    for (int row = 0; row < listStoreSize; row++) {
      List<String> dataRows = new ArrayList<>();
      for (int col : columnIndices) {
        Element e = gridView.getCell(row, col);
        dataRows.add(e.getInnerText());
      }
      csv.add(dataRows);
    }
    exportAsCsv(fileName, csv);
  }

  /**
   * @param grid
   * @param fileName
   * @param visibleOnly 是否列印隱藏的 Column, true 不印, false 印
   * @param preMessageList 放在 grid 前面的 Message
   * @param <M>
   */
  public static <M> void export(
      Grid<M> grid, String fileName, boolean visibleOnly, List<List<String>> preMessageList) {
    if (grid.getStore().size() == 0 && (preMessageList == null || preMessageList.isEmpty())) {
      return;
    }

    List<List<String>> csv = new ArrayList<>();
    csv.addAll(preMessageList);
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
    csv.add(header);

    // generate data
    GridView<M> gridView = grid.getView();
    int listStoreSize = grid.getStore().getAll().size();
    for (int row = 0; row < listStoreSize; row++) {
      List<String> dataRows = new ArrayList<>();
      for (int col : columnIndices) {
        Element e = gridView.getCell(row, col);
        dataRows.add(e.getInnerText());
      }
      csv.add(dataRows);
    }
    exportAsCsv(fileName, csv);
  }
}
