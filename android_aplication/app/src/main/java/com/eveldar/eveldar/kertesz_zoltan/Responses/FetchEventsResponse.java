package com.eveldar.eveldar.kertesz_zoltan.Responses;

import com.eveldar.eveldar.kertesz_zoltan.Models.Event;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchEventsResponse {
    @SerializedName("events")
    List<Event> eventList;

    public FetchEventsResponse(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
