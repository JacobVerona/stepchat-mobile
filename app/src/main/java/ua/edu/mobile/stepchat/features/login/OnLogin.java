package ua.edu.mobile.stepchat.features.login;

import ua.edu.mobile.stepchat.data.models.User;

public interface OnLogin {
    void loginSuccess(User user);
    void loginFailed(String details);
}
