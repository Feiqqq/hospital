package net.thumbtack.school.hospital.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Patient extends User{

    private String nameOfDisease;
    private Doctor doctor;
    private List<String> proceduresAndMedications = new ArrayList<>();

    public Patient(String firstName, String lastName, String login, String password,String nameOfDisease) {
        super(firstName, lastName, login, password);
        this.nameOfDisease = nameOfDisease;
        this.proceduresAndMedications = new ArrayList<>();
    }

    public void addProceduresAndMedications(String str){
        proceduresAndMedications.add(str);
    }

    public void setProceduresAndMedications(List<String> proceduresAndMedications) {
        this.proceduresAndMedications = proceduresAndMedications;
    }
}
