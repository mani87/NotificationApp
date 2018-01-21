package com.example.mani.notificationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText mEmail = (EditText) findViewById(R.id.et_user_email);
        EditText mPassword = (EditText) findViewById(R.id.et_user_password);
        Button mLoginButton = (Button) findViewById(R.id.btn_login);
        Button mRegisterNot = (Button) findViewById(R.id.btn_not_registered);

        mRegisterNot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
