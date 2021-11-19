package net.thumbtack.school.hospital.daoimpl;

import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.database.Database;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Treatment;   
import net.thumbtack.school.hospital.server.exceptions.ServerException;

import java.util.List;

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
    public void addTreatment(Doctor doctor, Treatment treatment) throws ServerException {
        Database.getInstance().addTreatment(doctor,treatment);
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
        return Database.getInstance().getNextID();
    }


}
