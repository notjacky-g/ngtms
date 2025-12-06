/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.util;

public class DownloadUtil {

  public static native void download(String fileName, String text) /*-{
      var blob = new Blob([text], { type: 'text/csv;charset=utf-8;' });
      // IE 10+
      if (navigator.msSaveBlob) {
          navigator.msSaveBlob(blob, fileName);
      } else {
          var link = document.createElement("a");
          // feature detection
          if (link.download !== undefined) {
              // Browsers that support HTML5 download attribute
              var url = URL.createObjectURL(blob);
              link.setAttribute("href", url);
              link.setAttribute("download", fileName);
              link.style.visibility = 'hidden';
              document.body.appendChild(link);
              link.click();
              document.body.removeChild(link);
          }
      }
  }-*/;

  public static native void downloadCsv(String fileName, String text) /*-{
	  var blob = new Blob([text], { type: 'text/csv;charset=utf-8;' });
	  // IE 10+
	  if (navigator.msSaveBlob) {
	      navigator.msSaveBlob(blob, fileName);
	  } else {
	      var link = document.createElement("a");
	      // feature detection
	      if (link.download !== undefined) {
	          // Browsers that support HTML5 download attribute
	          var url = URL.createObjectURL(blob);
	          link.setAttribute("href", "data:text/csv;charset=utf-8,%EF%BB%BF" + encodeURI(text));
	          link.setAttribute("download", fileName);
	          link.style.visibility = 'hidden';
	          document.body.appendChild(link);
	          link.click();
	          document.body.removeChild(link);
	      }
	  }
	}-*/;

  public static native void downloadXml(String fileName, String text) /*-{
    var blob = new Blob([text], { type: 'application/xml;charset=utf-8;' });
    var url = URL.createObjectURL(blob);
    @com.hwacom.ngtms.common.am.util.DownloadUtil::downloadAsFile(Ljava/lang/String;Ljava/lang/String;)(url, fileName);
  }-*/;

  private static native void downloadAsFile(String url, String fileName) /*-{
    var link = document.createElement("a");
    if (link.download !== undefined) {
      // Browsers that support HTML5 download attribute
      link.setAttribute("href", url);
      link.setAttribute("download", fileName);
      link.style.visibility = 'hidden';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }-*/;

  public static void download(String url) {
    downloadAsFile(url, url.substring(url.lastIndexOf('/') + 1));
  }
}
