package com.frixty.frixtyhotel.APIs.RequestBody;

public class SignInBody {
    private final String email;
    private final String password;

    public SignInBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
