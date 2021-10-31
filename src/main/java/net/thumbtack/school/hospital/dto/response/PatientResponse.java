package net.thumbtack.school.hospital.dto.response;

import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.hospital.model.Doctor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PatientResponse {
    private String doctorId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String nameOfDisease;
    private List<String> proceduresAndMedications = new ArrayList<>();
}
