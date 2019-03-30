package com.nsq.minispring.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NsqlRequestParam {
    String value() default "";
}
