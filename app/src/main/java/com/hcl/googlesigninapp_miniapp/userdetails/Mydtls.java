package com.hcl.googlesigninapp_miniapp.userdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hcl.googlesigninapp_miniapp.R;

import org.w3c.dom.Text;


import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.FAMILYNAME;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.GIVENNAME;
import static com.hcl.googlesigninapp_miniapp.userdetails.UserActivity.NAME;

public class Mydtls extends AppCompatActivity {


    public static final String NAMEUPDATED = "name";
    Button save;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydtls);


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

    }

