package com.teamdev.persistence.dom;

import java.time.LocalDateTime;

public final class Message {

    private long id;
    private LocalDateTime time;
    private String text;
    private long senderId;
    private long receiverId;
    private long chatId;

    public Message() {
    }

    public Message(String text, long senderId, long chatId) {
        this.text = text;
        this.senderId = senderId;
        this.chatId = chatId;
    }

    public Message(String text, long senderId, long receiverId, long chatId) {
        this.text = text;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatId = chatId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("id=").append(id);
        sb.append(", time=").append(time);
        sb.append(", text='").append(text).append('\'');
        sb.append(", senderId=").append(senderId);
        sb.append(", chatId=").append(chatId);
        sb.append('}');
        return sb.toString();
    }
}
