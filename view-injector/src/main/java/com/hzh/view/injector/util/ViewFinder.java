package com.hzh.view.injector.util;

import android.app.Activity;
import android.view.View;

/**
 * Package: com.hzh.view.injector.util
 * FileName: ViewFinder
 * Date: on 2017/11/28  下午9:35
 * Auther: zihe
 * Descirbe: View查找者
 * Email: hezihao@linghit.com
 */

public class ViewFinder {
    private View view;
    private Activity activity;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    /**
     * 查找控件
     *
     * @param resId 控件id
     * @return 控件对象
     */
    public <T extends View> T findViewById(int resId) {
        if (view != null) {
            return ((View) view).findViewById(resId);
        } else if (activity != null) {
            return ((Activity) activity).findViewById(resId);
        }
        return null;
    }
}
