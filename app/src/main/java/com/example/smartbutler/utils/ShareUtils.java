package com.example.smartbutler.utils;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.utils
 * 文件名：ShareUtils
 * 创建者：LB
 * 创建时间：2018/1/17 下午4:02
 * 描述：   SharedPreferences 工具类封装
 */

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {

    public static final String NAME = "config";

    //键 值
    public static void putString(Context mContext, String key, String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    //键 默认值
    public static String getString(Context mContext, String key, String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    //键 值
    public static void putInt(Context mContext, String key, int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    //键 默认值
    public static int getInt(Context mContext, String key, int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    //键 值
    public static void putBoolean(Context mContext, String key, boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    //键 默认值
    public static boolean getBoolean(Context mContext, String key, boolean defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void delete(Context mContext, String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key);
    }

}
