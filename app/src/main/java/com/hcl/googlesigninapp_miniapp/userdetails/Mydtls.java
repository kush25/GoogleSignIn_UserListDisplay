package com.hcl.googlesigninapp_miniapp.userdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.hcl.googlesigninapp_miniapp.R;

import org.w3c.dom.Text;


import static com.hcl.googlesigninapp_miniapp.userdetails.NotifyApp.CHANNEL_ID;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.FAMILYNAME;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.GIVENNAME;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.NAME;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.notifying;

public class Mydtls extends AppCompatActivity {


    public static final String NAMEUPDATED = "name";
    Button save;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydtls);
        notifying=false;

        Intent intent = getIntent();

        String name = intent.getStringExtra(NAME);
        String givenname = intent.getStringExtra(GIVENNAME);
        String familyname = intent.getStringExtra(FAMILYNAME);


        EditText nm = (EditText) findViewById(R.id.edit_name);
        EditText givennm = (EditText) findViewById(R.id.edit_givenname);
        EditText familynm = (EditText) findViewById(R.id.edit_familyname);


        nm.setText(name);
        givennm.setText(givenname);
        familynm.setText(familyname);

        boolean flag = getIntent().getBooleanExtra("flag",false);

        sharedpreferences = getSharedPreferences("myedit", Context.MODE_PRIVATE);

        save = (Button) findViewById(R.id.save_btns);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatednm = nm.getText().toString();
                nm.setText(updatednm);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("myname", updatednm);
                editor.apply();
                Toast.makeText(Mydtls.this,"Saved Successfully",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notifying = true;
        Log.i("My_TAG","on Restart is called");
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
    protected void onPause() {
        super.onPause();
        notifying=true;
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        if(notifying) {
//
//            Log.i("My_TAG","on Stop is Called");
//            notifyMe();
//        }
//    }



}


