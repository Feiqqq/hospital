package net.thumbtack.school.hospital.mappers;

import net.thumbtack.school.hospital.dto.request.PatientAndTreatment;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.dto.response.PatientResponse;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Treatment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Doctor toDoctor(RegisterDoctorDtoRequest regDoc);
    Patient toPatient(RegisterPatientDtoRequest regPat);
    PatientResponse toPatientResponse(Patient patient);
    Treatment toTreatment(PatientAndTreatment patientAndTreatment);
}
