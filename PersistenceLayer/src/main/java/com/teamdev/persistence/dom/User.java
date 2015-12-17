package com.teamdev.persistence.dom;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public final class User {

    private long id;
    private String firstName;
    private String password;
    private String mail;
    private AuthenticationToken token;
    private Set<Message> messages = new HashSet<Message>();

    private Set<ChatRoom> chatRooms = new HashSet<ChatRoom>();

    public User() {
    }

    public User(String firstName, String password, String mail) {
        this.firstName = firstName;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes(), 0, password.length());
            this.password = new BigInteger(1, md5.digest()).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.mail = mail;
    }

    public AuthenticationToken getToken() {
        return token;
    }

    public void setToken(AuthenticationToken token) {
        this.token = token;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
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
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes(), 0, password.length());
            this.password = new BigInteger(1, md5.digest()).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(Set<ChatRoom> chatRooms) {
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        return result;
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
