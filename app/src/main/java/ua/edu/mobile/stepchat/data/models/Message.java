package ua.edu.mobile.stepchat.data.models;

import com.google.gson.annotations.SerializedName;

public class Message {

    private int id;
    @SerializedName("userid")
    private String userId;
    private String content;
    @SerializedName("sendtime")
    private long sendTime;

    public Message(int id, String userId, String content, long sendTime) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public long getSendTime() {
        return sendTime;
    }
}
