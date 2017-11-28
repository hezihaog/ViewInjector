package com.hzh.view.injector.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package: com.hzh.view.injector.anno
 * FileName: ContentView
 * Date: on 2017/11/28  下午9:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
    /**
     * 布局id
     */
    int value();
}
