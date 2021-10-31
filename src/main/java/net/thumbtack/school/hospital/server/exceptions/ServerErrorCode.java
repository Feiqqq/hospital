package net.thumbtack.school.hospital.server.exceptions;

public enum ServerErrorCode {
    LOGIN_ALREADY_EXISTS("LOGIN_ALREADY_EXISTS"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"),
    WRONG_JSON("WRONG_JSON"),
    INCORRECT_INPUT("INCORRECT_INPUT"),
    WRONG_FISRTNAME("WRONG_FISRTNAME"),
    WRONG_LASTNAME("WRONG_LASTNAME"),
    WRONG_LOGIN("WRONG_LOGIN"),
    WRONG_PASSWORD("WRONG_PASSWORD"),
    WRONG_SPECIALTY("WRONG_SPECIALTY"),
    NOT_FOUND_USER("NOT_FOUND_USER"),
    WRONG_PASSWORD_OR_LOGIN("WRONG_PASSWORD_OR_LOGIN"),
    WRONG_DELETE_DOCTOR("WRONG_DELETE_DOCTOR");
    String errorString;
    ServerErrorCode(String errorCode){
        this.errorString = errorCode;
   }
    public String getErrorCode(){
        return errorString;
   }
}
