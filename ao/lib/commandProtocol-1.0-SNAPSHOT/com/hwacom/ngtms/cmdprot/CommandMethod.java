package com.hwacom.ngtms.cmdprot;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface CommandMethod
{
  String name() default "";
  
  boolean async() default false;
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\CommandMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */