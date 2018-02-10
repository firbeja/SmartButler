package com.example.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

public class UpdateActivity extends AppCompatActivity {

    //正在下载
    public static final int HANDLE_LOADING = 10001;
    //下载完成
    public static final int HANDLE_SUCCESS = 10002;
    //下载失败
    public static final int HANDLE_ON = 10003;

    private TextView tv_size;
    private String path;
    private String url;
    private NumberProgressBar number_progress_bar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_LOADING:
                    Bundle data = msg.getData();
                    long transferredBytes = data.getLong("transferredBytes", 0);
                    long totalSize = data.getLong("totalSize", 0);
                    tv_size.setText(transferredBytes + " / " + totalSize);
                    int progress = (int) (((float)transferredBytes/(float)totalSize) * 100) ;
                    L.d("progress : " + progress);
                    number_progress_bar.setProgress(progress);
                    break;
                case HANDLE_SUCCESS:
                    tv_size.setText("下载完成");
                    //启动这个应用安装
                    startInstallApk();
                    break;
                case HANDLE_ON:
                    tv_size.setText("下载失败");
                    break;
            }
        }
    };

    private void startInstallApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initView();


    }

    private void initView() {
        tv_size = (TextView) findViewById(R.id.tv_size);
        number_progress_bar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        number_progress_bar.setMax(100);

        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";
        L.e(path);

        url = getIntent().getStringExtra("url");
        L.d("UpdateActivity -- url :" + url);
        if (!TextUtils.isEmpty(url)) {
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    L.d("transferredBytes: " + transferredBytes + "totalSize: " + totalSize);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putLong("transferredBytes", transferredBytes);
                    bundle.putLong("totalSize", totalSize);
                    msg.setData(bundle);
                    msg.what = HANDLE_LOADING;
                    handler.sendMessage(msg);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    handler.sendEmptyMessage(HANDLE_SUCCESS);
                }

                @Override
                public void onFailure(VolleyError error) {
                    handler.sendEmptyMessage(HANDLE_ON);
                }
            });
        }

    }

}
