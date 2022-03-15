package com.eveldar.eveldar.kertesz_zoltan.Models;

public class Event {
    Integer id, complete;
    Integer user_id;
    String topic, description, start, end;

    public Event(Integer id, Integer user_id, String topic, String description, String start, String end, Integer complete) {
        this.id = id;
        this.user_id = user_id;
        this.topic = topic;
        this.description = description;
        this.start = start;
        this.end = end;
        this.complete=complete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }
}
