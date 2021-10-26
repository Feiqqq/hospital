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
}
