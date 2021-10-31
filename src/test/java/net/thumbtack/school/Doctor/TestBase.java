package net.thumbtack.school.Doctor;

import com.google.gson.Gson;
import net.thumbtack.school.hospital.database.Database;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.server.Server;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

public class TestBase {
    @BeforeEach
    public void deleteAll(){
        Database.getInstance().deleteAll();
    }
    protected Server server = new Server();
    protected Gson json = new Gson();
    protected final String password = UUID.randomUUID().toString();
    protected final String resultJson = "[{\"doctorId\":\"1\",\"firstName\":\"Дмитрий\",\"lastName\":\"Федотов\",\"login\":\"dimasik\",\"password\":\"frol1234\",\"nameOfDisease\":\"ОРВИ\",\"proceduresAndMedications\":[]},{\"firstName\":\"Иван\",\"lastName\":\"Соколов\",\"login\":\"ivan43\",\"password\":\"kepor\",\"nameOfDisease\":\"ОРВИ\",\"proceduresAndMedications\":[]}]";
    protected final String doctor = json.toJson(new RegisterDoctorDtoRequest("Василий","Флоров","Терапевт","vas1969","1969vas"));
    protected final String doctor2 = json.toJson(new RegisterDoctorDtoRequest("Иван","Тарелкин","Хирург","ivan55","55ivan"));
    protected final String doctor3 = json.toJson(new RegisterDoctorDtoRequest("Пётр","Кечиков","Терапевт","petr1488","1488petr"));
    protected final String login = json.toJson(new LoginDtoRequest("vas1969","1969vas"));
    protected final String login2 = json.toJson(new LoginDtoRequest("ivan55","55ivan"));
    protected final String patient = json.toJson(new RegisterPatientDtoRequest("Дмитрий","Федотов","dimasik","frol1234","ОРВИ"));
    protected final String patient2 = json.toJson(new RegisterPatientDtoRequest("Иван","Соколов","ivan43","kepor","ОРВИ"));

}
