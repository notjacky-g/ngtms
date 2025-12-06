/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.DeviceStatusType;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.DragItem;
import com.hwacom.ngtms.c.shared.TcProtocolType;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceGroupDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceGroupDeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.c.shared.dto.EmsDeviceStatusDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DragItemDeselectEvent;
import com.hwacom.ngtms.cam.client.event.GroupOperationEvent;
import com.hwacom.ngtms.cam.client.event.GroupOperationEvent.GroupOperationType;
import com.hwacom.ngtms.cam.client.event.GroupOperationHandler;
import com.hwacom.ngtms.cam.client.event.RoadTreeSelectEvent;
import com.hwacom.ngtms.cam.client.event.RoadTreeViewerEvent;
import com.hwacom.ngtms.cam.client.event.RoadTreeViewerEvent.Action;
import com.hwacom.ngtms.cam.client.event.TitleViewMaskEvent;
import com.hwacom.ngtms.cam.client.event.TitleViewMaskEvent.TitleViewMaskEventHandler;
import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.factory.RoadTreeFactory;
import com.hwacom.ngtms.cam.factory.RoadTreeFactoryImpl;
import com.hwacom.ngtms.cam.images.AmImages;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.hwacom.ngtms.cam.util.DataCenter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.IdNameNode;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DND.TreeSource;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent.DndDragStartHandler;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.DragSource;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.dnd.core.client.TreeDragSource;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeAppearance;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * 第一層是「中交控系統」、第二層是 {@link RoadLineDTO}、 第三層是 {東, 西, 南, 北} 向（沒有實際對應的 vo）、 第四層是 {@link
 * RoadSectionDTO}、第五層是 {@link DeviceConfigDTO}。
 *
 * <p>第四層跟第五層的呈現邏輯與 {@link #setDeviceType(DeviceTypeDTO)} 或 {@link #setDeviceTypeList(List)} 相關：
 *
 * <ul>
 *   <li>傳入 null：不會出現第五層。第四層會顯示全部的 {@link RoadSectionDTO}
 *   <li>傳入一個或多個 {@link DeviceTypeDTO}：顯示第五層。第四層（以上）只會出現有 device 的節點。
 * </ul>
 *
 * <p>觸發事件：
 *
 * <ul>
 *   <li>{@link RoadTreeSelectEvent}：使用者點選 tree node 時產生
 * </ul>
 *
 * <p>無論是身為 {@link DragSource}（DnD Group = {@link AddType#DeviceConfig}） 或是 {@link DropTarget}（DnD
 * group = {@link RemoveDeviceUtil#GROUP} ）， RoadTreeView 只處理 {@link DeviceConfigDTO} 的資料。
 *
 * <p>
 * <li>變換icon請呼叫{@link #setIconProvider(IconProvider)} 目前icon尚未全部載入am-common,
 *     所以請由各系統自行實作ImageResource
 */
public class RoadTreeViewer extends Composite {
  private static RoadTreeViewUiBinder uiBinder = GWT.create(RoadTreeViewUiBinder.class);

  private static Logger logger = Logger.getLogger("RoadTreeView");

  interface RoadTreeViewUiBinder extends UiBinder<Widget, RoadTreeViewer> {}

  private RoadTreeFactory factory = GWT.create(RoadTreeFactoryImpl.class);
  private TreeAppearance treeAppearance = GWT.create(TreeAppearance.class);
  private Messages messages = GWT.create(Messages.class);

  private static final String RLS = "RLS";

  private static final String TDS = "TDS";

  @UiField(provided = true)
  TreeStore<IdNameNode> store;

  @UiField(provided = true)
  ValueProvider<IdNameNode, String> valueProvider;

  @UiField Tree<IdNameNode, String> tree;
  @UiField RoadTreeSelectedViewer selectedView;
  @UiField BorderLayoutContainer borderLayoutContainer;
  @UiField VerticalLayoutData toolBarRowData;
  @UiField ToolBar toolBar;
  @UiField Image expandAllImage;
  @UiField Image collapseAllImage;

  /** 沒有 element 表示不顯示第五層。 */
  private List<String> acceptDevice = new ArrayList<>();

  private IdNameNode rootNode = new IdNameNode("root", messages.tree_root());
  private IdNameNode groupRoot = new IdNameNode("group", messages.tree_group());
  private TreeDragSource<IdNameNode> dragSource;
  private Map<String, DeviceConfigDTO> deviceMap = new HashMap<>();
  // 儲存設備EmsDeviceStatusDTO的Map
  private Map<String, EmsDeviceStatusDTO> statusMap = new HashMap<>();
  private Map<String, IdNameNode> nodeMap = new HashMap<>();

  private Timer repeatFetechDataTimer = null;
  private TcProtocolType tcProtocolTypeValue = null;

  // private boolean statusRefresh = true;
  private boolean autoGeneration = true;
  private boolean removeRoot = false;
  private boolean stopRepeat = false;
  private boolean roadSectionLevel = true;

  // For GWT log
  private boolean toShowGwtLog = false;
  private String monitorDeviceName = "CCTV-N3-S-112.203-M";
  /* 使用者自行新增 node 時 (使用 insertNodeUnderRoot), 記錄底下的 node, 並在 traversal 部檢查該 node */
  private Map<String, IdNameNode> userAddNodeMap = new HashMap<String, IdNameNode>();

  public RoadTreeViewer() {
    IdNameProperties props = GWT.create(IdNameProperties.class);
    store = new TreeStore<>(props.id());
    store.addSortInfo(new TreeSorter());

    valueProvider =
        new ValueProvider<IdNameNode, String>() {
          @Override
          public String getValue(IdNameNode item) {
            String color =
                item.getStatus() == DeviceStatusType.NONE
                    ? "black"
                    : item.getStatus() == DeviceStatusType.SUSPEND
                        ? "gray"
                        : (item.getStatus() == DeviceStatusType.ONLINE) ? "black" : "red";
            return (color.equals("gray"))
                ? "<strike><span style='color: "
                    + color
                    + "'>"
                    + item.getName()
                    + "</span></strike>"
                : "<span style='color: " + color + "'>" + item.getName() + "</span>";
          }

          @Override
          public void setValue(IdNameNode object, String value) {}

          @Override
          public String getPath() {
            return "roadTreeProvider";
          }
        };
    initWidget(uiBinder.createAndBindUi(this));

    selectedView.setDndType(factory.isDragDeviceConfig() ? AddType.DeviceConfig : AddType.Other);

    // TODO 依照不同的 deviceTypeDTO 給不同的 icon（IconProvider？）
    tree.getStyle().setLeafIcon(AmImages.INSTANCE.icCar());
    // 透過Abstractcell去轉換顯示顏色
    tree.setCell(new SimpleAbstractCell());
    // 設定 tree node 被點選時候要炸哪些事情
    tree.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<IdNameNode>() {
              @Override
              public void onSelection(SelectionEvent<IdNameNode> event) {
                // 不明白為什麼第一次點的時候會炸兩次 onSelection Orz
                GWT.log("tree SelectionHandler ON selection.");
                IdNameNode node = event.getSelectedItem();
                AmEventCenter.fireEvent(
                    new RoadTreeSelectEvent(
                        SelectType.byDepth(store.getDepth(node)), node.getId()));
              }
            });

    buildDropTarget();

    AmEventCenter.addGroupOperationHandler(
        new GroupOperationHandler() {
          @Override
          public void onGroupOperation(GroupOperationEvent event) {
            if (event.getType() == GroupOperationType.UPDATE) {
              IdNameNode groupNode = null;
              for (IdNameNode node : tree.getStore().getChildren(groupRoot)) {
                if (node.getName().equals(event.getDeviceGroup().getGroupName())) {
                  groupNode = node;
                }
              }
              if (groupNode == null) {
                IdNameNode dgNode =
                    new IdNameNode(
                        event.getDeviceGroup().getGroupId(), event.getDeviceGroup().getGroupName());
                store.add(groupRoot, dgNode);
                ArrayList<IdNameNode> dgdcNodeList = new ArrayList<>();
                for (DeviceGroupDeviceConfigDTO dgdc : event.getDeviceGroup().getDevices()) {
                  String displayName;
                  if (null == factory) {
                    displayName = DataCenter.getDisplayName(dgdc.getDeviceName());
                  } else {
                    displayName =
                        factory.getDisplayName(DataCenter.getDeviceConfig(dgdc.getDeviceName()));
                  }
                  IdNameNode dgdcNode =
                      new IdNameNode(encodeDeviceConfigKey(dgdc.getDeviceName()), displayName);
                  dgdcNodeList.add(dgdcNode);
                }
                if (!dgdcNodeList.isEmpty()) {
                  store.add(dgNode, dgdcNodeList);
                }
              } else {
                for (IdNameNode node : tree.getStore().getChildren(groupNode)) {
                  store.remove(node);
                }
                for (DeviceGroupDeviceConfigDTO dgdc : event.getDeviceGroup().getDevices()) {
                  String displayName;
                  if (null == factory) {
                    displayName = DataCenter.getDisplayName(dgdc.getDeviceName());
                  } else {
                    displayName =
                        factory.getDisplayName(DataCenter.getDeviceConfig(dgdc.getDeviceName()));
                  }
                  IdNameNode dgdcNode =
                      new IdNameNode(encodeDeviceConfigKey(dgdc.getDeviceName()), displayName);
                  store.add(groupNode, dgdcNode);
                }
              }
            } else if (event.getType() == GroupOperationType.DELETE) {
              for (IdNameNode node : tree.getStore().getChildren(groupRoot)) {
                if (node.getName().equals(event.getDeviceGroup().getGroupName())) {
                  store.remove(node);
                }
              }
            }
            //tree.refresh(groupRoot);
          }
        });
    expandAllImage.getElement().getStyle().setCursor(Cursor.POINTER);
    collapseAllImage.getElement().getStyle().setCursor(Cursor.POINTER);
    AmEventCenter.addTitleViewMaskEventHandler(
        new TitleViewMaskEventHandler() {

          @Override
          public void onMaskFired(TitleViewMaskEvent event) {
            GWT.log("RoadTreeView Get TitleViewMaskEvent!");
            stopRepeatFetechDataTimer();
          }
        });
  }

  /**
   * 設定樹狀結構是否自動產生(預設使用{@link DeviceConfigDTO})
   *
   * @param autoGeneration 預設true=是
   */
  public void setAutoGeneration(boolean autoGeneration) {
    this.autoGeneration = autoGeneration;
    if (dragSource != null) {
      dragSource.release();
      dragSource = null;
    }
    buildDragSource();

    store.clear();
    if (toShowGwtLog) {
      GWT.log("setAutoGeneration removeRoot:" + removeRoot);
    }
    if (!removeRoot) {
      store.add(rootNode);
    }

    if (autoGeneration) {
      if (toShowGwtLog) {
        GWT.log("setAutoGeneration Run deviceTypeSetting...");
      }
      deviceTypeSetting();
    } else {
      Scheduler.get()
          .scheduleFixedDelay(
              new RepeatingCommand() {
                @Override
                public boolean execute() {
                  if (factory.isFactoryReady()) {
                    genCustomTree();
                    return false;
                  } else {
                    return true;
                  }
                }
              },
              500); // 0.5 秒檢查一次
    }
  }

  /**
   * 決定第五層要顯示哪個 {@link String} 的 device。
   *
   * @param String 傳入 null 就不會顯示第五層
   */
  public void setDeviceType(String deviceType) {
    // 設定相同的 deviceType 就不繼續做下去
    if (acceptDevice.size() == 1 && acceptDevice.contains(deviceType)) {
      return;
    }

    acceptDevice.clear();
    deviceMap.clear();

    if (deviceType != null) {
      acceptDevice.add(deviceType);
    }
    if (toShowGwtLog) GWT.log("setDeviceType Run setAutoGeneration...");
    if (acceptDevice.size() != 0) {
      DataCenter.setDeviceType(acceptDevice);
    }
    setAutoGeneration(autoGeneration);
  }

  /**
   * 決定第五層要顯示哪個 {@link String} 的 device。
   *
   * @param String 傳入 null 就不會顯示第五層
   */
  public void setDeviceTypeListAndTcProtocolType(
      List<String> deviceTypeList, TcProtocolType tcProtocolType) {
    acceptDevice.clear();
    deviceMap.clear();

    if (tcProtocolType != null) {
      tcProtocolTypeValue = tcProtocolType;
    } else {
      tcProtocolTypeValue = null;
    }

    // 懶得比對，一律重作 XD
    if (deviceTypeList != null) {
      acceptDevice.addAll(deviceTypeList);
    }

    if (acceptDevice.size() != 0) {
      DataCenter.setDeviceType(acceptDevice);
    }
    setAutoGeneration(autoGeneration);
  }

  public void resetDeviceType(String deviceType) {
    if (toShowGwtLog) GWT.log("resetDeviceType deviceType:" + deviceType);
    acceptDevice.clear();
    deviceMap.clear();
    if (deviceType != null) {
      acceptDevice.add(deviceType);
    }
    if (toShowGwtLog) GWT.log("resetDeviceType Run setAutoGeneration...");
    if (acceptDevice.size() != 0) {
      DataCenter.setDeviceType(acceptDevice);
    }
    setAutoGeneration(autoGeneration);
  }

  /**
   * 決定第五層要顯示哪些 {@link DeviceTypeDTO} 的 device。
   *
   * @param deviceTypeList 傳入 null 就不會顯示第五層
   */
  public void setDeviceTypeList(List<String> deviceTypeList) {
    acceptDevice.clear();
    deviceMap.clear();

    // 懶得比對，一律重作 XD
    if (deviceTypeList != null) {
      acceptDevice.addAll(deviceTypeList);
    }

    if (acceptDevice.size() != 0) {
      DataCenter.setDeviceType(acceptDevice);
    }
    setAutoGeneration(autoGeneration);
  }

  /**
   * 設定已選取設備區內的指定設備
   *
   * @param index 單一設備在已選取設備區內的次序
   */
  public void setGridSelection(int index) {
    selectedView.setGridSelection(index);
  }
  //	/**
  //     * 設定是否自動更新設備連線狀態
  //     * @param statusRefresh 預設true=更新
  //     */
  //	public void setStatusRefresh(Boolean statusRefresh) {
  //	    this.statusRefresh = statusRefresh;

  //	}

  /**
   * 設定樹狀選擇模式(單選/複選)
   *
   * @param selectionMode 預設true=複選
   */
  public void setSelectionMode(Boolean selectionMode) {
    tree.getSelectionModel()
        .setSelectionMode(selectionMode ? SelectionMode.MULTI : SelectionMode.SINGLE);
    selectedView.setSelectionMode(selectionMode);
  }

  /**
   * 設定已選取設備區是否顯示
   *
   * @param visible 預設true=是
   */
  public void setSelectedVisible(boolean visible) {
    selectedView.setVisible(visible);
  }

  private void deviceTypeSetting() {
    if (toShowGwtLog) {
      GWT.log("deviceTypeSetting start ...");
    }

    this.mask(messages.message_fecthData());

    // 用 pooling 的方式檢查各項資料到底做完 RPC 了沒
    Scheduler.get()
        .scheduleFixedDelay(
            new RepeatingCommand() {
              @Override
              public boolean execute() {
                if (toShowGwtLog) GWT.log("deviceTypeSetting 請稍後，資料準備中..");
                mask("請稍後，資料準備中..");
                if (DataCenter.getRoadLineList() != null
                    && DataCenter.getRoadSectionList() != null) {
                  if (acceptDevice.size() != 0) {
                    if (toShowGwtLog) GWT.log("deviceTypeSetting acceptDevice.size() != 0");
                    if (DataCenter.getDeviceGroupList() != null) {
                      if (toShowGwtLog) {
                        GWT.log("DataCenter.getDeviceGroupList() != null");
                      }
                      //unmask();
                      // 改用getEmsDeviceStatus來接管設備樹產生
                      if (factory.isFactoryReady()) {
                        if (toShowGwtLog) {
                          GWT.log(
                              "deviceTypeSetting 01 actory.isFactoryReady()="
                                  + factory.isFactoryReady());
                        }
                        getDeviceConfigMap();
                        return false;
                      } else {
                        if (toShowGwtLog) {
                          GWT.log(
                              "deviceTypeSetting 02 actory.isFactoryReady()="
                                  + factory.isFactoryReady());
                        }
                        return true;
                      }
                    } else {
                      if (toShowGwtLog) {
                        GWT.log("deviceTypeSetting DataCenter.getDeviceGroupList() == null");
                      }
                      return true;
                    }
                  } else {
                    // 沒有指定 acceptDevice 就不用等 DeviceConfig 資料
                    //unmask();
                    buildTree();
                    return false;
                  }
                }
                return true;
              }
            },
            500); // 0.5 秒檢查一次
  }

  private void genCustomTree() {
    store.clear();
    if (toShowGwtLog) {
      GWT.log("genCustomTree removeRoot:" + removeRoot);
    }

    if (!removeRoot) {
      store.add(rootNode);
    }

    if (factory.getTreeSource() == null) {
      return;
    }

    for (IdNameNode node : factory.getTreeSource()) {
      if (!removeRoot) {
        store.add(rootNode, node);
      } else {
        store.add(node);
      }
      checkCustomChildren(node);
    }
  }

  private void checkCustomChildren(IdNameNode node) {
    if (node.getChildren() != null && !node.getChildren().isEmpty()) {
      for (IdNameNode child : node.getChildren()) {
        store.add(node, child);
        checkCustomChildren(child);
      }
    }
  }

  private void buildDropTarget() {
    DropTarget dropTarget = new DropTarget(this);
    dropTarget.setGroup(RemoveDeviceUtil.GROUP);
    dropTarget.setOperation(Operation.COPY);
    dropTarget.addDropHandler(
        new DndDropHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDrop(DndDropEvent event) {
            ArrayList<DragItem> source = (ArrayList<DragItem>) event.getData();
            AmEventCenter.fireEvent(new DeviceConfigDeselectEvent(genDeviceConfigList(source)));
            AmEventCenter.fireEvent(new DragItemDeselectEvent(source));
          }
        });

    // CustomGroupView 專屬的 DropTarget
    // 本來就要移除、也沒有要再發 event，所以用 default 的 MOVE operation
    new DropTarget(this).setGroup(CustomGroupViewer.DND_GROUP);
  }

  private ArrayList<DeviceConfigDTO> genDeviceConfigList(ArrayList<DragItem> source) {
    ArrayList<DeviceConfigDTO> result = new ArrayList<>();
    for (DragItem item : source) {
      DeviceConfigDTO dto = DataCenter.getDeviceConfig(item.getUuid());
      if (dto != null) {
        result.add(dto);
      } else if (userAddNodeMap.get(item.getUuid()) != null) {
        // 自行加入的設備可能不是 deviceConfig
        IdNameNode node = userAddNodeMap.get(item.getUuid());
        DeviceConfigDTO deviceDTO = new DeviceConfigDTO();
        deviceDTO.setDeviceName(node.getDeviceName());
        deviceDTO.setDisplayName(node.getDeviceName());
        result.add(deviceDTO);
      }
    }
    return result;
  }

  /** 目前設計：acceptDevice 不為 null、且只有 deviceConfig（leaf）才能成為 dragSource。 */
  private void buildDragSource() {
    // 沒有設定 device type，所以沒有東西可以 DnD 也是很合理的
    // if (acceptDevice == null) {
    //     return;
    // }

    dragSource = new TreeDragSource<>(tree);
    dragSource.setTreeSource(TreeSource.BOTH);
    dragSource.setGroup(
        factory.isDragDeviceConfig() ? AddType.DeviceConfig.toString() : AddType.Other.toString());

    dragSource.addDragStartHandler(
        new DndDragStartHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDragStart(DndDragStartEvent event) {
            HashSet<DragItem> result = new HashSet<>();
            for (TreeNode<IdNameNode> node : (List<TreeNode<IdNameNode>>) event.getData()) {
              traversal(node, result);
            }
            event.setData(new ArrayList<>(result));
          }
        });
  }

  /**
   * 取得指定 node 下的所有 {@link DeviceConfigDTO}。
   *
   * <p>這裡用了一個前提假設，就是 leaf node 一定是 DeviceConfig。 這個前提假設 base on deviceTypeSetting() 的寫法 不能用
   * store.getDepth() 的原因在於，「自訂群組」的 DeviceConfig 會在第四層， 而標準路線路段下的 DeviceConfig 會在第五層。
   */
  private void traversal(TreeNode<IdNameNode> node, HashSet<DragItem> result) {
    if (node.getChildren().size() == 0) {
      String deviceName = node.getData().getDeviceName();
      if (factory.isDragDeviceConfig()) {
        DeviceConfigDTO dto = DataCenter.getDeviceConfig(deviceName);
        if (dto != null) {
          dto.setStatus(node.getData().getStatus());
          result.add(dto);
        } else if (userAddNodeMap.get(node.getData().getDeviceName()) != null) {
          DeviceConfigDTO addDto = new DeviceConfigDTO();
          addDto.setDeviceName(deviceName);
          addDto.setDisplayName(deviceName);
          result.add(addDto);
        } else {
          Info.display(
              messages.message(), node.getData().getName() + messages.RoadTreeView_notExist());
        }
        return;
      } else {
        DragItem item = new DragItem();
        //目前只能先用DeviceName做判斷
        item.setUuid(node.getData().getDeviceName());
        item.setSelectedDisplayName(node.getData().getName());
        result.add(item);
      }
    }

    for (TreeNode<IdNameNode> child : node.getChildren()) {
      traversal(child, result);
    }
  }

  /**
   * 是以 {@link DataCenter#getDeviceConfigList()} 的內容由下到上建 tree， 也就是有 device cofing 的 roadLine /
   * roadSection 才會出現在 tree。
   */
  private void buildTreeByDeviceConfig() {
    // 有設定 device type 才要顯示操作群組
    if (!removeRoot) {
      store.add(rootNode, groupRoot);
    } else {
      store.add(groupRoot);
    }

    // ======== //
    for (DeviceConfigDTO dc : deviceMap.values()) {
      deviceMap.put(dc.getDeviceName(), dc);
      // DeviceConfig.deviceName 就是邏輯意義的 id，所以 id 跟 name 都傳相同值
      IdNameNode dcNode =
          new IdNameNode(
              encodeDeviceConfigKey(dc.getDeviceName()),
              (null == factory) ? dc.getDisplayName() : factory.getDisplayName(dc),
              dc.getMilepost(),
              dc.getDirection());
      dcNode.setDeviceName(dc.getDeviceName());
      if (factory != null) {
        dcNode.setSpecialSignal(factory.getSpecialSignal(dc.getDeviceName()));
      }
      if (monitorDeviceName.equals(dc.getDeviceName())) {
        if (toShowGwtLog)
          GWT.log(
              "buildTreeByDeviceConfig put nodeMap deviceName:"
                  + dc.getDeviceName()
                  + "; dc.getEnable()="
                  + dc.getEnable());
      }
      nodeMap.put(dcNode.getId(), dcNode);
      dcNode.setStatus(getDeviceStatus(dc.getDeviceName()));
      try {
        if (roadSectionLevel) {
          store.add(getRoadSectionNode(dc.getSectionId()), dcNode);
        } else {
          store.add(getDirectionNode(dc.getLineId(), dc.getDirection()), dcNode);
        }
        store.update(dcNode);
      } catch (DataNotFoundException dnfe) {
        // 建樹的過程、一路往上找對應資料發生問題，目前暫時不作任何處理
      }
    }

    // ==== device group ==== //
    for (DeviceGroupDTO dg : DataCenter.getDeviceGroupList()) {
      IdNameNode dgNode = new IdNameNode(dg.getGroupId(), dg.getGroupName());
      store.add(groupRoot, dgNode); // 要先加完才有辦法建小孩

      ArrayList<IdNameNode> dgdcNodeList = new ArrayList<>();
      for (DeviceGroupDeviceConfigDTO dgdc : dg.getDevices()) {
        if (deviceMap.containsKey(dgdc.getDeviceName())) {
          String displayName;
          if (null == factory) {
            displayName = deviceMap.get(dgdc.getDeviceName()).getDisplayName();
          } else {
            displayName = factory.getDisplayName(deviceMap.get(dgdc.getDeviceName()));
          }
          IdNameNode dgdcNode =
              new IdNameNode(encodeDeviceConfigKey(dgdc.getDeviceName()), displayName);
          dgdcNode.setStatus(getDeviceStatus(dgdc.getDeviceName()));
          dgdcNode.setDeviceName(dgdc.getDeviceName());
          nodeMap.put(dgdcNode.getId(), dgdcNode);
          dgdcNodeList.add(dgdcNode);
        }
      }
      store.add(dgNode, dgdcNodeList);
    }
    //tree.expandAll();
    //tree.collapseAll();
    if (!removeRoot) tree.setExpanded(rootNode, true);
    if (toShowGwtLog) GWT.log("startRepeatFetchDataTimer in buildTreeByDeviceConfig");
    startRepeatFetchDataTimer();
    AmEventCenter.fireEvent(new RoadTreeViewerEvent(Action.READY));
    unmask();
    Timer delayTimer =
        new Timer() {
          @Override
          public void run() {
            tree.expandAll();
            tree.collapseAll();
            if (!removeRoot) tree.setExpanded(rootNode, true);
          }
        };
    delayTimer.schedule(200);
    //if (statusRefresh) {
    //    timer.run();
    //}
  }

  private DeviceStatusType getDeviceStatus(
      String deviceName, EmsDeviceStatusDTO emsDeviceStatusDTO) {
    DeviceStatusType deviceStatusType = DeviceStatusType.NONE;
    DeviceConfigDTO dc = deviceMap.get(deviceName);
    if (dc == null) {
      deviceStatusType = DeviceStatusType.SUSPEND;
    } else {
      if (monitorDeviceName.equals(deviceName)) {
        if (toShowGwtLog)
          GWT.log("deviceName:" + deviceName + "; dc.getEnable()=" + dc.getEnable());
      }
      if (dc.getEnable() != null && dc.getEnable()) {
        if (emsDeviceStatusDTO == null) {
          deviceStatusType = DeviceStatusType.OFFLINE;
        } else {
          if (monitorDeviceName.equals(deviceName)) {
            if (toShowGwtLog)
              GWT.log(
                  "deviceName:"
                      + deviceName
                      + "; emsDeviceStatusDTO.isAlive()="
                      + emsDeviceStatusDTO.isAlive());
          }
          if (emsDeviceStatusDTO.isAlive()) {
            deviceStatusType = DeviceStatusType.ONLINE;
          } else {
            deviceStatusType = DeviceStatusType.OFFLINE;
          }
        }
      } else {
        deviceStatusType = DeviceStatusType.SUSPEND;
      }
    }
    if (monitorDeviceName.equals(deviceName)) {
      if (toShowGwtLog)
        GWT.log("02 deviceName:" + deviceName + "; deviceStatusType=" + deviceStatusType);
    }
    return deviceStatusType;
  }

  private DeviceStatusType getDeviceStatus(String deviceName) {
    DeviceStatusType deviceStatusType = DeviceStatusType.NONE;
    DeviceConfigDTO dc = deviceMap.get(deviceName);
    if (dc == null) {
      deviceStatusType = DeviceStatusType.SUSPEND;
    } else {
      EmsDeviceStatusDTO emsDeviceStatusDTO = statusMap.get(dc.getDeviceName());
      deviceStatusType = getDeviceStatus(deviceName, emsDeviceStatusDTO);
    }

    //        DeviceStatusType status = (dc.getEnable()) ?
    //                DeviceStatusType.OFFLINE : DeviceStatusType.SUSPEND;
    //停用設備不列入設備狀態顯示及連線率計算
    // 設備狀態預設為正常
    //        if (statusMap.containsKey(dc.getDeviceName())) {
    //            EmsDeviceStatusDTO dto = statusMap.get(dc.getDeviceName());
    //            status = (deviceMap.get(dc.getDeviceName()).getEnable()) ?
    //                    (dto.isAlive()) ? DeviceStatusType.ONLINE : status : DeviceStatusType.SUSPEND;
    //        }
    if (monitorDeviceName.equals(deviceName)) {
      if (toShowGwtLog)
        GWT.log("01 deviceName:" + deviceName + "; deviceStatusType=" + deviceStatusType);
    }
    return deviceStatusType;
  }

  /**
   * 取得目前 {@link #store} 中的 roadLine 對應 node。 如果找不到，就會建立 roadLine node。
   *
   * @param lineId
   * @return
   */
  private IdNameNode getRoadLineNode(String lineId) {
    IdNameNode result = store.findModelWithKey(lineId);
    if (result != null) {
      return result;
    }

    RoadLineDTO rl = DataCenter.getRoadLine(lineId);
    if (rl == null) { // 找不到 RoadLine
      throw new DataNotFoundException("RoadLine id = " + lineId);
    }

    result = new IdNameNode(rl.getLineId(), rl.getLineName());
    if (!removeRoot) store.add(rootNode, result);
    else store.add(result);

    return result;
  }

  /**
   * 取得目前 {@link #store} 中的 roadSection 對應 node。 如果找不到，就會建立 roadSection node（以及上兩層的 node，如果有需要）。
   *
   * @param sectionId
   * @return
   */
  private IdNameNode getRoadSectionNode(String sectionId) {
    IdNameNode result = store.findModelWithKey(sectionId);
    if (result != null) {
      return result;
    }

    RoadSectionDTO rs = DataCenter.getRoadSection(sectionId);
    if (rs == null) { // 找不到 RoadSection
      throw new DataNotFoundException("RoadSection id = " + sectionId);
    }

    IdNameNode directionNode = getDirectionNode(rs.getLineid(), rs.getDirection());

    //無法取得milepost則指定為0
    Integer milepost =
        (DataCenter.getRoadDivision(rs.getStartDivisionId()) == null)
            ? 0
            : DataCenter.getRoadDivision(rs.getStartDivisionId()).getMileage();
    result = new IdNameNode(rs.getSectionId(), rs.getSectionName(), milepost, rs.getDirection());
    store.add(directionNode, result);

    return result;
  }

  private IdNameNode getDirectionNode(String lineId, Direction direction) {
    // 找對應的 roadLine 下的 direction node
    String directionId = genDirectionId(lineId, direction);
    IdNameNode directionNode = store.findModelWithKey(directionId);
    if (directionNode == null) {
      IdNameNode roadLineNode = getRoadLineNode(lineId);
      directionNode =
          new IdNameNode(directionId, CommonStringConverter.getDirectionName(direction));
      store.add(roadLineNode, directionNode);
    }

    return directionNode;
  }

  private void buildTree() {
    if (toShowGwtLog) GWT.log("buildTree removeRoot:" + removeRoot);
    if (!removeRoot) store.add(rootNode);
    for (RoadSectionDTO rs : DataCenter.getRoadSectionList()) {
      try {
        tree.setLeaf(getRoadSectionNode(rs.getSectionId()), true);
      } catch (DataNotFoundException dnfe) {
        // 建樹的過程、一路往上找對應資料發生問題，目前暫時不作任何處理
      }
    }

    // 把第二層打開
    if (toShowGwtLog) GWT.log("資料準備中 2");
    //tree.expandAll();
    //tree.collapseAll();
    AmEventCenter.fireEvent(new RoadTreeViewerEvent(Action.READY));
    unmask();
    if (!removeRoot) tree.setExpanded(rootNode, true);
  }

  //	@UiHandler("allExpand")
  //	void selectAllExpand(SelectEvent se) {
  //		tree.expandAll();
  //	}
  //
  //	@UiHandler("allCollapse")
  //	void selectAllCollapse(SelectEvent se) {
  //		tree.collapseAll();
  //        if(!removeRoot)tree.setExpanded(rootNode, true);
  //	}
  @UiHandler("expandAllImage")
  void expandAllImageClick(ClickEvent e) {
    tree.expandAll();
  }

  @UiHandler("collapseAllImage")
  void collapseAllImageClick(ClickEvent e) {
    tree.collapseAll();
    if (!removeRoot) {
      tree.setExpanded(rootNode, true);
    }
  }

  private static int autoIndex = 0;
  private static final String SPLITER = "::::";

  private static String encodeDeviceConfigKey(String deviceName) {
    return autoIndex++ + SPLITER + deviceName;
  }

  /*private static String decodeDeviceConfigKey(String value) {
      return value.substring(value.indexOf(SPLITER) + SPLITER.length());
  }*/

  private static String genDirectionId(String id, Direction direction) {
    return id + SPLITER + direction.toString();
  }

  /**
   * 回傳所屬 roadLine 的 id，對應 {@link #genDirectionId(String, Direction)}。
   *
   * <p>因為 {@link #genDirectionId(String, Direction)} 的關係， 所以這個 method 也只好放在這裡 T__T。（謎之聲：有夠醜的）
   *
   * @param directionId
   * @return 所屬 roadLine 的 id
   */
  public static String parseRoadlineId(String directionId) {
    for (Direction dt : Direction.values()) {
      if (directionId.endsWith(dt.toString())) {
        return directionId.substring(0, directionId.length() - dt.toString().length());
      }
    }
    return null;
  }

  public enum SelectType {
    ROOT(1),
    LINE(2),
    DIRECTION(3),
    SECTION(4),
    DEVICE_CONFIG(5);

    private int depth;

    SelectType(int depth) {
      this.depth = depth;
    }

    public int getDepth() {
      return depth;
    }

    public static SelectType byDepth(int value) {
      for (SelectType st : SelectType.values()) {
        if (st.getDepth() == value) {
          return st;
        }
      }
      return null;
    }
  }

  /**
   * 設定RoadTreeView的IconProvider
   *
   * @param iconProvider
   */
  public void setIconProvider(IconProvider<IdNameNode> iconProvider) {
    tree.setIconProvider(iconProvider);
  }

  public void setLeafImage(ImageResource imageResource) {
    tree.getStyle().setLeafIcon(imageResource);
  }

  public ImageResource getJointImage() {
    return treeAppearance.closeNodeIcon();
  }

  interface IdNameProperties extends PropertyAccess<IdNameNode> {
    ModelKeyProvider<IdNameNode> id();

    ValueProvider<IdNameNode, String> name();
  }

  //調整Sorter，針對direction及milepost進行sort
  private class TreeSorter extends StoreSortInfo<IdNameNode> {
    public TreeSorter() {
      super(
          new Comparator<IdNameNode>() {
            @Override
            public int compare(IdNameNode o1, IdNameNode o2) {
              if (o1.getMilePost() != null && o2.getMilePost() != null) {
                if (o1.getDirection() == Direction.N || o1.getDirection() == Direction.W) {
                  return o2.getMilePost().compareTo(o1.getMilePost());
                } else {
                  return o1.getMilePost().compareTo(o2.getMilePost());
                }
              }
              return o1.getId().compareTo(o2.getId());
            }
          },
          SortDir.ASC);
    }
  }

  @SuppressWarnings("serial")
  private class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
      super(message);
    }
  }

  // 設定解析SafeHtml的Cell
  public class SimpleAbstractCell extends AbstractCell<String> {
    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
      if (value == null) {
        return;
      }
      SafeHtml safeValue = SafeHtmlUtils.fromTrustedString(value);
      sb.append(safeValue);
    }
  }

  /** 取得對應{@link DeviceTypeDTO}的{@link DeviceConfigDTO} */
  private void getDeviceConfigMap() {
    // mask("資料讀取中..");
    // 加入判斷通訊協定 擷取設備
    if (toShowGwtLog) {
      GWT.log("資料讀取中..1");
      GWT.log("RoadTreeView getDeviceConfigMap acceptDevice =" + acceptDevice);
    }

    HomeEP.camService.getDeviceConfig(
        acceptDevice,
        new MethodCallback<List<DeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceConfigDTO> result) {
            if (toShowGwtLog) {
              GWT.log("getDeviceConfigMap onSuccess");
            }
            for (DeviceConfigDTO dto : result) {
              if (toShowGwtLog && monitorDeviceName.equals(dto.getDeviceName())) {
                GWT.log(
                    "RoadTreeView getDeviceConfigMap " + "monitorDeviceName =" + dto.toString());
              }
              if (tcProtocolTypeValue != null) {
                if (tcProtocolTypeValue.equals(dto.getProtocolType())) {
                  deviceMap.put(dto.getDeviceName(), dto);
                }
              } else {
                deviceMap.put(dto.getDeviceName(), dto);
              }
            }
            getEmsDeviceStatus();
            // unmask();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            if (toShowGwtLog) {
              GWT.log("getDeviceConfigMap onFailure");
            }
            Info.display(messages.error(), messages.message_cannotFetchData());
            // unmask();
          }
        });
  }

  /** 取得設備的目前狀態，之後呼叫{@link #buildTreeByDeviceConfig} */
  private void getEmsDeviceStatus() {

    if (toShowGwtLog) GWT.log("RoadTreeView getEmsDeviceStatus acceptDevice =" + acceptDevice);
    HomeEP.camService.getDeviceStatus(
        acceptDevice,
        new MethodCallback<List<EmsDeviceStatusDTO>>() {
          @Override
          public void onSuccess(Method method, List<EmsDeviceStatusDTO> result) {
            // 檢查"中交控系統"下是否建立資料
            // 是=更新資料, 否=建立資料
            statusMap.clear();
            if (store.getAll().size() > 1) {
              if (toShowGwtLog) GWT.log("getEmsDeviceStatus 資料讀取中..2_1 start");
              for (EmsDeviceStatusDTO eds : result) {
                statusMap.put(eds.getDeviceName(), eds);
                if (monitorDeviceName.equals(eds.getDeviceName())) {
                  if (toShowGwtLog)
                    GWT.log("RoadTreeView getEmsDeviceStatus monitorDeviceName =" + eds.toString());
                }
                List<IdNameNode> nodeList = new ArrayList<>();
                for (String nodeId : nodeMap.keySet()) {
                  if (nodeId.indexOf(eds.getDeviceName()) > 0) {
                    nodeList.add(nodeMap.get(nodeId));
                  }
                }
                for (IdNameNode item : nodeList) {
                  item.setStatus(getDeviceStatus(eds.getDeviceName(), eds));
                  if (monitorDeviceName.equals(eds.getDeviceName())) {
                    if (toShowGwtLog)
                      GWT.log(
                          "RoadTreeView getEmsDeviceStatus monitorDeviceName  : item ="
                              + item.toString());
                  }
                  // if(toShowGwtLog) GWT.log("RoadTreeView
                  // getEmsDeviceStatus item ="+item.toString());
                  IdNameNode existedItem = store.findModelWithKey(item.getId());
                  if (existedItem != null) {
                    store.update(item);
                  } else {
                    logger.info(
                        "getEmsDeviceStatus existedItem :" + existedItem + " 找不到 in store!為什麼?");
                    // store.add(item);
                  }
                }
              }
              if (toShowGwtLog) GWT.log("getEmsDeviceStatus 資料讀取中..2_1 end");
            } else {
              if (toShowGwtLog) GWT.log("getEmsDeviceStatus 資料讀取中..2_2");
              for (EmsDeviceStatusDTO eds : result) {
                if (monitorDeviceName.equals(eds.getDeviceName())) {
                  if (toShowGwtLog)
                    GWT.log(
                        "RoadTreeView getEmsDeviceStatus store.getAll().size() < 1  , monitorDeviceName: "
                            + eds.toString());
                }
                statusMap.put(eds.getDeviceName(), eds);
              }
              buildTreeByDeviceConfig();
              // unmask();
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            Info.display(messages.error(), messages.message_cannotFetchData());
            unmask();
          }
        });
  }

  /** @return the selectedView */
  public RoadTreeSelectedViewer getSelectedView() {
    return selectedView;
  }

  /** @return the borderLayoutContainer */
  public BorderLayoutContainer getBorderLayoutContainer() {
    return borderLayoutContainer;
  }

  public void expandedTreeAndSelected(IdNameNode expandedNode, String deviceName) {
    if (toShowGwtLog) GWT.log("RoadTreeView expandedTreeAndSelected deviceName=" + deviceName);
    tree.setExpanded(expandedNode, true);
    for (IdNameNode node : store.getAll()) {
      if (deviceName.equals(node.getDeviceName())) {
        tree.getSelectionModel().select(node, true);
      }
    }
  }

  public IdNameNode getParentNode(String deviceName) {
    if (toShowGwtLog) GWT.log("RoadTreeView getParentNode deviceName=" + deviceName);
    IdNameNode parentNode = null;
    for (IdNameNode node : store.getAll()) {
      if (node.getMilePost() == null) {
        continue;
      }
      //if(node.getDirection() != null && deviceName.equals(node.getDeviceName())) {
      if (deviceName.equals(node.getDeviceName())) {
        if (toShowGwtLog) GWT.log("RoadTreeView node=" + node);
        parentNode = store.getParent(node);
        if (toShowGwtLog) GWT.log("RoadTreeView parentNode=" + parentNode);
      }
    }
    return parentNode;
  }

  public IdNameNode findModelWithDeviceName(String deviceName) {
    if (toShowGwtLog) GWT.log("RoadTreeView findModelWithDeviceName deviceName=" + deviceName);
    IdNameNode returnNode = null;
    for (IdNameNode node : store.getAll()) {
      if (node.getMilePost() == null) {
        continue;
      }
      if (toShowGwtLog) GWT.log(deviceName + ":" + node.getDeviceName());
      if (node.getDeviceName() != null && deviceName.equals(node.getDeviceName())) {
        if (toShowGwtLog) GWT.log("RoadTreeView findModelWithDeviceName node=" + node);
        returnNode = node;
      }
    }
    return returnNode;
  }

  public IdNameNode findModelWithKey(String key) {
    if (toShowGwtLog) {
      GWT.log("RoadTreeView findModelWithKey key = " + key);
    }
    IdNameNode returnNode = store.findModelWithKey(key);
    if (toShowGwtLog) {
      GWT.log("RoadTreeView findModelWithKey returnNode = " + returnNode);
    }
    return returnNode;
  }

  public void setHiddenToolBar(boolean hide) {
    if (hide) {
      toolBar.hide();
      toolBarRowData.setHeight(0);
    }
  }

  public void setHiddenSelectedView(boolean hide) {
    if (hide) {
      borderLayoutContainer.hide(LayoutRegion.SOUTH);
    }
  }

  public void setRemoveRoot(boolean removeRoot) {
    this.removeRoot = removeRoot;
    if (removeRoot && store.findModel(rootNode) != null) {
      store.remove(rootNode);
    }
  }

  public void setStopRepeat(boolean stopRepeat) {
    if (toShowGwtLog) {
      GWT.log("RoadTreeView setStopRepeat = " + stopRepeat);
    }

    this.stopRepeat = stopRepeat;
  }

  public void stopRepeatFetchData() {
    if (toShowGwtLog) GWT.log("stopRepeatFetchData start ...");
    stopRepeatFetechDataTimer();
  }

  public void startRepeatFetchData() {
    startRepeatFetchDataTimer();
  }

  private void startRepeatFetchDataTimer() {
    if (toShowGwtLog) GWT.log("startRepeatFetechDataTimer start ...: stopRepeat =" + stopRepeat);
    if (stopRepeat) {
      return;
    }
    if (repeatFetechDataTimer != null) {
      if (toShowGwtLog) GWT.log("repeatFetechDataTimer != null , so cancel 後再建立!");
      repeatFetechDataTimer.cancel();
    }
    repeatFetechDataTimer =
        new Timer() {
          @Override
          public void run() {
            if (toShowGwtLog) GWT.log("startRepeatFetechDataTimer getDeviceConfigMap...");
            getDeviceConfigMap();
          }
        };
    if (toShowGwtLog) GWT.log("Run repeatFetechDataTimer.scheduleRepeating ...");
    repeatFetechDataTimer.scheduleRepeating(30000);
    stopRepeat = false;
    if (toShowGwtLog) GWT.log("startRepeatFetechDataTimer end ...: stopRepeat =" + stopRepeat);
  }

  private void stopRepeatFetechDataTimer() {
    if (toShowGwtLog) {
      GWT.log("stopRepeatFetechDataTimer start ...");
      stopRepeat = true;
    }
    if (repeatFetechDataTimer != null) {
      if (toShowGwtLog) GWT.log("stopRepeatFetechDataTimer...repeatFetechDataTimer.cancel()");
      repeatFetechDataTimer.cancel();
    } else {
      if (toShowGwtLog) GWT.log("repeatFetechDataTimer had be null.can not stop");
    }
  }

  public boolean isRoadSectionLevel() {
    return roadSectionLevel;
  }

  public void setRoadSectionLevel(boolean roadSectionLevel) {
    this.roadSectionLevel = roadSectionLevel;
  }

  /**
   * 自行加入 leafNode </br> 注意!自行加入的 node 將不檢查是否為 DeviceConfigDTO 請小心使用 </br> 注意!leaf 的 IdNameNode 務必要
   * setDeviceName 否則已選取設備無法顯示 </br>
   *
   * @param groupNode 群組 node
   * @param nodes 加入的 nodes
   */
  public void insertNodeUnderRoot(IdNameNode groupNode, List<IdNameNode> nodes) {
    if (groupNode == null || nodes == null || nodes.size() == 0) {
      GWT.log("RoadTreeView insert node under root failed, groupNode or nodes is null or empty.");
      return;
    }
    store.add(rootNode, groupNode);
    for (IdNameNode node : nodes) {
      userAddNodeMap.put(node.getDeviceName(), node);
    }
    store.add(groupNode, nodes);
  }

  public void initSelectView() {
    selectedView.removeAllDevices();
  }
}
