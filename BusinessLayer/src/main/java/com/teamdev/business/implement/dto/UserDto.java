package com.teamdev.business.implement.dto;

public class UserDto {

    private String firstName;
    private String mail;
    private String birthday;

    public UserDto(String firstName, String mail, String birthday) {
        this.firstName = firstName;
        this.mail = mail;
        this.birthday = birthday;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", mail='").append(mail).append('\'');
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append('}');
        return sb.toString();
    }
}