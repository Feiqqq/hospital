package net.thumbtack.school.hospital.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Treatment {
    private int id;
    private String medicament;
    private String frequency;
    private String procedures;
    private String daysOfWeek;

    public Treatment(int id, String medicament, String frequency, String procedures, String daysOfWeek) {
        this.id = id;
        this.medicament = medicament;
        this.frequency = frequency;
        this.procedures = procedures;
        this.daysOfWeek = daysOfWeek;
    }
}
