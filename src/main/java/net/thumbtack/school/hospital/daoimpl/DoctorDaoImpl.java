package net.thumbtack.school.hospital.daoimpl;

import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.database.Database;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

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
    public void addPatient(String token, Doctor doctor) throws ServerException {
        Database.getInstance().addPatient(token,doctor);
    }

}
