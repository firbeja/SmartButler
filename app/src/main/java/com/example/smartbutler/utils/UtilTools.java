package com.example.smartbutler.utils;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.utils
 * 文件名：UtilTools
 * 创建者：LB
 * 创建时间：2018/1/17 下午1:24
 * 描述：   工具统一类
 */

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class UtilTools {

    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

}
