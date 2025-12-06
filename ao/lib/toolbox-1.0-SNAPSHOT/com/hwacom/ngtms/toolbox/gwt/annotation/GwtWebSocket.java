package com.hwacom.ngtms.toolbox.gwt.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface GwtWebSocket
{
  String name();
  
  String entryPoint();
  
  String uri();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\gwt\annotation\GwtWebSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */