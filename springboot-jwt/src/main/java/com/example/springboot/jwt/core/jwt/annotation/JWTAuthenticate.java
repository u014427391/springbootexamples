package com.example.springboot.jwt.core.jwt.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *  JWTAuthenticate
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/06 16:12  修改内容:
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JWTAuthenticate {

}
