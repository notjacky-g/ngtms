/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.hwacom.ngtms.c.dis.shared.DownloadAllBackgroundGraphicMessage;
import com.hwacom.ngtms.c.dis.shared.DownloadBackgroundGraphicMessage;
import com.hwacom.ngtms.c.dis.shared.dto.GraphicConfigDTO;
import com.hwacom.ngtms.cam.client.event.BackgroundGraphicEditViewerEvent;
import com.hwacom.ngtms.cam.client.event.BackgroundGraphicEditViewerEvent.Action;
import com.hwacom.ngtms.cam.client.event.BackgroundGraphicEditViewerEvent.BackgroundGraphicEditViewerEventHandler;
import com.hwacom.ngtms.cam.util.CommonUtil;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.writer.JsonWriter;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.SingleUploader;
import java.util.ArrayList;
import java.util.List;

public class BackgroundGraphicEditViewer extends Composite {

  private static BackgroundGraphicEditViewerUiBinder uiBinder =
      GWT.create(BackgroundGraphicEditViewerUiBinder.class);

  interface BackgroundGraphicEditViewerUiBinder
      extends UiBinder<Widget, BackgroundGraphicEditViewer> {}

  private GraphicConfigPropertyAccess propertyAccess =
      GWT.create(GraphicConfigPropertyAccess.class);

  private Messages messages = GWT.create(Messages.class);

  private ColumnConfig<GraphicConfigDTO, String> descriptionColumnConfig;

  private List<String> deviceNames = new ArrayList<>();

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private Presenter presenter;

  private BackgroundAutoBeanFactory factory = GWT.create(BackgroundAutoBeanFactory.class);

  @UiField Grid<GraphicConfigDTO> grid;

  @UiField(provided = true)
  ListStore<GraphicConfigDTO> listStore;

  @UiField(provided = true)
  ColumnModel<GraphicConfigDTO> columnModel;

  @UiField GridView<GraphicConfigDTO> gridView;

  @UiField TextButton downloadAll;

  @UiField TextButton download;

  @UiField TextField description;

  @UiField SingleUploader uploader;

  @UiField Image graphic;

  @UiField TextButton create;

  @UiField TextButton modify;

  @UiField TextButton remove;

  public BackgroundGraphicEditViewer() {
    listStore = new ListStore<>(propertyAccess.id());
    listStore.addSortInfo(new StoreSortInfo<>(propertyAccess.tcCodeId(), SortDir.ASC));
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    uploader.setServletPath(GWT.getHostPageBaseURL() + "common/backgroundGraphicUploader");
    gridView.setAutoExpandColumn(descriptionColumnConfig);
    gridView.setAutoExpandMax(1000);
    gridView.setAutoExpandMin(descriptionColumnConfig.getWidth());
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    addEventHandlers();
    clientFactory
        .getEventBus()
        .addHandler(
            BackgroundGraphicEditViewerEvent.TYPE,
            new DefaultBackgroundGraphicEditViewerEventHandler());
  }

  private void initColumnModel() {
    List<ColumnConfig<GraphicConfigDTO, ?>> configs = new ArrayList<>();
    ColumnConfig<GraphicConfigDTO, Integer> gCodeId = new ColumnConfig<>(propertyAccess.tcCodeId());
    gCodeId.setHeader(messages.backgroundGraphic_edit_grid_gCodeId());
    gCodeId.setWidth(40);
    configs.add(gCodeId);

    ColumnConfig<GraphicConfigDTO, Integer> preview = new ColumnConfig<>(propertyAccess.tcCodeId());
    final int height = 48;
    preview.setCell(
        new AbstractCell<Integer>() {
          @Override
          public void render(Context context, Integer value, SafeHtmlBuilder sb) {
            Image image = new Image(CommonUtil.getBackgroundGraphicUrl(value));
            image.setHeight(height + Unit.PX.getType());
            sb.appendHtmlConstant(image.getElement().getString());
          }
        });
    preview.setWidth(200);
    preview.setHeader(messages.backgroundGraphic_edit_grid_preview());
    configs.add(preview);

    descriptionColumnConfig = new ColumnConfig<>(propertyAccess.description());
    descriptionColumnConfig.setHeader(messages.backgroundGraphic_edit_grid_description());
    descriptionColumnConfig.setWidth(120);
    configs.add(descriptionColumnConfig);

    columnModel = new ColumnModel<>(configs);
  }

