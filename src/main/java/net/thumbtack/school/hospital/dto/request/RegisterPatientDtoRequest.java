package net.thumbtack.school.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPatientDtoRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String nameOfDisease;

    public RegisterPatientDtoRequest(String firstName, String lastName, String login, String password,String nameOfDisease) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.nameOfDisease = nameOfDisease;
    }
}
