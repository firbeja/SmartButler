package com.example.smartbutler.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;
import com.example.smartbutler.utils.L;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etAge;
    private EditText etDesc;
    private RadioGroup mRadioGroup;
    private EditText etPass;
    private EditText etPassword;
    private EditText etEmail;
    private Button btnRegisteredUser;
    private boolean isGender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }else {
            initView();
        }

//        initView();


    }

    private void initView() {

        etUser = (EditText) findViewById(R.id.et_user);
        etAge = (EditText) findViewById(R.id.et_age);
        etDesc = (EditText) findViewById(R.id.et_desc);
        etPass = (EditText) findViewById(R.id.et_pass);
        etPassword = (EditText) findViewById(R.id.et_password);
        etEmail = (EditText) findViewById(R.id.et_email);

        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);

        btnRegisteredUser = (Button) findViewById(R.id.btn_registered_user);
        btnRegisteredUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registered_user:

                //获取到输入框的值
                String name = etUser.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                //判断输入框是否为空  !TextUtils.isEmpty(email)
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(pass) &
                        !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(email)) {


                    //判断两次输入的密码是否一致
                    if (pass.equals(password)) {

                        //先把性别判断一下
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                if (checkedId == R.id.rb_boy) {
                                    isGender = true;
                                } else {
                                    isGender = false;
                                }
                            }
                        });

                        //判断简介是否为空
                        if (!TextUtils.isEmpty(desc)) {
                            desc = "z这个人很懒什么也没有留下。";
                        }

                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setAge(Integer.parseInt(age));
                        user.setEmail(email);
                        user.setDesc(desc);
                        user.setSex(isGender);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisteredActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisteredActivity.this, "注册失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                                    L.d(e.toString() + "----------");
                                }
                                L.d("-----------------------");
                            }
                        });

                        L.d("sssss");

                    } else {
                        Toast.makeText(RegisteredActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(RegisteredActivity.this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        L.i("权限");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
