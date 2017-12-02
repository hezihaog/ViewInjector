package com.hzh.view.injector.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package: com.hzh.view.injector.anno
 * FileName: OnClick
 * Date: on 2017/11/23  下午4:29
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    int[] value();
}