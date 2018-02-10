package com.example.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class QrCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        ImageView iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);
        //屏幕宽度
        int width = getResources().getDisplayMetrics().widthPixels;

        String contentString = "我是智能管家";
        if (!contentString.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, width/2, width/2,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            iv_qr_code.setImageBitmap(qrCodeBitmap);
        } else {
            Toast.makeText(this, "Text can not be empty", Toast.LENGTH_SHORT).show();
        }

    }
}
