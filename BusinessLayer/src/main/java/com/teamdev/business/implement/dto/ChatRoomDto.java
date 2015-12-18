package com.teamdev.business.implement.dto;

public class ChatRoomDto {

    private long id;
    private String name;
    private int userCount;
    private long messageCount;

    public ChatRoomDto(long id, String name, int userCount, long messageCount) {
        this.id = id;
        this.name = name;
        this.userCount = userCount;
        this.messageCount = messageCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChatRoomDto{");
        sb.append("name='").append(name).append('\'');
        sb.append(", userCount=").append(userCount);
        sb.append(", messageCount=").append(messageCount);
        sb.append('}');
        return sb.toString();
    }
}
