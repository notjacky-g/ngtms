/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public interface AmImages extends ClientBundle {

  AmImages INSTANCE = GWT.create(AmImages.class);

  ImageResource add();

  ImageResource add_small();

  ImageResource delete();

  ImageResource modify();

  ImageResource undo();

  ImageResource excel();

  ImageResource icCar();

  @ImageOptions(width = 20, height = 20)
  ImageResource icCarVd();

  @ImageOptions(width = 20, height = 20)
  ImageResource icCarVdShoulder();

  ImageResource folder();

  ImageResource folder_close();

  ImageResource treeIconAllCollapse();

  ImageResource treeIconAllExpand();

  ImageResource treeIconAllSelect();

  ImageResource treeIconAllUnSelect();

  ImageResource cctv1();

  ImageResource cctv2();

  ImageResource cctv3();

  ImageResource cctv4();

  ImageResource role();

  ImageResource arrowDown();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms1x4();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms2x6();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms2x8FullColor();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms2x8();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms3x6();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms4x1();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms5x2();

  @ImageOptions(width = 20, height = 20)
  ImageResource cms8x1();

  ImageResource accountTitleItem();

  ImageResource loginButtonOff();

  ImageResource accountConfig();

  ImageResource working();

  ImageResource homeImage();

  ImageResource homeImage01();

  ImageResource homeImage02();

  ImageResource homeImage03();

  ImageResource configTitleItem();

  /* 資料收集 */
  ImageResource EED();

  ImageResource EEDOn();

  ImageResource ETS();

  ImageResource ETSOn();

  ImageResource IID();

  ImageResource IIDOn();

  ImageResource LSS();

  ImageResource LSSOn();

  ImageResource NDS();

  ImageResource NDSOn();

  ImageResource PDCP();

  ImageResource PDCPOn();

  ImageResource QLDS();

  ImageResource QLDSOn();

  ImageResource RD();

  ImageResource RDOn();

  ImageResource TEM();

  ImageResource TEMOn();

  ImageResource VD();

  ImageResource VDOn();

  ImageResource VI();

  ImageResource VIOn();

  ImageResource WD();

  ImageResource WDOn();

  /* 設備顯示 */
  ImageResource CMS();

  ImageResource CMSOn();

  ImageResource CSLS();

  ImageResource CSLSOn();

  ImageResource LCS();

  ImageResource LCSOn();

  ImageResource RGS();

  ImageResource RGSOn();

  ImageResource RMS();

  ImageResource RMSOn();

  ImageResource SCS();

  ImageResource SCSOn();

  ImageResource TTS();

  ImageResource TTSOn();

  ImageResource WIS();

  ImageResource WISOn();

  /* 反應計畫 */
  ImageResource IIP();

  ImageResource IIPOn();

  ImageResource RSP();

  ImageResource RSPOn();

  /* 報表統計 */
  ImageResource HDA();

  ImageResource HDAOn();

  ImageResource RPT();

  ImageResource RPTOn();

  /* 監控管理 */
  ImageResource CCS();

  ImageResource CCSOn();

  ImageResource EMS();

  ImageResource EMSOn();

  ImageResource NCC();

  ImageResource NCCOn();

  ImageResource SCH();

  ImageResource SCHOn();

  /* 動態畫面 */
  ImageResource DDS();

  ImageResource DDSOn();

  ImageResource WMS();

  ImageResource WMSOn();

  ImageResource DDSConfig();

  ImageResource DDSConfigOn();

  /* 系統管理 */
  ImageResource AccountManager();

  ImageResource AccountManagerOn();

  ImageResource AlarmManager();

  ImageResource AlarmManagerOn();

  ImageResource ConfigManager();

  ImageResource ConfigManagerOn();

  ImageResource HcceManager();

  ImageResource HcceManagerOn();

  /* 其他 */
  ImageResource FS();

  ImageResource FSOn();

  ImageResource SCM();

  ImageResource SCMOn();

  ImageResource PTS();

  ImageResource PTSOn();

  /* title */
  ImageResource headerLeft();

  ImageResource headerCenter();

  ImageResource headerRight();

  ImageResource alarmText();

  ImageResource logo();

  ImageResource logo_main();

  ImageResource logo_dr();

  ImageResource logo_dajia();

  ImageResource home();

  ImageResource separationLine();

  ImageResource time();

  ImageResource people();

  ImageResource logout();

  ImageResource alarm();

  /* DIS */
  ImageResource rareWord();

  ImageResource overRareWord();

  ImageResource backgroundGraphic();

  ImageResource overBackgroundGraphic();
}
