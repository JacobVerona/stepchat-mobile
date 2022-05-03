package ua.edu.mobile.stepchat.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.edu.mobile.stepchat.data.models.Message;
import ua.edu.mobile.stepchat.data.models.SendMessage;
import ua.edu.mobile.stepchat.data.models.User;

public interface StepChatAPI {

    @GET("user/all")
    Call<User[]> getUsers(@Header("idToken") String idToken);

    @GET("user/id?")
    Call<User> getUserById(@Header("idToken") String idToken, @Query("userId") String userId);

    @GET("user/active")
    Call<User> getUserByToken(@Header("idToken") String idToken);

    @GET("user/fetch")
    Call<Message[]> fetchMessages(@Header("idToken") String idToken);

    @POST("user/send")
    Call<Message> sendMessage(@Header("idToken") String idToken, @Body SendMessage message);
}
