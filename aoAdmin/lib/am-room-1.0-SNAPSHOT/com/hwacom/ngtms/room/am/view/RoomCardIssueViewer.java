package com.hwacom.ngtms.room.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.room.am.presenter.RoomCardIssuePresenter;
import com.hwacom.ngtms.room.am.util.StringConverter;
import com.hwacom.ngtms.room.shared.CardStatus;
import com.hwacom.ngtms.room.shared.CardType;
import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
import com.hwacom.ngtms.room.shared.dto.RoomCardPermissionTreeDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardReaderMappingConfigDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreFilter;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent.CheckChangedHandler;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.TimeField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckCascade;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckNodes;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckState;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RoomCardIssueViewer extends AmTab {

  private static RoomCardIssueViewerUiBinder uiBinder =
      GWT.create(RoomCardIssueViewerUiBinder.class);

  interface RoomCardIssueViewerUiBinder extends UiBinder<Widget, RoomCardIssueViewer> {}

  private RoomCardConfigPropertyAccess roomCardConfigPropertyAccess =
      GWT.create(RoomCardConfigPropertyAccess.class);

  private RoomCardIssuePresenter presenter = new RoomCardIssuePresenter(this);

  private Dialog createCardDialog;

  private StoreFilter<RoomCardConfigDTO> abafilter;

  private StoreFilter<RoomCardConfigDTO> cardTypefilter;

  private StoreFilter<RoomCardConfigDTO> issuefilter;

  private Dialog addCardFailDialog;

  private Dialog logoutCardDialog;

  private Dialog forceIssuingCardDialog;

  @UiField Label aba;

  @UiField Label issued;

  @UiField TextField name;

  @UiField TextField idNo;

  @UiField TextField company;

  @UiField TextField employeeNo;

  @UiField TextField jobTitle;

  @UiField TextField tel;

  @UiField TextField mobile;

  @UiField TextField roleId;

  @UiField DateField startDate;

  @UiField DateField endDate;

  @UiField DateField actualStartDate;

  @UiField TimeField actualStartTime;

  @UiField DateField actualEndDate;

  @UiField TimeField actualEndTime;

  @UiField DateField expectedReturnDate;

  @UiField TimeField expectedReturnTime;

  @UiField DateField returnDate;

  @UiField TimeField returnTime;

  @UiField TextField memo;

  @UiField Grid<RoomCardConfigDTO> grid;

  @UiField(provided = true)
  ListStore<RoomCardConfigDTO> listStore;

  @UiField(provided = true)
  ColumnModel<RoomCardConfigDTO> columnModel;

  @UiField TextField queryAba;

  @UiField ComboBox<String> queryCardTypeComboBox;

  @UiField(provided = true)
  ListStore<String> queryCardTypeCbStore;

  @UiField(provided = true)
  LabelProvider<String> queryCardTypeCbLabelProvider;

  @UiField ComboBox<String> queryIssueComboBox;

  @UiField(provided = true)
  ListStore<String> queryIssueCbStore;

  @UiField(provided = true)
  LabelProvider<String> queryIssueCbLabelProvider;

  @UiField ComboBox<CardType> cardTypeComboBox;

  @UiField(provided = true)
  ListStore<CardType> cardTypeCbStore;

  @UiField(provided = true)
  LabelProvider<CardType> cardTypeCbLabelProvider;

  @UiField ComboBox<RoomCardGroupConfigDTO> cardGroupComboBox;

  @UiField(provided = true)
  ListStore<RoomCardGroupConfigDTO> cardGroupCbStore;

  @UiField(provided = true)
  LabelProvider<RoomCardGroupConfigDTO> cardGroupCbLabelProvider;

  @UiField ComboBox<CardStatus> cardStatusComboBox;

  @UiField(provided = true)
  ListStore<CardStatus> cardStatusCbStore;

  @UiField(provided = true)
  LabelProvider<CardStatus> cardStatusCbLabelProvider;

  @UiField(provided = true)
  TreeStore<CardPermissionVO> treeStore;

  @UiField(provided = true)
  ValueProvider<CardPermissionVO, String> treeValueProvider;

  @UiField Tree<CardPermissionVO, String> tree;

  @UiField ToolButton createCard;

  @UiField TextButton issueImmediately;

  @UiField TextButton issue;

  @UiField TextButton modify;

  private CardPermissionVO root = new CardPermissionVO("root", "機房", null);

  public RoomCardIssueViewer() {

    initStoreAndProvider();
    initColumnModel();
    initFilter();
    initTree();
    initWidget(uiBinder.createAndBindUi(this));

    tree.setCheckable(true);
    tree.setCheckStyle(CheckCascade.TRI);
    tree.setCheckNodes(CheckNodes.BOTH);

    fillQueryCardType();
    fillQueryIssue();
    fillCardTypeCb();
    fillCardStatusCb();
    addEventHandler();
  }

  private void initStoreAndProvider() {
    listStore =
        new ListStore<>(
            new ModelKeyProvider<RoomCardConfigDTO>() {

              @Override
              public String getKey(RoomCardConfigDTO item) {
                return item.getId();
              }
            });

    queryCardTypeCbStore =
        new ListStore<>(
            new ModelKeyProvider<String>() {

              @Override
              public String getKey(String item) {
                return item;
              }
            });

    queryIssueCbStore =
        new ListStore<>(
            new ModelKeyProvider<String>() {

              @Override
              public String getKey(String item) {
                return item;
              }
            });

    cardTypeCbStore =
        new ListStore<>(
            new ModelKeyProvider<CardType>() {

              @Override
              public String getKey(CardType item) {
                return item.name();
              }
            });

    cardTypeCbLabelProvider =
        new LabelProvider<CardType>() {

          @Override
          public String getLabel(CardType item) {
            return StringConverter.getCardType(item);
          }
        };

    cardGroupCbStore =
        new ListStore<>(
            new ModelKeyProvider<RoomCardGroupConfigDTO>() {

              @Override
              public String getKey(RoomCardGroupConfigDTO item) {
                return item.getId().toString();
              }
            });

    queryCardTypeCbLabelProvider =
        new LabelProvider<String>() {

          @Override
          public String getLabel(String item) {
            return item;
          }
        };

    queryIssueCbLabelProvider =
        new LabelProvider<String>() {

          @Override
          public String getLabel(String item) {
            return item;
          }
        };

    cardGroupCbLabelProvider =
        new LabelProvider<RoomCardGroupConfigDTO>() {

          @Override
          public String getLabel(RoomCardGroupConfigDTO item) {
            return item.getName();
          }
        };

    cardStatusCbStore =
        new ListStore<>(
            new ModelKeyProvider<CardStatus>() {

              @Override
              public String getKey(CardStatus item) {
                return item.name();
              }
            });

    cardStatusCbLabelProvider =
        new LabelProvider<CardStatus>() {

          @Override
          public String getLabel(CardStatus item) {
            return StringConverter.getCardStatus(item);
          }
        };

    treeStore =
        new TreeStore<>(
            new ModelKeyProvider<CardPermissionVO>() {

              @Override
              public String getKey(CardPermissionVO item) {
                return item.getId();
              }
            });

    treeValueProvider =
        new ValueProvider<CardPermissionVO, String>() {

          @Override
          public void setValue(CardPermissionVO object, String value) {}

          @Override
          public String getValue(CardPermissionVO object) {
            return object.getLocName();
          }

          @Override
          public String getPath() {
            return "treeValueProvider";
          }
        };

    listStore = new ListStore<>(roomCardConfigPropertyAccess.id());
  }

  private void initColumnModel() {
    List<ColumnConfig<RoomCardConfigDTO, ?>> ColumnConfigs = new ArrayList<>();

    ColumnConfig<RoomCardConfigDTO, String> abaColumn =
        new ColumnConfig<>(roomCardConfigPropertyAccess.aba(), 100, "卡片編號");
    ColumnConfigs.add(abaColumn);
    ColumnConfig<RoomCardConfigDTO, String> cardTypeColumn =
        new ColumnConfig<>(
            new ValueProvider<RoomCardConfigDTO, String>() {

              @Override
              public String getValue(RoomCardConfigDTO object) {
                return StringConverter.getCardType(object.getCardType());
              }

              @Override
              public void setValue(RoomCardConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "cardTypeColumn";
              }
            },
            65,
            "卡片種類");
    ColumnConfigs.add(cardTypeColumn);
    ColumnConfig<RoomCardConfigDTO, String> issuedColumn =
        new ColumnConfig<>(
            new ValueProvider<RoomCardConfigDTO, String>() {

              @Override
              public String getValue(RoomCardConfigDTO object) {
                return StringConverter.getYesOrNo(object.getIssued());
              }

              @Override
              public void setValue(RoomCardConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "issuedColumn";
              }
            },
            75,
            "是否已發卡");
    ColumnConfigs.add(issuedColumn);

    ColumnConfig<RoomCardConfigDTO, String> issueDateColumn =
        new ColumnConfig<>(
            new ValueProvider<RoomCardConfigDTO, String>() {

              @Override
              public String getValue(RoomCardConfigDTO object) {
                return StringConverter.getTimeyyyyMMdd(object.getIssueDate());
              }

              @Override
              public void setValue(RoomCardConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "issueDateColumn";
              }
            },
            80,
            "發卡日期");
    ColumnConfigs.add(issueDateColumn);
    ColumnConfig<RoomCardConfigDTO, String> cardStatusColumn =
        new ColumnConfig<>(
            new ValueProvider<RoomCardConfigDTO, String>() {

              @Override
              public String getValue(RoomCardConfigDTO object) {
                return StringConverter.getCardStatus(object.getCardStatus());
              }

              @Override
              public void setValue(RoomCardConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "cardStatusColumn";
              }
            },
            60,
            "卡片狀態");
    ColumnConfigs.add(cardStatusColumn);
    //發卡人資料:

    ColumnConfig<RoomCardConfigDTO, String> companyColumn =
        new ColumnConfig<>(roomCardConfigPropertyAccess.company(), 50, "公司");
    ColumnConfigs.add(companyColumn);

    ColumnConfig<RoomCardConfigDTO, String> NameColumn =
        new ColumnConfig<>(roomCardConfigPropertyAccess.name(), 50, "姓名");
    ColumnConfigs.add(NameColumn);

    ColumnConfig<RoomCardConfigDTO, String> jobTitleColumn =
        new ColumnConfig<>(roomCardConfigPropertyAccess.jobTitle(), 50, "職稱");
    ColumnConfigs.add(jobTitleColumn);

    ColumnConfig<RoomCardConfigDTO, String> startDateColumn =
        new ColumnConfig<>(
            new ValueProvider<RoomCardConfigDTO, String>() {

              @Override
              public String getValue(RoomCardConfigDTO object) {
                return StringConverter.getTimeyyyyMMddHHmmss(object.getStartDate());
              }

              @Override
              public void setValue(RoomCardConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return null;
              }
            },
            135,
            "實際可進入機房開始日期");

    ColumnConfigs.add(startDateColumn);

    ColumnConfig<RoomCardConfigDTO, String> endDateColumn =
        new ColumnConfig<>(
            new ValueProvider<RoomCardConfigDTO, String>() {

              @Override
              public String getValue(RoomCardConfigDTO object) {
                return StringConverter.getTimeyyyyMMddHHmmss(object.getEndDate());
              }

              @Override
              public void setValue(RoomCardConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "endDateColumn";
              }
            },
            135,
            "實際可進入機房結束日期");

    ColumnConfigs.add(endDateColumn);

    columnModel = new ColumnModel<>(ColumnConfigs);
  }

  private void addEventHandler() {
    tree.addCheckChangedHandler(
        new CheckChangedHandler<CardPermissionVO>() {
          @Override
          public void onCheckChanged(CheckChangedEvent<CardPermissionVO> event) {
            cardStatusComboBox.setValue(CardStatus.ENABLE);
          }
        });
  }

  private void initFilter() {

    abafilter =
        new StoreFilter<RoomCardConfigDTO>() {
          @Override
          public boolean select(
              Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item) {
            return true;
          }
        };
    cardTypefilter =
        new StoreFilter<RoomCardConfigDTO>() {
          @Override
          public boolean select(
              Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item) {
            return true;
          }
        };
    issuefilter =
        new StoreFilter<RoomCardConfigDTO>() {
          @Override
          public boolean select(
              Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item) {
            return true;
          }
        };
    listStore.addFilter(abafilter);
    listStore.addFilter(cardTypefilter);
    listStore.addFilter(issuefilter);
    listStore.setEnableFilters(true);
    GWT.log("listStore add filter");
  }

  public void initTree() {
    treeStore.clear();
    treeStore.add(root);
    presenter.getCardReaderLocation();
  }

  public void setPresenter(RoomCardIssuePresenter presenter) {
    this.presenter = presenter;
  }

  @UiHandler("grid")
  public void onGridSelect(RowClickEvent event) {
    RoomCardConfigDTO selectItem = grid.getSelectionModel().getSelectedItem();
    CardType selectCardType = selectItem.getCardType();
    setSettingFieldValue(selectItem, selectCardType);
    decideDateTimeFieldEnable(selectCardType);
    if (selectCardType != CardType.UNLIMITE) {
      tree.enable();
    }
    //填上卡片的卡機權限tree
    presenter.getRoomCardReaderMappingConfig(selectItem.getAba());
  }

  //TODO 做timer延遲，等輸入者0.5秒再filter
  @UiHandler("queryAba")
  public void onQueryAba(KeyUpEvent event) {
    Timer timer =
        new Timer() {
          @Override
          public void run() {}
        };
    timer.schedule(500);
    String aba = queryAba.getCurrentValue();
    if (aba == null || aba.equals("")) {
      GWT.log("aba == null");
      listStore.removeFilter(abafilter);
    } else {
      GWT.log("aba != null, aba: " + aba);

      listStore.removeFilter(abafilter);
      abafilter =
          new StoreFilter<RoomCardConfigDTO>() {
            @Override
            public boolean select(
                Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item) {
              return item.getAba().contains(aba);
            }
          };
      listStore.addFilter(abafilter);
    }
  }

  @UiHandler("queryCardTypeComboBox")
  public void onQueryCardType(SelectionEvent<String> event) {
    CardType cardType = StringConverter.getCardTpyeFromStr(event.getSelectedItem());

    if (cardType == null) {
      GWT.log("cardType: " + cardType);
      listStore.removeFilter(cardTypefilter);
    } else {
      GWT.log("cardType != null, cardType: " + cardType);
      listStore.removeFilter(cardTypefilter);
      cardTypefilter =
          new StoreFilter<RoomCardConfigDTO>() {
            @Override
            public boolean select(
                Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item) {
              return cardType == item.getCardType();
            }
          };
      listStore.addFilter(cardTypefilter);
    }
  }

  @UiHandler("queryIssueComboBox")
  public void onQueryIssue(SelectionEvent<String> event) {

    Boolean issue = StringConverter.getYesOrNoBoolean(event.getSelectedItem());
    if (issue == null) {
      GWT.log("issue == null, remove issueFilter");
      listStore.removeFilter(issuefilter);
    } else {
      listStore.removeFilter(issuefilter);
      issuefilter =
          new StoreFilter<RoomCardConfigDTO>() {
            @Override
            public boolean select(
                Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item) {
              return issue == item.getIssued();
            }
          };
      listStore.addFilter(issuefilter);
      GWT.log("issue != null,update issueFilter");
    }
  }

  @UiHandler("cardTypeComboBox")
  public void onCardTypeSelect(SelectionEvent<CardType> event) {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    CardType cardType = event.getSelectedItem();
    decideDateTimeFieldEnable(cardType);
    //控制權限tree可否設定
    if (cardType != CardType.UNLIMITE && aba.getText() != null) {
      cardGroupComboBox.disable();
      presenter.getRoomCardReaderMappingConfig(aba.getText());
      tree.enable();
    } else {
      tree.disable();
    }
    if (cardType == CardType.TEMPORARY && dto != null) {
      cardGroupComboBox.disable();
      startDate.setValue(dto.getCardStartDate());
      endDate.setValue(dto.getCardEndDate());
      actualStartDate.setValue(dto.getStartDate());
      actualStartTime.setValue(dto.getStartDate());
      actualEndDate.setValue(dto.getEndDate());
      actualEndTime.setValue(dto.getEndDate());
      expectedReturnDate.setValue(dto.getExpectedReturnDate());
      expectedReturnTime.setValue(dto.getExpectedReturnDate());
      returnDate.setValue(dto.getReturnDate());
      returnDate.setValue(dto.getReturnDate());
    }
    if (cardType == CardType.REGULAR && dto != null) {
      cardGroupComboBox.enable();
      startDate.setValue(dto.getCardStartDate());
      endDate.setValue(dto.getCardEndDate());
      actualStartDate.setValue(dto.getStartDate());
      actualEndDate.setValue(dto.getEndDate());
      expectedReturnDate.setValue(dto.getExpectedReturnDate());
      expectedReturnTime.setValue(dto.getExpectedReturnDate());
      returnDate.setValue(dto.getReturnDate());
      returnDate.setValue(dto.getReturnDate());
    }
    if (cardType == CardType.UNLIMITE) {
      for (CardPermissionVO vo : treeStore.getAll()) {
        tree.setChecked(vo, CheckState.CHECKED);
      }
    }
  }

  private void decideDateTimeFieldEnable(CardType cardType) {
    GWT.log("decideDateTimeFieldEnable cardType: " + cardType);
    if (cardType == CardType.TEMPORARY) {
      startDate.enable();
      endDate.enable();
      actualStartDate.enable();
      actualStartTime.enable();
      actualEndDate.enable();
      actualEndTime.enable();
      expectedReturnDate.enable();
      expectedReturnTime.enable();
      returnDate.enable();
      returnTime.enable();
    } else if (cardType == CardType.REGULAR) {
      GWT.log("in cardType == REGULAR");
      startDate.enable();
      endDate.enable();
      actualStartDate.enable();
      actualStartTime.disable();
      actualEndDate.enable();
      actualEndTime.disable();
      actualStartTime.setValue(StringConverter.getTime0000());
      actualEndTime.setValue(StringConverter.getTime0000());
      expectedReturnDate.enable();
      expectedReturnTime.enable();
      returnDate.enable();
      returnTime.enable();
    } else if (cardType == CardType.UNLIMITE) {
      startDate.clear();
      startDate.disable();
      endDate.clear();
      endDate.disable();
      actualStartDate.clear();
      actualStartDate.disable();
      actualStartTime.clear();
      actualStartTime.disable();
      actualEndDate.clear();
      actualEndDate.disable();
      actualEndTime.clear();
      actualEndTime.disable();
      expectedReturnDate.clear();
      expectedReturnDate.disable();
      expectedReturnTime.clear();
      expectedReturnTime.disable();
      returnDate.clear();
      returnDate.disable();
      returnTime.clear();
      returnTime.disable();
    }
  }

  @UiHandler("cardStatusComboBox")
  public void onCardStatusSelect(SelectionEvent<CardStatus> event) {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    CardStatus selectionItem = event.getSelectedItem();
    CardStatus currentValue = cardStatusComboBox.getCurrentValue();
    decideSettingFieldEnable(selectionItem);
    if (cardTypeComboBox.getCurrentValue() != null) {
      decideDateTimeFieldEnable(cardTypeComboBox.getCurrentValue());
    }
    if (currentValue != CardStatus.ENABLE && currentValue != CardStatus.LOGOUT) {
      setSettingFieldValue(dto, dto.getCardType());
    }
    if (currentValue != CardStatus.ENABLE) {
      for (CardPermissionVO vo : treeStore.getAll()) {
        tree.setChecked(vo, CheckState.UNCHECKED);
      }
    } else if (currentValue == CardStatus.ENABLE && dto != null) {
      setSettingFieldValue(dto, dto.getCardType());
      presenter.getRoomCardReaderMappingConfig(aba.getText());
    }
    if (currentValue != CardStatus.ENABLE) {
      startDate.disable();
      endDate.disable();
      actualStartDate.disable();
      actualEndDate.disable();
      expectedReturnDate.disable();
      expectedReturnTime.disable();
      returnDate.disable();
      returnTime.disable();
    }
  }

  private void setSettingFieldValue(RoomCardConfigDTO dto, CardType cardType) {
    aba.setText(dto.getAba());
    issued.setText(StringConverter.getYesOrNo(dto.getIssued()));
    cardTypeComboBox.setValue(cardType);
    if (cardType == CardType.REGULAR) {
      cardGroupComboBox.enable();
      if (cardGroupCbStore.getAll() != null && cardGroupCbStore.getAll().size() > 0) {
        for (RoomCardGroupConfigDTO groupDto : cardGroupCbStore.getAll()) {
          if (dto.getGroupName().equals(groupDto.getName())) {
            cardGroupComboBox.setValue(groupDto);
          }
        }
      }
    } else {
      cardGroupComboBox.disable();
    }
    cardStatusComboBox.setValue(dto.getCardStatus());
    name.setValue(dto.getName());
    idNo.setValue(dto.getIdNo());
    company.setValue(dto.getCompany());
    employeeNo.setValue(dto.getEmployeeNo());
    jobTitle.setValue(dto.getJobTitle());
    tel.setValue(dto.getTel());
    mobile.setValue(dto.getMobile());
    startDate.setValue(dto.getCardStartDate());
    endDate.setValue(dto.getCardEndDate());
    actualStartDate.setValue(dto.getStartDate());
    actualStartTime.setValue(dto.getStartDate());
    actualEndDate.setValue(dto.getEndDate());
    actualEndTime.setValue(dto.getEndDate());
    expectedReturnDate.setValue(dto.getExpectedReturnDate());
    expectedReturnTime.setValue(dto.getExpectedReturnDate());
    returnDate.setValue(dto.getReturnDate());
    returnTime.setValue(dto.getReturnDate());
    memo.setValue(dto.getMemo());
  }

  private void decideSettingFieldEnable(CardStatus cardStatus) {
    if (cardStatus == CardStatus.ENABLE) {
      enableAllSettingField();
    } else if (cardStatus == CardStatus.LOGOUT) {
      disableAllSettingField();
      tree.disable();
    } else {
      enableAllSettingField();
      tree.disable();
    }
  }

  private void disableAllSettingField() {
    cardTypeComboBox.disable();
    cardGroupComboBox.clear();
    cardGroupComboBox.disable();
    name.clear();
    name.disable();
    idNo.clear();
    idNo.disable();
    company.clear();
    company.disable();
    employeeNo.clear();
    employeeNo.disable();
    jobTitle.clear();
    jobTitle.disable();
    tel.clear();
    tel.disable();
    mobile.clear();
    mobile.disable();
    roleId.clear();
    roleId.disable();
    startDate.clear();
    startDate.disable();
    endDate.clear();
    endDate.disable();
    actualStartDate.disable();
    actualStartTime.disable();
    actualEndDate.disable();
    actualEndTime.disable();
    expectedReturnDate.disable();
    expectedReturnTime.disable();
    returnDate.disable();
    returnTime.disable();
  }

  private void enableAllSettingField() {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    cardTypeComboBox.enable();
    if (dto != null && cardTypeComboBox.getCurrentValue() != CardType.REGULAR) {
      cardGroupComboBox.disable();
    } else {
      cardGroupComboBox.enable();
    }
    cardStatusComboBox.enable();
    name.enable();
    idNo.enable();
    company.enable();
    employeeNo.enable();
    jobTitle.enable();
    tel.enable();
    mobile.enable();
    roleId.enable();
    startDate.enable();
    endDate.enable();
    actualStartDate.enable();
    actualStartTime.enable();
    actualEndDate.enable();
    actualEndTime.enable();
    expectedReturnDate.enable();
    expectedReturnTime.enable();
    returnDate.enable();
    returnTime.enable();
    memo.enable();

    tree.enable();
  }

  /** 卡片立即發卡 */
  @UiHandler("issueImmediately")
  public void onIssueImmediately(SelectEvent event) {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    if (dto == null) {
      Info.display("發卡失敗", "請選擇一張卡片");
    }
    if (checkIssueCardValue(true)) {
      RoomCardIssueParam issueParam = new RoomCardIssueParam();
      RoomCardConfigDTO issueCard = new RoomCardConfigDTO();
      issueCard = getSettingFieldRoomCardConfig();
      issueCard.setId(dto.getId());
      issueCard.setAba(dto.getAba());
      issueParam.setRoomCardConfigDto(issueCard);

      Map<String, Boolean> addCardMap = getTreePermissionMap();
      issueParam.setCardReaderAddCardMap(addCardMap);
      GWT.log("issue.getText(): " + issue.getText());
      if (issued.getText().equals("是")) {
        showForceIssuingCardDialog(issueParam);
      } else {
        presenter.issueRoomCardImmediately(issueParam);
      }
    }
  }

  /** 卡片發卡，須先檢查卡片為啟用狀態 */
  @UiHandler("issue")
  public void onIssue(SelectEvent event) {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    if (dto == null) {
      Info.display("發卡失敗", "請選擇一張卡片");
    }
    if (checkIssueCardValue(false)) {
      RoomCardIssueParam issueParam = new RoomCardIssueParam();
      RoomCardConfigDTO issueCard = new RoomCardConfigDTO();
      issueCard = getSettingFieldRoomCardConfig();
      issueCard.setId(dto.getId());
      issueCard.setAba(dto.getAba());
      issueParam.setRoomCardConfigDto(issueCard);

      Map<String, Boolean> addCardMap = getTreePermissionMap();
      issueParam.setCardReaderAddCardMap(addCardMap);
      presenter.issueRoomCard(issueParam);
    }
  }

  @UiHandler("modify")
  public void onModify(SelectEvent event) {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    if (dto == null) {
      Info.display("發卡失敗", "請選擇一張卡片");
    }
    if (checkModifyCardValue()) {
      RoomCardIssueParam issueParam = new RoomCardIssueParam();
      RoomCardConfigDTO updateCard = new RoomCardConfigDTO();
      updateCard = getSettingFieldRoomCardConfig();
      updateCard.setId(dto.getId());
      updateCard.setAba(dto.getAba());
      updateCard.setIssued(dto.getIssued());
      issueParam.setRoomCardConfigDto(updateCard);

      Map<String, Boolean> addCardMap = getTreePermissionMap();
      issueParam.setCardReaderAddCardMap(addCardMap);

      if (updateCard.getCardStatus() == CardStatus.LOGOUT) {
        showLogoutCardDialog(issueParam);
      } else {
        presenter.updateRoomCardConfig(issueParam);
      }
    }
  }

  @UiHandler("expandAll")
  public void expandAll(SelectEvent event) {
    tree.expandAll();
  }

  @UiHandler("collapseAll")
  public void collapseAll(SelectEvent event) {
    tree.collapseAll();
  }

  @UiHandler("createCard")
  public void onCreateCard(SelectEvent event) {
    if (createCardDialog == null) {
      initCreateCardDialog();
    }
    createCardDialog.show();
  }

  //發卡時檢查必填欄位的值
  private Boolean checkIssueCardValue(Boolean issueImmediately) {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    if (aba.getText() == null || aba.getText().length() != 10) {
      Info.display("發卡失敗", "請選擇卡片");
      return false;
    }
    if (!issued.getText().equals("否") && issueImmediately == false) {
      Info.display("發卡失敗", "此卡片已發卡");
      return false;
    }
    if (cardTypeComboBox.getValue() == null) {
      Info.display("發卡失敗", "需選擇卡片種類");
      return false;
    }
    if (cardStatusComboBox.getCurrentValue() != CardStatus.ENABLE) {
      Info.display("發卡失敗", "卡片狀態須為啟用狀態");
      return false;
    }
    if (dto != null && dto.getCardType() != CardType.UNLIMITE) {
      if (actualStartDate.getValue() == null
          || actualStartTime.getValue() == null
          || actualEndDate.getValue() == null
          || actualEndTime.getValue() == null) {
        Info.display("發卡失敗", "實際可進機房日期時間不能為空");
        return false;
      }
      if (expectedReturnDate.getValue() == null || expectedReturnTime.getValue() == null) {
        Info.display("發卡失敗", "預計歸還日期不能為空");
        return false;
      }
    }
    if (dto != null
        && dto.getCardType() == CardType.REGULAR
        && StringConverter.getTimeyyyyMMdd(actualStartDate.getValue())
            .equals(StringConverter.getTimeyyyyMMdd(actualEndDate.getValue()))) {
      Info.display("發卡失敗", "實際可進機房起訖日期不能相同");
      return false;
    }
    if (actualStartDate.getValue().after(actualEndDate.getValue())) {
      Info.display("發卡失敗", "起始日期不能大於結束日期");
      return false;
    }
    Date now = new Date();

    Date actualEnd =
        StringConverter.getTimeFromDateAndTime(
            actualEndDate.getCurrentValue(), actualEndTime.getCurrentValue());
    if (actualEnd.before(now)) {
      Info.display("發卡失敗", "實際可進機房日期不可在當下日期之前");
      return false;
    }
    if (endDate.getValue().before(now)) {
      Info.display("發卡失敗", "有效日期不可在當下日期之前");
      return false;
    }

    return true;
  }

  //取得當下設定的卡片狀態RoomCardConfigDTO
  private RoomCardConfigDTO getSettingFieldRoomCardConfig() {

    CardType cardType = cardTypeComboBox.getCurrentValue();
    RoomCardConfigDTO dto = new RoomCardConfigDTO();

    dto.setCardType(cardTypeComboBox.getCurrentValue());
    if (cardType == CardType.UNLIMITE) {
      dto.setStartDate(new Date());
    } else if (actualStartDate.getValue() != null && actualStartTime.getValue() != null) {
      GWT.log(
          "actualStartDate.getValue(): "
              + actualStartDate.getValue()
              + "actualStartDate.getCurrentValue(): "
              + actualStartDate.getCurrentValue());
      dto.setStartDate(
          StringConverter.getTimeFromDateAndTime(
              actualStartDate.getCurrentValue(), actualStartTime.getCurrentValue()));
    }
    if (cardType == CardType.UNLIMITE) {
      dto.setEndDate(StringConverter.getAfter10yearTime());
    } else if (actualEndDate.getCurrentValue() != null && actualEndTime.getCurrentValue() != null) {
      GWT.log(
          "actualStartDate.getValue(): "
              + actualEndDate.getCurrentValue()
              + "actualStartDate.getCurrentValue(): "
              + actualEndDate.getCurrentValue());
      dto.setEndDate(
          StringConverter.getTimeFromDateAndTime(
              actualEndDate.getCurrentValue(), actualEndTime.getCurrentValue()));
    }
    if (cardType != CardType.UNLIMITE
        && expectedReturnDate.getValue() != null
        && expectedReturnTime.getValue() != null) {
      dto.setExpectedReturnDate(
          StringConverter.getTimeFromDateAndTime(
              expectedReturnDate.getCurrentValue(), expectedReturnTime.getCurrentValue()));
    }
    if (cardType != CardType.UNLIMITE
        && returnDate.getValue() != null
        && returnTime.getValue() != null) {
      dto.setExpectedReturnDate(
          StringConverter.getTimeFromDateAndTime(
              returnDate.getCurrentValue(), returnTime.getCurrentValue()));
    }

    dto.setCardStatus(cardStatusComboBox.getCurrentValue());
    dto.setMemo(memo.getText());
    dto.setCompany(company.getText());
    dto.setName(name.getText());
    dto.setIdNo(idNo.getText());
    dto.setEmployeeNo(employeeNo.getText());
    dto.setJobTitle(jobTitle.getText());
    dto.setTel(tel.getText());
    dto.setMobile(mobile.getText());
    dto.setCardType(cardTypeComboBox.getCurrentValue());
    if (cardTypeComboBox.getCurrentValue() == CardType.REGULAR
        && cardGroupComboBox.getCurrentValue() != null) {
      dto.setGroupName(cardGroupComboBox.getCurrentValue().getName());
    }
    dto.setRoleId(roleId.getText());
    dto.setIssueDate(new Date());
    dto.setCardStartDate(startDate.getCurrentValue());
    dto.setCardEndDate(endDate.getCurrentValue());

    return dto;
  }

  //取得當下tree的權限Map<key, value>,key=卡機deviceName(String), value=是否有權限(Boolean)
  private Map<String, Boolean> getTreePermissionMap() {
    Map<String, Boolean> result = new HashMap<>();
    for (CardPermissionVO vo : getTreeCheckedVO()) {
      result.put(vo.getDeviceName(), true);
    }
    for (CardPermissionVO vo : getTreeUncheckVO()) {
      result.put(vo.getDeviceName(), false);
    }
    return result;
  }

  /** 取得Tree當下的卡機checked選項 */
  private List<CardPermissionVO> getTreeCheckedVO() {
    List<CardPermissionVO> checkedTreeVoList = new ArrayList<>(tree.getCheckedSelection());
    List<CardPermissionVO> removeList = new ArrayList<>();
    Iterator<CardPermissionVO> checkTreeIt = checkedTreeVoList.iterator();
    while (checkTreeIt.hasNext()) {
      CardPermissionVO vo = checkTreeIt.next();
      if (vo.getDeviceName() == null) {
        //改成先暫存一個移除List 再一次移除
        removeList.add(vo);
      }
    }
    GWT.log("get allchecked tree VO list: " + checkedTreeVoList);
    checkedTreeVoList.removeAll(removeList);
    return checkedTreeVoList;
  }

  /** 取得Tree當下的卡機unchecked選項 */
  private List<CardPermissionVO> getTreeUncheckVO() {
    List<CardPermissionVO> allTreeVoList = new ArrayList<>(treeStore.getAll());
    List<CardPermissionVO> checkedTreeVoList = new ArrayList<>(tree.getCheckedSelection());
    List<CardPermissionVO> removeList = new ArrayList<>();
    Iterator<CardPermissionVO> allTreeIt = allTreeVoList.iterator();
    //移除非機房的節點
    while (allTreeIt.hasNext()) {
      CardPermissionVO vo = allTreeIt.next();
      if (vo.getDeviceName() == null) {
        removeList.add(vo);
      }
    }

    GWT.log("after remove allTreeVoList: " + allTreeVoList);
    //移除已勾選之卡機，取得所有沒勾選卡機
    allTreeVoList.removeAll(removeList);
    allTreeVoList.removeAll(checkedTreeVoList);

    GWT.log("get all unchecked tree VO list: " + allTreeVoList);
    return allTreeVoList;
  }

  private Boolean checkModifyCardValue() {
    RoomCardConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    if (aba.getText() == null || aba.getText().length() != 10) {
      Info.display("修改失敗", "請選擇卡片");
      return false;
    }
    if (!issued.getText().equals("是")) {
      Info.display("修改失敗", "此卡片尚未發卡");
      return false;
    }
    if (cardTypeComboBox.getCurrentValue() == null) {
      Info.display("修改失敗", "需選擇卡片種類");
      return false;
    }
    if (dto != null
        && dto.getCardStatus() == CardStatus.ENABLE
        && dto.getCardType() != CardType.UNLIMITE) {
      if (actualStartDate.getCurrentValue() == null
          || actualStartTime.getCurrentValue() == null
          || actualEndDate.getCurrentValue() == null
          || actualEndTime.getCurrentValue() == null) {
        Info.display("修改失敗", "實際可進機房日期時間不能為空");
        return false;
      }
      if (expectedReturnDate.getValue() == null || expectedReturnTime.getValue() == null) {
        Info.display("修改失敗", "預計歸還日期不能為空");
        return false;
      }
    }
    if (dto != null
        && dto.getCardType() == CardType.REGULAR
        && StringConverter.getTimeyyyyMMdd(actualStartDate.getValue())
            .equals(StringConverter.getTimeyyyyMMdd(actualEndDate.getValue()))) {
      Info.display("修改失敗", "實際可進機房起訖日期不能相同");
      return false;
    }
    if (actualStartDate.getValue().after(actualEndDate.getValue())) {
      Info.display("修改失敗", "起始日期不能大於結束日期");
      return false;
    }
    return true;
  }

  public void fillTreeData(List<RoomCardPermissionTreeDTO> response) {
    for (RoomCardPermissionTreeDTO dto : response) {
      if (dto.getId() == null) {
        continue;
      }
      treeStore.add(root, new CardPermissionVO("" + dto.getId(), dto.getLocationName(), null));
      CardPermissionVO parent = treeStore.findModelWithKey("" + dto.getId());
      if (dto.getCardReaderLocation().values() != null
          && dto.getCardReaderLocation().values().size() > 0) {
        for (Entry<String, String> entry : dto.getCardReaderLocation().entrySet()) {
          treeStore.add(
              parent,
              new CardPermissionVO(dto.getId() + entry.getKey(), entry.getKey(), entry.getValue()));
        }
      }
    }
  }

  public void fillTreeCheckValue(List<RoomCardReaderMappingConfigDTO> cardReaderMappingDtos) {
    //初始化
    for (CardPermissionVO vo : tree.getCheckedSelection()) {
      tree.setChecked(vo, CheckState.UNCHECKED);
    }
    List<String> cardReaderDeviceName = new ArrayList<>();
    for (RoomCardReaderMappingConfigDTO dto : cardReaderMappingDtos) {
      if (dto.getLoginCardReader()) {
        cardReaderDeviceName.add(dto.getReaderId());
      }
    }
    for (CardPermissionVO vo : treeStore.getAll()) {
      if (vo.getDeviceName() != null
          && cardReaderDeviceName != null
          && cardReaderDeviceName.contains(vo.getDeviceName())) {
        GWT.log("tree node: " + vo);
        tree.setChecked(vo, CheckState.CHECKED);
      }
    }
  }

  /** 機房門禁權限tree VO */
  public class CardPermissionVO {
    private String id;
    private String locName;
    private String deviceName;

    public CardPermissionVO(String id, String locName, String deviceName) {
      this.id = id;
      this.locName = locName;
      this.deviceName = deviceName;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getLocName() {
      return locName;
    }

    public void setLocName(String locName) {
      this.locName = locName;
    }

    public String getDeviceName() {
      return deviceName;
    }

    public void setDeviceName(String deviceName) {
      this.deviceName = deviceName;
    }

    @Override
    public String toString() {
      return "CardPermissionVO [id="
          + id
          + ", locName="
          + locName
          + ", deviceName="
          + deviceName
          + "]";
    }
  }

  private void initCreateCardDialog() {
    createCardDialog = new Dialog();
    createCardDialog.setHeading("新增卡片");
    createCardDialog.setHeight(200);
    createCardDialog.setWidth(250);
    createCardDialog.setModal(true);
    final RoomCardCreateWidget widget = new RoomCardCreateWidget();
    createCardDialog.add(widget);

    TextButton createButton = new TextButton();
    createButton.setText("新增");
    createButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            //TODO 檢查卡號是否重複 沒重複 presenter新增
            String newAba = widget.aba.getCurrentValue();
            CardType newCardType = widget.cardTypeComboBox.getCurrentValue();
            if (newAba == null || newAba.length() != 10) {
              Info.display("新增卡片失敗", "卡號需為10碼");
              return;
            }
            if (newCardType == null) {
              Info.display("新增卡片失敗", "需選擇卡片種類");
              return;
            }
            Boolean abaDuplicate = false;
            for (RoomCardConfigDTO dto : listStore.getAll()) {
              if (newAba.equals(dto.getAba())) {
                abaDuplicate = true;
              }
            }
            if (abaDuplicate) {
              Info.display("新增卡片失敗", "卡號已重複");
              return;
            }
            RoomCardConfigDTO dto = new RoomCardConfigDTO();
            dto.setAba(newAba);
            dto.setCardType(newCardType);
            presenter.createRoomCardConfig(dto);
          }
        });

    TextButton cancel = new TextButton();
    cancel.setText("取消");
    cancel.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            createCardDialog.hide();
          }
        });
    createCardDialog.getButtonBar().clear();
    createCardDialog.getButtonBar().add(createButton);
    createCardDialog.getButtonBar().add(cancel);

    createCardDialog.addHideHandler(
        new HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            createCardDialog = null;
          }
        });
  }

  public void showAddCardFailDialog(String failMsg) {
    if (failMsg != null && addCardFailDialog == null) {
      GWT.log("showAddCardFailDialog.");
      initAddCardFailDialog(failMsg);
    }
    addCardFailDialog.show();
  }

  private void showLogoutCardDialog(RoomCardIssueParam param) {
    if (logoutCardDialog == null) {
      initLogoutCardDialog(param);
    }
    logoutCardDialog.show();
  }

  private void showForceIssuingCardDialog(RoomCardIssueParam param) {
    if (forceIssuingCardDialog == null) {
      initForceIssuingCardDialog(param);
    }
    forceIssuingCardDialog.show();
  }

  private void initAddCardFailDialog(String failMsg) {
    addCardFailDialog = new Dialog();
    addCardFailDialog.setHeading("失敗通知");
    addCardFailDialog.setHeight(160);
    addCardFailDialog.setWidth(200);
    addCardFailDialog.setModal(true);
    Label label = new Label();
    StringBuilder failMessage = new StringBuilder();
    if (failMsg != null && !failMsg.isEmpty()) {
      GWT.log("in create failList Message.");
      label.setHeight("1");
      label.setWidth("1");
      failMessage.append(failMsg);
      label.setText(failMessage.toString());
      addCardFailDialog.add(label);
    }

    TextButton cancel = new TextButton();
    cancel.setText("確定");
    cancel.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            addCardFailDialog.hide();
          }
        });
    addCardFailDialog.getButtonBar().clear();
    addCardFailDialog.getButtonBar().add(cancel);

    addCardFailDialog.addHideHandler(
        new HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            addCardFailDialog = null;
          }
        });
  }

  private void initLogoutCardDialog(RoomCardIssueParam param) {
    logoutCardDialog = new Dialog();
    logoutCardDialog.setHeading("註銷卡片");
    logoutCardDialog.setHeight(120);
    logoutCardDialog.setWidth(150);
    logoutCardDialog.setModal(true);
    Label label = new Label();
    label.setWidth("1");
    label.setHeight("1");
    label.setText("確定註銷卡片嗎?註銷後將無法進行任何操作。");
    logoutCardDialog.add(label);

    TextButton createButton = new TextButton();
    createButton.setText("確定");
    createButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            presenter.updateRoomCardConfig(param);
          }
        });

    TextButton cancel = new TextButton();
    cancel.setText("取消");
    cancel.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            logoutCardDialog.hide();
          }
        });
    logoutCardDialog.getButtonBar().clear();
    logoutCardDialog.getButtonBar().add(createButton);
    logoutCardDialog.getButtonBar().add(cancel);

    logoutCardDialog.addHideHandler(
        new HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            logoutCardDialog = null;
          }
        });
  }

  public void hideCreateDialog() {
    if (createCardDialog != null) {
      createCardDialog.hide();
    }
  }

  public void hideForceIssuingDialog() {
    if (forceIssuingCardDialog != null) {
      forceIssuingCardDialog.hide();
    }
  }

  private void initForceIssuingCardDialog(RoomCardIssueParam param) {
    forceIssuingCardDialog = new Dialog();
    forceIssuingCardDialog.setHeading("卡片已發卡警告");
    forceIssuingCardDialog.setHeight(120);
    forceIssuingCardDialog.setWidth(150);
    forceIssuingCardDialog.setModal(true);
    Label label = new Label();
    label.setWidth("1");
    label.setHeight("1");
    label.setText("此卡片已發過卡，確定再次發卡?");
    forceIssuingCardDialog.add(label);

    TextButton createButton = new TextButton();
    createButton.setText("確定");
    createButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            presenter.issueRoomCardImmediately(param);
          }
        });

    TextButton cancel = new TextButton();
    cancel.setText("取消");
    cancel.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            forceIssuingCardDialog.hide();
          }
        });
    forceIssuingCardDialog.getButtonBar().clear();
    forceIssuingCardDialog.getButtonBar().add(createButton);
    forceIssuingCardDialog.getButtonBar().add(cancel);

    forceIssuingCardDialog.addHideHandler(
        new HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            forceIssuingCardDialog = null;
          }
        });
  }

  public void fillRoomCardConfig(List<RoomCardConfigDTO> result) {
    if (result == null) {
      GWT.log("fillRoomCardConfig result is null");
    } else {
      GWT.log("fillRoomCardConfig result.size:" + result.size());
    }

    listStore.clear();
    listStore.addAll(result);
  }

  private void fillQueryCardType() {
    queryCardTypeCbStore.add("全部");
    queryCardTypeCbStore.add("定期卡");
    queryCardTypeCbStore.add("臨時卡");
    queryCardTypeCbStore.add("無限卡");
    queryCardTypeComboBox.setValue(queryCardTypeCbStore.get(0));
  }

  private void fillQueryIssue() {
    queryIssueCbStore.add("全部");
    queryIssueCbStore.add("是");
    queryIssueCbStore.add("否");
    queryIssueComboBox.setValue(queryIssueCbStore.get(0));
  }

  public void fillRoomCardGroup(List<RoomCardGroupConfigDTO> cardGroupList) {
    cardGroupCbStore.clear();
    cardGroupCbStore.addAll(cardGroupList);
  }

  private void fillCardTypeCb() {
    cardTypeCbStore.add(CardType.REGULAR);
    cardTypeCbStore.add(CardType.TEMPORARY);
    cardTypeCbStore.add(CardType.UNLIMITE);

    cardTypeComboBox.setValue(cardTypeCbStore.get(0));
    decideDateTimeFieldEnable(cardTypeCbStore.get(0));
  }

  private void fillCardStatusCb() {
    cardStatusCbStore.add(CardStatus.ENABLE);
    cardStatusCbStore.add(CardStatus.DISABLE);
    cardStatusCbStore.add(CardStatus.RETURN);
    cardStatusCbStore.add(CardStatus.LOST);
    cardStatusCbStore.add(CardStatus.BLACKLIST);
    cardStatusCbStore.add(CardStatus.LOGOUT);
  }

  interface RoomCardConfigPropertyAccess extends PropertyAccess<RoomCardConfigDTO> {
    ModelKeyProvider<RoomCardConfigDTO> id();

    ValueProvider<RoomCardConfigDTO, String> aba();

    ValueProvider<RoomCardConfigDTO, String> memo();

    ValueProvider<RoomCardConfigDTO, String> company();

    ValueProvider<RoomCardConfigDTO, String> name();

    ValueProvider<RoomCardConfigDTO, String> idNo();

    ValueProvider<RoomCardConfigDTO, String> employeeNo();

    ValueProvider<RoomCardConfigDTO, String> jobTitle();

    ValueProvider<RoomCardConfigDTO, String> tel();

    ValueProvider<RoomCardConfigDTO, String> mobile();

    ValueProvider<RoomCardConfigDTO, String> groupName();

    ValueProvider<RoomCardConfigDTO, String> roleId();
  }
}
