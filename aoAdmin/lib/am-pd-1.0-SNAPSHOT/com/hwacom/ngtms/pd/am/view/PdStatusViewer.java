package com.hwacom.ngtms.pd.am.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.pd.am.presenter.PdStatusPresenter;
import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
import com.hwacom.ngtms.pd.shared.dto.PdStatusDTO;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreFilter;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vectomatic.dom.svg.OMElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.utils.OMSVGParser;

public class PdStatusViewer extends AmTab {

  private static PdStatusViewerUiBinder uiBinder = GWT.create(PdStatusViewerUiBinder.class);

  interface PdStatusViewerUiBinder extends UiBinder<Widget, PdStatusViewer> {}

  private static Messages messages = GWT.create(Messages.class);

  private final PdStatusPropertyAccess propertyAccess = GWT.create(PdStatusPropertyAccess.class);

  private final RoadLineProertyAccess roadLineProertyAccess =
      GWT.create(RoadLineProertyAccess.class);

  private PdStatusPresenter presenter = new PdStatusPresenter(this);

  @UiField Grid<PdStatusDTO> grid;

  @UiField(provided = true)
  ListStore<PdStatusDTO> listStore;

  @UiField(provided = true)
  ColumnModel<PdStatusDTO> columnModel;

  @UiField GridView<PdStatusDTO> gridView;

  @UiField ComboBox<RoadLineDTO> roadLineCB;

  @UiField(provided = true)
  ListStore<RoadLineDTO> roadLineStore;

  @UiField(provided = true)
  LabelProvider<RoadLineDTO> roadLineProvider;

  @UiField ComboBox<String> abnormalStatusCB;

  @UiField(provided = true)
  ListStore<String> abnormalStatusStore;

  @UiField(provided = true)
  LabelProvider<String> abnormalStatusProvider;

  @UiField ToolButton update;

  @UiField ContentPanel svgContainer;

  private OMSVGDocument doc = OMSVGParser.currentDocument();

  private OMSVGSVGElement svg = doc.createSVGSVGElement();
  private Element div = null;

  private Timer timer;

  private StatusFilter filter = new StatusFilter();

  private String selectedDevice = new String();

  private List<String> deviceNames = new ArrayList<>();

  private Map<Integer, List<String>> underPDsMap = new HashMap<>();

  private static AbstractCell<String> statusCell =
      new AbstractCell<String>() {
        @Override
        public void render(
            com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
          sb.appendHtmlConstant(value);
        }
      };

  public PdStatusViewer() {
    roadLineStore = new ListStore<>(roadLineProertyAccess.lineId());
    roadLineProvider = roadLineProertyAccess.lineName();

    abnormalStatusStore =
        new ListStore<>(
            new ModelKeyProvider<String>() {
              @Override
              public String getKey(String item) {
                return item;
              }
            });
    abnormalStatusProvider =
        new LabelProvider<String>() {
          @Override
          public String getLabel(String item) {
            return item;
          }
        };

    listStore = new ListStore<PdStatusDTO>(propertyAccess.pdId());
    initColumnModel();

    initWidget(uiBinder.createAndBindUi(this));
    div = svgContainer.getElement();
    abnormalStatusStore.add("全部");
    abnormalStatusStore.add("欠相");
    abnormalStatusStore.add("斷電");
    abnormalStatusStore.add("分迴路異常");
    abnormalStatusStore.add("斷線");
    abnormalStatusCB.setValue(abnormalStatusStore.get(0));
    listStore.addFilter(filter);
    listStore.setEnableFilters(true);
    presenter.retrieveRoadLine();
    presenter.retrievePdStatus();
    startTimer();

    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
  }

