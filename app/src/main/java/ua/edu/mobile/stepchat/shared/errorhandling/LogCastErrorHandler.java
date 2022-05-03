package ua.edu.mobile.stepchat.shared.errorhandling;

import android.util.Log;

public class LogCastErrorHandler implements ErrorHandler<String> {
    @Override
    public void onError(String error) {
        Log.e("ERROR", error);
    }
}
