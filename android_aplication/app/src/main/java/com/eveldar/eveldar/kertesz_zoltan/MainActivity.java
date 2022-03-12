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

public class MainActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button btn_login;
    String email,password;
    LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);
        localStorage = new LocalStorage(this);

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
        Toast.makeText(this,"Bejelentkezés...", Toast.LENGTH_SHORT).show();
        JSONObject params= new JSONObject();
        try {
            params.put("email",email);
            params.put("password",password);
        } catch (JSONException e){
            e.printStackTrace();
        }
        String data = params.toString();
        String url=getString(R.string.api_server)+"login";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http=new Http(MainActivity.this,url);
                http.setMethod("post");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code= http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getRespose());
                                String token = response.getString("token");
                                localStorage.setToken(token);
                                Intent intent = new Intent(MainActivity.this,SecoundActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else if(code == 422){
                            try {
                                JSONObject response = new JSONObject(http.getRespose());
                                String msg = response.getString("message");
                                alertLogin(msg);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else if(code==401){
                            try{
                                JSONObject response = new JSONObject(http.getRespose());
                                String msg = response.getString("message");
                                alertLogin(msg);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        }).start();
    }
}