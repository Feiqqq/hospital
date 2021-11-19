package net.thumbtack.school.hospital.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TreatmentDtoResponse {
    private String doctorFullName;
    private String doctorSpeciality;
    private String nameOfDisease;
    private String treatment;

    public TreatmentDtoResponse(String doctorFullName,String doctorSpeciality,String nameOfDisease, String treatment) {
        this.doctorFullName = doctorFullName;
        this.doctorSpeciality = doctorSpeciality;
        this.nameOfDisease = nameOfDisease;
        this.treatment = treatment;
    }
}
