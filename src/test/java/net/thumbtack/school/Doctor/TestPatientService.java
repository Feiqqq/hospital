package net.thumbtack.school.Doctor;

import net.thumbtack.school.hospital.dto.response.TokenDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPatientService extends TestBase{
    @Test
    public void getMyTreatment(){
        server.regDoctor(doctorJson1);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token, TokenDtoResponse.class).getToken(), patient);
        String patientToken = server.login(patientLog).getResponseData();
        Assertions.assertEquals(patientRes,server.getMyTreatment(json.fromJson(patientToken,TokenDtoResponse.class).getToken()).getResponseData());
    }
    @Test
    public void getMyDoctor(){
        server.regDoctor(doctorJson1);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token, TokenDtoResponse.class).getToken(), patient);
        String patientToken = server.login(patientLog).getResponseData();
        Assertions.assertEquals(200,server.getMyDoctor(json.fromJson(patientToken,TokenDtoResponse.class).getToken()).getResponseCode());
    }
}
