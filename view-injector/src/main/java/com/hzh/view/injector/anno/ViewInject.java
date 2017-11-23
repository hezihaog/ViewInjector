package com.hzh.view.injector.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package: com.hzh.view.injector
 * FileName: ViewInject
 * Date: on 2017/11/23  下午3:15
 * Auther: zihe
 * Descirbe: 注入View的注解
 * Email: hezihao@linghit.com
 */

@Target(ElementType.FIELD)//设置注解使用范围为变量
@Retention(RetentionPolicy.RUNTIME)//设置注解生命时长，运行时
public @interface ViewInject {
    /**
     * View value
     */
    int value();
}