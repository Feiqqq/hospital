package net.thumbtack.school.hospital.model;

import lombok.*;

@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private int id;
    public User(){

    }

    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

}
