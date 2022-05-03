package ua.edu.mobile.stepchat.features.messages;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.mobile.stepchat.data.api.StepChatAPI;
import ua.edu.mobile.stepchat.data.models.Message;
import ua.edu.mobile.stepchat.data.models.SendMessage;
import ua.edu.mobile.stepchat.data.repository.TokenRepository;
import ua.edu.mobile.stepchat.shared.fetching.FetchResultReceiver;
import ua.edu.mobile.stepchat.shared.fetching.ThreadFetcher;

public class Messages extends ThreadFetcher<Message> {

    private StepChatAPI stepChatAPI;
    private TokenRepository tokenRepository;

    public Messages(StepChatAPI api, TokenRepository tokenRepository, int fetchPeriod, FetchResultReceiver<Message> messageReceiver) {
        super(fetchPeriod, messageReceiver);
        stepChatAPI = api;
        this.tokenRepository = tokenRepository;
    }

    public void sendMessage(SendMessage content) {
        stepChatAPI.sendMessage(tokenRepository.getToken(), content).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code() == 200){
                    //messageReceiver.onMessageReceive(response.body());
                }else{
                    Log.e("fetch", "Failed to send message to server. Code: "+ response.code());
                    getReceiver().onFetchFailed("Failed to send message to server. Code: "+ response.code());
                }
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("fetch", "Failed to send message to server. Details: "+ t.getMessage());
                getReceiver().onFetchFailed("Failed to send message to server. Details: "+ t.getMessage());
            }
        });
    }

    @Override
    public synchronized void fetch() {
        stepChatAPI.fetchMessages(tokenRepository.getToken()).enqueue(new Callback<Message[]>() {
            @Override
            public void onResponse(Call<Message[]> call, Response<Message[]> response) {
                if(response.code() == 200) {
                    for (Message message : response.body()){
                        getReceiver().onFetchReceive(message);
                    }
                }else {
                    getReceiver().onFetchFailed("Failed to fetch message from server. Code: "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Message[]> call, Throwable t) {
                getReceiver().onFetchFailed("Failed to fetch message from server. Details: "+ t.getMessage());
            }
        });
    }
}

