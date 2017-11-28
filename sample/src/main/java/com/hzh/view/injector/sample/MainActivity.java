package com.hzh.view.injector.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hzh.view.injector.ViewInjectorImpl;
import com.hzh.view.injector.anno.ContentView;
import com.hzh.view.injector.anno.OnClick;
import com.hzh.view.injector.anno.OnLongClick;
import com.hzh.view.injector.anno.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.toastBtn)
    public Button toastBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectorImpl.getInstance().inject(this);
        //如果绑定成功，按钮的问题就会改为bind success
        toastBtn.setText("bind success");
    }

    @OnClick(R.id.toastBtn)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.toastBtn:
                toast("onClick !!!");
                break;
        }
    }

    @OnLongClick(R.id.toastBtn)
    public boolean onLongClick(View view) {
        toast("onLongClick !!!");
        return true;
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}