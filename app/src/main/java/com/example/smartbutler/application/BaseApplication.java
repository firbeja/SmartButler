package com.example.smartbutler.application;

import android.app.Application;

import com.example.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.application
 * 文件名：BaseApplication
 * 创建者：LB
 * 创建时间：2018/1/17 下午12:03
 * 描述：   TODO
 */

public class BaseApplication extends Application {

    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);

        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
