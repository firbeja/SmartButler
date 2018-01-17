package com.example.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.smartbutler.R;

/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.ui
 * 文件名：BaseActivity
 * 创建者：LB
 * 创建时间：2018/1/17 下午12:06
 * 描述：   Activity基类
 */

/**
 * 主要做的事情：
 * 1.统一的属性
 * 2.统一的接口
 * 3.统一的方法
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
