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
import android.widget.ImageView;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity {

    ImageView menu_show, imv_add_event;
    private MenuBuilder menuBuilder;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        getMenu();

    }

    @SuppressLint("RestrictedApi")
    private void getMenu() {
        menu_show = (ImageView) findViewById(R.id.imv_add_menu);
        menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(AddEventActivity.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(AddEventActivity.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(AddEventActivity.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(AddEventActivity.this, ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(AddEventActivity.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
                            case R.id.addnew:
                                Intent addnew = new Intent(AddEventActivity.this,AddEventActivity.class);
                                startActivity(addnew);
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
    }

    private void logoutUser() {
        sharedPrefManager=new SharedPrefManager(AddEventActivity.this);
        Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(AddEventActivity.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(AddEventActivity.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(AddEventActivity.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }
}