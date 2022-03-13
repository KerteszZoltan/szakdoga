package com.eveldar.eveldar.kertesz_zoltan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button btn_login;
    String email,password;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        email=et_email.getText().toString();
        password=et_password.getText().toString();
        if (email.isEmpty() || password.isEmpty()){
            alertLogin("Az email cím vagy jelszó nem lehet üres");
        } else {
            sendLogin();
        }
    }

    private void alertLogin(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Hiba a bejelentkezésnél")
                .setIcon(R.drawable.exclamation_mark)
                .setMessage(s)
                .setPositiveButton("Rendben", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void sendLogin() {
        String user_email = email;
        String user_password = password;
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(email,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful()){
                    sharedPrefManager.saveUser(loginResponse.getUser());
                    Toast.makeText(MainActivity.this, "Bejelentkezés...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SecoundActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Hibás adat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hibás email cím / jelszó páros", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sharedPrefManager.isLoggedIn()){
            Intent intent = new Intent(MainActivity.this, SecoundActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}