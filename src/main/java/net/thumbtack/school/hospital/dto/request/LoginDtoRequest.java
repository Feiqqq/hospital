package net.thumbtack.school.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDtoRequest {
    private String login;
    private String password;

    public LoginDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
