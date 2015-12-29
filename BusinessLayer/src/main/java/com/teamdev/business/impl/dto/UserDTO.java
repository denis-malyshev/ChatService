package com.teamdev.business.impl.dto;

public class UserDTO {

    private long id;
    private String firstName;
    private String mail;

    public UserDTO(long id, String firstName, String mail) {
        this.id = id;
        this.firstName = firstName;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDTO{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", mail='").append(mail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
