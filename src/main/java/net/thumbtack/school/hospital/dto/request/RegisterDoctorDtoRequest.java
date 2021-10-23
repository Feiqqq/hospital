package net.thumbtack.school.hospital.dto.request;

import lombok.Data;

@Data
public class RegisterDoctorDtoRequest {
    private String firstName;
    private String lastName;
    private String specialty;
    private String login;
    private String password;

    public RegisterDoctorDtoRequest(String firstName, String lastName, String specialty, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.login = login;
        this.password = password;
    }
}
