package com.hzh.view.injector.inter;

import android.app.Activity;

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
     * 以Activity为注入对象
     *
     * @param activity Activity实例
     */
    void inject(Activity activity);
}