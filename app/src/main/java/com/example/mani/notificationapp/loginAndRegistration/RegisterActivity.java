package com.example.mani.notificationapp.loginAndRegistration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mani.notificationapp.main.MainActivity;
import com.example.mani.notificationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName, mEmail, mPassword;
    private String user_id, name;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        mName = findViewById(R.id.et_user_name);
        mEmail = findViewById(R.id.et_user_email_regitser);
        mPassword = findViewById(R.id.user_password_regitser);
        mProgressBar = findViewById(R.id.pb_register);
        Button mRegisterButton = findViewById(R.id.btn_register);
        Button mAlreadyAccount = findViewById(R.id.btn_account_already);

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
                mProgressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                name = mName.getText().toString();
                if (name.length() > 0) {
                    if (name.contains(" ")) {
                        String[] names = name.split(" ");
                        names[0] = names[0].substring(0, 1).toUpperCase() + names[0].substring(1).toLowerCase();
                        names[1] = names[1].substring(0, 1).toUpperCase() + names[1].substring(1).toLowerCase();
                        name = names[0] + " " + names[1];
                    } else {
                        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
                    }
                }

                final String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user_id = mAuth.getCurrentUser().getUid();

                                String token_id = FirebaseInstanceId.getInstance().getToken();

                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("name", name);
                                userMap.put("email", email);
                                userMap.put("usertype", "guest");
                                userMap.put("token_id", token_id);

                                mStore.collection("Users").document(user_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mProgressBar.setVisibility(View.GONE);
                                        sendToMain();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        TastyToast.makeText(getApplicationContext(), e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                                    }
                                });

                            } else {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                TastyToast.makeText(getApplicationContext(), task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                            }
                        }
                    });
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Make sure you have filled all credentials!", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                }
                break;
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
