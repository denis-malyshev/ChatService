package com.teamdev.persistence.dom;

import java.util.HashSet;
import java.util.Set;

public final class User {

    private long id;
    private String firstName;
    private String password;
    private String mail;
    private long tokenId;

    private Set<Long> chatRooms = new HashSet<Long>();

    public User() {
    }

    public User(String firstName, String password, String mail) {
        this.firstName = firstName;
        this.password = password;
        this.mail = mail;
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Long> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(Set<Long> chatRooms) {
        this.chatRooms = chatRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public java.lang.String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", firstName=").append(firstName);
        sb.append(", mail=").append(mail);
        sb.append('}');
        return sb.toString();
    }
}
