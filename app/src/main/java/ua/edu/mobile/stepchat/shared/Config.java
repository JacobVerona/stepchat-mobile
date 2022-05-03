package ua.edu.mobile.stepchat.shared;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Config {

    public static final String BASE_URL = "https://stepchat.herokuapp.com/";
    //public static final String BASE_URL = "http://192.168.1.6:3000/";

    public static final int FETCH_MESSAGES_MS = 3000;
    public static final int FETCH_USERS_MS = 5000;
    public static final DateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyyy kk:mm");

    public static final String API_KEY = "AIzaSyDo1GVEMgaiocf1-K7y7pf0CEcwfD1pN04";
    public static final String SERVER_CLIENT_ID = "903801528075-8ribbrcup04r0uugmvs6ri4ekpvr5ur0.apps.googleusercontent.com";
}
