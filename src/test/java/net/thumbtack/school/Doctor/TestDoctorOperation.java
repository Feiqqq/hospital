package net.thumbtack.school.Doctor;

import com.google.gson.Gson;
import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.response.TokenDtoResponse;
import net.thumbtack.school.hospital.server.Server;
import net.thumbtack.school.hospital.server.ServerResponse;
// REVU исключения сервера клиенту (тесту) недоступны, это внутренность сервера
import net.thumbtack.school.hospital.server.exceptions.ServerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDoctorOperation extends TestBase {
    @Test
    public void TestRegDoctor() {
        Assertions.assertEquals(200, server.regDoctor(doctor).getResponseCode());
        Assertions.assertEquals(400, server.regDoctor(doctor).getResponseCode());
        Assertions.assertEquals(200, server.regDoctor(doctor2).getResponseCode());
        Assertions.assertEquals(400, server.regDoctor(doctor2).getResponseCode());

    }

    @Test
    public void addPatient() {
        server.regDoctor(doctor);
        server.regDoctor(doctor2);
        Assertions.assertEquals(200, server.addPatient(json.fromJson(server.login(login).getResponseData(), TokenDtoResponse.class).getToken(), patient).getResponseCode());
        Assertions.assertEquals(200, server.addPatient(json.fromJson(server.login(login2).getResponseData(), TokenDtoResponse.class).getToken(), patient).getResponseCode());
    }

    @Test
    public void deleteDoctor() {
        server.regDoctor(doctor);
        server.regDoctor(doctor2);
        server.regDoctor(doctor3);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient);
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient2);
        Assertions.assertEquals(200, server.deleteDoctor(json.fromJson(token,TokenDtoResponse.class).getToken()).getResponseCode());
        Assertions.assertEquals(200, server.deleteDoctor(json.fromJson(server.login(login2).getResponseData(),TokenDtoResponse.class).getToken()).getResponseCode());
    }
    @Test
    public void addTreatment(){
        server.regDoctor(doctor);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(),patient);
        PatientAndTreatment patientAndTreatment = new PatientAndTreatment("dimasik","Парацетамол","3","Прогревание лёгких","");
        PatientAndTreatment patientAndTreatment1 = new PatientAndTreatment("dimasik","","3","Прогревание лёгких","Понедельник");
        Assertions.assertEquals(200,server.addTreatment(json.fromJson(token,TokenDtoResponse.class).getToken(),json.toJson(patientAndTreatment,PatientAndTreatment.class)).getResponseCode());
        Assertions.assertEquals(200,server.addTreatment(json.fromJson(token,TokenDtoResponse.class).getToken(),json.toJson(patientAndTreatment1,PatientAndTreatment.class)).getResponseCode());
    }
    @Test
    public void getAllMyPatients() throws ServerException {
        server.regDoctor(doctor);
        String token = server.login(login).getResponseData();
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient);
        server.addPatient(json.fromJson(token,TokenDtoResponse.class).getToken(), patient2);
        Assertions.assertEquals(resultJson,server.getAllMyPatients(json.fromJson(token,TokenDtoResponse.class).getToken()).getResponseData());
    }
}
