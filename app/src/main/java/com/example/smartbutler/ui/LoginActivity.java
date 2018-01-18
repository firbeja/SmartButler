package com.example.smartbutler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistered;
    private Button btnLogin;
    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private CheckBox cbKeepPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegistered = (Button) findViewById(R.id.btn_registered);
        btnRegistered.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        etLoginUsername = (EditText) findViewById(R.id.et_login_username);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);

        cbKeepPassword = (CheckBox) findViewById(R.id.cb_keep_password);

        boolean isChecked = ShareUtils.getBoolean(this, "cbKeepPassword", false);
        if (isChecked){
            String loginUserName = ShareUtils.getString(this, "loginUserName", "");
            String loginPassword = ShareUtils.getString(this, "loginPassword", "");
            etLoginUsername.setText(loginUserName);
            etLoginPassword.setText(loginPassword);
            cbKeepPassword.setChecked(isChecked);
        }else {
            //TODO
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registered :
                startActivity(new Intent(LoginActivity.this, RegisteredActivity.class));
                break;
            case R.id.btn_login :
                String loginUsername = etLoginUsername.getText().toString().trim();
                String loginPassword = etLoginPassword.getText().toString().trim();
                //之后再利用 Bomb 的代码去 "登录"

                MyUser user = new MyUser();
                user.setUsername(loginUsername);
                user.setPassword(loginPassword);
                user.login(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (e == null){
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "登录失败" + e.toString(), Toast.LENGTH_SHORT).show();
                            L.d("登录失败--------------------- 》》》》》》》》》》》" + e.toString());
                        }
                    }
                });

                break;
        }
    }

    //当用户离开登录界面，保存用户名，密码。并勾选了 "记住密码"。
    @Override
    protected void onDestroy() {

        boolean isChecked = cbKeepPassword.isChecked();
        ShareUtils.putBoolean(this, "cbKeepPassword", isChecked);
        if (isChecked){
            ShareUtils.putString(this, "loginUserName", etLoginUsername.getText().toString().trim());
            ShareUtils.putString(this, "loginPassword", etLoginPassword.getText().toString().trim());
        }else {
            ShareUtils.delete(this, "loginUserName");
            ShareUtils.delete(this, "loginPassword");
        }
        super.onDestroy();
    }
}
