package com.example.smartbutler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartbutler.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegistered = (Button) findViewById(R.id.btn_registered);
        btnRegistered.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registered :
                startActivity(new Intent(LoginActivity.this, RegisteredActivity.class));
                break;
        }
    }
}
