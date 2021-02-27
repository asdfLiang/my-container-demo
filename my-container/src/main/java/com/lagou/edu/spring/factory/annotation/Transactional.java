package com.lagou.edu.spring.factory.annotation;

import java.lang.annotation.*;

/**
 * @author liangzj
 * @date 2021/2/22 1:14
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transactional {
}
