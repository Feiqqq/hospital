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
    // REVU сначала поля, потом конструкторы (если есть), потом методы

    protected Server server = new Server();
    protected Gson json = new Gson();
    protected final String password = UUID.randomUUID().toString();

    // REVU не нравится мне эта строка как какой-то result
    // порядок полей в json не определен
    // как GSON захочет, так и будет
    // поэтому сравнение может пройти, а может и не пройти
    // да и не нужно это, см.TestDoctorOperation
    protected final String resultJson = "[{\"doctorId\":1,\"firstName\":\"Дмитрий\",\"lastName\":\"Федотов\",\"nameOfDisease\":\"ОРВИ\",\"proceduresAndMedications\":[]},{\"doctorId\":0,\"firstName\":\"Иван\",\"lastName\":\"Соколов\",\"nameOfDisease\":\"ОРВИ\",\"proceduresAndMedications\":[]}]";
    // REVU я бы назвал String doctorJson. Сейчас первая мысль - что это класс модели
    protected final String doctorJson1 = json.toJson(new RegisterDoctorDtoRequest("Василий","Флоров","Терапевт","vas1969","1969vas"));
    protected final String doctorJson2 = json.toJson(new RegisterDoctorDtoRequest("Иван","Тарелкин","Хирург","ivan55","55ivan"));
    protected final String doctorJson3 = json.toJson(new RegisterDoctorDtoRequest("Пётр","Кечиков","Терапевт","petr1488","1488petr"));
    protected final String doctorJson4 = json.toJson(new RegisterDoctorDtoRequest("null","null","Терапевт","null","null"));
    protected final String doctorJson5 = json.toJson(new RegisterDoctorDtoRequest("Пётр","Кечиков","Терапевт","","1488petr"));
    protected final String doctorJson6 = json.toJson(new RegisterDoctorDtoRequest("Пётр","Кечиков","Терапевт"," ","1488petr"));
    protected final String login = json.toJson(new LoginDtoRequest("vas1969","1969vas"));
    protected final String login2 = json.toJson(new LoginDtoRequest("ivan55","55ivan"));
    protected final String patientLog = json.toJson(new LoginDtoRequest("dimasik","frol1234"));
    protected final String patient = json.toJson(new RegisterPatientDtoRequest("Дмитрий","Федотов","dimasik","frol1234","ОРВИ"));
    protected final String patient2 = json.toJson(new RegisterPatientDtoRequest("Иван","Соколов","ivan43","kepor","ОРВИ"));
    protected final String patientRes = "{\"doctorFullName\":\"Василий Флоров\",\"doctorSpeciality\":\"Терапевт\",\"nameOfDisease\":\"ОРВИ\",\"treatment\":\"[]\"}";

    @BeforeEach
    public void deleteAll(){
        Database.getInstance().deleteAll();
    }
}
