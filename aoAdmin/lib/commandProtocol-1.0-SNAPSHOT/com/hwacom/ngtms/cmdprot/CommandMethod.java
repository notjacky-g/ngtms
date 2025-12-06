package com.hwacom.ngtms.cmdprot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CommandMethod {
  String name() default "";
  
  boolean async() default false;
}


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\CommandMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */