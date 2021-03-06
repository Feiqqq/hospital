package net.thumbtack.school.hospital.server.exceptions;

public class ServerException extends Exception{
    private final ServerErrorCode serverErrorCode;
    public ServerException(ServerErrorCode serverErrorCode){
        this.serverErrorCode = serverErrorCode;
    }

    public ServerErrorCode getServerErrorCode() {
        return serverErrorCode;
    }
}
