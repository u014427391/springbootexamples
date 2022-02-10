package com.example.easyexcel.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EasyExcelCollection {
    /**
     * 是否需要合并单元格
     */
    boolean needMerge() default false;

    /**
     * 是否是主键,即该字段相同的行合并
     */
    boolean isPk() default false;
}
