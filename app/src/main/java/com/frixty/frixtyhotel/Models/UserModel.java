package com.frixty.frixtyhotel.Models;

public class UserModel {
    private String email, password, fullName, passportNumber, phoneNumber, id;
    private Long createdAt, updatedAt;
    private JWTAuthTokenModel auth;

    public UserModel(String email, String password, String fullName, String passportNumber, String phoneNumber, String id, Long createdAt, Long updatedAt, JWTAuthTokenModel auth) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.auth = auth;
    }

    public UserModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public JWTAuthTokenModel getAuth() {
        return auth;
    }

    public void setAuth(JWTAuthTokenModel auth) {
        this.auth = auth;
    }
}
