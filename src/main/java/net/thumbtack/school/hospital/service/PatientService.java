package net.thumbtack.school.hospital.service;


import com.google.gson.Gson;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.dto.response.DoctorInfoForPatientDtoResponse;
import net.thumbtack.school.hospital.dto.response.TreatmentDtoResponse;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.ServerResponse;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

public class PatientService {
    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_ERROR = 400;
    Gson json = new Gson();
    private final UserDao userDao = new UserDaoImpl();

    public ServerResponse getMyTreatment(String token){
        try{
            Patient patient = getPatientByToken(token);
            return new ServerResponse(RESPONSE_CODE_OK, json.toJson(new TreatmentDtoResponse(patient.getDoctor().getFirstName()+' '+patient.getDoctor().getLastName(), patient.getDoctor().getSpecialty(), patient.getNameOfDisease(), patient.getProceduresAndMedications().toString())));
        }
        catch (ServerException e){
            return new ServerResponse(RESPONSE_CODE_ERROR,e);
        }
    }
    public ServerResponse getMyDoctorInfo(String token){
        try {
            Patient patient = getPatientByToken(token);
            return new ServerResponse(RESPONSE_CODE_OK, json.toJson(new DoctorInfoForPatientDtoResponse(patient.getDoctor().getFirstName(),patient.getDoctor().getLastName(),patient.getDoctor().getSpecialty())));
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR,e);
        }
    }


    private Patient getPatientByToken(String token) throws ServerException {
        User user = userDao.selectByToken(token);
        if (user != null) {
            if (user instanceof Patient) {
                return (Patient) user;
            }
        }
        throw new ServerException(ServerErrorCode.WRONG_TOKEN);
    }
}
