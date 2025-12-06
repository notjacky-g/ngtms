/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.images;

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

  @Source("excel.png")
  @ImageOptions(width = 16, height = 16)
  ImageResource excel16();

  ImageResource folder();

  ImageResource folder_close();

  ImageResource treeIconAllCollapse();

  ImageResource treeIconAllExpand();

  ImageResource treeIconAllSelect();

  ImageResource treeIconAllUnSelect();

  ImageResource role();

  ImageResource arrowDown();

  ImageResource accountTitleItem();

  ImageResource loginButtonOff();

  ImageResource accountConfig();

  ImageResource working();

  ImageResource homeImage();

  ImageResource homeImage01();

  ImageResource homeImage02();

  ImageResource homeImage03();

  ImageResource configTitleItem();

  /* 系統管理 */
  ImageResource AccountManager();

  ImageResource AccountManagerOn();

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

  @Source("delete1024.png")
  @ImageOptions(width = 16, height = 16)
  ImageResource delete16();

  @Source("edit256.png")
  @ImageOptions(width = 16, height = 16)
  ImageResource edit16();

  @Source("preview.png")
  @ImageOptions(width = 16, height = 16)
  ImageResource preview16();

  @Source("pdf.png")
  @ImageOptions(width = 16, height = 16)
  ImageResource pdf16();

  @Source("png.png")
  @ImageOptions(width = 16, height = 16)
  ImageResource png16();
}
