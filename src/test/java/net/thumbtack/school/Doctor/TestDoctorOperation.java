package net.thumbtack.school.Doctor;


import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.dto.response.TokenDtoResponse;
import net.thumbtack.school.hospital.model.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDoctorOperation extends TestBase {
    @Test
    public void TestRegDoctor() {
        Assertions.assertEquals(200, server.regDoctor(doctorJson1).getResponseCode());
        Assertions.assertEquals(400, server.regDoctor(doctorJson1).getResponseCode());
        Assertions.assertEquals(200, server.regDoctor(doctorJson2).getResponseCode());
        Assertions.assertEquals(400, server.regDoctor(doctorJson2).getResponseCode());
        Assertions.assertEquals(400,server.regDoctor(doctorJson4).getResponseCode());
        Assertions.assertEquals(400,server.regDoctor(doctorJson5).getResponseCode());
        Assertions.assertEquals(400,server.regDoctor(doctorJson6).getResponseCode());
    }

    @Test
    public void addPatient() {
        server.regDoctor(doctorJson1);
        server.regDoctor(doctorJson2);
        Assertions.assertEquals(200, server.addPatient(json.fromJson(server.login(login).getResponseData(), TokenDtoResponse.class).getToken(), patient).getResponseCode());
        Assertions.assertEquals(200, server.addPatient(json.fromJson(server.login(login2).getResponseData(), TokenDtoResponse.class).getToken(), patient).getResponseCode());
    }

    @Test
    public void deleteDoctor() {
        server.regDoctor(doctorJson1);
        server.regDoctor(doctorJson2);
        server.regDoctor(doctorJson3);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient);
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient2);
        Assertions.assertEquals(200, server.deleteDoctor(json.fromJson(token,TokenDtoResponse.class).getToken()).getResponseCode());
        Assertions.assertEquals(200, server.deleteDoctor(json.fromJson(server.login(login2).getResponseData(),TokenDtoResponse.class).getToken()).getResponseCode());
    }
    @Test
    public void addTreatment(){
        server.regDoctor(doctorJson1);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(),patient);
        PatientAndTreatment patientAndTreatment = new PatientAndTreatment(1,"Парацетамол","3","Прогревание лёгких","");
        PatientAndTreatment patientAndTreatment1 = new PatientAndTreatment(1,"","3","Прогревание лёгких","Понедельник");
        Assertions.assertEquals(200,server.addTreatment(json.fromJson(token,TokenDtoResponse.class).getToken(),json.toJson(patientAndTreatment,PatientAndTreatment.class)).getResponseCode());
        Assertions.assertEquals(200,server.addTreatment(json.fromJson(token,TokenDtoResponse.class).getToken(),json.toJson(patientAndTreatment1,PatientAndTreatment.class)).getResponseCode());
    }
    @Test
    public void getAllMyPatients(){
        server.regDoctor(doctorJson1);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient);
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient2);
        Assertions.assertEquals(resultJson,server.getAllMyPatients(json.fromJson(token,TokenDtoResponse.class).getToken()).getResponseData());
    }
}
