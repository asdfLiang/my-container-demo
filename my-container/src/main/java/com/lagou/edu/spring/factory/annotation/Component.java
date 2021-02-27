package com.lagou.edu.spring.factory.annotation;

import java.lang.annotation.*;

/**
 * @author liangzj
 * @date 2021/2/24 22:52
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
}
