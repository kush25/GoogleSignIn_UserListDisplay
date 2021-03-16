package com.hcl.googlesigninapp_miniapp;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.hcl.googlesigninapp_miniapp.R;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



    public class UserActivity extends AppCompatActivity {



        private final OkHttpClient client = new OkHttpClient();
        private RecyclerView mRecyclerView;
        private UserAdapter userAdapter;
        private ArrayList<User> mUserList;
        private RequestQueue mRequestQueue;
        private TextView rslt;
        private User[] users;
        GoogleSignInClient mGoogleSignInClient;

        int[] users_img_url  =
                {
                        R.drawable.user1,
                        R.drawable.user2,
                        R.drawable.user3,
                        R.drawable.user4,
                        R.drawable.user5,
                        R.drawable.user6,
                        R.drawable.user7,
                        R.drawable.user8,
                        R.drawable.user9,
                        R.drawable.user10

                };



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);


            mRecyclerView = findViewById(R.id.recycle_view);
            rslt = findViewById(R.id.rslt);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            TextView txtmyname= (TextView) findViewById(R.id.text_name);
            TextView txtmyemail = (TextView) findViewById(R.id.text_username);
            ImageView myimage = (ImageView) findViewById(R.id.image_user);

            Button sgnout = (Button) findViewById(R.id.signout_btns);

            mUserList = new ArrayList<>();


            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            sgnout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signOut();
                }
            });

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(UserActivity.this);

            if (acct != null) {
              String personName = acct.getDisplayName();

              String personEmail = acct.getEmail();
                Uri personPhoto = acct.getPhotoUrl();

                txtmyname.setText(personName);
                txtmyemail.setText(personEmail);
                Glide.with(this).load(String.valueOf(personPhoto)).into(myimage);



            }

            parseJSON();
        }


        private void signOut() {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(UserActivity.this,"Sign Out Success",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }

        public void parseJSON(){

            Request request = new Request.Builder().url("http://jsonplaceholder.typicode.com/users")
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rslt.setText("Failed due to some reasons");
                        }
                    });

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    if (response.isSuccessful()) {

                        final String myResponse = response.body().string();



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                User[] users = gson.fromJson(myResponse, User[].class);
                                for(User u : users) {
                                    mUserList.add(u);
                                    Log.i("USER", u.toString());


                                }

                                userAdapter = new UserAdapter(UserActivity.this,mUserList,users_img_url);
                                mRecyclerView.setAdapter(userAdapter);
                            }
                        });
                    }
                }
            });

        }

    }

