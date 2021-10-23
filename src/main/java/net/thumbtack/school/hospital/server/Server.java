package net.thumbtack.school.hospital.server;


import net.thumbtack.school.hospital.service.DoctorService;
import net.thumbtack.school.hospital.service.PatientService;
import net.thumbtack.school.hospital.service.UserService;

public class Server {
    private DoctorService doctorService = new DoctorService();
    private PatientService patientService = new PatientService();
    private UserService userService = new UserService();

    public ServerResponse regDoctor(String json)  {
        return doctorService.registerDoctor(json);
    }

    public ServerResponse deleteDoctor(String token, String json) {
        return doctorService.deleteDoctor(token, json);
    }

    public ServerResponse changePass(String token, String password) {
        return userService.changePass(token, password);
    }

    public ServerResponse login(String json)  {
        return userService.loginUser(json);
    }

    public ServerResponse logout(String token) {
        return userService.logout(token);
    }

    public ServerResponse addPatient(String token, String jsonString) {
        return doctorService.addPatient(token, jsonString);
    }
}
