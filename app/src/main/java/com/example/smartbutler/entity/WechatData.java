package com.example.smartbutler.entity;

/**
 * Created by john on 2018/1/25.
 */

public class WechatData {

//    "result":{
//        "list":[
//        {
//            "id":"wechat_20180125010912",
//                "title":"有鱼，有鸟，算不算生态河道？山东十六项指标确立“标尺”",
//                "source":"山东环境",
//                "firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-34292156.static/640",
//                "mark":"",
//                "url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20180125010912"
//        },
//        {
//            "id":"wechat_20180125010754",
//                "title":"温馨提示：中央气象台发布暴雪橙色预警、寒潮蓝色预警",
//                "source":"山东环境",
//                "firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-34292156.static/640",
//                "mark":"",
//                "url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20180125010754"
//        },

    private String title;

    private String source;

    private String firstImg;

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
