package com.hzh.view.injector.sample;

import com.hzh.view.injector.ViewInjectorImpl;

/**
 * Package: com.hzh.view.injector.sample
 * FileName: ViewInjectManager
 * Date: on 2017/11/28  下午11:01
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ViewInjectManager {

    /**
     * 获取注入器实现对象
     *
     * @return 注入器实例
     */
    public static ViewInjectorImpl getOperate() {
        return ViewInjectorImpl.getInstance();
    }
}
