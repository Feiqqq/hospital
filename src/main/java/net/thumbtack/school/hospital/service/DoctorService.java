package net.thumbtack.school.hospital.service;

import com.google.gson.Gson;
import net.thumbtack.school.hospital.daoimpl.DoctorDaoImpl;
import net.thumbtack.school.hospital.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.PatientResponse;
import net.thumbtack.school.hospital.mappers.UserMapper;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;
import net.thumbtack.school.hospital.server.ServerResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoctorService extends ServiceUtils {
    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_ERROR = 400;
    private DoctorDaoImpl doctorDao = new DoctorDaoImpl();
    private UserDaoImpl userDao = new UserDaoImpl();
    Gson json = new Gson();

    public ServerResponse registerDoctor(String jsonString) {
        try {
            RegisterDoctorDtoRequest regDoc = ServiceUtils.getClassFromJson(jsonString, RegisterDoctorDtoRequest.class);
            validateRegDoc(regDoc);
            doctorDao.insert(UserMapper.INSTANCE.toDoctor(regDoc));
            return new ServerResponse(RESPONSE_CODE_OK, "");
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e.getServerErrorCode().getErrorCode());
        }
    }


    public ServerResponse deleteDoctor(String token) {
        try {
            if (!doctorDao.getAllMyPatients(token).isEmpty()) {
                Doctor doctor = (Doctor) userDao.selectByToken(token);
                List<Doctor> doctorList = doctorDao.getDoctorBySpeciality(doctor.getSpecialty());
                doctorList.remove(doctor);
                if (doctorList.size() == 0) {
                    Integer randomDoctorId = new Random().nextInt(doctorDao.getQuantityDoctor());
                    Doctor doc = doctorDao.selectById(randomDoctorId);
                    if (doctor.equals(doc)) {
                        Integer quantityDoctor = doctorDao.getQuantityDoctor();
                        if (randomDoctorId + 1 < quantityDoctor) {
                            doc = doctorDao.selectById(randomDoctorId+1);
                        } else {
                            doc = doctorDao.selectById(randomDoctorId-1);
                        }
                    }
                    doc.addPatientFromList(doctor.getPatients());
                    for (Patient patient : doctor.getPatients()) {
                        patient.setDoctor(doc);
                    }
                } else {
                    int x = new Random().nextInt(doctorList.size());
                    doctorList.get(x).addPatientFromList(doctor.getPatients());
                    for (Patient patient : doctor.getPatients()) {
                        patient.setDoctor(doctorList.get(x));
                    }
                }
            }
            doctorDao.delete(token);
            return new ServerResponse(RESPONSE_CODE_OK, json.toJson(new EmptyDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e.getServerErrorCode().getErrorCode());
        }
    }


    public ServerResponse addPatient(String token, String jsonString) {
        try {
            User user = userDao.selectByToken(token);
            if (user.getClass().equals(Doctor.class)) {
                RegisterPatientDtoRequest regPatient = ServiceUtils.getClassFromJson(jsonString, RegisterPatientDtoRequest.class);
                Doctor doctor = (Doctor) user;
                Patient patient = UserMapper.INSTANCE.toPatient(regPatient);
                doctor.addPatient(patient);
                patient.setDoctor(doctor);
                doctorDao.addPatient(token, doctor, patient);
            } else {
                throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
            }
            return new ServerResponse(RESPONSE_CODE_OK, "");
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e.getServerErrorCode().getErrorCode());
        }

    }

    public ServerResponse addTreatment(String token, String jsonPatientAndTreatment) {
        try {
            PatientAndTreatment patientAndTreatment = ServiceUtils.getClassFromJson(jsonPatientAndTreatment, PatientAndTreatment.class); // экземляр
            Doctor doctor = (Doctor) userDao.selectByToken(token);
            doctorDao.addTreatment(doctor, patientAndTreatment);
            return new ServerResponse(RESPONSE_CODE_OK, "");
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e.getServerErrorCode().getErrorCode());
        }
    }

    public ServerResponse getAllMyPatients(String token) throws ServerException {
        List<Patient> list = doctorDao.getAllMyPatients(token);
        List<PatientResponse> patientResponseList = new ArrayList<>();
        int i = 0;
        for (Patient patient: list) {
            patientResponseList.add(UserMapper.INSTANCE.toPatientResponse(patient));
            patientResponseList.get(i).setDoctorId(doctorDao.selectIdByDoctor(patient.getDoctor()).toString());
        }
        return new ServerResponse(RESPONSE_CODE_OK, json.toJson(patientResponseList));
    }
}

