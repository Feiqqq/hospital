package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

import java.util.*;

public final class Database {

    private static Database instance;// реализация ленивого сингелтона
    private Map<String, User> users = new HashMap<>();
    private Map<String, User> usersByToken = new HashMap<>();

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    public User selectByLogin(String login) throws ServerException {
        if (users.get(login) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
        return users.get(login);
    }

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null)
            throw new ServerException(ServerErrorCode.USER_ALREADY_EXISTS);
    }

    public void delete(String token) {
        // нужно вытащить доктора
        // получить список его пациентов
        // этим пациентам назначить нового доктора с такой же специальностью
        // нужно учитывать тот факт, что доктор который будет первый в списке с нужной специальностью, именно он будет получать всех пациентов, что с этим делать
        // получить лист всех докторов с нужной специальностью сгенерировать рандомное число, а если нет врача с нужной специальностью на рандом любому врачу
        Doctor doctor = (Doctor) usersByToken.get(token);
        List<Doctor> doctorWithSpecialty = new ArrayList<>();
        List<Doctor> allDoctors = new ArrayList<>();
        if (!doctor.getPatients().isEmpty()) {
            users.forEach((key, value) -> {
                Class clazz = value.getClass();
                if (clazz.isInstance(doctor)) {
                    Doctor newDoc = (Doctor) value;
                    if (newDoc.getSpecialty().equals(doctor.getSpecialty()) && !newDoc.getLogin().equals(doctor.getLogin())) {
                        doctorWithSpecialty.add(newDoc);
                    }
                    allDoctors.add(newDoc);
                }
            });

            if (!doctorWithSpecialty.isEmpty()) {
                int x = new Random().nextInt(doctorWithSpecialty.size());
                doctorWithSpecialty.get(x).addPatientFromList(doctor.getPatients());
                for (Patient patient : doctor.getPatients()) {
                    patient.setDoctor(doctorWithSpecialty.get(x));
                }
            } else {
                if (!allDoctors.isEmpty()) {
                    int x = new Random().nextInt(allDoctors.size());
                    allDoctors.get(x).addPatientFromList(doctor.getPatients());
                    for (Patient patient : doctor.getPatients()) {
                        patient.setDoctor(allDoctors.get(x));
                    }
                }
            }
        }
        users.remove(usersByToken.get(token).getLogin());
        usersByToken.remove(token);
    }

    public void addTreatment(Doctor doctor, PatientAndTreatment patientAndTreatment) throws ServerException {
        for (Patient patient : doctor.getPatients()) {
            if (patient.getLogin().equals(patientAndTreatment.getLogin())) {
                if (patientAndTreatment.getMedicament() != null && patientAndTreatment.getFrequency() != null) {
                    patient.addProceduresAndMedications(patientAndTreatment.getMedicament() + patientAndTreatment.getFrequency());
                }
                else if(patientAndTreatment.getProcedures() != null && patientAndTreatment.getDaysOfWeek() != null) {
                    patient.addProceduresAndMedications(patientAndTreatment.getProcedures()+ patientAndTreatment.getDaysOfWeek());
                }
                else {
                    throw new ServerException(ServerErrorCode.INCORRECT_INPUT);
                }
            }
        }
    }

    public void upgrade(String token, User user) {
        users.remove(user.getLogin());
        users.put(user.getLogin(), user);
        usersByToken.remove(token);
        usersByToken.put(token, user);
    }

    public void insert(String token, User user) {
        usersByToken.put(token, user);
    }

    public void logoutDoc(String token) throws ServerException {
        if (usersByToken.remove(token) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);

    }

    public void addPatient(String token, Doctor doctor) throws ServerException {
        if (usersByToken.remove(token) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
        usersByToken.put(token, doctor);
    }

    public User selectByToken(String token) {
        return usersByToken.get(token);
    }

    public void deleteAll() {
        users.clear();
        usersByToken.clear();
    }
}

