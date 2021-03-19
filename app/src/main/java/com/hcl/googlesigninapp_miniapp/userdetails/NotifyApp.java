package com.hcl.googlesigninapp_miniapp.userdetails;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotifyApp extends Application {

    public static final String CHANNEL_ID= "channelid";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,"channel", NotificationManager.IMPORTANCE_DEFAULT);

            channel1.setDescription("Rememeber Me");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }

    }
}
