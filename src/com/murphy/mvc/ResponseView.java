package com.murphy.mvc;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 注解的作用：
 *  被此注解添加的方法， 会被用于处理请求。
 *  方法返回的内容，会直接重定向
 * @author murphy
 */
public @interface ResponseView {
    String value();
}
