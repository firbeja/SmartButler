package com.example.smartbutler.entity;

/**
 * Created by john on 2018/1/23.
 */

//"list":[
//        {
//        "datetime":"2018-01-14 18:37:37",
//        "remark":"快件在 北京市场部公司 进行揽件扫描",
//        "zone":""
//        },
//        {
//        "datetime":"2018-01-14 20:33:29",
//        "remark":"快件在 北京分拨中心 在分拨中心进行称重扫描",
//        "zone":""
//        },
//        {
//        "datetime":"2018-01-14 20:47:56",
//        "remark":"快件在 北京分拨中心 进行中转集包扫描，将发往：广东佛山分拨中心",
//        "zone":""
//        },

public class CourierData {

    private String datetime;
    private String remark;
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
