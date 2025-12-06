/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AllImages extends ClientBundle {

  AllImages INSTANCE = GWT.create(AllImages.class);

  ImageResource headerLeft();

  ImageResource headerCenter();

  ImageResource headerRight();

  ImageResource systemGray();

  ImageResource systemGreen();

  ImageResource systemYellow();

  ImageResource oneGreen();

  ImageResource oneGray();

  ImageResource twoGreen();

  ImageResource twoGray();

  ImageResource dbBackupGray();

  ImageResource dbBackupGreen();

  ImageResource dbBackupYellow();

  ImageResource dbOnlineGray();

  ImageResource dbOnlineGreen();

  ImageResource dbOnlineYellow();

  ImageResource dbWhite();

  ImageResource numberWhite();

  ImageResource numberOne();

  ImageResource numberOne16();

  ImageResource numberTwo();

  ImageResource numberTwo16();

  ImageResource primarySystemGray();

  ImageResource primarySystemGreen();

  ImageResource primarySystemYellow();

  ImageResource backupSystemGray();

  ImageResource backupSystemGreen();

  ImageResource backupSystemYellow();

  ImageResource titleItem();
}
