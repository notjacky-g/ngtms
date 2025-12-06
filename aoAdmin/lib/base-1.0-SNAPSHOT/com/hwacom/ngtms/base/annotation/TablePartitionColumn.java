package com.hwacom.ngtms.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TablePartitionColumn {
  String value() default "";
}


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\annotation\TablePartitionColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */