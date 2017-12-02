package com.hzh.view.injector.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzh.logger.L;
import com.hzh.view.injector.anno.ContentView;
import com.hzh.view.injector.sample.R;
import com.hzh.view.injector.ViewInjectManager;

/**
 * Package: com.hzh.view.injector.sample.fragment
 * FileName: MyFragment
 * Date: on 2017/11/28  下午10:46
 * Auther: zihe
 * Descirbe: 注入fragment例子
 * Email: hezihao@linghit.com
 */

@ContentView(R.layout.fragment_me)
public class MyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ViewInjectManager.getOperate().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d("onViewCreated ::: onViewCreated");
    }
}