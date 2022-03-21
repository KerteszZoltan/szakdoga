package com.eveldar.eveldar.kertesz_zoltan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, final int position) {
        holder.eventId.setText("Esemény azonosító: "+eventList.get(position).getId());
        holder.topic.setText("Esemény neve: "+eventList.get(position).getTopic());
        if (eventList.get(position).getDescription() != null) {
            holder.description.setText("Esemény leírása: " + eventList.get(position).getDescription());
        }else{
            holder.description.setHeight(1);
        }
        holder.start.setText("Esemény kezdete: "+eventList.get(position).getStart());
        holder.end.setText("Esemény vége: "+eventList.get(position).getEnd());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView topic,description,start,end, eventId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventId = itemView.findViewById(R.id.tw_event_id);
            topic = itemView.findViewById(R.id.tw_event_topic);
            description = itemView.findViewById(R.id.tw_event_desc);
            start = itemView.findViewById(R.id.tw_event_start);
            end = itemView.findViewById(R.id.tw_event_end);

        }
    }
}