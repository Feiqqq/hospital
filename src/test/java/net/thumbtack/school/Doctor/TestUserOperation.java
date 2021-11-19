package net.thumbtack.school.Doctor;

import net.thumbtack.school.hospital.dto.response.TokenDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUserOperation extends TestBase{
    @Test
    public void changePass()  {
        server.regDoctor(doctorJson1);
        server.regDoctor(doctorJson2);
        Assertions.assertEquals(200,server.changePass(json.fromJson(server.login(login).getResponseData(), TokenDtoResponse.class).getToken(), password).getResponseCode());
        Assertions.assertEquals(200,server.changePass(json.fromJson(server.login(login2).getResponseData(),TokenDtoResponse.class).getToken(),password).getResponseCode());
    }
    @Test
    public void logoutUser() {
        server.regDoctor(doctorJson2);
        server.regDoctor(doctorJson1);
        Assertions.assertEquals(200,server.logout(json.fromJson(server.login(login2).getResponseData(),TokenDtoResponse.class).getToken()).getResponseCode());
        Assertions.assertEquals(200,server.logout(json.fromJson(server.login(login).getResponseData(),TokenDtoResponse.class).getToken()).getResponseCode());
    }
}
