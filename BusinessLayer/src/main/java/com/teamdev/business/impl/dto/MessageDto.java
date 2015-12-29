package com.teamdev.business.impl.dto;

import java.time.LocalDateTime;

public class MessageDTO {

    private long id;
    private String text;
    private LocalDateTime time;

    public MessageDTO(long id, String text, LocalDateTime time) {
        this.id = id;
        this.text = text;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageDTO{");
        sb.append("text='").append(text).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
