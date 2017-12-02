package com.hzh.view.injector.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hzh.view.injector.anno.ContentView;
import com.hzh.view.injector.sample.R;
import com.hzh.view.injector.ViewInjectManager;
import com.hzh.view.injector.sample.fragment.MyFragment;

@ContentView(R.layout.activity_me)
public class MeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectManager.getOperate().inject(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new MyFragment(), MyFragment.class.getSimpleName())
                .commit();
    }
}
