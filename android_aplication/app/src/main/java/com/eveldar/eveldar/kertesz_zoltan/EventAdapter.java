package com.eveldar.eveldar.kertesz_zoltan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eveldar.eveldar.kertesz_zoltan.Activities.UpdateEventActivity;
import com.eveldar.eveldar.kertesz_zoltan.Models.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    Context context;
    List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList){
        this.context=context;
        this.eventList=eventList;
    };


    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.eventId.setText("Esemény azonosító: "+eventList.get(position).getId());
        holder.topic.setText("Esemény neve: "+eventList.get(position).getTopic());
        holder.idSend.setText(eventList.get(position).getId().toString());
        Integer id = Integer.parseInt(holder.idSend.getText().toString());
        String sendID = String.valueOf(id);
        if (eventList.get(position).getDescription() != null) {
            holder.description.setText("Esemény leírása: " + eventList.get(position).getDescription());
        }else{
            holder.description.setHeight(1);
        }
        holder.start.setText("Esemény kezdete: "+eventList.get(position).getStart());
        holder.end.setText("Esemény vége: "+eventList.get(position).getEnd());

        holder.event_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uniqItem = new Intent(context, UpdateEventActivity.class);
                uniqItem.putExtra("id", sendID);
                uniqItem.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(uniqItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView topic,description,start,end, eventId,idSend;
        LinearLayout event_item_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventId = itemView.findViewById(R.id.tw_event_id);
            topic = itemView.findViewById(R.id.tw_event_topic);
            description = itemView.findViewById(R.id.tw_event_desc);
            start = itemView.findViewById(R.id.tw_event_start);
            end = itemView.findViewById(R.id.tw_event_end);
            event_item_layout = itemView.findViewById(R.id.event_item_layout);
            idSend = itemView.findViewById(R.id.tw_ev_id_s);
        }
    }
}