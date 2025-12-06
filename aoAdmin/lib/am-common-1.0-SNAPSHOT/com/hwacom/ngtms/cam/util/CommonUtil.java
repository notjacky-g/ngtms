/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.sencha.gxt.widget.core.client.form.ValueBaseField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonUtil {
  public static String getBackgroundGraphicUrl(Integer id) {
    return GWT.getHostPageBaseURL()
        + "api/graphicProvider?backgroundGrapihcId="
        + id
        + "&t="
        + new Date().getTime(); // disable cache
  }

  public static ArrayList<String> genDeviceNameList(List<DeviceConfigDTO> deviceConfigList) {
    ArrayList<String> result = new ArrayList<>();
    for (DeviceConfigDTO dc : deviceConfigList) {
      result.add(dc.getDeviceName());
    }
    return result;
  }

  public static ArrayList<String> genDisplayNameList(List<DeviceConfigDTO> deviceConfigList) {
    ArrayList<String> result = new ArrayList<>();
    for (DeviceConfigDTO dc : deviceConfigList) {
      result.add(dc.getDisplayName());
    }
    return result;
  }

  /**
   * @return browser 實做的 unique id，不保證格式。
   * @see <a
   *     href="https://groups.google.com/d/msg/google-web-toolkit/0Iwgv-4wmsc/6V9qCArplNYJ">reference</a>
   * @see #genUUID()
   */
  public static String genUID() {
    return Document.get().createUniqueId();
  }

  /**
   * @return 產生 RFC-4122 version 4 的 unique id
   * @see <a href="http://www.pst.ifi.lmu.de/~rauschma/download/UUID.java">reference</a>
   * @see #genUID()
   */
  public static String genUUID() {
    final char[] CHARS =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    char[] uuid = new char[36];
    int r;

    // rfc4122 requires these characters
    uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
    uuid[14] = '4';

    // Fill in random data.  At i==19 set the high bits of clock sequence as
    // per rfc4122, sec. 4.1.5
    for (int i = 0; i < 36; i++) {
      if (uuid[i] == 0) {
        r = (int) (Math.random() * 16);
        uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
      }
    }
    return new String(uuid);
  }

  /**
   * 當 source 的值有改變時，同步 shadow 的值。 會對 source 作 addValueChangeHander()。
   *
   * @param source
   * @param shadow
   */
  public static <T> void syncField(final ValueBaseField<T> source, final ValueBaseField<T> shadow) {
    source.addValueChangeHandler(
        new ValueChangeHandler<T>() {
          @Override
          public void onValueChange(ValueChangeEvent<T> event) {
            shadow.setValue(source.getValue());
          }
        });
  }

  //ref: https://code.google.com/p/google-web-toolkit/issues/detail?id=5068
  public static native <T> T clone(T source) /*-{
		// 這個東西在GWT 2.8, com.google.gwt.core.client.impl.Impl 已經不存在了
//		var result = {};
//		@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(result);
//		for (var value in source) {
//			if (!(value in result)) {
//				result[value] = source[value];
//			}
//		}
//		return result;
		return null;
	}-*/;
}
