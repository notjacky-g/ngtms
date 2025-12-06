package com.hwacom.ngtms.cmdprot.cmdfmtdef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GlobalParams {
  String paramsName() default "";
}


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\GlobalParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */