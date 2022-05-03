package ua.edu.mobile.stepchat.data.repository;

import ua.edu.mobile.stepchat.data.models.User;

public class TokenRepository {
    private String token;
    private User localUser;

    public void setToken(String idToken) {
        token = idToken;
    }

    public String getToken() {
        return token;
    }

    public User getLocalUser() {
        return localUser;
    }

    public void setLocalUser(User localUser) {
        this.localUser = localUser;
    }
}
