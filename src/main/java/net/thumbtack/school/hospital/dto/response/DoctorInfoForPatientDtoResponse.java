package net.thumbtack.school.hospital.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorInfoForPatientDtoResponse {
    private String firstname;
    private String lastname;
    private String speciality;

    public DoctorInfoForPatientDtoResponse(String firstname, String lastname, String speciality) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.speciality = speciality;
    }
}