  private void initColumnModel() {
    List<ColumnConfig<PdStatusDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<PdStatusDTO, ?>>();

    String onlineCheck =
        "<span style='color:black'>" + messages.pdStatus_status_online() + "</span>";

    ColumnConfig<PdStatusDTO, String> displayName =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                return dto.getPdDisplayName();
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "pdDisplayName";
              }
            },
            100,
            messages.column_pdStatus_displayName());
    displayName.setMenuDisabled(true);
    columnConfigs.add(displayName);

    ColumnConfig<PdStatusDTO, String> connectivity =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                return "<span style='color:"
                    + (dto.getConnectivity() == 0 ? "black" : "red")
                    + "'>"
                    + (dto.getConnectivity() == 0
                        ? messages.pdStatus_status_online()
                        : messages.pdStatus_status_offline())
                    + "</span>";
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "connectivity";
              }
            },
            100,
            messages.column_pdStatus_connectivity());
    connectivity.setMenuDisabled(true);
    connectivity.setCell(statusCell);
    columnConfigs.add(connectivity);

    ColumnConfig<PdStatusDTO, String> primaryR =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                  return "<span style='color:"
                      + (dto.getPrimaryR() == 0 ? "black" : "red")
                      + "'>"
                      + (dto.getPrimaryR() == 0
                          ? messages.pdStatus_status_normal()
                          : messages.pdStatus_status_abnormal())
                      + "</span>";
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "primaryR";
              }
            },
            35,
            "R");
    primaryR.setMenuDisabled(true);
    primaryR.setCell(statusCell);
    columnConfigs.add(primaryR);

    ColumnConfig<PdStatusDTO, String> primaryS =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                  return "<span style='color:"
                      + (dto.getPrimaryS() == 0 ? "black" : "red")
                      + "'>"
                      + (dto.getPrimaryS() == 0
                          ? messages.pdStatus_status_normal()
                          : messages.pdStatus_status_abnormal())
                      + "</span>";
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "primaryS";
              }
            },
            35,
            "S");
    primaryS.setMenuDisabled(true);
    primaryS.setCell(statusCell);
    columnConfigs.add(primaryS);

    ColumnConfig<PdStatusDTO, String> primaryT =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                  return "<span style='color:"
                      + (dto.getPrimaryT() == 0 ? "black" : "red")
                      + "'>"
                      + (dto.getPrimaryT() == 0
                          ? messages.pdStatus_status_normal()
                          : messages.pdStatus_status_abnormal())
                      + "</span>";
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "primaryT";
              }
            },
            35,
            "T");
    primaryT.setMenuDisabled(true);
    primaryT.setCell(statusCell);
    columnConfigs.add(primaryT);

    ColumnConfig<PdStatusDTO, String> secondaryR =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                  return "<span style='color:"
                      + (dto.getSecondaryR() == 0 ? "black" : "red")
                      + "'>"
                      + (dto.getSecondaryR() == 0
                          ? messages.pdStatus_status_normal()
                          : messages.pdStatus_status_abnormal())
                      + "</span>";
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "secondaryR";
              }
            },
            35,
            "R");
    secondaryR.setMenuDisabled(true);
    secondaryR.setCell(statusCell);
    columnConfigs.add(secondaryR);

    ColumnConfig<PdStatusDTO, String> secondaryS =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                  return "<span style='color:"
                      + (dto.getSecondaryS() == 0 ? "black" : "red")
                      + "'>"
                      + (dto.getSecondaryS() == 0
                          ? messages.pdStatus_status_normal()
                          : messages.pdStatus_status_abnormal())
                      + "</span>";
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "secondaryS";
              }
            },
            35,
            "S");
    secondaryS.setMenuDisabled(true);
    secondaryS.setCell(statusCell);
    columnConfigs.add(secondaryS);

    ColumnConfig<PdStatusDTO, String> secondaryT =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                  return "<span style='color:"
                      + (dto.getSecondaryT() == 0 ? "black" : "red")
                      + "'>"
                      + (dto.getSecondaryT() == 0
                          ? messages.pdStatus_status_normal()
                          : messages.pdStatus_status_abnormal())
                      + "</span>";
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "secondaryT";
              }
            },
            35,
            "T");
    secondaryT.setMenuDisabled(true);
    secondaryT.setCell(statusCell);
    columnConfigs.add(secondaryT);

    ColumnConfig<PdStatusDTO, String> loop1Status =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (dto.getLoop1Status() != null) {
                  if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                    return "<span style='color:"
                        + (dto.getLoop1Status() == 0 ? "black" : "red")
                        + "'>"
                        + (dto.getLoop1Status() == 0
                            ? messages.pdStatus_status_normal()
                            : messages.pdStatus_status_abnormal())
                        + "</span>";
                  } else {
                    return "<span style='color:black'>-</span>";
                  }
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "loop1Status";
              }
            },
            35,
            "1");
    loop1Status.setMenuDisabled(true);
    loop1Status.setCell(statusCell);
    columnConfigs.add(loop1Status);

    ColumnConfig<PdStatusDTO, String> loop2Status =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (dto.getLoop2Status() != null) {
                  if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                    return "<span style='color:"
                        + (dto.getLoop2Status() == 0 ? "black" : "red")
                        + "'>"
                        + (dto.getLoop2Status() == 0
                            ? messages.pdStatus_status_normal()
                            : messages.pdStatus_status_abnormal())
                        + "</span>";
                  } else {
                    return "<span style='color:black'>-</span>";
                  }
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "loop2Status";
              }
            },
            35,
            "2");
    loop2Status.setMenuDisabled(true);
    loop2Status.setCell(statusCell);
    columnConfigs.add(loop2Status);

    ColumnConfig<PdStatusDTO, String> loop3Status =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (dto.getLoop3Status() != null) {
                  if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                    return "<span style='color:"
                        + (dto.getLoop3Status() == 0 ? "black" : "red")
                        + "'>"
                        + (dto.getLoop3Status() == 0
                            ? messages.pdStatus_status_normal()
                            : messages.pdStatus_status_abnormal())
                        + "</span>";
                  } else {
                    return "<span style='color:black'>-</span>";
                  }
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "loop3Status";
              }
            },
            35,
            "3");
    loop3Status.setMenuDisabled(true);
    loop3Status.setCell(statusCell);
    columnConfigs.add(loop3Status);

    ColumnConfig<PdStatusDTO, String> loop4Status =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (dto.getLoop4Status() != null) {
                  if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                    return "<span style='color:"
                        + (dto.getLoop4Status() == 0 ? "black" : "red")
                        + "'>"
                        + (dto.getLoop4Status() == 0
                            ? messages.pdStatus_status_normal()
                            : messages.pdStatus_status_abnormal())
                        + "</span>";
                  } else {
                    return "<span style='color:black'>-</span>";
                  }
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "loop4Status";
              }
            },
            35,
            "4");
    loop4Status.setMenuDisabled(true);
    loop4Status.setCell(statusCell);
    columnConfigs.add(loop4Status);

    ColumnConfig<PdStatusDTO, String> loop5Status =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (dto.getLoop5Status() != null) {
                  if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                    return "<span style='color:"
                        + (dto.getLoop5Status() == 0 ? "black" : "red")
                        + "'>"
                        + (dto.getLoop5Status() == 0
                            ? messages.pdStatus_status_normal()
                            : messages.pdStatus_status_abnormal())
                        + "</span>";
                  } else {
                    return "<span style='color:black'>-</span>";
                  }
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "loop5Status";
              }
            },
            35,
            "5");
    loop5Status.setMenuDisabled(true);
    loop5Status.setCell(statusCell);
    columnConfigs.add(loop5Status);

    ColumnConfig<PdStatusDTO, String> doorOpen =
        new ColumnConfig<>(
            new ValueProvider<PdStatusDTO, String>() {
              @Override
              public String getValue(PdStatusDTO dto) {
                if (columnConfigs.get(1).getValueProvider().getValue(dto) == onlineCheck) {
                  return "<span style='color:"
                      + (dto.getDoorOpen() == 0 ? "black" : "red")
                      + "'>"
                      + (dto.getDoorOpen() == 0
                          ? messages.pdStatus_status_close()
                          : messages.pdStatus_status_open())
                      + "</span>";
                } else {
                  return "<span style='color:black'>-</span>";
                }
              }

              @Override
              public void setValue(PdStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "doorOpen";
              }
            },
            100,
            messages.column_pdStatus_doorOpen());
    doorOpen.setMenuDisabled(true);
    doorOpen.setCell(statusCell);
    columnConfigs.add(doorOpen);

    columnModel = new ColumnModel<>(columnConfigs);
    columnModel.addHeaderGroup(
        0, 2, new HeaderGroupConfig(messages.pdStatus_headerGroup_primary(), 1, 3));
    columnModel.addHeaderGroup(
        0, 5, new HeaderGroupConfig(messages.pdStatus_headerGroup_secondary(), 1, 3));
    columnModel.addHeaderGroup(
        0, 8, new HeaderGroupConfig(messages.pdStatus_headerGroup_loop(), 1, 5));
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    PdStatusDTO dto = grid.getSelectionModel().getSelectedItem();
    selectedDevice = dto.getPdId();
    deviceNames = new ArrayList<>();
    deviceNames.add(dto.getPdId());
    presenter.retrieveLoopDeviceConfig(deviceNames, dto);
  }

  @UiHandler("update")
  public void onUpdate(SelectEvent se) {
    presenter.retrievePdStatus();
  }

  @UiHandler("roadLineCB")
  public void onRoadLineCB(SelectionEvent<RoadLineDTO> se) {
    listStore.setEnableFilters(false);
    listStore.setEnableFilters(true);
  }

  @UiHandler("abnormalStatusCB")
  public void onAbnormalStatusCB(SelectionEvent<String> se) {
    listStore.setEnableFilters(false);
    listStore.setEnableFilters(true);
  }

  private void startTimer() {
    timer =
        new Timer() {
          @Override
          public void run() {
            presenter.retrievePdStatus();
            clearSVGComponent();
          }
        };
    timer.scheduleRepeating(60000);
    timer.run();
  }

  private class StatusFilter implements StoreFilter<PdStatusDTO> {

    @Override
    public boolean select(Store<PdStatusDTO> store, PdStatusDTO parent, PdStatusDTO item) {
      List<Boolean> checkList = checkAbnormal(item);
      if (!roadLineCB.getCurrentValue().getLineId().equals("all")) {
        if (!item.getLineId().equals(roadLineCB.getCurrentValue().getLineId())) {
          return false;
        }
      }

      if (!abnormalStatusCB.getCurrentValue().equals("全部")) {
        if (abnormalStatusCB.getCurrentValue().equals("斷線") && item.getConnectivity() == 0) {
          return false;
        }
        if (abnormalStatusCB.getCurrentValue().equals("欠相") && checkList.get(0) == false) {
          return false;
        }
        if (abnormalStatusCB.getCurrentValue().equals("斷電") && checkList.get(1) == false) {
          return false;
        }
        if (abnormalStatusCB.getCurrentValue().equals("分迴路異常") && checkList.get(2) == false) {
          return false;
        }
      }
      return true;
    }
  }

  private List<Boolean> checkAbnormal(PdStatusDTO dto) {
    List<Boolean> list = new ArrayList<>();
    Boolean primaryStatus = false;
    Boolean secondaryStatus = false;
    Boolean loopStatus = false;
    if (dto.getConnectivity() == 0) {
      if ((dto.getPrimaryR() == 1
              || dto.getPrimaryS() == 1
              || dto.getPrimaryT() == 1
              || dto.getSecondaryR() == 1
              || dto.getSecondaryS() == 1
              || dto.getSecondaryT() == 1)
          && (!(dto.getPrimaryR() == 1 && dto.getPrimaryR() == 1 && dto.getPrimaryR() == 1))
          && (!(dto.getSecondaryR() == 1
              && dto.getSecondaryS() == 1
              && dto.getSecondaryT() == 1))) {
        primaryStatus = true;
      }
      if ((dto.getPrimaryR() == 1 && dto.getPrimaryR() == 1 && dto.getPrimaryR() == 1)
          || (dto.getSecondaryR() == 1 && dto.getSecondaryS() == 1 && dto.getSecondaryT() == 1)) {
        secondaryStatus = true;
      }
      if (dto.getLoop1Status() != null && dto.getLoop1Status() == 1
          || dto.getLoop2Status() != null && dto.getLoop2Status() == 1
          || dto.getLoop3Status() != null && dto.getLoop3Status() == 1
          || dto.getLoop4Status() != null && dto.getLoop4Status() == 1
          || dto.getLoop5Status() != null && dto.getLoop5Status() == 1) {
        loopStatus = true;
      }
    }
    list.add(primaryStatus);
    list.add(secondaryStatus);
    list.add(loopStatus);
    return list;
  }

  public void fillRoadLine(List<RoadLineDTO> list) {
    roadLineStore.clear();
    RoadLineDTO allDto = new RoadLineDTO();
    allDto.setLineId("all");
    allDto.setLineName(messages.pdStatus_comboBox_all());
    list.add(0, allDto);
    roadLineStore.addAll(list);
    roadLineCB.setValue(roadLineStore.get(0));
  }

  public void fillPdStatus(List<PdStatusDTO> list) {
    listStore.clear();
    listStore.addAll(list);
    if (!selectedDevice.equals("")) {
      grid.getSelectionModel().select(true, listStore.findModelWithKey(selectedDevice));
      fillSvg(grid.getSelectionModel().getSelectedItem());
    }
  }

  public void fillSvg(PdStatusDTO dto) {
    // 先清空所有元件
    clearSVGComponent();

    SVGResource resource = com.hwacom.ngtms.pd.am.images.AllImages.INSTANCE.pd();
    if (resource == null) {
      GWT.log("Fill svg failed, can not get resource.");
      return;
    }
    OMSVGSVGElement svgElement = resource.getSvg();
    // 將 viewbox 設為 svg 底圖大小
    svg.setViewBox(
        0f,
        0f,
        svgElement.getWidth().getBaseVal().getValue(),
        svgElement.getHeight().getBaseVal().getValue());

    Boolean check = dto.getConnectivity() == 1 ? true : false;
    OMElement primaryRElement = svgElement.getElementById("primaryR");
    primaryRElement.setAttribute(
        "style", setSvgColor(primaryRElement.getAttribute("style"), check, dto.getPrimaryR()));

    OMElement primarySElement = svgElement.getElementById("primaryS");
    primarySElement.setAttribute(
        "style", setSvgColor(primarySElement.getAttribute("style"), check, dto.getPrimaryS()));

    OMElement primaryTElement = svgElement.getElementById("primaryT");
    primaryTElement.setAttribute(
        "style", setSvgColor(primaryTElement.getAttribute("style"), check, dto.getPrimaryT()));

    OMElement secondaryRElement = svgElement.getElementById("secondaryR");
    secondaryRElement.setAttribute(
        "style", setSvgColor(secondaryRElement.getAttribute("style"), check, dto.getSecondaryR()));

    OMElement secondarySElement = svgElement.getElementById("secondaryS");
    secondarySElement.setAttribute(
        "style", setSvgColor(secondarySElement.getAttribute("style"), check, dto.getSecondaryS()));

    OMElement secondaryTElement = svgElement.getElementById("secondaryT");
    secondaryTElement.setAttribute(
        "style", setSvgColor(secondaryTElement.getAttribute("style"), check, dto.getSecondaryT()));

    OMElement loop1Element = svgElement.getElementById("loop1Status");
    if (dto.getLoop1Status() != null) {
      loop1Element.setAttribute(
          "style", setSvgColor(loop1Element.getAttribute("style"), check, dto.getLoop1Status()));

      if (underPDsMap.containsKey(1) && underPDsMap.get(1).size() > 0) {
        loop1Element.getElement().setInnerText(String.join("<br />", underPDsMap.get(1)));
      }
    } else {
      List<String> styleList = Arrays.asList(loop1Element.getAttribute("style").split(";"));
      styleList.set(1, "fill:#666666");
      loop1Element.setAttribute("style", String.join(";", styleList));

      OMElement loop1DevcieElement = svgElement.getElementById("loop1");
      List<String> devcieStyleList =
          Arrays.asList(loop1DevcieElement.getAttribute("style").split(";"));
      devcieStyleList.set(1, "fill:#666666");
      devcieStyleList.set(2, "fill-opacity:0.8");
      loop1DevcieElement.setAttribute("style", String.join(";", devcieStyleList));
    }

    OMElement loop2Element = svgElement.getElementById("loop2Status");
    if (dto.getLoop2Status() != null) {
      loop2Element.setAttribute(
          "style", setSvgColor(loop2Element.getAttribute("style"), check, dto.getLoop2Status()));

      if (underPDsMap.containsKey(2) && underPDsMap.get(2).size() > 0) {
        loop2Element.getElement().setInnerText(String.join("<br />", underPDsMap.get(2)));
      }
    } else {
      List<String> styleList = Arrays.asList(loop2Element.getAttribute("style").split(";"));
      styleList.set(1, "fill:#666666");
      loop2Element.setAttribute("style", String.join(";", styleList));

      OMElement loop2DevcieElement = svgElement.getElementById("loop2");
      List<String> devcieStyleList =
          Arrays.asList(loop2DevcieElement.getAttribute("style").split(";"));
      devcieStyleList.set(1, "fill:#666666");
      devcieStyleList.set(2, "fill-opacity:0.8");
      loop2DevcieElement.setAttribute("style", String.join(";", devcieStyleList));
    }

    OMElement loop3Element = svgElement.getElementById("loop3Status");
    if (dto.getLoop3Status() != null) {
      loop3Element.setAttribute(
          "style", setSvgColor(loop3Element.getAttribute("style"), check, dto.getLoop3Status()));

      if (underPDsMap.containsKey(3) && underPDsMap.get(3).size() > 0) {
        loop3Element.getElement().setInnerText(String.join("<br />", underPDsMap.get(3)));
      }
    } else {
      List<String> styleList = Arrays.asList(loop3Element.getAttribute("style").split(";"));
      styleList.set(1, "fill:#666666");
      loop3Element.setAttribute("style", String.join(";", styleList));

      OMElement loop3DevcieElement = svgElement.getElementById("loop3");
      List<String> devcieStyleList =
          Arrays.asList(loop3DevcieElement.getAttribute("style").split(";"));
      devcieStyleList.set(1, "fill:#666666");
      devcieStyleList.set(2, "fill-opacity:0.8");
      loop3DevcieElement.setAttribute("style", String.join(";", devcieStyleList));
    }

    OMElement loop4Element = svgElement.getElementById("loop4Status");
    if (dto.getLoop4Status() != null) {
      loop4Element.setAttribute(
          "style", setSvgColor(loop4Element.getAttribute("style"), check, dto.getLoop4Status()));

      if (underPDsMap.containsKey(4) && underPDsMap.get(4).size() > 0) {
        loop4Element.getElement().setInnerText(String.join("<br />", underPDsMap.get(4)));
      }
    } else {
      List<String> styleList = Arrays.asList(loop4Element.getAttribute("style").split(";"));
      styleList.set(1, "fill:#666666");
      loop4Element.setAttribute("style", String.join(";", styleList));

      OMElement loop4DevcieElement = svgElement.getElementById("loop4");
      List<String> devcieStyleList =
          Arrays.asList(loop4DevcieElement.getAttribute("style").split(";"));
      devcieStyleList.set(1, "fill:#666666");
      devcieStyleList.set(2, "fill-opacity:0.8");
      loop4DevcieElement.setAttribute("style", String.join(";", devcieStyleList));
    }

    OMElement loop5Element = svgElement.getElementById("loop5Status");
    if (dto.getLoop5Status() != null) {
      loop5Element.setAttribute(
          "style", setSvgColor(loop5Element.getAttribute("style"), check, dto.getLoop5Status()));

      if (underPDsMap.containsKey(5) && underPDsMap.get(5).size() > 0) {
        loop5Element.getElement().setInnerText(String.join("<br />", underPDsMap.get(5)));
      }
    } else {
      List<String> styleList = Arrays.asList(loop5Element.getAttribute("style").split(";"));
      styleList.set(1, "fill:#666666");
      loop5Element.setAttribute("style", String.join(";", styleList));

      OMElement loop5DevcieElement = svgElement.getElementById("loop5");
      List<String> devcieStyleList =
          Arrays.asList(loop5DevcieElement.getAttribute("style").split(";"));
      devcieStyleList.set(1, "fill:#666666");
      devcieStyleList.set(2, "fill-opacity:0.8");
      loop5DevcieElement.setAttribute("style", String.join(";", devcieStyleList));
    }

    svg.appendChild(svgElement);
    div.appendChild(svg.getElement());
  }

  public String setSvgColor(String style, Boolean check, Integer status) {
    List<String> styleList = Arrays.asList(style.split(";"));
    ;
    if (check == true) {
      styleList.set(1, "fill:#666666");
    } else {
      if (status.equals(0)) {
        styleList.set(1, "fill:#00ff00");
      } else {
        styleList.set(1, "fill:#ff0000");
      }
    }
    return String.join(";", styleList);
  }

  private void clearSVGComponent() {
    div.removeAllChildren();
    svg = doc.createSVGSVGElement();
  }

  public void fillLoopDevice(List<LoopDeviceConfigDTO> result, PdStatusDTO dto) {
    //將資料分成五個資料
    underPDsMap = new HashMap<>();
    for (LoopDeviceConfigDTO loop : result) {
      //先判斷第一層 底下是不是有資料
      if (!loop.getCheck()) {
        List<String> deviceL1 = new ArrayList<>();
        List<String> deviceL2 = new ArrayList<>();
        List<String> deviceL3 = new ArrayList<>();
        List<String> deviceL4 = new ArrayList<>();
        List<String> deviceL5 = new ArrayList<>();
        for (LoopDeviceConfigDTO loopC1 : loop.getChildren()) {
          //再判斷第二層 底下是不是有資料
          if (!loopC1.getCheck()) {
            for (LoopDeviceConfigDTO loopC2 : loopC1.getChildren()) {
              if (loopC2.getCheck()) {
                if (loopC2.getLoopNo() == 1) {
                  deviceL1.add(loopC2.getDisplayName());
                } else if (loopC2.getLoopNo() == 2) {
                  deviceL2.add(loopC2.getDisplayName());
                } else if (loopC2.getLoopNo() == 3) {
                  deviceL3.add(loopC2.getDisplayName());
                } else if (loopC2.getLoopNo() == 4) {
                  deviceL4.add(loopC2.getDisplayName());
                } else if (loopC2.getLoopNo() == 5) {
                  deviceL5.add(loopC2.getDisplayName());
                }
              }
            }
          }
        }
        underPDsMap.put(1, deviceL1);
        underPDsMap.put(2, deviceL2);
        underPDsMap.put(3, deviceL3);
        underPDsMap.put(4, deviceL4);
        underPDsMap.put(5, deviceL5);
      }
    }
    fillSvg(dto);
  }

  interface RoadLineProertyAccess extends PropertyAccess<RoadLineDTO> {
    ModelKeyProvider<RoadLineDTO> lineId();

    LabelProvider<RoadLineDTO> lineName();
  }

  interface PdStatusPropertyAccess extends PropertyAccess<PdStatusDTO> {
    ModelKeyProvider<PdStatusDTO> pdId();

    ValueProvider<PdStatusDTO, String> pdDisplayName();

    ValueProvider<PdStatusDTO, Integer> primaryR();

    ValueProvider<PdStatusDTO, Integer> primaryS();

    ValueProvider<PdStatusDTO, Integer> primaryT();

    ValueProvider<PdStatusDTO, Integer> secondaryR();

    ValueProvider<PdStatusDTO, Integer> secondaryS();

    ValueProvider<PdStatusDTO, Integer> secondaryT();

    ValueProvider<PdStatusDTO, Integer> connectivity();

    ValueProvider<PdStatusDTO, Integer> doorOpen();

    ValueProvider<PdStatusDTO, Integer> loop1Status();

    ValueProvider<PdStatusDTO, Integer> loop2Status();

    ValueProvider<PdStatusDTO, Integer> loop3Status();

    ValueProvider<PdStatusDTO, Integer> loop4Status();

    ValueProvider<PdStatusDTO, Integer> loop5Status();
  }
}
