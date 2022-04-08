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

public class ActiveEvents extends AppCompatActivity {
    ImageView menu_show, btn_eventAdd;
    MenuBuilder menuBuilder;
    SharedPrefManager sharedPrefManager;
    RecyclerView rcv;
    List<Event> eventList;
    EventAdapter eventAdapter;
    Button btn_complete, btn_expired;
    TextView tw_no_active;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_events);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        sharedPrefManager=new SharedPrefManager(ActiveEvents.this);
        btn_eventAdd = findViewById(R.id.imv_active_add);
        btn_eventAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newadd = new Intent(ActiveEvents.this,AddEventActivity.class);
                startActivity(newadd);
                finish();
            }
        });
        menu_show = (ImageView) findViewById(R.id.active_menu_show);
        menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(ActiveEvents.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(ActiveEvents.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                getActiveEvents();
                                return true;
                            case R.id.complete:
                                Intent complete = new Intent(ActiveEvents.this,CompleteEventsActivity.class);
                                startActivity(complete);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(ActiveEvents.this,ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(ActiveEvents.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
                            case R.id.addnew:
                                Intent addnew = new Intent(ActiveEvents.this,AddEventActivity.class);
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
        rcv=findViewById(R.id.active);
        getActiveEvents();



    }

    private void getActiveEvents() {
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<FetchEventsResponse> call = RetrofitClient.getInstance().getApi().activeEvents(token);
        call.enqueue(new Callback<FetchEventsResponse>() {
            @Override
            public void onResponse(Call<FetchEventsResponse> call, Response<FetchEventsResponse> response) {
                eventList=response.body().getEventList();
                rcv.setLayoutManager(new LinearLayoutManager(ActiveEvents.this));
                eventAdapter = new EventAdapter(ActiveEvents.this, eventList);
                rcv.setAdapter(eventAdapter);
                if(eventList.isEmpty()){
                    tw_no_active = findViewById(R.id.tw_no_active);
                    tw_no_active.setVisibility(View.VISIBLE);
                    btn_complete = findViewById(R.id.btn_complete_list);
                    btn_expired = findViewById(R.id.btn_expired_list);
                    btn_complete.setVisibility(View.VISIBLE);
                    btn_expired.setVisibility(View.VISIBLE);
                    btn_complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent complete = new Intent(ActiveEvents.this, CompleteEventsActivity.class);
                            startActivity(complete);
                            finish();
                        }
                    });
                    btn_expired.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent expired = new Intent(ActiveEvents.this, ExpiredEventActivity.class);
                            startActivity(expired);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FetchEventsResponse> call, Throwable t) {
                Toast.makeText(ActiveEvents.this,"sikertelen hívás",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void logoutUser() {
        sharedPrefManager=new SharedPrefManager(ActiveEvents.this);
        Intent intent = new Intent(ActiveEvents.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(ActiveEvents.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(ActiveEvents.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(ActiveEvents.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }
}