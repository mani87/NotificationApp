package com.example.mani.notificationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName, mEmail, mPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = (EditText) findViewById(R.id.et_user_name);
        mEmail = (EditText) findViewById(R.id.et_user_email_regitser);
        mPassword = (EditText) findViewById(R.id.user_password_regitser);
        Button mRegisterButton = (Button) findViewById(R.id.btn_register);
        Button mAlreadyAccount = (Button) findViewById(R.id.btn_account_already);

        mAlreadyAccount.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_account_already:
                finish();
                break;
            case R.id.btn_register:
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                }
        }
    }
}
