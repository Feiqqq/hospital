package net.thumbtack.school.hospital.server;

import lombok.Data;

@Data
public class ServerResponse {
    private int responseCode;
    private String responseData;

    public ServerResponse(int responseCode, String responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public ServerResponse() {

    }
}
