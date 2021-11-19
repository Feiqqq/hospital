package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.dto.request.TokenDtoRequest;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Treatment;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

import java.util.List;
import java.util.Map;

public interface DoctorDao {
    void insert(Doctor doctor) throws ServerException;
    void delete(String token) throws ServerException;
    void addPatient(String token, Doctor doctor, Patient patient) throws ServerException;
    void addTreatment(Doctor doctor, Treatment treatment) throws ServerException;
    List<Patient> getAllMyPatients(String token);
    List<Doctor> getDoctorBySpeciality(String speciality);
    Integer getQuantityDoctor();
}
