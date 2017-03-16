package com.zrk1000.proxytest.annotation;

import java.lang.annotation.*;

/**
 * Created by pc on 2017/3/13.
 * 标注是否生成代理类  调用远程DRCPService
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DRCPService {

    String value() default "";

}
