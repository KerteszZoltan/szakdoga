package com.eveldar.eveldar.kertesz_zoltan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.ResponseEvent;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity {

    ImageView menu_show, imv_add_start, imv_add_end;
    EditText et_topic,et_description;
    Button btn_add_event;
    TextView tw_start, tw_end;
    private MenuBuilder menuBuilder;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        et_topic = findViewById(R.id.et_add_event_topic);
        et_description = findViewById(R.id.et_add_event_desc);
        tw_start = findViewById(R.id.tw_picked_start);
        tw_end = findViewById(R.id.tw_picked_end);
        btn_add_event = findViewById(R.id.btn_add_event);


        imv_add_start = findViewById(R.id.imv_add_start);
        imv_add_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeStartDialog();
            }
        });


        imv_add_end = findViewById(R.id.imv_add_end);
        imv_add_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeEndDialog();
            }
        });

        btn_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topic =et_topic.getText().toString();
                String description = et_description.getText().toString();
                String sendStart = tw_start.getText().toString();
                String sendEnd=tw_end.getText().toString();
                sharedPrefManager=new SharedPrefManager(AddEventActivity.this);
                String token = "Bearer "+sharedPrefManager.getUser().getToken();
                if (topic.isEmpty()){
                    et_topic.requestFocus();
                    et_topic.setError("Nem lehet üres az esemény neve!");
                }
                if (sendStart.isEmpty()){
                    tw_start.requestFocus();
                    tw_start.setError("");
                    tw_start.setText("Meg kell adni kezdési dátumot!");
                }
                if (sendEnd.isEmpty()){
                    tw_end.requestFocus();
                    tw_end.setError("");
                    tw_end.setText("Meg kell adni befejezési dátumot!");
                }
                else{
                    Toast.makeText(AddEventActivity.this, "A mentés folyamatban...", Toast.LENGTH_SHORT).show();
                    Call<ResponseEvent> addEventActivityCall = RetrofitClient.getInstance().getApi().addEvent(token,topic, description,sendStart,sendEnd);
                    addEventActivityCall.enqueue(new Callback<ResponseEvent>() {
                        @Override
                        public void onResponse(Call<ResponseEvent> call, Response<ResponseEvent> response) {
                            Toast.makeText(AddEventActivity.this, "A mentés sikeresen megtörtént!", Toast.LENGTH_SHORT).show();
                            Intent active = new Intent(AddEventActivity.this,ActiveEvents.class);
                            startActivity(active);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseEvent> call, Throwable t) {
                            Toast.makeText(AddEventActivity.this, "A mentés sikertelen...", Toast.LENGTH_SHORT).show();
                            tw_start.requestFocus();
                            tw_start.setError("");
                            tw_start.setText("Nem lehet a dátum korábbi a mai napnál!");
                            tw_end.requestFocus();
                            tw_end.setError("");
                            tw_end.setText("A befejezési dátum nem lehet korábban, mint a kezdés!");
                        }
                    });
                }
            }
        });
        getMenu();
    }

    private void showDateTimeStartDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        tw_start.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(AddEventActivity.this, timeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(AddEventActivity.this, dateSetListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDateTimeEndDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        tw_end.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(AddEventActivity.this, timeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(AddEventActivity.this, dateSetListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @SuppressLint("RestrictedApi")
    private void getMenu() {
        menu_show = (ImageView) findViewById(R.id.imv_add_menu);
        menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(AddEventActivity.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(AddEventActivity.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(AddEventActivity.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(AddEventActivity.this, ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(AddEventActivity.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
                            case R.id.addnew:
                                Intent addnew = new Intent(AddEventActivity.this,AddEventActivity.class);
                                startActivity(addnew);
                                finish();
                                return true;
                            case R.id.complete:
                                Intent complete = new Intent(AddEventActivity.this,CompleteEventsActivity.class);
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
        sharedPrefManager=new SharedPrefManager(AddEventActivity.this);
        Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(AddEventActivity.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(AddEventActivity.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(AddEventActivity.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }
}