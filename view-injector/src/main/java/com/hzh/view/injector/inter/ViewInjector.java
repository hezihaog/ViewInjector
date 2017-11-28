package com.hzh.view.injector.inter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Package: com.hzh.view.injector.inter
 * FileName: ViewInjector
 * Date: on 2017/11/23  下午4:04
 * Auther: zihe
 * Descirbe: View注入接口
 * Email: hezihao@linghit.com
 */

public interface ViewInjector {
    /**
     * 以View为注入对象
     *
     * @param view view实例
     */
    void inject(View view);

    /**
     * 以Activity为注入对象
     *
     * @param activity Activity实例
     */
    void inject(Activity activity);

    /**
     * 注入ListView的ViewHolder
     *
     * @param target 目标对象
     * @param view   View实例
     */
    void inject(Object target, View view);

    /**
     * 注入Fragment
     *
     * @param fragment  fragment对象
     * @param inflater  填充器
     * @param container 容器
     * @return
     */
    View inject(Object fragment, LayoutInflater inflater, ViewGroup container);
}