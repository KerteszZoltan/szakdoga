package com.eveldar.eveldar.kertesz_zoltan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    ImageView imv_cp_menu, imv_cp_active;
    EditText et_old_password, et_new_password, et_new_password_confirmation;
    Button btn_cp_save;
    private MenuBuilder menuBuilder;
    private View menu_show;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        imv_cp_active = findViewById(R.id.imv_cp_active);
        imv_cp_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent active = new Intent(ChangePassword.this, ActiveEvents.class);
                startActivity(active);
                finish();
            }
        });
        getMenu();

        btn_cp_save = findViewById(R.id.btn_cp_save);
        btn_cp_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }

    @SuppressLint("RestrictedApi")
    private void getMenu() {
        imv_cp_menu = findViewById(R.id.imv_cp_menu);
        menuBuilder = new MenuBuilder(ChangePassword.this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(ChangePassword.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(ChangePassword.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(ChangePassword.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(ChangePassword.this, ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(ChangePassword.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
                            case R.id.addnew:
                                Intent addnew = new Intent(ChangePassword.this,AddEventActivity.class);
                                startActivity(addnew);
                                finish();
                                return true;
                            case R.id.complete:
                                Intent complete = new Intent(ChangePassword.this,CompleteEventsActivity.class);
                                startActivity(complete);
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
        sharedPrefManager=new SharedPrefManager(ChangePassword.this);
        Intent intent = new Intent(ChangePassword.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(ChangePassword.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(ChangePassword.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(ChangePassword.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }
}