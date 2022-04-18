package com.eveldar.eveldar.kertesz_zoltan.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.Responses.LoginResponse;
import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button btn_login;
    ImageView imv_openBrowser;
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
        imv_openBrowser = findViewById(R.id.imv_open_browser);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        imv_openBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BASE_URL = "192.168.185.106";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+BASE_URL+":80/eveldar/public"));
                startActivity(browserIntent);
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
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(email,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse.getUser()!=null){
                    sharedPrefManager.saveUser(loginResponse.getUser());
                    Toast.makeText(MainActivity.this, "Bejelentkezés...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ActiveEvents.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Hibás e-mail cím / jelszó páros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Kapcsolódási hiba", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPrefManager.isLoggedIn()){
            Intent intent = new Intent(MainActivity.this, ActiveEvents.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}