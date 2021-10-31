package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;

public final class Database {

    private static Database instance;// реализация ленивого сингелтона
    private Map<String, User> users = new HashMap<>();
    private Map<String, User> usersByToken = new HashMap<>();
    private MultiValuedMap<String, Doctor> doctorBySpeciality = new ArrayListValuedHashMap<>();
    private Map<Integer,Doctor> doctorById = new HashMap<>();
    private Map<Doctor,Integer> idByDoctor = new HashMap<>();
    private Integer id = 1;

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null)
            throw new ServerException(ServerErrorCode.USER_ALREADY_EXISTS);
        if (user.getClass().equals(Doctor.class)) {
            Doctor doctor = (Doctor) user;
            doctorBySpeciality.put(doctor.getSpecialty(), doctor);
            doctorById.put(id,(Doctor) user);
            idByDoctor.put((Doctor) user,id);
            id++;
        }
    }

    public User selectByLogin(String login) throws ServerException {
        User user = users.get(login);
        if (user == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
        return user;
    }

    public User selectByToken(String token) {
        return usersByToken.get(token);
    }

    public List<Doctor> selectBySpeciality(String speciality) {
        return (List<Doctor>) doctorBySpeciality.get(speciality);
    }
    public Doctor selectById(Integer idDto) throws ServerException {
        if(id == 1|| id == 2) throw new ServerException(ServerErrorCode.WRONG_DELETE_DOCTOR);
        return doctorById.get(idDto);
    }
    public Integer selectIdByDoctor(Doctor doctor){
        return idByDoctor.get(doctor);
    }


    public List<Patient> getAllMyPatients(String token) {
        Doctor doctor = (Doctor) usersByToken.get(token);
        return doctor.getPatients();
    }
    public Integer getQuantityDoctor(){
        return id;
    }

    public void addPatient(String token, Doctor doctor, Patient patient) throws ServerException {
        if (usersByToken.remove(token) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
        usersByToken.put(token, doctor);
        users.put(patient.getLogin(), patient);
    }

    public void addTreatment(Doctor doctor, PatientAndTreatment patientAndTreatment) {
        for (Patient patient : doctor.getPatients()) {
            if (patient.getLogin().equals(patientAndTreatment.getLogin())) {
                if (patient.getProceduresAndMedications() == null) {
                    List<String> list = new ArrayList<>();
                    patient.setProceduresAndMedications(list);
                }
                if (!patientAndTreatment.getMedicament().isEmpty() && !patientAndTreatment.getFrequency().isEmpty()) {
                    patient.addProceduresAndMedications(patientAndTreatment.getMedicament() + " " + patientAndTreatment.getFrequency());
                }
                if (!patientAndTreatment.getProcedures().isEmpty() && !patientAndTreatment.getDaysOfWeek().isEmpty()) {
                    patient.addProceduresAndMedications(patientAndTreatment.getProcedures() + " " + patientAndTreatment.getDaysOfWeek());
                }
            }
        }
    }

    public void upgrade(String token, User user) {
        users.replace(user.getLogin(), user);
        usersByToken.replace(token, user);
    }

    public void login(String token, User user) {
        usersByToken.put(token, user);
    }

    public void logoutDoc(String token) throws ServerException {
        if (usersByToken.remove(token) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);

    }

    public void delete(String token) {
        users.remove(usersByToken.get(token).getLogin());
        usersByToken.remove(token);
    }


    public void deleteAll() {
        doctorBySpeciality.clear();
        users.clear();
        usersByToken.clear();
        doctorById.clear();
        idByDoctor.clear();
        id = 1;
    }
}

