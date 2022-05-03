package ua.edu.mobile.stepchat.features.users;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.mobile.stepchat.data.api.StepChatAPI;
import ua.edu.mobile.stepchat.data.models.User;
import ua.edu.mobile.stepchat.data.repository.TokenRepository;
import ua.edu.mobile.stepchat.shared.fetching.FetchResultReceiver;
import ua.edu.mobile.stepchat.shared.fetching.ThreadFetcher;

public class Users extends ThreadFetcher<User[]> {

    private TokenRepository tokenRepository;
    private StepChatAPI stepChatAPI;

    public Users(StepChatAPI stepChatAPI, TokenRepository tokenRepository, int fetchPeriod, FetchResultReceiver<User[]> usersReceiver) {
        super(fetchPeriod, usersReceiver);
        this.stepChatAPI = stepChatAPI;
        this.tokenRepository = tokenRepository;
    }

    public void getUser(String id, UserFound userFoundCallback) {
        stepChatAPI.getUserById(tokenRepository.getToken(), id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200){
                    userFoundCallback.setUser(response.body());
                } else {
                    Log.e("User", ""+response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("User",  t.getMessage());
            }
        });
    }

    @Override
    public void fetch() {
        stepChatAPI.getUsers(tokenRepository.getToken()).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if(response.code() == 200){
                    getReceiver().onFetchReceive(response.body());
                } else {
                    getReceiver().onFetchFailed("Failed to send message to server. Code: "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                getReceiver().onFetchFailed("Failed to send message to server. Details: "+ t.getMessage());
            }
        });
    }
}

