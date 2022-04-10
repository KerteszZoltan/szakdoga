package com.eveldar.eveldar.kertesz_zoltan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.eveldar.eveldar.kertesz_zoltan.EventAdapter;
import com.eveldar.eveldar.kertesz_zoltan.Models.Event;
import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.FetchEventsResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpiredEventActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    ImageView menu_show, addEvent, imv_expired_main;
    MenuBuilder menuBuilder;
    RecyclerView rcv;
    List<Event> eventList;
    EventAdapter eventAdapter;
    Button btn_exp_active, btn_exp_complete;
    TextView tw_no_exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired_event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        imv_expired_main = findViewById(R.id.imv_expired_main);
        imv_expired_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent active = new Intent(ExpiredEventActivity.this, ActiveEvents.class);
                startActivity(active);
                finish();
            }
        });
        sharedPrefManager=new SharedPrefManager(ExpiredEventActivity.this);
        getMenu();
        getExpiredEvent();
        getAddEvent();
    }

    private void getAddEvent() {
        addEvent= findViewById(R.id.imv_expired_add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEvent=new Intent(ExpiredEventActivity.this,AddEventActivity.class);
                startActivity(addEvent);
                finish();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void getMenu() {
        menu_show = (ImageView) findViewById(R.id.expired_menu_show);
        menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(ExpiredEventActivity.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(ExpiredEventActivity.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(ExpiredEventActivity.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.complete:
                                Intent complete = new Intent(ExpiredEventActivity.this, CompleteEventsActivity.class);
                                startActivity(complete);
                                finish();
                                return true;
                            case R.id.expired:
                                getExpiredEvent();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(ExpiredEventActivity.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
                            case R.id.addnew:
                                Intent addnew = new Intent(ExpiredEventActivity.this,AddEventActivity.class);
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

    private void getExpiredEvent() {
        rcv = findViewById(R.id.rcv_expired);
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<FetchEventsResponse> expiredEvent = RetrofitClient.getInstance().getApi().expiredEvent(token);
        expiredEvent.enqueue(new Callback<FetchEventsResponse>() {
            @Override
            public void onResponse(Call<FetchEventsResponse> call, Response<FetchEventsResponse> response) {
                eventList=response.body().getEventList();
                rcv.setLayoutManager(new LinearLayoutManager(ExpiredEventActivity.this));
                eventAdapter = new EventAdapter(ExpiredEventActivity.this, eventList);
                rcv.setAdapter(eventAdapter);
                if (eventList.isEmpty()){
                    tw_no_exp = findViewById(R.id.tw_exp_no_exp);
                    tw_no_exp.setVisibility(View.VISIBLE);
                    btn_exp_active=findViewById(R.id.btn_exp_active_list);
                    btn_exp_active.setVisibility(View.VISIBLE);
                    btn_exp_active.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent active = new Intent(ExpiredEventActivity.this, ActiveEvents.class);
                            startActivity(active);
                            finish();
                        }
                    });
                    btn_exp_complete = findViewById(R.id.btn_exp_complete_list);
                    btn_exp_complete.setVisibility(View.VISIBLE);
                    btn_exp_complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent complete = new Intent(ExpiredEventActivity.this, CompleteEventsActivity.class);
                            startActivity(complete);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FetchEventsResponse> call, Throwable t) {
                Toast.makeText(ExpiredEventActivity.this,"sikertelen hívás", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutUser() {
        sharedPrefManager=new SharedPrefManager(ExpiredEventActivity.this);
        Intent intent = new Intent(ExpiredEventActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(ExpiredEventActivity.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(ExpiredEventActivity.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(ExpiredEventActivity.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }
}