package com.example.springcloud.ribbon.configuration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreComponentScan {

}
