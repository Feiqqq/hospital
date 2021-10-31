package net.thumbtack.school.hospital.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;


public class ServiceUtils {

    private Gson json = new Gson();

    public static <T> T getClassFromJson(String jsonString, Class<T> className) throws ServerException {
        Gson json = new Gson();
        try {
            return json.fromJson(jsonString, className);
        }
        catch (JsonSyntaxException e){
          throw new ServerException(ServerErrorCode.WRONG_JSON);
        }

    }
    public static void validateLogDtoRequest(LoginDtoRequest loginDtoRequest) throws ServerException {
        if(loginDtoRequest.getLogin()==null) throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        if(loginDtoRequest.getPassword() == null) throw new ServerException(ServerErrorCode.WRONG_PASSWORD);

    }
    public void validateRegDoc(RegisterDoctorDtoRequest regDoc) throws ServerException {
        if (regDoc.getFirstName() == null) {
            throw new ServerException(ServerErrorCode.WRONG_FISRTNAME);
        }
        if (regDoc.getLastName() == null) {
            throw new ServerException(ServerErrorCode.WRONG_LASTNAME);
        }
        if (regDoc.getLogin() == null) {
            throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        }
        if (regDoc.getPassword() == null) {
            throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        }
        if (regDoc.getSpecialty() == null) {
            throw new ServerException(ServerErrorCode.WRONG_SPECIALTY);
        }
    }
}
