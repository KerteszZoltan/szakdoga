package com.eveldar.eveldar.kertesz_zoltan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.ResponseEvent;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEventActivity extends AppCompatActivity {
    ConstraintLayout update;
    EditText eventUpTopic, eventUpDesc, eventUpStart, eventUpEnd, etUpdaterId;
    SharedPrefManager sharedPrefManager;
    Button updateCheck, updateEvent, deleteEvent;
    CheckBox eventUpComplete;
    ImageView menu_show, imv_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        sharedPrefManager=new SharedPrefManager(UpdateEventActivity.this);
        imv_main = findViewById(R.id.imv_update_main);
        imv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent secound = new Intent(UpdateEventActivity.this, SecoundActivity.class);
                startActivity(secound);
                finish();
            }
        });

        update = findViewById(R.id.updater_layout);
        updateCheck= findViewById(R.id.btn_checkId);
        etUpdaterId = findViewById(R.id.et_updater_id);

        update.setVisibility(View.INVISIBLE);
        updateCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventId = etUpdaterId.getText().toString();
                if (eventId.isEmpty()){
                    recreate();
                }
                else {
                    getEventData();
                }
            }
        });

        getMenu();

    }

    @SuppressLint("RestrictedApi")
    private void getMenu() {
        menu_show = (ImageView) findViewById(R.id.update_event_show);
        MenuBuilder menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.popup, menuBuilder);
        menu_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper optionMenu=new MenuPopupHelper(UpdateEventActivity.this, menuBuilder, view);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.profile:
                                Intent profile = new Intent(UpdateEventActivity.this, ProfileActivity.class);
                                startActivity(profile);
                                finish();
                                return true;
                            case R.id.logout:
                                logoutUser();
                                return true;
                            case R.id.active:
                                Intent active = new Intent(UpdateEventActivity.this, ActiveEvents.class);
                                startActivity(active);
                                finish();
                                return true;
                            case R.id.complete:
                                Intent complete = new Intent(UpdateEventActivity.this,CompleteEventsActivity.class);
                                startActivity(complete);
                                finish();
                                return true;
                            case R.id.expired:
                                Intent expired = new Intent(UpdateEventActivity.this,ExpiredEventActivity.class);
                                startActivity(expired);
                                finish();
                                return true;
                            case R.id.event_update:
                                Intent update = new Intent(UpdateEventActivity.this,UpdateEventActivity.class);
                                startActivity(update);
                                finish();
                                return true;
                            case R.id.addnew:
                                Intent addnew = new Intent(UpdateEventActivity.this,AddEventActivity.class);
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

    private void logoutUser() {
        sharedPrefManager=new SharedPrefManager(UpdateEventActivity.this);
        Intent intent = new Intent(UpdateEventActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(UpdateEventActivity.this,"Kijelentkezés...", Toast.LENGTH_LONG).show();
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        Call<LogoutResponse> call = RetrofitClient.getInstance().getApi().logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                String res = response.body().getMessage().toString();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
                Toast.makeText(UpdateEventActivity.this,res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(UpdateEventActivity.this,"nem törölt token",Toast.LENGTH_SHORT).show();
                sharedPrefManager.logout();
                startActivity(intent);
                finish();
            }
        });
    }

    private void getEventData() {
        String getIdFromForm = etUpdaterId.getText().toString();
        Integer parsedId = Integer.parseInt(getIdFromForm);
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        eventUpTopic = findViewById(R.id.et_event_up_name);
        eventUpDesc = findViewById(R.id.et_event_up_desc);
        eventUpStart = findViewById(R.id.et_event_up_start);
        eventUpEnd = findViewById(R.id.et_event_up_end);
        eventUpComplete = findViewById(R.id.chb_evnt_up_comp);
        updateEvent = findViewById(R.id.btn_event_up);


        Call<ResponseEvent> call = RetrofitClient.getInstance().getApi().specifiedEvent(token, parsedId);
        call.enqueue(new Callback<ResponseEvent>() {
            @Override
            public void onResponse(Call<ResponseEvent> call, Response<ResponseEvent> response) {
                etUpdaterId.setInputType(InputType.TYPE_NULL);
                etUpdaterId.setEnabled(false);
                etUpdaterId.setFocusable(false);
                etUpdaterId.setFocusableInTouchMode(false);
                updateCheck.setVisibility(View.INVISIBLE);
                eventUpTopic.setText(response.body().getEvent().getTopic());
                eventUpDesc.setText(response.body().getEvent().getDescription());
                eventUpStart.setText(response.body().getEvent().getStart());
                eventUpStart.setInputType(InputType.TYPE_NULL);
                eventUpStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateTimeStartPickerDialog();
                    }
                });
                eventUpEnd.setText(response.body().getEvent().getEnd());
                eventUpEnd.setInputType(InputType.TYPE_NULL);
                eventUpEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateTimeEndPickerDialog();
                    }
                });
                Integer complete = response.body().getEvent().getComplete();
                Boolean completeB = null;
                if (complete==0) {
                    completeB=Boolean.FALSE;
                }else if (complete == 1 ){
                    completeB=Boolean.TRUE;
                }
                eventUpComplete.setChecked(completeB);
                update.setVisibility(View.VISIBLE);

                updateEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer sendComplete = 0;
                        String sendTopic = eventUpTopic.getText().toString();
                        String sendDescription = eventUpDesc.getText().toString();
                        String sendStart=eventUpStart.getText().toString();
                        String sendEnd=eventUpEnd.getText().toString();
                        if (eventUpComplete.isChecked()){
                            sendComplete = 1;
                        }
                        if(sendTopic.isEmpty()){
                            eventUpTopic.requestFocus();
                            eventUpTopic.setError("A név nem lehet üres");
                        }else {
                            Call<ResponseEvent> callEvent = RetrofitClient.getInstance().getApi().updateEvent(token, parsedId, sendTopic, sendDescription, sendStart, sendEnd, sendComplete);
                            callEvent.enqueue(new Callback<ResponseEvent>() {
                                @Override
                                public void onResponse(Call<ResponseEvent> call, Response<ResponseEvent> response) {
                                    Toast.makeText(UpdateEventActivity.this, "Sikeres módosítás", Toast.LENGTH_SHORT).show();
                                    getEventData();
                                }

                                @Override
                                public void onFailure(Call<ResponseEvent> call, Throwable t) {
                                    Toast.makeText(UpdateEventActivity.this, "Sikertelen módosítás!", Toast.LENGTH_SHORT).show();
                                    eventUpStart.requestFocus();
                                    eventUpStart.setError("A dátum nem lehet korábbi az aktuálisnál!");
                                    eventUpEnd.requestFocus();
                                    eventUpEnd.setError("A dátum nem lehet korábbi mint a kezdés és az aktuális dátum");

                                }
                            });
                        }
                    }
                });
                deleteEvent = findViewById(R.id.btn_event_del);
                deleteEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String token = "Bearer "+sharedPrefManager.getUser().getToken();
                        String deleteID=etUpdaterId.getText().toString();
                        Call<ResponseEvent> callDelete = RetrofitClient.getInstance().getApi().deleteEvent(token,deleteID);
                        callDelete.enqueue(new Callback<ResponseEvent>() {
                            @Override
                            public void onResponse(Call<ResponseEvent> call, Response<ResponseEvent> response) {
                                recreate();
                                Toast.makeText(UpdateEventActivity.this,"A törlés sikeres!", Toast.LENGTH_SHORT).show();
                                Intent secound = new Intent(UpdateEventActivity.this, SecoundActivity.class);
                                startActivity(secound);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseEvent> call, Throwable t) {
                                Toast.makeText(UpdateEventActivity.this,"Sikertelen törlés", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseEvent> call, Throwable t) {
                Toast.makeText(UpdateEventActivity.this,"Hibás azonosító", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateTimeEndPickerDialog() {
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
                        eventUpEnd.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(UpdateEventActivity.this, timeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(UpdateEventActivity.this, dateSetListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void showDateTimeStartPickerDialog() {
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
                        eventUpStart.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(UpdateEventActivity.this, timeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(UpdateEventActivity.this, dateSetListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}