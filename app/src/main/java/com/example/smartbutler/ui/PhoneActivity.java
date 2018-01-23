package com.example.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {

    //输入框
    private EditText et_number;
    //查询按钮
    private Button btn_query;
    //公司logo
    private ImageView iv_company;
    //结果
    private TextView tv_result;
    //自定义键盘按钮
    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_del;
    //标志位 如果为 true 则代表查询成功过
    private boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();

    }

    private void initView() {

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_del = (Button) findViewById(R.id.btn_del);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_number.setText("");
                return false;
            }
        });

        et_number = (EditText) findViewById(R.id.et_number);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        iv_company = (ImageView) findViewById(R.id.iv_company);
        tv_result = (TextView) findViewById(R.id.tv_result);

    }


    @Override
    public void onClick(View v) {

        String number = et_number.getText().toString().trim();

        switch (v.getId()) {

            case R.id.btn_1 :
            case R.id.btn_2 :
            case R.id.btn_3 :
            case R.id.btn_4 :
            case R.id.btn_5 :
            case R.id.btn_6 :
            case R.id.btn_7 :
            case R.id.btn_8 :
            case R.id.btn_9 :
            case R.id.btn_0 :
                if (flag){
                    flag = false;
                    number = "";
                    et_number.setText("");
                }
                //每次结尾加1
                et_number.setText(number + ((Button)v).getText());
                //移动光标
                et_number.setSelection(number.length() +1);
                break;
            case R.id.btn_del :
                if (!TextUtils.isEmpty(number) && number.length()>0){
                    //每次结尾减去1
                    et_number.setText(number.substring(0, number.length() - 1));
                    //移动光标
                    et_number.setSelection(number.length() - 1);
                }
                break;

            case R.id.btn_query:
                //http://apis.juhe.cn/mobile/get?phone=13429667914&key=您申请的KEY
                L.d("R.id.btn_query");
//                String number = et_number.getText().toString().trim();
                L.d(number);
                String url = "http://apis.juhe.cn/mobile/get?phone=" + number + "&key=" + StaticClass.PHONE_KEY;

                //get请求简洁版实现
                RxVolley.get(url, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.d(t);
                        parsingJson(t);
                    }
                });
                break;

        }
    }

    //    {
//        "resultcode":"200",
//              "reason":"Return Successd!",
//              "result":{
//                  "province":"浙江",
//                  "city":"杭州",
//                  "areacode":"0571",
//                  "zip":"310000",
//                  "company":"移动",
//                  "card":""
//                  },
//              "error_code":0
//    }
    private void parsingJson(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            String province = result.getString("province");
            String city = result.getString("city");
            String areacode = result.getString("areacode");
            String zip = result.getString("zip");
            String company = result.getString("company");
            String card = result.getString("card");

            tv_result.setText("归属地：" + province + city + "\n"
                    + "区号：" + areacode + "\n"
                    + "邮编：" + zip + "\n"
                    + "运营商：" + company + "\n"
                    + "类型：" + card);

            //图片显示
            switch (company) {
                case "移动":
                    iv_company.setImageResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    iv_company.setImageResource(R.drawable.china_unicom);
                    break;
                case "电信" :
                    iv_company.setImageResource(R.drawable.china_telecom);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
