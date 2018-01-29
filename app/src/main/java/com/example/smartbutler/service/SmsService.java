package com.example.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.PixelCopy;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.example.smartbutler.view.DispatchLinearLayout;

public class SmsService extends Service implements View.OnClickListener {

    public static final String SYSTEM_DIALOGS_RESON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";
    private SmsReceiver smsReceiver;
    private String address;
    private String messageBody;
    private DispatchLinearLayout mView;
    private WindowManager wm;
    private HomeWatchReceiver homeWatchReceiver;

    public SmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    //初始化
    private void init() {
        L.d("init SmsService");
        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        intent.addAction(StaticClass.SMS_ACTION);
        intent.setPriority(Integer.MAX_VALUE);
        registerReceiver(smsReceiver, intent);

        homeWatchReceiver = new HomeWatchReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeWatchReceiver, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("stop SmsService");
        //注销
        unregisterReceiver(smsReceiver);
        unregisterReceiver(homeWatchReceiver);
    }



    class SmsReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_ACTION.equals(action)){
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                for (Object obj : objs) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                    address = smsMessage.getDisplayOriginatingAddress();
                    messageBody = smsMessage.getDisplayMessageBody();
                    L.d("address: " + address + "   messageBody: " + messageBody);
                }
                showWindow();
            }
        }
    }

    private void showWindow() {
        wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);

        TextView tv_phone = mView.findViewById(R.id.tv_phone);
        TextView tv_content = mView.findViewById(R.id.tv_content);
        Button btn_send_sms = mView.findViewById(R.id.btn_send_sms);

        tv_phone.setText("发件人：" + address);
        tv_content.setText(messageBody);
        btn_send_sms.setOnClickListener(this);

        wm.addView(mView, layoutParams);

        mView.setDispatchKeyEventListener(new DispatchLinearLayout.DispatchKeyEventListener() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){

                    if (mView.getParent() != null){
                        wm.removeView(mView);
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_sms :
                L.d("点击了 btn_send_sms");
                sendSms();
                if (mView.getParent() != null){
                    wm.removeView(mView);
                }
                break;
        }
    }

    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + address);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "private void sendSms() ");
        startActivity(intent);
    }

    class HomeWatchReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d("我点击了 home 键 --------- onReceive----- action:" + action);
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
                L.d("action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)---------" + action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
                String reson = intent.getStringExtra(SYSTEM_DIALOGS_RESON_KEY);
                L.d("reson-----" + reson);
                if (SYSTEM_DIALOGS_HOME_KEY.equals(reson)){
                    L.d("SYSTEM_DIALOGS_HOME_KEY.equals(reson)" + SYSTEM_DIALOGS_HOME_KEY.equals(reson));
                    L.d("HOME!!!!!!");
                    if (mView != null){
                        wm.removeView(mView);
                    }
                }
            }
        }
    }

}
