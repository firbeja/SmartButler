package com.example.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.CourierAdapter;
import com.example.smartbutler.entity.CourierData;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourierActivity extends AppCompatActivity implements View.OnClickListener {

    private List<CourierData>mList;
    private ListView mListView;
    private EditText et_name;
    private EditText et_number;
    private Button btn_get_courier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        //juhe key http://v.juhe.cn/exp/index?key=6286d90b8f8a1d604e871b67d02f288a&com=yd&no=3831691405932
        //AppKey：6286d90b8f8a1d604e871b67d02f288a

        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_get_courier = (Button) findViewById(R.id.btn_get_courier);
        mList = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.mListView);

        btn_get_courier.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            {"resultcode":"200","reason":"成功的返回","result":{"company":"韵达","com":"yd","no":"3831691405932","status":"1","list":[{"datetime":"2018-01-14 18:37:37","remark":"快件在 北京市场部公司 进行揽件扫描","zone":""},{"datetime":"2018-01-14 20:33:29","remark":"快件在 北京分拨中心 在分拨中心进行称重扫描","zone":""},{"datetime":"2018-01-14 20:47:56","remark":"快件在 北京分拨中心 进行中转集包扫描，将发往：广东佛山分拨中心","zone":""},{"datetime":"2018-01-14 20:51:50","remark":"快件在 北京分拨中心 进行装车扫描，即将发往：广东佛山分拨中心","zone":""},{"datetime":"2018-01-16 20:41:08","remark":"快件在 广东佛山分拨中心 在分拨中心进行卸车扫描","zone":""},{"datetime":"2018-01-16 22:38:48","remark":"快件在 广东佛山分拨中心 从站点发出，本次转运目的地：广东广州海珠区大学城公司","zone":""},{"datetime":"2018-01-17 09:29:29","remark":"快件在 广东广州海珠区大学城公司 进行派件扫描；派送业务员：谭联辉；联系电话：15521188335","zone":""},{"datetime":"2018-01-17 10:23:36","remark":"快件在 广东广州海珠区大学城公司 快件已被 入快递柜 签收","zone":""},{"datetime":"2018-01-17 22:35:18","remark":"快件在 广东广州海珠区大学城公司 快件已被 已签收 签收","zone":""}]},"error_code":0}
            /**
             * 1.获取输入框的内容
             * 2.判断是否为空
             * 3.拿到数据去请求数据（Json）
             * 4.解析Json
             * 5.listview适配器
             * 6.实体类（item）
             * 7.设置数据/显示效果
             */
            case R.id.btn_get_courier :

                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接我们的url
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY + "&com=" + name + "&no=" + number;

                //2.判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)){

                    //3.拿到数据去请求数据
                    //get请求简洁版实现
//                    String url = "http://v.juhe.cn/exp/index?key=6286d90b8f8a1d604e871b67d02f288a&com=yd&no=3831691405932";
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.d(t);
                            //4.解析Json
                            parsingJson(t);
                        }
                    });

                }else {
                    Toast.makeText(CourierActivity.this, R.string.text_toast_empty, Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

//    "resultcode":"200",
//            "reason":"成功的返回",
//            "result":{
//        "company":"韵达",
//                "com":"yd",
//                "no":"3831691405932",
//                "status":"1",
//                "list":[
//        {
//            "datetime":"2018-01-14 18:37:37",
//                "remark":"快件在 北京市场部公司 进行揽件扫描",
//                "zone":""
//        },
//        {
//            "datetime":"2018-01-14 20:33:29",
//                "remark":"快件在 北京分拨中心 在分拨中心进行称重扫描",
//                "zone":""
//        },
    //解析数据
    private void parsingJson(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject json = (JSONObject) list.get(i);
                CourierData courierData = new CourierData();
                L.d(json.getString("datetime"));
                courierData.setDatetime(json.getString("datetime"));
                courierData.setRemark(json.getString("remark"));
                courierData.setZone(json.getString("zone"));
                mList.add(courierData);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this, mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
