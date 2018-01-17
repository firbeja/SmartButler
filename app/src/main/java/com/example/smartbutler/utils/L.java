package com.example.smartbutler.utils;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.utils
 * 文件名：L
 * 创建者：LB
 * 创建时间：2018/1/17 下午3:27
 * 描述：   Log 封装
 */

import android.util.Log;

public class L {

    public static final boolean Debug = true;

    public static final String TAG = "SmartButler";

    //五个等级 DIWEF F没有写 只写了DIWE

    public static void d(String text){
        if (Debug == true){
            Log.d(TAG, text);
        }
    }

    public static void i(String text){
        if (Debug == true){
            Log.i(TAG, text);
        }
    }

    public static void w(String text){
        if (Debug == true){
            Log.w(TAG, text);
        }
    }

    public static void e(String text){
        if (Debug == true){
            Log.e(TAG, text);
        }
    }

}
