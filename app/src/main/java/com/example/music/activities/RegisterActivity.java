package com.example.music.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.example.music.R;
import com.example.music.utils.UserUtils;
import com.example.music.views.InputView;

public class RegisterActivity extends BaseActivity {
    private InputView mInputPhone,mInputPassword,mInputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    /**
     * 初始化view
     */

    private void initView() {
        initNavBar(true,"注册",false );

        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
        mInputPasswordConfirm = fd(R.id.input_password_confirm);


    }
    /**
     * 注册点击事件
     * 1.用户输入合法性验证
     *    1.用户手机号验证合法
     *    2.用户是否输入密码和确定密码，并且这两次的输入是否相同
     *    3.用户输入手机号是否已被注册
     *  2.保存用户输入的手机号和密码（MD5加密）
     */
    public void onRegisterClick(View view){
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfirm.getInputStr();

        boolean result = UserUtils.registerUser(this,phone,password,passwordConfirm);

        if (!result)return;
        //后退页面
        onBackPressed();
    }
}
