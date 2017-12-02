package com.hzh.view.injector.sample.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hzh.view.injector.ViewInjectManager;
import com.hzh.view.injector.anno.ContentView;
import com.hzh.view.injector.anno.OnClick;
import com.hzh.view.injector.anno.OnLongClick;
import com.hzh.view.injector.anno.ViewInject;
import com.hzh.view.injector.sample.R;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.startBtn)
    public Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectManager.getOperate().inject(this);
        //如果能给View添加缩放动画，则表示
        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1f, 1.5f);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float cValue = (Float) animation.getAnimatedValue();
                startBtn.setScaleX(cValue);
                startBtn.setScaleY(cValue);
            }
        });
        scaleAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnimator.setDuration(1000);
        scaleAnimator.start();
    }

    @OnClick({R.id.startBtn, R.id.clickBtn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.startBtn:
                startActivity(new Intent(MainActivity.this, MeActivity.class));
                break;
            case R.id.clickBtn:
                toast("clickBtn <<onClick>>");
                break;
        }
    }

    @OnLongClick(R.id.longClickBtn)
    public boolean onLongClick(View view) {
        toast("longClickBtn <<onLongClick>>");
        return true;
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}