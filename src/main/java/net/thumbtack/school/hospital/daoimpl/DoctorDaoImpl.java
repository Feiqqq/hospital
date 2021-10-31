package net.thumbtack.school.hospital.daoimpl;

import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.database.Database;
import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.dto.request.TokenDtoRequest;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

import java.util.List;
import java.util.Map;

public class DoctorDaoImpl implements DoctorDao {
    @Override
    public void insert(Doctor doctor) throws ServerException {
        Database.getInstance().insert(doctor);
    }

    @Override
    public void delete(String token) throws ServerException {
        Database.getInstance().delete(token);
    }


    @Override
    public void addPatient(String token, Doctor doctor, Patient patient) throws ServerException {
        Database.getInstance().addPatient(token,doctor,patient);
    }

    @Override
    public void addTreatment(Doctor doctor, PatientAndTreatment patientAndTreatment) throws ServerException {
        Database.getInstance().addTreatment(doctor,patientAndTreatment);
    }


    @Override
    public List<Patient> getAllMyPatients(String token) {
        return Database.getInstance().getAllMyPatients(token);
    }

    @Override
    public List<Doctor> getDoctorBySpeciality(String speciality) {
        return Database.getInstance().selectBySpeciality(speciality);
    }

    @Override
    public Integer getQuantityDoctor() {
        return Database.getInstance().getQuantityDoctor();
    }

    @Override
    public Doctor selectById(Integer id) throws ServerException {
        return Database.getInstance().selectById(id);
    }

    @Override
    public Integer selectIdByDoctor(Doctor doctor) throws ServerException {
        return Database.getInstance().selectIdByDoctor(doctor);
    }

}
