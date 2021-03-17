package com.hcl.googlesigninapp_miniapp.userdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcl.googlesigninapp_miniapp.R;

import org.w3c.dom.Text;


import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.EXTRA_NAME;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.EXTRA_USERNAME;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.EXTRA_EMAIL;

import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.EXTRA_PHONE;

public class UserDetailsActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Intent intent = getIntent();

        String name = intent.getStringExtra(EXTRA_NAME);
        String username = intent.getStringExtra(EXTRA_USERNAME);
        String email = intent.getStringExtra(EXTRA_EMAIL);
        String phone = intent.getStringExtra(EXTRA_PHONE);

        TextView nm = (TextView) findViewById(R.id.text_view_name_detail);
        TextView unm = (TextView) findViewById(R.id.text_view_username_detail);
        TextView eml = (TextView) findViewById(R.id.text_view_email_detail);
        TextView phns = (TextView) findViewById(R.id.text_view_phone_detail);


        nm.setText(name);
        unm.setText(username);
        eml.setText(email);
        phns.setText(phone);

    }



}