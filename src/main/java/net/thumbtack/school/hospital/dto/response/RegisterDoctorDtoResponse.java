package net.thumbtack.school.hospital.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDoctorDtoResponse {
    private String name;
    private String login;
    private String password;
}
