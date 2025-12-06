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
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.fm.model.DeviceType;
import com.hwacom.ngtms.c.shared.DeviceStatusType;
import com.hwacom.ngtms.c.shared.Direction;
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
import com.hwacom.ngtms.cam.client.event.RoadTreeSelectEvent;
import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.images.AmImages;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.hwacom.ngtms.cam.util.DataCenter;
import com.hwacom.ngtms.cam.view.Messages;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
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
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.dnd.core.client.TreeDragSource;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * 第一層是「中交控系統」、第二層是 {@link RoadLineDTO}、 第三層是 {東, 西, 南, 北} 向（沒有實際對應的 vo）、 第四層是 {@link
 * RoadSectionDTO}、第五層是 {@link DeviceConfigDTO}。
 *
 * <p>第四層跟第五層的呈現邏輯與 {@link #setDeviceType(DeviceType)} 或 {@link #setDeviceTypeList(List)} 相關：
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
 * @author monty.pan
 */
public class DeviceTreeViewer extends Composite {
  private static DeviceTreeViewUiBinder uiBinder = GWT.create(DeviceTreeViewUiBinder.class);

  interface DeviceTreeViewUiBinder extends UiBinder<Widget, DeviceTreeViewer> {}

  private static final GridProperties props = GWT.create(GridProperties.class);

  @UiField Messages messages;

  @UiField(provided = true)
  TreeStore<IdNameNode> store;

  @UiField(provided = true)
  ValueProvider<IdNameNode, String> valueProvider;

  @UiField Tree<IdNameNode, String> tree;

  @UiField(provided = true)
  ListStore<DeviceConfigDTO> gridStore;

  @UiField(provided = true)
  ColumnModel<DeviceConfigDTO> cm;

  @UiField Grid<DeviceConfigDTO> grid;
  @UiField ContentPanel gridPanel;
  @UiField VerticalLayoutContainer treeVerticalPanel;

  /** 沒有 element 表示不顯示第五層。 */
  private ArrayList<String> acceptDevice = new ArrayList<>();

  private IdNameNode rootNode = new IdNameNode("root", messages.tree_root());
  private IdNameNode groupRoot = new IdNameNode("group", messages.tree_group());
  private TreeDragSource<IdNameNode> dragSource;
  // 儲存設備EmsDeviceStatusDTO的Map
  private Map<String, EmsDeviceStatusDTO> statusMap = new HashMap<>();

  public DeviceTreeViewer() {
    IdNameProperties props = GWT.create(IdNameProperties.class);
    store = new TreeStore<>(props.id());
    store.addSortInfo(new TreeSorter());
    store.add(rootNode);
    valueProvider =
        new ValueProvider<DeviceTreeViewer.IdNameNode, String>() {
          @Override
          public String getValue(IdNameNode item) {
            String color =
                (item.getStatus() == DeviceStatusType.SUSPEND)
                    ? "grey"
                    : (item.getStatus() == DeviceStatusType.ONLINE) ? "black" : "red";
            return "<span style='color: " + color + "'>" + item.getName() + "</span>";
          }

          @Override
          public void setValue(IdNameNode object, String value) {}

          @Override
          public String getPath() {
            return "deviceTree";
          }
        };
    generateGirdCondition();
    initWidget(uiBinder.createAndBindUi(this));

    // TODO 依照不同的 deviceType 給不同的 icon（IconProvider？）
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
                // IdNameNode node = event.getSelectedItem();
              }
            });

    buildDropTarget();
  }

  /**
   * 決定第五層要顯示哪個 {@link DeviceType} 的 device。
   *
   * @param deviceType 傳入 null 就不會顯示第五層
   */
  public void setDeviceType(String deviceType) {
    // 設定相同的 deviceType 就不繼續做下去
    if (acceptDevice.size() == 1 && acceptDevice.contains(deviceType)) {
      return;
    }

    acceptDevice.clear();

    if (deviceType != null) {
      acceptDevice.add(deviceType);
    }

    deviceTypeSetting();
  }

  /**
   * 設定已選取設備區內的指定設備
   *
   * @param index 單一設備在已選取設備區內的次序
   */
  public void setGridSelection(int index) {
    if (index < gridStore.size()) {
      List<DeviceConfigDTO> selection = new ArrayList<>();
      selection.add(gridStore.get(index));
      grid.getSelectionModel().setSelection(selection);
    }
  }

  /**
   * 決定第五層要顯示哪些 {@link DeviceType} 的 device。
   *
   * @param deviceTypeList 傳入 null 就不會顯示第五層
   */
  public void setDeviceTypeList(List<String> deviceTypeList) {
    acceptDevice.clear();

    // 懶得比對，一律重作 XD
    if (deviceTypeList != null) {
      acceptDevice.addAll(deviceTypeList);
    }

    deviceTypeSetting();
  }

  private void deviceTypeSetting() {
    store.clear();
    store.add(rootNode);

    if (acceptDevice.size() != 0) {
      // 有指定 deviceType，重新要一次 deviceConfig，然後判斷要不要建立 dragSource
      DataCenter.setDeviceType(acceptDevice);
      if (dragSource == null) {
        buildDragSource();
      }
    } else {
      // 反之，拿掉 DragSource 的功能
      if (dragSource != null) {
        dragSource.release();
      }
      dragSource = null;
    }

    this.mask(messages.message_fecthData());
    // 用 pooling 的方式檢查各項資料到底做完 RPC 了沒
    Scheduler.get()
        .scheduleFixedDelay(
            new RepeatingCommand() {
              @Override
              public boolean execute() {
                if (DataCenter.getRoadLineList() != null
                    && DataCenter.getRoadSectionList() != null) {
                  if (acceptDevice.size() != 0) {
                    if (DataCenter.getDeviceConfigList() != null
                        && DataCenter.getDeviceGroupList() != null) {
                      unmask();
                      // 改用getEmsDeviceStatus來接管設備樹產生
                      getEmsDeviceStatus();
                      return false;
                    }
                  } else { // 沒有指定 acceptDevice 就不用等 DeviceConfig 資料
                    unmask();
                    buildTree();
                    return false;
                  }
                }
                return true;
              }
            },
            200); // 0.2 秒檢查一次
  }

  private void buildDropTarget() {
    DropTarget dropTarget = new DropTarget(tree);
    dropTarget.setGroup("DeviceTreeDropGroup");
    dropTarget.setOperation(Operation.COPY);
    dropTarget.addDropHandler(
        new DndDropHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDrop(DndDropEvent event) {
            AmEventCenter.fireEvent(
                new DeviceConfigDeselectEvent((ArrayList<DeviceConfigDTO>) event.getData()));
          }
        });

    DropTarget gridDropTarget = new DropTarget(grid);
    gridDropTarget.setOperation(Operation.COPY);
    gridDropTarget.setGroup("DeviceTreeGroup");
    gridDropTarget.addDropHandler(
        new DndDropHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDrop(DndDropEvent event) {
            //if (grid.getStore().size() == 0) {
            ArrayList<DeviceConfigDTO> newList = new ArrayList<>();
            for (DeviceConfigDTO device : (ArrayList<DeviceConfigDTO>) event.getData()) {
              // 排除重複
              if (grid.getStore().findModel(device) == null) {
                newList.add(device);
              }
            }
            grid.getStore().addAll(newList);
            //}

          }
        });

    // CustomGroupView 專屬的 DropTarget
    // 本來就要移除、也沒有要再發 event，所以用 default 的 MOVE operation
    new DropTarget(tree).setGroup("DeviceGridGroup");
  }

  /** 目前設計：acceptDevice 不為 null、且只有 deviceConfig（leaf）才能成為 dragSource。 */
  private void buildDragSource() {
    // 沒有設定 device type，所以沒有東西可以 DnD 也是很合理的
    if (acceptDevice == null) {
      return;
    }

    dragSource = new TreeDragSource<>(tree);
    dragSource.setTreeSource(TreeSource.BOTH);
    dragSource.setGroup("DeviceTreeGroup");

    dragSource.addDragStartHandler(
        new DndDragStartHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDragStart(DndDragStartEvent event) {
            HashSet<DeviceConfigDTO> result = new HashSet<>();
            for (TreeNode<IdNameNode> node : (List<TreeNode<IdNameNode>>) event.getData()) {
              traversal(node, result);
            }
            event.setData(new ArrayList<>(result));
          }
        });

    GridDragSource<DeviceConfigDTO> gridDragSource = new GridDragSource<>(grid);
    gridDragSource.setGroup("DeviceGridGroup");
    gridDragSource.addDropHandler(
        new DndDropHandler() {
          @Override
          public void onDrop(DndDropEvent event) {
            GWT.log("gridDragSource onDrop...start");
          }
        });
  }

  /**
   * 取得指定 node 下的所有 {@link DeviceConfigDTO}。
   *
   * <p>這裡用了一個前提假設，就是 leaf node 一定是 DeviceConfig。 這個前提假設 base on deviceTypeSetting() 的寫法 不能用
   * store.getDepth() 的原因在於，「自訂群組」的 DeviceConfig 會在第四層， 而標準路線路段下的 DeviceConfig 會在第五層。
   */
  private void traversal(TreeNode<IdNameNode> node, HashSet<DeviceConfigDTO> result) {
    if (node.getChildren().size() == 0) {
      result.add(DataCenter.getDeviceConfig(decodeDeviceConfigKey(node.getData().getId())));
      return;
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
    store.add(rootNode, groupRoot);

    // ==== device group ==== //
    for (DeviceGroupDTO dg : DataCenter.getDeviceGroupList()) {
      IdNameNode dgNode = new IdNameNode(dg.getGroupId(), dg.getGroupName());
      store.add(groupRoot, dgNode); // 要先加完才有辦法建小孩

      ArrayList<IdNameNode> dgdcNodeList = new ArrayList<>();
      for (DeviceGroupDeviceConfigDTO dgdc : dg.getDevices()) {
        IdNameNode dgdcNode =
            new IdNameNode(
                encodeDeviceConfigKey(dgdc.getDeviceName()),
                DataCenter.getDisplayName(dgdc.getDeviceName()));
        dgdcNodeList.add(dgdcNode);
      }
      store.add(dgNode, dgdcNodeList);
    }
    // ======== //
    for (DeviceConfigDTO dc : DataCenter.getDeviceConfigList()) {
      // DeviceConfig.deviceName 就是邏輯意義的 id，所以 id 跟 name 都傳相同值
      // 設備狀態預設為正常
      DeviceStatusType status = DeviceStatusType.OFFLINE;
      if (statusMap.containsKey(dc.getDeviceName())) {
        EmsDeviceStatusDTO dto = statusMap.get(dc.getDeviceName());
        // 檢查連線或斷線
        status =
            (dto == null)
                ? DeviceStatusType.OFFLINE
                : (dto.getCommStatus() == 0)
                    ? DeviceStatusType.ONLINE
                    : (!dc.getEnable()) ? DeviceStatusType.SUSPEND : status;
      }
      IdNameNode dcNode =
          new IdNameNode(
              encodeDeviceConfigKey(dc.getDeviceName()),
              dc.getDisplayName(),
              dc.getMilepost(),
              dc.getDirection());
      dcNode.setStatus(status);
      try {
        store.add(getRoadSectionNode(dc.getSectionId()), dcNode);
        store.update(dcNode);
      } catch (DataNotFoundException dnfe) {
        // 建樹的過程、一路往上找對應資料發生問題，目前暫時不作任何處理
      }
    }

    tree.setExpanded(rootNode, true);
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
    store.add(rootNode, result);

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

    // 找對應的 roadLine 下的 direction node
    String directionId = genDirectionId(rs.getLineid(), rs.getDirection());
    IdNameNode directionNode = store.findModelWithKey(directionId);
    if (directionNode == null) {
      IdNameNode rsNode = getRoadLineNode(rs.getLineid());
      directionNode =
          new IdNameNode(directionId, CommonStringConverter.getDirectionName(rs.getDirection()));
      store.add(rsNode, directionNode);
    }

    // 無法取得milepost則指定為0
    // TODO 修正DB資料正確性才是正確的解法
    Integer milepost =
        (DataCenter.getRoadDivision(rs.getStartDivisionId()) == null)
            ? 0
            : DataCenter.getRoadDivision(rs.getStartDivisionId()).getMileage();
    result = new IdNameNode(rs.getSectionId(), rs.getSectionName(), milepost, rs.getDirection());
    store.add(directionNode, result);

    return result;
  }

  private void buildTree() {
    for (RoadSectionDTO rs : DataCenter.getRoadSectionList()) {
      try {
        tree.setLeaf(getRoadSectionNode(rs.getSectionId()), true);
      } catch (DataNotFoundException dnfe) {
        // 建樹的過程、一路往上找對應資料發生問題，目前暫時不作任何處理
      }
    }

    // 把第二層打開
    tree.setExpanded(rootNode, true);
  }

  @UiHandler("allExpand")
  void selectAllExpand(SelectEvent se) {
    tree.expandAll();
  }

  @UiHandler("allCollapse")
  void selectAllCollapse(SelectEvent se) {
    tree.collapseAll();
  }

  private static int autoIndex = 0;
  private static final String SPLITER = "::::";

  private static String encodeDeviceConfigKey(String deviceName) {
    return autoIndex++ + SPLITER + deviceName;
  }

  private static String decodeDeviceConfigKey(String value) {
    return value.substring(value.indexOf(SPLITER) + SPLITER.length());
  }

  private static String genDirectionId(String id, Direction direction) {
    return id + direction.toString();
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

  public DeviceConfigDTO getSelectedDeviceConfig() {
    if (grid.getStore().size() > 0) return grid.getStore().get(0);
    return null;
  }

  public List<DeviceConfigDTO> getSelectedDeviceConfigs() {
    if (grid.getStore().size() > 0) return grid.getStore().getAll();
    return null;
  }

  // // ==== 下面都是為了建 tree 而弄出來的 class，基本上不用理會也不該理會 XD ====//

  // 得是 default、不能 private，不然 BeforeExpandItemEvent 無法用 UiHandler 直接處理 Orz
  // 新增milepost及direction做排序處理
  class IdNameNode {
    private String id;
    private String name;
    private Integer milepost;
    private Direction direction;
    private DeviceStatusType status = DeviceStatusType.ONLINE;

    public IdNameNode(String id, String name) {
      this(id, name, null, null);
    }

    public IdNameNode(String id, String name, Integer milepost, Direction direction) {
      this.id = id;
      this.name = name;
      this.milepost = milepost;
      this.direction = direction;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public Integer getMilePost() {
      return milepost;
    }

    public Direction getDirection() {
      return direction;
    }

    public void setStatus(DeviceStatusType status) {
      this.status = status;
    }

    public DeviceStatusType getStatus() {
      return status;
    }
  }

  interface IdNameProperties extends PropertyAccess<IdNameNode> {
    ModelKeyProvider<IdNameNode> id();

    ValueProvider<IdNameNode, String> name();
  }

  // 調整Sorter，針對direction及milepost進行sort
  private class TreeSorter extends StoreSortInfo<IdNameNode> {
    public TreeSorter() {
      super(
          new Comparator<IdNameNode>() {
            @Override
            public int compare(IdNameNode o1, IdNameNode o2) {
              if (o1.getMilePost() != null && o2.getMilePost() != null) {
                if (o1.getDirection() == Direction.N || o1.getDirection() == Direction.W) {
                  return o2.milepost.compareTo(o1.milepost);
                } else {
                  return o1.milepost.compareTo(o2.milepost);
                }
              }
              return o1.id.compareTo(o2.id);
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

  /** 取得設備的目前狀態，之後呼叫{@link #buildTreeByDeviceConfig} */
  private void getEmsDeviceStatus() {
    mask("資料讀取中..");
    HomeEP.camService.getDeviceStatus(
        acceptDevice,
        new MethodCallback<List<EmsDeviceStatusDTO>>() {
          @Override
          public void onSuccess(Method method, List<EmsDeviceStatusDTO> result) {
            for (EmsDeviceStatusDTO eds : result) {
              statusMap.put(eds.getDeviceName(), eds);
            }
            buildTreeByDeviceConfig();
            unmask();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            Info.display(messages.error(), messages.message_cannotFetchData());
            unmask();
          }
        });
  }

  interface GridProperties extends PropertyAccess<DeviceConfigDTO> {
    @Path("deviceName")
    ModelKeyProvider<DeviceConfigDTO> id();

    ValueProvider<DeviceConfigDTO, String> displayName();
  }

  private void generateGirdCondition() {
    ArrayList<ColumnConfig<DeviceConfigDTO, ?>> columnConfigList = new ArrayList<>();
    // 因為在 ui.xml 當中 GridView 有設定 forceFit=true，所以這邊傳入的大小不是很重要
    columnConfigList.add(new ColumnConfig<>(props.displayName()));
    cm = new ColumnModel<>(columnConfigList);
    gridStore = new ListStore<>(props.id());
  }
}
