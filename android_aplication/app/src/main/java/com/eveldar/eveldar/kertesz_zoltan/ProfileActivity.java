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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView menu_profil_show;
    MenuBuilder menuBuilder;
    EditText et_profile_email, et_profile_name;
    SharedPrefManager sharedPrefManager;
    Button btn_save;
    TextView test;

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
        et_profile_name = findViewById(R.id.et_profile_name);
        et_profile_email = findViewById(R.id.et_profil_email);
        test = findViewById(R.id.test);
        profileGet();

        btn_save=findViewById(R.id.btn_save_profil);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
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
        Integer id = sharedPrefManager.getUser().getId();
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateProfilWithoutPassword(id,newName,newEmail);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse updateResponse= response.body();
                if (response.isSuccessful()) {
                    sharedPrefManager.saveUser(updateResponse.getUser());
                    Toast.makeText(ProfileActivity.this, "Sikeres módosítás", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileActivity.this, "Sikertelen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Sikertelen hívás", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void profileGet() {
        sharedPrefManager=new SharedPrefManager(ProfileActivity.this);
        String name = sharedPrefManager.getUser().getName();
        String email = sharedPrefManager.getUser().getEmail();
        String teszt = sharedPrefManager.getUser().getToken();
        et_profile_name.setText(name);
        et_profile_email.setText(email);
        test.setText(teszt);
    }
}