package net.thumbtack.school.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientAndTreatment {
    private String login;
    private String medicament;
    private String frequency;
    private String procedures;
    private String daysOfWeek;

    public PatientAndTreatment(String login, String medicament, String frequency, String procedures, String daysOfWeek) {
        this.login = login;
        this.medicament = medicament;
        this.frequency = frequency;
        this.procedures = procedures;
        this.daysOfWeek = daysOfWeek;
    }
}
