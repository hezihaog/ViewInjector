package com.hzh.view.injector.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package: com.hzh.view.injector.anno
 * FileName: OnLongClick
 * Date: on 2017/11/23  下午5:04
 * Auther: zihe
 * Descirbe: 注入长按事件
 * Email: hezihao@linghit.com
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnLongClick {
    int value();
}
