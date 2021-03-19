package com.hcl.googlesigninapp_miniapp.userdetails;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.hcl.googlesigninapp_miniapp.MainActivity;
import com.hcl.googlesigninapp_miniapp.R;
import com.skydoves.balloon.Balloon;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.hcl.googlesigninapp_miniapp.userdetails.NotifyApp.CHANNEL_ID;


public class UserActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

        SharedPreferences sharedPreferences;

        private final OkHttpClient client = new OkHttpClient();
        private RecyclerView mRecyclerView;
        private UserAdapter userAdapter;
        private ArrayList<User> mUserList;
        private TextView rslt;
        private User[] users;
        GoogleSignInClient mGoogleSignInClient;

        public static final String MY_TAG = "message";

        NotificationManagerCompat notificationManager;

        public static final String EXTRA_IMG = "image";
        public static final String EXTRA_NAME = "name";
        public static final String EXTRA_USERNAME = "username";
        public static final String EXTRA_EMAIL = "email";
        public static final String EXTRA_PHONE = "phone";

        public static final String NAME = "name";
        public static final String  GIVENNAME = "givenname";
        public static final String  FAMILYNAME = "familyname";

        public static Boolean notifying = true;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);

            notifying = true;


            mRecyclerView = findViewById(R.id.recycle_view);
            rslt = findViewById(R.id.rslt);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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

            MyDetails();
            parseJSON();

            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);

            }

        }




    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("My_TAG","on Restart is called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MY_TAG","On Pause Called");
    }

    public void notifyMe() {


        String title = "Rememebr Me";
        String message ="Dont Forget to come back";

        Intent activityIntent =  new Intent(this,UserActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent  = PendingIntent.getActivity(
                this,
                0,
                activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this,CHANNEL_ID);
        notificationbuilder.setSmallIcon(R.drawable.user1);
        notificationbuilder.setContentTitle(title);
        notificationbuilder.setContentText(message);
        notificationbuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationbuilder.setContentIntent(contentIntent);
        notificationbuilder.setAutoCancel(true);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify("mytag",1,  notificationbuilder.build());

    }


    @Override
    protected void onStop() {
        super.onStop();

        if(notifying) {

            Log.i("My_TAG","on Stop is Called");
            notifyMe();
        }
    }




        private void signOut() {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            notifying=false;
                            Toast.makeText(UserActivity.this,"Sign Out Success",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }




        public void MyDetails(){

            CardView cardview = (CardView) findViewById(R.id.card_view);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(UserActivity.this);

            if (acct != null) {

                String personName = acct.getDisplayName();

                String personEmail = acct.getEmail();
                Uri personPhoto = acct.getPhotoUrl();

                TextView txtmyname= (TextView) findViewById(R.id.text_name);
                TextView txtmyemail = (TextView) findViewById(R.id.text_username);
                ImageView myimage = (ImageView) findViewById(R.id.image_user);


               txtmyname.setText(personName);
                txtmyemail.setText(personEmail);
                Glide.with(this).load(String.valueOf(personPhoto)).into(myimage);

                sharedPreferences = getSharedPreferences("myedit", Context.MODE_PRIVATE);
                if (sharedPreferences.contains("myname")) {

                    String updated = sharedPreferences.getString("myname","Done");
                    txtmyname.setText(updated);

                }

                cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mydts = new Intent(UserActivity.this,Mydtls.class);

                        mydts.putExtra(NAME,acct.getDisplayName());
                        mydts.putExtra(GIVENNAME,acct.getGivenName());
                        mydts.putExtra(FAMILYNAME,acct.getFamilyName());

                        startActivity(mydts);
                        notifying=false;
                    }
                });

            }

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
                               userAdapter = new UserAdapter(UserActivity.this,mUserList);
                              //  userAdapter = new UserAdapter(UserActivity.this,mUserList,users_img_url);
                                mRecyclerView.setAdapter(userAdapter);
                               userAdapter.setOnClickListener(UserActivity.this::onItemClick);
                            }
                        });
                    } }
            });
        }



        @Override
        public void onItemClick(int position) {
            Intent userdts = new Intent(UserActivity.this,UserDetailsActivity.class);
            User clickedDetails = mUserList.get(position);
            userdts.putExtra(EXTRA_NAME,clickedDetails.getName());
            userdts.putExtra(EXTRA_USERNAME,clickedDetails.getUsername());
            userdts.putExtra(EXTRA_EMAIL,clickedDetails.getEmail());
            userdts.putExtra(EXTRA_PHONE,clickedDetails.getPhone());
            startActivity(userdts);
            notifying=false;

        }

    }

