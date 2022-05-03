package ua.edu.mobile.stepchat.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.edu.mobile.stepchat.data.repository.TokenRepository;

public class API {

    private static Retrofit retrofit = null;
    private static TokenRepository tokenRepository = null;

    public static Retrofit getApiClient() {
        if(retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }

    public static TokenRepository getTokenRepository() {
        if(tokenRepository == null){
            tokenRepository = new TokenRepository();
        }
        return tokenRepository;
    }
}
