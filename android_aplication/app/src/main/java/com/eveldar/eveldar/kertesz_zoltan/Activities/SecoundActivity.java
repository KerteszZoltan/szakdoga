package com.eveldar.eveldar.kertesz_zoltan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.ProfileResponse;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecoundActivity extends AppCompatActivity {
ImageView menu_show;
TextView tw_name;
MenuBuilder menuBuilder;
SharedPrefManager sharedPrefManager;
Button btn_newEvent;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secound);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        menu_show = (ImageView) findViewById(R.id.show_menu);
        menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(SecoundActivity.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(SecoundActivity.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(SecoundActivity.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.complete:
                                Intent complete = new Intent(SecoundActivity.this,CompleteEventsActivity.class);
                                startActivity(complete);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(SecoundActivity.this,ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(SecoundActivity.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
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

        tw_name=findViewById(R.id.tw_name);
        getProfile();

        btn_newEvent = findViewById(R.id.btn_new_event);
        btn_newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEvent = new Intent(SecoundActivity.this, AddEventActivity.class);
                startActivity(addEvent);
                finish();
            }
        });
    }

    private void getProfile() {
        sharedPrefManager=new SharedPrefManager(SecoundActivity.this);

        String name = sharedPrefManager.getUser().getName();
        tw_name.setText(name);
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<ProfileResponse> call = RetrofitClient.getInstance().getApi().profilData(token);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                tw_name.setText(response.body().getUser().getName());
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void logoutUser() {
        sharedPrefManager=new SharedPrefManager(SecoundActivity.this);
        Intent intent = new Intent(SecoundActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(SecoundActivity.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(SecoundActivity.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(SecoundActivity.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }
}