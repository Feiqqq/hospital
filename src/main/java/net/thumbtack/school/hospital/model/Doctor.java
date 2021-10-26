package net.thumbtack.school.hospital.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Doctor extends User{
    private String specialty;
    private List<Patient> patients = new ArrayList<>();
    public Doctor(String firstName,String lastName,String specialty, String login,String password){
        super(firstName, lastName, login, password);
        this.specialty = specialty;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }
    public void addPatientFromList(List<Patient> list){
        patients.addAll(list);
    }
}
