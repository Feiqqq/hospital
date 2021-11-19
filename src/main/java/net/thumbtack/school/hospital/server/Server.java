package net.thumbtack.school.hospital.server;


import net.thumbtack.school.hospital.service.DoctorService;
import net.thumbtack.school.hospital.service.PatientService;
import net.thumbtack.school.hospital.service.UserService;

public class Server {
    private final DoctorService doctorService = new DoctorService();
    private final PatientService patientService = new PatientService();
    private final UserService userService = new UserService();

    public ServerResponse regDoctor(String json) {
        return doctorService.registerDoctor(json);
    }

    public ServerResponse deleteDoctor(String token) {
        return doctorService.deleteDoctor(token);
    }

    public ServerResponse changePass(String token, String password) {
        return userService.changePass(token, password);
    }

    public ServerResponse login(String json) {
        return userService.loginUser(json);
    }

    public ServerResponse logout(String token) {
        return userService.logout(token);
    }

    public ServerResponse addPatient(String token, String jsonString) {
        return doctorService.addPatient(token, jsonString);
    }

    public ServerResponse addTreatment(String token, String jsonPatientAndTreatment) {
        return doctorService.addTreatment(token, jsonPatientAndTreatment);
    }

    public ServerResponse getAllMyPatients(String token) {
        return doctorService.getAllMyPatients(token);
    }
    public ServerResponse getMyTreatment(String token){
        return patientService.getMyTreatment(token);
    }
    public ServerResponse getMyDoctor(String token){
        return patientService.getMyDoctorInfo(token);
    }
}

