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
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
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
        et_old_password = findViewById(R.id.et_old_password);
        et_new_password = findViewById(R.id.et_new_password);
        et_new_password_confirmation = findViewById(R.id.et_new_password_confirm);
        btn_cp_save = findViewById(R.id.btn_cp_save);
        btn_cp_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager=new SharedPrefManager(ChangePassword.this);
                String password= et_old_password.getText().toString();
                String newPassword = et_new_password.getText().toString();
                String newPasswordConfirm = et_new_password_confirmation.getText().toString();
                if (password.isEmpty()){
                    et_old_password.setError("A jelszó nem lehet üres!");
                }else if (password.length()<6){
                    et_old_password.setError("A jelszó nem lehet 6 karakternél rövidebb!");
                }else if (newPassword.isEmpty()){
                    et_new_password.setError("Az új jelszó nem lehet üres!");
                }else if (newPassword.length()<6){
                    et_new_password.setError("Az új jelszó nem lehet 6 karakternél rövidebb!");
                }else if(newPasswordConfirm.isEmpty()) {
                    et_new_password_confirmation.setError("Meg kell erősíteni a beállítani kívánt jelszót!");
                }else if (password.equals(newPassword)){
                    et_new_password.requestFocus();
                    et_new_password.setError("Az új és a régi jelszó nem egyezhet meg!");
                }else if (!newPassword.equals(newPasswordConfirm)){
                    et_new_password.setError("");
                    et_new_password_confirmation.setError("A két jelszó nem egyezik meg!");
                }else {
                    String token = "Bearer " + sharedPrefManager.getUser().getToken();
                    Call<LogoutResponse> savePassword = RetrofitClient.getInstance().getApi().updatePassword(token, password, newPassword, newPasswordConfirm);
                    savePassword.enqueue(new Callback<LogoutResponse>() {
                        @Override
                        public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                            String res = response.body().getMessage();
                            Toast.makeText(ChangePassword.this, res, Toast.LENGTH_SHORT).show();
                            logoutUser();
                        }

                        @Override
                        public void onFailure(Call<LogoutResponse> call, Throwable t) {
                            Toast.makeText(ChangePassword.this, "A módosítás sikertelen!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @SuppressLint("RestrictedApi")
    private void getMenu() {
        imv_cp_menu = findViewById(R.id.imv_cp_menu);
        menuBuilder = new MenuBuilder(ChangePassword.this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        imv_cp_menu.setOnClickListener(new View.OnClickListener() {
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
        sharedPrefManager.logout();
        startActivity(intent);
        finish();
    }
}