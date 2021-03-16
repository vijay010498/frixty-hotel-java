package com.frixty.frixtyhotel.Models;

public class JWTAuthTokenModel {
    private String jwtAuthToken;

    public JWTAuthTokenModel() {
    }

    public JWTAuthTokenModel(String jwtAuthToken) {
        this.jwtAuthToken = jwtAuthToken;
    }

    public String getJwtAuthToken() {
        return jwtAuthToken;
    }

    public void setJwtAuthToken(String jwtAuthToken) {
        this.jwtAuthToken = jwtAuthToken;
    }
}
