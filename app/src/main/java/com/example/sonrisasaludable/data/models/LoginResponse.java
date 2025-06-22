// LoginResponse.java
package com.example.sonrisasaludable.data.models;

public class LoginResponse {
    private String auth_token;
    private int user_id;
    private String user_role;
    private String user_name;
    private String user_email;
    private String user_photo;
    private boolean is_logged_in;

    // Getters
    public String getAuthToken() {
        return auth_token;
    }

    public int getUserId() {
        return user_id;
    }

    public String getUserRole() {
        return user_role;
    }

    public String getUserName() {
        return user_name;
    }

    public String getUserEmail() {
        return user_email;
    }

    public String getUserPhoto() {
        return user_photo;
    }

    public boolean isLoggedIn() {
        return is_logged_in;
    }

    // Setters
    public void setAuthToken(String auth_token) {
        this.auth_token = auth_token;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setUserRole(String user_role) {
        this.user_role = user_role;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    public void setUserPhoto(String user_photo) {
        this.user_photo = user_photo;
    }

    public void setLoggedIn(boolean is_logged_in) {
        this.is_logged_in = is_logged_in;
    }
}
