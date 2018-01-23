package com.example.smartbutler.utils;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.utils
 * 文件名：StaticClass
 * 创建者：LB
 * 创建时间：2018/1/17 下午1:25
 * 描述：   数据、常量
 */

public class StaticClass {

    //闪屏页 的 handler.what 的 key，延时启动
    public static final int HANDLER_SPLASH = 1001;

    //判断是否 第一次启动，存入SharePreference的key
    public static final String SHARE_IS_FIRST = "isFirst";

    //Bugly_key
    public static final String BUGLY_APP_ID = "a1d0ef57f9";

    //Bmob key
    public static final String BMOB_APP_ID = "04d9a8c7690dd274b53a445258f8bfe0";

    //juhe key http://v.juhe.cn/exp/index?key=6286d90b8f8a1d604e871b67d02f288a&com=yd&no=3831691405932
    //AppKey：6286d90b8f8a1d604e871b67d02f288a
    //快递key
    public static final String COURIER_KEY = "6286d90b8f8a1d604e871b67d02f288a";

    //归属地key 聚合接口
    //http://apis.juhe.cn/mobile/get?phone=13429667914&key=您申请的KEY
    public static final String PHONE_KEY = "2e57c70b54c4e0cec96e0fd529b18862";

    //问答机器人key
    //http://op.juhe.cn/robot/index?info=你好&key=您申请到的APPKEY
//    {
//        "reason":"成功的返回",
//            "result": /*根据code值的不同，返回的字段有所不同*/
//        {
//            "code":100000, /*返回的数据类型，请根据code的值去数据类型API查询*/
//                "text":"你好啊，希望你今天过的快乐"
//        },
//        "error_code":0
//    }
    public static final String CHAT_LIST_KEY = "7a48539921338ef90866922b21e25f6d";

}
