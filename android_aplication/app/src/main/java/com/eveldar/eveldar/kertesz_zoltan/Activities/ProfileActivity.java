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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.Responses.CheckTokenResponse;
import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LoginResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.ProfileResponse;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView menu_profil_show;
    MenuBuilder menuBuilder;
    EditText et_profile_email, et_profile_name;
    SharedPrefManager sharedPrefManager;
    Button btn_save;

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
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.profile:
                                profileGet();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(ProfileActivity.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.complete:
                                Intent complete = new Intent(ProfileActivity.this,CompleteEventsActivity.class);
                                startActivity(complete);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(ProfileActivity.this,ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(ProfileActivity.this,UpdateEventActivity.class);
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
        et_profile_name = findViewById(R.id.et_profile_name);
        et_profile_email = findViewById(R.id.et_profil_email);
        profileGet();

        btn_save=findViewById(R.id.btn_save_profil);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateProfile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void logoutUser() {
        sharedPrefManager=new SharedPrefManager(ProfileActivity.this);
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(ProfileActivity.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(ProfileActivity.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateProfile() throws IOException {
        String newName=et_profile_name.getText().toString();
        String newEmail= et_profile_email.getText().toString();
        if (newName.isEmpty()){
            et_profile_name.requestFocus();
            et_profile_name.setError("A név nem lehet üres");
        }
        if (newEmail.isEmpty()){
            et_profile_email.requestFocus();
            et_profile_email.setError("A emailcím nem lehet üres");
        }
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<CheckTokenResponse> callcheck = RetrofitClient.getInstance().getApi().checkToken(token,sharedPrefManager.getUser().getId());
        callcheck.enqueue(new Callback<CheckTokenResponse>() {
            @Override
            public void onResponse(Call<CheckTokenResponse> callCheck, Response<CheckTokenResponse> responseCheck) {
                Integer responseId = responseCheck.body().getId();
                Integer origId = sharedPrefManager.getUser().getId();
                if (responseId == origId){
                    Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateProfilWithoutPassword(token,newName,newEmail);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Toast.makeText( ProfileActivity.this,"Sikeres módosítás", Toast.LENGTH_SHORT).show();
                            profileGet();
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText( ProfileActivity.this,"Sikertelen módosítás", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    logoutUser();
                }
            }

            @Override
            public void onFailure(Call<CheckTokenResponse> callCheck, Throwable tCheck) {
                logoutUser();
            }
        });

    }

    private void profileGet() {
        sharedPrefManager=new SharedPrefManager(ProfileActivity.this);

        String name = sharedPrefManager.getUser().getName();
        String email = sharedPrefManager.getUser().getEmail();
        et_profile_name.setText(name);
        et_profile_email.setText(email);
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<ProfileResponse> call = RetrofitClient.getInstance().getApi().profilData(token);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                et_profile_email.setText(response.body().getUser().getEmail());
                et_profile_name.setText(response.body().getUser().getName());
                Toast.makeText( ProfileActivity.this,"Profil betöltés...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });

    }
}