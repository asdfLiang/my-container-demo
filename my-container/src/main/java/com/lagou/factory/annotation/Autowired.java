package com.lagou.factory.annotation;

import java.lang.annotation.*;

/**
 * @author liangzj
 * @date 2021/2/21 23:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean required() default true;

}
