package com.example.music.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.music.R;

import java.util.Timer;
import java.util.TimerTask;

//1.延迟三秒
//2.跳转
public class WelcomeActivity extends BaseActivity {
    private Timer mTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

    }

    private void init() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("WelcomeActivity","当前线程为：" + Thread.currentThread());
                //toMain();
                toLogin();
            }
        },3*1000);

    }

    /**
     * 跳转至main
     */
    private void toMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 跳转至login
     */
    private void toLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
