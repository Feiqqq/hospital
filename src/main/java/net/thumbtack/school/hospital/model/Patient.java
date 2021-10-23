package net.thumbtack.school.hospital.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient extends User{

    private String nameOfDisease;
    private Doctor doctor;

    public Patient(String firstName, String lastName, String login, String password,String nameOfDisease) {
        super(firstName, lastName, login, password);
        this.nameOfDisease = nameOfDisease;
    }

}
