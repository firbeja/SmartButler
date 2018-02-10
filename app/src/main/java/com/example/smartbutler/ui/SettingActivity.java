package com.example.smartbutler.ui;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.ui
 * 文件名：SettingActivity
 * 创建者：LB
 * 创建时间：2018/1/17 下午2:38
 * 描述：   设置
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.service.SmsService;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Switch sw_speak;
    private Switch sw_sms;
    private String versionName;
    private int versionCode;
    private int jsonVersionCode;
    private String jsonContent;
    private String jsonUrl;
    private String jsonVersionName;
    //扫一扫
    private LinearLayout ll_scan;
    private TextView tv_scan_result;
    //生成二维码
    private LinearLayout ll_qr_code;
    //我的位置
    private LinearLayout ll_my_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sw_speak = (Switch) findViewById(R.id.sw_speak);
        boolean isSpeak = ShareUtils.getBoolean(SettingActivity.this, "isSpeak", false);
        sw_speak.setChecked(isSpeak);
//        sw_speak.setOnClickListener(this);

        //既然是switch，那么你监听的不应该是click，而是OnCheckedChangeListener。
        sw_speak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                sw_speak.setChecked(!sw_speak.isChecked());
                ShareUtils.putBoolean(SettingActivity.this, "isSpeak", sw_speak.isChecked());
                boolean isSpeak = ShareUtils.getBoolean(SettingActivity.this, "isSpeak", false);
                L.d(sw_speak.isChecked() + "------isSpeak---" + isSpeak);
            }
        });

        //短信提醒
        sw_sms = (Switch) findViewById(R.id.sw_sms);
        //设置 开关 sw_sms 的状态
        boolean isSms = ShareUtils.getBoolean(SettingActivity.this, "isSms", false);
        sw_sms.setChecked(isSms);

        sw_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtils.putBoolean(SettingActivity.this, "isSms", sw_sms.isChecked());
                boolean isSms = ShareUtils.getBoolean(SettingActivity.this, "isSms", false);
                L.d("isSms:-------- " + isSms);
                if (sw_sms.isChecked()){
                    startService(new Intent(SettingActivity.this, SmsService.class));
                }else {
                    stopService(new Intent(SettingActivity.this, SmsService.class));
                }
            }
        });

        //版本更新
        TextView tv_version = (TextView) findViewById(R.id.tv_version);
        try {
            getVersionNameCode();
            tv_version.setText("检测版本：" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tv_version.setText("检测版本：");
        }
        LinearLayout ll_update = (LinearLayout) findViewById(R.id.ll_update);
        ll_update.setOnClickListener(this);

        //扫一扫
        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        ll_scan.setOnClickListener(this);
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);

        //生成二维码
        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
        ll_qr_code.setOnClickListener(this);

        //我的位置
        ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
        ll_my_location.setOnClickListener(this);

    }

    private void getVersionNameCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
        versionName = packageInfo.versionName;
        versionCode = packageInfo.versionCode;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_update :
                /**
                 * 步骤：
                 * 1.从服务器拿到 配置信息 config.json
                 * 2.比较 versionCode
                 * 3.Dialog 提示更新
                 * 4.拿到 url ，并将其传递到 UpdateActivity
                 */
                RxVolley.get(StaticClass.CHECK_UPDATE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.d("config.json---- " + t);
                        parsingJson(t);
                    }
                });

                break;

            //扫一扫
            case R.id.ll_scan :
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;

            //生成二维码
            case R.id.ll_qr_code :
                startActivity(new Intent(this, QrCodeActivity.class));
                break;

            //我的位置
            case R.id.ll_my_location :
                startActivity(new Intent(this, LocationActivity.class));
                break;

        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            jsonVersionName = jsonObject.getString("versionName");
            jsonVersionCode = jsonObject.getInt("versionCode");
            jsonContent = jsonObject.getString("content");
            jsonUrl = jsonObject.getString("url");

            if (versionCode < jsonVersionCode){
                showUpdateDialog();
            }else {
                Toast.makeText(this, "当前是最新版本", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showUpdateDialog() {
        new AlertDialog.Builder(this)
                .setTitle("有新版本可更新")
                .setMessage(jsonContent)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
                        intent.putExtra("url", jsonUrl);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //什么也不做，也会执行dismis方法
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            tv_scan_result.setText(scanResult);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("title", "扫一扫");
            intent.putExtra("url", scanResult);
            startActivity(intent);
        }
    }
}
