package net.thumbtack.school.hospital.server;

import lombok.Data;
import net.thumbtack.school.hospital.server.exceptions.ServerException;


@Data
public class ServerResponse {
    private int responseCode;
    private String responseData;

    public ServerResponse(int responseCode, String responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }
    public ServerResponse(int responseCode){
        this.responseCode = responseCode;
    }

    public ServerResponse(int responseCode, ServerException e){
        this.responseCode = responseCode;
        this.responseData = e.getServerErrorCode().getErrorString();
    }

    public ServerResponse() {
    }
}
