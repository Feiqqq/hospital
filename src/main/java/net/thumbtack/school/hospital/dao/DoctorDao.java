package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.dto.request.TokenDtoRequest;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

public interface DoctorDao {
    void insert(Doctor doctor) throws ServerException;
    void delete(String token) throws ServerException;
    void addPatient(String token, Doctor doctor) throws ServerException;
    void addTreatment(Doctor doctor, PatientAndTreatment patientAndTreatment) throws ServerException;

}
