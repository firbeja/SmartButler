package com.example.smartbutler.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.utils.StaticClass;
import com.example.smartbutler.utils.UtilTools;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                    break;
            }
        }
    };

    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

    }

    private void initView() {
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 500);

        TextView tvSplash = (TextView) findViewById(R.id.tv_splash);
        TextView tvSplashNext = (TextView) findViewById(R.id.tv_splash_next);
//        Typeface fontType = Typeface.createFromAsset(getAssets(), "fonts/FONT.TTF");
//        tvSplash.setTypeface(fontType);
        UtilTools.setFont(this, tvSplash);
        UtilTools.setFont(this, tvSplashNext);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
