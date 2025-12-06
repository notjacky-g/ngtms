package com.hwacom.ngtms.common.am.presenter;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.ReportEP;
import com.hwacom.ngtms.common.am.view.ReportViewer;
import com.hwacom.ngtms.common.shared.dto.ExportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import java.util.List;
import java.util.function.BiConsumer;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.TextCallback;

public class ReportPresenter {

  private ReportViewer viewer;

  public ReportPresenter(ReportViewer viewer, String module) {
    this.viewer = viewer;
    retrieveReportTreeDTO(module);
  }

  private void retrieveReportTreeDTO(String module) {
    ReportEP.service.retrieveReportTreeDTO(
        module,
        new MethodCallback<List<ReportTreeDTO>>() {
          @Override
          public void onSuccess(Method method, List<ReportTreeDTO> response) {
            viewer.initTreeData(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("ReportPresenter.retrieveReportTreeDTO failed.", exception);
          }
        });
  }

  public void previewReport(
      PreviewReportDTO previewReportDTO,
      BiConsumer<String, PreviewReportOutputDTO> consumer,
      Callback<PagingLoadResult<String>, Throwable> callback) {
    ReportEP.service.previewReport(
        previewReportDTO,
        new MethodCallback<PreviewReportOutputDTO>() {
          @Override
          public void onSuccess(Method method, PreviewReportOutputDTO dto) {
            consumer.accept(previewReportDTO.getReportId(), dto);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("ReportPresenter.previewReport failed.", exception);
            callback.onFailure(exception);
          }
        });
  }

  public void exportCsv(ExportDTO dto) {
    ReportEP.service.export(
        dto,
        new TextCallback() {
          @Override
          public void onSuccess(Method method, String csv) {
            viewer.downloadCsv(csv);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("ReportPresenter.exportCsv failed.", exception);
          }
        });
  }

  public void exportFile(ExportDTO dto) {
    ReportEP.service.export(
        dto,
        new TextCallback() {
          @Override
          public void onSuccess(Method method, String file) {
            viewer.downloadFile(file);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("ReportPresenter.exportCsv failed.", exception);
          }
        });
  }

  public void exportPng(ExportDTO dto, Integer pages, List<String> fileList) {
    ReportEP.service.export(
        dto,
        new TextCallback() {
          @Override
          public void onSuccess(Method method, String file) {
            fileList.add(file);
            if (dto.getChartPage() < pages - 1) {
              dto.setChartPage(dto.getChartPage() + 1);
              exportPng(dto, pages, fileList);
            } else {
              viewer.downloadFiles(fileList);
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("ReportPresenter.exportPng failed.", exception);
          }
        });
  }

  public void exportJpeg(ExportDTO dto, Integer pages, List<String> fileList) {
    ReportEP.service.export(
        dto,
        new TextCallback() {
          @Override
          public void onSuccess(Method method, String file) {
            fileList.add(file);
            if (dto.getChartPage() < pages - 1) {
              dto.setChartPage(dto.getChartPage() + 1);
              exportPng(dto, pages, fileList);
            } else {
              viewer.downloadFiles(fileList);
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("ReportPresenter.exportJpeg failed.", exception);
          }
        });
  }
}
