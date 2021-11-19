package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Treatment;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;

public final class Database {

    private static Database instance;// реализация ленивого сингелтона
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, User> usersByToken = new HashMap<>();
    private final MultiValuedMap<String, Doctor> doctorBySpeciality = new ArrayListValuedHashMap<>();
    private final Map<Integer,User> userById = new HashMap<>();
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
        user.setId(id);
        if (user.getClass().equals(Doctor.class)) {
            Doctor doctor = (Doctor) user;
            doctorBySpeciality.put(doctor.getSpecialty(), doctor);
            userById.put(id,user);
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
    public User selectById(Integer idDto) throws ServerException {
        // 1 если в базе вообще никого нет
        // 2 если в базе только ток, кто и делает данный запрос
        if(id == 1|| id == 2) throw new ServerException(ServerErrorCode.WRONG_DELETE_DOCTOR);
        return userById.get(idDto);
    }

    public List<Patient> getAllMyPatients(String token) {
        Doctor doctor = (Doctor) usersByToken.get(token);
        return doctor.getPatients();
    }

    public Integer getNextID(){
        return id;
    }

    public void addPatient(String token, Doctor doctor, Patient patient) throws ServerException {
        if (usersByToken.remove(token) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
        usersByToken.put(token, doctor);
        users.put(patient.getLogin(), patient);
    }

    public void addTreatment(Doctor doctor, Treatment treatment) {
        for (Patient patient : doctor.getPatients()) {
            if (patient.getId() == treatment.getId()) {
                if (patient.getProceduresAndMedications() == null) {
                    List<String> list = new ArrayList<>();
                    patient.setProceduresAndMedications(list);
                }
                if (!treatment.getMedicament().isEmpty() && !treatment.getFrequency().isEmpty()) {
                    patient.addProceduresAndMedications(treatment.getMedicament() + " " + treatment.getFrequency());
                }
                if (!treatment.getProcedures().isEmpty() && !treatment.getDaysOfWeek().isEmpty()) {
                    patient.addProceduresAndMedications(treatment.getProcedures() + " " + treatment.getDaysOfWeek());
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
        userById.clear();
        id = 1;
    }
}

