package ua.edu.mobile.stepchat.features.login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.mobile.stepchat.data.api.StepChatAPI;
import ua.edu.mobile.stepchat.data.models.User;
import ua.edu.mobile.stepchat.data.repository.TokenRepository;

public class Login {

    private StepChatAPI stepChatAPI;
    private TokenRepository tokenRepository;

    public Login(StepChatAPI stepChatAPI, TokenRepository tokenRepository) {
        this.stepChatAPI = stepChatAPI;
        this.tokenRepository = tokenRepository;
    }

    public void login(String token, OnLogin onLoginCallback) {
        tokenRepository.setToken(token);
        stepChatAPI.getUserByToken(tokenRepository.getToken()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200){
                    onLoginCallback.loginSuccess(response.body());
                    tokenRepository.setLocalUser(response.body());
                }else{
                    onLoginCallback.loginFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onLoginCallback.loginFailed(t.getMessage());
            }
        });
    }
}
