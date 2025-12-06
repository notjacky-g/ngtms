/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.road;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeSelectEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeSelectEvent.RoadConfigTreeSelectEventHandler;
import com.hwacom.ngtms.cam.presenter.road.RoadConfigPresenter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.RoadLineProperties;
import com.hwacom.ngtms.cam.vo.RoadType;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckCascade;
import java.util.List;

public class RoadConfigViewer extends Composite {

  private static RoadConfigViewerUiBinder uiBinder = GWT.create(RoadConfigViewerUiBinder.class);

  interface RoadConfigViewerUiBinder extends UiBinder<Widget, RoadConfigViewer> {}

  private static final Messages messages = GWT.create(Messages.class);
  private List<RoadLineDTO> roadLines;
  private List<RoadDivisionDTO> roadDivisions;
  private RoadLineDTO rootNodes;
  private static final RoadLineProperties roadLineProps = GWT.create(RoadLineProperties.class);
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private final HandlerRegistration roadConfigTreeSelectEventHandlerRegistration;
  private RoadConfigPresenter presenter;

  @UiField(provided = true)
  SimpleComboBox<RoadType> roadTypeCombo =
      new SimpleComboBox<RoadType>(
          new LabelProvider<RoadType>() {
            @Override
            public String getLabel(RoadType item) {
              return RoadType.getName(item);
            }
          });

  @UiField(provided = true)
  TreeStore<RoadLineDTO> treeStore = new TreeStore<RoadLineDTO>(new KeyProvider());

  @UiField Tree<RoadLineDTO, String> tree;

  @UiFactory
  public ValueProvider<RoadLineDTO, String> createValueProvider() {
    return new ValueProvider<RoadLineDTO, String>() {
      @Override
      public String getValue(RoadLineDTO object) {
        return object.getLineName();
      }

      @Override
      public void setValue(RoadLineDTO object, String value) {}

      @Override
      public String getPath() {
        return "lineName";
      }
    };
  }

  @UiField CardLayoutContainer cardlayout;
  @UiField RoadLineConfigViewer roadLineConfigViewer;
  @UiField ServiceAreaViewer serviceAreaViewer;
  @UiField InterchangeViewer interchangeViewer;
  @UiField SystemInterchangeViewer systemInterchangeViewer;

  public RoadConfigViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    initComboBox();
    initTree();
    presenter = new RoadConfigPresenter(this);
    roadConfigTreeSelectEventHandlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(RoadConfigTreeSelectEvent.TYPE, new RoadConfigTreeSelectEventHandlerImpl());
  }

  private void initTree() {
    treeStore.addSortInfo(new StoreSortInfo<RoadLineDTO>(roadLineProps.lineId(), SortDir.ASC));
    tree.setCheckStyle(CheckCascade.CHILDREN);
    tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tree.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<RoadLineDTO>() {
              @Override
              public void onSelection(SelectionEvent<RoadLineDTO> event) {
                RoadLineDTO node = event.getSelectedItem();
                GWT.log("node =" + node);
                clientFactory
                    .getEventBus()
                    .fireEventFromSource(
                        new RoadConfigTreeFireSelectedEvent(
                            RoadConfigTreeFireSelectedEvent.Action.SELECT_ONE_ITEM),
                        node);
              }
            });
  }

  private void initComboBox() {
    GWT.log("init comboBox");
    for (RoadType roadType : RoadType.values()) {
      roadTypeCombo.add(roadType);
    }
    roadTypeCombo.setValue(RoadType.L);
    roadTypeCombo.addSelectionHandler(
        new SelectionHandler<RoadType>() {

          @Override
          public void onSelection(SelectionEvent<RoadType> event) {
            RoadType roadType = event.getSelectedItem();
            GWT.log("roadType =" + roadType);
            switch (roadType) {
              case C:
                cardlayout.setActiveWidget(cardlayout.getWidget(3));
                break;
              case I:
                cardlayout.setActiveWidget(cardlayout.getWidget(2));
                break;
              case L:
                cardlayout.setActiveWidget(cardlayout.getWidget(0));
                break;
              case S:
                cardlayout.setActiveWidget(cardlayout.getWidget(1));
                break;
                // case T:
                // break;
              default:
                break;
            }
          }
        });
  }

  public void selectItem(RoadLineDTO roadLineDto) {
    tree.getSelectionModel().select(roadLineDto, false);
  }

  protected void onUnload() {
    roadConfigTreeSelectEventHandlerRegistration.removeHandler();
    super.onUnload();
  }

  public void fillRoadLines(List<RoadLineDTO> result) {
    if (result == null) return;
    this.roadLines = result;
    treeStore.clear();
    rootNodes = new RoadLineDTO();
    rootNodes.setLineId("root");
    rootNodes.setLineName(messages.tree_root());
    treeStore.add(rootNodes);
    for (RoadLineDTO roadLine : result) {
      treeStore.add(rootNodes, roadLine);
    }
    tree.expandAll();

    roadLineConfigViewer.initGridData(roadLines);
    serviceAreaViewer.initRoadLineData(roadLines);
    interchangeViewer.initRoadLineData(roadLines);
    systemInterchangeViewer.initRoadLineData(roadLines);
  }

  public void fillRoadDivisions(List<RoadDivisionDTO> result) {
    this.roadDivisions = result;
    serviceAreaViewer.initRoadDivisionData(result);
    interchangeViewer.initRoadDivisionData(result);
    systemInterchangeViewer.initRoadDivisionData(result);
  }

  class KeyProvider implements ModelKeyProvider<RoadLineDTO> {
    @Override
    public String getKey(RoadLineDTO item) {
      return item.getLineId();
    }
  }

  class RoadConfigTreeSelectEventHandlerImpl implements RoadConfigTreeSelectEventHandler {
    @Override
    public void selectOneItem(RoadConfigTreeSelectEvent event) {
      GWT.log("DeviceConfigViewer RoadConfigTreeSelectEventHandlerImpl selectOneItem start...");
      RoadLineDTO dto = (RoadLineDTO) event.getSource();
      selectItem(dto);
    }
  }
}
