package net.thumbtack.school.hospital.server;


import net.thumbtack.school.hospital.server.exceptions.ServerException;
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

    public ServerResponse deleteDoctor(String token) {
        return doctorService.deleteDoctor(token);
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
    public ServerResponse addTreatment(String token,String jsonPatientAndTreatment){
        return doctorService.addTreatment(token,jsonPatientAndTreatment);
    }
    public ServerResponse getAllMyPatients(String jsonToken) throws ServerException {
        return doctorService.getAllMyPatients(jsonToken);
    }
}

