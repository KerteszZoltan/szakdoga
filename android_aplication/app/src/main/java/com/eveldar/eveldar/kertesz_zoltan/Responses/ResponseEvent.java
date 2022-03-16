package com.eveldar.eveldar.kertesz_zoltan.Responses;

import com.eveldar.eveldar.kertesz_zoltan.Models.Event;
import com.google.gson.annotations.SerializedName;

public class ResponseEvent {
    @SerializedName("events")
    Event event;

    public ResponseEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
