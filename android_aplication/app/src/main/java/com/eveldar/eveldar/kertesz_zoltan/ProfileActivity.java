package com.eveldar.eveldar.kertesz_zoltan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    ImageView menu_profil_show;
    MenuBuilder menuBuilder;
    EditText et_profil_email, et_profil_name;
    TextView test;
    Button save;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        menu_profil_show = (ImageView) findViewById(R.id.menu_profil_show);
        menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_profil_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu= new MenuPopupHelper(ProfileActivity.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){

                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionMenu.show();
            }
        });
        et_profil_name = findViewById(R.id.et_profile_name);
        et_profil_email = findViewById(R.id.et_profil_email);
        test = findViewById(R.id.test);
        profileGet();

    }

    public void profileGet() {
        String url = getString(R.string.api_server)+"profile";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(ProfileActivity.this,url);
                http.setToken(true);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code==200){
                            test.setText("asdf");
                        }
                        else{
                            test.setText(code.toString());
                        }
                    }
                });
            }
        }).start();
        /*String url = getString(R.string.api_server)+"profile";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(ProfileActivity.this, url);
                http.setToken(true);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code==200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String name = response.getString("name");
                                String email = response.getString("email");
                                et_profil_email.setText(name);
                                et_profil_name.setText(email);
                                test.setText("Teszt");

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(ProfileActivity.this,"Hiba"+code, Toast.LENGTH_SHORT);
                        }
                    }
                });

            }
        }).start();*/
    }
}