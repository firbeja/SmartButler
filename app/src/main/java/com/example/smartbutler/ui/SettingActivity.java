package com.example.smartbutler.ui;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.ui
 * 文件名：SettingActivity
 * 创建者：LB
 * 创建时间：2018/1/17 下午2:38
 * 描述：   设置
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Switch sw_speak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sw_speak = (Switch) findViewById(R.id.sw_speak);
        boolean isSpeak = ShareUtils.getBoolean(SettingActivity.this, "isSpeak", false);
        sw_speak.setChecked(isSpeak);
//        sw_speak.setOnClickListener(this);

        //既然是switch，那么你监听的不应该是click，而是OnCheckedChangeListener。
        sw_speak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                sw_speak.setChecked(!sw_speak.isChecked());
                ShareUtils.putBoolean(SettingActivity.this, "isSpeak", sw_speak.isChecked());
                boolean isSpeak = ShareUtils.getBoolean(SettingActivity.this, "isSpeak", false);
                L.d(sw_speak.isChecked() + "------isSpeak---" + isSpeak);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){



        }

    }
}
