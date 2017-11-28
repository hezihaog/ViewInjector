package com.hzh.view.injector.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hzh.view.injector.anno.ContentView;
import com.hzh.view.injector.anno.OnClick;
import com.hzh.view.injector.anno.OnLongClick;
import com.hzh.view.injector.anno.ViewInject;
import com.hzh.view.injector.sample.R;
import com.hzh.view.injector.sample.ViewInjectManager;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.startBtn)
    public Button toastBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectManager.getOperate().inject(this);
    }

    @OnClick(R.id.startBtn)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.startBtn:
                startActivity(new Intent(MainActivity.this, MeActivity.class));
                break;
        }
    }

    @OnLongClick(R.id.startBtn)
    public boolean onLongClick(View view) {
        toast("onLongClick !!!");
        return true;
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}