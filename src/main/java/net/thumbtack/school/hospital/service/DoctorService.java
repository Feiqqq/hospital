package net.thumbtack.school.hospital.service;

import com.google.gson.Gson;
import net.thumbtack.school.hospital.daoimpl.DoctorDaoImpl;
import net.thumbtack.school.hospital.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.dto.request.EmptyDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.mappers.UserMapper;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;
import net.thumbtack.school.hospital.server.ServerResponse;

public class DoctorService{
    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_ERROR = 400;
    private DoctorDaoImpl doctorDao = new DoctorDaoImpl();
    private UserDaoImpl userDao = new UserDaoImpl();
    Gson json = new Gson();

    public ServerResponse registerDoctor(String jsonString) {
        try {
            RegisterDoctorDtoRequest regDoc = ServiceUtils.getClassFromJson(jsonString, RegisterDoctorDtoRequest.class);
            validate(regDoc);
            doctorDao.insert(UserMapper.INSTANCE.toDoctor(regDoc));
            return new ServerResponse(RESPONSE_CODE_OK,"");
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR,e.getServerErrorCode().getErrorCode());
        }
    }


    public ServerResponse deleteDoctor(String token, String jsonString) {
        try {
            EmptyDtoRequest emptyDtoRequest = ServiceUtils.getClassFromJson(jsonString, EmptyDtoRequest.class);
            doctorDao.delete(token);
            EmptyDtoResponse emptyDtoResponse = new EmptyDtoResponse();
            return new ServerResponse(RESPONSE_CODE_OK,json.toJson(emptyDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR,e.getServerErrorCode().getErrorCode());
        }
    }



    public ServerResponse addPatient(String token, String jsonString){
        try{
            Doctor doctor = (Doctor) userDao.selectByToken(token);
            Patient patient = ServiceUtils.getClassFromJson(jsonString,Patient.class);
            doctor.addPatient(patient);
            patient.setDoctor(doctor);
            doctorDao.addPatient(token,doctor);
            return new ServerResponse(RESPONSE_CODE_OK,"");
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR,e.getServerErrorCode().getErrorCode());
        }

    }

    public void validate(RegisterDoctorDtoRequest regDoc) throws ServerException {
        if (regDoc.getFirstName() == null) throw new ServerException(ServerErrorCode.WRONG_FISRTNAME);
        if (regDoc.getLastName() == null) throw new ServerException(ServerErrorCode.WRONG_LASTNAME);
        if (regDoc.getLogin() == null) throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        if (regDoc.getPassword() == null) throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        if (regDoc.getSpecialty() == null) throw new ServerException(ServerErrorCode.WRONG_SPECIALTY);
    }
}

