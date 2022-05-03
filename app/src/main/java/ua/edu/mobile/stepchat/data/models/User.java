package ua.edu.mobile.stepchat.data.models;

import android.os.Debug;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

import ua.edu.mobile.stepchat.shared.Config;

public class User {
    private String id;
    private String name;
    private String email;
    @SerializedName("avatarurl")
    private String avatarURL;
    @SerializedName("lastfetchtime")
    private long lastFetchTime;

    public User(String id, String name, String email, String avatarURL, long lastFetchTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.lastFetchTime = lastFetchTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public long getLastFetchTime() {
        return lastFetchTime;
    }

    public boolean isActive() {
        long time = Calendar.getInstance().getTimeInMillis();
        return getLastFetchTime() >= (time - (Config.FETCH_MESSAGES_MS+10000));
    }
}
