package com.example.mani.notificationapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private Button mLoginButton, mRegisterNot;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentMain);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.et_user_email);
        mPassword = (EditText) findViewById(R.id.et_user_password);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mRegisterNot = (Button) findViewById(R.id.btn_not_registered);

        mAuth = FirebaseAuth.getInstance();

        mRegisterNot.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_not_registered:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendToMain();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

        }

    }
}
