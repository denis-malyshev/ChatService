package com.teamdev.persistence.dom;

import java.util.HashSet;
import java.util.Set;

public final class ChatRoom {

    private long id;
    private String name;
    private Set<Long> messages = new HashSet<Long>();
    private Set<Long> users = new HashSet<Long>();

    public ChatRoom() {
    }

    public ChatRoom(String name) {
        this.name = name;
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

    public Set<Long> getMessages() {
        return messages;
    }

    public void setMessages(Set<Long> messages) {
        this.messages = messages;
    }

    public Set<Long> getUsers() {
        return users;
    }

    public void setUsers(Set<Long> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoom chatRoom = (ChatRoom) o;

        return id == chatRoom.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChatRoom{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
