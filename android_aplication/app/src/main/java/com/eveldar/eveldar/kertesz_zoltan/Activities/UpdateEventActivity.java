package com.eveldar.eveldar.kertesz_zoltan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eveldar.eveldar.kertesz_zoltan.Models.Event;
import com.eveldar.eveldar.kertesz_zoltan.R;
import com.eveldar.eveldar.kertesz_zoltan.Responses.FetchEventsResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.ResponseEvent;
import com.eveldar.eveldar.kertesz_zoltan.RetrofitClient;
import com.eveldar.eveldar.kertesz_zoltan.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEventActivity extends AppCompatActivity {
    ConstraintLayout update;
    EditText eventUpTopic, eventUpDesc, eventUpStart, eventUpEnd, etUpdaterId;
    SharedPrefManager sharedPrefManager;
    Button updateCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        sharedPrefManager=new SharedPrefManager(UpdateEventActivity.this);

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


    }

    private void getEventData() {
        String getIdFromForm = etUpdaterId.getText().toString();
        Integer parsedId = Integer.parseInt(getIdFromForm);
        String token = "Bearer "+sharedPrefManager.getUser().getToken();
        eventUpTopic = findViewById(R.id.et_event_up_name);
        eventUpDesc = findViewById(R.id.et_event_up_desc);
        eventUpStart = findViewById(R.id.et_event_up_start);
        eventUpEnd = findViewById(R.id.et_event_up_end);

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
                eventUpEnd.setText(response.body().getEvent().getEnd());
                update.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseEvent> call, Throwable t) {
                Toast.makeText(UpdateEventActivity.this,"Hibás azonosító", Toast.LENGTH_SHORT).show();
            }
        });
    }
}