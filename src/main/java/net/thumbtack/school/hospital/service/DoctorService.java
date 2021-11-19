package net.thumbtack.school.hospital.service;

import com.google.gson.Gson;
import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.dao.UserDao;
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

public class DoctorService {
    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_ERROR = 400;
    private final DoctorDao doctorDao = new DoctorDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    Gson json = new Gson();

    public ServerResponse registerDoctor(String jsonString) {
        try {
            ServiceUtils serviceUtils = new ServiceUtils();
            RegisterDoctorDtoRequest regDoc = ServiceUtils.getClassFromJson(jsonString, RegisterDoctorDtoRequest.class);
            serviceUtils.validateRegDoc(regDoc);
            doctorDao.insert(UserMapper.INSTANCE.toDoctor(regDoc));
            return new ServerResponse(RESPONSE_CODE_OK);
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e);
        }
    }


    public ServerResponse deleteDoctor(String token) {
        try {
            if (doctorDao.getAllMyPatients(token).isEmpty()) {
                throw new ServerException(ServerErrorCode.WRONG_PATIENTS_LIST);
            }
            if (!userDao.selectByToken(token).getClass().equals(Doctor.class)) {
                throw new ServerException(ServerErrorCode.NOT_FOUND_DOCTOR);
            }
            Doctor doctor = getDoctorByToken(token);
            List<Doctor> doctorList = doctorDao.getDoctorBySpeciality(doctor.getSpecialty());
            doctorList.remove(doctor);
            if (doctorList.size() == 0) {
                Integer randomDoctorId = new Random().nextInt(doctorDao.getQuantityDoctor());
                if (!userDao.selectById(randomDoctorId).getClass().equals(Doctor.class)) {
                    throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
                }
                Doctor doc = (Doctor) userDao.selectById(randomDoctorId);
                if (doctor.equals(doc)) {
                    Integer quantityDoctor = doctorDao.getQuantityDoctor();
                    if (randomDoctorId + 1 < quantityDoctor) {
                        if (!userDao.selectById(randomDoctorId + 1).getClass().equals(Doctor.class))
                            throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
                        doc = (Doctor) userDao.selectById(randomDoctorId + 1);
                    } else {
                        if (!userDao.selectById(randomDoctorId + 1).getClass().equals(Doctor.class))
                            throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
                        doc = (Doctor) userDao.selectById(randomDoctorId - 1);
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

            doctorDao.delete(token);
            return new ServerResponse(RESPONSE_CODE_OK, json.toJson(new EmptyDtoResponse()));
        } catch (ServerException e) {

            if (e.getServerErrorCode().equals(ServerErrorCode.WRONG_PATIENTS_LIST))
                return new ServerResponse(RESPONSE_CODE_OK, json.toJson(new EmptyDtoResponse()));
            return new ServerResponse(RESPONSE_CODE_ERROR, e);
        }
    }


    public ServerResponse addPatient(String token, String jsonString) {
        try {
            RegisterPatientDtoRequest regPatient = ServiceUtils.getClassFromJson(jsonString, RegisterPatientDtoRequest.class);
            Doctor doctor = getDoctorByToken(token);
            Patient patient = UserMapper.INSTANCE.toPatient(regPatient);
            doctor.addPatient(patient);
            patient.setDoctor(doctor);
            doctorDao.addPatient(token, doctor, patient);
            return new ServerResponse(RESPONSE_CODE_OK);
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e);
        }

    }

    public ServerResponse addTreatment(String token, String jsonPatientAndTreatment) {
        try {
            PatientAndTreatment patientAndTreatment = ServiceUtils.getClassFromJson(jsonPatientAndTreatment, PatientAndTreatment.class);
            Doctor doctor = getDoctorByToken(token);
            doctorDao.addTreatment(doctor, UserMapper.INSTANCE.toTreatment(patientAndTreatment));
            return new ServerResponse(RESPONSE_CODE_OK);
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e);
        }
    }

    public ServerResponse getAllMyPatients(String token) {
        try {
            List<Patient> list = getDoctorByToken(token).getPatients();
            List<PatientResponse> patientResponseList = new ArrayList<>();
            int i = 0;
            for (Patient patient : list) {
                patientResponseList.add(UserMapper.INSTANCE.toPatientResponse(patient));
                patientResponseList.get(0).setDoctorId(userDao.selectByToken(token).getId());
            }
            return new ServerResponse(RESPONSE_CODE_OK, json.toJson(patientResponseList));
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e);
        }
    }

    private Doctor getDoctorByToken(String token) throws ServerException {
        User user = userDao.selectByToken(token);
        if (user != null) {
            if (user instanceof Doctor) {
                return (Doctor) user;
            }
        }
        throw new ServerException(ServerErrorCode.WRONG_TOKEN);
    }
}

