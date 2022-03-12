package com.eveldar.kertesz_zoltan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button btn_login;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);

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
        Toast.makeText(this,"Bejelentkezés...", Toast.LENGTH_LONG).show();
    }
}