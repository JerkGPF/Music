package com.example.music.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.music.R;
import com.example.music.utils.UserUtils;
import com.example.music.views.InputView;

public class LoginActivity extends BaseActivity {
    private InputView mInputPhone,mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 初始化view
     */

    private void initView() {
        initNavBar(false,"登录",false);
        mInputPhone =fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
    }
    /***
     *注册点击事件
     */
    public void onRegisterClick(View v){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }
    /**
     * 登录
     */
    public void  onCommitClick(View v){

        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        //验证用户
        if (!UserUtils.validateLogin(this,phone,password)){
            return;
        }
        //跳转到主页
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
