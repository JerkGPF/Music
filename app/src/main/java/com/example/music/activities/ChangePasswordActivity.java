package com.example.music.activities;


import android.os.Bundle;

import com.example.music.R;

public class ChangePasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();

    }

    private void initView() {
        initNavBar(true,"修改密码",false);
    }

}
