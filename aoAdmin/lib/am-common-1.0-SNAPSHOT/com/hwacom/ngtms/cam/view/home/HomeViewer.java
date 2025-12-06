/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.images.AmImages;
import com.hwacom.ngtms.cam.presenter.home.HomePresenter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
import com.hwacom.ngtms.common.shared.dto.RoleDTO;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class HomeViewer extends Composite {

  private static Logger logger = Logger.getLogger("HomeViewer");
  private static HomeUiBinder uiBinder = GWT.create(HomeUiBinder.class);

  @SuppressWarnings("unused")
  private static final Messages messages = GWT.create(Messages.class);

  private static final AmImages allImages = GWT.create(AmImages.class);

  interface HomeUiBinder extends UiBinder<Widget, HomeViewer> {}

  private HomePresenter presenter;
  private List<FunctionPermissionDTO> functionPermissionList;
  private List<FunctionPermissionDTO> userFunctionPermissionList = new ArrayList<>();
  private Timer changeHomeImageTimer;
  private int homeImageNumber = 0;
  @UiField TabPanel mainTabPanel;
  @UiField TitleView homeTitleView;
  @UiField Image homeImage;

  public HomeViewer() {
    this.initWidget(uiBinder.createAndBindUi(this));
    presenter = new HomePresenter(this);
    createShowHomeImageTimer();
  }

  public void init() {
    int num = mainTabPanel.getWidgetCount();
    for (int i = 0; i < num; i++) {
      mainTabPanel.remove(0);
    }
    //mainTabPanel.add(new Label(" "),new TabItemConfig("Test Tab", false));
    if (functionPermissionList != null) functionPermissionList.clear();
    userFunctionPermissionList.clear();
    presenter.getFunctionPermissions();
    homeTitleView.init();
    //mainTabPanel.hide();
    mainTabPanel.add(new Label(""), new TabItemConfig("test", false));
    Timer clockTimer =
        new Timer() {
          @Override
          public void run() {
            //mainTabPanel.show();
            mainTabPanel.remove(0);
          }
        };
    clockTimer.schedule(500);
  }

  private void createShowHomeImageTimer() {
    changeHomeImageTimer =
        new Timer() {
          @Override
          public void run() {
            changeHomeImage();
          }
        };
    changeHomeImageTimer.scheduleRepeating(60000);
  }

  private void changeHomeImage() {
    homeImageNumber++;
    if (homeImageNumber >= 3) homeImageNumber = 0;
    if (homeImageNumber == 0) {
      homeImage.setResource(AmImages.INSTANCE.homeImage01());
    }
    if (homeImageNumber == 1) {
      homeImage.setResource(AmImages.INSTANCE.homeImage02());
    }
    if (homeImageNumber == 2) {
      homeImage.setResource(AmImages.INSTANCE.homeImage03());
    }
  }

  private void initMainTab(List<FunctionPermissionDTO> userFunctionPermissionList) {
    List<FunctionPermissionDTO> rootList = new ArrayList<>();
    List<FunctionPermissionDTO> itemList = new ArrayList<>();
    if (functionPermissionList != null) {
      for (FunctionPermissionDTO dto : functionPermissionList) {
        if (1 == dto.getLevel()) {
          rootList.add(dto);
        }
      }
    }
    if (userFunctionPermissionList != null) {
      for (FunctionPermissionDTO dto : userFunctionPermissionList) {
        if (2 == dto.getLevel()) {
          itemList.add(dto);
        }
      }
    }
    Collections.sort(rootList);
    Collections.sort(itemList);
    logger.info("rootList size = " + rootList.size());
    logger.info("itemList size = " + itemList.size());
    if (rootList != null && rootList.size() > 0) {
      for (FunctionPermissionDTO dto : rootList) {
        List<ImageItem> itemImageList = new ArrayList<>();
        for (FunctionPermissionDTO subDto : itemList) {
          if (dto.getId().equals(subDto.getParentId())) {
            itemImageList.add(genImageItem(subDto));
          }
        }
        logger.info("name = " + dto.getName() + "; itemImageList size = " + itemImageList.size());
        this.addMainTab(dto.getName(), itemImageList);
      }
    }
  }

  public void addMainTab(String name, List<ImageItem> itemList) {
    TabItemConfig config = new TabItemConfig(name, false);
    SafeHtmlBuilder builder = new SafeHtmlBuilder();
    //String html = "<span style='font-size:20px;font:bold;'>"+ name+"</span>";
    String html = name;
    builder.appendHtmlConstant(html);
    config.setHTML(builder.toSafeHtml());
    GroupMenuItem groupItem = new GroupMenuItem(name);
    groupItem.add(itemList);
    mainTabPanel.add(groupItem, config);
  }

  public void setPresenter(HomePresenter presenter) {
    this.presenter = presenter;
  }

  public void initFunctionPermission(List<FunctionPermissionDTO> result) {
    logger.info(
        "HomeViewer initFunctionPermission result :"
            + (result != null ? result.size() : " is null"));
    functionPermissionList = result;
  }

  public void initUser(UserDTO user) {
    logger.info(
        "HomeViewer initUser UserDTO :" + (user != null ? user.toInfoString() : " is null"));
    if (user == null) return;

    Set<FunctionPermissionDTO> roleFunctionPermissionSet = new HashSet<>();
    for (RoleDTO roleDto : user.getRoles()) {
      for (FunctionPermissionDTO functionPermission : roleDto.getFunctionPermissions()) {
        roleFunctionPermissionSet.add(functionPermission);
      }
    }
    for (FunctionPermissionDTO functionPermissionDto : functionPermissionList) {
      for (FunctionPermissionDTO roleFunctionPermissionDto : roleFunctionPermissionSet) {
        if (functionPermissionDto.getId().equals(roleFunctionPermissionDto.getId())) {
          userFunctionPermissionList.add(functionPermissionDto);
          break;
        }
      }
    }

    logger.info("userFunctionPermissionList size = " + userFunctionPermissionList.size());
    initMainTab(userFunctionPermissionList);
  }

  private ImageItem genImageItem(FunctionPermissionDTO subDto) {
    ImageItem imageItem = new ImageItem(subDto);
    /* 資料收集 */
    if ("EED".equals(subDto.getId())) {
      imageItem.setImage(allImages.EED(), allImages.EEDOn());
    }
    if ("ETS".equals(subDto.getId())) {
      imageItem.setImage(allImages.ETS(), allImages.ETSOn());
    }
    if ("IID".equals(subDto.getId())) {
      imageItem.setImage(allImages.IID(), allImages.IIDOn());
    }
    if ("LSS".equals(subDto.getId())) {
      imageItem.setImage(allImages.LSS(), allImages.LSSOn());
    }
    if ("NDS".equals(subDto.getId())) {
      imageItem.setImage(allImages.NDS(), allImages.NDSOn());
    }
    if ("PDCP".equals(subDto.getId())) {
      imageItem.setImage(allImages.PDCP(), allImages.PDCPOn());
    }
    if ("QLDS".equals(subDto.getId())) {
      imageItem.setImage(allImages.QLDS(), allImages.QLDSOn());
    }
    if ("RD".equals(subDto.getId())) {
      imageItem.setImage(allImages.RD(), allImages.RDOn());
    }
    if ("TEM".equals(subDto.getId())) {
      imageItem.setImage(allImages.TEM(), allImages.TEMOn());
    }
    if ("VDS".equals(subDto.getId())) {
      imageItem.setImage(allImages.VD(), allImages.VDOn());
    }
    if ("VI".equals(subDto.getId())) {
      imageItem.setImage(allImages.VI(), allImages.VIOn());
    }
    if ("WD".equals(subDto.getId())) {
      imageItem.setImage(allImages.WD(), allImages.WDOn());
    }

    /* 資料收集 */
    if ("CMS".equals(subDto.getId())) {
      imageItem.setImage(allImages.CMS(), allImages.CMSOn());
    }
    if ("CSLS".equals(subDto.getId())) {
      imageItem.setImage(allImages.CSLS(), allImages.CSLSOn());
    }
    if ("LCS".equals(subDto.getId())) {
      imageItem.setImage(allImages.LCS(), allImages.LCSOn());
    }
    if ("RGS".equals(subDto.getId())) {
      imageItem.setImage(allImages.RGS(), allImages.RGSOn());
    }
    if ("RMS".equals(subDto.getId())) {
      imageItem.setImage(allImages.RMS(), allImages.RMSOn());
    }
    if ("SCS".equals(subDto.getId())) {
      imageItem.setImage(allImages.SCS(), allImages.SCSOn());
    }
    if ("TTS".equals(subDto.getId())) {
      imageItem.setImage(allImages.TTS(), allImages.TTSOn());
    }
    if ("WIS".equals(subDto.getId())) {
      imageItem.setImage(allImages.WIS(), allImages.WISOn());
    }

    /* 反應計畫 */
    if ("IIP".equals(subDto.getId())) {
      imageItem.setImage(allImages.IIP(), allImages.IIPOn());
    }
    if ("RSP".equals(subDto.getId())) {
      imageItem.setImage(allImages.RSP(), allImages.RSPOn());
    }

    /* 報表統計 */
    if ("HDA".equals(subDto.getId())) {
      imageItem.setImage(allImages.HDA(), allImages.HDAOn());
    }
    if ("RPT".equals(subDto.getId())) {
      imageItem.setImage(allImages.RPT(), allImages.RPTOn());
    }

    /* 監控管理 */
    if ("CCS".equals(subDto.getId())) {
      imageItem.setImage(allImages.CCS(), allImages.CCSOn());
    }
    if ("EMS".equals(subDto.getId())) {
      imageItem.setImage(allImages.EMS(), allImages.EMSOn());
    }
    if ("NCC".equals(subDto.getId())) {
      imageItem.setImage(allImages.NCC(), allImages.NCCOn());
    }
    if ("SCH".equals(subDto.getId())) {
      imageItem.setImage(allImages.SCH(), allImages.SCHOn());
    }

    /* 動態畫面 */
    if ("DDS".equals(subDto.getId())) {
      imageItem.setImage(allImages.DDS(), allImages.DDSOn());
    }
    if ("WMS".equals(subDto.getId())) {
      imageItem.setImage(allImages.WMS(), allImages.WMSOn());
    }
    if ("DDSConfig".equals(subDto.getId())) {
      imageItem.setImage(allImages.DDSConfig(), allImages.DDSConfigOn());
    }

    /* 系統管理 */
    if ("AccountManager".equals(subDto.getId())) {
      imageItem.setImage(allImages.AccountManager(), allImages.AccountManagerOn());
    }
    if ("AlarmManager".equals(subDto.getId())) {
      imageItem.setImage(allImages.AlarmManager(), allImages.AlarmManagerOn());
    }
    if ("ConfigManager".equals(subDto.getId())) {
      imageItem.setImage(allImages.ConfigManager(), allImages.ConfigManagerOn());
    }
    if ("HcceManager".equals(subDto.getId())) {
      imageItem.setImage(allImages.HcceManager(), allImages.HcceManagerOn());
    }

    /* 其他 */
    if ("FS".equals(subDto.getId())) {
      imageItem.setImage(allImages.FS(), allImages.FSOn());
    }
    if ("SCM".equals(subDto.getId())) {
      imageItem.setImage(allImages.SCM(), allImages.SCMOn());
    }
    if ("PTS".equals(subDto.getId())) {
      imageItem.setImage(allImages.PTS(), allImages.PTSOn());
    }

    return imageItem;
  }
}
