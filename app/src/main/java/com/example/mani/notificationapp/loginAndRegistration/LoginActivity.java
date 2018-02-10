package com.example.mani.notificationapp.loginAndRegistration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.mani.notificationapp.main.MainActivity;
import com.example.mani.notificationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            sendToMain();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        mEmail = findViewById(R.id.et_user_email);
        mPassword = findViewById(R.id.et_user_password);
        final Button loginButton = findViewById(R.id.btn_login);
        Button registerNot = findViewById(R.id.btn_not_registered);
        mProgressBar = findViewById(R.id.pb_login_activity);

        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEmail.getText().length() > 0 && mPassword.getText().length() > 0) {
                    loginButton.setBackgroundResource(R.drawable.btn_dark);
                } else {
                    loginButton.setBackgroundResource(R.drawable.disable_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };


        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        registerNot.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        mEmail.addTextChangedListener(mTextWatcher);
        mPassword.addTextChangedListener(mTextWatcher);
        
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btn_not_registered:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                addValidationToViews();
                LoginProcess();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mProgressBar.setVisibility(View.GONE);
        super.onBackPressed();
    }

    private void sendToMain() {
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentMain);
        finish();
    }

    private void LoginProcess() {
        mProgressBar.setVisibility(View.VISIBLE);
        if (awesomeValidation.validate()) {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String token_id = FirebaseInstanceId.getInstance().getToken();
                            String current_id = mAuth.getCurrentUser().getUid();

                            Map<String, Object> tokenMap = new HashMap<>();
                            tokenMap.put("token_id", token_id);

                            mFirestore.collection("Users").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProgressBar.setVisibility(View.GONE);
                                    sendToMain();
                                }
                            });

                        } else {
                            mProgressBar.setVisibility(View.GONE);
                            TastyToast.makeText(getApplicationContext(), task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                        }
                    }
                });

            } else {
                mProgressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Make sure you have filled all credentials!", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
            }
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(LoginActivity.this, R.id.et_user_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.validate();
    }
}
