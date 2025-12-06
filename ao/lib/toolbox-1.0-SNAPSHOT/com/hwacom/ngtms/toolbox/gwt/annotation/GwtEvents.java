package com.hwacom.ngtms.toolbox.gwt.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({java.lang.annotation.ElementType.PACKAGE})
public @interface GwtEvents
{
  GwtEvent[] value();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\gwt\annotation\GwtEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */