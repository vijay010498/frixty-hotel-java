package com.frixty.frixtyhotel.APIs.RequestBody;

public class VerifyAuthTokenBody {
    private String jwtAuthToken;

    public VerifyAuthTokenBody(String jwtAuthToken) {
        this.jwtAuthToken = jwtAuthToken;
    }

    public String getJwtAuthToken() {
        return jwtAuthToken;
    }

    public void setJwtAuthToken(String jwtAuthToken) {
        this.jwtAuthToken = jwtAuthToken;
    }
}