  private void addEventHandlers() {
    grid.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<GraphicConfigDTO>() {
              @Override
              public void onSelection(SelectionEvent<GraphicConfigDTO> event) {
                clientFactory
                    .getEventBus()
                    .fireEventFromSource(
                        new BackgroundGraphicEditViewerEvent(Action.SELECT_GRAPHIC),
                        event.getSelectedItem());
              }
            });

    grid.getSelectionModel()
        .addSelectionChangedHandler(
            new SelectionChangedHandler<GraphicConfigDTO>() {
              @Override
              public void onSelectionChanged(SelectionChangedEvent<GraphicConfigDTO> event) {
                if (event.getSelection().isEmpty()) {
                  description.clearInvalid();
                  description.setValue(null);
                  modify.disable();
                  remove.disable();
                  download.disable();
                }
              }
            });

    uploader.addOnFinishUploadHandler(
        new OnFinishUploaderHandler() {
          @Override
          public void onFinish(IUploader uploader) {
            uploader.clear();
            if (uploader.getStatus() == Status.SUCCESS) {
              graphic.setUrl(uploader.getServerMessage().getUploadedFileUrls().get(0));
              create.enable();
            }
          }
        });
  }

  @UiHandler("downloadAll")
  public void onDownloadAll(SelectEvent event) {
    clientFactory
        .getEventBus()
        .fireEvent(new BackgroundGraphicEditViewerEvent(Action.DOWNLOAD_ALL));
    mask(messages.inProgress());
  }

  @UiHandler("download")
  public void onDownload(SelectEvent event) {
    clientFactory
        .getEventBus()
        .fireEventFromSource(
            new BackgroundGraphicEditViewerEvent(Action.DOWNLOAD),
            grid.getSelectionModel().getSelectedItem());
    mask(messages.inProgress());
  }

  @UiHandler("create")
  public void onCreate(SelectEvent event) {
    if (description.isValid()) {
      clientFactory
          .getEventBus()
          .fireEvent(new BackgroundGraphicEditViewerEvent(Action.CREATE_GRAPHIC));
    }
  }

  @UiHandler("modify")
  public void onModify(SelectEvent event) {
    if (description.isValid()) {
      clientFactory
          .getEventBus()
          .fireEventFromSource(
              new BackgroundGraphicEditViewerEvent(Action.MODIFY_GRAPHIC),
              description.getCurrentValue());
    }
  }

  @UiHandler("remove")
  public void onRemove(SelectEvent event) {
    ConfirmMessageBox confirm =
        new ConfirmMessageBox(
            messages.backgroundGraphic_edit_button_remove_title(),
            messages.backgroundGraphic_edit_button_remove_message());
    confirm.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              clientFactory
                  .getEventBus()
                  .fireEvent(new BackgroundGraphicEditViewerEvent(Action.REMOVE_GRAPHIC));
            }
          }
        });
    confirm.show();
  }

  public void addDevices(List<String> deviceNames) {
    for (String deviceName : deviceNames) {
      if (!this.deviceNames.contains(deviceName)) {
        this.deviceNames.add(deviceName);
      }
    }
    clientFactory
        .getEventBus()
        .fireEvent(new BackgroundGraphicEditViewerEvent(Action.DEVICE_CHANGED));
  }

  public void removeDevices(List<String> deviceNames) {
    this.deviceNames.removeAll(deviceNames);
    clientFactory
        .getEventBus()
        .fireEvent(new BackgroundGraphicEditViewerEvent(Action.DEVICE_CHANGED));
  }

  public void init(List<GraphicConfigDTO> result) {
    listStore.addAll(result);
    grid.getSelectionModel().select(0, false);
  }

  public void addGraphic(GraphicConfigDTO result) {
    listStore.add(result);
    grid.getSelectionModel().select(result, false);
    create.disable();
  }

  public void operateFailed() {
    Info.display(
        messages.backgroundGraphic_edit_failed_title(),
        messages.backgroundGraphic_edit_failed_message());
  }

  public void updateGraphic(GraphicConfigDTO dto) {
    listStore.update(dto);
    create.disable();
  }

  public void removeGraphic(GraphicConfigDTO dto) {
    listStore.remove(dto);
    graphic.setUrl("");
  }

  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  public void downloadFinished() {
    unmask();
  }

  class DefaultBackgroundGraphicEditViewerEventHandler
      implements BackgroundGraphicEditViewerEventHandler {

    public void onDeviceChanged(BackgroundGraphicEditViewerEvent event) {
      if (deviceNames.isEmpty()) {
        downloadAll.disable();
      } else {
        downloadAll.enable();
      }
      changeDownloadButtonState();
    }

    private void changeDownloadButtonState() {
      if (deviceNames.isEmpty() || grid.getSelectionModel().getSelectedItem() == null) {
        download.disable();
      } else {
        download.enable();
      }
    }

    public void onSelectGraphic(BackgroundGraphicEditViewerEvent event) {
      GraphicConfigDTO selectedItem = (GraphicConfigDTO) event.getSource();
      description.clearInvalid();
      description.setValue(selectedItem.getDescription());
      graphic.setUrl(CommonUtil.getBackgroundGraphicUrl(selectedItem.getTcCodeId()));
      modify.enable();
      remove.enable();
      changeDownloadButtonState();
    }

    public void onDownloadAll(BackgroundGraphicEditViewerEvent event) {
      AutoBean<DownloadAllBackgroundGraphicMessage> autoBean =
          factory.downloadAllBackgroundGraphicMessage();
      DownloadAllBackgroundGraphicMessage message = autoBean.as();
      message.setType(DownloadAllBackgroundGraphicMessage.TYPE);
      message.setDeviceNames(deviceNames);

      JsonWriter<DownloadAllBackgroundGraphicMessage> jsonWriter =
          new JsonWriter<>(factory, DownloadAllBackgroundGraphicMessage.class);
      String json = jsonWriter.write(message);
      presenter.downloadAll(json);
    }

    public void onDownload(BackgroundGraphicEditViewerEvent event) {
      final GraphicConfigDTO dto = (GraphicConfigDTO) event.getSource();
      AutoBean<DownloadBackgroundGraphicMessage> autoBean =
          factory.downloadBackgroundGraphicMessage();
      DownloadBackgroundGraphicMessage message = autoBean.as();
      message.setType(DownloadBackgroundGraphicMessage.TYPE);
      message.setDeviceNames(deviceNames);
      message.setGraphicConfigId(dto.getId());
      message.setTcCodeId(dto.getTcCodeId());

      JsonWriter<DownloadBackgroundGraphicMessage> jsonWriter =
          new JsonWriter<>(factory, DownloadBackgroundGraphicMessage.class);
      String json = jsonWriter.write(message);
      presenter.download(json);
    }

    public void onCreateGraphic(BackgroundGraphicEditViewerEvent event) {
      presenter.createGraphic(description.getCurrentValue());
    }

    public void onModifyGraphic(BackgroundGraphicEditViewerEvent event) {
      GraphicConfigDTO selectedItem = grid.getSelectionModel().getSelectedItem();
      presenter.modifyGraphic(selectedItem, (String) event.getSource());
    }

    public void onRemoveGraphic(BackgroundGraphicEditViewerEvent event) {
      presenter.removeGraphic(grid.getSelectionModel().getSelectedItem());
    }
  }

  public static class ClickTextButton extends TextButton implements HasClickHandlers {
    public HandlerRegistration addClickHandler(ClickHandler handler) {
      return addDomHandler(handler, ClickEvent.getType());
    }
  }

  public interface Presenter {
    void createGraphic(String description);

    void modifyGraphic(GraphicConfigDTO dto, String description);

    void removeGraphic(GraphicConfigDTO dto);

    void download(String json);

    void downloadAll(String json);
  }

  interface GraphicConfigPropertyAccess extends PropertyAccess<GraphicConfigDTO> {
    ModelKeyProvider<GraphicConfigDTO> id();

    @Path("id")
    ValueProvider<GraphicConfigDTO, String> idValueProvider();

    ValueProvider<GraphicConfigDTO, Integer> tcCodeId();

    ValueProvider<GraphicConfigDTO, String> description();
  }

  interface BackgroundAutoBeanFactory extends AutoBeanFactory {
    AutoBean<DownloadAllBackgroundGraphicMessage> downloadAllBackgroundGraphicMessage();

    AutoBean<DownloadBackgroundGraphicMessage> downloadBackgroundGraphicMessage();
  }
}
