package com.example.mani.notificationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mUserListView;
    private UsersRecyclerAdapter mAdapter;
    private List<Users> usersList;
    private Button mLogoutButton;
    private TextView mProfileName;
    private FirebaseFirestore mFirestore;
    private String mUserId;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        usersList.clear();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            sendToLogin();
        } else {
            mFirestore.collection("Users").addSnapshotListener(MainActivity.this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String user_id = doc.getDocument().getId();

                            Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                            usersList.add(users);

                            mAdapter.notifyDataSetChanged();

                        }

                    }

                }
            });
        }
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mUserId = mAuth.getCurrentUser().getUid();
        }


        mLogoutButton = (Button) findViewById(R.id.btn_logout);
        mProfileName = (TextView) findViewById(R.id.tv_profile_name);

        usersList = new ArrayList<>();
        mAdapter = new UsersRecyclerAdapter(usersList);

        mUserListView = (RecyclerView) findViewById(R.id.rv_users);
        mUserListView.setHasFixedSize(true);
        mUserListView.setLayoutManager(new LinearLayoutManager(this));
        mUserListView.setAdapter(mAdapter);


        if (currentUser != null) {
            mFirestore.collection("Users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String user_name = documentSnapshot.getString("name");
                    mProfileName.setText(user_name);
                }
            });
        }

        mLogoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Map<String, Object> tokenRemove = new HashMap<>();
        tokenRemove.put("token_id", FieldValue.delete());

        mFirestore.collection("Users").document(mUserId).update(tokenRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAuth.signOut();
                Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
            }
        });

    }
}
