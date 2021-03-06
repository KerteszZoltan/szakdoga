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

public class CompleteEventsActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    ImageView menu_show, imv_active_add, imv_complete_main;
    MenuBuilder menuBuilder;
    RecyclerView rcv;
    List<Event> eventList;
    EventAdapter eventAdapter;
    Button btn_active_list, btn_expired_list;
    TextView tw_no_comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_events);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        imv_complete_main = findViewById(R.id.imv_complete_main);
        imv_complete_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent active = new Intent(CompleteEventsActivity.this, ActiveEvents.class);
                startActivity(active);
                finish();
            }
        });
        sharedPrefManager=new SharedPrefManager(CompleteEventsActivity.this);
        getMenu();
        getCompleteEvents();
        addEventIcon();
    }

    private void addEventIcon() {
        imv_active_add = findViewById(R.id.imv_active_add_event);
        imv_active_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEvent = new Intent(CompleteEventsActivity.this, AddEventActivity.class);
                startActivity(addEvent);
                finish();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void getMenu() {
        menu_show = (ImageView) findViewById(R.id.complete_menu_show);
        menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(CompleteEventsActivity.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(CompleteEventsActivity.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(CompleteEventsActivity.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(CompleteEventsActivity.this, ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(CompleteEventsActivity.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
                            case R.id.addnew:
                                Intent addnew = new Intent(CompleteEventsActivity.this,AddEventActivity.class);
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

    private void getCompleteEvents() {
        rcv = findViewById(R.id.rcv_complete);
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<FetchEventsResponse> completeEvents = RetrofitClient.getInstance().getApi().completeEvents(token);
        completeEvents.enqueue(new Callback<FetchEventsResponse>() {
            @Override
            public void onResponse(Call<FetchEventsResponse> call, Response<FetchEventsResponse> response) {
                eventList=response.body().getEventList();
                rcv.setLayoutManager(new LinearLayoutManager(CompleteEventsActivity.this));
                eventAdapter = new EventAdapter(CompleteEventsActivity.this, eventList);
                rcv.setAdapter(eventAdapter);
                if (eventList.isEmpty()){
                    tw_no_comp = findViewById(R.id.tw_comp_no);
                    tw_no_comp.setVisibility(View.VISIBLE);
                    btn_active_list = findViewById(R.id.btn_comp_active);
                    btn_active_list.setVisibility(View.VISIBLE);
                    btn_active_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent active = new Intent(CompleteEventsActivity.this, ActiveEvents.class);
                            startActivity(active);
                            finish();
                        }
                    });
                    btn_expired_list = findViewById(R.id.btn_comp_expired);
                    btn_expired_list.setVisibility(View.VISIBLE);
                    btn_expired_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent expired = new Intent(CompleteEventsActivity.this, ExpiredEventActivity.class);
                            startActivity(expired);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FetchEventsResponse> call, Throwable t) {
                Toast.makeText(CompleteEventsActivity.this,"sikertelen h??v??s", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutUser() {
        sharedPrefManager=new SharedPrefManager(CompleteEventsActivity.this);
        Intent intent = new Intent(CompleteEventsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(CompleteEventsActivity.this,"Kijelentkez??s...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(CompleteEventsActivity.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(CompleteEventsActivity.this,"nem t??r??lt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }
}