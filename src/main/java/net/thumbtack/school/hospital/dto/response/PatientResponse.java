package net.thumbtack.school.hospital.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PatientResponse {
    private int doctorId;
    private String firstName;
    private String lastName;
    private String nameOfDisease;
    private List<String> proceduresAndMedications = new ArrayList<>();
}
