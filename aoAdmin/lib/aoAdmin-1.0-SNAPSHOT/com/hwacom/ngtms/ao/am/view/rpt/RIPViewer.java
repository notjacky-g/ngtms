package com.hwacom.ngtms.ao.am.view.rpt;

import com.google.gwt.user.client.ui.IsWidget;
import java.util.Map;

public interface RIPViewer extends IsWidget {
  Map<String, Object> getInputParameter();
  
  void setInputParameter(Map<String, Object> paramMap);
  
  boolean isValid();
  
  void clearInputParameter();
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */